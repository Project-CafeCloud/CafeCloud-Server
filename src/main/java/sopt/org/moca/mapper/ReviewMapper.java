package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.UserInfo;
import sopt.org.moca.model.ReviewCommentReq;
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
    @Select("SELECT review_id FROM REVIEW " +
            "WHERE cafe_id = #{cafeId}")
    List<Review> findAllByCafeId(@Param("cafeId") final int cafeId);


    /**
     * 해당 카페의 인기 리뷰 조회 (좋아요 개수 순)
     *
     * @param   cafeId     카페 고유 id
     * @param   num        개수
     */
    @Select("SELECT *, COUNT(review_like_date) AS like_count " +
            "FROM REVIEW LEFT JOIN REVIEW_LIKE " +
            "ON REVIEW.review_id = REVIEW_LIKE.review_id " +
            "WHERE REVIEW.cafe_id = #{cafeId} " +
            "GROUP BY REVIEW_LIKE.review_id " +
            "ORDER BY like_count DESC, REVIEW.review_date DESC " +
            "LIMIT #{num}")
    List<Review> findBestByCafeId(@Param("cafeId") final int cafeId,
                                  @Param("num") final int num);


    /**
     * 유저가 쓴 모든 리뷰 최신순으로 조회
     * (유저피드)
     *
     * @param   userId     유저 고유 id
     */
    @Select("SELECT * FROM REVIEW " +
            "WHERE user_id = #{userId} " +
            "ORDER BY review_date DESC")
    List<Review> findUserFeedByUserId(@Param("userId") final String userId);


    /**
     * 유저가 팔로우한 유저들이 쓴 모든 리뷰 최신순으로 조회
     * (소셜피드)
     *
     * @param   userId     유저 고유 id
     */
    @Select("SELECT * FROM REVIEW " +
            "WHERE user_id IN " +
            "(SELECT following_id FROM FOLLOW " +
            "WHERE follower_id = #{userId}) " +
            "ORDER BY review_date DESC")
    List<Review> findSocialFeedByUserId(@Param("userId") final String userId);


    /**
     * 리뷰 상세 조회
     *
     * @param   reviewId      리뷰 고유 id
     */
    @Select("SELECT * FROM REVIEW " +
            "WHERE review_id = #{reviewId}")
    Review findByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 리뷰 등록
     *
     * @param   reviewReq     리뷰 데이터
     */
    @Insert("INSERT INTO REVIEW (cafe_id, user_id, review_rating, review_title, review_content, review_date) " +
            "VALUES (#{reviewReq.cafe_id}, #{reviewReq.user_id}, #{reviewReq.rating}, #{reviewReq.title}, #{reviewReq.content}, #{reviewReq.created_date})")
    @Options(useGeneratedKeys = true, keyProperty = "reviewReq.review_id")
    void save(@Param("reviewReq") final ReviewReq reviewReq);



    /**
     * 유저 별 리뷰 개수 세기
     *
     * @param   userId     유저 고유 id
     */
    @Select("SELECT COUNT(*) AS review_count, user_id " +
            "FROM REVIEW " +
            "WHERE user_id = #{userId} " +
            "GROUP BY user_id")
    UserInfo countReviewByUserId(@Param("userId") final String userId);


    /**
     * 댓글 수정
     *
     * @param reviewReq      리뷰 댓글 고유 데이터
     */
    @Delete("UPDATE REVIEW SET " +
            "review_title = #{reviewReq.title}, review_content = #{reviewReq.content}, review_rating = #{reviewReq.rating}, review_date = #{reviewReq.created_date} " +
            "WHERE review_id = #{reviewReq.review_id}")
    void updateByReviewId(@Param("reviewReq") final ReviewReq reviewReq);



    /**
     * 리뷰 삭제
     *
     * @param reviewId      리뷰 고유 id
     */
    @Delete("DELETE FROM REVIEW " +
            "WHERE review_id = #{reviewId}")
    void deleteByReviewId(@Param("reviewId") final int reviewId);

}
