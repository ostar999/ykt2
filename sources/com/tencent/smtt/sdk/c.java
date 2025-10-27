package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: e, reason: collision with root package name */
    private static c f21150e = null;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f21151g = false;

    /* renamed from: a, reason: collision with root package name */
    private String f21152a = "EmergenceMsgPublisher";

    /* renamed from: b, reason: collision with root package name */
    private String f21153b = "tbs_emergence";

    /* renamed from: c, reason: collision with root package name */
    private String f21154c = "emergence_executed_ids";

    /* renamed from: d, reason: collision with root package name */
    private String f21155d = "emergence_ids";

    /* renamed from: f, reason: collision with root package name */
    private final Map<Integer, a> f21156f = new ConcurrentHashMap();

    public interface a {
        void a(String str);
    }

    public static class b {

        /* renamed from: a, reason: collision with root package name */
        public int f21157a = -1;

        /* renamed from: b, reason: collision with root package name */
        public int f21158b = -1;

        /* renamed from: c, reason: collision with root package name */
        public String f21159c = "";

        /* renamed from: d, reason: collision with root package name */
        public long f21160d = -1;

        public String toString() {
            return "{seqId=" + this.f21157a + ", code=" + this.f21158b + ", extra='" + this.f21159c + CharPool.SINGLE_QUOTE + ", expired=" + this.f21160d + '}';
        }
    }

    private c() {
    }

    public static c a() {
        if (f21150e == null) {
            f21150e = new c();
        }
        return f21150e;
    }

    private synchronized void a(Context context, b bVar, a aVar) {
        String[] strArrSplit;
        if (aVar != null) {
            a("Executed command: " + bVar.f21158b + ", extra: " + bVar.f21159c);
            aVar.a(bVar.f21159c);
            SharedPreferences sharedPreferences = context.getSharedPreferences(this.f21153b, 4);
            String string = sharedPreferences.getString(this.f21154c, "");
            HashSet hashSet = new HashSet();
            if (!TextUtils.isEmpty(string) && (strArrSplit = string.split(",")) != null && strArrSplit.length > 0) {
                try {
                    for (String str : strArrSplit) {
                        hashSet.add(Integer.valueOf(Integer.parseInt(str)));
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
            hashSet.add(Integer.valueOf(bVar.f21157a));
            StringBuilder sb = new StringBuilder();
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                sb.append((Integer) it.next());
                sb.append(",");
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.putString(this.f21154c, sb.toString());
            editorEdit.commit();
        }
    }

    private void a(String str) {
    }

    public synchronized Map<Integer, b> a(Context context) {
        int i2;
        String[] strArrSplit;
        HashMap map = new HashMap();
        SharedPreferences sharedPreferences = context.getSharedPreferences(this.f21153b, 0);
        String string = sharedPreferences.getString(this.f21155d, "");
        if (TextUtils.isEmpty(string)) {
            a("Empty local emergence ids!");
            return map;
        }
        a("Local emergence ids: " + string);
        String[] strArrSplit2 = string.split(com.alipay.sdk.util.h.f3376b);
        if (strArrSplit2 != null) {
            for (String str : strArrSplit2) {
                if (!TextUtils.isEmpty(str) && (strArrSplit = str.split(",")) != null && strArrSplit.length == 4) {
                    b bVar = new b();
                    try {
                        bVar.f21157a = Integer.parseInt(strArrSplit[0]);
                        bVar.f21158b = Integer.parseInt(strArrSplit[1]);
                        bVar.f21159c = String.valueOf(strArrSplit[2]);
                        bVar.f21160d = Long.parseLong(strArrSplit[3]);
                    } catch (Throwable unused) {
                    }
                    if (System.currentTimeMillis() < bVar.f21160d) {
                        map.put(Integer.valueOf(bVar.f21157a), bVar);
                    }
                }
            }
        }
        String string2 = sharedPreferences.getString(this.f21154c, "");
        a("Executed ids: " + string2);
        String[] strArrSplit3 = string2.split(",");
        if (strArrSplit3 != null) {
            ArrayList arrayList = new ArrayList();
            for (String str2 : strArrSplit3) {
                if (!TextUtils.isEmpty(str2)) {
                    try {
                        i2 = Integer.parseInt(str2);
                    } catch (Throwable th) {
                        th.printStackTrace();
                        i2 = -1;
                    }
                    if (i2 > 0) {
                        arrayList.add(Integer.valueOf(i2));
                    }
                }
            }
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                map.remove(arrayList.get(i3));
            }
        }
        return map;
    }

    public synchronized void a(Context context, Integer num, a aVar) {
        Map<Integer, b> mapA = a(context);
        Iterator<Integer> it = mapA.keySet().iterator();
        while (it.hasNext()) {
            b bVar = mapA.get(it.next());
            if (bVar == null) {
                a("Unexpected null command!");
            } else if (bVar.f21158b == num.intValue()) {
                a(context, bVar, aVar);
                return;
            }
        }
        if (!f21151g) {
            this.f21156f.put(num, aVar);
            a("Emergence config did not arrived yet, code[" + num + "] has been suspended");
        }
    }

    public synchronized void b(Context context) {
        Map<Integer, b> mapA = a(context);
        a("On notify emergence, validate commands: " + mapA);
        f21151g = true;
        for (Integer num : this.f21156f.keySet()) {
            for (Integer num2 : mapA.keySet()) {
                if (mapA.get(num2).f21158b == num.intValue()) {
                    a(context, mapA.get(num2), this.f21156f.get(num));
                }
            }
        }
        if (!this.f21156f.isEmpty()) {
            a("Emergency code[" + this.f21156f.keySet() + "] did not happen, clear suspended queries");
            this.f21156f.clear();
        }
    }
}
