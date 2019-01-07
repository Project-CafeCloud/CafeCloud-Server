package sopt.org.moca.service;

import sopt.org.moca.dto.Message;
import sopt.org.moca.dto.MessageBox;
import sopt.org.moca.dto.MyMessages;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MessageReq;

import java.util.List;

public interface MessageService {

    DefaultRes save(final MessageReq messageReq);
    DefaultRes<List<MessageBox>> findMessageList(final String my_id, final String user_id);
    DefaultRes<List<MyMessages>> findMyMessageList(final String my_id);

}
