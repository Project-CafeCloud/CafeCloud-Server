package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Membership {

    private  int cafe_id;
    private Date membership_create_date;
    private String cafe_img_url;
}
