package org.bouncycastle.bcpg;

import cn.hutool.core.text.CharPool;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import okio.Utf8;

/* loaded from: classes9.dex */
public class ArmoredInputStream extends InputStream {
    private static final byte[] decodingTable = new byte[128];
    int bufPtr;
    boolean clearText;
    CRC24 crc;
    boolean crcFound;
    boolean hasHeaders;
    String header;
    Vector headerList;
    InputStream in;
    boolean isEndOfStream;
    int lastC;
    boolean newLineFound;
    int[] outBuf;
    boolean restart;
    boolean start;

    static {
        for (int i2 = 65; i2 <= 90; i2++) {
            decodingTable[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            decodingTable[i3] = (byte) ((i3 - 97) + 26);
        }
        for (int i4 = 48; i4 <= 57; i4++) {
            decodingTable[i4] = (byte) ((i4 - 48) + 52);
        }
        byte[] bArr = decodingTable;
        bArr[43] = 62;
        bArr[47] = Utf8.REPLACEMENT_BYTE;
    }

    public ArmoredInputStream(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public ArmoredInputStream(InputStream inputStream, boolean z2) throws IOException {
        this.start = true;
        this.outBuf = new int[3];
        this.bufPtr = 3;
        this.crc = new CRC24();
        this.crcFound = false;
        this.hasHeaders = true;
        this.header = null;
        this.newLineFound = false;
        this.clearText = false;
        this.restart = false;
        this.headerList = new Vector();
        this.lastC = 0;
        this.in = inputStream;
        this.hasHeaders = z2;
        if (z2) {
            parseHeaders();
        }
        this.start = false;
    }

    private int decode(int i2, int i3, int i4, int i5, int[] iArr) throws EOFException {
        if (i5 < 0) {
            throw new EOFException("unexpected end of file in armored stream.");
        }
        if (i4 == 61) {
            byte[] bArr = decodingTable;
            iArr[2] = (((bArr[i2] & 255) << 2) | ((bArr[i3] & 255) >> 4)) & 255;
            return 2;
        }
        if (i5 == 61) {
            byte[] bArr2 = decodingTable;
            byte b3 = bArr2[i2];
            byte b4 = bArr2[i3];
            byte b5 = bArr2[i4];
            iArr[1] = ((b3 << 2) | (b4 >> 4)) & 255;
            iArr[2] = ((b4 << 4) | (b5 >> 2)) & 255;
            return 1;
        }
        byte[] bArr3 = decodingTable;
        byte b6 = bArr3[i2];
        byte b7 = bArr3[i3];
        byte b8 = bArr3[i4];
        byte b9 = bArr3[i5];
        iArr[0] = ((b6 << 2) | (b7 >> 4)) & 255;
        iArr[1] = ((b7 << 4) | (b8 >> 2)) & 255;
        iArr[2] = ((b8 << 6) | b9) & 255;
        return 0;
    }

    private boolean parseHeaders() throws IOException {
        int i2;
        boolean z2;
        this.header = null;
        this.headerList = new Vector();
        if (this.restart) {
            z2 = true;
            i2 = 0;
        } else {
            i2 = 0;
            while (true) {
                int i3 = this.in.read();
                if (i3 < 0) {
                    z2 = false;
                    break;
                }
                if (i3 == 45 && (i2 == 0 || i2 == 10 || i2 == 13)) {
                    break;
                }
                i2 = i3;
            }
            z2 = true;
        }
        if (z2) {
            StringBuffer stringBuffer = new StringBuffer("-");
            if (this.restart) {
                stringBuffer.append(CharPool.DASHED);
            }
            boolean z3 = false;
            boolean z4 = false;
            while (true) {
                int i4 = this.in.read();
                if (i4 >= 0) {
                    if (i2 == 13 && i4 == 10) {
                        z4 = true;
                    }
                    if ((z3 && i2 != 13 && i4 == 10) || (z3 && i4 == 13)) {
                        break;
                    }
                    if (i4 == 13 || (i2 != 13 && i4 == 10)) {
                        String string = stringBuffer.toString();
                        if (string.trim().length() == 0) {
                            break;
                        }
                        this.headerList.addElement(string);
                        stringBuffer.setLength(0);
                    }
                    if (i4 != 10 && i4 != 13) {
                        stringBuffer.append((char) i4);
                        z3 = false;
                    } else if (i4 == 13 || (i2 != 13 && i4 == 10)) {
                        z3 = true;
                    }
                    i2 = i4;
                } else {
                    break;
                }
            }
            if (z4) {
                this.in.read();
            }
        }
        if (this.headerList.size() > 0) {
            this.header = (String) this.headerList.elementAt(0);
        }
        this.clearText = "-----BEGIN PGP SIGNED MESSAGE-----".equals(this.header);
        this.newLineFound = true;
        return z2;
    }

    private int readIgnoreSpace() throws IOException {
        while (true) {
            int i2 = this.in.read();
            if (i2 != 32 && i2 != 9) {
                return i2;
            }
        }
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.in.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.in.close();
    }

    public String getArmorHeaderLine() {
        return this.header;
    }

    public String[] getArmorHeaders() {
        if (this.headerList.size() <= 1) {
            return null;
        }
        int size = this.headerList.size() - 1;
        String[] strArr = new String[size];
        int i2 = 0;
        while (i2 != size) {
            int i3 = i2 + 1;
            strArr[i2] = (String) this.headerList.elementAt(i3);
            i2 = i3;
        }
        return strArr;
    }

    public boolean isClearText() {
        return this.clearText;
    }

    public boolean isEndOfStream() {
        return this.isEndOfStream;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int ignoreSpace;
        int iDecode;
        int i2;
        if (this.start) {
            if (this.hasHeaders) {
                parseHeaders();
            }
            this.crc.reset();
            this.start = false;
        }
        if (this.clearText) {
            int i3 = this.in.read();
            if (i3 == 13 || (i3 == 10 && this.lastC != 13)) {
                this.newLineFound = true;
            } else {
                if (this.newLineFound && i3 == 45) {
                    i3 = this.in.read();
                    if (i3 == 45) {
                        this.clearText = false;
                        this.start = true;
                        this.restart = true;
                    } else {
                        i3 = this.in.read();
                    }
                } else if (i3 != 10 && this.lastC != 13) {
                }
                this.newLineFound = false;
            }
            this.lastC = i3;
            if (i3 < 0) {
                this.isEndOfStream = true;
            }
            return i3;
        }
        if (this.bufPtr > 2 || this.crcFound) {
            int ignoreSpace2 = readIgnoreSpace();
            if (ignoreSpace2 == 13 || ignoreSpace2 == 10) {
                while (true) {
                    ignoreSpace = readIgnoreSpace();
                    if (ignoreSpace != 10 && ignoreSpace != 13) {
                        break;
                    }
                }
                if (ignoreSpace < 0) {
                    this.isEndOfStream = true;
                    return -1;
                }
                if (ignoreSpace == 61) {
                    int iDecode2 = decode(readIgnoreSpace(), readIgnoreSpace(), readIgnoreSpace(), readIgnoreSpace(), this.outBuf);
                    this.bufPtr = iDecode2;
                    if (iDecode2 != 0) {
                        throw new IOException("no crc found in armored message.");
                    }
                    int[] iArr = this.outBuf;
                    int i4 = (iArr[2] & 255) | ((iArr[0] & 255) << 16) | ((iArr[1] & 255) << 8);
                    this.crcFound = true;
                    if (i4 == this.crc.getValue()) {
                        return read();
                    }
                    throw new IOException("crc check failed in armored message.");
                }
                if (ignoreSpace == 45) {
                    do {
                        i2 = this.in.read();
                        if (i2 < 0 || i2 == 10) {
                            break;
                        }
                    } while (i2 != 13);
                    if (!this.crcFound) {
                        throw new IOException("crc check not found.");
                    }
                    this.crcFound = false;
                    this.start = true;
                    this.bufPtr = 3;
                    if (i2 < 0) {
                        this.isEndOfStream = true;
                    }
                    return -1;
                }
                iDecode = decode(ignoreSpace, readIgnoreSpace(), readIgnoreSpace(), readIgnoreSpace(), this.outBuf);
            } else {
                if (ignoreSpace2 < 0) {
                    this.isEndOfStream = true;
                    return -1;
                }
                iDecode = decode(ignoreSpace2, readIgnoreSpace(), readIgnoreSpace(), readIgnoreSpace(), this.outBuf);
            }
            this.bufPtr = iDecode;
        }
        int[] iArr2 = this.outBuf;
        int i5 = this.bufPtr;
        this.bufPtr = i5 + 1;
        int i6 = iArr2[i5];
        this.crc.update(i6);
        return i6;
    }
}
