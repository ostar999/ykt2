package top.defaults.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.SdkConstant;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes9.dex */
public class PickerView extends View {
    private static final boolean DEBUG = false;
    static final int DEFAULT_ITEM_HEIGHT_IN_DP = 56;
    static final int DEFAULT_MAX_OFFSET_ITEM_COUNT = 3;
    static final int DEFAULT_TEXT_SIZE_IN_SP = 16;
    private static final int DURATION_LONG = 1000;
    private static final int DURATION_SHORT = 250;
    private final int[] DEFAULT_GRADIENT_COLORS;
    private float actionDownY;
    private Adapter<? extends PickerItem> adapter;
    private boolean autoFitSize;
    private GradientDrawable bottomMask;
    private Camera camera;
    private boolean curved;
    private GestureDetector gestureDetector;
    private final int[] gradientColors;
    private boolean isChangeItemColor;
    private boolean isCyclic;
    private boolean isScrollSuspendedByDownEvent;
    private int itemHeight;
    private Matrix matrix;
    private int maxOverScrollY;
    private int maxY;
    private int minY;
    private OnSelectedItemChangedListener onSelectedItemChangedListener;
    private boolean pendingJustify;
    private int preferredMaxOffsetItemCount;
    private int previousScrollerY;
    private float previousTouchedY;
    private float radius;
    private OverScroller scroller;
    private boolean scrolling;
    private GradientDrawable selectedItemDrawable;
    private int selectedItemPosition;
    private final Layout.Alignment textAlign;
    private Rect textBounds;
    private int textColor;
    private Paint textPaint;
    private int textSecondColor;
    private int textSize;
    private int textThirdColor;
    private GradientDrawable topMask;
    private int touchSlop;
    private Typeface typeface;
    private int yOffset;

    public static abstract class Adapter<T extends PickerItem> {
        private WeakReference<PickerView> pickerViewRef;

        /* JADX INFO: Access modifiers changed from: private */
        public void setPickerView(PickerView pickerView) {
            this.pickerViewRef = new WeakReference<>(pickerView);
        }

        public abstract T getItem(int i2);

        public abstract int getItemCount();

        public T getLastItem() {
            return (T) getItem(getItemCount() - 1);
        }

        public String getText(int i2) {
            return getItem(i2) == null ? "null" : getItem(i2).getText();
        }

        public void notifyDataSetChanged() {
            PickerView pickerView;
            WeakReference<PickerView> weakReference = this.pickerViewRef;
            if (weakReference == null || (pickerView = weakReference.get()) == null) {
                return;
            }
            pickerView.updateSelectedItem();
            pickerView.computeScrollParams();
            if (!pickerView.scroller.isFinished()) {
                pickerView.scroller.forceFinished(true);
            }
            pickerView.justify(0);
            pickerView.invalidate();
        }
    }

    public interface OnItemSelectedListener<T extends PickerItem> {
        void onItemSelected(T t2);
    }

    public interface OnSelectedItemChangedListener {
        void onSelectedItemChanged(PickerView pickerView, int i2, int i3);
    }

    public interface PickerItem {
        String getText();
    }

    public PickerView(Context context) {
        this(context, null);
    }

    private int calculateIntrinsicHeight() {
        if (!this.curved) {
            return ((this.preferredMaxOffsetItemCount * 2) + 1) * this.itemHeight;
        }
        this.radius = this.itemHeight / ((float) Math.sin(3.141592653589793d / ((this.preferredMaxOffsetItemCount * 2) + 3)));
        return (int) Math.ceil(r0 * 2.0f);
    }

    private float centerPosition() {
        return (this.selectedItemPosition + 0.5f) - ((this.yOffset * 1.0f) / this.itemHeight);
    }

