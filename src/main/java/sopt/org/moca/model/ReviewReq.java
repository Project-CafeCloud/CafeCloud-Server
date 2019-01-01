package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Data
public class ReviewReq {

    private int review_id;
    private int cafe_id;
    private String user_id;
    private int rating;
    private String title;
    private String content;
    private MultipartFile[] image;
    private Date created_date = new Date();

    public boolean checkProperties() {

        return (title != null && content != null && image != null);
    }
}
