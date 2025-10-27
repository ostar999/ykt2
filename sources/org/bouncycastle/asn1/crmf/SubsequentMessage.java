package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.DERInteger;

/* loaded from: classes9.dex */
public class SubsequentMessage extends DERInteger {
    public static final SubsequentMessage encrCert = new SubsequentMessage(0);
    public static final SubsequentMessage challengeResp = new SubsequentMessage(1);

    private SubsequentMessage(int i2) {
        super(i2);
    }

    public static SubsequentMessage valueOf(int i2) {
        if (i2 == 0) {
            return encrCert;
        }
        if (i2 == 1) {
            return challengeResp;
        }
        throw new IllegalArgumentException("unknown value: " + i2);
    }
}
