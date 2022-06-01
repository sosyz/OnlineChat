package cn.sonui.onlinechat.service;

import cn.sonui.onlinechat.model.FileModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileServices {
    /**
     * 上传文件
     * @param file 文件
     * @return 上传结果
     */
    FileModel upload(MultipartFile file, Integer type, Long userId);

    /**
     * 下载文件
     *
     * @param id 文件ID
     * @return 下载结果
     */
    ResponseEntity<byte[]> download(String id);

    /**
     * 删除文件
     * @param  id 文件ID
     * @return 删除结果
     */
    Boolean delete(String id);

    /**
     * 获取文件信息
     * @param id 文件ID
     * @return 文件信息
     */
    FileModel info(String id);
}
