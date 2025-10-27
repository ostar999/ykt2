package com.nirvana.tools.jsoner;

import android.text.TextUtils;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
final class b {

    /* renamed from: a, reason: collision with root package name */
    String f10665a;

    /* renamed from: b, reason: collision with root package name */
    final Class<?> f10666b;

    /* renamed from: c, reason: collision with root package name */
    boolean f10667c;

    public b(Field field) {
        JsonerTag jsonerTag = (JsonerTag) field.getAnnotation(JsonerTag.class);
        if (jsonerTag != null) {
            this.f10665a = jsonerTag.keyName();
            this.f10667c = jsonerTag.isExcluded();
        }
        if (TextUtils.isEmpty(this.f10665a)) {
            this.f10665a = field.getName();
        }
        this.f10666b = field.getType();
    }

    private static boolean a(Class<?> cls) {
        return Integer.TYPE.equals(cls) || Integer.class.equals(cls) || int[].class.equals(cls) || Integer[].class.equals(cls) || Short.TYPE.equals(cls) || Short.class.equals(cls) || short[].class.equals(cls) || Short[].class.equals(cls) || Long.TYPE.equals(cls) || Long.class.equals(cls) || long[].class.equals(cls) || Long[].class.equals(cls) || Float.TYPE.equals(cls) || Float.class.equals(cls) || float[].class.equals(cls) || Float[].class.equals(cls) || Double.TYPE.equals(cls) || Double.class.equals(cls) || double[].class.equals(cls) || Double[].class.equals(cls);
    }

    private static boolean b(Class<?> cls) {
        return String.class.equals(cls) || StringBuilder.class.equals(cls) || String[].class.equals(cls) || StringBuilder[].class.equals(cls) || StringBuffer.class.equals(cls) || CharSequence.class.equals(cls) || StringBuffer[].class.equals(cls) || CharSequence[].class.equals(cls);
    }

    private static boolean c(Class<?> cls) {
        return Byte.TYPE.equals(cls) || Byte.class.equals(cls) || byte[].class.equals(cls) || Byte[].class.equals(cls) || Character.TYPE.equals(cls) || Character.class.equals(cls) || char[].class.equals(cls) || Character[].class.equals(cls);
    }

    private static boolean d(Class<?> cls) {
        return Boolean.TYPE.equals(cls) || Boolean.class.equals(cls) || boolean[].class.equals(cls) || Boolean[].class.equals(cls);
    }

    public final boolean a() {
        return d(this.f10666b) || c(this.f10666b) || a(this.f10666b) || b(this.f10666b);
    }

    public final boolean b() {
        Class<?>[] interfaces = this.f10666b.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> cls : interfaces) {
                if (cls == Jsoner.class) {
                    return true;
                }
            }
        }
        return false;
    }
}
