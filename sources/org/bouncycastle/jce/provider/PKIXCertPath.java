package org.bouncycastle.jce.provider;

import cn.hutool.crypto.KeyUtil;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.security.NoSuchProviderException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.SignedData;
import org.bouncycastle.openssl.PEMWriter;

/* loaded from: classes9.dex */
public class PKIXCertPath extends CertPath {
    static final List certPathEncodings;
    private List certificates;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("PkiPath");
        arrayList.add("PEM");
        arrayList.add("PKCS7");
        certPathEncodings = Collections.unmodifiableList(arrayList);
    }

    public PKIXCertPath(InputStream inputStream, String str) throws CertificateException, NoSuchProviderException {
        super(KeyUtil.CERT_TYPE_X509);
        try {
            if (!str.equalsIgnoreCase("PkiPath")) {
                if (!str.equalsIgnoreCase("PKCS7") && !str.equalsIgnoreCase("PEM")) {
                    throw new CertificateException("unsupported encoding: " + str);
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                this.certificates = new ArrayList();
                CertificateFactory certificateFactory = CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509, BouncyCastleProvider.PROVIDER_NAME);
                while (true) {
                    Certificate certificateGenerateCertificate = certificateFactory.generateCertificate(bufferedInputStream);
                    if (certificateGenerateCertificate == null) {
                        break;
                    } else {
                        this.certificates.add(certificateGenerateCertificate);
                    }
                }
            } else {
                DERObject object = new ASN1InputStream(inputStream).readObject();
                if (!(object instanceof ASN1Sequence)) {
                    throw new CertificateException("input stream does not contain a ASN1 SEQUENCE while reading PkiPath encoded data to load CertPath");
                }
                Enumeration objects = ((ASN1Sequence) object).getObjects();
                this.certificates = new ArrayList();
                CertificateFactory certificateFactory2 = CertificateFactory.getInstance(KeyUtil.CERT_TYPE_X509, BouncyCastleProvider.PROVIDER_NAME);
                while (objects.hasMoreElements()) {
                    this.certificates.add(0, certificateFactory2.generateCertificate(new ByteArrayInputStream(((ASN1Encodable) objects.nextElement()).getEncoded(ASN1Encodable.DER))));
                }
            }
            this.certificates = sortCerts(this.certificates);
        } catch (IOException e2) {
            throw new CertificateException("IOException throw while decoding CertPath:\n" + e2.toString());
        } catch (NoSuchProviderException e3) {
            throw new CertificateException("BouncyCastle provider not found while trying to get a CertificateFactory:\n" + e3.toString());
        }
    }

    public PKIXCertPath(List list) {
        super(KeyUtil.CERT_TYPE_X509);
        this.certificates = sortCerts(new ArrayList(list));
    }

    private List sortCerts(List list) {
        boolean z2;
        boolean z3;
        if (list.size() < 2) {
            return list;
        }
        X500Principal issuerX500Principal = ((X509Certificate) list.get(0)).getIssuerX500Principal();
        int i2 = 1;
        while (true) {
            if (i2 == list.size()) {
                z2 = true;
                break;
            }
            if (!issuerX500Principal.equals(((X509Certificate) list.get(i2)).getSubjectX500Principal())) {
                z2 = false;
                break;
            }
            issuerX500Principal = ((X509Certificate) list.get(i2)).getIssuerX500Principal();
            i2++;
        }
        if (z2) {
            return list;
        }
        ArrayList arrayList = new ArrayList(list.size());
        ArrayList arrayList2 = new ArrayList(list);
        for (int i3 = 0; i3 < list.size(); i3++) {
            X509Certificate x509Certificate = (X509Certificate) list.get(i3);
            X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
            int i4 = 0;
            while (true) {
                if (i4 == list.size()) {
                    z3 = false;
                    break;
                }
                if (((X509Certificate) list.get(i4)).getIssuerX500Principal().equals(subjectX500Principal)) {
                    z3 = true;
                    break;
                }
                i4++;
            }
            if (!z3) {
                arrayList.add(x509Certificate);
                list.remove(i3);
            }
        }
        if (arrayList.size() > 1) {
            return arrayList2;
        }
        for (int i5 = 0; i5 != arrayList.size(); i5++) {
            X500Principal issuerX500Principal2 = ((X509Certificate) arrayList.get(i5)).getIssuerX500Principal();
            int i6 = 0;
            while (true) {
                if (i6 < list.size()) {
                    X509Certificate x509Certificate2 = (X509Certificate) list.get(i6);
                    if (issuerX500Principal2.equals(x509Certificate2.getSubjectX500Principal())) {
                        arrayList.add(x509Certificate2);
                        list.remove(i6);
                        break;
                    }
                    i6++;
                }
            }
        }
        return list.size() > 0 ? arrayList2 : arrayList;
    }

    private DERObject toASN1Object(X509Certificate x509Certificate) throws CertificateEncodingException {
        try {
            return new ASN1InputStream(x509Certificate.getEncoded()).readObject();
        } catch (Exception e2) {
            throw new CertificateEncodingException("Exception while encoding certificate: " + e2.toString());
        }
    }

    private byte[] toDEREncoded(ASN1Encodable aSN1Encodable) throws CertificateEncodingException {
        try {
            return aSN1Encodable.getEncoded(ASN1Encodable.DER);
        } catch (IOException e2) {
            throw new CertificateEncodingException("Exception thrown: " + e2);
        }
    }

    @Override // java.security.cert.CertPath
    public List getCertificates() {
        return Collections.unmodifiableList(new ArrayList(this.certificates));
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded() throws CertificateEncodingException {
        Iterator encodings = getEncodings();
        if (!encodings.hasNext()) {
            return null;
        }
        Object next = encodings.next();
        if (next instanceof String) {
            return getEncoded((String) next);
        }
        return null;
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded(String str) throws IOException, CertificateEncodingException {
        if (str.equalsIgnoreCase("PkiPath")) {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            List list = this.certificates;
            ListIterator listIterator = list.listIterator(list.size());
            while (listIterator.hasPrevious()) {
                aSN1EncodableVector.add(toASN1Object((X509Certificate) listIterator.previous()));
            }
            return toDEREncoded(new DERSequence(aSN1EncodableVector));
        }
        int i2 = 0;
        if (str.equalsIgnoreCase("PKCS7")) {
            ContentInfo contentInfo = new ContentInfo(PKCSObjectIdentifiers.data, null);
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            while (i2 != this.certificates.size()) {
                aSN1EncodableVector2.add(toASN1Object((X509Certificate) this.certificates.get(i2)));
                i2++;
            }
            return toDEREncoded(new ContentInfo(PKCSObjectIdentifiers.signedData, new SignedData(new DERInteger(1), new DERSet(), contentInfo, new DERSet(aSN1EncodableVector2), null, new DERSet())));
        }
        if (!str.equalsIgnoreCase("PEM")) {
            throw new CertificateEncodingException("unsupported encoding: " + str);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PEMWriter pEMWriter = new PEMWriter(new OutputStreamWriter(byteArrayOutputStream));
        while (i2 != this.certificates.size()) {
            try {
                pEMWriter.writeObject(this.certificates.get(i2));
                i2++;
            } catch (Exception unused) {
                throw new CertificateEncodingException("can't encode certificate for PEM encoded path");
            }
        }
        pEMWriter.close();
        return byteArrayOutputStream.toByteArray();
    }

    @Override // java.security.cert.CertPath
    public Iterator getEncodings() {
        return certPathEncodings.iterator();
    }
}
