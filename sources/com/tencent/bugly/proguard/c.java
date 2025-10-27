package com.tencent.bugly.proguard;

import cn.hutool.core.util.CharsetUtil;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
class c {

    /* renamed from: a, reason: collision with root package name */
    protected HashMap<String, HashMap<String, byte[]>> f17812a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    protected HashMap<String, Object> f17813b = new HashMap<>();

    /* renamed from: e, reason: collision with root package name */
    private HashMap<String, Object> f17816e = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    protected String f17814c = CharsetUtil.GBK;

    /* renamed from: d, reason: collision with root package name */
    k f17815d = new k();

    public void a(String str) {
        this.f17814c = str;
    }

    public <T> void a(String str, T t2) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, UnsupportedEncodingException {
        if (str == null) {
            throw new IllegalArgumentException("put key can not is null");
        }
        if (t2 == null) {
            throw new IllegalArgumentException("put value can not is null");
        }
        if (t2 instanceof Set) {
            throw new IllegalArgumentException("can not support Set");
        }
        l lVar = new l();
        lVar.a(this.f17814c);
        lVar.a(t2, 0);
        byte[] bArrA = n.a(lVar.f17845a);
        HashMap<String, byte[]> map = new HashMap<>(1);
        ArrayList arrayList = new ArrayList(1);
        a((ArrayList<String>) arrayList, t2);
        map.put(a.a(arrayList), bArrA);
        this.f17816e.remove(str);
        this.f17812a.put(str, map);
    }

    private static void a(ArrayList<String> arrayList, Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        while (true) {
            if (obj.getClass().isArray()) {
                if (obj.getClass().getComponentType().toString().equals("byte")) {
                    if (Array.getLength(obj) > 0) {
                        arrayList.add("java.util.List");
                        obj = Array.get(obj, 0);
                    } else {
                        arrayList.add("Array");
                        arrayList.add("?");
                        return;
                    }
                } else {
                    throw new IllegalArgumentException("only byte[] is supported");
                }
            } else if (!(obj instanceof Array)) {
                if (obj instanceof List) {
                    arrayList.add("java.util.List");
                    List list = (List) obj;
                    if (list.size() > 0) {
                        obj = list.get(0);
                    } else {
                        arrayList.add("?");
                        return;
                    }
                } else if (obj instanceof Map) {
                    arrayList.add("java.util.Map");
                    Map map = (Map) obj;
                    if (map.size() > 0) {
                        Object next = map.keySet().iterator().next();
                        obj = map.get(next);
                        arrayList.add(next.getClass().getName());
                    } else {
                        arrayList.add("?");
                        arrayList.add("?");
                        return;
                    }
                } else {
                    arrayList.add(obj.getClass().getName());
                    return;
                }
            } else {
                throw new IllegalArgumentException("can not support Array, please use List");
            }
        }
    }

    public byte[] a() throws UnsupportedEncodingException {
        l lVar = new l(0);
        lVar.a(this.f17814c);
        lVar.a((Map) this.f17812a, 0);
        return n.a(lVar.f17845a);
    }

    public void a(byte[] bArr) {
        this.f17815d.a(bArr);
        this.f17815d.a(this.f17814c);
        HashMap map = new HashMap(1);
        HashMap map2 = new HashMap(1);
        map2.put("", new byte[0]);
        map.put("", map2);
        this.f17812a = this.f17815d.a((Map) map, 0, false);
    }
}
