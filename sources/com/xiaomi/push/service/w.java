package com.xiaomi.push.service;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import com.xiaomi.push.gn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes6.dex */
public class w {

    /* renamed from: a, reason: collision with other field name */
    private static final Map<String, byte[]> f1096a = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    private static ArrayList<Pair<String, byte[]>> f25725a = new ArrayList<>();

    public static void a(Context context, int i2, String str) {
        Map<String, byte[]> map = f1096a;
        synchronized (map) {
            for (String str2 : map.keySet()) {
                a(context, str2, f1096a.get(str2), i2, str);
            }
            f1096a.clear();
        }
    }

    public static void a(Context context, String str, byte[] bArr, int i2, String str2) {
        Intent intent = new Intent("com.xiaomi.mipush.ERROR");
        intent.setPackage(str);
        intent.putExtra("mipush_payload", bArr);
        intent.putExtra("mipush_error_code", i2);
        intent.putExtra("mipush_error_msg", str2);
        context.sendBroadcast(intent, af.a(str));
    }

    public static void a(XMPushService xMPushService) {
        try {
            Map<String, byte[]> map = f1096a;
            synchronized (map) {
                for (String str : map.keySet()) {
                    af.a(xMPushService, str, f1096a.get(str));
                }
                f1096a.clear();
            }
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            xMPushService.a(10, e2);
        }
    }

    public static void a(String str, byte[] bArr) {
        Map<String, byte[]> map = f1096a;
        synchronized (map) {
            map.put(str, bArr);
        }
    }

    public static void b(XMPushService xMPushService) {
        ArrayList<Pair<String, byte[]>> arrayList;
        try {
            synchronized (f25725a) {
                arrayList = f25725a;
                f25725a = new ArrayList<>();
            }
            Iterator<Pair<String, byte[]>> it = arrayList.iterator();
            while (it.hasNext()) {
                Pair<String, byte[]> next = it.next();
                af.a(xMPushService, (String) next.first, (byte[]) next.second);
            }
        } catch (gn e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            xMPushService.a(10, e2);
        }
    }

    public static void b(String str, byte[] bArr) {
        synchronized (f25725a) {
            f25725a.add(new Pair<>(str, bArr));
            if (f25725a.size() > 50) {
                f25725a.remove(0);
            }
        }
    }
}
