package org.bouncycastle.ocsp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.bouncycastle.asn1.ocsp.SingleResponse;
import org.bouncycastle.asn1.x509.X509Extensions;

/* loaded from: classes9.dex */
public class BasicOCSPResp implements X509Extension {
    X509Certificate[] chain = null;
    ResponseData data;
    BasicOCSPResponse resp;

    public BasicOCSPResp(BasicOCSPResponse basicOCSPResponse) {
        this.resp = basicOCSPResponse;
        this.data = basicOCSPResponse.getTbsResponseData();
    }

    private List getCertList(String str) throws OCSPException, NoSuchProviderException {
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
        try {
            CertificateFactory certificateFactoryCreateX509CertificateFactory = OCSPUtil.createX509CertificateFactory(str);
            ASN1Sequence certs = this.resp.getCerts();
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
        X509Extensions responseExtensions = getResponseExtensions();
        if (responseExtensions != null) {
            Enumeration enumerationOids = responseExtensions.oids();
            while (enumerationOids.hasMoreElements()) {
                DERObjectIdentifier dERObjectIdentifier = (DERObjectIdentifier) enumerationOids.nextElement();
                if (z2 == responseExtensions.getExtension(dERObjectIdentifier).isCritical()) {
                    hashSet.add(dERObjectIdentifier.getId());
                }
            }
        }
        return hashSet;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof BasicOCSPResp) {
            return this.resp.equals(((BasicOCSPResp) obj).resp);
        }
        return false;
    }

    public CertStore getCertificates(String str, String str2) throws OCSPException, NoSuchAlgorithmException, NoSuchProviderException {
        try {
            return OCSPUtil.createCertStoreInstance(str, new CollectionCertStoreParameters(getCertList(str2)), str2);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new OCSPException("can't setup the CertStore", e2);
        }
    }

    public X509Certificate[] getCerts(String str) throws OCSPException, NoSuchProviderException {
        List certList = getCertList(str);
        return (X509Certificate[]) certList.toArray(new X509Certificate[certList.size()]);
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public byte[] getEncoded() throws IOException {
        return this.resp.getEncoded();
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        org.bouncycastle.asn1.x509.X509Extension extension;
        X509Extensions responseExtensions = getResponseExtensions();
        if (responseExtensions == null || (extension = responseExtensions.getExtension(new DERObjectIdentifier(str))) == null) {
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

    public Date getProducedAt() {
        try {
            return this.data.getProducedAt().getDate();
        } catch (ParseException e2) {
            throw new IllegalStateException("ParseException:" + e2.getMessage());
        }
    }

    public RespID getResponderId() {
        return new RespID(this.data.getResponderID());
    }

    public RespData getResponseData() {
        return new RespData(this.resp.getTbsResponseData());
    }

    public X509Extensions getResponseExtensions() {
        return this.data.getResponseExtensions();
    }

    public SingleResp[] getResponses() {
        ASN1Sequence responses = this.data.getResponses();
        int size = responses.size();
        SingleResp[] singleRespArr = new SingleResp[size];
        for (int i2 = 0; i2 != size; i2++) {
            singleRespArr[i2] = new SingleResp(SingleResponse.getInstance(responses.getObjectAt(i2)));
        }
        return singleRespArr;
    }

    public byte[] getSignature() {
        return this.resp.getSignature().getBytes();
    }

    public String getSignatureAlgName() {
        return OCSPUtil.getAlgorithmName(this.resp.getSignatureAlgorithm().getObjectId());
    }

    public String getSignatureAlgOID() {
        return this.resp.getSignatureAlgorithm().getObjectId().getId();
    }

    public byte[] getTBSResponseData() throws OCSPException {
        try {
            return this.resp.getTbsResponseData().getEncoded();
        } catch (IOException e2) {
            throw new OCSPException("problem encoding tbsResponseData", e2);
        }
    }

    public int getVersion() {
        return this.data.getVersion().getValue().intValue() + 1;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        Set criticalExtensionOIDs = getCriticalExtensionOIDs();
        return (criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty()) ? false : true;
    }

    public int hashCode() {
        return this.resp.hashCode();
    }

    public boolean verify(PublicKey publicKey, String str) throws OCSPException, SignatureException, InvalidKeyException, NoSuchProviderException {
        try {
            Signature signatureCreateSignatureInstance = OCSPUtil.createSignatureInstance(getSignatureAlgName(), str);
            signatureCreateSignatureInstance.initVerify(publicKey);
            signatureCreateSignatureInstance.update(this.resp.getTbsResponseData().getEncoded(ASN1Encodable.DER));
            return signatureCreateSignatureInstance.verify(getSignature());
        } catch (NoSuchProviderException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new OCSPException("exception processing sig: " + e3, e3);
        }
    }
}
