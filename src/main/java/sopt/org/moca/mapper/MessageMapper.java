package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.Message;
import sopt.org.moca.model.MessageReq;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     *
     * 쪽지 보내기
     * **/

    @Insert("INSERT INTO MESSAGE(sender_id,receiver_id,message_content,message_send_date,message_img) VALUES(#{messageReq.sender_id},#{messageReq.receiver_id}," +
            "#{messageReq.message_content},#{messageReq.message_send_date},#{messageReq.message_img_url})")
    void save(@Param("messageReq") final MessageReq messageReq);


    @Select("SELECT * FROM MESSAGE ")
    List<Message> findByUserId(@Param("user_id") final String user_id);
}
