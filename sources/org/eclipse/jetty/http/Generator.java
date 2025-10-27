package org.eclipse.jetty.http;

import java.io.IOException;
import org.eclipse.jetty.io.Buffer;

/* loaded from: classes9.dex */
public interface Generator {
    public static final boolean LAST = true;
    public static final boolean MORE = false;

    void addContent(Buffer buffer, boolean z2) throws IOException;

    void complete() throws IOException;

    void completeHeader(HttpFields httpFields, boolean z2) throws IOException;

    int flushBuffer() throws IOException;

    int getContentBufferSize();

    long getContentWritten();

    void increaseContentBufferSize(int i2);

    boolean isAllContentWritten();

    boolean isBufferFull();

    boolean isCommitted();

    boolean isComplete();

    boolean isIdle();

    boolean isPersistent();

    boolean isWritten();

    void reset();

    void resetBuffer();

    void returnBuffers();

    void sendError(int i2, String str, String str2, boolean z2) throws IOException;

    void setContentLength(long j2);

    void setDate(Buffer buffer);

    void setHead(boolean z2);

    void setPersistent(boolean z2);

    void setRequest(String str, String str2);

    void setResponse(int i2, String str);

    void setSendServerVersion(boolean z2);

    void setVersion(int i2);
}
