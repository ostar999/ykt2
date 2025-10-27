package org.bouncycastle.asn1;

import cn.hutool.core.text.StrPool;
import java.io.IOException;

/* loaded from: classes9.dex */
public abstract class ASN1TaggedObject extends ASN1Object implements ASN1TaggedObjectParser {
    boolean empty;
    boolean explicit;
    DEREncodable obj;
    int tagNo;

    public ASN1TaggedObject(int i2, DEREncodable dEREncodable) {
        this.empty = false;
        this.explicit = true;
        this.tagNo = i2;
        this.obj = dEREncodable;
    }

    public ASN1TaggedObject(boolean z2, int i2, DEREncodable dEREncodable) {
        this.empty = false;
        this.explicit = true;
        this.obj = null;
        if (dEREncodable instanceof ASN1Choice) {
            this.explicit = true;
        } else {
            this.explicit = z2;
        }
        this.tagNo = i2;
        this.obj = dEREncodable;
    }

    public static ASN1TaggedObject getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1TaggedObject)) {
            return (ASN1TaggedObject) obj;
        }
        throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
    }

    public static ASN1TaggedObject getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        if (z2) {
            return (ASN1TaggedObject) aSN1TaggedObject.getObject();
        }
        throw new IllegalArgumentException("implicitly tagged tagged object");
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (!(dERObject instanceof ASN1TaggedObject)) {
            return false;
        }
        ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject) dERObject;
        if (this.tagNo != aSN1TaggedObject.tagNo || this.empty != aSN1TaggedObject.empty || this.explicit != aSN1TaggedObject.explicit) {
            return false;
        }
        DEREncodable dEREncodable = this.obj;
        return dEREncodable == null ? aSN1TaggedObject.obj == null : dEREncodable.getDERObject().equals(aSN1TaggedObject.obj.getDERObject());
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public abstract void encode(DEROutputStream dEROutputStream) throws IOException;

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public DERObject getLoadedObject() {
        return getDERObject();
    }

    public DERObject getObject() {
        DEREncodable dEREncodable = this.obj;
        if (dEREncodable != null) {
            return dEREncodable.getDERObject();
        }
        return null;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public DEREncodable getObjectParser(int i2, boolean z2) {
        if (i2 == 4) {
            return ASN1OctetString.getInstance(this, z2).parser();
        }
        if (i2 == 16) {
            return ASN1Sequence.getInstance(this, z2).parser();
        }
        if (i2 == 17) {
            return ASN1Set.getInstance(this, z2).parser();
        }
        if (z2) {
            return getObject();
        }
        throw new RuntimeException("implicit tagging not implemented for tag: " + i2);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this.tagNo;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        int i2 = this.tagNo;
        DEREncodable dEREncodable = this.obj;
        return dEREncodable != null ? i2 ^ dEREncodable.hashCode() : i2;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isExplicit() {
        return this.explicit;
    }

    public String toString() {
        return StrPool.BRACKET_START + this.tagNo + StrPool.BRACKET_END + this.obj;
    }
}
