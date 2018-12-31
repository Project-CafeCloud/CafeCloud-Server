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

    private int reviewId;
    private int cafeId;
    private String userId;
    private List<ReviewImage> image;
    private int rating;
    private String title;
    private String content;
    private Date createDate;

    private int likeCount;
    private int commentCount;

    private boolean auth;
    private boolean like;
}
