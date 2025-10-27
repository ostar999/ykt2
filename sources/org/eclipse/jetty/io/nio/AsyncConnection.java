package org.eclipse.jetty.io.nio;

import java.io.IOException;
import org.eclipse.jetty.io.Connection;

/* loaded from: classes9.dex */
public interface AsyncConnection extends Connection {
    void onInputShutdown() throws IOException;
}
