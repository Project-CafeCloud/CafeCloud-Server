package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.User;
import sopt.org.moca.dto.UserInfo;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.service.FollowService;
import sopt.org.moca.service.UserService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String HEADER = "Authorization";
    private final UserService userService;
    private final FollowService followService;


    public UserController(final UserService userService, final FollowService followService) {
        this.userService = userService;
        this.followService = followService;

    }


    /**
     * 회원가입
     *
     * @param userSignUpReq 회원 정보
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity signUp(
            UserSignUpReq userSignUpReq,
            @RequestPart(value = "profile", required = false) final MultipartFile profile
    ) {
//        if(userSignUpReq.getUser_img() != null) userSignUpReq.setUser_img(profile);
        try {
            return new ResponseEntity<>(userService.save(userSignUpReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Mypage 정보 조회
     *
     * @param httpServletRequest Request
     * @retrun ResponseEntity
     **/
    @GetMapping("/mypage")
    public ResponseEntity getMypage(final HttpServletRequest httpServletRequest) {
        try {
            final String tokenValue = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            DefaultRes<User> defaultRes = userService.findById(tokenValue);
            if (tokenValue.compareTo(tokenValue) == 0)
                defaultRes.getData().setAuth(true);
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Mypage 정보 수정
     **/
    @Auth
    @PutMapping("/mypage")
    public ResponseEntity updateMypage(
            @RequestHeader("Authorization") final String jwt,
            final UserSignUpReq userSignUpReq,
            @RequestPart(value = "profile", required = false) final MultipartFile profile
    ) {
        try {
           // log.info(user_img.toString());
            if (profile != null) userSignUpReq.setUser_img(profile);
            //log.info(user_img.toString());
            final String tokenValue = JwtUtils.decode(jwt).getUser_id();
            if(tokenValue != null) return new ResponseEntity<>(userService.updateUser(tokenValue, userSignUpReq), HttpStatus.OK);
            else{
                    return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 회원 정보 조회
     *
     * @param httpServletRequest Request
     * @param user_id
     * @retrun ResponseEntity
     *
     **/
    @GetMapping("/{user_id}")
    public ResponseEntity getUser(final HttpServletRequest httpServletRequest, @PathVariable String user_id) {
        try {
            log.info(user_id);

            final String tokenValue = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            log.info(tokenValue);
            if(user_id.equals("-1")){
                user_id = tokenValue;
            }
            log.info(user_id);
            DefaultRes<UserInfo> userInfoDefaultRes = userService.findUser(user_id,tokenValue);

            if (tokenValue.compareTo(userInfoDefaultRes.getData().getUser_id()) == 0)
                userInfoDefaultRes.getData().setAuth(true);


//            else {
//
//                userInfoDefaultRes.getData().setFollow(followService.checkFollow(tokenValue, user_id));
//            }

            return new ResponseEntity<>(userInfoDefaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     *
     *  회원 탈퇴
     *
     * **/
    @Auth
    @DeleteMapping("/{user_id}")
    public ResponseEntity DeleteUser(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final String user_id)
    {
        try{
            final String tokenValue = JwtUtils.decode(jwt).getUser_id();
            if(tokenValue.compareTo(user_id)==0) return new ResponseEntity<>(userService.deleteById(user_id),HttpStatus.OK);
            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /**
     * 인기 유저 조회
     *
     * @param httpServletRequest Request
     * @retrun ResponseEntity
     **/
    @GetMapping("/best")
    public ResponseEntity getBestUser(final HttpServletRequest httpServletRequest) {
        try {
            final String user_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();

            DefaultRes<List<User>> bestUser = userService.findBestUser(5);

            for(User u : bestUser.getData()){
                // 나
                if (user_id.compareTo(u.getUser_id()) == 0)
                    u.setAuth(true);
                else
                    u.setFollow(followService.checkFollow(user_id, u.getUser_id()));
            }

            return new ResponseEntity<>(bestUser, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

  

