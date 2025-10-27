package org.bouncycastle.openpgp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class PGPPublicKeyRingCollection {
    private List order;
    private Map pubRings;

    public PGPPublicKeyRingCollection(InputStream inputStream) throws IOException, PGPException {
        this.pubRings = new HashMap();
        this.order = new ArrayList();
        PGPObjectFactory pGPObjectFactory = new PGPObjectFactory(inputStream);
        while (true) {
            Object objNextObject = pGPObjectFactory.nextObject();
            if (objNextObject == null) {
                return;
            }
            if (!(objNextObject instanceof PGPPublicKeyRing)) {
                throw new PGPException(objNextObject.getClass().getName() + " found where PGPPublicKeyRing expected");
            }
            PGPPublicKeyRing pGPPublicKeyRing = (PGPPublicKeyRing) objNextObject;
            Long l2 = new Long(pGPPublicKeyRing.getPublicKey().getKeyID());
            this.pubRings.put(l2, pGPPublicKeyRing);
            this.order.add(l2);
        }
    }

    public PGPPublicKeyRingCollection(Collection collection) throws IOException, PGPException {
        this.pubRings = new HashMap();
        this.order = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            PGPPublicKeyRing pGPPublicKeyRing = (PGPPublicKeyRing) it.next();
            Long l2 = new Long(pGPPublicKeyRing.getPublicKey().getKeyID());
            this.pubRings.put(l2, pGPPublicKeyRing);
            this.order.add(l2);
        }
    }

    private PGPPublicKeyRingCollection(Map map, List list) {
        this.pubRings = new HashMap();
        new ArrayList();
        this.pubRings = map;
        this.order = list;
    }

    public PGPPublicKeyRingCollection(byte[] bArr) throws IOException, PGPException {
        this(new ByteArrayInputStream(bArr));
    }

    public static PGPPublicKeyRingCollection addPublicKeyRing(PGPPublicKeyRingCollection pGPPublicKeyRingCollection, PGPPublicKeyRing pGPPublicKeyRing) {
        Long l2 = new Long(pGPPublicKeyRing.getPublicKey().getKeyID());
        if (pGPPublicKeyRingCollection.pubRings.containsKey(l2)) {
            throw new IllegalArgumentException("Collection already contains a key with a keyID for the passed in ring.");
        }
        HashMap map = new HashMap(pGPPublicKeyRingCollection.pubRings);
        ArrayList arrayList = new ArrayList(pGPPublicKeyRingCollection.order);
        map.put(l2, pGPPublicKeyRing);
        arrayList.add(l2);
        return new PGPPublicKeyRingCollection(map, arrayList);
    }

    public static PGPPublicKeyRingCollection removePublicKeyRing(PGPPublicKeyRingCollection pGPPublicKeyRingCollection, PGPPublicKeyRing pGPPublicKeyRing) {
        Long l2 = new Long(pGPPublicKeyRing.getPublicKey().getKeyID());
        if (!pGPPublicKeyRingCollection.pubRings.containsKey(l2)) {
            throw new IllegalArgumentException("Collection does not contain a key with a keyID for the passed in ring.");
        }
        HashMap map = new HashMap(pGPPublicKeyRingCollection.pubRings);
        ArrayList arrayList = new ArrayList(pGPPublicKeyRingCollection.order);
        map.remove(l2);
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                break;
            }
            if (((Long) arrayList.get(i2)).longValue() == l2.longValue()) {
                arrayList.remove(i2);
                break;
            }
            i2++;
        }
        return new PGPPublicKeyRingCollection(map, arrayList);
    }

    public boolean contains(long j2) throws PGPException {
        return getPublicKey(j2) != null;
    }

    public void encode(OutputStream outputStream) throws IOException {
        BCPGOutputStream bCPGOutputStream = outputStream instanceof BCPGOutputStream ? (BCPGOutputStream) outputStream : new BCPGOutputStream(outputStream);
        Iterator it = this.order.iterator();
        while (it.hasNext()) {
            ((PGPPublicKeyRing) this.pubRings.get(it.next())).encode(bCPGOutputStream);
        }
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Iterator getKeyRings() {
        return this.pubRings.values().iterator();
    }

    public Iterator getKeyRings(String str) throws PGPException {
        return getKeyRings(str, false, false);
    }

    public Iterator getKeyRings(String str, boolean z2) throws PGPException {
        return getKeyRings(str, z2, false);
    }

    public Iterator getKeyRings(String str, boolean z2, boolean z3) throws PGPException {
        Iterator keyRings = getKeyRings();
        ArrayList arrayList = new ArrayList();
        if (z3) {
            str = Strings.toLowerCase(str);
        }
        while (keyRings.hasNext()) {
            PGPPublicKeyRing pGPPublicKeyRing = (PGPPublicKeyRing) keyRings.next();
            Iterator userIDs = pGPPublicKeyRing.getPublicKey().getUserIDs();
            while (userIDs.hasNext()) {
                String lowerCase = (String) userIDs.next();
                if (z3) {
                    lowerCase = Strings.toLowerCase(lowerCase);
                }
                if (z2) {
                    if (lowerCase.indexOf(str) > -1) {
                        arrayList.add(pGPPublicKeyRing);
                    }
                } else if (lowerCase.equals(str)) {
                    arrayList.add(pGPPublicKeyRing);
                }
            }
        }
        return arrayList.iterator();
    }

    public PGPPublicKey getPublicKey(long j2) throws PGPException {
        Iterator keyRings = getKeyRings();
        while (keyRings.hasNext()) {
            PGPPublicKey publicKey = ((PGPPublicKeyRing) keyRings.next()).getPublicKey(j2);
            if (publicKey != null) {
                return publicKey;
            }
        }
        return null;
    }

    public PGPPublicKeyRing getPublicKeyRing(long j2) throws PGPException {
        Long l2 = new Long(j2);
        if (this.pubRings.containsKey(l2)) {
            return (PGPPublicKeyRing) this.pubRings.get(l2);
        }
        Iterator keyRings = getKeyRings();
        while (keyRings.hasNext()) {
            PGPPublicKeyRing pGPPublicKeyRing = (PGPPublicKeyRing) keyRings.next();
            if (pGPPublicKeyRing.getPublicKey(j2) != null) {
                return pGPPublicKeyRing;
            }
        }
        return null;
    }

    public int size() {
        return this.order.size();
    }
}
