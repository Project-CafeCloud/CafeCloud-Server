package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.mapper.ReviewImageMapper;
import sopt.org.moca.mapper.ReviewLikeMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * findAllByCafeId      : 해당 카페에 대한 모든 리뷰 조회
 * findBestByCafeId     : 해당 카페에 대한 인기 리뷰 조회
 * findByReviewId       : 리뷰 상세 조회
 * save                 : 리뷰 등록
 * like                 : 리뷰 좋아요 & 좋아요 취소
 */


@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final CafeMapper cafeMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final ReviewLikeMapper reviewLikeMapper;
    private final FileUploadService fileUploadService;



    /**
     * 생성자 의존성 주입
     *
     * @param reviewMapper
     * @param reviewImageMapper
     * @param reviewLikeMapper
     * @param fileUploadService
     */

    public ReviewServiceImpl(final CafeMapper cafeMapper,
                             final ReviewMapper reviewMapper,
                             final ReviewImageMapper reviewImageMapper,
                             final ReviewLikeMapper reviewLikeMapper,
                             final FileUploadService fileUploadService) {
        this.cafeMapper = cafeMapper;
        this.reviewMapper = reviewMapper;
        this.reviewImageMapper = reviewImageMapper;
        this.reviewLikeMapper = reviewLikeMapper;
        this.fileUploadService = fileUploadService;
    }


    /**
     * 해당 카페에 대한 모든 리뷰 이미지 조회
     *
     * @param cafeId    카페 고유 id
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<ReviewImage>> findAllByCafeId(final int cafeId) {

        CafeInfo cafeinfo = cafeMapper.findByCafeId(cafeId);

        if (cafeinfo == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CAFE);

        List<ReviewImage> reviewImageList = reviewImageMapper.findOneByCafeId(cafeId);

        if (reviewImageList == null)
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_REVIEWS);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, reviewImageList);
    }


    /**
     * 해당 카페에 대한 인기 리뷰 조회
     *
     * @param cafeId    카페 고유 id
     * @param num       개수
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<Review>> findBestByCafeId(final int cafeId, final int num) {

        CafeInfo cafeinfo = cafeMapper.findByCafeId(cafeId);

        if (cafeinfo == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CAFE);

        List<Review> reviewList = reviewMapper.findBestByCafeId(cafeId, num);
        for (Review r : reviewList){

            r.setImage(reviewImageMapper.findAllByReviewId(r.getReview_id()));
            r.setCafe_name(cafeinfo.getCafe_name());
            r.setCafe_address("서울 " + cafeinfo.getAddress_district_name());
            r.setLike_count(reviewLikeMapper.countByReviewId(r.getReview_id()));
            r.setTime(Time.toText(r.getReview_date()));
        }

        if (reviewList.isEmpty())
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_REVIEWS);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, reviewList);
    }



    /**
     * 유저가 쓴 모든 리뷰 최신순으로 조회
     *
     * @param userIdList        유저 고유 id
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<Review>> findByUserId(final String userIdList) {


        // 유저가 쓴 리뷰 모두 가져오기 findByUserId

        // 리뷰 이미지 붙이기
        // 카페 정보 붙이기
        // like_count, time 붙이기


        List<Review> reviewList = reviewMapper.findByUserId(userIdList);
        for (Review r : reviewList){

            CafeInfo cafeinfo = cafeMapper.findByCafeId(r.getCafe_id());

            r.setImage(reviewImageMapper.findAllByReviewId(r.getReview_id()));
            r.setCafe_name(cafeinfo.getCafe_name());
            r.setCafe_address("서울 " + cafeinfo.getAddress_district_name());
            r.setLike_count(reviewLikeMapper.countByReviewId(r.getReview_id()));
            r.setTime(Time.toText(r.getReview_date()));
        }

        if (reviewList.isEmpty())
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_FEEDS);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_FEEDS, reviewList);

    }


    /**
     * 리뷰 상세 조회
     *
     * @param reviewId  리뷰 고유 id
     * @return DefaultRes
     */
    @Override
    public DefaultRes<Review> findByReviewId(final int reviewId) {

        // 이거 에러 어떻게 잡아 ; -> controller로
        if(reviewMapper.findByReviewId(reviewId) == null){
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
        }

        Review review = reviewMapper.findByReviewId(reviewId);
        CafeInfo cafeinfo = cafeMapper.findByCafeId(review.getCafe_id());

        review.setImage(reviewImageMapper.findAllByReviewId(review.getReview_id()));
        review.setCafe_name(cafeinfo.getCafe_name());
        review.setCafe_address("서울 " + cafeinfo.getAddress_district_name());
        review.setLike_count(reviewLikeMapper.countByReviewId(review.getReview_id()));
        review.setTime(Time.toText(review.getReview_date()));

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_REVIEWS, review);
    }


    /**
     * 리뷰 작성
     *
     * @param reviewReq 컨텐츠 데이터
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes save(final ReviewReq reviewReq) {
        if (reviewReq.checkProperties()) {
            try {

                if(cafeMapper.findByCafeId(reviewReq.getCafe_id()) == null){
                    return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CAFE);
                }

                reviewMapper.save(reviewReq);
                final int reviewId = reviewReq.getReview_id();

                for (MultipartFile image : reviewReq.getImage()) {
                    reviewImageMapper.save(reviewId, fileUploadService.upload(image));
                }

                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_REVIEW);
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_REVIEW);
    }


    /**
     * 리뷰 좋아요 & 좋아요 취소
     *
     * @param userId    유저 고유 id
     * @param reviewId  리뷰 고유 id
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes like(final String userId, final int reviewId) {
        Review review = findByReviewId(reviewId).getData();
        if (review == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);

        ReviewLike reviewLike = reviewLikeMapper.findByUserIdAndReviewId(userId, reviewId);

        try {
            if (reviewLike == null) {
                // 좋아요
                reviewLikeMapper.save(userId, reviewId, new Date());
            } else {
                // 좋아요 취소
                reviewLikeMapper.deleteByUserIdAndReviewId(userId, reviewId);
            }

            // review = findByReviewId(reviewId).getData();
            review.setAuth(checkAuth(userId, reviewId));
            review.setLike(checkLike(userId, reviewId));

            return DefaultRes.res(StatusCode.OK, ResponseMessage.LIKE_REVIEW);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }


    /**
     * 리뷰 권한 확인
     *
     * @param userId    유저 고유 id
     * @param reviewId  리뷰 고유 id
     * @return boolean
     */
    @Override
    public boolean checkAuth(final String userId, final int reviewId) {
        return userId == findByReviewId(reviewId).getData().getUser_id();
    }


    /**
     * 좋아요 여부 확인
     *
     * @param userId    유저 고유 id
     * @param reviewId  리뷰 고유 id
     * @return boolean
     */
    @Override
    public boolean checkLike(final String userId, final int reviewId) {
        return reviewLikeMapper.findByUserIdAndReviewId(userId, reviewId) != null;
    }
}