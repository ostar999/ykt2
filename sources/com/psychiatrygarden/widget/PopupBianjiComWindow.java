package com.psychiatrygarden.widget;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.interfaceclass.OnClickbianjiListenter;
import com.psychiatrygarden.utils.AnimUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class PopupBianjiComWindow extends BottomPopupView implements View.OnClickListener {
    private TextView bainjifanduishu;
    private TextView bainjineirong;
    private TextView bainjishijian;
    private TextView bainjizantongshu;
    private OnClickbianjiListenter clickListener;
    private LinearLayout lin_window;
    private LinearLayout lineview;
    private Activity mActivity;
    private PopupWindow popupWindow;
    private TextView quxiao;

    public PopupBianjiComWindow(Activity mActivity, final OnClickbianjiListenter clickListener) {
        super(mActivity);
        this.mActivity = mActivity;
        this.clickListener = clickListener;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.activity_bainjiwindow;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        this.lin_window.clearAnimation();
        int id = v2.getId();
        if (id == R.id.lineview) {
            dismiss();
        }
        if (id == R.id.quxiao) {
            dismiss();
            return;
        }
        switch (id) {
            case R.id.bainjifanduishu /* 2131362163 */:
                this.clickListener.mBianjiData(3);
                dismiss();
                break;
            case R.id.bainjineirong /* 2131362164 */:
                this.clickListener.mBianjiData(1);
                dismiss();
                break;
            case R.id.bainjishijian /* 2131362165 */:
                this.clickListener.mBianjiData(0);
                dismiss();
                break;
            case R.id.bainjizantongshu /* 2131362166 */:
                this.clickListener.mBianjiData(2);
                dismiss();
                break;
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.bainjishijian = (TextView) findViewById(R.id.bainjishijian);
        this.bainjineirong = (TextView) findViewById(R.id.bainjineirong);
        this.bainjizantongshu = (TextView) findViewById(R.id.bainjizantongshu);
        this.bainjifanduishu = (TextView) findViewById(R.id.bainjifanduishu);
        this.quxiao = (TextView) findViewById(R.id.quxiao);
        this.lineview = (LinearLayout) findViewById(R.id.lineview);
        this.lin_window = (LinearLayout) findViewById(R.id.lin_window);
        AnimUtil.fromBottomToTopAnim(this.lineview);
        this.bainjishijian.setOnClickListener(this);
        this.bainjineirong.setOnClickListener(this);
        this.bainjizantongshu.setOnClickListener(this);
        this.bainjifanduishu.setOnClickListener(this);
        this.quxiao.setOnClickListener(this);
        this.lineview.setOnClickListener(this);
    }
}
