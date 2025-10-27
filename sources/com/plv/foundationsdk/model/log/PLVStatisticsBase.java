package com.plv.foundationsdk.model.log;

import android.os.Build;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVUtils;
import java.io.File;

/* loaded from: classes4.dex */
public class PLVStatisticsBase extends PLVLogBase {
    protected String appId;
    protected String bitrate;
    protected String channelId2;
    protected String deviceType;
    private transient PLVELogSendType eLogSendType;
    protected int errorCode;
    protected String framework;
    protected String imei;
    protected int level;
    protected PLVLogFileBase logFile;
    protected String logVersion;
    protected String platform2;
    protected String playId;
    protected String project;
    protected String userId2;
    protected String version2;
    protected String vid;
    protected String viewerAvatar;
    protected String viewerId;
    protected String viewerName;

    public PLVStatisticsBase(PLVLogFileBase pLVLogFileBase) {
        this(pLVLogFileBase, "");
    }

    public void addLogInfo() {
        setImei(PLVUtils.getPhoneIMEI(PLVAppUtils.getApp()));
        setPlatform2(Build.MODEL + File.separator + Build.VERSION.RELEASE);
    }

    public String getAppId() {
        return this.appId;
    }

    public String getBitrate() {
        return this.bitrate;
    }

    public String getChannelId2() {
        return this.channelId2;
    }

    public String getDeviceType() {
        return this.deviceType;
    }

    public PLVELogSendType getELogSendType() {
        return this.eLogSendType;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getFramework() {
        return this.framework;
    }

    public String getImei() {
        return this.imei;
    }

    public int getLevel() {
        return this.level;
    }

    public PLVLogFileBase getLogFile() {
        return this.logFile;
    }

    public String getLogVersion() {
        return this.logVersion;
    }

    public String getPlatform2() {
        return this.platform2;
    }

    public String getPlayId() {
        return this.playId;
    }

    public String getProject() {
        return this.project;
    }

    public String getProjectName() {
        return getProject() + getUserId2();
    }

    public String getUserId2() {
        return this.userId2;
    }

    public String getVersion2() {
        return this.version2;
    }

    public String getVid() {
        return this.vid;
    }

    public String getViewerAvatar() {
        return this.viewerAvatar;
    }

    public String getViewerId() {
        return this.viewerId;
    }

    public String getViewerName() {
        return this.viewerName;
    }

    public boolean isNeedBatches() {
        return false;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setBitrate(String str) {
        this.bitrate = str;
    }

    public void setChannelId2(String str) {
        this.channelId2 = str;
    }

    public void setDeviceType(String str) {
        this.deviceType = str;
    }

    public void setELogSendType(PLVELogSendType pLVELogSendType) {
        this.eLogSendType = pLVELogSendType;
    }

    public void setErrorCode(int i2) {
        this.errorCode = i2;
    }

    public void setFramework(String str) {
        this.framework = str;
    }

    public void setImei(String str) {
        this.imei = str;
    }

    public void setLevel(int i2) {
        this.level = i2;
    }

    public void setLogFile(PLVLogFileBase pLVLogFileBase) {
        this.logFile = pLVLogFileBase;
    }

    public void setLogVersion(String str) {
        this.logVersion = str;
    }

    public void setPlatform2(String str) {
        this.platform2 = str;
    }

    public void setPlayId(String str) {
        this.playId = str;
    }

    public void setProject(String str) {
        this.project = str;
    }

    public void setUserId2(String str) {
        this.userId2 = str;
    }

    public void setVersion2(String str) {
        this.version2 = str;
    }

    public void setVid(String str) {
        this.vid = str;
    }

    public void setViewerAvatar(String str) {
        this.viewerAvatar = str;
    }

    public void setViewerId(String str) {
        this.viewerId = str;
    }

    public void setViewerName(String str) {
        this.viewerName = str;
    }

    public PLVStatisticsBase(PLVLogFileBase pLVLogFileBase, String str) {
        this(pLVLogFileBase, str, "", "");
    }

    public PLVStatisticsBase(PLVLogFileBase pLVLogFileBase, String str, String str2, String str3) {
        this.logVersion = "1.0.0";
        this.eLogSendType = PLVELogSendType.LIVE_ELOG;
        this.logFile = pLVLogFileBase;
        this.event = str;
        this.vid = str2;
        this.playId = str3;
        this.time = System.currentTimeMillis() + "";
        addLogInfo();
    }
}
