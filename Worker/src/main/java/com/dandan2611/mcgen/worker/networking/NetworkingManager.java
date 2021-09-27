package com.dandan2611.mcgen.worker.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkingManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkingManager.class);

    private boolean master = false;

    public void initMaster() {
        this.master = true;
    }

    public void initWorker(String masterIp) {

    }

    public void exit() {

    }

    public boolean isMaster() {
        return this.master;
    }

}
