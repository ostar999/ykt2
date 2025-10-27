package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.event.ShowFilterYearEvent;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;

/* loaded from: classes6.dex */
public class NoQuestionByYearFilterDialogs extends BottomPopupView {
    private String mCategory;

    public NoQuestionByYearFilterDialogs(@NonNull Context context, String category) {
        super(context);
        this.mCategory = category;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        EventBus.getDefault().post(new ShowFilterYearEvent(this.mCategory));
        dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.show_no_question_window;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        ((TextView) findViewById(R.id.btn_update)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.lb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16679c.lambda$onCreate$0(view);
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        XPopup.setShadowBgColor(Color.parseColor("#7F000000"));
    }
}
