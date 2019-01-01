package sopt.org.moca.service;

import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewComment;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.mapper.ReviewCommentMapper;
import sopt.org.moca.mapper.ReviewImageMapper;
import sopt.org.moca.mapper.ReviewLikeMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewCommentReq;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.impl.FileUploadService;

import java.util.List;

public interface ReviewCommentService {

    DefaultRes<List<ReviewComment>> findByReviewId(final int reviewId);

    DefaultRes save(final ReviewCommentReq reviewCommentReq);

}
