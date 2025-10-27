package org.apache.commons.compress.archivers.zip;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/* loaded from: classes9.dex */
class NioZipEncoding implements ZipEncoding {
    private final Charset charset;

    public NioZipEncoding(Charset charset) {
        this.charset = charset;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
    public boolean canEncode(String str) {
        CharsetEncoder charsetEncoderNewEncoder = this.charset.newEncoder();
        charsetEncoderNewEncoder.onMalformedInput(CodingErrorAction.REPORT);
        charsetEncoderNewEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return charsetEncoderNewEncoder.canEncode(str);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
    public String decode(byte[] bArr) throws IOException {
        return this.charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(bArr)).toString();
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipEncoding
    public ByteBuffer encode(String str) {
        CharsetEncoder charsetEncoderNewEncoder = this.charset.newEncoder();
        charsetEncoderNewEncoder.onMalformedInput(CodingErrorAction.REPORT);
        charsetEncoderNewEncoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        CharBuffer charBufferWrap = CharBuffer.wrap(str);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(str.length() + ((str.length() + 1) / 2));
        while (true) {
            if (charBufferWrap.remaining() <= 0) {
                break;
            }
            CoderResult coderResultEncode = charsetEncoderNewEncoder.encode(charBufferWrap, byteBufferAllocate, true);
            if (!coderResultEncode.isUnmappable() && !coderResultEncode.isMalformed()) {
                if (!coderResultEncode.isOverflow()) {
                    if (coderResultEncode.isUnderflow()) {
                        charsetEncoderNewEncoder.flush(byteBufferAllocate);
                        break;
                    }
                } else {
                    byteBufferAllocate = ZipEncodingHelper.growBuffer(byteBufferAllocate, 0);
                }
            } else {
                if (coderResultEncode.length() * 6 > byteBufferAllocate.remaining()) {
                    byteBufferAllocate = ZipEncodingHelper.growBuffer(byteBufferAllocate, byteBufferAllocate.position() + (coderResultEncode.length() * 6));
                }
                for (int i2 = 0; i2 < coderResultEncode.length(); i2++) {
                    ZipEncodingHelper.appendSurrogate(byteBufferAllocate, charBufferWrap.get());
                }
            }
        }
        byteBufferAllocate.limit(byteBufferAllocate.position());
        byteBufferAllocate.rewind();
        return byteBufferAllocate;
    }
}
