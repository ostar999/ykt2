package com.unity3d.splash.services.core.cache;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.sdk.android.oss.common.RequestParameters;
import com.tencent.open.SocialConstants;
import com.unity3d.splash.services.core.api.Request;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.WebRequest;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
class CacheThreadHandler extends Handler {
    private WebRequest _currentRequest = null;
    private boolean _canceled = false;
    private boolean _active = false;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0306 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:150:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v16, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r3v17, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v45, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v10, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v12, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v13, types: [boolean] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v35, types: [long] */
    /* JADX WARN: Type inference failed for: r6v36 */
    /* JADX WARN: Type inference failed for: r6v37 */
    /* JADX WARN: Type inference failed for: r6v38 */
    /* JADX WARN: Type inference failed for: r6v39 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v40 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v9, types: [boolean] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void downloadFile(java.lang.String r24, java.lang.String r25, int r26, int r27, final int r28, java.util.HashMap r29, boolean r30) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 810
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.splash.services.core.cache.CacheThreadHandler.downloadFile(java.lang.String, java.lang.String, int, int, int, java.util.HashMap, boolean):void");
    }

    private WebRequest getWebRequest(String str, int i2, int i3, HashMap map) {
        HashMap map2 = new HashMap();
        if (map != null) {
            map2.putAll(map);
        }
        return new WebRequest(str, "GET", map2, i2, i3);
    }

    private void postProcessDownload(long j2, String str, File file, long j3, long j4, boolean z2, int i2, Map map) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - j2;
        if (!file.setReadable(true, false)) {
            DeviceLog.debug("Unity Ads cache: could not set file readable!");
        }
        if (z2) {
            DeviceLog.debug("Unity Ads cache: downloading of " + str + " stopped");
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.CACHE, CacheEvent.DOWNLOAD_STOPPED, str, Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(jElapsedRealtime), Integer.valueOf(i2), Request.getResponseHeadersMap(map));
            return;
        }
        DeviceLog.debug("Unity Ads cache: File " + file.getName() + " of " + j3 + " bytes downloaded in " + jElapsedRealtime + "ms");
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.CACHE, CacheEvent.DOWNLOAD_END, str, Long.valueOf(j3), Long.valueOf(j4), Long.valueOf(jElapsedRealtime), Integer.valueOf(i2), Request.getResponseHeadersMap(map));
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) throws Throwable {
        HashMap map;
        Bundle data = message.getData();
        String string = data.getString(SocialConstants.PARAM_SOURCE);
        data.remove(SocialConstants.PARAM_SOURCE);
        String string2 = data.getString(TypedValues.AttributesType.S_TARGET);
        data.remove(TypedValues.AttributesType.S_TARGET);
        int i2 = data.getInt("connectTimeout");
        data.remove("connectTimeout");
        int i3 = data.getInt("readTimeout");
        data.remove("readTimeout");
        int i4 = data.getInt("progressInterval");
        data.remove("progressInterval");
        boolean z2 = data.getBoolean(RequestParameters.SUBRESOURCE_APPEND, false);
        data.remove(RequestParameters.SUBRESOURCE_APPEND);
        if (data.size() > 0) {
            DeviceLog.debug("There are headers left in data, reading them");
            HashMap map2 = new HashMap();
            for (String str : data.keySet()) {
                map2.put(str, Arrays.asList(data.getStringArray(str)));
            }
            map = map2;
        } else {
            map = null;
        }
        File file = new File(string2);
        if ((z2 && !file.exists()) || (!z2 && file.exists())) {
            this._active = false;
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.CACHE, CacheEvent.DOWNLOAD_ERROR, CacheError.FILE_STATE_WRONG, string, string2, Boolean.valueOf(z2), Boolean.valueOf(file.exists()));
        } else {
            if (message.what != 1) {
                return;
            }
            downloadFile(string, string2, i2, i3, i4, map, z2);
        }
    }

    public boolean isActive() {
        return this._active;
    }

    public void setCancelStatus(boolean z2) {
        WebRequest webRequest;
        this._canceled = z2;
        if (!z2 || (webRequest = this._currentRequest) == null) {
            return;
        }
        this._active = false;
        webRequest.cancel();
    }
}
