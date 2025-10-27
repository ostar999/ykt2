package cn.hutool.core.annotation;

import cn.hutool.core.annotation.Hierarchical;

@FunctionalInterface
/* loaded from: classes.dex */
public interface SynthesizedAnnotationSelector {
    public static final SynthesizedAnnotationSelector NEAREST_AND_OLDEST_PRIORITY = new NearestAndOldestPrioritySelector();
    public static final SynthesizedAnnotationSelector NEAREST_AND_NEWEST_PRIORITY = new NearestAndNewestPrioritySelector();
    public static final SynthesizedAnnotationSelector FARTHEST_AND_OLDEST_PRIORITY = new FarthestAndOldestPrioritySelector();
    public static final SynthesizedAnnotationSelector FARTHEST_AND_NEWEST_PRIORITY = new FarthestAndNewestPrioritySelector();

    public static class FarthestAndNewestPrioritySelector implements SynthesizedAnnotationSelector {
        @Override // cn.hutool.core.annotation.SynthesizedAnnotationSelector
        public <T extends SynthesizedAnnotation> T choose(T t2, T t3) {
            return (T) Hierarchical.Selector.FARTHEST_AND_NEWEST_PRIORITY.choose(t2, t3);
        }
    }

    public static class FarthestAndOldestPrioritySelector implements SynthesizedAnnotationSelector {
        @Override // cn.hutool.core.annotation.SynthesizedAnnotationSelector
        public <T extends SynthesizedAnnotation> T choose(T t2, T t3) {
            return (T) Hierarchical.Selector.FARTHEST_AND_OLDEST_PRIORITY.choose(t2, t3);
        }
    }

    public static class NearestAndNewestPrioritySelector implements SynthesizedAnnotationSelector {
        @Override // cn.hutool.core.annotation.SynthesizedAnnotationSelector
        public <T extends SynthesizedAnnotation> T choose(T t2, T t3) {
            return (T) Hierarchical.Selector.NEAREST_AND_NEWEST_PRIORITY.choose(t2, t3);
        }
    }

    public static class NearestAndOldestPrioritySelector implements SynthesizedAnnotationSelector {
        @Override // cn.hutool.core.annotation.SynthesizedAnnotationSelector
        public <T extends SynthesizedAnnotation> T choose(T t2, T t3) {
            return (T) Hierarchical.Selector.NEAREST_AND_OLDEST_PRIORITY.choose(t2, t3);
        }
    }

    <T extends SynthesizedAnnotation> T choose(T t2, T t3);
}
