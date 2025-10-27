package com.unity3d.splash.services.core.api;

import android.app.Activity;
import android.net.Uri;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import com.vivo.push.PushClientConstants;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class Intent {
    private static WeakReference _activeActivity;

    public enum IntentError {
        COULDNT_PARSE_EXTRAS,
        COULDNT_PARSE_CATEGORIES,
        INTENT_WAS_NULL,
        JSON_EXCEPTION,
        ACTIVITY_WAS_NULL
    }

    public static class IntentException extends Exception {
        private IntentError error;
        private Object field;

        public IntentException(IntentError intentError, Object obj) {
            this.error = intentError;
            this.field = obj;
        }

        public IntentError getError() {
            return this.error;
        }

        public Object getField() {
            return this.field;
        }
    }

    @WebViewExposed
    public static void canOpenIntent(JSONObject jSONObject, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            webViewCallback.invoke(Boolean.valueOf(checkIntentResolvable(intentFromMetadata(jSONObject))));
        } catch (IntentException e2) {
            DeviceLog.exception("Couldn't resolve intent", e2);
            webViewCallback.error(e2.getError(), e2.getField());
        }
    }

    @WebViewExposed
    public static void canOpenIntents(JSONArray jSONArray, WebViewCallback webViewCallback) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        JSONObject jSONObject = new JSONObject();
        int length = jSONArray.length();
        for (int i2 = 0; i2 < length; i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
            try {
                jSONObject.put(jSONObjectOptJSONObject.optString("id"), checkIntentResolvable(intentFromMetadata(jSONObjectOptJSONObject)));
            } catch (IntentException e2) {
                DeviceLog.exception("Exception parsing intent", e2);
                webViewCallback.error(e2.getError(), e2.getField());
                return;
            } catch (JSONException e3) {
                webViewCallback.error(IntentError.JSON_EXCEPTION, e3.getMessage());
                return;
            }
        }
        webViewCallback.invoke(jSONObject);
    }

    private static boolean checkIntentResolvable(android.content.Intent intent) {
        return ClientProperties.getApplicationContext().getPackageManager().resolveActivity(intent, 0) != null;
    }

    private static Activity getStartingActivity() {
        WeakReference weakReference = _activeActivity;
        if (weakReference != null && weakReference.get() != null) {
            return (Activity) _activeActivity.get();
        }
        if (ClientProperties.getActivity() != null) {
            return ClientProperties.getActivity();
        }
        return null;
    }

    private static android.content.Intent intentFromMetadata(JSONObject jSONObject) throws IntentException {
        String str = (String) jSONObject.opt(PushClientConstants.TAG_CLASS_NAME);
        String str2 = (String) jSONObject.opt("packageName");
        String str3 = (String) jSONObject.opt("action");
        String str4 = (String) jSONObject.opt("uri");
        String str5 = (String) jSONObject.opt("mimeType");
        JSONArray jSONArray = (JSONArray) jSONObject.opt("categories");
        Integer num = (Integer) jSONObject.opt("flags");
        JSONArray jSONArray2 = (JSONArray) jSONObject.opt("extras");
        if (str2 != null && str == null && str3 == null && str5 == null) {
            android.content.Intent launchIntentForPackage = ClientProperties.getApplicationContext().getPackageManager().getLaunchIntentForPackage(str2);
            if (launchIntentForPackage == null || num.intValue() < 0) {
                return launchIntentForPackage;
            }
            launchIntentForPackage.addFlags(num.intValue());
            return launchIntentForPackage;
        }
        android.content.Intent intent = new android.content.Intent();
        if (str != null && str2 != null) {
            intent.setClassName(str2, str);
        }
        if (str3 != null) {
            intent.setAction(str3);
        }
        if (str4 != null) {
            intent.setData(Uri.parse(str4));
        }
        if (str5 != null) {
            intent.setType(str5);
        }
        if (num != null && num.intValue() >= 0) {
            intent.setFlags(num.intValue());
        }
        if (!setCategories(intent, jSONArray)) {
            throw new IntentException(IntentError.COULDNT_PARSE_CATEGORIES, jSONArray);
        }
        if (setExtras(intent, jSONArray2)) {
            return intent;
        }
        throw new IntentException(IntentError.COULDNT_PARSE_EXTRAS, jSONArray2);
    }

    @WebViewExposed
    public static void launch(JSONObject jSONObject, WebViewCallback webViewCallback) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        android.content.Intent launchIntentForPackage;
        String str = (String) jSONObject.opt(PushClientConstants.TAG_CLASS_NAME);
        String str2 = (String) jSONObject.opt("packageName");
        String str3 = (String) jSONObject.opt("action");
        String str4 = (String) jSONObject.opt("uri");
        String str5 = (String) jSONObject.opt("mimeType");
        JSONArray jSONArray = (JSONArray) jSONObject.opt("categories");
        Integer num = (Integer) jSONObject.opt("flags");
        JSONArray jSONArray2 = (JSONArray) jSONObject.opt("extras");
        if (str2 != null && str == null && str3 == null && str5 == null) {
            launchIntentForPackage = ClientProperties.getApplicationContext().getPackageManager().getLaunchIntentForPackage(str2);
            if (launchIntentForPackage != null && num.intValue() >= 0) {
                launchIntentForPackage.addFlags(num.intValue());
            }
        } else {
            android.content.Intent intent = new android.content.Intent();
            if (str != null && str2 != null) {
                intent.setClassName(str2, str);
            }
            if (str3 != null) {
                intent.setAction(str3);
            }
            if (str4 != null && str5 != null) {
                intent.setDataAndType(Uri.parse(str4), str5);
            } else if (str4 != null) {
                intent.setData(Uri.parse(str4));
            } else if (str5 != null) {
                intent.setType(str5);
            }
            if (num != null && num.intValue() >= 0) {
                intent.setFlags(num.intValue());
            }
            if (!setCategories(intent, jSONArray)) {
                webViewCallback.error(IntentError.COULDNT_PARSE_CATEGORIES, jSONArray);
            }
            if (!setExtras(intent, jSONArray2)) {
                webViewCallback.error(IntentError.COULDNT_PARSE_EXTRAS, jSONArray2);
            }
            launchIntentForPackage = intent;
        }
        if (launchIntentForPackage == null) {
            webViewCallback.error(IntentError.INTENT_WAS_NULL, new Object[0]);
        } else if (getStartingActivity() == null) {
            webViewCallback.error(IntentError.ACTIVITY_WAS_NULL, new Object[0]);
        } else {
            getStartingActivity().startActivity(launchIntentForPackage);
            webViewCallback.invoke(new Object[0]);
        }
    }

    public static void removeActiveActivity(Activity activity) {
        WeakReference weakReference = _activeActivity;
        if (weakReference == null || weakReference.get() == null || activity == null || !activity.equals(_activeActivity.get())) {
            return;
        }
        _activeActivity = null;
    }

    public static void setActiveActivity(Activity activity) {
        if (activity == null) {
            _activeActivity = null;
        } else {
            _activeActivity = new WeakReference(activity);
        }
    }

    private static boolean setCategories(android.content.Intent intent, JSONArray jSONArray) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (jSONArray == null || jSONArray.length() <= 0) {
            return true;
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                intent.addCategory(jSONArray.getString(i2));
            } catch (Exception e2) {
                DeviceLog.exception("Couldn't parse categories for intent", e2);
                return false;
            }
        }
        return true;
    }

    private static boolean setExtra(android.content.Intent intent, String str, Object obj) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (obj instanceof String) {
            intent.putExtra(str, (String) obj);
            return true;
        }
        if (obj instanceof Integer) {
            intent.putExtra(str, ((Integer) obj).intValue());
            return true;
        }
        if (obj instanceof Double) {
            intent.putExtra(str, ((Double) obj).doubleValue());
            return true;
        }
        if (obj instanceof Boolean) {
            intent.putExtra(str, ((Boolean) obj).booleanValue());
            return true;
        }
        DeviceLog.error("Unable to parse launch intent extra " + str);
        return false;
    }

    private static boolean setExtras(android.content.Intent intent, JSONArray jSONArray) throws JSONException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (jSONArray == null) {
            return true;
        }
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                if (!setExtra(intent, jSONObject.getString("key"), jSONObject.get("value"))) {
                    return false;
                }
            } catch (Exception e2) {
                DeviceLog.exception("Couldn't parse extras", e2);
                return false;
            }
        }
        return true;
    }
}
