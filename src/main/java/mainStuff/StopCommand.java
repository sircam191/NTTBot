package mainStuff;

import musicBot.GuildMusicManager;
import musicBot.PlayerManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class StopCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        PlayerManager playerManager = PlayerManager.getInstances();
        GuildMusicManager  musicManager = playerManager.getGuildMusicManager(event.getGuild());

        if (args[0].equalsIgnoreCase(Main.prefix + "stop") || args[0].equalsIgnoreCase(Main.prefix + "clear")) {
            musicManager.scheduler.getQueue().clear();
            musicManager.player.stopTrack();
            musicManager.player.setPaused(false);

            event.getChannel().sendMessage("Aight I stopped the music and cleared the queue for yee").queue();
        }

        if (args[0].equalsIgnoreCase(Main.prefix + "pause")) {
            event.getChannel().sendMessage("Music Paused").queue();
            musicManager.player.setPaused(true);
        }

    }
}
