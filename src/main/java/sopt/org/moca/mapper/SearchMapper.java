package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.SearchCafeInfo;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select()
    List<SearchCafeInfo>searchCafeInfoList(String keyword);

}
