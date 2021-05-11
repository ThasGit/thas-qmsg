package cc.thas.qmsg.plugin;

import cc.thas.qmsg.service.QmsgTokenService;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandPlugin extends BotPlugin {

    private static final String GEN_TOKEN = "gen token";
    private static final String LIST_TOKEN = "list token";

    @Autowired
    private QmsgTokenService qmsgTokenService;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        String rawMessage = event.getRawMessage();
        switch (rawMessage) {
            case GEN_TOKEN:
                String token = qmsgTokenService.generateToken(String.valueOf(event.getUserId()));
                bot.sendPrivateMsg(event.getUserId(), token, false);
                return MESSAGE_BLOCK;
            case LIST_TOKEN:
                bot.sendPrivateMsg(event.getUserId(), "not support.", false);
                return MESSAGE_BLOCK;
            default:break;
        }
        return MESSAGE_IGNORE;
    }
}
