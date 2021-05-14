package cc.thas.qmsg.plugin;

import cc.thas.qmsg.entity.QmsgTokenStatus;
import cc.thas.qmsg.service.QmsgTokenService;
import cc.thas.qmsg.util.BotUtil;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotPlugin;
import onebot.OnebotEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QmsgTokenPlugin extends BotPlugin {

    private static final String GEN_TOKEN = "gen token";
    private static final String LIST_TOKEN = "list token";

    @Autowired
    private QmsgTokenService qmsgTokenService;

    @Override
    public int onPrivateMessage(@NotNull Bot bot, @NotNull OnebotEvent.PrivateMessageEvent event) {
        String audience = String.valueOf(event.getUserId());
        switch (event.getRawMessage()) {
            case GEN_TOKEN:
                String issuer = String.valueOf(bot.getSelfId());
                String token = qmsgTokenService.generateToken(issuer, audience);
                BotUtil.sendPrivateMsg(bot, event.getUserId(), "token for you:" + audience, false);
                BotUtil.sendPrivateMsg(bot, event.getUserId(), token, false);
                return MESSAGE_BLOCK;
            case LIST_TOKEN:
                List<String> tokens = qmsgTokenService.listTokens(audience, QmsgTokenStatus.VALID);
                BotUtil.sendPrivateMsg(bot, event.getUserId(), "valid tokens", false);
                for (String token2 : tokens) {
                    BotUtil.sendPrivateMsg(bot, event.getUserId(), token2, false);
                }
                return MESSAGE_BLOCK;
            default:
                break;
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(@NotNull Bot bot, @NotNull OnebotEvent.GroupMessageEvent event) {
        switch (event.getRawMessage()) {
            case GEN_TOKEN:
                if (BotUtil.isGroupAdmin(bot, event.getGroupId(), event.getUserId())) {
                    String audience = String.valueOf(event.getUserId());
                    String group = String.valueOf(event.getGroupId());
                    String issuer = String.valueOf(bot.getSelfId());
                    String token = qmsgTokenService.generateToken(issuer, audience, group);
                    BotUtil.sendPrivateMsg(bot, event.getUserId(), "token for group:" + event.getGroupId(), false);
                    BotUtil.sendPrivateMsg(bot, event.getUserId(), token, false);
                } else {
                    BotUtil.sendPrivateMsg(bot, event.getUserId(), "no permission to gen token for group:" + event.getGroupId(), false);
                }
                return MESSAGE_BLOCK;
            default:
                break;
        }
        return MESSAGE_IGNORE;
    }
}
