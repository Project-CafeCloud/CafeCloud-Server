package sopt.org.moca.service;

import sopt.org.moca.dto.Review;

import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;


import java.util.List;

public interface ReviewCommentService {

    ReviewComment findByCommentId(final int reviewCommentId);

    DefaultRes<List<ReviewComment>> findByReviewId(final String user_id, final int reviewId);

    DefaultRes<ReviewComment> update(final ReviewCommentReq reviewCommentReq);

    DefaultRes deleteByReviewCommentId(final int reviewCommentId);

    DefaultRes save(final ReviewCommentReq reviewCommentReq);

    boolean checkAuth(final String user_id, final int review_comment_id);

}
