
package sopt.org.moca.service;

import sopt.org.moca.model.DefaultRes;
import sopt.org.moca.model.ReportReq;

public interface ReportService {

    DefaultRes save(final ReportReq reportReq, final int flag);
}

