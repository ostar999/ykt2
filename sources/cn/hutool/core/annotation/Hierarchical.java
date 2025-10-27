package cn.hutool.core.annotation;

import java.util.Comparator;
import java.util.function.Function;

/* loaded from: classes.dex */
public interface Hierarchical extends Comparable<Hierarchical> {
    public static final Comparator<Hierarchical> DEFAULT_HIERARCHICAL_COMPARATOR = Comparator.comparing(new Function() { // from class: cn.hutool.core.annotation.m1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Integer.valueOf(((Hierarchical) obj).getVerticalDistance());
        }
    }).thenComparing(new Function() { // from class: cn.hutool.core.annotation.n1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Integer.valueOf(((Hierarchical) obj).getHorizontalDistance());
        }
    });

    @FunctionalInterface
    public interface Selector {
        public static final Selector NEAREST_AND_OLDEST_PRIORITY = new NearestAndOldestPrioritySelector();
        public static final Selector NEAREST_AND_NEWEST_PRIORITY = new NearestAndNewestPrioritySelector();
        public static final Selector FARTHEST_AND_OLDEST_PRIORITY = new FarthestAndOldestPrioritySelector();
        public static final Selector FARTHEST_AND_NEWEST_PRIORITY = new FarthestAndNewestPrioritySelector();

        public static class FarthestAndNewestPrioritySelector implements Selector {
            @Override // cn.hutool.core.annotation.Hierarchical.Selector
            public <T extends Hierarchical> T choose(T t2, T t3) {
                return t3.getVerticalDistance() >= t2.getVerticalDistance() ? t3 : t2;
            }
        }

        public static class FarthestAndOldestPrioritySelector implements Selector {
            @Override // cn.hutool.core.annotation.Hierarchical.Selector
            public <T extends Hierarchical> T choose(T t2, T t3) {
                return t3.getVerticalDistance() > t2.getVerticalDistance() ? t3 : t2;
            }
        }

        public static class NearestAndNewestPrioritySelector implements Selector {
            @Override // cn.hutool.core.annotation.Hierarchical.Selector
            public <T extends Hierarchical> T choose(T t2, T t3) {
                return t3.getVerticalDistance() <= t2.getVerticalDistance() ? t3 : t2;
            }
        }

        public static class NearestAndOldestPrioritySelector implements Selector {
            @Override // cn.hutool.core.annotation.Hierarchical.Selector
            public <T extends Hierarchical> T choose(T t2, T t3) {
                return t3.getVerticalDistance() < t2.getVerticalDistance() ? t3 : t2;
            }
        }

        <T extends Hierarchical> T choose(T t2, T t3);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    int compareTo(Hierarchical hierarchical);

    @Override // java.lang.Comparable
    /* bridge */ /* synthetic */ int compareTo(Hierarchical hierarchical);

    int getHorizontalDistance();

    Object getRoot();

    int getVerticalDistance();
}
