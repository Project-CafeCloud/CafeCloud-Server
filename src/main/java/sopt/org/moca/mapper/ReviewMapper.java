package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Review;

import java.util.List;

// review 등록
// review_id 상세 조회
// cafe_id에 대한 review 모두 조회
// cafe_id에 대한 review best 3개 조회
// review_id에 대한 comment 등록
// review_id에 대한 comment 상세 조회

@Mapper
public interface ReviewMapper {

    // review 등록
    @Insert("INSERT INTO review(cafe_id, user_id, review_rating, review_title, review_content) " +
            "VALUES (#{review.cafe_id}, #{review.user_id}, #{review.rating}, #{review.title}, #{review.content})")
    void save(@Param("review") final Review review);

    // cafe_id에 대한 review 모두 조회
    @Select("SELECT * " +
            "FROM review " +
            "WHERE cafe_id = #(cafe_id)")
    List<Review> findAllByCafeId(@Param("cafe_id") final int cafe_id);


}
