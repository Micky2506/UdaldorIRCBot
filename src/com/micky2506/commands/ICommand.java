package com.micky2506.commands;

import com.micky2506.lib.MessageSource;

public interface ICommand
{
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource);

    public String getCommandHelp();

    public boolean requiresOperator();
    public boolean requiresVoice();

    public void init();
}
