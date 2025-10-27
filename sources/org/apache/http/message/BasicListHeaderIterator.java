package org.apache.http.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;

/* loaded from: classes9.dex */
public class BasicListHeaderIterator implements HeaderIterator {
    protected final List allHeaders;
    protected int currentIndex;
    protected String headerName;
    protected int lastIndex;

    public BasicListHeaderIterator(List list, String str) {
        if (list == null) {
            throw new IllegalArgumentException("Header list must not be null.");
        }
        this.allHeaders = list;
        this.headerName = str;
        this.currentIndex = findNext(-1);
        this.lastIndex = -1;
    }

    public boolean filterHeader(int i2) {
        if (this.headerName == null) {
            return true;
        }
        return this.headerName.equalsIgnoreCase(((Header) this.allHeaders.get(i2)).getName());
    }

    public int findNext(int i2) {
        if (i2 < -1) {
            return -1;
        }
        int size = this.allHeaders.size() - 1;
        boolean zFilterHeader = false;
        while (!zFilterHeader && i2 < size) {
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
        this.lastIndex = i2;
        this.currentIndex = findNext(i2);
        return (Header) this.allHeaders.get(i2);
    }

    @Override // java.util.Iterator
    public void remove() throws UnsupportedOperationException {
        int i2 = this.lastIndex;
        if (i2 < 0) {
            throw new IllegalStateException("No header to remove.");
        }
        this.allHeaders.remove(i2);
        this.lastIndex = -1;
        this.currentIndex--;
    }
}
