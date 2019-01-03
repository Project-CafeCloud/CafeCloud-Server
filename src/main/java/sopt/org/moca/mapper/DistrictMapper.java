package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.GetMapping;
import sopt.org.moca.dto.District;

import java.util.List;

@Mapper
public interface DistrictMapper {



    @Select("select * from ADDRESS_DISTRICT")
    List<District> findDistrictList();
}
