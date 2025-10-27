package cn.hutool.core.annotation;

import cn.hutool.core.annotation.scanner.AnnotationScanner;
import cn.hutool.core.annotation.scanner.MetaAnnotationScanner;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;

/* loaded from: classes.dex */
public class GenericSynthesizedAggregateAnnotation extends AbstractAnnotationSynthesizer<List<Annotation>> implements SynthesizedAggregateAnnotation {
    private final SynthesizedAnnotationAttributeProcessor attributeProcessor;
    private final int horizontalDistance;
    private final Object root;
    private final int verticalDistance;

    public static class MetaAnnotation extends GenericSynthesizedAnnotation<Annotation, Annotation> {
        public MetaAnnotation(Annotation annotation, Annotation annotation2, int i2, int i3) {
            super(annotation, annotation2, i2, i3);
        }
    }

    public GenericSynthesizedAggregateAnnotation(Annotation... annotationArr) {
        this(Arrays.asList(annotationArr), new MetaAnnotationScanner());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Annotation[] lambda$getAnnotations$1(int i2) {
        return new Annotation[i2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadAnnotations$0(Map map, Annotation annotation, Integer num, Annotation annotation2) {
        SynthesizedAnnotation synthesizedAnnotation = (SynthesizedAnnotation) map.get(annotation2.annotationType());
        MetaAnnotation metaAnnotation = new MetaAnnotation(annotation, annotation2, num.intValue() + 1, map.size());
        if (ObjectUtil.isNull(synthesizedAnnotation)) {
            map.put(annotation2.annotationType(), metaAnnotation);
        } else {
            map.put(annotation2.annotationType(), this.annotationSelector.choose(synthesizedAnnotation, metaAnnotation));
        }
    }

    @Override // cn.hutool.core.annotation.SynthesizedAggregateAnnotation, java.lang.annotation.Annotation
    public /* synthetic */ Class annotationType() {
        return p1.a(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // cn.hutool.core.annotation.Hierarchical
    public /* synthetic */ int compareTo(Hierarchical hierarchical) {
        return o1.a(this, hierarchical);
    }

    @Override // cn.hutool.core.annotation.Hierarchical, java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Hierarchical hierarchical) {
        return compareTo((Hierarchical) hierarchical);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAggregateAnnotation
    public <T extends Annotation> T getAnnotation(final Class<T> cls) {
        Opt optOfNullable = Opt.ofNullable(cls);
        final Map<Class<? extends Annotation>, SynthesizedAnnotation> map = this.synthesizedAnnotationMap;
        map.getClass();
        Opt map2 = optOfNullable.map(new Function() { // from class: cn.hutool.core.annotation.x0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (SynthesizedAnnotation) map.get((Class) obj);
            }
        }).map(new y0());
        cls.getClass();
        return (T) map2.map(new Function() { // from class: cn.hutool.core.annotation.z0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (Annotation) cls.cast((Annotation) obj);
            }
        }).orElse(null);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAggregateAnnotation
    public SynthesizedAnnotationAttributeProcessor getAnnotationAttributeProcessor() {
        return this.attributeProcessor;
    }

    @Override // cn.hutool.core.annotation.AggregateAnnotation
    public Annotation[] getAnnotations() {
        return (Annotation[]) this.synthesizedAnnotationMap.values().stream().map(new y0()).toArray(new IntFunction() { // from class: cn.hutool.core.annotation.b1
            @Override // java.util.function.IntFunction
            public final Object apply(int i2) {
                return GenericSynthesizedAggregateAnnotation.lambda$getAnnotations$1(i2);
            }
        });
    }

    @Override // cn.hutool.core.annotation.SynthesizedAggregateAnnotation, cn.hutool.core.annotation.AnnotationAttributeValueProvider
    public Object getAttributeValue(String str, Class<?> cls) {
        return this.attributeProcessor.getAttributeValue(str, cls, this.synthesizedAnnotationMap.values());
    }

    @Override // cn.hutool.core.annotation.SynthesizedAggregateAnnotation, cn.hutool.core.annotation.Hierarchical
    public int getHorizontalDistance() {
        return this.horizontalDistance;
    }

    @Override // cn.hutool.core.annotation.Hierarchical
    public Object getRoot() {
        return this.root;
    }

    @Override // cn.hutool.core.annotation.SynthesizedAggregateAnnotation, cn.hutool.core.annotation.Hierarchical
    public int getVerticalDistance() {
        return this.verticalDistance;
    }

    @Override // cn.hutool.core.annotation.AggregateAnnotation
    public boolean isAnnotationPresent(Class<? extends Annotation> cls) {
        return this.synthesizedAnnotationMap.containsKey(cls);
    }

    @Override // cn.hutool.core.annotation.AbstractAnnotationSynthesizer
    public Map<Class<? extends Annotation>, SynthesizedAnnotation> loadAnnotations() throws Throwable {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i2 = 0; i2 < ((List) this.source).size(); i2++) {
            final Annotation annotation = (Annotation) ((List) this.source).get(i2);
            Assert.isFalse(AnnotationUtil.isSynthesizedAnnotation(annotation), "source [{}] has been synthesized", new Object[0]);
            linkedHashMap.put(annotation.annotationType(), new MetaAnnotation(annotation, annotation, 0, i2));
            Assert.isTrue(this.annotationScanner.support(annotation.annotationType()), "annotation scanner [{}] cannot support scan [{}]", this.annotationScanner, annotation.annotationType());
            this.annotationScanner.scan(new BiConsumer() { // from class: cn.hutool.core.annotation.a1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f2319c.lambda$loadAnnotations$0(linkedHashMap, annotation, (Integer) obj, (Annotation) obj2);
                }
            }, annotation.annotationType(), null);
        }
        return linkedHashMap;
    }

    @Override // cn.hutool.core.annotation.AbstractAnnotationSynthesizer
    public <T extends Annotation> T synthesize(Class<T> cls, SynthesizedAnnotation synthesizedAnnotation) {
        return (T) SynthesizedAnnotationProxy.create(cls, this, synthesizedAnnotation);
    }

    public GenericSynthesizedAggregateAnnotation(List<Annotation> list, AnnotationScanner annotationScanner) {
        this(list, SynthesizedAnnotationSelector.NEAREST_AND_OLDEST_PRIORITY, new CacheableSynthesizedAnnotationAttributeProcessor(), Arrays.asList(SynthesizedAnnotationPostProcessor.ALIAS_ANNOTATION_POST_PROCESSOR, SynthesizedAnnotationPostProcessor.MIRROR_LINK_ANNOTATION_POST_PROCESSOR, SynthesizedAnnotationPostProcessor.ALIAS_LINK_ANNOTATION_POST_PROCESSOR), annotationScanner);
    }

    public GenericSynthesizedAggregateAnnotation(List<Annotation> list, SynthesizedAnnotationSelector synthesizedAnnotationSelector, SynthesizedAnnotationAttributeProcessor synthesizedAnnotationAttributeProcessor, Collection<SynthesizedAnnotationPostProcessor> collection, AnnotationScanner annotationScanner) {
        this(null, 0, 0, list, synthesizedAnnotationSelector, synthesizedAnnotationAttributeProcessor, collection, annotationScanner);
    }

    public GenericSynthesizedAggregateAnnotation(Object obj, int i2, int i3, List<Annotation> list, SynthesizedAnnotationSelector synthesizedAnnotationSelector, SynthesizedAnnotationAttributeProcessor synthesizedAnnotationAttributeProcessor, Collection<SynthesizedAnnotationPostProcessor> collection, AnnotationScanner annotationScanner) throws IllegalArgumentException {
        super(list, synthesizedAnnotationSelector, collection, annotationScanner);
        Assert.notNull(synthesizedAnnotationAttributeProcessor, "attributeProcessor must not null", new Object[0]);
        this.root = ObjectUtil.defaultIfNull((GenericSynthesizedAggregateAnnotation) obj, this);
        this.verticalDistance = i2;
        this.horizontalDistance = i3;
        this.attributeProcessor = synthesizedAnnotationAttributeProcessor;
    }
}
