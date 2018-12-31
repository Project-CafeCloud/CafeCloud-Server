package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.ReviewService;
import javax.servlet.http.HttpServletRequest;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.auth.JwtUtils;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;


/**
 * findAllByCafeId      : 해당 카페에 대한 모든 리뷰 조회
 * findBestByCafeId     : 해당 카페에 대한 인기 리뷰 조회
 * findByReviewId       : 리뷰 상세 조회
 * save                 : 리뷰 등록
 * like                 : 리뷰 좋아요 & 좋아요 취소
 */


@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {


    // private static final DefaultRes UNAUTHORIZED_RES = new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED);

    private static final String HEADER = "Authorization";

    private final ReviewService reviewService;

    private final JwtUtils jwtUtils;

    public ReviewController(final ReviewService reviewService, final JwtUtils jwtUtils) {

        this.reviewService = reviewService;
        this.jwtUtils = jwtUtils;
    }

    /**
     * 해당 카페에 대한 모든 리뷰 조회
     *
     * @param httpServletRequest Request
     * @param header     jwt token
     * @param cafeId    카페 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{cafeId}/all")
    public ResponseEntity getAllByCafeId(
            final HttpServletRequest httpServletRequest,
            @RequestHeader(value = "Authorization", required = false) final String header,
            @PathVariable final int cafeId) {

        try {

            final String userId = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            DefaultRes<List<Review>> defaultRes = reviewService.findAllByCafeId(cafeId);
            if(defaultRes.getData() != null) {
                for (Review r : defaultRes.getData()) {
                    r.setAuth(r.getUserId() == userId);
                    // r.setLike(reviewService.checkLike(userId, r.getUserId()));
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
     * @param header     jwt token
     * @param cafeId    카페 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{cafeId}/best")
    public ResponseEntity getAllByCafeId(
            @RequestHeader(value = "Authorization", required = false) final String header,
            @PathVariable final int cafeId) {

        try {
            final String userId = jwtUtils.decode(header).getUser_id();
            DefaultRes<List<Review>> defaultRes = reviewService.findAllByCafeId(cafeId);
            if(defaultRes.getData() != null) {
                for (Review r : defaultRes.getData()) {
                    r.setAuth(r.getUserId() == userId);
                    // r.setLike(reviewService.checkLike(userId, r.getUserId()));
                }
            }
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
