package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.service.CafeService;

import javax.servlet.http.HttpServletRequest;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("/cafe")
public class CafeController {
   private final CafeService cafeService;




    public CafeController(final CafeService cafeService) {
        this.cafeService = cafeService;
    }

    /**
     *
     *
     * 인기 있는  검증 카페 리스트 조회
     *
     */
    @GetMapping("/pick/{length}")
    public ResponseEntity getEvaluatedCafeList(final HttpServletRequest httpServletRequest, @PathVariable final int length )
    {
        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */


        try{
            return new ResponseEntity<>(cafeService.findEvaluatedCafeSimpleList(length), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     *검증 카페 상세 정보 조회(카페 이름, 카페 주소, 총평)
     * @param httpServletRequest
     * @param cafe_id
     * @return
     */


    @GetMapping("/pick/detail/{cafe_id}")
    public ResponseEntity getEvaluatedCafeInfo(final HttpServletRequest httpServletRequest,@PathVariable final int cafe_id)
    {
        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */




        try{
            return new ResponseEntity<>(cafeService.findEvaluatedCafeInfo(cafe_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 검증카페 이미지 리스트 조회
     * @param httpServletRequest
     * @param cafe_id
     * @return
     */

    @GetMapping("/pick/image/{cafe_id}")
    public ResponseEntity getEvaluatedCafeImgList(final HttpServletRequest httpServletRequest,@PathVariable final int cafe_id)
    {
        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */




        try{
            return new ResponseEntity<>(cafeService.findEvaluatedCafeImg(cafe_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 검증 카페 인증 위원 평가 상세 조회
     * @param httpServletRequest
     * @param cafe_id
     * @return
     */

    @GetMapping("/pick/evaluate/{cafe_id}")
    public ResponseEntity getEvaluationList(final HttpServletRequest httpServletRequest,@PathVariable final int cafe_id)
    {
        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */




        try{
            return new ResponseEntity<>(cafeService.findEvaluationList(cafe_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }





    }


    /**
     * 검증카페 인증 위원 평가 상세 조회
     * @param httpServletRequest
     * @param cafe_id
     * @param barista_id
     * @return
     */

    @GetMapping("/pick/evaluate/{cafe_id}/barista/{barista_id}")
    public ResponseEntity getEvaluationDetail(final HttpServletRequest httpServletRequest,@PathVariable final int cafe_id,@PathVariable final int barista_id)
    {

        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */




        try{
            return new ResponseEntity<>(cafeService.findEvaluationDetail(cafe_id,barista_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * 카페 상세 이미지 리스트 조회
     * @param httpServletRequest
     * @param cafe_id
     * @return
     */
    @GetMapping("/{cafe_id}/image")
    public ResponseEntity getCafeImgList(final HttpServletRequest httpServletRequest,@PathVariable final int cafe_id)
    {

        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */




        try{
            return new ResponseEntity<>(cafeService.findCafeImgList(cafe_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    /**
     * 카페 상세 정보 조회
     * @param httpServletRequest
     * @param cafe_id
     * @return
     */
    @GetMapping("/{cafe_id}/detail")
    public ResponseEntity getCafeInfo(final HttpServletRequest httpServletRequest,@PathVariable final int cafe_id )
    {
        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */



        try{
            return new ResponseEntity<>(cafeService.findCafeInfo(cafe_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }

    /**
     * 카페 시그니처 메뉴 리스트 조회
     * @param httpServletRequest
     * @param cafe_id
     * @return
     */

    @GetMapping("/{cafe_id}/signiture")
    public ResponseEntity getCafeSignitureList(final HttpServletRequest httpServletRequest, @PathVariable final int cafe_id)
    {

        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */



        try{
            return new ResponseEntity<>(cafeService.findCafeSignitureMenuList(cafe_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * 지역구별 카페 리스트
     *
     *
     */
    @GetMapping("/category/location/{address_district_id}")
    public ResponseEntity getCafeSimpleList(final HttpServletRequest httpServletRequest, @PathVariable final int address_district_id)
    {



        /**
         * 토큰으로 유효한지 아닌지 확인 구현 필요
         *
         */
        log.info("1234");

        try{
            return new ResponseEntity<>(cafeService.findCafeSimpleList(address_district_id), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }






}
