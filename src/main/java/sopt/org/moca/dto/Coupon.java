package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coupon {

    private int coupon_id;
    private Date coupon_create_date;
    private int coupon_authentication_number;
}
