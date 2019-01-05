
## BASE URL 
#### 👉 52.79.173.137:8080 👈

<br>

## 기능
> update date : 2018-01-05 19:00

- 유저 관련
  - [로그인](로그인)
  - [회원 가입](회원가입)
  - [회원 탈퇴](회원탈퇴)
  - [마이페이지 조회](마이페이지조회)
  - [마이페이지 수정](마이페이지수정)
  - [팔로우 / 언팔로우하기](팔로우하기)
  - [팔로워 조회](팔로워조회)
  - [팔로잉 조회](팔로잉조회)
- 멤버십 및 쿠폰 관련
  - [멤버십 리스트 조회](멤버십리스트조회)
  - [멤버십 적립](멤버십적립)
  - [쿠폰 리스트 조회](쿠폰리스트조회)
  - [쿠폰 적립](쿠폰적립)
- 컨셉 및 핫플레이스 및 지역 관련
  - [핫플레이스 리스트 조회](핫플레이스조회)
  - [컨셉 리스트 조회](컨셉조회)
  - [지역구 리스트 조회](지역구조회)
- 카페 관련
  - [카페 상세 조회](카페상세조회)
  - [카페 이미지 리스트 조회](카페이미지조회)
  - [카페 시그니처 메뉴 조회](카페시그니처메뉴조회)
  - [근처카페 조회](근처카페조회)
- 검증카페 관련 (Pick)
  - [검증카페 베스트 조회](검증카페베스트조회)
  - [검증카페 상세 조회](검증카페상세조회)
  - [검증카페 이미지 조회](검증카페이미지조회)
  - [검증카페 인증 평가 리스트 조회](검증카페인증평가리스트조회)
  - [검증카페 인증 평가 상세 조회](검증카페인증평가상세조회)
- 모카플러스 관련
  - [플러스 주제 리스트 조회](플러스주제리스트조회)
  - [플러스 주제 상세 조회](플러스주제상세조회)
  - [플러스 컨텐츠 상세 조회](플러스컨텐츠상세조회)
  - [플러스 컨텐츠 이미지 조회](플러스컨텐츠이미지조회)
- 카페에 대한 리뷰 관련
  - [리뷰 상세 조회](리뷰상세조회)
  - [리뷰 작성](리뷰작성)
  - [리뷰 최신순 조회](리뷰최신순조회)
  - [리뷰 인기순 조회](리뷰인기순조회)
  - [리뷰 좋아요/취소](리뷰좋아요)
- 리뷰에 대한 댓글 관련
  - [댓글 조회](댓글조회)
  - [댓글 작성](댓글작성) 
- 커뮤니티 관련
  - [유저 피드 조회](유저피드조회)
  - [소셜 피드 조회](소셜피드조회)

<br>

## path 요약
> update date : 2018-01-05 19:00

