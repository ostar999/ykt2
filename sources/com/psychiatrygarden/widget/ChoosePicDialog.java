package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ChoosePicDialog extends BottomPopupView {
    private OnBtnClickLisenter mClickLisenter;

    public interface OnBtnClickLisenter {
        void onClickLisenter(boolean isCamera, ChoosePicDialog dialog);
    }

    public ChoosePicDialog(@NonNull Context context, OnBtnClickLisenter clickLisenter) {
        super(context);
        this.mClickLisenter = clickLisenter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        this.mClickLisenter.onClickLisenter(true, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        this.mClickLisenter.onClickLisenter(false, this);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_choose_pic;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.btn_camera);
        TextView textView2 = (TextView) findViewById(R.id.btn_album);
        ((TextView) findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.a2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16304c.lambda$onCreate$0(view);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.b2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16331c.lambda$onCreate$1(view);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.c2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16357c.lambda$onCreate$2(view);
            }
        });
    }
}
