package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CafeSimple {

    private int cafe_id;
    private String cafe_name;
    private String cafe_introduction;
    private double cafe_latitude;
    private double cafe_longitude;
    private String cafe_rating_avg;
    private String cafe_img_url;
}
