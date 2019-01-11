package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.Message;
import sopt.org.moca.dto.MessageBox;
import sopt.org.moca.dto.MyMessages;
import sopt.org.moca.dto.User;
import sopt.org.moca.mapper.MessageMapper;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MessageReq;
import sopt.org.moca.service.MessageService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.Time;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    private MessageMapper messageMapper;
    private UserMapper userMapper;

    private final FileUploadService fileUploadService;


    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;


    public MessageServiceImpl(final MessageMapper messageMapper,
                              final FileUploadService fileUploadService,
                              final UserMapper userMapper) {
        this.messageMapper = messageMapper;
        this.fileUploadService = fileUploadService;
        this.userMapper = userMapper;
    }

    /**
     *
     * 쪽지 보내기
     *
     * **/
    @Transactional
    @Override
    public DefaultRes save(MessageReq messageReq) {
        log.info(messageReq.toString());
        if (messageReq.checkElement()) {
            try {
                if(userMapper.findById(messageReq.getReceiver_id()) ==null){
                    return DefaultRes.res(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_USER);
                }
                if(userMapper.findById(messageReq.getSender_id()) ==null){
                    return DefaultRes.res(StatusCode.BAD_REQUEST,ResponseMessage.NOT_FOUND_USER);
                }
                if(messageReq.getMessage_img() != null)
                    messageReq.setMessage_img_url(fileUploadService.upload(messageReq.getMessage_img(), "message"));
                messageReq.setMessage_send_date(messageReq.getMessage_send_date());
                messageMapper.save(messageReq);
                return DefaultRes.res(StatusCode.CREATED,ResponseMessage.MESSAGE_SAVE_SUCCESS);
            } catch(Exception e){
                //Rollback
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                log.error(e.getMessage());
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_TO_SAVE_MESSAGE);
    }
    /**
     *
     * 상대방과 주고받은 쪽지함 조회
     *
     * **/
    @Override
    public DefaultRes<List<MessageBox>> findMessageList(final String my_id , final String user_id){
        List<MessageBox> messageBoxList = messageMapper.findByUserId(my_id, user_id);
        log.info(String.valueOf(messageBoxList));
        if(messageBoxList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_MESSAGELIST);
        }else{
            for(MessageBox m : messageBoxList){
                m.setReceiver_profile_img(defaultUrl + m.getReceiver_profile_img());
                m.setSender_profile_img(defaultUrl + m.getSender_profile_img());
            }
            return  DefaultRes.res(StatusCode.OK,ResponseMessage.READ_MESSAGE,messageBoxList);
        }
    }

    /**
     * 내 쪽지함 조회
     * **/
    @Override
    public DefaultRes<List<MyMessages>> findMyMessageList(final String my_id){
        List<MyMessages> myMessagesList = messageMapper.findMyMessages(my_id);
        if(myMessagesList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_MESSAGELIST);
        }else{
            for(MyMessages m : myMessagesList){
                m.setPartner_profile_img(defaultUrl + m.getPartner_profile_img());
            }
            return  DefaultRes.res(StatusCode.OK,ResponseMessage.READ_MESSAGE,myMessagesList);
        }
    }
}
