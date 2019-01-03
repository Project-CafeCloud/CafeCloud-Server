package sopt.org.moca.service.impl;


import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.model.CafeSimpleRef;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.CafeService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CafeServiceImpl implements CafeService {
   final private CafeMapper cafeMapper;


    public CafeServiceImpl(final CafeMapper cafeMapper) {
        this.cafeMapper = cafeMapper;
    }

    @Override
    public DefaultRes<List<EvaluatedCafeSimple>> findEvaluatedCafeSimpleList(final int length) {
        List<EvaluatedCafeSimple> evaluatedCafeSimpleList;

        if(length <0)
        {

            evaluatedCafeSimpleList = cafeMapper.findAllEvaluatedCafe();
        }
        else
        {
          evaluatedCafeSimpleList = cafeMapper.findPopularEvaluatedCafe(length);

        }
        if(evaluatedCafeSimpleList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_EVALUATED_CAFE_LIST);


        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_EVALUATED_CAFE_LIST ,evaluatedCafeSimpleList);

    }

    @Override
    public DefaultRes<EvaluatedCafeInfo> findEvaluatedCafeInfo(final int cafe_id) {
        EvaluatedCafeInfo evaluatedCafeInfo = null;
        evaluatedCafeInfo = cafeMapper.findEvaluatedCafeInfo(cafe_id);
        if(evaluatedCafeInfo == null)
        {

            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.FAIL_EVALUATED_CAFE_INFO);
        }
        else
        {

            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_EVALUATED_CAFE_INFO,evaluatedCafeInfo);
        }
    }

    @Override
    public DefaultRes<List<EvaluatedCafeImg>> findEvaluatedCafeImg(final int cafe_id) {
        List<EvaluatedCafeImg> evaluatedCafeImgList;
        evaluatedCafeImgList =  cafeMapper.findEvaluatedCafeImg(cafe_id);
        if(evaluatedCafeImgList.isEmpty())
        {
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.FAIL_EVALUATED_CAFE_IMG);
        }
        else
        {
            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_EVALUATED_CAFE_IMG,evaluatedCafeImgList);

        }
    }

    @Override
    public DefaultRes<List<Evaluation>> findEvaluationList(final int cafe_id) {
        List <Evaluation> evaluationList;
        evaluationList = cafeMapper.findAllEvaluation(cafe_id);
        if(evaluationList.isEmpty())
        {
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.FAIL_EVALUATION_LIST);
        }
        else
        {
            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_EVALUATION_LIST,evaluationList);

        }
    }

    @Override
    public DefaultRes<Evaluation_detail> findEvaluationDetail(int cafe_id, int barista_id) {
        Evaluation_detail evaluationDetail = null;
        evaluationDetail = cafeMapper.findBaristaEvaluation(cafe_id,barista_id);
        if(evaluationDetail == null)
        {

            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.FAIL_EVALUATION_DETAIL);
        }
        else
        {

            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_EVALUATION_DETAIL,evaluationDetail);
        }
    }

    @Override
    public DefaultRes<List<CafeImg>> findCafeImgList(int cafe_id) {
        List<CafeImg> cafeImgList = null;

        cafeImgList = cafeMapper.findCafeImgList(cafe_id);
        if (cafeImgList.isEmpty()) {

            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_CAFE_IMG_LIST);
        } else {

            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CAFE_IMG_LIST, cafeImgList);
        }
    }
    @Override
    public DefaultRes<CafeInfo> findCafeInfo(int cafe_id) {

        CafeInfo cafeInfo = null;
        cafeInfo = cafeMapper.findCafeInfo(cafe_id);
        if (cafeInfo == null) {

            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_CAFE_INFO);
        } else {

            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CAFE_INFO, cafeInfo);
        }
    }

    @Override
    public DefaultRes<List<CafeSignitureMenu>> findCafeSignitureMenuList(int cafe_id) {
        List<CafeSignitureMenu> cafeSignitureMenuList = null;
        cafeSignitureMenuList =  cafeMapper.findCafeSigitureMenuList(cafe_id);
        if (cafeSignitureMenuList.isEmpty()) {

            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_CAFE_SIGNITURE_MENU);
        } else {

            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CAFE_SIGNITURE_MENU, cafeSignitureMenuList);
        }
    }

    @Override
    public DefaultRes<List<CafeSimpleRef>> findCafeSimpleList(int address_district_id) {
     List<CafeSimple> cafeSimpleList = null;
     cafeSimpleList = cafeMapper.findCafeInfoList(address_district_id);
     log.info("123");
        if (cafeSimpleList.isEmpty()) {

            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_CAFE_SIGNITURE_MENU);
        } else {

            List<CafeSimpleRef> cafeSimpleRefList = new ArrayList<>();
            for(CafeSimple cafeSimple : cafeSimpleList)
            {
                BufferedReader in = null;
                try { URL obj = new URL("https://dapi.kakao.com/v2/local/search/keyword.json?"+"query=숭실&category_group_code=SW8&y="+cafeSimple.getCafe_latitude()+"&x="+cafeSimple.getCafe_longitude()+"&radius=1000"); // 호출할 url
                    HttpURLConnection con = (HttpURLConnection)obj.openConnection();
                    log.info(""+obj);
                    con.setRequestProperty("Authorization","KakaoAK facb7875bb49f6a31243407bd0baab17");
                    con.setRequestProperty("X-Requested-With", "curl");

                    con.setRequestMethod("GET");
                    in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String line; while((line = in.readLine()) != null) { // response를 차례대로
                        log.info(line); } }
                catch(Exception e) { e.printStackTrace(); }
                finally { if(in != null) try { in.close(); }
                catch(Exception e) { e.printStackTrace(); } }



//                in = null;
//                log.info(cafeSimple.getCafe_latitude() +"    "+cafeSimple.getCafe_longitude());
//                try { URL obj = new URL("http://swopenAPI.seoul.go.kr/api/subway/54696e7764726f75363874516e756c/json/nearBy/0/1" +
//                        "/"+196171.402081 +"/"+443911.205095); // 호출할 url
//                HttpURLConnection con = (HttpURLConnection)obj.openConnection();
//                con.setRequestMethod("GET"); in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//                String line; while((line = in.readLine()) != null) { // response를 차례대로
//                        log.info(line); } }
//                    catch(Exception e) { e.printStackTrace(); }
//                    finally { if(in != null) try { in.close(); }
//                    catch(Exception e) { e.printStackTrace(); } }



            }
            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CAFE_SIGNITURE_MENU, cafeSimpleRefList);
        }

    }



}
