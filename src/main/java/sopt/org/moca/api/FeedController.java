package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.dto.User;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.service.UserService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;


/**
 * getUserFeed       : 유저 피드
 * getSocialFeed     : 소셜 피드
 */


@Slf4j
@RestController
@RequestMapping("/feed")
public class FeedController {

    private static final String HEADER = "Authorization";
    private final ReviewService reviewService;
    private final UserService userService;

    public FeedController(final ReviewService reviewService, final UserService userService) {

        this.reviewService = reviewService;
        this.userService = userService;
    }

    /**
     * 유저 피드
     *
     * @param httpServletRequest Request
     * @param user_id    피드 유저 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/user/{user_id}")
    public ResponseEntity getUserFeed(
            final HttpServletRequest httpServletRequest,
            @PathVariable final String user_id) {

        try {
            final String feedId = user_id;
            final String userId = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            List<String> feedIdList = new ArrayList<>();
            feedIdList.add(feedId);

            //String feedIdList = "['" + feedId + "']";
            DefaultRes<List<Review>> feedList = reviewService.findByUserId(feedIdList);

            if(feedList.getData() != null) {
                for (Review r : feedList.getData()) {
                    r.setAuth(r.getUser_id().compareTo(userId) == 0);
                    r.setLike(reviewService.checkLike(userId, r.getReview_id()));
                }
            }

            return new ResponseEntity<>(feedList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 소셜 피드
     *
     * @param httpServletRequest Request
     * @return ResponseEntity
     */
    @GetMapping("/social")
    public ResponseEntity getSocialFeed(
            final HttpServletRequest httpServletRequest) {

        try {

            final String userId = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();


            DefaultRes<List<User>> followingList = userService.findFollow(userId, true);

            //String feedIdList = "[";


            List<String> feedIdList = new ArrayList<>();

            // user가 팔로우 하고 있는 사람들
            if(followingList.getData() != null) {
                for (User f : followingList.getData()) {

                    feedIdList.add(f.getUser_id());
                    //feedIdList += "'" + f.getUser_id() + "',";

                }
            }

            //feedIdList.substring(0, feedIdList.length()-1);
            //log.info(feedIdList);

            DefaultRes<List<Review>> feedList = reviewService.findByUserId(feedIdList);

            if(feedList.getData() != null) {
                for (Review r : feedList.getData()) {
                    r.setAuth(r.getUser_id().compareTo(userId) == 0);
                    r.setLike(reviewService.checkLike(userId, r.getReview_id()));
                }
            }

            return new ResponseEntity<>(feedList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
