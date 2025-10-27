package com.easefun.polyv.livecommon.module.modules.player.floating;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Space;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.PLVSwitchViewAnchorLayout;

/* loaded from: classes3.dex */
public class PLVFloatingPlayerView extends FrameLayout {
    private ImageView floatingCloseIv;
    private PLVSwitchViewAnchorLayout floatingContentSwitchAnchorLayout;
    private Space floatingPlaceholderSpace;

    public PLVFloatingPlayerView(@NonNull Context context) {
        super(context);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_floating_content_layout, this);
        this.floatingContentSwitchAnchorLayout = (PLVSwitchViewAnchorLayout) findViewById(R.id.plv_floating_content_switch_anchor_layout);
        this.floatingPlaceholderSpace = (Space) findViewById(R.id.plv_floating_placeholder_space);
        this.floatingCloseIv = (ImageView) findViewById(R.id.plv_floating_close_iv);
    }

    public PLVSwitchViewAnchorLayout getAnchorLayout() {
        return this.floatingContentSwitchAnchorLayout;
    }

    @Nullable
    public PLVSwitchViewAnchorLayout getPlaceholderParentAnchorLayout() {
        if (this.floatingPlaceholderSpace.getParent() instanceof PLVSwitchViewAnchorLayout) {
            return (PLVSwitchViewAnchorLayout) this.floatingPlaceholderSpace.getParent();
        }
        return null;
    }

    public PLVFloatingPlayerView setOnClickCloseListener(final View.OnClickListener onClickCloseListener) {
        this.floatingCloseIv.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                View.OnClickListener onClickListener = onClickCloseListener;
                if (onClickListener != null) {
                    onClickListener.onClick(v2);
                }
            }
        });
        return this;
    }

    public PLVFloatingPlayerView setOnClickGoBackListener(final View.OnClickListener onClickGoBackListener) {
        this.floatingContentSwitchAnchorLayout.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.module.modules.player.floating.PLVFloatingPlayerView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                View.OnClickListener onClickListener = onClickGoBackListener;
                if (onClickListener != null) {
                    onClickListener.onClick(v2);
                }
            }
        });
        return this;
    }
}
