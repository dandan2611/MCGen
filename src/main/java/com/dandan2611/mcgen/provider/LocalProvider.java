package com.dandan2611.mcgen.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LocalProvider extends Provider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalProvider.class);

    public static final File CACHE_DIRECTORY = new File("cache/");

    @Override
    public void init() {
        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("LOCAL PROVIDER - By dandan2611");
        LOGGER.info("Use local instance to create, generate and merge maps");
        LOGGER.info("--------------------------------------------------------");

        // Cache directory creation
        if(!CACHE_DIRECTORY.exists())
            CACHE_DIRECTORY.mkdirs();

    }

    @Override
    public void exit() {
        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("EXITING LOCAL PROVIDER");
        LOGGER.info("--------------------------------------------------------");

        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("See you next time!");
        LOGGER.info("--------------------------------------------------------");
    }

}
