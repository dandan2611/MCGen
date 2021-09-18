package com.dandan2611.mcgen;

import com.dandan2611.mcgen.config.AppProvider;
import com.dandan2611.mcgen.config.AppState;
import com.dandan2611.mcgen.config.VersionsConfig;
import com.dandan2611.mcgen.provider.Provider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Main {

    static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static Provider RUNNING_PROVIDER;

    public static void main(String[] args) {
        // Command line args parsing

        Options options = new Options();

        Option stateOption = getOption("s", "state", true, "Select the starting state of the application", true);
        Option providerOption = getOption("p", "provider", true, "Select the provider for the servers (local)", true);
        Option versionOption = getOption("v", "version", true, "Select the version for the servers", true);

        Option helpOption = getOption("h", "help", false, "Show help text", false);

        options.addOption(stateOption);
        options.addOption(providerOption);
        options.addOption(versionOption);
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

        // Payload creation

        final StartupPayload startupPayload = new StartupPayload(provider, state, versionsConfig, enteredVersion);

        Class<? extends Provider> providerClass = provider.getProviderClass();
        try {
            Constructor<?> constructor = providerClass.getConstructor(null);
            Provider providerInstance = (Provider) constructor.newInstance();

            RUNNING_PROVIDER = providerInstance;

            LOGGER.info("Launching " + providerClass.getName() + " provider");

            providerInstance.init(commandLine, startupPayload);
        } catch (Exception e) {
            LOGGER.error("Unable to instantiate provider", e);
            System.exit(1);
        }


    }

    private static Option getOption(String shortName, String longName, boolean hasArg, String description, boolean required) {
        Option option = new Option(shortName, longName, hasArg, description);
        option.setRequired(required);
        return option;
    }

}
