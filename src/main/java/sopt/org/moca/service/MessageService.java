package sopt.org.moca.service;

import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MessageReq;

public interface MessageService {

    DefaultRes save(final MessageReq messageReq);
}
