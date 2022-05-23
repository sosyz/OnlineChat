package cn.sonui.onlinechat.mapper;

import cn.sonui.onlinechat.model.MessageHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Sonui
 */
@Repository
public interface MessageHistoryMapper {
    /**
     * 插入消息
     *
     * @param message 待插入的消息
     * @return 消息ID
     */
    @Insert("INSERT INTO `messages` " +
            "(`sender`, `receiver`, `type`, `content`, `create_time`, `status`) " +
            "VALUES " +
            "(#{sender}, #{receiver}, #{type}, #{content}, NOW(), #{status})")
    @Result(column = "create_time", property = "createTime", javaType = Date.class)
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertMessage(MessageHistory message);

    /**
     * 查询消息
     *
     * @param id 消息id
     * @return 消息
     */
    @Select("SELECT * FROM `messages` WHERE `id` = #{id}")
    MessageHistory selectMessage(Integer id);


}
