//package sopt.org.moca.service.impl;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import sopt.org.moca.dto.ReviewAlarm;
//import sopt.org.moca.model.DefaultRes;
//import sopt.org.moca.service.PushService;
//
//
//
//@Service
//public class PushServiceImpl implements PushService {
//
//    private final AndroidPushNotificationsService androidPushNotificationsService;
//    private final HeaderRequestInterceptor headerRequestInterceptor;
//
//    public PushServiceImpl(final AndroidPushNotificationsService androidPushNotificationsService,final HeaderRequestInterceptor headerRequestInterceptor){
//        this.androidPushNotificationsService = androidPushNotificationsService;
//        this.headerRequestInterceptor = headerRequestInterceptor;
//    }
//
//    public ResponseEntity<ReviewAlarm> send(final DefaultRes likeDefaultRes) {
//        ReviewAlarm reviewAlarm = new ReviewAlarm();
//        reviewAlarm.getAlarm_contents();
//    }
//}
