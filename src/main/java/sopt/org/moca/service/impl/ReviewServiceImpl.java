package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.*;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.Time;

import java.util.Date;
import java.util.List;



@Slf4j
@Service
public class ReviewServiceImpl implements ReviewService {

    private final UserMapper userMapper;
    private final FollowMapper followMapper;
    private final CafeMapper cafeMapper;
    private final ReviewMapper reviewMapper;
    private final ReviewImageMapper reviewImageMapper;
    private final ReviewLikeMapper reviewLikeMapper;
    private final FileUploadService fileUploadService;
    private final AndroidPushNotificationsService androidPushNotificationsService;

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;


    /**
     * 생성자 의존성 주입
     *
     * @param userMapper
     * @param followMapper
     * @param cafeMapper
     * @param reviewMapper
     * @param reviewImageMapper
     * @param reviewLikeMapper
     * @param fileUploadService
     */

    public ReviewServiceImpl(final UserMapper userMapper,
                             final FollowMapper followMapper,
                             final CafeMapper cafeMapper,
                             final ReviewMapper reviewMapper,
                             final ReviewImageMapper reviewImageMapper,
                             final ReviewLikeMapper reviewLikeMapper,
                             final FileUploadService fileUploadService,
                             final AndroidPushNotificationsService androidPushNotificationsService) {

        this.userMapper = userMapper;
        this.followMapper = followMapper;
        this.cafeMapper = cafeMapper;
        this.reviewMapper = reviewMapper;
        this.reviewImageMapper = reviewImageMapper;
        this.reviewLikeMapper = reviewLikeMapper;
        this.fileUploadService = fileUploadService;
        this.androidPushNotificationsService = androidPushNotificationsService;
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

        for(ReviewImage img : reviewImageList){
            img.setReview_img_url(defaultUrl + img.getReview_img_url());
        }

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

            // 유저
            User user = userMapper.findById(r.getUser_id());

            r.setUser_name(user.getUser_name());
            r.setUser_img_url(defaultUrl + user.getUser_img_url());

            // 이미지
            List<ReviewImage> reviewImageList = reviewImageMapper.findAllByReviewId(r.getReview_id());

            for(ReviewImage img : reviewImageList){
                img.setReview_img_url(defaultUrl + img.getReview_img_url());
            }
            r.setImage(reviewImageList);

            // 카페
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
     * @param userId        유저 고유 id
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<Review>> findByUserId(final String userId, final boolean is_user_feed) {

        List<Review> reviewList;

        if(is_user_feed){
            reviewList = reviewMapper.findUserFeedByUserId(userId);
        } else {
            if(followMapper.findFollowing(userId).size() == 0){
                return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_FOLLOW);
            }
            reviewList = reviewMapper.findSocialFeedByUserId(userId);
        }

        for (Review r : reviewList){

            // 유저
            User user = userMapper.findById(r.getUser_id());

            r.setUser_name(user.getUser_name());
            r.setUser_img_url(defaultUrl + user.getUser_img_url());

            // 이미지
            List<ReviewImage> reviewImageList = reviewImageMapper.findAllByReviewId(r.getReview_id());

            for(ReviewImage img : reviewImageList){
                img.setReview_img_url(defaultUrl + img.getReview_img_url());
            }

            r.setImage(reviewImageList);

            // 카페
            CafeInfo cafeinfo = cafeMapper.findByCafeId(r.getCafe_id());

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

        // 이거 에러 어떻게 잡아 ;
        if(reviewMapper.findByReviewId(reviewId) == null){
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);
        }

        Review review = reviewMapper.findByReviewId(reviewId);

        // 유저
        User user = userMapper.findById(review.getUser_id());

        review.setUser_name(user.getUser_name());
        review.setUser_img_url(defaultUrl + user.getUser_img_url());

        // 이미지
        List<ReviewImage> reviewImageList = reviewImageMapper.findAllByReviewId(review.getReview_id());

        for(ReviewImage img : reviewImageList){
            img.setReview_img_url(defaultUrl + img.getReview_img_url());
        }

        review.setImage(reviewImageList);

        // 카페
        CafeInfo cafeinfo = cafeMapper.findByCafeId(review.getCafe_id());

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
                    reviewImageMapper.save(reviewId, fileUploadService.upload(image, "review"));
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
        ReviewAlarm reviewAlarm = new ReviewAlarm();
        try {
            if (reviewLike == null) {
                // 좋아요

                reviewAlarm.setAlarm_user(review.getUser_id());
                reviewAlarm.setAlarm_contents(userId+"님이 좋아요를 눌렀습니다."); //이름으로 바꾸기
                String alarm_user = review.getUser_name();
                String alarm_contnets = userId+"님이 좋아요를 눌렀습니다.";
//                new Thread(() -> {
//                        try {
//                            androidPushNotificationsService.makeJson(alarm_user,alarm_contnets);
//
//                        } catch (Exception e) {
//
//                        }
//                }).start();


                reviewLikeMapper.save(userId, reviewId, new Date());
            } else {
                // 좋아요 취소
                reviewLikeMapper.deleteByUserIdAndReviewId(userId, reviewId);
            }

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
     * 리뷰 수정
     *
     * @param reviewReq 댓글 내용
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes<Review> update(final ReviewReq reviewReq) {

        if (reviewMapper.findByReviewId(reviewReq.getReview_id()) == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);

        try {

            reviewMapper.updateByReviewId(reviewReq);
            DefaultRes<Review> review = findByReviewId(reviewReq.getReview_id());

            review.getData().setAuth(checkAuth(reviewReq.getUser_id(), reviewReq.getReview_id()));
            review.getData().setLike(checkLike(reviewReq.getUser_id(), reviewReq.getReview_id()));

            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_REVIEW, review.getData());
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 리뷰 삭제
     *
     * @param reviewId      리뷰 고유 id
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes deleteByReviewId(final int reviewId) {
        final Review review = reviewMapper.findByReviewId(reviewId);

        if (review == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_REVIEWS);

        try {
            reviewMapper.deleteByReviewId(reviewId);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_REIVEW);
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
        return userId.equals(findByReviewId(reviewId).getData().getUser_id());
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