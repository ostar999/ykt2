package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.List;

/* loaded from: classes3.dex */
final class zzfw {
    private static final Class<?> zznn = zzdb();
    private static final zzgm<?, ?> zzno = zzh(false);
    private static final zzgm<?, ?> zznp = zzh(true);
    private static final zzgm<?, ?> zznq = new zzgo();

    public static void zza(int i2, List<Double> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzg(i2, list, z2);
    }

    public static void zzb(int i2, List<Float> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzf(i2, list, z2);
    }

    public static void zzc(int i2, List<Long> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzc(i2, list, z2);
    }

    public static zzgm<?, ?> zzcy() {
        return zzno;
    }

    public static zzgm<?, ?> zzcz() {
        return zznp;
    }

    public static void zzd(int i2, List<Long> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzd(i2, list, z2);
    }

    public static zzgm<?, ?> zzda() {
        return zznq;
    }

    private static Class<?> zzdb() {
        try {
            return Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Class<?> zzdc() {
        try {
            return Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void zze(int i2, List<Long> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzn(i2, list, z2);
    }

    public static void zzf(Class<?> cls) {
        Class<?> cls2;
        if (!zzdx.class.isAssignableFrom(cls) && (cls2 = zznn) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzg(int i2, List<Long> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzl(i2, list, z2);
    }

    public static void zzh(int i2, List<Integer> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zza(i2, list, z2);
    }

    public static void zzi(int i2, List<Integer> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzj(i2, list, z2);
    }

    public static void zzj(int i2, List<Integer> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzm(i2, list, z2);
    }

    public static void zzk(int i2, List<Integer> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzb(i2, list, z2);
    }

    public static void zzl(int i2, List<Integer> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzk(i2, list, z2);
    }

    public static void zzm(int i2, List<Integer> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzh(i2, list, z2);
    }

    public static void zzn(int i2, List<Boolean> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzi(i2, list, z2);
    }

    public static int zzo(int i2, List<Long> list, boolean z2) {
        if (list.size() == 0) {
            return 0;
        }
        return zza(list) + (list.size() * zzdk.zzs(i2));
    }

    public static int zzp(int i2, List<Long> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzb(list) + (size * zzdk.zzs(i2));
    }

    public static int zzq(int i2, List<Long> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzc(list) + (size * zzdk.zzs(i2));
    }

    public static int zzr(int i2, List<Integer> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzdk.zzs(i2));
    }

    public static int zzs(int i2, List<Integer> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zze(list) + (size * zzdk.zzs(i2));
    }

    public static int zzt(int i2, List<Integer> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzf(list) + (size * zzdk.zzs(i2));
    }

    public static int zzu(int i2, List<Integer> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzg(list) + (size * zzdk.zzs(i2));
    }

    public static int zzv(int i2, List<?> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzdk.zzj(i2, 0);
    }

    public static int zzw(int i2, List<?> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzdk.zzg(i2, 0L);
    }

    public static int zzx(int i2, List<?> list, boolean z2) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * zzdk.zzb(i2, true);
    }

    public static void zza(int i2, List<String> list, zzhg zzhgVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zza(i2, list);
    }

    public static void zzb(int i2, List<zzct> list, zzhg zzhgVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzb(i2, list);
    }

    public static int zzc(List<Long> list) {
        int iZzg;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzev) {
            zzev zzevVar = (zzev) list;
            iZzg = 0;
            while (i2 < size) {
                iZzg += zzdk.zzg(zzevVar.getLong(i2));
                i2++;
            }
        } else {
            iZzg = 0;
            while (i2 < size) {
                iZzg += zzdk.zzg(list.get(i2).longValue());
                i2++;
            }
        }
        return iZzg;
    }

    public static int zzd(List<Integer> list) {
        int iZzy;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzy = 0;
            while (i2 < size) {
                iZzy += zzdk.zzy(zzdzVar.getInt(i2));
                i2++;
            }
        } else {
            iZzy = 0;
            while (i2 < size) {
                iZzy += zzdk.zzy(list.get(i2).intValue());
                i2++;
            }
        }
        return iZzy;
    }

    public static int zze(List<Integer> list) {
        int iZzt;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzt = 0;
            while (i2 < size) {
                iZzt += zzdk.zzt(zzdzVar.getInt(i2));
                i2++;
            }
        } else {
            iZzt = 0;
            while (i2 < size) {
                iZzt += zzdk.zzt(list.get(i2).intValue());
                i2++;
            }
        }
        return iZzt;
    }

    public static int zzg(List<Integer> list) {
        int iZzv;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzv = 0;
            while (i2 < size) {
                iZzv += zzdk.zzv(zzdzVar.getInt(i2));
                i2++;
            }
        } else {
            iZzv = 0;
            while (i2 < size) {
                iZzv += zzdk.zzv(list.get(i2).intValue());
                i2++;
            }
        }
        return iZzv;
    }

    public static int zzh(List<?> list) {
        return list.size() << 2;
    }

    public static int zzi(List<?> list) {
        return list.size() << 3;
    }

    public static int zzj(List<?> list) {
        return list.size();
    }

