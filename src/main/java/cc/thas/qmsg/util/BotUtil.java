package cc.thas.qmsg.util;

import net.lz1998.pbbot.bot.Bot;
import onebot.OnebotApi;
import org.jetbrains.annotations.NotNull;

public interface BotUtil {

    static int sendPrivateMsg(@NotNull Bot bot, long qq, String msg, boolean escape) {
        OnebotApi.SendPrivateMsgResp resp = bot.sendPrivateMsg(qq, msg, escape);
        return resp != null ? resp.getMessageId() : -1;
    }

    static int sendGroupMsg(@NotNull Bot bot, long groupId, String msg, boolean escape) {
        OnebotApi.SendGroupMsgResp resp = bot.sendGroupMsg(groupId, msg, escape);
        return resp != null ? resp.getMessageId() : -1;
    }

    static boolean isGroupAdmin(@NotNull Bot bot, long groupId, long qq) {
        OnebotApi.GetGroupMemberInfoResp resp = bot.getGroupMemberInfo(groupId, qq, false);
        if (resp == null) {
            return false;
        }
        String role = resp.getRole();
        return "admin".equals(role) || "owner".equals(role);
    }
}
