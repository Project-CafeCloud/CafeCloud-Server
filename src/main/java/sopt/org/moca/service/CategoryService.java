package sopt.org.moca.service;

import sopt.org.moca.dto.CategorizedCafeSimple;
import sopt.org.moca.dto.Concept;
import sopt.org.moca.dto.District;
import sopt.org.moca.dto.MainMenu;
import sopt.org.moca.model.CategoryParam;
import sopt.org.moca.model.DefaultRes;

import java.util.List;

public interface CategoryService {

    DefaultRes<List<Concept>>findConceptList();

    DefaultRes<List<MainMenu>>findMainMenuList();


    DefaultRes<List<District>>findDistrictList();


    DefaultRes<List<CategorizedCafeSimple>>findCategorizedCafeList(int district_id, CategoryParam categoryParam);

}
