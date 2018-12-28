package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.User;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.LoginReq;
import sopt.org.moca.service.AuthService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.auth.JwtUtils;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    public AuthServiceImpl(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     *
     * 로그인
     * **/
    @Override
    public DefaultRes<JwtUtils.TokenRes> login(final LoginReq loginReq) {
        final User user = userMapper.findByIdAndPassword(loginReq.getUser_id(), loginReq.getUser_password());
        if (user != null) {
            final JwtUtils.TokenRes tokenDto = new JwtUtils.TokenRes(JwtUtils.create(user.getUser_id()));
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}
