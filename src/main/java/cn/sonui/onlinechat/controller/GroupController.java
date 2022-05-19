package cn.sonui.onlinechat.controller;

import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.GroupMembersService;
import cn.sonui.onlinechat.service.GroupService;
import cn.sonui.onlinechat.vo.impl.UniversalVO;
import cn.sonui.onlinechat.vo.impl.group.GroupListVO;
import cn.sonui.onlinechat.vo.impl.group.GroupMembersListVO;
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

    @PostMapping("/list")
    public GroupListVO list(
            @RequestParam(value = "key") String key
    ) {
        return new GroupListVO(0, "查询完毕", key.equals("") ? groupService.queryAll() : groupService.queryByKey(key));
    }

    @PostMapping("/create")
    public UniversalVO create(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "id") String key,
            @RequestParam(value = "avatar") String avatar,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }
        // TODO: 直接信任头像URL有风险，做一个映射
        if (name.equals("") || key.equals("") || avatar.equals("")) {
            return new UniversalVO(1, "参数不能为空");
        } else {
            Integer s = groupService.create(name, key, avatar);
            groupMembersService.addGroupMember(key, 1L, user.getUid());
            groupMembersService.setPri(key, user.getUid(), 2L);
            return new UniversalVO(s != 0 ? 0 : 1, s != 0 ? "创建成功" : "创建失败");
        }
    }

    @PostMapping("/join")
    public UniversalVO join(
            @RequestParam(value = "id") String groupId,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVO(1, "群组不存在");
        } else {
            Integer res = groupMembersService.addGroupMember(groupId, 1L, user.getUid());
            return new UniversalVO(res != 1 ? res : 0, res != 1 ? "加入失败" : "加入成功");
        }
    }

    @PostMapping("/leave")
    public UniversalVO leave(
            @RequestParam(value = "id") String groupId,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVO(1, "群组不存在");
        }
        Integer res = groupMembersService.deleteGroupMember(groupId, 0L, user.getUid());
        return new UniversalVO(res != 1 ? res : 0, res != 1 ? "退出失败" : "退出成功");
    }

    @PostMapping("/delete")
    public UniversalVO delete(
            @RequestParam(value = "id") String groupId
    ) {
        Integer s = groupService.delete(groupId);
        return new UniversalVO(s != 0 ? 0 : 1, s != 0 ? "删除成功" : "删除失败");
    }

    @PostMapping("/update")
    public UniversalVO update(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "avatar") String avatar,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVO(2, "群组不存在");
        }

        if (name.equals("") || avatar.equals("")) {
            return new UniversalVO(3, "参数不能为空");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
            return new UniversalVO(4, "没有权限");
        }
        Integer s = groupService.update(groupId, name, avatar);
        return new UniversalVO(s != 0 ? 0 : 1, s != 0 ? "更新成功" : "更新失败");
    }

    @PostMapping("/member/list")
    public GroupMembersListVO memberList(
            @RequestParam(value = "id") String groupId,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new GroupMembersListVO(1, "请先登录");
        } else {
            return new GroupMembersListVO(0, "查询完毕", groupMembersService.queryGroupMember(groupId, user.getUid()));
        }
    }

    @PostMapping("/member/add")
    public UniversalVO memberAdd(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "uid") Long uid,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVO(2, "群组不存在");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
            return new UniversalVO(3, "没有权限");
        }

        if (groupMembersService.queryGroupMember(groupId, uid) != null) {
            return new UniversalVO(4, "已经在群组中");
        }

        Integer s = groupMembersService.addGroupMember(groupId, 0L, uid);
        return new UniversalVO(s != 1 ? s : 0, s != 1 ? "添加失败" : "添加成功");
    }

    @PostMapping("/member/delete")
    public UniversalVO memberDelete(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "uid") Long uid,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVO(2, "群组不存在");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) == 0L) {
            return new UniversalVO(3, "没有权限");
        }

        Integer s = groupMembersService.deleteGroupMember(groupId, 0L, uid);
        return new UniversalVO(s != 1 ? s : 0, s != 1 ? "删除失败" : "删除成功");
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
    public UniversalVO memberTransfer(
            @RequestParam(value = "id") String groupId,
            @RequestParam(value = "uid") Long uid,
            @RequestParam(value = "permission") Long permission,
            @CookieValue(value = "token") String token
    ) {
        User user = cache.get(token, User.class, null);
        if (user == null) {
            return new UniversalVO(1, "请先登录");
        }

        if (groupService.query(groupId) == null) {
            return new UniversalVO(2, "群组不存在");
        }

        if (groupMembersService.queryGroupMemberPermission(groupId, user.getUid()) != 2L) {
            return new UniversalVO(3, "没有权限");
        }

        Integer s = groupMembersService.setPri(groupId, uid, permission);
        return new UniversalVO(s != 1 ? s : 0, s != 1 ? "修改失败" : "修改成功");
    }
}