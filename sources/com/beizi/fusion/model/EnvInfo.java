package com.beizi.fusion.model;

import android.content.Context;
import android.os.Debug;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.as;
import com.beizi.fusion.g.s;
import com.beizi.fusion.g.v;

/* loaded from: classes2.dex */
public class EnvInfo {
    private String developerMode;
    private String isDebugApk;
    private String isLockScreen;
    private String isSimulator;
    private String isUsb;
    private String isWifiProxy;
    private String isp;

    /* renamed from: net, reason: collision with root package name */
    private String f5278net;
    private String userAgent;
    private String isDebugConnected = String.valueOf(Debug.isDebuggerConnected());
    private String isVpn = String.valueOf(as.b());

    public EnvInfo(Context context) {
        this.userAgent = aq.a(context, "userAgent");
        this.f5278net = String.valueOf(s.b(context));
        this.isp = String.valueOf(s.a(context));
        this.developerMode = String.valueOf(as.h(context));
        this.isUsb = String.valueOf(as.i(context));
        this.isDebugApk = String.valueOf(as.g(context));
        this.isWifiProxy = String.valueOf(as.j(context));
        this.isSimulator = String.valueOf(v.a().a(context));
    }

    public String getIsp() {
        return this.isp;
    }

    public String getNet() {
        return this.f5278net;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String isDeveloperMode() {
        return this.developerMode;
    }

    public String isIsDebugApk() {
        return this.isDebugApk;
    }

    public String isIsDebugConnected() {
        return this.isDebugConnected;
    }

    public String isIsLockScreen() {
        return this.isLockScreen;
    }

    public String isIsSimulator() {
        return this.isSimulator;
    }

    public String isIsUsb() {
        return this.isUsb;
    }

    public String isIsVpn() {
        return this.isVpn;
    }

    public String isIsWifiProxy() {
        return this.isWifiProxy;
    }

    public void setDeveloperMode(String str) {
        this.developerMode = str;
    }

    public void setIsDebugApk(String str) {
        this.isDebugApk = str;
    }

    public void setIsDebugConnected(String str) {
        this.isDebugConnected = str;
    }

    public void setIsLockScreen(String str) {
        this.isLockScreen = str;
    }

    public void setIsSimulator(String str) {
        this.isSimulator = str;
    }

    public void setIsUsb(String str) {
        this.isUsb = str;
    }

    public void setIsVpn(String str) {
        this.isVpn = str;
    }

    public void setIsWifiProxy(String str) {
        this.isWifiProxy = str;
    }

    public void setIsp(String str) {
        this.isp = str;
    }

    public void setNet(String str) {
        this.f5278net = str;
    }

    public void setUserAgent(String str) {
        this.userAgent = str;
    }
}
