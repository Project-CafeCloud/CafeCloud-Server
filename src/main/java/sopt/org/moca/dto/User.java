package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import sopt.org.moca.model.UserSignUpReq;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private String user_id;
    private String user_name;
    private String user_phone;
    private String user_img_url;
    private String user_status_comment;
    private boolean auth;

//    public void update(final UserSignUpReq usersignUpReq) {
//        if(usersignUpReq.getUser_name() != null) user_name = usersignUpReq.getUser_name();
//        if(usersignUpReq.getUser_phone() != null) user_phone = usersignUpReq.getUser_phone();
//        if(usersignUpReq.getUser_status_comment() != null) user_status_comment = usersignUpReq.getUser_status_comment();
//        if(usersignUpReq.getUser_img() != null) user_img = usersignUpReq.getUser_img_url();
//    }
}
