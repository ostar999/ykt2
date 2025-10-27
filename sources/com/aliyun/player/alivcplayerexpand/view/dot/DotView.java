package com.aliyun.player.alivcplayerexpand.view.dot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class DotView extends RelativeLayout {
    private String mDotMsg;
    private RelativeLayout mDotViewRoot;
    private OnDotViewClickListener mOnDotViewClickListener;
    private String mTime;

    public interface OnDotViewClickListener {
        void onDotViewClick();
    }

    public DotView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_player_dot_view_layout, (ViewGroup) this, true);
        initView();
        initListener();
    }

    private void initListener() {
        this.mDotViewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.dot.DotView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DotView.this.mOnDotViewClickListener != null) {
                    DotView.this.mOnDotViewClickListener.onDotViewClick();
                }
            }
        });
    }

    private void initView() {
        this.mDotViewRoot = (RelativeLayout) findViewById(R.id.dot_view_root);
    }

    public String getDotMsg() {
        return this.mDotMsg;
    }

    public String getDotTime() {
        return this.mTime;
    }

    public int getRootWidth() {
        return this.mDotViewRoot.getMeasuredWidth();
    }

    public void setDotMsg(String str) {
        this.mDotMsg = str;
    }

    public void setDotTime(String str) {
        this.mTime = str;
    }

    public void setOnDotViewClickListener(OnDotViewClickListener onDotViewClickListener) {
        this.mOnDotViewClickListener = onDotViewClickListener;
    }

    public DotView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public DotView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
