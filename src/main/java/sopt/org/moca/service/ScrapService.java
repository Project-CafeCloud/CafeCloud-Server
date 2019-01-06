package sopt.org.moca.service;

import sopt.org.moca.dto.Scrap;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ScrapReq;

import java.util.List;

public interface ScrapService {
    DefaultRes scrap (final ScrapReq scrapReq);
    boolean checkScrap(final String user_id, final int cafe_id);
    DefaultRes<List<Scrap>> findById(final String user_id);
    DefaultRes deleteByCafeId(final int cafe_id);
}
