package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Data
public class ReviewCommentReq {

    private int reviewCommentId;
    private int reviewId;
    private String userId;
    private String content;
    private Date createdDate = new Date();

    public boolean checkProperties() {

        return (content != null);
    }
}
