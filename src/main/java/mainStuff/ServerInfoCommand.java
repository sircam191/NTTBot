package mainStuff;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.format.DateTimeFormatter;

public class ServerInfoCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //SERVER INFO COMMAND
        if (args[0].equalsIgnoreCase(Main.prefix + "server") || args[0].equalsIgnoreCase(Main.prefix + "serverinfo")) {
            Guild guild = event.getGuild();

            //get online members
            int online = 0;
            for (Member member : event.getGuild().getMembers()) {
                if (!member.getOnlineStatus().equals(OnlineStatus.OFFLINE)) {
                    ++online;
                }
            }

            //get offline members
            int offline = 0;
            for (Member member : event.getGuild().getMembers()) {
                if (!member.getOnlineStatus().equals(OnlineStatus.ONLINE)) {
                    ++offline;
                }
            }


                EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Server info for " + guild.getName());
                embed.setThumbnail(guild.getIconUrl());
                embed.addField("**General Info**", "\nOwner: " + event.getGuild().getMemberById("182729649703485440").getAsMention() + "\nRegion: " + guild.getRegion().getName() + "\nCreation Date: " + guild.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
                embed.addField("Member Info ", "Total Members: " + Integer.toString(event.getGuild().getMembers().size()) + "\nOnline Members: " + online + "\nOffline Members: " + offline, false);


                //embed.addField("Role And Member Counts", memberInfo, false);


                event.getChannel().sendMessage(embed.build()).queue();


            }
        }
    }
