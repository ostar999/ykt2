package org.apache.http.impl.cookie;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;

@NotThreadSafe
/* loaded from: classes9.dex */
public abstract class AbstractCookieSpec implements CookieSpec {
    private final Map<String, CookieAttributeHandler> attribHandlerMap = new HashMap(10);

    public CookieAttributeHandler findAttribHandler(String str) {
        return this.attribHandlerMap.get(str);
    }

    public CookieAttributeHandler getAttribHandler(String str) {
        CookieAttributeHandler cookieAttributeHandlerFindAttribHandler = findAttribHandler(str);
        if (cookieAttributeHandlerFindAttribHandler != null) {
            return cookieAttributeHandlerFindAttribHandler;
        }
        throw new IllegalStateException("Handler not registered for " + str + " attribute.");
    }

    public Collection<CookieAttributeHandler> getAttribHandlers() {
        return this.attribHandlerMap.values();
    }

    public void registerAttribHandler(String str, CookieAttributeHandler cookieAttributeHandler) {
        if (str == null) {
            throw new IllegalArgumentException("Attribute name may not be null");
        }
        if (cookieAttributeHandler == null) {
            throw new IllegalArgumentException("Attribute handler may not be null");
        }
        this.attribHandlerMap.put(str, cookieAttributeHandler);
    }
}
