package org.apache.commons.compress.compressors.pack200;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
abstract class StreamBridge extends FilterOutputStream {
    private final Object INPUT_LOCK;
    private InputStream input;

    public StreamBridge(OutputStream outputStream) {
        super(outputStream);
        this.INPUT_LOCK = new Object();
    }

    public InputStream getInput() throws IOException {
        synchronized (this.INPUT_LOCK) {
            if (this.input == null) {
                this.input = getInputView();
            }
        }
        return this.input;
    }

    public abstract InputStream getInputView() throws IOException;

    public void stop() throws IOException {
        close();
        synchronized (this.INPUT_LOCK) {
            InputStream inputStream = this.input;
            if (inputStream != null) {
                inputStream.close();
                this.input = null;
            }
        }
    }

    public StreamBridge() {
        this(null);
    }
}
