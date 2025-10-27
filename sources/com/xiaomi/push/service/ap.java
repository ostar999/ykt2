package com.xiaomi.push.service;

import android.util.Pair;
import com.xiaomi.push.id;
import com.xiaomi.push.ie;
import com.xiaomi.push.im;
import com.xiaomi.push.io;
import com.xiaomi.push.jc;
import com.xiaomi.push.jd;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class ap {
    public static int a(ao aoVar, id idVar) {
        return aoVar.f1000a.getInt(a(idVar), aq.f25586a[idVar.ordinal()] != 1 ? 0 : 1);
    }

    private static String a(id idVar) {
        return "oc_version_" + idVar.a();
    }

    private static List<Pair<Integer, Object>> a(List<io> list, boolean z2) {
        if (com.xiaomi.push.ad.a(list)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (io ioVar : list) {
            int iA = ioVar.a();
            ie ieVarA = ie.a(ioVar.b());
            if (ieVarA != null) {
                if (z2 && ioVar.f628a) {
                    arrayList.add(new Pair(Integer.valueOf(iA), null));
                } else {
                    int i2 = aq.f25587b[ieVarA.ordinal()];
                    arrayList.add(i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? null : new Pair(Integer.valueOf(iA), Boolean.valueOf(ioVar.g())) : new Pair(Integer.valueOf(iA), ioVar.m540a()) : new Pair(Integer.valueOf(iA), Long.valueOf(ioVar.m539a())) : new Pair(Integer.valueOf(iA), Integer.valueOf(ioVar.c())));
                }
            }
        }
        return arrayList;
    }

    public static void a(ao aoVar, id idVar, int i2) {
        aoVar.f1000a.edit().putInt(a(idVar), i2).commit();
    }

    public static void a(ao aoVar, jc jcVar) {
        aoVar.b(a(jcVar.a(), true));
    }

    public static void a(ao aoVar, jd jdVar) {
        for (im imVar : jdVar.a()) {
            if (imVar.a() > a(aoVar, imVar.m535a())) {
                a(aoVar, imVar.m535a(), imVar.a());
                aoVar.a(a(imVar.f620a, false));
            }
        }
    }
}
