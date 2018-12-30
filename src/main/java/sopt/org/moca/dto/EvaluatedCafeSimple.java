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
    private String cafe_introduction;
    private int cafe_rating;
    private double latitude;
    private double longitude;
    private String cafe_img_main;
    private String nerby_subway;
}
