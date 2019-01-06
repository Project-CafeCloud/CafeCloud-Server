package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.SearchService;

@Slf4j
@RequestMapping("/search")
@RestController
public class SearchController {
    private final SearchService searchService;


    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }

    public ResponseEntity searchCafeKeyword(String keyword)
    {
        try
        {
            return new ResponseEntity(searchService.searchKeywordInCafe(keyword), HttpStatus.OK);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    public ResponseEntity searchCommunityKeyword(String keyword)
    {
        try
        {
            return new ResponseEntity(searchService.searchKeywrodInCommunity(keyword), HttpStatus.OK);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
