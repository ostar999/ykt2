package org.apache.http;

/* loaded from: classes9.dex */
public interface Header {
    HeaderElement[] getElements() throws ParseException;

    String getName();

    String getValue();
}
