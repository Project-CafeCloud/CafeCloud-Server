package sopt.org.moca.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

@Service
@Slf4j
public class UserService {
    private final UserMapper userMapper;

    /**
     * 생성자 의존성 주입
     *
     * @param userMapper*
     **/

    public UserService(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 회원 가입
     * @param userSignUpReq 회원 데이터
     *
     */
    @Transactional
    public DefaultRes save(UserSignUpReq userSignUpReq){
        try{
            userMapper.save(userSignUpReq);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_USER);
        } catch (Exception e){
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

}
