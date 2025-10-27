package com.tencent.tbs.one.impl.e;

import android.content.SharedPreferences;
import android.os.Bundle;
import com.tencent.tbs.one.impl.common.d;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes6.dex */
public final class j extends com.tencent.tbs.one.impl.a.b<Void> {

    /* renamed from: b, reason: collision with root package name */
    public i f22246b;

    /* renamed from: c, reason: collision with root package name */
    public Bundle f22247c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f22248d;

    /* renamed from: e, reason: collision with root package name */
    public Map<String, com.tencent.tbs.one.impl.a.b> f22249e = new ConcurrentHashMap();

    public j(i iVar, Bundle bundle) {
        this.f22246b = iVar;
        this.f22247c = bundle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Bundle bundle, com.tencent.tbs.one.impl.common.d dVar) {
        String[] strArr;
        int i2;
        final String str = this.f22246b.f22205b;
        this.f22249e.remove("DEPS");
        if (dVar.f21989a <= this.f22246b.f22223q.getInt("in_use_deps_version", -1)) {
            a(506, "Failed to request more recent DEPS", (Throwable) null);
            return;
        }
        String[] strArrL = this.f22246b.l();
        if (strArrL == null || strArrL.length <= 0) {
            a(507, "No in-use components", (Throwable) null);
            return;
        }
        int length = strArrL.length;
        char c3 = 0;
        Bundle bundle2 = bundle;
        int i3 = 0;
        int i4 = 0;
        while (i4 < length) {
            final String str2 = strArrL[i4];
            d.a aVarB = dVar.b(str2);
            if (aVarB == null) {
                Object[] objArr = new Object[1];
                objArr[c3] = str2;
                com.tencent.tbs.one.impl.a.g.b("The component %s is no longer valid", objArr);
                strArr = strArrL;
                i2 = length;
            } else {
                int i5 = i3 + 1;
                final String str3 = aVarB.f21992a;
                final int i6 = aVarB.f21994c;
                Object[] objArr2 = new Object[3];
                objArr2[c3] = str;
                objArr2[1] = str3;
                objArr2[2] = Integer.valueOf(i6);
                com.tencent.tbs.one.impl.a.g.a("[%s] Updating component %s#%d", objArr2);
                if (bundle2 == null) {
                    bundle2 = new Bundle();
                }
                Bundle bundle3 = bundle2;
                bundle3.putInt("info_from", 1);
                strArr = strArrL;
                i2 = length;
                this.f22249e.put(str3, this.f22246b.a(bundle3, aVarB, new com.tencent.tbs.one.impl.common.c<e<File>>() { // from class: com.tencent.tbs.one.impl.e.j.2
                    @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                    public final void a(int i7, String str4, Throwable th) {
                        if (i7 == 322) {
                            i iVar = j.this.f22246b;
                            String str5 = str2;
                            com.tencent.tbs.one.impl.a.g.c("deleteInUseComponentNames", str5);
                            HashSet hashSet = new HashSet(Arrays.asList(iVar.l()));
                            hashSet.remove(str5);
                            SharedPreferences.Editor editorEdit = iVar.f22223q.edit();
                            editorEdit.putStringSet("in_use_component_names", hashSet);
                            editorEdit.apply();
                        }
                        com.tencent.tbs.one.impl.a.g.c("[%s] Failed to update component %s#%d, error: [%d] %s", str, str3, Integer.valueOf(i6), Integer.valueOf(i7), str4, th);
                        j.this.a(i7, "Failed to update component " + str3 + ", error: " + str4, th);
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                    public final /* synthetic */ void a(Object obj) {
                        e eVar = (e) obj;
                        com.tencent.tbs.one.impl.a.g.a("[%s] Finished updating component at %s from %s", str, ((File) eVar.f22177b).getAbsolutePath(), eVar.f22176a);
                        j.a(j.this, str3);
                    }
                }));
                String[] strArr2 = aVarB.f21997f;
                if (strArr2 != null) {
                    for (final String str4 : strArr2) {
                        this.f22249e.put(str4, this.f22246b.a(bundle3, dVar.b(str4), new com.tencent.tbs.one.impl.common.c<e<File>>() { // from class: com.tencent.tbs.one.impl.e.j.3
                            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                            public final void a(int i7, String str5, Throwable th) {
                                com.tencent.tbs.one.impl.a.g.c("[%s] Failed to update component %s#%d, error: [%d] %s", str, str4, Integer.valueOf(i6), Integer.valueOf(i7), str5, th);
                                j.this.a(i7, "Failed to update component " + str4 + ", error: " + str5, th);
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            @Override // com.tencent.tbs.one.impl.common.c, com.tencent.tbs.one.impl.a.m
                            public final /* synthetic */ void a(Object obj) {
                                e eVar = (e) obj;
                                com.tencent.tbs.one.impl.a.g.a("[%s] Finished updating component at %s from %s", str, ((File) eVar.f22177b).getAbsolutePath(), eVar.f22176a);
                                j.a(j.this, str4);
                            }
                        }));
                    }
                }
                bundle2 = bundle3;
                i3 = i5;
            }
            i4++;
            strArrL = strArr;
            length = i2;
            c3 = 0;
        }
        if (i3 <= 0) {
            a(508, "No valid components", (Throwable) null);
        }
    }

    public static /* synthetic */ void a(j jVar, String str) {
        jVar.f22249e.remove(str);
        if (jVar.f22248d || jVar.f22249e.size() > 0) {
            return;
        }
        com.tencent.tbs.one.impl.a.g.a("[%s] Finished updating", jVar.f22246b.f22205b);
        jVar.a((j) null);
    }

    private void c() {
        Iterator<com.tencent.tbs.one.impl.a.b> it = this.f22249e.values().iterator();
        while (it.hasNext()) {
            it.next().b();
        }
        this.f22249e.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b1  */
    @Override // com.tencent.tbs.one.impl.a.b
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 242
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.e.j.a():void");
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void a(int i2, String str, Throwable th) {
        com.tencent.tbs.one.impl.a.g.b("Failed to update, error: [%d] %s", Integer.valueOf(i2), str);
        this.f22248d = true;
        c();
        File fileC = com.tencent.tbs.one.impl.common.f.c(this.f22246b.f22206c);
        if (fileC.exists()) {
            com.tencent.tbs.one.impl.a.d.c(fileC);
        }
        super.a(i2, str, th);
    }

    @Override // com.tencent.tbs.one.impl.a.b
    public final void b() {
        super.b();
        c();
    }
}
