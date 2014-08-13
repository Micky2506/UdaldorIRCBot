package com.micky2506.commands;

import com.micky2506.Bot;
import com.micky2506.lib.Configuration;
import com.micky2506.lib.MessageSource;
import com.micky2506.util.Utils;
import org.jibble.pircbot.Colors;

import java.util.ArrayList;
import java.util.List;

public class CommandRules implements ICommand
{
    @Override
    public void execute(String executedAlias, String sender, String channel, String[] args, MessageSource messageSource)
    {
        List<String> rules = new ArrayList<String>();
        rules.add("English only.");
        rules.add("Respect the operators and moderators.");
        rules.add("Operators have the final say.");
        rules.add("Keep language clean and without ANY objectionable content.");
        rules.add("Please do not spam anything, that includes pings.");
        rules.add("Do not promote any illegal activities.");
        rules.add("Please ask an operator before posting any links/URLs or advertisements.");
        rules.add("Do not ping anyone with a recording tag.");

        String ruleString;
        if (args.length == 1)
        {
            int ruleNumber = Integer.parseInt(args[0]);

            if (ruleNumber > rules.size())
            {
                Bot.notice(sender, String.format("Invalid rule ID. Maximum rule ID is %d.", rules.size()));
                return; // Don't output anything else.
            }

            ruleString = String.format("Rule #%d out of %s: %s", ruleNumber, rules.size(), Colors.RED + Colors.BOLD + rules.get(ruleNumber - 1) + Colors.NORMAL);
        }
        else
        {
            ruleString = "Rules: ";
            for (int ruleNumber = 0 ; ruleNumber < rules.size() ; ruleNumber++)
            {
                ruleString += String.format("%s%d. %s ",
                        ruleNumber % 2 == 0 ? Colors.RED + Colors.BOLD : Colors.NORMAL,
                        ruleNumber + 1,
                        rules.get(ruleNumber)
                );
            }
        }

        if (messageSource == MessageSource.CHANNEL)
        {
            if (Utils.isOP(sender, Configuration.CHANNEL))
            {
                Bot.message(Configuration.CHANNEL, ruleString);
            }
            else
            {
                Bot.notice(sender, ruleString);
            }
        }
        else if (messageSource == MessageSource.PRIVATE)
        {
            Bot.notice(sender, ruleString);
        }
    }

    @Override
    public String getCommandHelp()
    {
        return "Returns all rules, or `!rule <rule number>` to print out a specific rule.";
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
