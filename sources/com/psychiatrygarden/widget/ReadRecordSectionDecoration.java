package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ReadRecordSectionDecoration extends RecyclerView.ItemDecoration {
    private Paint bgPaint;
    private DecorationCallback callback;
    private float cornerRadius;
    private Paint.FontMetrics fontMetrics;
    private Paint paint;
    private TextPaint textPaint;
    private int topGap;

    public interface DecorationCallback {
        String getGroupFirstLine(int position);

        long getGroupId(int position);
    }

    public ReadRecordSectionDecoration(Context context, DecorationCallback callback) {
        this.callback = callback;
        init(context);
    }

    private void init(Context context) {
        this.bgPaint = new Paint();
        this.paint = new Paint();
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.app_bg, R.attr.new_bg_two_color});
        this.bgPaint.setColor(typedArrayObtainStyledAttributes.getColor(1, Color.parseColor("#F9FAFB")));
        this.paint.setColor(typedArrayObtainStyledAttributes.getColor(0, -1));
        this.bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        TextPaint textPaint = new TextPaint();
        this.textPaint = textPaint;
        textPaint.setAntiAlias(true);
        this.textPaint.setColor(context.getResources().getColor(R.color.first_txt_color, context.getTheme()));
        if (SkinManager.getCurrentSkinType(context) == 1) {
            this.textPaint.setColor(context.getResources().getColor(R.color.first_txt_color_night, context.getTheme()));
        }
        this.textPaint.setTextSize(CommonUtil.sp2px(context, 12.0f));
        this.textPaint.setTextAlign(Paint.Align.LEFT);
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        this.fontMetrics = fontMetrics;
        this.textPaint.getFontMetrics(fontMetrics);
        typedArrayObtainStyledAttributes.recycle();
        this.topGap = (int) TypedValue.applyDimension(1, 24.0f, context.getResources().getDisplayMetrics());
        this.cornerRadius = CommonUtil.dip2px(context, 4.0f);
    }

    private boolean isFirstInGroup(int pos) {
        return pos == 0 || this.callback.getGroupId(pos + (-1)) != this.callback.getGroupId(pos);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        long groupId = this.callback.getGroupId(childAdapterPosition);
        if (groupId < 0) {
            return;
        }
        if (groupId == 0 || isFirstInGroup(childAdapterPosition)) {
            outRect.top = this.topGap;
        } else {
            outRect.top = 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007e  */
    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onDrawOver(@androidx.annotation.NonNull android.graphics.Canvas r19, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView r20, @androidx.annotation.NonNull androidx.recyclerview.widget.RecyclerView.State r21) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            super.onDrawOver(r19, r20, r21)
            int r2 = r21.getItemCount()
            int r3 = r20.getChildCount()
            int r4 = r20.getPaddingLeft()
            float r4 = (float) r4
            int r5 = r20.getWidth()
            int r6 = r20.getPaddingRight()
            int r5 = r5 - r6
            float r13 = (float) r5
            r5 = -1
            r7 = 0
        L21:
            if (r7 >= r3) goto Ld5
            android.view.View r8 = r1.getChildAt(r7)
            int r9 = r1.getChildAdapterPosition(r8)
            int r14 = r7 + 1
            java.lang.String r7 = java.lang.String.valueOf(r14)
            java.lang.String r10 = "onDrawOver"
            com.psychiatrygarden.utils.LogUtils.d(r10, r7)
            com.psychiatrygarden.widget.ReadRecordSectionDecoration$DecorationCallback r7 = r0.callback
            long r15 = r7.getGroupId(r9)
            r10 = 0
            int r7 = (r15 > r10 ? 1 : (r15 == r10 ? 0 : -1))
            if (r7 < 0) goto Ld1
            int r5 = (r5 > r15 ? 1 : (r5 == r15 ? 0 : -1))
            if (r5 != 0) goto L48
            goto Ld1
        L48:
            com.psychiatrygarden.widget.ReadRecordSectionDecoration$DecorationCallback r5 = r0.callback
            java.lang.String r5 = r5.getGroupFirstLine(r9)
            java.lang.String r11 = r5.toUpperCase()
            boolean r5 = android.text.TextUtils.isEmpty(r11)
            if (r5 == 0) goto L5a
            goto Ld1
        L5a:
            int r5 = r8.getBottom()
            int r6 = r0.topGap
            int r7 = r8.getTop()
            int r6 = java.lang.Math.max(r6, r7)
            float r6 = (float) r6
            int r9 = r9 + 1
            if (r9 >= r2) goto L7e
            com.psychiatrygarden.widget.ReadRecordSectionDecoration$DecorationCallback r7 = r0.callback
            long r7 = r7.getGroupId(r9)
            int r7 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r7 == 0) goto L7e
            float r5 = (float) r5
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 >= 0) goto L7e
            r12 = r5
            goto L7f
        L7e:
            r12 = r6
        L7f:
            int r5 = r0.topGap
            float r5 = (float) r5
            float r7 = r12 - r5
            android.graphics.Paint r10 = r0.paint
            r5 = r19
            r6 = r4
            r8 = r13
            r9 = r12
            r5.drawRect(r6, r7, r8, r9, r10)
            android.content.Context r5 = r20.getContext()
            r6 = 1082130432(0x40800000, float:4.0)
            int r5 = com.psychiatrygarden.utils.CommonUtil.dip2px(r5, r6)
            float r5 = (float) r5
            android.content.Context r6 = r20.getContext()
            r7 = 1098907648(0x41800000, float:16.0)
            com.psychiatrygarden.utils.CommonUtil.dip2px(r6, r7)
            android.text.TextPaint r6 = r0.textPaint
            r6.measureText(r11)
            android.content.Context r6 = r20.getContext()
            r7 = 1090519040(0x41000000, float:8.0)
            int r6 = com.psychiatrygarden.utils.CommonUtil.dip2px(r6, r7)
            float r6 = (float) r6
            float r6 = r6 + r4
            float r5 = r12 - r5
            android.text.TextPaint r7 = r0.textPaint
            r10 = r19
            r10.drawText(r11, r6, r5, r7)
            int r5 = r0.topGap
            float r5 = (float) r5
            float r7 = r12 - r5
            float r11 = r0.cornerRadius
            android.graphics.Paint r9 = r0.bgPaint
            r5 = r19
            r6 = r4
            r17 = r9
            r9 = r12
            r10 = r11
            r12 = r17
            r5.drawRoundRect(r6, r7, r8, r9, r10, r11, r12)
        Ld1:
            r7 = r14
            r5 = r15
            goto L21
        Ld5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.ReadRecordSectionDecoration.onDrawOver(android.graphics.Canvas, androidx.recyclerview.widget.RecyclerView, androidx.recyclerview.widget.RecyclerView$State):void");
    }
}
