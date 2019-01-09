package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.*;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReportReq;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.ReportService;
import sopt.org.moca.service.ReviewService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.Time;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;



    /**
     * 생성자 의존성 주입
     *
     * @param reportMapper
     */

    public ReportServiceImpl(final ReportMapper reportMapper) {

        this.reportMapper = reportMapper;
    }



    /**
     * 리뷰 작성
     *
     * @param reportReq      신고 데이터
     * @param flag           플래그 (유저/리뷰/댓글)
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes save(final ReportReq reportReq, final int flag) {
        if (reportReq.checkProperties()) {
            try {

                reportMapper.save(reportReq, flag);

                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_REPORT);
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_REPORT);
    }


}