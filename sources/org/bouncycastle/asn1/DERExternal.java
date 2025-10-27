package org.bouncycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* loaded from: classes9.dex */
public class DERExternal extends ASN1Object {
    private ASN1Object dataValueDescriptor;
    private DERObjectIdentifier directReference;
    private int encoding;
    private DERObject externalContent;
    private DERInteger indirectReference;

    public DERExternal(ASN1EncodableVector aSN1EncodableVector) {
        int i2 = 0;
        DERObject objFromVector = getObjFromVector(aSN1EncodableVector, 0);
        if (objFromVector instanceof DERObjectIdentifier) {
            this.directReference = (DERObjectIdentifier) objFromVector;
            objFromVector = getObjFromVector(aSN1EncodableVector, 1);
            i2 = 1;
        }
        if (objFromVector instanceof DERInteger) {
            this.indirectReference = (DERInteger) objFromVector;
            i2++;
            objFromVector = getObjFromVector(aSN1EncodableVector, i2);
        }
        if (!(objFromVector instanceof DERTaggedObject)) {
            this.dataValueDescriptor = (ASN1Object) objFromVector;
            i2++;
            objFromVector = getObjFromVector(aSN1EncodableVector, i2);
        }
        if (aSN1EncodableVector.size() != i2 + 1) {
            throw new IllegalArgumentException("input vector too large");
        }
        if (!(objFromVector instanceof DERTaggedObject)) {
            throw new IllegalArgumentException("No tagged object found in vector. Structure doesn't seem to be of type External");
        }
        DERTaggedObject dERTaggedObject = (DERTaggedObject) objFromVector;
        setEncoding(dERTaggedObject.getTagNo());
        this.externalContent = dERTaggedObject.getObject();
    }

    public DERExternal(DERObjectIdentifier dERObjectIdentifier, DERInteger dERInteger, ASN1Object aSN1Object, int i2, DERObject dERObject) {
        setDirectReference(dERObjectIdentifier);
        setIndirectReference(dERInteger);
        setDataValueDescriptor(aSN1Object);
        setEncoding(i2);
        setExternalContent(dERObject.getDERObject());
    }

    public DERExternal(DERObjectIdentifier dERObjectIdentifier, DERInteger dERInteger, ASN1Object aSN1Object, DERTaggedObject dERTaggedObject) {
        this(dERObjectIdentifier, dERInteger, aSN1Object, dERTaggedObject.getTagNo(), dERTaggedObject.getDERObject());
    }

    private DERObject getObjFromVector(ASN1EncodableVector aSN1EncodableVector, int i2) {
        if (aSN1EncodableVector.size() > i2) {
            return aSN1EncodableVector.get(i2).getDERObject();
        }
        throw new IllegalArgumentException("too few objects in input vector");
    }

    private void setDataValueDescriptor(ASN1Object aSN1Object) {
        this.dataValueDescriptor = aSN1Object;
    }

    private void setDirectReference(DERObjectIdentifier dERObjectIdentifier) {
        this.directReference = dERObjectIdentifier;
    }

    private void setEncoding(int i2) {
        if (i2 >= 0 && i2 <= 2) {
            this.encoding = i2;
            return;
        }
        throw new IllegalArgumentException("invalid encoding value: " + i2);
    }

    private void setExternalContent(DERObject dERObject) {
        this.externalContent = dERObject;
    }

    private void setIndirectReference(DERInteger dERInteger) {
        this.indirectReference = dERInteger;
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        ASN1Object aSN1Object;
        DERInteger dERInteger;
        DERObjectIdentifier dERObjectIdentifier;
        if (!(dERObject instanceof DERExternal)) {
            return false;
        }
        if (this == dERObject) {
            return true;
        }
        DERExternal dERExternal = (DERExternal) dERObject;
        DERObjectIdentifier dERObjectIdentifier2 = this.directReference;
        if (dERObjectIdentifier2 != null && ((dERObjectIdentifier = dERExternal.directReference) == null || !dERObjectIdentifier.equals(dERObjectIdentifier2))) {
            return false;
        }
        DERInteger dERInteger2 = this.indirectReference;
        if (dERInteger2 != null && ((dERInteger = dERExternal.indirectReference) == null || !dERInteger.equals(dERInteger2))) {
            return false;
        }
        ASN1Object aSN1Object2 = this.dataValueDescriptor;
        if (aSN1Object2 == null || ((aSN1Object = dERExternal.dataValueDescriptor) != null && aSN1Object.equals(aSN1Object2))) {
            return this.externalContent.equals(dERExternal.externalContent);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DERObjectIdentifier dERObjectIdentifier = this.directReference;
        if (dERObjectIdentifier != null) {
            byteArrayOutputStream.write(dERObjectIdentifier.getDEREncoded());
        }
        DERInteger dERInteger = this.indirectReference;
        if (dERInteger != null) {
            byteArrayOutputStream.write(dERInteger.getDEREncoded());
        }
        ASN1Object aSN1Object = this.dataValueDescriptor;
        if (aSN1Object != null) {
            byteArrayOutputStream.write(aSN1Object.getDEREncoded());
        }
        byteArrayOutputStream.write(new DERTaggedObject(this.encoding, this.externalContent).getDEREncoded());
        dEROutputStream.writeEncoded(32, 8, byteArrayOutputStream.toByteArray());
    }

    public ASN1Object getDataValueDescriptor() {
        return this.dataValueDescriptor;
    }

    public DERObjectIdentifier getDirectReference() {
        return this.directReference;
    }

    public int getEncoding() {
        return this.encoding;
    }

    public DERObject getExternalContent() {
        return this.externalContent;
    }

    public DERInteger getIndirectReference() {
        return this.indirectReference;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        DERObjectIdentifier dERObjectIdentifier = this.directReference;
        int iHashCode = dERObjectIdentifier != null ? dERObjectIdentifier.hashCode() : 0;
        DERInteger dERInteger = this.indirectReference;
        if (dERInteger != null) {
            iHashCode ^= dERInteger.hashCode();
        }
        ASN1Object aSN1Object = this.dataValueDescriptor;
        if (aSN1Object != null) {
            iHashCode ^= aSN1Object.hashCode();
        }
        return iHashCode ^ this.externalContent.hashCode();
    }
}
