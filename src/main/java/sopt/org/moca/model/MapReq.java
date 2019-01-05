package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MapReq {

    private Double cafe_latitude;
    private Double cafe_longitude;
    private int cafe_id;
    private int is_cafe_detail;

}

