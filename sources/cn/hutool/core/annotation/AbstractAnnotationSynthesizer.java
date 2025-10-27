package cn.hutool.core.annotation;

import cn.hutool.core.annotation.scanner.AnnotationScanner;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: classes.dex */
public abstract class AbstractAnnotationSynthesizer<T> implements AnnotationSynthesizer {
    protected final AnnotationScanner annotationScanner;
    protected final SynthesizedAnnotationSelector annotationSelector;
    protected final Collection<SynthesizedAnnotationPostProcessor> postProcessors;
    protected final T source;
    protected final Map<Class<? extends Annotation>, SynthesizedAnnotation> synthesizedAnnotationMap;
    private final Map<Class<? extends Annotation>, Annotation> synthesizedProxyAnnotations;

    public AbstractAnnotationSynthesizer(T t2, SynthesizedAnnotationSelector synthesizedAnnotationSelector, Collection<SynthesizedAnnotationPostProcessor> collection, AnnotationScanner annotationScanner) throws IllegalArgumentException {
        Assert.notNull(t2, "source must not null", new Object[0]);
        Assert.notNull(synthesizedAnnotationSelector, "annotationSelector must not null", new Object[0]);
        Assert.notNull(collection, "annotationPostProcessors must not null", new Object[0]);
        Assert.notNull(collection, "annotationScanner must not null", new Object[0]);
        this.source = t2;
        this.annotationSelector = synthesizedAnnotationSelector;
        this.annotationScanner = annotationScanner;
        this.postProcessors = CollUtil.unmodifiable(CollUtil.sort(collection, Comparator.comparing(new d())));
        this.synthesizedProxyAnnotations = new LinkedHashMap();
        this.synthesizedAnnotationMap = MapUtil.unmodifiable(loadAnnotations());
        collection.forEach(new Consumer() { // from class: cn.hutool.core.annotation.e
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2329c.lambda$new$1((SynthesizedAnnotationPostProcessor) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(final SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor) {
        this.synthesizedAnnotationMap.values().forEach(new Consumer() { // from class: cn.hutool.core.annotation.g
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f2334c.lambda$null$0(synthesizedAnnotationPostProcessor, (SynthesizedAnnotation) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$null$0(SynthesizedAnnotationPostProcessor synthesizedAnnotationPostProcessor, SynthesizedAnnotation synthesizedAnnotation) {
        synthesizedAnnotationPostProcessor.process(synthesizedAnnotation, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Annotation lambda$synthesize$2(Class cls, Class cls2) {
        SynthesizedAnnotation synthesizedAnnotation = this.synthesizedAnnotationMap.get(cls);
        if (ObjectUtil.isNull(synthesizedAnnotation)) {
            return null;
        }
        return synthesize(cls, synthesizedAnnotation);
    }

    @Override // cn.hutool.core.annotation.AnnotationSynthesizer
    public Map<Class<? extends Annotation>, SynthesizedAnnotation> getAllSynthesizedAnnotation() {
        return this.synthesizedAnnotationMap;
    }

    @Override // cn.hutool.core.annotation.AnnotationSynthesizer
    public Collection<SynthesizedAnnotationPostProcessor> getAnnotationPostProcessors() {
        return this.postProcessors;
    }

    @Override // cn.hutool.core.annotation.AnnotationSynthesizer
    public SynthesizedAnnotationSelector getAnnotationSelector() {
        return this.annotationSelector;
    }

    @Override // cn.hutool.core.annotation.AnnotationSynthesizer
    public T getSource() {
        return this.source;
    }

    @Override // cn.hutool.core.annotation.AnnotationSynthesizer
    public SynthesizedAnnotation getSynthesizedAnnotation(Class<?> cls) {
        return this.synthesizedAnnotationMap.get(cls);
    }

    public abstract Map<Class<? extends Annotation>, SynthesizedAnnotation> loadAnnotations();

    @Override // cn.hutool.core.annotation.AnnotationSynthesizer
    public <A extends Annotation> A synthesize(final Class<A> cls) {
        return (A) this.synthesizedProxyAnnotations.computeIfAbsent(cls, new Function() { // from class: cn.hutool.core.annotation.f
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2331c.lambda$synthesize$2(cls, (Class) obj);
            }
        });
    }

    public abstract <A extends Annotation> A synthesize(Class<A> cls, SynthesizedAnnotation synthesizedAnnotation);
}
