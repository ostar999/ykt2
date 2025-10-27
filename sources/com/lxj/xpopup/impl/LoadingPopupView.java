package com.lxj.xpopup.impl;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import com.lxj.xpopup.R;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class LoadingPopupView extends CenterPopupView {
    private boolean first;
    private CharSequence title;
    private TextView tv_title;

    public LoadingPopupView(@NonNull Context context, int i2) {
        super(context);
        this.first = true;
        this.bindLayoutId = i2;
        addInnerContent();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        int i2 = this.bindLayoutId;
        return i2 != 0 ? i2 : R.layout._xpopup_center_impl_loading;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        getPopupImplView().setElevation(10.0f);
        if (this.bindLayoutId == 0) {
            getPopupImplView().setBackground(XPopupUtils.createDrawable(Color.parseColor("#CF000000"), this.popupInfo.borderRadius));
        }
        setup();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        TextView textView = this.tv_title;
        if (textView == null) {
            return;
        }
        textView.setText("");
        this.tv_title.setVisibility(8);
    }

    public LoadingPopupView setTitle(CharSequence charSequence) {
        this.title = charSequence;
        setup();
        return this;
    }

    public void setup() {
        if (this.tv_title == null) {
            return;
        }
        post(new Runnable() { // from class: com.lxj.xpopup.impl.LoadingPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                TransitionSet transitionSetAddTransition = new TransitionSet().setDuration(LoadingPopupView.this.getAnimationDuration()).addTransition(new Fade());
                if (!LoadingPopupView.this.first) {
                    transitionSetAddTransition.addTransition(new ChangeBounds());
                }
                TransitionManager.beginDelayedTransition(((CenterPopupView) LoadingPopupView.this).centerPopupContainer, transitionSetAddTransition);
                LoadingPopupView.this.first = false;
                if (LoadingPopupView.this.title == null || LoadingPopupView.this.title.length() == 0) {
                    LoadingPopupView.this.tv_title.setVisibility(8);
                } else {
                    LoadingPopupView.this.tv_title.setVisibility(0);
                    LoadingPopupView.this.tv_title.setText(LoadingPopupView.this.title);
                }
            }
        });
    }
}
