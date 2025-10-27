package cn.hutool.core.lang;

import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/* loaded from: classes.dex */
public class Tuple extends CloneSupport<Tuple> implements Iterable<Object>, Serializable {
    private static final long serialVersionUID = -7689304393482182157L;
    private boolean cacheHash;
    private int hashCode;
    private final Object[] members;

    public Tuple(Object... objArr) {
        this.members = objArr;
    }

    public boolean contains(Object obj) {
        return ArrayUtil.contains(this.members, obj);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            return Arrays.deepEquals(this.members, ((Tuple) obj).members);
        }
        return false;
    }

    public <T> T get(int i2) {
        return (T) this.members[i2];
    }

    public Object[] getMembers() {
        return this.members;
    }

    public int hashCode() {
        int i2;
        if (this.cacheHash && (i2 = this.hashCode) != 0) {
            return i2;
        }
        int iDeepHashCode = 31 + Arrays.deepHashCode(this.members);
        if (this.cacheHash) {
            this.hashCode = iDeepHashCode;
        }
        return iDeepHashCode;
    }

    @Override // java.lang.Iterable
    public Iterator<Object> iterator() {
        return new ArrayIter(this.members);
    }

    public final Stream<Object> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    public Tuple setCacheHash(boolean z2) {
        this.cacheHash = z2;
        return this;
    }

    public int size() {
        return this.members.length;
    }

    @Override // java.lang.Iterable
    public final Spliterator<Object> spliterator() {
        return Spliterators.spliterator(this.members, 16);
    }

    public final Stream<Object> stream() {
        return Arrays.stream(this.members);
    }

    public final Tuple sub(int i2, int i3) {
        return new Tuple(ArrayUtil.sub(this.members, i2, i3));
    }

    public final List<Object> toList() {
        return ListUtil.toList(this.members);
    }

    public String toString() {
        return Arrays.toString(this.members);
    }
}
