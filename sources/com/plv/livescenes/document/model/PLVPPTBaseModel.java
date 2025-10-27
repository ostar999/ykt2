package com.plv.livescenes.document.model;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVPPTBaseModel implements PLVFoundationVO {
    private boolean isSelected;
    protected int pos;

    public int getPos() {
        return this.pos;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setPos(int i2) {
        this.pos = i2;
    }

    public void setSelected(boolean z2) {
        this.isSelected = z2;
    }
}
