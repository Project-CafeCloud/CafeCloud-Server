package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.dto.PlusSubject;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.PlusService;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("/plus")
public class PlusController {

    private static final String HEADER = "Authorization";
    private final PlusService plusService;

    public PlusController(final PlusService plusService) {
        this.plusService = plusService;
    }

    /**
     *
     * PLUS 주제 조회
     * **/
    @GetMapping("/{length}")
    public ResponseEntity GetPlusSubjectList( @PathVariable final int length){
        try{
            DefaultRes<List<PlusSubject>> plusDefaultRes = plusService.findPlusSubjectList(length);
            return new ResponseEntity<>(plusDefaultRes, HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * PLUS 카페 디테일 뷰 조회
     * **/
//    @GetMapping("/{plus_subject_id}/detail")
//    public ResponseEntity GetPlusDetail (@PathVariable final int plus_subject_id){
//        try{
//
//
//        }catch (Exception e){
//            log.error(e.getMessage());
//            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
