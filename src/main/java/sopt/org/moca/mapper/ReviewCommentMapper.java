package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Review;
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
public interface ReviewCommentMapper {

    /**
     * 해당 리뷰의 모든 댓글 조회
     *
     * @param   reviewId     리뷰 고유 id
     */
    @Select("SELECT * FROM review_comment " +
            "WHERE contentIdx = #{cafeId}")
    List<Review> findByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 리뷰 등록
     *
     * @param   reviewCommentReq     리뷰 데이터
     */
    @Insert("INSERT INTO review (cafe_id, user_id, review_rating, review_title, review_content) " +
            "VALUES (#{reviewReq.cafe_id}, #{reviewReq.user_id}, #{reviewReq.rating}, #{reviewReq.title}, #{reviewReq.content})")
    void save(@Param("reviewCommentReq") final ReviewCommentReq reviewCommentReq);




    // [후순위]

    /**
     * 댓글 삭제
     *
     * @param reviewCommentId      리뷰 댓글 고유 id
     */
    @Delete("DELETE FROM review " +
            "WHERE review_id = #{reviewId}")
    void deleteByReviewCommentId(@Param("reviewCommentId") final int reviewCommentId);

}
