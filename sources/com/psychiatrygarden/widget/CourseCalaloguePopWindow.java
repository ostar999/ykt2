package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CourseCalaloguePopWindow extends BottomPopupView implements View.OnClickListener {
    TextView bigSpeed;
    public SpeedClick mSpeedClick;
    TextView middleSpeed;
    TextView nomalspeed;
    TextView oneSmallSpeed;
    TextView smallSpeed;
    public int speekPosition;

    public interface SpeedClick {
        void mSpeedClick(int type);
    }

    public CourseCalaloguePopWindow(@NonNull Context context, int speekPosition, SpeedClick mSpeedClick) {
        super(context);
        this.mSpeedClick = mSpeedClick;
        this.speekPosition = speekPosition;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_coursecala_pop;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        switch (v2.getId()) {
            case R.id.bigSpeed /* 2131362241 */:
                dismiss();
                this.mSpeedClick.mSpeedClick(4);
                selectTextColor(false, false, false, false, true);
                break;
            case R.id.middleSpeed /* 2131365437 */:
                dismiss();
                this.mSpeedClick.mSpeedClick(3);
                selectTextColor(false, false, false, true, false);
                break;
            case R.id.nomalspeed /* 2131365623 */:
                dismiss();
                this.mSpeedClick.mSpeedClick(1);
                selectTextColor(false, true, false, false, false);
                break;
            case R.id.oneSmallSpeed /* 2131365668 */:
                dismiss();
                this.mSpeedClick.mSpeedClick(2);
                selectTextColor(false, false, true, false, false);
                break;
            case R.id.smallSpeed /* 2131366980 */:
                dismiss();
                this.mSpeedClick.mSpeedClick(0);
                selectTextColor(true, false, false, false, false);
                break;
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.nomalspeed = (TextView) findViewById(R.id.nomalspeed);
        this.smallSpeed = (TextView) findViewById(R.id.smallSpeed);
        this.middleSpeed = (TextView) findViewById(R.id.middleSpeed);
        this.bigSpeed = (TextView) findViewById(R.id.bigSpeed);
        this.oneSmallSpeed = (TextView) findViewById(R.id.oneSmallSpeed);
        this.nomalspeed.setOnClickListener(this);
        this.smallSpeed.setOnClickListener(this);
        this.middleSpeed.setOnClickListener(this);
        this.bigSpeed.setOnClickListener(this);
        this.oneSmallSpeed.setOnClickListener(this);
        int i2 = this.speekPosition;
        selectTextColor(i2 == 0, i2 == 1, i2 == 2, i2 == 3, i2 == 4);
    }

    public void selectTextColor(boolean ismallSpeed, boolean isnomalspeed, boolean ionesmallSpeed, boolean ismiddleSpeed, boolean isbigSpeed) {
        this.smallSpeed.setSelected(ismallSpeed);
        this.nomalspeed.setSelected(isnomalspeed);
        this.oneSmallSpeed.setSelected(ionesmallSpeed);
        this.middleSpeed.setSelected(ismiddleSpeed);
        this.bigSpeed.setSelected(isbigSpeed);
    }
}
