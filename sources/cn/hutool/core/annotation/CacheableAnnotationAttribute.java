package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class CacheableAnnotationAttribute implements AnnotationAttribute {
    private final Annotation annotation;
    private final Method attribute;
    private Object defaultValue;
    private boolean defaultValueInvoked;
    private Object value;
    private boolean valueInvoked;

    public CacheableAnnotationAttribute(Annotation annotation, Method method) throws IllegalArgumentException {
        Assert.notNull(annotation, "annotation must not null", new Object[0]);
        Assert.notNull(method, "attribute must not null", new Object[0]);
        this.annotation = annotation;
        this.attribute = method;
        this.valueInvoked = false;
        this.defaultValueInvoked = false;
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public Annotation getAnnotation() {
        return this.annotation;
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Annotation getAnnotation(Class cls) {
        return x.a(this, cls);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Class getAnnotationType() {
        return x.b(this);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public Method getAttribute() {
        return this.attribute;
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ String getAttributeName() {
        return x.c(this);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Class getAttributeType() {
        return x.d(this);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public Object getValue() {
        if (!this.valueInvoked) {
            this.valueInvoked = true;
            this.value = ReflectUtil.invoke(this.annotation, this.attribute, new Object[0]);
        }
        return this.value;
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public boolean isValueEquivalentToDefaultValue() {
        if (!this.defaultValueInvoked) {
            this.defaultValue = this.attribute.getDefaultValue();
            this.defaultValueInvoked = true;
        }
        return ObjectUtil.equals(getValue(), this.defaultValue);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ boolean isWrapped() {
        return x.f(this);
    }
}
