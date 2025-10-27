package org.bouncycastle.crypto.params;

/* loaded from: classes9.dex */
public class DHKeyParameters extends AsymmetricKeyParameter {
    private DHParameters params;

    public DHKeyParameters(boolean z2, DHParameters dHParameters) {
        super(z2);
        this.params = dHParameters;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DHKeyParameters)) {
            return false;
        }
        DHParameters dHParameters = this.params;
        DHParameters parameters = ((DHKeyParameters) obj).getParameters();
        return dHParameters == null ? parameters == null : dHParameters.equals(parameters);
    }

    public DHParameters getParameters() {
        return this.params;
    }

    public int hashCode() {
        int i2 = !isPrivate() ? 1 : 0;
        DHParameters dHParameters = this.params;
        return dHParameters != null ? i2 ^ dHParameters.hashCode() : i2;
    }
}
