package com.google.android.gms.internal.icing;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.collection.ArrayMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public final class zzbc implements zzbg {

    @GuardedBy("ConfigurationContentLoader.class")
    private static final Map<Uri, zzbc> zzcj = new ArrayMap();
    private static final String[] zzcp = {"key", "value"};
    private final Uri uri;
    private final ContentResolver zzck;
    private final ContentObserver zzcl;
    private final Object zzcm;
    private volatile Map<String, String> zzcn;

    @GuardedBy("this")
    private final List<zzbd> zzco;

    private zzbc(ContentResolver contentResolver, Uri uri) {
        zzbe zzbeVar = new zzbe(this, null);
        this.zzcl = zzbeVar;
        this.zzcm = new Object();
        this.zzco = new ArrayList();
        this.zzck = contentResolver;
        this.uri = uri;
        contentResolver.registerContentObserver(uri, false, zzbeVar);
    }

    public static zzbc zza(ContentResolver contentResolver, Uri uri) {
        zzbc zzbcVar;
        synchronized (zzbc.class) {
            Map<Uri, zzbc> map = zzcj;
            zzbcVar = map.get(uri);
            if (zzbcVar == null) {
                try {
                    zzbc zzbcVar2 = new zzbc(contentResolver, uri);
                    try {
                        map.put(uri, zzbcVar2);
                    } catch (SecurityException unused) {
                    }
                    zzbcVar = zzbcVar2;
                } catch (SecurityException unused2) {
                }
            }
        }
        return zzbcVar;
    }

    private final Map<String, String> zzm() {
        Map<String, String> mapZzo = this.zzcn;
        if (mapZzo == null) {
            synchronized (this.zzcm) {
                mapZzo = this.zzcn;
                if (mapZzo == null) {
                    mapZzo = zzo();
                    this.zzcn = mapZzo;
                }
            }
        }
        return mapZzo != null ? mapZzo : Collections.emptyMap();
    }

    private final Map<String, String> zzo() {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                return (Map) zzbf.zza(new zzbi(this) { // from class: com.google.android.gms.internal.icing.zzbb
                    private final zzbc zzci;

                    {
                        this.zzci = this;
                    }

                    @Override // com.google.android.gms.internal.icing.zzbi
                    public final Object zzl() {
                        return this.zzci.zzq();
                    }
                });
            } catch (SQLiteException | IllegalStateException | SecurityException unused) {
                Log.e("ConfigurationContentLoader", "PhenotypeFlag unable to load ContentProvider, using default values");
                StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
                return null;
            }
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    public static synchronized void zzp() {
        for (zzbc zzbcVar : zzcj.values()) {
            zzbcVar.zzck.unregisterContentObserver(zzbcVar.zzcl);
        }
        zzcj.clear();
    }

    @Override // com.google.android.gms.internal.icing.zzbg
    public final /* synthetic */ Object zzi(String str) {
        return zzm().get(str);
    }

    public final void zzn() {
        synchronized (this.zzcm) {
            this.zzcn = null;
            zzbq.zzt();
        }
        synchronized (this) {
            Iterator<zzbd> it = this.zzco.iterator();
            while (it.hasNext()) {
                it.next().zzr();
            }
        }
    }

    public final /* synthetic */ Map zzq() {
        Cursor cursorQuery = this.zzck.query(this.uri, zzcp, null, null, null);
        if (cursorQuery == null) {
            return Collections.emptyMap();
        }
        try {
            int count = cursorQuery.getCount();
            if (count == 0) {
                return Collections.emptyMap();
            }
            Map arrayMap = count <= 256 ? new ArrayMap(count) : new HashMap(count, 1.0f);
            while (cursorQuery.moveToNext()) {
                arrayMap.put(cursorQuery.getString(0), cursorQuery.getString(1));
            }
            return arrayMap;
        } finally {
            cursorQuery.close();
        }
    }
}
