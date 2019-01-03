package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.PlusSubject;

import java.util.List;

@Mapper
public interface PlusMapper {


    /**
     *
     * Home 피드에 Moca Plus 주제 3개 보여주기
     *
     * **/

    @Select("SELECT * FROM PLUS_SUBJECT ORDER BY plus_subject_id DESC LIMIT #{length}")
    List<PlusSubject> findPlusSubject(@Param("length") final int length);


}
