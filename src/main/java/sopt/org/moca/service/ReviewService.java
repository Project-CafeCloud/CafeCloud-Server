
package sopt.org.moca.service;

import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewImage;
import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReviewReq;

import java.util.List;

public interface ReviewService {


    DefaultRes<List<ReviewImage>> findAllByCafeId(final int cafeId);

    DefaultRes<List<Review>> findBestByCafeId(final int cafeId, final int num);

    DefaultRes<Review> findByReviewId(final int reviewId);

    DefaultRes<List<Review>> findByUserId(final  List<String>  userId);

    DefaultRes save(final ReviewReq reviewReq);

    DefaultRes like(final String userId, final int reviewId);

    boolean checkAuth(final String userId, final int reviewId);

    boolean checkLike(final String userId, final int reviewId);
}

