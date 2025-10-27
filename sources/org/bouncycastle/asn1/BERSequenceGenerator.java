package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes9.dex */
public class BERSequenceGenerator extends BERGenerator {
    public BERSequenceGenerator(OutputStream outputStream) throws IOException {
        super(outputStream);
        writeBERHeader(48);
    }

    public BERSequenceGenerator(OutputStream outputStream, int i2, boolean z2) throws IOException {
        super(outputStream, i2, z2);
        writeBERHeader(48);
    }

    public void addObject(DEREncodable dEREncodable) throws IOException {
        dEREncodable.getDERObject().encode(new BEROutputStream(this._out));
    }

    public void close() throws IOException {
        writeBEREnd();
    }
}
