package org.apache.http.impl.conn;

import com.yikaobang.yixue.R2;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;

@ThreadSafe
/* loaded from: classes9.dex */
public final class SchemeRegistryFactory {
    public static SchemeRegistry createDefault() {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", R2.attr.banner_indicator_selected_color, SSLSocketFactory.getSocketFactory()));
        return schemeRegistry;
    }
}
