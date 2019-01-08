//package sopt.org.moca.api;
//
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import sopt.org.moca.service.AndroidPushNotificationsService;
//
//@Slf4j
//@RestController
//@RequestMapping("/pushalarm")
//public class AlarmController {
//    private final AndroidPushNotificationsService androidPushNotificationsService;
//
//    public AlarmController(final AndroidPushNotificationsService androidPushNotificationsService) {
//        this.androidPushNotificationsService = androidPushNotificationsService;
//    }
//
//    @GetMapping("")
//    public ResponseEntity<String> send() {
//        JSONObject notification = new JSONObject();
//        JSONArray array = new JSONArray(); //
//
//        notification.put("registration_ids",array);
//
//    }
//}
