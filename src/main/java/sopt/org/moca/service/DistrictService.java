package sopt.org.moca.service;


import org.springframework.stereotype.Service;
import sopt.org.moca.dto.District;
import sopt.org.moca.mapper.DistrictMapper;
import sopt.org.moca.model.DefaultRes;

import java.util.List;

@Service
public interface DistrictService {

    DefaultRes<List<District>>findDistrictList();
}
