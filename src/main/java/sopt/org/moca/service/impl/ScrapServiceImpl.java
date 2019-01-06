package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.dto.CafeInfo;
import sopt.org.moca.dto.Scrap;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.mapper.ScrapMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ScrapReq;
import sopt.org.moca.service.ScrapService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

@Slf4j
@Service
public class ScrapServiceImpl implements ScrapService {

    private ScrapMapper scrapMapper;
    private CafeMapper cafeMapper;

    public ScrapServiceImpl(final ScrapMapper scrapMapper,final  CafeMapper cafeMapper){
        this.scrapMapper = scrapMapper;
        this.cafeMapper = cafeMapper;
    }

    /**
     *
     * 스크랩하기
     * **/
    @Override
    @Transactional
    public DefaultRes scrap(final ScrapReq scrapReq){

        final String user_id = scrapReq.getUser_id();
        final int cafe_id = scrapReq.getCafe_id();
        log.info(String.valueOf(scrapReq));

        try{
            if(!checkScrap(user_id,cafe_id)) {
                scrapMapper.save(scrapReq);
            }
            return DefaultRes.res(StatusCode.OK,ResponseMessage.SCRAP_SUCCESS);
        }catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     *
     * 스크랩 취소
     * **/
    @Transactional
    public DefaultRes deleteByCafeId(final int cafe_id){

        try{
            scrapMapper.deleteByCafeId(cafe_id);
            return DefaultRes.res(StatusCode.OK,ResponseMessage.SCRAP_DELETE_SUCCESS);
        }catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     *
     * 스크랩 조회
     * **/
    @Override
    public DefaultRes<List<Scrap>> findById(final String user_id){
        List<Scrap> scrapList = scrapMapper.findScrapList(user_id);

        if(scrapList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_SCRAP_LIST);
        }
        else{

            for(Scrap s : scrapList){
                CafeInfo cafeInfo = cafeMapper.findCafeInfo(s.getCafe_id());
                s.setCafe_img_url(cafeMapper.findCafeImgList(s.getCafe_id()));
                s.setCafe_name(cafeInfo.getCafe_name());
                s.setAddress_district_name("서울 "+ cafeInfo.getAddress_district_name()+" " + cafeInfo.getCafe_address_detail());
                s.setCafe_rating_avg(cafeInfo.getCafe_rating_avg());
            }
            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_SCRAP_LIST,scrapList);
        }
    }

    @Override
    public boolean checkScrap(final String user_id,final int cafe_id){
        return scrapMapper.findByID(user_id,cafe_id) != null;
    }
}
