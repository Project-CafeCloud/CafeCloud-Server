package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Slf4j
@Service
public class AndroidPushNotificationsService  {
    private static final String FIREBASE_SERVER_KEY = "AAAASe0zFwM:APA91bHt1hwGdEZ2xf2WdRsu1No3dtPc6MGQo9ugcXIr4cM1ysmMBE3EdqtuK4PNRGEQuQpLvEYsm2Fn1LnBZ6an2dhxaADTtgDYxi00G-7gi6pAv1KgmMO4JgbMXzxwfw1zOQuT8owO";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

  //  public static String sendPushNotification(String deviceToken, String Message, String Message1) throws IOException {
//
//            String result = "";
//            URL url = new URL(FIREBASE_API_URL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setUseCaches(false);
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Authorization", "key=" + FIREBASE_SERVER_KEY);
//            conn.setRequestProperty("Content-Type", "application/json");
//
//            JSONObject json = new JSONObject();
//
//            try {
//
//                json.put("to", deviceToken.trim());
//
//                JSONObject data = new JSONObject();
//                data.put("alarm_user", Message);
//                data.put("alarm_contnets", Message1);
//                json.put("data", data);
                //DefaultRes <ReviewAlarm> alarmDefaultRes
//
//                JSON info = new JSONString();
//
//                        info ="status : 200"; // Notification title
//                        info.put("message", "푸시알람 성공"); // Notification
//                        info.put(Message, Message1); // body
//                        json.put("push_alarm", info);

                    //}
             //   }
//
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//
//            try {
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());//문자 스트림에서 바이트 스트림
//                wr.write(json.toString());
//                wr.flush();
//
//                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()))); //문자 입력 스트림으로부터 문자를 읽어 드림
//
//                String output;
//
//                while ((output = br.readLine()) != null) {
//                    System.out.println(output);
//                }
//                result = "succcess";
//            } catch (Exception e) {
//                e.printStackTrace();
//                result = "failure";
//            }
//           // System.out.println("푸시알람 성공");
//            log.info(result);
//            return result;
//
//        }
//    }



    public ResponseEntity makeJson(String Message, String Message1){
        Runnable r = new RunExecute(); // 실제 구현한 Runnable 인터페이스
        Thread threadList = new Thread(); // 쓰레드들을 담을 객체

        Thread test = new Thread(r);
        test.start();
//        threadList.add(test);
//        threadList.join();

        JSONArray json = new JSONArray();
        JSONObject data = new JSONObject();
                data.put("alarm_user", Message);
                data.put("alarm_contnets", Message1);
                json.put(data);

        List<String> tokenList = new ArrayList<String>();
        JSONArray array = new JSONArray();
        array.put("qwertyuio");
        //tokenList.add(); 수신자 값 삽입!!
        for(int i=0; i<tokenList.size();i++){
            array.put(tokenList.get(i));
        }
        data.put("registration_ids",array);

        HttpEntity<String> request = new HttpEntity<>(data.toString());


        CompletableFuture<String> pushNotification = send(request);


        try{
            String firebaseResponse = pushNotification.get();

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("푸시알람 실패",HttpStatus.BAD_REQUEST);
    }

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY*/

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));

        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }

}

class RunExecute implements Runnable {

    private int index = 0;

    @Override
    public void run() {
        Random r = new Random(System.currentTimeMillis());

        long s = r.nextInt(3000);
        try{

            Thread.sleep(s);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        addIndex();
    }

    synchronized void addIndex(){
        index++;
        System.out.println("current index value: " + index);
    }
}