const box = Vue.createApp({
    data() {
        return {
            modal: {
                title: '',
                type: '',
            },
            document: {
                dropdownView: false,
            },
            alert: [
                // {
                //     class: 'info',
                //     message: '这是一条系统消息',
                // },
                // {
                //     class: 'success',
                //     message: '这是一条成功消息',
                // },
                // {
                //     class: 'warning',
                //     message: '这是一条警告消息',
                // },
                // {
                //     class: 'error',
                //     message: '这是一条错误消息',
                // }
            ],
            selected: '',
            selecttext: '',
            ws: null,
            myselfInfo: {
                uid: 1,
            },
            chatList: [
            ],
            msgList: {
            },
            userList: [
            ],
            nowGroup: 1,
            sendMsgContent: [],
            localMsgId: 0,
        }
    },
    methods: {
        addAlert(className, message) {
            this.alert.push({
                class: className,
                message: message,
            })
        },
        apiJoinGroup(groupId) {
            onlineChat.group.join(groupId).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        console.log(data);
                        this.addAlert(
                            data.code === 0 ? 'success' : 'error',
                            data.code === 0 ? '加入群成功' : '加入群失败 错误原因: ' + data.msg
                        );
                        if (data.code === 0) {
                            this.updateGroupList();
                        }
                    })
                } else {
                    this.addAlert('error', '服务器错误');
                }
            });
        },
        apiCreateGroup(name, id, avatar) {
            onlineChat.group.create(name, id, avatar).then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        console.log(data);
                        this.addAlert(
                            data.code === 0 ? 'success' : 'warning',
                            data.code === 0 ? '创建群成功' : '创建群失败 错误原因: ' + data.msg
                        );
                        if (data.code === 0) {
                            this.updateGroupList();
                        }
                    })
                } else {
                    this.addAlert('error', '服务器错误');
                }
            });
        },
        updateGroupList: function () {
            onlineChat.group.inList().then(res => {
                if (res.status === 200) {
                    res.json().then(data => {
                        console.log(data);
                        if (data.code === 0) {
                            this.chatList = data.groups;
                        } else {
                            this.addAlert('error', '获取群列表失败 错误原因: ' + data.msg);
                        }
                    })
                } else {
                    this.addAlert('error', '服务器错误');
                }
            });
        },
        inputPaste: function (e, value) {
            // e.preventDefault();
            // 判断是否为图片
            console.log(e.clipboardData.files)

            let file = e.clipboardData.files[0]
            console.log(file)
            // 判断是否为图片
            if (file.type.indexOf('image') !== -1) {
                // 获取文件baase64
                let reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function (e) {
                    let base64 = e.target.result;
                    let cnt = {
                        type: 2, //image
                        content: base64,
                    }
                    vm.data.sendMsgContent.push(cnt);
                    console.log(base64)
                }

            }
        },
        onLoad: function () {
            onlineChat.user.myself().then(res => {
                res.json().then(data => {
                    console.log(data);
                    this.myselfInfo = {
                        uid: data.uid,
                        name: data.name,
                        grade: data.grade,
                        nickName: data.nickName
                    }
                })
            })
            this.updateGroupList();
            this.ws = new WebSocket('ws://' + window.location.host + '/v1/ws/chat?token=' + localStorage.getItem('token'))
            this.ws.onopen = () => {
                console.log("建立websocket连接成功");
            };
            this.ws.onmessage = this.newMsg;
            this.ws.onclose = () => {
                console.log("websocket连接已关闭");
            }
        },
        upLoadFile: function (option) {
            let file = document.getElementById(option.eleId).files[0];
            if (!file) return;
            console.log(file);
            onlineChat.file.upload(file).then(res => {
                res.json().then(data => {
                    console.log(data);
                    if (data.code === 0) {
                        option.callback('/v1/api/file/get?id=' + data.fid);
                    }else{
                        option.callback(null);
                    }
                })
            })
            console.log();

        },
        creatSendMsg: function (e) {
            if (e === undefined) return;
            e.forEach(element => {
                if (element.nodeName !== '#text' && element.nodeName !== 'IMG') {
                    // 处理换行
                    if (element.nodeName === 'DIV') {
                        this.sendMsgContent.push({ msgId: this.sendMsgContent.length + 1, type: 3 });// 换行
                    }

                    this.creatSendMsg(element.firstChild.childNodes)
                } else {
                    let cnt = {
                        msgId: this.sendMsgContent.length + 1,
                        type: 1, //text
                        content: '',
                    }
                    if (element.nodeName === '#text') {
                        cnt.content = element.textContent;
                    }
                    if (element.nodeName === 'IMG') {
                        cnt.type = 2;
                        cnt.content = element.src;
                    }
                    this.sendMsgContent.push(cnt);
                }
            });
        },
        sendMsg: async function () {
            this.creatSendMsg(document.querySelector("#msgSend > div").childNodes);
            let data = {
                "type": "SEND_MESSAGE",
                "msgType": 2,
                "receiver": this.nowGroup,
                "content": this.sendMsgContent
            }
            console.log(data)
            this.ws.send(JSON.stringify(data));
            this.sendMsgContent = [];
            document.querySelector("#msgSend > div").innerHTML = '';
        },
        getUserInfoFromLocal: function (sender) {
            let user = this.userList.find(user => user.uid === sender);
            return user === undefined ? { id: -1, name: '', avatar: '' } : user;
        },
        arrayContentToString: function (content) {
            let msg = '';
            for (let n in content) {
                msg += content[n].content;
            }
            return msg;
        },
        getLastMessage: function (groupId) {
            if (this.msgList[groupId]) {
                let content = this.msgList[groupId][this.msgList[groupId].length - 1]
                let sender = this.getUserInfoFromLocal(content.sender).nickName
                return sender + ':' + this.arrayContentToString(content.content);
            }
            return ''
        },
        newMsg: async function (data) {
            data = JSON.parse(data.data)
            switch (data.type) {
                case 'BROADCAST_MESSAGE':
                    //{"type": "BROADCAST_MESSAGE","groupId": '@test', "sender": 1, "content": [{"msgId": 1"type":1"content":"hello"},{"msgId": 2"type":1"content":"hello"},]}
                    console.log('newMsg')
                    let user = this.userList.find(user => user.uid === data.sender.uid);
                    if (!user) {
                        this.userList.push(data.sender);
                    }

                    if (this.msgList[data.groupId] === undefined) {
                        this.msgList[data.groupId] = [];
                    }
                    this.msgList[data.groupId].push({
                        sender: data.sender.uid,
                        content: data.content
                    })
                    if (this.msgList[data.groupId].length > 100) {
                        // 删除最早的50条消息
                        this.msgList = this.msgList[data.groupId].splice(0, this.msgList[data.groupId].length - 50);
                    }
                    break;
            }

        }
    }
});
const vm = box.mount('#box');

window.onload = () => {
    vm.onLoad();
}