package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Data
public class ReviewReq {

    private int reviewId;
    private int cafeId;
    private int userId;
    private int rating;
    private String title;
    private String content;
    private MultipartFile[] image;
    private Date createdDate = new Date();

    public boolean checkProperties() {

        return (title != null && content != null && image != null);
    }
}
