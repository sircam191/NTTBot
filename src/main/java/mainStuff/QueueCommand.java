package mainStuff;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import musicBot.GuildMusicManager;
import musicBot.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        if (args[0].equalsIgnoreCase(Main.prefix + "queue") || args[0].equalsIgnoreCase(Main.prefix + "q")) {
            TextChannel channel = event.getChannel();
            PlayerManager playerManager = PlayerManager.getInstances();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            if (queue.isEmpty()) {
                channel.sendMessage("Queue is __empty__ my dude").queue();
            }
            else {


                int trackCount = Math.min(queue.size(), 20);
                List<AudioTrack> tracks = new ArrayList<>(queue);
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Current Queue (``" + queue.size() + "`` Total Tracks)");

                for (int i = 0; i < trackCount; i++) {
                    AudioTrack track = tracks.get(i);
                    AudioTrackInfo info = track.getInfo();

                    builder.addField("", String.format(
                            "**%s** - %s\n",
                            info.title,
                            info.author
                    ), false);
                }


                channel.sendMessage(builder.build()).queue();
            }

        }

    }

}
