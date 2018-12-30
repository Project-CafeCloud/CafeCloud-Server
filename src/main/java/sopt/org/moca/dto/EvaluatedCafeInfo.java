package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EvaluatedCafeInfo {
   private String cafe_name;
   private String cafe_address;
   private String evaluated_cafe_total_evaluation;
   private String evaluated_cafe_rating;

}
