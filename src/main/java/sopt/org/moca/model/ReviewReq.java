package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Data
public class ReviewReq {

    private int reviewId;
    private int userId;
    private String body;
    private MultipartFile[] photo;
    private Date createdDate = new Date();

    public boolean checkProperties() {
        return (body != null && photo != null);
    }
}
