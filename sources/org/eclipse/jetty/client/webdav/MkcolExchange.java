package org.eclipse.jetty.client.webdav;

import java.io.IOException;
import org.eclipse.jetty.client.CachedExchange;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class MkcolExchange extends CachedExchange {
    private static final Logger LOG = Log.getLogger((Class<?>) MkcolExchange.class);
    boolean exists;

    public MkcolExchange() {
        super(true);
        this.exists = false;
    }

    public boolean exists() {
        return this.exists;
    }

    @Override // org.eclipse.jetty.client.CachedExchange, org.eclipse.jetty.client.HttpExchange
    public void onResponseStatus(Buffer buffer, int i2, Buffer buffer2) throws IOException {
        if (i2 == 201) {
            LOG.debug("MkcolExchange:Status: Successfully created resource", new Object[0]);
            this.exists = true;
        }
        if (i2 == 405) {
            LOG.debug("MkcolExchange:Status: Resource must exist", new Object[0]);
            this.exists = true;
        }
        super.onResponseStatus(buffer, i2, buffer2);
    }
}
