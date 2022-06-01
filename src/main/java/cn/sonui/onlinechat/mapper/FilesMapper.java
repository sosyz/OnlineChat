package cn.sonui.onlinechat.mapper;

import cn.sonui.onlinechat.model.FileModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesMapper {
    /**
     * 删除文件记录
     *
     * @param id 文件id
     * @return 删除结果
     */
    @Delete("DELETE FROM `files` WHERE id = #{id}")
    int deleteById(String id);

    /**
     * 删除文件记录
     *
     * @param path 文件名
     * @return 删除结果
     */
    @Delete("DELETE FROM `files` WHERE path = #{path}")
    int deleteByPath(String path);

    /**
     * 插入文件记录
     *
     * @param record 文件记录
     * @return 插入结果
     */
    @Insert("INSERT INTO `files`(`id`, `path`, `name`, `size`, `type`, `create_time`, `author`, `status`, `file_type`) VALUES (#{id}, #{path}, #{name}, #{size}, #{type}, #{createTime}, #{author}, #{status}, #{fileType})")
    int insert(FileModel record);

    /**
     * 根据id查询文件记录
     *
     * @param id 文件id
     * @return 文件记录
     */
    @Select("SELECT * FROM `files` WHERE id = #{id}")
    FileModel selectById(String id);

    /**
     * 根据文件名查询文件信息
     *
     * @param path 文件名
     * @return 文件信息
     */
    @Select("SELECT * FROM `files` WHERE path = #{path}")
    FileModel selectByName(String path);

    /**
     * 查询用户上传的所有文件
     *
     * @param uid 用户id
     * @return 文件列表
     */
    @Select("SELECT * FROM `files` WHERE author = #{author}")
    List<FileModel> getFileListByUid(Long uid);


}
