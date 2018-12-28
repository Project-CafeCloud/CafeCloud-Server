package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class LoginReq {
    private String user_id;
    private String user_password;

}
