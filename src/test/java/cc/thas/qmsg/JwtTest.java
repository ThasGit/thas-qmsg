package cc.thas.qmsg;

import cc.thas.qmsg.service.QmsgTokenService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class JwtTest extends BaseTest {

    @Autowired
    private QmsgTokenService qmsgTokenService;

    @Test
    public void testGenerateToken() {
        String token = qmsgTokenService.generateToken("1");
        System.out.println(token);
    }

    @Test
    public void testVerifyAndDecode() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiJ0aGFzIHFtc2cgdG9rZW4iLCJhdWQiOiIxIiwibmJmIjoxNjIwNjU3NDUyLCJpc3MiOiIyNjE5MTExNDgiLCJleHAiOjE2NTIxOTM0NTIsImlhdCI6MTYyMDY1NzQ1Mn0." +
                "yNzQDWmkxYqmN0nEf_0u8C-uSu7y1yH87kfARYzk7aI";
        DecodedJWT decodedJWT = qmsgTokenService.verify(token);
        System.out.println(decodedJWT);
    }

}
