package com.micky2506;

import com.micky2506.commands.*;
import com.micky2506.lib.Configuration;
import com.micky2506.lib.MessageSource;
import org.jibble.pircbot.PircBot;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class Bot extends PircBot
{
    public static Bot instance;

    public static HashMap<String, String> messages = new HashMap<String, String>();

    public Bot()
    {
        this.setName("UdaBot");
        Bot.instance = this;
    }

    public void init()
    {

        setVerbose(true);

        try
        {
            connect("irc.esper.net");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        identify(Configuration.NICK_SERV_PASSWORD);
        this.changeNick("DoorBot");

        CommandRegistry.registerCommand("rules", new CommandRules());
        CommandRegistry.registerCommand("stream", new CommandStream());
        CommandRegistry.registerCommand("help", new CommandHelp());
        CommandRegistry.registerCommand("alias", new CommandAlias());
        CommandRegistry.registerCommand("info", new CommandInfo());

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new CommandRegistry(), 0, 2000);
    }

    @Override
    protected void onNotice(String sourceNick, String sourceLogin, String sourceHostname, String target, String notice)
    {
        super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
        if (sourceNick.equalsIgnoreCase("NickServ") && notice.contains("You are now identified for"))
        {
            Bot.instance.setMessageDelay(0);
            joinChannel(Configuration.CHANNEL);
        }
    }

    @Override
    protected void onJoin(String channel, String sender, String login, String hostname)
    {
        super.onJoin(channel, sender, login, hostname);

        if (sender.equals(this.getNick()))
            Bot.message(channel, this.getNick() + " is now running.");
        else
            CommandRegistry.executeCommand("rules", sender, channel, MessageSource.PRIVATE);
    }

    @Override
    protected void onKick(String channel, String kickerNick, String kickerLogin, String kickerHostname, String recipientNick, String reason)
    {
        if (recipientNick.equalsIgnoreCase(getNick()))
        {
            joinChannel(channel);
        }
        super.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick, reason);
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message)
    {
        super.onPrivateMessage(sender, login, hostname, message);
        if (message.startsWith("!"))
        {
            CommandRegistry.executeCommand(message.substring(1), sender, Configuration.CHANNEL, MessageSource.PRIVATE);
        }
        else
        {
            Bot.message(sender, "Huh? I am a bot. Perhaps you wish to speak to my creator? Please message " + Configuration.CREATOR + " for issues/concerns/suggestions.");
            Bot.message(Configuration.CREATOR, "This was messaged to me by " + sender + " : " + message);
        }
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        if (message.startsWith("!"))
        {
            CommandRegistry.executeCommand(message.substring(1), sender, channel, MessageSource.CHANNEL);
        }
    }

    public static void notice(String target, String notice)
    {
        Bot.instance.sendNotice(target, notice);
    }

    public static void notice(String target, List<String> notices)
    {
        for (String notice : notices)
        {
            notice(target, notice);
        }
    }

    public static void message(String target, String message)
    {
        Bot.instance.sendMessage(target, message);
    }

    public static void message(String target, List<String> messages)
    {
        for (String message : messages)
        {
            message(target, message);
        }
    }
}

