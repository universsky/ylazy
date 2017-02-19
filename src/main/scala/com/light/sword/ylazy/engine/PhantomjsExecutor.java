package com.light.sword.ylazy.engine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.light.sword.ylazy.config.PhantomjsConfig;
import com.light.sword.ylazy.dao.LazyTaskDao;
import com.light.sword.ylazy.entity.LazyTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 2017/2/18.
 */
@Service
public class PhantomjsExecutor {
    static Logger log = LoggerFactory.getLogger(PhantomjsExecutor.class);

    @Autowired
    PhantomjsConfig phantomjsConfig;
    @Autowired
    LazyTaskDao lazyTaskDao;

    public String getPhantomJSEnv() {
        String binPath = phantomjsConfig.getBinPathMac();
        String netsniffPath = phantomjsConfig.getNetsniffPathMac();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("binPath", binPath);
        jsonObject.put("netsniffPath", netsniffPath);
        return JSON.toJSONString(jsonObject);
    }

    public Map<String, String> getPhantomJSEnvMap() {
        String binPath = phantomjsConfig.getBinPathMac();
        String netsniffPath = phantomjsConfig.getNetsniffPathMac();
        Map jsonObject = new HashMap();
        jsonObject.put("binPath", binPath);
        jsonObject.put("netsniffPath", netsniffPath);
        return jsonObject;
    }

    /**
     * 执行引擎debugTest接口用
     * 不存入数据库
     *
     * @param url
     * @return
     */
    public String ylazy(String url) {
        String binPath = phantomjsConfig.getBinPathMac();
        String netsniffPath = phantomjsConfig.getNetsniffPathMac();
        String cmd = binPath + " " + netsniffPath + " " + url;
        log.info(cmd);
        String result = ShellExecutor.runShell(cmd);
        result = result.substring(result.indexOf("{    \"log\": {"));//截取正式的json输出
        return result;
    }

    /**
     * 执行引擎
     * 入库
     *
     * @param id
     * @return
     */
    public String ylazyById(Integer id) {
        LazyTask task = lazyTaskDao.findOne(id);
        if (null == task) return "";

        String url = task.getUrl();
        if (url == null || url.replaceAll(" ", "").equals("")) return "";

        String binPath = getBinPath();
        String netsniffPath = getNetsniffPath();

        String cmd = binPath + " " + netsniffPath + " " + url;
        log.info(cmd);
        String result = ShellExecutor.runShell(cmd);

        int startIndex = result.indexOf("{    \"log\": {");
        if (startIndex < 0) return "";
        result = result.substring(startIndex);//截取正式的json输出

        task.setGmtModify(new Date());
        task.setResultJson(result);
        task.setExecuteTimes(task.getExecuteTimes() == null ? 0 + 1 : task.getExecuteTimes() + 1);

        lazyTaskDao.save(task);
        return result;
    }

    private String getBinPath() {
        String binPath = "";
        if (isLinux()) {
            binPath = phantomjsConfig.getBinPathLinux();
        }
        if (isMacOS()) {
            binPath = phantomjsConfig.getBinPathMac();
        }
        if (isWindows()) {
            binPath = phantomjsConfig.getBinPathWindows();
        }
        return binPath;
    }

    private String getNetsniffPath() {
        String netsniffPath = "";
        if (isLinux()) {
            netsniffPath = phantomjsConfig.getNetsniffPathLinux();
        }
        if (isMacOS()) {
            netsniffPath = phantomjsConfig.getNetsniffPathMac();
        }
        if (isWindows()) {
            netsniffPath = phantomjsConfig.getNetsniffPathWindows();
        }
        return netsniffPath;
    }


    public boolean isLinux() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.indexOf("linux") >= 0;
    }

    public boolean isMacOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.indexOf("mac") >= 0 && osName.indexOf("os") > 0;
    }

    public boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.indexOf("windows") >= 0;
    }


}
