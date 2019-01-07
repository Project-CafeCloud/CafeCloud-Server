package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.dto.Map;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MapReq;
import sopt.org.moca.service.MapService;
import sopt.org.moca.utils.auth.JwtUtils;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("/cafe")
public class MapController {

    private final MapService mapService;

    public MapController(final MapService mapService) {
        this.mapService = mapService;
    }


    /**
     *반경 3km 카페 찾기
     *
     *
     * **/
    @PostMapping("/nearbycafe")
    public ResponseEntity GetNearbyCafe(
            @RequestHeader("Authorization") final String jwt, @RequestBody MapReq mapReq){
        try{
            String user_id = JwtUtils.decode(jwt).getUser_id();
            DefaultRes<List<Map>> mapDefaultRes = mapService.GetNearByCafe(mapReq,user_id);


            return new ResponseEntity<>(mapDefaultRes, HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.toString());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
