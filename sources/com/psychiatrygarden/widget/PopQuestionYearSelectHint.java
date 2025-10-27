package com.psychiatrygarden.widget;

import android.content.Context;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.AttachPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PopQuestionYearSelectHint extends AttachPopupView {
    private TextView tv_note_edit;

    public PopQuestionYearSelectHint(@NonNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_question_year_select_hint;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
    }
}
