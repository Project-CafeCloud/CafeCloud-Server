package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;


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

    private final ReviewService reviewService;

    private final JwtUtils jwtUtils;

    public ReviewController(final ReviewService reviewService, final JwtUtils jwtUtils) {

        this.reviewService = reviewService;
        this.jwtUtils = jwtUtils;
    }


    /**
     * 리뷰 상세 조회
     *
     * @param header        jwt token
     * @param review_id     리뷰 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{review_id}")
    public ResponseEntity getByReviewId(
            @RequestHeader(value = "Authorization", required = false) final String header,
            @PathVariable final int review_id) {
        try {
            final String userId = jwtUtils.decode(header).getUser_id();

            DefaultRes<Review> defaultRes = reviewService.findByReviewId(review_id);

            defaultRes.getData().setAuth(userId == defaultRes.getData().getUserId());
            defaultRes.getData().setLike(reviewService.checkLike(userId, review_id));

            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 해당 카페에 대한 모든 리뷰 조회
     *
     * @param header        jwt token
     * @param cafe_id       카페 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{cafe_id}/all")
    public ResponseEntity getAllByCafeId(
            @RequestHeader(value = "Authorization", required = false) final String header,
            @PathVariable final int cafe_id) {

        try {

            final String userId = JwtUtils.decode(header).getUser_id();
            DefaultRes<List<Review>> defaultRes = reviewService.findAllByCafeId(cafe_id);
            if(defaultRes.getData() != null) {
                for (Review r : defaultRes.getData()) {
                    r.setAuth(r.getUserId() == userId);
                    r.setLike(reviewService.checkLike(userId, r.getReviewId()));
                }
            }
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 해당 카페에 대한 인기 리뷰 조회
     *
     * @param header    jwt token
     * @param cafe_id    카페 고유 id
     * @param num       개수
     * @return ResponseEntity
     */
    @GetMapping("/{cafe_id}/best")
    public ResponseEntity getAllByCafeId(
            @RequestHeader(value = "Authorization", required = false) final String header,
            @PathVariable final int cafe_id, @PathVariable final int num) {

        try {
            final String userId = jwtUtils.decode(header).getUser_id();
            DefaultRes<List<Review>> defaultRes = reviewService.findBestByCafeId(cafe_id, num);
            if(defaultRes.getData() != null) {
                for (Review r : defaultRes.getData()) {
                    r.setAuth(r.getUserId() == userId);
                    r.setLike(reviewService.checkLike(userId, r.getReviewId()));
                }
            }
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 리뷰 등록
     *
     * @param reviewReq 컨텐츠 데이터
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/")
    public ResponseEntity save(
            @RequestHeader(value = "Authorization") final String header,
            ReviewReq reviewReq) {
        try {
            reviewReq.setUserId(jwtUtils.decode(header).getUser_id());
            return new ResponseEntity<>(reviewService.save(reviewReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 리뷰 좋아요 & 좋아요 취소
     *
     * @param header        jwt token
     * @param review_id     리뷰 고유 id
     * @return
     */
    @Auth
    @PostMapping("/{review_id}/like")
    public ResponseEntity like(
            @RequestHeader(value = "Authorization") final String header,
            @PathVariable final int review_id) {
        try {
            final String userId = jwtUtils.decode(header).getUser_id();
            return new ResponseEntity<>(reviewService.like(userId, review_id), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
