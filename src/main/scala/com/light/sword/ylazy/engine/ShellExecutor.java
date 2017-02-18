package com.light.sword.ylazy.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jack on 2017/2/18.
 */
public class ShellExecutor {
    static Logger log = LoggerFactory.getLogger(ShellExecutor.class);

    public static String runShell(String cmd) {
        StringBuffer lineList = new StringBuffer();

        try {
            synchronized (ShellExecutor.class) {
                Process process = Runtime.getRuntime().exec(cmd);
                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = input.readLine()) != null) {
                    lineList.append(line);
                }
                input.close();

                int exitValue = process.waitFor();
                if (0 != exitValue) {
                    log.error("call shell failed. error code is :" + exitValue);
                }
            }
        } catch (Throwable e) {
            log.error("call shell failed. " + e);
        }

        String result = lineList.toString();
        log.info(result);
        return result;
    }
}
