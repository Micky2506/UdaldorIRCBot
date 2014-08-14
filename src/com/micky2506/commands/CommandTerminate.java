package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.MessageSource;

public class CommandTerminate implements ICommand
{
    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        Bot.express(channel, "Terminating all the things! Exterminate! Delete! Delete! Exterminate!");
    }

    @Override
    public String getCommandHelp()
    {
        return null;
    }

    @Override
    public boolean requiresOperator()
    {
        return false;
    }

    @Override
    public boolean requiresVoice()
    {
        return true;
    }

    @Override
    public void init()
    {

    }
}
