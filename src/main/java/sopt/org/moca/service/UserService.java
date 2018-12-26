package sopt.org.moca.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.mapper.UserMapper;

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

}
