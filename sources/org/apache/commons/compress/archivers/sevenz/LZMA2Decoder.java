package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.tukaani.xz.FinishableWrapperOutputStream;
import org.tukaani.xz.LZMA2InputStream;
import org.tukaani.xz.LZMA2Options;

/* loaded from: classes9.dex */
class LZMA2Decoder extends CoderBase {
    public LZMA2Decoder() {
        super(LZMA2Options.class, Number.class);
    }

    private int getDictSize(Object obj) {
        return obj instanceof LZMA2Options ? ((LZMA2Options) obj).getDictSize() : numberOptionOrDefault(obj);
    }

    private int getDictionarySize(Coder coder) throws IllegalArgumentException {
        int i2 = coder.properties[0] & 255;
        if ((i2 & (-64)) != 0) {
            throw new IllegalArgumentException("Unsupported LZMA2 property bits");
        }
        if (i2 > 40) {
            throw new IllegalArgumentException("Dictionary larger than 4GiB maximum size");
        }
        if (i2 == 40) {
            return -1;
        }
        return ((i2 & 1) | 2) << ((i2 / 2) + 11);
    }

    private LZMA2Options getOptions(Object obj) throws IOException {
        if (obj instanceof LZMA2Options) {
            return (LZMA2Options) obj;
        }
        LZMA2Options lZMA2Options = new LZMA2Options();
        lZMA2Options.setDictSize(numberOptionOrDefault(obj));
        return lZMA2Options;
    }

    private int numberOptionOrDefault(Object obj) {
        return CoderBase.numberOptionOrDefault(obj, 8388608);
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public InputStream decode(String str, InputStream inputStream, long j2, Coder coder, byte[] bArr) throws IOException {
        try {
            return new LZMA2InputStream(inputStream, getDictionarySize(coder));
        } catch (IllegalArgumentException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public OutputStream encode(OutputStream outputStream, Object obj) throws IOException {
        return getOptions(obj).getOutputStream(new FinishableWrapperOutputStream(outputStream));
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public byte[] getOptionsAsProperties(Object obj) {
        return new byte[]{(byte) (((19 - Integer.numberOfLeadingZeros(getDictSize(obj))) * 2) + ((r3 >>> (30 - r0)) - 2))};
    }

    @Override // org.apache.commons.compress.archivers.sevenz.CoderBase
    public Object getOptionsFromCoder(Coder coder, InputStream inputStream) {
        return Integer.valueOf(getDictionarySize(coder));
    }
}
