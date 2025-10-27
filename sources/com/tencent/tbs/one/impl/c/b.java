package com.tencent.tbs.one.impl.c;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import cn.hutool.core.text.StrPool;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.TBSOneDelegate;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.TBSOneTiming;
import com.tencent.tbs.one.impl.a.a.c;
import com.tencent.tbs.one.impl.a.a.d;
import com.tencent.tbs.one.impl.a.g;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.common.e;
import com.tencent.tbs.one.impl.common.f;
import com.tencent.tbs.one.impl.e.h;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
public final class b implements d.a {

    /* renamed from: a, reason: collision with root package name */
    public final String f21885a;

    /* renamed from: b, reason: collision with root package name */
    public final h f21886b;

    /* renamed from: d, reason: collision with root package name */
    public boolean f21888d;

    /* renamed from: e, reason: collision with root package name */
    public boolean f21889e;

    /* renamed from: f, reason: collision with root package name */
    public d.a f21890f;

    /* renamed from: g, reason: collision with root package name */
    public int f21891g;

    /* renamed from: h, reason: collision with root package name */
    public String f21892h;

    /* renamed from: i, reason: collision with root package name */
    public File f21893i;

    /* renamed from: j, reason: collision with root package name */
    public a f21894j;

    /* renamed from: k, reason: collision with root package name */
    public WeakReference<com.tencent.tbs.one.impl.a.b> f21895k;

    /* renamed from: m, reason: collision with root package name */
    public e f21897m;

    /* renamed from: n, reason: collision with root package name */
    public Map<String, ClassLoader> f21898n;

    /* renamed from: o, reason: collision with root package name */
    public int f21899o;

    /* renamed from: p, reason: collision with root package name */
    public int f21900p;

    /* renamed from: l, reason: collision with root package name */
    public final ArrayList<TBSOneCallback<File>> f21896l = new ArrayList<>();

    /* renamed from: c, reason: collision with root package name */
    public final ArrayList<TBSOneCallback<TBSOneComponent>> f21887c = new ArrayList<>();

    /* renamed from: com.tencent.tbs.one.impl.c.b$2, reason: invalid class name */
    public class AnonymousClass2 extends com.tencent.tbs.one.impl.a.a.b {

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ Bundle f21903d;

        /* renamed from: e, reason: collision with root package name */
        public final /* synthetic */ d.a f21904e;

        /* renamed from: f, reason: collision with root package name */
        public final /* synthetic */ float f21905f;

        /* renamed from: g, reason: collision with root package name */
        public final /* synthetic */ String f21906g;

        /* renamed from: h, reason: collision with root package name */
        public final /* synthetic */ String f21907h;

        public AnonymousClass2(Bundle bundle, d.a aVar, float f2, String str, String str2) {
            this.f21903d = bundle;
            this.f21904e = aVar;
            this.f21905f = f2;
            this.f21906g = str;
            this.f21907h = str2;
        }

