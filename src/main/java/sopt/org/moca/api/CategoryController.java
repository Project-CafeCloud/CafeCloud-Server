package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.model.CategoryParam;
import sopt.org.moca.service.CategoryService;

import java.util.*;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;


@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }




    @GetMapping("/district")
public ResponseEntity findDistrictList()
    {


        try{
            return new ResponseEntity<>(categoryService.findDistrictList(), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/location/{district_id}")
    public ResponseEntity findCategorizedCafeList(@PathVariable final int district_id ,final CategoryParam categoryParam) {
        try{
            if(categoryParam.getConcept() == null)
            {
                 int[] ary = new int[8];
                for(int i = 1 ; i < 9 ; i++)
                     ary[i-1] = i;
                categoryParam.setConcept(ary);
            }
            if(categoryParam.getMenu() == null)
            {
                int[] ary = new int[6];
                for(int i = 1 ; i < 7 ; i++)
                    ary[i-1] = i;;
                categoryParam.setMenu(ary);
            }

            return new ResponseEntity<>(categoryService.findCategorizedCafeList(district_id,categoryParam), HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/concept")
public ResponseEntity findConceptList()
{
    try{
        return new ResponseEntity<>(categoryService.findConceptList(), HttpStatus.OK);
    } catch (Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

@GetMapping("/menu")
public ResponseEntity findMenuList()
{
    try{
        return new ResponseEntity<>(categoryService.findMainMenuList(), HttpStatus.OK);
    } catch (Exception e){
        log.error(e.getMessage());
        return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}
