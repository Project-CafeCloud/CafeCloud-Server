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


    //회원등록
    @Insert("INSERT INTO USER(user_id,user_password,user_name) VALUES(#{UserSignUpReq.user_id}, #{UserSignUpReq.user_password},#{UserSignUpReq.user_name})")
    void save(@Param("userSignupReq") final UserSignUpReq userSignUpReq);


}
