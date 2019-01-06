package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchUSerInfo {
    private String user_id;
    private String user_name;
    private String user_status_comment;
    private boolean is_follow;
}
