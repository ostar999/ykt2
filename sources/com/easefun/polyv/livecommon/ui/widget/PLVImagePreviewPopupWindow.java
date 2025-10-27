package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import pl.droidsonroids.gif.GifImageView;

/* loaded from: classes3.dex */
public class PLVImagePreviewPopupWindow extends FrameLayout {
    private Context context;
    private GifImageView gifImageView;
    private int maxX;
    private int minX;
    private PopupWindow previewWindow;
    private PLVTriangleIndicateLayout triangleIndicateLayout;

    public PLVImagePreviewPopupWindow(Context context) {
        super(context);
        this.context = context;
        this.minX = ConvertUtils.dp2px(8.0f);
        if (PLVScreenUtils.isLandscape(context)) {
            this.maxX = ScreenUtils.getScreenHeight() - ConvertUtils.dp2px(8.0f);
        } else {
            this.maxX = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(8.0f);
        }
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.plv_emotion_preview_window, (ViewGroup) null);
        this.previewWindow = new PopupWindow(viewInflate, -2, -2);
        this.gifImageView = (GifImageView) viewInflate.findViewById(R.id.plv_gif_image_view);
        this.triangleIndicateLayout = (PLVTriangleIndicateLayout) viewInflate.findViewById(R.id.plv_triangle_layout);
        this.previewWindow.setContentView(viewInflate);
        this.previewWindow.setOutsideTouchable(true);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 1) {
            this.maxX = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(8.0f);
        } else {
            this.maxX = ScreenUtils.getScreenHeight() - ConvertUtils.dp2px(8.0f);
        }
    }

    public void showInTopCenter(String url, View parent) {
        PLVImageLoader.getInstance().loadImage(url, this.gifImageView);
        int[] iArr = new int[2];
        parent.getLocationInWindow(iArr);
        this.previewWindow.getContentView().measure(0, 0);
        PopupWindow popupWindow = this.previewWindow;
        popupWindow.setWidth(popupWindow.getContentView().getMeasuredWidth());
        PopupWindow popupWindow2 = this.previewWindow;
        popupWindow2.setHeight(popupWindow2.getContentView().getMeasuredHeight());
        int width = (iArr[0] + (parent.getWidth() / 2)) - (this.previewWindow.getWidth() / 2);
        int height = iArr[1] - this.previewWindow.getHeight();
        int i2 = this.minX;
        if (width < i2) {
            width = i2;
        } else {
            int width2 = this.previewWindow.getWidth() + width;
            int i3 = this.maxX;
            if (width2 > i3) {
                width = i3 - this.previewWindow.getWidth();
            }
        }
        this.previewWindow.showAtLocation(parent, 0, width, height);
    }
}
