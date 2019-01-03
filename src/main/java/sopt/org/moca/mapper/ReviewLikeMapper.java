package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.ReviewLike;

import java.util.Date;
import java.util.List;

/**
 * findByUserIdAndReviewId      : 좋아요 조회
 * save                         : 좋아요 등록
 * deleteByUserIdxAndReviewIdx  : 좋아요 취소
 */

@Mapper
public interface ReviewLikeMapper {



    /**
     * 좋아요 개수 세기
     *
     * @param reviewId  리뷰 고유 id
     * @return 좋아요 객체
     */
    @Select("SELECT count(*) FROM REVIEW_LIKE " +
            "WHERE review_id = #{reviewId}")
    int countByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 좋아요 조회
     *
     * @param userId    유저 고유 id
     * @param reviewId  리뷰 고유 id
     * @return 좋아요 객체
     */
    @Select("SELECT * FROM REVIEW_LIKE " +
            "WHERE review_id = #{reviewId} AND user_id = #{userId}")
    ReviewLike findByUserIdAndReviewId(@Param("userId") final String userId, @Param("reviewId") final int reviewId);


    /**
     * 좋아요 등록
     *
     * @param userId    유저 고유 id
     * @param reviewId  리뷰 고유 id
     */
    @Insert("INSERT INTO REVIEW_LIKE (review_id, user_id, review_like_date) " +
            "VALUES(#{reviewId}, #{userId}, #{date})")
    void save(@Param("userId") final String userId, @Param("reviewId") final int reviewId, @Param("date") final Date date);


    /**
     * 좋아요 취소
     *
     * @param userId    유저 고유 id
     * @param reviewId  리뷰 고유 id
     */
    @Delete("DELETE FROM REVIEW_LIKE " +
            "WHERE review_id = #{reviewId} AND user_id = #{userId}")
    void deleteByUserIdAndReviewId(@Param("userId") final String userId, @Param("reviewId") final int reviewId);

}
