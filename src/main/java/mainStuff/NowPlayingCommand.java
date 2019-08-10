package mainStuff;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import musicBot.GuildMusicManager;
import musicBot.PlayerManager;
import musicBot.TrackScheduler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.concurrent.TimeUnit;

public class NowPlayingCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.prefix + "nowplaying") || args[0].equalsIgnoreCase(Main.prefix + "np")) {
            PlayerManager playerManager = PlayerManager.getInstances();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            TrackScheduler scheduler = musicManager.scheduler;
            AudioPlayer player = musicManager.player;

            if (player.getPlayingTrack() == null) {
                event.getChannel().sendMessage("Im a not playing shiiiit dude").queue();

            }
            else {
                AudioTrackInfo info = player.getPlayingTrack().getInfo();

                EmbedBuilder emb = new EmbedBuilder();

                emb.addField("**Now Playing**", (String.format(
                        "[%s](%s)\n%s %s - %s",
                        info.title,
                        info.uri,
                        player.isPaused() ? "\u23F8" : "**Time:** ",
                        formatTime(player.getPlayingTrack().getPosition()),
                        formatTime(player.getPlayingTrack().getDuration())
                )),false);
                emb.setFooter("NTT Music Bot", null);
                event.getChannel().sendMessage(emb.build()).queue();
            }


        }
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}