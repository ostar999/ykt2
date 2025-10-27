package org.eclipse.jetty.client.webdav;

import java.io.IOException;
import org.eclipse.jetty.client.HttpExchange;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class PropfindExchange extends HttpExchange {
    private static final Logger LOG = Log.getLogger((Class<?>) PropfindExchange.class);
    boolean _propertyExists = false;

    public boolean exists() {
        return this._propertyExists;
    }

    @Override // org.eclipse.jetty.client.HttpExchange
    public void onResponseStatus(Buffer buffer, int i2, Buffer buffer2) throws IOException {
        if (i2 == 200) {
            LOG.debug("PropfindExchange:Status: Exists", new Object[0]);
            this._propertyExists = true;
        } else {
            LOG.debug("PropfindExchange:Status: Not Exists", new Object[0]);
        }
        super.onResponseStatus(buffer, i2, buffer2);
    }
}
