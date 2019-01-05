package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.Follow;
import sopt.org.moca.dto.User;
import sopt.org.moca.model.FollowReq;
import sopt.org.moca.model.UserSignUpReq;

import java.util.List;

@Mapper
public interface FollowMapper {


    /**
     * 팔로우하기
     *
     * @param followReq
     *
     */
    @Insert("INSERT INTO FOLLOW (follower_id, following_id) "+
            "VALUES(#{followReq.follower_id}, #{followReq.following_id})")
    void save(@Param("followReq") final FollowReq followReq);


    /**
     * 언팔로우하기
     *
     * @param followReq
     *
     */
    @Insert("DELETE FROM FOLLOW " +
            "WHERE follower_id = #{followReq.follower_id} AND following_id = #{followReq.following_id}")
    void delete(@Param("followReq") final FollowReq followReq);


    /**
     * 팔로우 조회
     *
     * @param follower_id
     * @param following_id
     *
     * */
    @Select("SELECT * FROM FOLLOW " +
            "WHERE follower_id = #{follower_id} AND following_id = #{following_id}")
    Follow findByFollowerIdAndFollowingId(@Param("follower_id") final String follower_id,
                                          @Param("following_id") final String following_id);


    /**
     * 팔로워 목록 조회
     *
     * @param user_id
     *
     * */
    @Select("SELECT USER.user_id, USER.user_name, USER.user_img_url " +
            "FROM FOLLOW, USER " +
            "WHERE FOLLOW.follower_id = USER.user_id " +
            "AND following_id = #{user_id}")
    List<User> findFollower(@Param("user_id") final String user_id);


    /**
     * 팔로잉 목록 조회
     *
     * @param user_id
     *
     * */
    @Select("SELECT USER.user_id, USER.user_name, USER.user_img_url " +
            "FROM FOLLOW, USER " +
            "WHERE FOLLOW.following_id = USER.user_id " +
            "AND FOLLOW.follower_id = #{user_id}")
    List<User> findFollowing(@Param("user_id") final String user_id);


}
