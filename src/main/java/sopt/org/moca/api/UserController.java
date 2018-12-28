package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.service.UserService;

import javax.servlet.http.HttpServletRequest;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

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
    *
    * 회원가입
    *
     * @param userSignUpReq 회원 정보
     * @return ResponseEntity
    * */
    @PostMapping("")
    public ResponseEntity signUp(@RequestBody final UserSignUpReq userSignUpReq){
        try{
            return new ResponseEntity<>(userService.save(userSignUpReq), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    /**
//     *
//     * 회원 정보 조회
//     *
//     * @param httpServletRequest Request
//     * @param user_id
//     * @retrun ResponseEntity
//     * **/
//    @GetMapping("/{user_idx}")
//    public ResponseEntity getMypage(final HttpServletRequest httpServletRequest, @PathVariable final String user_id){
//
//    }
}
