package com.catchpig.mvvm.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BubbleAttachPopupView;

/* loaded from: classes2.dex */
public class BubbleAttachDirectionPopupView extends BubbleAttachPopupView {
    private int mode;

    public BubbleAttachDirectionPopupView(@NonNull Context context) {
        super(context);
        this.mode = 0;
    }

    @Override // com.lxj.xpopup.core.BubbleAttachPopupView
    public boolean isShowUpToTarget() {
        int i2 = this.mode;
        if (100 == i2) {
            return super.isShowUpToTarget();
        }
        if (101 == i2) {
            return true;
        }
        if (102 == i2) {
            return false;
        }
        return super.isShowUpToTarget();
    }

    public void setMode(int i2) {
        this.mode = i2;
    }
}
