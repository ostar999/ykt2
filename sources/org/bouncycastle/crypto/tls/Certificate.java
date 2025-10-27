package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x509.X509CertificateStructure;

/* loaded from: classes9.dex */
public class Certificate {
    public static final Certificate EMPTY_CHAIN = new Certificate(new X509CertificateStructure[0]);
    protected X509CertificateStructure[] certs;

    public Certificate(X509CertificateStructure[] x509CertificateStructureArr) {
        if (x509CertificateStructureArr == null) {
            throw new IllegalArgumentException("'certs' cannot be null");
        }
        this.certs = x509CertificateStructureArr;
    }

    public static Certificate parse(InputStream inputStream) throws IOException {
        int uint24 = TlsUtils.readUint24(inputStream);
        if (uint24 == 0) {
            return EMPTY_CHAIN;
        }
        Vector vector = new Vector();
        while (uint24 > 0) {
            int uint242 = TlsUtils.readUint24(inputStream);
            uint24 -= uint242 + 3;
            byte[] bArr = new byte[uint242];
            TlsUtils.readFully(bArr, inputStream);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            vector.addElement(X509CertificateStructure.getInstance(new ASN1InputStream(byteArrayInputStream).readObject()));
            if (byteArrayInputStream.available() > 0) {
                throw new IllegalArgumentException("Sorry, there is garbage data left after the certificate");
            }
        }
        X509CertificateStructure[] x509CertificateStructureArr = new X509CertificateStructure[vector.size()];
        for (int i2 = 0; i2 < vector.size(); i2++) {
            x509CertificateStructureArr[i2] = (X509CertificateStructure) vector.elementAt(i2);
        }
        return new Certificate(x509CertificateStructureArr);
    }

    public void encode(OutputStream outputStream) throws IOException {
        Vector vector = new Vector();
        int i2 = 0;
        int length = 0;
        while (true) {
            X509CertificateStructure[] x509CertificateStructureArr = this.certs;
            if (i2 >= x509CertificateStructureArr.length) {
                break;
            }
            byte[] encoded = x509CertificateStructureArr[i2].getEncoded(ASN1Encodable.DER);
            vector.addElement(encoded);
            length += encoded.length + 3;
            i2++;
        }
        TlsUtils.writeUint24(length + 3, outputStream);
        TlsUtils.writeUint24(length, outputStream);
        for (int i3 = 0; i3 < vector.size(); i3++) {
            TlsUtils.writeOpaque24((byte[]) vector.elementAt(i3), outputStream);
        }
    }

    public X509CertificateStructure[] getCerts() {
        X509CertificateStructure[] x509CertificateStructureArr = this.certs;
        X509CertificateStructure[] x509CertificateStructureArr2 = new X509CertificateStructure[x509CertificateStructureArr.length];
        System.arraycopy(x509CertificateStructureArr, 0, x509CertificateStructureArr2, 0, x509CertificateStructureArr.length);
        return x509CertificateStructureArr2;
    }

    public boolean isEmpty() {
        return this.certs.length == 0;
    }
}
