package cn.hutool.core.lang;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class WeightRandom<T> implements Serializable {
    private static final long serialVersionUID = -8244697995702786499L;
    private final TreeMap<Double, T> weightMap;

    public static class WeightObj<T> {
        private T obj;
        private final double weight;

        public WeightObj(T t2, double d3) {
            this.obj = t2;
            this.weight = d3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            WeightObj weightObj = (WeightObj) obj;
            T t2 = this.obj;
            if (t2 == null) {
                if (weightObj.obj != null) {
                    return false;
                }
            } else if (!t2.equals(weightObj.obj)) {
                return false;
            }
            return Double.doubleToLongBits(this.weight) == Double.doubleToLongBits(weightObj.weight);
        }

        public T getObj() {
            return this.obj;
        }

        public double getWeight() {
            return this.weight;
        }

        public int hashCode() {
            T t2 = this.obj;
            int iHashCode = t2 == null ? 0 : t2.hashCode();
            long jDoubleToLongBits = Double.doubleToLongBits(this.weight);
            return ((iHashCode + 31) * 31) + ((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32)));
        }

        public void setObj(T t2) {
            this.obj = t2;
        }
    }

    public WeightRandom() {
        this.weightMap = new TreeMap<>();
    }

    public static <T> WeightRandom<T> create() {
        return new WeightRandom<>();
    }

    public WeightRandom<T> add(T t2, double d3) {
        return add(new WeightObj<>(t2, d3));
    }

    public WeightRandom<T> clear() {
        TreeMap<Double, T> treeMap = this.weightMap;
        if (treeMap != null) {
            treeMap.clear();
        }
        return this;
    }

    public T next() {
        if (MapUtil.isEmpty(this.weightMap)) {
            return null;
        }
        return this.weightMap.get(this.weightMap.tailMap(Double.valueOf(this.weightMap.lastKey().doubleValue() * RandomUtil.getRandom().nextDouble()), false).firstKey());
    }

    public WeightRandom<T> add(WeightObj<T> weightObj) {
        if (weightObj != null) {
            double weight = weightObj.getWeight();
            if (weightObj.getWeight() > 0.0d) {
                this.weightMap.put(Double.valueOf(weight + (this.weightMap.size() != 0 ? this.weightMap.lastKey().doubleValue() : 0.0d)), weightObj.getObj());
            }
        }
        return this;
    }

    public WeightRandom(WeightObj<T> weightObj) {
        this();
        if (weightObj != null) {
            add(weightObj);
        }
    }

    public WeightRandom(Iterable<WeightObj<T>> iterable) {
        this();
        if (CollUtil.isNotEmpty(iterable)) {
            Iterator<WeightObj<T>> it = iterable.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
        }
    }

    public WeightRandom(WeightObj<T>[] weightObjArr) {
        this();
        for (WeightObj<T> weightObj : weightObjArr) {
            add(weightObj);
        }
    }
}
