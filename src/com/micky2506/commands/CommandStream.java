package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.MessageSource;
import com.micky2506.lib.Messages;
import com.micky2506.util.Utils;
import com.sun.deploy.util.StringUtils;

import java.util.Arrays;

public class CommandStream implements ICommand
{
    private static String url = "";

    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        if (args.length > 0 && Utils.isOP(sender, channel))
        {
            if (args[0].equalsIgnoreCase("clear"))
            {
                url = "";
                Bot.notice(sender, "Cleared stream URL");
            }
            else
            {
                url = StringUtils.join(Arrays.asList(args), " ");
                Bot.notice(sender, String.format("Successfully set stream URL to '%s'", url));
            }
        }
        else
        {
            if (url.isEmpty())
            {
                if (Utils.isOP(sender, channel))
                {
                    Bot.message(channel, Messages.NO_STREAM_URL);
                }
                else
                {
                    Bot.notice(sender, Messages.NO_STREAM_URL);
                }
            }
            else
            {
                if (Utils.isOP(sender, channel))
                {
                    Bot.message(channel, String.format(Messages.STREAM_URL_FORMAT, url));
                }
                else
                {
                    Bot.notice(sender, String.format(Messages.STREAM_URL_FORMAT, url));
                }
            }
        }
    }

    @Override
    public String getCommandHelp()
    {
        return "Returns the current stream URL. Operators can set the stream URL with `!stream <URL>`.";
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

    @Override
    public void init()
    {

    }
}
