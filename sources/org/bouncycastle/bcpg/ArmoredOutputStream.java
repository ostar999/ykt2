package org.bouncycastle.bcpg;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes9.dex */
public class ArmoredOutputStream extends OutputStream {
    private static final byte[] encodingTable = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, TarConstants.LF_GNUTYPE_LONGLINK, TarConstants.LF_GNUTYPE_LONGNAME, 77, 78, 79, 80, 81, 82, TarConstants.LF_GNUTYPE_SPARSE, 84, 85, 86, 87, TarConstants.LF_PAX_EXTENDED_HEADER_UC, 89, 90, 97, 98, 99, 100, 101, 102, TarConstants.LF_PAX_GLOBAL_EXTENDED_HEADER, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, TarConstants.LF_PAX_EXTENDED_HEADER_LC, 121, 122, TarConstants.LF_NORMAL, TarConstants.LF_LINK, TarConstants.LF_SYMLINK, TarConstants.LF_CHR, TarConstants.LF_BLK, TarConstants.LF_DIR, TarConstants.LF_FIFO, TarConstants.LF_CONTIG, 56, 57, 43, 47};
    int[] buf;
    int bufPtr;
    int chunkCount;
    boolean clearText;
    CRC24 crc;
    String footerStart;
    String footerTail;
    String headerStart;
    String headerTail;
    Hashtable headers;
    int lastb;
    boolean newLine;
    String nl;
    OutputStream out;
    boolean start;
    String type;
    String version;

    public ArmoredOutputStream(OutputStream outputStream) {
        this.buf = new int[3];
        this.bufPtr = 0;
        this.crc = new CRC24();
        this.chunkCount = 0;
        this.start = true;
        this.clearText = false;
        this.newLine = false;
        this.nl = System.getProperty("line.separator");
        this.headerStart = "-----BEGIN PGP ";
        this.headerTail = "-----";
        this.footerStart = "-----END PGP ";
        this.footerTail = "-----";
        this.version = "BCPG v1.46";
        this.headers = new Hashtable();
        this.out = outputStream;
        if (this.nl == null) {
            this.nl = "\r\n";
        }
        resetHeaders();
    }

    public ArmoredOutputStream(OutputStream outputStream, Hashtable hashtable) {
        this(outputStream);
        Enumeration enumerationKeys = hashtable.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            this.headers.put(objNextElement, hashtable.get(objNextElement));
        }
    }

    private void encode(OutputStream outputStream, int[] iArr, int i2) throws IOException {
        if (i2 != 0) {
            if (i2 == 1) {
                int i3 = iArr[0];
                byte[] bArr = encodingTable;
                outputStream.write(bArr[(i3 >>> 2) & 63]);
                outputStream.write(bArr[(i3 << 4) & 63]);
                outputStream.write(61);
            } else {
                if (i2 != 2) {
                    if (i2 != 3) {
                        throw new IOException("unknown length in encode");
                    }
                    int i4 = iArr[0];
                    int i5 = iArr[1];
                    int i6 = iArr[2];
                    byte[] bArr2 = encodingTable;
                    outputStream.write(bArr2[(i4 >>> 2) & 63]);
                    outputStream.write(bArr2[((i4 << 4) | (i5 >>> 4)) & 63]);
                    outputStream.write(bArr2[((i5 << 2) | (i6 >>> 6)) & 63]);
                    outputStream.write(bArr2[i6 & 63]);
                    return;
                }
                int i7 = iArr[0];
                int i8 = iArr[1];
                byte[] bArr3 = encodingTable;
                outputStream.write(bArr3[(i7 >>> 2) & 63]);
                outputStream.write(bArr3[((i7 << 4) | (i8 >>> 4)) & 63]);
                outputStream.write(bArr3[(i8 << 2) & 63]);
            }
            outputStream.write(61);
        }
    }

    private void writeHeaderEntry(String str, String str2) throws IOException {
        for (int i2 = 0; i2 != str.length(); i2++) {
            this.out.write(str.charAt(i2));
        }
        this.out.write(58);
        this.out.write(32);
        for (int i3 = 0; i3 != str2.length(); i3++) {
            this.out.write(str2.charAt(i3));
        }
        for (int i4 = 0; i4 != this.nl.length(); i4++) {
            this.out.write(this.nl.charAt(i4));
        }
    }

    public void beginClearText(int i2) throws IOException {
        String str;
        if (i2 == 1) {
            str = "MD5";
        } else if (i2 == 2) {
            str = "SHA1";
        } else if (i2 == 3) {
            str = "RIPEMD160";
        } else if (i2 != 5) {
            switch (i2) {
                case 8:
                    str = "SHA256";
                    break;
                case 9:
                    str = "SHA384";
                    break;
                case 10:
                    str = "SHA512";
                    break;
                default:
                    throw new IOException("unknown hash algorithm tag in beginClearText: " + i2);
            }
        } else {
            str = "MD2";
        }
        String str2 = "-----BEGIN PGP SIGNED MESSAGE-----" + this.nl;
        String str3 = "Hash: " + str + this.nl + this.nl;
        for (int i3 = 0; i3 != str2.length(); i3++) {
            this.out.write(str2.charAt(i3));
        }
        for (int i4 = 0; i4 != str3.length(); i4++) {
            this.out.write(str3.charAt(i4));
        }
        this.clearText = true;
        this.newLine = true;
        this.lastb = 0;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.type != null) {
            encode(this.out, this.buf, this.bufPtr);
            for (int i2 = 0; i2 != this.nl.length(); i2++) {
                this.out.write(this.nl.charAt(i2));
            }
            this.out.write(61);
            int value = this.crc.getValue();
            int[] iArr = this.buf;
            iArr[0] = (value >> 16) & 255;
            iArr[1] = (value >> 8) & 255;
            iArr[2] = value & 255;
            encode(this.out, iArr, 3);
            for (int i3 = 0; i3 != this.nl.length(); i3++) {
                this.out.write(this.nl.charAt(i3));
            }
            for (int i4 = 0; i4 != this.footerStart.length(); i4++) {
                this.out.write(this.footerStart.charAt(i4));
            }
            for (int i5 = 0; i5 != this.type.length(); i5++) {
                this.out.write(this.type.charAt(i5));
            }
            for (int i6 = 0; i6 != this.footerTail.length(); i6++) {
                this.out.write(this.footerTail.charAt(i6));
            }
            for (int i7 = 0; i7 != this.nl.length(); i7++) {
                this.out.write(this.nl.charAt(i7));
            }
            this.out.flush();
            this.type = null;
            this.start = true;
        }
    }

    public void endClearText() {
        this.clearText = false;
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
    }

    public void resetHeaders() {
        this.headers.clear();
        this.headers.put("Version", this.version);
    }

    public void setHeader(String str, String str2) {
        this.headers.put(str, str2);
    }

    @Override // java.io.OutputStream
    public void write(int i2) throws IOException {
        if (this.clearText) {
            this.out.write(i2);
            if (this.newLine) {
                if (i2 != 10 || this.lastb != 13) {
                    this.newLine = false;
                }
                if (i2 == 45) {
                    this.out.write(32);
                    this.out.write(45);
                }
            }
            if (i2 == 13 || (i2 == 10 && this.lastb != 13)) {
                this.newLine = true;
            }
            this.lastb = i2;
            return;
        }
        if (this.start) {
            int i3 = (i2 & 64) != 0 ? i2 & 63 : (i2 & 63) >> 2;
            this.type = i3 != 2 ? i3 != 5 ? i3 != 6 ? "MESSAGE" : "PUBLIC KEY BLOCK" : "PRIVATE KEY BLOCK" : "SIGNATURE";
            for (int i4 = 0; i4 != this.headerStart.length(); i4++) {
                this.out.write(this.headerStart.charAt(i4));
            }
            for (int i5 = 0; i5 != this.type.length(); i5++) {
                this.out.write(this.type.charAt(i5));
            }
            for (int i6 = 0; i6 != this.headerTail.length(); i6++) {
                this.out.write(this.headerTail.charAt(i6));
            }
            for (int i7 = 0; i7 != this.nl.length(); i7++) {
                this.out.write(this.nl.charAt(i7));
            }
            writeHeaderEntry("Version", (String) this.headers.get("Version"));
            Enumeration enumerationKeys = this.headers.keys();
            while (enumerationKeys.hasMoreElements()) {
                String str = (String) enumerationKeys.nextElement();
                if (!str.equals("Version")) {
                    writeHeaderEntry(str, (String) this.headers.get(str));
                }
            }
            for (int i8 = 0; i8 != this.nl.length(); i8++) {
                this.out.write(this.nl.charAt(i8));
            }
            this.start = false;
        }
        int i9 = this.bufPtr;
        if (i9 == 3) {
            encode(this.out, this.buf, i9);
            this.bufPtr = 0;
            int i10 = this.chunkCount + 1;
            this.chunkCount = i10;
            if ((i10 & 15) == 0) {
                for (int i11 = 0; i11 != this.nl.length(); i11++) {
                    this.out.write(this.nl.charAt(i11));
                }
            }
        }
        this.crc.update(i2);
        int[] iArr = this.buf;
        int i12 = this.bufPtr;
        this.bufPtr = i12 + 1;
        iArr[i12] = i2 & 255;
    }
}
