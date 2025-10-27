package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class EntranceNoPopWindow extends CenterPopupView {
    private ImageView close;

    public EntranceNoPopWindow(@NonNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_entranceno_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.close = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.EntranceNoPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                EntranceNoPopWindow.this.dismiss();
            }
        });
    }
}
