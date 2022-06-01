package cn.sonui.onlinechat.controller;

import cn.sonui.onlinechat.model.FileModel;
import cn.sonui.onlinechat.model.User;
import cn.sonui.onlinechat.service.FileServices;
import cn.sonui.onlinechat.vo.impl.file.FileVo;
import com.tairitsu.ignotus.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/api/file")
public class FileController {
    @Autowired
    private FileServices fileServices;
    @Autowired
    private CacheService cache;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public FileVo upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(name = "type", defaultValue = "0") Integer type,
            @CookieValue("token") String token) {
        // 上传
        FileVo ret = new FileVo();
        User user = cache.get(token, User.class, null);
        if (user == null) {
            ret.setCode(1);
            ret.setMsg("请先登录");
            return ret;
        }

        if (type <= 0 || type > 2) {
            ret.setCode(1);
            ret.setMsg("type error");
        } else {
            FileModel res = fileServices.upload(file, type, user.getUid());
            if (res == null) {
                ret.setCode(2);
                ret.setMsg("上传失败");
            } else {
                ret.setCode(0);
                ret.setMsg("上传成功");
                ret.setFid(res.getId());
            }
        }
        return ret;
    }

    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    public FileVo delete(
            @RequestParam("id") String id,
            @CookieValue("token") String token) {
        FileVo ret = new FileVo();
        User user = cache.get(token, User.class, null);
        if (user == null) {
            ret.setCode(1);
            ret.setMsg("请先登录");
            return ret;
        }
        FileModel info = fileServices.info(id);
        if (info == null) {
            ret.setCode(1);
            ret.setMsg("file not found");
        } else {
            if (info.getAuthor() != user.getUid()) {
                ret.setCode(1);
                ret.setMsg("非上传者无法删除");
            } else {
                Boolean res = fileServices.delete(id);
                if (res) {
                    ret.setCode(2);
                    ret.setMsg("删除失败");
                } else {
                    ret.setCode(0);
                    ret.setMsg("删除成功");
                }
            }
        }
        return ret;
    }

    /**
     * 获取文件
     */
    @GetMapping("/get")
    public ResponseEntity<byte[]> get(@RequestParam("id") String id) throws Exception {
        return fileServices.download(id);
    }
}