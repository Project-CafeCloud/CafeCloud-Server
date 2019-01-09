//package sopt.org.moca.api;
//
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import sopt.org.moca.model.DefaultRes;
//import sopt.org.moca.service.impl.AndroidPushNotificationsService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//@Slf4j
//@RestController
//@RequestMapping("/pushalarm")
//public class AlarmController {
//
//    private final AndroidPushNotificationsService androidPushNotificationsService;
//
//    public AlarmController(final AndroidPushNotificationsService androidPushNotificationsService) {
//        this.androidPushNotificationsService = androidPushNotificationsService;
//    }
//
//    @GetMapping("")
//    public ResponseEntity send(final JSONObject jsonObject) {
//
//        List<String> tokenList = new ArrayList<String>();
//        JSONArray array = new JSONArray();
//
//        //tokenList.add(); 수신자 값 삽입!!
//        for(int i=0; i<tokenList.size();i++){
//            array.put(tokenList.get(i));
//        }
//        jsonObject.put("registration_ids",array);
//
//        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString());
//
//        CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
//        CompletableFuture.allOf(pushNotification).join();
//
//        try{
//            String firebaseResponse = pushNotification.get();
//
//            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<>("푸시알람 실패",HttpStatus.BAD_REQUEST);
//    }
//
//}
