package sopt.org.moca.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewLike;
import sopt.org.moca.mapper.ReviewImageMapper;
import sopt.org.moca.mapper.ReviewLikeMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.service.S3FileUploadService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;
import java.util.Optional;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final ReviewLikeMapper reviewLikeMapper;
    private final S3FileUploadService s3FileUploadService;

    public ReviewService(final ReviewMapper reviewMapper,
                         final ReviewImageMapper reviewImageMapper,
                         final ReviewLikeMapper reviewLikeMapper,
                         final S3FileUploadService s3FileUploadService) {
        this.reviewMapper = reviewMapper;
        this.reviewImageMapper = reviewImageMapper;
        this.reviewLikeMapper = reviewLikeMapper;
        this.s3FileUploadService = s3FileUploadService;
    }

    // DefaultRes<List<Review>> findAllReview();

    /**
     * 리뷰 작성
     *
     * @param reviewReq 컨텐츠 데이터
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes save(final ReviewReq reviewReq) {
        if (reviewReq.checkProperties()) {
            try {
                reviewMapper.insertReivew(reviewReq);
                final int reviewId = reviewReq.getReviewId();

                for (MultipartFile photo : reviewReq.getPhoto()) {
                    reviewImageMapper.insertReviewImage(reviewId, s3FileUploadService.upload(photo));
                }

                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATE_CONTENT);
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_CONTENT);
    }


    /**
     * 리뷰 좋아요 & 좋아요 취소
     *
     * @param userId    서비스 요청한 사람의 고유 번호
     * @param reviewId  컨텐츠 고유 번호
     * @return DefaultRes
     */
    @Transactional
    public DefaultRes likes(final int userId, final int reviewId) {
        Review review = findByReviewId(reviewId).getData();
        if (review == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENTS);

        ReviewLike contentLike = reviewLikeMapper.findByUserIdxAndContentIdx(userId, reviewId);

        try {
            if (contentLike == null) {
                //좋아요 카운트 반영
                contentMapper.like(contentIdx, content.getLikeCount() + 1);
                //좋아요
                contentLikeMapper.save(userIdx, contentIdx);
            } else {
                //싫어요 카운트 반영
                contentMapper.like(contentIdx, content.getLikeCount() - 1);
                //싫어요
                contentLikeMapper.deleteByUserIdxAndContentIdx(userIdx, contentIdx);
            }

            content = findByContentIdx(contentIdx).getData();
            content.setAuth(checkAuth(userIdx, contentIdx));
            content.setLike(checkLike(userIdx, contentIdx));

            return DefaultRes.res(StatusCode.OK, ResponseMessage.LIKE_CONTENT, content);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
