package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.mapper.PlusMapper;
import sopt.org.moca.mapper.UserMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.PlusService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;
import sopt.org.moca.utils.auth.JwtUtils;

import java.util.List;

@Slf4j
@Service
public class PlusServiceImpl implements PlusService {

    private final PlusMapper plusMapper;
    private final CafeMapper cafeMapper;
    private final UserMapper userMapper;
    private static final String HEADER = "Authorization";

    @Value("${cloud.aws.s3.bucket.url}")
    private String defaultUrl;

    public PlusServiceImpl(final PlusMapper plusMapper, final CafeMapper cafeMapper,final UserMapper userMapper) {

        this.plusMapper = plusMapper;
        this.cafeMapper = cafeMapper;
        this.userMapper = userMapper;
    }

    /**
     *
     * PLUS 주제 리스트 조회
     * **/

    @Override
    public DefaultRes<List<PlusSubject>> findPlusSubjectList(final int length){
        List<PlusSubject> plusSubjectList;

        if(length<0){
            plusSubjectList = plusMapper.findPlusSubjectAll();
        }
        else{
            plusSubjectList = plusMapper.findPlusSubject(length);
        }

        for(PlusSubject p : plusSubjectList){
            User user = userMapper.findById(p.getEditor_id());
            p.setEditor_name(user.getUser_name());
            p.setEditor_img_url(defaultUrl + user.getUser_img_url());
        }

        if(plusSubjectList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PLUS_SUBJECT_LIST);
        }
        return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_PLUS_SUBJECT_LIST,plusSubjectList);

    }

    /**
     *
     * PLUS 주제 상세 조회 1개
     * **/
    @Override
    public DefaultRes<PlusSubject> findPlusSubject(final int plus_subject_id){
      PlusSubject plusSubject = plusMapper.findSubject(plus_subject_id);
        if(plusSubject == null)
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_PLUS_SUBJECT);
        else {
            User user = userMapper.findById(plusSubject.getEditor_id());
            plusSubject.setEditor_name(user.getUser_name());
            plusSubject.setEditor_img_url(defaultUrl + user.getUser_img_url());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PLUS_SUBJECT, plusSubject);
        }
    }


    /**
     *
     * PLUS 해당 주제의 컨텐츠 리스트 조회
     * **/

    @Override
    public DefaultRes<List<PlusContents>> findContentsList(final int plus_subject_id, final String user_id){
        List<PlusContents> plusContentsList = plusMapper.findContents(plus_subject_id);

        if(plusContentsList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_PLUS_CONTENT_LIST);

        }else {
            for(PlusContents p : plusContentsList ) {
                CafeInfo cafeInfo = cafeMapper.findCafeInfo(p.getCafe_id(), user_id);
                p.setAddress_district_name("서울 " + cafeInfo.getAddress_district_name());
                p.setCafe_name(cafeInfo.getCafe_name());
                p.setContentImages(plusMapper.findPlusContentsImg(p.getPlus_contents_id()));
            }

            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PLUS_CONTENT_LIST, plusContentsList);
        }
    }

}
