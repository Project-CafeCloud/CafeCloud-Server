package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.org.moca.model.MessageReq;
import sopt.org.moca.service.MessageService;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/{receiver_id}")
    public ResponseEntity save(
            final HttpServletRequest httpServletRequest,
            MessageReq messageReq,
            @PathVariable final String receiver_id
    ){
        try{
            messageReq.setReceiver_id(receiver_id);
            messageReq.setSender_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());
            return new ResponseEntity<>(messageService.save(messageReq),HttpStatus.OK);
        }catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
