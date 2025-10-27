package com.easefun.polyv.livecloudclass.modules.linkmic.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.ui.widget.PLVNoConsumeTouchEventButton;

/* loaded from: classes3.dex */
public class PLVLCLinkMicRingButton extends PLVNoConsumeTouchEventButton {
    private static final int STATE_RING_OFF = 2;
    private static final int STATE_RING_SETTING = 3;
    private static final int STATE_RING_UP = 0;
    private OnPLVLCLinkMicRingButtonClickListener onPLVLCLinkMicRingButtonClickListener;
    private int state;

    public interface OnPLVLCLinkMicRingButtonClickListener {
        void onClickRingOff();

        void onClickRingSetting();

        void onClickRingUp();
    }

    public PLVLCLinkMicRingButton(Context context) {
        this(context, null);
    }

    private void initView() {
        setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.linkmic.widget.PLVLCLinkMicRingButton.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (PLVLCLinkMicRingButton.this.onPLVLCLinkMicRingButtonClickListener != null) {
                    int i2 = PLVLCLinkMicRingButton.this.state;
                    if (i2 == 0) {
                        PLVLCLinkMicRingButton.this.onPLVLCLinkMicRingButtonClickListener.onClickRingUp();
                    } else if (i2 == 2) {
                        PLVLCLinkMicRingButton.this.onPLVLCLinkMicRingButtonClickListener.onClickRingOff();
                    } else {
                        if (i2 != 3) {
                            return;
                        }
                        PLVLCLinkMicRingButton.this.onPLVLCLinkMicRingButtonClickListener.onClickRingSetting();
                    }
                }
            }
        });
    }

    public void setOnLinkMicRingButtonClickListener(OnPLVLCLinkMicRingButtonClickListener onPLVLCLinkMicRingButtonClickListener) {
        this.onPLVLCLinkMicRingButtonClickListener = onPLVLCLinkMicRingButtonClickListener;
    }

    public void setRingOffState() {
        this.state = 2;
        setBackgroundResource(R.drawable.plvlc_linkmic_iv_ring_off);
    }

    public void setRingSettingState() {
        this.state = 3;
        setBackgroundResource(R.drawable.plvlc_linkmic_ring_setting);
    }

    public void setRingUpState() {
        this.state = 0;
        setBackgroundResource(R.drawable.plvlc_linkmic_iv_ring_up);
    }

    public PLVLCLinkMicRingButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLinkMicRingButton(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        initView();
    }
}
