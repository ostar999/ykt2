package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DeleteNotePop extends CenterPopupView {
    private OnConfirmListener mOnClickBtnListener;

    public DeleteNotePop(@NonNull Context context, OnConfirmListener listener) {
        super(context);
        this.mOnClickBtnListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        if (this.mOnClickBtnListener != null) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        OnConfirmListener onConfirmListener = this.mOnClickBtnListener;
        if (onConfirmListener != null) {
            onConfirmListener.onConfirm();
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_delete_note_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16983c.lambda$onCreate$0(view);
            }
        });
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17031c.lambda$onCreate$1(view);
            }
        });
    }
}
