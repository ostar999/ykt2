package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.lxj.xpopup.core.CenterPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ExamTimeHintPop extends CenterPopupView {
    private long examMinute;
    private long leftMinute;

    public ExamTimeHintPop(@NonNull Context context, long leftMinute, long examMinute) {
        super(context);
        this.leftMinute = leftMinute;
        this.examMinute = examMinute;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_exam_test_time_pop;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_content);
        int color = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_color}).getColor(0, ContextCompat.getColor(getContext(), R.color.main_theme_color));
        SpannableString spannableString = new SpannableString("离考试结束时间还有" + this.leftMinute + "分钟，已不足" + this.examMinute + "分钟，请把握好做题时间哦~");
        spannableString.setSpan(new ForegroundColorSpan(color), 9, String.valueOf(this.leftMinute).length() + 9, 34);
        textView.setText(spannableString);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.e9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16434c.lambda$onCreate$0(view);
            }
        });
    }
}
