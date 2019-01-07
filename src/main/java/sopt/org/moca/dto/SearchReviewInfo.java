package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchReviewInfo {
    private int review_id;
    private String review_img_url;
    private int like_count;
}
