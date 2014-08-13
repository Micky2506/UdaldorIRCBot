package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.Configuration;
import com.micky2506.lib.MessageSource;
import com.micky2506.util.Utils;
import org.jibble.pircbot.Colors;
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommandInfo implements ICommand
{
    private static HashMap<String, String> info = new HashMap<String, String>();

    @Override
    public void init()
    {
        addValuesToInfo();
        CommandRegistry.addAlias(this, info.keySet());
    }

    private void addValuesToInfo()
    {
        info.put("Site", "http://udaldor.com");
        info.put("Twitter", "http://twitter.com/Udaldor");
        info.put("Youtube", "http://youtube.com/Udaldor");
        info.put("Patreon", "http://patreon.com/Udaldor");
        info.put("Teamspeak", "udaldor.com");
    }

    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        info.clear();
        addValuesToInfo();

        if (executedAlias.equals("info"))
        {
            String message = "Udaldor's Online Information: ";
            List<String> infoMessage = new ArrayList<String>(info.size());
            for (String infoKey : info.keySet())
            {
                infoMessage.add(String.format("%s: %s", infoKey, Colors.BLUE + info.get(infoKey) + Colors.NORMAL));
            }
            message += Utils.commaSeperate(infoMessage);
            noticeChannelOrUser(messageSource, sender, message);
        }
        else
        {
            for (String infoKey : info.keySet())
            {
                if (infoKey.equalsIgnoreCase(executedAlias))
                {
                    noticeChannelOrUser(messageSource, sender, String.format("%s: %s", infoKey, Colors.BLUE + info.get(infoKey) + Colors.NORMAL));
                }
            }
        }
    }

    private static void noticeChannelOrUser(MessageSource messageSource, String sender, String message)
    {
        if (messageSource == MessageSource.CHANNEL && Utils.isOP(sender, Configuration.CHANNEL))
        {
            Bot.message(Configuration.CHANNEL, message);
        }
        else
        {
            Bot.notice(sender, message);
        }
    }

    @Override
    public String getCommandHelp()
    {
        return "Returns various information, including twitter, youtube and site URLs.";
    }

    @Override
    public boolean requiresOperator()
    {
        return false;
    }

    @Override
    public boolean requiresVoice()
    {
        return false;
    }
}
