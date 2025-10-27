package com.google.common.hash;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.common.annotations.GwtIncompatible;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

@GwtIncompatible
/* loaded from: classes4.dex */
abstract class Striped64 extends Number {
    private static final Unsafe UNSAFE;
    private static final long baseOffset;
    private static final long busyOffset;
    volatile transient long base;
    volatile transient int busy;

    @NullableDecl
    volatile transient Cell[] cells;
    static final ThreadLocal<int[]> threadHashCode = new ThreadLocal<>();
    static final Random rng = new Random();
    static final int NCPU = Runtime.getRuntime().availableProcessors();

    public static final class Cell {
        private static final Unsafe UNSAFE;
        private static final long valueOffset;

        /* renamed from: p0, reason: collision with root package name */
        volatile long f7066p0;

        /* renamed from: p1, reason: collision with root package name */
        volatile long f7067p1;
        volatile long p2;
        volatile long p3;
        volatile long p4;
        volatile long p5;
        volatile long p6;

        /* renamed from: q0, reason: collision with root package name */
        volatile long f7068q0;

        /* renamed from: q1, reason: collision with root package name */
        volatile long f7069q1;
        volatile long q2;
        volatile long q3;
        volatile long q4;
        volatile long q5;
        volatile long q6;
        volatile long value;

        static {
            try {
                Unsafe unsafe = Striped64.getUnsafe();
                UNSAFE = unsafe;
                valueOffset = unsafe.objectFieldOffset(Cell.class.getDeclaredField("value"));
            } catch (Exception e2) {
                throw new Error(e2);
            }
        }

        public Cell(long j2) {
            this.value = j2;
        }

        public final boolean cas(long j2, long j3) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, j2, j3);
        }
    }

    static {
        try {
            Unsafe unsafe = getUnsafe();
            UNSAFE = unsafe;
            baseOffset = unsafe.objectFieldOffset(Striped64.class.getDeclaredField(TtmlNode.RUBY_BASE));
            busyOffset = unsafe.objectFieldOffset(Striped64.class.getDeclaredField("busy"));
        } catch (Exception e2) {
            throw new Error(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Unsafe getUnsafe() {
        try {
            try {
                return Unsafe.getUnsafe();
            } catch (SecurityException unused) {
                return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() { // from class: com.google.common.hash.Striped64.1
                    @Override // java.security.PrivilegedExceptionAction
                    public Unsafe run() throws Exception {
                        for (Field field : Unsafe.class.getDeclaredFields()) {
                            field.setAccessible(true);
                            Object obj = field.get(null);
                            if (Unsafe.class.isInstance(obj)) {
                                return (Unsafe) Unsafe.class.cast(obj);
                            }
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                });
            }
        } catch (PrivilegedActionException e2) {
            throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
        }
    }

    final boolean casBase(long j2, long j3) {
        return UNSAFE.compareAndSwapLong(this, baseOffset, j2, j3);
    }

    final boolean casBusy() {
        return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
    }

    abstract long fn(long j2, long j3);

    final void internalReset(long j2) {
        Cell[] cellArr = this.cells;
        this.base = j2;
        if (cellArr != null) {
            for (Cell cell : cellArr) {
                if (cell != null) {
                    cell.value = j2;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0023 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00ee A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    final void retryUpdate(long r17, @org.checkerframework.checker.nullness.compatqual.NullableDecl int[] r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.Striped64.retryUpdate(long, int[], boolean):void");
    }
}
