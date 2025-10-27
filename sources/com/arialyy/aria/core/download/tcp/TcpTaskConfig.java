package com.arialyy.aria.core.download.tcp;

/* loaded from: classes2.dex */
public class TcpTaskConfig {
    private String heartbeat;
    private String params;
    private long heartbeatInterval = 1000;
    private String charset = "utf-8";

    public String getCharset() {
        return this.charset;
    }

    public String getHeartbeat() {
        return this.heartbeat;
    }

    public long getHeartbeatInterval() {
        return this.heartbeatInterval;
    }

    public String getParams() {
        return this.params;
    }

    public void setCharset(String str) {
        this.charset = str;
    }

    public void setHeartbeat(String str) {
        this.heartbeat = str;
    }

    public void setHeartbeatInterval(long j2) {
        this.heartbeatInterval = j2;
    }

    public void setParams(String str) {
        this.params = str;
    }
}
