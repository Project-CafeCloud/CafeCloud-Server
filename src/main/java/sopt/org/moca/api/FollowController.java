package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.Follow;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.User;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.FollowReq;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.service.FollowService;
import sopt.org.moca.service.UserService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;
import static sopt.org.moca.model.DefaultRes.UNAUTHORIZED_RES;

@Slf4j
@RestController
@RequestMapping("/user")
public class FollowController {

    private static final String HEADER = "Authorization";
    private final UserService userService;
    private final FollowService followService;


    public FollowController(final UserService userService, final FollowService followService) {
        this.userService = userService;
        this.followService = followService;

    }


    /**
     * 팔로워 목록 조회
     *
     * @param httpServletRequest Request
     * @param user_id            유저 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{user_id}/follower")
    public ResponseEntity getFollowerList(
            final HttpServletRequest httpServletRequest,
            @PathVariable final String user_id) {

        try {
            final String followingId = user_id;
            final String userId = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<List<User>> followerList = followService.findFollowList(followingId, true);

            if(followerList.getData() != null) {
                for (User u : followerList.getData()) {
                    u.setFollow(followService.checkFollow(userId, u.getUser_id()));
                }
            }

            return new ResponseEntity<>(followerList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 팔로잉 목록 조회
     *
     * @param httpServletRequest Request
     * @param user_id            유저 고유 id
     * @return ResponseEntity
     */
    @GetMapping("/{user_id}/following")
    public ResponseEntity getFollowingList(
            final HttpServletRequest httpServletRequest,
            @PathVariable final String user_id) {

        try {
            final String followerId = user_id;
            final String userId = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<List<User>> followingList = followService.findFollowList(followerId, false);

            if(followingList.getData() != null) {
                for (User u : followingList.getData()) {
                    u.setFollow(followService.checkFollow(userId, u.getUser_id()));
                }
            }

            return new ResponseEntity<>(followingList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 팔로우하기
     *
     * @param httpServletRequest Request
     * @param user_id            팔로우할 유저 고유 id
     * @return ResponseEntity
     */
    @PostMapping("/{user_id}/follow")
    public ResponseEntity follow(
            final HttpServletRequest httpServletRequest,
            @PathVariable final String user_id) {

        try {
            FollowReq followReq = new FollowReq();

            followReq.setFollower_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            followReq.setFollowing_id(user_id);

            return new ResponseEntity<>(followService.follow(followReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

  

