package org.apache.http;

/* loaded from: classes9.dex */
public interface RequestLine {
    String getMethod();

    ProtocolVersion getProtocolVersion();

    String getUri();
}
