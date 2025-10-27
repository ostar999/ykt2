package com.google.android.gms.internal.icing;

import android.annotation.SuppressLint;
import android.content.Context;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public abstract class zzbq<T> {

    @SuppressLint({"StaticFieldLeak"})
    private static Context zzcs = null;
    private static boolean zzda = false;
    private static zzcc<zzbx<zzbm>> zzdb;
    private final String name;
    private final zzbu zzdc;
    private final T zzdd;
    private volatile int zzdf;
    private volatile T zzdg;
    private static final Object zzcz = new Object();
    private static final AtomicInteger zzde = new AtomicInteger();

    private zzbq(zzbu zzbuVar, String str, T t2) {
        this.zzdf = -1;
        if (zzbuVar.zzdl == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zzdc = zzbuVar;
        this.name = str;
        this.zzdd = t2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzbq<Boolean> zza(zzbu zzbuVar, String str, boolean z2) {
        return new zzbr(zzbuVar, str, Boolean.valueOf(z2));
    }

    public static void zzg(Context context) {
        synchronized (zzcz) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                context = applicationContext;
            }
            if (zzcs != context) {
                zzbc.zzp();
                zzbt.zzp();
                zzbh.zzs();
                zzde.incrementAndGet();
                zzcs = context;
                zzdb = zzcb.zza(zzbp.zzcy);
            }
        }
    }

    private final String zzm(String str) {
        if (str != null && str.isEmpty()) {
            return this.name;
        }
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf(this.name);
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    public static void zzt() {
        zzde.incrementAndGet();
    }

    public static final /* synthetic */ zzbx zzv() {
        new zzbl();
        return zzbl.zzd(zzcs);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0096 A[Catch: all -> 0x00ed, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0013, B:11:0x0021, B:17:0x0033, B:19:0x0039, B:21:0x0041, B:25:0x005a, B:27:0x0064, B:45:0x00b5, B:47:0x00c3, B:49:0x00d7, B:50:0x00da, B:51:0x00de, B:38:0x0096, B:40:0x00aa, B:44:0x00b3, B:23:0x0052, B:28:0x0069, B:30:0x0072, B:32:0x0084, B:34:0x008f, B:33:0x0089, B:52:0x00e3, B:53:0x00ea, B:54:0x00eb), top: B:61:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00c3 A[Catch: all -> 0x00ed, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0013, B:11:0x0021, B:17:0x0033, B:19:0x0039, B:21:0x0041, B:25:0x005a, B:27:0x0064, B:45:0x00b5, B:47:0x00c3, B:49:0x00d7, B:50:0x00da, B:51:0x00de, B:38:0x0096, B:40:0x00aa, B:44:0x00b3, B:23:0x0052, B:28:0x0069, B:30:0x0072, B:32:0x0084, B:34:0x008f, B:33:0x0089, B:52:0x00e3, B:53:0x00ea, B:54:0x00eb), top: B:61:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final T get() {
        /*
            Method dump skipped, instructions count: 243
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzbq.get():java.lang.Object");
    }

    public abstract T zza(Object obj);

    public final String zzu() {
        return zzm(this.zzdc.zzdn);
    }

    public /* synthetic */ zzbq(zzbu zzbuVar, String str, Object obj, zzbs zzbsVar) {
        this(zzbuVar, str, obj);
    }
}
