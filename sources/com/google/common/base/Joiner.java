package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
/* loaded from: classes3.dex */
public class Joiner {
    private final String separator;

    public static final class MapJoiner {
        private final Joiner joiner;
        private final String keyValueSeparator;

        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A a3, Map<?, ?> map) throws IOException {
            return (A) appendTo((MapJoiner) a3, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        public String join(Map<?, ?> map) {
            return join(map.entrySet());
        }

        public MapJoiner useForNull(String str) {
            return new MapJoiner(this.joiner.useForNull(str), this.keyValueSeparator);
        }

        private MapJoiner(Joiner joiner, String str) {
            this.joiner = joiner;
            this.keyValueSeparator = (String) Preconditions.checkNotNull(str);
        }

        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder sb, Map<?, ?> map) {
            return appendTo(sb, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        @Beta
        public String join(Iterable<? extends Map.Entry<?, ?>> iterable) {
            return join(iterable.iterator());
        }

        @CanIgnoreReturnValue
        @Beta
        public <A extends Appendable> A appendTo(A a3, Iterable<? extends Map.Entry<?, ?>> iterable) throws IOException {
            return (A) appendTo((MapJoiner) a3, iterable.iterator());
        }

        @Beta
        public String join(Iterator<? extends Map.Entry<?, ?>> it) {
            return appendTo(new StringBuilder(), it).toString();
        }

        @CanIgnoreReturnValue
        @Beta
        public <A extends Appendable> A appendTo(A a3, Iterator<? extends Map.Entry<?, ?>> it) throws IOException {
            Preconditions.checkNotNull(a3);
            if (it.hasNext()) {
                Map.Entry<?, ?> next = it.next();
                a3.append(this.joiner.toString(next.getKey()));
                a3.append(this.keyValueSeparator);
                a3.append(this.joiner.toString(next.getValue()));
                while (it.hasNext()) {
                    a3.append(this.joiner.separator);
                    Map.Entry<?, ?> next2 = it.next();
                    a3.append(this.joiner.toString(next2.getKey()));
                    a3.append(this.keyValueSeparator);
                    a3.append(this.joiner.toString(next2.getValue()));
                }
            }
            return a3;
        }

        @CanIgnoreReturnValue
        @Beta
        public StringBuilder appendTo(StringBuilder sb, Iterable<? extends Map.Entry<?, ?>> iterable) {
            return appendTo(sb, iterable.iterator());
        }

        @CanIgnoreReturnValue
        @Beta
        public StringBuilder appendTo(StringBuilder sb, Iterator<? extends Map.Entry<?, ?>> it) {
            try {
                appendTo((MapJoiner) sb, it);
                return sb;
            } catch (IOException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    private static Iterable<Object> iterable(final Object obj, final Object obj2, final Object[] objArr) {
        Preconditions.checkNotNull(objArr);
        return new AbstractList<Object>() { // from class: com.google.common.base.Joiner.3
            @Override // java.util.AbstractList, java.util.List
            public Object get(int i2) {
                return i2 != 0 ? i2 != 1 ? objArr[i2 - 2] : obj2 : obj;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return objArr.length + 2;
            }
        };
    }

    public static Joiner on(String str) {
        return new Joiner(str);
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A a3, Iterable<?> iterable) throws IOException {
        return (A) appendTo((Joiner) a3, iterable.iterator());
    }

    public final String join(Iterable<?> iterable) {
        return join(iterable.iterator());
    }

    public Joiner skipNulls() {
        return new Joiner(this) { // from class: com.google.common.base.Joiner.2
            @Override // com.google.common.base.Joiner
            public <A extends Appendable> A appendTo(A a3, Iterator<?> it) throws IOException {
                Preconditions.checkNotNull(a3, "appendable");
                Preconditions.checkNotNull(it, "parts");
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Object next = it.next();
                    if (next != null) {
                        a3.append(Joiner.this.toString(next));
                        break;
                    }
                }
                while (it.hasNext()) {
                    Object next2 = it.next();
                    if (next2 != null) {
                        a3.append(Joiner.this.separator);
                        a3.append(Joiner.this.toString(next2));
                    }
                }
                return a3;
            }

            @Override // com.google.common.base.Joiner
            public Joiner useForNull(String str) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            @Override // com.google.common.base.Joiner
            public MapJoiner withKeyValueSeparator(String str) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    public CharSequence toString(Object obj) {
        Preconditions.checkNotNull(obj);
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }

    public Joiner useForNull(final String str) {
        Preconditions.checkNotNull(str);
        return new Joiner(this) { // from class: com.google.common.base.Joiner.1
            @Override // com.google.common.base.Joiner
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            @Override // com.google.common.base.Joiner
            public CharSequence toString(@NullableDecl Object obj) {
                return obj == null ? str : Joiner.this.toString(obj);
            }

            @Override // com.google.common.base.Joiner
            public Joiner useForNull(String str2) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char c3) {
        return withKeyValueSeparator(String.valueOf(c3));
    }

    private Joiner(String str) {
        this.separator = (String) Preconditions.checkNotNull(str);
    }

    public static Joiner on(char c3) {
        return new Joiner(String.valueOf(c3));
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A a3, Iterator<?> it) throws IOException {
        Preconditions.checkNotNull(a3);
        if (it.hasNext()) {
            a3.append(toString(it.next()));
            while (it.hasNext()) {
                a3.append(this.separator);
                a3.append(toString(it.next()));
            }
        }
        return a3;
    }

    public final String join(Iterator<?> it) {
        return appendTo(new StringBuilder(), it).toString();
    }

    public MapJoiner withKeyValueSeparator(String str) {
        return new MapJoiner(str);
    }

    public final String join(Object[] objArr) {
        return join(Arrays.asList(objArr));
    }

    private Joiner(Joiner joiner) {
        this.separator = joiner.separator;
    }

    public final String join(@NullableDecl Object obj, @NullableDecl Object obj2, Object... objArr) {
        return join(iterable(obj, obj2, objArr));
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A a3, Object[] objArr) throws IOException {
        return (A) appendTo((Joiner) a3, (Iterable<?>) Arrays.asList(objArr));
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A a3, @NullableDecl Object obj, @NullableDecl Object obj2, Object... objArr) throws IOException {
        return (A) appendTo((Joiner) a3, iterable(obj, obj2, objArr));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, Iterable<?> iterable) {
        return appendTo(sb, iterable.iterator());
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, Iterator<?> it) {
        try {
            appendTo((Joiner) sb, it);
            return sb;
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, Object[] objArr) {
        return appendTo(sb, (Iterable<?>) Arrays.asList(objArr));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder sb, @NullableDecl Object obj, @NullableDecl Object obj2, Object... objArr) {
        return appendTo(sb, iterable(obj, obj2, objArr));
    }
}
