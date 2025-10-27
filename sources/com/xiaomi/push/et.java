package com.xiaomi.push;

import java.io.IOException;

/* loaded from: classes6.dex */
public final class et {

    public static final class a extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f351a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f353b;

        /* renamed from: c, reason: collision with other field name */
        private boolean f355c;

        /* renamed from: d, reason: collision with other field name */
        private boolean f357d;

        /* renamed from: e, reason: collision with other field name */
        private boolean f359e;

        /* renamed from: f, reason: collision with other field name */
        private boolean f360f;

        /* renamed from: g, reason: collision with root package name */
        private boolean f24783g;

        /* renamed from: h, reason: collision with root package name */
        private boolean f24784h;

        /* renamed from: i, reason: collision with root package name */
        private boolean f24785i;

        /* renamed from: j, reason: collision with root package name */
        private boolean f24786j;

        /* renamed from: k, reason: collision with root package name */
        private boolean f24787k;

        /* renamed from: a, reason: collision with root package name */
        private int f24777a = 0;

        /* renamed from: a, reason: collision with other field name */
        private long f349a = 0;

        /* renamed from: a, reason: collision with other field name */
        private String f350a = "";

        /* renamed from: b, reason: collision with other field name */
        private String f352b = "";

        /* renamed from: c, reason: collision with other field name */
        private String f354c = "";

        /* renamed from: d, reason: collision with other field name */
        private String f356d = "";

        /* renamed from: e, reason: collision with other field name */
        private String f358e = "";

        /* renamed from: b, reason: collision with root package name */
        private int f24778b = 1;

        /* renamed from: c, reason: collision with root package name */
        private int f24779c = 0;

        /* renamed from: d, reason: collision with root package name */
        private int f24780d = 0;

        /* renamed from: f, reason: collision with root package name */
        private String f24782f = "";

