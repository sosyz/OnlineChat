const urlEncode = function (data) {
    let ret = "";
    for (let key in data)
        ret += "&" + key + "=" + encodeURIComponent(data[key])
    return ret.substring(1);
};
const apiUrl = {
    group: {
        create: '/v1/api/group/create',
        join: '/v1/api/group/join',
        leave: '/v1/api/group/leave',
        list: '/v1/api/group/list',
        delete: '/v1/api/group/delete',
    },
    user: {
        login: '/v1/api/user/login',
        register: '/v1/api/user/register',
        getUserInfo: '/v1/api/user/info',
        myself: '/v1/api/user/self'
    }
}
const onlineChat = {
    group: {
        creat: (name, id, avatar) => {
            return fetch(
                apiUrl.group.create,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        name: name,
                        id: id,
                        avatar: avatar
                    }),
                    credentials: "same-origin"
                }
            )
        },
        join: groupId => {
            return fetch(
                apiUrl.group.join,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        id: groupId
                    }),
                    credentials: "same-origin"
                }
            )
        },
        list: key => {
            return fetch(
                apiUrl.group.list,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        key: key
                    }),
                    credentials: 'same-origin'
                }
            )
        },
        leave: id => {
            return fetch(
                apiUrl.group.leave,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        id: id
                    }),
                    credentials: 'same-origin'
                }
            )
        },
        delete: id => {
            return fetch(
                apiUrl.group.delete,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        id: id
                    }),
                    credentials: 'same-origin'
                }
            )
        }
    },
    user: {
        login: (username, password) => {
            return fetch(
                apiUrl.user.login,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        username: username,
                        password: password
                    }),
                    credentials: "same-origin"
                }
            )
        },
        register: (username, password) => {
            return fetch(
                apiUrl.user.register,
                {
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded",
                    },
                    method: "POST",
                    body: urlEncode({
                        username: username,
                        password: password
                    }),
                    credentials: "same-origin"
                }
            )
        },
        getUserInfoByUid: uid => {
            return fetch(
                apiUrl.user.getUserInfo + "?uid=" + uid,
                {
                    method: "GET",
                    credentials: "same-origin"
                }
            )
        },
        getUserInfoByKey: key => {
            return fetch(
                apiUrl.user.getUserInfo + "?key=" + key,
                {
                    method: "GET",
                    credentials: "same-origin"
                }
            )
        },
        myself: ()=>{
            return fetch(
                apiUrl.user.myself,
                {
                    method: "GET",
                    credentials: "same-origin",
                }
            )
        }
    }
};