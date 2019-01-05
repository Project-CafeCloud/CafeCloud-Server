package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.dto.PlusContentImg;
import sopt.org.moca.dto.PlusContents;
import sopt.org.moca.dto.PlusSubject;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.PlusService;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;

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
     * PLUS 주제 리스트 조회
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
     *  PLUS 카페 주제 상세 조회
     * **/
    @GetMapping("/{plus_subject_id}/cafe")
    public ResponseEntity GetPlusSubjectCafe (@PathVariable final int plus_subject_id){
        try{
            DefaultRes <PlusSubject> plusSubujectRes = plusService.findPlusSubject(plus_subject_id);
            return new ResponseEntity<>(plusSubujectRes,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     *
     * PLUS 카페 콘텐츠 뷰 조회
     * **/
    @GetMapping("/{plus_subject_id}/detail")
    public ResponseEntity GetPlusDetail (@PathVariable final int plus_subject_id){
        try{
            DefaultRes<List<PlusContents>> contentDefaultRes = plusService.findContentList(plus_subject_id);
            return new ResponseEntity<>(contentDefaultRes,HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * Plus 카페 디테일 이미지 뷰 조회
     * **/

    @GetMapping("/{plus_contents_id}/image")
    public ResponseEntity GetPlusImg (@PathVariable final int plus_contents_id){
        try{
            DefaultRes<List<PlusContentImg>> plusContentImgRes = plusService.findPlusImg(plus_contents_id);
            return new ResponseEntity<>(plusContentImgRes,HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
