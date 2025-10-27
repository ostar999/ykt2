package org.bouncycastle.openpgp;

import java.util.ArrayList;
import java.util.Date;
import org.bouncycastle.bcpg.SignatureSubpacket;
import org.bouncycastle.bcpg.sig.IssuerKeyID;
import org.bouncycastle.bcpg.sig.KeyExpirationTime;
import org.bouncycastle.bcpg.sig.KeyFlags;
import org.bouncycastle.bcpg.sig.NotationData;
import org.bouncycastle.bcpg.sig.PreferredAlgorithms;
import org.bouncycastle.bcpg.sig.PrimaryUserID;
import org.bouncycastle.bcpg.sig.SignatureCreationTime;
import org.bouncycastle.bcpg.sig.SignatureExpirationTime;
import org.bouncycastle.bcpg.sig.SignerUserID;

/* loaded from: classes9.dex */
public class PGPSignatureSubpacketVector {
    SignatureSubpacket[] packets;

    public PGPSignatureSubpacketVector(SignatureSubpacket[] signatureSubpacketArr) {
        this.packets = signatureSubpacketArr;
    }

    public int[] getCriticalTags() {
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            SignatureSubpacket[] signatureSubpacketArr = this.packets;
            if (i3 == signatureSubpacketArr.length) {
                break;
            }
            if (signatureSubpacketArr[i3].isCritical()) {
                i4++;
            }
            i3++;
        }
        int[] iArr = new int[i4];
        int i5 = 0;
        while (true) {
            SignatureSubpacket[] signatureSubpacketArr2 = this.packets;
            if (i2 == signatureSubpacketArr2.length) {
                return iArr;
            }
            if (signatureSubpacketArr2[i2].isCritical()) {
                iArr[i5] = this.packets[i2].getType();
                i5++;
            }
            i2++;
        }
    }

    public long getIssuerKeyID() {
        SignatureSubpacket subpacket = getSubpacket(16);
        if (subpacket == null) {
            return 0L;
        }
        return ((IssuerKeyID) subpacket).getKeyID();
    }

    public long getKeyExpirationTime() {
        SignatureSubpacket subpacket = getSubpacket(9);
        if (subpacket == null) {
            return 0L;
        }
        return ((KeyExpirationTime) subpacket).getTime();
    }

    public int getKeyFlags() {
        SignatureSubpacket subpacket = getSubpacket(27);
        if (subpacket == null) {
            return 0;
        }
        return ((KeyFlags) subpacket).getFlags();
    }

    public NotationData[] getNotationDataOccurences() {
        SignatureSubpacket[] subpackets = getSubpackets(20);
        NotationData[] notationDataArr = new NotationData[subpackets.length];
        for (int i2 = 0; i2 < subpackets.length; i2++) {
            notationDataArr[i2] = (NotationData) subpackets[i2];
        }
        return notationDataArr;
    }

    public int[] getPreferredCompressionAlgorithms() {
        SignatureSubpacket subpacket = getSubpacket(22);
        if (subpacket == null) {
            return null;
        }
        return ((PreferredAlgorithms) subpacket).getPreferences();
    }

    public int[] getPreferredHashAlgorithms() {
        SignatureSubpacket subpacket = getSubpacket(21);
        if (subpacket == null) {
            return null;
        }
        return ((PreferredAlgorithms) subpacket).getPreferences();
    }

    public int[] getPreferredSymmetricAlgorithms() {
        SignatureSubpacket subpacket = getSubpacket(11);
        if (subpacket == null) {
            return null;
        }
        return ((PreferredAlgorithms) subpacket).getPreferences();
    }

    public Date getSignatureCreationTime() {
        SignatureSubpacket subpacket = getSubpacket(2);
        if (subpacket == null) {
            return null;
        }
        return ((SignatureCreationTime) subpacket).getTime();
    }

    public long getSignatureExpirationTime() {
        SignatureSubpacket subpacket = getSubpacket(3);
        if (subpacket == null) {
            return 0L;
        }
        return ((SignatureExpirationTime) subpacket).getTime();
    }

    public String getSignerUserID() {
        SignatureSubpacket subpacket = getSubpacket(28);
        if (subpacket == null) {
            return null;
        }
        return ((SignerUserID) subpacket).getID();
    }

    public SignatureSubpacket getSubpacket(int i2) {
        int i3 = 0;
        while (true) {
            SignatureSubpacket[] signatureSubpacketArr = this.packets;
            if (i3 == signatureSubpacketArr.length) {
                return null;
            }
            if (signatureSubpacketArr[i3].getType() == i2) {
                return this.packets[i3];
            }
            i3++;
        }
    }

    public SignatureSubpacket[] getSubpackets(int i2) {
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (true) {
            SignatureSubpacket[] signatureSubpacketArr = this.packets;
            if (i3 == signatureSubpacketArr.length) {
                return (SignatureSubpacket[]) arrayList.toArray(new SignatureSubpacket[0]);
            }
            if (signatureSubpacketArr[i3].getType() == i2) {
                arrayList.add(this.packets[i3]);
            }
            i3++;
        }
    }

    public boolean hasSubpacket(int i2) {
        return getSubpacket(i2) != null;
    }

    public boolean isPrimaryUserID() {
        PrimaryUserID primaryUserID = (PrimaryUserID) getSubpacket(25);
        if (primaryUserID != null) {
            return primaryUserID.isPrimaryUserID();
        }
        return false;
    }

    public int size() {
        return this.packets.length;
    }

    public SignatureSubpacket[] toSubpacketArray() {
        return this.packets;
    }
}
