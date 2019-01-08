package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewComment {

    private int review_comment_id;
    private String user_id;
    private String user_name;
    private String user_img_url;
    private String review_comment_content;
    private Date review_comment_date;
    private String time; // ~시간 전

    private boolean auth;

}
