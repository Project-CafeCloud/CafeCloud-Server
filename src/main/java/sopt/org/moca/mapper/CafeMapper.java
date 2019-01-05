package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.*;

import java.util.List;


@Mapper
public interface CafeMapper {

    /**
     *
     *
     *     검증 카페
     *
     *
     */

    //인기 있는 검증 카페 리스트 조회 (갯수)
    @Select("select cafe_id, cafe_name , address_district_name, evaluated_cafe_img_url,evaluated_cafe_rating " +
            "from EVALUATED_CAFE natural join EVALUATED_CAFE_IMG natural join CAFE natural join ADDRESS_DISTRICT " +
            " where evaluated_cafe_main_img = 1 "
            +
            "ORDER BY evaluated_cafe_rating DESC "+
            "limit  #{length}" )
    List<EvaluatedCafeSimple>findPopularEvaluatedCafe(@Param("length") final int length);

    //인기 있는 검증 카페 리스트 조회(전체)
    @Select("select cafe_id, cafe_name , address_district_name, evaluated_cafe_img_url,evaluated_cafe_rating " +
            "from EVALUATED_CAFE natural join EVALUATED_CAFE_IMG natural join CAFE natural join ADDRESS_DISTRICT " +
            "where evaluated_cafe_main_img = 1 ")
    List<EvaluatedCafeSimple>findAllEvaluatedCafe();

    //검증 카페 상세 정보 조회(카페이름, 카페주소, 총평, 평균 별점)
    @Select("select cafe_name,cafe_address_detail , evaluated_cafe_total_evaluation, evaluated_cafe_rating " +
            "from EVALUATED_CAFE natural join CAFE " +
            "where cafe_id = #{cafe_id}")
    EvaluatedCafeInfo findEvaluatedCafeInfo(@Param("cafe_id")final int cafe_id);



    //검증 카페  이미지 조회
    @Select("select evaluated_cafe_img_url,evaluated_cafe_main_img " +
            "from EVALUATED_CAFE_IMG " +
            "where cafe_id = #{cafe_id}")
    List<EvaluatedCafeImg>findEvaluatedCafeImg(@Param("cafe_id")final int cafe_id);



    //검증 카페 검증 위원 평가 정보 리스트 조회
    @Select("select barista_id, barista_name, barista_title, barista_img_url, " +
            "evaluation_bean_condition, evaluation_bean_condition_comment, evaluation_roasting, evaluation_roasting_comment, evaluation_creativity, evaluation_creativity_comment, evaluation_reasonable, evaluation_reasonable_comment, evaluation_consistancy, evaluation_consistancy_comment " +
            "from EVALUATION natural join BARISTA " +
            "where cafe_id = #{cafe_id}")
    List<Evaluation>findAllEvaluation(@Param("cafe_id")final int cafe_id);


    //해당 검증 위원 평가 정보 조회
    @Select("select barista_name,barista_title,barista_img_url,evaluation_bean_condition,evaluation_bean_condition_comment,evaluation_roasting,evaluation_roasting_comment,evaluation_creativity,evaluation_creativity_comment,evaluation_reasonable,evaluation_reasonable_comment,evaluation_consistancy,evaluation_consistancy_comment,evaluation_summary " +
            "from EVALUATION natural join BARISTA " +
            "where barista_id = #{barista_id} AND cafe_id = #{cafe_id}")
    Evaluation_detail findBaristaEvaluation(@Param("cafe_id")final int cafe_id,@Param("barista_id")final int barista_id);

    /**
     *
     *
     *카페 상세보기
     *
     *
     *
     */

    // 카페 간단 조회 (REVIEW에서 사용)
    @Select("SELECT CAFE.cafe_id, CAFE.cafe_name, ADDRESS_DISTRICT.address_district_name " +
            "FROM CAFE, ADDRESS_DISTRICT " +
            "WHERE CAFE.cafe_id = #{cafe_id} " +
            "AND ADDRESS_DISTRICT.address_district_id = CAFE.cafe_address_district_id")
    CafeInfo findByCafeId(@Param("cafe_id") final int cafe_id);


    //카페 이미지 리스트 조회
    @Select("select cafe_img_url from CAFE_IMG where cafe_id = #{cafe_id}")
    List<CafeImg> findCafeImgList(@Param("cafe_id")final int cafe_id);


    //카페 상세 정보 조회  model
    @Select("select cafe_name, cafe_latitude, cafe_longitude, cafe_phone, cafe_menu_img_url, address_district_name, cafe_address_detail, " +
            "cafe_rating_avg, cafe_times, cafe_days, cafe_option_parking, cafe_option_wifi, cafe_option_allnight, cafe_option_reservation, cafe_option_smokingarea " +
            "from (CAFE LEFT join CAFE_OPTION on CAFE.cafe_id = CAFE_OPTION.cafe_id) join ADDRESS_DISTRICT on cafe_address_district_id = address_district_id " +
            "where CAFE.cafe_id = #{cafe_id}")
    CafeInfo findCafeInfo(@Param("cafe_id")final int cafe_id);


    //시그니처 메뉴 리스트 조회
    @Select("select cafe_signiture_menu,cafe_signiture_price,cafe_signiture_img " +
            "from CAFE_SIGNITURE " +
            "where cafe_id = #{cafe_id}")
    List<CafeSignitureMenu> findCafeSigitureMenuList(@Param("cafe_id")final int cafe_id);


    /**
     * 카페 카테고리
     */

    //해당 지역구 전체
    @Select("select cafe_id, cafe_name, cafe_introduction, cafe_latitude, cafe_longitude ,cafe_rating_avg,cafe_img_url " +
            "from CAFE natural join CAFE_IMG " +
            "where cafe_img_main = 1 and cafe_address_district_id = #{address_district_id}")
    List<CafeSimple> findCafeInfoList(@Param("address_district_id")final int address_district_id);
}