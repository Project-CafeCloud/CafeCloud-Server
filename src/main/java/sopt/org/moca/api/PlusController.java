package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.service.PlusService;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/{length}")
    public ResponseEntity GetPlusSubjectList(final HttpServletRequest httpServletRequest, @PathVariable final int length){
        try{
            return new ResponseEntity(HttpStatus.OK);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
