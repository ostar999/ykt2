package com.tencent.tbs.one.impl.common;

import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import com.tencent.tbs.one.TBSOneException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    public int f21998a;

    /* renamed from: b, reason: collision with root package name */
    public String f21999b;

    /* renamed from: c, reason: collision with root package name */
    public String[] f22000c;

    /* renamed from: d, reason: collision with root package name */
    public String f22001d;

    /* renamed from: e, reason: collision with root package name */
    public a[] f22002e;

    /* renamed from: f, reason: collision with root package name */
    public Pair<String, String>[] f22003f;

    /* renamed from: g, reason: collision with root package name */
    public String f22004g;

    /* renamed from: h, reason: collision with root package name */
    public boolean f22005h;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f22006a;

        /* renamed from: b, reason: collision with root package name */
        public String f22007b;

        /* renamed from: c, reason: collision with root package name */
        public String[] f22008c;

        /* renamed from: d, reason: collision with root package name */
        public b f22009d;

        /* renamed from: e, reason: collision with root package name */
        public String f22010e;
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public String f22011a;

        /* renamed from: b, reason: collision with root package name */
        public String f22012b;
    }

    public e(JSONObject jSONObject) throws JSONException {
        this.f22005h = false;
        try {
            this.f22005h = jSONObject.optBoolean("NO_SEALED");
        } catch (Exception unused) {
        }
        this.f21998a = jSONObject.optInt("VERSIONCODE");
        this.f22004g = jSONObject.optString("BUILD_TYPE");
        this.f21999b = jSONObject.optString("RESOURCE");
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("ENTRY2");
                if (jSONObjectOptJSONObject != null) {
                    String strOptString = jSONObjectOptJSONObject.optString("DEX_LIST");
                    com.tencent.tbs.one.impl.a.g.a("ManifestConfig ENTRY2 dexListStr=" + strOptString, new Object[0]);
                    if (!TextUtils.isEmpty(strOptString)) {
                        String[] strArrSplit = strOptString.split(",");
                        this.f22000c = strArrSplit;
                        if (strArrSplit.length > 1) {
                            com.tencent.tbs.one.impl.a.g.a("ManifestConfig mEntryDexPath[0]:%s  mEntryDexPath[1]:%s", strArrSplit[0], strArrSplit[1]);
                        }
                    }
                    String strOptString2 = jSONObjectOptJSONObject.optString("ENTRY_CLASS");
                    this.f22001d = strOptString2;
                    if (!TextUtils.isEmpty(strOptString2)) {
                        com.tencent.tbs.one.impl.a.g.a("ManifestConfig mEntryClassName:%s", this.f22001d);
                    }
                } else {
                    com.tencent.tbs.one.impl.a.g.a("ManifestConfig ENTRY2 NULL", new Object[0]);
                }
            }
        } catch (Exception e2) {
            com.tencent.tbs.one.impl.a.g.a("ManifestConfig ENTRY2 Exception", new Object[0]);
            e2.printStackTrace();
        }
        String[] strArr = this.f22000c;
        if (strArr == null || TextUtils.isEmpty(strArr[0]) || TextUtils.isEmpty(this.f22001d)) {
            String strOptString3 = jSONObject.optString("ENTRY");
            if (!TextUtils.isEmpty(strOptString3)) {
                this.f22000c = new String[1];
                String[] strArrSplit2 = strOptString3.split(":");
                if (strArrSplit2.length == 1) {
                    this.f22000c[0] = strArrSplit2[0];
                } else if (strArrSplit2.length >= 2) {
                    this.f22000c[0] = strArrSplit2[0];
                    this.f22001d = strArrSplit2[1];
                }
            }
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("FILES");
        if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
            int length = jSONArrayOptJSONArray.length();
            ArrayList arrayList = new ArrayList(length);
            for (int i2 = 0; i2 < length; i2++) {
                a aVarA = a(jSONArrayOptJSONArray.optJSONObject(i2));
                if (aVarA != null) {
                    arrayList.add(aVarA);
                }
            }
            this.f22002e = (a[]) arrayList.toArray(new a[0]);
        }
        a(jSONObject.optJSONArray("RECEIVERS"));
    }

    public static a a(JSONObject jSONObject) throws JSONException {
        String str;
        if (jSONObject == null) {
            return null;
        }
        String strOptString = jSONObject.optString("PATH");
        if (TextUtils.isEmpty(strOptString)) {
            return null;
        }
        a aVar = new a();
        aVar.f22006a = strOptString;
        aVar.f22007b = jSONObject.optString("MD5");
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("UNSEALED");
            if (jSONArray != null && jSONArray.length() > 0) {
                int length = jSONArray.length();
                String[] strArr = new String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    strArr[i2] = jSONArray.optString(i2);
                }
                aVar.f22008c = strArr;
            }
        } catch (JSONException e2) {
            e2.getMessage();
        }
        aVar.f22010e = jSONObject.optString("URL");
        String strOptString2 = jSONObject.optString("PARENT");
        if (!TextUtils.isEmpty(strOptString2)) {
            b bVar = new b();
            String[] strArrSplit = strOptString2.split(":");
            if (strArrSplit.length == 1) {
                str = strArrSplit[0];
            } else {
                bVar.f22011a = strArrSplit[0];
                str = strArrSplit[1];
            }
            bVar.f22012b = str;
            aVar.f22009d = bVar;
        }
        return aVar;
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x00a2: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:30:0x00a2 */
    public static e a(File file) throws Throwable {
        JSONException e2;
        IOException e3;
        FileNotFoundException e4;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e5) {
                e4 = e5;
            } catch (IOException e6) {
                e3 = e6;
            } catch (JSONException e7) {
                e2 = e7;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream3;
                com.tencent.tbs.one.impl.a.d.a(fileInputStream);
                throw th;
            }
            try {
                e eVar = new e(new JSONObject(com.tencent.tbs.one.impl.a.d.a(fileInputStream, "utf-8")));
                com.tencent.tbs.one.impl.a.d.a(fileInputStream);
                return eVar;
            } catch (FileNotFoundException e8) {
                e4 = e8;
                throw new TBSOneException(401, "Failed to open MANIFEST file " + file.getAbsolutePath() + ", error: " + e4.getMessage(), e4);
            } catch (IOException e9) {
                e3 = e9;
                throw new TBSOneException(402, "Failed to read MANIFEST file " + file.getAbsolutePath() + ", error: " + e3.getMessage(), e3);
            } catch (JSONException e10) {
                e2 = e10;
                throw new TBSOneException(403, "Failed to parse MANIFEST json from file " + file.getAbsolutePath() + ", error: " + e2.getMessage(), e2);
            } catch (Throwable th2) {
                th = th2;
                com.tencent.tbs.one.impl.a.d.a(fileInputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream3 = fileInputStream2;
            fileInputStream = fileInputStream3;
            com.tencent.tbs.one.impl.a.d.a(fileInputStream);
            throw th;
        }
    }

    private void a(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() <= 0) {
            return;
        }
        int length = jSONArray.length();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i2);
            String strOptString = jSONObjectOptJSONObject.optString("CLASS");
            JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("EVENTS");
            if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() > 0) {
                int length2 = jSONArrayOptJSONArray.length();
                for (int i3 = 0; i3 < length2; i3++) {
                    arrayList.add(new Pair(jSONArrayOptJSONArray.optString(i3), strOptString));
                }
            }
        }
        this.f22003f = (Pair[]) arrayList.toArray(new Pair[0]);
    }

    public final a a(String str) {
        for (a aVar : this.f22002e) {
            if (aVar.f22006a.equals(str)) {
                return aVar;
            }
        }
        return null;
    }
}
