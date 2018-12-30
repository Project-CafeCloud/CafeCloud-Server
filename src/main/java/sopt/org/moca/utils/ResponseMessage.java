package sopt.org.moca.utils;

public class ResponseMessage {

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String FAIL_CREATE_USER = "회원 가입 실패";
    public static final String USER_ALREADY_EXISTS = "회원이 이미 존재합니다.";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";

    public static final String AUTHORIZED = "인증 성공";
    public static final String UNAUTHORIZED = "인증 실패";
    public static final String FORBIDDEN = "인가 실패";


    public static final String GET_SERVER_TIME_SUCCESS = "서버 시간 조회 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";

    public static final String DB_ERROR = "데이터베이스 에러";


    // 핫플레이스
    public static final String READ_HOT_PLACE = "핫플레이스 리스트 조회 성공";


    // 리뷰
    public static final String READ_REVIEWS = "리뷰 조회 성공";
    public static final String NOT_FOUND_REVIEWS = "리뷰를 찾을 수 없습니다";
    public static final String CREATED_REVIEW = "리뷰 작성 성공";
    public static final String FAIL_CREATE_REVIEW = "리뷰 작성 실패";
    public static final String LIKE_REVIEW = "리뷰 좋아요 성공";

    // 리뷰에 대한 댓글
    public static final String READ_COMMENTS = "댓글 조회 성공";
    public static final String NOT_FOUND_COMMENTS = "댓글을 찾을 수 없습니다";
    public static final String CREATED_COMMENT = "댓글 작성 성공";
    public static final String FAIL_CREATE_COMMENT = "댓글 작성 실패";

}
