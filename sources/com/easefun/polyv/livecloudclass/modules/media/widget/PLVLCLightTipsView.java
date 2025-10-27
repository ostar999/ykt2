package com.easefun.polyv.livecloudclass.modules.media.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.easefun.polyv.livecloudclass.R;

/* loaded from: classes3.dex */
public class PLVLCLightTipsView extends FrameLayout {
    private Handler handler;
    private TextView tvPercent;
    private View view;

    public PLVLCLightTipsView(Context context) {
        this(context, null);
    }

    private void initView() {
        hide();
        this.tvPercent = (TextView) this.view.findViewById(R.id.tv_percent);
    }

    public void hide() {
        setVisibility(8);
    }

    public void setLightPercent(int i2, boolean z2) {
        this.handler.removeMessages(8);
        if (z2) {
            this.handler.sendEmptyMessageDelayed(8, 300L);
            return;
        }
        setVisibility(0);
        this.tvPercent.setText(i2 + "%");
    }

    public PLVLCLightTipsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLightTipsView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.handler = new Handler() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCLightTipsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 8) {
                    PLVLCLightTipsView.this.setVisibility(8);
                }
            }
        };
        this.view = LayoutInflater.from(context).inflate(R.layout.plvlc_player_light_tips_layout, this);
        initView();
    }
}
