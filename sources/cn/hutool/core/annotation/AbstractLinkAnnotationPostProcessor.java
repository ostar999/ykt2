package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public abstract class AbstractLinkAnnotationPostProcessor implements SynthesizedAnnotationPostProcessor {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Link lambda$getLinkAnnotation$1(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        return (Link) AnnotationUtil.getSynthesizedAnnotation(annotationAttribute.getAttribute(), Link.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getLinkAnnotation$2(RelationType[] relationTypeArr, Link link) {
        return ArrayUtil.contains(relationTypeArr, link.type());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$process$0(AnnotationSynthesizer annotationSynthesizer, SynthesizedAnnotation synthesizedAnnotation, String str, AnnotationAttribute annotationAttribute) {
        Link linkAnnotation = getLinkAnnotation(annotationAttribute, processTypes());
        if (ObjectUtil.isNull(linkAnnotation)) {
            return;
        }
        SynthesizedAnnotation linkedAnnotation = getLinkedAnnotation(linkAnnotation, annotationSynthesizer, synthesizedAnnotation.annotationType());
        if (ObjectUtil.isNull(linkedAnnotation)) {
            return;
        }
        processLinkedAttribute(annotationSynthesizer, linkAnnotation, synthesizedAnnotation, synthesizedAnnotation.getAttributes().get(str), linkedAnnotation, linkedAnnotation.getAttributes().get(linkAnnotation.attribute()));
    }

    public void checkAttributeType(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        Assert.equals(annotationAttribute.getAttributeType(), annotationAttribute2.getAttributeType(), "return type of the linked attribute [{}] is inconsistent with the original [{}]", annotationAttribute.getAttribute(), annotationAttribute2.getAttribute());
    }

    public void checkLinkedAttributeNotNull(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2, Link link) {
        Assert.notNull(annotationAttribute2, "cannot find linked attribute [{}] of original [{}] in [{}]", annotationAttribute.getAttribute(), link.attribute(), getLinkedAnnotationType(link, annotationAttribute.getAnnotationType()));
    }

    public void checkLinkedSelf(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        Assert.isFalse(annotationAttribute == annotationAttribute2 || ObjectUtil.equals(annotationAttribute.getAttribute(), annotationAttribute2.getAttribute()), "cannot link self [{}]", annotationAttribute.getAttribute());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public /* synthetic */ int compareTo(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor) {
        return s1.a(this, synthesizedAnnotationPostProcessor);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor, java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor) {
        return compareTo((SynthesizedAnnotationPostProcessor) synthesizedAnnotationPostProcessor);
    }

    public Link getLinkAnnotation(final AnnotationAttribute annotationAttribute, final RelationType... relationTypeArr) {
        return (Link) Opt.ofNullable(annotationAttribute).map(new Function() { // from class: cn.hutool.core.annotation.i
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractLinkAnnotationPostProcessor.lambda$getLinkAnnotation$1(annotationAttribute, (AnnotationAttribute) obj);
            }
        }).filter(new Predicate() { // from class: cn.hutool.core.annotation.j
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractLinkAnnotationPostProcessor.lambda$getLinkAnnotation$2(relationTypeArr, (Link) obj);
            }
        }).get();
    }

    public SynthesizedAnnotation getLinkedAnnotation(Link link, AnnotationSynthesizer annotationSynthesizer, Class<? extends Annotation> cls) {
        return annotationSynthesizer.getSynthesizedAnnotation(getLinkedAnnotationType(link, cls));
    }

    public Class<?> getLinkedAnnotationType(Link link, Class<?> cls) {
        return ObjectUtil.equals(link.annotation(), Annotation.class) ? cls : link.annotation();
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public /* synthetic */ int order() {
        return s1.c(this);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public void process(final SynthesizedAnnotation synthesizedAnnotation, final AnnotationSynthesizer annotationSynthesizer) {
        new HashMap(synthesizedAnnotation.getAttributes()).forEach(new BiConsumer() { // from class: cn.hutool.core.annotation.k
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f2345c.lambda$process$0(annotationSynthesizer, synthesizedAnnotation, (String) obj, (AnnotationAttribute) obj2);
            }
        });
    }

    public abstract void processLinkedAttribute(AnnotationSynthesizer annotationSynthesizer, Link link, SynthesizedAnnotation synthesizedAnnotation, AnnotationAttribute annotationAttribute, SynthesizedAnnotation synthesizedAnnotation2, AnnotationAttribute annotationAttribute2);

    public abstract RelationType[] processTypes();
}
