package cn.hutool.core.annotation;

/* loaded from: classes.dex */
public class AliasedAnnotationAttribute extends AbstractWrappedAnnotationAttribute {
    public AliasedAnnotationAttribute(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        super(annotationAttribute, annotationAttribute2);
    }

    @Override // cn.hutool.core.annotation.AbstractWrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public Object getValue() {
        return this.linked.isValueEquivalentToDefaultValue() ? super.getValue() : this.linked.getValue();
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public boolean isValueEquivalentToDefaultValue() {
        return this.linked.isValueEquivalentToDefaultValue() && this.original.isValueEquivalentToDefaultValue();
    }
}
