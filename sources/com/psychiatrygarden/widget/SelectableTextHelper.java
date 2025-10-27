package com.psychiatrygarden.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SelectableTextHelper {
    private static final int DEFAULT_SELECTION_LENGTH = 1;
    private static final int DEFAULT_SHOW_DURATION = 100;
    private boolean isHideWhenScroll;
    private Context mContext;
    private int mCursorHandleColor;
    private int mCursorHandleSize;
    private CursorHandle mEndHandle;
    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private OperateWindow mOperateWindow;
    private OnSelectListener mSelectListener;
    private int mSelectedColor;
    private BackgroundColorSpan mSpan;
    private Spannable mSpannable;
    private CursorHandle mStartHandle;
    private TextView mTextView;
    private int mTouchX;
    private int mTouchY;
    private SelectionInfo mSelectionInfo = new SelectionInfo();
    private boolean isHide = true;
    private final Runnable mShowSelectViewRunnable = new Runnable() { // from class: com.psychiatrygarden.widget.SelectableTextHelper.2
        @Override // java.lang.Runnable
        public void run() {
            if (SelectableTextHelper.this.isHide) {
                return;
            }
            if (SelectableTextHelper.this.mOperateWindow != null) {
                SelectableTextHelper.this.mOperateWindow.show();
            }
            if (SelectableTextHelper.this.mStartHandle != null) {
                SelectableTextHelper selectableTextHelper = SelectableTextHelper.this;
                selectableTextHelper.showCursorHandle(selectableTextHelper.mStartHandle);
            }
            if (SelectableTextHelper.this.mEndHandle != null) {
                SelectableTextHelper selectableTextHelper2 = SelectableTextHelper.this;
                selectableTextHelper2.showCursorHandle(selectableTextHelper2.mEndHandle);
            }
        }
    };

    public static class Builder {
        private TextView mTextView;
        private int mCursorHandleColor = -15500842;
        private int mSelectedColor = -5250572;
        private float mCursorHandleSizeInDp = 24.0f;

        public Builder(TextView textView) {
            this.mTextView = textView;
        }

        public SelectableTextHelper build() {
            return new SelectableTextHelper(this);
        }

        public Builder setCursorHandleColor(@ColorInt int cursorHandleColor) {
            this.mCursorHandleColor = cursorHandleColor;
            return this;
        }

        public Builder setCursorHandleSizeInDp(float cursorHandleSizeInDp) {
            this.mCursorHandleSizeInDp = cursorHandleSizeInDp;
            return this;
        }

        public Builder setSelectedColor(@ColorInt int selectedBgColor) {
            this.mSelectedColor = selectedBgColor;
            return this;
        }
    }

    public class CursorHandle extends View {
        private boolean isLeft;
        private int mAdjustX;
        private int mAdjustY;
        private int mBeforeDragEnd;
        private int mBeforeDragStart;
        private int mCircleRadius;
        private int mHeight;
        private int mPadding;
        private Paint mPaint;
        private PopupWindow mPopupWindow;
        private int[] mTempCoors;
        private int mWidth;

        public CursorHandle(boolean isLeft) {
            super(SelectableTextHelper.this.mContext);
            int i2 = SelectableTextHelper.this.mCursorHandleSize / 2;
            this.mCircleRadius = i2;
            this.mWidth = i2 * 2;
            this.mHeight = i2 * 2;
            this.mPadding = 25;
            this.mTempCoors = new int[2];
            this.isLeft = isLeft;
            Paint paint = new Paint(1);
            this.mPaint = paint;
            paint.setColor(SelectableTextHelper.this.mCursorHandleColor);
            PopupWindow popupWindow = new PopupWindow(this);
            this.mPopupWindow = popupWindow;
            popupWindow.setClippingEnabled(false);
            this.mPopupWindow.setWidth(this.mWidth + (this.mPadding * 2));
            this.mPopupWindow.setHeight(this.mHeight + (this.mPadding / 2));
            invalidate();
        }

        private void changeDirection() {
            this.isLeft = !this.isLeft;
            invalidate();
        }

        private void updateCursorHandle() {
            SelectableTextHelper.this.mTextView.getLocationInWindow(this.mTempCoors);
            Layout layout = SelectableTextHelper.this.mTextView.getLayout();
            if (this.isLeft) {
                this.mPopupWindow.update((((int) layout.getPrimaryHorizontal(SelectableTextHelper.this.mSelectionInfo.mStart)) - this.mWidth) + getExtraX(), layout.getLineBottom(layout.getLineForOffset(SelectableTextHelper.this.mSelectionInfo.mStart)) + getExtraY(), -1, -1);
            } else {
                this.mPopupWindow.update(((int) layout.getPrimaryHorizontal(SelectableTextHelper.this.mSelectionInfo.mEnd)) + getExtraX(), layout.getLineBottom(layout.getLineForOffset(SelectableTextHelper.this.mSelectionInfo.mEnd)) + getExtraY(), -1, -1);
            }
        }

        public void dismiss() {
            this.mPopupWindow.dismiss();
        }

        public int getExtraX() {
            return (this.mTempCoors[0] - this.mPadding) + SelectableTextHelper.this.mTextView.getPaddingLeft();
        }

        public int getExtraY() {
            return this.mTempCoors[1] + SelectableTextHelper.this.mTextView.getPaddingTop();
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            int i2 = this.mCircleRadius;
            canvas.drawCircle(this.mPadding + i2, i2, i2, this.mPaint);
            if (this.isLeft) {
                int i3 = this.mCircleRadius;
                int i4 = this.mPadding;
                canvas.drawRect(i3 + i4, 0.0f, (i3 * 2) + i4, i3, this.mPaint);
            } else {
                canvas.drawRect(this.mPadding, 0.0f, r0 + r1, this.mCircleRadius, this.mPaint);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x003b  */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onTouchEvent(android.view.MotionEvent r4) {
            /*
                r3 = this;
                int r0 = r4.getAction()
                r1 = 1
                if (r0 == 0) goto L4d
                if (r0 == r1) goto L3b
                r2 = 2
                if (r0 == r2) goto L10
                r4 = 3
                if (r0 == r4) goto L3b
                goto L6f
            L10:
                com.psychiatrygarden.widget.SelectableTextHelper r0 = com.psychiatrygarden.widget.SelectableTextHelper.this
                com.psychiatrygarden.widget.SelectableTextHelper$OperateWindow r0 = com.psychiatrygarden.widget.SelectableTextHelper.access$500(r0)
                if (r0 == 0) goto L21
                com.psychiatrygarden.widget.SelectableTextHelper r0 = com.psychiatrygarden.widget.SelectableTextHelper.this
                com.psychiatrygarden.widget.SelectableTextHelper$OperateWindow r0 = com.psychiatrygarden.widget.SelectableTextHelper.access$500(r0)
                r0.dismiss()
            L21:
                float r0 = r4.getRawX()
                int r0 = (int) r0
                float r4 = r4.getRawY()
                int r4 = (int) r4
                int r2 = r3.mAdjustX
                int r0 = r0 + r2
                int r2 = r3.mWidth
                int r0 = r0 - r2
                int r2 = r3.mAdjustY
                int r4 = r4 + r2
                int r2 = r3.mHeight
                int r4 = r4 - r2
                r3.update(r0, r4)
                goto L6f
            L3b:
                com.psychiatrygarden.widget.SelectableTextHelper r4 = com.psychiatrygarden.widget.SelectableTextHelper.this
                com.psychiatrygarden.widget.SelectableTextHelper$OperateWindow r4 = com.psychiatrygarden.widget.SelectableTextHelper.access$500(r4)
                if (r4 == 0) goto L6f
                com.psychiatrygarden.widget.SelectableTextHelper r4 = com.psychiatrygarden.widget.SelectableTextHelper.this
                com.psychiatrygarden.widget.SelectableTextHelper$OperateWindow r4 = com.psychiatrygarden.widget.SelectableTextHelper.access$500(r4)
                r4.show()
                goto L6f
            L4d:
                com.psychiatrygarden.widget.SelectableTextHelper r0 = com.psychiatrygarden.widget.SelectableTextHelper.this
                com.psychiatrygarden.widget.SelectionInfo r0 = com.psychiatrygarden.widget.SelectableTextHelper.access$1100(r0)
                int r0 = r0.mStart
                r3.mBeforeDragStart = r0
                com.psychiatrygarden.widget.SelectableTextHelper r0 = com.psychiatrygarden.widget.SelectableTextHelper.this
                com.psychiatrygarden.widget.SelectionInfo r0 = com.psychiatrygarden.widget.SelectableTextHelper.access$1100(r0)
                int r0 = r0.mEnd
                r3.mBeforeDragEnd = r0
                float r0 = r4.getX()
                int r0 = (int) r0
                r3.mAdjustX = r0
                float r4 = r4.getY()
                int r4 = (int) r4
                r3.mAdjustY = r4
            L6f:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.SelectableTextHelper.CursorHandle.onTouchEvent(android.view.MotionEvent):boolean");
        }

        public void show(int x2, int y2) {
            SelectableTextHelper.this.mTextView.getLocationInWindow(this.mTempCoors);
            this.mPopupWindow.showAtLocation(SelectableTextHelper.this.mTextView, 0, (x2 - (this.isLeft ? this.mWidth : 0)) + getExtraX(), y2 + getExtraY());
        }

        public void update(int x2, int y2) {
            SelectableTextHelper.this.mTextView.getLocationInWindow(this.mTempCoors);
            int i2 = this.isLeft ? SelectableTextHelper.this.mSelectionInfo.mStart : SelectableTextHelper.this.mSelectionInfo.mEnd;
            int hysteresisOffset = TextLayoutUtil.getHysteresisOffset(SelectableTextHelper.this.mTextView, x2, y2 - this.mTempCoors[1], i2);
            if (hysteresisOffset != i2) {
                SelectableTextHelper.this.resetSelectionInfo();
                if (this.isLeft) {
                    if (hysteresisOffset > this.mBeforeDragEnd) {
                        CursorHandle cursorHandle = SelectableTextHelper.this.getCursorHandle(false);
                        changeDirection();
                        cursorHandle.changeDirection();
                        int i3 = this.mBeforeDragEnd;
                        this.mBeforeDragStart = i3;
                        SelectableTextHelper.this.selectText(i3, hysteresisOffset);
                        cursorHandle.updateCursorHandle();
                    } else {
                        SelectableTextHelper.this.selectText(hysteresisOffset, -1);
                    }
                    updateCursorHandle();
                    return;
                }
                int i4 = this.mBeforeDragStart;
                if (hysteresisOffset < i4) {
                    CursorHandle cursorHandle2 = SelectableTextHelper.this.getCursorHandle(true);
                    cursorHandle2.changeDirection();
                    changeDirection();
                    int i5 = this.mBeforeDragStart;
                    this.mBeforeDragEnd = i5;
                    SelectableTextHelper.this.selectText(hysteresisOffset, i5);
                    cursorHandle2.updateCursorHandle();
                } else {
                    SelectableTextHelper.this.selectText(i4, hysteresisOffset);
                }
                updateCursorHandle();
            }
        }
    }

    public class OperateWindow {
        private int mHeight;
        private int[] mTempCoors = new int[2];
        private int mWidth;
        private PopupWindow mWindow;

        public OperateWindow(final Context context) {
            View viewInflate = LayoutInflater.from(context).inflate(R.layout.layout_operate_windows, (ViewGroup) null);
            viewInflate.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            this.mWidth = viewInflate.getMeasuredWidth();
            this.mHeight = viewInflate.getMeasuredHeight();
            PopupWindow popupWindow = new PopupWindow(viewInflate, -2, -2, false);
            this.mWindow = popupWindow;
            popupWindow.setClippingEnabled(false);
            viewInflate.findViewById(R.id.tv_copy).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.eh
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16444c.lambda$new$0(view);
                }
            });
            viewInflate.findViewById(R.id.tv_select_all).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.fh
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16492c.lambda$new$1(view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            try {
                ((ClipboardManager) SelectableTextHelper.this.mContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(SelectableTextHelper.this.mSelectionInfo.mSelectionContent, SelectableTextHelper.this.mSelectionInfo.mSelectionContent));
                if (SelectableTextHelper.this.mSelectListener != null) {
                    SelectableTextHelper.this.mSelectListener.onTextSelected(SelectableTextHelper.this.mSelectionInfo.mSelectionContent);
                }
                SelectableTextHelper.this.resetSelectionInfo();
                SelectableTextHelper.this.hideSelectView();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            SelectableTextHelper.this.hideSelectView();
            SelectableTextHelper selectableTextHelper = SelectableTextHelper.this;
            selectableTextHelper.selectText(0, selectableTextHelper.mTextView.getText().length());
            SelectableTextHelper.this.isHide = false;
            SelectableTextHelper selectableTextHelper2 = SelectableTextHelper.this;
            selectableTextHelper2.showCursorHandle(selectableTextHelper2.mStartHandle);
            SelectableTextHelper selectableTextHelper3 = SelectableTextHelper.this;
            selectableTextHelper3.showCursorHandle(selectableTextHelper3.mEndHandle);
            if (SelectableTextHelper.this.mOperateWindow != null) {
                SelectableTextHelper.this.mOperateWindow.show();
            }
        }

        public void dismiss() {
            this.mWindow.dismiss();
        }

        public boolean isShowing() {
            return this.mWindow.isShowing();
        }

        public void show() {
            SelectableTextHelper.this.mTextView.getLocationInWindow(this.mTempCoors);
            Layout layout = SelectableTextHelper.this.mTextView.getLayout();
            int primaryHorizontal = ((int) layout.getPrimaryHorizontal(SelectableTextHelper.this.mSelectionInfo.mStart)) + this.mTempCoors[0];
            int lineTop = ((layout.getLineTop(layout.getLineForOffset(SelectableTextHelper.this.mSelectionInfo.mStart)) + this.mTempCoors[1]) - this.mHeight) - 16;
            if (primaryHorizontal <= 0) {
                primaryHorizontal = 16;
            }
            if (lineTop < 0) {
                lineTop = 16;
            }
            if (this.mWidth + primaryHorizontal > TextLayoutUtil.getScreenWidth(SelectableTextHelper.this.mContext)) {
                primaryHorizontal = (TextLayoutUtil.getScreenWidth(SelectableTextHelper.this.mContext) - this.mWidth) - 16;
            }
            this.mWindow.setElevation(8.0f);
            this.mWindow.showAtLocation(SelectableTextHelper.this.mTextView, 0, primaryHorizontal, lineTop);
        }
    }

    public SelectableTextHelper(Builder builder) {
        TextView textView = builder.mTextView;
        this.mTextView = textView;
        this.mContext = textView.getContext();
        this.mSelectedColor = builder.mSelectedColor;
        this.mCursorHandleColor = builder.mCursorHandleColor;
        this.mCursorHandleSize = TextLayoutUtil.dp2px(this.mContext, builder.mCursorHandleSizeInDp);
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CursorHandle getCursorHandle(boolean isLeft) {
        return this.mStartHandle.isLeft == isLeft ? this.mStartHandle : this.mEndHandle;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideSelectView() {
        this.isHide = true;
        CursorHandle cursorHandle = this.mStartHandle;
        if (cursorHandle != null) {
            cursorHandle.dismiss();
        }
        CursorHandle cursorHandle2 = this.mEndHandle;
        if (cursorHandle2 != null) {
            cursorHandle2.dismiss();
        }
        OperateWindow operateWindow = this.mOperateWindow;
        if (operateWindow != null) {
            operateWindow.dismiss();
        }
    }

    private void init() {
        TextView textView = this.mTextView;
        textView.setText(textView.getText(), TextView.BufferType.SPANNABLE);
        this.mTextView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.psychiatrygarden.widget.zg
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f17156c.lambda$init$0(view);
            }
        });
        this.mTextView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.ah
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f16321c.lambda$init$1(view, motionEvent);
            }
        });
        this.mTextView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.bh
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16351c.lambda$init$2(view);
            }
        });
        this.mTextView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.psychiatrygarden.widget.SelectableTextHelper.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View v2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View v2) {
                SelectableTextHelper.this.destroy();
            }
        });
        this.mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.psychiatrygarden.widget.ch
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                return this.f16381c.lambda$init$3();
            }
        };
        this.mTextView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        this.mOnScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.psychiatrygarden.widget.dh
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                this.f16411a.lambda$init$4();
            }
        };
        this.mTextView.getViewTreeObserver().addOnScrollChangedListener(this.mOnScrollChangedListener);
        this.mOperateWindow = new OperateWindow(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$0(View view) {
        showSelectView(this.mTouchX, this.mTouchY);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$1(View view, MotionEvent motionEvent) {
        this.mTouchX = (int) motionEvent.getX();
        this.mTouchY = (int) motionEvent.getY();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$2(View view) {
        resetSelectionInfo();
        hideSelectView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$init$3() {
        if (!this.isHideWhenScroll) {
            return true;
        }
        this.isHideWhenScroll = false;
        postShowSelectView(100);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$4() {
        if (this.isHideWhenScroll || this.isHide) {
            return;
        }
        this.isHideWhenScroll = true;
        OperateWindow operateWindow = this.mOperateWindow;
        if (operateWindow != null) {
            operateWindow.dismiss();
        }
        CursorHandle cursorHandle = this.mStartHandle;
        if (cursorHandle != null) {
            cursorHandle.dismiss();
        }
        CursorHandle cursorHandle2 = this.mEndHandle;
        if (cursorHandle2 != null) {
            cursorHandle2.dismiss();
        }
    }

    private void postShowSelectView(int duration) {
        this.mTextView.removeCallbacks(this.mShowSelectViewRunnable);
        if (duration <= 0) {
            this.mShowSelectViewRunnable.run();
        } else {
            this.mTextView.postDelayed(this.mShowSelectViewRunnable, duration);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetSelectionInfo() {
        BackgroundColorSpan backgroundColorSpan;
        this.mSelectionInfo.mSelectionContent = null;
        Spannable spannable = this.mSpannable;
        if (spannable == null || (backgroundColorSpan = this.mSpan) == null) {
            return;
        }
        spannable.removeSpan(backgroundColorSpan);
        this.mSpan = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectText(int startPos, int endPos) {
        if (startPos != -1) {
            this.mSelectionInfo.mStart = startPos;
        }
        if (endPos != -1) {
            this.mSelectionInfo.mEnd = endPos;
        }
        SelectionInfo selectionInfo = this.mSelectionInfo;
        int i2 = selectionInfo.mStart;
        int i3 = selectionInfo.mEnd;
        if (i2 > i3) {
            selectionInfo.mStart = i3;
            selectionInfo.mEnd = i2;
        }
        if (this.mSpannable != null) {
            if (this.mSpan == null) {
                this.mSpan = new BackgroundColorSpan(this.mSelectedColor);
            }
            SelectionInfo selectionInfo2 = this.mSelectionInfo;
            selectionInfo2.mSelectionContent = this.mSpannable.subSequence(selectionInfo2.mStart, selectionInfo2.mEnd).toString();
            Spannable spannable = this.mSpannable;
            BackgroundColorSpan backgroundColorSpan = this.mSpan;
            SelectionInfo selectionInfo3 = this.mSelectionInfo;
            spannable.setSpan(backgroundColorSpan, selectionInfo3.mStart, selectionInfo3.mEnd, 17);
            OnSelectListener onSelectListener = this.mSelectListener;
            if (onSelectListener != null) {
                onSelectListener.onTextSelected(this.mSelectionInfo.mSelectionContent);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCursorHandle(CursorHandle cursorHandle) {
        Layout layout = this.mTextView.getLayout();
        int i2 = cursorHandle.isLeft ? this.mSelectionInfo.mStart : this.mSelectionInfo.mEnd;
        cursorHandle.show((int) layout.getPrimaryHorizontal(i2), layout.getLineBottom(layout.getLineForOffset(i2)));
    }

    private void showSelectView(int x2, int y2) {
        hideSelectView();
        resetSelectionInfo();
        this.isHide = false;
        if (this.mStartHandle == null) {
            this.mStartHandle = new CursorHandle(true);
        }
        if (this.mEndHandle == null) {
            this.mEndHandle = new CursorHandle(false);
        }
        int preciseOffset = TextLayoutUtil.getPreciseOffset(this.mTextView, x2, y2);
        int i2 = preciseOffset + 1;
        if (this.mTextView.getText() instanceof Spannable) {
            this.mSpannable = (Spannable) this.mTextView.getText();
        }
        if (this.mSpannable == null || preciseOffset >= this.mTextView.getText().length()) {
            return;
        }
        selectText(preciseOffset, i2);
        showCursorHandle(this.mStartHandle);
        showCursorHandle(this.mEndHandle);
        OperateWindow operateWindow = this.mOperateWindow;
        if (operateWindow != null) {
            operateWindow.show();
        }
    }

    public void destroy() {
        this.mTextView.getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
        this.mTextView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        resetSelectionInfo();
        hideSelectView();
        this.mStartHandle = null;
        this.mEndHandle = null;
        this.mOperateWindow = null;
    }

    public void setSelectListener(OnSelectListener selectListener) {
        this.mSelectListener = selectListener;
    }
}
