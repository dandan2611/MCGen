package com.dandan2611.mcgen.worker;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class StartupOptions {

    /**
     * Options list
     */
    public static final Options OPTIONS;

    /**
     * Help paragraph option
     */
    public static final Option HELP_OPTION = getOption("h", "help", false,
            "Show the help paragraph", false);

    /**
     * Master node option
     */
    public static final Option MASTER_OPTION = getOption("m", "master", false,
            "Set the current worker as MASTER", false);

    static {
        OPTIONS = new Options();
        OPTIONS.addOption(HELP_OPTION);
        OPTIONS.addOption(MASTER_OPTION);
    }

    private static Option getOption(String shortName, String longName, boolean hasArg, String description,
                                    boolean required) {
        Option option = new Option(shortName, longName, hasArg, description);
        option.setRequired(required);
        return option;
    }

}
