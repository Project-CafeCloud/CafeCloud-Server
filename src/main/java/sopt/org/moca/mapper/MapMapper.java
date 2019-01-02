package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.Map;
import sopt.org.moca.model.MapReq;

import java.util.List;

@Mapper
public interface MapMapper {

    /**
     *
     *  반경 구하기
     *
     * **/
    @Select("SELECT *, (6371*acos(cos(radians(#{mapReq.cafe_latitude}))*cos(radians(cafe_latitude))*cos(radians(cafe_longitude) -radians(#{mapReq.cafe_longitude}))+sin(radians(#{mapReq.cafe_latitude}))*sin(radians(cafe_latitude)))) AS distance FROM CAFE HAVING distance <= 0.3 ORDER BY distance LIMIT 0,5")
    List<Map> findNearbyCafe(@Param("mapReq")final MapReq mapReq);

}
