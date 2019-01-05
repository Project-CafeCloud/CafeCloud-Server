package sopt.org.moca.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Scrap;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ScrapReq;
import sopt.org.moca.service.ScrapService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("/user")
public class ScrapController {

    private static final String HEADER = "Authorization";

    private final ScrapService scrapService;

    public ScrapController(final ScrapService scrapService){
        this.scrapService = scrapService;
    }


    /**
     * 스크랩 하기
     *
     * **/
    @PostMapping("/{cafe_id}/scrap")
    public ResponseEntity addScrap(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int cafe_id
    ){

        try{
            ScrapReq scrapReq = new ScrapReq();
            scrapReq.setUser_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            scrapReq.setCafe_id(cafe_id);
            log.info(String.valueOf(scrapReq));
            return new ResponseEntity<>(scrapService.scrap(scrapReq),HttpStatus.OK);

        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * 찜한 카페 조회
     * **/
    @GetMapping("/scrap")
    public ResponseEntity GetScrap(
            final HttpServletRequest httpServletRequest
    ){
        try{
            DefaultRes<List<Scrap>> scrapDefaultRes = scrapService.findById(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            return new ResponseEntity<>(scrapDefaultRes,HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * 스크랩 취소
     * **/
    @DeleteMapping("/{cafe_id}/scrap")
    public ResponseEntity DeleteScrap(
            @PathVariable final int cafe_id
    ){
        try{
            return new ResponseEntity<>(scrapService.deleteByCafeId(cafe_id),HttpStatus.OK);

        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
