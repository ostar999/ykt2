package com.easefun.polyv.livecommon.ui.widget.floating.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class PLVAppFloatingLayout extends PLVAbsFloatingLayout {
    public PLVAppFloatingLayout(@NonNull Context context) {
        super(context);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void destroy() {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public View getContentView() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public Point getFloatLocation() {
        return null;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void hide() {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVAbsFloatingLayout, com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public boolean isShowing() {
        return false;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setContentView(View view) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void show(Activity activity) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void updateFloatLocation(int x2, int y2) {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void updateFloatSize(int width, int height) {
    }

    public PLVAppFloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PLVAppFloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
