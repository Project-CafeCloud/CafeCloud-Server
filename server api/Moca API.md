# MoCa 서버통신 API

### base URL : ~



### 기능

- 유저 관련
  - 로그인
  - 회원정보 조회
  - 회원가입
  - 회원 삭제
- 카페 조회
  - 카페 
- 카페에 대한 리뷰
  - [리뷰 상세조회](리뷰상세조회)
  - [리뷰 작성](리뷰작성)
  - [리뷰 최신순조회](리뷰최신순조회)
  - [리뷰 인기순조회](리뷰인기순조회)
  - [리뷰 좋아요/취소](리뷰좋아요)
- 리뷰에 대한 댓글 
  - [댓글 조회](댓글조회)
  - [댓글 작성](댓글작성) 
- 커뮤니티
  - [유저 피드](유저피드)
  - [소셜 피드](소셜피드)





### path 요약

| 메소드 | 경로                                                         | 설명                                           |
| ------ | ------------------------------------------------------------ | ---------------------------------------------- |
| POST   | /login                                                       | 로그인                                         |
| POST   | /user                                                        | 회원  가입                                     |
| DELETE | /user/{user_id}                                              | 회원 정보 삭제                                 |
|        |                                                              |                                                |
| GET    | /user/{user_id}/mypage                                       | 마이페이지 조회                                |
| PUT    | /user/{user_id}/mypage                                       | 마이페이지 수정                                |
| GET    | /user/membership                                             | 맴버쉽 조회                                    |
| GET    | /user/scrap                                                  | 스크랩(찜)한 카페 조회                         |
| POST   | /user/scrap                                                  | 스크랩(찜)한 카페 생성                         |
| DELETE | /user/scrap                                                  | 스크랩(찜)한 카페 삭제                         |
| GET    | /user/coupon                                                 | 쿠폰 조회                                      |
| DELETE | /user/coupon/{coupon_id}                                     | 쿠폰 사용해서 삭제                             |
| GET    | /user/{user_id}/following                                    | 팔로잉 조회                                    |
| GET    | /user/{user_id}/follower                                     | 팔로워 조회                                    |
| GET    | /search/{user_id}/following                                  | 팔로잉 검색                                    |
| GET    | /search/{user_id}/follower                                   | 팔로워 검색                                    |
|        |                                                              |                                                |
| POST   | /message/{user_id값}                                         | 쪽지 보내기                                    |
| GET    | /message                                                     | 쪽지 조회                                      |
| GET    | /message/{user_id값}                                         | 상대방과 주고 받은 메세지 조회                 |
| GET    | /push                                                        | 푸시 알림                                      |
|        |                                                              |                                                |
| GET    | /membership                                                  | 맴버쉽 조회                                    |
| POST   | /membership                                                  | 맴버쉽 적립                                    |
| GET    | /coupon                                                      | 쿠폰 조회                                      |
| POST   | /coupon                                                      | 쿠폰 사용                                      |
|        |                                                              |                                                |
| GET    | /hot_place                                                   | 핫플레이스  조회                               |
| GET    | /district                                                    | 지역구 아이디 조회                             |
|        |                                                              |                                                |
| GET    | /cafe/pick/{length}                                          | 인기 있는 검증 카페 리스트  조회 (-1일때 전체) |
| GET    | /cafe/pick/detail/{cafe_id}                                  | 검증카페 상세 정보 조회                        |
| GET    | /cafe/pick/image/{cafe_id}                                   | 검증카페 이미지 조회                           |
| GET    | /cafe/pick/evaluate/{cafe_id}                                | 검증카페 인증위원 평가 리스트                  |
| GET    | /cafe/pick/evaluate/{cafe_id}/barista/{barista_id}           | 특정 인증 평가 정보 조회                       |
| GET    | /cafe/image/{cafe_id}                                        | 해당 카페 이미지 리스트 조회                   |
| GET    | /cafe/detail/{cafe_id}                                       | 해당 카페 정보 조회                            |
| GET    | /cafe/signiture/{cafe_id}                                    | 해당 카페 시그니처 메뉴 리스트                 |
| GET    | /cafe/hot_place/{hot_place_id}                               | 해당 핫플레이스 카페 리스트 조회               |
| GET    | /cafe/ranking/{length}                                       | 평점 높은순. 카페 리스트 3개 조회              |
| GET    | /cafe/district/{district_id}                                 |                                                |
| POST   | /cafe/nearbycafe                                             | 지도에서 3KM 이내 반경 조회                    |
|        |                                                              |                                                |
| GET    | /plus/{length}                                               | 에디터 작성 게시글  조회(최신순)(-1일때 전체)  |
| GET    | /plus/{plus_subject_id}/detail                               | 해당 주제 게시글 상단 텍스트, 이미지           |
| GET    | /plus/cafe/{plus_subject_id}                                 | 해당 게시글 카페 리스트 조회                   |
| GET    | /search/{keyword}                                            | 카페 및 컨셉 검색                              |
| GET    | /category/location/{cafe_address_district_id}<br />/signiture/{cafe_main_menu_id}<br />/concept/{cafe_concept_id} | 필터링된 카페 리스트 조회                      |
|        |                                                              |                                                |
|        |                                                              |                                                |
| POST   | /review                                                      | 리뷰 글 작성                                   |
| GET    | /review/{review_id}                                          | 리뷰 상세 조회                                 |
| GET    | /review/{cafe_id}/all                                        | 카페에서 리뷰 모아보기                         |
| GET    | /review/{cafe_id}/best                                       | 카페 리뷰 베스트 3개 조회                      |
| GET    | /review/{review_id}/comment                                  | 댓글 상세 조회                                 |
| POST   | /review/{review_id}/comment                                  | 댓글 작성                                      |
| GET    | /feed/user/{user_id}                                         | 유저 피드 보기                                 |
| GET    | /feed/social                                                 | 소셜 피드 보기                                 |

