package com.dandan2611.mcgen.config;

import java.io.File;
import java.util.HashMap;

public class VersionsConfig {

    public static final File VERSIONS_CONFIG_FILE = new File("versions.json");

    private HashMap<String, String> versions = new HashMap<>() {{
        put("1.8.8", "https://papermc.io/api/v2/projects/paper/versions/1.8.8/builds/443/downloads/paper-1.8.8-443.jar");
        put("1.9.4", "https://papermc.io/api/v2/projects/paper/versions/1.9.4/builds/773/downloads/paper-1.9.4-773.jar");
        put("1.10.2", "https://papermc.io/api/v2/projects/paper/versions/1.10.2/builds/916/downloads/paper-1.10.2-916.jar");
        put("1.11.2", "https://papermc.io/api/v2/projects/paper/versions/1.11.2/builds/1104/downloads/paper-1.11.2-1104.jar");
        put("1.12.2", "https://papermc.io/api/v2/projects/paper/versions/1.12.2/builds/1618/downloads/paper-1.12.2-1618.jar");
        put("1.13.2", "https://papermc.io/api/v2/projects/paper/versions/1.13.2/builds/655/downloads/paper-1.13.2-655.jar");
        put("1.14.4", "https://papermc.io/api/v2/projects/paper/versions/1.14.4/builds/243/downloads/paper-1.14.4-243.jar");
        put("1.15.2", "https://papermc.io/api/v2/projects/paper/versions/1.15.2/builds/391/downloads/paper-1.15.2-391.jar");
        put("1.16.5", "https://papermc.io/api/v2/projects/paper/versions/1.16.5/builds/788/downloads/paper-1.16.5-788.jar");
        put("1.17.1", "https://papermc.io/api/v2/projects/paper/versions/1.17.1/builds/266/downloads/paper-1.17.1-266.jar");
    }};

    public VersionsConfig() {
    }

    public String getVersion(String identifier) {
        return versions.get(identifier);
    }

    @Override
    public String toString() {
        return versions.toString();
    }

}
