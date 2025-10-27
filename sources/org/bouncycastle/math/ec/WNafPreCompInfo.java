package org.bouncycastle.math.ec;

/* loaded from: classes9.dex */
class WNafPreCompInfo implements PreCompInfo {
    private ECPoint[] preComp = null;
    private ECPoint twiceP = null;

    public ECPoint[] getPreComp() {
        return this.preComp;
    }

    public ECPoint getTwiceP() {
        return this.twiceP;
    }

    public void setPreComp(ECPoint[] eCPointArr) {
        this.preComp = eCPointArr;
    }

    public void setTwiceP(ECPoint eCPoint) {
        this.twiceP = eCPoint;
    }
}
