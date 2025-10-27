package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zzfl<T> implements zzfu<T> {
    private static final int[] zzmr = new int[0];
    private static final Unsafe zzms = zzgs.zzdp();
    private final zzfh zzmn;
    private final zzgm<?, ?> zzmo;
    private final boolean zzmp;
    private final zzdn<?> zzmq;
    private final int[] zzmt;
    private final Object[] zzmu;
    private final int zzmv;
    private final int zzmw;
    private final boolean zzmx;
    private final boolean zzmy;
    private final boolean zzmz;
    private final int[] zzna;
    private final int zznb;
    private final int zznc;
    private final zzfm zznd;
    private final zzer zzne;
    private final zzfa zznf;

    private zzfl(int[] iArr, Object[] objArr, int i2, int i3, zzfh zzfhVar, boolean z2, boolean z3, int[] iArr2, int i4, int i5, zzfm zzfmVar, zzer zzerVar, zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfa zzfaVar) {
        this.zzmt = iArr;
        this.zzmu = objArr;
        this.zzmv = i2;
        this.zzmw = i3;
        this.zzmx = zzfhVar instanceof zzdx;
        this.zzmy = z2;
        this.zzmp = zzdnVar != null && zzdnVar.zze(zzfhVar);
        this.zzmz = false;
        this.zzna = iArr2;
        this.zznb = i4;
        this.zznc = i5;
        this.zznd = zzfmVar;
        this.zzne = zzerVar;
        this.zzmo = zzgmVar;
        this.zzmq = zzdnVar;
        this.zzmn = zzfhVar;
        this.zznf = zzfaVar;
    }

    public static <T> zzfl<T> zza(Class<T> cls, zzff zzffVar, zzfm zzfmVar, zzer zzerVar, zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfa zzfaVar) {
        int i2;
        int iCharAt;
        int iCharAt2;
        int i3;
        int i4;
        int i5;
        int i6;
        int[] iArr;
        int i7;
        int i8;
        char cCharAt;
        int i9;
        char cCharAt2;
        int i10;
        char cCharAt3;
        int i11;
        char cCharAt4;
        int i12;
        char cCharAt5;
        int i13;
        char cCharAt6;
        int i14;
        char cCharAt7;
        int i15;
        char cCharAt8;
        int i16;
        int i17;
        boolean z2;
        int i18;
        zzfv zzfvVar;
        int i19;
        int iObjectFieldOffset;
        int i20;
        int i21;
        Class<?> cls2;
        String str;
        int iObjectFieldOffset2;
        int i22;
        Field fieldZza;
        int i23;
        char cCharAt9;
        int i24;
        Field fieldZza2;
        Field fieldZza3;
        int i25;
        char cCharAt10;
        int i26;
        char cCharAt11;
        int i27;
        char cCharAt12;
        int i28;
        char cCharAt13;
        char cCharAt14;
        if (!(zzffVar instanceof zzfv)) {
            ((zzgj) zzffVar).zzco();
            int i29 = zzdx.zze.zzkv;
            throw new NoSuchMethodError();
        }
        zzfv zzfvVar2 = (zzfv) zzffVar;
        int i30 = 0;
        boolean z3 = zzfvVar2.zzco() == zzdx.zze.zzkv;
        String strZzcw = zzfvVar2.zzcw();
        int length = strZzcw.length();
        int iCharAt3 = strZzcw.charAt(0);
        if (iCharAt3 >= 55296) {
            int i31 = iCharAt3 & R2.dimen.preference_seekbar_padding_start;
            int i32 = 1;
            int i33 = 13;
            while (true) {
                i2 = i32 + 1;
                cCharAt14 = strZzcw.charAt(i32);
                if (cCharAt14 < 55296) {
                    break;
                }
                i31 |= (cCharAt14 & 8191) << i33;
                i33 += 13;
                i32 = i2;
            }
            iCharAt3 = i31 | (cCharAt14 << i33);
        } else {
            i2 = 1;
        }
        int i34 = i2 + 1;
        int iCharAt4 = strZzcw.charAt(i2);
        if (iCharAt4 >= 55296) {
            int i35 = iCharAt4 & R2.dimen.preference_seekbar_padding_start;
            int i36 = 13;
            while (true) {
                i28 = i34 + 1;
                cCharAt13 = strZzcw.charAt(i34);
                if (cCharAt13 < 55296) {
                    break;
                }
                i35 |= (cCharAt13 & 8191) << i36;
                i36 += 13;
                i34 = i28;
            }
            iCharAt4 = i35 | (cCharAt13 << i36);
            i34 = i28;
        }
        if (iCharAt4 == 0) {
            i7 = 0;
            iCharAt = 0;
            i5 = 0;
            iCharAt2 = 0;
            i6 = 0;
            iArr = zzmr;
            i4 = 0;
        } else {
            int i37 = i34 + 1;
            int iCharAt5 = strZzcw.charAt(i34);
            if (iCharAt5 >= 55296) {
                int i38 = iCharAt5 & R2.dimen.preference_seekbar_padding_start;
                int i39 = 13;
                while (true) {
                    i15 = i37 + 1;
                    cCharAt8 = strZzcw.charAt(i37);
                    if (cCharAt8 < 55296) {
                        break;
                    }
                    i38 |= (cCharAt8 & 8191) << i39;
                    i39 += 13;
                    i37 = i15;
                }
                iCharAt5 = i38 | (cCharAt8 << i39);
                i37 = i15;
            }
            int i40 = i37 + 1;
            int iCharAt6 = strZzcw.charAt(i37);
            if (iCharAt6 >= 55296) {
                int i41 = iCharAt6 & R2.dimen.preference_seekbar_padding_start;
                int i42 = 13;
                while (true) {
                    i14 = i40 + 1;
                    cCharAt7 = strZzcw.charAt(i40);
                    if (cCharAt7 < 55296) {
                        break;
                    }
                    i41 |= (cCharAt7 & 8191) << i42;
                    i42 += 13;
                    i40 = i14;
                }
                iCharAt6 = i41 | (cCharAt7 << i42);
                i40 = i14;
            }
            int i43 = i40 + 1;
            iCharAt = strZzcw.charAt(i40);
            if (iCharAt >= 55296) {
                int i44 = iCharAt & R2.dimen.preference_seekbar_padding_start;
                int i45 = 13;
                while (true) {
                    i13 = i43 + 1;
                    cCharAt6 = strZzcw.charAt(i43);
                    if (cCharAt6 < 55296) {
                        break;
                    }
                    i44 |= (cCharAt6 & 8191) << i45;
                    i45 += 13;
                    i43 = i13;
                }
                iCharAt = i44 | (cCharAt6 << i45);
                i43 = i13;
            }
            int i46 = i43 + 1;
            int iCharAt7 = strZzcw.charAt(i43);
            if (iCharAt7 >= 55296) {
                int i47 = iCharAt7 & R2.dimen.preference_seekbar_padding_start;
                int i48 = 13;
                while (true) {
                    i12 = i46 + 1;
                    cCharAt5 = strZzcw.charAt(i46);
                    if (cCharAt5 < 55296) {
                        break;
                    }
                    i47 |= (cCharAt5 & 8191) << i48;
                    i48 += 13;
                    i46 = i12;
                }
                iCharAt7 = i47 | (cCharAt5 << i48);
                i46 = i12;
            }
            int i49 = i46 + 1;
            iCharAt2 = strZzcw.charAt(i46);
            if (iCharAt2 >= 55296) {
                int i50 = iCharAt2 & R2.dimen.preference_seekbar_padding_start;
                int i51 = 13;
                while (true) {
                    i11 = i49 + 1;
                    cCharAt4 = strZzcw.charAt(i49);
                    if (cCharAt4 < 55296) {
                        break;
                    }
                    i50 |= (cCharAt4 & 8191) << i51;
                    i51 += 13;
                    i49 = i11;
                }
                iCharAt2 = i50 | (cCharAt4 << i51);
                i49 = i11;
            }
            int i52 = i49 + 1;
            int iCharAt8 = strZzcw.charAt(i49);
            if (iCharAt8 >= 55296) {
                int i53 = iCharAt8 & R2.dimen.preference_seekbar_padding_start;
                int i54 = 13;
                while (true) {
                    i10 = i52 + 1;
                    cCharAt3 = strZzcw.charAt(i52);
                    if (cCharAt3 < 55296) {
                        break;
                    }
                    i53 |= (cCharAt3 & 8191) << i54;
                    i54 += 13;
                    i52 = i10;
                }
                iCharAt8 = i53 | (cCharAt3 << i54);
                i52 = i10;
            }
            int i55 = i52 + 1;
            int iCharAt9 = strZzcw.charAt(i52);
            if (iCharAt9 >= 55296) {
                int i56 = iCharAt9 & R2.dimen.preference_seekbar_padding_start;
                int i57 = i55;
                int i58 = 13;
                while (true) {
                    i9 = i57 + 1;
                    cCharAt2 = strZzcw.charAt(i57);
                    if (cCharAt2 < 55296) {
                        break;
                    }
                    i56 |= (cCharAt2 & 8191) << i58;
                    i58 += 13;
                    i57 = i9;
                }
                iCharAt9 = i56 | (cCharAt2 << i58);
                i3 = i9;
            } else {
                i3 = i55;
            }
            int i59 = i3 + 1;
            int iCharAt10 = strZzcw.charAt(i3);
            if (iCharAt10 >= 55296) {
                int i60 = iCharAt10 & R2.dimen.preference_seekbar_padding_start;
                int i61 = i59;
                int i62 = 13;
                while (true) {
                    i8 = i61 + 1;
                    cCharAt = strZzcw.charAt(i61);
                    if (cCharAt < 55296) {
                        break;
                    }
                    i60 |= (cCharAt & 8191) << i62;
                    i62 += 13;
                    i61 = i8;
                }
                iCharAt10 = i60 | (cCharAt << i62);
                i59 = i8;
            }
            int[] iArr2 = new int[iCharAt10 + iCharAt8 + iCharAt9];
            int i63 = (iCharAt5 << 1) + iCharAt6;
            i4 = iCharAt7;
            i5 = i63;
            i6 = iCharAt10;
            i30 = iCharAt5;
            i34 = i59;
            int i64 = iCharAt8;
            iArr = iArr2;
            i7 = i64;
        }
        Unsafe unsafe = zzms;
        Object[] objArrZzcx = zzfvVar2.zzcx();
        Class<?> cls3 = zzfvVar2.zzcq().getClass();
        int i65 = i34;
        int[] iArr3 = new int[iCharAt2 * 3];
        Object[] objArr = new Object[iCharAt2 << 1];
        int i66 = i6 + i7;
        int i67 = i6;
        int i68 = i65;
        int i69 = i66;
        int i70 = 0;
        int i71 = 0;
        while (i68 < length) {
            int i72 = i68 + 1;
            int iCharAt11 = strZzcw.charAt(i68);
            int i73 = length;
            if (iCharAt11 >= 55296) {
                int i74 = iCharAt11 & R2.dimen.preference_seekbar_padding_start;
                int i75 = i72;
                int i76 = 13;
                while (true) {
                    i27 = i75 + 1;
                    cCharAt12 = strZzcw.charAt(i75);
                    i16 = i6;
                    if (cCharAt12 < 55296) {
                        break;
                    }
                    i74 |= (cCharAt12 & 8191) << i76;
                    i76 += 13;
                    i75 = i27;
                    i6 = i16;
                }
                iCharAt11 = i74 | (cCharAt12 << i76);
                i17 = i27;
            } else {
                i16 = i6;
                i17 = i72;
            }
            int i77 = i17 + 1;
            int iCharAt12 = strZzcw.charAt(i17);
            if (iCharAt12 >= 55296) {
                int i78 = iCharAt12 & R2.dimen.preference_seekbar_padding_start;
                int i79 = i77;
                int i80 = 13;
                while (true) {
                    i26 = i79 + 1;
                    cCharAt11 = strZzcw.charAt(i79);
                    z2 = z3;
                    if (cCharAt11 < 55296) {
                        break;
                    }
                    i78 |= (cCharAt11 & 8191) << i80;
                    i80 += 13;
                    i79 = i26;
                    z3 = z2;
                }
                iCharAt12 = i78 | (cCharAt11 << i80);
                i18 = i26;
            } else {
                z2 = z3;
                i18 = i77;
            }
            int i81 = iCharAt12 & 255;
            int i82 = i4;
            if ((iCharAt12 & 1024) != 0) {
                iArr[i70] = i71;
                i70++;
            }
            int i83 = iCharAt;
            if (i81 >= 51) {
                int i84 = i18 + 1;
                int iCharAt13 = strZzcw.charAt(i18);
                char c3 = 55296;
                if (iCharAt13 >= 55296) {
                    int i85 = iCharAt13 & R2.dimen.preference_seekbar_padding_start;
                    int i86 = 13;
                    while (true) {
                        i25 = i84 + 1;
                        cCharAt10 = strZzcw.charAt(i84);
                        if (cCharAt10 < c3) {
                            break;
                        }
                        i85 |= (cCharAt10 & 8191) << i86;
                        i86 += 13;
                        i84 = i25;
                        c3 = 55296;
                    }
                    iCharAt13 = i85 | (cCharAt10 << i86);
                    i84 = i25;
                }
                int i87 = i81 - 51;
                int i88 = i84;
                if (i87 == 9 || i87 == 17) {
                    objArr[((i71 / 3) << 1) + 1] = objArrZzcx[i5];
                    i5++;
                } else if (i87 == 12 && (iCharAt3 & 1) == 1) {
                    objArr[((i71 / 3) << 1) + 1] = objArrZzcx[i5];
                    i5++;
                }
                int i89 = iCharAt13 << 1;
                Object obj = objArrZzcx[i89];
                if (obj instanceof Field) {
                    fieldZza2 = (Field) obj;
                } else {
                    fieldZza2 = zza(cls3, (String) obj);
                    objArrZzcx[i89] = fieldZza2;
                }
                zzfvVar = zzfvVar2;
                String str2 = strZzcw;
                int iObjectFieldOffset3 = (int) unsafe.objectFieldOffset(fieldZza2);
                int i90 = i89 + 1;
                Object obj2 = objArrZzcx[i90];
                if (obj2 instanceof Field) {
                    fieldZza3 = (Field) obj2;
                } else {
                    fieldZza3 = zza(cls3, (String) obj2);
                    objArrZzcx[i90] = fieldZza3;
                }
                cls2 = cls3;
                i20 = i5;
                i18 = i88;
                str = str2;
                i22 = 0;
                iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza3);
                iObjectFieldOffset = iObjectFieldOffset3;
                i21 = i30;
            } else {
                zzfvVar = zzfvVar2;
                String str3 = strZzcw;
                int i91 = i5 + 1;
                Field fieldZza4 = zza(cls3, (String) objArrZzcx[i5]);
                if (i81 == 9 || i81 == 17) {
                    i19 = 1;
                    objArr[((i71 / 3) << 1) + 1] = fieldZza4.getType();
                } else {
                    if (i81 == 27 || i81 == 49) {
                        i19 = 1;
                        i24 = i91 + 1;
                        objArr[((i71 / 3) << 1) + 1] = objArrZzcx[i91];
                    } else if (i81 == 12 || i81 == 30 || i81 == 44) {
                        i19 = 1;
                        if ((iCharAt3 & 1) == 1) {
                            i24 = i91 + 1;
                            objArr[((i71 / 3) << 1) + 1] = objArrZzcx[i91];
                        }
                    } else if (i81 == 50) {
                        int i92 = i67 + 1;
                        iArr[i67] = i71;
                        int i93 = (i71 / 3) << 1;
                        int i94 = i91 + 1;
                        objArr[i93] = objArrZzcx[i91];
                        if ((iCharAt12 & 2048) != 0) {
                            i91 = i94 + 1;
                            objArr[i93 + 1] = objArrZzcx[i94];
                            i67 = i92;
                            i19 = 1;
                        } else {
                            i91 = i94;
                            i19 = 1;
                            i67 = i92;
                        }
                    } else {
                        i19 = 1;
                    }
                    i91 = i24;
                }
                iObjectFieldOffset = (int) unsafe.objectFieldOffset(fieldZza4);
                if ((iCharAt3 & 1) != i19 || i81 > 17) {
                    i20 = i91;
                    i21 = i30;
                    cls2 = cls3;
                    str = str3;
                    iObjectFieldOffset2 = 0;
                    i22 = 0;
                    if (i81 >= 18 && i81 <= 49) {
                        iArr[i69] = iObjectFieldOffset;
                        i69++;
                    }
                } else {
                    int i95 = i18 + 1;
                    str = str3;
                    int iCharAt14 = str.charAt(i18);
                    if (iCharAt14 >= 55296) {
                        int i96 = iCharAt14 & R2.dimen.preference_seekbar_padding_start;
                        int i97 = 13;
                        while (true) {
                            i23 = i95 + 1;
                            cCharAt9 = str.charAt(i95);
                            if (cCharAt9 < 55296) {
                                break;
                            }
                            i96 |= (cCharAt9 & 8191) << i97;
                            i97 += 13;
                            i95 = i23;
                        }
                        iCharAt14 = i96 | (cCharAt9 << i97);
                        i95 = i23;
                    }
                    int i98 = (i30 << 1) + (iCharAt14 / 32);
                    Object obj3 = objArrZzcx[i98];
                    i20 = i91;
                    if (obj3 instanceof Field) {
                        fieldZza = (Field) obj3;
                    } else {
                        fieldZza = zza(cls3, (String) obj3);
                        objArrZzcx[i98] = fieldZza;
                    }
                    i21 = i30;
                    cls2 = cls3;
                    i22 = iCharAt14 % 32;
                    iObjectFieldOffset2 = (int) unsafe.objectFieldOffset(fieldZza);
                    i18 = i95;
                    if (i81 >= 18) {
                        iArr[i69] = iObjectFieldOffset;
                        i69++;
                    }
                }
            }
            int i99 = i71 + 1;
            iArr3[i71] = iCharAt11;
            int i100 = i99 + 1;
            iArr3[i99] = iObjectFieldOffset | ((iCharAt12 & 256) != 0 ? 268435456 : 0) | ((iCharAt12 & 512) != 0 ? 536870912 : 0) | (i81 << 20);
            i71 = i100 + 1;
            iArr3[i100] = (i22 << 20) | iObjectFieldOffset2;
            i30 = i21;
            strZzcw = str;
            i68 = i18;
            cls3 = cls2;
            i4 = i82;
            length = i73;
            i6 = i16;
            z3 = z2;
            iCharAt = i83;
            i5 = i20;
            zzfvVar2 = zzfvVar;
        }
        return new zzfl<>(iArr3, objArr, iCharAt, i4, zzfvVar2.zzcq(), z3, false, iArr, i6, i66, zzfmVar, zzerVar, zzgmVar, zzdnVar, zzfaVar);
    }

    private final zzfu zzae(int i2) {
        int i3 = (i2 / 3) << 1;
        zzfu zzfuVar = (zzfu) this.zzmu[i3];
        if (zzfuVar != null) {
            return zzfuVar;
        }
        zzfu<T> zzfuVarZze = zzft.zzcv().zze((Class) this.zzmu[i3 + 1]);
        this.zzmu[i3] = zzfuVarZze;
        return zzfuVarZze;
    }

    private final Object zzaf(int i2) {
        return this.zzmu[(i2 / 3) << 1];
    }

    private final int zzag(int i2) {
        return this.zzmt[i2 + 1];
    }

    private final int zzah(int i2) {
        return this.zzmt[i2 + 2];
    }

    private final void zzb(T t2, T t3, int i2) {
        int iZzag = zzag(i2);
        int i3 = this.zzmt[i2];
        long j2 = iZzag & 1048575;
        if (zza((zzfl<T>) t3, i3, i2)) {
            Object objZzo = zzgs.zzo(t2, j2);
            Object objZzo2 = zzgs.zzo(t3, j2);
            if (objZzo != null && objZzo2 != null) {
                zzgs.zza(t2, j2, zzeb.zza(objZzo, objZzo2));
                zzb((zzfl<T>) t2, i3, i2);
            } else if (objZzo2 != null) {
                zzgs.zza(t2, j2, objZzo2);
                zzb((zzfl<T>) t2, i3, i2);
            }
        }
    }

    private static List<?> zzd(Object obj, long j2) {
        return (List) zzgs.zzo(obj, j2);
    }

    private static <T> double zze(T t2, long j2) {
        return ((Double) zzgs.zzo(t2, j2)).doubleValue();
    }

    private static <T> int zzg(T t2, long j2) {
        return ((Integer) zzgs.zzo(t2, j2)).intValue();
    }

    private static <T> long zzh(T t2, long j2) {
        return ((Long) zzgs.zzo(t2, j2)).longValue();
    }

    private static <T> boolean zzi(T t2, long j2) {
        return ((Boolean) zzgs.zzo(t2, j2)).booleanValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x01c1  */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean equals(T r10, T r11) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.equals(java.lang.Object, java.lang.Object):boolean");
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final int hashCode(T t2) {
        int i2;
        int iZzk;
        int length = this.zzmt.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4 += 3) {
            int iZzag = zzag(i4);
            int i5 = this.zzmt[i4];
            long j2 = 1048575 & iZzag;
            int iHashCode = 37;
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzk(Double.doubleToLongBits(zzgs.zzn(t2, j2)));
                    i3 = i2 + iZzk;
                    break;
                case 1:
                    i2 = i3 * 53;
                    iZzk = Float.floatToIntBits(zzgs.zzm(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 2:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 3:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 4:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzj(t2, j2);
                    i3 = i2 + iZzk;
                    break;
                case 5:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 6:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzj(t2, j2);
                    i3 = i2 + iZzk;
                    break;
                case 7:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzg(zzgs.zzl(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 8:
                    i2 = i3 * 53;
                    iZzk = ((String) zzgs.zzo(t2, j2)).hashCode();
                    i3 = i2 + iZzk;
                    break;
                case 9:
                    Object objZzo = zzgs.zzo(t2, j2);
                    if (objZzo != null) {
                        iHashCode = objZzo.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 10:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzo(t2, j2).hashCode();
                    i3 = i2 + iZzk;
                    break;
                case 11:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzj(t2, j2);
                    i3 = i2 + iZzk;
                    break;
                case 12:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzj(t2, j2);
                    i3 = i2 + iZzk;
                    break;
                case 13:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzj(t2, j2);
                    i3 = i2 + iZzk;
                    break;
                case 14:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 15:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzj(t2, j2);
                    i3 = i2 + iZzk;
                    break;
                case 16:
                    i2 = i3 * 53;
                    iZzk = zzeb.zzk(zzgs.zzk(t2, j2));
                    i3 = i2 + iZzk;
                    break;
                case 17:
                    Object objZzo2 = zzgs.zzo(t2, j2);
                    if (objZzo2 != null) {
                        iHashCode = objZzo2.hashCode();
                    }
                    i3 = (i3 * 53) + iHashCode;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzo(t2, j2).hashCode();
                    i3 = i2 + iZzk;
                    break;
                case 50:
                    i2 = i3 * 53;
                    iZzk = zzgs.zzo(t2, j2).hashCode();
                    i3 = i2 + iZzk;
                    break;
                case 51:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzk(Double.doubleToLongBits(zze(t2, j2)));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 52:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = Float.floatToIntBits(zzf(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 53:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzk(zzh(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 54:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzk(zzh(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 55:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzg(t2, j2);
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 56:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzk(zzh(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 57:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzg(t2, j2);
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 58:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzg(zzi(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 59:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = ((String) zzgs.zzo(t2, j2)).hashCode();
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 60:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzgs.zzo(t2, j2).hashCode();
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 61:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzgs.zzo(t2, j2).hashCode();
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 62:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzg(t2, j2);
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 63:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzg(t2, j2);
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 64:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzg(t2, j2);
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 65:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzk(zzh(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 66:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzg(t2, j2);
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 67:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzeb.zzk(zzh(t2, j2));
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
                case 68:
                    if (zza((zzfl<T>) t2, i5, i4)) {
                        i2 = i3 * 53;
                        iZzk = zzgs.zzo(t2, j2).hashCode();
                        i3 = i2 + iZzk;
                        break;
                    } else {
                        break;
                    }
            }
        }
        int iHashCode2 = (i3 * 53) + this.zzmo.zzp(t2).hashCode();
        return this.zzmp ? (iHashCode2 * 53) + this.zzmq.zzd(t2).hashCode() : iHashCode2;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzc(T t2, T t3) {
        t3.getClass();
        for (int i2 = 0; i2 < this.zzmt.length; i2 += 3) {
            int iZzag = zzag(i2);
            long j2 = 1048575 & iZzag;
            int i3 = this.zzmt[i2];
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza(t2, j2, zzgs.zzn(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 1:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzm(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 2:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzk(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 3:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzk(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzj(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzk(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzj(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 7:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza(t2, j2, zzgs.zzl(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 8:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza(t2, j2, zzgs.zzo(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 9:
                    zza(t2, t3, i2);
                    break;
                case 10:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza(t2, j2, zzgs.zzo(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 11:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzj(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 12:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzj(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 13:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzj(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 14:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzk(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 15:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzj(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 16:
                    if (zza((zzfl<T>) t3, i2)) {
                        zzgs.zza((Object) t2, j2, zzgs.zzk(t3, j2));
                        zzb((zzfl<T>) t2, i2);
                        break;
                    } else {
                        break;
                    }
                case 17:
                    zza(t2, t3, i2);
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    this.zzne.zza(t2, t3, j2);
                    break;
                case 50:
                    zzfw.zza(this.zznf, t2, t3, j2);
                    break;
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                    if (zza((zzfl<T>) t3, i3, i2)) {
                        zzgs.zza(t2, j2, zzgs.zzo(t3, j2));
                        zzb((zzfl<T>) t2, i3, i2);
                        break;
                    } else {
                        break;
                    }
                case 60:
                    zzb(t2, t3, i2);
                    break;
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                    if (zza((zzfl<T>) t3, i3, i2)) {
                        zzgs.zza(t2, j2, zzgs.zzo(t3, j2));
                        zzb((zzfl<T>) t2, i3, i2);
                        break;
                    } else {
                        break;
                    }
                case 68:
                    zzb(t2, t3, i2);
                    break;
            }
        }
        zzfw.zza(this.zzmo, t2, t3);
        if (this.zzmp) {
            zzfw.zza(this.zzmq, t2, t3);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzf(T t2) {
        int i2;
        int i3 = this.zznb;
        while (true) {
            i2 = this.zznc;
            if (i3 >= i2) {
                break;
            }
            long jZzag = zzag(this.zzna[i3]) & 1048575;
            Object objZzo = zzgs.zzo(t2, jZzag);
            if (objZzo != null) {
                zzgs.zza(t2, jZzag, this.zznf.zzj(objZzo));
            }
            i3++;
        }
        int length = this.zzna.length;
        while (i2 < length) {
            this.zzne.zza(t2, this.zzna[i2]);
            i2++;
        }
        this.zzmo.zzf(t2);
        if (this.zzmp) {
            this.zzmq.zzf(t2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ca  */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.google.android.gms.internal.icing.zzfu] */
    /* JADX WARN: Type inference failed for: r6v10 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12, types: [com.google.android.gms.internal.icing.zzfu] */
    /* JADX WARN: Type inference failed for: r6v15 */
    /* JADX WARN: Type inference failed for: r6v16 */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean zzm(T r14) {
        /*
            Method dump skipped, instructions count: 285
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zzm(java.lang.Object):boolean");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:421:0x090b A[PHI: r6
      0x090b: PHI (r6v4 int) = 
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v16 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v1 int)
      (r6v17 int)
      (r6v1 int)
     binds: [B:256:0x0545, B:459:0x09b0, B:453:0x0994, B:450:0x0982, B:447:0x0973, B:444:0x0966, B:441:0x0959, B:437:0x094e, B:434:0x0943, B:431:0x0936, B:428:0x0929, B:425:0x0916, B:396:0x081f, B:390:0x0802, B:384:0x07e5, B:378:0x07c8, B:372:0x07aa, B:366:0x078c, B:360:0x076e, B:354:0x0750, B:348:0x0732, B:342:0x0714, B:336:0x06f6, B:330:0x06d8, B:324:0x06ba, B:318:0x069c, B:313:0x0668, B:310:0x065b, B:307:0x064b, B:304:0x063b, B:301:0x062b, B:298:0x061d, B:295:0x0610, B:292:0x0603, B:286:0x05e5, B:283:0x05d1, B:280:0x05bf, B:277:0x05af, B:274:0x059f, B:439:0x0955, B:271:0x0592, B:268:0x0584, B:265:0x0574, B:262:0x0564, B:420:0x090a, B:259:0x054e] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int zzn(T r20) {
        /*
            Method dump skipped, instructions count: 2986
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zzn(java.lang.Object):int");
    }

    private static <T> float zzf(T t2, long j2) {
        return ((Float) zzgs.zzo(t2, j2)).floatValue();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void zzb(T r18, com.google.android.gms.internal.icing.zzhg r19) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zzb(java.lang.Object, com.google.android.gms.internal.icing.zzhg):void");
    }

    private final boolean zzc(T t2, T t3, int i2) {
        return zza((zzfl<T>) t2, i2) == zza((zzfl<T>) t3, i2);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String string = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + name.length() + String.valueOf(string).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(string);
            throw new RuntimeException(sb.toString());
        }
    }

    private final void zza(T t2, T t3, int i2) {
        long jZzag = zzag(i2) & 1048575;
        if (zza((zzfl<T>) t3, i2)) {
            Object objZzo = zzgs.zzo(t2, jZzag);
            Object objZzo2 = zzgs.zzo(t3, jZzag);
            if (objZzo != null && objZzo2 != null) {
                zzgs.zza(t2, jZzag, zzeb.zza(objZzo, objZzo2));
                zzb((zzfl<T>) t2, i2);
            } else if (objZzo2 != null) {
                zzgs.zza(t2, jZzag, objZzo2);
                zzb((zzfl<T>) t2, i2);
            }
        }
    }

    private static <UT, UB> int zza(zzgm<UT, UB> zzgmVar, T t2) {
        return zzgmVar.zzn(zzgmVar.zzp(t2));
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x054a  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    @Override // com.google.android.gms.internal.icing.zzfu
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zza(T r14, com.google.android.gms.internal.icing.zzhg r15) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 2916
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.icing.zzfl.zza(java.lang.Object, com.google.android.gms.internal.icing.zzhg):void");
    }

    private final void zzb(T t2, int i2) {
        if (this.zzmy) {
            return;
        }
        int iZzah = zzah(i2);
        long j2 = iZzah & 1048575;
        zzgs.zza((Object) t2, j2, zzgs.zzj(t2, j2) | (1 << (iZzah >>> 20)));
    }

    private final void zzb(T t2, int i2, int i3) {
        zzgs.zza((Object) t2, zzah(i3) & 1048575, i2);
    }

    private final <K, V> void zza(zzhg zzhgVar, int i2, Object obj, int i3) throws IOException {
        if (obj != null) {
            zzhgVar.zza(i2, this.zznf.zzk(zzaf(i3)), this.zznf.zzi(obj));
        }
    }

    private static <UT, UB> void zza(zzgm<UT, UB> zzgmVar, T t2, zzhg zzhgVar) throws IOException {
        zzgmVar.zza(zzgmVar.zzp(t2), zzhgVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean zza(Object obj, int i2, zzfu zzfuVar) {
        return zzfuVar.zzm(zzgs.zzo(obj, i2 & 1048575));
    }

    private static void zza(int i2, Object obj, zzhg zzhgVar) throws IOException {
        if (obj instanceof String) {
            zzhgVar.zza(i2, (String) obj);
        } else {
            zzhgVar.zza(i2, (zzct) obj);
        }
    }

    private final boolean zza(T t2, int i2, int i3, int i4) {
        if (this.zzmy) {
            return zza((zzfl<T>) t2, i2);
        }
        return (i3 & i4) != 0;
    }

    private final boolean zza(T t2, int i2) {
        if (this.zzmy) {
            int iZzag = zzag(i2);
            long j2 = iZzag & 1048575;
            switch ((iZzag & 267386880) >>> 20) {
                case 0:
                    return zzgs.zzn(t2, j2) != 0.0d;
                case 1:
                    return zzgs.zzm(t2, j2) != 0.0f;
                case 2:
                    return zzgs.zzk(t2, j2) != 0;
                case 3:
                    return zzgs.zzk(t2, j2) != 0;
                case 4:
                    return zzgs.zzj(t2, j2) != 0;
                case 5:
                    return zzgs.zzk(t2, j2) != 0;
                case 6:
                    return zzgs.zzj(t2, j2) != 0;
                case 7:
                    return zzgs.zzl(t2, j2);
                case 8:
                    Object objZzo = zzgs.zzo(t2, j2);
                    if (objZzo instanceof String) {
                        return !((String) objZzo).isEmpty();
                    }
                    if (objZzo instanceof zzct) {
                        return !zzct.zzgi.equals(objZzo);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzgs.zzo(t2, j2) != null;
                case 10:
                    return !zzct.zzgi.equals(zzgs.zzo(t2, j2));
                case 11:
                    return zzgs.zzj(t2, j2) != 0;
                case 12:
                    return zzgs.zzj(t2, j2) != 0;
                case 13:
                    return zzgs.zzj(t2, j2) != 0;
                case 14:
                    return zzgs.zzk(t2, j2) != 0;
                case 15:
                    return zzgs.zzj(t2, j2) != 0;
                case 16:
                    return zzgs.zzk(t2, j2) != 0;
                case 17:
                    return zzgs.zzo(t2, j2) != null;
                default:
                    throw new IllegalArgumentException();
            }
        }
        int iZzah = zzah(i2);
        return (zzgs.zzj(t2, (long) (iZzah & 1048575)) & (1 << (iZzah >>> 20))) != 0;
    }

    private final boolean zza(T t2, int i2, int i3) {
        return zzgs.zzj(t2, (long) (zzah(i3) & 1048575)) == i2;
    }
}
