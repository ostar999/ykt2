package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes3.dex */
public abstract class zzdk extends zzcu {
    private static final Logger logger = Logger.getLogger(zzdk.class.getName());
    private static final boolean zzgx = zzgs.zzdn();
    zzdm zzgy;

    public static class zza extends IOException {
        public zza() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }

        public zza(Throwable th) {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.", th);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public zza(String str, Throwable th) {
            String strValueOf = String.valueOf(str);
            super(strValueOf.length() != 0 ? "CodedOutputStream was writing to a flat byte array and ran out of space.: ".concat(strValueOf) : new String("CodedOutputStream was writing to a flat byte array and ran out of space.: "), th);
        }
    }

    public static class zzb extends zzdk {
        private final byte[] buffer;
        private final int limit;
        private final int offset;
        private int position;

        public zzb(byte[] bArr, int i2, int i3) {
            super();
            if (bArr == null) {
                throw new NullPointerException("buffer");
            }
            int i4 = i3 + 0;
            if ((i3 | 0 | (bArr.length - i4)) < 0) {
                throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(bArr.length), 0, Integer.valueOf(i3)));
            }
            this.buffer = bArr;
            this.offset = 0;
            this.position = 0;
            this.limit = i4;
        }

        private final void write(byte[] bArr, int i2, int i3) throws IOException {
            try {
                System.arraycopy(bArr, i2, this.buffer, this.position, i3);
                this.position += i3;
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), Integer.valueOf(i3)), e2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i2, long j2) throws IOException {
            zzb(i2, 0);
            zzb(j2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final int zzau() {
            return this.limit - this.position;
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(int i2, int i3) throws IOException {
            zzp((i2 << 3) | i3);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzc(int i2, int i3) throws IOException {
            zzb(i2, 0);
            zzo(i3);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzd(int i2, int i3) throws IOException {
            zzb(i2, 0);
            zzp(i3);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzf(int i2, int i3) throws IOException {
            zzb(i2, 5);
            zzr(i3);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzo(int i2) throws IOException {
            if (i2 >= 0) {
                zzp(i2);
            } else {
                zzb(i2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzp(int i2) throws IOException {
            if (!zzdk.zzgx || zzcs.zzal() || zzau() < 5) {
                while ((i2 & (-128)) != 0) {
                    try {
                        byte[] bArr = this.buffer;
                        int i3 = this.position;
                        this.position = i3 + 1;
                        bArr[i3] = (byte) ((i2 & 127) | 128);
                        i2 >>>= 7;
                    } catch (IndexOutOfBoundsException e2) {
                        throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
                    }
                }
                byte[] bArr2 = this.buffer;
                int i4 = this.position;
                this.position = i4 + 1;
                bArr2[i4] = (byte) i2;
                return;
            }
            if ((i2 & (-128)) == 0) {
                byte[] bArr3 = this.buffer;
                int i5 = this.position;
                this.position = i5 + 1;
                zzgs.zza(bArr3, i5, (byte) i2);
                return;
            }
            byte[] bArr4 = this.buffer;
            int i6 = this.position;
            this.position = i6 + 1;
            zzgs.zza(bArr4, i6, (byte) (i2 | 128));
            int i7 = i2 >>> 7;
            if ((i7 & (-128)) == 0) {
                byte[] bArr5 = this.buffer;
                int i8 = this.position;
                this.position = i8 + 1;
                zzgs.zza(bArr5, i8, (byte) i7);
                return;
            }
            byte[] bArr6 = this.buffer;
            int i9 = this.position;
            this.position = i9 + 1;
            zzgs.zza(bArr6, i9, (byte) (i7 | 128));
            int i10 = i7 >>> 7;
            if ((i10 & (-128)) == 0) {
                byte[] bArr7 = this.buffer;
                int i11 = this.position;
                this.position = i11 + 1;
                zzgs.zza(bArr7, i11, (byte) i10);
                return;
            }
            byte[] bArr8 = this.buffer;
            int i12 = this.position;
            this.position = i12 + 1;
            zzgs.zza(bArr8, i12, (byte) (i10 | 128));
            int i13 = i10 >>> 7;
            if ((i13 & (-128)) == 0) {
                byte[] bArr9 = this.buffer;
                int i14 = this.position;
                this.position = i14 + 1;
                zzgs.zza(bArr9, i14, (byte) i13);
                return;
            }
            byte[] bArr10 = this.buffer;
            int i15 = this.position;
            this.position = i15 + 1;
            zzgs.zza(bArr10, i15, (byte) (i13 | 128));
            byte[] bArr11 = this.buffer;
            int i16 = this.position;
            this.position = i16 + 1;
            zzgs.zza(bArr11, i16, (byte) (i13 >>> 7));
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzq(String str) throws IOException {
            int i2 = this.position;
            try {
                int iZzu = zzdk.zzu(str.length() * 3);
                int iZzu2 = zzdk.zzu(str.length());
                if (iZzu2 != iZzu) {
                    zzp(zzgv.zza(str));
                    this.position = zzgv.zza(str, this.buffer, this.position, zzau());
                    return;
                }
                int i3 = i2 + iZzu2;
                this.position = i3;
                int iZza = zzgv.zza(str, this.buffer, i3, zzau());
                this.position = i2;
                zzp((iZza - i2) - iZzu2);
                this.position = iZza;
            } catch (zzgz e2) {
                this.position = i2;
                zza(str, e2);
            } catch (IndexOutOfBoundsException e3) {
                throw new zza(e3);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzr(int i2) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i3 = this.position;
                int i4 = i3 + 1;
                bArr[i3] = (byte) i2;
                int i5 = i4 + 1;
                bArr[i4] = (byte) (i2 >> 8);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (i2 >> 16);
                this.position = i6 + 1;
                bArr[i6] = (byte) (i2 >>> 24);
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(byte[] bArr, int i2, int i3) throws IOException {
            zzp(i3);
            write(bArr, 0, i3);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i2, boolean z2) throws IOException {
            zzb(i2, 0);
            zzc(z2 ? (byte) 1 : (byte) 0);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzc(int i2, long j2) throws IOException {
            zzb(i2, 1);
            zzd(j2);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzd(long j2) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                int i3 = i2 + 1;
                bArr[i2] = (byte) j2;
                int i4 = i3 + 1;
                bArr[i3] = (byte) (j2 >> 8);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (j2 >> 16);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (j2 >> 24);
                int i7 = i6 + 1;
                bArr[i6] = (byte) (j2 >> 32);
                int i8 = i7 + 1;
                bArr[i7] = (byte) (j2 >> 40);
                int i9 = i8 + 1;
                bArr[i8] = (byte) (j2 >> 48);
                this.position = i9 + 1;
                bArr[i9] = (byte) (j2 >> 56);
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(int i2, zzct zzctVar) throws IOException {
            zzb(1, 3);
            zzd(2, i2);
            zza(3, zzctVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i2, String str) throws IOException {
            zzb(i2, 2);
            zzq(str);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzc(byte b3) throws IOException {
            try {
                byte[] bArr = this.buffer;
                int i2 = this.position;
                this.position = i2 + 1;
                bArr[i2] = b3;
            } catch (IndexOutOfBoundsException e2) {
                throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i2, zzct zzctVar) throws IOException {
            zzb(i2, 2);
            zza(zzctVar);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(zzfh zzfhVar) throws IOException {
            zzp(zzfhVar.zzbl());
            zzfhVar.zzb(this);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(zzct zzctVar) throws IOException {
            zzp(zzctVar.size());
            zzctVar.zza(this);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zzb(long j2) throws IOException {
            if (zzdk.zzgx && zzau() >= 10) {
                while ((j2 & (-128)) != 0) {
                    byte[] bArr = this.buffer;
                    int i2 = this.position;
                    this.position = i2 + 1;
                    zzgs.zza(bArr, i2, (byte) ((((int) j2) & 127) | 128));
                    j2 >>>= 7;
                }
                byte[] bArr2 = this.buffer;
                int i3 = this.position;
                this.position = i3 + 1;
                zzgs.zza(bArr2, i3, (byte) j2);
                return;
            }
            while ((j2 & (-128)) != 0) {
                try {
                    byte[] bArr3 = this.buffer;
                    int i4 = this.position;
                    this.position = i4 + 1;
                    bArr3[i4] = (byte) ((((int) j2) & 127) | 128);
                    j2 >>>= 7;
                } catch (IndexOutOfBoundsException e2) {
                    throw new zza(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.position), Integer.valueOf(this.limit), 1), e2);
                }
            }
            byte[] bArr4 = this.buffer;
            int i5 = this.position;
            this.position = i5 + 1;
            bArr4[i5] = (byte) j2;
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i2, zzfh zzfhVar, zzfu zzfuVar) throws IOException {
            zzb(i2, 2);
            zzcm zzcmVar = (zzcm) zzfhVar;
            int iZzae = zzcmVar.zzae();
            if (iZzae == -1) {
                iZzae = zzfuVar.zzn(zzcmVar);
                zzcmVar.zzg(iZzae);
            }
            zzp(iZzae);
            zzfuVar.zza(zzfhVar, this.zzgy);
        }

        @Override // com.google.android.gms.internal.icing.zzdk
        public final void zza(int i2, zzfh zzfhVar) throws IOException {
            zzb(1, 3);
            zzd(2, i2);
            zzb(3, 2);
            zzb(zzfhVar);
            zzb(1, 4);
        }

        @Override // com.google.android.gms.internal.icing.zzcu
        public final void zza(byte[] bArr, int i2, int i3) throws IOException {
            write(bArr, i2, i3);
        }
    }

    private zzdk() {
    }

    @Deprecated
    public static int zzaa(int i2) {
        return zzu(i2);
    }

    public static int zzb(double d3) {
        return 8;
    }

    public static int zzb(float f2) {
        return 4;
    }

    public static zzdk zzb(byte[] bArr) {
        return new zzb(bArr, 0, bArr.length);
    }

    public static int zzd(int i2, long j2) {
        return zzs(i2) + zzf(j2);
    }

    public static int zzf(int i2, long j2) {
        return zzs(i2) + zzf(zzj(j2));
    }

    public static int zzf(long j2) {
        int i2;
        if (((-128) & j2) == 0) {
            return 1;
        }
        if (j2 < 0) {
            return 10;
        }
        if (((-34359738368L) & j2) != 0) {
            j2 >>>= 28;
            i2 = 6;
        } else {
            i2 = 2;
        }
        if (((-2097152) & j2) != 0) {
            i2 += 2;
            j2 >>>= 14;
        }
        return (j2 & (-16384)) != 0 ? i2 + 1 : i2;
    }

    public static int zzf(boolean z2) {
        return 1;
    }

    public static int zzg(int i2, int i3) {
        return zzs(i2) + zzt(i3);
    }

    public static int zzh(int i2, int i3) {
        return zzs(i2) + zzu(i3);
    }

    public static int zzh(long j2) {
        return 8;
    }

    public static int zzi(int i2, int i3) {
        return zzs(i2) + zzu(zzz(i3));
    }

    public static int zzi(long j2) {
        return 8;
    }

    public static int zzj(int i2, int i3) {
        return zzs(i2) + 4;
    }

    private static long zzj(long j2) {
        return (j2 >> 63) ^ (j2 << 1);
    }

    public static int zzk(int i2, int i3) {
        return zzs(i2) + 4;
    }

    public static int zzl(int i2, int i3) {
        return zzs(i2) + zzt(i3);
    }

    public static int zzr(String str) {
        int length;
        try {
            length = zzgv.zza(str);
        } catch (zzgz unused) {
            length = str.getBytes(zzeb.UTF_8).length;
        }
        return zzu(length) + length;
    }

    public static int zzs(int i2) {
        return zzu(i2 << 3);
    }

    public static int zzt(int i2) {
        if (i2 >= 0) {
            return zzu(i2);
        }
        return 10;
    }

    public static int zzu(int i2) {
        if ((i2 & (-128)) == 0) {
            return 1;
        }
        if ((i2 & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i2) == 0) {
            return 3;
        }
        return (i2 & (-268435456)) == 0 ? 4 : 5;
    }

    public static int zzv(int i2) {
        return zzu(zzz(i2));
    }

    public static int zzw(int i2) {
        return 4;
    }

    public static int zzx(int i2) {
        return 4;
    }

    public static int zzy(int i2) {
        return zzt(i2);
    }

    private static int zzz(int i2) {
        return (i2 >> 31) ^ (i2 << 1);
    }

    public final void zza(int i2, float f2) throws IOException {
        zzf(i2, Float.floatToRawIntBits(f2));
    }

    public abstract void zza(int i2, long j2) throws IOException;

    public abstract void zza(int i2, zzct zzctVar) throws IOException;

    public abstract void zza(int i2, zzfh zzfhVar) throws IOException;

    public abstract void zza(int i2, zzfh zzfhVar, zzfu zzfuVar) throws IOException;

    public abstract void zza(int i2, String str) throws IOException;

    public abstract void zza(int i2, boolean z2) throws IOException;

    public abstract void zza(zzct zzctVar) throws IOException;

    public abstract int zzau();

    public final void zzav() {
        if (zzau() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public abstract void zzb(int i2, int i3) throws IOException;

    public abstract void zzb(int i2, zzct zzctVar) throws IOException;

    public abstract void zzb(long j2) throws IOException;

    public abstract void zzb(zzfh zzfhVar) throws IOException;

    public abstract void zzb(byte[] bArr, int i2, int i3) throws IOException;

    public abstract void zzc(byte b3) throws IOException;

    public abstract void zzc(int i2, int i3) throws IOException;

    public abstract void zzc(int i2, long j2) throws IOException;

    public final void zzc(long j2) throws IOException {
        zzb(zzj(j2));
    }

    public abstract void zzd(int i2, int i3) throws IOException;

    public abstract void zzd(long j2) throws IOException;

    public final void zze(int i2, int i3) throws IOException {
        zzd(i2, zzz(i3));
    }

    public abstract void zzf(int i2, int i3) throws IOException;

    public abstract void zzo(int i2) throws IOException;

    public abstract void zzp(int i2) throws IOException;

    public final void zzq(int i2) throws IOException {
        zzp(zzz(i2));
    }

    public abstract void zzq(String str) throws IOException;

    public abstract void zzr(int i2) throws IOException;

    public static int zzc(int i2, zzct zzctVar) {
        int iZzs = zzs(i2);
        int size = zzctVar.size();
        return iZzs + zzu(size) + size;
    }

    public static int zzg(int i2, long j2) {
        return zzs(i2) + 8;
    }

    public static int zzh(int i2, long j2) {
        return zzs(i2) + 8;
    }

    public final void zza(int i2, double d3) throws IOException {
        zzc(i2, Double.doubleToRawLongBits(d3));
    }

    public final void zze(boolean z2) throws IOException {
        zzc(z2 ? (byte) 1 : (byte) 0);
    }

    public static int zzd(int i2, zzct zzctVar) {
        return (zzs(1) << 1) + zzh(2, i2) + zzc(3, zzctVar);
    }

    public static int zze(int i2, long j2) {
        return zzs(i2) + zzf(j2);
    }

    public static int zzg(long j2) {
        return zzf(zzj(j2));
    }

    public final void zza(float f2) throws IOException {
        zzr(Float.floatToRawIntBits(f2));
    }

    public final void zzb(int i2, long j2) throws IOException {
        zza(i2, zzj(j2));
    }

    public static int zzb(int i2, float f2) {
        return zzs(i2) + 4;
    }

    public static int zze(long j2) {
        return zzf(j2);
    }

    public final void zza(double d3) throws IOException {
        zzd(Double.doubleToRawLongBits(d3));
    }

    public static int zza(int i2, zzem zzemVar) {
        int iZzs = zzs(i2);
        int iZzbl = zzemVar.zzbl();
        return iZzs + zzu(iZzbl) + iZzbl;
    }

    public static int zzb(int i2, double d3) {
        return zzs(i2) + 8;
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        return zzu(length) + length;
    }

    public static int zzb(int i2, boolean z2) {
        return zzs(i2) + 1;
    }

    @Deprecated
    public static int zzd(zzfh zzfhVar) {
        return zzfhVar.zzbl();
    }

    public static int zzb(int i2, String str) {
        return zzs(i2) + zzr(str);
    }

    public static int zzc(zzfh zzfhVar) {
        int iZzbl = zzfhVar.zzbl();
        return zzu(iZzbl) + iZzbl;
    }

    public static int zza(zzem zzemVar) {
        int iZzbl = zzemVar.zzbl();
        return zzu(iZzbl) + iZzbl;
    }

    public static int zzb(int i2, zzfh zzfhVar, zzfu zzfuVar) {
        return zzs(i2) + zza(zzfhVar, zzfuVar);
    }

    public static int zzb(int i2, zzfh zzfhVar) {
        return (zzs(1) << 1) + zzh(2, i2) + zzs(3) + zzc(zzfhVar);
    }

    @Deprecated
    public static int zzc(int i2, zzfh zzfhVar, zzfu zzfuVar) {
        int iZzs = zzs(i2) << 1;
        zzcm zzcmVar = (zzcm) zzfhVar;
        int iZzae = zzcmVar.zzae();
        if (iZzae == -1) {
            iZzae = zzfuVar.zzn(zzcmVar);
            zzcmVar.zzg(iZzae);
        }
        return iZzs + iZzae;
    }

    public static int zza(zzfh zzfhVar, zzfu zzfuVar) {
        zzcm zzcmVar = (zzcm) zzfhVar;
        int iZzae = zzcmVar.zzae();
        if (iZzae == -1) {
            iZzae = zzfuVar.zzn(zzcmVar);
            zzcmVar.zzg(iZzae);
        }
        return zzu(iZzae) + iZzae;
    }

    public static int zzb(int i2, zzem zzemVar) {
        return (zzs(1) << 1) + zzh(2, i2) + zza(3, zzemVar);
    }

    public static int zzb(zzct zzctVar) {
        int size = zzctVar.size();
        return zzu(size) + size;
    }

    public final void zza(String str, zzgz zzgzVar) throws IOException {
        logger.logp(Level.WARNING, "com.google.protobuf.CodedOutputStream", "inefficientWriteStringNoTag", "Converting ill-formed UTF-16. Your Protocol Buffer will not round trip correctly!", (Throwable) zzgzVar);
        byte[] bytes = str.getBytes(zzeb.UTF_8);
        try {
            zzp(bytes.length);
            zza(bytes, 0, bytes.length);
        } catch (zza e2) {
            throw e2;
        } catch (IndexOutOfBoundsException e3) {
            throw new zza(e3);
        }
    }
}
