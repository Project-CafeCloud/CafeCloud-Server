package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
// 구글 FCM서버에 데이터를 전송하는 역할
public class AndroidPushNotificationsService  {
    private static final String FIREBASE_SERVER_KEY = "AAAASe0zFwM:APA91bHt1hwGdEZ2xf2WdRsu1No3dtPc6MGQo9ugcXIr4cM1ysmMBE3EdqtuK4PNRGEQuQpLvEYsm2Fn1LnBZ6an2dhxaADTtgDYxi00G-7gi6pAv1KgmMO4JgbMXzxwfw1zOQuT8owO";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    public void makeJson(String Message, String Message1){


       // JSONArray json = new JSONArray();
        JSONObject data = new JSONObject();
                data.put("alarm_user", Message);
                data.put("alarm_contnets", Message1);
              //  json.put(data);

        String tokenVal = "aaaaa";
        JSONArray array = new JSONArray();

        array.put(tokenVal);

        data.put("to",array);

        ///////////////////제이슨으로 만들기

        HttpEntity<String> request = new HttpEntity<>(data.toString());
        final CompletableFuture<String> pushNotification = send(request);
        log.info(String.valueOf(pushNotification));

    }

    //HTTP 통신을 가능하게 해주기 위해서 Request 객체를 만들어주어 설정
    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {
        RestTemplate restTemplate = new RestTemplate();
        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY**/

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));

        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

}


//@EnableAsync
//class RunExecute implements Runnable {
//
//    private volatile CompletableFuture<String> pushNotification;
//
//    public RunExecute(CompletableFuture<String> pushNotification){
//        this.pushNotification = pushNotification;
//    }
//    @Override
//    @Async
//    public void run() {
//        try{
//            String firebaseResponse = pushNotification.get();
//            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>("푸시알람 실패",HttpStatus.BAD_REQUEST);
//    }
//}