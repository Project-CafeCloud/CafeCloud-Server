package sopt.org.moca.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.CategorizedCafeSimple;
import sopt.org.moca.dto.Concept;
import sopt.org.moca.dto.District;
import sopt.org.moca.dto.MainMenu;
import sopt.org.moca.mapper.CategoryMapper;
import sopt.org.moca.model.CategoryParam;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.CategoryService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
   final CategoryMapper categoryMapper;


    public CategoryServiceImpl(final CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    @Override
    public DefaultRes<List<Concept>> findConceptList() {
        List<Concept> conceptList  = null;
        conceptList = categoryMapper.findConceptList();
        if (conceptList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_CONCEPT);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CONCPET,conceptList);

    }


    @Override
    public DefaultRes<List<MainMenu>> findMainMenuList() {
        List<MainMenu> mainMenuList  = null;
        mainMenuList = categoryMapper.findMainMenuList();
        if (mainMenuList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_MAIN_MENU);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_MAIN_MENU,mainMenuList);

    }

    @Override
    public DefaultRes<List<District>> findDistrictList() {
        List<District> districtList =null;
        districtList = categoryMapper.findDistrictList();
        if (districtList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_DISTRICT);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_DISTRICT, districtList);

    }

    @Override
    public DefaultRes<List<CategorizedCafeSimple>> findCategorizedCafeList(int district_id, CategoryParam categoryParam) {
        List<CategorizedCafeSimple> categorizedCafeSimpleList = null;
        log.info(categoryParam.toString());


        categorizedCafeSimpleList = categoryMapper.findCategorizedCafeList(categoryParam,district_id);
        log.info(categorizedCafeSimpleList.toString());

        if ( categorizedCafeSimpleList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_CATEGORIZED_CAFE);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CATEGORIZED_CAFE,  categorizedCafeSimpleList);


    }
}
