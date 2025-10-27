package org.bouncycastle.ocsp;

import java.text.ParseException;
import java.util.Date;
import org.bouncycastle.asn1.DERGeneralizedTime;
import org.bouncycastle.asn1.ocsp.RevokedInfo;
import org.bouncycastle.asn1.x509.CRLReason;

/* loaded from: classes9.dex */
public class RevokedStatus implements CertificateStatus {
    RevokedInfo info;

    public RevokedStatus(Date date, int i2) {
        this.info = new RevokedInfo(new DERGeneralizedTime(date), new CRLReason(i2));
    }

    public RevokedStatus(RevokedInfo revokedInfo) {
        this.info = revokedInfo;
    }

    public int getRevocationReason() {
        if (this.info.getRevocationReason() != null) {
            return this.info.getRevocationReason().getValue().intValue();
        }
        throw new IllegalStateException("attempt to get a reason where none is available");
    }

    public Date getRevocationTime() {
        try {
            return this.info.getRevocationTime().getDate();
        } catch (ParseException e2) {
            throw new IllegalStateException("ParseException:" + e2.getMessage());
        }
    }

    public boolean hasRevocationReason() {
        return this.info.getRevocationReason() != null;
    }
}
