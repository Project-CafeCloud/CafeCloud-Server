package sopt.org.moca.model;

import lombok.Data;
import sopt.org.moca.dto.SearchCafeInfo;
import sopt.org.moca.dto.SearchReviewInfo;

import java.util.ArrayList;

@Data
public class SearchCommunityCombination {
    private ArrayList<SearchReviewInfo> popularReviewList;
    private ArrayList<SearchReviewInfo> reviewListOrderByLatest;
    private ArrayList<SearchCafeInfo> searchUserList;
}
