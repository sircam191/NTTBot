package mainStuff;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildBanEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class UserBanEvent extends ListenerAdapter {
    public void onGuildBan(GuildBanEvent event) {
        TextChannel mainLobChannel = event.getGuild().getTextChannelById("268904408883003393");

        mainLobChannel.sendMessage("<:oof:522611818376724502> " + event.getUser().getAsMention() + " has been **BANNED**").queue();
    }
}

