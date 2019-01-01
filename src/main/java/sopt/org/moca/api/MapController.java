package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.service.MapService;

@Slf4j
@RestController
@RequestMapping("/cafe")
public class MapController {

    private final MapService mapService;

    public MapController(final MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/nearbycafe")
    public ResponseEntity<> GetNearbyCafe(){

    }
}
