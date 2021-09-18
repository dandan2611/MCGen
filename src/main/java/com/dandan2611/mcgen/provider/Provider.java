package com.dandan2611.mcgen.provider;

import com.dandan2611.mcgen.McGenApplication;

public abstract class Provider {

    private McGenApplication application;

    public void setApplication(McGenApplication application) {
        if(this.application != null)
            throw new UnsupportedOperationException("Can't run with two separate applications!");
        this.application = application;
    }

    public McGenApplication getApplication() {
        return this.application;
    }

    /**
     * Called when the application is currently ready to launch
     */
    public abstract void init();

    /**
     * Check if the provider is already initialized (ready to begin generation or ready to merge)
     * @return If the provider is initialized
     */
    public abstract boolean isInitialized();

    /**
     * Called when the whole process is finished and a cleanup is needed
     */
    public abstract void exit();

}
