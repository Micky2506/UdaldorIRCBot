package com.micky2506.util;

import com.micky2506.Bot;
import com.micky2506.commands.CommandRegistry;
import com.micky2506.commands.ICommand;
import com.sun.deploy.util.StringUtils;
import org.jibble.pircbot.User;

import java.util.ArrayList;
import java.util.List;

public class Utils
{
    public static void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isOP(String sender, String channel)
    {
        User[] users = Bot.instance.getUsers(channel);

        for (User user : users)
        {
            if (user.getNick().equals(sender) && user.isOp())
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isVoiced(String sender, String channel)
    {
        User[] users = Bot.instance.getUsers(channel);

        for (User user : users)
        {
            if (user.getNick().equals(sender) && user.hasVoice())
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isSpecial(String sender, String channel)
    {
        return Utils.isOP(sender, channel) || Utils.isVoiced(sender, channel);
    }

    public static String commaSeperate(List<String> list)
    {
        String string = StringUtils.join(list, ", ") + ".";
        return string;
    }

    public static boolean isAllowedToExecute(String sender, String commandName, String channel)
    {
        ICommand command = CommandRegistry.getCommand(commandName);
        if (command.requiresOperator() && Utils.isOP(sender, channel))
            return true;

        if (command.requiresVoice() && (Utils.isOP(sender, channel) || Utils.isVoiced(sender, channel)))
            return true;

        if (!command.requiresOperator() && !command.requiresVoice())
            return true;

        return false; // Should never happen
    }

    public static List<String> cloneList(List<String> list)
    {
        List<String> clonedList = new ArrayList<>(list.size());
        for (String string : list)
        {
            clonedList.add(string);
        }
        return clonedList;
    }

    public static boolean isGreeting(String message)
    {
        if (message.contains("hello")) return true;
        if (message.contains("o/")) return true;
        if (message.contains("\\o")) return true;
        if (message.contains("\\o/")) return true;
        if (message.contains("hi")) return true;
        return false;
    }
}
