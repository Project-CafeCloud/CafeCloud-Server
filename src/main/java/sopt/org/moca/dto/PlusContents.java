package sopt.org.moca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlusContents {

    private int plus_contents_id;
    private int plus_subject_id;
    private int cafe_id;
    private String cafe_name;
    private String address_district_name;
    private String plus_contents_content;
    private List<PlusContentImg> contentImages;

}
