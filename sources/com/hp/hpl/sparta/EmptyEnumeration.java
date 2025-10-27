package com.hp.hpl.sparta;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* loaded from: classes4.dex */
class EmptyEnumeration implements Enumeration {
    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return false;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        throw new NoSuchElementException();
    }
}
