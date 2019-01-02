package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.mapper.ReviewCommentMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.service.ReviewCommentService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

/**
 * findByReviewId      : 해당 리뷰에 대한 모든 댓글 조회
 * save                : 리뷰 댓글 등록
 */

@Slf4j
@Service
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewCommentMapper reviewCommentMapper;


    /**
     * 생성자 의존성 주입
     *
     * @param reviewCommentMapper
     */
    public ReviewCommentServiceImpl(final ReviewCommentMapper reviewCommentMapper) {
        this.reviewCommentMapper = reviewCommentMapper;

    }


    /**
     * 해당 리뷰에 대한 모든 댓글 조회
     *
     * @param reviewId    리뷰 고유 id
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<ReviewComment>> findByReviewId(final int reviewId) {

        List<ReviewComment> reviewCommentList = reviewCommentMapper.findByReviewId(reviewId);

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

}
