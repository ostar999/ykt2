package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class RedoVideoNodeQuestionDialog extends CenterPopupView {
    private final OnConfirmListener mOnConfirmListener;

    public RedoVideoNodeQuestionDialog(@NonNull Context context, OnConfirmListener listener) {
        super(context);
        this.mOnConfirmListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        OnConfirmListener onConfirmListener = this.mOnConfirmListener;
        if (onConfirmListener != null) {
            onConfirmListener.onConfirm();
        }
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_redo_video_node_questions;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17098c.lambda$onCreate$0(view);
            }
        });
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.yf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17130c.lambda$onCreate$1(view);
            }
        });
    }
}
