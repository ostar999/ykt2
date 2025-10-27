package com.google.android.gms.internal.icing;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* loaded from: classes3.dex */
final class zzgs {
    private static final Logger logger = Logger.getLogger(zzgs.class.getName());
    private static final Class<?> zzgg;
    private static final boolean zzgx;
    private static final Unsafe zzms;
    private static final boolean zzol;
    private static final boolean zzom;
    private static final zzd zzon;
    private static final boolean zzoo;
    private static final long zzop;
    private static final long zzoq;
    private static final long zzor;
    private static final long zzos;
    private static final long zzot;
    private static final long zzou;
    private static final long zzov;
    private static final long zzow;
    private static final long zzox;
    private static final long zzoy;
    private static final long zzoz;
    private static final long zzpa;
    private static final long zzpb;
    private static final long zzpc;
    private static final int zzpd;
    static final boolean zzpe;

    public static final class zzb extends zzd {
        public zzb(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, boolean z2) {
            this.zzph.putBoolean(obj, j2, z2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zze(Object obj, long j2, byte b3) {
            this.zzph.putByte(obj, j2, b3);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final boolean zzl(Object obj, long j2) {
            return this.zzph.getBoolean(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final float zzm(Object obj, long j2) {
            return this.zzph.getFloat(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final double zzn(Object obj, long j2) {
            return this.zzph.getDouble(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final byte zzx(Object obj, long j2) {
            return this.zzph.getByte(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, float f2) {
            this.zzph.putFloat(obj, j2, f2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, double d3) {
            this.zzph.putDouble(obj, j2, d3);
        }
    }

    public static abstract class zzd {
        Unsafe zzph;

        public zzd(Unsafe unsafe) {
            this.zzph = unsafe;
        }

        public abstract void zza(Object obj, long j2, double d3);

        public abstract void zza(Object obj, long j2, float f2);

        public final void zza(Object obj, long j2, int i2) {
            this.zzph.putInt(obj, j2, i2);
        }

        public abstract void zza(Object obj, long j2, boolean z2);

        public abstract void zze(Object obj, long j2, byte b3);

        public final int zzj(Object obj, long j2) {
            return this.zzph.getInt(obj, j2);
        }

        public final long zzk(Object obj, long j2) {
            return this.zzph.getLong(obj, j2);
        }

        public abstract boolean zzl(Object obj, long j2);

        public abstract float zzm(Object obj, long j2);

        public abstract double zzn(Object obj, long j2);

        public abstract byte zzx(Object obj, long j2);

        public final void zza(Object obj, long j2, long j3) {
            this.zzph.putLong(obj, j2, j3);
        }
    }

    static {
        Unsafe unsafeZzdp = zzdp();
        zzms = unsafeZzdp;
        zzgg = zzcs.zzam();
        boolean zZzj = zzj(Long.TYPE);
        zzol = zZzj;
        boolean zZzj2 = zzj(Integer.TYPE);
        zzom = zZzj2;
        zzd zzbVar = null;
        if (unsafeZzdp != null) {
            if (!zzcs.zzal()) {
                zzbVar = new zzb(unsafeZzdp);
            } else if (zZzj) {
                zzbVar = new zzc(unsafeZzdp);
            } else if (zZzj2) {
                zzbVar = new zza(unsafeZzdp);
            }
        }
        zzon = zzbVar;
        zzoo = zzdr();
        zzgx = zzdq();
        long jZzh = zzh(byte[].class);
        zzop = jZzh;
        zzoq = zzh(boolean[].class);
        zzor = zzi(boolean[].class);
        zzos = zzh(int[].class);
        zzot = zzi(int[].class);
        zzou = zzh(long[].class);
        zzov = zzi(long[].class);
        zzow = zzh(float[].class);
        zzox = zzi(float[].class);
        zzoy = zzh(double[].class);
        zzoz = zzi(double[].class);
        zzpa = zzh(Object[].class);
        zzpb = zzi(Object[].class);
        Field fieldZzds = zzds();
        zzpc = (fieldZzds == null || zzbVar == null) ? -1L : zzbVar.zzph.objectFieldOffset(fieldZzds);
        zzpd = (int) (jZzh & 7);
        zzpe = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private zzgs() {
    }

    public static void zza(Object obj, long j2, int i2) {
        zzon.zza(obj, j2, i2);
    }

    private static Field zzb(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzc(Object obj, long j2, boolean z2) {
        zzb(obj, j2, z2 ? (byte) 1 : (byte) 0);
    }

    public static boolean zzdn() {
        return zzgx;
    }

    public static boolean zzdo() {
        return zzoo;
    }

    public static Unsafe zzdp() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzgu());
        } catch (Throwable unused) {
            return null;
        }
    }

    private static boolean zzdq() {
        Unsafe unsafe = zzms;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            Class<?> cls2 = Long.TYPE;
            cls.getMethod("getInt", Object.class, cls2);
            cls.getMethod("putInt", Object.class, cls2, Integer.TYPE);
            cls.getMethod("getLong", Object.class, cls2);
            cls.getMethod("putLong", Object.class, cls2, cls2);
            cls.getMethod("getObject", Object.class, cls2);
            cls.getMethod("putObject", Object.class, cls2, Object.class);
            if (zzcs.zzal()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, cls2);
            cls.getMethod("putByte", Object.class, cls2, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, cls2);
            cls.getMethod("putBoolean", Object.class, cls2, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, cls2);
            cls.getMethod("putFloat", Object.class, cls2, Float.TYPE);
            cls.getMethod("getDouble", Object.class, cls2);
            cls.getMethod("putDouble", Object.class, cls2, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(strValueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", sb.toString());
            return false;
        }
    }

    private static boolean zzdr() {
        Unsafe unsafe = zzms;
        if (unsafe == null) {
            return false;
        }
        try {
            Class<?> cls = unsafe.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            Class<?> cls2 = Long.TYPE;
            cls.getMethod("getLong", Object.class, cls2);
            if (zzds() == null) {
                return false;
            }
            if (zzcs.zzal()) {
                return true;
            }
            cls.getMethod("getByte", cls2);
            cls.getMethod("putByte", cls2, Byte.TYPE);
            cls.getMethod("getInt", cls2);
            cls.getMethod("putInt", cls2, Integer.TYPE);
            cls.getMethod("getLong", cls2);
            cls.getMethod("putLong", cls2, cls2);
            cls.getMethod("copyMemory", cls2, cls2, cls2);
            cls.getMethod("copyMemory", Object.class, cls2, Object.class, cls2, cls2);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 71);
            sb.append("platform method missing - proto runtime falling back to safer methods: ");
            sb.append(strValueOf);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", sb.toString());
            return false;
        }
    }

    private static Field zzds() {
        Field fieldZzb;
        if (zzcs.zzal() && (fieldZzb = zzb(Buffer.class, "effectiveDirectAddress")) != null) {
            return fieldZzb;
        }
        Field fieldZzb2 = zzb(Buffer.class, "address");
        if (fieldZzb2 == null || fieldZzb2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzb2;
    }

    public static <T> T zzg(Class<T> cls) {
        try {
            return (T) zzms.allocateInstance(cls);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        }
    }

    private static int zzh(Class<?> cls) {
        if (zzgx) {
            return zzon.zzph.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzi(Class<?> cls) {
        if (zzgx) {
            return zzon.zzph.arrayIndexScale(cls);
        }
        return -1;
    }

    public static int zzj(Object obj, long j2) {
        return zzon.zzj(obj, j2);
    }

    public static long zzk(Object obj, long j2) {
        return zzon.zzk(obj, j2);
    }

    public static boolean zzl(Object obj, long j2) {
        return zzon.zzl(obj, j2);
    }

    public static float zzm(Object obj, long j2) {
        return zzon.zzm(obj, j2);
    }

    public static double zzn(Object obj, long j2) {
        return zzon.zzn(obj, j2);
    }

    public static Object zzo(Object obj, long j2) {
        return zzon.zzph.getObject(obj, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzp(Object obj, long j2) {
        return (byte) (zzj(obj, (-4) & j2) >>> ((int) (((~j2) & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte zzq(Object obj, long j2) {
        return (byte) (zzj(obj, (-4) & j2) >>> ((int) ((j2 & 3) << 3)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzr(Object obj, long j2) {
        return zzp(obj, j2) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean zzs(Object obj, long j2) {
        return zzq(obj, j2) != 0;
    }

    public static void zza(Object obj, long j2, long j3) {
        zzon.zza(obj, j2, j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j2, byte b3) {
        long j3 = (-4) & j2;
        int i2 = (((int) j2) & 3) << 3;
        zza(obj, j3, ((255 & b3) << i2) | (zzj(obj, j3) & (~(255 << i2))));
    }

    private static boolean zzj(Class<?> cls) {
        if (!zzcs.zzal()) {
            return false;
        }
        try {
            Class<?> cls2 = zzgg;
            Class<?> cls3 = Boolean.TYPE;
            cls2.getMethod("peekLong", cls, cls3);
            cls2.getMethod("pokeLong", cls, Long.TYPE, cls3);
            Class<?> cls4 = Integer.TYPE;
            cls2.getMethod("pokeInt", cls, cls4, cls3);
            cls2.getMethod("peekInt", cls, cls3);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, cls4, cls4);
            cls2.getMethod("peekByteArray", cls, byte[].class, cls4, cls4);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static final class zza extends zzd {
        public zza(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, boolean z2) {
            if (zzgs.zzpe) {
                zzgs.zzb(obj, j2, z2);
            } else {
                zzgs.zzc(obj, j2, z2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zze(Object obj, long j2, byte b3) {
            if (zzgs.zzpe) {
                zzgs.zza(obj, j2, b3);
            } else {
                zzgs.zzb(obj, j2, b3);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final boolean zzl(Object obj, long j2) {
            return zzgs.zzpe ? zzgs.zzr(obj, j2) : zzgs.zzs(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final float zzm(Object obj, long j2) {
            return Float.intBitsToFloat(zzj(obj, j2));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final double zzn(Object obj, long j2) {
            return Double.longBitsToDouble(zzk(obj, j2));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final byte zzx(Object obj, long j2) {
            return zzgs.zzpe ? zzgs.zzp(obj, j2) : zzgs.zzq(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, float f2) {
            zza(obj, j2, Float.floatToIntBits(f2));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, double d3) {
            zza(obj, j2, Double.doubleToLongBits(d3));
        }
    }

    public static final class zzc extends zzd {
        public zzc(Unsafe unsafe) {
            super(unsafe);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, boolean z2) {
            if (zzgs.zzpe) {
                zzgs.zzb(obj, j2, z2);
            } else {
                zzgs.zzc(obj, j2, z2);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zze(Object obj, long j2, byte b3) {
            if (zzgs.zzpe) {
                zzgs.zza(obj, j2, b3);
            } else {
                zzgs.zzb(obj, j2, b3);
            }
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final boolean zzl(Object obj, long j2) {
            return zzgs.zzpe ? zzgs.zzr(obj, j2) : zzgs.zzs(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final float zzm(Object obj, long j2) {
            return Float.intBitsToFloat(zzj(obj, j2));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final double zzn(Object obj, long j2) {
            return Double.longBitsToDouble(zzk(obj, j2));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final byte zzx(Object obj, long j2) {
            return zzgs.zzpe ? zzgs.zzp(obj, j2) : zzgs.zzq(obj, j2);
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, float f2) {
            zza(obj, j2, Float.floatToIntBits(f2));
        }

        @Override // com.google.android.gms.internal.icing.zzgs.zzd
        public final void zza(Object obj, long j2, double d3) {
            zza(obj, j2, Double.doubleToLongBits(d3));
        }
    }

    public static void zza(Object obj, long j2, boolean z2) {
        zzon.zza(obj, j2, z2);
    }

    public static void zza(Object obj, long j2, float f2) {
        zzon.zza(obj, j2, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zzb(Object obj, long j2, boolean z2) {
        zza(obj, j2, z2 ? (byte) 1 : (byte) 0);
    }

    public static void zza(Object obj, long j2, double d3) {
        zzon.zza(obj, j2, d3);
    }

    public static void zza(Object obj, long j2, Object obj2) {
        zzon.zzph.putObject(obj, j2, obj2);
    }

    public static byte zza(byte[] bArr, long j2) {
        return zzon.zzx(bArr, zzop + j2);
    }

    public static void zza(byte[] bArr, long j2, byte b3) {
        zzon.zze(bArr, zzop + j2, b3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void zza(Object obj, long j2, byte b3) {
        long j3 = (-4) & j2;
        int iZzj = zzj(obj, j3);
        int i2 = ((~((int) j2)) & 3) << 3;
        zza(obj, j3, ((255 & b3) << i2) | (iZzj & (~(255 << i2))));
    }
}
