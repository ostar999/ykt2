package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

/* loaded from: classes9.dex */
public abstract class ASN1Set extends ASN1Object {
    protected Vector set = new Vector();

    private byte[] getEncoded(DEREncodable dEREncodable) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ASN1OutputStream(byteArrayOutputStream).writeObject(dEREncodable);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    public static ASN1Set getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1Set)) {
            return (ASN1Set) obj;
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1Set getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        if (z2) {
            if (aSN1TaggedObject.isExplicit()) {
                return (ASN1Set) aSN1TaggedObject.getObject();
            }
            throw new IllegalArgumentException("object implicit - explicit expected.");
        }
        if (aSN1TaggedObject.isExplicit()) {
            return new DERSet(aSN1TaggedObject.getObject());
        }
        if (aSN1TaggedObject.getObject() instanceof ASN1Set) {
            return (ASN1Set) aSN1TaggedObject.getObject();
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (aSN1TaggedObject.getObject() instanceof ASN1Sequence) {
            Enumeration objects = ((ASN1Sequence) aSN1TaggedObject.getObject()).getObjects();
            while (objects.hasMoreElements()) {
                aSN1EncodableVector.add((DEREncodable) objects.nextElement());
            }
            return new DERSet(aSN1EncodableVector, false);
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + aSN1TaggedObject.getClass().getName());
    }

    private DEREncodable getNext(Enumeration enumeration) {
        DEREncodable dEREncodable = (DEREncodable) enumeration.nextElement();
        return dEREncodable == null ? DERNull.INSTANCE : dEREncodable;
    }

    private boolean lessThanOrEqual(byte[] bArr, byte[] bArr2) {
        int iMin = Math.min(bArr.length, bArr2.length);
        for (int i2 = 0; i2 != iMin; i2++) {
            byte b3 = bArr[i2];
            byte b4 = bArr2[i2];
            if (b3 != b4) {
                return (b3 & 255) < (b4 & 255);
            }
        }
        return iMin == bArr.length;
    }

    public void addObject(DEREncodable dEREncodable) {
        this.set.addElement(dEREncodable);
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (!(dERObject instanceof ASN1Set)) {
            return false;
        }
        ASN1Set aSN1Set = (ASN1Set) dERObject;
        if (size() != aSN1Set.size()) {
            return false;
        }
        Enumeration objects = getObjects();
        Enumeration objects2 = aSN1Set.getObjects();
        while (objects.hasMoreElements()) {
            DEREncodable next = getNext(objects);
            DEREncodable next2 = getNext(objects2);
            DERObject dERObject2 = next.getDERObject();
            DERObject dERObject3 = next2.getDERObject();
            if (dERObject2 != dERObject3 && !dERObject2.equals(dERObject3)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public abstract void encode(DEROutputStream dEROutputStream) throws IOException;

    public DEREncodable getObjectAt(int i2) {
        return (DEREncodable) this.set.elementAt(i2);
    }

    public Enumeration getObjects() {
        return this.set.elements();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        Enumeration objects = getObjects();
        int size = size();
        while (objects.hasMoreElements()) {
            size = (size * 17) ^ getNext(objects).hashCode();
        }
        return size;
    }

    public ASN1SetParser parser() {
        return new ASN1SetParser() { // from class: org.bouncycastle.asn1.ASN1Set.1
            private int index;
            private final int max;

            {
                this.max = ASN1Set.this.size();
            }

            @Override // org.bouncycastle.asn1.DEREncodable
            public DERObject getDERObject() {
                return this;
            }

            @Override // org.bouncycastle.asn1.InMemoryRepresentable
            public DERObject getLoadedObject() {
                return this;
            }

            @Override // org.bouncycastle.asn1.ASN1SetParser
            public DEREncodable readObject() throws IOException {
                int i2 = this.index;
                if (i2 == this.max) {
                    return null;
                }
                ASN1Set aSN1Set = ASN1Set.this;
                this.index = i2 + 1;
                DEREncodable objectAt = aSN1Set.getObjectAt(i2);
                return objectAt instanceof ASN1Sequence ? ((ASN1Sequence) objectAt).parser() : objectAt instanceof ASN1Set ? ((ASN1Set) objectAt).parser() : objectAt;
            }
        };
    }

    public int size() {
        return this.set.size();
    }

    public void sort() {
        if (this.set.size() > 1) {
            int size = this.set.size() - 1;
            boolean z2 = true;
            while (z2) {
                int i2 = 0;
                byte[] encoded = getEncoded((DEREncodable) this.set.elementAt(0));
                z2 = false;
                int i3 = 0;
                while (i3 != size) {
                    int i4 = i3 + 1;
                    byte[] encoded2 = getEncoded((DEREncodable) this.set.elementAt(i4));
                    if (lessThanOrEqual(encoded, encoded2)) {
                        encoded = encoded2;
                    } else {
                        Object objElementAt = this.set.elementAt(i3);
                        Vector vector = this.set;
                        vector.setElementAt(vector.elementAt(i4), i3);
                        this.set.setElementAt(objElementAt, i4);
                        z2 = true;
                        i2 = i3;
                    }
                    i3 = i4;
                }
                size = i2;
            }
        }
    }

    public ASN1Encodable[] toArray() {
        ASN1Encodable[] aSN1EncodableArr = new ASN1Encodable[size()];
        for (int i2 = 0; i2 != size(); i2++) {
            aSN1EncodableArr[i2] = (ASN1Encodable) getObjectAt(i2);
        }
        return aSN1EncodableArr;
    }

    public String toString() {
        return this.set.toString();
    }
}
