package cn.hutool.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.text.CharSequenceUtil;
import java.util.Properties;

/* loaded from: classes.dex */
public class SystemPropsUtil {
    public static String HUTOOL_DATE_LENIENT = "hutool.date.lenient";

    public static String get(String str, String str2) {
        return CharSequenceUtil.nullToDefault(get(str, false), str2);
    }

    public static boolean getBoolean(String str, boolean z2) {
        String str2 = get(str);
        return str2 == null ? z2 : BooleanUtil.toBoolean(str2);
    }

    public static int getInt(String str, int i2) {
        return Convert.toInt(get(str), Integer.valueOf(i2)).intValue();
    }

    public static long getLong(String str, long j2) {
        return Convert.toLong(get(str), Long.valueOf(j2)).longValue();
    }

    public static Properties getProps() {
        return System.getProperties();
    }

    public static void set(String str, String str2) {
        if (str2 == null) {
            System.clearProperty(str);
        } else {
            System.setProperty(str, str2);
        }
    }

    public static String get(String str, boolean z2) {
        String property;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
            if (!z2) {
                Console.error("Caught a SecurityException reading the system property '{}'; the SystemUtil property value will default to null.", str);
            }
            property = null;
        }
        if (property != null) {
            return property;
        }
        try {
            return System.getenv(str);
        } catch (SecurityException unused2) {
            if (z2) {
                return property;
            }
            Console.error("Caught a SecurityException reading the system env '{}'; the SystemUtil env value will default to null.", str);
            return property;
        }
    }

    public static String get(String str) {
        return get(str, (String) null);
    }
}
