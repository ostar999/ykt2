package f;

import d.a;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes8.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public int f26869a = 0;

    /* renamed from: b, reason: collision with root package name */
    public String f26870b = "";

    /* renamed from: c, reason: collision with root package name */
    public Map<String, g> f26871c = new HashMap();

    /* renamed from: d, reason: collision with root package name */
    public Map<String, d> f26872d = new HashMap();

    /* renamed from: e, reason: collision with root package name */
    public Map<String, String> f26873e = new HashMap();

    public String a(String str) {
        return this.f26873e.get(str);
    }

    public String b() {
        return this.f26870b;
    }

    public g c(String str) {
        return this.f26871c.get(str);
    }

    public void d(String str) {
        d dVarH = h(str);
        if (dVarH != null) {
            String str2 = dVarH.f26874a;
            int i2 = dVarH.f26882i;
            if (i2 == 1) {
                this.f26873e.remove(str2 + a.h.f26636f);
                if (this.f26873e.containsKey(str2 + a.h.f26637g)) {
                    return;
                }
                this.f26871c.remove(str2);
                return;
            }
            if (i2 == 2) {
                this.f26873e.remove(str2 + a.h.f26637g);
                if (this.f26873e.containsKey(str2 + a.h.f26636f)) {
                    return;
                }
                this.f26871c.remove(str2);
            }
        }
    }

    public void e(String str) {
        d dVarH = h(str);
        if (dVarH != null) {
            String str2 = dVarH.f26874a;
            int i2 = dVarH.f26882i;
            if (i2 == 1) {
                this.f26873e.remove(str2 + a.h.f26636f);
                return;
            }
            if (i2 == 2) {
                this.f26873e.remove(str2 + a.h.f26637g);
            }
        }
    }

    public void f(String str) {
        if (this.f26871c.remove(str) != null) {
            this.f26872d.remove(this.f26873e.remove(str + a.h.f26636f));
            this.f26872d.remove(this.f26873e.remove(str + a.h.f26637g));
        }
    }

    public void g(String str) {
        this.f26873e.remove(str);
    }

    public d h(String str) {
        return this.f26872d.remove(str);
    }

    public void i(String str) {
        this.f26871c.remove(str);
    }

    public void j(String str) {
        this.f26870b = str;
    }

    public void a(g gVar) {
        if (gVar != null) {
            this.f26871c.put(gVar.a(), gVar);
        }
    }

    public d b(String str) {
        return this.f26872d.get(str);
    }

    public Map<String, d> c() {
        return this.f26872d;
    }

    public void a(d dVar) {
        if (dVar != null) {
            this.f26872d.put(dVar.b(), dVar);
            this.f26873e.put(dVar.d() + a.h.a(dVar.a()), dVar.b());
        }
    }

    public int e() {
        return this.f26869a;
    }

    public void a() {
        this.f26870b = "";
        this.f26871c.clear();
        this.f26872d.clear();
        this.f26873e.clear();
    }

    public Map<String, g> d() {
        return this.f26871c;
    }

    public void a(int i2) {
        this.f26869a = i2;
    }
}
