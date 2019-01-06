package sopt.org.moca.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class ScrapReq {
    private String user_id; //스크랩한 유저
    private int cafe_id; //스크랩한 카페



}
