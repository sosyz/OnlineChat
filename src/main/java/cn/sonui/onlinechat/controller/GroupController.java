package cn.sonui.onlinechat.controller;

import cn.sonui.onlinechat.model.Group;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.GroupMembersService;
import cn.sonui.onlinechat.service.GroupService;
import cn.sonui.onlinechat.vo.impl.UniversalVo;
import cn.sonui.onlinechat.vo.impl.group.GroupInfoVo;
import cn.sonui.onlinechat.vo.impl.group.GroupListVo;
import cn.sonui.onlinechat.vo.impl.group.GroupMembersListVo;
import com.tairitsu.ignotus.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/group")
public class GroupController {
    @Autowired
    GroupService groupService;
    @Autowired
    GroupMembersService groupMembersService;

    @Autowired
    CacheService cache;

    @PostMapping("/info")
    public GroupInfoVo info(@RequestParam("id") String id) {
        GroupInfoVo ret = new GroupInfoVo();
        User user = cache.get("token", User.class, null);
        if (user == null) {
            ret.setCode(1);
            ret.setMsg("请先登录");
            return ret;
        }
        Group res = groupService.query(id);
        if (res == null) {
            ret.setCode(2);
            ret.setMsg("获取失败");
        } else {
            ret.setCode(0);
            ret.setGroup(new Group(res.getGroupId(), res.getName(), res.getAvatar()));
        }
        return ret;
    }

    @PostMapping("/list")
    public GroupListVo list(
            @RequestParam(value = "key") String key
    ) {
        return new GroupListVo(0, "查询完毕", "".equals(key) ? groupService.queryAll() : groupService.queryByKey(key));
    }

    @PostMapping("/create")
    public UniversalVo create(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "id") String key,
            @RequestParam(value = "avatar") String avatar,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }
        // TODO: 直接信任头像URL有风险，做一个映射
        if ("".equals(name) || "".equals(key) || "".equals(avatar)) {
            return new UniversalVo(1, "参数不能为空");
        } else {
            Integer s = groupService.create(name, key, avatar);
            groupMembersService.addGroupMember(key, 1L, user.getUid());
            groupMembersService.setPri(key, user.getUid(), 2L);
            return new UniversalVo(s != 0 ? 0 : 1, s != 0 ? "创建成功" : "创建失败");
        }
    }

    @PostMapping("/join")
    public UniversalVo join(
            @RequestParam(value = "id") String groupId,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVo(1, "群组不存在");
        } else {
            Integer res = groupMembersService.addGroupMember(groupId, 1L, user.getUid());
            return new UniversalVo(res != 1 ? res : 0, res != 1 ? "加入失败" : "加入成功");
        }
    }

    @PostMapping("/leave")
    public UniversalVo leave(
            @RequestParam(value = "id") String groupId,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVo(1, "群组不存在");
        }
        Integer res = groupMembersService.deleteGroupMember(groupId, 0L, user.getUid());
        return new UniversalVo(res != 1 ? res : 0, res != 1 ? "退出失败" : "退出成功");
    }

    @PostMapping("/delete")
    public UniversalVo delete(
            @RequestParam(value = "id") String groupId
    ) {
        Integer s = groupService.delete(groupId);
        return new UniversalVo(s != 0 ? 0 : 1, s != 0 ? "删除成功" : "删除失败");
    }

    @PostMapping("/update")
    public UniversalVo update(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "avatar") String avatar,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVo(2, "群组不存在");
        }

        if ("".equals(name) || "".equals(avatar)) {
            return new UniversalVo(3, "参数不能为空");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
            return new UniversalVo(4, "没有权限");
        }
        Integer s = groupService.update(groupId, name, avatar);
        return new UniversalVo(s != 0 ? 0 : 1, s != 0 ? "更新成功" : "更新失败");
    }

    @PostMapping("/member/list")
    public GroupMembersListVo memberList(
            @RequestParam(value = "id") String groupId,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new GroupMembersListVo(1, "请先登录");
        } else {
            return new GroupMembersListVo(0, "查询完毕", groupMembersService.queryGroupMember(groupId, user.getUid()));
        }
    }

    @PostMapping("/member/add")
    public UniversalVo memberAdd(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "uid") Long uid,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVo(2, "群组不存在");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
            return new UniversalVo(3, "没有权限");
        }

        if (groupMembersService.queryGroupMember(groupId, uid) != null) {
            return new UniversalVo(4, "已经在群组中");
        }

        Integer s = groupMembersService.addGroupMember(groupId, 0L, uid);
        return new UniversalVo(s != 1 ? s : 0, s != 1 ? "添加失败" : "添加成功");
    }

    @PostMapping("/member/delete")
    public UniversalVo memberDelete(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "uid") Long uid,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVo(2, "群组不存在");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
            return new UniversalVo(3, "没有权限");
        }

        Integer s = groupMembersService.deleteGroupMember(groupId, 0L, uid);
        return new UniversalVo(s != 1 ? s : 0, s != 1 ? "删除失败" : "删除成功");
    }
//
//    @PostMapping("/member/update")
//    public UniversalVO memberUpdate(
//            @RequestParam(value = "id") String groupId,
//            @RequestParam(value = "uid") Long uid,
//            @RequestParam(value = "permission") Long permission,
//            @CookieValue(value = "token") String token
//    ) {
//        User user = cache.get(token, User.class, null);
//        if (user == null) {
//            return new UniversalVO(1, "请先登录");
//        }
//
//        if (groupService.query(groupId) == null) {
//            return new UniversalVO(2, "群组不存在");
//        }
//
//        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
//            return new UniversalVO(3, "没有权限");
//        }
//
//        Integer s = groupMembersService.setPri(groupId, uid, permission);
//        return new UniversalVO(s != 1 ? s : 0, s != 1 ? "修改失败" : "修改成功");
//    }

    @RequestMapping("/admin/set")
    public UniversalVo memberTransfer(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "uid") Long uid,
            @RequestParam(value = "permission") Long permission,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVo(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVo(2, "群组不存在");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) != 2L) {
            return new UniversalVo(3, "没有权限");
        }

        Integer s = groupMembersService.setPri(groupId, uid, permission);
        return new UniversalVo(s != 1 ? s : 0, s != 1 ? "修改失败" : "修改成功");
    }
}