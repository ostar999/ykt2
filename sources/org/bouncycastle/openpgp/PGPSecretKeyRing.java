package org.bouncycastle.openpgp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.PublicSubkeyPacket;
import org.bouncycastle.bcpg.SecretKeyPacket;
import org.bouncycastle.bcpg.SecretSubkeyPacket;
import org.bouncycastle.bcpg.TrustPacket;

/* loaded from: classes9.dex */
public class PGPSecretKeyRing extends PGPKeyRing {
    List extraPubKeys;
    List keys;

    public PGPSecretKeyRing(InputStream inputStream) throws IOException, PGPException {
        List list;
        Object pGPPublicKey;
        this.keys = new ArrayList();
        this.extraPubKeys = new ArrayList();
        BCPGInputStream bCPGInputStreamWrap = PGPKeyRing.wrap(inputStream);
        int iNextPacketTag = bCPGInputStreamWrap.nextPacketTag();
        if (iNextPacketTag != 5 && iNextPacketTag != 7) {
            throw new IOException("secret key ring doesn't start with secret key tag: tag 0x" + Integer.toHexString(iNextPacketTag));
        }
        SecretKeyPacket secretKeyPacket = (SecretKeyPacket) bCPGInputStreamWrap.readPacket();
        while (bCPGInputStreamWrap.nextPacketTag() == 61) {
            bCPGInputStreamWrap.readPacket();
        }
        TrustPacket optionalTrustPacket = PGPKeyRing.readOptionalTrustPacket(bCPGInputStreamWrap);
        List signaturesAndTrust = PGPKeyRing.readSignaturesAndTrust(bCPGInputStreamWrap);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        PGPKeyRing.readUserIDs(bCPGInputStreamWrap, arrayList, arrayList2, arrayList3);
        this.keys.add(new PGPSecretKey(secretKeyPacket, new PGPPublicKey(secretKeyPacket.getPublicKeyPacket(), optionalTrustPacket, signaturesAndTrust, arrayList, arrayList2, arrayList3)));
        while (true) {
            if (bCPGInputStreamWrap.nextPacketTag() != 7 && bCPGInputStreamWrap.nextPacketTag() != 14) {
                return;
            }
            if (bCPGInputStreamWrap.nextPacketTag() == 7) {
                SecretSubkeyPacket secretSubkeyPacket = (SecretSubkeyPacket) bCPGInputStreamWrap.readPacket();
                while (bCPGInputStreamWrap.nextPacketTag() == 61) {
                    bCPGInputStreamWrap.readPacket();
                }
                TrustPacket optionalTrustPacket2 = PGPKeyRing.readOptionalTrustPacket(bCPGInputStreamWrap);
                List signaturesAndTrust2 = PGPKeyRing.readSignaturesAndTrust(bCPGInputStreamWrap);
                list = this.keys;
                pGPPublicKey = new PGPSecretKey(secretSubkeyPacket, new PGPPublicKey(secretSubkeyPacket.getPublicKeyPacket(), optionalTrustPacket2, signaturesAndTrust2));
            } else {
                PublicSubkeyPacket publicSubkeyPacket = (PublicSubkeyPacket) bCPGInputStreamWrap.readPacket();
                TrustPacket optionalTrustPacket3 = PGPKeyRing.readOptionalTrustPacket(bCPGInputStreamWrap);
                List signaturesAndTrust3 = PGPKeyRing.readSignaturesAndTrust(bCPGInputStreamWrap);
                list = this.extraPubKeys;
                pGPPublicKey = new PGPPublicKey(publicSubkeyPacket, optionalTrustPacket3, signaturesAndTrust3);
            }
            list.add(pGPPublicKey);
        }
    }

    public PGPSecretKeyRing(List list) {
        this(list, new ArrayList());
    }

    private PGPSecretKeyRing(List list, List list2) {
        this.keys = list;
        this.extraPubKeys = list2;
    }

    public PGPSecretKeyRing(byte[] bArr) throws IOException, PGPException {
        this(new ByteArrayInputStream(bArr));
    }

    public static PGPSecretKeyRing copyWithNewPassword(PGPSecretKeyRing pGPSecretKeyRing, char[] cArr, char[] cArr2, int i2, SecureRandom secureRandom, String str) throws PGPException, NoSuchProviderException {
        return copyWithNewPassword(pGPSecretKeyRing, cArr, cArr2, i2, secureRandom, PGPUtil.getProvider(str));
    }

