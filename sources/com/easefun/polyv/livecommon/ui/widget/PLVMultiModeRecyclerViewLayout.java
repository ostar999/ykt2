package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.easefun.polyv.livecommon.R;

/* loaded from: classes3.dex */
public class PLVMultiModeRecyclerViewLayout extends RelativeLayout {
    public static final int MODE_ITEM_FULLSCREEN = 3;
    public static final int MODE_ONE_TO_MORE = 1;
    public static final int MODE_PLACEHOLDER = 2;
    public static final int MODE_TILED = 0;
    private int currentMode;
    private FrameLayout frameLayout;
    private PLVNoInterceptTouchRecyclerView recyclerView;

    public PLVMultiModeRecyclerViewLayout(Context context) {
        this(context, null);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(getContext()).inflate(R.layout.plv_multi_mode_recyclerview_layout, this);
        this.frameLayout = (FrameLayout) findViewById(R.id.plv_frame_layout);
        this.recyclerView = (PLVNoInterceptTouchRecyclerView) findViewById(R.id.plv_recycler_view);
    }

    public void addViewToMain(@NonNull View view, int width, int height) {
        this.frameLayout.addView(view, width, height);
    }

    public void changeMode(int mode) {
        this.currentMode = mode;
    }

    public void clearMainView() {
        this.frameLayout.removeAllViews();
    }

    public int getCurrentMode() {
        return this.currentMode;
    }

    public ViewGroup getMainContainer() {
        return this.frameLayout;
    }

    public PLVNoInterceptTouchRecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public void setMainViewVisibility(int visibility) {
        this.frameLayout.setVisibility(visibility);
    }

    public PLVMultiModeRecyclerViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVMultiModeRecyclerViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.currentMode = 0;
        init(attrs);
    }
}
