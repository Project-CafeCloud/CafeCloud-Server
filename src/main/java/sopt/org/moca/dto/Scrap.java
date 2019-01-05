package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Scrap {
    private String user_id; //스크랩한 유저
    private int cafe_id; //스크랩한 카페
    private List<CafeImg> cafe_img_url;
}
