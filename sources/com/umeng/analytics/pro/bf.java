package com.umeng.analytics.pro;

import com.umeng.analytics.pro.bc;
import com.umeng.analytics.pro.bf;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
public abstract class bf<T extends bf<?, ?>, F extends bc> implements av<T, F> {

    /* renamed from: c, reason: collision with root package name */
    private static final Map<Class<? extends cc>, cd> f22559c;

    /* renamed from: a, reason: collision with root package name */
    protected Object f22560a;

    /* renamed from: b, reason: collision with root package name */
    protected F f22561b;

    public static class a extends ce<bf> {
        private a() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, bf bfVar) throws bb {
            bfVar.f22561b = null;
            bfVar.f22560a = null;
            buVar.j();
            bp bpVarL = buVar.l();
            Object objA = bfVar.a(buVar, bpVarL);
            bfVar.f22560a = objA;
            if (objA != null) {
                bfVar.f22561b = (F) bfVar.a(bpVarL.f22626c);
            }
            buVar.m();
            buVar.l();
            buVar.k();
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, bf bfVar) throws bb {
            if (bfVar.a() == null || bfVar.b() == null) {
                throw new bv("Cannot write a TUnion with no set value!");
            }
            buVar.a(bfVar.d());
            buVar.a(bfVar.c(bfVar.f22561b));
            bfVar.a(buVar);
            buVar.c();
            buVar.d();
            buVar.b();
        }
    }

    public static class b implements cd {
        private b() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public a b() {
            return new a();
        }
    }

    public static class c extends cf<bf> {
        private c() {
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void b(bu buVar, bf bfVar) throws bb {
            bfVar.f22561b = null;
            bfVar.f22560a = null;
            short sV = buVar.v();
            Object objA = bfVar.a(buVar, sV);
            bfVar.f22560a = objA;
            if (objA != null) {
                bfVar.f22561b = (F) bfVar.a(sV);
            }
        }

        @Override // com.umeng.analytics.pro.cc
        /* renamed from: b, reason: merged with bridge method [inline-methods] */
        public void a(bu buVar, bf bfVar) throws bb {
            if (bfVar.a() == null || bfVar.b() == null) {
                throw new bv("Cannot write a TUnion with no set value!");
            }
            buVar.a(bfVar.f22561b.a());
            bfVar.b(buVar);
        }
    }

    public static class d implements cd {
        private d() {
        }

        @Override // com.umeng.analytics.pro.cd
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public c b() {
            return new c();
        }
    }

    static {
        HashMap map = new HashMap();
        f22559c = map;
        map.put(ce.class, new b());
        map.put(cf.class, new d());
    }

    public bf() {
        this.f22561b = null;
        this.f22560a = null;
    }

    private static Object a(Object obj) {
        return obj instanceof av ? ((av) obj).deepCopy() : obj instanceof ByteBuffer ? aw.d((ByteBuffer) obj) : obj instanceof List ? a((List) obj) : obj instanceof Set ? a((Set) obj) : obj instanceof Map ? a((Map<Object, Object>) obj) : obj;
    }

    public abstract F a(short s2);

    public abstract Object a(bu buVar, bp bpVar) throws bb;

    public abstract Object a(bu buVar, short s2) throws bb;

    public abstract void a(bu buVar) throws bb;

    public Object b() {
        return this.f22560a;
    }

    public abstract void b(F f2, Object obj) throws ClassCastException;

    public abstract void b(bu buVar) throws bb;

    public abstract bp c(F f2);

    public boolean c() {
        return this.f22561b != null;
    }

    @Override // com.umeng.analytics.pro.av
    public final void clear() {
        this.f22561b = null;
        this.f22560a = null;
    }

    public abstract bz d();

    @Override // com.umeng.analytics.pro.av
    public void read(bu buVar) throws bb {
        f22559c.get(buVar.D()).b().b(buVar, this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(getClass().getSimpleName());
        sb.append(" ");
        if (a() != null) {
            Object objB = b();
            sb.append(c(a()).f22624a);
            sb.append(":");
            if (objB instanceof ByteBuffer) {
                aw.a((ByteBuffer) objB, sb);
            } else {
                sb.append(objB.toString());
            }
        }
        sb.append(">");
        return sb.toString();
    }

    @Override // com.umeng.analytics.pro.av
    public void write(bu buVar) throws bb {
        f22559c.get(buVar.D()).b().a(buVar, this);
    }

    public boolean b(F f2) {
        return this.f22561b == f2;
    }

    public boolean b(int i2) {
        return b((bf<T, F>) a((short) i2));
    }

    public bf(F f2, Object obj) throws ClassCastException {
        a((bf<T, F>) f2, obj);
    }

    public bf(bf<T, F> bfVar) {
        if (bfVar.getClass().equals(getClass())) {
            this.f22561b = bfVar.f22561b;
            this.f22560a = a(bfVar.f22560a);
            return;
        }
        throw new ClassCastException();
    }

    private static Map a(Map<Object, Object> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            map2.put(a(entry.getKey()), a(entry.getValue()));
        }
        return map2;
    }

    private static Set a(Set set) {
        HashSet hashSet = new HashSet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            hashSet.add(a(it.next()));
        }
        return hashSet;
    }

    private static List a(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(a(it.next()));
        }
        return arrayList;
    }

    public F a() {
        return this.f22561b;
    }

    public Object a(F f2) {
        if (f2 == this.f22561b) {
            return b();
        }
        throw new IllegalArgumentException("Cannot get the value of field " + f2 + " because union's set field is " + this.f22561b);
    }

    public Object a(int i2) {
        return a((bf<T, F>) a((short) i2));
    }

    public void a(F f2, Object obj) throws ClassCastException {
        b(f2, obj);
        this.f22561b = f2;
        this.f22560a = obj;
    }

    public void a(int i2, Object obj) throws ClassCastException {
        a((bf<T, F>) a((short) i2), obj);
    }
}
