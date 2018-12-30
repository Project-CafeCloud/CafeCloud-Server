package sopt.org.moca.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import static com.auth0.jwt.JWT.require;



@Slf4j
public class JwtUtils {

    private static final String ISSUER = "DoITSOPT";

    private static final String SECRET = "vji2k@#49c!@!@#$knvldkm3$";

    /**
     * 토큰 생성
     *
     * @param user_id 토큰에 담길 로그인한 사용자의 회원 고유 IDX
     * @return 토큰
     */
    public static String create(final String user_id) {
        try {
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("user_id", user_id);
            return b.sign(Algorithm.HMAC256(SECRET));
        } catch (JWTCreationException JwtCreationException) {
            log.info(JwtCreationException.getMessage());
        }
        return null;
    }

    /**
     * 토큰 해독
     *
     * @param token 토큰
     * @return 로그인한 사용자의 회원 고유 IDX
     */
    public static Token decode(final String token) {
        try {
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return new Token(decodedJWT.getClaim("user_id").asString());  //맞는지 궁금
        } catch (JWTVerificationException jve) {
            log.error(jve.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new Token();
    }

    public static class Token {

        private String user_id = null;
//        private String user_idx = -1;

        public Token() {
        }

        public Token(final String user_id) {
            this.user_id = user_id;
        }

        public String getUser_id() {
            return user_id;
        }
    }

    public static class TokenRes {
        private String token;

        public TokenRes() {
        }

        public TokenRes(final String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
