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
public class PGPSecretKeyRingCollection {
    private List order;
    private Map secretRings;

    public PGPSecretKeyRingCollection(InputStream inputStream) throws IOException, PGPException {
        this.secretRings = new HashMap();
        this.order = new ArrayList();
        PGPObjectFactory pGPObjectFactory = new PGPObjectFactory(inputStream);
        while (true) {
            Object objNextObject = pGPObjectFactory.nextObject();
            if (objNextObject == null) {
                return;
            }
            if (!(objNextObject instanceof PGPSecretKeyRing)) {
                throw new PGPException(objNextObject.getClass().getName() + " found where PGPSecretKeyRing expected");
            }
            PGPSecretKeyRing pGPSecretKeyRing = (PGPSecretKeyRing) objNextObject;
            Long l2 = new Long(pGPSecretKeyRing.getPublicKey().getKeyID());
            this.secretRings.put(l2, pGPSecretKeyRing);
            this.order.add(l2);
        }
    }

    public PGPSecretKeyRingCollection(Collection collection) throws IOException, PGPException {
        this.secretRings = new HashMap();
        this.order = new ArrayList();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            PGPSecretKeyRing pGPSecretKeyRing = (PGPSecretKeyRing) it.next();
            Long l2 = new Long(pGPSecretKeyRing.getPublicKey().getKeyID());
            this.secretRings.put(l2, pGPSecretKeyRing);
            this.order.add(l2);
        }
    }

    private PGPSecretKeyRingCollection(Map map, List list) {
        this.secretRings = new HashMap();
        new ArrayList();
        this.secretRings = map;
        this.order = list;
    }

    public PGPSecretKeyRingCollection(byte[] bArr) throws IOException, PGPException {
        this(new ByteArrayInputStream(bArr));
    }

    public static PGPSecretKeyRingCollection addSecretKeyRing(PGPSecretKeyRingCollection pGPSecretKeyRingCollection, PGPSecretKeyRing pGPSecretKeyRing) {
        Long l2 = new Long(pGPSecretKeyRing.getPublicKey().getKeyID());
        if (pGPSecretKeyRingCollection.secretRings.containsKey(l2)) {
            throw new IllegalArgumentException("Collection already contains a key with a keyID for the passed in ring.");
        }
        HashMap map = new HashMap(pGPSecretKeyRingCollection.secretRings);
        ArrayList arrayList = new ArrayList(pGPSecretKeyRingCollection.order);
        map.put(l2, pGPSecretKeyRing);
        arrayList.add(l2);
        return new PGPSecretKeyRingCollection(map, arrayList);
    }

    public static PGPSecretKeyRingCollection removeSecretKeyRing(PGPSecretKeyRingCollection pGPSecretKeyRingCollection, PGPSecretKeyRing pGPSecretKeyRing) {
        Long l2 = new Long(pGPSecretKeyRing.getPublicKey().getKeyID());
        if (!pGPSecretKeyRingCollection.secretRings.containsKey(l2)) {
            throw new IllegalArgumentException("Collection does not contain a key with a keyID for the passed in ring.");
        }
        HashMap map = new HashMap(pGPSecretKeyRingCollection.secretRings);
        ArrayList arrayList = new ArrayList(pGPSecretKeyRingCollection.order);
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
        return new PGPSecretKeyRingCollection(map, arrayList);
    }

    public boolean contains(long j2) throws PGPException {
        return getSecretKey(j2) != null;
    }

    public void encode(OutputStream outputStream) throws IOException {
        BCPGOutputStream bCPGOutputStream = outputStream instanceof BCPGOutputStream ? (BCPGOutputStream) outputStream : new BCPGOutputStream(outputStream);
        Iterator it = this.order.iterator();
        while (it.hasNext()) {
            ((PGPSecretKeyRing) this.secretRings.get(it.next())).encode(bCPGOutputStream);
        }
    }

    public byte[] getEncoded() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        encode(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Iterator getKeyRings() {
        return this.secretRings.values().iterator();
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
            PGPSecretKeyRing pGPSecretKeyRing = (PGPSecretKeyRing) keyRings.next();
            Iterator userIDs = pGPSecretKeyRing.getSecretKey().getUserIDs();
            while (userIDs.hasNext()) {
                String lowerCase = (String) userIDs.next();
                if (z3) {
                    lowerCase = Strings.toLowerCase(lowerCase);
                }
                if (z2) {
                    if (lowerCase.indexOf(str) > -1) {
                        arrayList.add(pGPSecretKeyRing);
                    }
                } else if (lowerCase.equals(str)) {
                    arrayList.add(pGPSecretKeyRing);
                }
            }
        }
        return arrayList.iterator();
    }

    public PGPSecretKey getSecretKey(long j2) throws PGPException {
        Iterator keyRings = getKeyRings();
        while (keyRings.hasNext()) {
            PGPSecretKey secretKey = ((PGPSecretKeyRing) keyRings.next()).getSecretKey(j2);
            if (secretKey != null) {
                return secretKey;
            }
        }
        return null;
    }

    public PGPSecretKeyRing getSecretKeyRing(long j2) throws PGPException {
        Long l2 = new Long(j2);
        if (this.secretRings.containsKey(l2)) {
            return (PGPSecretKeyRing) this.secretRings.get(l2);
        }
        Iterator keyRings = getKeyRings();
        while (keyRings.hasNext()) {
            PGPSecretKeyRing pGPSecretKeyRing = (PGPSecretKeyRing) keyRings.next();
            if (pGPSecretKeyRing.getSecretKey(j2) != null) {
                return pGPSecretKeyRing;
            }
        }
        return null;
    }

    public int size() {
        return this.order.size();
    }
}
