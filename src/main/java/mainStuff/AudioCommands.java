package mainStuff;

import mainStuff.Main;
import musicBot.PlayerManager;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

public class AudioCommands extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        PlayerManager manager = PlayerManager.getInstances();
        AudioManager audioManager = event.getGuild().getAudioManager();


        //PLAY TEST
        if (args[0].equalsIgnoreCase(Main.prefix + "playtest")) {
            event.getChannel().sendMessage("PLAYING TEST TRACK\n").queue();

            if (!event.getMember().getVoiceState().inVoiceChannel()) {
                event.getChannel().sendMessage("Dink Dink Dink, you need to be in a voice channel bruh").queue();
            }
            else {
                manager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=0k9SjMpAxRM");
                manager.getGuildMusicManager(event.getGuild()).player.setVolume(30);
                VoiceChannel playChannel = event.getMember().getVoiceState().getChannel();
                audioManager.openAudioConnection(playChannel);
            }
        }

        //JOIN COMMAND
        if (args[0].equalsIgnoreCase(Main.prefix + "join")) {
            try {
                event.getChannel().sendMessage("Joining: ``" + event.getMember().getVoiceState().getChannel().getName() + "``").queue();
                audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
            }
            catch (Exception e) {
                event.getChannel().sendMessage("Umm I can't join your channel if your not in a channel").queue();
            }
        }

        //LEAVE COMMAND
        if (args[0].equalsIgnoreCase(Main.prefix + "leave")) {
            event.getChannel().sendMessage("Aight I'll Leave").queue();
            audioManager.closeAudioConnection();
        }








    }
}
