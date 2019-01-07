package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.service.HotPlaceService;

import javax.servlet.http.HttpServletRequest;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("/hot_place")
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;

    public HotPlaceController(final HotPlaceService hotPlaceService) {
        this.hotPlaceService = hotPlaceService;
    }

    @GetMapping("")
    public ResponseEntity findAllHotPlace(final HttpServletRequest httpServletRequest)
    {
        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */

        try{
            return new ResponseEntity<>(hotPlaceService.findAllHotPlace(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
