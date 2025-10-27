package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class NextNodePopWindow extends CenterPopupView {
    public TextView close;
    public TextView content;
    private boolean isDailyTaskDoQuestion;
    public TextView ok;
    public ClickIml sClickIml;

    public interface ClickIml {
        void mClickIml();
    }

    public NextNodePopWindow(@NonNull Context context, boolean isDailyTaskDoQuestion, ClickIml sClickIml) {
        super(context);
        this.isDailyTaskDoQuestion = isDailyTaskDoQuestion;
        this.sClickIml = sClickIml;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        dismiss();
        this.sClickIml.mClickIml();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_next_node_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.close = (TextView) findViewById(R.id.cancel);
        this.ok = (TextView) findViewById(R.id.tv_next);
        this.content = (TextView) findViewById(R.id.tv_content);
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.jb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16615c.lambda$onCreate$0(view);
            }
        });
        this.ok.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.kb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16647c.lambda$onCreate$1(view);
            }
        });
        if (this.isDailyTaskDoQuestion) {
            this.content.setText("已经是最后一题，是否跳转下一节？");
        }
    }
}
