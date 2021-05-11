package cc.thas.qmsg.controller;

import cc.thas.qmsg.service.QmsgTokenService;
import com.auth0.jwt.interfaces.DecodedJWT;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import onebot.OnebotApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class QmsgController {

    @Autowired
    private BotContainer botContainer;

    @Autowired
    private QmsgTokenService tokenService;

    @RequestMapping("/send/{token3}")
    public String send(@RequestParam(value = "token1", required = false) String token1,
                       @RequestHeader(value = "token2", required = false) String token2,
                       @PathVariable(value = "token3", required = false) String token3, String msg) {
        String token = token3 != null ? token3 : (token2 != null ? token2 : token1);
        DecodedJWT decodedJWT = tokenService.verify(token);
        Bot bot = botContainer.getBots().get(Long.valueOf(decodedJWT.getIssuer()));
        long qq = Long.parseLong(decodedJWT.getAudience().get(0));
        OnebotApi.SendPrivateMsgResp resp = bot.sendPrivateMsg(qq, msg, false);
        return resp != null ? "success" : "failed";
    }
}
