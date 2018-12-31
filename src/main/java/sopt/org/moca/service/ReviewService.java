
package sopt.org.moca.service;

import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.mapper.ReviewImageMapper;
import sopt.org.moca.mapper.ReviewLikeMapper;
import sopt.org.moca.mapper.ReviewMapper;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;
import sopt.org.moca.service.impl.FileUploadService;

import java.util.List;

public interface ReviewService {

    ReviewService(final ReviewMapper reviewMapper,
                  final ReviewImageMapper reviewImageMapper,
                  final ReviewLikeMapper reviewLikeMapper,
                  final FileUploadService fileUploadService);

    DefaultRes<ReviewImage> findAllByCafeId(final int cafeId);

    DefaultRes<List<Review>> findBestByCafeId(final int cafeId, final int num);

    DefaultRes<Review> findByReviewId(final int reviewId);

    DefaultRes save(final ReviewReq reviewReq);

    DefaultRes like(final String userId, final int reviewId);

    boolean checkAuth(final String userId, final int reviewId);

    boolean checkLike(final String userId, final int reviewId);
}

