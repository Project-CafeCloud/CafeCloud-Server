package sopt.org.moca.model;

import lombok.Data;
import sopt.org.moca.dto.SearchCafeInfo;

@Data
public class SearchCafeInfoRef {

    private int cafe_id;
    private String cafe_name;
    private String cafe_img_url;
    private String cafe_address_detail;
    private boolean type;


    public SearchCafeInfoRef(SearchCafeInfo searchCafeInfo, boolean type)
    {
        cafe_id = searchCafeInfo.getCafe_id();
        cafe_name = searchCafeInfo.getCafe_name();
        cafe_img_url = searchCafeInfo.getCafe_img_url();
        cafe_address_detail = searchCafeInfo.getCafe_address_detail();
        this.type = type;
    }
}
