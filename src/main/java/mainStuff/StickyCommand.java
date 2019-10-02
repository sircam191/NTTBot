package mainStuff;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class StickyCommand extends ListenerAdapter {
    String stickId = null;
    String stickMessage = null;
    TextChannel stickChannel = null;
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Member nttBot = event.getGuild().getMemberById("603112523973001216");

        Role bigBois = event.getGuild().getRoleById("434983695288631297");
        Role StickTester = event.getGuild().getRoleById("625830975095570442");


        ///////////////////////////

        if(stickMessage != null) {
            event.getChannel().getHistory().retrievePast(3).queue(m -> {
                for (int i = 1; i < 3; i++) {
                    if (m.get(i).getContentDisplay().contains(stickMessage)) {
                        m.get(i).delete().queue();
                    }
                }
            });
        }
        //////////////////////////////

        if (event.getMember() == nttBot && (event.getChannel() == stickChannel) && (event.getMessage().getContentDisplay().contains(stickMessage) )) {
            stickId = event.getMessageId();
        }

        if(stickId != null && event.getMember() != nttBot && stickMessage != null && (event.getChannel() == stickChannel ))  {
            if(!args[0].equalsIgnoreCase(Main.prefix + "stickstop")) {
                event.getChannel().sendMessage(stickMessage).queue();
            }
            event.getChannel().deleteMessageById(stickId).queue();
        }

        if (args[0].equalsIgnoreCase(Main.prefix + "stick") && (event.getMember().getRoles().contains(bigBois) ||  event.getMember().getRoles().contains(StickTester))) {
          try {
              stickMessage = "__**Pinned Message:**__\n\n";
              stickMessage += event.getMessage().getContentRaw().substring(7);
              stickChannel = event.getChannel();
              if(!args[0].equalsIgnoreCase(Main.prefix + "stickstop")) {
                  event.getChannel().sendMessage(stickMessage).queue();
              }
              event.getMessage().addReaction("\u2705").queue();

         } catch (Exception e) {
               event.getChannel().sendMessage("Use the format ``?stick <message>``").queue();
               event.getMessage().addReaction("\u274C").queue();
          }
    }

        if (args[0].equalsIgnoreCase(Main.prefix + "stickstop") && (event.getMember().getRoles().contains(bigBois) ||  event.getMember().getRoles().contains(StickTester))) {
            stickId = null;
            stickMessage = null;
            event.getMessage().addReaction("\u2705").queue();
        }

        if ((args[0].equalsIgnoreCase(Main.prefix + "stickstop") || args[0].equalsIgnoreCase(Main.prefix + "stick") ) && (!event.getMember().getRoles().contains(bigBois) &&  !event.getMember().getRoles().contains(StickTester))) {
            event.getMessage().addReaction("\u274C").queue();
        }

    }
}
