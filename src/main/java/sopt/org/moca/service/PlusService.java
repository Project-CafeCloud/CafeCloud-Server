package sopt.org.moca.service;

import sopt.org.moca.dto.PlusSubject;
import sopt.org.moca.model.DefaultRes;

import java.util.List;

public interface PlusService {

    DefaultRes<List<PlusSubject>> findPlusSubjectList (final int length);
}
