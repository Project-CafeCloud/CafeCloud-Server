package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.model.ReviewReq;

import java.util.List;


/**
 * findAllByCafeId      : 해당 카페에 대한 모든 리뷰 조회
 * findBestByCafeId     : 해당 카페에 대한 인기 리뷰 조회
 * findByReviewId       : 리뷰 상세 조회
 * save                 : 리뷰 등록
 */

@Mapper
public interface ReviewMapper {

    /**
     * 해당 카페의 모든 리뷰 조회 (최신순)
     *
     * @param   cafeId     카페 고유 id
     */
    @Select("SELECT review_id FROM review " +
            "WHERE contentIdx = #{cafeId}")
    List<Review> findAllByCafeId(@Param("cafeId") final int cafeId);


    /**
     * 해당 카페의 인기 리뷰 조회 (좋아요 개수 순)
     *
     * @param   cafeId     카페 고유 id
     * @param   num        개수
     */
    @Select("SELECT * FROM review " +
            "WHERE contentIdx = #{cafeId} " +
            "LIMIT #{num}")
    List<Review> findBestByCafeId(@Param("cafeId") final int cafeId,
                                  @Param("num") final int num);


    /**
     * 리뷰 상세 조회
     *
     * @param   reviewId      리뷰 고유 id
     */
    @Select("SELECT * FROM review " +
            "WHERE review_id = #{reviewId}")
    Review findByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 리뷰 등록
     *
     * @param   reviewReq     리뷰 데이터
     */
    @Insert("INSERT INTO review (cafe_id, user_id, review_rating, review_title, review_content) " +
            "VALUES (#{reviewReq.cafe_id}, #{reviewReq.user_id}, #{reviewReq.rating}, #{reviewReq.title}, #{reviewReq.content})")
    void save(@Param("reviewReq") final ReviewReq reviewReq);




    // [후순위]

    /**
     * 리뷰 삭제
     *
     * @param reviewId      리뷰 고유 id
     */
    @Delete("DELETE FROM review " +
            "WHERE review_id = #{reviewId}")
    void deleteByReviewId(@Param("reviewId") final int reviewId);

}
