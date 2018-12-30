package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EvaluatedCafeSimple {

    private int cafe_id;
    private String cafe_name;
    private int evaluated_cafe_rating;
    private  String address_district_name;
    private String cafe_img_main;
}
