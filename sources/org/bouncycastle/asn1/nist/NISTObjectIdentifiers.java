package org.bouncycastle.asn1.nist;

import androidx.room.RoomMasterTable;
import com.tencent.connect.common.Constants;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/* loaded from: classes9.dex */
public interface NISTObjectIdentifiers {
    public static final ASN1ObjectIdentifier aes;
    public static final ASN1ObjectIdentifier dsa_with_sha224;
    public static final ASN1ObjectIdentifier dsa_with_sha256;
    public static final ASN1ObjectIdentifier dsa_with_sha384;
    public static final ASN1ObjectIdentifier dsa_with_sha512;
    public static final ASN1ObjectIdentifier id_aes128_CBC;
    public static final ASN1ObjectIdentifier id_aes128_CCM;
    public static final ASN1ObjectIdentifier id_aes128_CFB;
    public static final ASN1ObjectIdentifier id_aes128_ECB;
    public static final ASN1ObjectIdentifier id_aes128_GCM;
    public static final ASN1ObjectIdentifier id_aes128_OFB;
    public static final ASN1ObjectIdentifier id_aes128_wrap;
    public static final ASN1ObjectIdentifier id_aes192_CBC;
    public static final ASN1ObjectIdentifier id_aes192_CCM;
    public static final ASN1ObjectIdentifier id_aes192_CFB;
    public static final ASN1ObjectIdentifier id_aes192_ECB;
    public static final ASN1ObjectIdentifier id_aes192_GCM;
    public static final ASN1ObjectIdentifier id_aes192_OFB;
    public static final ASN1ObjectIdentifier id_aes192_wrap;
    public static final ASN1ObjectIdentifier id_aes256_CBC;
    public static final ASN1ObjectIdentifier id_aes256_CCM;
    public static final ASN1ObjectIdentifier id_aes256_CFB;
    public static final ASN1ObjectIdentifier id_aes256_ECB;
    public static final ASN1ObjectIdentifier id_aes256_GCM;
    public static final ASN1ObjectIdentifier id_aes256_OFB;
    public static final ASN1ObjectIdentifier id_aes256_wrap;
    public static final ASN1ObjectIdentifier id_dsa_with_sha2;
    public static final ASN1ObjectIdentifier id_sha224;
    public static final ASN1ObjectIdentifier id_sha256;
    public static final ASN1ObjectIdentifier id_sha384;
    public static final ASN1ObjectIdentifier id_sha512;
    public static final ASN1ObjectIdentifier nistAlgorithm;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = new ASN1ObjectIdentifier("2.16.840.1.101.3.4");
        nistAlgorithm = aSN1ObjectIdentifier;
        id_sha256 = aSN1ObjectIdentifier.branch("2.1");
        id_sha384 = aSN1ObjectIdentifier.branch("2.2");
        id_sha512 = aSN1ObjectIdentifier.branch("2.3");
        id_sha224 = aSN1ObjectIdentifier.branch("2.4");
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch = aSN1ObjectIdentifier.branch("1");
        aes = aSN1ObjectIdentifierBranch;
        id_aes128_ECB = aSN1ObjectIdentifierBranch.branch("1");
        id_aes128_CBC = aSN1ObjectIdentifierBranch.branch("2");
        id_aes128_OFB = aSN1ObjectIdentifierBranch.branch("3");
        id_aes128_CFB = aSN1ObjectIdentifierBranch.branch("4");
        id_aes128_wrap = aSN1ObjectIdentifierBranch.branch("5");
        id_aes128_GCM = aSN1ObjectIdentifierBranch.branch(Constants.VIA_SHARE_TYPE_INFO);
        id_aes128_CCM = aSN1ObjectIdentifierBranch.branch("7");
        id_aes192_ECB = aSN1ObjectIdentifierBranch.branch("21");
        id_aes192_CBC = aSN1ObjectIdentifierBranch.branch(Constants.VIA_REPORT_TYPE_DATALINE);
        id_aes192_OFB = aSN1ObjectIdentifierBranch.branch(Constants.VIA_REPORT_TYPE_SHARE_TO_TROOPBAR);
        id_aes192_CFB = aSN1ObjectIdentifierBranch.branch("24");
        id_aes192_wrap = aSN1ObjectIdentifierBranch.branch(Constants.VIA_REPORT_TYPE_CHAT_AUDIO);
        id_aes192_GCM = aSN1ObjectIdentifierBranch.branch(Constants.VIA_REPORT_TYPE_CHAT_VIDEO);
        id_aes192_CCM = aSN1ObjectIdentifierBranch.branch("27");
        id_aes256_ECB = aSN1ObjectIdentifierBranch.branch("41");
        id_aes256_CBC = aSN1ObjectIdentifierBranch.branch(RoomMasterTable.DEFAULT_ID);
        id_aes256_OFB = aSN1ObjectIdentifierBranch.branch("43");
        id_aes256_CFB = aSN1ObjectIdentifierBranch.branch("44");
        id_aes256_wrap = aSN1ObjectIdentifierBranch.branch("45");
        id_aes256_GCM = aSN1ObjectIdentifierBranch.branch("46");
        id_aes256_CCM = aSN1ObjectIdentifierBranch.branch("47");
        ASN1ObjectIdentifier aSN1ObjectIdentifierBranch2 = aSN1ObjectIdentifier.branch("3");
        id_dsa_with_sha2 = aSN1ObjectIdentifierBranch2;
        dsa_with_sha224 = aSN1ObjectIdentifierBranch2.branch("1");
        dsa_with_sha256 = aSN1ObjectIdentifierBranch2.branch("2");
        dsa_with_sha384 = aSN1ObjectIdentifierBranch2.branch("3");
        dsa_with_sha512 = aSN1ObjectIdentifierBranch2.branch("4");
    }
}
