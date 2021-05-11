package cc.thas.qmsg.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConfigurationProperties(prefix = "thas.jwt")
@Data
public class JwtProperties {

    private String subject;
    private String issuer;
    private String secret;
    private Number expire;
}
