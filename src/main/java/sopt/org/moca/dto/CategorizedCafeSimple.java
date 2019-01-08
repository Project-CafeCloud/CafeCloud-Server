package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategorizedCafeSimple {
    private int cafe_id;
    private String cafe_name;
    private String cafe_address_detail;
    private int cafe_rating_avg;
    private String cafe_img_url;
    private String cafe_main_menu_name;
    private String cafe_concept_name;
    private  boolean evaluated_cafe_is;
}

