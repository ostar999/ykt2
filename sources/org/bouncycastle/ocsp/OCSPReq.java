package org.bouncycastle.ocsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ocsp.OCSPRequest;
import org.bouncycastle.asn1.ocsp.Request;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.X509Extensions;

/* loaded from: classes9.dex */
public class OCSPReq implements X509Extension {
    private OCSPRequest req;

    public OCSPReq(InputStream inputStream) throws IOException {
        this(new ASN1InputStream(inputStream));
    }

    private OCSPReq(ASN1InputStream aSN1InputStream) throws IOException {
        try {
            this.req = OCSPRequest.getInstance(aSN1InputStream.readObject());
        } catch (ClassCastException e2) {
            throw new IOException("malformed request: " + e2.getMessage());
        } catch (IllegalArgumentException e3) {
            throw new IOException("malformed request: " + e3.getMessage());
        }
    }

    public OCSPReq(OCSPRequest oCSPRequest) {
        this.req = oCSPRequest;
    }

    public OCSPReq(byte[] bArr) throws IOException {
        this(new ASN1InputStream(bArr));
    }

    private List getCertList(String str) throws OCSPException, NoSuchProviderException {
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
        try {
            CertificateFactory certificateFactoryCreateX509CertificateFactory = OCSPUtil.createX509CertificateFactory(str);
            ASN1Sequence certs = this.req.getOptionalSignature().getCerts();
            if (certs != null) {
                Enumeration objects = certs.getObjects();
                while (objects.hasMoreElements()) {
                    try {
                        aSN1OutputStream.writeObject(objects.nextElement());
                        arrayList.add(certificateFactoryCreateX509CertificateFactory.generateCertificate(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
                        byteArrayOutputStream.reset();
                    } catch (IOException e2) {
                        throw new OCSPException("can't re-encode certificate!", e2);
                    } catch (CertificateException e3) {
                        throw new OCSPException("can't re-encode certificate!", e3);
                    }
                }
            }
            return arrayList;
        } catch (CertificateException e4) {
            throw new OCSPException("can't get certificate factory.", e4);
        }
    }

    private Set getExtensionOIDs(boolean z2) {
        HashSet hashSet = new HashSet();
        X509Extensions requestExtensions = getRequestExtensions();
        if (requestExtensions != null) {
            Enumeration enumerationOids = requestExtensions.oids();
            while (enumerationOids.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) enumerationOids.nextElement();
                if (z2 == requestExtensions.getExtension(aSN1ObjectIdentifier).isCritical()) {
                    hashSet.add(aSN1ObjectIdentifier.getId());
                }
            }
        }
        return hashSet;
    }

    public CertStore getCertificates(String str, String str2) throws OCSPException, NoSuchAlgorithmException, NoSuchProviderException {
        if (!isSigned()) {
            return null;
        }
        try {
            return OCSPUtil.createCertStoreInstance(str, new CollectionCertStoreParameters(getCertList(str2)), str2);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new OCSPException("can't setup the CertStore", e2);
        }
    }

    public X509Certificate[] getCerts(String str) throws OCSPException, NoSuchProviderException {
        if (!isSigned()) {
            return null;
        }
        List certList = getCertList(str);
        return (X509Certificate[]) certList.toArray(new X509Certificate[certList.size()]);
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ASN1OutputStream(byteArrayOutputStream).writeObject(this.req);
        return byteArrayOutputStream.toByteArray();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        org.bouncycastle.asn1.x509.X509Extension extension;
        X509Extensions requestExtensions = getRequestExtensions();
        if (requestExtensions == null || (extension = requestExtensions.getExtension(new ASN1ObjectIdentifier(str))) == null) {
            return null;
        }
        try {
            return extension.getValue().getEncoded(ASN1Encodable.DER);
        } catch (Exception e2) {
            throw new RuntimeException("error encoding " + e2.toString());
        }
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public X509Extensions getRequestExtensions() {
        return X509Extensions.getInstance(this.req.getTbsRequest().getRequestExtensions());
    }

    public Req[] getRequestList() {
        ASN1Sequence requestList = this.req.getTbsRequest().getRequestList();
        int size = requestList.size();
        Req[] reqArr = new Req[size];
        for (int i2 = 0; i2 != size; i2++) {
            reqArr[i2] = new Req(Request.getInstance(requestList.getObjectAt(i2)));
        }
        return reqArr;
    }

    public GeneralName getRequestorName() {
        return GeneralName.getInstance(this.req.getTbsRequest().getRequestorName());
    }

    public byte[] getSignature() {
        if (isSigned()) {
            return this.req.getOptionalSignature().getSignature().getBytes();
        }
        return null;
    }

    public String getSignatureAlgOID() {
        if (isSigned()) {
            return this.req.getOptionalSignature().getSignatureAlgorithm().getObjectId().getId();
        }
        return null;
    }

    public byte[] getTBSRequest() throws OCSPException {
        try {
            return this.req.getTbsRequest().getEncoded();
        } catch (IOException e2) {
            throw new OCSPException("problem encoding tbsRequest", e2);
        }
    }

    public int getVersion() {
        return this.req.getTbsRequest().getVersion().getValue().intValue() + 1;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return (criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty()) ? false : true;
    }

    public boolean isSigned() {
        return this.req.getOptionalSignature() != null;
    }

    public boolean verify(PublicKey publicKey, String str) throws OCSPException, SignatureException, InvalidKeyException, NoSuchProviderException {
        if (!isSigned()) {
            throw new OCSPException("attempt to verify signature on unsigned object");
        }
        try {
            Signature signatureCreateSignatureInstance = OCSPUtil.createSignatureInstance(getSignatureAlgOID(), str);
            signatureCreateSignatureInstance.initVerify(publicKey);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            new ASN1OutputStream(byteArrayOutputStream).writeObject(this.req.getTbsRequest());
            signatureCreateSignatureInstance.update(byteArrayOutputStream.toByteArray());
            return signatureCreateSignatureInstance.verify(getSignature());
        } catch (NoSuchProviderException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new OCSPException("exception processing sig: " + e3, e3);
        }
    }
}
