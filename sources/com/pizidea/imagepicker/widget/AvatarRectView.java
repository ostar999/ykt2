package com.pizidea.imagepicker.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import com.pizidea.imagepicker.R;

/* loaded from: classes4.dex */
public class AvatarRectView extends View {
    private static final String TAG = "AvatarRectView";
    Bitmap centerBitmap;
    Rect centerRect;
    private int mAvatarSize;
    final Paint mPaint;
    final Rect mRect;
    Rect[] rectArray;

    public AvatarRectView(Context context, int i2) {
        super(context);
        this.mPaint = new Paint();
        this.mRect = new Rect();
        this.mAvatarSize = i2;
        this.rectArray = new Rect[8];
        int i3 = 0;
        while (true) {
            Rect[] rectArr = this.rectArray;
            if (i3 >= rectArr.length) {
                this.centerBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.head_photo_preview_circle_mask);
                this.centerRect = new Rect(0, 0, this.centerBitmap.getWidth(), this.centerBitmap.getHeight());
                return;
            } else {
                rectArr[i3] = new Rect();
                i3++;
            }
        }
    }

    public Rect getCropRect() {
        return this.mRect;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        this.mPaint.setFlags(1);
        this.mRect.left = (getWidth() - this.mAvatarSize) / 2;
        this.mRect.right = (getWidth() + this.mAvatarSize) / 2;
        this.mRect.top = (getHeight() - this.mAvatarSize) / 2;
        this.mRect.bottom = (getHeight() + this.mAvatarSize) / 2;
        Rect rect = this.rectArray[0];
        Rect rect2 = this.mRect;
        rect.set(0, 0, rect2.left, rect2.top);
        Rect rect3 = this.rectArray[1];
        Rect rect4 = this.mRect;
        rect3.set(rect4.left, 0, rect4.right, rect4.top);
        this.rectArray[2].set(this.mRect.right, 0, getWidth(), this.mRect.top);
        Rect rect5 = this.rectArray[3];
        Rect rect6 = this.mRect;
        rect5.set(0, rect6.top, rect6.left, rect6.bottom);
        Rect rect7 = this.rectArray[4];
        Rect rect8 = this.mRect;
        rect7.set(rect8.right, rect8.top, getWidth(), this.mRect.bottom);
        Rect rect9 = this.rectArray[5];
        Rect rect10 = this.mRect;
        rect9.set(0, rect10.bottom, rect10.left, getHeight());
        Rect rect11 = this.rectArray[6];
        Rect rect12 = this.mRect;
        rect11.set(rect12.left, rect12.bottom, rect12.right, getHeight());
        Rect rect13 = this.rectArray[7];
        Rect rect14 = this.mRect;
        rect13.set(rect14.right, rect14.bottom, getWidth(), getHeight());
        this.mPaint.setColor(2130706432);
        this.mPaint.setStyle(Paint.Style.FILL);
        for (Rect rect15 : this.rectArray) {
            canvas.drawRect(rect15, this.mPaint);
        }
        this.mPaint.reset();
        if (this.centerBitmap.isRecycled()) {
            Log.i(TAG, "bitmap recycle");
        } else {
            canvas.drawBitmap(this.centerBitmap, this.centerRect, this.mRect, this.mPaint);
        }
    }
}
