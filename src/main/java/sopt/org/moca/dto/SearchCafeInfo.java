package sopt.org.moca.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchCafeInfo {
    private int cafe_id;
    private String cafe_name;
    private String cafe_img_url;
    private String cafe_address_detail;
}
