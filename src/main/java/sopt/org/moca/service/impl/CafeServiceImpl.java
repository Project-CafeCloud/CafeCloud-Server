package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.model.CafeSimpleRef;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.CafeService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

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




    /**
     * 해당 카페에 대한 인기 리뷰 조회
     *
     * @param flag          0: 스크랩순 / 1: 리뷰개수순
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<CafeBest>>findBestCafeSimpleList(final int flag){

        List<CafeBest> bestCafeSimpleList = null;

        // flag 0: 스크랩 순
        // flag 1: 리뷰 개수 순
        if(flag == 0){
            bestCafeSimpleList = cafeMapper.findBestCafeOrderByScrapCnt();
        } else if (flag == 1) {
            bestCafeSimpleList = cafeMapper.findBestCafeOrderByReviewCnt();
        }

        if(bestCafeSimpleList == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_BEST_CAFE);

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_BEST_CAFE_LIST, bestCafeSimpleList);

    }

}
