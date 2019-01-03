package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.PlusContents;
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



    /**
     *
     * Plus 디테일 뷰
     *
     * **/
    @Select("SELECT p.* FROM PLUS_CONTENTS as p, CAFE as c, PLUS_SUBJECT s WHERE c.cafe_id = p.cafe_id AND s.plus_subject_id = p.plus_subject_id ORDER BY plus_contents_id DESC")
    List<PlusContents> findContent (@Param("plus_subject_id") final int plus_subject_id);
}
