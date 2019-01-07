package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.Message;
import sopt.org.moca.dto.MessageBox;
import sopt.org.moca.dto.MyMessages;
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


    /**
     * 상대방과 쪽지 조회
     * **/
    @Select("SELECT receiver_id ,sender_id,message_content,message_send_date ,T.user_img_url as receiver_profile_img,S.user_img_url as sender_profile_img ,T.user_name  as  receiver_name , S.user_name as sender_name "
            +"FROM MESSAGE  inner join  USER as T on receiver_id = T.user_id inner join USER as S on sender_id = S.user_id "
            +"WHERE (receiver_id = #{my_id} AND sender_id = #{user_id}) OR (receiver_id = #{user_id} AND sender_id = #{my_id})")
    List<MessageBox> findByUserId(@Param("my_id") final String my_id , @Param("user_id") final String user_id);

    /**
     *
     * 내 쪽지함 조회
     *
     * @param my_id**/
    @Select("SELECT T.user_img_url as partner_profile_img,message_content,message_send_date,T.user_name  as  partner_name  FROM MESSAGE inner join  USER as T on receiver_id = T.user_id WHERE receiver_id = #{my_id};")
    List<MyMessages> findMyMessages(@Param("my_id")final String my_id);

}
