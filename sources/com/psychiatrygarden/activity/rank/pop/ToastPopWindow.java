package com.psychiatrygarden.activity.rank.pop;

import android.content.Context;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ToastPopWindow extends CenterPopupView {
    public String authentication;
    public String rank;
    public String titles;
    public String total;
    public String txt;
    public String yanzhengs;

    public ToastPopWindow(@NonNull Context context, String title, String txt, String yanzheng, String authentication) {
        super(context);
        this.txt = txt;
        this.titles = title;
        this.yanzhengs = yanzheng;
        this.authentication = authentication;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_toast_pop;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:8:0x005e  */
    @Override // com.lxj.xpopup.core.BasePopupView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate() {
        /*
            Method dump skipped, instructions count: 312
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.activity.rank.pop.ToastPopWindow.onCreate():void");
    }
}
