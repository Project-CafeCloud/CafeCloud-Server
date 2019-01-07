package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfo {

    private String user_id;
    private String user_name;
    private String user_img_url;
    private String user_status_comment;

    private int review_count;
    private int follower_count;
    private int following_count;

    private boolean auth;
    private boolean follow;
}
