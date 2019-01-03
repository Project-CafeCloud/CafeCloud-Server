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

    //검증 카페
    public static final String READ_EVALUATED_CAFE_LIST = "검증 카페 리스트 조회 성공";
    public static final String FAIL_EVALUATED_CAFE_LIST = "검증 카페 리스트 조회 실패";
    public static final String READ_EVALUATED_CAFE_INFO = "검증카페 상세 정보 조회 성공";
    public static final String FAIL_EVALUATED_CAFE_INFO = "검증카페 상세 정보 조회 실패";
    public static final String READ_EVALUATED_CAFE_IMG = "감증카페 이미지 조회 성공";
    public static final String FAIL_EVALUATED_CAFE_IMG = "검증카페 이미지 조회 실패";
    public static final String READ_EVALUATION_LIST = "검증 카페 인증 위원 평가 리스트 조회 성공";
    public static final String FAIL_EVALUATION_LIST = "검증 카페 인증 위원 평가 리스트 조회 실패";
    public static final String READ_EVALUATION_DETAIL = "검증카페 인증 위원 평가 조회 성공";
    public static final String FAIL_EVALUATION_DETAIL = "해당 검증카페 인증 위원 조회 실패";

    //카페 상세 보기
    public static final String READ_CAFE_IMG_LIST = "해당 카페 이미지 리스트 조회 성공";
    public static final String FAIL_CAFE_IMG_LIST = "해당 카페 이미지 리스트 조회 실패";
    public static final String READ_CAFE_INFO = "해당 카페 상세 정보 조회 성공";
    public static final String FAIL_CAFE_INFO = "해당 카페 상세 정보 조회 실패";
    public static final String READ_CAFE_SIGNITURE_MENU = "해당 카페 시그니처 메뉴 조회 성공";
    public static final String FAIL_CAFE_SIGNITURE_MENU = "해당 카페 시그니처 메뉴 조회 실패";


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

    // 위치
    public static final String NOT_FOUND_NEAR_BY_CAFE = "가까운 카페를 찾을 수 없습니다";
    public static final String READ_NEAR_BY_CAFE = "가까운 카페 리스트 찾기 성공";


    // 플러스
    public static final String NOT_FOUND_PLUS_SUBJECT_LIST = "플러스 주제 리스트를 찾을 수 없습니다.";
    public static final String READ_PLUS_SUBJECT_LIST = "플러스 주제 리스트 찾기 성공";
}
