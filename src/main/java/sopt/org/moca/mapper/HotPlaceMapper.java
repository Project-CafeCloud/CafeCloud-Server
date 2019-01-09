package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.HotPlace;

import java.util.List;

@Mapper
public interface HotPlaceMapper {

    //HotPlace 리스트 조회
    @Select("SELECT * FROM HOT_PLACE")
    List<HotPlace>findAllHotPlace();


    // 추천 HotPlace 리스트 조회 (카페 많은 순)
    @Select("SELECT * FROM HOT_PLACE, " +
            "(SELECT COUNT(*) AS c, hot_place_id FROM CAFE GROUP BY hot_place_id) AS cafe " +
            "WHERE HOT_PLACE.hot_place_id = cafe.hot_place_id " +
            "ORDER BY cafe.c DESC " +
            "LIMIT #{num};")
    List<HotPlace> findBestHotPlace(final int num);


}
