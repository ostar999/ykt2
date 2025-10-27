package org.apache.commons.codec;

import java.util.Comparator;

/* loaded from: classes9.dex */
public class StringEncoderComparator implements Comparator {
    private final StringEncoder stringEncoder;

    public StringEncoderComparator() {
        this.stringEncoder = null;
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        try {
            return ((Comparable) this.stringEncoder.encode(obj)).compareTo((Comparable) this.stringEncoder.encode(obj2));
        } catch (EncoderException unused) {
            return 0;
        }
    }

    public StringEncoderComparator(StringEncoder stringEncoder) {
        this.stringEncoder = stringEncoder;
    }
}
