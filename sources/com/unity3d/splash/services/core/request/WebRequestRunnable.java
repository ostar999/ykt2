package com.unity3d.splash.services.core.request;

import android.os.Bundle;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class WebRequestRunnable implements Runnable {
    private final String _body;
    private boolean _canceled = false;
    private final int _connectTimeout;
    private WebRequest _currentRequest;
    private final Map _headers;
    private final IWebRequestListener _listener;
    private final int _readTimeout;
    private final String _type;
    private final String _url;

    public WebRequestRunnable(String str, String str2, String str3, int i2, int i3, Map map, IWebRequestListener iWebRequestListener) {
        this._url = str;
        this._type = str2;
        this._body = str3;
        this._connectTimeout = i2;
        this._readTimeout = i3;
        this._headers = map;
        this._listener = iWebRequestListener;
    }

    private Map getResponseHeaders(Bundle bundle) {
        if (bundle.size() <= 0) {
            return null;
        }
        HashMap map = new HashMap();
        for (String str : bundle.keySet()) {
            String[] stringArray = bundle.getStringArray(str);
            if (stringArray != null) {
                map.put(str, new ArrayList(Arrays.asList(stringArray)));
            }
        }
        return map;
    }

    private void makeRequest(String str, String str2, Map map, String str3, int i2, int i3) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (this._canceled) {
            return;
        }
        WebRequest webRequest = new WebRequest(str, str2, map, i2, i3);
        this._currentRequest = webRequest;
        if (str3 != null) {
            webRequest.setBody(str3);
        }
        try {
            String strMakeRequest = this._currentRequest.makeRequest();
            if (this._currentRequest.isCanceled()) {
                return;
            }
            Bundle bundle = new Bundle();
            for (String str4 : this._currentRequest.getResponseHeaders().keySet()) {
                if (str4 != null && !str4.contentEquals("null")) {
                    String[] strArr = new String[((List) this._currentRequest.getResponseHeaders().get(str4)).size()];
                    for (int i4 = 0; i4 < ((List) this._currentRequest.getResponseHeaders().get(str4)).size(); i4++) {
                        strArr[i4] = (String) ((List) this._currentRequest.getResponseHeaders().get(str4)).get(i4);
                    }
                    bundle.putStringArray(str4, strArr);
                }
            }
            if (this._currentRequest.isCanceled()) {
                return;
            }
            onSucceed(strMakeRequest, this._currentRequest.getResponseCode(), getResponseHeaders(bundle));
        } catch (NetworkIOException | IOException | IllegalArgumentException | IllegalStateException e2) {
            DeviceLog.exception("Error completing request", e2);
            onFailed(e2.getClass().getName() + ": " + e2.getMessage());
        }
    }

    private void onFailed(String str) {
        this._listener.onFailed(this._url, str);
    }

    private void onSucceed(String str, int i2, Map map) {
        this._listener.onComplete(this._url, str, i2, map);
    }

    @Override // java.lang.Runnable
    public void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        DeviceLog.debug("Handling request message: " + this._url + " type=" + this._type);
        try {
            makeRequest(this._url, this._type, this._headers, this._body, this._connectTimeout, this._readTimeout);
        } catch (MalformedURLException e2) {
            DeviceLog.exception("Malformed URL", e2);
            onFailed("Malformed URL");
        }
    }

    public void setCancelStatus(boolean z2) {
        WebRequest webRequest;
        this._canceled = z2;
        if (!z2 || (webRequest = this._currentRequest) == null) {
            return;
        }
        webRequest.cancel();
    }
}
