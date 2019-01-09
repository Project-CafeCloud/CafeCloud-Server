package sopt.org.moca.service;

import org.springframework.http.ResponseEntity;
import sopt.org.moca.dto.ReviewAlarm;
import sopt.org.moca.model.DefaultRes;

public interface PushService {
    ResponseEntity<ReviewAlarm> send(DefaultRes likeDefaultRes);
}
