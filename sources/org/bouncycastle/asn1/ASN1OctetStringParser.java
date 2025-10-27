package org.bouncycastle.asn1;

import java.io.InputStream;

/* loaded from: classes9.dex */
public interface ASN1OctetStringParser extends DEREncodable, InMemoryRepresentable {
    InputStream getOctetStream();
}
