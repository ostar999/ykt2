package cn.hutool.core.collection;

import java.util.Spliterator;
import java.util.function.Function;

/* loaded from: classes.dex */
public class SpliteratorUtil {
    public static <F, T> Spliterator<T> trans(Spliterator<F> spliterator, Function<? super F, ? extends T> function) {
        return new TransSpliterator(spliterator, function);
    }
}
