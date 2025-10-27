package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CommentCoursePop extends CenterPopupView {
    private final OnConfirmListener mListener;

    public CommentCoursePop(@NonNull Context context, OnConfirmListener l2) {
        super(context);
        this.mListener = l2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        OnConfirmListener onConfirmListener = this.mListener;
        if (onConfirmListener != null) {
            onConfirmListener.onConfirm();
        }
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_comment_course;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.k3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16636c.lambda$onCreate$0(view);
            }
        });
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.l3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16667c.lambda$onCreate$1(view);
            }
        });
    }
}
