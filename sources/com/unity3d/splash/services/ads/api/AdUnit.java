package com.unity3d.splash.services.ads.api;

import android.content.Intent;
import android.os.Build;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.WindowInsets;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.plv.business.sub.danmaku.entity.PLVDanmakuInfo;
import com.tencent.open.SocialConstants;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitError;
import com.unity3d.splash.services.ads.adunit.AdUnitMotionEvent;
import com.unity3d.splash.services.ads.adunit.AdUnitSoftwareActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitTransparentActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitTransparentSoftwareActivity;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class AdUnit {
    private static AdUnitActivity _adUnitActivity = null;
    private static int _currentActivityId = -1;

    private AdUnit() {
    }

    @WebViewExposed
    public static void clearMotionEventCapture(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().getLayout().clearCapture();
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void close(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().finish();
            webViewCallback.invoke(new Object[0]);
        }
    }

    @WebViewExposed
    public static void endMotionEventCapture(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().getLayout().endCapture();
            webViewCallback.invoke(new Object[0]);
        }
    }

    public static AdUnitActivity getAdUnitActivity() {
        return _adUnitActivity;
    }

    public static int getCurrentAdUnitActivityId() {
        return _currentActivityId;
    }

    @WebViewExposed
    public static void getCurrentMotionEventCount(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() != null) {
            webViewCallback.invoke(Integer.valueOf(getAdUnitActivity().getLayout().getCurrentEventCount()));
        } else {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        }
    }

    private static ArrayList getKeyEventList(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        int iIntValue = 0;
        while (true) {
            Integer numValueOf = Integer.valueOf(iIntValue);
            if (numValueOf.intValue() >= jSONArray.length()) {
                return arrayList;
            }
            arrayList.add(Integer.valueOf(jSONArray.getInt(numValueOf.intValue())));
            iIntValue = numValueOf.intValue() + 1;
        }
    }

    @WebViewExposed
    public static void getMotionEventCount(JSONArray jSONArray, WebViewCallback webViewCallback) throws IllegalAccessException, JSONException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                arrayList.add(Integer.valueOf(jSONArray.getInt(i2)));
            } catch (Exception e2) {
                DeviceLog.exception("Error retrieving int from eventTypes", e2);
            }
        }
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout().getCurrentEventCount() >= getAdUnitActivity().getLayout().getMaxEventCount()) {
            webViewCallback.error(AdUnitError.MAX_MOTION_EVENT_COUNT_REACHED, new Object[0]);
            return;
        }
        SparseIntArray eventCount = getAdUnitActivity().getLayout().getEventCount(arrayList);
        JSONObject jSONObject = new JSONObject();
        for (int i3 = 0; i3 < eventCount.size(); i3++) {
            int iKeyAt = eventCount.keyAt(i3);
            try {
                jSONObject.put(Integer.toString(iKeyAt), eventCount.get(iKeyAt));
            } catch (Exception e3) {
                DeviceLog.exception("Error building response JSON", e3);
            }
        }
        webViewCallback.invoke(jSONObject);
    }

    @WebViewExposed
    public static void getMotionEventData(JSONObject jSONObject, WebViewCallback webViewCallback) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        JSONArray jSONArray;
        Iterator<String> itKeys = jSONObject.keys();
        SparseArray sparseArray = new SparseArray();
        while (true) {
            if (!itKeys.hasNext()) {
                break;
            }
            String next = itKeys.next();
            int i2 = Integer.parseInt(next);
            if (sparseArray.get(i2) == null) {
                sparseArray.put(i2, new ArrayList());
            }
            try {
                jSONArray = jSONObject.getJSONArray(next);
            } catch (Exception e2) {
                DeviceLog.exception("Couldn't fetch keyIndices", e2);
                jSONArray = null;
            }
            if (jSONArray != null) {
                for (int i3 = 0; i3 < jSONArray.length(); i3++) {
                    try {
                        ((ArrayList) sparseArray.get(i2)).add(Integer.valueOf(jSONArray.getInt(i3)));
                    } catch (Exception e3) {
                        DeviceLog.exception("Couldn't add value to requested infos", e3);
                    }
                }
            }
        }
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
            return;
        }
        if (getAdUnitActivity().getLayout().getCurrentEventCount() >= getAdUnitActivity().getLayout().getMaxEventCount()) {
            webViewCallback.error(AdUnitError.MAX_MOTION_EVENT_COUNT_REACHED, new Object[0]);
            return;
        }
        SparseArray events = getAdUnitActivity().getLayout().getEvents(sparseArray);
        JSONObject jSONObject2 = new JSONObject();
        for (int i4 = 0; i4 < events.size(); i4++) {
            int iKeyAt = events.keyAt(i4);
            SparseArray sparseArray2 = (SparseArray) events.get(iKeyAt);
            JSONObject jSONObject3 = new JSONObject();
            for (int i5 = 0; i5 < sparseArray2.size(); i5++) {
                JSONObject jSONObject4 = new JSONObject();
                int iKeyAt2 = sparseArray2.keyAt(i5);
                AdUnitMotionEvent adUnitMotionEvent = (AdUnitMotionEvent) sparseArray2.get(iKeyAt2);
                try {
                    jSONObject4.put("action", adUnitMotionEvent.getAction());
                    jSONObject4.put("isObscured", adUnitMotionEvent.isObscured());
                    jSONObject4.put("toolType", adUnitMotionEvent.getToolType());
                    jSONObject4.put(SocialConstants.PARAM_SOURCE, adUnitMotionEvent.getSource());
                    jSONObject4.put("deviceId", adUnitMotionEvent.getDeviceId());
                    jSONObject4.put("x", adUnitMotionEvent.getX());
                    jSONObject4.put("y", adUnitMotionEvent.getY());
                    jSONObject4.put("eventTime", adUnitMotionEvent.getEventTime());
                    jSONObject4.put("pressure", adUnitMotionEvent.getPressure());
                    jSONObject4.put(DatabaseManager.SIZE, adUnitMotionEvent.getSize());
                    jSONObject3.put(Integer.toString(iKeyAt2), jSONObject4);
                } catch (Exception e4) {
                    DeviceLog.debug("Couldn't construct event info", e4);
                }
            }
            try {
                jSONObject2.put(Integer.toString(iKeyAt), jSONObject3);
            } catch (Exception e5) {
                DeviceLog.debug("Couldn't construct info object", e5);
            }
        }
        webViewCallback.invoke(jSONObject2);
    }

    @WebViewExposed
    public static void getOrientation(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(Integer.valueOf(getAdUnitActivity().getRequestedOrientation()));
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void getSafeAreaInsets(WebViewCallback webViewCallback) throws IllegalAccessException, JSONException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null || getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        if (Build.VERSION.SDK_INT < 28) {
            webViewCallback.error(AdUnitError.API_LEVEL_ERROR, new Object[0]);
            return;
        }
        WindowInsets rootWindowInsets = getAdUnitActivity().getLayout().getRootWindowInsets();
        if (rootWindowInsets == null) {
            webViewCallback.error(AdUnitError.NO_DISPLAY_CUTOUT_AVAILABLE, new Object[0]);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            Object objInvoke = rootWindowInsets.getClass().getMethod("getDisplayCutout", new Class[0]).invoke(rootWindowInsets, new Object[0]);
            if (objInvoke == null) {
                webViewCallback.error(AdUnitError.NO_DISPLAY_CUTOUT_AVAILABLE, new Object[0]);
                return;
            }
            Object objInvoke2 = objInvoke.getClass().getMethod("getSafeInsetTop", new Class[0]).invoke(objInvoke, new Object[0]);
            Object objInvoke3 = objInvoke.getClass().getMethod("getSafeInsetRight", new Class[0]).invoke(objInvoke, new Object[0]);
            Object objInvoke4 = objInvoke.getClass().getMethod("getSafeInsetBottom", new Class[0]).invoke(objInvoke, new Object[0]);
            Object objInvoke5 = objInvoke.getClass().getMethod("getSafeInsetLeft", new Class[0]).invoke(objInvoke, new Object[0]);
            jSONObject.put(PLVDanmakuInfo.FONTMODE_TOP, objInvoke2);
            jSONObject.put("right", objInvoke3);
            jSONObject.put(PLVDanmakuInfo.FONTMODE_BOTTOM, objInvoke4);
            jSONObject.put("left", objInvoke5);
            webViewCallback.invoke(jSONObject);
        } catch (IllegalAccessException e2) {
            e = e2;
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_INVOKE_FAILED, new Object[0]);
            DeviceLog.debug("Error while calling displayCutout getter", e);
        } catch (NoSuchMethodException e3) {
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_METHOD_NOT_AVAILABLE, new Object[0]);
            DeviceLog.debug("Method getDisplayCutout not found", e3);
        } catch (InvocationTargetException e4) {
            e = e4;
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_INVOKE_FAILED, new Object[0]);
            DeviceLog.debug("Error while calling displayCutout getter", e);
        } catch (JSONException e5) {
            webViewCallback.error(AdUnitError.DISPLAY_CUTOUT_JSON_ERROR, new Object[0]);
            DeviceLog.debug("JSON error while constructing display cutout object", e5);
        }
    }

    @WebViewExposed
    public static void getViewFrame(String str, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getViewFrame(str) == null) {
            webViewCallback.error(AdUnitError.UNKNOWN_VIEW, new Object[0]);
        } else {
            Map viewFrame = getAdUnitActivity().getViewFrame(str);
            webViewCallback.invoke(viewFrame.get("x"), viewFrame.get("y"), viewFrame.get("width"), viewFrame.get("height"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String[] getViewList(JSONArray jSONArray) {
        String[] strArr = new String[jSONArray.length()];
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            strArr[i2] = jSONArray.getString(i2);
        }
        return strArr;
    }

    @WebViewExposed
    public static void getViews(WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(new JSONArray((Collection) Arrays.asList(getAdUnitActivity().getViews())));
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        open(num, jSONArray, num2, null, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        open(num, jSONArray, num2, jSONArray2, 0, Boolean.TRUE, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, Integer num3, Boolean bool, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        open(num, jSONArray, num2, jSONArray2, num3, bool, Boolean.FALSE, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, Integer num3, Boolean bool, Boolean bool2, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        open(num, jSONArray, num2, jSONArray2, num3, bool, bool2, 0, webViewCallback);
    }

    @WebViewExposed
    public static void open(Integer num, JSONArray jSONArray, Integer num2, JSONArray jSONArray2, Integer num3, Boolean bool, Boolean bool2, Integer num4, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Intent intent;
        if (!bool.booleanValue() && bool2.booleanValue()) {
            DeviceLog.debug("Unity Ads opening new transparent ad unit activity, hardware acceleration disabled");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitTransparentSoftwareActivity.class);
        } else if (bool.booleanValue() && !bool2.booleanValue()) {
            DeviceLog.debug("Unity Ads opening new hardware accelerated ad unit activity");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitActivity.class);
        } else if (bool.booleanValue() && bool2.booleanValue()) {
            DeviceLog.debug("Unity Ads opening new hardware accelerated transparent ad unit activity");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitTransparentActivity.class);
        } else {
            DeviceLog.debug("Unity Ads opening new ad unit activity, hardware acceleration disabled");
            intent = new Intent(ClientProperties.getActivity(), (Class<?>) AdUnitSoftwareActivity.class);
        }
        intent.addFlags(268500992);
        if (num == null) {
            DeviceLog.error("Activity ID is NULL");
            webViewCallback.error(AdUnitError.ACTIVITY_ID, "Activity ID NULL");
            return;
        }
        try {
            intent.putExtra(AdUnitActivity.EXTRA_ACTIVITY_ID, num.intValue());
            setCurrentAdUnitActivityId(num.intValue());
            try {
                intent.putExtra(AdUnitActivity.EXTRA_VIEWS, getViewList(jSONArray));
                if (jSONArray2 != null) {
                    try {
                        intent.putExtra(AdUnitActivity.EXTRA_KEY_EVENT_LIST, getKeyEventList(jSONArray2));
                    } catch (Exception e2) {
                        DeviceLog.exception("Error parsing views from viewList", e2);
                        webViewCallback.error(AdUnitError.CORRUPTED_KEYEVENTLIST, jSONArray2, e2.getMessage());
                        return;
                    }
                }
                intent.putExtra(AdUnitActivity.EXTRA_SYSTEM_UI_VISIBILITY, num3);
                intent.putExtra("orientation", num2);
                intent.putExtra(AdUnitActivity.EXTRA_DISPLAY_CUTOUT_MODE, num4);
                ClientProperties.getActivity().startActivity(intent);
                DeviceLog.debug("Opened AdUnitActivity with: " + jSONArray.toString());
                webViewCallback.invoke(new Object[0]);
            } catch (Exception e3) {
                DeviceLog.exception("Error parsing views from viewList", e3);
                webViewCallback.error(AdUnitError.CORRUPTED_VIEWLIST, jSONArray, e3.getMessage());
            }
        } catch (Exception e4) {
            DeviceLog.exception("Could not set activityId for intent", e4);
            webViewCallback.error(AdUnitError.ACTIVITY_ID, Integer.valueOf(num.intValue()), e4.getMessage());
        }
    }

    public static void setAdUnitActivity(AdUnitActivity adUnitActivity) {
        _adUnitActivity = adUnitActivity;
    }

    public static void setCurrentAdUnitActivityId(int i2) {
        _currentActivityId = i2;
    }

    @WebViewExposed
    public static void setKeepScreenOn(final Boolean bool, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.3
            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setKeepScreenOn(bool.booleanValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setKeyEventList(JSONArray jSONArray, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
            return;
        }
        try {
            getAdUnitActivity().setKeyEventList(getKeyEventList(jSONArray));
            webViewCallback.invoke(jSONArray);
        } catch (Exception e2) {
            DeviceLog.exception("Error parsing views from viewList", e2);
            webViewCallback.error(AdUnitError.CORRUPTED_KEYEVENTLIST, jSONArray, e2.getMessage());
        }
    }

    @WebViewExposed
    public static void setLayoutInDisplayCutoutMode(final Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.6
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setLayoutInDisplayCutoutMode(num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(num);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setOrientation(final Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.2
            @Override // java.lang.Runnable
            public final void run() {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setOrientation(num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(num);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setSystemUiVisibility(final Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.4
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setSystemUiVisibility(num.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(num);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setViewFrame(final String str, final Integer num, final Integer num2, final Integer num3, final Integer num4, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.5
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                if (AdUnit.getAdUnitActivity() != null) {
                    AdUnit.getAdUnitActivity().setViewFrame(str, num.intValue(), num2.intValue(), num3.intValue(), num4.intValue());
                }
            }
        });
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(new Object[0]);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void setViews(final JSONArray jSONArray, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        boolean z2;
        try {
            getViewList(jSONArray);
            z2 = false;
        } catch (JSONException unused) {
            webViewCallback.error(AdUnitError.CORRUPTED_VIEWLIST, jSONArray);
            z2 = true;
        }
        if (!z2) {
            Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.ads.api.AdUnit.1
                @Override // java.lang.Runnable
                public final void run() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
                    if (AdUnit.getAdUnitActivity() != null) {
                        try {
                            AdUnit.getAdUnitActivity().setViews(AdUnit.getViewList(jSONArray));
                        } catch (Exception e2) {
                            DeviceLog.exception("Corrupted viewlist", e2);
                        }
                    }
                }
            });
        }
        if (getAdUnitActivity() != null) {
            webViewCallback.invoke(jSONArray);
        } else {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        }
    }

    @WebViewExposed
    public static void startMotionEventCapture(Integer num, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (getAdUnitActivity() == null) {
            webViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
        } else if (getAdUnitActivity().getLayout() == null) {
            webViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
        } else {
            getAdUnitActivity().getLayout().startCapture(num.intValue());
            webViewCallback.invoke(new Object[0]);
        }
    }
}
