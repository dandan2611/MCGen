package com.dandan2611.mcgen.config;

import com.dandan2611.mcgen.provider.LocalProvider;
import com.dandan2611.mcgen.provider.Provider;

public enum AppProvider {

    LOCAL(LocalProvider.class);

    private final Class<? extends Provider> providerClass;

    AppProvider(Class<? extends Provider> providerClass) {
        this.providerClass = providerClass;
    }

    public Class<? extends Provider> getProviderClass() {
        return providerClass;
    }

}
