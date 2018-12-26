package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.User;

import java.util.List;

@Mapper
public interface UserMapper {

    //모든 회원 리스트 조회
    @Select("SELECT * FROM user")
    List<User> findAll();

}
