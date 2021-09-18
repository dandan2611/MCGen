package com.dandan2611.mcgen.provider;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LocalProvider extends Provider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalProvider.class);

    public static final File CACHE_DIRECTORY = new File("cache/");

    @Override
    public void init() {
        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("LOCAL PROVIDER - By dandan2611");
        LOGGER.info("Use local instance to create, generate and merge maps");
        LOGGER.info("--------------------------------------------------------");

        switch (getApplication().getState()) {
            case INIT -> initState();
            case WORK -> workState();
            case MERGE -> mergeState();
        }
        // Cache directory creation
        if(!CACHE_DIRECTORY.exists())
            CACHE_DIRECTORY.mkdirs();

        // Version executable download
        String serverVersion = getApplication().getVersion();
        URL versionDownloadUrl = null;
        try {
            versionDownloadUrl = new URL(getApplication().getVersionsConfig().getVersion(serverVersion));
        } catch (MalformedURLException e) {
            LOGGER.error("Malformed version URL", e);
            return;
        }

        LOGGER.info("Fetching executable {}", getApplication().getVersion());
        File cachedServerExecutable = new File(CACHE_DIRECTORY, "server-" + serverVersion + ".jar");

        boolean forceFreshExecutable = getApplication().getCommandLine().hasOption("--fresh-executable");

        if(!cachedServerExecutable.exists() || forceFreshExecutable) {
            LOGGER.info("Downloading version {} {}", getApplication().getVersion(), (forceFreshExecutable ? "[FORCED BY --fresh-executable]" : ""));
            try {
                FileUtils.copyURLToFile(versionDownloadUrl, cachedServerExecutable);
            } catch (IOException e) {
                LOGGER.error("Unable to download version executable jar", e);
                return;
            }
        }
        else
            LOGGER.info("Executable already exist in cache, if you wish to download it again use --fresh-executable argument");

    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    private void initState() {

    }

    private void workState() {

    }

    private void mergeState() {

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
