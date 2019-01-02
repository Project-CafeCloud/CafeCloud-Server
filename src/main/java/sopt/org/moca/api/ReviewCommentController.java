package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewCommentService;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;


/**
 * findByReviewId       : 해당 리뷰의 모든 댓글 조회
 * save                 : 리뷰에 대한 댓글 등록
 */



@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewCommentController {

    private static final String HEADER = "Authorization";
    private final ReviewCommentService reviewCommentService;

    public ReviewCommentController(final ReviewCommentService reviewCommentService) {

        this.reviewCommentService = reviewCommentService;
    }


    /**
     * 해당 리뷰의 모든 댓글 조회
     *
     * @param httpServletRequest    Request
     * @param review_id             리뷰 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{review_id}/comment")
    public ResponseEntity getByReviewId(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int review_id) {
        try {

//            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<List<ReviewComment>> reviewCommentList = reviewCommentService.findByReviewId(review_id);
/*
            if(reviewCommentList.getData() != null) {
                for (ReviewComment c : reviewCommentList.getData()) {
                    c.setAuth(c.getUser_id() == user_id);
                }
            }
*/
            return new ResponseEntity<>(reviewCommentList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 리뷰에 대한 댓글 등록
     *
     * @param httpServletRequest    Request
     * @param review_id             리뷰 고유 id
     * @param reviewCommentReq             리뷰 댓글 데이터
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/{review_id}/comment")
    public ResponseEntity save(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int review_id,
            ReviewCommentReq reviewCommentReq) {
        try {
            reviewCommentReq.setUser_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            reviewCommentReq.setReview_id(review_id);

            return new ResponseEntity<>(reviewCommentService.save(reviewCommentReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
