package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CafeRankingInfo {

   private int cafe_id;
   private String cafe_name;
    private String cafe_menu_img_url;
    private String address_district_name;
    private int cafe_rating_avg;
    private  boolean evaluated_cafe_is;
}
