package cn.hutool.core.comparator;

import cn.hutool.core.util.ObjectUtil;
import java.util.function.Function;

/* loaded from: classes.dex */
public class FuncComparator<T> extends NullComparator<T> {
    private static final long serialVersionUID = 1;
    private final boolean compareSelf;
    private final Function<T, Comparable<?>> func;

    public FuncComparator(boolean z2, Function<T, Comparable<?>> function) {
        this(z2, true, function);
    }

    private int compare(T t2, T t3, Comparable comparable, Comparable comparable2) {
        int iCompare = ObjectUtil.compare(comparable, comparable2, this.nullGreater);
        return (this.compareSelf && iCompare == 0) ? CompareUtil.compare(t2, t3, this.nullGreater) : iCompare;
    }

    @Override // cn.hutool.core.comparator.NullComparator
    public int doCompare(T t2, T t3) {
        try {
            return compare(t2, t3, (Comparable) this.func.apply(t2), (Comparable) this.func.apply(t3));
        } catch (Exception e2) {
            throw new ComparatorException(e2);
        }
    }

    public FuncComparator(boolean z2, boolean z3, Function<T, Comparable<?>> function) {
        super(z2, null);
        this.compareSelf = z3;
        this.func = function;
    }
}
