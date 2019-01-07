package sopt.org.moca.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Slf4j
@Data
public class MessageReq {

    private String message_content;
    private MultipartFile message_img;
    private String receiver_id;
    private String sender_id;
    private Date message_send_date = new Date();

    public boolean checkElement(){
        if(!isThereRId()) return false;
        if(!isThereSId()) return false;
        if(!isthereMC()) return false;

        return true;
    }

    public boolean isThereRId() {
        if(receiver_id == null) return false;
        return true;
    }

    public boolean isThereSId(){
        if(sender_id == null) return false;
        return true;
    }
    public boolean isthereMC(){
        if(message_content == null) return false;
        return true;
    }


}
