package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.Configuration;
import com.micky2506.lib.MessageSource;
import com.micky2506.lib.Messages;
import com.micky2506.util.Utils;

import java.util.*;

public class CommandRegistry
{
    private static final int TIME_BETWEEN_COMMANDS = 3; // In seconds
    private static final int COMMAND_AMOUNT = 3; // In seconds
    public static HashMap<String, ICommand> commands = new HashMap<String, ICommand>();
    private static HashMap<String, List<String>> commandAliases = new HashMap<String, List<String>>();
    private static HashMap<String, Long> lastCommandTimes = new HashMap<String, Long>();
    private static HashMap<String, Integer> commandAmounts = new HashMap<String, Integer>();

    public static void addAlias(String originalCommand, String alias)
    {
        List<String> aliases = commandAliases.get(originalCommand);
        if (!aliases.contains(alias))
            aliases.add(alias);
    }

    public static void addAlias(ICommand command, Set<String> aliases)
    {
        for (String alias : aliases)
        {
            addAlias(getCommandName(command), alias);
        }
    }

    public static void registerCommand(String commandName, ICommand command)
    {
        commands.put(commandName, command);
        commandAliases.put(commandName, new ArrayList<String>());
        command.init();
    }

    public static void executeCommand(String message, String sender, String channel, MessageSource source)
    {
        String[] args = message.split("\\s+");
        String commandName = args[0].toLowerCase();

        if (!lastCommandTimes.containsKey(sender))
        {
            lastCommandTimes.put(sender, System.currentTimeMillis() - TIME_BETWEEN_COMMANDS*1000);
        }

        if (!commandAmounts.containsKey(sender))
        {
            commandAmounts.put(sender, 0);
        }

        if (System.currentTimeMillis() - lastCommandTimes.get(sender) > TIME_BETWEEN_COMMANDS*1000)
        {
            commandAmounts.put(sender, 0);
            lastCommandTimes.put(sender, System.currentTimeMillis() - TIME_BETWEEN_COMMANDS*1000);
        }

        if (commandAmounts.get(sender) >= COMMAND_AMOUNT && !Utils.isOP(sender, channel))
        {
            commandAmounts.put(sender, 0);
            Bot.instance.kick(channel, sender, "Too many commands!");
            return;
        }

        if (System.currentTimeMillis() - lastCommandTimes.get(sender) < TIME_BETWEEN_COMMANDS*1000 && !Utils.isOP(sender, Configuration.CHANNEL))
        {
            Bot.instance.sendNotice(sender, "Please wait " + TIME_BETWEEN_COMMANDS + " seconds between commands.");
            Bot.instance.sendNotice(sender, "Time left before next command: " + (TIME_BETWEEN_COMMANDS - (System.currentTimeMillis() - lastCommandTimes.get(sender))/1000) + " seconds.");
            commandAmounts.put(sender, commandAmounts.get(sender) + 1);
            return;
        }

        commandAmounts.put(sender, commandAmounts.get(sender) + 1);
        lastCommandTimes.put(sender, System.currentTimeMillis());

        ICommand command = getCommand(commandName);

        if (command != null)
        {
            args = Arrays.copyOfRange(args, 1, args.length);

            if (command.requiresOperator() || command.requiresVoice())
            {
                if (command.requiresOperator())
                {
                    if (Utils.isOP(sender, Configuration.CHANNEL))
                    {
                        command.execute(commandName, sender, Configuration.CHANNEL, args, source);
                    }
                    else
                    {
                        Bot.notice(sender, Messages.REQUIRES_OPERATOR);
                    }
                }
                else if (command.requiresVoice())
                {
                    if (Utils.isSpecial(sender, Configuration.CHANNEL))
                    {
                        command.execute(commandName, sender, Configuration.CHANNEL, args, source);
                    }
                    else
                    {
                        Bot.instance.sendMessage(sender, Messages.REQUIRES_VOICE);
                    }
                }
            }
            else
            {
                command.execute(commandName, sender, Configuration.CHANNEL, args, source);
            }
        }
        else if (commandName.matches("[\\w\\d]+"))
        {
            Bot.instance.sendNotice(sender, Messages.NOT_A_COMMAND);
        }
    }

    public static ICommand getCommand(String commandName)
    {
        for (String command : commands.keySet())
        {
            if (commands.get(command) == commands.get(commandName))
            {
                return commands.get(command);
            }

            for (String commandAlias : commandAliases.get(command))
            {
                if (commandAlias.equalsIgnoreCase(commandName))
                {
                    return commands.get(command);
                }
            }
        }
        return null;
    }

    public static String getCommandName(ICommand keyCommand)
    {
        for (String command : commands.keySet())
        {
            if (getCommand(command) == keyCommand)
            {
                return command;
            }
        }
        return null;
    }

    public static List<String> getCommandHelp(String commandName)
    {
        ICommand command = commands.get(commandName);
        List<String> commandHelp = new ArrayList<String>();
        if (command != null)
        {
            List<String> aliases = getCommandAliases(command);
            if (aliases.size() != 0)
            {
                commandHelp.add(Utils.commaSeperate(aliases));
            }
            commandHelp.add(command.getCommandHelp());
            return commandHelp;
        }
        return commandHelp;
    }

    private static List<String> getCommandAliases(ICommand command)
    {
        return commandAliases.get(getCommandName(command));
    }
}
