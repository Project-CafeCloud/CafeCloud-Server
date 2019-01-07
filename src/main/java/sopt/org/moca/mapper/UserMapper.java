package sopt.org.moca.mapper;

import org.apache.ibatis.annotations.*;
import sopt.org.moca.dto.User;
import sopt.org.moca.dto.UserInfo;
import sopt.org.moca.model.UserSignUpReq;

import java.util.List;

@Mapper
public interface UserMapper {

    //모든 회원 리스트 조회
    @Select("SELECT * FROM USER")
    List<User> findAll();


    /**
     * 회원 정보 조회 (feed)
     * @param user_id
     *
     * */
    @Select("SELECT user_id, user_name, user_img_url, user_status_comment " +
            "FROM USER WHERE user_id = #{user_id}")
    UserInfo findUser(@Param("user_id") final String user_id);



    /**
    * 아이디로 조회
    * @param user_id
     *
    * */
    @Select("SELECT * FROM USER WHERE user_id = #{user_id}")
    User findById(@Param("user_id") final String user_id);



    /**
    * 회원가입
    *
    * @param userSignUpReq
    *
    */
    @Insert("INSERT INTO USER(user_id,user_password,user_name,user_phone,user_img_url)"+
            "VALUES(#{userSignUpReq.user_id}, #{userSignUpReq.user_password},#{userSignUpReq.user_name},#{userSignUpReq.user_phone},#{userSignUpReq.user_img_url})")
    void save(@Param("userSignUpReq") final UserSignUpReq userSignUpReq);


    /***
    *
    * 전화 번호로 아이디 찾기
    *
    */
    @Select("SELECT user_id FROM USER WHERE user_phone = #{user_phone}")
    String findUserIdByUserPhone(@Param("user_phone")final String user_phone);

    /**
     *
     * 로그인
     *
     * @param user_id
     * @param user_password
     * @return User
     * **/
    @Select("SELECT * FROM USER WHERE user_id = #{user_id} AND user_password = #{user_password}")
    User findByIdAndPassword(@Param("user_id") final String user_id,@Param("user_password") final String user_password);

    /**
     *
     * Mypage 수정
     *
     * **/
    @Update("UPDATE USER SET user_name = #{user.user_name}, user_status_comment = #{user.user_status_comment}," +
            "user_phone = #{user.user_phone}, user_img_url = #{user.user_img_url} WHERE user_id = #{user.user_id}")
    void update(@Param("user_id") final String user_id,@Param("user") final User user);

    /**
     *
     * 회원 탈퇴
     *
     * **/
    @Delete("DELETE  FROM USER WHERE user_id = #{user_id}")
    void deleteById(@Param("user_id") final String user_id);



    /**
     * 인기 있는 유저 리스트 조회 (팔로워 순)
     *
     * @param num
     *
     * */
    @Select("SELECT user_id, user_name, user_img_url " +
            "FROM USER JOIN " +
            "(SELECT following_id, count(*) AS c " +
            "FROM FOLLOW " +
            "GROUP BY following_id) AS F " +
            "ON F.following_id = USER.user_id " +
            "ORDER BY c DESC " +
            "LIMIT #{num}")
    List<User> findBestUser(@Param("num") final int num);


}
