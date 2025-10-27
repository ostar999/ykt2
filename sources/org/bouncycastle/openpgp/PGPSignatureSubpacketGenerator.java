package org.bouncycastle.openpgp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bouncycastle.bcpg.SignatureSubpacket;
import org.bouncycastle.bcpg.sig.EmbeddedSignature;
import org.bouncycastle.bcpg.sig.Exportable;
import org.bouncycastle.bcpg.sig.KeyExpirationTime;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.bcpg.sig.NotationData;
import org.bouncycastle.bcpg.sig.PreferredAlgorithms;
import org.bouncycastle.bcpg.sig.PrimaryUserID;
import org.bouncycastle.bcpg.sig.Revocable;
import org.bouncycastle.bcpg.sig.SignatureCreationTime;
import org.bouncycastle.bcpg.sig.SignatureExpirationTime;
import org.bouncycastle.bcpg.sig.SignerUserID;
import org.bouncycastle.bcpg.sig.TrustSignature;

/* loaded from: classes9.dex */
public class PGPSignatureSubpacketGenerator {
    List list = new ArrayList();

    public PGPSignatureSubpacketVector generate() {
        List list = this.list;
        return new PGPSignatureSubpacketVector((SignatureSubpacket[]) list.toArray(new SignatureSubpacket[list.size()]));
    }

    public void setEmbeddedSignature(boolean z2, PGPSignature pGPSignature) throws IOException {
        byte[] encoded = pGPSignature.getEncoded();
        byte[] bArr = new byte[encoded.length + (-1) > 256 ? encoded.length - 3 : encoded.length - 2];
        System.arraycopy(encoded, encoded.length - bArr.length, bArr, 0, bArr.length);
        this.list.add(new EmbeddedSignature(z2, bArr));
    }

    public void setExportable(boolean z2, boolean z3) {
        this.list.add(new Exportable(z2, z3));
    }

    public void setKeyExpirationTime(boolean z2, long j2) {
        this.list.add(new KeyExpirationTime(z2, j2));
    }

    public void setKeyFlags(boolean z2, int i2) {
        this.list.add(new KeyFlags(z2, i2));
    }

    public void setNotationData(boolean z2, boolean z3, String str, String str2) {
        this.list.add(new NotationData(z2, z3, str, str2));
    }

    public void setPreferredCompressionAlgorithms(boolean z2, int[] iArr) {
        this.list.add(new PreferredAlgorithms(22, z2, iArr));
    }

    public void setPreferredHashAlgorithms(boolean z2, int[] iArr) {
        this.list.add(new PreferredAlgorithms(21, z2, iArr));
    }

    public void setPreferredSymmetricAlgorithms(boolean z2, int[] iArr) {
        this.list.add(new PreferredAlgorithms(11, z2, iArr));
    }

    public void setPrimaryUserID(boolean z2, boolean z3) {
        this.list.add(new PrimaryUserID(z2, z3));
    }

    public void setRevocable(boolean z2, boolean z3) {
        this.list.add(new Revocable(z2, z3));
    }

    public void setSignatureCreationTime(boolean z2, Date date) {
        this.list.add(new SignatureCreationTime(z2, date));
    }

    public void setSignatureExpirationTime(boolean z2, long j2) {
        this.list.add(new SignatureExpirationTime(z2, j2));
    }

    public void setSignerUserID(boolean z2, String str) {
        if (str == null) {
            throw new IllegalArgumentException("attempt to set null SignerUserID");
        }
        this.list.add(new SignerUserID(z2, str));
    }

    public void setTrust(boolean z2, int i2, int i3) {
        this.list.add(new TrustSignature(z2, i2, i3));
    }
}
