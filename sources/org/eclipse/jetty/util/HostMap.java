package org.eclipse.jetty.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes9.dex */
public class HostMap<TYPE> extends HashMap<String, TYPE> {
    public HostMap() {
        super(11);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public TYPE get(Object obj) {
        return (TYPE) super.get(obj);
    }

    public Object getLazyMatches(String str) {
        int iIndexOf;
        if (str == null) {
            return LazyList.getList(super.entrySet());
        }
        String strTrim = str.trim();
        HashSet hashSet = new HashSet();
        do {
            hashSet.add(strTrim);
            iIndexOf = strTrim.indexOf(46);
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(iIndexOf + 1);
            }
        } while (iIndexOf > 0);
        Object objAdd = null;
        for (Map.Entry entry : super.entrySet()) {
            if (hashSet.contains(entry.getKey())) {
                objAdd = LazyList.add(objAdd, entry);
            }
        }
        return objAdd;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((String) obj, (String) obj2);
    }

    public HostMap(int i2) {
        super(i2);
    }

    public TYPE put(String str, TYPE type) throws IllegalArgumentException {
        return (TYPE) super.put((HostMap<TYPE>) str, (String) type);
    }
}
