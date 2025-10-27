package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.PositionPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CombinRecordEntranceGuidePop extends PositionPopupView {
    public CombinRecordEntranceGuidePop(@NonNull Context context) {
        super(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.dialog_show_combin_question_guid;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.j3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16606c.lambda$onCreate$0(view);
            }
        });
    }
}
