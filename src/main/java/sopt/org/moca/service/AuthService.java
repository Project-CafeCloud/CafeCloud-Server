package sopt.org.moca.service;

import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.LoginReq;


public interface AuthService {
    DefaultRes login(final LoginReq loginReq);
}
