package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.PushService;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.service.impl.AndroidPushNotificationsService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;
import static sopt.org.moca.model.DefaultRes.UNAUTHORIZED_RES;


/**
 * getAllByCafeId      : 해당 카페에 대한 모든 리뷰 조회
 * getBestByCafeId     : 해당 카페에 대한 인기 리뷰 조회
 * getByReviewId       : 리뷰 상세 조회
 * save                : 리뷰 등록
 * like                : 리뷰 좋아요 & 좋아요 취소
 */


@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {

    private static final String HEADER = "Authorization";
    private final ReviewService reviewService;
    private final AndroidPushNotificationsService androidPushNotificationsService;

    public ReviewController(final ReviewService reviewService,final AndroidPushNotificationsService androidPushNotificationsService) {

        this.reviewService = reviewService;
        this.androidPushNotificationsService = androidPushNotificationsService;
    }


    /**
     * 리뷰 상세 조회
     *
     * @param httpServletRequest Request
     * @param review_id     리뷰 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{review_id}/detail")
    public ResponseEntity getByReviewId(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int review_id) {
        try {

            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<Review> review = reviewService.findByReviewId(review_id);

            review.getData().setAuth(review.getData().getUser_id().compareTo(user_id) == 0);
            review.getData().setLike(reviewService.checkLike(user_id, review_id));
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 해당 카페에 대한 모든 리뷰 (이미지) 조회
     *
     * @param cafe_id       카페 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{cafe_id}/all")
    public ResponseEntity getAllByCafeId(@PathVariable final int cafe_id) {

        try {
            DefaultRes<List<ReviewImage>> defaultRes = reviewService.findAllByCafeId(cafe_id);

            return new ResponseEntity<>(defaultRes, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 해당 카페에 대한 인기 리뷰 조회
     *
     * @param httpServletRequest Request
     * @param cafe_id    카페 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{cafe_id}/best")
    public ResponseEntity getAllByCafeId(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int cafe_id) {

        try {
            final int num = 3; // 베스트 몇 개?

            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<List<Review>> reviewList = reviewService.findBestByCafeId(cafe_id, num);
            if(reviewList.getData() != null) {
                for (Review r : reviewList.getData()) {
                    r.setAuth(r.getUser_id().compareTo(user_id) == 0);
                    r.setLike(reviewService.checkLike(user_id, r.getReview_id()));
                }
            }
            return new ResponseEntity<>(reviewList,HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 리뷰 등록
     *
     * @param httpServletRequest Request
     * @param reviewReq 컨텐츠 데이터
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/")
    public ResponseEntity save(
            final HttpServletRequest httpServletRequest,
            @RequestBody ReviewReq reviewReq) {
        try {
            reviewReq.setUser_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());

            return new ResponseEntity<>(reviewService.save(reviewReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 리뷰 좋아요 & 좋아요 취소
     *
     * @param httpServletRequest Request
     * @param review_id     리뷰 고유 id
     * @return
     */
    @Auth
    @PostMapping("/{review_id}/like")
    public ResponseEntity like(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int review_id) {
        try {
            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            DefaultRes likeDefaultRes = reviewService.like(user_id, review_id);
            return new ResponseEntity<>(likeDefaultRes, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /**
     * 리뷰 수정
     *
     * @param httpServletRequest    Request
     * @param review_id             리뷰 고유 id
     * @param reviewReq             리뷰 댓글 데이터
     * @return ResponseEntity
     */
    @Auth
    @PutMapping("/{review_id}")
    public ResponseEntity update(final HttpServletRequest httpServletRequest,
                                 @PathVariable final int review_id,
                                 @RequestBody ReviewReq reviewReq) {
        try {
            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            // 수정 권한 확인
            if(!reviewService.checkAuth(user_id, review_id)){
                return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
            }

            reviewReq.setUser_id(user_id);
            reviewReq.setReview_id(review_id);

            return new ResponseEntity<>(reviewService.update(reviewReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 리뷰 삭제
     *
     * @param httpServletRequest    Request
     * @param review_id             리뷰 고유 id
     * @return ResponseEntity
     */
    @DeleteMapping("/{review_id}")
    public ResponseEntity delete(final HttpServletRequest httpServletRequest,
                                 @PathVariable final int review_id) {
        try {

            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            if (reviewService.checkAuth(user_id, review_id)){
                return new ResponseEntity<>(reviewService.deleteByReviewId(review_id), HttpStatus.OK);
            }

            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
