package com.tencent.tbs.one.impl.e;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.TBSOneDebugger;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.TBSOneManager;
import com.tencent.tbs.one.TBSOneOnlineService;
import com.tencent.tbs.one.impl.a.k;
import com.tencent.tbs.one.impl.a.m;
import com.tencent.tbs.one.impl.a.o;
import com.tencent.tbs.one.impl.common.Statistics;
import com.tencent.tbs.one.impl.common.d;
import com.tencent.tbs.one.impl.e.e;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public final class i extends h {

    /* renamed from: q, reason: collision with root package name */
    public SharedPreferences f22223q;

    /* renamed from: r, reason: collision with root package name */
    public g f22224r;

    /* renamed from: s, reason: collision with root package name */
    public final Object f22225s;

    /* renamed from: t, reason: collision with root package name */
    public final Object f22226t;

    /* renamed from: u, reason: collision with root package name */
    public Set<String> f22227u;

    /* renamed from: v, reason: collision with root package name */
    public Set<String> f22228v;

    /* renamed from: w, reason: collision with root package name */
    public boolean f22229w;

    /* renamed from: x, reason: collision with root package name */
    public final Runnable f22230x;

    /* renamed from: y, reason: collision with root package name */
    public com.tencent.tbs.one.impl.b.b f22231y;

    /* renamed from: com.tencent.tbs.one.impl.e.i$6, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass6 {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int[] f22245a;

        static {
            int[] iArr = new int[TBSOneManager.Policy.values().length];
            f22245a = iArr;
            try {
                iArr[TBSOneManager.Policy.BUILTIN_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f22245a[TBSOneManager.Policy.BUILTIN_ASSETS_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f22245a[TBSOneManager.Policy.LOCAL_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f22245a[TBSOneManager.Policy.ONLINE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f22245a[TBSOneManager.Policy.AUTO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public i(Context context, String str) {
        super(context, str);
        this.f22225s = new Object();
        this.f22226t = new Object();
        this.f22229w = false;
        this.f22230x = new Runnable() { // from class: com.tencent.tbs.one.impl.e.i.1
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                i iVar = i.this;
                String str2 = iVar.f22205b;
                g gVar = iVar.f22224r;
                if (gVar != null) {
                    if (gVar.f22192a != null) {
                        com.tencent.tbs.one.impl.a.g.a("[%s] %s in updating", str2, "Early out for idle task,");
                        i.this.j();
                        return;
                    }
                }
                for (com.tencent.tbs.one.impl.c.b bVar : iVar.f22209f.values()) {
                    if (bVar.f21888d && bVar.f21893i == null) {
                        com.tencent.tbs.one.impl.a.g.a("[%s] %s in loading component", str2, "Early out for idle task,");
                        i.this.j();
                        return;
                    }
                }
                f.a(i.this.f22204a);
                f.d(i.this.f22206c);
                if (!i.this.f22212i) {
                    com.tencent.tbs.one.impl.a.g.a("[%s] %s disabled", str2, "Early out for auto update,");
                } else if (i.this.b()) {
                    i.this.e();
                } else {
                    com.tencent.tbs.one.impl.a.g.a("[%s] %s not use online service", str2, "Early out for auto update,");
                }
            }
        };
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.format("com.tencent.tbs.one.%s.prefs", str), 4);
        this.f22223q = sharedPreferences;
        if (!sharedPreferences.contains("last_update_time")) {
            k();
        }
        this.f22227u = this.f22223q.getStringSet("disabled_component_names", new HashSet());
        this.f22228v = this.f22223q.getStringSet("disabled_local_components", new HashSet());
        com.tencent.tbs.one.impl.a.g.a("[%s] Disabled components {%s} from preferences", str, TextUtils.join(", ", this.f22227u));
        j();
    }

    private com.tencent.tbs.one.impl.a.b<e<File>> a(TBSOneManager.Policy policy, d.a aVar, final File file, Bundle bundle) {
        final Context context = this.f22204a;
        String str = this.f22205b;
        int i2 = AnonymousClass6.f22245a[policy.ordinal()];
        if (i2 == 1) {
            return new com.tencent.tbs.one.impl.e.c.a(context, str, aVar, file, bundle, m());
        }
        if (i2 == 2) {
            return new com.tencent.tbs.one.impl.e.b.a.a(context, str, aVar, file, bundle, m());
        }
        if (i2 == 3) {
            final String strM = m();
            final String str2 = aVar.f21995d;
            final int i3 = aVar.f21994c;
            return new com.tencent.tbs.one.impl.a.b<e<File>>() { // from class: com.tencent.tbs.one.impl.e.i.5
                /*  JADX ERROR: Types fix failed
                    java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
                    	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
                    */
                /* JADX WARN: Failed to calculate best type for var: r0v0 ??
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:156)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:133)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:238)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
                 */
                /* JADX WARN: Failed to calculate best type for var: r0v0 ??
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:466)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:188)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:145)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:123)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:101)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:101)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
                 */
                /* JADX WARN: Failed to calculate best type for var: r0v2 ??
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.calculateFromBounds(FixTypesVisitor.java:156)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.setBestType(FixTypesVisitor.java:133)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:238)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
                 */
                /* JADX WARN: Failed to calculate best type for var: r0v2 ??
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.allSameListener(TypeUpdate.java:473)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:145)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:123)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:101)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:101)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
                 */
                /* JADX WARN: Failed to calculate best type for var: r0v4 ??
                java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:447)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
                	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.calculateFromBounds(TypeInferenceVisitor.java:145)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.setBestType(TypeInferenceVisitor.java:123)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.lambda$runTypePropagation$2(TypeInferenceVisitor.java:101)
                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runTypePropagation(TypeInferenceVisitor.java:101)
                	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:75)
                 */
                /* JADX WARN: Not initialized variable reg: 1, insn: 0x0076: MOVE (r4 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:22:0x0076 */
                @Override // com.tencent.tbs.one.impl.a.b
                public final void a() {
                    /*
                        r5 = this;
                        r0 = 0
                        android.content.Context r1 = r2     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
                        java.lang.String r2 = r3     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
                        java.lang.String r3 = r4     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
                        java.io.InputStream r1 = com.tencent.tbs.one.impl.a.d.a(r1, r2, r3)     // Catch: java.lang.Throwable -> L3e java.io.IOException -> L40
                        java.io.File r2 = r5     // Catch: java.io.IOException -> L3c java.lang.Throwable -> L75
                        com.tencent.tbs.one.impl.a.d.a(r1, r2, r0)     // Catch: java.io.IOException -> L3c java.lang.Throwable -> L75
                        com.tencent.tbs.one.impl.a.d.a(r1)
                        java.io.File r0 = r5     // Catch: com.tencent.tbs.one.TBSOneException -> L2b
                        com.tencent.tbs.one.impl.e.f.a(r0, r0)     // Catch: com.tencent.tbs.one.TBSOneException -> L2b
                        java.io.File r0 = r5     // Catch: com.tencent.tbs.one.TBSOneException -> L2b
                        int r1 = r6     // Catch: com.tencent.tbs.one.TBSOneException -> L2b
                        com.tencent.tbs.one.impl.e.f.a(r0, r1)     // Catch: com.tencent.tbs.one.TBSOneException -> L2b
                        com.tencent.tbs.one.impl.e.e$a r0 = com.tencent.tbs.one.impl.e.e.a.LOCAL
                        java.io.File r1 = r5
                        com.tencent.tbs.one.impl.e.e r0 = com.tencent.tbs.one.impl.e.e.a(r0, r1)
                        r5.a(r0)
                        return
                    L2b:
                        r0 = move-exception
                        int r1 = r0.getErrorCode()
                        java.lang.String r2 = r0.getMessage()
                        java.lang.Throwable r0 = r0.getCause()
                        r5.a(r1, r2, r0)
                        return
                    L3c:
                        r0 = move-exception
                        goto L44
                    L3e:
                        r1 = move-exception
                        goto L79
                    L40:
                        r1 = move-exception
                        r4 = r1
                        r1 = r0
                        r0 = r4
                    L44:
                        java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L75
                        java.lang.String r3 = "Failed to unzip local component from "
                        r2.<init>(r3)     // Catch: java.lang.Throwable -> L75
                        java.lang.String r3 = r4     // Catch: java.lang.Throwable -> L75
                        r2.append(r3)     // Catch: java.lang.Throwable -> L75
                        java.lang.String r3 = " to "
                        r2.append(r3)     // Catch: java.lang.Throwable -> L75
                        java.io.File r3 = r5     // Catch: java.lang.Throwable -> L75
                        java.lang.String r3 = r3.getAbsolutePath()     // Catch: java.lang.Throwable -> L75
                        r2.append(r3)     // Catch: java.lang.Throwable -> L75
                        java.lang.String r3 = ", localRepository: "
                        r2.append(r3)     // Catch: java.lang.Throwable -> L75
                        java.lang.String r3 = r3     // Catch: java.lang.Throwable -> L75
                        r2.append(r3)     // Catch: java.lang.Throwable -> L75
                        java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L75
                        r3 = 313(0x139, float:4.39E-43)
                        r5.a(r3, r2, r0)     // Catch: java.lang.Throwable -> L75
                        com.tencent.tbs.one.impl.a.d.a(r1)
                        return
                    L75:
                        r0 = move-exception
                        r4 = r1
                        r1 = r0
                        r0 = r4
                    L79:
                        com.tencent.tbs.one.impl.a.d.a(r0)
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.e.i.AnonymousClass5.a():void");
                }
            };
        }
        if (i2 == 4) {
            return new com.tencent.tbs.one.impl.e.d.a(context, str, aVar, file, bundle);
        }
        if (i2 != 5) {
            return null;
        }
        return new com.tencent.tbs.one.impl.e.a.a(context, str, f(), aVar, file, bundle);
    }

    private void a(e<com.tencent.tbs.one.impl.common.d> eVar, Set<String> set) {
        Object obj = eVar.f22178c;
        if (obj instanceof JSONObject) {
            String str = this.f22205b;
            JSONObject jSONObject = (JSONObject) obj;
            JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("SWITCH");
            if (jSONObjectOptJSONObject != null) {
                Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    if (jSONObjectOptJSONObject.optInt(next, 0) == 1) {
                        set.add(next);
                    }
                }
            }
            JSONObject jSONObjectOptJSONObject2 = jSONObject.optJSONObject("RESET");
            if (jSONObjectOptJSONObject2 != null) {
                Iterator<String> itKeys2 = jSONObjectOptJSONObject2.keys();
                while (itKeys2.hasNext()) {
                    String next2 = itKeys2.next();
                    if (jSONObjectOptJSONObject2.optInt(next2, 0) == 1) {
                        File fileA = a(next2, f.c(a(next2)));
                        if (fileA.exists()) {
                            f.e(fileA);
                            com.tencent.tbs.one.impl.a.g.a("[%s] Reset component %s", str, fileA.getAbsolutePath());
                        } else {
                            com.tencent.tbs.one.impl.a.g.c("[%s] Failed to reset component, %s does not exist", str, fileA.getAbsolutePath());
                        }
                    }
                }
            }
            if (jSONObject.optInt("ULOG") == 1) {
                Statistics.reportLog();
            }
        }
    }

    private boolean a(File file, TBSOneManager.Policy policy) throws Throwable {
        com.tencent.tbs.one.impl.common.d dVarA;
        String str = this.f22205b;
        if (file.exists()) {
            try {
                com.tencent.tbs.one.impl.common.d dVarA2 = com.tencent.tbs.one.impl.common.d.a(file);
                if (policy == TBSOneManager.Policy.BUILTIN_FIRST) {
                    dVarA = com.tencent.tbs.one.impl.common.d.a(com.tencent.tbs.one.impl.common.f.c(com.tencent.tbs.one.impl.common.f.a(this.f22204a), str));
                } else if (policy == TBSOneManager.Policy.BUILTIN_ASSETS_ONLY) {
                    try {
                        InputStream inputStreamOpen = this.f22204a.getAssets().open("webkit/repo/" + str + "/DEPS");
                        if (inputStreamOpen == null) {
                            return false;
                        }
                        dVarA = com.tencent.tbs.one.impl.common.d.a(inputStreamOpen);
                    } catch (Throwable unused) {
                        return false;
                    }
                } else {
                    dVarA = null;
                }
                int i2 = dVarA2.f21989a;
                if (i2 < dVarA.f21989a) {
                    com.tencent.tbs.one.impl.a.g.a("[%s] Ignoring existing DEPS, builtin DEPS version has changed %d => %d", str, Integer.valueOf(i2), Integer.valueOf(dVarA.f21989a));
                    return true;
                }
            } catch (TBSOneException unused2) {
            }
        }
        return false;
    }

    private boolean a(String str, TBSOneCallback tBSOneCallback) {
        if (!this.f22227u.contains(str)) {
            return true;
        }
        if (tBSOneCallback == null) {
            return false;
        }
        tBSOneCallback.onError(501, "The component has disabled");
        return false;
    }

    private void h(String str) {
        File fileD = com.tencent.tbs.one.impl.common.f.d(com.tencent.tbs.one.impl.common.f.a(this.f22204a.getDir("tbs", 0), Process.myPid()), this.f22205b);
        com.tencent.tbs.one.impl.a.d.e(fileD.getParentFile());
        if (!fileD.exists()) {
            com.tencent.tbs.one.impl.a.d.b(fileD);
        }
        HashSet hashSet = new HashSet(Arrays.asList(l()));
        hashSet.add(str);
        SharedPreferences.Editor editorEdit = this.f22223q.edit();
        editorEdit.putStringSet("in_use_component_names", hashSet);
        editorEdit.apply();
    }

    private String m() {
        Object objG = g(TBSOneConfigurationKeys.LOCAL_REPOSITORY_PATH);
        return objG instanceof String ? (String) objG : "/android_asset/";
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final TBSOneManager.Policy a() {
        Object objG = g("overridden_policy");
        return objG instanceof TBSOneManager.Policy ? (TBSOneManager.Policy) objG : objG instanceof String ? TBSOneManager.Policy.valueOf(objG.toString()) : super.a();
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0098 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.tencent.tbs.one.impl.e.h
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.tencent.tbs.one.impl.a.b<com.tencent.tbs.one.impl.e.e<com.tencent.tbs.one.impl.common.d>> a(android.os.Bundle r17, com.tencent.tbs.one.impl.a.m<com.tencent.tbs.one.impl.e.e<com.tencent.tbs.one.impl.common.d>> r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 405
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tbs.one.impl.e.i.a(android.os.Bundle, com.tencent.tbs.one.impl.a.m):com.tencent.tbs.one.impl.a.b");
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final com.tencent.tbs.one.impl.a.b<e<File>> a(Bundle bundle, d.a aVar, m<e<File>> mVar) {
        com.tencent.tbs.one.impl.a.b<e<File>> bVarA;
        com.tencent.tbs.one.impl.a.b<e<File>> bVar;
        com.tencent.tbs.one.impl.a.h hVar;
        TBSOneManager.Policy policyA = a();
        TBSOneManager.Policy policy = TBSOneManager.Policy.BUILTIN_ONLY;
        if (policyA == policy) {
            return super.a(bundle, aVar, mVar);
        }
        com.tencent.tbs.one.impl.a.g.a("[%s] Installing component %s, version: %d, policy: %s", this.f22205b, aVar.f21992a, Integer.valueOf(aVar.f21994c), policyA);
        File fileA = a(aVar.f21992a, aVar.f21994c);
        com.tencent.tbs.one.impl.a.d.e(fileA.getParentFile());
        TBSOneManager.Policy policy2 = TBSOneManager.Policy.AUTO;
        if (policyA != policy2) {
            TBSOneManager.Policy policy3 = TBSOneManager.Policy.LOCAL_ONLY;
            if (policyA == policy3) {
                policy2 = policy3;
            } else {
                TBSOneManager.Policy policy4 = TBSOneManager.Policy.ONLINE;
                if (policyA == policy4) {
                    policy2 = policy4;
                } else {
                    if (policyA == TBSOneManager.Policy.BUILTIN_FIRST) {
                        hVar = new com.tencent.tbs.one.impl.a.h(new com.tencent.tbs.one.impl.a.b[]{a(policy, aVar, fileA, bundle), a(policy2, aVar, fileA, bundle)});
                    } else {
                        TBSOneManager.Policy policy5 = TBSOneManager.Policy.BUILTIN_ASSETS_ONLY;
                        if (policyA == policy5) {
                            bVarA = a(policy5, aVar, fileA, bundle);
                        } else if (policyA == TBSOneManager.Policy.BUILTIN_ASSETS_FIRST) {
                            hVar = new com.tencent.tbs.one.impl.a.h(new com.tencent.tbs.one.impl.a.b[]{a(policy5, aVar, fileA, bundle), a(policy2, aVar, fileA, bundle)});
                        } else if (policyA == TBSOneManager.Policy.LOCAL_FIRST) {
                            hVar = new com.tencent.tbs.one.impl.a.h(new com.tencent.tbs.one.impl.a.b[]{a(policy3, aVar, fileA, bundle), a(policy2, aVar, fileA, bundle)});
                        } else {
                            bVarA = null;
                        }
                        bVar = bVarA;
                    }
                    bVar = hVar;
                }
            }
            bVarA = a(policy2, aVar, fileA, bundle);
            bVar = bVarA;
        } else {
            bVarA = a(policy2, aVar, fileA, bundle);
            bVar = bVarA;
        }
        c cVar = new c(this.f22204a, aVar, bVar, fileA, bundle != null ? bundle.getInt("time_out", 10000) : 10000);
        cVar.a((m) mVar);
        return cVar;
    }

    public final com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>> a(TBSOneManager.Policy policy, int i2, final File file, Bundle bundle) {
        com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>> bVar;
        final Context context = this.f22204a;
        String str = this.f22205b;
        int i3 = AnonymousClass6.f22245a[policy.ordinal()];
        if (i3 == 1) {
            return new com.tencent.tbs.one.impl.e.c.b(context, str, file);
        }
        if (i3 == 2) {
            return new com.tencent.tbs.one.impl.e.b.a.b(context, str, file);
        }
        if (i3 == 3) {
            final String strM = m();
            bVar = new com.tencent.tbs.one.impl.a.b<e<com.tencent.tbs.one.impl.common.d>>() { // from class: com.tencent.tbs.one.impl.e.i.4
                @Override // com.tencent.tbs.one.impl.a.b
                public final void a() throws IOException {
                    InputStream inputStreamA = null;
                    try {
                        try {
                            inputStreamA = com.tencent.tbs.one.impl.a.d.a(context, strM, "DEPS");
                            String strA = com.tencent.tbs.one.impl.a.d.a(inputStreamA, "utf-8", file);
                            com.tencent.tbs.one.impl.common.d dVarA = com.tencent.tbs.one.impl.common.d.a(strA);
                            com.tencent.tbs.one.impl.a.d.a(strA, "utf-8", file);
                            com.tencent.tbs.one.impl.a.d.a(inputStreamA);
                            a((AnonymousClass4) e.a(e.a.LOCAL, dVarA));
                        } catch (TBSOneException e2) {
                            a(e2.getErrorCode(), e2.getMessage(), e2.getCause());
                            com.tencent.tbs.one.impl.a.d.a(inputStreamA);
                        } catch (IOException e3) {
                            a(303, "Failed to copy local DEPS from " + strM + " to " + file.getAbsolutePath(), e3);
                            com.tencent.tbs.one.impl.a.d.a(inputStreamA);
                        }
                    } catch (Throwable th) {
                        com.tencent.tbs.one.impl.a.d.a(inputStreamA);
                        throw th;
                    }
                }
            };
        } else {
            if (i3 != 4) {
                if (i3 != 5) {
                    return null;
                }
                return com.tencent.tbs.one.impl.a.a(this, i2, file, bundle);
            }
            bVar = new com.tencent.tbs.one.impl.e.d.b(context, str, f(), file);
        }
        return bVar;
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void a(Bundle bundle, String str, TBSOneCallback<File> tBSOneCallback) {
        if (a(str, (TBSOneCallback) tBSOneCallback)) {
            super.a(bundle, str, tBSOneCallback);
        }
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void a(e<com.tencent.tbs.one.impl.common.d> eVar) {
        super.a(eVar);
        SharedPreferences.Editor editorEdit = this.f22223q.edit();
        editorEdit.putInt("in_use_deps_version", eVar.f22177b.f21989a);
        editorEdit.apply();
    }

    public final void a(e<com.tencent.tbs.one.impl.common.d> eVar, boolean z2) {
        String str;
        if (eVar.f22176a != e.a.ONLINE) {
            return;
        }
        HashSet hashSet = new HashSet();
        a(eVar, hashSet);
        com.tencent.tbs.one.impl.a.g.a("[%s] Disabled components {%s} from server", this.f22205b, TextUtils.join(", ", hashSet));
        SharedPreferences.Editor editorEdit = this.f22223q.edit();
        if (z2) {
            this.f22227u = hashSet;
            str = "disabled_component_names";
        } else {
            this.f22228v = hashSet;
            str = "disabled_local_components";
        }
        editorEdit.putStringSet(str, hashSet);
        editorEdit.apply();
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void a(String str, Bundle bundle, TBSOneCallback<TBSOneComponent> tBSOneCallback) {
        h(str);
        if (a(str, (TBSOneCallback) tBSOneCallback)) {
            super.a(str, bundle, tBSOneCallback);
        }
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void a(String str, Object obj) {
        super.a(str, obj);
        if (str == null) {
            return;
        }
        if (str.equals(TBSOneConfigurationKeys.PERMANENT_VERSION)) {
            a(false);
        }
        if (obj == null) {
            return;
        }
        if (str.equals(TBSOneConfigurationKeys.GUID)) {
            com.tencent.tbs.one.impl.a.e.a(obj.toString());
        }
        if (str.equals(TBSOneConfigurationKeys.PPVN)) {
            com.tencent.tbs.one.impl.a.e.b(obj.toString());
        }
        if (str.equals(TBSOneConfigurationKeys.DISABLE_QUERY_RUNNING_PROCESSES)) {
            com.tencent.tbs.one.impl.a.e.a(com.tencent.tbs.one.impl.a.e.a(obj));
        }
        if (str.equals(TBSOneConfigurationKeys.ENABLE_CONSOLE_LOGGING)) {
            com.tencent.tbs.one.impl.a.g.a(com.tencent.tbs.one.impl.a.e.a(obj));
        }
        if (str.equals(TBSOneConfigurationKeys.NEED_UPDATE_AT_UPGRADE) && (obj instanceof Boolean)) {
            this.f22214k = ((Boolean) obj).booleanValue();
        }
        if (str.equals(TBSOneConfigurationKeys.UPDATE_INTERVAL) && (obj instanceof Long)) {
            this.f22213j = ((Long) obj).longValue();
        }
        if (str.equals(TBSOneConfigurationKeys.IGNORE_UPDATE_FLOW_CONTROL) && (obj instanceof Boolean)) {
            this.f22215l = ((Boolean) obj).booleanValue();
        }
        if (str.equals(TBSOneConfigurationKeys.IGNORE_UPDATE_WIFI_NETWORK) && (obj instanceof Boolean)) {
            this.f22216m = ((Boolean) obj).booleanValue();
        }
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void a(boolean z2) {
        super.a(z2);
        if (z2) {
            j();
        }
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void b(String str, Bundle bundle, TBSOneCallback<File> tBSOneCallback) {
        h(str);
        if (a(str, (TBSOneCallback) tBSOneCallback)) {
            super.b(str, bundle, tBSOneCallback);
        }
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final boolean b(String str) {
        File[] fileArrListFiles;
        File fileA = a(str);
        if (!fileA.exists() || (fileArrListFiles = fileA.listFiles()) == null) {
            return false;
        }
        File fileB = com.tencent.tbs.one.impl.common.f.b(this.f22206c);
        if (!fileB.exists()) {
            return false;
        }
        try {
            d.a aVarB = com.tencent.tbs.one.impl.common.d.a(fileB).b(str);
            if (aVarB == null) {
                return false;
            }
            String[] strArr = aVarB.f21997f;
            if (strArr != null) {
                for (String str2 : strArr) {
                    if (!b(str2)) {
                        return false;
                    }
                }
            }
            for (File file : fileArrListFiles) {
                if (file.isDirectory() && f.g(file)) {
                    return true;
                }
            }
            return false;
        } catch (TBSOneException e2) {
            com.tencent.tbs.one.impl.a.g.c("[%s] Failed to parse DEPS file %s", this.f22205b, fileB.getAbsolutePath(), e2);
            return false;
        }
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final TBSOneOnlineService c() {
        g gVar;
        if (!b()) {
            return null;
        }
        synchronized (this.f22225s) {
            if (this.f22224r == null) {
                this.f22224r = new g(this);
            }
            gVar = this.f22224r;
        }
        return gVar;
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final int[] c(String str) {
        File[] fileArrListFiles;
        File fileA = a(str);
        if (!fileA.exists() || (fileArrListFiles = fileA.listFiles()) == null) {
            return new int[0];
        }
        int[] iArr = new int[fileArrListFiles.length];
        int i2 = 0;
        for (File file : fileArrListFiles) {
            if (file.isDirectory() && f.g(file)) {
                try {
                    int i3 = i2 + 1;
                    try {
                        iArr[i2] = Integer.parseInt(file.getName());
                        i2 = i3;
                    } catch (Exception unused) {
                        i2 = i3;
                        com.tencent.tbs.one.impl.a.g.c("[%s] Failed to parse installed version from path %s", this.f22205b, file.getAbsolutePath());
                    }
                } catch (Exception unused2) {
                }
            }
        }
        return Arrays.copyOfRange(iArr, 0, i2);
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final TBSOneDebugger d() {
        com.tencent.tbs.one.impl.b.b bVar;
        synchronized (this.f22226t) {
            if (this.f22231y == null) {
                this.f22231y = new com.tencent.tbs.one.impl.b.b(this.f22205b);
            }
            bVar = this.f22231y;
        }
        return bVar;
    }

    @Override // com.tencent.tbs.one.impl.e.h
    public final void e() {
        if (this.f22229w) {
            return;
        }
        this.f22229w = true;
        TBSOneOnlineService tBSOneOnlineServiceC = c();
        if (tBSOneOnlineServiceC == null) {
            return;
        }
        Bundle bundle = new Bundle();
        if (this.f22215l) {
            bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FLOW_CONTROL_SUGGESTION, true);
        }
        if (this.f22216m) {
            bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, true);
        }
        tBSOneOnlineServiceC.update(bundle, new TBSOneCallback<Void>() { // from class: com.tencent.tbs.one.impl.e.i.2
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(Void r12) {
                i.this.k();
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i2, String str) {
            }
        });
    }

    public final String f() {
        Object objG = g(TBSOneConfigurationKeys.ONLINE_SERVICE_URL);
        return objG instanceof String ? (String) objG : "https://tbsone.imtt.qq.com";
    }

    public final File g() {
        return new File(this.f22206c, "incomplete-update");
    }

    public final boolean h() {
        boolean zExists = com.tencent.tbs.one.impl.common.f.c(this.f22206c).exists();
        boolean z2 = !g().exists();
        com.tencent.tbs.one.impl.a.g.a("[%s] Checking update availability, exists: %b, complete: %b", this.f22205b, Boolean.valueOf(zExists), Boolean.valueOf(z2));
        return zExists && z2;
    }

    public final k i() {
        return k.a(com.tencent.tbs.one.impl.common.f.e(com.tencent.tbs.one.impl.common.f.c(this.f22206c), ".lock"));
    }

    public final void j() {
        Handler handlerA = o.a();
        handlerA.removeCallbacks(this.f22230x);
        handlerA.postDelayed(this.f22230x, com.heytap.mcssdk.constant.a.f7153q);
    }

    public final void k() {
        SharedPreferences.Editor editorEdit = this.f22223q.edit();
        editorEdit.putLong("last_update_time", System.currentTimeMillis());
        editorEdit.apply();
    }

    public final String[] l() {
        return (String[]) this.f22223q.getStringSet("in_use_component_names", new HashSet()).toArray(new String[0]);
    }
}
