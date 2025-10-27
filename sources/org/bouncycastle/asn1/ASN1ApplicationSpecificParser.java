package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public interface ASN1ApplicationSpecificParser extends DEREncodable, InMemoryRepresentable {
    DEREncodable readObject() throws IOException;
}
