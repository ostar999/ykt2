package com.arialyy.aria.core.config;

import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import java.io.Serializable;

/* loaded from: classes2.dex */
public abstract class BaseTaskConfig extends BaseConfig implements Serializable {
    String caName;
    String caPath;
    protected String TAG = CommonUtil.getClassName((Class) getClass());
    int buffSize = 8192;
    long updateInterval = 1000;
    public int oldMaxTaskNum = 2;
    int maxTaskNum = 2;
    int reTryNum = 10;
    int reTryInterval = 2000;
    int connectTimeOut = 5000;
    boolean isConvertSpeed = false;
    String queueMod = "wait";
    int iOTimeOut = 20000;
    int maxSpeed = 0;

    public int getBuffSize() {
        return this.buffSize;
    }

    public String getCaName() {
        return this.caName;
    }

    public String getCaPath() {
        return this.caPath;
    }

    public int getConnectTimeOut() {
        return this.connectTimeOut;
    }

    public int getIOTimeOut() {
        return this.iOTimeOut;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public int getMaxTaskNum() {
        return this.maxTaskNum;
    }

    public String getQueueMod() {
        return this.queueMod;
    }

    public int getReTryInterval() {
        return this.reTryInterval;
    }

    public int getReTryNum() {
        return this.reTryNum;
    }

    public long getUpdateInterval() {
        return this.updateInterval;
    }

    public boolean isConvertSpeed() {
        return this.isConvertSpeed;
    }

    public BaseTaskConfig setBuffSize(int i2) throws Throwable {
        this.buffSize = i2;
        save();
        return this;
    }

    public BaseConfig setCaName(String str) throws Throwable {
        this.caName = str;
        save();
        return this;
    }

    public BaseConfig setCaPath(String str) throws Throwable {
        this.caPath = str;
        save();
        return this;
    }

    public BaseTaskConfig setConnectTimeOut(int i2) throws Throwable {
        this.connectTimeOut = i2;
        save();
        return this;
    }

    public BaseTaskConfig setConvertSpeed(boolean z2) throws Throwable {
        this.isConvertSpeed = z2;
        save();
        return this;
    }

    public BaseTaskConfig setIOTimeOut(int i2) throws Throwable {
        this.iOTimeOut = i2;
        save();
        return this;
    }

    public BaseTaskConfig setMaxSpeed(int i2) throws Throwable {
        this.maxSpeed = i2;
        save();
        return this;
    }

    public BaseTaskConfig setMaxTaskNum(int i2) throws Throwable {
        this.oldMaxTaskNum = this.maxTaskNum;
        this.maxTaskNum = i2;
        save();
        return this;
    }

    public BaseTaskConfig setQueueMod(String str) throws Throwable {
        this.queueMod = str;
        save();
        return this;
    }

    public BaseTaskConfig setReTryInterval(int i2) throws Throwable {
        this.reTryInterval = i2;
        save();
        return this;
    }

    public BaseTaskConfig setReTryNum(int i2) throws Throwable {
        this.reTryNum = i2;
        save();
        return this;
    }

    public BaseTaskConfig setUpdateInterval(long j2) throws Throwable {
        if (j2 <= 0) {
            ALog.w("Configuration", "进度更新间隔不能小于0");
            return this;
        }
        this.updateInterval = j2;
        save();
        return this;
    }
}
