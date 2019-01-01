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

}
