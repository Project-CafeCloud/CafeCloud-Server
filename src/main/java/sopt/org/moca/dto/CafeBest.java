package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CafeBest {

    private int cafe_id;
    private String cafe_name;
    private String cafe_img_url;
    private int scrap_count;
    private int review_count;
}
