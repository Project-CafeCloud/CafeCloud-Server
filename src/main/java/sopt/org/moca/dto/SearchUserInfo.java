package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchUserInfo {
    private String user_id;
    private String user_name;
    private String user_status_comment;
    private boolean  follow_is ;
}
