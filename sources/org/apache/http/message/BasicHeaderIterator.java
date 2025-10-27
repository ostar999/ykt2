package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

/* loaded from: classes9.dex */
public class BasicHeaderIterator implements HeaderIterator {
    protected final Header[] allHeaders;
    protected int currentIndex;
    protected String headerName;

    public BasicHeaderIterator(Header[] headerArr, String str) {
        if (headerArr == null) {
            throw new IllegalArgumentException("Header array must not be null.");
        }
        this.allHeaders = headerArr;
        this.headerName = str;
        this.currentIndex = findNext(-1);
    }

    public boolean filterHeader(int i2) {
        String str = this.headerName;
        return str == null || str.equalsIgnoreCase(this.allHeaders[i2].getName());
    }

    public int findNext(int i2) {
        if (i2 < -1) {
            return -1;
        }
        int length = this.allHeaders.length - 1;
        boolean zFilterHeader = false;
        while (!zFilterHeader && i2 < length) {
            i2++;
            zFilterHeader = filterHeader(i2);
        }
        if (zFilterHeader) {
            return i2;
        }
        return -1;
    }

    @Override // org.apache.http.HeaderIterator, java.util.Iterator
    public boolean hasNext() {
        return this.currentIndex >= 0;
    }

    @Override // java.util.Iterator
    public final Object next() throws NoSuchElementException {
        return nextHeader();
    }

    @Override // org.apache.http.HeaderIterator
    public Header nextHeader() throws NoSuchElementException {
        int i2 = this.currentIndex;
        if (i2 < 0) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        this.currentIndex = findNext(i2);
        return this.allHeaders[i2];
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing headers is not supported.");
    }
}
