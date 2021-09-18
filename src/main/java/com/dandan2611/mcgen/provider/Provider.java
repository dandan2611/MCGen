package com.dandan2611.mcgen.provider;

import com.dandan2611.mcgen.StartupPayload;
import org.apache.commons.cli.CommandLine;

public abstract class Provider {

    /**
     * Called when the application is currently ready to launch
     */
    public abstract void init(CommandLine commandLine, StartupPayload startupPayload);

    /**
     * Called when the whole process is finished and a cleanup is needed
     */
    public abstract void exit();

}
