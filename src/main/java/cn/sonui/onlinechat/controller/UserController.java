package cn.sonui.onlinechat.controller;

import cn.sonui.onlinechat.VO.LoginVO;
import cn.sonui.onlinechat.VO.UserInfoVO;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public LoginVO login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ){
        LoginVO ret = new LoginVO();
        String token = userService.login(username, password);
        if (token == null) {
            ret.setCode(1);
            ret.setMsg("用户名或密码错误");
        }else{
            ret.setCode(0);
            ret.setMsg("登录成功");
            ret.setToken(token);
        }
        return ret;
    }

    @GetMapping("myself")
    public UserInfoVO myself(
            @CookieValue("token") String token
    ){
        UserInfoVO ret;// = new UserInfoVO();
        User user = userService.Self(token);
        if (user == null) {
            ret = new UserInfoVO(1, "用户不存在");
        }else {
            ret = new UserInfoVO(user.getUserName(), user.getAvatar(), user.getNickName(), user.getGrade(), user.getEmail(), user.getReadme(),
                    user.getRegisterTime(), user.getLastLoginTime(), user.getLastLoginIp(), user.getPrivateId());
            ret.setCode(0);
            ret.setMsg("获取成功");
        }
        return ret;
    }
}
