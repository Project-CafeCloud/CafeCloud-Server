package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.Coupon;
import sopt.org.moca.dto.Membership;
import sopt.org.moca.mapper.MembershipMapper;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MembershipIns;
import sopt.org.moca.model.MembershipReq;
import sopt.org.moca.service.MembershipService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class MembershipServiceImpl implements MembershipService {
   private final MembershipMapper membershipMapper;
   private final UserMapper userMapper;

    public MembershipServiceImpl(final MembershipMapper membershipMapper, final UserMapper userMapper) {
        this.membershipMapper = membershipMapper;
        this.userMapper = userMapper;
    }


    @Override
    public DefaultRes<List<Membership>> findMembershipList(String user_id) {
        List<Membership> membershipList = membershipMapper.findMembershipList(user_id);

        if(membershipList.isEmpty())
        {
            log.info(membershipList.get(0).toString()+"");
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_MEMBERSHIP_LIST);

        }
        return DefaultRes.res(StatusCode.OK , ResponseMessage.READ_MEMBERSHIP_LIST,membershipList);


    }

    @Override
    public DefaultRes saveMembership(MembershipReq membershipReq)  {

        log.info(membershipReq.toString());
        MembershipIns membershipIns =  new MembershipIns();
        membershipIns.setCafe_id(membershipReq.getCafe_id());
        membershipIns.setUser_id(userMapper.findUserIdByUserPhone(membershipReq.getUser_phone()));
        membershipMapper.saveMembership(membershipIns);
        if(countMembership(membershipIns.getUser_id()) == 12)
        {
            //쿠폰 등록
            Coupon coupon  = new Coupon();
            coupon.setCoupon_authentication_number(GenerateAuthNumber());
            coupon.setUser_id(membershipIns.getUser_id());
            membershipMapper.saveCoupon(coupon);
            //멤버쉽 사용됨으로 바꿈
            membershipMapper.updateMembershipUsed(membershipIns.getUser_id());
        }
        return DefaultRes.res(StatusCode.OK , ResponseMessage.SAVE_MEMBERSHIP);

    }





    private int countMembership(String user_id)
    {
        return membershipMapper.countMembershopByUserId(user_id);
    }

    private String GenerateAuthNumber()
    {
        String str ="";
        Random random = new Random();
        for(int i = 0 ; i <  4; i++)
        {
            str += random.nextInt(10);
        }
        log.info(str);
        return str;
    }

}