        @Override // com.tencent.tbs.one.impl.a.a.b
        public final void a(final c cVar) {
            o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.c.b.2.1
                @Override // java.lang.Runnable
                public final void run() {
                    AnonymousClass2 anonymousClass2 = AnonymousClass2.this;
                    b bVar = b.this;
                    if (bVar.f21889e) {
                        cVar.a(104, "Aborted", null);
                        return;
                    }
                    com.tencent.tbs.one.impl.a.b<com.tencent.tbs.one.impl.e.e<File>> bVarA = bVar.f21886b.a(anonymousClass2.f21903d, anonymousClass2.f21904e, new m<com.tencent.tbs.one.impl.e.e<File>>() { // from class: com.tencent.tbs.one.impl.c.b.2.1.1
                        @Override // com.tencent.tbs.one.impl.a.m
                        public final void a(int i2, int i3) {
                            AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                            b.a(b.this, i2, i3, anonymousClass22.f21905f);
                        }

                        @Override // com.tencent.tbs.one.impl.a.m
                        public final void a(int i2, String str, Throwable th) {
                            cVar.a(i2, str, th);
                        }

                        @Override // com.tencent.tbs.one.impl.a.m
                        public final /* synthetic */ void a(com.tencent.tbs.one.impl.e.e<File> eVar) {
                            AnonymousClass2 anonymousClass22 = AnonymousClass2.this;
                            g.a("[%s] {%s} Finished installing component itself from source %s", anonymousClass22.f21906g, anonymousClass22.f21907h, eVar.f22176a);
                            cVar.c();
                        }
                    });
                    b.this.f21895k = new WeakReference<>(bVarA);
                }
            });
        }
    }

    /* renamed from: com.tencent.tbs.one.impl.c.b$3, reason: invalid class name */
    public class AnonymousClass3 extends com.tencent.tbs.one.impl.a.a.b {

        /* renamed from: d, reason: collision with root package name */
        public final /* synthetic */ h f21912d;

        /* renamed from: e, reason: collision with root package name */
        public final /* synthetic */ Bundle f21913e;

        /* renamed from: f, reason: collision with root package name */
        public final /* synthetic */ String f21914f;

        /* renamed from: g, reason: collision with root package name */
        public final /* synthetic */ float f21915g;

        /* renamed from: h, reason: collision with root package name */
        public final /* synthetic */ String f21916h;

        /* renamed from: i, reason: collision with root package name */
        public final /* synthetic */ String f21917i;

        public AnonymousClass3(h hVar, Bundle bundle, String str, float f2, String str2, String str3) {
            this.f21912d = hVar;
            this.f21913e = bundle;
            this.f21914f = str;
            this.f21915g = f2;
            this.f21916h = str2;
            this.f21917i = str3;
        }

        @Override // com.tencent.tbs.one.impl.a.a.b
        public final void a(final c cVar) {
            o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.c.b.3.1
                @Override // java.lang.Runnable
                public final void run() {
                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                    if (b.this.f21889e) {
                        cVar.a(104, "Aborted", null);
                    } else {
                        anonymousClass3.f21912d.a(anonymousClass3.f21913e, anonymousClass3.f21914f, new TBSOneCallback<File>() { // from class: com.tencent.tbs.one.impl.c.b.3.1.1
                            @Override // com.tencent.tbs.one.TBSOneCallback
                            public final /* synthetic */ void onCompleted(File file) {
                                AnonymousClass3 anonymousClass32 = AnonymousClass3.this;
                                g.a("[%s] {%s} Finished installing dependency %s", anonymousClass32.f21916h, anonymousClass32.f21917i, anonymousClass32.f21914f);
                                cVar.c();
                            }

                            @Override // com.tencent.tbs.one.TBSOneCallback
                            public final void onError(int i2, String str) {
                                cVar.a(i2, "Failed to install dependency " + AnonymousClass3.this.f21914f + ", error: " + str, null);
                            }

                            @Override // com.tencent.tbs.one.TBSOneCallback
                            public final void onProgressChanged(int i2, int i3) {
                                AnonymousClass3 anonymousClass32 = AnonymousClass3.this;
                                b.a(b.this, i2, i3, anonymousClass32.f21915g);
                            }
                        });
                    }
                }
            });
        }
    }

    public b(h hVar, String str) {
        this.f21885a = str;
        this.f21886b = hVar;
    }

    private File a(String str) {
        String strShouldOverrideFilePath;
        h hVar = this.f21886b;
        TBSOneDelegate tBSOneDelegate = hVar.f22217n;
        if (tBSOneDelegate != null && (strShouldOverrideFilePath = tBSOneDelegate.shouldOverrideFilePath(hVar.f22205b, this.f21885a, this.f21890f.f21994c, str)) != null) {
            return new File(strShouldOverrideFilePath);
        }
        File file = new File(this.f21893i, str);
        if (!file.exists()) {
            g.b("Failed to find component file %s in installation directory", file.getAbsolutePath());
        }
        return file;
    }

    private ClassLoader a(Context context, String[] strArr) throws TBSOneException {
        ClassLoader classLoader;
        ClassLoader classLoader2;
        String str = this.f21886b.f22205b;
        String str2 = this.f21885a;
        Map<String, ClassLoader> map = this.f21898n;
        if (map != null && (classLoader2 = map.get(strArr[0])) != null) {
            g.a("[%s] {%s} Reusing class loader %s %s", str, str2, strArr, classLoader2);
            return classLoader2;
        }
        File[] fileArr = new File[strArr.length];
        for (int i2 = 0; i2 < strArr.length; i2++) {
            fileArr[i2] = a(strArr[i2]);
        }
        File file = fileArr[0];
        if (!file.exists()) {
            throw new TBSOneException(105, "Failed to find dex file " + file.getAbsolutePath());
        }
        g.a("[%s] {%s} Creating class loader %s from %s", str, str2, strArr, file.getAbsolutePath());
        if (this.f21897m == null) {
            File fileA = a("MANIFEST");
            if (fileA.exists()) {
                g.a("[%s] {%s} Initializing component according to MANIFEST file %s", str, str2, fileA.getAbsolutePath());
                try {
                    this.f21897m = e.a(fileA);
                } catch (TBSOneException e2) {
                    b(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                    return null;
                }
            }
        }
        e eVar = this.f21897m;
        e.a aVarA = eVar == null ? null : eVar.a(strArr[0]);
        h hVar = this.f21886b;
        String str3 = this.f21885a + StrPool.DOT + TBSOneConfigurationKeys.PARENT_CLASSLOADER;
        Object obj = hVar.f22208e.containsKey(str3) ? hVar.f22208e.get(str3) : hVar.f22208e.containsKey(TBSOneConfigurationKeys.PARENT_CLASSLOADER) ? hVar.f22208e.get(TBSOneConfigurationKeys.PARENT_CLASSLOADER) : null;
        if (obj instanceof ClassLoader) {
            classLoader = (ClassLoader) obj;
        } else {
            e.b bVar = aVarA == null ? null : aVarA.f22009d;
            if (bVar != null) {
                String str4 = bVar.f22011a;
                String[] strArr2 = {bVar.f22012b};
                if (TextUtils.isEmpty(str4)) {
                    classLoader = a(context, strArr2);
                } else {
                    b bVarE = this.f21886b.e(str4);
                    if (bVarE == null) {
                        throw new TBSOneException(404, "Failed to get loaded dependency " + str4);
                    }
                    classLoader = bVarE.a(context, strArr2);
                }
            } else {
                classLoader = this.f21886b.f22204a.getClassLoader();
            }
        }
        ClassLoader classLoader3 = classLoader;
        String strB = b(file.getAbsolutePath());
        boolean zA = this.f21886b.a(str2, TBSOneConfigurationKeys.DISABLE_SEALED_CLASSLOADER);
        g.a("[%s] {%s} isSealedClassLoaderDisabled [%s] by Configuration", str, str2, Boolean.valueOf(zA));
        e eVar2 = this.f21897m;
        if (eVar2 != null && eVar2.f22005h && Build.VERSION.SDK_INT >= 29) {
            g.a("[%s] {%s} isSealedClassLoaderDisabled set true by manifest", str, str2);
            zA = true;
        }
        boolean zA2 = this.f21886b.a(str2, TBSOneConfigurationKeys.ENABLE_ASYNC_DEX_OPTIMIZATION);
        long jCurrentTimeMillis = System.currentTimeMillis();
        ClassLoader classLoaderA = com.tencent.tbs.one.impl.c.a.c.a(context, fileArr, this.f21893i.getAbsolutePath(), strB, classLoader3, zA, aVarA == null ? null : aVarA.f22008c, zA2);
        g.a("[%s] {%s} Created dex class loader %s %s cost %dms", str, str2, strArr, classLoaderA, Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
        if (this.f21898n == null) {
            this.f21898n = new HashMap();
        }
        this.f21898n.put(strArr[0], classLoaderA);
        TBSOneDelegate tBSOneDelegate = this.f21886b.f22217n;
        if (tBSOneDelegate != null) {
            tBSOneDelegate.onDexLoaded(str, str2, this.f21890f.f21994c, file.getAbsolutePath(), classLoaderA);
        }
        return classLoaderA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        String str = this.f21886b.f22205b;
        int i3 = this.f21899o;
        if (i2 - i3 > 2) {
            this.f21899o = i2;
            TBSOneCallback[] tBSOneCallbackArr = (TBSOneCallback[]) this.f21896l.toArray(new TBSOneCallback[0]);
            TBSOneCallback[] tBSOneCallbackArr2 = (TBSOneCallback[]) this.f21887c.toArray(new TBSOneCallback[0]);
            for (TBSOneCallback tBSOneCallback : tBSOneCallbackArr) {
                tBSOneCallback.onProgressChanged(i3, i2);
            }
            for (TBSOneCallback tBSOneCallback2 : tBSOneCallbackArr2) {
                tBSOneCallback2.onProgressChanged(i3, i2);
            }
        }
    }

    private void a(final Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        String str = this.f21886b.f22205b;
        String str2 = this.f21885a;
        if (this.f21888d) {
            g.a("[%s] {%s} Have started loading component", str, str2);
            return;
        }
        g.a("[%s] {%s} Loading DEPS", str, str2);
        this.f21888d = true;
        this.f21889e = false;
        if (bundle.containsKey("deps")) {
            com.tencent.tbs.one.impl.common.d dVar = (com.tencent.tbs.one.impl.common.d) bundle.getSerializable("deps");
            if (dVar != null) {
                a(bundle, dVar);
                return;
            }
            return;
        }
        final h hVar = this.f21886b;
        com.tencent.tbs.one.impl.common.c<com.tencent.tbs.one.impl.common.d> cVar = new com.tencent.tbs.one.impl.common.c<com.tencent.tbs.one.impl.common.d>() { // from class: com.tencent.tbs.one.impl.c.b.1
            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
            public final void a(int i2, int i3) {
                b.this.a(i3, 0, 20);
            }

            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
            public final void a(int i2, String str3, Throwable th) {
                b.this.b(i2, str3, th);
            }

            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
            public final /* bridge */ /* synthetic */ void a(Object obj) {
                b.this.a(bundle, (com.tencent.tbs.one.impl.common.d) obj);
            }
        };
        String str3 = hVar.f22205b;
        g.a("[%s] Loading DEPS", str3);
        com.tencent.tbs.one.impl.common.d dVar2 = hVar.f22218o;
        if (dVar2 != null) {
            g.a("[%s] Loaded DEPS#%d", str3, Integer.valueOf(dVar2.f21989a));
            cVar.a(hVar.f22218o);
        } else {
            if (hVar.f22219p == null) {
                hVar.f22219p = new com.tencent.tbs.one.impl.a.b<com.tencent.tbs.one.impl.common.d>() { // from class: com.tencent.tbs.one.impl.e.h.1

                    /* renamed from: b */
                    public final /* synthetic */ Bundle f22220b;

                    /* renamed from: com.tencent.tbs.one.impl.e.h$1$1 */
                    public class C03701 extends com.tencent.tbs.one.impl.common.c<e<com.tencent.tbs.one.impl.common.d>> {
                        public C03701() {
                        }

                        @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                        public final void a(int i2, int i3) {
                            AnonymousClass1.this.a(i3);
                        }

                        @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                        public final void a(int i2, String str, Throwable th) {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            h.this.f22219p = null;
                            anonymousClass1.a(i2, str, th);
                        }

                        @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                        public final /* bridge */ /* synthetic */ void a(Object obj) {
                            e<com.tencent.tbs.one.impl.common.d> eVar = (e) obj;
                            h hVar = h.this;
                            hVar.f22219p = null;
                            hVar.a(eVar);
                            AnonymousClass1.this.a((AnonymousClass1) eVar.f22177b);
                        }
                    }

                    public AnonymousClass1(final Bundle bundle2) {
                        bundle = bundle2;
                    }

                    @Override // com.tencent.tbs.one.impl.a.b
                    public final void a() {
                        h.this.a(bundle, new com.tencent.tbs.one.impl.common.c<e<com.tencent.tbs.one.impl.common.d>>() { // from class: com.tencent.tbs.one.impl.e.h.1.1
                            public C03701() {
                            }

                            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                            public final void a(int i2, int i3) {
                                AnonymousClass1.this.a(i3);
                            }

                            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                            public final void a(int i2, String str4, Throwable th) {
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                h.this.f22219p = null;
                                anonymousClass1.a(i2, str4, th);
                            }

                            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                            public final /* bridge */ /* synthetic */ void a(Object obj) {
                                e<com.tencent.tbs.one.impl.common.d> eVar = (e) obj;
                                h hVar2 = h.this;
                                hVar2.f22219p = null;
                                hVar2.a(eVar);
                                AnonymousClass1.this.a((AnonymousClass1) eVar.f22177b);
                            }
                        });
                    }
                };
            }
            hVar.f22219p.a(cVar);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle, com.tencent.tbs.one.impl.common.d dVar) {
        int i2 = 0;
        g.a("ComponentLoader onDEPSLoaded", new Object[0]);
        if (this.f21889e) {
            b(104, "Aborted", null);
            return;
        }
        a(20);
        String str = this.f21886b.f22205b;
        int i3 = dVar.f21989a;
        String str2 = this.f21885a;
        g.a("[%s] {%s} Finished loading DEPS#%d", str, str2, Integer.valueOf(i3));
        d.a aVarB = dVar.b(str2);
        if (aVarB == null) {
            b(309, "Failed to get info for component " + str2, null);
            return;
        }
        String[] strArr = aVarB.f21997f;
        float length = 1.0f;
        if (strArr != null) {
            for (String str3 : strArr) {
                if (dVar.b(str3) == null) {
                    b(310, "Failed to get info for dependency " + str3, null);
                    return;
                }
            }
            length = 1.0f / (strArr.length + 1);
        }
        float f2 = length;
        this.f21890f = aVarB;
        this.f21900p = 0;
        Object[] objArr = new Object[3];
        objArr[0] = str;
        objArr[1] = str2;
        objArr[2] = strArr == null ? "" : TextUtils.join(", ", strArr);
        g.a("[%s] {%s} Installing component and dependencies [%s]", objArr);
        com.tencent.tbs.one.impl.a.a.a aVar = new com.tencent.tbs.one.impl.a.a.a();
        aVar.b(new AnonymousClass2(bundle, aVarB, f2, str, str2));
        h hVar = this.f21886b;
        if (strArr != null) {
            int length2 = strArr.length;
            while (i2 < length2) {
                aVar.b(new AnonymousClass3(hVar, bundle, strArr[i2], f2, str, str2));
                i2++;
                strArr = strArr;
            }
        }
        com.tencent.tbs.one.impl.a.a.e eVar = new com.tencent.tbs.one.impl.a.a.e(AsyncTask.THREAD_POOL_EXECUTOR);
        eVar.f21718a = this;
        eVar.a(aVar);
    }

    private void a(a aVar) {
        g.a("ComponentLoader finishLoading  " + aVar.toString(), new Object[0]);
        g.a("[%s] {%s} Finished loading component %s", this.f21886b.f22205b, this.f21885a, aVar);
        this.f21894j = aVar;
        int i2 = this.f21899o;
        this.f21899o = 100;
        TBSOneCallback[] tBSOneCallbackArr = (TBSOneCallback[]) this.f21887c.toArray(new TBSOneCallback[0]);
        this.f21887c.clear();
        for (TBSOneCallback tBSOneCallback : tBSOneCallbackArr) {
            tBSOneCallback.onProgressChanged(i2, 100);
            tBSOneCallback.onCompleted(aVar);
        }
    }

    public static /* synthetic */ void a(b bVar, final int i2, final int i3, final float f2) {
        if (!o.b()) {
            o.b(new Runnable() { // from class: com.tencent.tbs.one.impl.c.b.6
                @Override // java.lang.Runnable
                public final void run() {
                    b.a(b.this, i2, i3, f2);
                }
            });
            return;
        }
        int iMin = Math.min(Math.max(0, i2), 100);
        int iMin2 = (int) (((Math.min(Math.max(0, i3), 100) - iMin) * f2) + bVar.f21900p);
        String str = bVar.f21886b.f22205b;
        bVar.f21900p = iMin2;
        bVar.a(iMin2, 20, 90);
    }

    private String b(String str) {
        String strShouldOverrideLibrarySearchPath;
        String strJoin = TextUtils.join(File.pathSeparator, d());
        g.a("[%s] {%s} Collected librarySearchPath %s", this.f21886b.f22205b, this.f21885a, strJoin);
        h hVar = this.f21886b;
        TBSOneDelegate tBSOneDelegate = hVar.f22217n;
        return (tBSOneDelegate == null || (strShouldOverrideLibrarySearchPath = tBSOneDelegate.shouldOverrideLibrarySearchPath(hVar.f22205b, this.f21885a, this.f21890f.f21994c, str, strJoin)) == null) ? strJoin : strShouldOverrideLibrarySearchPath;
    }

    private Set<String> d() {
        String[] strArr;
        HashSet hashSet = new HashSet();
        File file = this.f21893i;
        if (file != null) {
            hashSet.add(file.getAbsolutePath());
        }
        d.a aVar = this.f21890f;
        if (aVar != null && (strArr = aVar.f21997f) != null) {
            for (String str : strArr) {
                hashSet.addAll(this.f21886b.e(str).d());
            }
        }
        return hashSet;
    }

    @Override // com.tencent.tbs.one.impl.a.a.d.a
    public final void a() {
        final String str = this.f21885a;
        g.a("[%s] {%s} Finished installing component and dependencies", this.f21886b.f22205b, str);
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.c.b.5
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                b bVar = b.this;
                if (bVar.f21890f != null) {
                    bVar.a(90);
                    b bVar2 = b.this;
                    b.this.a(bVar2.f21886b.a(str, bVar2.f21890f.f21994c));
                    if (b.this.f21887c.size() > 0) {
                        b.this.c();
                        return;
                    }
                    return;
                }
                g.c("[%s] {%s} Unknown error, finished installing component and dependencies but config is null, last error: [%d] %s", bVar.f21886b.f22205b, bVar.f21885a, Integer.valueOf(bVar.f21891g), b.this.f21892h);
                com.tencent.tbs.one.impl.common.d dVar = b.this.f21886b.f22218o;
                Statistics.create(Statistics.EVENT_ERROR, 101).setDEPSVersion(dVar != null ? dVar.f21989a : -1).setComponentName(b.this.f21885a).report();
                g.c("ComponentConfig is null, last error: [" + b.this.f21891g + "] " + b.this.f21892h, new Object[0]);
            }
        });
    }

    public final void a(int i2, int i3, int i4) {
        a((int) (((Math.min(Math.max(0, i2), 100) / 100.0f) * (i4 - i3)) + i3));
    }

    @Override // com.tencent.tbs.one.impl.a.a.d.a
    public final void a(final int i2, final String str, final Throwable th) {
        g.a("ComponentLoader onError errorCode is " + i2, new Object[0]);
        o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.c.b.4
            @Override // java.lang.Runnable
            public final void run() {
                b.this.b(i2, str, th);
            }
        });
    }

    public final void a(Bundle bundle, TBSOneCallback<File> tBSOneCallback) {
        File file = this.f21893i;
        if (file == null) {
            if (tBSOneCallback != null) {
                tBSOneCallback.onProgressChanged(0, this.f21899o);
                this.f21896l.add(tBSOneCallback);
            }
            a(bundle);
            return;
        }
        g.a("[%s] {%s} Installed component at %s", this.f21886b.f22205b, this.f21885a, file);
        if (tBSOneCallback != null) {
            tBSOneCallback.onProgressChanged(0, 100);
            tBSOneCallback.onCompleted(this.f21893i);
        }
    }

    public final void a(File file) {
        g.a("[%s] {%s} Finished installing component at %s", this.f21886b.f22205b, this.f21885a, file.getAbsolutePath());
        this.f21893i = file;
        TBSOneCallback[] tBSOneCallbackArr = (TBSOneCallback[]) this.f21896l.toArray(new TBSOneCallback[0]);
        this.f21896l.clear();
        for (TBSOneCallback tBSOneCallback : tBSOneCallbackArr) {
            tBSOneCallback.onProgressChanged(this.f21899o, 100);
            tBSOneCallback.onCompleted(file);
        }
    }

    public final void b() {
        String[] strArr;
        com.tencent.tbs.one.impl.a.b bVar;
        this.f21889e = true;
        WeakReference<com.tencent.tbs.one.impl.a.b> weakReference = this.f21895k;
        if (weakReference != null && (bVar = weakReference.get()) != null) {
            bVar.b();
        }
        d.a aVar = this.f21890f;
        if (aVar == null || (strArr = aVar.f21997f) == null) {
            return;
        }
        for (String str : strArr) {
            this.f21886b.d(str);
        }
    }

    public final void b(int i2, String str, Throwable th) {
        boolean z2 = this.f21886b.f22212i;
        g.a("ComponentLoader fail errorCode is " + i2 + " needUpdate is " + z2, new Object[0]);
        String str2 = this.f21885a;
        g.b("Failed to install or load component %s, error: [%d] %s", str2, Integer.valueOf(i2), str);
        g.c("[%s] {%s} Failed to install or load component, error: [%d] %s", this.f21886b.f22205b, str2, Integer.valueOf(i2), str, th);
        if (z2) {
            com.tencent.tbs.one.impl.common.d dVar = this.f21886b.f22218o;
            int i3 = dVar != null ? dVar.f21989a : -1;
            d.a aVar = this.f21890f;
            Statistics.Builder componentVersion = Statistics.create(Statistics.EVENT_ERROR, i2).setDEPSVersion(i3).setComponentName(this.f21885a).setComponentVersion(aVar != null ? aVar.f21994c : -1);
            h hVar = this.f21886b;
            Statistics.Builder componentLocalVersion = componentVersion.setComponentLocalVersion(f.a(hVar.f22204a, hVar.f22205b, this.f21885a));
            h hVar2 = this.f21886b;
            componentLocalVersion.setDEPSVersion(f.a(hVar2.f22204a, hVar2.f22205b)).report();
            g.c("Failed to load component code : " + i2 + ", detail: " + str, th);
            h hVar3 = this.f21886b;
            hVar3.f22215l = true;
            hVar3.f22216m = true;
            hVar3.e();
        }
        this.f21891g = i2;
        this.f21892h = str;
        this.f21890f = null;
        this.f21897m = null;
        this.f21893i = null;
        this.f21894j = null;
        this.f21899o = 0;
        this.f21888d = false;
        TBSOneCallback[] tBSOneCallbackArr = (TBSOneCallback[]) this.f21896l.toArray(new TBSOneCallback[0]);
        this.f21896l.clear();
        TBSOneCallback[] tBSOneCallbackArr2 = (TBSOneCallback[]) this.f21887c.toArray(new TBSOneCallback[0]);
        this.f21887c.clear();
        for (TBSOneCallback tBSOneCallback : tBSOneCallbackArr) {
            tBSOneCallback.onError(i2, str);
        }
        for (TBSOneCallback tBSOneCallback2 : tBSOneCallbackArr2) {
            tBSOneCallback2.onError(i2, str);
        }
    }

    public final void b(Bundle bundle, TBSOneCallback<TBSOneComponent> tBSOneCallback) throws Throwable {
        a aVar = this.f21894j;
        if (aVar != null) {
            g.a("[%s] {%s} Loaded component %s", this.f21886b.f22205b, this.f21885a, aVar);
            if (tBSOneCallback != null) {
                tBSOneCallback.onProgressChanged(0, 100);
                tBSOneCallback.onCompleted(this.f21894j);
                return;
            }
            return;
        }
        if (tBSOneCallback != null) {
            tBSOneCallback.onProgressChanged(0, this.f21899o);
            this.f21887c.add(tBSOneCallback);
        }
        if (this.f21893i != null) {
            c();
        } else {
            a(bundle);
        }
    }

    public final void c() throws Throwable {
        char c3;
        com.tencent.tbs.one.impl.c.b.b bVar;
        TBSOneTiming.category(this.f21886b.f22205b).component(this.f21885a).start("initialize");
        String str = this.f21886b.f22205b;
        String str2 = this.f21885a;
        int i2 = this.f21890f.f21994c;
        g.a("[%s] {%s} Initializing component", str, str2);
        a aVar = new a(str2, this.f21890f.f21993b, i2, this.f21893i);
        File fileA = a("MANIFEST");
        if (fileA.exists()) {
            g.a("[%s] {%s} Initializing component according to MANIFEST file %s", str, str2, fileA.getAbsolutePath());
            try {
                e eVarA = e.a(fileA);
                this.f21897m = eVarA;
                Pair<String, String>[] pairArr = eVarA.f22003f;
                if (pairArr != null) {
                    g.a("[%s] {%s} Registering event receivers", str, str2, pairArr);
                    for (Pair<String, String> pair : pairArr) {
                        h hVar = this.f21886b;
                        com.tencent.tbs.one.impl.common.g gVar = new com.tencent.tbs.one.impl.common.g(str2, (String) pair.second, (String) pair.first);
                        String str3 = gVar.f22015c;
                        List<com.tencent.tbs.one.impl.common.g> arrayList = hVar.f22210g.get(str3);
                        if (arrayList == null) {
                            arrayList = new ArrayList<>();
                            hVar.f22210g.put(str3, arrayList);
                        }
                        arrayList.add(gVar);
                    }
                } else {
                    g.a("[%s] {%s} No event receivers", str, str2);
                }
                Context context = this.f21886b.f22204a;
                String str4 = this.f21897m.f21999b;
                if (TextUtils.isEmpty(str4)) {
                    c3 = 0;
                    g.a("[%s] {%s} No resource file", str, str2);
                    bVar = null;
                } else {
                    File fileA2 = a(str4);
                    g.a("[%s] {%s} Creating resource context %s from %s", str, str2, str4, fileA2.getAbsolutePath());
                    if (!fileA2.exists()) {
                        b(105, "Failed to find resource file " + fileA2.getAbsolutePath(), null);
                        return;
                    } else {
                        bVar = new com.tencent.tbs.one.impl.c.b.b(context, fileA2.getAbsolutePath());
                        aVar.f21803a = bVar;
                        c3 = 0;
                    }
                }
                String[] strArr = this.f21897m.f22000c;
                if (strArr == null || strArr.length <= 0 || TextUtils.isEmpty(strArr[c3])) {
                    g.a("[%s] {%s} No entry dex file", str, str2);
                } else {
                    try {
                        ClassLoader classLoaderA = a(context, strArr);
                        aVar.f21804b = classLoaderA;
                        String str5 = this.f21897m.f22001d;
                        if (TextUtils.isEmpty(str5)) {
                            Object[] objArr = new Object[2];
                            objArr[c3] = str;
                            objArr[1] = str2;
                            g.a("[%s] {%s} No entry class", objArr);
                        } else {
                            Object[] objArr2 = new Object[3];
                            objArr2[c3] = str;
                            objArr2[1] = str2;
                            objArr2[2] = str5;
                            g.a("[%s] {%s} Constructing entry object %s", objArr2);
                            HashMap map = new HashMap();
                            map.put("callerContext", context);
                            if (bVar != null) {
                                bVar.f21941a.f21934a = classLoaderA;
                                map.put("resourcesContext", bVar);
                            }
                            map.put("classLoader", classLoaderA);
                            map.put("installationDirectory", this.f21893i);
                            map.put("optimizedDirectory", this.f21893i);
                            map.put("librarySearchPath", b(strArr[0]));
                            map.put("versionName", this.f21890f.f21993b);
                            map.put("versionCode", Integer.valueOf(i2));
                            map.put("eventEmitter", this.f21886b.f22207d);
                            try {
                                g.a("componentLoader initializeComponent try to loadclass " + str5, new Object[0]);
                                aVar.f21805c = classLoaderA.loadClass(str5).getConstructor(Map.class).newInstance(map);
                            } catch (ClassNotFoundException e2) {
                                b(405, "Failed to load entry class " + str5 + " in " + strArr[0] + "\n" + Log.getStackTraceString(e2), null);
                                return;
                            } catch (NoSuchMethodException e3) {
                                b(406, "Failed to find entry class " + str5 + " constructor in " + strArr[0] + "\n" + Log.getStackTraceString(e3), null);
                                return;
                            } catch (Exception e4) {
                                b(407, "Failed to construct the entry object with " + str5 + " in " + strArr[0] + "\n" + Log.getStackTraceString(e4), null);
                                return;
                            }
                        }
                    } catch (TBSOneException e5) {
                        b(e5.getErrorCode(), e5.getMessage(), e5.getCause());
                        return;
                    }
                }
            } catch (TBSOneException e6) {
                b(e6.getErrorCode(), e6.getMessage(), e6.getCause());
                return;
            }
        } else {
            g.a("[%s] {%s} No MANIFEST file", str, str2);
        }
        TBSOneTiming.category(this.f21886b.f22205b).component(this.f21885a).end("initialize");
        a(aVar);
    }
}
