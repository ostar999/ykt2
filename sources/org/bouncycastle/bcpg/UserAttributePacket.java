package org.bouncycastle.bcpg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

/* loaded from: classes9.dex */
public class UserAttributePacket extends ContainedPacket {
    private UserAttributeSubpacket[] subpackets;

    public UserAttributePacket(BCPGInputStream bCPGInputStream) throws IOException {
        UserAttributeSubpacketInputStream userAttributeSubpacketInputStream = new UserAttributeSubpacketInputStream(bCPGInputStream);
        Vector vector = new Vector();
        while (true) {
            UserAttributeSubpacket packet = userAttributeSubpacketInputStream.readPacket();
            if (packet == null) {
                break;
            } else {
                vector.addElement(packet);
            }
        }
        this.subpackets = new UserAttributeSubpacket[vector.size()];
        int i2 = 0;
        while (true) {
            UserAttributeSubpacket[] userAttributeSubpacketArr = this.subpackets;
            if (i2 == userAttributeSubpacketArr.length) {
                return;
            }
            userAttributeSubpacketArr[i2] = (UserAttributeSubpacket) vector.elementAt(i2);
            i2++;
        }
    }

    public UserAttributePacket(UserAttributeSubpacket[] userAttributeSubpacketArr) {
        this.subpackets = userAttributeSubpacketArr;
    }

    @Override // org.bouncycastle.bcpg.ContainedPacket
    public void encode(BCPGOutputStream bCPGOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        while (true) {
            UserAttributeSubpacket[] userAttributeSubpacketArr = this.subpackets;
            if (i2 == userAttributeSubpacketArr.length) {
                bCPGOutputStream.writePacket(17, byteArrayOutputStream.toByteArray(), false);
                return;
            } else {
                userAttributeSubpacketArr[i2].encode(byteArrayOutputStream);
                i2++;
            }
        }
    }

    public UserAttributeSubpacket[] getSubpackets() {
        return this.subpackets;
    }
}
