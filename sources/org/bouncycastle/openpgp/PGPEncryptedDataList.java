package org.bouncycastle.openpgp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.InputStreamPacket;
import org.bouncycastle.bcpg.PublicKeyEncSessionPacket;
import org.bouncycastle.bcpg.SymmetricKeyEncSessionPacket;

/* loaded from: classes9.dex */
public class PGPEncryptedDataList {
    InputStreamPacket data;
    List list = new ArrayList();

    public PGPEncryptedDataList(BCPGInputStream bCPGInputStream) throws IOException {
        List list;
        PGPEncryptedData pGPPublicKeyEncryptedData;
        while (true) {
            if (bCPGInputStream.nextPacketTag() != 1 && bCPGInputStream.nextPacketTag() != 3) {
                break;
            } else {
                this.list.add(bCPGInputStream.readPacket());
            }
        }
        this.data = (InputStreamPacket) bCPGInputStream.readPacket();
        for (int i2 = 0; i2 != this.list.size(); i2++) {
            if (this.list.get(i2) instanceof SymmetricKeyEncSessionPacket) {
                list = this.list;
                pGPPublicKeyEncryptedData = new PGPPBEEncryptedData((SymmetricKeyEncSessionPacket) list.get(i2), this.data);
            } else {
                list = this.list;
                pGPPublicKeyEncryptedData = new PGPPublicKeyEncryptedData((PublicKeyEncSessionPacket) list.get(i2), this.data);
            }
            list.set(i2, pGPPublicKeyEncryptedData);
        }
    }

    public Object get(int i2) {
        return this.list.get(i2);
    }

    public Iterator getEncryptedDataObjects() {
        return this.list.iterator();
    }

    public Iterator getEncyptedDataObjects() {
        return this.list.iterator();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public int size() {
        return this.list.size();
    }
}
