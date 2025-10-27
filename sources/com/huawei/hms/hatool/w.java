package com.huawei.hms.hatool;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes4.dex */
public class w {
    public static Map<String, List<q>> a(Context context, String str) {
        if (context == null) {
            return null;
        }
        Map<String, ?> mapA = g0.a(context, str);
        b(mapA);
        return a(mapA);
    }

    public static Map<String, List<q>> a(Context context, String str, String str2) {
        Map<String, List<q>> mapA;
        Map<String, List<q>> mapA2;
        if ("alltype".equals(str2) || TextUtils.isEmpty(str)) {
            y.c("hmsSdk", "read all event records");
            mapA = a(context, "stat_v2_1");
            mapA2 = a(context, "cached_v2_1");
        } else {
            String strA = u0.a(str, str2);
            mapA = b(context, "stat_v2_1", strA);
            mapA2 = b(context, "cached_v2_1", strA);
        }
        return a(mapA, mapA2);
    }

    public static Map<String, List<q>> a(Map<String, ?> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() instanceof String) {
                a(key, (String) entry.getValue(), map2);
            }
        }
        return map2;
    }

    public static Map<String, List<q>> a(Map<String, List<q>> map, Map<String, List<q>> map2) {
        if (map.size() == 0 && map2.size() == 0) {
            return new HashMap();
        }
        if (map.size() == 0) {
            return map2;
        }
        if (map2.size() == 0) {
            return map;
        }
        HashMap map3 = new HashMap(map);
        map3.putAll(map2);
        return map3;
    }

    public static void a(String str, String str2, Map<String, List<q>> map) {
        ArrayList arrayList = new ArrayList();
        try {
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            JSONArray jSONArray = new JSONArray(str2);
            if (jSONArray.length() == 0) {
                return;
            }
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                q qVar = new q();
                try {
                    qVar.a(jSONArray.getJSONObject(i2));
                    arrayList.add(qVar);
                } catch (JSONException unused) {
                    y.e("hmsSdk", "JSON Exception happened when create data for report - readDataToRecord");
                }
            }
            map.put(str, arrayList);
        } catch (JSONException unused2) {
            y.e("hmsSdk", "When events turn to JSONArray,JSON Exception has happened");
        }
    }

    public static Map<String, List<q>> b(Context context, String str, String str2) {
        String strA = g0.a(context, str, str2, "");
        HashMap map = new HashMap();
        a(str2, strA, map);
        return map;
    }

    public static void b(Map<String, ?> map) {
        Iterator<Map.Entry<String, ?>> it = map.entrySet().iterator();
        Set<String> setA = u0.a(b.b());
        while (it.hasNext()) {
            if (!setA.contains(it.next().getKey())) {
                it.remove();
            }
        }
    }
}
