package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.PlusContentImg;
import sopt.org.moca.dto.PlusContents;
import sopt.org.moca.dto.PlusSubject;
import sopt.org.moca.mapper.PlusMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.PlusService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

@Slf4j
@Service
public class PlusServiceImpl implements PlusService {

    private final PlusMapper plusMapper;

    public PlusServiceImpl(final PlusMapper plusMapper) {
        this.plusMapper =plusMapper;
    }

    /**
     *
     * PLUS 주제 조회
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
        if(plusSubjectList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PLUS_SUBJECT_LIST);
        }
        return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_PLUS_SUBJECT_LIST,plusSubjectList);

    }

    @Override
    public DefaultRes<PlusSubject> findPlusSubject(final int plus_subject_id){
      PlusSubject plusSubject = plusMapper.findSubject(plus_subject_id);

      if(plusSubject == null)
          return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_PLUS_SUBJECT);
      else
          return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PLUS_SUBJECT,plusSubject);
    }

    /**
     *
     * PLUS 이미지 조회
     * **/
    @Override
    public DefaultRes<List <PlusContentImg>> findPlusImg(final int plus_contents_id){
        List<PlusContentImg> plusContentImgList = plusMapper.findPlusContentImg(plus_contents_id);

        if(plusContentImgList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PLUS_CONTENT_IMG_LIST);
        }else{
            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_PLUS_CONTENT_IMG_LIST,plusContentImgList);
        }

    }

    /**
     *
     * PLUS 디테일 뷰 조회
     * **/

    @Override
    public DefaultRes<List<PlusContents>> findContentList(final int plus_subject_id){
        List<PlusContents> plusContentsList = plusMapper.findContent(plus_subject_id);

        if(plusContentsList.isEmpty()){
            return DefaultRes.res(StatusCode.NOT_FOUND,ResponseMessage.NOT_FOUND_PLUS_CONTENT_LIST);

        }else
            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_PLUS_CONTENT_LIST,plusContentsList);
    }

}
