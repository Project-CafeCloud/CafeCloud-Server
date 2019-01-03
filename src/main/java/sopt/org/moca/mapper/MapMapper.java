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
     *  반경 구하기 및 카페와 떨어진 거리계산
     *
     * **/
    @Select("SELECT c.*, i.cafe_img_url, (6371*acos(cos(radians(#{mapReq.cafe_latitude}))*cos(radians(cafe_latitude))*cos(radians(cafe_longitude) -radians(#{mapReq.cafe_longitude}))+sin(radians(#{mapReq.cafe_latitude}))*sin(radians(cafe_latitude)))) AS distance FROM CAFE c LEFT JOIN CAFE_IMG i ON c.cafe_id = i.cafe_id HAVING distance <= 3 ORDER BY distance "
            )
    List<Map> findNearbyCafe(@Param("mapReq")final MapReq mapReq);

}
