package org.eclipse.jetty.util.ajax;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.ajax.JSON;

/* loaded from: classes9.dex */
public class JSONCollectionConvertor implements JSON.Convertor {
    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    public Object fromJSON(Map map) {
        try {
            Collection collection = (Collection) Loader.loadClass(getClass(), (String) map.get("class")).newInstance();
            Collections.addAll(collection, (Object[]) map.get("list"));
            return collection;
        } catch (Exception e2) {
            if (e2 instanceof RuntimeException) {
                throw ((RuntimeException) e2);
            }
            throw new RuntimeException(e2);
        }
    }

    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    public void toJSON(Object obj, JSON.Output output) {
        output.addClass(obj.getClass());
        output.add("list", ((Collection) obj).toArray());
    }
}
