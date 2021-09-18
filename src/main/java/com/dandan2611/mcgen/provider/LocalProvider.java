package com.dandan2611.mcgen.provider;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class LocalProvider extends Provider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalProvider.class);

    public static final File CACHE_DIRECTORY = new File("cache/");
    public static final File CACHE_SERVER_EXECUTABLE = new File(CACHE_DIRECTORY, "server.jar");
    public static final File CACHE_SERVERS_DIRECTORY = new File(CACHE_DIRECTORY, "servers/");

    @Override
    public void init() {
        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("LOCAL PROVIDER - By dandan2611");
        LOGGER.info("Use local instance to create, generate and merge maps");
        LOGGER.info("--------------------------------------------------------");

        if(getApplication().getCommandLine().hasOption("cleanup")) {
            cleanup();
        }

        switch (getApplication().getState()) {
            case INIT -> initState();
            case WORK -> workState();
            case MERGE -> mergeState();
            case CLEANUP -> cleanup();
        }

    }

    @Override
    public boolean isInitialized() {
        if(!CACHE_DIRECTORY.exists()
                || !CACHE_SERVER_EXECUTABLE.exists()
                || !CACHE_SERVERS_DIRECTORY.exists())
            return false;

        return true;
    }

    private void initState() {
        if(isInitialized()) {
            LOGGER.warn("Provider is already initialized, please use --cleanup or cleanup state to cleanup before " +
                    "using this app again");
            return;
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

        boolean forceFreshExecutable = getApplication().getCommandLine().hasOption("--fresh-executable");

        if(!CACHE_SERVER_EXECUTABLE.exists() || forceFreshExecutable) {
            LOGGER.info("Downloading version {} {}", getApplication().getVersion(), (forceFreshExecutable ? "[FORCED BY --fresh-executable]" : ""));
            try {
                FileUtils.copyURLToFile(versionDownloadUrl, CACHE_SERVER_EXECUTABLE);
            } catch (IOException e) {
                LOGGER.error("Unable to download version executable jar", e);
                return;
            }
        }
        else
            LOGGER.info("Executable already exist in cache, if you wish to download it again use --fresh-executable argument");
    }

    private void workState() {

    }

    private void mergeState() {

    }

    @Override
    public void cleanup() {
        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("INITIATING CLEANUP");
        LOGGER.info("--------------------------------------------------------");

        long startTime = System.currentTimeMillis();

        if(CACHE_DIRECTORY.exists()) {
            try {
                FileUtils.deleteDirectory(CACHE_DIRECTORY);
                LOGGER.info("Deleted cache directory");
            } catch (IOException e) {
                LOGGER.error("Unable to delete cache directory", e);
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        LOGGER.info("--------------------------------------------------------");
        LOGGER.info("CLEANUP COMPLETE ({}s)", TimeUnit.MILLISECONDS.toSeconds(totalTime));
        LOGGER.info("--------------------------------------------------------");
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
