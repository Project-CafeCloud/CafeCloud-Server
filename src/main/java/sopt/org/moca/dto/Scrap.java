package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Scrap {
    private int cafe_id; //스크랩한 카페
    private String cafe_name;
    private String address_district_name;
    private String cafe_address_detail;
    private int cafe_rating_avg;
    private List<CafeImg> cafe_img_url;
}
