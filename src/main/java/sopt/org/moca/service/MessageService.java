package sopt.org.moca.service;

import sopt.org.moca.dto.Message;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MessageReq;

import java.util.List;

public interface MessageService {

    DefaultRes save(final MessageReq messageReq);
//    DefaultRes<List<Message>> findMessageList(final String user_id);

}
