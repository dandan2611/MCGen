package com.dandan2611.mcgen;

import com.dandan2611.mcgen.config.AppProvider;
import com.dandan2611.mcgen.config.AppState;
import com.dandan2611.mcgen.config.VersionsConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class McGenMain {

    static final Logger LOGGER = LoggerFactory.getLogger(McGenMain.class);

    public static void main(String[] args) {
        // Command line args parsing

        Options options = new Options();

        Option stateOption = getOption("s", "state", true,
                "Select the starting state of the application (init, work, merge, cleanup)", true);
        Option providerOption = getOption("p", "provider", true,
                "Select the provider for the servers (local)", true);
        Option versionOption = getOption("v", "version", true,
                "Select the version for the servers", true);
        Option freshExecutableOption = getOption("fe", "fresh-executable", false,
                "Download a fresh executable and replace the cached one (if there is one)", false);
        Option cleanupOption = getOption("c", "cleanup", false,
                "Cleanup system before using it again", false);
        Option serversOptions = getOption("se", "servers", true,
                "Number of servers per worker (must be a multiple of 1,3,5,7,9)", true);

        Option helpOption = getOption("h", "help", false, "Show help text",
                false);

        options.addOption(stateOption);
        options.addOption(providerOption);
        options.addOption(versionOption);
        options.addOption(freshExecutableOption);
        options.addOption(cleanupOption);
        options.addOption(serversOptions);
        options.addOption(helpOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();
        CommandLine commandLine = null;

        try {
            commandLine = parser.parse(options, args);
        }
        catch(ParseException exception) {
            LOGGER.warn(exception.getMessage());
            helpFormatter.printHelp("mcgen", options);
            System.exit(1);
        }

        // Check if no options OR -h option

        // 0 || help
        if(commandLine.getOptions().length == 0
                || commandLine.hasOption(helpOption.getOpt())) {
            helpFormatter.printHelp("mcgen", options);
            System.exit(0);
        }

        // App state parsing

        String enteredState = commandLine.getOptionValue(stateOption.getOpt());
        AppState state = null;
        try {
            state = AppState.valueOf(enteredState.toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException exception) {
            LOGGER.error("Invalid state {}", enteredState);
            helpFormatter.printHelp("mcgen", options);
            System.exit(0);
        }

        // Versions config loading

        VersionsConfig versionsConfig = null;
        if(VersionsConfig.VERSIONS_CONFIG_FILE.exists()) {
            try {
                String content = FileUtils.readFileToString(VersionsConfig.VERSIONS_CONFIG_FILE, StandardCharsets.UTF_8);
                versionsConfig = new Gson().fromJson(content, VersionsConfig.class);
            } catch (IOException e) {
                LOGGER.error("Unable to load versions config file", e);
                System.exit(1);
            }
        }
        else {
            versionsConfig = new VersionsConfig();
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            try {
                new FileWriter(VersionsConfig.VERSIONS_CONFIG_FILE, StandardCharsets.UTF_8)
                        .append(gson.toJson(versionsConfig))
                        .flush();
            } catch (IOException e) {
                LOGGER.error("Unable to save versions config file", e);
            }
        }

        // Version option parsing

        String enteredVersion = commandLine.getOptionValue(versionOption.getOpt());
        if(versionsConfig.getVersion(enteredVersion) == null) {
            LOGGER.warn("Invalid version {}", enteredVersion);
            helpFormatter.printHelp("mcgen", options);
            System.exit(0);
        }

        // Provider parsing

        String enteredProvider = commandLine.getOptionValue(providerOption.getOpt());
        AppProvider provider = null;
        try {
            provider = AppProvider.valueOf(enteredProvider.toUpperCase(Locale.ROOT));
        }
        catch (IllegalArgumentException exception) {
            LOGGER.error("Invalid provider {}", enteredState);
            helpFormatter.printHelp("mcgen", options);
            System.exit(0);
        }

        // Application init
        McGenApplication application = new McGenApplication(commandLine, provider, state, versionsConfig, enteredVersion);
        application.init();

    }

    private static Option getOption(String shortName, String longName, boolean hasArg, String description, boolean required) {
        Option option = new Option(shortName, longName, hasArg, description);
        option.setRequired(required);
        return option;
    }

}
