package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;

/* loaded from: classes.dex */
public class MirroredAnnotationAttribute extends AbstractWrappedAnnotationAttribute {
    public MirroredAnnotationAttribute(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        super(annotationAttribute, annotationAttribute2);
    }

    @Override // cn.hutool.core.annotation.AbstractWrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public Object getValue() throws Throwable {
        boolean zIsValueEquivalentToDefaultValue = this.original.isValueEquivalentToDefaultValue();
        boolean zIsValueEquivalentToDefaultValue2 = this.linked.isValueEquivalentToDefaultValue();
        Object value = this.original.getValue();
        Object value2 = this.linked.getValue();
        if (zIsValueEquivalentToDefaultValue != zIsValueEquivalentToDefaultValue2) {
            return zIsValueEquivalentToDefaultValue ? value2 : value;
        }
        Assert.equals(value, value2, "the values of attributes [{}] and [{}] that mirror each other are different: [{}] <==> [{}]", this.original.getAttribute(), this.linked.getAttribute(), value, value2);
        return value;
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public boolean isValueEquivalentToDefaultValue() {
        return this.original.isValueEquivalentToDefaultValue() && this.linked.isValueEquivalentToDefaultValue();
    }
}
