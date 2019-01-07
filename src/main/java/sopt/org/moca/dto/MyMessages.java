package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyMessages {

    private String partner_name;
    private String partner_profile_img;
    private Date message_send_date;
    private String message_content;
}
