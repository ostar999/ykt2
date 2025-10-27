package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes3.dex */
public final class Verify {
    private Verify() {
    }

    public static void verify(boolean z2) {
        if (!z2) {
            throw new VerifyException();
        }
    }

    @CanIgnoreReturnValue
    public static <T> T verifyNotNull(@NullableDecl T t2) {
        return (T) verifyNotNull(t2, "expected a non-null reference", new Object[0]);
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object... objArr) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, objArr));
        }
    }

    @CanIgnoreReturnValue
    public static <T> T verifyNotNull(@NullableDecl T t2, @NullableDecl String str, @NullableDecl Object... objArr) {
        verify(t2 != null, str, objArr);
        return t2;
    }

    public static void verify(boolean z2, @NullableDecl String str, char c3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c3)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, char c3, char c4) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c3), Character.valueOf(c4)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, int i2, char c3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), Character.valueOf(c3)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, long j2, char c3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), Character.valueOf(c3)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj, char c3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, Character.valueOf(c3)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, char c3, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c3), Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, int i2, int i3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), Integer.valueOf(i3)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, long j2, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj, int i2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, Integer.valueOf(i2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, char c3, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c3), Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, int i2, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, long j2, long j3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), Long.valueOf(j3)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj, long j2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, Long.valueOf(j2)));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, char c3, @NullableDecl Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Character.valueOf(c3), obj));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, int i2, @NullableDecl Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Integer.valueOf(i2), obj));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, long j2, @NullableDecl Object obj) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, Long.valueOf(j2), obj));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj, @NullableDecl Object obj2) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, obj2));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj, @NullableDecl Object obj2, @NullableDecl Object obj3) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, obj2, obj3));
        }
    }

    public static void verify(boolean z2, @NullableDecl String str, @NullableDecl Object obj, @NullableDecl Object obj2, @NullableDecl Object obj3, @NullableDecl Object obj4) {
        if (!z2) {
            throw new VerifyException(Strings.lenientFormat(str, obj, obj2, obj3, obj4));
        }
    }
}
