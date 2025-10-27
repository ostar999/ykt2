package com.easefun.polyv.livecommon.module.modules.watermark;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.PLVUIUtil;

/* loaded from: classes3.dex */
public class PLVWatermarkView extends FrameLayout implements IPLVWatermarkView {
    private PLVWatermarkTextVO plvWatermarkTextVO;

    public class PLVWatermarkDrawable extends Drawable {
        private static final int CANVAS_COLOR_TRANSPARENT = 0;
        private Paint paint;
        private Paint paintFilter;
        private PLVWatermarkTextVO plvWatermarkTextVO;

        private void initPaint() {
            int iDip2px = PLVUIUtil.dip2px(PLVWatermarkView.this.getContext(), this.plvWatermarkTextVO.getFontSize());
            this.paint.setColor(-16777216);
            float f2 = iDip2px;
            this.paint.setTextSize(f2);
            this.paint.setAlpha(100 - Integer.parseInt(this.plvWatermarkTextVO.getFontAlpha()));
            this.paintFilter.setColor(-1);
            this.paintFilter.setStyle(Paint.Style.STROKE);
            this.paintFilter.setTextSize(f2);
            this.paintFilter.setAlpha(100 - Integer.parseInt(this.plvWatermarkTextVO.getFontAlpha()));
            this.paint.setDither(true);
            this.paint.setAntiAlias(true);
            this.paint.setFilterBitmap(true);
            this.paintFilter.setDither(true);
            this.paintFilter.setAntiAlias(true);
            this.paintFilter.setFilterBitmap(true);
            this.paintFilter.setStrokeWidth(1.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(@NonNull Canvas canvas) {
            int iDip2px = PLVUIUtil.dip2px(PLVWatermarkView.this.getContext(), this.plvWatermarkTextVO.getFontSize());
            int width = PLVWatermarkView.this.getWidth();
            int height = PLVWatermarkView.this.getHeight();
            initPaint();
            int iSqrt = (int) Math.sqrt((width * width) + (height * height));
            float fMeasureText = this.paint.measureText(this.plvWatermarkTextVO.getContent());
            canvas.drawColor(0);
            for (int i2 = (-iSqrt) / 12; i2 <= iSqrt; i2 += iDip2px * 4) {
                for (float f2 = 3.0f; f2 < width; f2 += (iDip2px * 2) + fMeasureText) {
                    float f3 = i2;
                    canvas.rotate(-15.0f, f2, f3);
                    canvas.drawText(this.plvWatermarkTextVO.getContent(), f2, f3, this.paint);
                    canvas.drawText(this.plvWatermarkTextVO.getContent(), f2, f3, this.paintFilter);
                    canvas.rotate(15.0f, f2, f3);
                }
            }
            canvas.save();
            canvas.restore();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
        }

        private PLVWatermarkDrawable() {
            this.paint = new Paint();
            this.paintFilter = new Paint();
        }
    }

    public PLVWatermarkView(Context context) {
        super(context);
        this.plvWatermarkTextVO = new PLVWatermarkTextVO();
        initView(context);
    }

    public void initView(Context context) {
        PLVWatermarkDrawable pLVWatermarkDrawable = new PLVWatermarkDrawable();
        pLVWatermarkDrawable.plvWatermarkTextVO = this.plvWatermarkTextVO;
        View frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        frameLayout.setBackground(pLVWatermarkDrawable);
        addView(frameLayout);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView
    public void removeWatermark() {
        super.removeAllViews();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView
    public void setPLVWatermarkVO(PLVWatermarkTextVO plvWatermarkVO) {
        this.plvWatermarkTextVO = plvWatermarkVO;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.watermark.IPLVWatermarkView
    public void showWatermark() {
        initView(getContext());
    }

    public PLVWatermarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.plvWatermarkTextVO = new PLVWatermarkTextVO();
        initView(context);
    }

    public PLVWatermarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.plvWatermarkTextVO = new PLVWatermarkTextVO();
        initView(context);
    }
}
