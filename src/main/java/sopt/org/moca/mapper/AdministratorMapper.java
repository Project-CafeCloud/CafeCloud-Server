package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.Administrator;

import java.util.List;

@Mapper
public interface AdministratorMapper {

    //관리자 조회
    @Select("SELECT * FROM ADMINISTRATOR")
    List<Administrator> findAll();
}
