package com.dandan2611.mcgen.worker;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public static final String COMMAND_LINE_SYNTAX = "worker";

    /**
     * Init worker
     * @param args Startup parameters
     */
    public void init(String[] args) {
        LOGGER.info("--- Launching worker");

        // Command line parsing
        CommandLine commandLine = null;
        try {
            commandLine = new DefaultParser().parse(StartupOptions.OPTIONS, args);
        }
        catch(ParseException exception) {
            LOGGER.error("Unable to parse given arguments", exception);
            printHelp();
            System.exit(1);
        }

        // Check if no options OR -h option
        if(commandLine.getOptions().length == 0
                || commandLine.hasOption(StartupOptions.HELP_OPTION.getOpt())) {
            printHelp();
            System.exit(0);
        }

    }

    public static void printHelp() {
        new HelpFormatter().printHelp(COMMAND_LINE_SYNTAX, StartupOptions.OPTIONS);
    }

}