package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewImage {

    private int reviewImgId;
    private int reviewId;
    private String ImgUrl;
}
