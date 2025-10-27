package io.crossbar.autobahn.utils;

/* loaded from: classes8.dex */
public class ABLogger {
    public static boolean a() {
        return System.getProperty("java.vendor").equals("The Android Project");
    }

    public static IABLogger a(String str) {
        try {
            return (IABLogger) (a() ? ABALogger.class : ABJLogger.class).getConstructor(String.class).newInstance(str);
        } catch (Exception e2) {
            throw new RuntimeException(e2.getMessage(), e2);
        }
    }
}
