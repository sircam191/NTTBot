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
import java.util.Random;

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
                    "``?poll <your question>`` - Turns your question into a poll.\n" +
                    "``?say <#text-channel> <Message>`` - Bot says your message in the tagged channel (Admin only).\n" +
                    "``?userinfo <@user>`` - Get the stats on a user.\n" +
                    "``?ask <question>`` - The bot will answer your yes or no question.\n" +
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


        if (args[0].equalsIgnoreCase(Main.prefix + "ask")) {
            String answers[] = {"F**k No", "No one loves you, *", "It is possible", "100% yes", "Eat some a** then ask again", "I guess", "Hell naw", "Yessir", "naww dogg", "Ask me again...", "** my *** wee", "You will never know", "Yes", "No", "Prob nawt", "I think yes", "I think nope", "Impossible"};
            //18 options

            //if the used command but did not ask a question
            if(event.getMessage().getContentRaw().length() <= 4) {
                event.getChannel().sendMessage("You gotta ask my a question you dingle berry").queue();
            } else {
                Random rand = new Random();
                int randomInt = rand.nextInt(17);
                event.getChannel().sendMessage(answers[randomInt]).queue();
            }

        }

        }

    }
