const box = Vue.createApp({
    data() {
        return {
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
            sendMsgContent: '',
            localMsgId: 0,
        }
    },
    methods: {
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
        sendMsg: async function (content) {
            let msg = [{
                msgId: this.localMsgId + 1,
                type: 1,
                content: content,
            }]

            let data = {
                "type": "SEND_MESSAGE",
                "msgType": 2,
                "receiver": this.nowGroup,
                "content": msg//[
                //     {
                //         "msgId": 1,
                //         "type": 1,
                //         "content": "hello"
                //     },
                //     {
                //         "msgId": 2,
                //         "type": 1,
                //         "content": "hello"
                //     }
                // ]
            }
            this.ws.send(JSON.stringify(data));
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
            if (this.msgList[groupId]){
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
                    if (this.msgList[data.groupId].length > 100){
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