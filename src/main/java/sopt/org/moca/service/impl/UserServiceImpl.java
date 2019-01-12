package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.User;
import sopt.org.moca.dto.UserInfo;
import sopt.org.moca.mapper.FollowMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.service.UserService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final ReviewMapper reviewMapper;
    private final FollowMapper followMapper;
    private final FileUploadService fileUploadService;

    @Value("https://s3.ap-northeast-2.amazonaws.com/project-moca/")
    private String defaultUrl;

    private String defaultUserImage = "user/commonDefaultimage%403x.png";

    /**
     * 생성자 의존성 주입
     *
     * @param userMapper
     */
    public UserServiceImpl(final UserMapper userMapper,
                           final ReviewMapper reviewMapper,
                           final FollowMapper followMapper,
                           final FileUploadService fileUploadService) {
        this.userMapper = userMapper;
        this.reviewMapper = reviewMapper;
        this.followMapper = followMapper;
        this.fileUploadService = fileUploadService;
    }

    /**
     * 회원 가입
     * @param userSignUpReq 회원 데이터
     *
     */
    @Transactional
    @Override
    public DefaultRes save(UserSignUpReq userSignUpReq){
        //모든 항목이 있는지 체크하기
        if(userSignUpReq.checkElement()) {
            final User user = userMapper.findById(userSignUpReq.getUser_id());
            if(user == null) {
                try {
                    if(userSignUpReq.getUser_img() != null){
                        userSignUpReq.setUser_img_url(fileUploadService.upload(userSignUpReq.getUser_img(), "user"));
                        userMapper.save(userSignUpReq);
                    }
                    else{
                        //디폴트 이미지
                        userMapper.saveNoImg(userSignUpReq);
                    }
                    return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_USER);

                } catch (Exception e) {
                    //Rollback
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error(e.getMessage());
                    return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
                }
            } else return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.USER_ALREADY_EXISTS);
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_USER);
    }


    /**
     * User 조회
     * @param user_id
     * @return DefaultRes
     * **/
    @Override
    public DefaultRes<User> findById(final String user_id){

        User user = userMapper.findById(user_id);
        if (user != null) {
            if(user.getUser_img_url() !=null) {
                user.setUser_img_url(defaultUrl + user.getUser_img_url());
            }else{
                user.setUser_img_url(null);
            }
            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
        }
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }

    /**
     *
     * Mypage USER 수정
     *
     * **/

    @Transactional
    @Override
    public DefaultRes updateUser(final String token_value, UserSignUpReq userSignUpReq){
        User temp = findById(token_value).getData();
        if(temp == null){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_USER);
        }
        try{
            if(userSignUpReq.getUser_name() != null) temp.setUser_name(userSignUpReq.getUser_name());
            if(userSignUpReq.getUser_phone() != null) temp.setUser_phone(userSignUpReq.getUser_phone());
            if(userSignUpReq.getUser_status_comment() != null) temp.setUser_status_comment(userSignUpReq.getUser_status_comment());

            if(userSignUpReq.getUser_img() != null)
                temp.setUser_img_url(fileUploadService.upload(userSignUpReq.getUser_img(), "user"));
            else
                temp.setUser_img_url(defaultUserImage);

            userMapper.update(token_value, temp);

            temp.setUser_img_url(defaultUrl + temp.getUser_img_url());
            temp.setAuth(true);

            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER, temp);
        } catch (IOException e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }

    }


    /**
     * 회원 조회
     *
     * **/
    public DefaultRes findUser(final String user_id,final String my_id){

        try{

            UserInfo user = userMapper.findUser(user_id,my_id);
            if (user != null) {
                if(user.getUser_img_url() !=null) {
                    user.setUser_img_url(defaultUrl + user.getUser_img_url());
                }else{
                    user.setUser_img_url(null);
                }
                return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
            }
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        }catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }


    /**
     * 회원 탈퇴
     *
     * **/
    @Transactional
    public DefaultRes deleteById(final String user_id){
        final User user = userMapper.findById(user_id);
        if(user == null){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_USER);
        }
        try{
            userMapper.deleteById(user_id);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.DELETE_USER);
        }catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }



    /**
     * 인기 있는 유저 리스트 조회 (팔로워 순)
     *
     * @param num       몇 명까지
     * @return DefaultRes
     *
     **/
    @Override
    public DefaultRes<List<User>> findBestUser(final int num){

        List<User> bestUserList = userMapper.findBestUser(num);

        if (bestUserList == null){
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.NOT_FOUND_USER);
        }else {
            for(User u : bestUserList)
                u.setUser_img_url(defaultUrl + u.getUser_img_url());
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, bestUserList);

    }

}
