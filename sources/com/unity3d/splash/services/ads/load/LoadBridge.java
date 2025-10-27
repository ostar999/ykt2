package com.unity3d.splash.services.ads.load;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class LoadBridge implements ILoadBridge {

    public enum LoadEvent {
        LOAD_PLACEMENTS
    }

    @Override // com.unity3d.splash.services.ads.load.ILoadBridge
    public void loadPlacements(Map map) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            JSONObject jSONObject = new JSONObject();
            for (Map.Entry entry : map.entrySet()) {
                jSONObject.put((String) entry.getKey(), ((Integer) entry.getValue()).intValue());
            }
            if (WebViewApp.getCurrentApp() != null) {
                WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LOAD_API, LoadEvent.LOAD_PLACEMENTS, jSONObject);
            }
        } catch (Exception e2) {
            DeviceLog.error("An exception was thrown while loading placements " + e2.getLocalizedMessage());
        }
    }
}
