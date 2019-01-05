
package sopt.org.moca.service;

import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.dto.User;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.FollowReq;
import sopt.org.moca.model.ReviewReq;

import java.util.List;

public interface FollowService {

    DefaultRes<List<User>> findFollowList(final String userId, final boolean flag);

    DefaultRes follow(final FollowReq followReq);

    boolean checkFollow(final String followerId, final String followingId);

}

