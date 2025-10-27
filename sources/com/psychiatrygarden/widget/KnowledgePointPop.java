package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgePointPop extends BottomPopupView {
    private String videoId;

    public KnowledgePointPop(@NonNull Context context, String videoId) {
        super(context);
        this.videoId = videoId;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        new XPopup.Builder(view.getContext()).asCustom(new RedoVideoNodeQuestionDialog(view.getContext(), new OnConfirmListener() { // from class: com.psychiatrygarden.widget.KnowledgePointPop.1
            @Override // com.lxj.xpopup.interfaces.OnConfirmListener
            public void onConfirm() {
            }
        })).show();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_video_knowledge_point;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ka
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16646c.lambda$onCreate$0(view);
            }
        });
        findViewById(R.id.ll_redo).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.la
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16678c.lambda$onCreate$1(view);
            }
        });
    }
}
