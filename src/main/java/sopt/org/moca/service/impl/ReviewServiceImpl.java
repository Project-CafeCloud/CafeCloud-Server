package sopt.org.moca.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewLike;
import sopt.org.moca.mapper.ReviewImageMapper;
import sopt.org.moca.mapper.ReviewLikeMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

/**
 * findAllByCafeId      : 해당 카페에 대한 모든 리뷰 조회
 * findBestByCafeId     : 해당 카페에 대한 인기 리뷰 조회
 * findByReviewId       : 리뷰 상세 조회
 * save                 : 리뷰 등록
 * like                 : 리뷰 좋아요 & 좋아요 취소
 */


@Service
public class ReviewServiceImpl implements ReviewService {

//    private final ReviewMapper reviewMapper;
//    private final ReviewImageMapper reviewImageMapper;
//    private final ReviewLikeMapper reviewLikeMapper;
//    private final FileUploadService fileUploadService;
//
//    public ReviewService(final ReviewMapper reviewMapper,
//                         final ReviewImageMapper reviewImageMapper,
//                         final ReviewLikeMapper reviewLikeMapper,
//                         final FileUploadService fileUploadService) {
//        this.reviewMapper = reviewMapper;
//        this.reviewImageMapper = reviewImageMapper;
//        this.reviewLikeMapper = reviewLikeMapper;
//        this.fileUploadService = fileUploadService;
//    }
//
//    /**
//     * 해당 카페에 대한 모든 리뷰 조회
//     *
//     * @param cafeId    카페 고유 id
//     * @return DefaultRes
//     */
//    public DefaultRes<List> findAllByCafeId(final int cafeId) {
//
//        List<Review> reviewList = reviewMapper.findAllByCafeId(cafeId);
//
//        if (reviewList.isEmpty())
//            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
//        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, reviewList);
//    }
//
//
//    /**
//     * 해당 카페에 대한 인기 리뷰 조회
//     *
//     * @param cafeId    카페 고유 id
//     * @param num       개수
//     * @return DefaultRes
//     */
//    public DefaultRes<List> findBestByCafeId(final int cafeId, final int num) {
//
//        List<Review> reviewList = reviewMapper.findBestByCafeId(cafeId, num);
//
//        if (reviewList.isEmpty())
//            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
//        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, reviewList);
//    }
//
//
//    /**
//     * 리뷰 상세 조회
//     *
//     * @param reviewId  리뷰 고유 id
//     * @return DefaultRes
//     */
//    public DefaultRes<Review> findByReviewId(final int reviewId) {
//        Review review = reviewMapper.findByReviewId(reviewId);
//
//        if (review == null)
//            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
//        review.setImage(reviewImageMapper.findAllByReviewId(review.getReviewId()));
//        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, review);
//    }
//
//
//    /**
//     * 리뷰 작성
//     *
//     * @param reviewReq 컨텐츠 데이터
//     * @return DefaultRes
//     */
//    @Transactional
//    public DefaultRes save(final ReviewReq reviewReq) {
//        if (reviewReq.checkProperties()) {
//            try {
//                reviewMapper.save(reviewReq);
//                final int reviewId = reviewReq.getReviewId();
//
//                for (MultipartFile image : reviewReq.getImage()) {
//                    reviewImageMapper.save(reviewId, fileUploadService.upload(image));
//                }
//
//                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_REVIEW);
//            } catch (Exception e) {
//                // log.info(e.getMessage());
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
//            }
//        }
//        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_REVIEW);
//    }
//
//
//    /**
//     * 리뷰 좋아요 & 좋아요 취소
//     *
//     * @param userId    유저 고유 id
//     * @param reviewId  리뷰 고유 id
//     * @return DefaultRes
//     */
//
//    @Transactional
//    public DefaultRes like(final int userId, final int reviewId) {
//        Review review = findByReviewId(reviewId).getData();
//        if (review == null)
//            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
//
//        ReviewLike reviewLike = reviewLikeMapper.findByUserIdAndReviewId(userId, reviewId);
//
//        try {
//            if (reviewLike == null) {
//                // 좋아요
//                reviewLikeMapper.save(userId, reviewId);
//            } else {
//                // 좋아요 취소
//                reviewLikeMapper.deleteByUserIdAndReviewId(userId, reviewId);
//            }
//
//            review = findByReviewId(reviewId).getData();
//            // review.setAuth(checkAuth(userId, reviewId));
//            // review.setLike(checkLike(userId, reviewId));
//
//            return DefaultRes.res(StatusCode.OK, ResponseMessage.LIKE_REVIEW, review);
//        } catch (Exception e) {
//            // log.error(e.getMessage());
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
//        }
//
//    }
}