        /* renamed from: e, reason: collision with root package name */
        private int f24781e = -1;

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24781e < 0) {
                b();
            }
            return this.f24781e;
        }

        /* renamed from: a, reason: collision with other method in class */
        public long m345a() {
            return this.f349a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public a m346a() {
            this.f360f = false;
            this.f356d = "";
            return this;
        }

        public a a(int i2) {
            this.f351a = true;
            this.f24777a = i2;
            return this;
        }

        public a a(long j2) {
            this.f353b = true;
            this.f349a = j2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public a a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                switch (iM213a) {
                    case 0:
                        return this;
                    case 8:
                        a(bVar.m222b());
                        break;
                    case 16:
                        a(bVar.m223b());
                        break;
                    case 26:
                        a(bVar.m216a());
                        break;
                    case 34:
                        b(bVar.m216a());
                        break;
                    case 42:
                        c(bVar.m216a());
                        break;
                    case 50:
                        d(bVar.m216a());
                        break;
                    case 58:
                        e(bVar.m216a());
                        break;
                    case 64:
                        b(bVar.m222b());
                        break;
                    case 72:
                        c(bVar.m222b());
                        break;
                    case 80:
                        d(bVar.m222b());
                        break;
                    case 90:
                        f(bVar.m216a());
                        break;
                    default:
                        if (!a(bVar, iM213a)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public a a(String str) {
            this.f355c = true;
            this.f350a = str;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m347a() {
            return this.f350a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m348a()) {
                cVar.m255a(1, c());
            }
            if (m350b()) {
                cVar.m269b(2, m345a());
            }
            if (m352c()) {
                cVar.m259a(3, m347a());
            }
            if (m354d()) {
                cVar.m259a(4, m349b());
            }
            if (m356e()) {
                cVar.m259a(5, m351c());
            }
            if (m358f()) {
                cVar.m259a(6, m353d());
            }
            if (g()) {
                cVar.m259a(7, m355e());
            }
            if (h()) {
                cVar.m255a(8, d());
            }
            if (i()) {
                cVar.m255a(9, e());
            }
            if (j()) {
                cVar.m255a(10, f());
            }
            if (k()) {
                cVar.m259a(11, m357f());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m348a() {
            return this.f351a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m348a() ? 0 + com.xiaomi.push.c.a(1, c()) : 0;
            if (m350b()) {
                iA += com.xiaomi.push.c.b(2, m345a());
            }
            if (m352c()) {
                iA += com.xiaomi.push.c.a(3, m347a());
            }
            if (m354d()) {
                iA += com.xiaomi.push.c.a(4, m349b());
            }
            if (m356e()) {
                iA += com.xiaomi.push.c.a(5, m351c());
            }
            if (m358f()) {
                iA += com.xiaomi.push.c.a(6, m353d());
            }
            if (g()) {
                iA += com.xiaomi.push.c.a(7, m355e());
            }
            if (h()) {
                iA += com.xiaomi.push.c.a(8, d());
            }
            if (i()) {
                iA += com.xiaomi.push.c.a(9, e());
            }
            if (j()) {
                iA += com.xiaomi.push.c.a(10, f());
            }
            if (k()) {
                iA += com.xiaomi.push.c.a(11, m357f());
            }
            this.f24781e = iA;
            return iA;
        }

        public a b(int i2) {
            this.f24784h = true;
            this.f24778b = i2;
            return this;
        }

        public a b(String str) {
            this.f357d = true;
            this.f352b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m349b() {
            return this.f352b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m350b() {
            return this.f353b;
        }

        public int c() {
            return this.f24777a;
        }

        public a c(int i2) {
            this.f24785i = true;
            this.f24779c = i2;
            return this;
        }

        public a c(String str) {
            this.f359e = true;
            this.f354c = str;
            return this;
        }

        /* renamed from: c, reason: collision with other method in class */
        public String m351c() {
            return this.f354c;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m352c() {
            return this.f355c;
        }

        public int d() {
            return this.f24778b;
        }

        public a d(int i2) {
            this.f24786j = true;
            this.f24780d = i2;
            return this;
        }

        public a d(String str) {
            this.f360f = true;
            this.f356d = str;
            return this;
        }

        /* renamed from: d, reason: collision with other method in class */
        public String m353d() {
            return this.f356d;
        }

        /* renamed from: d, reason: collision with other method in class */
        public boolean m354d() {
            return this.f357d;
        }

        public int e() {
            return this.f24779c;
        }

        public a e(String str) {
            this.f24783g = true;
            this.f358e = str;
            return this;
        }

        /* renamed from: e, reason: collision with other method in class */
        public String m355e() {
            return this.f358e;
        }

        /* renamed from: e, reason: collision with other method in class */
        public boolean m356e() {
            return this.f359e;
        }

        public int f() {
            return this.f24780d;
        }

        public a f(String str) {
            this.f24787k = true;
            this.f24782f = str;
            return this;
        }

        /* renamed from: f, reason: collision with other method in class */
        public String m357f() {
            return this.f24782f;
        }

        /* renamed from: f, reason: collision with other method in class */
        public boolean m358f() {
            return this.f360f;
        }

        public boolean g() {
            return this.f24783g;
        }

        public boolean h() {
            return this.f24784h;
        }

        public boolean i() {
            return this.f24785i;
        }

        public boolean j() {
            return this.f24786j;
        }

        public boolean k() {
            return this.f24787k;
        }
    }

    public static final class b extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f361a;

        /* renamed from: c, reason: collision with other field name */
        private boolean f363c;

        /* renamed from: d, reason: collision with other field name */
        private boolean f364d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f24792e;

        /* renamed from: b, reason: collision with other field name */
        private boolean f362b = false;

        /* renamed from: a, reason: collision with root package name */
        private int f24788a = 0;

        /* renamed from: b, reason: collision with root package name */
        private int f24789b = 0;

        /* renamed from: c, reason: collision with root package name */
        private int f24790c = 0;

        /* renamed from: d, reason: collision with root package name */
        private int f24791d = -1;

        public static b a(byte[] bArr) {
            return (b) new b().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24791d < 0) {
                b();
            }
            return this.f24791d;
        }

        public b a(int i2) {
            this.f363c = true;
            this.f24788a = i2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public b a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 8) {
                    a(bVar.m219a());
                } else if (iM213a == 24) {
                    a(bVar.m222b());
                } else if (iM213a == 32) {
                    b(bVar.m222b());
                } else if (iM213a == 40) {
                    c(bVar.m222b());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public b a(boolean z2) {
            this.f361a = true;
            this.f362b = z2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m360b()) {
                cVar.m260a(1, m359a());
            }
            if (m361c()) {
                cVar.m255a(3, c());
            }
            if (m362d()) {
                cVar.m255a(4, d());
            }
            if (m363e()) {
                cVar.m255a(5, e());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m359a() {
            return this.f362b;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m360b() ? 0 + com.xiaomi.push.c.a(1, m359a()) : 0;
            if (m361c()) {
                iA += com.xiaomi.push.c.a(3, c());
            }
            if (m362d()) {
                iA += com.xiaomi.push.c.a(4, d());
            }
            if (m363e()) {
                iA += com.xiaomi.push.c.a(5, e());
            }
            this.f24791d = iA;
            return iA;
        }

        public b b(int i2) {
            this.f364d = true;
            this.f24789b = i2;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m360b() {
            return this.f361a;
        }

        public int c() {
            return this.f24788a;
        }

        public b c(int i2) {
            this.f24792e = true;
            this.f24790c = i2;
            return this;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m361c() {
            return this.f363c;
        }

        public int d() {
            return this.f24789b;
        }

        /* renamed from: d, reason: collision with other method in class */
        public boolean m362d() {
            return this.f364d;
        }

        public int e() {
            return this.f24790c;
        }

        /* renamed from: e, reason: collision with other method in class */
        public boolean m363e() {
            return this.f24792e;
        }
    }

    public static final class c extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f366a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f367b;

        /* renamed from: c, reason: collision with other field name */
        private boolean f368c;

        /* renamed from: d, reason: collision with other field name */
        private boolean f369d;

        /* renamed from: e, reason: collision with other field name */
        private boolean f370e;

        /* renamed from: f, reason: collision with other field name */
        private boolean f371f;

        /* renamed from: a, reason: collision with other field name */
        private String f365a = "";

        /* renamed from: b, reason: collision with root package name */
        private String f24794b = "";

        /* renamed from: c, reason: collision with root package name */
        private String f24795c = "";

        /* renamed from: d, reason: collision with root package name */
        private String f24796d = "";

        /* renamed from: e, reason: collision with root package name */
        private String f24797e = "";

        /* renamed from: f, reason: collision with root package name */
        private String f24798f = "";

        /* renamed from: a, reason: collision with root package name */
        private int f24793a = -1;

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24793a < 0) {
                b();
            }
            return this.f24793a;
        }

        @Override // com.xiaomi.push.e
        public c a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 10) {
                    a(bVar.m216a());
                } else if (iM213a == 18) {
                    b(bVar.m216a());
                } else if (iM213a == 26) {
                    c(bVar.m216a());
                } else if (iM213a == 34) {
                    d(bVar.m216a());
                } else if (iM213a == 42) {
                    e(bVar.m216a());
                } else if (iM213a == 50) {
                    f(bVar.m216a());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public c a(String str) {
            this.f366a = true;
            this.f365a = str;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m364a() {
            return this.f365a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m365a()) {
                cVar.m259a(1, m364a());
            }
            if (m367b()) {
                cVar.m259a(2, m366b());
            }
            if (m368c()) {
                cVar.m259a(3, c());
            }
            if (m369d()) {
                cVar.m259a(4, d());
            }
            if (m370e()) {
                cVar.m259a(5, e());
            }
            if (m371f()) {
                cVar.m259a(6, f());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m365a() {
            return this.f366a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m365a() ? 0 + com.xiaomi.push.c.a(1, m364a()) : 0;
            if (m367b()) {
                iA += com.xiaomi.push.c.a(2, m366b());
            }
            if (m368c()) {
                iA += com.xiaomi.push.c.a(3, c());
            }
            if (m369d()) {
                iA += com.xiaomi.push.c.a(4, d());
            }
            if (m370e()) {
                iA += com.xiaomi.push.c.a(5, e());
            }
            if (m371f()) {
                iA += com.xiaomi.push.c.a(6, f());
            }
            this.f24793a = iA;
            return iA;
        }

        public c b(String str) {
            this.f367b = true;
            this.f24794b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m366b() {
            return this.f24794b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m367b() {
            return this.f367b;
        }

        public c c(String str) {
            this.f368c = true;
            this.f24795c = str;
            return this;
        }

        public String c() {
            return this.f24795c;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m368c() {
            return this.f368c;
        }

        public c d(String str) {
            this.f369d = true;
            this.f24796d = str;
            return this;
        }

        public String d() {
            return this.f24796d;
        }

        /* renamed from: d, reason: collision with other method in class */
        public boolean m369d() {
            return this.f369d;
        }

        public c e(String str) {
            this.f370e = true;
            this.f24797e = str;
            return this;
        }

        public String e() {
            return this.f24797e;
        }

        /* renamed from: e, reason: collision with other method in class */
        public boolean m370e() {
            return this.f370e;
        }

        public c f(String str) {
            this.f371f = true;
            this.f24798f = str;
            return this;
        }

        public String f() {
            return this.f24798f;
        }

        /* renamed from: f, reason: collision with other method in class */
        public boolean m371f() {
            return this.f371f;
        }
    }

    public static final class d extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f373a;

        /* renamed from: c, reason: collision with other field name */
        private boolean f375c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f24802d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f24803e;

        /* renamed from: b, reason: collision with other field name */
        private boolean f374b = false;

        /* renamed from: a, reason: collision with other field name */
        private String f372a = "";

        /* renamed from: b, reason: collision with root package name */
        private String f24800b = "";

        /* renamed from: c, reason: collision with root package name */
        private String f24801c = "";

        /* renamed from: a, reason: collision with root package name */
        private int f24799a = -1;

        public static d a(byte[] bArr) {
            return (d) new d().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24799a < 0) {
                b();
            }
            return this.f24799a;
        }

        @Override // com.xiaomi.push.e
        public d a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 8) {
                    a(bVar.m219a());
                } else if (iM213a == 18) {
                    a(bVar.m216a());
                } else if (iM213a == 26) {
                    b(bVar.m216a());
                } else if (iM213a == 34) {
                    c(bVar.m216a());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public d a(String str) {
            this.f375c = true;
            this.f372a = str;
            return this;
        }

        public d a(boolean z2) {
            this.f373a = true;
            this.f374b = z2;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m372a() {
            return this.f372a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m375b()) {
                cVar.m260a(1, m373a());
            }
            if (m376c()) {
                cVar.m259a(2, m372a());
            }
            if (d()) {
                cVar.m259a(3, m374b());
            }
            if (e()) {
                cVar.m259a(4, c());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m373a() {
            return this.f374b;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m375b() ? 0 + com.xiaomi.push.c.a(1, m373a()) : 0;
            if (m376c()) {
                iA += com.xiaomi.push.c.a(2, m372a());
            }
            if (d()) {
                iA += com.xiaomi.push.c.a(3, m374b());
            }
            if (e()) {
                iA += com.xiaomi.push.c.a(4, c());
            }
            this.f24799a = iA;
            return iA;
        }

        public d b(String str) {
            this.f24802d = true;
            this.f24800b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m374b() {
            return this.f24800b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m375b() {
            return this.f373a;
        }

        public d c(String str) {
            this.f24803e = true;
            this.f24801c = str;
            return this;
        }

        public String c() {
            return this.f24801c;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m376c() {
            return this.f375c;
        }

        public boolean d() {
            return this.f24802d;
        }

        public boolean e() {
            return this.f24803e;
        }
    }

    public static final class e extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f378a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f380b;

        /* renamed from: c, reason: collision with other field name */
        private boolean f382c;

        /* renamed from: d, reason: collision with other field name */
        private boolean f384d;

        /* renamed from: e, reason: collision with other field name */
        private boolean f385e;

        /* renamed from: f, reason: collision with other field name */
        private boolean f386f;

        /* renamed from: g, reason: collision with root package name */
        private boolean f24810g;

        /* renamed from: h, reason: collision with root package name */
        private boolean f24811h;

        /* renamed from: i, reason: collision with root package name */
        private boolean f24812i;

        /* renamed from: j, reason: collision with root package name */
        private boolean f24813j;

        /* renamed from: a, reason: collision with root package name */
        private int f24804a = 0;

        /* renamed from: a, reason: collision with other field name */
        private String f377a = "";

        /* renamed from: b, reason: collision with other field name */
        private String f379b = "";

        /* renamed from: c, reason: collision with other field name */
        private String f381c = "";

        /* renamed from: b, reason: collision with root package name */
        private int f24805b = 0;

        /* renamed from: d, reason: collision with other field name */
        private String f383d = "";

        /* renamed from: e, reason: collision with root package name */
        private String f24808e = "";

        /* renamed from: f, reason: collision with root package name */
        private String f24809f = "";

        /* renamed from: a, reason: collision with other field name */
        private b f376a = null;

        /* renamed from: c, reason: collision with root package name */
        private int f24806c = 0;

        /* renamed from: d, reason: collision with root package name */
        private int f24807d = -1;

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24807d < 0) {
                b();
            }
            return this.f24807d;
        }

        /* renamed from: a, reason: collision with other method in class */
        public b m377a() {
            return this.f376a;
        }

        public e a(int i2) {
            this.f378a = true;
            this.f24804a = i2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public e a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                switch (iM213a) {
                    case 0:
                        return this;
                    case 8:
                        a(bVar.c());
                        break;
                    case 18:
                        a(bVar.m216a());
                        break;
                    case 26:
                        b(bVar.m216a());
                        break;
                    case 34:
                        c(bVar.m216a());
                        break;
                    case 40:
                        b(bVar.m222b());
                        break;
                    case 50:
                        d(bVar.m216a());
                        break;
                    case 58:
                        e(bVar.m216a());
                        break;
                    case 66:
                        f(bVar.m216a());
                        break;
                    case 74:
                        b bVar2 = new b();
                        bVar.a(bVar2);
                        a(bVar2);
                        break;
                    case 80:
                        c(bVar.m222b());
                        break;
                    default:
                        if (!a(bVar, iM213a)) {
                            return this;
                        }
                        break;
                }
            }
        }

        public e a(b bVar) {
            bVar.getClass();
            this.f24812i = true;
            this.f376a = bVar;
            return this;
        }

        public e a(String str) {
            this.f380b = true;
            this.f377a = str;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m378a() {
            return this.f377a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m379a()) {
                cVar.m268b(1, c());
            }
            if (m381b()) {
                cVar.m259a(2, m378a());
            }
            if (m383c()) {
                cVar.m259a(3, m380b());
            }
            if (m385d()) {
                cVar.m259a(4, m382c());
            }
            if (m387e()) {
                cVar.m255a(5, d());
            }
            if (m388f()) {
                cVar.m259a(6, m384d());
            }
            if (g()) {
                cVar.m259a(7, m386e());
            }
            if (h()) {
                cVar.m259a(8, f());
            }
            if (i()) {
                cVar.m258a(9, (com.xiaomi.push.e) m377a());
            }
            if (j()) {
                cVar.m255a(10, e());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m379a() {
            return this.f378a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iB = m379a() ? 0 + com.xiaomi.push.c.b(1, c()) : 0;
            if (m381b()) {
                iB += com.xiaomi.push.c.a(2, m378a());
            }
            if (m383c()) {
                iB += com.xiaomi.push.c.a(3, m380b());
            }
            if (m385d()) {
                iB += com.xiaomi.push.c.a(4, m382c());
            }
            if (m387e()) {
                iB += com.xiaomi.push.c.a(5, d());
            }
            if (m388f()) {
                iB += com.xiaomi.push.c.a(6, m384d());
            }
            if (g()) {
                iB += com.xiaomi.push.c.a(7, m386e());
            }
            if (h()) {
                iB += com.xiaomi.push.c.a(8, f());
            }
            if (i()) {
                iB += com.xiaomi.push.c.a(9, (com.xiaomi.push.e) m377a());
            }
            if (j()) {
                iB += com.xiaomi.push.c.a(10, e());
            }
            this.f24807d = iB;
            return iB;
        }

        public e b(int i2) {
            this.f385e = true;
            this.f24805b = i2;
            return this;
        }

        public e b(String str) {
            this.f382c = true;
            this.f379b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m380b() {
            return this.f379b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m381b() {
            return this.f380b;
        }

        public int c() {
            return this.f24804a;
        }

        public e c(int i2) {
            this.f24813j = true;
            this.f24806c = i2;
            return this;
        }

        public e c(String str) {
            this.f384d = true;
            this.f381c = str;
            return this;
        }

        /* renamed from: c, reason: collision with other method in class */
        public String m382c() {
            return this.f381c;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m383c() {
            return this.f382c;
        }

        public int d() {
            return this.f24805b;
        }

        public e d(String str) {
            this.f386f = true;
            this.f383d = str;
            return this;
        }

        /* renamed from: d, reason: collision with other method in class */
        public String m384d() {
            return this.f383d;
        }

        /* renamed from: d, reason: collision with other method in class */
        public boolean m385d() {
            return this.f384d;
        }

        public int e() {
            return this.f24806c;
        }

        public e e(String str) {
            this.f24810g = true;
            this.f24808e = str;
            return this;
        }

        /* renamed from: e, reason: collision with other method in class */
        public String m386e() {
            return this.f24808e;
        }

        /* renamed from: e, reason: collision with other method in class */
        public boolean m387e() {
            return this.f385e;
        }

        public e f(String str) {
            this.f24811h = true;
            this.f24809f = str;
            return this;
        }

        public String f() {
            return this.f24809f;
        }

        /* renamed from: f, reason: collision with other method in class */
        public boolean m388f() {
            return this.f386f;
        }

        public boolean g() {
            return this.f24810g;
        }

        public boolean h() {
            return this.f24811h;
        }

        public boolean i() {
            return this.f24812i;
        }

        public boolean j() {
            return this.f24813j;
        }
    }

    public static final class f extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f389a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f390b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f24816c;

        /* renamed from: a, reason: collision with other field name */
        private String f388a = "";

        /* renamed from: b, reason: collision with root package name */
        private String f24815b = "";

        /* renamed from: a, reason: collision with other field name */
        private b f387a = null;

        /* renamed from: a, reason: collision with root package name */
        private int f24814a = -1;

        public static f a(byte[] bArr) {
            return (f) new f().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24814a < 0) {
                b();
            }
            return this.f24814a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public b m389a() {
            return this.f387a;
        }

        @Override // com.xiaomi.push.e
        public f a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 10) {
                    a(bVar.m216a());
                } else if (iM213a == 18) {
                    b(bVar.m216a());
                } else if (iM213a == 26) {
                    b bVar2 = new b();
                    bVar.a(bVar2);
                    a(bVar2);
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public f a(b bVar) {
            bVar.getClass();
            this.f24816c = true;
            this.f387a = bVar;
            return this;
        }

        public f a(String str) {
            this.f389a = true;
            this.f388a = str;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m390a() {
            return this.f388a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m391a()) {
                cVar.m259a(1, m390a());
            }
            if (m393b()) {
                cVar.m259a(2, m392b());
            }
            if (c()) {
                cVar.m258a(3, (com.xiaomi.push.e) m389a());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m391a() {
            return this.f389a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m391a() ? 0 + com.xiaomi.push.c.a(1, m390a()) : 0;
            if (m393b()) {
                iA += com.xiaomi.push.c.a(2, m392b());
            }
            if (c()) {
                iA += com.xiaomi.push.c.a(3, (com.xiaomi.push.e) m389a());
            }
            this.f24814a = iA;
            return iA;
        }

        public f b(String str) {
            this.f390b = true;
            this.f24815b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m392b() {
            return this.f24815b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m393b() {
            return this.f390b;
        }

        public boolean c() {
            return this.f24816c;
        }
    }

    public static final class g extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f392a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f393b;

        /* renamed from: c, reason: collision with other field name */
        private boolean f394c;

        /* renamed from: a, reason: collision with other field name */
        private String f391a = "";

        /* renamed from: b, reason: collision with root package name */
        private String f24818b = "";

        /* renamed from: c, reason: collision with root package name */
        private String f24819c = "";

        /* renamed from: a, reason: collision with root package name */
        private int f24817a = -1;

        public static g a(byte[] bArr) {
            return (g) new g().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24817a < 0) {
                b();
            }
            return this.f24817a;
        }

        @Override // com.xiaomi.push.e
        public g a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 10) {
                    a(bVar.m216a());
                } else if (iM213a == 18) {
                    b(bVar.m216a());
                } else if (iM213a == 26) {
                    c(bVar.m216a());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public g a(String str) {
            this.f392a = true;
            this.f391a = str;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m394a() {
            return this.f391a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m395a()) {
                cVar.m259a(1, m394a());
            }
            if (m397b()) {
                cVar.m259a(2, m396b());
            }
            if (m398c()) {
                cVar.m259a(3, c());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m395a() {
            return this.f392a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m395a() ? 0 + com.xiaomi.push.c.a(1, m394a()) : 0;
            if (m397b()) {
                iA += com.xiaomi.push.c.a(2, m396b());
            }
            if (m398c()) {
                iA += com.xiaomi.push.c.a(3, c());
            }
            this.f24817a = iA;
            return iA;
        }

        public g b(String str) {
            this.f393b = true;
            this.f24818b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m396b() {
            return this.f24818b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m397b() {
            return this.f393b;
        }

        public g c(String str) {
            this.f394c = true;
            this.f24819c = str;
            return this;
        }

        public String c() {
            return this.f24819c;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m398c() {
            return this.f394c;
        }
    }

    public static final class h extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f396a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f397b;

        /* renamed from: a, reason: collision with root package name */
        private int f24820a = 0;

        /* renamed from: a, reason: collision with other field name */
        private String f395a = "";

        /* renamed from: b, reason: collision with root package name */
        private int f24821b = -1;

        public static h a(byte[] bArr) {
            return (h) new h().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24821b < 0) {
                b();
            }
            return this.f24821b;
        }

        public h a(int i2) {
            this.f396a = true;
            this.f24820a = i2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public h a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 8) {
                    a(bVar.m222b());
                } else if (iM213a == 18) {
                    a(bVar.m216a());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public h a(String str) {
            this.f397b = true;
            this.f395a = str;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m399a() {
            return this.f395a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m400a()) {
                cVar.m255a(1, c());
            }
            if (m401b()) {
                cVar.m259a(2, m399a());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m400a() {
            return this.f396a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m400a() ? 0 + com.xiaomi.push.c.a(1, c()) : 0;
            if (m401b()) {
                iA += com.xiaomi.push.c.a(2, m399a());
            }
            this.f24821b = iA;
            return iA;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m401b() {
            return this.f397b;
        }

        public int c() {
            return this.f24820a;
        }
    }

    public static final class i extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f399a;

        /* renamed from: a, reason: collision with other field name */
        private com.xiaomi.push.a f398a = com.xiaomi.push.a.f24586a;

        /* renamed from: a, reason: collision with root package name */
        private int f24822a = -1;

        public static i a(byte[] bArr) {
            return (i) new i().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24822a < 0) {
                b();
            }
            return this.f24822a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public com.xiaomi.push.a m402a() {
            return this.f398a;
        }

        public i a(com.xiaomi.push.a aVar) {
            this.f399a = true;
            this.f398a = aVar;
            return this;
        }

        @Override // com.xiaomi.push.e
        public i a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 10) {
                    a(bVar.m215a());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m403a()) {
                cVar.m257a(1, m402a());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m403a() {
            return this.f399a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m403a() ? 0 + com.xiaomi.push.c.a(1, m402a()) : 0;
            this.f24822a = iA;
            return iA;
        }
    }

    public static final class j extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f402a;

        /* renamed from: b, reason: collision with root package name */
        private boolean f24824b;

        /* renamed from: a, reason: collision with other field name */
        private com.xiaomi.push.a f400a = com.xiaomi.push.a.f24586a;

        /* renamed from: a, reason: collision with other field name */
        private b f401a = null;

        /* renamed from: a, reason: collision with root package name */
        private int f24823a = -1;

        public static j a(byte[] bArr) {
            return (j) new j().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24823a < 0) {
                b();
            }
            return this.f24823a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public com.xiaomi.push.a m404a() {
            return this.f400a;
        }

        /* renamed from: a, reason: collision with other method in class */
        public b m405a() {
            return this.f401a;
        }

        public j a(com.xiaomi.push.a aVar) {
            this.f402a = true;
            this.f400a = aVar;
            return this;
        }

        @Override // com.xiaomi.push.e
        public j a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 10) {
                    a(bVar.m215a());
                } else if (iM213a == 18) {
                    b bVar2 = new b();
                    bVar.a(bVar2);
                    a(bVar2);
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public j a(b bVar) {
            bVar.getClass();
            this.f24824b = true;
            this.f401a = bVar;
            return this;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m406a()) {
                cVar.m257a(1, m404a());
            }
            if (m407b()) {
                cVar.m258a(2, (com.xiaomi.push.e) m405a());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m406a() {
            return this.f402a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m406a() ? 0 + com.xiaomi.push.c.a(1, m404a()) : 0;
            if (m407b()) {
                iA += com.xiaomi.push.c.a(2, (com.xiaomi.push.e) m405a());
            }
            this.f24823a = iA;
            return iA;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m407b() {
            return this.f24824b;
        }
    }

    public static final class k extends com.xiaomi.push.e {

        /* renamed from: a, reason: collision with other field name */
        private boolean f405a;

        /* renamed from: b, reason: collision with other field name */
        private boolean f408b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f24827c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f24828d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f24829e;

        /* renamed from: g, reason: collision with root package name */
        private boolean f24831g;

        /* renamed from: a, reason: collision with other field name */
        private String f404a = "";

        /* renamed from: b, reason: collision with other field name */
        private String f407b = "";

        /* renamed from: a, reason: collision with other field name */
        private long f403a = 0;

        /* renamed from: b, reason: collision with other field name */
        private long f406b = 0;

        /* renamed from: f, reason: collision with root package name */
        private boolean f24830f = false;

        /* renamed from: a, reason: collision with root package name */
        private int f24825a = 0;

        /* renamed from: b, reason: collision with root package name */
        private int f24826b = -1;

        public static k a(byte[] bArr) {
            return (k) new k().a(bArr);
        }

        @Override // com.xiaomi.push.e
        public int a() {
            if (this.f24826b < 0) {
                b();
            }
            return this.f24826b;
        }

        /* renamed from: a, reason: collision with other method in class */
        public long m408a() {
            return this.f403a;
        }

        public k a(int i2) {
            this.f24831g = true;
            this.f24825a = i2;
            return this;
        }

        public k a(long j2) {
            this.f24827c = true;
            this.f403a = j2;
            return this;
        }

        @Override // com.xiaomi.push.e
        public k a(com.xiaomi.push.b bVar) throws com.xiaomi.push.d {
            while (true) {
                int iM213a = bVar.m213a();
                if (iM213a == 0) {
                    return this;
                }
                if (iM213a == 10) {
                    a(bVar.m216a());
                } else if (iM213a == 18) {
                    b(bVar.m216a());
                } else if (iM213a == 24) {
                    a(bVar.m214a());
                } else if (iM213a == 32) {
                    b(bVar.m214a());
                } else if (iM213a == 40) {
                    a(bVar.m219a());
                } else if (iM213a == 48) {
                    a(bVar.m222b());
                } else if (!a(bVar, iM213a)) {
                    return this;
                }
            }
        }

        public k a(String str) {
            this.f405a = true;
            this.f404a = str;
            return this;
        }

        public k a(boolean z2) {
            this.f24829e = true;
            this.f24830f = z2;
            return this;
        }

        /* renamed from: a, reason: collision with other method in class */
        public String m409a() {
            return this.f404a;
        }

        @Override // com.xiaomi.push.e
        public void a(com.xiaomi.push.c cVar) throws IOException {
            if (m410a()) {
                cVar.m259a(1, m409a());
            }
            if (m413b()) {
                cVar.m259a(2, m412b());
            }
            if (m414c()) {
                cVar.m256a(3, m408a());
            }
            if (d()) {
                cVar.m256a(4, m411b());
            }
            if (f()) {
                cVar.m260a(5, e());
            }
            if (g()) {
                cVar.m255a(6, c());
            }
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m410a() {
            return this.f405a;
        }

        @Override // com.xiaomi.push.e
        public int b() {
            int iA = m410a() ? 0 + com.xiaomi.push.c.a(1, m409a()) : 0;
            if (m413b()) {
                iA += com.xiaomi.push.c.a(2, m412b());
            }
            if (m414c()) {
                iA += com.xiaomi.push.c.a(3, m408a());
            }
            if (d()) {
                iA += com.xiaomi.push.c.a(4, m411b());
            }
            if (f()) {
                iA += com.xiaomi.push.c.a(5, e());
            }
            if (g()) {
                iA += com.xiaomi.push.c.a(6, c());
            }
            this.f24826b = iA;
            return iA;
        }

        /* renamed from: b, reason: collision with other method in class */
        public long m411b() {
            return this.f406b;
        }

        public k b(long j2) {
            this.f24828d = true;
            this.f406b = j2;
            return this;
        }

        public k b(String str) {
            this.f408b = true;
            this.f407b = str;
            return this;
        }

        /* renamed from: b, reason: collision with other method in class */
        public String m412b() {
            return this.f407b;
        }

        /* renamed from: b, reason: collision with other method in class */
        public boolean m413b() {
            return this.f408b;
        }

        public int c() {
            return this.f24825a;
        }

        /* renamed from: c, reason: collision with other method in class */
        public boolean m414c() {
            return this.f24827c;
        }

        public boolean d() {
            return this.f24828d;
        }

        public boolean e() {
            return this.f24830f;
        }

        public boolean f() {
            return this.f24829e;
        }

        public boolean g() {
            return this.f24831g;
        }
    }
}
