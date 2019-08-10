package mainStuff;

import musicBot.GuildMusicManager;
import musicBot.PlayerManager;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;

public class PlayCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        TextChannel musicCommands = event.getGuild().getTextChannelById("434989084952231936");

        String input = event.getMessage().getContentRaw();
        String songSearch = "";



        if (input.length() > 6) {
            songSearch = event.getMessage().getContentDisplay().substring(5);
        }
        else {
            songSearch = event.getMessage().getContentDisplay();
        }
        PlayerManager playerManager = PlayerManager.getInstances();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioManager audioManager = event.getGuild().getAudioManager();
        //PlayerManager manager = PlayerManager.getInstances();

        if (args[0].equalsIgnoreCase(Main.prefix + "play") || args[0].equalsIgnoreCase(Main.prefix + "p")) {

            if (event.getChannel() != musicCommands) {
                event.getChannel().sendMessage("You gotta use music commands in " + musicCommands.getAsMention()).queue();
            }
            else {
                playerManager.getGuildMusicManager(event.getGuild()).player.setVolume(30);

                if (input.length() > 4) {
                    input = event.getMessage().getContentRaw().substring(5);
                    input = input.replaceAll("\\s+", "");
                }

                if (input.isEmpty() && musicManager.player.isPaused()) {
                    musicManager.player.setPaused(false);
                } else if (input.isEmpty()) {
                    event.getChannel().sendMessage("You gotta tell me what to play loser").queue();
                }

                if (!isUrl(input) && !input.isEmpty()) {

                    try {
                        event.getChannel().sendMessage("Joining: ``" + event.getMember().getVoiceState().getChannel().getName() + "``").queue();
                        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
                        event.getChannel().sendMessage("Searching YouTube For: ``" + songSearch + " ``").queue();
                        playerManager.loadAndPlay(event.getChannel(), "ytsearch:" + songSearch);
                    } catch (Exception e) {
                        event.getChannel().sendMessage("Umm I can't join your channel if your not in a channel").queue();
                    }
                }

                if (!input.isEmpty() && isUrl(input)) {
                    playerManager.loadAndPlay(event.getChannel(), input);
                    try {
                        event.getChannel().sendMessage("Joining: ``" + event.getMember().getVoiceState().getChannel().getName() + "``").queue();
                        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
                    } catch (Exception e) {
                        event.getChannel().sendMessage("Umm I can't join your channel if your not in a channel").queue();
                    }
                }
            }
            }
    }

    private boolean isUrl (String input){
        try {
            new URL(input);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
