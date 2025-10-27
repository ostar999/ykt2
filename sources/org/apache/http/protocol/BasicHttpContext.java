package org.apache.http.protocol;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes9.dex */
public class BasicHttpContext implements HttpContext {
    private Map map;
    private final HttpContext parentContext;

    public BasicHttpContext() {
        this(null);
    }

    @Override // org.apache.http.protocol.HttpContext
    public Object getAttribute(String str) {
        HttpContext httpContext;
        if (str == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        Map map = this.map;
        Object obj = map != null ? map.get(str) : null;
        return (obj != null || (httpContext = this.parentContext) == null) ? obj : httpContext.getAttribute(str);
    }

    @Override // org.apache.http.protocol.HttpContext
    public Object removeAttribute(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        Map map = this.map;
        if (map != null) {
            return map.remove(str);
        }
        return null;
    }

    @Override // org.apache.http.protocol.HttpContext
    public void setAttribute(String str, Object obj) {
        if (str == null) {
            throw new IllegalArgumentException("Id may not be null");
        }
        if (this.map == null) {
            this.map = new HashMap();
        }
        this.map.put(str, obj);
    }

    public BasicHttpContext(HttpContext httpContext) {
        this.map = null;
        this.parentContext = httpContext;
    }
}
