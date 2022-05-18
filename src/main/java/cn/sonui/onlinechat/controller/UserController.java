package cn.sonui.onlinechat.controller;

import cn.sonui.onlinechat.VO.impl.LoginVO;
import cn.sonui.onlinechat.VO.impl.UserInfoVO;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public LoginVO login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse responseBody
    ){
        LoginVO ret;
        String token = userService.login(username, password);
        if (token == null) {
            ret = new LoginVO(1, "用户名或密码错误");
        }else{
            Cookie cookie = new Cookie("token", token);
            //Https 安全cookie
            cookie.setSecure(true);
            responseBody.addCookie(cookie);
            ret = new LoginVO(0, "登录成功", token);
        }
        return ret;
    }

    @GetMapping("self")
    public UserInfoVO myself(
            @CookieValue(value = "token", defaultValue = "") String token
    ) {
        if (token.equals("")) {
            return new UserInfoVO(3, "未登录");
        }
        UserInfoVO ret;
        User user = userService.self(token);
        if (user == null) {
            ret = new UserInfoVO(2, "用户不存在");
        }else {
            ret = new UserInfoVO(0, "获取成功", user.getUserName(), user.getAvatar(), user.getNickName(), user.getGrade(), user.getEmail(), user.getReadme(),
                    user.getRegisterTime(), user.getLastLoginTime(), user.getLastLoginIp(), user.getPrivateId());
        }
        return ret;
    }

    @GetMapping("info")
    public UserInfoVO userInfo(
            @RequestParam("key") String key
    ) {
        UserInfoVO ret;
        User user = userService.info(key);
        if (user == null) {
            ret = new UserInfoVO(2, "用户不存在");
        }else {
            ret = new UserInfoVO(0, "获取成功");
            ret.setNickname(user.getNickName());
            ret.setAvatar(user.getAvatar());
            ret.setGrade(user.getGrade());
            ret.setReadme(user.getReadme());
            ret.setPrivateId(user.getPrivateId());
        }
        return ret;
    }

}
