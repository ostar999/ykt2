package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
interface zzhg {
    void zza(int i2, double d3) throws IOException;

    void zza(int i2, float f2) throws IOException;

    void zza(int i2, long j2) throws IOException;

    void zza(int i2, zzct zzctVar) throws IOException;

    <K, V> void zza(int i2, zzey<K, V> zzeyVar, Map<K, V> map) throws IOException;

    void zza(int i2, Object obj) throws IOException;

    void zza(int i2, Object obj, zzfu zzfuVar) throws IOException;

    void zza(int i2, String str) throws IOException;

    void zza(int i2, List<String> list) throws IOException;

    void zza(int i2, List<?> list, zzfu zzfuVar) throws IOException;

    void zza(int i2, List<Integer> list, boolean z2) throws IOException;

    void zza(int i2, boolean z2) throws IOException;

    @Deprecated
    void zzab(int i2) throws IOException;

    @Deprecated
    void zzac(int i2) throws IOException;

    int zzay();

    void zzb(int i2, long j2) throws IOException;

    @Deprecated
    void zzb(int i2, Object obj, zzfu zzfuVar) throws IOException;

    void zzb(int i2, List<zzct> list) throws IOException;

    @Deprecated
    void zzb(int i2, List<?> list, zzfu zzfuVar) throws IOException;

    void zzb(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzc(int i2, int i3) throws IOException;

    void zzc(int i2, long j2) throws IOException;

    void zzc(int i2, List<Long> list, boolean z2) throws IOException;

    void zzd(int i2, int i3) throws IOException;

    void zzd(int i2, List<Long> list, boolean z2) throws IOException;

    void zze(int i2, int i3) throws IOException;

    void zze(int i2, List<Long> list, boolean z2) throws IOException;

    void zzf(int i2, int i3) throws IOException;

    void zzf(int i2, List<Float> list, boolean z2) throws IOException;

    void zzg(int i2, List<Double> list, boolean z2) throws IOException;

    void zzh(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzi(int i2, long j2) throws IOException;

    void zzi(int i2, List<Boolean> list, boolean z2) throws IOException;

    void zzj(int i2, long j2) throws IOException;

    void zzj(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzk(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzl(int i2, List<Long> list, boolean z2) throws IOException;

    void zzm(int i2, int i3) throws IOException;

    void zzm(int i2, List<Integer> list, boolean z2) throws IOException;

    void zzn(int i2, int i3) throws IOException;

    void zzn(int i2, List<Long> list, boolean z2) throws IOException;
}
