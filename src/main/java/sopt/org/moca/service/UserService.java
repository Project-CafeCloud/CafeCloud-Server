package sopt.org.moca.service;

import sopt.org.moca.dto.User;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.UserSignUpReq;

import java.util.List;


public interface UserService {

    DefaultRes save(final UserSignUpReq userSignUpReq);
    DefaultRes<User> findById(final String user_id);
    DefaultRes updateUser(final String token_value ,final UserSignUpReq userSignUpReq);
    DefaultRes deleteById(final String user_id);
    DefaultRes<List<User>> findBestUser(final int num);
}
