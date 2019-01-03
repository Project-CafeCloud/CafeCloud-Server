package sopt.org.moca.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.DistrictService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
public class DistrictController {
    private final DistrictService districtService;

    public DistrictController(final DistrictService districtService) {
        this.districtService = districtService;
    }

    /**
     * 지역구 별 아이디 조회
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/district")
    public ResponseEntity findDistrictList(final HttpServletRequest httpServletRequest)
    {


        try{
            return new ResponseEntity<>(districtService.findDistrictList(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
