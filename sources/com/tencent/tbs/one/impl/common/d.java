package com.tencent.tbs.one.impl.common;

import android.text.TextUtils;
import com.tencent.tbs.one.TBSOneException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class d implements Serializable {

    /* renamed from: a, reason: collision with root package name */
    public int f21989a;

    /* renamed from: b, reason: collision with root package name */
    public String f21990b;

    /* renamed from: c, reason: collision with root package name */
    public Map<String, a> f21991c;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f21992a;

        /* renamed from: b, reason: collision with root package name */
        public String f21993b;

        /* renamed from: c, reason: collision with root package name */
        public int f21994c;

        /* renamed from: d, reason: collision with root package name */
        public String f21995d;

        /* renamed from: e, reason: collision with root package name */
        public String f21996e;

        /* renamed from: f, reason: collision with root package name */
        public String[] f21997f;
    }

    public d(JSONObject jSONObject) {
        this.f21990b = jSONObject.optString("VERSIONNAME");
        this.f21989a = jSONObject.optInt("VERSIONCODE");
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("COMPONENTS");
        if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
            return;
        }
        int length = jSONArrayOptJSONArray.length();
        this.f21991c = new HashMap(length);
        for (int i2 = 0; i2 < length; i2++) {
            a aVarA = a(jSONArrayOptJSONArray.optJSONObject(i2));
            if (aVarA != null) {
                this.f21991c.put(aVarA.f21992a, aVarA);
            }
        }
    }

    public static a a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        String strOptString = jSONObject.optString("NAME");
        if (TextUtils.isEmpty(strOptString)) {
            return null;
        }
        a aVar = new a();
        aVar.f21992a = strOptString;
        aVar.f21993b = jSONObject.optString("VERSIONNAME");
        aVar.f21994c = jSONObject.optInt("VERSIONCODE");
        aVar.f21995d = jSONObject.optString("URL");
        aVar.f21996e = jSONObject.optString("MD5");
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("DEPENDENCIES");
        if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
            int length = jSONArrayOptJSONArray.length();
            aVar.f21997f = new String[length];
            for (int i2 = 0; i2 < length; i2++) {
                aVar.f21997f[i2] = jSONArrayOptJSONArray.optString(i2);
            }
        }
        return aVar;
    }

    public static d a(File file) throws Throwable {
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    d dVarA = a(fileInputStream2);
                    com.tencent.tbs.one.impl.a.d.a(fileInputStream2);
                    return dVarA;
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileInputStream = fileInputStream2;
                    throw new TBSOneException(306, "Failed to open DEPS file, error: " + e.getMessage(), e);
                } catch (Throwable th) {
                    th = th;
                    fileInputStream = fileInputStream2;
                    com.tencent.tbs.one.impl.a.d.a(fileInputStream);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (FileNotFoundException e3) {
            e = e3;
        }
    }

    public static d a(InputStream inputStream) throws TBSOneException {
        try {
            return a(com.tencent.tbs.one.impl.a.d.a(inputStream, "utf-8"));
        } catch (IOException e2) {
            throw new TBSOneException(307, "Failed to read DEPS contents, error: " + e2.getMessage(), e2);
        }
    }

    public static d a(String str) throws TBSOneException {
        try {
            return new d(new JSONObject(str));
        } catch (JSONException e2) {
            throw new TBSOneException(308, "Failed to parse DEPS " + str + ", error: " + e2.getMessage(), e2);
        }
    }

    public final a b(String str) {
        Map<String, a> map = this.f21991c;
        if (map == null) {
            return null;
        }
        return map.get(str);
    }
}
