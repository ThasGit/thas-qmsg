package cc.thas.qmsg.manager;

import cc.thas.qmsg.util.BotUtil;
import net.lz1998.pbbot.bot.Bot;
import net.lz1998.pbbot.bot.BotContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotManager {

    @Autowired
    private BotContainer botContainer;

    public int sendPrivateMsg(long botId, long qq, String msg, boolean escape) {
        Bot bot = botContainer.getBots().get(botId);
        return BotUtil.sendPrivateMsg(bot, qq, msg, escape);
    }

    public int sendGroupMsg(long botId, long groupId, String msg, boolean escape) {
        Bot bot = botContainer.getBots().get(botId);
        return BotUtil.sendGroupMsg(bot, groupId, msg, escape);
    }
}
