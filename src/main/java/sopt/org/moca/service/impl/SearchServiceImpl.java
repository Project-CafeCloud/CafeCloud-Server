package sopt.org.moca.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.org.moca.dto.*;
import sopt.org.moca.mapper.SearchMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.SearchCafeInfoRef;
import sopt.org.moca.model.SearchCommunityCombination;
import sopt.org.moca.model.SearchReviewRef;
import sopt.org.moca.service.SearchService;
import sopt.org.moca.utils.ResponseMessage;
import sopt.org.moca.utils.StatusCode;

import java.util.ArrayList;
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
        List<SearchUserInfo> searchUserInfoList = searchMapper.searchUserInfoList(keyword,user_id);
        log.info(searchUserInfoList.toString());
        searchCommunityCombination.setSearchUserList(searchUserInfoList);

        //카페 리뷰
        List<SearchReviewInfo> searchReviewInfoList = searchMapper.searchReviewInfoList(keyword);

        if(searchUserInfoList.isEmpty() && searchReviewInfoList.isEmpty())
            return DefaultRes.res(StatusCode.NO_CONTENT,ResponseMessage.FAIL_SEARCH_REVIEW_LIST);


        ArrayList<SearchReviewRef> popularReviewList = new ArrayList<>();

        ArrayList<SearchReviewRef> reviewListOrderByLatest = new ArrayList<>();
        if(searchReviewInfoList.size()<3)
        {
            for(int j = 0 ; j < searchReviewInfoList.size() ; j++)
            {
                popularReviewList.add(new SearchReviewRef(searchReviewInfoList.get(j)));
                searchReviewInfoList.remove(j);
            }

        }
        else {
            for (int j = 0; j < 3; j++) {
                int max = 0;
                for (int i = 0; i < searchReviewInfoList.size(); i++) {
                    if (0 <= searchReviewInfoList.get(i).getLike_count()) {
                        max = i;
                    }
                }
                popularReviewList.add(new SearchReviewRef(searchReviewInfoList.get(max)));
                searchReviewInfoList.remove(max);
            }
        }
        for(SearchReviewInfo searchReviewInfo : searchReviewInfoList)
        {
            reviewListOrderByLatest.add(new SearchReviewRef(searchReviewInfo));
        }

        searchCommunityCombination.setPopularReviewList(popularReviewList);
        searchCommunityCombination.setReviewListOrderByLatest(reviewListOrderByLatest);


        return DefaultRes.res(StatusCode.OK,ResponseMessage.SEARCH_REVIEW_LIST, searchCommunityCombination);
    }

    @Override
    public DefaultRes<List<FollowerInfo>> searchFollowerInfoList(String user_id) {
        List<FollowerInfo> followerInfoList = searchMapper.searchFollowerList(user_id);
        if(followerInfoList.isEmpty())
        {
         return  DefaultRes.res(StatusCode.NO_CONTENT,ResponseMessage.FAIL_SEARCH_FOLLOWER_LIST);
        }
        else
        {
            return DefaultRes.res(StatusCode.OK, ResponseMessage.SEARCH_FOLLOWER_LIST,followerInfoList);

        }
    }

    @Override
    public DefaultRes<List<FollowingInfo>> searchFollingInfoList(String user_id) {
        List<FollowingInfo> followingInfoList = searchMapper.searchFollowingList(user_id);
        if(followingInfoList.isEmpty())
        {
            return  DefaultRes.res(StatusCode.NO_CONTENT,ResponseMessage.FAIL_SEARCH_FOLLOWING_LIST);
        }
        else
        {
            return DefaultRes.res(StatusCode.OK, ResponseMessage.SEARCH_FOLLOWING_LIST,followingInfoList);

        }


    }


}
