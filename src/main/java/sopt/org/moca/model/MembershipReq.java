package sopt.org.moca.model;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MembershipReq {
    private String user_id;
    private int cafe_id;
}
