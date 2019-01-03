package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.Membership;
import sopt.org.moca.mapper.MembershipMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MembershipReq;
import sopt.org.moca.service.MembershipService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.sql.SQLException;
import java.util.List;
@Slf4j
@Service
public class MembershipServiceImpl implements MembershipService {
   private final MembershipMapper membershipMapper;

    public MembershipServiceImpl(final MembershipMapper membershipMapper) {
        this.membershipMapper = membershipMapper;
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
    public DefaultRes saveMembership(MembershipReq membershipReq) {

        log.info(membershipReq.toString());

        try
        {
            membershipMapper.saveMembership(membershipReq);
        }
        catch (MyBatisSystemException e)
        {

            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.FAIL_SAVE_MEMBERSHIP);

        }

        return DefaultRes.res(StatusCode.OK , ResponseMessage.SAVE_MEMBERSHIP);

    }

}
