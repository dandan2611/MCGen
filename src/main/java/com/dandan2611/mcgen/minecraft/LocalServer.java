package com.dandan2611.mcgen.minecraft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LocalServer extends Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalServer.class);

    private Process serverProcess;

    public LocalServer(File dataDirectory) {
        super(dataDirectory);
    }

    @Override
    public void init() {
        LOGGER.info("Initializing server files " + getDataDirectory().getName());

        LOGGER.info(getDataDirectory().getName() + " > Initialization complete");
    }

    @Override
    public boolean isInitialized() {
        return getDataDirectory().exists();
    }

    @Override
    public void cleanup() {
        if(isRunning())
            throw new UnsupportedOperationException("Unable to cleanup a running server!");
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return serverProcess != null
                && serverProcess.isAlive();
    }

    @Override
    public void consoleCommand(String command) {

    }

}
