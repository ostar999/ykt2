package org.bouncycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
class RecordStream {
    private TlsProtocolHandler handler;
    private InputStream is;
    private OutputStream os;
    private TlsCipher readCipher;
    private TlsCompression readCompression;
    private TlsCipher writeCipher;
    private TlsCompression writeCompression;
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private CombinedHash hash = new CombinedHash();

    public RecordStream(TlsProtocolHandler tlsProtocolHandler, InputStream inputStream, OutputStream outputStream) {
        this.readCompression = null;
        this.writeCompression = null;
        this.readCipher = null;
        this.writeCipher = null;
        this.handler = tlsProtocolHandler;
        this.is = inputStream;
        this.os = outputStream;
        TlsNullCompression tlsNullCompression = new TlsNullCompression();
        this.readCompression = tlsNullCompression;
        this.writeCompression = tlsNullCompression;
        TlsNullCipher tlsNullCipher = new TlsNullCipher();
        this.readCipher = tlsNullCipher;
        this.writeCipher = tlsNullCipher;
    }

    private static byte[] doFinal(CombinedHash combinedHash) {
        byte[] bArr = new byte[combinedHash.getDigestSize()];
        combinedHash.doFinal(bArr, 0);
        return bArr;
    }

    private byte[] getBufferContents() {
        byte[] byteArray = this.buffer.toByteArray();
        this.buffer.reset();
        return byteArray;
    }

    public void clientCipherSpecDecided(TlsCompression tlsCompression, TlsCipher tlsCipher) {
        this.writeCompression = tlsCompression;
        this.writeCipher = tlsCipher;
    }

    public void close() throws IOException {
        try {
            this.is.close();
            e = null;
        } catch (IOException e2) {
            e = e2;
        }
        try {
            this.os.close();
        } catch (IOException e3) {
            e = e3;
        }
        if (e != null) {
            throw e;
        }
    }

    public byte[] decodeAndVerify(short s2, InputStream inputStream, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        TlsUtils.readFully(bArr, inputStream);
        byte[] bArrDecodeCiphertext = this.readCipher.decodeCiphertext(s2, bArr, 0, i2);
        OutputStream outputStreamDecompress = this.readCompression.decompress(this.buffer);
        if (outputStreamDecompress == this.buffer) {
            return bArrDecodeCiphertext;
        }
        outputStreamDecompress.write(bArrDecodeCiphertext, 0, bArrDecodeCiphertext.length);
        outputStreamDecompress.flush();
        return getBufferContents();
    }

    public void flush() throws IOException {
        this.os.flush();
    }

    public byte[] getCurrentHash() {
        return doFinal(new CombinedHash(this.hash));
    }

    public void readData() throws IOException {
        short uint8 = TlsUtils.readUint8(this.is);
        TlsUtils.checkVersion(this.is, this.handler);
        byte[] bArrDecodeAndVerify = decodeAndVerify(uint8, this.is, TlsUtils.readUint16(this.is));
        this.handler.processData(uint8, bArrDecodeAndVerify, 0, bArrDecodeAndVerify.length);
    }

    public void serverClientSpecReceived() {
        this.readCompression = this.writeCompression;
        this.readCipher = this.writeCipher;
    }

    public void updateHandshakeData(byte[] bArr, int i2, int i3) {
        this.hash.update(bArr, i2, i3);
    }

    public void writeMessage(short s2, byte[] bArr, int i2, int i3) throws IOException {
        byte[] bArrEncodePlaintext;
        if (s2 == 22) {
            updateHandshakeData(bArr, i2, i3);
        }
        OutputStream outputStreamCompress = this.writeCompression.compress(this.buffer);
        if (outputStreamCompress == this.buffer) {
            bArrEncodePlaintext = this.writeCipher.encodePlaintext(s2, bArr, i2, i3);
        } else {
            outputStreamCompress.write(bArr, i2, i3);
            outputStreamCompress.flush();
            byte[] bufferContents = getBufferContents();
            bArrEncodePlaintext = this.writeCipher.encodePlaintext(s2, bufferContents, 0, bufferContents.length);
        }
        byte[] bArr2 = new byte[bArrEncodePlaintext.length + 5];
        TlsUtils.writeUint8(s2, bArr2, 0);
        TlsUtils.writeVersion(bArr2, 1);
        TlsUtils.writeUint16(bArrEncodePlaintext.length, bArr2, 3);
        System.arraycopy(bArrEncodePlaintext, 0, bArr2, 5, bArrEncodePlaintext.length);
        this.os.write(bArr2);
        this.os.flush();
    }
}
