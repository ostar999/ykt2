package cn.hutool.core.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.map.MapUtil;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

/* loaded from: classes.dex */
public class JNDIUtil {
    public static InitialContext createInitialContext(Map<String, String> map) {
        try {
            return MapUtil.isEmpty(map) ? new InitialContext() : new InitialContext((Hashtable) Convert.convert(Hashtable.class, (Object) map));
        } catch (NamingException e2) {
            throw new UtilException((Throwable) e2);
        }
    }

    public static InitialDirContext createInitialDirContext(Map<String, String> map) {
        try {
            return MapUtil.isEmpty(map) ? new InitialDirContext() : new InitialDirContext((Hashtable) Convert.convert(Hashtable.class, (Object) map));
        } catch (NamingException e2) {
            throw new UtilException((Throwable) e2);
        }
    }

    public static Attributes getAttributes(String str, String... strArr) {
        try {
            return createInitialDirContext(null).getAttributes(str, strArr);
        } catch (NamingException e2) {
            throw new UtilException((Throwable) e2);
        }
    }
}
