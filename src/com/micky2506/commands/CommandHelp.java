package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.Configuration;
import com.micky2506.lib.MessageSource;
import com.micky2506.lib.Messages;
import com.micky2506.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.micky2506.lib.Messages.AVAILABLE_COMMANDS;

public class CommandHelp implements ICommand
{
    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        if (args.length == 1)
        {
            if (Utils.isAllowedToExecute(sender, args[0], Configuration.CHANNEL))
                Bot.notice(sender, CommandRegistry.getCommandHelp(args[0]));
            else
                Bot.notice(sender, Messages.NOT_A_COMMAND);
        }
        else
        {
            List<String> help = new ArrayList<String>();
            for (String commandName : CommandRegistry.commands.keySet())
            {
                if (Utils.isAllowedToExecute(sender, commandName, Configuration.CHANNEL))
                {
                    help.add("!" + commandName);
                }
            }

            Bot.notice(sender, AVAILABLE_COMMANDS + Utils.commaSeperate(help));
        }

    }

    @Override
    public String getCommandHelp()
    {
        return "List of commands, use `!help <command>` for a description of each command.";
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
        CommandRegistry.addAlias(this, "commands");
    }
}
