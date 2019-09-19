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
        Role regulars = event.getGuild().getRoleById("436654248906850304");


        //ignore bots.
        if(event.getAuthor().isBot()) {
            return;
        }
        if ((textInput[0].equalsIgnoreCase(Main.prefix + "say"))) {
            if (event.getMember().getRoles().contains(bigBois) || event.getMember().getRoles().contains(bigBois) ) {
                try {
                    TextChannel aChannel = event.getMessage().getMentionedChannels().get(0);
                    String messageToSay = event.getMessage().getContentRaw().substring(4).replaceAll(aChannel.getAsMention(), "");
                    aChannel.sendMessage(messageToSay).queue();
                    event.getMessage().addReaction("\u2705").queue();
               }
                catch (Exception e) {
                    event.getChannel().sendMessage("__ERROR__: Use this format: ``?say <Channel Name> <Text to say>``").queue();
                    event.getMessage().addReaction("\u274C").queue();
                }
            }
            else {
                event.getChannel().sendMessage("Sorry dogg only ``BIG BOIS`` and ``REGULARS`` can use this sick ass command...").queue();
                event.getMessage().addReaction("\u274C").queue();
            }
        }

    }


}
