package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class DERSequenceGenerator extends DERGenerator {
    private final ByteArrayOutputStream _bOut;

    public DERSequenceGenerator(OutputStream outputStream) throws IOException {
        super(outputStream);
        this._bOut = new ByteArrayOutputStream();
    }

    public DERSequenceGenerator(OutputStream outputStream, int i2, boolean z2) throws IOException {
        super(outputStream, i2, z2);
        this._bOut = new ByteArrayOutputStream();
    }

    public void addObject(DEREncodable dEREncodable) throws IOException {
        dEREncodable.getDERObject().encode(new DEROutputStream(this._bOut));
    }

    public void close() throws IOException {
        writeDEREncoded(48, this._bOut.toByteArray());
    }

    @Override // org.bouncycastle.asn1.ASN1Generator
    public OutputStream getRawOutputStream() {
        return this._bOut;
    }
}
