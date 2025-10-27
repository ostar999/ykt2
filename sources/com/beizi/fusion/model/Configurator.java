package com.beizi.fusion.model;

import cn.hutool.core.text.CharPool;
import com.beizi.fusion.model.JsonResolver;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class Configurator {

    @JsonNode(key = "checkInterval")
    private long checkInterval;

    @JsonNode(key = "configUrl")
    private String configUrl;

    @JsonNode(key = "configVersion")
    private String configVersion;

    @JsonNode(key = "id")
    private String id;

    @JsonNode(key = "job")
    private String job;

    @JsonNode(key = "s2sbidding")
    private S2SBiddingConfig s2sbidding;

    @JsonNode(key = "version")
    private String version;

    public static List<Configurator> arraySdkConfigFromData(String str) {
        try {
            return (List) JsonResolver.fromJson(str, new JsonResolver.TypeToken<ArrayList<Configurator>>() { // from class: com.beizi.fusion.model.Configurator.1
            }.getType());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Configurator objectFromData(String str) {
        try {
            return (Configurator) JsonResolver.fromJson(str, Configurator.class);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public long getCheckInterval() {
        return this.checkInterval;
    }

    public String getConfigUrl() {
        return this.configUrl;
    }

    public String getConfigVersion() {
        return this.configVersion;
    }

    public String getId() {
        return this.id;
    }

    public String getJob() {
        return this.job;
    }

    public S2SBiddingConfig getS2sbidding() {
        return this.s2sbidding;
    }

    public String getVersion() {
        return this.version;
    }

    public void setCheckInterval(long j2) {
        this.checkInterval = j2;
    }

    public void setConfigUrl(String str) {
        this.configUrl = str;
    }

    public void setConfigVersion(String str) {
        this.configVersion = str;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setJob(String str) {
        this.job = str;
    }

    public void setS2sbidding(S2SBiddingConfig s2SBiddingConfig) {
        this.s2sbidding = s2SBiddingConfig;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String toString() {
        return "Configurator{id='" + this.id + CharPool.SINGLE_QUOTE + ", job='" + this.job + CharPool.SINGLE_QUOTE + ", version='" + this.version + CharPool.SINGLE_QUOTE + ", configVersion='" + this.configVersion + CharPool.SINGLE_QUOTE + ", checkInterval=" + this.checkInterval + ", configUrl='" + this.configUrl + CharPool.SINGLE_QUOTE + '}';
    }

    public static Configurator objectFromData(String str, String str2) {
        try {
            return (Configurator) JsonResolver.fromJson(new JSONObject(str).getString(str), Configurator.class);
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static List<Configurator> arraySdkConfigFromData(String str, String str2) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return (List) JsonResolver.fromJson(jSONObject.getJSONArray(str2).toString(), new JsonResolver.TypeToken<ArrayList<Configurator>>() { // from class: com.beizi.fusion.model.Configurator.2
            }.getType());
        } catch (JSONException e2) {
            e2.printStackTrace();
            return new ArrayList();
        } catch (Exception e3) {
            e3.printStackTrace();
            return new ArrayList();
        }
    }
}
