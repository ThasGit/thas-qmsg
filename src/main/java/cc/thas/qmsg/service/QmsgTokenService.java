package cc.thas.qmsg.service;

import cc.thas.qmsg.config.JwtProperties;
import cc.thas.qmsg.constant.QmsgTokenScenes;
import cc.thas.qmsg.entity.QmsgTokenStatus;
import cc.thas.qmsg.entity.QsmgToken;
import cc.thas.qmsg.repository.QmsgTokenRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QmsgTokenService {

    public static final String JWT_CLAIM_TARGET = "target";

    private final JwtProperties jwtProps;
    private final QmsgTokenRepository tokenRepo;
    private final Algorithm algorithm;
    private final JWTVerifier simpleVerifier;

    @Autowired
    public QmsgTokenService(JwtProperties jwtProps, QmsgTokenRepository tokenRepo) {
        this.jwtProps = jwtProps;
        this.tokenRepo = tokenRepo;
        // 线程安全的
        algorithm = Algorithm.HMAC256(jwtProps.getSecret());
        simpleVerifier = JWT.require(algorithm).build();
    }

    public String generateToken(@NotNull String audience) {
        return generateToken(jwtProps.getIssuer(), audience);
    }

    public String generateToken(@NotNull String issuer, @NotNull String audience,  @NotNull String group) {
        Map<String, Integer> targets = Collections.singletonMap(group, QmsgTokenScenes.GROUP.ordinal());
        return generateToken(issuer, audience, targets);
    }

    public String generateToken(@NotNull String issuer, @NotNull String audience) {
        Map<String, Integer> targets = Collections.singletonMap(audience, QmsgTokenScenes.PRIVATE.ordinal());
        return generateToken(issuer, audience, targets);
    }

    public String generateToken(@NotNull String issuer, @NotNull String audience, @NotNull Map<String, Integer> targets) {
        Date expire = DateUtils.addSeconds(new Date(), jwtProps.getExpire().intValue());
        return generateToken(issuer, audience, targets, expire, jwtProps.getSubject());
    }

    public String generateToken(@NotNull String issuer, @NotNull String audience, @NotNull Map<String, Integer> targets,
                                @NotNull Date expire, String subject) {

        String token = JWT.create()
                // 主题
                .withSubject(subject)
                // 签发人
                .withIssuer(issuer)
                // 受众
                .withAudience(audience)
                // 额外
                .withClaim(JWT_CLAIM_TARGET, targets)
                // 生效时间
//                .withNotBefore(now)
                // 签发时间
//                .withIssuedAt(now)
                // 过期时间
                .withExpiresAt(expire)
                .sign(algorithm);
        QsmgToken qsmgToken = QsmgToken.builder().issuer(issuer).audience(audience).expire(expire).token(token).build();
        tokenRepo.save(qsmgToken);
        return token;
    }

    public DecodedJWT verify(@NotNull String token) {
        return simpleVerifier.verify(token);
    }

    public List<String> listTokens(@NotNull String audience, QmsgTokenStatus status) {
        QsmgToken qsmgToken = QsmgToken.builder().audience(audience).status(status).build();
        return tokenRepo.findAll(Example.of(qsmgToken)).stream().map(QsmgToken::getToken).collect(Collectors.toList());
    }
}
