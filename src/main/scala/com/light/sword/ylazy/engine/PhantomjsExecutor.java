package com.light.sword.ylazy.engine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.light.sword.ylazy.config.PhantomjsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 2017/2/18.
 */
@Service
public class PhantomjsExecutor {
    static Logger log = LoggerFactory.getLogger(ShellExecutor.class);

    @Autowired
    PhantomjsConfig phantomjsConfig;

    public String getPhantomJSEnv() {
        String binPath = phantomjsConfig.getBinPath();
        String netsniffPath = phantomjsConfig.getNetsniffPath();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("binPath", binPath);
        jsonObject.put("netsniffPath", netsniffPath);
        return JSON.toJSONString(jsonObject);
    }

    public Map<String, String> getPhantomJSEnvMap() {
        String binPath = phantomjsConfig.getBinPath();
        String netsniffPath = phantomjsConfig.getNetsniffPath();
        Map jsonObject = new HashMap();
        jsonObject.put("binPath", binPath);
        jsonObject.put("netsniffPath", netsniffPath);
        return jsonObject;
    }

    public String netSniff(String url){
        String binPath = phantomjsConfig.getBinPath();
        String netsniffPath = phantomjsConfig.getNetsniffPath();
        String cmd = binPath + " " + netsniffPath + " " + url;
        log.info(cmd);
        return ShellExecutor.runShell(cmd);
    }

}
