package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public interface ASN1TaggedObjectParser extends DEREncodable, InMemoryRepresentable {
    DEREncodable getObjectParser(int i2, boolean z2) throws IOException;

    int getTagNo();
}
