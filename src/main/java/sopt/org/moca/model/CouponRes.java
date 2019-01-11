package sopt.org.moca.model;

import lombok.Data;

import java.util.Date;

@Data
public class CouponRes {
    private int coupon_id;
    private String  coupon_authentication_number;
    private Date   coupon_create_date;
}
