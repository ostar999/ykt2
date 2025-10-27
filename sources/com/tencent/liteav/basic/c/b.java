package com.tencent.liteav.basic.c;

import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private final Map<String, String> f18342a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private JSONObject f18343b = null;

    private void b() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.f18342a.entrySet()) {
            sb.append(entry.getKey());
            sb.append(" : ");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        TXCLog.i("CompatibleConfig", sb.toString());
    }

    public synchronized void a(JSONArray jSONArray) {
        this.f18342a.clear();
        JSONObject jSONObjectB = b(jSONArray);
        this.f18343b = jSONObjectB;
        if (jSONObjectB == null) {
            TXCLog.i("CompatibleConfig", "can't find best match value");
        } else {
            a(this.f18342a, "", jSONObjectB);
            b();
        }
    }

    private JSONObject b(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONObject jSONObject = null;
        int i2 = 0;
        for (int i3 = 0; i3 < jSONArray.length(); i3++) {
            try {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i3);
                int iA = a(jSONObject2.optJSONObject("deviceinfo"));
                JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("deviceconfig");
                if (iA > i2 && jSONObjectOptJSONObject != null) {
                    jSONObject = jSONObjectOptJSONObject;
                    i2 = iA;
                }
            } catch (JSONException e2) {
                TXCLog.e("CompatibleConfig", "Find best match value failed.", e2);
            }
        }
        TXCLog.i("CompatibleConfig", "bestMatchLevel: %d", Integer.valueOf(i2));
        if (i2 > 0) {
            return jSONObject;
        }
        return null;
    }

    public synchronized void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            this.f18342a.clear();
            JSONObject jSONObject = new JSONObject(str);
            this.f18343b = jSONObject;
            a(this.f18342a, "", jSONObject);
            b();
        } catch (JSONException e2) {
            TXCLog.e("CompatibleConfig", "parse best match value failed.", e2);
        }
    }

    public synchronized JSONObject a() {
        return this.f18343b;
    }

    private int a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return 0;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Pair(TXCBuild.Manufacturer(), jSONObject.optString("MANUFACTURER")));
        arrayList.add(new Pair(TXCBuild.Model(), jSONObject.optString("MODEL")));
        arrayList.add(new Pair(String.valueOf(TXCBuild.VersionInt()), jSONObject.optString("VERSION")));
        arrayList.add(new Pair(Build.VERSION.INCREMENTAL, jSONObject.optString("VERSION_INCREMENTAL")));
        arrayList.add(new Pair(Build.DISPLAY, jSONObject.optString("DISPLAY")));
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Pair pair = (Pair) arrayList.get(i2);
            if (TextUtils.isEmpty((CharSequence) pair.second)) {
                return i2;
            }
            if (!((String) pair.first).equalsIgnoreCase((String) pair.second)) {
                return 0;
            }
        }
        return arrayList.size();
    }

    private void a(Map<String, String> map, String str, JSONObject jSONObject) {
        Iterator<String> itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Object objOpt = jSONObject.opt(next);
            if (objOpt instanceof JSONObject) {
                a(map, str + StrPool.DOT + next, (JSONObject) objOpt);
            } else if (objOpt != null) {
                map.put(str + StrPool.DOT + next, objOpt.toString());
            }
        }
    }
}
