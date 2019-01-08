package sopt.org.moca.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.CafeInfo;
import sopt.org.moca.dto.Map;
import sopt.org.moca.mapper.CafeMapper;
import sopt.org.moca.mapper.MapMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MapReq;
import sopt.org.moca.service.MapService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

@Service
@Slf4j
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;
    private final CafeMapper cafeMapper;

    public MapServiceImpl(final MapMapper mapMapper,final CafeMapper cafeMapper) {

        this.mapMapper = mapMapper;
        this.cafeMapper = cafeMapper;
    }


    @Override
    public DefaultRes<List<Map>> GetNearByCafe(final MapReq mapReq,final String user_id){

        //log.info(mapReq.getIs_cafe_detail()+"");
        List<Map> mapList = mapMapper.findNearbyCafe(mapReq);

        if(mapReq.getIs_cafe_detail() == 1) {
            for (Map m : mapList) {
                if (m.getCafe_id() == mapReq.getCafe_id())
                {
                    mapList.remove(m);
                    break;
                }

            }
        }

        log.info(String.valueOf(mapList));
        if(mapList.isEmpty())

            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_NEAR_BY_CAFE);

        else
        {
            for(Map m : mapList){
                CafeInfo cafeInfo = cafeMapper.findCafeInfo(m.getCafe_id(),user_id);
                m.setCafe_rating_avg(cafeInfo.getCafe_rating_avg());
                m.setAddress_district_name("서울 "+cafeInfo.getAddress_district_name());
                m.setCafe_img_url(cafeInfo.getCafe_menu_img_url());

            }
            return DefaultRes.res(StatusCode.OK,ResponseMessage.READ_NEAR_BY_CAFE,mapList);
        }

    }


}
