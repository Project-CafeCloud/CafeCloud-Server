package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.Message;
import sopt.org.moca.dto.MyMessages;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MessageReq;
import sopt.org.moca.service.MessageService;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private static final String HEADER = "Authorization";
    private final MessageService messageService;

    public MessageController(final MessageService messageService){
        this.messageService = messageService;
    }

    /**
     *
     * 메세지 보내기
     * **/

    @PostMapping("")
    public ResponseEntity save(
            final HttpServletRequest httpServletRequest,
            MessageReq messageReq,
            @RequestPart(value = "profile", required = false) final MultipartFile message_img
    ){
        try{
            messageReq.setSender_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            return new ResponseEntity<>(messageService.save(messageReq),HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     *
     * 상대방과 주고받은 쪽지함 조회
     * **/
    @GetMapping("/{user_id}") //상대방id
    public ResponseEntity getMessageList(
            final HttpServletRequest httpServletRequest,
            @PathVariable final String user_id
    ){
        try{
            String my_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            DefaultRes<List<Message>> messageDefaultRes ;
            return new ResponseEntity<>(messageService.findMessageList(my_id,user_id),HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 내 쪽지함 조회
     * **/
    @GetMapping("")
    public ResponseEntity getMyMessageList(
            final HttpServletRequest httpServletRequest
    ) {
        try {
            String my_id = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id();
            DefaultRes<List<MyMessages>> myMessagesDefaultRes;
            return new ResponseEntity<>(messageService.findMyMessageList(my_id),HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
