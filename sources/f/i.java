package f;

import cn.hutool.core.text.CharPool;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes8.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    public boolean f26913a = false;

    /* renamed from: b, reason: collision with root package name */
    public boolean f26914b;

    /* renamed from: c, reason: collision with root package name */
    public ArrayList<a> f26915c;

    /* renamed from: d, reason: collision with root package name */
    public int f26916d;

    public class a {

        /* renamed from: a, reason: collision with root package name */
        public String f26917a;

        /* renamed from: b, reason: collision with root package name */
        public String f26918b;

        /* renamed from: c, reason: collision with root package name */
        public String f26919c;

        public a(String str, String str2, String str3) {
            this.f26917a = str;
            this.f26918b = str2;
            this.f26919c = str3;
        }

        public String a() {
            return this.f26918b;
        }

        public String b() {
            return this.f26919c;
        }

        public String c() {
            return this.f26917a;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            a aVar = (a) obj;
            String str = this.f26917a;
            if (str == null ? aVar.f26917a != null : !str.equals(aVar.f26917a)) {
                return false;
            }
            String str2 = this.f26918b;
            if (str2 == null ? aVar.f26918b != null : !str2.equals(aVar.f26918b)) {
                return false;
            }
            String str3 = this.f26919c;
            String str4 = aVar.f26919c;
            return str3 != null ? str3.equals(str4) : str4 == null;
        }

        public int hashCode() {
            String str = this.f26917a;
            int iHashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.f26918b;
            int iHashCode2 = (iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.f26919c;
            return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
        }

        public String toString() {
            return "ServerURL{url='" + this.f26917a + CharPool.SINGLE_QUOTE + ", ip='" + this.f26918b + CharPool.SINGLE_QUOTE + ", port='" + this.f26919c + CharPool.SINGLE_QUOTE + '}';
        }

        public void a(String str) {
            this.f26918b = str;
        }

        public void b(String str) {
            this.f26919c = str;
        }

        public void c(String str) {
            this.f26917a = str;
        }
    }

    public void a(int i2) {
        this.f26916d = i2;
    }

    public void b(boolean z2) {
        this.f26913a = z2;
    }

    public ArrayList<a> c() {
        return this.f26915c;
    }

    public boolean d() {
        return this.f26914b;
    }

    public boolean e() {
        return this.f26913a;
    }

    public void a(boolean z2) {
        this.f26914b = z2;
    }

    public int b() {
        return this.f26916d;
    }

    public void a(String str, String str2, String str3) {
        if (this.f26915c == null) {
            this.f26915c = new ArrayList<>();
        }
        if (this.f26915c.contains(new a(str, str2, str3))) {
            return;
        }
        this.f26915c.add(new a(str, str2, str3));
    }

    public void b(String str, String str2, String str3) {
        Iterator<a> it = this.f26915c.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next.c().equals(str) && next.a().equals(str2) && next.b().equals(str3)) {
                this.f26915c.remove(next);
                return;
            }
        }
    }

    public void a() {
        ArrayList<a> arrayList = this.f26915c;
        if (arrayList != null) {
            arrayList.clear();
        }
    }
}
