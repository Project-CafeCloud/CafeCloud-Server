package sopt.org.moca.service;

import sopt.org.moca.dto.Review;

import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;


import java.util.List;

public interface ReviewCommentService {

    DefaultRes<List<Review>> findByReviewId(final int reviewId);

    DefaultRes save(final ReviewCommentReq reviewCommentReq);

}
