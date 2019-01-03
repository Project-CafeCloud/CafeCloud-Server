package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.model.ReviewReq;

import java.util.List;


/**
 * findByReviewId       : 해당 리뷰의 모든 댓글 조회
 * save                 : 리뷰에 대한 댓글 등록
 */

@Mapper
public interface ReviewCommentMapper {

    /**
     * 해당 리뷰의 모든 댓글 조회
     *
     * @param   reviewId     리뷰 고유 id
     */
    @Select("SELECT * FROM REVIEW_COMMENT " +
            "WHERE review_id = #{reviewId}")
    List<ReviewComment> findByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 리뷰에 대한 댓글 등록
     *
     * @param   reviewCommentReq     리뷰 데이터
     */
    @Insert("INSERT INTO REVIEW_COMMENT (review_id, user_id, review_comment_content, review_comment_date) " +
            "VALUES (#{reviewCommentReq.review_id}, #{reviewCommentReq.user_id}, #{reviewCommentReq.content}, #{reviewCommentReq.created_date})")
    @Options(useGeneratedKeys = true, keyProperty = "reviewCommentReq.review_comment_id")
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
