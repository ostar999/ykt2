package org.apache.http.cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.params.HttpParams;

@ThreadSafe
/* loaded from: classes9.dex */
public final class CookieSpecRegistry {
    private final ConcurrentHashMap<String, CookieSpecFactory> registeredSpecs = new ConcurrentHashMap<>();

    public CookieSpec getCookieSpec(String str, HttpParams httpParams) throws IllegalStateException {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        CookieSpecFactory cookieSpecFactory = this.registeredSpecs.get(str.toLowerCase(Locale.ENGLISH));
        if (cookieSpecFactory != null) {
            return cookieSpecFactory.newInstance(httpParams);
        }
        throw new IllegalStateException("Unsupported cookie spec: " + str);
    }

    public List<String> getSpecNames() {
        return new ArrayList(this.registeredSpecs.keySet());
    }

    public void register(String str, CookieSpecFactory cookieSpecFactory) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        if (cookieSpecFactory == null) {
            throw new IllegalArgumentException("Cookie spec factory may not be null");
        }
        this.registeredSpecs.put(str.toLowerCase(Locale.ENGLISH), cookieSpecFactory);
    }

    public void setItems(Map<String, CookieSpecFactory> map) {
        if (map == null) {
            return;
        }
        this.registeredSpecs.clear();
        this.registeredSpecs.putAll(map);
    }

    public void unregister(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        this.registeredSpecs.remove(str.toLowerCase(Locale.ENGLISH));
    }

    public CookieSpec getCookieSpec(String str) throws IllegalStateException {
        return getCookieSpec(str, null);
    }
}
