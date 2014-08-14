package com.micky2506;

import com.micky2506.commands.CommandRegistry;
import org.jibble.pircbot.IrcException;

import java.io.IOException;
import java.util.Timer;

public class Main {

    public static void main(String[] args) throws IrcException, IOException
    {
        // Now start our bot up.
        Bot bot = new Bot();
        bot.init();
    }
}
