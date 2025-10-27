package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;

/* loaded from: classes6.dex */
public class WebSettings {
    public static final int LOAD_CACHE_ELSE_NETWORK = 1;
    public static final int LOAD_CACHE_ONLY = 3;
    public static final int LOAD_DEFAULT = -1;
    public static final int LOAD_NORMAL = 0;
    public static final int LOAD_NO_CACHE = 2;

    /* renamed from: a, reason: collision with root package name */
    private IX5WebSettings f21056a;

    /* renamed from: b, reason: collision with root package name */
    private android.webkit.WebSettings f21057b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f21058c;

    public enum LayoutAlgorithm {
        NORMAL,
        SINGLE_COLUMN,
        NARROW_COLUMNS
    }

    public enum PluginState {
        ON,
        ON_DEMAND,
        OFF
    }

    public enum RenderPriority {
        NORMAL,
        HIGH,
        LOW
    }

    public enum TextSize {
        SMALLEST(50),
        SMALLER(75),
        NORMAL(100),
        LARGER(125),
        LARGEST(150);

        int value;

        TextSize(int i2) {
            this.value = i2;
        }
    }

    public enum ZoomDensity {
        FAR(150),
        MEDIUM(100),
        CLOSE(75);

        int value;

        ZoomDensity(int i2) {
            this.value = i2;
        }
    }

    public WebSettings(android.webkit.WebSettings webSettings) {
        this.f21056a = null;
        this.f21057b = webSettings;
        this.f21058c = false;
    }

    public WebSettings(IX5WebSettings iX5WebSettings) {
        this.f21056a = iX5WebSettings;
        this.f21057b = null;
        this.f21058c = true;
    }

    @TargetApi(17)
    public static String getDefaultUserAgent(Context context) {
        if (w.a().b()) {
            return w.a().c().i(context);
        }
        Object objA = com.tencent.smtt.utils.j.a((Class<?>) android.webkit.WebSettings.class, "getDefaultUserAgent", (Class<?>[]) new Class[]{Context.class}, context);
        if (objA == null) {
            return null;
        }
        return (String) objA;
    }

    @Deprecated
    public boolean enableSmoothTransition() {
        android.webkit.WebSettings webSettings;
        Object objA;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.enableSmoothTransition();
        }
        if (z2 || (webSettings = this.f21057b) == null || (objA = com.tencent.smtt.utils.j.a(webSettings, "enableSmoothTransition")) == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    @TargetApi(11)
    public boolean getAllowContentAccess() {
        android.webkit.WebSettings webSettings;
        Object objA;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getAllowContentAccess();
        }
        if (z2 || (webSettings = this.f21057b) == null || (objA = com.tencent.smtt.utils.j.a(webSettings, "getAllowContentAccess")) == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    @TargetApi(3)
    public boolean getAllowFileAccess() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getAllowFileAccess();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getAllowFileAccess();
    }

    public synchronized boolean getBlockNetworkImage() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getBlockNetworkImage();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getBlockNetworkImage();
    }

