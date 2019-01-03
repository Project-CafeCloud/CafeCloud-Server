package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Review {

    private int review_id;
    private int cafe_id;
    private String user_id;
    private List<ReviewImage> image;
    private int review_rating;
    private String review_title;
    private String review_content;
    private Date review_date;

    private String cafe_name;
    private String cafe_address;
    private String time; // ~시간 전

    private int like_count;
    private int comment_count;

    private boolean auth;
    private boolean like;
}
