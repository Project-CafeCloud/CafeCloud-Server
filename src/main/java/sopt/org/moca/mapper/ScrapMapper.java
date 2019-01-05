package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Scrap;
import sopt.org.moca.model.ScrapReq;

import java.util.List;

@Mapper
public interface ScrapMapper {

    /**
     *
     * 스크랩 하기
     * **/
    @Insert("INSERT INTO SCRAP(user_id, cafe_id) VALUES(#{scrapReq.user_id}, #{scrapReq.cafe_id})")
    void save(@Param("scrapReq") final ScrapReq scrapReq);

    /**
     * 찜한 카페 리스트 조회
     *
     * **/
    @Select("SELECT * FROM SCRAP i WHERE user_id =#{user_id}")
    List<Scrap> findScrapList(@Param("user_id") final String user_id);

    /**
     *
     * 찜한 카페 한개 조회
     * **/
    @Select("SELECT * FROM SCRAP WHERE user_id = #{user_id} AND cafe_id = #{cafe_id}")
    Scrap findByID(@Param("user_id") final String user_id,@Param("cafe_id") final int cafe_id);


}
