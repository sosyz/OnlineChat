package cn.sonui.onlinechat.controller;

import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.UserService;
import cn.sonui.onlinechat.vo.impl.user.LoginVo;
import cn.sonui.onlinechat.vo.impl.user.UserInfoVo;
import com.tairitsu.ignotus.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CacheService cache;

    @PostMapping("/register")
    public LoginVo register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("nickname") String nickname,
            @RequestParam("avatar") String avatar,
            @RequestParam("readme") String readme
    ) {
        LoginVo ret;
        Integer r = userService.register(new User(username, password, email, nickname, avatar, readme, (short) 0));
        if (r != 1) {
            ret = new LoginVo(1, "注册失败");
        } else {
            ret = new LoginVo(0, "注册成功");
        }
        return ret;
    }

    @PostMapping("/login")
    public LoginVo login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse responseBody
    ) {
        if ("".equals(username) || "".equals(password)) {
            return new LoginVo(1, "用户名或密码不能为空");
        }
        LoginVo ret;
        String token = userService.login(username, password);
        if (token == null) {
            ret = new LoginVo(1, "用户名或密码错误");
        } else {
            Cookie cookie = new Cookie("token", token);
            //Https 安全cookie
            cookie.setSecure(true);
            responseBody.addCookie(cookie);
            cache.put(token, userService.info(username));
            ret = new LoginVo(0, "登录成功", token);
        }
        return ret;
    }

    @GetMapping("/self")
    public UserInfoVo myself(
            @CookieValue(value = "token", defaultValue = "") String token
    ) {
        if ("".equals(token)) {
            return new UserInfoVo(3, "未登录");
        }
        UserInfoVo ret;
        User user = userService.self(token);
        if (user == null) {
            ret = new UserInfoVo(2, "用户不存在");
        } else {
            ret = new UserInfoVo(0, "获取成功", user.getUserName(), user.getAvatar(), user.getNickName(), user.getGrade(), user.getEmail(), user.getReadme(),
                    user.getRegisterTime(), user.getLastLoginTime(), user.getLastLoginIp(), user.getPrivateId());
        }
        return ret;
    }

    @GetMapping("/info")
    public UserInfoVo userInfo(
            @RequestParam("key") String key
    ) {
        UserInfoVo ret;
        User user = userService.info(key);
        if (user == null) {
            ret = new UserInfoVo(2, "用户不存在");
        } else {
            ret = new UserInfoVo(0, "获取成功");
            ret.setNickname(user.getNickName());
            ret.setAvatar(user.getAvatar());
            ret.setGrade(user.getGrade());
            ret.setReadme(user.getReadme());
            ret.setPrivateId(user.getPrivateId());
        }
        return ret;
    }

}
