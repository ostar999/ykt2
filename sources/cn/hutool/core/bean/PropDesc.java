package cn.hutool.core.bean;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.annotation.PropIgnore;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ModifierUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class PropDesc {
    final Field field;
    protected Method getter;
    protected Method setter;

    public PropDesc(Field field, Method method, Method method2) {
        this.field = field;
        this.getter = ClassUtil.setAccessible(method);
        this.setter = ClassUtil.setAccessible(method2);
    }

    private Class<?> findPropClass(Method method, Method method2) {
        Class<?> returnClass = method != null ? TypeUtil.getReturnClass(method) : null;
        return (returnClass != null || method2 == null) ? returnClass : TypeUtil.getFirstParamClass(method2);
    }

    private Type findPropType(Method method, Method method2) {
        Type returnType = method != null ? TypeUtil.getReturnType(method) : null;
        return (returnType != null || method2 == null) ? returnType : TypeUtil.getParamType(method2, 0);
    }

    private boolean isIgnoreGet() {
        return AnnotationUtil.hasAnnotation(this.field, PropIgnore.class) || AnnotationUtil.hasAnnotation(this.getter, PropIgnore.class);
    }

    private boolean isIgnoreSet() {
        return AnnotationUtil.hasAnnotation(this.field, PropIgnore.class) || AnnotationUtil.hasAnnotation(this.setter, PropIgnore.class);
    }

    private boolean isTransientForGet() {
        Method method;
        Field field = this.field;
        ModifierUtil.ModifierType modifierType = ModifierUtil.ModifierType.TRANSIENT;
        boolean zHasModifier = ModifierUtil.hasModifier(field, modifierType);
        if (zHasModifier || (method = this.getter) == null) {
            return zHasModifier;
        }
        boolean zHasModifier2 = ModifierUtil.hasModifier(method, modifierType);
        return !zHasModifier2 ? AnnotationUtil.hasAnnotation(this.getter, Transient.class) : zHasModifier2;
    }

    private boolean isTransientForSet() {
        Method method;
        Field field = this.field;
        ModifierUtil.ModifierType modifierType = ModifierUtil.ModifierType.TRANSIENT;
        boolean zHasModifier = ModifierUtil.hasModifier(field, modifierType);
        if (zHasModifier || (method = this.setter) == null) {
            return zHasModifier;
        }
        boolean zHasModifier2 = ModifierUtil.hasModifier(method, modifierType);
        return !zHasModifier2 ? AnnotationUtil.hasAnnotation(this.setter, Transient.class) : zHasModifier2;
    }

    public Field getField() {
        return this.field;
    }

    public Class<?> getFieldClass() {
        Field field = this.field;
        return field != null ? TypeUtil.getClass(field) : findPropClass(this.getter, this.setter);
    }

    public String getFieldName() {
        return ReflectUtil.getFieldName(this.field);
    }

    public Type getFieldType() {
        Field field = this.field;
        return field != null ? TypeUtil.getType(field) : findPropType(this.getter, this.setter);
    }

    public Method getGetter() {
        return this.getter;
    }

    public String getRawFieldName() {
        Field field = this.field;
        if (field == null) {
            return null;
        }
        return field.getName();
    }

    public Method getSetter() {
        return this.setter;
    }

    public Object getValue(Object obj) {
        Method method = this.getter;
        if (method != null) {
            return ReflectUtil.invoke(obj, method, new Object[0]);
        }
        if (ModifierUtil.isPublic(this.field)) {
            return ReflectUtil.getFieldValue(obj, this.field);
        }
        return null;
    }

    public boolean isReadable(boolean z2) {
        if (this.getter == null && !ModifierUtil.isPublic(this.field)) {
            return false;
        }
        if (z2 && isTransientForGet()) {
            return false;
        }
        return !isIgnoreGet();
    }

    public boolean isWritable(boolean z2) {
        if (this.setter == null && !ModifierUtil.isPublic(this.field)) {
            return false;
        }
        if (z2 && isTransientForSet()) {
            return false;
        }
        return !isIgnoreSet();
    }

    public PropDesc setValue(Object obj, Object obj2) throws UtilException {
        Method method = this.setter;
        if (method != null) {
            ReflectUtil.invoke(obj, method, obj2);
        } else if (ModifierUtil.isPublic(this.field)) {
            ReflectUtil.setFieldValue(obj, this.field, obj2);
        }
        return this;
    }

    public Object getValue(Object obj, Type type, boolean z2) {
        Object value;
        try {
            value = getValue(obj);
        } catch (Exception e2) {
            if (!z2) {
                throw new BeanException(e2, "Get value of [{}] error!", getFieldName());
            }
            value = null;
        }
        return (value == null || type == null) ? value : Convert.convertWithCheck(type, value, null, z2);
    }

    public PropDesc setValue(Object obj, Object obj2, boolean z2, boolean z3) {
        return setValue(obj, obj2, z2, z3, true);
    }

    public PropDesc setValue(Object obj, Object obj2, boolean z2, boolean z3, boolean z4) throws Exception {
        if (obj2 == null && z2) {
            return this;
        }
        if (!z4 && getValue(obj) != null) {
            return this;
        }
        if (obj2 != null) {
            Class<?> fieldClass = getFieldClass();
            if (!fieldClass.isInstance(obj2)) {
                obj2 = Convert.convertWithCheck(fieldClass, obj2, null, z3);
            }
        }
        if (obj2 != null || !z2) {
            try {
                setValue(obj, obj2);
            } catch (Exception e2) {
                if (!z3) {
                    throw new BeanException(e2, "Set value of [{}] error!", getFieldName());
                }
            }
        }
        return this;
    }
}
