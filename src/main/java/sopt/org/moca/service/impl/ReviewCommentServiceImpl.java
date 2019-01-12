package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.dto.User;
import sopt.org.moca.mapper.ReviewCommentMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.service.ReviewCommentService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.Time;

import java.util.List;

/**
 * findByReviewId      : 해당 리뷰에 대한 모든 댓글 조회
 * save                : 리뷰 댓글 등록
 */

@Slf4j
@Service
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewCommentMapper reviewCommentMapper;


    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    /**
     * 생성자 의존성 주입
     *
     * @param reviewCommentMapper
     */
    public ReviewCommentServiceImpl(final UserMapper userMapper,
                                    final ReviewMapper reviewMapper,
                                    final ReviewCommentMapper reviewCommentMapper) {
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
        this.reviewCommentMapper = reviewCommentMapper;

    }



    /**
     * 해당 댓글 조회
     *
     * @param reviewCommentId    리뷰 고유 id
     * @return DefaultRes
     */
    @Override
    public ReviewComment findByCommentId(final int reviewCommentId) {

        final ReviewComment reviewComment = reviewCommentMapper.findByCommentId(reviewCommentId);
        if (reviewComment == null) return new ReviewComment();

        reviewComment.setUser_img_url(defaultUrl + reviewComment.getUser_img_url());

        return reviewComment;
    }



    /**
     * 해당 리뷰에 대한 모든 댓글 조회
     *
     * @param reviewId    리뷰 고유 id
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<ReviewComment>> findByReviewId(final String user_id, final int reviewId) {

        if(reviewMapper.findByReviewId(reviewId) == null){
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
        }

        List<ReviewComment> reviewCommentList = reviewCommentMapper.findByReviewId(reviewId);

        for(ReviewComment c : reviewCommentList){
            User user = userMapper.findById(c.getUser_id());

            c.setTime(Time.toText(c.getReview_comment_date()));
            c.setUser_name(user.getUser_name());
            c.setUser_img_url(defaultUrl + user.getUser_img_url());
            c.setAuth(checkAuth(user_id, c.getReview_comment_id()));
        }

        if (reviewCommentList.isEmpty())
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_COMMENTS);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_COMMENTS, reviewCommentList);
    }


    /**
     * 리뷰 댓글 등록
     *
     * @param reviewCommentReq  리뷰 댓글 데이터
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes save(final ReviewCommentReq reviewCommentReq) {
        if (reviewCommentReq.checkProperties()) {
            try {

                if(reviewMapper.findByReviewId(reviewCommentReq.getReview_id()) == null){
                    return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
                }

                reviewCommentMapper.save(reviewCommentReq);

                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_COMMENT);
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_COMMENT);
    }




    /**
     * 댓글 수정
     *
     * @param reviewCommentReq 댓글 내용
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes<ReviewComment> update(final ReviewCommentReq reviewCommentReq) {
        ReviewComment comment = reviewCommentMapper.findByCommentId(reviewCommentReq.getReview_comment_id());
        if (comment == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENTS);

        try {
            reviewCommentMapper.updateByCommentId(reviewCommentReq);
            comment = reviewCommentMapper.findByCommentId(reviewCommentReq.getReview_comment_id());
            comment.setAuth(true);

            User user = userMapper.findById(comment.getUser_id());

            comment.setTime(Time.toText(comment.getReview_comment_date()));
            comment.setUser_name(user.getUser_name());
            comment.setUser_img_url(defaultUrl + user.getUser_img_url());

            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_COMMENT, comment);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 댓글 삭제
     *
     * @param reviewCommentId 댓글 고유 번호
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes deleteByReviewCommentId(final int reviewCommentId) {
        final ReviewComment comment = reviewCommentMapper.findByCommentId(reviewCommentId);

        if (comment == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENTS);

        try {
            reviewCommentMapper.deleteByCommentId(reviewCommentId);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_COMMENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 댓글 권한 확인
     *
     * @param user_id               사용자 고유 번호
     * @param review_comment_id     댓글 고유 번호
     * @return boolean
     */
    @Override
    public boolean checkAuth(final String user_id, final int review_comment_id) {

        return user_id.equals(findByCommentId(review_comment_id).getUser_id());
    }

}
