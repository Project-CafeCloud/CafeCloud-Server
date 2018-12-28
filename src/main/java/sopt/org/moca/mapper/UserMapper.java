package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.User;
import sopt.org.moca.model.UserSignUpReq;

import java.util.List;

@Mapper
public interface UserMapper {

    //모든 회원 리스트 조회
    @Select("SELECT * FROM USER")
    List<User> findAll();

    /**
    * 아이디로 조회
    * */
    @Select("SELECT * FROM USER WHERE user_id = #{user_id}")
    User findById(@Param("user_id") final String user_id);


    /**
    * 회원가입
    *
    * @param userSignUpReq
    *
    */
    @Insert("INSERT INTO USER(user_id,user_password,user_name,user_phone)"+
            "VALUES(#{userSignUpReq.user_id}, #{userSignUpReq.user_password},#{userSignUpReq.user_name},#{userSignUpReq.user_phone})")
    void save(@Param("userSignUpReq") final UserSignUpReq userSignUpReq);



}
