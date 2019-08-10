package mainStuff;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import musicBot.GuildMusicManager;
import musicBot.PlayerManager;
import musicBot.TrackScheduler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SkipCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.prefix + "skip") || args[0].equalsIgnoreCase(Main.prefix + "skip")) {
            PlayerManager playerManager = PlayerManager.getInstances();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            TrackScheduler scheduler = musicManager.scheduler;
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null) {
                event.getChannel().sendMessage("Im not playing anything dude").queue();
            }
            else {
                scheduler.nextTrack();
                event.getChannel().sendMessage("Skipping current song").queue();
            }


        }

    }
}
