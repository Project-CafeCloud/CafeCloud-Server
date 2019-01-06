package sopt.org.moca.model;

import lombok.Data;
import sopt.org.moca.dto.SearchCafeInfo;
import sopt.org.moca.dto.SearchReviewInfo;
import sopt.org.moca.dto.SearchUSerInfo;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchCommunityCombination {
    private ArrayList<SearchReviewInfo> popularReviewList;
    private ArrayList<SearchReviewInfo> reviewListOrderByLatest;
    private List<SearchUSerInfo> searchUserList;
}
