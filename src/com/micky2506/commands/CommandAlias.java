package com.micky2506.commands;

import com.micky2506.lib.MessageSource;

public class CommandAlias implements ICommand
{
    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        CommandRegistry.addAlias(args[0], args[1]);
    }

    @Override
    public String getCommandHelp()
    {
        return null;
    }

    @Override
    public boolean requiresOperator()
    {
        return true;
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
