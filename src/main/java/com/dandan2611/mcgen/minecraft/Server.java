package com.dandan2611.mcgen.minecraft;

import java.io.File;

public abstract class Server {

   public File dataDirectory;

    public Server(File dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public abstract void init();
    public abstract boolean isInitialized();
    public abstract void cleanup();

    public abstract void start();
    public abstract void stop();

    public abstract boolean isRunning();

    public abstract void consoleCommand(String command);

    public File getDataDirectory() {
        return dataDirectory;
    }

}
