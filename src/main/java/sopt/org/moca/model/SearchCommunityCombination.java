package sopt.org.moca.model;

import lombok.Data;
import sopt.org.moca.dto.SearchUserInfo;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchCommunityCombination {
    private ArrayList<SearchReviewRef> popularReviewList;
    private ArrayList<SearchReviewRef> reviewListOrderByLatest;
    private List<SearchUserInfo> searchUserList;
}
