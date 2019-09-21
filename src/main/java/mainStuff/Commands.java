package mainStuff;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.MalformedURLException;
import java.net.URL;

import static mainStuff.Main.jda;

public class Commands extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        //Uptime time stuff
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime();
        long uptimeInSeconds = uptime / 1000;
        long numberOfHours = uptimeInSeconds / (60 * 60);
        long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
        long numberOfSeconds = uptimeInSeconds % 60;


        //PING
        if (args[0].equalsIgnoreCase(Main.prefix + "ping")) {
            event.getChannel().sendMessage("Pong!" + "\n> WebSocket Latency: " + Long.toString(jda.getPing()) + "ms").queue();
        }

        //HELP or COMMANDS
        if (args[0].equalsIgnoreCase(Main.prefix + "help") || args[0].equalsIgnoreCase(Main.prefix + "commands")) {
            Emote nttEmote = event.getGuild().getEmotesByName("NTT", false).get(0);
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("**Commands**");
            embed.setColor(Color.CYAN);
            embed.addField("Basic Commands:", "``?ping`` - Bot responds with Pong!\n" +
                    "``?invite`` - Get invite to this server.\n" +
                    "``?twitch <full twitch link>`` - Set the bots streaming link.\n" +
                    "``?roll`` - Rolls 2 dice.\n" +
                    "``?poll <your question>`` - Turns your question into a poll\n" +
                    "``?say <#text-channel> <Message>`` - Bot says your message in the tagged channel\n" +
                    "``?userinfo <@user>`` - Get the stats on a user\n" +
                    "``?about`` - Bot Info.\n", false);

            embed.addField("Music Commands:", "``?play <Song name or Link>`` - Plays song.\n" +
                    "``?join`` - Joins your current voice channel.\n" +
                    "``?leave`` - Leaves voice channel.\n" +
                    "``?pause``- Pauses current song.\n" +
                    "``?queue`` - Shows song queue.\n" +
                    "``?skip`` - Skips current song.\n" +
                    "``?np`` - Show current playing song.\n" +
                    "``?clear`` - Clears the queue", false);


            event.getChannel().sendMessage(embed.build()).queue(m -> {
                m.addReaction(nttEmote).queue();
            });
        }

        //INVITE
        if (args[0].equalsIgnoreCase(Main.prefix + "invite")) {
            event.getChannel().sendMessage("https://discord.gg/KbZvHHb").queue();
        }

        //ABOUT
        if (args[0].equalsIgnoreCase(Main.prefix + "about")) {
            Emote nttEmote = event.getGuild().getEmotesByName("NTT", false).get(0);
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.cyan);
            eb.setTitle("__**NTT Bot Information:**__");
            eb.addField("Developed By:", event.getGuild().getOwner().getAsMention(), false);
            eb.addField("Development: ", "-Coded in Java using [JDA](https://github.com/DV8FromTheWorld/JDA).\n-Music uses [LavaPlayer](https://github.com/sedmelluq/lavaplayer) API.\n-Built using [Gradle](https://gradle.org/).", false);
            eb.addField("Ping: ", Long.toString(jda.getPing()) + "ms", false);
            eb.addField("Uptime:", "``" + numberOfHours + " Hours, " + numberOfMinutes + " Min, " + numberOfSeconds + " Seconds``", true);
            eb.addField("Source Code:", "[``GitHub``](https://github.com/sircam191/NTTBot)", false);
            eb.addField("Commands: ", "Do ``?commands`` or ``?help``", false);
            event.getChannel().sendMessage(eb.build()).queue(m -> {
                m.addReaction(nttEmote).queue();
            });
        }

            //SHUTDOWN
            if (args[0].equalsIgnoreCase(Main.prefix + "shutdown")) {
                if (event.getMember().isOwner()) {
                    event.getChannel().sendMessage("```Shutting Down Bot```").queue();
                    jda.shutdown();
                } else {
                    event.getChannel().sendMessage("Only ``P_O_G#2222`` can use this command.").queue();
                }
            }

            //JOIN DATE
            if (args[0].equalsIgnoreCase(Main.prefix + "joindate")) {

                try {

                    Member taggedMember = event.getMessage().getMentionedMembers().get(0);
                    String joinDateClean = String.valueOf(taggedMember.getJoinDate().getMonth() + " " + String.valueOf(taggedMember.getJoinDate().getDayOfMonth()) + ", " + String.valueOf(taggedMember.getJoinDate().getYear()));
                    event.getChannel().sendMessage(taggedMember.getEffectiveName() + " Joined: " + joinDateClean).queue();
                } catch (Exception e) {
                    event.getChannel().sendMessage("You gotta tag the dude u want me to get the join date for.").queue();
                }
            }

            //UPTIME
            if (args[0].equalsIgnoreCase(Main.prefix + "uptime")) {
                event.getChannel().sendMessage("Uptime: ``" + numberOfHours + " Hours, " + numberOfMinutes + " Min, " + numberOfSeconds + " Seconds``").queue();
            }

            //TWITCH
            if (args[0].equalsIgnoreCase(Main.prefix + "twitch")) {

                try {
                    if (!args[1].isEmpty() && args[1].startsWith("https://www.twitch.tv/")) {
                        Main.twitchLink = args[1];
                        event.getChannel().sendMessage("Setting my twitch link to: ``" + Main.twitchLink + "``").queue();
                    } else {
                        event.getChannel().sendMessage("Looks like that's not a twitch link dude.").queue();
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("You gotta provide me with a twitch link my dude ").queue();
                }
            }

            //POLL
            if (args[0].equalsIgnoreCase(Main.prefix + "poll")) {
                String pollQ;

                try {
                    if (!args[1].isEmpty()) {
                        pollQ = String.join(" ", args).substring(5);

                        event.getMessage().delete().queue();

                        EmbedBuilder emb = new EmbedBuilder();

                        emb.setColor(Color.BLACK);
                        emb.setFooter("Poll by: " + event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl());

                        emb.setTitle("**" + pollQ + "**");

                        //adds reactions to poll message
                        event.getChannel().sendMessage(emb.build()).queue(m -> {
                            m.addReaction("\ud83d\udc4d").queue();
                            m.addReaction("\uD83D\uDC4E").queue();
                        });
                    }
                } catch (Exception e) {
                    event.getChannel().sendMessage("You gotta tell me the thing u wanna poll").queue();
                }
            }

            //DICE ROLL
            if (args[0].equalsIgnoreCase(Main.prefix + "dice") || args[0].equalsIgnoreCase(Main.prefix + "roll")) {
                int dice1 = (int) (Math.random() * 6 + 1);
                int dice2 = (int) (Math.random() * 6 + 1);
                EmbedBuilder emb = new EmbedBuilder();

                //emb.setTitle("Two Dice Rolled");
                emb.addField("Dice 1:    **" + Integer.toString(dice1) + "**", "", false);
                emb.addField("Dice 2:   **" + Integer.toString(dice2) + "**", "", false);
                emb.addField("**TOTAL:**   **" + Integer.toString(dice1 + dice2) + "**", "", false);
                emb.setColor(Color.RED);
                emb.setThumbnail("https://media.giphy.com/media/5nxHFn5888nrq/giphy.gif");
                event.getChannel().sendMessage(emb.build()).queue();

            }

            //USER INFO
            if (args[0].equalsIgnoreCase(Main.prefix + "userinfo")) {

              try {
                   User tagUser = event.getMessage().getMentionedUsers().get(0);
                   Member taggedMember = event.getMessage().getMentionedMembers().get(0);
                   EmbedBuilder emb = new EmbedBuilder();
                   String joinDateClean = String.valueOf(taggedMember.getJoinDate().getMonth() + " " + String.valueOf(taggedMember.getJoinDate().getDayOfMonth()) + ", " + String.valueOf(taggedMember.getJoinDate().getYear()));

                //Adds user roles as mentions into String
                int i = taggedMember.getRoles().size();
                String rolesTagged = "";
                while( i > 0) {
                    rolesTagged += taggedMember.getRoles().get(i -1).getAsMention();
                    rolesTagged += " ";
                    i--;
                }
                //////

                emb.setThumbnail(tagUser.getAvatarUrl());
                emb.setTitle("**User Info**");
                emb.addField("Info for " + taggedMember.getEffectiveName() + "#" + tagUser.getDiscriminator(),
                           "**User ID:** " + tagUser.getId() + "\n" +
                           "**Nickname:** " + tagUser.getName() + "\n" +
                            "**Join Date:** " + joinDateClean + "\n" +
                            "**Status:** " + taggedMember.getOnlineStatus().toString() + "\n" +
                            "**Tag: ** " + taggedMember.getAsMention() + "\n" +
                            "**Number of Roles:** " + taggedMember.getRoles().size() + "\n" +
                              "**Roles:** " + rolesTagged
                           , false);

                    emb.setColor(taggedMember.getColor());


                    if(rolesTagged.contains("434983695288631297")) {
                        emb.setFooter(taggedMember.getEffectiveName() + " is a Admin", tagUser.getAvatarUrl());
                    }
                    else {
                        emb.setFooter(taggedMember.getEffectiveName() + " is Not a Admin", tagUser.getAvatarUrl());
                    }
                   event.getChannel().sendMessage(emb.build()).queue();

              } catch (Exception e) {
                    event.getChannel().sendMessage("Tag the member you want me to get info for.").queue();
              }

            }

        }

    }
