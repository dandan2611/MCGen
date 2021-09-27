package com.dandan2611.mcgen.worker;

import com.dandan2611.mcgen.common.maven.PomReader;
import com.dandan2611.mcgen.worker.networking.NetworkingManager;
import org.apache.commons.cli.*;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Worker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public static final String COMMAND_LINE_SYNTAX = "worker";

    private NetworkingManager networkingManager;

    /**
     * Init worker
     * @param args Startup parameters
     */
    public void init(String[] args) {
        String version = "UNKNOWN";

        try {
            PomReader pomReader = new PomReader();
            version = pomReader.getModel().getVersion();
        } catch (IOException | XmlPullParserException e) {
            LOGGER.warn("Unable to read worker pom.xml", e);
        }

        LOGGER.info("--- Launching worker version {}", version);

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

        // NetworkingManager instantiation
        this.networkingManager = new NetworkingManager();

        // Check if master
        if(commandLine.hasOption(StartupOptions.MASTER_OPTION.getOpt())) {
            LOGGER.info("Launching as master");
            networkingManager.initMaster();
        }

        String connectIp = commandLine.getOptionValue(StartupOptions.CONNECT_OPTION.getOpt());

        LOGGER.info("Specified master ip: {}", connectIp);
        networkingManager.initWorker(connectIp, 44123);
    }

    public static void printHelp() {
        new HelpFormatter().printHelp(COMMAND_LINE_SYNTAX, StartupOptions.OPTIONS);
    }

}