    public static void zzf(int i2, List<Long> list, zzhg zzhgVar, boolean z2) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zze(i2, list, z2);
    }

    private static zzgm<?, ?> zzh(boolean z2) {
        try {
            Class<?> clsZzdc = zzdc();
            if (clsZzdc == null) {
                return null;
            }
            return (zzgm) clsZzdc.getConstructor(Boolean.TYPE).newInstance(Boolean.valueOf(z2));
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void zza(int i2, List<?> list, zzhg zzhgVar, zzfu zzfuVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zza(i2, list, zzfuVar);
    }

    public static void zzb(int i2, List<?> list, zzhg zzhgVar, zzfu zzfuVar) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzhgVar.zzb(i2, list, zzfuVar);
    }

    public static int zzf(List<Integer> list) {
        int iZzu;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzdz) {
            zzdz zzdzVar = (zzdz) list;
            iZzu = 0;
            while (i2 < size) {
                iZzu += zzdk.zzu(zzdzVar.getInt(i2));
                i2++;
            }
        } else {
            iZzu = 0;
            while (i2 < size) {
                iZzu += zzdk.zzu(list.get(i2).intValue());
                i2++;
            }
        }
        return iZzu;
    }

    public static int zza(List<Long> list) {
        int iZze;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzev) {
            zzev zzevVar = (zzev) list;
            iZze = 0;
            while (i2 < size) {
                iZze += zzdk.zze(zzevVar.getLong(i2));
                i2++;
            }
        } else {
            iZze = 0;
            while (i2 < size) {
                iZze += zzdk.zze(list.get(i2).longValue());
                i2++;
            }
        }
        return iZze;
    }

    public static int zzb(List<Long> list) {
        int iZzf;
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        if (list instanceof zzev) {
            zzev zzevVar = (zzev) list;
            iZzf = 0;
            while (i2 < size) {
                iZzf += zzdk.zzf(zzevVar.getLong(i2));
                i2++;
            }
        } else {
            iZzf = 0;
            while (i2 < size) {
                iZzf += zzdk.zzf(list.get(i2).longValue());
                i2++;
            }
        }
        return iZzf;
    }

    public static int zzc(int i2, List<?> list) {
        int iZzr;
        int iZzr2;
        int size = list.size();
        int i3 = 0;
        if (size == 0) {
            return 0;
        }
        int iZzs = zzdk.zzs(i2) * size;
        if (list instanceof zzeo) {
            zzeo zzeoVar = (zzeo) list;
            while (i3 < size) {
                Object objZzad = zzeoVar.zzad(i3);
                if (objZzad instanceof zzct) {
                    iZzr2 = zzdk.zzb((zzct) objZzad);
                } else {
                    iZzr2 = zzdk.zzr((String) objZzad);
                }
                iZzs += iZzr2;
                i3++;
            }
        } else {
            while (i3 < size) {
                Object obj = list.get(i3);
                if (obj instanceof zzct) {
                    iZzr = zzdk.zzb((zzct) obj);
                } else {
                    iZzr = zzdk.zzr((String) obj);
                }
                iZzs += iZzr;
                i3++;
            }
        }
        return iZzs;
    }

    public static int zzd(int i2, List<zzct> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzs = size * zzdk.zzs(i2);
        for (int i3 = 0; i3 < list.size(); i3++) {
            iZzs += zzdk.zzb(list.get(i3));
        }
        return iZzs;
    }

    public static <T> void zza(zzfa zzfaVar, T t2, T t3, long j2) {
        zzgs.zza(t2, j2, zzfaVar.zzb(zzgs.zzo(t2, j2), zzgs.zzo(t3, j2)));
    }

    public static int zzd(int i2, List<zzfh> list, zzfu zzfuVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzc = 0;
        for (int i3 = 0; i3 < size; i3++) {
            iZzc += zzdk.zzc(i2, list.get(i3), zzfuVar);
        }
        return iZzc;
    }

    public static boolean zzd(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static <T, FT extends zzdu<FT>> void zza(zzdn<FT> zzdnVar, T t2, T t3) {
        zzds<T> zzdsVarZzd = zzdnVar.zzd(t3);
        if (zzdsVarZzd.zzhk.isEmpty()) {
            return;
        }
        zzdnVar.zze(t2).zza(zzdsVarZzd);
    }

    public static <T, UT, UB> void zza(zzgm<UT, UB> zzgmVar, T t2, T t3) {
        zzgmVar.zze(t2, zzgmVar.zzf(zzgmVar.zzp(t2), zzgmVar.zzp(t3)));
    }

    public static int zzc(int i2, Object obj, zzfu zzfuVar) {
        if (obj instanceof zzem) {
            return zzdk.zza(i2, (zzem) obj);
        }
        return zzdk.zzb(i2, (zzfh) obj, zzfuVar);
    }

    public static int zzc(int i2, List<?> list, zzfu zzfuVar) {
        int iZza;
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzs = zzdk.zzs(i2) * size;
        for (int i3 = 0; i3 < size; i3++) {
            Object obj = list.get(i3);
            if (obj instanceof zzem) {
                iZza = zzdk.zza((zzem) obj);
            } else {
                iZza = zzdk.zza((zzfh) obj, zzfuVar);
            }
            iZzs += iZza;
        }
        return iZzs;
    }
}
