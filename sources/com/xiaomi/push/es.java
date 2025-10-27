package com.xiaomi.push;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public final class es {

    public static final class a extends e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f346a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f347b;

        /* renamed from: d, reason: collision with root package name */
        private boolean f24774d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f24775e;

        /* renamed from: a, reason: collision with root package name */
        private int f24771a = 0;

        /* renamed from: c, reason: collision with other field name */
        private boolean f348c = false;

        /* renamed from: b, reason: collision with root package name */
        private int f24772b = 0;

        /* renamed from: f, reason: collision with root package name */
        private boolean f24776f = false;

        /* renamed from: a, reason: collision with other field name */
        private List<String> f345a = Collections.emptyList();

        /* renamed from: c, reason: collision with root package name */
        private int f24773c = -1;

        public static a a(byte[] bArr) {
            return (a) new a().a(bArr);
        }

        public static a b(b bVar) {
            return new a().a(bVar);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24773c < 0) {
                b();
            }
            return this.f24773c;
        }

        public a a(int i2) {
            this.f346a = true;
            this.f24771a = i2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public a a(b bVar) throws d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 8) {
                    a(bVar.c());
                } else if (iM213a == 16) {
                    a(bVar.m219a());
                } else if (iM213a == 24) {
                    b(bVar.m222b());
                } else if (iM213a == 32) {
                    b(bVar.m219a());
                } else if (iM213a == 42) {
                    a(bVar.m216a());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public a a(String str) {
            str.getClass();
            if (this.f345a.isEmpty()) {
                this.f345a = new ArrayList();
            }
            this.f345a.add(str);
            return this;
        }

        public a a(boolean z2) {
            this.f347b = true;
            this.f348c = z2;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public List<String> m339a() {
            return this.f345a;
        }

        @Override // com.xiaomi.push.e
        public void a(c cVar) {
            if (m340a()) {
                cVar.m268b(1, c());
            }
            if (m342c()) {
                cVar.m260a(2, m341b());
            }
            if (m343d()) {
                cVar.m255a(3, d());
            }
            if (f()) {
                cVar.m260a(4, m344e());
            }
            Iterator<String> it = m339a().iterator();
            while (it.hasNext()) {
                cVar.m259a(5, it.next());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m340a() {
            return this.f346a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = 0;
            int iB = m340a() ? c.b(1, c()) + 0 : 0;
            if (m342c()) {
                iB += c.a(2, m341b());
            }
            if (m343d()) {
                iB += c.a(3, d());
            }
            if (f()) {
                iB += c.a(4, m344e());
            }
            Iterator<String> it = m339a().iterator();
            while (it.hasNext()) {
                iA += c.a(it.next());
            }
            int size = iB + iA + (m339a().size() * 1);
            this.f24773c = size;
            return size;
        }

        public a b(int i2) {
            this.f24774d = true;
            this.f24772b = i2;
            return this;
        }

        public a b(boolean z2) {
            this.f24775e = true;
            this.f24776f = z2;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m341b() {
            return this.f348c;
        }

        public int c() {
            return this.f24771a;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m342c() {
            return this.f347b;
        }

        public int d() {
            return this.f24772b;
        }

        /* renamed from: d, reason: collision with other method in class */
        public boolean m343d() {
            return this.f24774d;
        }

        public int e() {
            return this.f345a.size();
        }

        /* renamed from: e, reason: collision with other method in class */
        public boolean m344e() {
            return this.f24776f;
        }

        public boolean f() {
            return this.f24775e;
        }
    }
}
