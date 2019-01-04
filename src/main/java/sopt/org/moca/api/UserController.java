package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.User;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.service.UserService;
import sopt.org.moca.service.impl.FileUploadService;
import sopt.org.moca.service.impl.S3Service;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import static sopt.org.moca.model.DefaultRes.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String HEADER = "Authorization";
    private final UserService userService;


    public UserController(final UserService userService) {
        this.userService = userService;

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
            @RequestPart(value = "profile", required = false) final MultipartFile user_img
    ) {
        try {
            return new ResponseEntity<>(userService.save(userSignUpReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Mypage 회원 정보 조회
     *
     * @param httpServletRequest Request
     * @param user_id
     * @retrun ResponseEntity
     **/
    @GetMapping("/{user_id}/mypage")
    public ResponseEntity getMypage(final HttpServletRequest httpServletRequest, @PathVariable final String user_id) {
        try {
            final String tokenValue = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            DefaultRes<User> defaultRes = userService.findById(user_id);
            if (tokenValue.compareTo(user_id) == 0)
                defaultRes.getData().setAuth(true);
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Mypage 회원 정보 수정
     **/
    @Auth
    @PutMapping("/mypage")
    public ResponseEntity updateMypage(
            @RequestHeader("Authorization") final String jwt,
            final UserSignUpReq userSignUpReq,
            @RequestPart(value = "profile", required = false) final MultipartFile user_img
    ) {
        try {
            if (user_img != null) userSignUpReq.setUser_img(user_img);
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
     *
     * Mypage 회원 탈퇴
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

}

  

