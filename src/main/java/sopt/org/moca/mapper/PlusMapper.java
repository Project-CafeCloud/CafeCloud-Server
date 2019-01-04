package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.PlusContentImg;
import sopt.org.moca.dto.PlusContents;
import sopt.org.moca.dto.PlusSubject;

import java.util.List;

@Mapper
public interface PlusMapper {


    /**
     *  Plus 주제 보여주기
     *
     * **/

    @Select("SELECT p.*,u.user_img_url FROM PLUS_SUBJECT as p, USER as u WHERE u.user_id = p.editor_id ORDER BY plus_subject_id DESC LIMIT #{length}")
    List<PlusSubject> findPlusSubject(@Param("length") final int length);

    /**
     *
     * Plus 주제 한개
     *
     * **/
    @Select("SELECT p.* FROM PLUS_SUBJECT as p, PLUS_CONTENTS as c WHERE p.plus_subject_id = #{plus_subject_id}")
    PlusSubject findSubject(@Param("plus_subject_id")final int plus_subject_id);

    /**
     * PLUS 전체 리스트
     *
     * **/
    @Select("SELECT p.*,u.user_img_url FROM PLUS_SUBJECT as p, USER as u WHERE u.user_id = p.editor_id")
    List<PlusSubject> findPlusSubjectAll();

    /**
     *
     * Plus 이미지 보여주기
     ***/
    @Select("SELECT * FROM PLUS_IMG natural join PLUS_CONTENTS  WHERE plus_contents_id  = #{plus_contents_id}")
    List<PlusContentImg> findPlusContentImg(@Param("plus_contents_id") final int plus_contents_id);
    /**
     *
     * Plus 디테일 뷰
     *
     * **/
    @Select("SELECT p.*,u.user_img_url,s.editor_id FROM PLUS_CONTENTS as p, CAFE as c, PLUS_SUBJECT s,USER as u WHERE c.cafe_id = p.cafe_id AND p.plus_subject_id = #{plus_subject_id} AND u.user_id = s.editor_id ORDER BY plus_contents_id DESC")
    List<PlusContents> findContent (@Param("plus_subject_id") final int plus_subject_id);


}
