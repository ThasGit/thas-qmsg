package cc.thas.qmsg.service;

import cc.thas.qmsg.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QmsgTokenService {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateToken(String audience) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        Date now = new Date();
        return JWT.create()
                // 主题
                .withSubject(jwtProperties.getSubject())
                // 签发人
                .withIssuer(jwtProperties.getIssuer())
                // 受众
                .withAudience(audience)
                // 生效时间
                .withNotBefore(now)
                // 签发时间
                .withIssuedAt(now)
                // 过期时间
                .withExpiresAt(DateUtils.addSeconds(now, jwtProperties.getExpire().intValue()))
                .sign(algorithm);
    }

    public DecodedJWT verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtProperties.getIssuer())
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        if (jwt.getExpiresAt().getTime() - System.currentTimeMillis() > 0) {
            return jwt;
        }
        // throw Exception
        return null;
    }
}
