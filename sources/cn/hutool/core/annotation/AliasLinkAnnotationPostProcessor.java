package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/* loaded from: classes.dex */
public class AliasLinkAnnotationPostProcessor extends AbstractLinkAnnotationPostProcessor {
    private static final RelationType[] PROCESSED_RELATION_TYPES = {RelationType.ALIAS_FOR, RelationType.FORCE_ALIAS_FOR};

    private void checkAliasRelation(Link link, AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) throws Throwable {
        checkLinkedAttributeNotNull(annotationAttribute, annotationAttribute2, link);
        checkAttributeType(annotationAttribute, annotationAttribute2);
        checkCircularDependency(annotationAttribute, annotationAttribute2);
    }

    private void checkCircularDependency(AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) throws Throwable {
        checkLinkedSelf(annotationAttribute, annotationAttribute2);
        Link linkAnnotation = getLinkAnnotation(annotationAttribute2, RelationType.ALIAS_FOR, RelationType.FORCE_ALIAS_FOR);
        if (ObjectUtil.isNull(linkAnnotation) || ObjectUtil.notEqual(getLinkedAnnotationType(linkAnnotation, annotationAttribute2.getAnnotationType()), annotationAttribute.getAnnotationType())) {
            return;
        }
        Assert.notEquals(linkAnnotation.attribute(), annotationAttribute.getAttributeName(), "circular reference between the alias attribute [{}] and the original attribute [{}]", annotationAttribute2.getAttribute(), annotationAttribute.getAttribute());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AnnotationAttribute lambda$null$1(BinaryOperator binaryOperator, AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2) {
        return (AnnotationAttribute) binaryOperator.apply(annotationAttribute2, annotationAttribute);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$processAttribute$2(AnnotationAttribute annotationAttribute, final BinaryOperator binaryOperator, final AnnotationAttribute annotationAttribute2, SynthesizedAnnotation synthesizedAnnotation) {
        synthesizedAnnotation.replaceAttribute(annotationAttribute.getAttributeName(), new UnaryOperator() { // from class: cn.hutool.core.annotation.u
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AliasLinkAnnotationPostProcessor.lambda$null$1(binaryOperator, annotationAttribute2, (AnnotationAttribute) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processAttribute, reason: merged with bridge method [inline-methods] */
    public void lambda$wrappingLinkedAttribute$0(final AnnotationSynthesizer annotationSynthesizer, final AnnotationAttribute annotationAttribute, final AnnotationAttribute annotationAttribute2, final BinaryOperator<AnnotationAttribute> binaryOperator) {
        Opt optOfNullable = Opt.ofNullable(annotationAttribute2.getAnnotationType());
        annotationSynthesizer.getClass();
        optOfNullable.map(new Function() { // from class: cn.hutool.core.annotation.s
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return annotationSynthesizer.getSynthesizedAnnotation((Class) obj);
            }
        }).ifPresent(new Consumer() { // from class: cn.hutool.core.annotation.t
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AliasLinkAnnotationPostProcessor.lambda$processAttribute$2(annotationAttribute2, binaryOperator, annotationAttribute, (SynthesizedAnnotation) obj);
            }
        });
    }

    private void wrappingLinkedAttribute(final AnnotationSynthesizer annotationSynthesizer, final AnnotationAttribute annotationAttribute, AnnotationAttribute annotationAttribute2, final BinaryOperator<AnnotationAttribute> binaryOperator) {
        if (annotationAttribute2.isWrapped()) {
            ((AbstractWrappedAnnotationAttribute) annotationAttribute2).getAllLinkedNonWrappedAttributes().forEach(new Consumer() { // from class: cn.hutool.core.annotation.r
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f2359c.lambda$wrappingLinkedAttribute$0(annotationSynthesizer, annotationAttribute, binaryOperator, (AnnotationAttribute) obj);
                }
            });
        } else {
            lambda$wrappingLinkedAttribute$0(annotationSynthesizer, annotationAttribute, annotationAttribute2, binaryOperator);
        }
    }

    @Override // cn.hutool.core.annotation.AbstractLinkAnnotationPostProcessor, cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public int order() {
        return -2147483646;
    }

    @Override // cn.hutool.core.annotation.AbstractLinkAnnotationPostProcessor
    public void processLinkedAttribute(AnnotationSynthesizer annotationSynthesizer, Link link, SynthesizedAnnotation synthesizedAnnotation, AnnotationAttribute annotationAttribute, SynthesizedAnnotation synthesizedAnnotation2, AnnotationAttribute annotationAttribute2) throws Throwable {
        checkAliasRelation(link, annotationAttribute, annotationAttribute2);
        if (RelationType.ALIAS_FOR.equals(link.type())) {
            wrappingLinkedAttribute(annotationSynthesizer, annotationAttribute, annotationAttribute2, new BinaryOperator() { // from class: cn.hutool.core.annotation.v
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return new AliasedAnnotationAttribute((AnnotationAttribute) obj, (AnnotationAttribute) obj2);
                }
            });
        } else {
            wrappingLinkedAttribute(annotationSynthesizer, annotationAttribute, annotationAttribute2, new BinaryOperator() { // from class: cn.hutool.core.annotation.w
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return new ForceAliasedAnnotationAttribute((AnnotationAttribute) obj, (AnnotationAttribute) obj2);
                }
            });
        }
    }

    @Override // cn.hutool.core.annotation.AbstractLinkAnnotationPostProcessor
    public RelationType[] processTypes() {
        return PROCESSED_RELATION_TYPES;
    }
}
