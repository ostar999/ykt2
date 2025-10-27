package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes6.dex */
public class WebViewHandler implements IAdUnitViewHandler {
    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public boolean create(AdUnitActivity adUnitActivity) {
        return true;
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public boolean destroy() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (WebViewApp.getCurrentApp() == null || WebViewApp.getCurrentApp().getWebView() == null) {
            return true;
        }
        ViewUtilities.removeViewFromParent(WebViewApp.getCurrentApp().getWebView());
        return true;
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public View getView() {
        if (WebViewApp.getCurrentApp() != null) {
            return WebViewApp.getCurrentApp().getWebView();
        }
        return null;
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onCreate(AdUnitActivity adUnitActivity, Bundle bundle) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onDestroy(AdUnitActivity adUnitActivity) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        destroy();
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onPause(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onResume(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onStart(AdUnitActivity adUnitActivity) {
    }

    @Override // com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler
    public void onStop(AdUnitActivity adUnitActivity) {
    }
}
