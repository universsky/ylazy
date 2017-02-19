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
    private String binPathMac;
    private String netsniffPathMac;
    private String binPathLinux;
    private String netsniffPathLinux;
    private String binPathWindows;
    private String netsniffPathWindows;

    public String getBinPathMac() {
        return binPathMac;
    }

    public void setBinPathMac(String binPathMac) {
        this.binPathMac = binPathMac;
    }

    public String getNetsniffPathMac() {
        return netsniffPathMac;
    }

    public void setNetsniffPathMac(String netsniffPathMac) {
        this.netsniffPathMac = netsniffPathMac;
    }

    public String getBinPathLinux() {
        return binPathLinux;
    }

    public void setBinPathLinux(String binPathLinux) {
        this.binPathLinux = binPathLinux;
    }

    public String getNetsniffPathLinux() {
        return netsniffPathLinux;
    }

    public void setNetsniffPathLinux(String netsniffPathLinux) {
        this.netsniffPathLinux = netsniffPathLinux;
    }

    public String getBinPathWindows() {
        return binPathWindows;
    }

    public void setBinPathWindows(String binPathWindows) {
        this.binPathWindows = binPathWindows;
    }

    public String getNetsniffPathWindows() {
        return netsniffPathWindows;
    }

    public void setNetsniffPathWindows(String netsniffPathWindows) {
        this.netsniffPathWindows = netsniffPathWindows;
    }
}
