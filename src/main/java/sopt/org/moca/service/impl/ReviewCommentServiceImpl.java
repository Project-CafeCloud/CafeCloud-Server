package sopt.org.moca.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.dto.ReviewLike;
import sopt.org.moca.mapper.ReviewCommentMapper;
import sopt.org.moca.mapper.ReviewImageMapper;
import sopt.org.moca.mapper.ReviewLikeMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewCommentService;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

/**
 * findByReviewId      : 해당 리뷰에 대한 모든 댓글 조회
 * save                : 리뷰 댓글 등록
 */


@Service
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewCommentMapper reviewCommentMapper;

    public ReviewCommentServiceImpl(final ReviewCommentMapper reviewCommentMapper) {
        this.reviewCommentMapper = reviewCommentMapper;

    }


    /**
     * 해당 리뷰에 대한 모든 댓글 조회
     *
     * @param reviewId    리뷰 고유 id
     * @return DefaultRes
     */
    public DefaultRes<List<Review>> findByReviewId(final int reviewId) {

        List<Review> reviewList = reviewCommentMapper.findByReviewId(reviewId);

        if (reviewList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, reviewList);
    }


    /**
     * 리뷰 댓글 작성
     *
     * @param reviewCommentReq  리뷰 댓글 데이터
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes save(final ReviewCommentReq reviewCommentReq) {
        if (reviewCommentReq.checkProperties()) {
            try {
                reviewCommentMapper.save(reviewCommentReq);
                final int reviewId = reviewCommentReq.getReviewId();

                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_REVIEW);
            } catch (Exception e) {
                // log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_REVIEW);
    }

}
