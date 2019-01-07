package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageBox {

    private String sender_name;
    private String receiver_name;
    private String message_content;
    private Date message_send_date;
    private String sender_profile_img;
    private String receiver_profile_img;
}
