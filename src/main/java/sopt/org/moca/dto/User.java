package sopt.org.moca.dto;


import lombok.Data;

@Data
public class User {

    private String user_id;
    private String user_name;
    private String user_phone;
    private boolean auth;
}
