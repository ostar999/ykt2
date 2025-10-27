package com.aliyun.player.alivcplayerexpand.view.dot;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.svideo.common.base.BaseDialogFragment;
import com.google.android.material.badge.BadgeDrawable;

/* loaded from: classes2.dex */
public class AlivcDotMsgDialogFragment extends BaseDialogFragment {
    private TextView mDotMsgTextView;
    private DotView mDotView;
    private OnDotViewMsgClickListener mDotViewMsgClickListener;
    private int mLayoutParamsX;
    private int mLayoutParamsY;

    public interface OnDotViewMsgClickListener {
        void onDotViewMsgClick();
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public void bindView(View view) {
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.dot_view_msg_root);
        TextView textView = (TextView) view.findViewById(R.id.tv_dot_msg);
        this.mDotMsgTextView = textView;
        textView.setText(this.mDotView.getDotMsg());
        frameLayout.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.dot.AlivcDotMsgDialogFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (AlivcDotMsgDialogFragment.this.mDotViewMsgClickListener != null) {
                    AlivcDotMsgDialogFragment.this.mDotViewMsgClickListener.onDotViewMsgClick();
                }
            }
        });
    }

    public DotView getDotView() {
        return this.mDotView;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment
    public int getLayoutRes() {
        return R.layout.alivc_long_video_dialogfragment_dot_msg;
    }

    @Override // com.aliyun.svideo.common.base.BaseDialogFragment, androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        final Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            final WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = -2;
            attributes.height = -2;
            attributes.dimAmount = getDimAmount();
            attributes.gravity = BadgeDrawable.BOTTOM_START;
            this.mDotMsgTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.aliyun.player.alivcplayerexpand.view.dot.AlivcDotMsgDialogFragment.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    AlivcDotMsgDialogFragment.this.mDotMsgTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int measuredWidth = AlivcDotMsgDialogFragment.this.mDotMsgTextView.getMeasuredWidth();
                    attributes.x = AlivcDotMsgDialogFragment.this.mLayoutParamsX - (measuredWidth / 2);
                    attributes.y = BaseDialogFragment.getScreenHeight(AlivcDotMsgDialogFragment.this.getContext()) - AlivcDotMsgDialogFragment.this.mLayoutParamsY;
                    window.setAttributes(attributes);
                }
            });
        }
    }

    public void setDotView(DotView dotView) {
        this.mDotView = dotView;
    }

    public void setOnDotViewMsgClickListener(OnDotViewMsgClickListener onDotViewMsgClickListener) {
        this.mDotViewMsgClickListener = onDotViewMsgClickListener;
    }

    public void setX(int i2) {
        this.mLayoutParamsX = i2;
    }

    public void setY(int i2) {
        this.mLayoutParamsY = i2;
    }
}
