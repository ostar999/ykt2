package com.tencent.smtt.export.external.extension.proxy;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class ProxyWebViewClientExtension implements IX5WebViewClientExtension {
    private static boolean sCompatibleOnMetricsSavedCountReceived = true;
    private static boolean sCompatibleOnPageLoadingStartedAndFinished = true;
    protected IX5WebViewClientExtension mWebViewClientExt;

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean allowJavaScriptOpenWindowAutomatically(String str, String str2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.allowJavaScriptOpenWindowAutomatically(str, str2);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void computeScroll(View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.computeScroll(view);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void didFirstVisuallyNonEmptyPaint() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.didFirstVisuallyNonEmptyPaint();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean dispatchTouchEvent(MotionEvent motionEvent, View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.dispatchTouchEvent(motionEvent, view);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void documentAvailableInMainFrame() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.documentAvailableInMainFrame();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public int getHostByName(String str, List<String> list) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            try {
                return iX5WebViewClientExtension.getHostByName(str, list);
            } catch (NoSuchMethodError unused) {
            }
        }
        return 0;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void handlePluginTag(String str, String str2, boolean z2, String str3) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.handlePluginTag(str, str2, z2, str3);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void hideAddressBar() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.hideAddressBar();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void invalidate() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.invalidate();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean notifyAutoAudioPlay(String str, JsResult jsResult) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension == null) {
            return false;
        }
        try {
            return iX5WebViewClientExtension.notifyAutoAudioPlay(str, jsResult);
        } catch (NoSuchMethodError e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean notifyJavaScriptOpenWindowsBlocked(String str, String[] strArr, ValueCallback<Boolean> valueCallback, boolean z2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.notifyJavaScriptOpenWindowsBlocked(str, strArr, valueCallback, z2);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onDoubleTapStart() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onDoubleTapStart();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onFakeLoginRecognised(Bundle bundle) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onFakeLoginRecognised(bundle);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onFlingScrollBegin(int i2, int i3, int i4) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onFlingScrollBegin(i2, i3, i4);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onFlingScrollEnd() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onFlingScrollEnd();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onHideListBox() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onHideListBox();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onHistoryItemChanged() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onHistoryItemChanged();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onInputBoxTextChanged(IX5WebViewExtension iX5WebViewExtension, String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onInputBoxTextChanged(iX5WebViewExtension, str);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean onInterceptTouchEvent(MotionEvent motionEvent, View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.onInterceptTouchEvent(motionEvent, view);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onMetricsSavedCountReceived(String str, boolean z2, long j2, String str2, int i2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension == null || !sCompatibleOnMetricsSavedCountReceived) {
            return;
        }
        try {
            iX5WebViewClientExtension.onMetricsSavedCountReceived(str, z2, j2, str2, i2);
        } catch (NoSuchMethodError e2) {
            if (e2.getMessage() == null || !e2.getMessage().contains("onMetricsSavedCountReceived")) {
                throw e2;
            }
            Log.d("incompatible-oldcore", "IX5WebViewClientExtension.onMetricsSavedCountReceived");
            sCompatibleOnMetricsSavedCountReceived = false;
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public Object onMiscCallBack(String str, Bundle bundle) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.onMiscCallBack(str, bundle);
        }
        return null;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public Object onMiscCallBack(String str, Bundle bundle, Object obj, Object obj2, Object obj3, Object obj4) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.onMiscCallBack(str, bundle, obj, obj2, obj3, obj4);
        }
        return null;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onMissingPluginClicked(String str, String str2, String str3, int i2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onMissingPluginClicked(str, str2, str3, i2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onNativeCrashReport(int i2, String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onNativeCrashReport(i2, str);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onOverScrolled(int i2, int i3, boolean z2, boolean z3, View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onOverScrolled(i2, i3, z2, z3, view);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onPinchToZoomStart() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onPinchToZoomStart();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onPreReadFinished() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onPreReadFinished();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onPrefetchResourceHit(boolean z2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onPrefetchResourceHit(z2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onPreloadCallback(int i2, String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onPreloadCallback(i2, str);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onPromptScaleSaved() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onPromptScaleSaved();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onReceivedSslErrorCancel() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onReceivedSslErrorCancel();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onReceivedViewSource(String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onReceivedViewSource(str);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onReportAdFilterInfo(int i2, int i3, String str, boolean z2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onReportAdFilterInfo(i2, i3, str, z2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onReportHtmlInfo(int i2, String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onReportHtmlInfo(i2, str);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onReportMemoryCachedResponse(String str, int i2, HashMap<String, String> map) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onReportMemoryCachedResponse(str, i2, map);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onReportResponseHeaders(String str, int i2, HashMap<String, String> map) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onReportResponseHeaders(str, i2, map);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onResponseReceived(WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse, int i2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onResponseReceived(webResourceRequest, webResourceResponse, i2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onScrollChanged(i2, i3, i4, i5);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onScrollChanged(int i2, int i3, int i4, int i5, View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onScrollChanged(i2, i3, i4, i5, view);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onSetButtonStatus(boolean z2, boolean z3) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onSetButtonStatus(z2, z3);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onShowListBox(String[] strArr, int[] iArr, int[] iArr2, int i2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onShowListBox(strArr, iArr, iArr2, i2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean onShowLongClickPopupMenu() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension == null) {
            return false;
        }
        try {
            return iX5WebViewClientExtension.onShowLongClickPopupMenu();
        } catch (NoSuchMethodError e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onShowMutilListBox(String[] strArr, int[] iArr, int[] iArr2, int[] iArr3) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onShowMutilListBox(strArr, iArr, iArr2, iArr3);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onSlidingTitleOffScreen(int i2, int i3) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onSlidingTitleOffScreen(i2, i3);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onSoftKeyBoardHide(int i2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onSoftKeyBoardHide(i2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onSoftKeyBoardShow() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onSoftKeyBoardShow();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean onTouchEvent(MotionEvent motionEvent, View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.onTouchEvent(motionEvent, view);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onTransitionToCommitted() {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onTransitionToCommitted();
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onUploadProgressChange(int i2, int i3, String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onUploadProgressChange(i2, i3, str);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onUploadProgressStart(int i2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onUploadProgressStart(i2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void onUrlChange(String str, String str2) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.onUrlChange(str, str2);
        }
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean overScrollBy(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, boolean z2, View view) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.overScrollBy(i2, i3, i4, i5, i6, i7, i8, i9, z2, view);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public boolean preShouldOverrideUrlLoading(IX5WebViewExtension iX5WebViewExtension, String str) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            return iX5WebViewClientExtension.preShouldOverrideUrlLoading(iX5WebViewExtension, str);
        }
        return false;
    }

    @Override // com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension
    public void showTranslateBubble(int i2, String str, String str2, int i3) {
        IX5WebViewClientExtension iX5WebViewClientExtension = this.mWebViewClientExt;
        if (iX5WebViewClientExtension != null) {
            iX5WebViewClientExtension.showTranslateBubble(i2, str, str2, i3);
        }
    }
}
