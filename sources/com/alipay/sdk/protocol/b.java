package com.alipay.sdk.protocol;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.sdk.util.h;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public a f3313a;

    /* renamed from: b, reason: collision with root package name */
    public String[] f3314b;

    /* renamed from: c, reason: collision with root package name */
    private String f3315c;

    private b(String str) {
        this.f3315c = str;
    }

    public static List<b> a(JSONObject jSONObject) {
        ArrayList arrayList = new ArrayList();
        if (jSONObject == null) {
            return arrayList;
        }
        String strOptString = jSONObject.optString("name", "");
        String[] strArrSplit = !TextUtils.isEmpty(strOptString) ? strOptString.split(h.f3376b) : null;
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            a aVarA = a.a(strArrSplit[i2]);
            if (aVarA != a.None) {
                b bVar = new b(strArrSplit[i2], aVarA);
                bVar.f3314b = a(strArrSplit[i2]);
                arrayList.add(bVar);
            }
        }
        return arrayList;
    }

    private static String[] b(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.split(h.f3376b);
    }

    private String[] c() {
        return this.f3314b;
    }

    private b(String str, a aVar) {
        this.f3315c = str;
        this.f3313a = aVar;
    }

    private a b() {
        return this.f3313a;
    }

    private static String[] a(String str) {
        ArrayList arrayList = new ArrayList();
        int iIndexOf = str.indexOf(40);
        int iLastIndexOf = str.lastIndexOf(41);
        if (iIndexOf == -1 || iLastIndexOf == -1 || iLastIndexOf <= iIndexOf) {
            return null;
        }
        String[] strArrSplit = str.substring(iIndexOf + 1, iLastIndexOf).split(",");
        if (strArrSplit != null) {
            for (int i2 = 0; i2 < strArrSplit.length; i2++) {
                if (!TextUtils.isEmpty(strArrSplit[i2])) {
                    arrayList.add(strArrSplit[i2].trim().replaceAll("'", "").replaceAll("\"", ""));
                }
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private String a() {
        return this.f3315c;
    }

    private static void a(b bVar) {
        String[] strArr = bVar.f3314b;
        if (strArr.length == 3 && TextUtils.equals(com.alipay.sdk.cons.b.f3217c, strArr[0])) {
            Context context = com.alipay.sdk.sys.b.a().f3333a;
            com.alipay.sdk.tid.b bVarA = com.alipay.sdk.tid.b.a();
            if (TextUtils.isEmpty(strArr[1]) || TextUtils.isEmpty(strArr[2])) {
                return;
            }
            bVarA.f3338a = strArr[1];
            bVarA.f3339b = strArr[2];
            com.alipay.sdk.tid.a aVar = new com.alipay.sdk.tid.a(context);
            try {
                aVar.a(com.alipay.sdk.util.a.a(context).a(), com.alipay.sdk.util.a.a(context).b(), bVarA.f3338a, bVarA.f3339b);
            } catch (Exception unused) {
            } finally {
                aVar.close();
            }
        }
    }
}
