package sopt.org.moca.service;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import sopt.org.moca.dto.Membership;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MembershipReq;

import java.util.List;

public interface MembershipService {

    DefaultRes<List<Membership>>findMembershipList(final String user_id);
    DefaultRes saveMembership(final MembershipReq membershipReq);


}
