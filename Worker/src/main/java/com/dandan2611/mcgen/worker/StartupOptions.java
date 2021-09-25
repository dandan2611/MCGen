package com.dandan2611.mcgen.worker;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class StartupOptions {

    /**
     * Options list
     */
    public static final Options OPTIONS = new Options();

    /**
     * Help paragraph option
     */
    public static final Option HELP_OPTION = createOption("h", "help", false,
            "Show the help paragraph", false);

    /**
     * Master node option
     */
    public static final Option MASTER_OPTION = createOption("m", "master", false,
            "Set the current worker as MASTER", false);

    /**
     * Connect option
     */
    public static final Option CONNECT_OPTION = createOption("c", "connect", true,
            "Connect to the specified master node", true);

    private static Option createOption(String shortName, String longName, boolean hasArg, String description,
                                       boolean required) {
        Option option = new Option(shortName, longName, hasArg, description);
        option.setRequired(required);
        OPTIONS.addOption(option);
        return option;
    }

}
