package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class BERGenerator extends ASN1Generator {
    private boolean _isExplicit;
    private int _tagNo;
    private boolean _tagged;

    public BERGenerator(OutputStream outputStream) {
        super(outputStream);
        this._tagged = false;
    }

    public BERGenerator(OutputStream outputStream, int i2, boolean z2) {
        super(outputStream);
        this._tagged = true;
        this._isExplicit = z2;
        this._tagNo = i2;
    }

    private void writeHdr(int i2) throws IOException {
        this._out.write(i2);
        this._out.write(128);
    }

    @Override // org.bouncycastle.asn1.ASN1Generator
    public OutputStream getRawOutputStream() {
        return this._out;
    }

    public void writeBERBody(InputStream inputStream) throws IOException {
        while (true) {
            int i2 = inputStream.read();
            if (i2 < 0) {
                return;
            } else {
                this._out.write(i2);
            }
        }
    }

    public void writeBEREnd() throws IOException {
        this._out.write(0);
        this._out.write(0);
        if (this._tagged && this._isExplicit) {
            this._out.write(0);
            this._out.write(0);
        }
    }

    public void writeBERHeader(int i2) throws IOException {
        if (this._tagged) {
            int i3 = this._tagNo | 128;
            if (this._isExplicit) {
                writeHdr(i3 | 32);
            } else {
                if ((i2 & 32) == 0) {
                    writeHdr(i3);
                    return;
                }
                i2 = i3 | 32;
            }
        }
        writeHdr(i2);
    }
}
