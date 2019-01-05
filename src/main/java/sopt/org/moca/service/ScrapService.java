package sopt.org.moca.service;

import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ScrapReq;

public interface ScrapService {
    DefaultRes scrap (final ScrapReq scrapReq);
    boolean checkScrap(final String user_id, final int cafe_id);
}
