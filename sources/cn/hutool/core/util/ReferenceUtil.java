package cn.hutool.core.util;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class ReferenceUtil {

    /* renamed from: cn.hutool.core.util.ReferenceUtil$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType;

        static {
            int[] iArr = new int[ReferenceType.values().length];
            $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType = iArr;
            try {
                iArr[ReferenceType.SOFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType[ReferenceType.WEAK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType[ReferenceType.PHANTOM.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum ReferenceType {
        SOFT,
        WEAK,
        PHANTOM
    }

    public static <T> Reference<T> create(ReferenceType referenceType, T t2) {
        return create(referenceType, t2, null);
    }

    public static <T> Reference<T> create(ReferenceType referenceType, T t2, ReferenceQueue<T> referenceQueue) {
        int i2 = AnonymousClass1.$SwitchMap$cn$hutool$core$util$ReferenceUtil$ReferenceType[referenceType.ordinal()];
        if (i2 == 1) {
            return new SoftReference(t2, referenceQueue);
        }
        if (i2 == 2) {
            return new WeakReference(t2, referenceQueue);
        }
        if (i2 != 3) {
            return null;
        }
        return new PhantomReference(t2, referenceQueue);
    }
}
