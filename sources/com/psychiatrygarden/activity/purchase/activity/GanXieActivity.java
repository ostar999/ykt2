package com.psychiatrygarden.activity.purchase.activity;

import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.util.Bimp;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class GanXieActivity extends BaseActivity {
    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_wanjie);
        setTitle("感谢");
        try {
            if (Bimp.temp.size() > 0) {
                for (int i2 = 0; i2 < Bimp.temp.size(); i2++) {
                    if (Bimp.temp.get(i2).getBitmap().isRecycled()) {
                        Bimp.temp.get(i2).getBitmap().recycle();
                    }
                }
                Bimp.temp.clear();
            }
            if (Bimp.tempSelectBitmap.size() > 0) {
                for (int i3 = 0; i3 < Bimp.tempSelectBitmap.size(); i3++) {
                    if (Bimp.tempSelectBitmap.get(i3).getBitmap().isRecycled()) {
                        Bimp.tempSelectBitmap.get(i3).getBitmap().recycle();
                    }
                }
                Bimp.tempSelectBitmap.clear();
            }
            Bimp.max = 0;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
