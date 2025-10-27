package com.psychiatrygarden.db;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.purchase.beans.ProductInfo;
import com.psychiatrygarden.bean.AuroraVerification;
import com.psychiatrygarden.bean.ImageDataBean;
import com.psychiatrygarden.bean.LinkDataBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.CommonParameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SharePreferencesUtils {
    private static final String mPreferName = "yikaobang";

    public static void clearAll(Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.clear();
        editorEdit.apply();
    }

    public static void clearAppointData(Context mContext) {
        UserConfig.getInstance().saveUser(null);
        removeConfig(CommonParameter.GroupId, mContext);
        removeConfig(CommonParameter.coursecontentstr, mContext);
        removeConfig(CommonParameter.IS_RANK_SHOW_DIALOG, mContext);
    }

    public static List<ImageDataBean> getImageList(Context context, String key) {
        ArrayList arrayList = new ArrayList();
        try {
            return (List) new Gson().fromJson(context.getSharedPreferences("imgdata", 0).getString(key, ""), new TypeToken<List<ImageDataBean>>() { // from class: com.psychiatrygarden.db.SharePreferencesUtils.1
            }.getType());
        } catch (Exception e2) {
            e2.printStackTrace();
            return arrayList;
        }
    }

    public static List<Map<String, String>> getInfo(Context context, String key) throws JSONException {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(context.getSharedPreferences("finals", 0).getString(key, ""));
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                HashMap map = new HashMap();
                JSONArray jSONArrayNames = jSONObject.names();
                if (jSONArrayNames != null) {
                    for (int i3 = 0; i3 < jSONArrayNames.length(); i3++) {
                        String string = jSONArrayNames.getString(i3);
                        map.put(string, jSONObject.getString(string));
                    }
                }
                arrayList.add(map);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public static List<ProductInfo> getInfoList(Context context, String key) {
        ArrayList arrayList = new ArrayList();
        try {
            return (List) new Gson().fromJson(context.getSharedPreferences("pro", 0).getString(key, ""), new TypeToken<List<ProductInfo>>() { // from class: com.psychiatrygarden.db.SharePreferencesUtils.3
            }.getType());
        } catch (Exception e2) {
            e2.printStackTrace();
            return arrayList;
        }
    }

    public static List<AuroraVerification> getInfoListT(Context context, String key) {
        ArrayList arrayList = new ArrayList();
        try {
            return (List) new Gson().fromJson(context.getSharedPreferences("AuroraVerification", 0).getString(key, ""), new TypeToken<List<AuroraVerification>>() { // from class: com.psychiatrygarden.db.SharePreferencesUtils.4
            }.getType());
        } catch (Exception e2) {
            e2.printStackTrace();
            return arrayList;
        }
    }

    public static Map<String, String> getInfoMap(Context context, String key) throws JSONException {
        HashMap map = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(getSharedPreferences(context).getString(key, ""));
            JSONArray jSONArrayNames = jSONObject.names();
            if (jSONArrayNames != null) {
                for (int i2 = 0; i2 < jSONArrayNames.length(); i2++) {
                    String string = jSONArrayNames.getString(i2);
                    map.put(string, jSONObject.getString(string));
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.util.List] */
    public static List<LinkDataBean> getLinkList(Context context, String key) {
        ArrayList arrayList = new ArrayList();
        try {
            arrayList = (List) new Gson().fromJson(context.getSharedPreferences("linkData", 0).getString(key, ""), new TypeToken<List<LinkDataBean>>() { // from class: com.psychiatrygarden.db.SharePreferencesUtils.2
            }.getType());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return arrayList == null ? new ArrayList() : arrayList;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context == null ? ProjectApp.instance().getSharedPreferences("yikaobang", 0) : context.getSharedPreferences("yikaobang", 0);
    }

    public static boolean readBooleanConfig(String key, boolean defValue, Context context) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static float readFloatConfig(String key, Context context) {
        return getSharedPreferences(context).getFloat(key, 0.6f);
    }

    public static int readIntConfig(String key, Context context, int defaultValue) {
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    public static Long readLongConfig(String key, Context context, Long defaultValue) {
        return Long.valueOf(getSharedPreferences(context).getLong(key, defaultValue.longValue()));
    }

    public static String readStrConfig(String key, Context context, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key, defaultValue).isEmpty() ? defaultValue : sharedPreferences.getString(key, defaultValue);
    }

    public static void removeConfig(String key, Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.remove(key);
        editorEdit.apply();
    }

    public static void removeContainConfig(String containkey, Context context) {
        for (String str : getSharedPreferences(context).getAll().keySet()) {
            if (str != null && str.contains(containkey)) {
                removeConfig(str, context);
            }
        }
    }

    public static void saveImageList(Context context, String key, List<ImageDataBean> datas) {
        String json = new Gson().toJson(datas);
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("imgdata", 0).edit();
        editorEdit.putString(key, json);
        editorEdit.apply();
    }

    public static void saveInfo(Context context, String key, List<Map<String, String>> datas) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (int i2 = 0; i2 < datas.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            for (Map.Entry<String, String> entry : datas.get(i2).entrySet()) {
                try {
                    jSONObject.put(entry.getKey(), entry.getValue());
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            jSONArray.put(jSONObject);
        }
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("finals", 0).edit();
        editorEdit.putString(key, jSONArray.toString());
        editorEdit.apply();
    }

    public static void saveInfoList(Context context, String key, List<ProductInfo> datas) {
        String json = new Gson().toJson(datas);
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("pro", 0).edit();
        editorEdit.putString(key, json);
        editorEdit.apply();
    }

    public static void saveInfoListT(Context context, String key, List<AuroraVerification> datas) {
        String json = new Gson().toJson(datas);
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("AuroraVerification", 0).edit();
        editorEdit.putString(key, json);
        editorEdit.apply();
    }

    public static void saveInfoMap(Context context, String key, Map<String, String> datas) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (Map.Entry<String, String> entry : datas.entrySet()) {
            try {
                jSONObject.put(entry.getKey(), entry.getValue());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(key, jSONObject.toString());
        editorEdit.apply();
    }

    public static void saveLinkList(Context context, String key, List<LinkDataBean> datas) {
        String json = new Gson().toJson(datas);
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("linkData", 0).edit();
        editorEdit.putString(key, json);
        editorEdit.apply();
    }

    public static void writeBooleanConfig(String key, boolean value, Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putBoolean(key, value);
        editorEdit.apply();
    }

    public static void writeFloatConfig(String key, float value, Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putFloat(key, value);
        editorEdit.apply();
    }

    public static void writeIntConfig(String key, int value, Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putInt(key, value);
        editorEdit.apply();
    }

    public static void writeLongConfig(String key, Long value, Context context) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putLong(key, value.longValue());
        editorEdit.apply();
    }

    public static void writeStrConfig(String key, String value, Context context) {
        if (key == null || context == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(key, value);
        editorEdit.apply();
    }

    public static float readFloatConfig(String key, Context context, float defValue) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static String readStrConfig(String key, Context context) {
        return getSharedPreferences(context).getString(key, "");
    }
}
