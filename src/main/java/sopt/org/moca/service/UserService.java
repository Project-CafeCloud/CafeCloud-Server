package sopt.org.moca.service;

import sopt.org.moca.dto.User;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;

public interface UserService {


    DefaultRes save(final UserSignUpReq userSignUpReq);
    DefaultRes<User> findById(final String user_id);
}
