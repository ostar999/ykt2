package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.map.ForestMap;
import cn.hutool.core.map.LinkedForestMap;
import cn.hutool.core.map.TreeEntry;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public class AliasAnnotationPostProcessor implements SynthesizedAnnotationPostProcessor {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$process$0(Map map, ForestMap forestMap, String str, AnnotationAttribute annotationAttribute) throws IllegalArgumentException {
        String str2 = (String) Opt.ofNullable(annotationAttribute.getAnnotation(Alias.class)).map(new Function() { // from class: cn.hutool.core.annotation.l
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Alias) obj).value();
            }
        }).orElse(null);
        if (ObjectUtil.isNull(str2)) {
            return;
        }
        AnnotationAttribute annotationAttribute2 = (AnnotationAttribute) map.get(str2);
        Assert.notNull(annotationAttribute2, "no method for alias: [{}]", str2);
        forestMap.putLinkedNodes(str2, annotationAttribute2, str, annotationAttribute);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$process$1(final ForestMap forestMap, Map map, String str, AnnotationAttribute annotationAttribute) throws Throwable {
        Opt optOfNullable = Opt.ofNullable(str);
        forestMap.getClass();
        AnnotationAttribute annotationAttribute2 = (AnnotationAttribute) optOfNullable.map(new Function() { // from class: cn.hutool.core.annotation.m
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return forestMap.getRootNode((String) obj);
            }
        }).map(new Function() { // from class: cn.hutool.core.annotation.n
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (AnnotationAttribute) ((TreeEntry) obj).getValue();
            }
        }).orElse(annotationAttribute);
        Assert.isTrue(ObjectUtil.isNull(annotationAttribute2) || ClassUtil.isAssignable(annotationAttribute.getAttributeType(), annotationAttribute2.getAttributeType()), "return type of the root alias method [{}] is inconsistent with the original [{}]", annotationAttribute2.getClass(), annotationAttribute.getAttributeType());
        if (annotationAttribute != annotationAttribute2) {
            map.put(str, new ForceAliasedAnnotationAttribute(annotationAttribute, annotationAttribute2));
        }
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

    @Override // cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public int order() {
        return Integer.MIN_VALUE;
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotationPostProcessor
    public void process(SynthesizedAnnotation synthesizedAnnotation, AnnotationSynthesizer annotationSynthesizer) {
        final Map<String, AnnotationAttribute> attributes = synthesizedAnnotation.getAttributes();
        final LinkedForestMap linkedForestMap = new LinkedForestMap(false);
        attributes.forEach(new BiConsumer() { // from class: cn.hutool.core.annotation.o
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws IllegalArgumentException {
                AliasAnnotationPostProcessor.lambda$process$0(attributes, linkedForestMap, (String) obj, (AnnotationAttribute) obj2);
            }
        });
        attributes.forEach(new BiConsumer() { // from class: cn.hutool.core.annotation.p
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws Throwable {
                AliasAnnotationPostProcessor.lambda$process$1(linkedForestMap, attributes, (String) obj, (AnnotationAttribute) obj2);
            }
        });
        synthesizedAnnotation.setAttributes(attributes);
    }
}
