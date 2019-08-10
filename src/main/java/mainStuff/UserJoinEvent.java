package mainStuff;

import musicBot.PlayerManager;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;
import java.util.TimerTask;
import java.util.Timer;

public class UserJoinEvent extends ListenerAdapter {
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        TextChannel mainLobChannel = event.getGuild().getTextChannelById("268904408883003393");
        TextChannel musicCommandChannel = event.getGuild().getTextChannelById("434989084952231936");
        VoiceChannel mainVoiceChannel = event.getGuild().getVoiceChannelById("268904409482657793");


        mainLobChannel.sendMessage("<:not_the_ts:497274254459666432>" + event.getMember().getAsMention() +
                "<:not_the_ts:497274254459666432>\n" +
                "Welcome to <:NTT:530905639774453800> **-Not The TeamSpeak-** <:NTT:530905639774453800>\n" +
                "Check out <#527994282447994880> to unlock more voice / text channels.").queue();

        //Gives new member Role
        Role memberRole = event.getGuild().getRoleById("435164384474431508");
        event.getGuild().getController().addRolesToMember(event.getMember(), memberRole).queue();

        //Plays song if user is in voice channel
        PlayerManager manager = PlayerManager.getInstances();
        AudioManager audioManager = event.getGuild().getAudioManager();
        Timer timer = new Timer();

        audioManager.openAudioConnection(mainVoiceChannel);
        manager.loadAndPlay(musicCommandChannel, "https://www.youtube.com/watch?v=HYV26AI6IAQ");
        manager.getGuildMusicManager(event.getGuild()).player.setVolume(30);

       //Two minute timer so bot leaves voice channel after two min
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                audioManager.closeAudioConnection();
            }
        }, 4*120*2000);


    }

}
