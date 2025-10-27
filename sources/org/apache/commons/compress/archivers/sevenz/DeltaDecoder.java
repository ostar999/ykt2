package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.tukaani.xz.DeltaOptions;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.UnsupportedOptionsException;

/* loaded from: classes9.dex */
class DeltaDecoder extends CoderBase {
    public DeltaDecoder() {
        super(Number.class);
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
        return new DeltaOptions(getOptionsFromCoder(coder)).getInputStream(inputStream);
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
        try {
            return new DeltaOptions(CoderBase.numberOptionOrDefault(obj, 1)).getOutputStream(new FinishableWrapperOutputStream(outputStream));
        } catch (UnsupportedOptionsException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public byte[] getOptionsAsProperties(Object obj) {
        return new byte[]{(byte) (CoderBase.numberOptionOrDefault(obj, 1) - 1)};
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return Integer.valueOf(getOptionsFromCoder(coder));
    }

    private int getOptionsFromCoder(Coder coder) {
        byte[] bArr = coder.properties;
        if (bArr == null || bArr.length == 0) {
            return 1;
        }
        return (bArr[0] & 255) + 1;
    }
}
