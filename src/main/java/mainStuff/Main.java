package mainStuff;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;
    public static String prefix = "?";

    //mainStuff.Main Method
    public static void main (String[] args) throws LoginException{
        jda = new JDABuilder(AccountType.BOT).setToken("TOPSECRETTOKEN").build();

        //Sets Bot Presence
        //jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.getPresence().setGame(Game.listening("-Not The TeamSpeak-"));
        jda.addEventListener(new Commands());
        jda.addEventListener(new UserJoinEvent());
        jda.addEventListener(new mainStuff.SayCommand());
        jda.addEventListener(new UserBanEvent());
        jda.addEventListener(new AudioCommands());
        jda.addEventListener(new PlayCommand());
        jda.addEventListener(new ServerInfoCommand());
        jda.addEventListener(new StopCommand());
        jda.addEventListener(new QueueCommand());
        jda.addEventListener(new SkipCommand());
        jda.addEventListener(new NowPlayingCommand());
        jda.addEventListener(new userLeaveVoiceEvent());
        jda.addEventListener(new StickyCommand());


        /////////////////////////////////////////// time test thing
        try {
            while (true) {
                jda.getPresence().setStatus(OnlineStatus.ONLINE);
                Thread.sleep(2500);
                jda.getPresence().setGame(Game.playing("-Not The TeamSpeak-"));
                Thread.sleep(2500);
                jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                Thread.sleep(2500);
                jda.getPresence().setGame(Game.listening("?help"));
                Thread.sleep(2500);
                jda.getPresence().setStatus(OnlineStatus.IDLE);
                Thread.sleep(2500);
                jda.getPresence().setGame(Game.watching("You Sleep"));
                Thread.sleep(2500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
