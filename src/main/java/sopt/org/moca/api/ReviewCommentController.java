package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.service.ReviewCommentService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;
import static sopt.org.moca.model.DefaultRes.UNAUTHORIZED_RES;


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
    public ResponseEntity getByReviewId(final HttpServletRequest httpServletRequest, @PathVariable final int review_id) {
        try {
            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<List<ReviewComment>> reviewCommentList = reviewCommentService.findByReviewId(user_id, review_id);

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
     * @param reviewCommentReq             리뷰 댓글 데이터
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/comment")
    public ResponseEntity save(
            final HttpServletRequest httpServletRequest,
            ReviewCommentReq reviewCommentReq) {
        try {
            reviewCommentReq.setUser_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());

            return new ResponseEntity<>(reviewCommentService.save(reviewCommentReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 댓글 수정
     *
     * @param httpServletRequest    Request
     * @param reviewCommentReq             리뷰 댓글 데이터
     * @return ResponseEntity
     */
    @Auth
    @PutMapping("/comment/{comment_id}")
    public ResponseEntity update(final HttpServletRequest httpServletRequest,
                                @PathVariable final int comment_id,
                                ReviewCommentReq reviewCommentReq) {
        try {
            reviewCommentReq.setUser_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            reviewCommentReq.setReview_comment_id(comment_id);

            return new ResponseEntity<>(reviewCommentService.update(reviewCommentReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 댓글 삭제
     *
     * @param httpServletRequest    Request
     * @param comment_id             리뷰 고유 id
     * @return ResponseEntity
     */
    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity delete(final HttpServletRequest httpServletRequest,
                                 @PathVariable final int comment_id) {
        try {

            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            if (reviewCommentService.checkAuth(user_id, comment_id)){
                return new ResponseEntity<>(reviewCommentService.deleteByReviewCommentId(comment_id), HttpStatus.OK);
            }

            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
