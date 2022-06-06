package cn.sonui.onlinechat.service.impl;

import cn.sonui.onlinechat.mapper.FilesMapper;
import cn.sonui.onlinechat.model.FileModel;
import cn.sonui.onlinechat.service.FileServices;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileServicesImpl implements FileServices {

    @Autowired
    private FilesMapper filesMapper;
    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 上传结果
     */
    public FileModel upload(MultipartFile file, Integer type, Long userId) {
        String path = UUID.randomUUID().toString(); // 文件名，使用 UUID 随机
        FileModel ret = new FileModel();
        ret.setId(path);
        ret.setName(file.getOriginalFilename());
        ret.setFileType(file.getContentType());
        ret.setType(1);
        ret.setSize(file.getSize());

        switch (type) {
            case 1:
                path = "avatar/" + path;
                break;
            case 2:
                path = "chat/" + path;
                break;
        }
        ret.setPath(path);

        ret.setCreateTime(new Date());
        ret.setAuthor(userId);

        if (filesMapper.insert(ret) != 0) {
            try {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName) // 存储桶
                        .object(path) // 文件路径
                        .stream(file.getInputStream(), file.getSize(), -1) // 文件内容
                        .contentType(file.getContentType()) // 文件类型
                        .build());
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                System.out.println(e.getMessage());
                return null;
            }
            return ret;
        } else {
            System.out.println("文件上传失败1");
            return null;
        }
    }

    /**
     * 下载文件
     *
     * @param id 文件名
     * @return 下载结果
     */
    public ResponseEntity<byte[]> download(String id) {
        FileModel file = filesMapper.selectById(id);
        if (file == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        ResponseEntity<byte[]> responseEntity = null;
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(file.getPath()).build());
            out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            //封装返回值
            byte[] bytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), StandardCharsets.UTF_8));
            headers.setContentLength(bytes.length);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setAccessControlExposeHeaders(List.of("*"));
            responseEntity = new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseEntity;
    }

    /**
     * 删除文件
     *
     * @param id 文件ID
     * @return 删除结果
     */
    public Boolean delete(String id) {
        try {
            FileModel file = filesMapper.selectById(id);
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName) // 存储桶
                    .object(file.getPath()) // 文件名
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            return false;
        }
        return filesMapper.deleteById(id) > 0;
    }

    /**
     * 获取文件信息
     *
     * @param id 文件ID
     * @return 文件信息
     */
    public FileModel info(String id) {
        return filesMapper.selectById(id);
    }
}
