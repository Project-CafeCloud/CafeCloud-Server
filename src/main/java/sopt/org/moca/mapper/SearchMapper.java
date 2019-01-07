package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.SearchCafeInfo;
import sopt.org.moca.dto.SearchReviewInfo;
import sopt.org.moca.dto.SearchUserInfo;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("select CAFE.cafe_id ,cafe_name ,cafe_img_url,cafe_address_detail " +
            "from CAFE left join CAFE_IMG on  CAFE.cafe_id = CAFE_IMG.cafe_id inner join ADDRESS_DISTRICT on CAFE.cafe_address_district_id = ADDRESS_DISTRICT.address_district_id\n" +
            "natural join CAFE_CONCEPT natural join HOT_PLACE " +
            "where  address_district_name like'%${keyword}%' or hot_place_name like '%${keyword}%' or cafe_address_detail like '%${keyword}%'  or cafe_name like '%${keyword}%' ")
    List<SearchCafeInfo>searchCafeInfoList(@Param("keyword")final String keyword);




@Select("select  user_id, user_name ,user_status_comment , user_img_url , " +
        "(CASE  WHEN user_id in (select following_id from FOLLOW where follower_id  =#{user_id})THEN 1 ELSE 0 END) as is_follow " +
        "from USER " +
        "where user_name like '%${keyword}%'")
    List<SearchUserInfo>searchUserInfoList(@Param("keyword")final String keyword, @Param("user_id")final String user_id);


@Select("select  REVIEW.review_id,review_img_url ,(select count(*) from REVIEW_LIKE where REVIEW_LIKE.review_id = REVIEW.review_id) as like_count " +
        "from REVIEW natural join CAFE left  join REVIEW_IMG on REVIEW.review_id = REVIEW_IMG.review_id " +
        "where  CAFE.cafe_name like '%${keyword}%' " +
        "group by REVIEW.review_id " +
        "order by 1")
    List<SearchReviewInfo>searchReviewInfoList(@Param("keyword")final String keyword);







}
