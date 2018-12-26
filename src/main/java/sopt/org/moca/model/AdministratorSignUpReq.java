package sopt.org.moca.model;
import lombok.Data;

@Data
public class AdministratorSignUpReq {

    private String administrator_id;
    private String administrator_password;
    private String administrator_name;
    private String administrator_register_number;
    private String administrator_mocanumber;

}
