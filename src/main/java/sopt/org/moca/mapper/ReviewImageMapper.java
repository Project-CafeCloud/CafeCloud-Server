package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import sopt.org.moca.dto.Review;
import sopt.org.moca.dto.ReviewImage;

import java.util.List;

/**
 * findAllReviewImageByCafeId   : 리뷰 이미지 조회
 * save                         : 리뷰 이미지 저장
 */


@Mapper
public interface ReviewImageMapper {

    /**
     * 리뷰 이미지 조회
     * @param   reviewId    리뷰 고유 index
     * @return  이미지 리스트
     */
    @Select("SELECT * FROM reviewImage " +
            "WHERE review_id = #{reviewId}")
    List<ReviewImage> findAllByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 리뷰 이미지 1장만 조회
     * @param   reviewId    리뷰 고유 index
     * @return  이미지
     */
    @Select("SELECT review_img_url FROM reviewImage " +
            "WHERE review_id = #{reviewId} " +
            "LIMIT 1")
    ReviewImage findOneByReviewId(@Param("reviewId") final int reviewId);


    /**
     * 리뷰 이미지 저장
     *
     * @param reviewId      리뷰 고유 index
     * @param reviewImgUrl  리뷰 이미지 url
     */
    @Insert("INSERT INTO reviewImage (review_id, review_img_url) " +
            "VALUES(#{reviewId}, #{reviewImgUrl})")
    void save(@Param("reviewId") final int reviewId,
              @Param("reviewImgUrl") final String reviewImgUrl);

}