    public static PGPSecretKeyRing copyWithNewPassword(PGPSecretKeyRing pGPSecretKeyRing, char[] cArr, char[] cArr2, int i2, SecureRandom secureRandom, Provider provider) throws PGPException {
        ArrayList arrayList = new ArrayList(pGPSecretKeyRing.keys.size());
        Iterator secretKeys = pGPSecretKeyRing.getSecretKeys();
        while (secretKeys.hasNext()) {
            arrayList.add(PGPSecretKey.copyWithNewPassword((PGPSecretKey) secretKeys.next(), cArr, cArr2, i2, secureRandom, provider));
        }
        return new PGPSecretKeyRing(arrayList, pGPSecretKeyRing.extraPubKeys);
    }

    public static PGPSecretKeyRing insertSecretKey(PGPSecretKeyRing pGPSecretKeyRing, PGPSecretKey pGPSecretKey) {
        ArrayList arrayList = new ArrayList(pGPSecretKeyRing.keys);
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 != arrayList.size(); i2++) {
            PGPSecretKey pGPSecretKey2 = (PGPSecretKey) arrayList.get(i2);
            if (pGPSecretKey2.getKeyID() == pGPSecretKey.getKeyID()) {
                arrayList.set(i2, pGPSecretKey);
                z2 = true;
            }
            if (pGPSecretKey2.isMasterKey()) {
                z3 = true;
            }
        }
        if (!z2) {
            if (!pGPSecretKey.isMasterKey()) {
                arrayList.add(pGPSecretKey);
            } else {
                if (z3) {
                    throw new IllegalArgumentException("cannot add a master key to a ring that already has one");
                }
                arrayList.add(0, pGPSecretKey);
            }
        }
        return new PGPSecretKeyRing(arrayList, pGPSecretKeyRing.extraPubKeys);
    }

    public static PGPSecretKeyRing removeSecretKey(PGPSecretKeyRing pGPSecretKeyRing, PGPSecretKey pGPSecretKey) {
        ArrayList arrayList = new ArrayList(pGPSecretKeyRing.keys);
        boolean z2 = false;
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            if (((PGPSecretKey) arrayList.get(i2)).getKeyID() == pGPSecretKey.getKeyID()) {
                arrayList.remove(i2);
                z2 = true;
            }
        }
        if (z2) {
            return new PGPSecretKeyRing(arrayList, pGPSecretKeyRing.extraPubKeys);
        }
        return null;
    }

    public static PGPSecretKeyRing replacePublicKeys(PGPSecretKeyRing pGPSecretKeyRing, PGPPublicKeyRing pGPPublicKeyRing) {
        ArrayList arrayList = new ArrayList(pGPSecretKeyRing.keys.size());
        for (PGPSecretKey pGPSecretKey : pGPSecretKeyRing.keys) {
            arrayList.add(PGPSecretKey.replacePublicKey(pGPSecretKey, pGPPublicKeyRing.getPublicKey(pGPSecretKey.getKeyID())));
        }
        return new PGPSecretKeyRing(arrayList);
    }

    public void encode(OutputStream outputStream) throws IOException {
        for (int i2 = 0; i2 != this.keys.size(); i2++) {
            ((PGPSecretKey) this.keys.get(i2)).encode(outputStream);
        }
        for (int i3 = 0; i3 != this.extraPubKeys.size(); i3++) {
            ((PGPPublicKey) this.extraPubKeys.get(i3)).encode(outputStream);
        }
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Iterator getExtraPublicKeys() {
        return this.extraPubKeys.iterator();
    }

    public PGPPublicKey getPublicKey() {
        return ((PGPSecretKey) this.keys.get(0)).getPublicKey();
    }

    public PGPSecretKey getSecretKey() {
        return (PGPSecretKey) this.keys.get(0);
    }

    public PGPSecretKey getSecretKey(long j2) {
        for (int i2 = 0; i2 != this.keys.size(); i2++) {
            PGPSecretKey pGPSecretKey = (PGPSecretKey) this.keys.get(i2);
            if (j2 == pGPSecretKey.getKeyID()) {
                return pGPSecretKey;
            }
        }
        return null;
    }

    public Iterator getSecretKeys() {
        return Collections.unmodifiableList(this.keys).iterator();
    }
}
