package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Data
public class UserSignUpReq {

    private String user_id;
    private String user_password;
    private String user_name;
    private String user_phone;
    private String user_status_comment;

    private MultipartFile user_img;
    //사진 저장 url 주소
    private String user_img_url;



    public boolean checkElement() {
        if(!isthereId()) return false;
        if(!istherePassword()) return false;
        if(!isthereName()) return false;
        if(!istherePhone()) return false;

        return true;
    }

    public boolean isthereId() {
        if(user_id == null) return false;
        return true;
    }

    public boolean istherePassword() {
        if(user_password == null) return false;
        return true;
    }

    public boolean isthereName(){
        if(user_name == null) return false;
        return true;
    }

    public boolean istherePhone(){
        if(user_phone == null) return false;
        return true;
    }
}
