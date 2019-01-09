package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.SearchService;
import sopt.org.moca.utils.auth.JwtUtils;

@Slf4j
@RequestMapping("/search")
@RestController
public class SearchController {
    private final SearchService searchService;


    public SearchController(final SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping("/cafe/{keyword}")
    public ResponseEntity searchCafeKeyword(@PathVariable final String keyword)
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



    @GetMapping("/community/{keyword}")
    public ResponseEntity searchCommunityKeyword(@RequestHeader("Authorization") final String jwt, @PathVariable final String keyword)
    {
        String user_id;
        try
        {
            user_id = JwtUtils.decode(jwt).getUser_id();
            return new ResponseEntity(searchService.searchKeywordInCommunity(keyword,user_id), HttpStatus.OK);
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
            return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

   @GetMapping("/follower/{keyeord}")
   public ResponseEntity searchFollowerList(@RequestHeader("Authorization") final String jwt)
   {
       String user_id;
       try
       {
           user_id = JwtUtils.decode(jwt).getUser_id();
           return new ResponseEntity(searchService.searchFollowerInfoList(user_id), HttpStatus.OK);
       }
       catch(Exception e)
       {
           log.error(e.getMessage());
       }
       return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
   }



   @GetMapping("/following")
   public ResponseEntity searchFollowingList(@RequestHeader("Authorization") final String jwt)
   {
       String user_id;
       try
       {
           user_id = JwtUtils.decode(jwt).getUser_id();
           return new ResponseEntity(searchService.searchFollingInfoList(user_id), HttpStatus.OK);
       }
       catch(Exception e)
       {
           log.error(e.getMessage());
       }
       return new ResponseEntity(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
   }


}
