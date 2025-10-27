package org.bouncycastle.asn1.teletrust;

import com.tencent.connect.common.Constants;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/* loaded from: classes9.dex */
public interface TeleTrusTObjectIdentifiers {
    public static final ASN1ObjectIdentifier brainpoolP160r1;
    public static final ASN1ObjectIdentifier brainpoolP160t1;
    public static final ASN1ObjectIdentifier brainpoolP192r1;
    public static final ASN1ObjectIdentifier brainpoolP192t1;
    public static final ASN1ObjectIdentifier brainpoolP224r1;
    public static final ASN1ObjectIdentifier brainpoolP224t1;
    public static final ASN1ObjectIdentifier brainpoolP256r1;
    public static final ASN1ObjectIdentifier brainpoolP256t1;
    public static final ASN1ObjectIdentifier brainpoolP320r1;
    public static final ASN1ObjectIdentifier brainpoolP320t1;
    public static final ASN1ObjectIdentifier brainpoolP384r1;
    public static final ASN1ObjectIdentifier brainpoolP384t1;
    public static final ASN1ObjectIdentifier brainpoolP512r1;
    public static final ASN1ObjectIdentifier brainpoolP512t1;
    public static final ASN1ObjectIdentifier ecSign;
    public static final ASN1ObjectIdentifier ecSignWithRipemd160;
    public static final ASN1ObjectIdentifier ecSignWithSha1;
    public static final ASN1ObjectIdentifier ecc_brainpool;
    public static final ASN1ObjectIdentifier ellipticCurve;
    public static final ASN1ObjectIdentifier ripemd128;
    public static final ASN1ObjectIdentifier ripemd160;
    public static final ASN1ObjectIdentifier ripemd256;
    public static final ASN1ObjectIdentifier rsaSignatureWithripemd128;
    public static final ASN1ObjectIdentifier rsaSignatureWithripemd160;
    public static final ASN1ObjectIdentifier rsaSignatureWithripemd256;
    public static final ASN1ObjectIdentifier teleTrusTAlgorithm;
    public static final ASN1ObjectIdentifier teleTrusTRSAsignatureAlgorithm;
    public static final ASN1ObjectIdentifier versionOne;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("1.3.36.3");
        teleTrusTAlgorithm = aSN1ObjectIdentifier;
        ripemd160 = aSN1ObjectIdentifier.branch("2.1");
        ripemd128 = aSN1ObjectIdentifier.branch("2.2");
        ripemd256 = aSN1ObjectIdentifier.branch("2.3");
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch = aSN1ObjectIdentifier.branch("3.1");
        teleTrusTRSAsignatureAlgorithm = aSN1ObjectIdentifierBranch;
        rsaSignatureWithripemd160 = aSN1ObjectIdentifierBranch.branch("2");
        rsaSignatureWithripemd128 = aSN1ObjectIdentifierBranch.branch("3");
        rsaSignatureWithripemd256 = aSN1ObjectIdentifierBranch.branch("4");
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch2 = aSN1ObjectIdentifier.branch("3.2");
        ecSign = aSN1ObjectIdentifierBranch2;
        ecSignWithSha1 = aSN1ObjectIdentifierBranch2.branch("1");
        ecSignWithRipemd160 = aSN1ObjectIdentifierBranch2.branch("2");
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch3 = aSN1ObjectIdentifier.branch("3.2.8");
        ecc_brainpool = aSN1ObjectIdentifierBranch3;
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch4 = aSN1ObjectIdentifierBranch3.branch("1");
        ellipticCurve = aSN1ObjectIdentifierBranch4;
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch5 = aSN1ObjectIdentifierBranch4.branch("1");
        versionOne = aSN1ObjectIdentifierBranch5;
        brainpoolP160r1 = aSN1ObjectIdentifierBranch5.branch("1");
        brainpoolP160t1 = aSN1ObjectIdentifierBranch5.branch("2");
        brainpoolP192r1 = aSN1ObjectIdentifierBranch5.branch("3");
        brainpoolP192t1 = aSN1ObjectIdentifierBranch5.branch("4");
        brainpoolP224r1 = aSN1ObjectIdentifierBranch5.branch("5");
        brainpoolP224t1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_SHARE_TYPE_INFO);
        brainpoolP256r1 = aSN1ObjectIdentifierBranch5.branch("7");
        brainpoolP256t1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
        brainpoolP320r1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        brainpoolP320t1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_REPORT_TYPE_SHARE_TO_QQ);
        brainpoolP384r1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE);
        brainpoolP384t1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_REPORT_TYPE_SET_AVATAR);
        brainpoolP512r1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_REPORT_TYPE_JOININ_GROUP);
        brainpoolP512t1 = aSN1ObjectIdentifierBranch5.branch(Constants.VIA_REPORT_TYPE_MAKE_FRIEND);
    }
}