    private int clampItemPosition(int i2) {
        if (this.adapter.getItemCount() == 0) {
            return 0;
        }
        if (this.isCyclic) {
            if (i2 < 0) {
                i2 %= this.adapter.getItemCount();
                if (i2 != 0) {
                    i2 += this.adapter.getItemCount();
                }
            } else if (i2 >= this.adapter.getItemCount()) {
                i2 %= this.adapter.getItemCount();
            }
        }
        if (i2 < 0) {
            return 0;
        }
        return i2 >= this.adapter.getItemCount() ? this.adapter.getItemCount() - 1 : i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void computeScrollParams() {
        if (this.isCyclic) {
            this.minY = Integer.MIN_VALUE;
            this.maxY = Integer.MAX_VALUE;
        } else {
            this.minY = (-(this.adapter.getItemCount() - 1)) * this.itemHeight;
            this.maxY = 0;
        }
        this.maxOverScrollY = this.itemHeight * 2;
    }

    private void drawItems(Canvas canvas) {
        float measuredHeight = this.yOffset + ((getMeasuredHeight() - this.itemHeight) / 2.0f);
        drawText(canvas, this.adapter.getText(clampItemPosition(this.selectedItemPosition)), measuredHeight);
        float f2 = measuredHeight - this.itemHeight;
        int i2 = this.selectedItemPosition - 1;
        while (true) {
            if ((this.itemHeight * (this.curved ? 2 : 1)) + f2 <= 0.0f || (isPositionInvalid(i2) && !this.isCyclic)) {
                break;
            }
            String text = this.adapter.getText(clampItemPosition(i2));
            if (this.isChangeItemColor) {
                drawText(canvas, text, f2, i2);
            } else {
                drawText(canvas, text, f2);
            }
            f2 -= this.itemHeight;
            i2--;
        }
        float measuredHeight2 = this.yOffset + ((getMeasuredHeight() + this.itemHeight) / 2.0f);
        int i3 = this.selectedItemPosition + 1;
        while (measuredHeight2 - (this.itemHeight * (this.curved ? 1 : 0)) < getMeasuredHeight()) {
            if (isPositionInvalid(i3) && !this.isCyclic) {
                return;
            }
            String text2 = this.adapter.getText(clampItemPosition(i3));
            if (this.isChangeItemColor) {
                drawText(canvas, text2, measuredHeight2, i3);
            } else {
                drawText(canvas, text2, measuredHeight2);
            }
            measuredHeight2 += this.itemHeight;
            i3++;
        }
    }

    private void drawMasks(Canvas canvas) {
        this.topMask.setBounds(0, 0, getMeasuredWidth(), (getMeasuredHeight() - this.itemHeight) / 2);
        this.topMask.draw(canvas);
        this.bottomMask.setBounds(0, (getMeasuredHeight() + this.itemHeight) / 2, getMeasuredWidth(), getMeasuredHeight());
        this.bottomMask.draw(canvas);
    }

    private void drawText(Canvas canvas, String str, float f2) {
        this.textPaint.setTextSize(this.textSize);
        this.textPaint.setColor(this.textColor);
        this.textPaint.getTextBounds(str, 0, str.length(), this.textBounds);
        if (this.autoFitSize) {
            while (getMeasuredWidth() < this.textBounds.width() && this.textPaint.getTextSize() > 16.0f) {
                Paint paint = this.textPaint;
                paint.setTextSize(paint.getTextSize() - 1.0f);
                this.textPaint.getTextBounds(str, 0, str.length(), this.textBounds);
            }
        }
        float fHeight = ((this.itemHeight + this.textBounds.height()) / 2.0f) + f2;
        if (this.curved) {
            float f3 = this.radius;
            double dAtan = Math.atan((f3 - (f2 + (this.itemHeight / 2.0f))) / f3) * (2.0f / this.preferredMaxOffsetItemCount);
            this.camera.save();
            this.camera.rotateX((float) ((180.0d * dAtan) / 3.141592653589793d));
            this.camera.translate(0.0f, 0.0f, -Math.abs((this.radius / (this.preferredMaxOffsetItemCount + 2)) * ((float) Math.sin(dAtan))));
            this.camera.getMatrix(this.matrix);
            this.matrix.preTranslate((-getMeasuredWidth()) / 2.0f, (-getMeasuredHeight()) / 2.0f);
            this.matrix.postTranslate(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
            canvas.save();
            canvas.concat(this.matrix);
        }
        Layout.Alignment alignment = this.textAlign;
        if (alignment == Layout.Alignment.ALIGN_CENTER) {
            this.textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(str, getMeasuredWidth() / 2.0f, fHeight, this.textPaint);
        } else if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
            this.textPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(str, getMeasuredWidth(), fHeight, this.textPaint);
        } else {
            this.textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(str, 0.0f, fHeight, this.textPaint);
        }
        if (this.curved) {
            canvas.restore();
            this.camera.restore();
        }
    }

    private void handleOffset(int i2) {
        int i3 = this.yOffset + i2;
        this.yOffset = i3;
        if (Math.abs(i3) >= this.itemHeight) {
            int i4 = this.selectedItemPosition;
            if ((i4 != 0 || i2 < 0) && (i4 != this.adapter.getItemCount() - 1 || i2 > 0)) {
                int i5 = this.selectedItemPosition;
                notifySelectedItemChangedIfNeeded(i5 - (this.yOffset / this.itemHeight));
                this.yOffset -= (i5 - this.selectedItemPosition) * this.itemHeight;
                return;
            }
            int iAbs = Math.abs(this.yOffset);
            int i6 = this.maxOverScrollY;
            if (iAbs > i6) {
                if (this.yOffset <= 0) {
                    i6 = -i6;
                }
                this.yOffset = i6;
            }
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: top.defaults.view.PickerView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
                int i2 = PickerView.this.yOffset - (PickerView.this.itemHeight * PickerView.this.selectedItemPosition);
                if (i2 <= PickerView.this.minY || i2 >= PickerView.this.maxY) {
                    PickerView.this.justify(1000);
                    return true;
                }
                PickerView.this.scroller.fling(0, i2, 0, (int) f3, 0, 0, PickerView.this.minY, PickerView.this.maxY, 0, PickerView.this.maxOverScrollY);
                PickerView pickerView = PickerView.this;
                pickerView.previousScrollerY = pickerView.scroller.getCurrY();
                PickerView.this.pendingJustify = true;
                return true;
            }
        });
        this.scroller = new OverScroller(getContext());
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (isInEditMode()) {
            this.adapter = new Adapter<PickerItem>() { // from class: top.defaults.view.PickerView.2
                @Override // top.defaults.view.PickerView.Adapter
                public PickerItem getItem(final int i2) {
                    return new PickerItem() { // from class: top.defaults.view.PickerView.2.1
                        @Override // top.defaults.view.PickerView.PickerItem
                        public String getText() {
                            return "Item " + i2;
                        }
                    };
                }

                @Override // top.defaults.view.PickerView.Adapter
                public int getItemCount() {
                    return PickerView.this.getMaxCount();
                }
            };
        }
        this.topMask = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, this.gradientColors);
        this.bottomMask = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, this.gradientColors);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PickerView);
        int i2 = typedArrayObtainStyledAttributes.getInt(R.styleable.PickerView_preferredMaxOffsetItemCount, 3);
        this.preferredMaxOffsetItemCount = i2;
        if (i2 <= 0) {
            this.preferredMaxOffsetItemCount = 3;
        }
        int iPixelOfDp = Utils.pixelOfDp(getContext(), 56);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PickerView_itemHeight, iPixelOfDp);
        this.itemHeight = dimensionPixelSize;
        if (dimensionPixelSize <= 0) {
            this.itemHeight = iPixelOfDp;
        }
        this.textSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PickerView_textSize, Utils.pixelOfScaled(getContext(), 16));
        this.isCyclic = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PickerView_isCyclic, false);
        this.autoFitSize = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PickerView_autoFitSize, true);
        this.curved = typedArrayObtainStyledAttributes.getBoolean(R.styleable.PickerView_curved, false);
        typedArrayObtainStyledAttributes.recycle();
        boolean z2 = context.getSharedPreferences(com.yikaobang.yixue.BuildConfig.APPLICATION_ID.equals(context.getPackageName()) ? SdkConstant.UMENG_ALIS : "hukaobang", 0).getInt(CommonParameter.SkinMananer, 0) == 1;
        GradientDrawable gradientDrawable = new GradientDrawable();
        this.selectedItemDrawable = gradientDrawable;
        gradientDrawable.setColor(ColorStateList.valueOf(Color.parseColor("#F9FAFB")));
        this.selectedItemDrawable.setShape(0);
        this.selectedItemDrawable.setCornerRadius(TypedValue.applyDimension(1, 4.0f, context.getResources().getDisplayMetrics()));
        if (z2) {
            this.textColor = Color.parseColor("#7380A9");
            this.textSecondColor = Color.parseColor("#606A8A");
            this.textThirdColor = Color.parseColor("#575F79");
            this.selectedItemDrawable.setColor(ColorStateList.valueOf(Color.parseColor("#0D111D")));
        }
        initPaints();
        this.camera = new Camera();
        this.matrix = new Matrix();
    }

    private void initPaints() {
        Paint paint = new Paint();
        this.textPaint = paint;
        paint.setAntiAlias(true);
    }

    private boolean isPositionInvalid(int i2) {
        return i2 < 0 || i2 >= this.adapter.getItemCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void justify(int i2) {
        int i3;
        int i4;
        int i5 = this.yOffset;
        if (i5 != 0) {
            int i6 = -i5;
            int i7 = this.selectedItemPosition;
            if (i7 != 0 && i7 != this.adapter.getItemCount() - 1) {
                int i8 = this.yOffset;
                if (i8 > 0) {
                    int i9 = this.itemHeight;
                    if (i8 > i9 / 3) {
                        i6 = i9 - i8;
                    }
                } else {
                    int iAbs = Math.abs(i8);
                    int i10 = this.itemHeight;
                    if (iAbs > i10 / 3) {
                        i6 = -(i10 + this.yOffset);
                    }
                }
            }
            if (this.adapter.getItemCount() > 1) {
                if (this.selectedItemPosition == 0 && (i4 = this.yOffset) < 0) {
                    int iAbs2 = Math.abs(i4);
                    int i11 = this.itemHeight;
                    if (iAbs2 > i11 / 3) {
                        i6 = -(i11 + this.yOffset);
                    }
                }
                if (this.selectedItemPosition == this.adapter.getItemCount() - 1 && (i3 = this.yOffset) > 0) {
                    int i12 = this.itemHeight;
                    if (i3 > i12 / 3) {
                        i6 = i12 - i3;
                    }
                }
            }
            int i13 = this.yOffset - (this.itemHeight * this.selectedItemPosition);
            this.previousScrollerY = i13;
            this.scroller.startScroll(0, i13, 0, i6, i2);
            invalidate();
        }
        this.pendingJustify = false;
    }

    private void notifySelectedItemChangedIfNeeded(int i2) {
        notifySelectedItemChangedIfNeeded(i2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectedItem() {
        notifySelectedItemChangedIfNeeded((int) Math.floor(centerPosition()), true);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (!this.scroller.computeScrollOffset()) {
            if (this.pendingJustify) {
                justify(250);
            }
        } else {
            int currY = this.scroller.getCurrY();
            handleOffset(currY - this.previousScrollerY);
            this.previousScrollerY = currY;
            invalidate();
        }
    }

    public Adapter getAdapter() {
        return this.adapter;
    }

    public int getMaxCount() {
        return Integer.MAX_VALUE / this.itemHeight;
    }

    public <T extends PickerItem> T getSelectedItem(Class<T> cls) {
        Utils.checkNotNull(this.adapter, "adapter must be set first");
        PickerItem item = this.adapter.getItem(getSelectedItemPosition());
        if (cls.isInstance(item)) {
            return cls.cast(item);
        }
        return null;
    }

    public int getSelectedItemPosition() {
        return clampItemPosition(this.selectedItemPosition);
    }

    public void notifyDataSetChanged() {
        Adapter<? extends PickerItem> adapter = this.adapter;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Utils.checkNotNull(this.adapter, "adapter == null");
        if (this.adapter.getItemCount() == 0 || this.itemHeight == 0) {
            return;
        }
        if (!isInEditMode()) {
            this.selectedItemDrawable.setBounds(0, (getMeasuredHeight() - this.itemHeight) / 2, getMeasuredWidth(), (getMeasuredHeight() + this.itemHeight) / 2);
            this.selectedItemDrawable.draw(canvas);
        }
        drawItems(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        Utils.checkNotNull(this.adapter, "adapter == null");
        int iResolveSizeAndState = View.resolveSizeAndState(calculateIntrinsicHeight(), i3, 0);
        computeScrollParams();
        setMeasuredDimension(i2, iResolveSizeAndState);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00af A[PHI: r9
      0x00af: PHI (r9v20 int) = (r9v19 int), (r9v24 int) binds: [B:38:0x00ac, B:35:0x0098] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: top.defaults.view.PickerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends PickerItem> void setAdapter(Adapter<T> adapter) {
        Utils.checkNotNull(adapter, "adapter == null");
        if (adapter.getItemCount() > Integer.MAX_VALUE / this.itemHeight) {
            throw new RuntimeException("getItemCount() is too large, max count can be PickerView.getMaxCount()");
        }
        adapter.setPickerView(this);
        this.adapter = adapter;
    }

    public void setAutoFitSize(boolean z2) {
        if (this.autoFitSize != z2) {
            this.autoFitSize = z2;
            invalidate();
        }
    }

    public void setChangeItemColor(boolean z2) {
        this.isChangeItemColor = z2;
    }

    public void setCurved(boolean z2) {
        if (this.curved != z2) {
            this.curved = z2;
            invalidate();
            requestLayout();
        }
    }

    public void setCyclic(boolean z2) {
        if (this.isCyclic != z2) {
            this.isCyclic = z2;
            invalidate();
        }
    }

    public void setItemHeight(int i2) {
        if (this.itemHeight != i2) {
            this.itemHeight = i2;
            invalidate();
            requestLayout();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends PickerItem> void setItems(final List<T> list, final OnItemSelectedListener<T> onItemSelectedListener) {
        final Adapter<T> adapter = new Adapter<T>() { // from class: top.defaults.view.PickerView.3
            /* JADX WARN: Incorrect return type in method signature: (I)TT; */
            @Override // top.defaults.view.PickerView.Adapter
            public PickerItem getItem(int i2) {
                return (PickerItem) list.get(i2);
            }

            @Override // top.defaults.view.PickerView.Adapter
            public int getItemCount() {
                return list.size();
            }
        };
        setAdapter(adapter);
        setOnSelectedItemChangedListener(new OnSelectedItemChangedListener() { // from class: top.defaults.view.PickerView.4
            @Override // top.defaults.view.PickerView.OnSelectedItemChangedListener
            public void onSelectedItemChanged(PickerView pickerView, int i2, int i3) {
                OnItemSelectedListener onItemSelectedListener2 = onItemSelectedListener;
                if (onItemSelectedListener2 != null) {
                    onItemSelectedListener2.onItemSelected(adapter.getItem(i3));
                }
            }
        });
    }

    public void setOnSelectedItemChangedListener(OnSelectedItemChangedListener onSelectedItemChangedListener) {
        this.onSelectedItemChangedListener = onSelectedItemChangedListener;
    }

    public void setPreferredMaxOffsetItemCount(int i2) {
        this.preferredMaxOffsetItemCount = i2;
    }

    public void setSelectedItemPosition(int i2) {
        Utils.checkNotNull(this.adapter, "adapter must be set first");
        notifySelectedItemChangedIfNeeded(i2);
        invalidate();
    }

    public void setTextColor(int i2) {
        if (this.textColor != i2) {
            this.textColor = i2;
            invalidate();
        }
    }

    public void setTextSize(int i2) {
        if (this.textSize != i2) {
            this.textSize = i2;
            invalidate();
        }
    }

    public void setTypeface(Typeface typeface) {
        if (this.typeface != typeface) {
            this.typeface = typeface;
            this.textPaint.setTypeface(typeface);
            invalidate();
        }
    }

    public PickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private void notifySelectedItemChangedIfNeeded(int i2, boolean z2) {
        OnSelectedItemChangedListener onSelectedItemChangedListener;
        int i3 = this.selectedItemPosition;
        int iClampItemPosition = clampItemPosition(i2);
        if (this.isCyclic) {
            if (this.selectedItemPosition != i2) {
                this.selectedItemPosition = i2;
                z2 = true;
            }
        } else if (this.selectedItemPosition != iClampItemPosition) {
            this.selectedItemPosition = iClampItemPosition;
            z2 = true;
        }
        if (!z2 || (onSelectedItemChangedListener = this.onSelectedItemChangedListener) == null) {
            return;
        }
        onSelectedItemChangedListener.onSelectedItemChanged(this, i3, iClampItemPosition);
    }

    public PickerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.preferredMaxOffsetItemCount = 3;
        this.textBounds = new Rect();
        this.textColor = Color.parseColor("#141516");
        this.textSecondColor = Color.parseColor("#606060");
        this.textThirdColor = Color.parseColor("#909499");
        this.autoFitSize = true;
        int[] iArr = {-805635334, -1610941702, 1610283770};
        this.DEFAULT_GRADIENT_COLORS = iArr;
        this.gradientColors = iArr;
        this.textAlign = Layout.Alignment.ALIGN_CENTER;
        this.isChangeItemColor = false;
        init(context, attributeSet);
    }

    private void drawText(Canvas canvas, String str, float f2, int i2) {
        this.textPaint.setTextSize(this.textSize);
        if (Math.abs(this.selectedItemPosition - i2) == 1) {
            this.textPaint.setColor(this.textSecondColor);
        } else if (Math.abs(this.selectedItemPosition - i2) == 2) {
            this.textPaint.setColor(this.textThirdColor);
        }
        this.textPaint.getTextBounds(str, 0, str.length(), this.textBounds);
        if (this.autoFitSize) {
            while (getMeasuredWidth() < this.textBounds.width() && this.textPaint.getTextSize() > 16.0f) {
                Paint paint = this.textPaint;
                paint.setTextSize(paint.getTextSize() - 1.0f);
                this.textPaint.getTextBounds(str, 0, str.length(), this.textBounds);
            }
        }
        float fHeight = ((this.itemHeight + this.textBounds.height()) / 2.0f) + f2;
        if (this.curved) {
            float f3 = this.radius;
            double dAtan = Math.atan((f3 - (f2 + (this.itemHeight / 2.0f))) / f3) * (2.0f / this.preferredMaxOffsetItemCount);
            this.camera.save();
            this.camera.rotateX((float) ((180.0d * dAtan) / 3.141592653589793d));
            this.camera.translate(0.0f, 0.0f, -Math.abs((this.radius / (this.preferredMaxOffsetItemCount + 2)) * ((float) Math.sin(dAtan))));
            this.camera.getMatrix(this.matrix);
            this.matrix.preTranslate((-getMeasuredWidth()) / 2.0f, (-getMeasuredHeight()) / 2.0f);
            this.matrix.postTranslate(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
            canvas.save();
            canvas.concat(this.matrix);
        }
        Layout.Alignment alignment = this.textAlign;
        if (alignment == Layout.Alignment.ALIGN_CENTER) {
            this.textPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(str, getMeasuredWidth() / 2.0f, fHeight, this.textPaint);
        } else if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
            this.textPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(str, getMeasuredWidth(), fHeight, this.textPaint);
        } else {
            this.textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(str, 0.0f, fHeight, this.textPaint);
        }
        if (this.curved) {
            canvas.restore();
            this.camera.restore();
        }
    }
}
