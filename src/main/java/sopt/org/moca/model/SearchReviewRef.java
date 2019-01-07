package sopt.org.moca.model;

import lombok.Data;
import sopt.org.moca.dto.SearchReviewInfo;

@Data
public class SearchReviewRef {
    private int review_id;
    private String review_img_url;

    public SearchReviewRef(SearchReviewInfo searchReviewInfo)
    {
        review_id = searchReviewInfo.getReview_id();
        review_img_url = searchReviewInfo.getReview_img_url();
    }
}
