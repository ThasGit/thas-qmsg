package cc.thas.qmsg.service;

import cc.thas.qmsg.constant.QmsgTokenScenes;
import cc.thas.qmsg.manager.BotManager;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static cc.thas.qmsg.service.QmsgTokenService.JWT_CLAIM_TARGET;

@Service
public class QmsgSendService {

    @Autowired
    private BotManager botManager;

    @Autowired
    private QmsgTokenService tokenService;

    public int send(String token, String msg) {
        DecodedJWT jwt = tokenService.verify(token);
        long issuer = Long.parseLong(jwt.getIssuer());
//        long audience = Long.parseLong(jwt.getAudience().get(0));
        Map<String, Object> targets = jwt.getClaim(JWT_CLAIM_TARGET).asMap();
        for (Map.Entry<String, Object> entry : targets.entrySet()) {
            long target = Long.parseLong(entry.getKey());
            if ((int) entry.getValue() == QmsgTokenScenes.PRIVATE.ordinal()) {
                return botManager.sendPrivateMsg(issuer, target, msg, false);
            } else if ((int) entry.getValue() == QmsgTokenScenes.GROUP.ordinal()) {
                return botManager.sendGroupMsg(issuer, target, msg, false);
            }
        }
        return -1;
    }
}
