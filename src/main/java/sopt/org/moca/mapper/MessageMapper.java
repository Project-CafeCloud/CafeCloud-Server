package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sopt.org.moca.model.MessageReq;

@Mapper
public interface MessageMapper {

    /**
     *
     * 쪽지 보내기
     * **/
    @Insert("INSERT INTO MESSAGE(sender_id,receiver_id,message_content,message_send_date,message_img_url) VALUES(#{messageReq.sender_id},#{messageReq.receiver_id}," +
            "#{messageReq.message_content},#{messageReq.message_send_date},#{messageReq.message_img_url})")
    void save(@Param("messageReq") final MessageReq messageReq);


}
