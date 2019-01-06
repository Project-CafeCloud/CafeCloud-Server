package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.Message;
import sopt.org.moca.mapper.MessageMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MessageReq;
import sopt.org.moca.service.MessageService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.io.IOException;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private MessageMapper messageMapper;
    private final FileUploadService fileUploadService;

    public MessageServiceImpl(final MessageMapper messageMapper,final FileUploadService fileUploadService) {
        this.messageMapper = messageMapper;
        this.fileUploadService = fileUploadService;
    }

    /**
     *
     * 쪽지 보내기
     *
     * **/
    @Override
    public DefaultRes save(MessageReq messageReq) {

        if (messageReq.checkElement()) {
            try {
                if(messageReq.getMessage_img() != null)
                    messageReq.setMessage_img_url(fileUploadService.upload(messageReq.getMessage_img()));
                messageReq.setMessage_send_date(messageReq.getMessage_send_date());
                messageMapper.save(messageReq);
            } catch(Exception e){
                //Rollback
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error(e.getMessage());
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_TO_SAVE_MESSAGE);
    }
}
