package com.tencent.smtt.export.external.interfaces;

/* loaded from: classes6.dex */
public interface IX5WebSettings {
    public static final int DEFAULT_CACHE_CAPACITY = 15;
    public static final int LOAD_CACHE_AD = 100;
    public static final int LOAD_CACHE_ELSE_NETWORK = 1;
    public static final int LOAD_CACHE_ONLY = 3;
    public static final int LOAD_DEFAULT = -1;
    public static final int LOAD_NORMAL = 0;
    public static final int LOAD_NO_CACHE = 2;

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

    boolean enableSmoothTransition();

    boolean getAllowContentAccess();

    boolean getAllowFileAccess();

    boolean getBlockNetworkImage();

    boolean getBlockNetworkLoads();

    boolean getBuiltInZoomControls();

    int getCacheMode();

    String getCursiveFontFamily();

    boolean getDatabaseEnabled();

    String getDatabasePath();

    int getDefaultFixedFontSize();

    int getDefaultFontSize();

    String getDefaultTextEncodingName();

    ZoomDensity getDefaultZoom();

    boolean getDisplayZoomControls();

    boolean getDomStorageEnabled();

    String getFantasyFontFamily();

    String getFixedFontFamily();

    boolean getJavaScriptCanOpenWindowsAutomatically();

    boolean getJavaScriptEnabled();

    LayoutAlgorithm getLayoutAlgorithm();

    boolean getLightTouchEnabled();

    boolean getLoadWithOverviewMode();

    boolean getLoadsImagesAutomatically();

    boolean getMediaPlaybackRequiresUserGesture();

    int getMinimumFontSize();

    int getMinimumLogicalFontSize();

    int getMixedContentMode();

    boolean getNavDump();

    PluginState getPluginState();

    boolean getPluginsEnabled();

    String getPluginsPath();

    boolean getSafeBrowsingEnabled();

    String getSansSerifFontFamily();

    boolean getSaveFormData();

    boolean getSavePassword();

    String getSerifFontFamily();

    String getStandardFontFamily();

    TextSize getTextSize();

    int getTextZoom();

    boolean getUseWebViewBackgroundForOverscrollBackground();

    boolean getUseWideViewPort();

    String getUserAgent();

    String getUserAgentString();

    void setAllowContentAccess(boolean z2);

    void setAllowFileAccess(boolean z2);

    void setAllowFileAccessFromFileURLs(boolean z2);

    void setAllowUniversalAccessFromFileURLs(boolean z2);

    void setAppCacheEnabled(boolean z2);

    void setAppCacheMaxSize(long j2);

    void setAppCachePath(String str);

    void setBlockNetworkImage(boolean z2);

    void setBlockNetworkLoads(boolean z2);

    void setBuiltInZoomControls(boolean z2);

    void setCacheMode(int i2);

    void setCursiveFontFamily(String str);

    void setDatabaseEnabled(boolean z2);

    void setDatabasePath(String str);

    void setDefaultDatabasePath(boolean z2);

    void setDefaultFixedFontSize(int i2);

    void setDefaultFontSize(int i2);

    void setDefaultTextEncodingName(String str);

    void setDefaultZoom(ZoomDensity zoomDensity);

    void setDisplayZoomControls(boolean z2);

    void setDomStorageEnabled(boolean z2);

    void setEnableSmoothTransition(boolean z2);

    void setFantasyFontFamily(String str);

    void setFixedFontFamily(String str);

    void setGeolocationDatabasePath(String str);

    void setGeolocationEnabled(boolean z2);

    void setJavaScriptCanOpenWindowsAutomatically(boolean z2);

    void setJavaScriptEnabled(boolean z2);

    void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm);

    void setLightTouchEnabled(boolean z2);

    void setLoadWithOverviewMode(boolean z2);

    void setLoadsImagesAutomatically(boolean z2);

    void setMediaPlaybackRequiresUserGesture(boolean z2);

    void setMinimumFontSize(int i2);

    void setMinimumLogicalFontSize(int i2);

    void setNavDump(boolean z2);

    void setNeedInitialFocus(boolean z2);

    void setPluginEnabled(boolean z2);

    void setPluginState(PluginState pluginState);

    void setPluginsEnabled(boolean z2);

    void setPluginsPath(String str);

    void setRenderPriority(RenderPriority renderPriority);

    void setSafeBrowsingEnabled(boolean z2);

    void setSansSerifFontFamily(String str);

    void setSaveFormData(boolean z2);

    void setSavePassword(boolean z2);

    void setSerifFontFamily(String str);

    void setStandardFontFamily(String str);

    void setSupportMultipleWindows(boolean z2);

    void setSupportZoom(boolean z2);

    void setTextSize(TextSize textSize);

    void setTextZoom(int i2);

    void setUseWebViewBackgroundForOverscrollBackground(boolean z2);

    void setUseWideViewPort(boolean z2);

    void setUserAgent(String str);

    void setUserAgent(String str, boolean z2);

    void setUserAgentString(String str);

    boolean supportMultipleWindows();

    boolean supportZoom();
}
