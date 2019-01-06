package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.SearchCafeInfo;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("select CAFE.cafe_id ,cafe_name ,cafe_img_url,cafe_address_detail\n" +
            "from CAFE left join CAFE_IMG on  CAFE.cafe_id = CAFE_IMG.cafe_id inner join ADDRESS_DISTRICT on CAFE.cafe_address_district_id = ADDRESS_DISTRICT.address_district_id\n" +
            "natural join CAFE_CONCEPT natural join HOT_PLACE\n" +
            "where  address_district_name like'%${keyword}%' or hot_place_name like '%${keyword}%' or cafe_address_detail like '%${keyword}%'  or cafe_name like '%${keyword}%' ")
    List<SearchCafeInfo>searchCafeInfoList(@Param("keyword")final String keyword);

}
