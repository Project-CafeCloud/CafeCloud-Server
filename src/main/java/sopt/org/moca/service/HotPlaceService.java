package sopt.org.moca.service;

import sopt.org.moca.dto.HotPlace;
import sopt.org.moca.model.DefaultRes;

import java.util.List;

public interface HotPlaceService {

    DefaultRes<List<HotPlace>> findAllHotPlace();

    DefaultRes<List<HotPlace>> findBestHotPlace(final int num);
}
