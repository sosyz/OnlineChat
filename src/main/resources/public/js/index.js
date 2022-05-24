function urlEncode(data) {
    let ret = "";
    for (let key in data)
        ret += "&" + key + "=" + encodeURIComponent(data[key])
    return ret.substring(1);
}

var card = new Vue({
    el: "#loginCard",
    data: {
        login: {
            username: '',
            password: '',
            usernameTip: false,
            passwordTip: false
        },
        register: {
            username: '',
            password: '',
            password2: '',
            usernameTip: false,
            passwordTip: false,
            password2Tip: false
        },
        registerPage: false,
    },
    methods: {
        registerFunc: function (user, pwd) {
            if (user !== '' && pwd) {

            }
        },
        loginFunc: function (user, pwd) {
            if (user !== '' && pwd !== '') {
                fetch(
                    '/v1/api/user/login',
                    {
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded",
                        },
                        method: "POST",
                        body: urlEncode({
                            username: user,
                            password: pwd
                        }),
                        credentials: "same-origin"
                    }
                ).then(res => {
                    if (res.status !== 200) {
                        console.log(res);
                    }
                    res.json().then(data => {
                        console.log(data);
                        if (data.code === 0) {
                            localStorage.setItem('token', data.token);
                            window.location.href = './rooms.html'
                        }else{
                            alert(data.msg);
                        }
                    });
                }).catch(err => {
                    console.log(err)
                })
            }
        }
    }
})