package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.*;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.FollowReq;
import sopt.org.moca.service.FollowService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

/**
 * findFollowList       : 팔로우 리스트 조회
 * follow               : 팔로우/언팔로우 하기
 * checkFollow          : 팔로우 여부 확인
 */


@Slf4j
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;


    /**
     * 생성자 의존성 주입
     *
     * @param followMapper
     */

    public FollowServiceImpl(final FollowMapper followMapper) {

        this.followMapper = followMapper;
    }


    /**
     * 팔로우 리스트 조회
     *
     * @param userId       유저 고유 id
     * @param flag         팔로워:true / 팔로잉:false
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<User>> findFollowList(final String userId, final boolean flag) {

        List<User> followList;

        // 팔로워 리스트 조회
        if(flag){

            followList = followMapper.findFollower(userId);
        }
        // 팔로잉 리스트 조회
        else {

            followList = followMapper.findFollowing(userId);
        }

        if (followList.isEmpty())
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_FOLLOW);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_FOLLOW, followList);
    }


    /**
     * 팔로우 및 언팔로우
     *
     * @param followReq      팔로우 데이터
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes follow(final FollowReq followReq) {

        final String follower_id = followReq.getFollower_id();
        final String following_id = followReq.getFollowing_id();

        try {
            if (!checkFollow(follower_id, following_id)) {
                // 팔로우
                followMapper.save(followReq);
            } else {
                // 언팔로우
                followMapper.delete(followReq);
            }

            return DefaultRes.res(StatusCode.OK, ResponseMessage.FOLLOW_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }


    /**
     * 팔로우 여부 확인
     *
     * @param follower_id    팔로워 고유 id
     * @param following_id   팔로잉 고유 id
     * @return boolean
     */
    @Override
    public boolean checkFollow(final String follower_id, final String following_id) {
        return followMapper.findByFollowerIdAndFollowingId(follower_id, following_id) != null;
    }
}