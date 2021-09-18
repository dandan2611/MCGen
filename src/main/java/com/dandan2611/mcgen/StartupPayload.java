package com.dandan2611.mcgen;

import com.dandan2611.mcgen.config.AppProvider;
import com.dandan2611.mcgen.config.AppState;
import com.dandan2611.mcgen.config.VersionsConfig;

public record StartupPayload(AppProvider provider, AppState state, VersionsConfig versionsConfig, String version) {
}
