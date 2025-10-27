package org.eclipse.jetty.util.ajax;

import java.lang.reflect.Method;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class JSONEnumConvertor implements JSON.Convertor {
    private static final Logger LOG = Log.getLogger((Class<?>) JSONEnumConvertor.class);
    private boolean _fromJSON;
    private Method _valueOf;

    public JSONEnumConvertor() {
        this(false);
    }

    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    public Object fromJSON(Map map) {
        if (!this._fromJSON) {
            throw new UnsupportedOperationException();
        }
        try {
            return this._valueOf.invoke(null, Loader.loadClass(getClass(), (String) map.get("class")), map.get("value"));
        } catch (Exception e2) {
            LOG.warn(e2);
            return null;
        }
    }

    @Override // org.eclipse.jetty.util.ajax.JSON.Convertor
    public void toJSON(Object obj, JSON.Output output) {
        if (!this._fromJSON) {
            output.add(((Enum) obj).name());
        } else {
            output.addClass(obj.getClass());
            output.add("value", ((Enum) obj).name());
        }
    }

    public JSONEnumConvertor(boolean z2) {
        try {
            this._valueOf = Loader.loadClass(getClass(), "java.lang.Enum").getMethod("valueOf", Class.class, String.class);
            this._fromJSON = z2;
        } catch (Exception e2) {
            throw new RuntimeException("!Enums", e2);
        }
    }
}
