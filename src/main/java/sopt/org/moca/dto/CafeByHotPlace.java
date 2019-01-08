package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeByHotPlace {

   private int cafe_id;
   private String cafe_name;
   private String cafe_subway;
   private  int cafe_rating_avg;
   private String cafe_img_url;
   private  boolean evaluated_cafe_is;
}
