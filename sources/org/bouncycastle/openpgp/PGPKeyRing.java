package org.bouncycastle.openpgp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.Packet;
import org.bouncycastle.bcpg.SignaturePacket;
import org.bouncycastle.bcpg.TrustPacket;
import org.bouncycastle.bcpg.UserAttributePacket;
import org.bouncycastle.bcpg.UserIDPacket;

/* loaded from: classes9.dex */
public abstract class PGPKeyRing {
    public static TrustPacket readOptionalTrustPacket(BCPGInputStream bCPGInputStream) throws IOException {
        if (bCPGInputStream.nextPacketTag() == 12) {
            return (TrustPacket) bCPGInputStream.readPacket();
        }
        return null;
    }

    public static List readSignaturesAndTrust(BCPGInputStream bCPGInputStream) throws IOException {
        try {
            ArrayList arrayList = new ArrayList();
            while (bCPGInputStream.nextPacketTag() == 2) {
                arrayList.add(new PGPSignature((SignaturePacket) bCPGInputStream.readPacket(), readOptionalTrustPacket(bCPGInputStream)));
            }
            return arrayList;
        } catch (PGPException e2) {
            throw new IOException("can't create signature object: " + e2.getMessage() + ", cause: " + e2.getUnderlyingException().toString());
        }
    }

    public static void readUserIDs(BCPGInputStream bCPGInputStream, List list, List list2, List list3) throws IOException {
        while (true) {
            if (bCPGInputStream.nextPacketTag() != 13 && bCPGInputStream.nextPacketTag() != 17) {
                return;
            }
            Packet packet = bCPGInputStream.readPacket();
            if (packet instanceof UserIDPacket) {
                list.add(((UserIDPacket) packet).getID());
            } else {
                list.add(new PGPUserAttributeSubpacketVector(((UserAttributePacket) packet).getSubpackets()));
            }
            list2.add(readOptionalTrustPacket(bCPGInputStream));
            list3.add(readSignaturesAndTrust(bCPGInputStream));
        }
    }

    public static BCPGInputStream wrap(InputStream inputStream) {
        return inputStream instanceof BCPGInputStream ? (BCPGInputStream) inputStream : new BCPGInputStream(inputStream);
    }
}
