package mainStuff;

import mainStuff.Main;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class SayCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] textInput = event.getMessage().getContentRaw().split("\\s+", 3);
        Role bigBois = event.getGuild().getRoleById("434983695288631297");
        TextChannel sayChannel = event.getGuild().getTextChannelsByName(textInput[1],false).get(0);
        //TODO Fix dis shit &
        //if sayChannel = null give warning

        // Good practise to ignore bots.
        if(event.getAuthor().isBot()) {
            return;
        }
        if ((textInput[0].equalsIgnoreCase(Main.prefix + "say"))) {
            if (event.getMember().getRoles().contains(bigBois) ) {

                try {
                    sayChannel.sendMessage(textInput[2]).queue();
                    //event.getGuild().getTextChannelsByName(textInput[1], true).get(0).sendMessage(textInput[2]).queue();

               }
                catch (Exception e) {
                    event.getChannel().sendMessage("__ERROR__: Use this format: ``?say <Channel Name> <Text to say>``").queue();
                }
            }
            else {
                event.getChannel().sendMessage("__ERROR__: ``You must be a Big Boi to use this command``").queue();
            }
        }

    }


}