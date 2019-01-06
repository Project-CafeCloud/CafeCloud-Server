package sopt.org.moca.service;

import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.SearchCafeInfoRef;
import sopt.org.moca.model.SearchCommunityCombination;

import java.util.List;

public interface SearchService {

    DefaultRes<List<SearchCafeInfoRef>> searchKeywordInCafe(String keyword);

    DefaultRes<SearchCommunityCombination> searchKeywrodInCommunity(String keyword);
}
