package com.dandan2611.mcgen.worker.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkingManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkingManager.class);

    private boolean master = false;

    private MasterServer masterServer;
    private WorkerClient workerClient;

    public void initMaster() {
        this.master = true;

        this.masterServer = new MasterServer(44123);
        masterServer.init();
    }

    public void initWorker(String masterIp, int masterPort) {
        this.workerClient = new WorkerClient(masterIp, masterPort);
        workerClient.init();
    }

    public void exit() {
        if(master)
            exitMaster();
        exitWorker();
    }

    public void exitMaster() {
        masterServer.exit();
    }

    private void exitWorker() {

    }

    public boolean isMaster() {
        return this.master;
    }

}
