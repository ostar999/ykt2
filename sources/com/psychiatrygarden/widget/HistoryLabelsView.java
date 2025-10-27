package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cn.webdemo.com.supporfragment.tablayout.buildins.UIUtil;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class HistoryLabelsView extends ViewGroup implements View.OnClickListener, View.OnLongClickListener {
    private static final String KEY_BG_RES_ID_STATE = "key_bg_res_id_state";
    private static final String KEY_COMPULSORY_LABELS_STATE = "key_select_compulsory_state";
    private static final int KEY_DATA = 2131367177;
    private static final String KEY_INDICATOR_STATE = "key_indicator_state";
    private static final String KEY_LABELS_STATE = "key_labels_state";
    private static final String KEY_LABEL_GRAVITY_STATE = "key_label_gravity_state";
    private static final String KEY_LABEL_HEIGHT_STATE = "key_label_height_state";
    private static final String KEY_LABEL_WIDTH_STATE = "key_label_width_state";
    private static final String KEY_LINE_MARGIN_STATE = "key_line_margin_state";
    private static final String KEY_MAX_COLUMNS_STATE = "key_max_columns_state";
    private static final String KEY_MAX_LINES_STATE = "key_max_lines_state";
    private static final String KEY_MAX_SELECT_STATE = "key_max_select_state";
    private static final String KEY_MIN_SELECT_STATE = "key_min_select_state";
    private static final String KEY_PADDING_STATE = "key_padding_state";
    private static final int KEY_POSITION = 2131367178;
    private static final String KEY_SELECT_LABELS_STATE = "key_select_labels_state";
    private static final String KEY_SELECT_TYPE_STATE = "key_select_type_state";
    private static final String KEY_SINGLE_LINE_STATE = "key_single_line_state";
    private static final String KEY_SUPER_STATE = "key_super_state";
    private static final String KEY_TEXT_COLOR_STATE = "key_text_color_state";
    private static final String KEY_TEXT_SIZE_STATE = "key_text_size_state";
    private static final String KEY_TEXT_STYLE_STATE = "key_text_style_state";
    private static final String KEY_WORD_MARGIN_STATE = "key_word_margin_state";
    private boolean isIndicator;
    private boolean isSingleLine;
    private boolean isTextBold;
    private int mColumns;
    private final ArrayList<Integer> mCompulsorys;
    private Context mContext;
    private Drawable mLabelBg;
    private OnLabelClickListener mLabelClickListener;
    private int mLabelGravity;
    private int mLabelHeight;
    private OnLabelLongClickListener mLabelLongClickListener;
    private OnLabelSelectChangeListener mLabelSelectChangeListener;
    private int mLabelWidth;
    private final ArrayList<Object> mLabels;
    private int mLineMargin;
    private int mLines;
    private int mMaxColumns;
    private int mMaxLines;
    private int mMaxSelect;
    private int mMinSelect;
    private OnSelectChangeIntercept mOnSelectChangeIntercept;
    private int mScreenWidth;
    private final ArrayList<Integer> mSelectLabels;
    private SelectType mSelectType;
    private ColorStateList mTextColor;
    private int mTextPaddingBottom;
    private int mTextPaddingLeft;
    private int mTextPaddingRight;
    private int mTextPaddingTop;
    private float mTextSize;
    private int mWordMargin;

    public interface LabelTextProvider<T> {
        CharSequence getLabelText(TextView label, int position, T data);
    }

    public interface OnLabelClickListener {
        void onLabelClick(TextView label, Object data, int position);
    }

    public interface OnLabelLongClickListener {
        boolean onLabelLongClick(TextView label, Object data, int position);
    }

    public interface OnLabelSelectChangeListener {
        void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position);
    }

    public interface OnSelectChangeIntercept {
        boolean onIntercept(TextView label, Object data, boolean oldSelect, boolean newSelect, int position);
    }

    public enum SelectType {
        NONE(1),
        SINGLE(2),
        SINGLE_IRREVOCABLY(3),
        MULTI(4);

        int value;

        SelectType(int value) {
            this.value = value;
        }

        public static SelectType get(int value) {
            return value != 1 ? value != 2 ? value != 3 ? value != 4 ? NONE : MULTI : SINGLE_IRREVOCABLY : SINGLE : NONE;
        }
    }

    public interface Selectable {
        boolean isSelected();

        void onSelected(boolean selected);
    }

    public HistoryLabelsView(Context context) {
        super(context);
        this.mLabelWidth = -2;
        this.mLabelHeight = -2;
        this.mLabelGravity = 17;
        this.isSingleLine = false;
        this.isTextBold = false;
        this.mLabels = new ArrayList<>();
        this.mSelectLabels = new ArrayList<>();
        this.mCompulsorys = new ArrayList<>();
        this.mScreenWidth = 0;
        this.mContext = context;
        showEditPreview();
    }

    private <T> void addLabel(T data, int position, LabelTextProvider<T> provider) {
        TextView textView = new TextView(this.mContext);
        int i2 = this.mColumns;
        if (i2 > 0) {
            int i3 = this.mScreenWidth;
            if (i3 > 0) {
                textView.setWidth((i3 - (this.mWordMargin * (i2 - 1))) / i2);
                textView.setPadding(this.mTextPaddingLeft, this.mTextPaddingTop, this.mTextPaddingRight, this.mTextPaddingBottom);
            } else {
                int measuredWidth = getMeasuredWidth();
                int i4 = this.mWordMargin;
                int i5 = this.mColumns;
                textView.setWidth((measuredWidth - (i4 * (i5 - 1))) / i5);
                textView.setPadding(0, this.mTextPaddingTop, 0, this.mTextPaddingBottom);
            }
        } else {
            textView.setPadding(this.mTextPaddingLeft, this.mTextPaddingTop, this.mTextPaddingRight, this.mTextPaddingBottom);
        }
        textView.setTextSize(0, this.mTextSize);
        textView.setGravity(this.mLabelGravity);
        textView.setTextColor(this.mTextColor);
        textView.setLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setBackgroundDrawable(this.mLabelBg.getConstantState().newDrawable());
        textView.setTag(R.id.tag_key_data, data);
        textView.setTag(R.id.tag_key_position, Integer.valueOf(position));
        textView.setOnClickListener(this);
        textView.setOnLongClickListener(this);
        textView.getPaint().setFakeBoldText(this.isTextBold);
        if (data instanceof String) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.edit_delete_night : R.drawable.edit_delete);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(UIUtil.dip2px(getContext(), 100.0d));
            if (SkinManager.getCurrentSkinType(getContext()) == 0) {
                gradientDrawable.setColor(Color.parseColor("#F9FAFB"));
            } else {
                gradientDrawable.setColor(Color.parseColor("#0D111D"));
            }
            textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 1 ? "#7380A9" : "#141516"));
            textView.setBackground(gradientDrawable);
            textView.setCompoundDrawablePadding(CommonUtil.dip2px(getContext(), 8.0f));
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, drawable, (Drawable) null);
        }
        addView(textView, this.mLabelWidth, this.mLabelHeight);
        textView.setText(provider.getLabelText(textView, position, data));
    }

    private void clearNotCompulsorySelect() {
        int childCount = getChildCount();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (!this.mCompulsorys.contains(Integer.valueOf(i2))) {
                setLabelSelect((TextView) getChildAt(i2), false);
                arrayList.add(Integer.valueOf(i2));
            }
        }
        this.mSelectLabels.removeAll(arrayList);
    }

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(1, dpVal, getResources().getDisplayMetrics());
    }

    private void ensureLabelClickable() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((TextView) getChildAt(i2)).setClickable((this.mLabelClickListener == null && this.mLabelLongClickListener == null && this.mSelectType == SelectType.NONE) ? false : true);
        }
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.LabelsView);
            this.mSelectType = SelectType.get(typedArrayObtainStyledAttributes.getInt(19, 1));
            this.mMaxSelect = typedArrayObtainStyledAttributes.getInteger(17, 0);
            this.mMinSelect = typedArrayObtainStyledAttributes.getInteger(18, 0);
            this.mMaxLines = typedArrayObtainStyledAttributes.getInteger(16, 0);
            this.mMaxColumns = typedArrayObtainStyledAttributes.getInteger(15, 0);
            this.mColumns = typedArrayObtainStyledAttributes.getInteger(0, 0);
            this.isIndicator = typedArrayObtainStyledAttributes.getBoolean(1, false);
            this.mLabelGravity = typedArrayObtainStyledAttributes.getInt(4, this.mLabelGravity);
            this.mLabelWidth = typedArrayObtainStyledAttributes.getLayoutDimension(13, this.mLabelWidth);
            this.mLabelHeight = typedArrayObtainStyledAttributes.getLayoutDimension(6, this.mLabelHeight);
            if (typedArrayObtainStyledAttributes.hasValue(5)) {
                this.mTextColor = typedArrayObtainStyledAttributes.getColorStateList(5);
            } else {
                this.mTextColor = ColorStateList.valueOf(-16777216);
            }
            this.mTextSize = typedArrayObtainStyledAttributes.getDimension(12, sp2px(14.0f));
            if (typedArrayObtainStyledAttributes.hasValue(7)) {
                int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(7, 0);
                this.mTextPaddingBottom = dimensionPixelOffset;
                this.mTextPaddingRight = dimensionPixelOffset;
                this.mTextPaddingTop = dimensionPixelOffset;
                this.mTextPaddingLeft = dimensionPixelOffset;
            } else {
                this.mTextPaddingLeft = typedArrayObtainStyledAttributes.getDimensionPixelOffset(9, dp2px(10.0f));
                this.mTextPaddingTop = typedArrayObtainStyledAttributes.getDimensionPixelOffset(11, dp2px(5.0f));
                this.mTextPaddingRight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(10, dp2px(10.0f));
                this.mTextPaddingBottom = typedArrayObtainStyledAttributes.getDimensionPixelOffset(8, dp2px(5.0f));
            }
            this.mLineMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(14, dp2px(5.0f));
            this.mWordMargin = typedArrayObtainStyledAttributes.getDimensionPixelOffset(21, dp2px(5.0f));
            if (typedArrayObtainStyledAttributes.hasValue(3)) {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(3, 0);
                if (resourceId != 0) {
                    this.mLabelBg = getResources().getDrawable(resourceId, getContext().getTheme());
                } else {
                    this.mLabelBg = new ColorDrawable(typedArrayObtainStyledAttributes.getColor(3, 0));
                }
            } else {
                this.mLabelBg = getResources().getDrawable(R.drawable.default_label_bg, getContext().getTheme());
            }
            this.isSingleLine = typedArrayObtainStyledAttributes.getBoolean(20, false);
            this.isTextBold = typedArrayObtainStyledAttributes.getBoolean(2, false);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private void innerClearAllSelect() {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            setLabelSelect((TextView) getChildAt(i2), false);
        }
        this.mSelectLabels.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0076 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measureMultiLine(int r14, int r15) {
        /*
            r13 = this;
            int r0 = r13.getChildCount()
            int r1 = android.view.View.MeasureSpec.getSize(r14)
            int r2 = r13.getPaddingLeft()
            int r1 = r1 - r2
            int r2 = r13.getPaddingRight()
            int r1 = r1 - r2
            r2 = 0
            r3 = 1
            r4 = r2
            r5 = r4
            r6 = r5
            r7 = r6
            r8 = r7
            r9 = r8
            r10 = r3
        L1b:
            if (r4 >= r0) goto L79
            android.view.View r11 = r13.getChildAt(r4)
            r13.measureChild(r11, r14, r15)
            int r12 = r11.getMeasuredWidth()
            int r12 = r12 + r5
            if (r12 > r1) goto L31
            int r12 = r13.mMaxColumns
            if (r12 <= 0) goto L47
            if (r6 != r12) goto L47
        L31:
            int r10 = r10 + 1
            int r6 = r13.mMaxLines
            if (r6 <= 0) goto L3c
            if (r10 <= r6) goto L3c
        L39:
            int r10 = r10 + (-1)
            goto L79
        L3c:
            int r6 = r13.mLineMargin
            int r8 = r8 + r6
            int r8 = r8 + r7
            int r9 = java.lang.Math.max(r9, r5)
            r5 = r2
            r6 = r5
            r7 = r6
        L47:
            int r12 = r11.getMeasuredWidth()
            int r5 = r5 + r12
            int r6 = r6 + r3
            int r11 = r11.getMeasuredHeight()
            int r7 = java.lang.Math.max(r7, r11)
            int r11 = r0 + (-1)
            if (r4 == r11) goto L76
            int r11 = r13.mWordMargin
            int r12 = r5 + r11
            if (r12 <= r1) goto L75
            int r10 = r10 + 1
            int r6 = r13.mMaxLines
            if (r6 <= 0) goto L68
            if (r10 <= r6) goto L68
            goto L39
        L68:
            int r6 = r13.mLineMargin
            int r8 = r8 + r6
            int r8 = r8 + r7
            int r5 = java.lang.Math.max(r9, r5)
            r6 = r2
            r7 = r6
            r9 = r5
            r5 = r7
            goto L76
        L75:
            int r5 = r5 + r11
        L76:
            int r4 = r4 + 1
            goto L1b
        L79:
            int r8 = r8 + r7
            int r1 = java.lang.Math.max(r9, r5)
            int r3 = r13.getPaddingLeft()
            int r1 = r1 + r3
            int r3 = r13.getPaddingRight()
            int r1 = r1 + r3
            int r14 = r13.measureSize(r14, r1)
            int r1 = r13.getPaddingTop()
            int r8 = r8 + r1
            int r1 = r13.getPaddingBottom()
            int r8 = r8 + r1
            int r15 = r13.measureSize(r15, r8)
            r13.setMeasuredDimension(r14, r15)
            if (r0 <= 0) goto La0
            r2 = r10
        La0:
            r13.mLines = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.HistoryLabelsView.measureMultiLine(int, int):void");
    }

    private void measureSingleLine(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int measuredWidth = 0;
        int iMax = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            int i3 = this.mMaxColumns;
            if (i3 > 0 && i2 == i3) {
                break;
            }
            View childAt = getChildAt(i2);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            measuredWidth += childAt.getMeasuredWidth();
            if (i2 != childCount - 1) {
                measuredWidth += this.mWordMargin;
            }
            iMax = Math.max(iMax, childAt.getMeasuredHeight());
        }
        setMeasuredDimension(measureSize(widthMeasureSpec, measuredWidth + getPaddingLeft() + getPaddingRight()), measureSize(heightMeasureSpec, iMax + getPaddingTop() + getPaddingBottom()));
        this.mLines = childCount > 0 ? 1 : 0;
    }

    private int measureSize(int measureSpec, int size) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size2 = View.MeasureSpec.getSize(measureSpec);
        if (mode == 1073741824) {
            size = size2;
        } else if (mode == Integer.MIN_VALUE) {
            size = Math.min(size, size2);
        }
        return View.resolveSizeAndState(Math.max(size, getSuggestedMinimumWidth()), measureSpec, 0);
    }

    private boolean selectChangeIntercept(TextView label) {
        OnSelectChangeIntercept onSelectChangeIntercept = this.mOnSelectChangeIntercept;
        return onSelectChangeIntercept != null && onSelectChangeIntercept.onIntercept(label, label.getTag(R.id.tag_key_data), label.isSelected(), label.isSelected() ^ true, ((Integer) label.getTag(R.id.tag_key_position)).intValue());
    }

    private void setLabelSelect(TextView label, boolean isSelect) {
        if (label.isSelected() != isSelect) {
            label.setSelected(isSelect);
            if (isSelect) {
                if (this.mScreenWidth > 0) {
                    label.getPaint().setFakeBoldText(true);
                }
                this.mSelectLabels.add((Integer) label.getTag(R.id.tag_key_position));
            } else {
                if (this.mScreenWidth > 0) {
                    label.getPaint().setFakeBoldText(false);
                }
                this.mSelectLabels.remove((Integer) label.getTag(R.id.tag_key_position));
            }
            OnLabelSelectChangeListener onLabelSelectChangeListener = this.mLabelSelectChangeListener;
            if (onLabelSelectChangeListener != null) {
                onLabelSelectChangeListener.onLabelSelectChange(label, label.getTag(R.id.tag_key_data), isSelect, ((Integer) label.getTag(R.id.tag_key_position)).intValue());
            }
        }
    }

    private void showEditPreview() {
        if (isInEditMode()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("Label 1");
            arrayList.add("Label 2");
            arrayList.add("Label 3");
            arrayList.add("Label 4");
            arrayList.add("Label 5");
            arrayList.add("Label 6");
            arrayList.add("Label 7");
            setLabels(arrayList);
        }
    }

    private int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(2, spVal, getResources().getDisplayMetrics());
    }

    public void clearAllSelect() {
        SelectType selectType = this.mSelectType;
        if (selectType != SelectType.SINGLE_IRREVOCABLY) {
            if (selectType != SelectType.MULTI || this.mCompulsorys.isEmpty()) {
                innerClearAllSelect();
            } else {
                clearNotCompulsorySelect();
            }
        }
    }

    public void clearCompulsorys() {
        if (this.mSelectType != SelectType.MULTI || this.mCompulsorys.isEmpty()) {
            return;
        }
        this.mCompulsorys.clear();
        innerClearAllSelect();
    }

    public List<Integer> getCompulsorys() {
        return this.mCompulsorys;
    }

    public int getLabelGravity() {
        return this.mLabelGravity;
    }

    public ColorStateList getLabelTextColor() {
        return this.mTextColor;
    }

    public float getLabelTextSize() {
        return this.mTextSize;
    }

    public <T> List<T> getLabels() {
        return this.mLabels;
    }

    public int getLineMargin() {
        return this.mLineMargin;
    }

    public int getLines() {
        return this.mLines;
    }

    public int getMaxColumns() {
        return this.mMaxColumns;
    }

    public int getMaxLines() {
        return this.mMaxLines;
    }

    public int getMaxSelect() {
        return this.mMaxSelect;
    }

    public int getMinSelect() {
        return this.mMinSelect;
    }

    public <T> List<T> getSelectLabelDatas() {
        ArrayList arrayList = new ArrayList();
        int size = this.mSelectLabels.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object tag = getChildAt(this.mSelectLabels.get(i2).intValue()).getTag(R.id.tag_key_data);
            if (tag != null) {
                arrayList.add(tag);
            }
        }
        return arrayList;
    }

    public List<Integer> getSelectLabels() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mSelectLabels);
        return arrayList;
    }

    public SelectType getSelectType() {
        return this.mSelectType;
    }

    public int getTextPaddingBottom() {
        return this.mTextPaddingBottom;
    }

    public int getTextPaddingLeft() {
        return this.mTextPaddingLeft;
    }

    public int getTextPaddingRight() {
        return this.mTextPaddingRight;
    }

    public int getTextPaddingTop() {
        return this.mTextPaddingTop;
    }

    public int getWordMargin() {
        return this.mWordMargin;
    }

    public boolean isIndicator() {
        return this.isIndicator;
    }

    public boolean isSingleLine() {
        return this.isSingleLine;
    }

    public boolean isTextBold() {
        return this.isTextBold;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int i2;
        if (v2 instanceof TextView) {
            TextView textView = (TextView) v2;
            if (!this.isIndicator && this.mSelectType != SelectType.NONE) {
                boolean z2 = true;
                if (textView.isSelected()) {
                    SelectType selectType = this.mSelectType;
                    SelectType selectType2 = SelectType.MULTI;
                    if (!((selectType == selectType2 && this.mCompulsorys.contains((Integer) textView.getTag(R.id.tag_key_position))) || (this.mSelectType == selectType2 && this.mSelectLabels.size() <= this.mMinSelect)) && this.mSelectType != SelectType.SINGLE_IRREVOCABLY) {
                        z2 = false;
                    }
                    if (!z2 && !selectChangeIntercept(textView)) {
                        setLabelSelect(textView, false);
                    }
                } else {
                    SelectType selectType3 = this.mSelectType;
                    if (selectType3 == SelectType.SINGLE || selectType3 == SelectType.SINGLE_IRREVOCABLY) {
                        if (!selectChangeIntercept(textView)) {
                            innerClearAllSelect();
                            setLabelSelect(textView, true);
                        }
                    } else if (selectType3 == SelectType.MULTI && (((i2 = this.mMaxSelect) <= 0 || i2 > this.mSelectLabels.size()) && !selectChangeIntercept(textView))) {
                        setLabelSelect(textView, true);
                    }
                }
            }
            OnLabelClickListener onLabelClickListener = this.mLabelClickListener;
            if (onLabelClickListener != null) {
                onLabelClickListener.onLabelClick(textView, textView.getTag(R.id.tag_key_data), ((Integer) textView.getTag(R.id.tag_key_position)).intValue());
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        int i2;
        int i3;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int i4 = right - left;
        int childCount = getChildCount();
        int i5 = 1;
        int i6 = 0;
        int iMax = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (!this.isSingleLine && (i4 < childAt.getMeasuredWidth() + paddingLeft + getPaddingRight() || ((i3 = this.mMaxColumns) > 0 && i6 == i3))) {
                i5++;
                int i8 = this.mMaxLines;
                if (i8 > 0 && i5 > i8) {
                    return;
                }
                paddingLeft = getPaddingLeft();
                paddingTop = paddingTop + this.mLineMargin + iMax;
                i6 = 0;
                iMax = 0;
            }
            if (this.isSingleLine && (i2 = this.mMaxColumns) > 0 && i6 == i2) {
                return;
            }
            childAt.layout(paddingLeft, paddingTop, childAt.getMeasuredWidth() + paddingLeft, childAt.getMeasuredHeight() + paddingTop);
            paddingLeft = paddingLeft + childAt.getMeasuredWidth() + this.mWordMargin;
            iMax = Math.max(iMax, childAt.getMeasuredHeight());
            i6++;
        }
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View v2) {
        if (!(v2 instanceof TextView)) {
            return false;
        }
        TextView textView = (TextView) v2;
        OnLabelLongClickListener onLabelLongClickListener = this.mLabelLongClickListener;
        if (onLabelLongClickListener != null) {
            return onLabelLongClickListener.onLabelLongClick(textView, textView.getTag(R.id.tag_key_data), ((Integer) textView.getTag(R.id.tag_key_position)).intValue());
        }
        return false;
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.isSingleLine) {
            measureSingleLine(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureMultiLine(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
            return;
        }
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable(KEY_SUPER_STATE));
        ColorStateList colorStateList = (ColorStateList) bundle.getParcelable(KEY_TEXT_COLOR_STATE);
        if (colorStateList != null) {
            setLabelTextColor(colorStateList);
        }
        setLabelTextSize(bundle.getFloat(KEY_TEXT_SIZE_STATE, this.mTextSize));
        this.mLabelWidth = bundle.getInt(KEY_LABEL_WIDTH_STATE, this.mLabelWidth);
        this.mLabelHeight = bundle.getInt(KEY_LABEL_HEIGHT_STATE, this.mLabelHeight);
        setLabelGravity(bundle.getInt(KEY_LABEL_GRAVITY_STATE, this.mLabelGravity));
        int[] intArray = bundle.getIntArray(KEY_PADDING_STATE);
        if (intArray != null && intArray.length == 4) {
            setLabelTextPadding(intArray[0], intArray[1], intArray[2], intArray[3]);
        }
        setWordMargin(bundle.getInt(KEY_WORD_MARGIN_STATE, this.mWordMargin));
        setLineMargin(bundle.getInt(KEY_LINE_MARGIN_STATE, this.mLineMargin));
        setSelectType(SelectType.get(bundle.getInt(KEY_SELECT_TYPE_STATE, this.mSelectType.value)));
        setMaxSelect(bundle.getInt(KEY_MAX_SELECT_STATE, this.mMaxSelect));
        setMinSelect(bundle.getInt(KEY_MIN_SELECT_STATE, this.mMinSelect));
        setMaxLines(bundle.getInt(KEY_MAX_LINES_STATE, this.mMaxLines));
        setMaxLines(bundle.getInt(KEY_MAX_COLUMNS_STATE, this.mMaxColumns));
        setIndicator(bundle.getBoolean(KEY_INDICATOR_STATE, this.isIndicator));
        setSingleLine(bundle.getBoolean(KEY_SINGLE_LINE_STATE, this.isSingleLine));
        setTextBold(bundle.getBoolean(KEY_TEXT_STYLE_STATE, this.isTextBold));
        ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(KEY_COMPULSORY_LABELS_STATE);
        if (integerArrayList != null && !integerArrayList.isEmpty()) {
            setCompulsorys(integerArrayList);
        }
        ArrayList<Integer> integerArrayList2 = bundle.getIntegerArrayList(KEY_SELECT_LABELS_STATE);
        if (integerArrayList2 == null || integerArrayList2.isEmpty()) {
            return;
        }
        int size = integerArrayList2.size();
        int[] iArr = new int[size];
        for (int i2 = 0; i2 < size; i2++) {
            iArr[i2] = integerArrayList2.get(i2).intValue();
        }
        setSelects(iArr);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        ColorStateList colorStateList = this.mTextColor;
        if (colorStateList != null) {
            bundle.putParcelable(KEY_TEXT_COLOR_STATE, colorStateList);
        }
        bundle.putFloat(KEY_TEXT_SIZE_STATE, this.mTextSize);
        bundle.putInt(KEY_LABEL_WIDTH_STATE, this.mLabelWidth);
        bundle.putInt(KEY_LABEL_HEIGHT_STATE, this.mLabelHeight);
        bundle.putInt(KEY_LABEL_GRAVITY_STATE, this.mLabelGravity);
        bundle.putIntArray(KEY_PADDING_STATE, new int[]{this.mTextPaddingLeft, this.mTextPaddingTop, this.mTextPaddingRight, this.mTextPaddingBottom});
        bundle.putInt(KEY_WORD_MARGIN_STATE, this.mWordMargin);
        bundle.putInt(KEY_LINE_MARGIN_STATE, this.mLineMargin);
        bundle.putInt(KEY_SELECT_TYPE_STATE, this.mSelectType.value);
        bundle.putInt(KEY_MAX_SELECT_STATE, this.mMaxSelect);
        bundle.putInt(KEY_MIN_SELECT_STATE, this.mMinSelect);
        bundle.putInt(KEY_MAX_LINES_STATE, this.mMaxLines);
        bundle.putInt(KEY_MAX_COLUMNS_STATE, this.mMaxColumns);
        bundle.putBoolean(KEY_INDICATOR_STATE, this.isIndicator);
        if (!this.mSelectLabels.isEmpty()) {
            bundle.putIntegerArrayList(KEY_SELECT_LABELS_STATE, this.mSelectLabels);
        }
        if (!this.mCompulsorys.isEmpty()) {
            bundle.putIntegerArrayList(KEY_COMPULSORY_LABELS_STATE, this.mCompulsorys);
        }
        bundle.putBoolean(KEY_SINGLE_LINE_STATE, this.isSingleLine);
        bundle.putBoolean(KEY_TEXT_STYLE_STATE, this.isTextBold);
        return bundle;
    }

    public void removeLabelFromDatas(String value) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            TextView textView = (TextView) getChildAt(i2);
            if (value.equals(textView.getText().toString())) {
                removeView(textView);
                return;
            }
        }
    }

    public void setColumns(int columns) {
        if (this.mColumns != columns) {
            this.mColumns = columns;
            requestLayout();
        }
    }

    public void setCompulsorys(List<Integer> positions) {
        if (this.mSelectType != SelectType.MULTI || positions == null) {
            return;
        }
        this.mCompulsorys.clear();
        this.mCompulsorys.addAll(positions);
        innerClearAllSelect();
        setSelects(positions);
    }

    public void setIndicator(boolean indicator) {
        this.isIndicator = indicator;
    }

    public void setLabelBackgroundColor(int color) {
        setLabelBackgroundDrawable(new ColorDrawable(color));
    }

    public void setLabelBackgroundDrawable(Drawable drawable) {
        this.mLabelBg = drawable;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((TextView) getChildAt(i2)).setBackgroundDrawable(this.mLabelBg.getConstantState().newDrawable());
        }
    }

    public void setLabelBackgroundResource(int resId) {
        setLabelBackgroundDrawable(getResources().getDrawable(resId));
    }

    public void setLabelGravity(int gravity) {
        if (this.mLabelGravity != gravity) {
            this.mLabelGravity = gravity;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ((TextView) getChildAt(i2)).setGravity(gravity);
            }
        }
    }

    public void setLabelTextColor(int color) {
        setLabelTextColor(ColorStateList.valueOf(color));
    }

    public void setLabelTextPadding(int left, int top2, int right, int bottom) {
        if (this.mTextPaddingLeft == left && this.mTextPaddingTop == top2 && this.mTextPaddingRight == right && this.mTextPaddingBottom == bottom) {
            return;
        }
        this.mTextPaddingLeft = left;
        this.mTextPaddingTop = top2;
        this.mTextPaddingRight = right;
        this.mTextPaddingBottom = bottom;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((TextView) getChildAt(i2)).setPadding(left, top2, right, bottom);
        }
    }

    public void setLabelTextSize(float size) {
        if (this.mTextSize != size) {
            this.mTextSize = size;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                ((TextView) getChildAt(i2)).setTextSize(0, size);
            }
        }
    }

    public void setLabelWidth(int width) {
        this.mLabelWidth = width;
    }

    public void setLabels(List<String> labels) {
        setLabels(labels, new LabelTextProvider<String>() { // from class: com.psychiatrygarden.widget.HistoryLabelsView.1
            @Override // com.psychiatrygarden.widget.HistoryLabelsView.LabelTextProvider
            public CharSequence getLabelText(TextView label, int position, String data) {
                return data.trim();
            }
        });
    }

    public void setLineMargin(int margin) {
        if (this.mLineMargin != margin) {
            this.mLineMargin = margin;
            requestLayout();
        }
    }

    public void setMaxColumns(int maxColumns) {
        if (this.mMaxColumns != maxColumns) {
            this.mMaxColumns = maxColumns;
            requestLayout();
        }
    }

    public void setMaxLines(int maxLines) {
        if (this.mMaxLines != maxLines) {
            this.mMaxLines = maxLines;
            requestLayout();
        }
    }

    public void setMaxSelect(int maxSelect) {
        if (this.mMaxSelect != maxSelect) {
            this.mMaxSelect = maxSelect;
            if (this.mSelectType == SelectType.MULTI) {
                innerClearAllSelect();
            }
        }
    }

    public void setMinSelect(int minSelect) {
        this.mMinSelect = minSelect;
    }

    public void setOnLabelClickListener(OnLabelClickListener l2) {
        this.mLabelClickListener = l2;
        ensureLabelClickable();
    }

    public void setOnLabelLongClickListener(OnLabelLongClickListener l2) {
        this.mLabelLongClickListener = l2;
        ensureLabelClickable();
    }

    public void setOnLabelSelectChangeListener(OnLabelSelectChangeListener l2) {
        this.mLabelSelectChangeListener = l2;
    }

    public void setOnSelectChangeIntercept(OnSelectChangeIntercept intercept) {
        this.mOnSelectChangeIntercept = intercept;
    }

    public void setScreenWidth(int width) {
        this.mScreenWidth = width;
    }

    public void setSelectType(SelectType selectType) {
        if (this.mSelectType != selectType) {
            this.mSelectType = selectType;
            innerClearAllSelect();
            if (this.mSelectType == SelectType.SINGLE_IRREVOCABLY) {
                setSelects(0);
            }
            if (this.mSelectType != SelectType.MULTI) {
                this.mCompulsorys.clear();
            }
            ensureLabelClickable();
        }
    }

    public void setSelects(List<Integer> positions) {
        if (positions != null) {
            int size = positions.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = positions.get(i2).intValue();
            }
            setSelects(iArr);
        }
    }

    public void setSingleLine(boolean isSingleLine) {
        if (this.isSingleLine != isSingleLine) {
            this.isSingleLine = isSingleLine;
            requestLayout();
        }
    }

    public void setTextBold(boolean isBold) {
        if (this.isTextBold != isBold) {
            this.isTextBold = isBold;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                TextView textView = (TextView) getChildAt(i2);
                textView.getPaint().setFakeBoldText(this.isTextBold);
                textView.invalidate();
            }
        }
    }

    public void setWordMargin(int margin) {
        if (this.mWordMargin != margin) {
            this.mWordMargin = margin;
            requestLayout();
        }
    }

    public void setLabelTextColor(ColorStateList color) {
        this.mTextColor = color;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((TextView) getChildAt(i2)).setTextColor(this.mTextColor);
        }
    }

    public <T> void setLabels(List<T> labels, LabelTextProvider<T> provider) {
        innerClearAllSelect();
        removeAllViews();
        this.mLabels.clear();
        if (labels != null) {
            this.mLabels.addAll(labels);
            int size = labels.size();
            for (int i2 = 0; i2 < size; i2++) {
                addLabel(labels.get(i2), i2, provider);
            }
            ensureLabelClickable();
        }
        if (this.mSelectType == SelectType.SINGLE_IRREVOCABLY) {
            setSelects(0);
        }
    }

    public void setSelects(int... positions) {
        if (this.mSelectType != SelectType.NONE) {
            ArrayList arrayList = new ArrayList();
            int childCount = getChildCount();
            SelectType selectType = this.mSelectType;
            int i2 = (selectType == SelectType.SINGLE || selectType == SelectType.SINGLE_IRREVOCABLY) ? 1 : this.mMaxSelect;
            for (int i3 : positions) {
                if (i3 < childCount) {
                    TextView textView = (TextView) getChildAt(i3);
                    if (!arrayList.contains(textView)) {
                        setLabelSelect(textView, true);
                        arrayList.add(textView);
                    }
                    if (i2 > 0 && arrayList.size() == i2) {
                        break;
                    }
                }
            }
            for (int i4 = 0; i4 < childCount; i4++) {
                TextView textView2 = (TextView) getChildAt(i4);
                if (!arrayList.contains(textView2)) {
                    setLabelSelect(textView2, false);
                }
            }
        }
    }

    public void setCompulsorys(int... positions) {
        if (this.mSelectType != SelectType.MULTI || positions == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(positions.length);
        for (int i2 : positions) {
            arrayList.add(Integer.valueOf(i2));
        }
        setCompulsorys(arrayList);
    }

    public HistoryLabelsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLabelWidth = -2;
        this.mLabelHeight = -2;
        this.mLabelGravity = 17;
        this.isSingleLine = false;
        this.isTextBold = false;
        this.mLabels = new ArrayList<>();
        this.mSelectLabels = new ArrayList<>();
        this.mCompulsorys = new ArrayList<>();
        this.mScreenWidth = 0;
        this.mContext = context;
        getAttrs(context, attrs);
        showEditPreview();
    }

    public HistoryLabelsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mLabelWidth = -2;
        this.mLabelHeight = -2;
        this.mLabelGravity = 17;
        this.isSingleLine = false;
        this.isTextBold = false;
        this.mLabels = new ArrayList<>();
        this.mSelectLabels = new ArrayList<>();
        this.mCompulsorys = new ArrayList<>();
        this.mScreenWidth = 0;
        this.mContext = context;
        getAttrs(context, attrs);
        showEditPreview();
    }
}
