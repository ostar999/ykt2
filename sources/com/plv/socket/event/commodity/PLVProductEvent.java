package com.plv.socket.event.commodity;

import com.tencent.connect.common.Constants;

/* loaded from: classes5.dex */
public class PLVProductEvent {
    public static final String EVENT = "PRODUCT_MESSAGE";
    private String status;

    private boolean isExpurgate() {
        return "3".equals(this.status);
    }

    private boolean isMenuSwitch() {
        return Constants.VIA_REPORT_TYPE_SHARE_TO_QQ.equals(this.status);
    }

    private boolean isMoveDown() {
        return "7".equals(this.status);
    }

    private boolean isMoveUp() {
        return Constants.VIA_SHARE_TYPE_INFO.equals(this.status);
    }

    private boolean isNewly() {
        return "4".equals(this.status);
    }

    private boolean isPullOffShelves() {
        return "2".equals(this.status);
    }

    private boolean isPush() {
        return Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(this.status);
    }

    private boolean isPutOnShelves() {
        return "1".equals(this.status);
    }

    private boolean isRedact() {
        return "5".equals(this.status);
    }

    public boolean isProductControlEvent() {
        return isPutOnShelves() || isNewly() || isRedact() || isPush();
    }

    public boolean isProductMenuSwitchEvent() {
        return isMenuSwitch();
    }

    public boolean isProductMoveEvent() {
        return isMoveDown() || isMoveUp();
    }

    public boolean isProductRemoveEvent() {
        return isPullOffShelves() || isExpurgate();
    }
}
