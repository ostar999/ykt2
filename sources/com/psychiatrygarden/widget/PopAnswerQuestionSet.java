package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;

/* loaded from: classes6.dex */
public class PopAnswerQuestionSet extends BottomPopupView implements View.OnClickListener {
    private TextView tv_cancel;
    private TextView tv_font;

    public PopAnswerQuestionSet(@NonNull Context context) {
        super(context);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_answer_question_set;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_cancel) {
            dismiss();
        } else {
            if (id != R.id.tv_font) {
                return;
            }
            int intConfig = SharePreferencesUtils.readIntConfig(CommonParameter.QUESTION_FONT_SIZE, getContext(), 2);
            new XPopup.Builder(getContext()).atView(v2).asCenterList("", new String[]{"特大", "大", "标准", "小", "较小"}, null, intConfig, new com.lxj.xpopup.interfaces.OnSelectListener() { // from class: com.psychiatrygarden.widget.PopAnswerQuestionSet.1
                @Override // com.lxj.xpopup.interfaces.OnSelectListener
                public void onSelect(int position, String text) {
                    SharePreferencesUtils.writeIntConfig(CommonParameter.QUESTION_FONT_SIZE, position, PopAnswerQuestionSet.this.getContext());
                    EventBus.getDefault().post(EventBusConstant.EVENT_QUESTION_FONT_SIZE);
                }
            }).show();
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tv_font = (TextView) findViewById(R.id.tv_font);
        this.tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        this.tv_font.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17093c.onClick(view);
            }
        });
        this.tv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17093c.onClick(view);
            }
        });
    }
}
