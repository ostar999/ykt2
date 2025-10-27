package org.bouncycastle.openpgp;

import org.bouncycastle.bcpg.UserAttributeSubpacket;
import org.bouncycastle.bcpg.attr.ImageAttribute;

/* loaded from: classes9.dex */
public class PGPUserAttributeSubpacketVector {
    UserAttributeSubpacket[] packets;

    public PGPUserAttributeSubpacketVector(UserAttributeSubpacket[] userAttributeSubpacketArr) {
        this.packets = userAttributeSubpacketArr;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PGPUserAttributeSubpacketVector)) {
            return false;
        }
        PGPUserAttributeSubpacketVector pGPUserAttributeSubpacketVector = (PGPUserAttributeSubpacketVector) obj;
        if (pGPUserAttributeSubpacketVector.packets.length != this.packets.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            UserAttributeSubpacket[] userAttributeSubpacketArr = this.packets;
            if (i2 == userAttributeSubpacketArr.length) {
                return true;
            }
            if (!pGPUserAttributeSubpacketVector.packets[i2].equals(userAttributeSubpacketArr[i2])) {
                return false;
            }
            i2++;
        }
    }

    public ImageAttribute getImageAttribute() {
        UserAttributeSubpacket subpacket = getSubpacket(1);
        if (subpacket == null) {
            return null;
        }
        return (ImageAttribute) subpacket;
    }

    public UserAttributeSubpacket getSubpacket(int i2) {
        int i3 = 0;
        while (true) {
            UserAttributeSubpacket[] userAttributeSubpacketArr = this.packets;
            if (i3 == userAttributeSubpacketArr.length) {
                return null;
            }
            if (userAttributeSubpacketArr[i3].getType() == i2) {
                return this.packets[i3];
            }
            i3++;
        }
    }

    public int hashCode() {
        int i2 = 0;
        int iHashCode = 0;
        while (true) {
            UserAttributeSubpacket[] userAttributeSubpacketArr = this.packets;
            if (i2 == userAttributeSubpacketArr.length) {
                return iHashCode;
            }
            iHashCode ^= userAttributeSubpacketArr[i2].hashCode();
            i2++;
        }
    }

    public UserAttributeSubpacket[] toSubpacketArray() {
        return this.packets;
    }
}
