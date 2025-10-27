package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ReDoPopWindow extends BottomPopupView {
    public TextView allsubject;
    public LinearLayout cancle;
    public TextView chapter;
    public OnClickIml mOnClickIml;
    public TextView subject;

    public interface OnClickIml {
        void onClickData(int position);
    }

    public ReDoPopWindow(@NonNull Context context, OnClickIml mOnClickIml) {
        super(context);
        this.mOnClickIml = mOnClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mOnClickIml.onClickData(0);
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        this.mOnClickIml.onClickData(1);
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        this.mOnClickIml.onClickData(2);
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_redo_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.cancle = (LinearLayout) findViewById(R.id.cancle);
        this.chapter = (TextView) findViewById(R.id.chapter);
        this.subject = (TextView) findViewById(R.id.subject);
        this.allsubject = (TextView) findViewById(R.id.allsubject);
        this.chapter.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.tf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16935c.lambda$onCreate$0(view);
            }
        });
        this.subject.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.uf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16963c.lambda$onCreate$1(view);
            }
        });
        this.allsubject.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.vf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17003c.lambda$onCreate$2(view);
            }
        });
        this.cancle.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.wf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17053c.lambda$onCreate$3(view);
            }
        });
    }
}
