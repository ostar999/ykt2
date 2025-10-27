package org.eclipse.jetty.util;

import cn.hutool.core.text.StrPool;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/* loaded from: classes9.dex */
public class IPAddressMap<TYPE> extends HashMap<String, TYPE> {
    private final HashMap<String, IPAddrPattern> _patterns;

    public static class IPAddrPattern {
        private final OctetPattern[] _octets = new OctetPattern[4];

        public IPAddrPattern(String str) throws IllegalArgumentException {
            if (str == null || str.trim().length() == 0) {
                throw new IllegalArgumentException("Invalid IP address pattern: " + str);
            }
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(str, StrPool.DOT);
                for (int i2 = 0; i2 < 4; i2++) {
                    String str2 = "0-255";
                    String strTrim = stringTokenizer.hasMoreTokens() ? stringTokenizer.nextToken().trim() : "0-255";
                    int length = strTrim.length();
                    if (length == 0 && stringTokenizer.hasMoreTokens()) {
                        throw new IllegalArgumentException("Invalid IP address pattern: " + str);
                    }
                    OctetPattern[] octetPatternArr = this._octets;
                    if (length != 0) {
                        str2 = strTrim;
                    }
                    octetPatternArr[i2] = new OctetPattern(str2);
                }
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Invalid IP address pattern: " + str, e2);
            }
        }

        public boolean match(String str) throws IllegalArgumentException {
            if (str == null || str.trim().length() == 0) {
                throw new IllegalArgumentException("Invalid IP address: " + str);
            }
            try {
                StringTokenizer stringTokenizer = new StringTokenizer(str, StrPool.DOT);
                boolean zMatch = true;
                for (int i2 = 0; i2 < 4; i2++) {
                    if (!stringTokenizer.hasMoreTokens()) {
                        throw new IllegalArgumentException("Invalid IP address: " + str);
                    }
                    zMatch &= this._octets[i2].match(stringTokenizer.nextToken());
                    if (!zMatch) {
                        break;
                    }
                }
                return zMatch;
            } catch (IllegalArgumentException e2) {
                throw new IllegalArgumentException("Invalid IP address: " + str, e2);
            }
        }
    }

    public IPAddressMap() {
        super(11);
        this._patterns = new HashMap<>();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public TYPE get(Object obj) {
        return (TYPE) super.get(obj);
    }

    public Object getLazyMatches(String str) {
        if (str == null) {
            return LazyList.getList(super.entrySet());
        }
        Object objAdd = null;
        for (Map.Entry entry : super.entrySet()) {
            if (this._patterns.get(entry.getKey()).match(str)) {
                objAdd = LazyList.add(objAdd, entry);
            }
        }
        return objAdd;
    }

    public Map.Entry<String, TYPE> getMatch(String str) {
        if (str == null) {
            return null;
        }
        for (Map.Entry<String, TYPE> entry : super.entrySet()) {
            if (this._patterns.get(entry.getKey()).match(str)) {
                return entry;
            }
        }
        return null;
    }

    public TYPE match(String str) {
        Map.Entry<String, TYPE> match = getMatch(str);
        if (match == null) {
            return null;
        }
        return match.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((String) obj, (String) obj2);
    }

    public TYPE put(String str, TYPE type) throws IllegalArgumentException {
        if (str == null || str.trim().length() == 0) {
            throw new IllegalArgumentException("Invalid IP address pattern: " + str);
        }
        String strTrim = str.trim();
        if (this._patterns.get(strTrim) == null) {
            this._patterns.put(strTrim, new IPAddrPattern(strTrim));
        }
        return (TYPE) super.put((IPAddressMap<TYPE>) strTrim, (String) type);
    }

    public IPAddressMap(int i2) {
        super(i2);
        this._patterns = new HashMap<>();
    }

    public static class OctetPattern extends BitSet {
        private final BitSet _mask;

        public OctetPattern(String str) throws IllegalArgumentException {
            BitSet bitSet = new BitSet(256);
            this._mask = bitSet;
            if (str != null) {
                try {
                    String strTrim = str.trim();
                    if (strTrim.length() == 0) {
                        bitSet.set(0, 255);
                        return;
                    }
                    StringTokenizer stringTokenizer = new StringTokenizer(strTrim, ",");
                    while (stringTokenizer.hasMoreTokens()) {
                        String strTrim2 = stringTokenizer.nextToken().trim();
                        if (strTrim2.length() > 0) {
                            if (strTrim2.indexOf(45) < 0) {
                                this._mask.set(Integer.valueOf(strTrim2).intValue());
                            } else {
                                String[] strArrSplit = strTrim2.split("-", -2);
                                if (strArrSplit.length != 2) {
                                    throw new IllegalArgumentException("Invalid octet spec: " + str);
                                }
                                int i2 = strArrSplit[0].length() > 0 ? Integer.parseInt(strArrSplit[0]) : 0;
                                int i3 = strArrSplit[1].length() > 0 ? Integer.parseInt(strArrSplit[1]) : 255;
                                if (i2 > i3) {
                                    throw new IllegalArgumentException("Invalid octet spec: " + str);
                                }
                                this._mask.set(i2, i3 + 1);
                            }
                        }
                    }
                } catch (NumberFormatException e2) {
                    throw new IllegalArgumentException("Invalid octet spec: " + str, e2);
                }
            }
        }

        public boolean match(String str) throws IllegalArgumentException {
            if (str == null || str.trim().length() == 0) {
                throw new IllegalArgumentException("Invalid octet: " + str);
            }
            try {
                return match(Integer.parseInt(str));
            } catch (NumberFormatException unused) {
                throw new IllegalArgumentException("Invalid octet: " + str);
            }
        }

        public boolean match(int i2) throws IllegalArgumentException {
            if (i2 >= 0 && i2 <= 255) {
                return this._mask.get(i2);
            }
            throw new IllegalArgumentException("Invalid octet: " + i2);
        }
    }
}
