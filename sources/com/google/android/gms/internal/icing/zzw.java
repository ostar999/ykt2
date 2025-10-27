package com.google.android.gms.internal.icing;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.appindexing.AppIndexApi;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.icing.zzal;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.zip.CRC32;
import k.a;

@ShowFirstParty
@SafeParcelable.Class(creator = "UsageInfoCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzw extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzw> CREATOR = new zzy();

    @SafeParcelable.Field(id = 1)
    private final zzi zzaj;

    @SafeParcelable.Field(id = 2)
    private final long zzak;

    @SafeParcelable.Field(id = 3)
    private int zzal;

    @SafeParcelable.Field(id = 4)
    private final String zzam;

    @SafeParcelable.Field(id = 5)
    private final zzh zzan;

    @SafeParcelable.Field(defaultValue = a.f27524v, id = 6)
    private final boolean zzao;

    @SafeParcelable.Field(defaultValue = "-1", id = 7)
    private int zzap;

    @SafeParcelable.Field(id = 8)
    private int zzaq;

    @SafeParcelable.Field(id = 9)
    private final String zzar;

    @VisibleForTesting
    public zzw(String str, Intent intent, String str2, Uri uri, String str3, List<AppIndexApi.AppIndexingLink> list, int i2) {
        this(zza(str, intent), System.currentTimeMillis(), 0, null, zza(intent, str2, uri, null, list).zzb(), false, -1, 1, null);
    }

    public static zzi zza(String str, Intent intent) {
        return new zzi(str, "", zza(intent));
    }

    public final String toString() {
        return String.format(Locale.US, "UsageInfo[documentId=%s, timestamp=%d, usageType=%d, status=%d]", this.zzaj, Long.valueOf(this.zzak), Integer.valueOf(this.zzal), Integer.valueOf(this.zzaq));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zzaj, i2, false);
        SafeParcelWriter.writeLong(parcel, 2, this.zzak);
        SafeParcelWriter.writeInt(parcel, 3, this.zzal);
        SafeParcelWriter.writeString(parcel, 4, this.zzam, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zzan, i2, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzao);
        SafeParcelWriter.writeInt(parcel, 7, this.zzap);
        SafeParcelWriter.writeInt(parcel, 8, this.zzaq);
        SafeParcelWriter.writeString(parcel, 9, this.zzar, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    private static zzk zza(String str, String str2) {
        return new zzk(str2, new zzs(str).zzb(true).zzc(), str);
    }

    @SafeParcelable.Constructor
    public zzw(@SafeParcelable.Param(id = 1) zzi zziVar, @SafeParcelable.Param(id = 2) long j2, @SafeParcelable.Param(id = 3) int i2, @SafeParcelable.Param(id = 4) String str, @SafeParcelable.Param(id = 5) zzh zzhVar, @SafeParcelable.Param(id = 6) boolean z2, @SafeParcelable.Param(id = 7) int i3, @SafeParcelable.Param(id = 8) int i4, @SafeParcelable.Param(id = 9) String str2) {
        this.zzaj = zziVar;
        this.zzak = j2;
        this.zzal = i2;
        this.zzam = str;
        this.zzan = zzhVar;
        this.zzao = z2;
        this.zzap = i3;
        this.zzaq = i4;
        this.zzar = str2;
    }

    private static String zza(Intent intent) {
        String uri = intent.toUri(1);
        CRC32 crc32 = new CRC32();
        try {
            crc32.update(uri.getBytes("UTF-8"));
            return Long.toHexString(crc32.getValue());
        } catch (UnsupportedEncodingException e2) {
            throw new IllegalStateException(e2);
        }
    }

    @VisibleForTesting
    public static zzg zza(Intent intent, String str, Uri uri, String str2, List<AppIndexApi.AppIndexingLink> list) {
        String string;
        zzg zzgVar = new zzg();
        zzgVar.zza(new zzk(str, new zzs("title").zzc(true).zzd("name").zzc(), "text1"));
        if (uri != null) {
            zzgVar.zza(new zzk(uri.toString(), new zzs("web_url").zzb(true).zzd("url").zzc()));
        }
        if (list != null) {
            zzal.zza.C0145zza c0145zzaZzf = zzal.zza.zzf();
            int size = list.size();
            zzal.zza.zzb[] zzbVarArr = new zzal.zza.zzb[size];
            for (int i2 = 0; i2 < size; i2++) {
                zzal.zza.zzb.C0146zza c0146zzaZzh = zzal.zza.zzb.zzh();
                AppIndexApi.AppIndexingLink appIndexingLink = list.get(i2);
                c0146zzaZzh.zze(appIndexingLink.appIndexingUrl.toString()).zzd(appIndexingLink.viewId);
                Uri uri2 = appIndexingLink.webUrl;
                if (uri2 != null) {
                    c0146zzaZzh.zzf(uri2.toString());
                }
                zzbVarArr[i2] = (zzal.zza.zzb) ((zzdx) c0146zzaZzh.zzbx());
            }
            c0145zzaZzf.zza(Arrays.asList(zzbVarArr));
            zzgVar.zza(new zzk(((zzal.zza) ((zzdx) c0145zzaZzf.zzbx())).toByteArray(), new zzs("outlinks").zzb(true).zzd(".private:outLinks").zzc("blob").zzc()));
        }
        String action = intent.getAction();
        if (action != null) {
            zzgVar.zza(zza("intent_action", action));
        }
        String dataString = intent.getDataString();
        if (dataString != null) {
            zzgVar.zza(zza("intent_data", dataString));
        }
        ComponentName component = intent.getComponent();
        if (component != null) {
            zzgVar.zza(zza("intent_activity", component.getClassName()));
        }
        Bundle extras = intent.getExtras();
        if (extras != null && (string = extras.getString("intent_extra_data_key")) != null) {
            zzgVar.zza(zza("intent_extra_data", string));
        }
        return zzgVar.zza(str2).zza(true);
    }
}
