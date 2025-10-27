package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import com.easefun.polyv.livecommon.R;
import com.plv.foundationsdk.utils.PLVNetworkUtils;

/* loaded from: classes3.dex */
public class PLVLSNetworkQualityWidget extends FrameLayout {
    private ConstraintLayout plvsClNetworkStatus;
    private Group plvsGroupNetworkStatusCannotConnect;
    private ImageView plvsIvNetworkStatusSignal;
    private int resNetGood;
    private int resNetMiddle;
    private int resNetPoor;
    private boolean shouldShowNoNetworkHint;

    public PLVLSNetworkQualityWidget(@NonNull Context context) {
        this(context, null);
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plvls_status_bar_network_status_widget, (ViewGroup) this, true);
        this.plvsGroupNetworkStatusCannotConnect = (Group) findViewById(R.id.plvs_group_network_status_cannot_connect);
        this.plvsClNetworkStatus = (ConstraintLayout) findViewById(R.id.plvs_cl_network_status);
        this.plvsIvNetworkStatusSignal = (ImageView) findViewById(R.id.plvs_iv_network_status_signal);
    }

    private void showHashNetwork(boolean hasNetwork) {
        if (!hasNetwork) {
            this.plvsIvNetworkStatusSignal.setVisibility(4);
            this.plvsGroupNetworkStatusCannotConnect.setVisibility(0);
            this.plvsGroupNetworkStatusCannotConnect.updatePreLayout(this.plvsClNetworkStatus);
            requestLayout();
            return;
        }
        this.plvsIvNetworkStatusSignal.setVisibility(0);
        if (this.shouldShowNoNetworkHint) {
            this.plvsGroupNetworkStatusCannotConnect.setVisibility(4);
        } else {
            this.plvsGroupNetworkStatusCannotConnect.setVisibility(8);
        }
        this.plvsGroupNetworkStatusCannotConnect.updatePreLayout(this.plvsClNetworkStatus);
        requestLayout();
    }

    public void setNetQuality(int netQuality) {
        if (!PLVNetworkUtils.isConnected(getContext())) {
            netQuality = 14;
        }
        switch (netQuality) {
            case 11:
                showHashNetwork(true);
                this.plvsIvNetworkStatusSignal.setImageResource(this.resNetGood);
                break;
            case 12:
                showHashNetwork(true);
                this.plvsIvNetworkStatusSignal.setImageResource(this.resNetMiddle);
                break;
            case 13:
                showHashNetwork(true);
                this.plvsIvNetworkStatusSignal.setImageResource(this.resNetPoor);
                break;
            case 14:
                if (!this.shouldShowNoNetworkHint) {
                    showHashNetwork(true);
                    this.plvsIvNetworkStatusSignal.setImageResource(this.resNetPoor);
                    break;
                } else {
                    showHashNetwork(false);
                    break;
                }
        }
    }

    public void setNetQualityRes(int resNetGood, int resNetMiddle, int resNetPoor) {
        this.resNetGood = resNetGood;
        this.resNetMiddle = resNetMiddle;
        this.resNetPoor = resNetPoor;
        showHashNetwork(true);
        this.plvsIvNetworkStatusSignal.setImageResource(resNetGood);
    }

    public void shouldShowNoNetworkHint(boolean shouldShowNoNetworkHint) {
        this.shouldShowNoNetworkHint = shouldShowNoNetworkHint;
        if (shouldShowNoNetworkHint) {
            return;
        }
        this.plvsGroupNetworkStatusCannotConnect.setVisibility(8);
        this.plvsGroupNetworkStatusCannotConnect.updatePreLayout(this.plvsClNetworkStatus);
    }

    public PLVLSNetworkQualityWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVLSNetworkQualityWidget(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.shouldShowNoNetworkHint = true;
        this.resNetGood = R.drawable.plv_network_signal_streamer_good;
        this.resNetMiddle = R.drawable.plv_network_signal_streamer_middle;
        this.resNetPoor = R.drawable.plv_network_signal_streamer_poor;
        initView();
    }
}
