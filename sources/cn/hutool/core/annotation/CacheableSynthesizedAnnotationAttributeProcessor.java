package cn.hutool.core.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.multi.RowKeyTable;
import cn.hutool.core.map.multi.Table;
import cn.hutool.core.util.ObjectUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class CacheableSynthesizedAnnotationAttributeProcessor implements SynthesizedAnnotationAttributeProcessor {
    private final Comparator<Hierarchical> annotationComparator;
    private final Table<String, Class<?>, Object> valueCaches;

    public CacheableSynthesizedAnnotationAttributeProcessor(Comparator<Hierarchical> comparator) throws IllegalArgumentException {
        this.valueCaches = new RowKeyTable();
        Assert.notNull(comparator, "annotationComparator must not null", new Object[0]);
        this.annotationComparator = comparator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getAttributeValue$0(String str, Class cls, SynthesizedAnnotation synthesizedAnnotation) {
        return synthesizedAnnotation.hasAttribute(str, cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$getAttributeValue$1(String str, SynthesizedAnnotation synthesizedAnnotation) {
        return synthesizedAnnotation.getAttributeValue(str);
    }

    @Override // cn.hutool.core.annotation.SynthesizedAnnotationAttributeProcessor
    public <T> T getAttributeValue(final String str, final Class<T> cls, Collection<? extends SynthesizedAnnotation> collection) {
        T t2 = (T) this.valueCaches.get(str, cls);
        if (ObjectUtil.isNotNull(t2)) {
            return t2;
        }
        T t3 = (T) collection.stream().filter(new Predicate() { // from class: cn.hutool.core.annotation.u0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return CacheableSynthesizedAnnotationAttributeProcessor.lambda$getAttributeValue$0(str, cls, (SynthesizedAnnotation) obj);
            }
        }).min(this.annotationComparator).map(new Function() { // from class: cn.hutool.core.annotation.v0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CacheableSynthesizedAnnotationAttributeProcessor.lambda$getAttributeValue$1(str, (SynthesizedAnnotation) obj);
            }
        }).orElse(null);
        this.valueCaches.put(str, cls, t3);
        return t3;
    }

    public CacheableSynthesizedAnnotationAttributeProcessor() {
        this(Hierarchical.DEFAULT_HIERARCHICAL_COMPARATOR);
    }
}
