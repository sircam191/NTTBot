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


        //TODO check if this works, upload to live server
        if (input.length() > 6 && args[0].startsWith("?play")) {
            songSearch = event.getMessage().getContentDisplay().substring(5);
        }
        else if(args[0].startsWith("?p")) {
            songSearch = event.getMessage().getContentDisplay().substring(2);
        }
        //TODO Do I need this? (below else statement)
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

                if (!input.isEmpty()) {

                    try {

                        if (event.getMember().getVoiceState().getChannel() != audioManager.getConnectedChannel()) {
                            event.getChannel().sendMessage("> Joining: ``" + event.getMember().getVoiceState().getChannel().getName() + "``").queue();
                        }
                        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());

                        event.getChannel().sendMessage("> Searching YouTube For: ``" + songSearch + "``").queue();
                        playerManager.loadAndPlay(event.getChannel(), "ytsearch:" + songSearch);
                    } catch (Exception e) {
                        event.getChannel().sendMessage("Umm I can't join your channel if your not in a channel").queue();
                    }
                }
                /*
                if (!input.isEmpty() && isUrl(songSearch)) {
                    playerManager.loadAndPlay(event.getChannel(), input);
                    try {
                        event.getChannel().sendMessage("Joining: ``" + event.getMember().getVoiceState().getChannel().getName() + "``").queue();
                        audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());
                    } catch (Exception e) {
                        event.getChannel().sendMessage("Umm I can't join your channel if your not in a channel").queue();
                    }
                }
            */
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
