package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CourseVideoPopWindow extends BottomPopupView implements View.OnClickListener {
    private boolean isQuestion;
    private SpeedAtView mSpeedAtView;

    public interface SpeedAtView {
        void onShowAtView(int type);
    }

    public CourseVideoPopWindow(@NonNull Context context, SpeedAtView mSpeedAtView) {
        super(context);
        this.isQuestion = false;
        this.mSpeedAtView = mSpeedAtView;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_course_video_speed_pop;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (this.mSpeedAtView == null) {
        }
        switch (v2.getId()) {
            case R.id.beisubofang /* 2131362187 */:
                this.mSpeedAtView.onShowAtView(2);
                dismiss();
                break;
            case R.id.jinqunjiaoliu /* 2131364312 */:
                this.mSpeedAtView.onShowAtView(0);
                dismiss();
                break;
            case R.id.lixiantuisong /* 2131364712 */:
                this.mSpeedAtView.onShowAtView(1);
                dismiss();
                break;
            case R.id.tv_cancel /* 2131367766 */:
                dismiss();
                break;
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.jinqunjiaoliu);
        TextView textView2 = (TextView) findViewById(R.id.lixiantuisong);
        TextView textView3 = (TextView) findViewById(R.id.beisubofang);
        TextView textView4 = (TextView) findViewById(R.id.tv_cancel);
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        if (this.isQuestion) {
            textView.setVisibility(8);
            textView2.setVisibility(8);
        } else {
            textView.setVisibility(0);
            textView2.setVisibility(0);
        }
    }

    public CourseVideoPopWindow(@NonNull Context context, SpeedAtView mSpeedAtView, boolean isQuestion) {
        super(context);
        this.mSpeedAtView = mSpeedAtView;
        this.isQuestion = isQuestion;
    }
}
