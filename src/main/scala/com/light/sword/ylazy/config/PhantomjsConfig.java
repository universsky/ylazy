package com.light.sword.ylazy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jack on 2017/2/18.
 * <p>
 * phantomjs.binPath=src/main/resources/ext/phantomjs-2.1.1-macosx/bin
 * phantomjs.netsniffPath=src/main/resources/ext/phantomjs-2.1.1-macosx/examples
 */
@ConfigurationProperties(prefix = "phantomjs", locations = "classpath:phantomjs.properties")
public class PhantomjsConfig {
    private String binPath;
    private String netsniffPath;

    public String getBinPath() {
        return binPath;
    }

    public void setBinPath(String binPath) {
        this.binPath = binPath;
    }

    public String getNetsniffPath() {
        return netsniffPath;
    }

    public void setNetsniffPath(String netsniffPath) {
        this.netsniffPath = netsniffPath;
    }
}
