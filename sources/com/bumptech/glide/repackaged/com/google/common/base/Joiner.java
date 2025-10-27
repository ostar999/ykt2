package com.bumptech.glide.repackaged.com.google.common.base;

import java.io.IOException;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class Joiner {
    private final String separator;

    public static Joiner on(String str) {
        return new Joiner(str);
    }

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

    public final String join(Iterable<?> iterable) {
        return join(iterable.iterator());
    }

    public CharSequence toString(Object obj) {
        Preconditions.checkNotNull(obj);
        return obj instanceof CharSequence ? (CharSequence) obj : obj.toString();
    }

    public Joiner useForNull(final String str) {
        Preconditions.checkNotNull(str);
        return new Joiner(this) { // from class: com.bumptech.glide.repackaged.com.google.common.base.Joiner.1
            @Override // com.bumptech.glide.repackaged.com.google.common.base.Joiner
            public CharSequence toString(Object obj) {
                return obj == null ? str : Joiner.this.toString(obj);
            }

            @Override // com.bumptech.glide.repackaged.com.google.common.base.Joiner
            public Joiner useForNull(String str2) {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    private Joiner(String str) {
        this.separator = (String) Preconditions.checkNotNull(str);
    }

    public static Joiner on(char c3) {
        return new Joiner(String.valueOf(c3));
    }

    public final String join(Iterator<?> it) {
        return appendTo(new StringBuilder(), it).toString();
    }

    private Joiner(Joiner joiner) {
        this.separator = joiner.separator;
    }

    public final StringBuilder appendTo(StringBuilder sb, Iterator<?> it) {
        try {
            appendTo((Joiner) sb, it);
            return sb;
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }
}
