package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Coupon;
import sopt.org.moca.dto.Membership;
import sopt.org.moca.model.MembershipReq;

import java.util.List;

@Mapper
public interface MembershipMapper {


    //맴버쉽 적립
    @Insert("INSERT INTO MEMBERSHIP (cafe_id, user_id) " +
            "VALUES (#{membershipReq.cafe_id}, #{membershipReq.user_id})")
    void saveMembership(@Param("membershipReq")final MembershipReq membershipReq);



    /**
     * 쿠폰 생성 되고 나서 맴버쉽 리스트 재 조회시 처리해야함 (19,1,3)
     * @param user_id
     * @return
     */
    //맴버쉽  리스트 조회
    @Select("SELECT cafe_id,membership_create_date,cafe_img_url " +
            "from MEMBERSHIP natural join CAFE natural join CAFE_IMG " +
            "where user_id = #{user_id} and cafe_img_main = 1 ")
    List<Membership> findMembershipList(@Param("user_id")final String user_id);



    //쿠폰 리스트 조회
   @Select("SELECT coupon_id ,coupon_create_date,coupon_authentication_number" +
           "from COUPON" +
           "where user_id = #{user_id} and coupon_used = 0")
   List<Coupon> findCouponList(@Param("user_id")final String user_id);



    //쿠폰 적립
    @Insert("INSERT INTO COUPON (user_id, authentication_number)" +
            "VALUES (#{Coupon.user_id}, #{coupon_ authentication_number}")
    void saveCoupon(@Param("coupon")final Coupon coupon);


    //쿠폰 사용
    @Update("UPDATE COUPON SET coupon_used = 1 " +
            "where coupon_id = #{coupon_id}")
    void use(@Param("coupon_id")final int coupon_id);
}
