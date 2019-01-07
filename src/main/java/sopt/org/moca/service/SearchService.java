package sopt.org.moca.service;

import sopt.org.moca.dto.FollowerInfo;
import sopt.org.moca.dto.FollowingInfo;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.SearchCafeInfoRef;
import sopt.org.moca.model.SearchCommunityCombination;

import java.util.List;

public interface SearchService {

    DefaultRes<List<SearchCafeInfoRef>> searchKeywordInCafe(String keyword);

    DefaultRes<SearchCommunityCombination> searchKeywordInCommunity(String keyword, String user_id);

    DefaultRes<List<FollowerInfo>> searchFollowerInfoList(String user_id);

    DefaultRes<List<FollowingInfo>>searchFollingInfoList(String user_id);
}
