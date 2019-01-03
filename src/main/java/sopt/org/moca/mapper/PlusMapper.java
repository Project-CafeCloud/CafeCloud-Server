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
     *  Plus 주제 보여주기
     *
     * **/

    @Select("SELECT p.*,u.user_img_url FROM PLUS_SUBJECT as p, USER as u WHERE u.user_id = p.editor_id ORDER BY plus_subject_id DESC LIMIT #{length}")
    List<PlusSubject> findPlusSubject(@Param("length") final int length);


}
