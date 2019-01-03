package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Data
public class ReviewCommentReq {

    private int review_comment_id;
    private int review_id;
    private String user_id;
    private String content;
    private Date created_date = new Date();

    public boolean checkProperties() {

        return (content != null);
    }
}
