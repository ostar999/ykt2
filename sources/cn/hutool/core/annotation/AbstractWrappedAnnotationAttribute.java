package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes.dex */
public abstract class AbstractWrappedAnnotationAttribute implements WrappedAnnotationAttribute {
    protected final AnnotationAttribute linked;
    protected final AnnotationAttribute original;

    public AbstractWrappedAnnotationAttribute(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) throws IllegalArgumentException {
        Assert.notNull(annotationAttribute, "target must not null", new Object[0]);
        Assert.notNull(annotationAttribute2, "linked must not null", new Object[0]);
        this.original = annotationAttribute;
        this.linked = annotationAttribute2;
    }

    private void collectLeafAttribute(AnnotationAttribute annotationAttribute, List<AnnotationAttribute> list) {
        if (ObjectUtil.isNull(annotationAttribute)) {
            return;
        }
        if (!annotationAttribute.isWrapped()) {
            list.add(annotationAttribute);
            return;
        }
        WrappedAnnotationAttribute wrappedAnnotationAttribute = (WrappedAnnotationAttribute) annotationAttribute;
        collectLeafAttribute(wrappedAnnotationAttribute.getOriginal(), list);
        collectLeafAttribute(wrappedAnnotationAttribute.getLinked(), list);
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute
    public Collection<AnnotationAttribute> getAllLinkedNonWrappedAttributes() {
        ArrayList arrayList = new ArrayList();
        collectLeafAttribute(this, arrayList);
        return arrayList;
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Annotation getAnnotation() {
        return k2.a(this);
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Annotation getAnnotation(Class cls) {
        return k2.b(this, cls);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Class getAnnotationType() {
        return x.b(this);
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Method getAttribute() {
        return k2.c(this);
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ String getAttributeName() {
        return x.c(this);
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Class getAttributeType() {
        return k2.d(this);
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute
    public AnnotationAttribute getLinked() {
        return this.linked;
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute
    public AnnotationAttribute getNonWrappedOriginal() {
        AnnotationAttribute annotationAttribute = null;
        for (AnnotationAttribute original = this.original; original != null; original = original.isWrapped() ? ((WrappedAnnotationAttribute) original).getOriginal() : null) {
            annotationAttribute = original;
        }
        return annotationAttribute;
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute
    public AnnotationAttribute getOriginal() {
        return this.original;
    }

    @Override // cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ Object getValue() {
        return x.e(this);
    }

    @Override // cn.hutool.core.annotation.WrappedAnnotationAttribute, cn.hutool.core.annotation.AnnotationAttribute
    public /* synthetic */ boolean isWrapped() {
        return k2.e(this);
    }
}
