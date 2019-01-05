package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import sopt.org.moca.dto.CategorizedCafeSimple;
import sopt.org.moca.dto.Concept;
import sopt.org.moca.dto.District;
import sopt.org.moca.dto.MainMenu;
import sopt.org.moca.model.CategoryParam;
import sopt.org.moca.service.CategoryService;

import java.util.List;

@Mapper
public interface CategoryMapper {


    //지역구 리스트 조회
    @Select("SELECT * FROM ADDRESS_DISTRICT")
    List<District>findDistrictList();


    //컨셉 리스트 조회
    @Select("SELECT * FROM CAFE_CONCEPT")
    List<Concept>findConceptList();


    //주 메뉴 리스트 조회
    @Select("SELECT * FROM CAFE_MAIN_MENU")
    List<MainMenu>findMainMenuList();

    //카테고리회된 카페 리스트 조회

    @Select("<script>"+"SELECT  cafe_id , cafe_name ,cafe_address_detail ,cafe_rating_avg ,cafe_menu_img_url,cafe_main_menu_name, cafe_concept_name " +
            "from CAFE natural join CAFE_IMG natural join CAFE_CONCEPT natural join CAFE_MAIN_MENU "+
            "where cafe_address_district_id = #{district_id} and  cafe_main_menu_id in " +
            "<foreach item='item' index='index' collection='categoryParam.menu'" +
            " open='(' separator=',' close=')'> " +
            " #{item} " +
            " </foreach> " +
            "and cafe_concept_id in"+
            "<foreach item='item' index='index' collection='categoryParam.concept'" +
            " open='(' separator=',' close=')'>" +
            " #{item}" +
            "</foreach>" +"</script>")
    List<CategorizedCafeSimple>findCategorizedCafeList(@Param("categoryParam") CategoryParam categoryParam , @Param("district_id") int district_id);

}
