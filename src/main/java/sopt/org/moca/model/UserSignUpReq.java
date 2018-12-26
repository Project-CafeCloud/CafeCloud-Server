package sopt.org.moca.model;


import lombok.Data;

@Data
public class UserSignUpReq {
    private String user_id;
    private String user_password;
    private String user_name;

}
