package com.dandan2611.mcgen.worker.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServer.class);

    private int port;

    public MasterServer(int port) {
        this.port = port;
    }

    public void init() {
        LOGGER.info("Initializing master server");

        LOGGER.info("Master server initialized!");
    }

    public void exit() {
        LOGGER.info("Exiting master server");

        LOGGER.info("Master server exited!");
    }

}
