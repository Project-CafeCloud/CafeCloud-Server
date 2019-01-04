package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CafeInfo {

    private int cafe_id;
    private String cafe_name;
    private double cafe_latitude;
    private double cafe_longitude;
    private String cafe_phone;
    private String cafe_menu_img_url;
    private String address_district_name;
    private String cafe_address_detail;
    private int  cafe_rating_avg;
    private String cafe_times;
    private String cafe_days;
    private boolean cafe_option_parking;
    private boolean cafe_option_wifi;
    private boolean cafe_option_allnight;
    private boolean cafe_option_reservation;
    private boolean cafe_option_smokingarea;
}
