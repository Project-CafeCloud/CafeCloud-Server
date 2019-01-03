package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MembershipReq;
import sopt.org.moca.service.MembershipService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;
import static sopt.org.moca.model.DefaultRes.UNAUTHORIZED_RES;

@Slf4j
@RestController
public class MembershipController {
    private  final MembershipService membershipService;


    public MembershipController(final MembershipService membershipService) {
        this.membershipService = membershipService;
    }


    /**
     * 멤버쉽 리스트 조회
     * @param jwt
     * @return
     */
    @Auth
    @GetMapping("/membership")
    public ResponseEntity findMembershipList(@RequestHeader("Authorization") final String jwt)
    {
        String user_id = null;

        try{
            user_id = JwtUtils.decode(jwt).getUser_id();
            return new ResponseEntity<>(membershipService.findMembershipList(user_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/membership")
    public ResponseEntity saveMembership(@RequestBody final MembershipReq membershipReq)
    {

        try{
            return new ResponseEntity<>(membershipService.saveMembership(membershipReq), HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());

            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }



}
