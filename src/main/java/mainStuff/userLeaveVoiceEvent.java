package mainStuff;

import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;


    public class userLeaveVoiceEvent extends ListenerAdapter {
        @Override
        public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
            AudioManager audioManager = event.getGuild().getAudioManager();

            try {
                if ( audioManager.getConnectedChannel().getMembers().size() == 1 ) {
                    audioManager.closeAudioConnection();
                }
            } catch (Exception e) {
                System.out.println("error on user leave voice event");
            }


        }
    }
