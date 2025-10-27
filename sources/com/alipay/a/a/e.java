package com.alipay.a.a;

import cn.hutool.core.text.StrPool;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    static List<i> f2919a;

    static {
        ArrayList arrayList = new ArrayList();
        f2919a = arrayList;
        arrayList.add(new l());
        f2919a.add(new d());
        f2919a.add(new c());
        f2919a.add(new h());
        f2919a.add(new k());
        f2919a.add(new b());
        f2919a.add(new a());
        f2919a.add(new g());
    }

    public static final <T> T a(Object obj, Type type) {
        T t2;
        for (i iVar : f2919a) {
            if (iVar.a(com.alipay.a.b.a.a(type)) && (t2 = (T) iVar.a(obj, type)) != null) {
                return t2;
            }
        }
        return null;
    }

    public static final Object a(String str, Type type) {
        Object bVar;
        if (str == null || str.length() == 0) {
            return null;
        }
        String strTrim = str.trim();
        if (strTrim.startsWith(StrPool.BRACKET_START) && strTrim.endsWith(StrPool.BRACKET_END)) {
            bVar = new org.json.alipay.a(strTrim);
        } else {
            if (!strTrim.startsWith(StrPool.DELIM_START) || !strTrim.endsWith("}")) {
                return a((Object) strTrim, type);
            }
            bVar = new org.json.alipay.b(strTrim);
        }
        return a(bVar, type);
    }
}
