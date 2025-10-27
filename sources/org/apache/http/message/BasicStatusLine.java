package org.apache.http.message;

import java.io.Serializable;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public class BasicStatusLine implements StatusLine, Cloneable, Serializable {
    private static final long serialVersionUID = -2443303766890459269L;
    private final ProtocolVersion protoVersion;
    private final String reasonPhrase;
    private final int statusCode;

    public BasicStatusLine(ProtocolVersion protocolVersion, int i2, String str) {
        if (protocolVersion == null) {
            throw new IllegalArgumentException("Protocol version may not be null.");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Status code may not be negative.");
        }
        this.protoVersion = protocolVersion;
        this.statusCode = i2;
        this.reasonPhrase = str;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override // org.apache.http.StatusLine
    public ProtocolVersion getProtocolVersion() {
        return this.protoVersion;
    }

    @Override // org.apache.http.StatusLine
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    @Override // org.apache.http.StatusLine
    public int getStatusCode() {
        return this.statusCode;
    }

    public String toString() {
        return BasicLineFormatter.DEFAULT.formatStatusLine((CharArrayBuffer) null, this).toString();
    }
}
