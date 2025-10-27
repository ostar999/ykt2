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
import com.plv.foundationsdk.utils.PLVTimeUtils;

/* loaded from: classes3.dex */
public class PLVLCProgressTipsView extends FrameLayout {
    private Handler handler;
    private TextView tvProgress;
    private View view;

    public PLVLCProgressTipsView(Context context) {
        this(context, null);
    }

    private void initView() {
        hide();
        this.tvProgress = (TextView) this.view.findViewById(R.id.tv_progress);
    }

    public void delayHide() {
        this.handler.removeMessages(8);
        this.handler.sendEmptyMessageDelayed(8, 300L);
    }

    public void hide() {
        setVisibility(8);
    }

    public void setProgressPercent(int i2, int i3, boolean z2, boolean z3) {
        this.handler.removeMessages(8);
        if (z2) {
            this.handler.sendEmptyMessageDelayed(8, 300L);
            return;
        }
        setVisibility(0);
        if (i2 < 0) {
            i2 = 0;
        }
        if (i2 > i3) {
            i2 = i3;
        }
        this.tvProgress.setText(PLVTimeUtils.generateTime(i2, true) + "/" + PLVTimeUtils.generateTime(i3, true));
    }

    public PLVLCProgressTipsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCProgressTipsView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.handler = new Handler() { // from class: com.easefun.polyv.livecloudclass.modules.media.widget.PLVLCProgressTipsView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 8) {
                    PLVLCProgressTipsView.this.setVisibility(8);
                }
            }
        };
        this.view = LayoutInflater.from(context).inflate(R.layout.plvlc_tips_view_progress, this);
        initView();
    }
}
