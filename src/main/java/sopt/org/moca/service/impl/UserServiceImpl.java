package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.User;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.service.UserService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.io.IOException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final FileUploadService fileUploadService;

    /**
     * 생성자 의존성 주입
     *
     * @param userMapper
     */
    public UserServiceImpl(final UserMapper userMapper, final FileUploadService fileUploadService) {
        this.userMapper = userMapper;
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
                        user.setUser_img_url(fileUploadService.upload(userSignUpReq.getUser_img()));
                    }
                    userMapper.save(userSignUpReq);
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
        if (user != null) return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }

    /**
     *
     * Mypage USER 수정
     *
     * **/

    @Transactional
    @Override
    public DefaultRes<User> updateUser(final String user_id,final UserSignUpReq userSignUpReq){
        User temp= userMapper.findById(user_id);
        if(temp == null){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_USER);
        }
        try{
            temp.update(userSignUpReq);
            if(userSignUpReq.getUser_img() != null) temp.setUser_img_url(fileUploadService.upload(userSignUpReq.getUser_img()));
            userMapper.update(temp);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER, temp);
        } catch (IOException e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }

    }

}
