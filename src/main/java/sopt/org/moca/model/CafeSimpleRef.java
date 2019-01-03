package sopt.org.moca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CafeSimpleRef {
    private int cafe_id;
    private String cafe_name;
    private String cafe_introduction;
    private String cafe_nearby_subway;
    private int cafe_time_to_subway;
    private String cafe_rating_avg;
    private String cafe_img_url;
}
