package com.aliyun.vod.common.utils;

/* loaded from: classes2.dex */
public class Assert {
    public static void assertEquals(int i2, int i3) {
        assertEquals(Integer.valueOf(i2), Integer.valueOf(i3));
    }

    public static void assertFalse(Object obj) {
        assertEquals(Boolean.FALSE, obj);
    }

    public static void assertGreaterThan(int i2, int i3) {
        if (i2 > i3) {
            return;
        }
        throw new AssertionError("unexpected " + i2 + " <= " + i3);
    }

    public static void assertLessOrEqual(int i2, int i3) {
        if (i2 <= i3) {
            return;
        }
        throw new AssertionError("unexpected " + i2 + " > " + i3);
    }

    public static void assertNotEquals(Object obj, Object obj2) {
        if (obj.equals(obj2)) {
            throw new AssertionError("unexpected equality: " + obj2);
        }
    }

    public static void assertNotNull(Object obj) {
        if (obj == null) {
            throw new AssertionError("unexpected null");
        }
    }

    public static void assertNotSame(Object obj, Object obj2) {
        if (obj != obj2) {
            return;
        }
        throw new AssertionError("unexpected " + obj2);
    }

    public static void assertNull(Object obj) {
        assertEquals((Object) null, obj);
    }

    public static void assertSame(Object obj, Object obj2) {
        if (obj == obj2) {
            return;
        }
        throw new AssertionError("expected " + obj + ", got " + obj2);
    }

    public static void assertTrue(Object obj) {
        assertEquals(Boolean.TRUE, obj);
    }

    public static <T> T fail(Object obj) {
        throw new AssertionError(obj);
    }

    public static void assertEquals(Object obj, Object obj2) {
        if (obj == obj2) {
            return;
        }
        if (obj == null || obj2 == null || !obj.equals(obj2)) {
            throw new AssertionError("expected " + obj + ", got " + obj2);
        }
    }

    public static <T> T fail() {
        throw new AssertionError("failure");
    }
}
