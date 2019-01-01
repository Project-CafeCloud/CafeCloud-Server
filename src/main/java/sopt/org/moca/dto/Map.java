package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class Map {

    private int cafe_id;
    private Double cafe_latitude;
    private Double cafe_longitude;
    private String cafe_name;
    private String cafe_img_url;

    //거리
    private int distance;

}
