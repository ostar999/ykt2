package org.apache.commons.compress.compressors.pack200;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
class InMemoryCachingStreamBridge extends StreamBridge {
    public InMemoryCachingStreamBridge() {
        super(new ByteArrayOutputStream());
    }

    @Override // org.apache.commons.compress.compressors.pack200.StreamBridge
    public InputStream getInputView() throws IOException {
        return new ByteArrayInputStream(((ByteArrayOutputStream) ((FilterOutputStream) this).out).toByteArray());
    }
}
