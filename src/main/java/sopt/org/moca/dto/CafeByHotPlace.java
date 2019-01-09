package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/***
 * cafe_img 필드 삭제 (1/11)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CafeByHotPlace {

   private int cafe_id;
   private String cafe_name;
   private String cafe_subway;
   private  int cafe_rating_avg;
   private  boolean evaluated_cafe_is;
}