| 메소드 | 경로                                                         | 설명                                           | 완료 |
| ------ | --------------------------------------------------------- | ---------------------------------------------- | ------- |
| POST   | /login                                                    | 로그인                                         | 소연 |
| GET    | /user/{user_id}                                           | 회원 정보 조회                                 | 소연 |
| POST   | /user                                                     | 회원  가입                                     | 소연 |
| DELETE | /user/{user_id}                                           | 회원 정보 삭제                                 | 소연 |
| GET    | /user/{user_id}/mypage                                    | 마이페이지 조회                                | 소연 |
| PUT    | /user/{user_id}/mypage                                    | 마이페이지 수정                                | 소연 |
| GET    | /membership                                               | 맴버십 조회                                    | 선필 |
| POST   | /membership                                               | 멤버십 적립                                    | 선필 |
| GET    | /user/scrap                                               | 스크랩(찜)한 카페 조회                         |      |
| POST   | /user/scrap                                               | 스크랩(찜)한 카페 생성                         |      |
| DELETE | /user/scrap                                               | 스크랩(찜)한 카페 삭제                         |      |
| GET    | /coupon                                                   | 쿠폰 조회                                      | 선필 |
| DELETE | /coupon                                                   | 쿠폰 사용해서 삭제                             | 선필 |
| POST    | /user/{user_id}/follow                                   | 팔로우하기                                    | 지연 |
| GET    | /user/{user_id}/following                                 | 팔로잉 리스트 조회                                    | 지연 |
| GET    | /user/{user_id}/follower                                  | 팔로워 리스트 조회                                    | 지연 |
| GET    | /search/{user_id}/following                               | 팔로잉 검색                                    |      |
| GET    | /search/{user_id}/follower                                | 팔로워 검색                                    |      |
| POST   | /message/{user_id}                                        | 쪽지 보내기                                    |      |
| GET    | /message                                                  | 쪽지 조회                                      |      |
| GET    | /message/{user_id}                                        | 상대방과 주고 받은 메세지 조회                   |      |
| GET    | /push                                                     | 푸시 알림                                      |      |
| GET    | /hot_place                                                | 핫플레이스  조회                               | 선필 |
| GET    | /district                                                 | 지역구 아이디 조회                             | 선필 |
| GET    | /cafe/pick/{length}                                       | 검증 카페 리스트  조회 (인기순)               | 선필 |
| GET    | /cafe/pick/{cafe_id}/detail                               | 검증카페 상세 정보 조회                        | 선필 |
| GET    | /cafe/pick/{cafe_id}/image                                | 검증카페 이미지 조회                           | 선필 |
| GET    | /cafe/pick/evaluate/{cafe_id}                             | 검증카페 인증위원 평가 리스트                  | 선필 |
| GET    | /cafe/pick/evaluate/<br>{cafe_id}/barista/{barista_id}    | 특정 인증 평가 정보 조회                       | 선필 |
| GET    | /cafe/{cafe_id}/image                                     | 해당 카페 이미지 리스트 조회                   | 선필 |
| GET    | /cafe/{cafe_id}/detail                                    | 해당 카페 정보 조회                            | 선필 |
| GET    | /cafe/{cafe_id}/signiture                                 | 해당 카페 시그니처 메뉴 리스트                 | 선필 |
| GET    | /cafe/hot_place/{hot_place_id}                            | 해당 핫플레이스 카페 리스트 조회                |      |
| GET    | /cafe/ranking/{length}                                    | 평점 높은순. 카페 리스트 3개 조회              |      |
| POST   | /cafe/nearbycafe                                          | 지도에서 3KM 이내 반경 조회                    | 소연 |
| GET    | /plus/{length}                                            | 플러스 주제 리스트 조회(최신순)                | 소연  |
| GET    | /plus/{plus_subject_id}/detail                            | 플러스 해당 주제 게시글 상세 조회               | 소연  |
| GET    | /plus/{plus_subject_id}/contents                          | 플러스 해당 주제 컨텐츠 조회                   | 소연 |
| GET    | /plus/{plus_contents_id}/image                            | 플러스 컨텐츠 이미지 리스트 조회                | 소연  |
| GET    | /search/{keyword}                                         | 카페 및 컨셉 검색                              |      |
| GET    |            | 필터링된 카페 리스트 조회                      |      |
| POST   | /review                                                   | 리뷰 글 작성                                   | 지연 |
| GET    | /review/{review_id}                                       | 리뷰 상세 조회                                 | 지연 |
| GET    | /review/{cafe_id}/all                                     | 카페에서 리뷰 모아보기                         | 지연 |
| GET    | /review/{cafe_id}/best                                    | 카페 리뷰 베스트 3개 조회                      | 지연 |
| GET    | /review/{review_id}/comment                               | 댓글 상세 조회                                 | 지연 |
| POST   | /review/{review_id}/comment                               | 댓글 작성                                      | 지연 |
| GET    | /feed/user/{user_id}                                      | 유저 피드 보기                                 | 지연 |
| GET    | /feed/social                                              | 소셜 피드 보기                                 | 지연 |
