package sopt.org.moca.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sopt.org.moca.model.ReportReq;
import sopt.org.moca.service.ReportService;
import sopt.org.moca.utils.auth.Auth;
import sopt.org.moca.utils.auth.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import static sopt.org.moca.model.DefaultRes.FAIL_DEFAULT_RES;

/**
 * save                : 신고 등록
 */


@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    private static final String HEADER = "Authorization";
    private final ReportService reportService;

    public ReportController(final ReportService reportService) {

        this.reportService = reportService;
    }



    /**
     * 신고 등록
     *
     * @param httpServletRequest Request
     * @param reportReq          신고 데이터
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/{flag}")
    public ResponseEntity save(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int flag,
            ReportReq reportReq) {
        try {
            reportReq.setUser_id(JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_id());

            return new ResponseEntity<>(reportService.save(reportReq, flag), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
