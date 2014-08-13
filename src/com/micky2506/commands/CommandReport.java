package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.MessageSource;
import com.sun.deploy.util.StringUtils;

import java.util.Arrays;

public class CommandReport implements ICommand
{
    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        Bot.message("Micky2506", "Error report by " + sender + ": " + StringUtils.join(Arrays.asList(args), " "));
    }

    @Override
    public String getCommandHelp()
    {
        return "Report a issue with the bot.";
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
