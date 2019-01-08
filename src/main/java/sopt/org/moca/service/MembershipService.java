package sopt.org.moca.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import sopt.org.moca.dto.Membership;
import sopt.org.moca.model.CouponReq;
import sopt.org.moca.model.CouponRes;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MembershipReq;

import java.util.List;

public interface MembershipService {

    DefaultRes<List<Membership>>findMembershipList(final String user_id);


    DefaultRes saveMembership(final MembershipReq membershipReq);



    DefaultRes<List<CouponRes>> findCouponList(final String user_id);


    //쿠폰 사용
    DefaultRes useCoupon(final CouponReq couponReq);

    //쿠폰 인증 등록
    DefaultRes registerAuth(final int coupon_id);

    //쿠폰 인증 삭제
    DefaultRes deleteAuth(final int coupon_id);

    //맴버쉽 사용 내영
    DefaultRes findHistoryOfMembership(final String user_id);

}
