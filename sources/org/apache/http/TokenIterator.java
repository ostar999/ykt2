package org.apache.http;

import java.util.Iterator;

/* loaded from: classes9.dex */
public interface TokenIterator extends Iterator {
    @Override // java.util.Iterator
    boolean hasNext();

    String nextToken();
}
