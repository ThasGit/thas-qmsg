package cc.thas.qmsg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "thas.jwt")
@Data
public class JwtProperties {

    private String subject;
    private String issuer;
    private String secret;
    private Number expire;
}
