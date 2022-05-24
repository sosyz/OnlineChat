const box = Vue.createApp({
    data() {
        return {
            ws: null,
            myselfInfo: {
                uid: 1,
            },
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
            ],
            nowGroup: '@test',
            sendMsgContent: ''
        }
    },
    methods: {
        onLoad: function () {
            this.ws = new WebSocket('ws://' + window.location.host + '/v1/ws/chat?token=' + localStorage.getItem('token'))
            this.ws.onopen = () => {
                console.log("建立websocket连接成功");
            };
            this.ws.onmessage = this.newMsg;
            this.ws.onclose = () => {
                console.log("websocket连接已关闭");
            }
        },
        sendMsg: async function (msg) {
            let data = {
                "type": "SEND_MESSAGE",
                "msgType": 2,
                "receiver": "@test",
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
            return this.userList.find(user => user.id === uid);
        },
        arrayContentToString: function (content) {
            let msg = '';
            for (let n in content) {
                msg += content[n].content;
            }
            return msg;
        },
        getLastMessage: function (groupId) {
            if (this.msgList[groupId]){
                let content = this.msgList[groupId][this.msgList[groupId].length - 1]
                let sender = this.getUserInfoFromLocal(content.sender).name
                return sender + ':' + this.arrayContentToString(content.content);
            }
            return ''
        },
        newMsg: function (data) {
            data = JSON.parse(data.data)
            switch (data.type) {
                case 'BROADCAST_MESSAGE':
                    //{"type": "BROADCAST_MESSAGE","groupId": '@test', "sender": 1, "content": [{"msgId": 1"type":1"content":"hello"},{"msgId": 2"type":1"content":"hello"},]}
                    console.log('newMsg')
                    if (this.msgList[data.groupId] === undefined) {
                        this.msgList[data.groupId] = [];
                    }
                    this.msgList[data.groupId].push({
                        sender: data.sender,
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