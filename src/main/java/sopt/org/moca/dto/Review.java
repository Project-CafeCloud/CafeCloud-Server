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
    private int userId;
    private List<ReviewImage> images;
    private int rating;
    private String title;
    private String content;
    private int likeCount;
    private Date createDate;

    private boolean auth;
    private boolean like;
}
