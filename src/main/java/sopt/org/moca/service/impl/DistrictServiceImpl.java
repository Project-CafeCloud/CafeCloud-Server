package sopt.org.moca.service.impl;

import org.springframework.stereotype.Service;
import sopt.org.moca.dto.District;
import sopt.org.moca.mapper.DistrictMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.service.DistrictService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    private final DistrictMapper districtMapper;

    public DistrictServiceImpl(final DistrictMapper districtMapper) {
        this.districtMapper = districtMapper;
    }


    @Override
    public DefaultRes<List<District>> findDistrictList() {
        List<District> districtList =null;
        districtList = districtMapper.findDistrictList();
        if (districtList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.FAIL_DISTRICT);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_DISTRICT, districtList);
    }
}
