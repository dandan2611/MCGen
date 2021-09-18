package com.dandan2611.mcgen;

import com.dandan2611.mcgen.config.AppProvider;
import com.dandan2611.mcgen.config.AppState;
import com.dandan2611.mcgen.config.VersionsConfig;
import com.dandan2611.mcgen.provider.Provider;
import org.apache.commons.cli.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class McGenApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(McGenApplication.class);

    private final CommandLine commandLine;

    private final AppProvider provider;
    private final AppState state;
    private final VersionsConfig versionsConfig;
    private final String version;

    private Provider runningProvider;

    protected McGenApplication(CommandLine commandLine, AppProvider provider, AppState state, VersionsConfig versionsConfig, String version) {
        this.commandLine = commandLine;
        this.provider = provider;
        this.state = state;
        this.versionsConfig = versionsConfig;
        this.version = version;
    }

    /**
     * Initialize the application
     */
    public void init() {
        Class<? extends Provider> providerClass = provider.getProviderClass();
        try {
            Constructor<?> constructor = providerClass.getConstructor(null);
            Provider providerInstance = (Provider) constructor.newInstance();

            runningProvider = providerInstance;

            LOGGER.info("Launching " + providerClass.getName() + " provider");

            providerInstance.setApplication(this);
            providerInstance.init();
        } catch (Exception e) {
            LOGGER.error("Unable to instantiate provider", e);
            System.exit(1);
        }
    }

    /**
     * Exit the application
     */
    public void exit() {

    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public AppProvider getProvider() {
        return provider;
    }

    public AppState getState() {
        return state;
    }

    public VersionsConfig getVersionsConfig() {
        return versionsConfig;
    }

    public String getVersion() {
        return version;
    }

    public Provider getRunningProvider() {
        return runningProvider;
    }

}
