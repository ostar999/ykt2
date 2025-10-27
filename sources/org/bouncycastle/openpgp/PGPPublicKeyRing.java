package org.bouncycastle.openpgp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.PublicKeyPacket;
import org.bouncycastle.bcpg.TrustPacket;

/* loaded from: classes9.dex */
public class PGPPublicKeyRing extends PGPKeyRing {
    List keys;

    public PGPPublicKeyRing(InputStream inputStream) throws IOException {
        this.keys = new ArrayList();
        BCPGInputStream bCPGInputStreamWrap = PGPKeyRing.wrap(inputStream);
        int iNextPacketTag = bCPGInputStreamWrap.nextPacketTag();
        if (iNextPacketTag != 6 && iNextPacketTag != 14) {
            throw new IOException("public key ring doesn't start with public key tag: tag 0x" + Integer.toHexString(iNextPacketTag));
        }
        PublicKeyPacket publicKeyPacket = (PublicKeyPacket) bCPGInputStreamWrap.readPacket();
        TrustPacket optionalTrustPacket = PGPKeyRing.readOptionalTrustPacket(bCPGInputStreamWrap);
        List signaturesAndTrust = PGPKeyRing.readSignaturesAndTrust(bCPGInputStreamWrap);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        PGPKeyRing.readUserIDs(bCPGInputStreamWrap, arrayList, arrayList2, arrayList3);
        List list = this.keys;
        PGPPublicKey pGPPublicKey = new PGPPublicKey(publicKeyPacket, optionalTrustPacket, signaturesAndTrust, arrayList, arrayList2, arrayList3);
        while (true) {
            list.add(pGPPublicKey);
            if (bCPGInputStreamWrap.nextPacketTag() != 14) {
                return;
            }
            list = this.keys;
            pGPPublicKey = readSubkey(bCPGInputStreamWrap);
        }
    }

    public PGPPublicKeyRing(List list) {
        this.keys = list;
    }

    public PGPPublicKeyRing(byte[] bArr) throws IOException {
        this(new ByteArrayInputStream(bArr));
    }

    public static PGPPublicKeyRing insertPublicKey(PGPPublicKeyRing pGPPublicKeyRing, PGPPublicKey pGPPublicKey) {
        ArrayList arrayList = new ArrayList(pGPPublicKeyRing.keys);
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 != arrayList.size(); i2++) {
            PGPPublicKey pGPPublicKey2 = (PGPPublicKey) arrayList.get(i2);
            if (pGPPublicKey2.getKeyID() == pGPPublicKey.getKeyID()) {
                arrayList.set(i2, pGPPublicKey);
                z2 = true;
            }
            if (pGPPublicKey2.isMasterKey()) {
                z3 = true;
            }
        }
        if (!z2) {
            if (!pGPPublicKey.isMasterKey()) {
                arrayList.add(pGPPublicKey);
            } else {
                if (z3) {
                    throw new IllegalArgumentException("cannot add a master key to a ring that already has one");
                }
                arrayList.add(0, pGPPublicKey);
            }
        }
        return new PGPPublicKeyRing(arrayList);
    }

    public static PGPPublicKey readSubkey(BCPGInputStream bCPGInputStream) throws IOException {
        return new PGPPublicKey((PublicKeyPacket) bCPGInputStream.readPacket(), PGPKeyRing.readOptionalTrustPacket(bCPGInputStream), PGPKeyRing.readSignaturesAndTrust(bCPGInputStream));
    }

    public static PGPPublicKeyRing removePublicKey(PGPPublicKeyRing pGPPublicKeyRing, PGPPublicKey pGPPublicKey) {
        ArrayList arrayList = new ArrayList(pGPPublicKeyRing.keys);
        boolean z2 = false;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((PGPPublicKey) arrayList.get(i2)).getKeyID() == pGPPublicKey.getKeyID()) {
                arrayList.remove(i2);
                z2 = true;
            }
        }
        if (z2) {
            return new PGPPublicKeyRing(arrayList);
        }
        return null;
    }

    public void encode(OutputStream outputStream) throws IOException {
        for (int i2 = 0; i2 != this.keys.size(); i2++) {
            ((PGPPublicKey) this.keys.get(i2)).encode(outputStream);
        }
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public PGPPublicKey getPublicKey() {
        return (PGPPublicKey) this.keys.get(0);
    }

    public PGPPublicKey getPublicKey(long j2) {
        for (int i2 = 0; i2 != this.keys.size(); i2++) {
            PGPPublicKey pGPPublicKey = (PGPPublicKey) this.keys.get(i2);
            if (j2 == pGPPublicKey.getKeyID()) {
                return pGPPublicKey;
            }
        }
        return null;
    }

    public Iterator getPublicKeys() {
        return Collections.unmodifiableList(this.keys).iterator();
    }
}
