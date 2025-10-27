package org.bouncycastle.crypto.params;

/* loaded from: classes9.dex */
public class ECKeyParameters extends AsymmetricKeyParameter {
    ECDomainParameters params;

    public ECKeyParameters(boolean z2, ECDomainParameters eCDomainParameters) {
        super(z2);
        this.params = eCDomainParameters;
    }

    public ECDomainParameters getParameters() {
        return this.params;
    }
}
