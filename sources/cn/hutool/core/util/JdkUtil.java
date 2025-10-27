package cn.hutool.core.util;

import cn.hutool.core.text.CharSequenceUtil;

/* loaded from: classes.dex */
public class JdkUtil {
    public static final boolean IS_ANDROID;
    public static final boolean IS_AT_LEAST_JDK17;
    public static final boolean IS_JDK8;
    public static final int JVM_VERSION;

    static {
        int i_getJvmVersion = _getJvmVersion();
        JVM_VERSION = i_getJvmVersion;
        IS_JDK8 = 8 == i_getJvmVersion;
        IS_AT_LEAST_JDK17 = i_getJvmVersion >= 17;
        IS_ANDROID = _getJvmName().equals("Dalvik");
    }

    private static String _getJvmName() {
        return System.getProperty("java.vm.name");
    }

    private static int _getJvmVersion() {
        try {
            String property = System.getProperty("java.specification.version");
            if (!CharSequenceUtil.isNotBlank(property)) {
                return -1;
            }
            if (property.startsWith("1.")) {
                property = property.substring(2);
            }
            if (property.indexOf(46) == -1) {
                return Integer.parseInt(property);
            }
            return -1;
        } catch (Throwable unused) {
            return 8;
        }
    }
}
