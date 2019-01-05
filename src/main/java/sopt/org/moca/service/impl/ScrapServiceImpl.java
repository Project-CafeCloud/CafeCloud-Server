package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.mapper.ScrapMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ScrapReq;
import sopt.org.moca.service.ScrapService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

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
            if(!checkScrap(user_id,cafe_id)){
                scrapMapper.save(scrapReq);
            }
            return DefaultRes.res(StatusCode.OK,ResponseMessage.SCRAP_SUCCESS);
        }catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    @Override
    public boolean checkScrap(final String user_id, final int cafe_id){
        return scrapMapper.findByID(user_id,cafe_id) != null;
    }
}
