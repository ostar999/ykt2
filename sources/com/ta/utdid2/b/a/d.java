package com.ta.utdid2.b.a;

import com.ta.utdid2.b.a.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: b, reason: collision with root package name */
    private static final Object f17243b = new Object();

    /* renamed from: a, reason: collision with root package name */
    private final Object f17244a = new Object();

    /* renamed from: a, reason: collision with other field name */
    private HashMap<File, a> f86a = new HashMap<>();

    /* renamed from: c, reason: collision with root package name */
    private File f17245c;

    public static final class a implements b {

        /* renamed from: c, reason: collision with root package name */
        private static final Object f17246c = new Object();

        /* renamed from: a, reason: collision with root package name */
        private Map f17247a;

        /* renamed from: a, reason: collision with other field name */
        private WeakHashMap<b.InterfaceC0316b, Object> f87a;

        /* renamed from: d, reason: collision with root package name */
        private final int f17248d;

        /* renamed from: d, reason: collision with other field name */
        private final File f88d;

        /* renamed from: e, reason: collision with root package name */
        private final File f17249e;

        /* renamed from: j, reason: collision with root package name */
        private boolean f17250j = false;

        public a(File file, int i2, Map map) {
            this.f88d = file;
            this.f17249e = d.a(file);
            this.f17248d = i2;
            this.f17247a = map == null ? new HashMap() : map;
            this.f87a = new WeakHashMap<>();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean f() throws IOException {
            if (this.f88d.exists()) {
                if (this.f17249e.exists()) {
                    this.f88d.delete();
                } else if (!this.f88d.renameTo(this.f17249e)) {
                    return false;
                }
            }
            try {
                FileOutputStream fileOutputStreamA = a(this.f88d);
                if (fileOutputStreamA == null) {
                    return false;
                }
                e.a(this.f17247a, fileOutputStreamA);
                fileOutputStreamA.close();
                this.f17249e.delete();
                return true;
            } catch (Exception unused) {
                if (this.f88d.exists()) {
                    this.f88d.delete();
                }
                return false;
            }
        }

        public void b(boolean z2) {
            synchronized (this) {
                this.f17250j = z2;
            }
        }

        public boolean e() {
            boolean z2;
            synchronized (this) {
                z2 = this.f17250j;
            }
            return z2;
        }

        @Override // com.ta.utdid2.b.a.b
        public long getLong(String str, long j2) {
            synchronized (this) {
                Long l2 = (Long) this.f17247a.get(str);
                if (l2 != null) {
                    j2 = l2.longValue();
                }
            }
            return j2;
        }

        @Override // com.ta.utdid2.b.a.b
        public String getString(String str, String str2) {
            synchronized (this) {
                String str3 = (String) this.f17247a.get(str);
                if (str3 != null) {
                    str2 = str3;
                }
            }
            return str2;
        }

        /* renamed from: com.ta.utdid2.b.a.d$a$a, reason: collision with other inner class name */
        public final class C0317a implements b.a {

            /* renamed from: b, reason: collision with root package name */
            private final Map<String, Object> f17252b = new HashMap();

            /* renamed from: k, reason: collision with root package name */
            private boolean f17253k = false;

            public C0317a() {
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, String str2) {
                synchronized (this) {
                    this.f17252b.put(str, str2);
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a b() {
                synchronized (this) {
                    this.f17253k = true;
                }
                return this;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public boolean commit() {
                boolean z2;
                ArrayList arrayList;
                HashSet<b.InterfaceC0316b> hashSet;
                boolean zF;
                synchronized (d.f17243b) {
                    z2 = a.this.f87a.size() > 0;
                    if (z2) {
                        arrayList = new ArrayList();
                        hashSet = new HashSet(a.this.f87a.keySet());
                    } else {
                        arrayList = null;
                        hashSet = null;
                    }
                    synchronized (this) {
                        if (this.f17253k) {
                            a.this.f17247a.clear();
                            this.f17253k = false;
                        }
                        for (Map.Entry<String, Object> entry : this.f17252b.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (value == this) {
                                a.this.f17247a.remove(key);
                            } else {
                                a.this.f17247a.put(key, value);
                            }
                            if (z2) {
                                arrayList.add(key);
                            }
                        }
                        this.f17252b.clear();
                    }
                    zF = a.this.f();
                    if (zF) {
                        a.this.b(true);
                    }
                }
                if (z2) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = (String) arrayList.get(size);
                        for (b.InterfaceC0316b interfaceC0316b : hashSet) {
                            if (interfaceC0316b != null) {
                                interfaceC0316b.a(a.this, str);
                            }
                        }
                    }
                }
                return zF;
            }

            @Override // com.ta.utdid2.b.a.b.a
            public b.a a(String str, long j2) {
                synchronized (this) {
                    this.f17252b.put(str, Long.valueOf(j2));
                }
                return this;
            }
        }

        public void a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.f17247a = map;
                }
            }
        }

        @Override // com.ta.utdid2.b.a.b
        public b.a a() {
            return new C0317a();
        }

        private FileOutputStream a(File file) {
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException unused) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    return new FileOutputStream(file);
                } catch (FileNotFoundException unused2) {
                    return null;
                }
            }
        }
    }

    public d(String str) {
        if (str == null || str.length() <= 0) {
            throw new RuntimeException("Directory can not be empty");
        }
        this.f17245c = new File(str);
    }

    private File a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    private File b(String str) {
        return a(a(), str + ".xml");
    }

    private File a() {
        File file;
        synchronized (this.f17244a) {
            file = this.f17245c;
        }
        return file;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0089 A[Catch: all -> 0x005a, TRY_ENTER, TRY_LEAVE, TryCatch #16 {all -> 0x005a, blocks: (B:30:0x0057, B:51:0x0089), top: B:86:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.ta.utdid2.b.a.b a(java.lang.String r6, int r7) throws java.lang.Throwable {
        /*
            r5 = this;
            java.io.File r6 = r5.b(r6)
            java.lang.Object r0 = com.ta.utdid2.b.a.d.f17243b
            monitor-enter(r0)
            java.util.HashMap<java.io.File, com.ta.utdid2.b.a.d$a> r1 = r5.f86a     // Catch: java.lang.Throwable -> Lb0
            java.lang.Object r1 = r1.get(r6)     // Catch: java.lang.Throwable -> Lb0
            com.ta.utdid2.b.a.d$a r1 = (com.ta.utdid2.b.a.d.a) r1     // Catch: java.lang.Throwable -> Lb0
            if (r1 == 0) goto L19
            boolean r2 = r1.e()     // Catch: java.lang.Throwable -> Lb0
            if (r2 != 0) goto L19
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb0
            return r1
        L19:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb0
            java.io.File r0 = a(r6)
            boolean r2 = r0.exists()
            if (r2 == 0) goto L2a
            r6.delete()
            r0.renameTo(r6)
        L2a:
            boolean r0 = r6.exists()
            r2 = 0
            if (r0 == 0) goto L8d
            boolean r0 = r6.canRead()
            if (r0 == 0) goto L8d
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54 org.xmlpull.v1.XmlPullParserException -> L5c
            r0.<init>(r6)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L54 org.xmlpull.v1.XmlPullParserException -> L5c
            java.util.HashMap r2 = com.ta.utdid2.b.a.e.a(r0)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a org.xmlpull.v1.XmlPullParserException -> L4e
            r0.close()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a org.xmlpull.v1.XmlPullParserException -> L4e
            r0.close()     // Catch: java.lang.Throwable -> L8d
            goto L8d
        L47:
            r6 = move-exception
            r2 = r0
            goto L7b
        L4a:
            r4 = r2
            r2 = r0
            r0 = r4
            goto L55
        L4e:
            r4 = r2
            r2 = r0
            r0 = r4
            goto L5d
        L52:
            r6 = move-exception
            goto L7b
        L54:
            r0 = r2
        L55:
            if (r2 == 0) goto L5a
            r2.close()     // Catch: java.lang.Throwable -> L5a
        L5a:
            r2 = r0
            goto L8d
        L5c:
            r0 = r2
        L5d:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L81
            r3.<init>(r6)     // Catch: java.lang.Throwable -> L74 java.lang.Exception -> L81
            int r2 = r3.available()     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L72
            byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L72
            r3.read(r2)     // Catch: java.lang.Throwable -> L6f java.lang.Exception -> L72
            r3.close()     // Catch: java.lang.Throwable -> L87
            goto L87
        L6f:
            r6 = move-exception
            r2 = r3
            goto L75
        L72:
            r2 = r3
            goto L81
        L74:
            r6 = move-exception
        L75:
            if (r2 == 0) goto L7a
            r2.close()     // Catch: java.lang.Throwable -> L7a
        L7a:
            throw r6     // Catch: java.lang.Throwable -> L52
        L7b:
            if (r2 == 0) goto L80
            r2.close()     // Catch: java.lang.Throwable -> L80
        L80:
            throw r6
        L81:
            if (r2 == 0) goto L86
            r2.close()     // Catch: java.lang.Throwable -> L86
        L86:
            r3 = r2
        L87:
            if (r3 == 0) goto L5a
            r3.close()     // Catch: java.lang.Throwable -> L5a
            goto L5a
        L8d:
            java.lang.Object r3 = com.ta.utdid2.b.a.d.f17243b
            monitor-enter(r3)
            if (r1 == 0) goto L96
            r1.a(r2)     // Catch: java.lang.Throwable -> Lad
            goto Lab
        L96:
            java.util.HashMap<java.io.File, com.ta.utdid2.b.a.d$a> r0 = r5.f86a     // Catch: java.lang.Throwable -> Lad
            java.lang.Object r0 = r0.get(r6)     // Catch: java.lang.Throwable -> Lad
            r1 = r0
            com.ta.utdid2.b.a.d$a r1 = (com.ta.utdid2.b.a.d.a) r1     // Catch: java.lang.Throwable -> Lad
            if (r1 != 0) goto Lab
            com.ta.utdid2.b.a.d$a r1 = new com.ta.utdid2.b.a.d$a     // Catch: java.lang.Throwable -> Lad
            r1.<init>(r6, r7, r2)     // Catch: java.lang.Throwable -> Lad
            java.util.HashMap<java.io.File, com.ta.utdid2.b.a.d$a> r7 = r5.f86a     // Catch: java.lang.Throwable -> Lad
            r7.put(r6, r1)     // Catch: java.lang.Throwable -> Lad
        Lab:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lad
            return r1
        Lad:
            r6 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> Lad
            throw r6
        Lb0:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lb0
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.b.a.d.a(java.lang.String, int):com.ta.utdid2.b.a.b");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File a(File file) {
        return new File(file.getPath() + ".bak");
    }
}
