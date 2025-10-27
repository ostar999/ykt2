package org.bouncycastle.crypto.tls;

import org.bouncycastle.asn1.x509.X509CertificateStructure;

/* loaded from: classes9.dex */
public interface CertificateVerifyer {
    boolean isValid(X509CertificateStructure[] x509CertificateStructureArr);
}
