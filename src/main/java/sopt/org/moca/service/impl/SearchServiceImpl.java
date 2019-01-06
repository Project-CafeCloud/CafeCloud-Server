package sopt.org.moca.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.SearchCafeInfo;
import sopt.org.moca.dto.SearchUSerInfo;
import sopt.org.moca.mapper.SearchMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.SearchCafeInfoRef;
import sopt.org.moca.model.SearchCommunityCombination;
import sopt.org.moca.service.SearchService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    private final  SearchMapper searchMapper;

    public SearchServiceImpl(final SearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }


    public DefaultRes searchKeywordInCafe(String keyword)
    {
        List<SearchCafeInfo> searchCafeInfoList = searchMapper.searchCafeInfoList(keyword);
        if(searchCafeInfoList.isEmpty())
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.FAIL_SEARCH_CAFE_LIST);
        ArrayList searchCafeInfoRefArrayList = new ArrayList();
        for(SearchCafeInfo searchCafeInfo : searchCafeInfoList)
        {
            if(searchCafeInfo.getCafe_name().contains(keyword))
                searchCafeInfoRefArrayList.add(new SearchCafeInfoRef(searchCafeInfo, false));
            else
                searchCafeInfoRefArrayList.add(new SearchCafeInfoRef(searchCafeInfo, true));
        }

        return DefaultRes.res(StatusCode.OK, ResponseMessage.SEARCH_CAFE_LIST, searchCafeInfoRefArrayList);
    }

    public DefaultRes searchKeywordInCommunity(String keyword , String user_id)
    {
        SearchCommunityCombination searchCommunityCombination = new SearchCommunityCombination();
        List<SearchUSerInfo> searchUSerInfoList = searchMapper.searchUserInfoList(keyword,user_id);
        log.info(searchUSerInfoList.toString());
        searchCommunityCombination.setSearchUserList(searchUSerInfoList);

        //카페 리뷰



        return null;
    }

}
