//package sopt.org.moca.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import sopt.org.moca.service.AndroidPushNotificationsService;
//
//import java.util.ArrayList;
//import java.util.concurrent.CompletableFuture;
//
//
//@Slf4j
//@Service
//public class AndroidPushNotificationsServiceImpl implements AndroidPushNotificationsService {
//    private static final String FIREBASE_SERVER_KEY = "AAAASe0zFwM:APA91bHt1hwGdEZ2xf2WdRsu1No3dtPc6MGQo9ugcXIr4cM1ysmMBE3EdqtuK4PNRGEQuQpLvEYsm2Fn1LnBZ6an2dhxaADTtgDYxi00G-7gi6pAv1KgmMO4JgbMXzxwfw1zOQuT8owO";
//    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
//
//    @Async
//    public CompletableFuture<String> send(HttpEntity<String> entity) {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        /**
//         https://fcm.googleapis.com/fcm/send
//         Content-Type:application/json
//         Authorization:key=FIREBASE_SERVER_KEY*/
//
//        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
//        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
//        restTemplate.setInterceptors(interceptors);
//
//        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
//
//        return CompletableFuture.completedFuture(firebaseResponse);
//    }
//
//}
