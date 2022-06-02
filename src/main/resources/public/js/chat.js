const box = Vue.createApp({
    data() {
        return {
            document: {
                dropdownView: false,
            },
            selected: '',
            selecttext: '',
            ws: null,
            myselfInfo: {},
            chatList: [
                {
                    id: '@test',
                    name: 'MCT魔帆',
                    avatar: './img/defaultGroup.jpg',
                }
            ],
            msgList: {
            },
            userList: [
                {
                    id: -1,
                    name: '获取中...',
                    avatar: '',
                }
            ],
            nowGroup: '@test',
            sendMsgContent: [],
            localMsgId: 0,
        }
    },
    methods: {
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
            this.ws = new WebSocket('ws://' + window.location.host + '/v1/ws/chat?token=' + localStorage.getItem('token'))
            this.ws.onopen = () => {
                console.log("建立websocket连接成功");
            };
            this.ws.onmessage = this.newMsg;
            this.ws.onclose = () => {
                console.log("websocket连接已关闭");
            }
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
        sendMsg: async function (content) {
            this.creatSendMsg(document.querySelector("#msgSend > div").childNodes);
            let data = {
                "type": "SEND_MESSAGE",
                "msgType": 2,
                "receiver": this.nowGroup,
                "content": this.sendMsgContent
            }
            console.log(data)
            // this.ws.send(JSON.stringify(data));
        },
        getUserInfoFromLocal: function (uid) {
            let user = this.userList.find(user => user.uid === uid);
            if (user) {
                return user;
            }
            return {
                id: -1,
                name: '获取中...',
                avatar: '',
            };
        },
        arrayContentToString: function (content) {
            let msg = '';
            for (let n in content) {
                msg += content[n].content;
            }
            return msg;
        },
        getLastMessage: async function (groupId) {
            if (this.msgList[groupId]) {
                let content = this.msgList[groupId][this.msgList[groupId].length - 1]
                let sender = await this.getUserInfoFromLocal(content.sender).name
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