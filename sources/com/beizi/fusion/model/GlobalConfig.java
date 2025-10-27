package com.beizi.fusion.model;

import com.plv.socket.user.PLVSocketUserConstant;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class GlobalConfig {

    @JsonNode(key = "adPlusConfig")
    private AdPlusConfig adPlusConfig;

    @JsonNode(key = "bannerExcutorForLieYing")
    private Object bannerExcutorForLieYing;

    @JsonNode(key = "configVersion")
    private String configVersion;

    @JsonNode(key = "configurator")
    private Configurator configurator;

    @JsonNode(key = "crashUrl")
    private String crashUrl;

    @JsonNode(key = "expireTime")
    private int expireTime;

    @JsonNode(key = "hr")
    private Object hr;

    @JsonNode(key = "lastUpdateTime")
    private long lastUpdateTime;

    @JsonNode(key = PLVSocketUserConstant.USERTYPE_MANAGER)
    private Manager manager;

    @JsonNode(key = "maxValidTime")
    private long maxValidTime;

    @JsonNode(key = "messenger")
    private Messenger messenger;

    @JsonNode(key = "smFlag")
    private String smFlag;

    @JsonNode(key = "taskConfig")
    private TaskConfig taskConfig;

    public static GlobalConfig objectFromData(String str) {
        try {
            return (GlobalConfig) JsonResolver.fromJson(str, GlobalConfig.class);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String objectToJson(Object obj) {
        try {
            return JsonResolver.toJson(obj, GlobalConfig.class);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public AdPlusConfig getAdPlusConfig() {
        return this.adPlusConfig;
    }

    public Object getBannerExcutorForLieYing() {
        return this.bannerExcutorForLieYing;
    }

    public String getConfigVersion() {
        return this.configVersion;
    }

    public Configurator getConfigurator() {
        return this.configurator;
    }

    public String getCrashUrl() {
        return this.crashUrl;
    }

    public int getExpireTime() {
        return this.expireTime;
    }

    public Object getHr() {
        return this.hr;
    }

    public long getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public Manager getManager() {
        return this.manager;
    }

    public long getMaxValidTime() {
        return this.maxValidTime;
    }

    public Messenger getMessenger() {
        return this.messenger;
    }

    public String getSmFlag() {
        return this.smFlag;
    }

    public TaskConfig getTaskConfig() {
        return this.taskConfig;
    }

    public void setAdPlusConfig(AdPlusConfig adPlusConfig) {
        this.adPlusConfig = adPlusConfig;
    }

    public void setBannerExcutorForLieYing(Object obj) {
        this.bannerExcutorForLieYing = obj;
    }

    public void setConfigVersion(String str) {
        this.configVersion = str;
    }

    public void setConfigurator(Configurator configurator) {
        this.configurator = configurator;
    }

    public void setCrashUrl(String str) {
        this.crashUrl = str;
    }

    public void setExpireTime(int i2) {
        this.expireTime = i2;
    }

    public void setHr(Object obj) {
        this.hr = obj;
    }

    public void setLastUpdateTime(long j2) {
        this.lastUpdateTime = j2;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setMaxValidTime(long j2) {
        this.maxValidTime = j2;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public void setSmFlag(String str) {
        this.smFlag = str;
    }

    public void setTaskConfig(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }

    public static GlobalConfig objectFromData(String str, String str2) {
        try {
            return (GlobalConfig) JsonResolver.fromJson(new JSONObject(str).getString(str), GlobalConfig.class);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
