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


    //검증 카페 상세 정보 조회(카페이름, 카페주소,총평, 평균 별점)
    @Select("select cafe_name, cafe_address, evaluated_cafe_total_evaluation, evaluated_cafe_rating " +
            "from EVALUATED_CAFE natural join CAFE" +
            "where cafe_id = #{cafe_id}")
    EvaluatedCafeInfo findEvaluatedCafeInfo(@Param("cafe_id")final int cafe_id);



    //검증 카페  이미지 조회
    @Select("select evaluated_cafe_img_url,evaluated_cafe_main_img " +
            "from EVALUATED_CAFE_IMG" +
            "where cafe_id = #{cafe_id}")
    List<EvaluatedCafeImg>findEvaluatedCafeImg(@Param("cafe_id")final int cafe_id);



    //검증 카페 검증 위원 평가 정보 리스트 조회
    @Select("select barista_id,barista_name,barista_title,barista_img_url,evaluation_bean_condition,evaluation_bean_condition_comment,evaluation_roasting,evaluation_roasting_comment,evaluation_creativity,evaluation_creativity_comment,evaluation_reasonable,evaluation_reasonable_comment,evaluation_consistancy,evaluation_consistancy_comment" +
            "from EVALUATION natural join BARISTA" +
            "where cafe_id = #{cafe_id}")
    List<Evaluation>findAllEvaluation(@Param("cafe_id")final int cafe_id);


    //해당 검증 위원 평가 정보 조회
    @Select("select barista_name,barista_title,barista_img_url,evaluation_bean_condition,evaluation_bean_condition_comment,evaluation_roasting,evaluation_roasting_comment,evaluation_creativity,evaluation_creativity_comment,evaluation_reasonable,evaluation_reasonable_comment,evaluation_consistancy,evaluation_consistancy_comment,evaluation_summary" +
            "from EVALUATION natural join BARISTA" +
            "where barista_id = #{barista_id} , cafe_id = #{cafe_id}")
    Evaluation_detail findBaristaEvaluation(@Param("cafe_id")final int cafe_id,@Param("barista_id")final int barista_id);

/**
 *
 *
 *
 *
 *
 */

//    //해당 카페 정보 조회
//    @Select()
//
//    //해당 카페 검증위원 상세 조회
//    @Select()
//
//    //해당 핫플레이스 카페 리스트 조회
//    @Select()
//
//
//    //평점 높은 순, 카페리스트 조

}
