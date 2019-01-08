package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoryOfMembership {
   private String  cafe_name;
    private String cafe_menu_img_url;
    private Date  coupon_create_date;


}
