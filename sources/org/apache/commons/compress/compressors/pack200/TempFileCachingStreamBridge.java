package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
class TempFileCachingStreamBridge extends StreamBridge {

    /* renamed from: f, reason: collision with root package name */
    private final File f27754f;

    public TempFileCachingStreamBridge() throws IOException {
        File fileCreateTempFile = File.createTempFile("commons-compress", "packtemp");
        this.f27754f = fileCreateTempFile;
        fileCreateTempFile.deleteOnExit();
        ((FilterOutputStream) this).out = new FileOutputStream(fileCreateTempFile);
    }

    @Override // org.apache.commons.compress.compressors.pack200.StreamBridge
    public InputStream getInputView() throws IOException {
        ((FilterOutputStream) this).out.close();
        return new FileInputStream(this.f27754f) { // from class: org.apache.commons.compress.compressors.pack200.TempFileCachingStreamBridge.1
            @Override // java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                super.close();
                TempFileCachingStreamBridge.this.f27754f.delete();
            }
        };
    }
}
