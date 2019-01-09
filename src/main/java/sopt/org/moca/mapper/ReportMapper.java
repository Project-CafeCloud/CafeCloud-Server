package sopt.org.moca.mapper;


import org.apache.ibatis.annotations.*;
import sopt.org.moca.model.ReportReq;


/**
 * save                 : 리뷰 등록
 */

@Mapper
public interface ReportMapper {

    /**
     * 리뷰 등록
     *
     * @param   reportReq       신고 데이터
     * @param   flag            플래그 (유저/리뷰/댓글)
     */
    @Insert("INSERT INTO REPORT (user_id, report_relation, report_relation_id, report_content, report_date) " +
            "VALUES (#{reportReq.user_id}, #{flag}, #{reportReq.report_relation_id}, #{reportReq.content}, #{reportReq.created_date})")
    void save(@Param("reportReq") final ReportReq reportReq, final int flag);



}
