package sopt.org.moca.service.impl;

import org.springframework.stereotype.Service;
import sopt.org.moca.dto.HotPlace;
import sopt.org.moca.mapper.HotPlaceMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.HotPlaceService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;
@Service
public class HotPlaceServiceImpl implements HotPlaceService {

    private final HotPlaceMapper hotPlaceMapper;

    public HotPlaceServiceImpl(final HotPlaceMapper hotPlaceMapper) {
        this.hotPlaceMapper = hotPlaceMapper;
    }

    @Override
    public DefaultRes<List<HotPlace>> findAllHotPlace() {

        //token 인증 필요함
        //코드 추가 필요






        List<HotPlace> hotPlaceList = hotPlaceMapper.findAllHotPlace();
        if(hotPlaceList != null)
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_HOT_PLACE,hotPlaceList);
        else
         return DefaultRes.res(StatusCode.DB_ERROR,ResponseMessage.DB_ERROR);
    }
}