    @TargetApi(8)
    public synchronized boolean getBlockNetworkLoads() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getBlockNetworkLoads();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getBlockNetworkLoads();
    }

    @TargetApi(3)
    public boolean getBuiltInZoomControls() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getBuiltInZoomControls();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getBuiltInZoomControls();
    }

    public int getCacheMode() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getCacheMode();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return 0;
        }
        return webSettings.getCacheMode();
    }

    public synchronized String getCursiveFontFamily() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getCursiveFontFamily() : iX5WebSettings.getCursiveFontFamily();
    }

    @TargetApi(5)
    public synchronized boolean getDatabaseEnabled() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getDatabaseEnabled();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getDatabaseEnabled();
    }

    @TargetApi(5)
    @Deprecated
    public synchronized String getDatabasePath() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getDatabasePath() : iX5WebSettings.getDatabasePath();
    }

    public synchronized int getDefaultFixedFontSize() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getDefaultFixedFontSize();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return 0;
        }
        return webSettings.getDefaultFixedFontSize();
    }

    public synchronized int getDefaultFontSize() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getDefaultFontSize();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return 0;
        }
        return webSettings.getDefaultFontSize();
    }

    public synchronized String getDefaultTextEncodingName() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getDefaultTextEncodingName() : iX5WebSettings.getDefaultTextEncodingName();
    }

    @TargetApi(7)
    @Deprecated
    public ZoomDensity getDefaultZoom() {
        android.webkit.WebSettings webSettings;
        Enum defaultZoom;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            defaultZoom = iX5WebSettings.getDefaultZoom();
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return null;
            }
            defaultZoom = webSettings.getDefaultZoom();
        }
        return ZoomDensity.valueOf(defaultZoom.name());
    }

    @TargetApi(11)
    public boolean getDisplayZoomControls() {
        android.webkit.WebSettings webSettings;
        Object objA;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getDisplayZoomControls();
        }
        if (z2 || (webSettings = this.f21057b) == null || (objA = com.tencent.smtt.utils.j.a(webSettings, "getDisplayZoomControls")) == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    @TargetApi(7)
    public synchronized boolean getDomStorageEnabled() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getDomStorageEnabled();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getDomStorageEnabled();
    }

    public synchronized String getFantasyFontFamily() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getFantasyFontFamily() : iX5WebSettings.getFantasyFontFamily();
    }

    public synchronized String getFixedFontFamily() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getFixedFontFamily() : iX5WebSettings.getFixedFontFamily();
    }

    public synchronized boolean getJavaScriptCanOpenWindowsAutomatically() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getJavaScriptCanOpenWindowsAutomatically();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getJavaScriptCanOpenWindowsAutomatically();
    }

    public synchronized boolean getJavaScriptEnabled() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getJavaScriptEnabled();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getJavaScriptEnabled();
    }

    public synchronized LayoutAlgorithm getLayoutAlgorithm() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return LayoutAlgorithm.valueOf(iX5WebSettings.getLayoutAlgorithm().name());
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return null;
        }
        return LayoutAlgorithm.valueOf(webSettings.getLayoutAlgorithm().name());
    }

    @Deprecated
    public boolean getLightTouchEnabled() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getLightTouchEnabled();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getLightTouchEnabled();
    }

    @TargetApi(7)
    public boolean getLoadWithOverviewMode() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getLoadWithOverviewMode();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getLoadWithOverviewMode();
    }

    public synchronized boolean getLoadsImagesAutomatically() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getLoadsImagesAutomatically();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getLoadsImagesAutomatically();
    }

    @TargetApi(17)
    public boolean getMediaPlaybackRequiresUserGesture() {
        android.webkit.WebSettings webSettings;
        Object objA;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getMediaPlaybackRequiresUserGesture();
        }
        if (z2 || (webSettings = this.f21057b) == null || (objA = com.tencent.smtt.utils.j.a(webSettings, "getMediaPlaybackRequiresUserGesture")) == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public synchronized int getMinimumFontSize() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getMinimumFontSize();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return 0;
        }
        return webSettings.getMinimumFontSize();
    }

    public synchronized int getMinimumLogicalFontSize() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getMinimumLogicalFontSize();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return 0;
        }
        return webSettings.getMinimumLogicalFontSize();
    }

    public synchronized int getMixedContentMode() {
        IX5WebSettings iX5WebSettings;
        int iIntValue = -1;
        if (!this.f21058c || (iX5WebSettings = this.f21056a) == null) {
            Object objA = com.tencent.smtt.utils.j.a(this.f21057b, "getMixedContentMode", (Class<?>[]) new Class[0], new Object[0]);
            if (objA != null) {
                iIntValue = ((Integer) objA).intValue();
            }
            return iIntValue;
        }
        try {
            return iX5WebSettings.getMixedContentMode();
        } catch (Throwable th) {
            th.printStackTrace();
            return -1;
        }
    }

    @Deprecated
    public boolean getNavDump() {
        android.webkit.WebSettings webSettings;
        Object objA;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getNavDump();
        }
        if (z2 || (webSettings = this.f21057b) == null || (objA = com.tencent.smtt.utils.j.a(webSettings, "getNavDump")) == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    @TargetApi(8)
    @Deprecated
    public synchronized PluginState getPluginState() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return PluginState.valueOf(iX5WebSettings.getPluginState().name());
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return null;
        }
        Object objA = com.tencent.smtt.utils.j.a(webSettings, "getPluginState");
        if (objA == null) {
            return null;
        }
        return PluginState.valueOf(((WebSettings.PluginState) objA).name());
    }

    @TargetApi(8)
    @Deprecated
    public synchronized boolean getPluginsEnabled() {
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || this.f21057b == null) ? false : false : iX5WebSettings.getPluginsEnabled();
    }

    @Deprecated
    public synchronized String getPluginsPath() {
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || this.f21057b == null) ? "" : "" : iX5WebSettings.getPluginsPath();
    }

    public boolean getSafeBrowsingEnabled() {
        IX5WebSettings iX5WebSettings;
        android.webkit.WebSettings webSettings;
        boolean z2 = this.f21058c;
        if (!z2 && (webSettings = this.f21057b) != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                return webSettings.getSafeBrowsingEnabled();
            }
            return false;
        }
        if (!z2 || (iX5WebSettings = this.f21056a) == null) {
            return false;
        }
        try {
            return iX5WebSettings.getSafeBrowsingEnabled();
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    public synchronized String getSansSerifFontFamily() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getSansSerifFontFamily() : iX5WebSettings.getSansSerifFontFamily();
    }

    @Deprecated
    public boolean getSaveFormData() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getSaveFormData();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getSaveFormData();
    }

    @Deprecated
    public boolean getSavePassword() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getSavePassword();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getSavePassword();
    }

    public synchronized String getSerifFontFamily() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getSerifFontFamily() : iX5WebSettings.getSerifFontFamily();
    }

    public synchronized String getStandardFontFamily() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getStandardFontFamily() : iX5WebSettings.getStandardFontFamily();
    }

    @Deprecated
    public TextSize getTextSize() {
        android.webkit.WebSettings webSettings;
        Enum textSize;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            textSize = iX5WebSettings.getTextSize();
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return null;
            }
            textSize = webSettings.getTextSize();
        }
        return TextSize.valueOf(textSize.name());
    }

    @TargetApi(14)
    public synchronized int getTextZoom() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getTextZoom();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return 0;
        }
        try {
            return webSettings.getTextZoom();
        } catch (Exception unused) {
            Object objA = com.tencent.smtt.utils.j.a(this.f21057b, "getTextZoom");
            if (objA == null) {
                return 0;
            }
            return ((Integer) objA).intValue();
        }
    }

    @Deprecated
    public boolean getUseWebViewBackgroundForOverscrollBackground() {
        android.webkit.WebSettings webSettings;
        Object objA;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getUseWebViewBackgroundForOverscrollBackground();
        }
        if (z2 || (webSettings = this.f21057b) == null || (objA = com.tencent.smtt.utils.j.a(webSettings, "getUseWebViewBackgroundForOverscrollBackground")) == null) {
            return false;
        }
        return ((Boolean) objA).booleanValue();
    }

    public synchronized boolean getUseWideViewPort() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.getUseWideViewPort();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.getUseWideViewPort();
    }

    @TargetApi(3)
    public String getUserAgentString() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        return (!z2 || (iX5WebSettings = this.f21056a) == null) ? (z2 || (webSettings = this.f21057b) == null) ? "" : webSettings.getUserAgentString() : iX5WebSettings.getUserAgentString();
    }

    @TargetApi(11)
    public void setAllowContentAccess(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAllowContentAccess(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setAllowContentAccess", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    @TargetApi(3)
    public void setAllowFileAccess(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAllowFileAccess(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setAllowFileAccess(z2);
        }
    }

    @TargetApi(16)
    public void setAllowFileAccessFromFileURLs(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAllowFileAccessFromFileURLs(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setAllowFileAccessFromFileURLs", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    @TargetApi(16)
    public void setAllowUniversalAccessFromFileURLs(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAllowUniversalAccessFromFileURLs(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setAllowUniversalAccessFromFileURLs", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    @TargetApi(7)
    public void setAppCacheEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAppCacheEnabled(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setAppCacheEnabled(z2);
        }
    }

    @TargetApi(7)
    @Deprecated
    public void setAppCacheMaxSize(long j2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAppCacheMaxSize(j2);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setAppCacheMaxSize(j2);
        }
    }

    @TargetApi(7)
    public void setAppCachePath(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setAppCachePath(str);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setAppCachePath(str);
        }
    }

    public void setBlockNetworkImage(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setBlockNetworkImage(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setBlockNetworkImage(z2);
        }
    }

    @TargetApi(8)
    public synchronized void setBlockNetworkLoads(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setBlockNetworkLoads(z2);
        } else if (z3 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setBlockNetworkLoads(z2);
        }
    }

    @TargetApi(3)
    public void setBuiltInZoomControls(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setBuiltInZoomControls(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setBuiltInZoomControls(z2);
        }
    }

    public void setCacheMode(int i2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setCacheMode(i2);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setCacheMode(i2);
        }
    }

    public synchronized void setCursiveFontFamily(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setCursiveFontFamily(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setCursiveFontFamily(str);
        }
    }

    @TargetApi(5)
    public void setDatabaseEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDatabaseEnabled(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setDatabaseEnabled(z2);
        }
    }

    @TargetApi(5)
    @Deprecated
    public void setDatabasePath(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDatabasePath(str);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setDatabasePath", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    public synchronized void setDefaultFixedFontSize(int i2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDefaultFixedFontSize(i2);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setDefaultFixedFontSize(i2);
        }
    }

    public synchronized void setDefaultFontSize(int i2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDefaultFontSize(i2);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setDefaultFontSize(i2);
        }
    }

    public synchronized void setDefaultTextEncodingName(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDefaultTextEncodingName(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setDefaultTextEncodingName(str);
        }
    }

    @TargetApi(7)
    @Deprecated
    public void setDefaultZoom(ZoomDensity zoomDensity) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDefaultZoom(IX5WebSettings.ZoomDensity.valueOf(zoomDensity.name()));
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.valueOf(zoomDensity.name()));
        }
    }

    @TargetApi(11)
    public void setDisplayZoomControls(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDisplayZoomControls(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setDisplayZoomControls", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    @TargetApi(7)
    public void setDomStorageEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setDomStorageEnabled(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setDomStorageEnabled(z2);
        }
    }

    @TargetApi(11)
    @Deprecated
    public void setEnableSmoothTransition(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setEnableSmoothTransition(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setEnableSmoothTransition", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public synchronized void setFantasyFontFamily(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setFantasyFontFamily(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setFantasyFontFamily(str);
        }
    }

    public synchronized void setFixedFontFamily(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setFixedFontFamily(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setFixedFontFamily(str);
        }
    }

    @TargetApi(5)
    @Deprecated
    public void setGeolocationDatabasePath(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setGeolocationDatabasePath(str);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setGeolocationDatabasePath(str);
        }
    }

    @TargetApi(5)
    public void setGeolocationEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setGeolocationEnabled(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setGeolocationEnabled(z2);
        }
    }

    public synchronized void setJavaScriptCanOpenWindowsAutomatically(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setJavaScriptCanOpenWindowsAutomatically(z2);
        } else if (z3 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setJavaScriptCanOpenWindowsAutomatically(z2);
        }
    }

    @Deprecated
    public void setJavaScriptEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        try {
            boolean z3 = this.f21058c;
            if (z3 && (iX5WebSettings = this.f21056a) != null) {
                iX5WebSettings.setJavaScriptEnabled(z2);
            } else if (z3 || (webSettings = this.f21057b) == null) {
            } else {
                webSettings.setJavaScriptEnabled(z2);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setLayoutAlgorithm(IX5WebSettings.LayoutAlgorithm.valueOf(layoutAlgorithm.name()));
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.valueOf(layoutAlgorithm.name()));
        }
    }

    @Deprecated
    public void setLightTouchEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setLightTouchEnabled(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setLightTouchEnabled(z2);
        }
    }

    @TargetApi(7)
    public void setLoadWithOverviewMode(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setLoadWithOverviewMode(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setLoadWithOverviewMode(z2);
        }
    }

    public void setLoadsImagesAutomatically(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setLoadsImagesAutomatically(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setLoadsImagesAutomatically(z2);
        }
    }

    @TargetApi(17)
    public void setMediaPlaybackRequiresUserGesture(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setMediaPlaybackRequiresUserGesture(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setMediaPlaybackRequiresUserGesture", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public synchronized void setMinimumFontSize(int i2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setMinimumFontSize(i2);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setMinimumFontSize(i2);
        }
    }

    public synchronized void setMinimumLogicalFontSize(int i2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setMinimumLogicalFontSize(i2);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setMinimumLogicalFontSize(i2);
        }
    }

    @TargetApi(21)
    public void setMixedContentMode(int i2) {
        android.webkit.WebSettings webSettings;
        boolean z2 = this.f21058c;
        if ((z2 && this.f21056a != null) || z2 || (webSettings = this.f21057b) == null) {
            return;
        }
        com.tencent.smtt.utils.j.a(webSettings, "setMixedContentMode", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(i2));
    }

    @Deprecated
    public void setNavDump(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setNavDump(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setNavDump", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public void setNeedInitialFocus(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setNeedInitialFocus(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setNeedInitialFocus(z2);
        }
    }

    @TargetApi(8)
    @Deprecated
    public synchronized void setPluginState(PluginState pluginState) {
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setPluginState(IX5WebSettings.PluginState.valueOf(pluginState.name()));
        } else if (z2 || this.f21057b == null) {
        } else {
            com.tencent.smtt.utils.j.a(this.f21057b, "setPluginState", (Class<?>[]) new Class[]{WebSettings.PluginState.class}, WebSettings.PluginState.valueOf(pluginState.name()));
        }
    }

    @Deprecated
    public void setPluginsEnabled(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setPluginsEnabled(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setPluginsEnabled", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    @Deprecated
    public synchronized void setPluginsPath(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setPluginsPath(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            com.tencent.smtt.utils.j.a(webSettings, "setPluginsPath", (Class<?>[]) new Class[]{String.class}, str);
        }
    }

    @Deprecated
    public void setRenderPriority(RenderPriority renderPriority) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setRenderPriority(IX5WebSettings.RenderPriority.valueOf(renderPriority.name()));
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setRenderPriority(WebSettings.RenderPriority.valueOf(renderPriority.name()));
        }
    }

    public void setSafeBrowsingEnabled(boolean z2) {
        IX5WebSettings iX5WebSettings;
        android.webkit.WebSettings webSettings;
        boolean z3 = this.f21058c;
        if (!z3 && (webSettings = this.f21057b) != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                webSettings.setSafeBrowsingEnabled(z2);
            }
        } else {
            if (!z3 || (iX5WebSettings = this.f21056a) == null) {
                return;
            }
            try {
                iX5WebSettings.setSafeBrowsingEnabled(z2);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public synchronized void setSansSerifFontFamily(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setSansSerifFontFamily(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setSansSerifFontFamily(str);
        }
    }

    @Deprecated
    public void setSaveFormData(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setSaveFormData(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setSaveFormData(z2);
        }
    }

    @Deprecated
    public void setSavePassword(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setSavePassword(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setSavePassword(z2);
        }
    }

    public synchronized void setSerifFontFamily(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setSerifFontFamily(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setSerifFontFamily(str);
        }
    }

    public synchronized void setStandardFontFamily(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setStandardFontFamily(str);
        } else if (z2 || (webSettings = this.f21057b) == null) {
        } else {
            webSettings.setStandardFontFamily(str);
        }
    }

    public void setSupportMultipleWindows(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setSupportMultipleWindows(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setSupportMultipleWindows(z2);
        }
    }

    public void setSupportZoom(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setSupportZoom(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setSupportZoom(z2);
        }
    }

    @Deprecated
    public void setTextSize(TextSize textSize) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setTextSize(IX5WebSettings.TextSize.valueOf(textSize.name()));
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setTextSize(WebSettings.TextSize.valueOf(textSize.name()));
        }
    }

    @TargetApi(14)
    public synchronized void setTextZoom(int i2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setTextZoom(i2);
        } else if (!z2 && (webSettings = this.f21057b) != null) {
            try {
                webSettings.setTextZoom(i2);
            } catch (Exception unused) {
                com.tencent.smtt.utils.j.a(this.f21057b, "setTextZoom", (Class<?>[]) new Class[]{Integer.TYPE}, Integer.valueOf(i2));
            }
        }
    }

    @Deprecated
    public void setUseWebViewBackgroundForOverscrollBackground(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setUseWebViewBackgroundForOverscrollBackground(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            com.tencent.smtt.utils.j.a(webSettings, "setUseWebViewBackgroundForOverscrollBackground", (Class<?>[]) new Class[]{Boolean.TYPE}, Boolean.valueOf(z2));
        }
    }

    public void setUseWideViewPort(boolean z2) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z3 = this.f21058c;
        if (z3 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setUseWideViewPort(z2);
        } else {
            if (z3 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setUseWideViewPort(z2);
        }
    }

    public void setUserAgent(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setUserAgent(str);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setUserAgentString(str);
        }
    }

    @TargetApi(3)
    public void setUserAgentString(String str) {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            iX5WebSettings.setUserAgentString(str);
        } else {
            if (z2 || (webSettings = this.f21057b) == null) {
                return;
            }
            webSettings.setUserAgentString(str);
        }
    }

    public synchronized boolean supportMultipleWindows() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.supportMultipleWindows();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.supportMultipleWindows();
    }

    public boolean supportZoom() {
        android.webkit.WebSettings webSettings;
        IX5WebSettings iX5WebSettings;
        boolean z2 = this.f21058c;
        if (z2 && (iX5WebSettings = this.f21056a) != null) {
            return iX5WebSettings.supportZoom();
        }
        if (z2 || (webSettings = this.f21057b) == null) {
            return false;
        }
        return webSettings.supportZoom();
    }
}
