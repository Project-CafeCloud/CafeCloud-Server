package sopt.org.moca.service;

import sopt.org.moca.dto.Map;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.MapReq;

import java.util.List;

public interface MapService {

    DefaultRes<List<Map>> GetNearByCafe(final MapReq mapReq);
}
