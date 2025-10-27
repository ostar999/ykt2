package com.tencent.smtt.export.external.extension.interfaces;

import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public interface IX5WebSettingsExtension {
    public static final int PicModel_NORMAL = 1;
    public static final int PicModel_NetNoPic = 3;
    public static final int PicModel_NoPic = 2;

    void customDiskCachePathEnabled(boolean z2, String str);

    boolean getBlockLocalAddressEnable();

    boolean getPageSolarEnableFlag();

    boolean isFitScreen();

    boolean isReadModeWebView();

    boolean isWapSitePreferred();

    boolean isWebViewInBackground();

    void setARModeEnable(boolean z2);

    void setAcceptCookie(boolean z2);

    void setAdditionalHttpHeaders(Map<String, String> map);

    void setAutoDetectToOpenFitScreenEnabled(boolean z2);

    void setAutoRecoredAndRestoreScaleEnabled(boolean z2);

    void setBlockLocalAddressEnable(boolean z2);

    void setContentCacheEnable(boolean z2);

    void setDayOrNight(boolean z2);

    void setDisplayCutoutEnable(boolean z2);

    void setEnableUnderLine(boolean z2);

    void setFirstScreenDetect(boolean z2);

    void setFirstScreenSoftwareTextureDraw(boolean z2);

    void setFitScreen(boolean z2);

    void setForcePinchScaleEnabled(boolean z2);

    void setFramePerformanceRecordEnable(boolean z2);

    boolean setHttpDnsDomains(List<String> list);

    void setImageScanEnable(boolean z2);

    void setImgAsDownloadFile(boolean z2);

    void setIsViewSourceMode(boolean z2);

    void setJSPerformanceRecordEnable(boolean z2);

    void setJavaScriptOpenWindowsBlockedNotifyEnabled(boolean z2);

    void setOnContextMenuEnable(boolean z2);

    void setOnlyDomTreeBuild(boolean z2);

    void setPageCacheCapacity(int i2);

    void setPageSolarEnableFlag(boolean z2);

    void setPicModel(int i2);

    void setPreFectch(boolean z2);

    void setPreFectchEnableWhenHasMedia(boolean z2);

    void setReadModeWebView(boolean z2);

    void setRecordRequestEnabled(boolean z2);

    void setRememberScaleValue(boolean z2);

    void setSelectionColorEnabled(boolean z2);

    void setShouldRequestFavicon(boolean z2);

    void setShouldTrackVisitedLinks(boolean z2);

    void setSmartFullScreenEnabled(boolean z2);

    void setTbsARShareType(int i2);

    void setTextDecorationUnlineEnabled(boolean z2);

    void setUseQProxy(boolean z2);

    void setWapSitePreferred(boolean z2);

    void setWebViewInBackground(boolean z2);
}
