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


}
