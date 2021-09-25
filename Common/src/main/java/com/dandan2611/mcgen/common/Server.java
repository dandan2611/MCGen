package com.dandan2611.mcgen.common;

public abstract class Server {

    /**
     * Start the game server
     */
    public abstract void start();

    /**
     * Stop the game server
     */
    public abstract void stop();

    /**
     * Send a command in the server console
     * @param command Command to send in the console
     */
    public abstract void consoleCommand(String command);

}
