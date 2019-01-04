package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlusContents {

//    private String editor_id;
//    private String user_img_url;
    private int plus_contents_id;
    private int plus_subject_id;
    private int cafe_id;

    private String plus_contents_content;

}
