package com.uuzuche.lib_zxing.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.google.zxing.ResultPoint;
import com.uuzuche.lib_zxing.R;
import com.uuzuche.lib_zxing.camera.CameraManager;
import java.util.Collection;
import java.util.HashSet;

/* loaded from: classes6.dex */
public final class ViewfinderView extends View {
    private static final long ANIMATION_DELAY = 100;
    private static final int OPAQUE = 255;
    private int SCAN_VELOCITY;
    private Bitmap bitmap;
    private int innercornercolor;
    private int innercornerlength;
    private int innercornerwidth;
    private boolean isCircle;
    private Collection<ResultPoint> lastPossibleResultPoints;
    private final Paint mDotLinePaint;
    private final int maskColor;
    private final Paint paint;
    private Collection<ResultPoint> possibleResultPoints;
    private Bitmap resultBitmap;
    private final int resultColor;
    private final int resultPointColor;
    private Bitmap scanLight;
    private int scanLineTop;
    private final Paint textPaint;

    public ViewfinderView(Context context) {
        this(context, null);
    }

    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void drawFrameBounds(Canvas canvas, Rect rect) {
        this.paint.setColor(this.innercornercolor);
        this.paint.setStyle(Paint.Style.FILL);
        int i2 = this.innercornerwidth;
        int i3 = this.innercornerlength;
        canvas.drawRect(rect.left, rect.top, r2 + i2, r3 + i3, this.paint);
        canvas.drawRect(rect.left, rect.top, r2 + i3, r3 + i2, this.paint);
        int i4 = rect.right;
        canvas.drawRect(i4 - i2, rect.top, i4, r3 + i3, this.paint);
        int i5 = rect.right;
        canvas.drawRect(i5 - i3, rect.top, i5, r3 + i2, this.paint);
        canvas.drawRect(rect.left, r3 - i3, r2 + i2, rect.bottom, this.paint);
        canvas.drawRect(rect.left, r3 - i2, r2 + i3, rect.bottom, this.paint);
        canvas.drawRect(r2 - i2, r3 - i3, rect.right, rect.bottom, this.paint);
        canvas.drawRect(r2 - i3, r12 - i2, rect.right, rect.bottom, this.paint);
    }

    private void drawScanLight(Canvas canvas, Rect rect) {
        if (this.scanLineTop == 0) {
            this.scanLineTop = rect.top;
        }
        int i2 = this.scanLineTop;
        if (i2 >= rect.bottom - 30) {
            this.scanLineTop = rect.top;
        } else {
            this.scanLineTop = i2 + this.SCAN_VELOCITY;
        }
        int i3 = rect.left;
        int i4 = this.scanLineTop;
        canvas.drawBitmap(this.scanLight, (Rect) null, new Rect(i3, i4, rect.right, i4 + 30), this.paint);
    }

    private void initInnerRect(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewfinderView);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_inner_margintop, -1.0f);
        if (dimension != -1.0f) {
            CameraManager.FRAME_MARGINTOP = (int) dimension;
        }
        float fApplyDimension = TypedValue.applyDimension(1, 280.0f, getResources().getDisplayMetrics());
        CameraManager.FRAME_WIDTH = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_inner_width, fApplyDimension);
        CameraManager.FRAME_HEIGHT = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_inner_height, fApplyDimension);
        this.innercornercolor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_inner_corner_color, -1);
        this.innercornerlength = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_inner_corner_length, 50.0f);
        this.innercornerwidth = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_inner_corner_width, 10.0f);
        int i2 = R.styleable.ViewfinderView_inner_scan_bitmap;
        typedArrayObtainStyledAttributes.getDrawable(i2);
        this.scanLight = BitmapFactory.decodeResource(getResources(), typedArrayObtainStyledAttributes.getResourceId(i2, R.drawable.qr_scan_line));
        this.SCAN_VELOCITY = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_inner_scan_speed, 10);
        this.isCircle = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ViewfinderView_inner_scan_iscircle, true);
        typedArrayObtainStyledAttributes.recycle();
        GradientDrawable gradientDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.bg_dot_line);
        if (gradientDrawable != null) {
            int i3 = CameraManager.FRAME_WIDTH;
            int i4 = CameraManager.FRAME_HEIGHT;
            this.bitmap = Bitmap.createBitmap(i3, i4, gradientDrawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(this.bitmap);
            gradientDrawable.setBounds(0, 0, i3, i4);
            gradientDrawable.draw(canvas);
        }
    }

    public void addPossibleResultPoint(ResultPoint resultPoint) {
        this.possibleResultPoints.add(resultPoint);
    }

    public void drawViewfinder() {
        this.resultBitmap = null;
        invalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Rect framingRect = CameraManager.get().getFramingRect();
        if (framingRect == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
        float f2 = width;
        canvas.drawRect(0.0f, 0.0f, f2, framingRect.top, this.paint);
        canvas.drawRect(0.0f, framingRect.top, framingRect.left, framingRect.bottom + 1, this.paint);
        canvas.drawRect(framingRect.right + 1, framingRect.top, f2, framingRect.bottom + 1, this.paint);
        canvas.drawRect(0.0f, framingRect.bottom + 1, f2, height, this.paint);
        if (this.resultBitmap != null) {
            this.paint.setAlpha(255);
            canvas.drawBitmap(this.resultBitmap, framingRect.left, framingRect.top, this.paint);
        } else {
            drawFrameBounds(canvas, framingRect);
            drawScanLight(canvas, framingRect);
            Collection<ResultPoint> collection = this.possibleResultPoints;
            Collection<ResultPoint> collection2 = this.lastPossibleResultPoints;
            if (collection.isEmpty()) {
                this.lastPossibleResultPoints = null;
            } else {
                this.possibleResultPoints = new HashSet(5);
                this.lastPossibleResultPoints = collection;
                this.paint.setAlpha(255);
                this.paint.setColor(this.resultPointColor);
                if (this.isCircle) {
                    for (ResultPoint resultPoint : collection) {
                        canvas.drawCircle(framingRect.left + resultPoint.getX(), framingRect.top + resultPoint.getY(), 6.0f, this.paint);
                    }
                }
            }
            if (collection2 != null) {
                this.paint.setAlpha(127);
                this.paint.setColor(this.resultPointColor);
                if (this.isCircle) {
                    for (ResultPoint resultPoint2 : collection2) {
                        canvas.drawCircle(framingRect.left + resultPoint2.getX(), framingRect.top + resultPoint2.getY(), 3.0f, this.paint);
                    }
                }
            }
            postInvalidateDelayed(100L, framingRect.left, framingRect.top, framingRect.right, framingRect.bottom);
        }
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, framingRect.left, framingRect.top, this.mDotLinePaint);
        }
    }

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public ViewfinderView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.bitmap = null;
        this.paint = new Paint();
        this.mDotLinePaint = new Paint();
        TextPaint textPaint = new TextPaint();
        this.textPaint = textPaint;
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(TypedValue.applyDimension(2, 14.0f, context.getResources().getDisplayMetrics()));
        Resources resources = getResources();
        this.maskColor = resources.getColor(R.color.viewfinder_mask);
        this.resultColor = resources.getColor(R.color.result_view);
        this.resultPointColor = resources.getColor(R.color.possible_result_points);
        this.possibleResultPoints = new HashSet(5);
        this.scanLight = BitmapFactory.decodeResource(resources, R.drawable.qr_scan_line);
        initInnerRect(context, attributeSet);
    }
}
