package com.just.agentweb;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import cn.hutool.core.text.StrPool;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.mobile.auth.gatewayauth.Constant;
import java.lang.reflect.Method;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class JsCallJava {
    private static final String[] IGNORE_UNSAFE_METHODS = {"getClass", "hashCode", "notify", "notifyAll", "equals", "toString", "wait"};
    private static final String KEY_ARGS = "args";
    private static final String KEY_METHOD = "method";
    private static final String KEY_OBJ = "obj";
    private static final String KEY_TYPES = "types";
    private static final String MSG_PROMPT_HEADER = "AgentWeb:";
    private static final String RETURN_RESULT_FORMAT = "{\"CODE\": %d, \"result\": %s}";
    private static final String TAG = "JsCallJava";
    private Object mInterfaceObj;
    private String mInterfacedName;
    private HashMap<String, Method> mMethodsMap;
    private String mPreloadInterfaceJs;

    public JsCallJava(Object obj, String str) throws Exception {
        try {
            if (TextUtils.isEmpty(str)) {
                throw new Exception("injected name can not be null");
            }
            this.mInterfaceObj = obj;
            this.mInterfacedName = str;
            this.mMethodsMap = new HashMap<>();
            Method[] methods = this.mInterfaceObj.getClass().getMethods();
            StringBuilder sb = new StringBuilder("javascript:(function(b){console.log(\"");
            sb.append(this.mInterfacedName);
            sb.append(" init begin\");var a={queue:[],callback:function(){var d=Array.prototype.slice.call(arguments,0);var c=d.shift();var e=d.shift();this.queue[c].apply(this,d);if(!e){delete this.queue[c]}}};");
            for (Method method : methods) {
                Log.i("Info", "method:" + method);
                String strGenJavaMethodSign = genJavaMethodSign(method);
                if (strGenJavaMethodSign != null) {
                    this.mMethodsMap.put(strGenJavaMethodSign, method);
                    sb.append(String.format("a.%s=", method.getName()));
                }
            }
            sb.append("function(){var f=Array.prototype.slice.call(arguments,0);if(f.length<1){throw\"");
            sb.append(this.mInterfacedName);
            sb.append(" call result, message:miss method name\"}var e=[];for(var h=1;h<f.length;h++){var c=f[h];var j=typeof c;e[e.length]=j;if(j==\"function\"){var d=a.queue.length;a.queue[d]=c;f[h]=d}}var k = new Date().getTime();var l = f.shift();var m=prompt('");
            sb.append(MSG_PROMPT_HEADER);
            sb.append("'+JSON.stringify(");
            sb.append(promptMsgFormat("'" + this.mInterfacedName + "'", NotifyType.LIGHTS, AliyunLogKey.KEY_EVENT, "f"));
            sb.append("));console.log(\"invoke \"+l+\", time: \"+(new Date().getTime()-k));var g=JSON.parse(m);if(g.CODE!=200){throw\"");
            sb.append(this.mInterfacedName);
            sb.append(" call result, CODE:\"+g.CODE+\", message:\"+g.result}return g.result};Object.getOwnPropertyNames(a).forEach(function(d){var c=a[d];if(typeof c===\"function\"&&d!==\"callback\"){a[d]=function(){return c.apply(a,[d].concat(Array.prototype.slice.call(arguments,0)))}}});b.");
            sb.append(this.mInterfacedName);
            sb.append("=a;console.log(\"");
            sb.append(this.mInterfacedName);
            sb.append(" init end\")})(window)");
            this.mPreloadInterfaceJs = sb.toString();
            sb.setLength(0);
        } catch (Exception e2) {
            if (LogUtils.isDebug()) {
                Log.e(TAG, "init js result:" + e2.getMessage());
            }
        }
    }

    private String genJavaMethodSign(Method method) {
        StringBuilder sb;
        String str;
        String name = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (String str2 : IGNORE_UNSAFE_METHODS) {
            if (str2.equals(name)) {
                if (!LogUtils.isDebug()) {
                    return null;
                }
                Log.w(TAG, "method(" + name + ") is unsafe, will be pass");
                return null;
            }
        }
        for (Class<?> cls : parameterTypes) {
            if (cls == String.class) {
                sb = new StringBuilder();
                sb.append(name);
                str = "_S";
            } else if (cls == Integer.TYPE || cls == Long.TYPE || cls == Float.TYPE || cls == Double.TYPE) {
                sb = new StringBuilder();
                sb.append(name);
                str = "_N";
            } else if (cls == Boolean.TYPE) {
                sb = new StringBuilder();
                sb.append(name);
                str = "_B";
            } else if (cls == JSONObject.class) {
                sb = new StringBuilder();
                sb.append(name);
                str = "_O";
            } else if (cls == JsCallback.class) {
                sb = new StringBuilder();
                sb.append(name);
                str = "_F";
            } else {
                sb = new StringBuilder();
                sb.append(name);
                str = "_P";
            }
            sb.append(str);
            name = sb.toString();
        }
        return name;
    }

    public static String getInterfacedName(JSONObject jSONObject) {
        return jSONObject.optString(KEY_OBJ);
    }

    public static JSONObject getMsgJSONObject(String str) {
        try {
            return new JSONObject(str.substring(9));
        } catch (JSONException e2) {
            e2.printStackTrace();
            return new JSONObject();
        }
    }

    private String getReturn(JSONObject jSONObject, int i2, Object obj, long j2) {
        String str = String.format(RETURN_RESULT_FORMAT, Integer.valueOf(i2), obj == null ? "null" : obj instanceof String ? "\"".concat(String.valueOf(((String) obj).replace("\"", "\\\""))).concat("\"") : String.valueOf(obj));
        if (LogUtils.isDebug()) {
            Log.d(TAG, "call time: " + (SystemClock.uptimeMillis() - j2) + ", request: " + jSONObject + ", result:" + str);
        }
        return str;
    }

    public static boolean isSafeWebViewCallMsg(String str) {
        return str.startsWith(MSG_PROMPT_HEADER);
    }

    private static String promptMsgFormat(String str, String str2, String str3, String str4) {
        return StrPool.DELIM_START + KEY_OBJ + ":" + str + ",method:" + str2 + "," + KEY_TYPES + ":" + str3 + ",args:" + str4 + "}";
    }

    public String call(WebView webView, JSONObject jSONObject) throws JSONException {
        StringBuilder sb;
        String string;
        int i2;
        long jUptimeMillis = LogUtils.isDebug() ? SystemClock.uptimeMillis() : 0L;
        if (jSONObject != null) {
            try {
                String string2 = jSONObject.getString("method");
                JSONArray jSONArray = jSONObject.getJSONArray(KEY_TYPES);
                JSONArray jSONArray2 = jSONObject.getJSONArray("args");
                int length = jSONArray.length();
                Object[] objArr = new Object[length];
                int i3 = 0;
                for (int i4 = 0; i4 < length; i4++) {
                    String strOptString = jSONArray.optString(i4);
                    Object jSONObject2 = null;
                    if (TypedValues.Custom.S_STRING.equals(strOptString)) {
                        string2 = string2 + "_S";
                        if (!jSONArray2.isNull(i4)) {
                            jSONObject2 = jSONArray2.getString(i4);
                        }
                        objArr[i4] = jSONObject2;
                    } else if (Constant.LOGIN_ACTIVITY_NUMBER.equals(strOptString)) {
                        string2 = string2 + "_N";
                        i3 = (i3 * 10) + i4 + 1;
                    } else if (TypedValues.Custom.S_BOOLEAN.equals(strOptString)) {
                        string2 = string2 + "_B";
                        objArr[i4] = Boolean.valueOf(jSONArray2.getBoolean(i4));
                    } else if ("object".equals(strOptString)) {
                        string2 = string2 + "_O";
                        if (!jSONArray2.isNull(i4)) {
                            jSONObject2 = jSONArray2.getJSONObject(i4);
                        }
                        objArr[i4] = jSONObject2;
                    } else if ("function".equals(strOptString)) {
                        string2 = string2 + "_F";
                        objArr[i4] = new JsCallback(webView, this.mInterfacedName, jSONArray2.getInt(i4));
                    } else {
                        string2 = string2 + "_P";
                    }
                }
                Method method = this.mMethodsMap.get(string2);
                if (method == null) {
                    return getReturn(jSONObject, 500, "not found method(" + string2 + ") with valid parameters", jUptimeMillis);
                }
                if (i3 > 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    while (i3 > 0) {
                        int i5 = (i3 - ((i3 / 10) * 10)) - 1;
                        Class<?> cls = parameterTypes[i5];
                        if (cls == Integer.TYPE) {
                            objArr[i5] = Integer.valueOf(jSONArray2.getInt(i5));
                        } else if (cls == Long.TYPE) {
                            objArr[i5] = Long.valueOf(Long.parseLong(jSONArray2.getString(i5)));
                        } else {
                            objArr[i5] = Double.valueOf(jSONArray2.getDouble(i5));
                        }
                        i3 /= 10;
                    }
                }
                return getReturn(jSONObject, 200, method.invoke(this.mInterfaceObj, objArr), jUptimeMillis);
            } catch (Exception e2) {
                e = e2;
                LogUtils.safeCheckCrash(TAG, "call", e);
                if (e.getCause() != null) {
                    sb = new StringBuilder();
                    sb.append("method execute result:");
                    e = e.getCause();
                } else {
                    sb = new StringBuilder();
                    sb.append("method execute result:");
                }
                sb.append(e.getMessage());
                string = sb.toString();
                i2 = 500;
            }
        } else {
            i2 = 500;
            string = "call data empty";
        }
        return getReturn(jSONObject, i2, string, jUptimeMillis);
    }

    public String getPreloadInterfaceJs() {
        return this.mPreloadInterfaceJs;
    }
}
