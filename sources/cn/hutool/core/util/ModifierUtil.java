package cn.hutool.core.util;

import cn.hutool.core.exceptions.UtilException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ModifierUtil {

    public enum ModifierType {
        PUBLIC(1),
        PRIVATE(2),
        PROTECTED(4),
        STATIC(8),
        FINAL(16),
        SYNCHRONIZED(32),
        VOLATILE(64),
        TRANSIENT(128),
        NATIVE(256),
        ABSTRACT(1024),
        STRICT(2048);

        private final int value;

        ModifierType(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static boolean hasModifier(Class<?> cls, ModifierType... modifierTypeArr) {
        return (cls == null || ArrayUtil.isEmpty((Object[]) modifierTypeArr) || (cls.getModifiers() & modifiersToInt(modifierTypeArr)) == 0) ? false : true;
    }

    public static boolean isAbstract(Method method) {
        return hasModifier(method, ModifierType.ABSTRACT);
    }

    public static boolean isPublic(Field field) {
        return hasModifier(field, ModifierType.PUBLIC);
    }

    public static boolean isStatic(Field field) {
        return hasModifier(field, ModifierType.STATIC);
    }

    public static boolean isSynthetic(Field field) {
        return field.isSynthetic();
    }

    private static int modifiersToInt(ModifierType... modifierTypeArr) {
        int value = modifierTypeArr[0].getValue();
        for (int i2 = 1; i2 < modifierTypeArr.length; i2++) {
            value |= modifierTypeArr[i2].getValue();
        }
        return value;
    }

    public static void removeFinalModify(Field field) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (field == null || !hasModifier(field, ModifierType.FINAL)) {
            return;
        }
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        try {
            Field declaredField = Field.class.getDeclaredField("modifiers");
            declaredField.setAccessible(true);
            declaredField.setInt(field, field.getModifiers() & (-17));
        } catch (IllegalAccessException | NoSuchFieldException e2) {
            throw new UtilException(e2, "IllegalAccess for {}.{}", field.getDeclaringClass(), field.getName());
        }
    }

    public static boolean isPublic(Method method) {
        return hasModifier(method, ModifierType.PUBLIC);
    }

    public static boolean isStatic(Method method) {
        return hasModifier(method, ModifierType.STATIC);
    }

    public static boolean isSynthetic(Method method) {
        return method.isSynthetic();
    }

    public static boolean hasModifier(Constructor<?> constructor, ModifierType... modifierTypeArr) {
        return (constructor == null || ArrayUtil.isEmpty((Object[]) modifierTypeArr) || (constructor.getModifiers() & modifiersToInt(modifierTypeArr)) == 0) ? false : true;
    }

    public static boolean isPublic(Class<?> cls) {
        return hasModifier(cls, ModifierType.PUBLIC);
    }

    public static boolean isStatic(Class<?> cls) {
        return hasModifier(cls, ModifierType.STATIC);
    }

    public static boolean isSynthetic(Class<?> cls) {
        return cls.isSynthetic();
    }

    public static boolean isPublic(Constructor<?> constructor) {
        return hasModifier(constructor, ModifierType.PUBLIC);
    }

    public static boolean hasModifier(Method method, ModifierType... modifierTypeArr) {
        return (method == null || ArrayUtil.isEmpty((Object[]) modifierTypeArr) || (method.getModifiers() & modifiersToInt(modifierTypeArr)) == 0) ? false : true;
    }

    public static boolean hasModifier(Field field, ModifierType... modifierTypeArr) {
        return (field == null || ArrayUtil.isEmpty((Object[]) modifierTypeArr) || (field.getModifiers() & modifiersToInt(modifierTypeArr)) == 0) ? false : true;
    }
}
