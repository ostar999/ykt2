package org.bouncycastle.util;

import java.util.Collection;

/* loaded from: classes9.dex */
public interface Store {
    Collection getMatches(Selector selector) throws StoreException;
}
