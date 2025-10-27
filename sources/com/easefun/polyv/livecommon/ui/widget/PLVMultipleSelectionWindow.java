package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;

/* loaded from: classes3.dex */
public class PLVMultipleSelectionWindow {
    private Context context;
    private OnCancelListener onCancelListener;
    private TextView plvMultipleSelectionCancelTv;
    private LinearLayout plvMultipleSelectionLl;
    private View plvMultipleSelectionMaskView;
    private View plvMultipleSelectionSeparatorLine;
    private PopupWindow popupWindow;
    private View rootView;
    private int selectionSeparatorLineColor = -1;
    private int selectionSeparatorLineHeightInPx = 0;

    public interface OnCancelListener {
        void onCancel();
    }

    public static class SelectionItem {
        public static final int DEFAULT_TEXT_SIZE_SP = 18;
        private int backgroundColor;
        private View.OnClickListener onClickListener;
        private CharSequence text;
        private int textColor;
        private int textSizeInSp;
        private int viewHeightInPx;
        public static final int DEFAULT_TEXT_COLOR = Color.parseColor("#ADADC0");
        public static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#2B2C35");

        public SelectionItem(CharSequence text) {
            this(text, 18);
        }

        public SelectionItem onClick(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public SelectionItem setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public SelectionItem setText(CharSequence text) {
            this.text = text;
            return this;
        }

        public SelectionItem setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public SelectionItem setTextSizeInSp(int textSizeInSp) {
            this.textSizeInSp = textSizeInSp;
            return this;
        }

        public SelectionItem setViewHeightInPx(int viewHeightInPx) {
            this.viewHeightInPx = viewHeightInPx;
            return this;
        }

        public SelectionItem(CharSequence text, int textSizeInSp) {
            this(text, textSizeInSp, DEFAULT_TEXT_COLOR);
        }

        public SelectionItem(CharSequence text, int textSizeInSp, @ColorInt int textColor) {
            this(text, textSizeInSp, textColor, DEFAULT_BACKGROUND_COLOR);
        }

        public SelectionItem(CharSequence text, int textSizeInSp, @ColorInt int textColor, @ColorInt int backgroundColor) {
            this.viewHeightInPx = -3;
            setText(text);
            setTextSizeInSp(textSizeInSp);
            setTextColor(textColor);
            setBackgroundColor(backgroundColor);
        }
    }

    public PLVMultipleSelectionWindow(Context context) {
        this.context = context;
        initView();
    }

    private void findView() {
        this.plvMultipleSelectionMaskView = this.rootView.findViewById(R.id.plv_multiple_selection_mask_view);
        this.plvMultipleSelectionLl = (LinearLayout) this.rootView.findViewById(R.id.plv_multiple_selection_ll);
        this.plvMultipleSelectionSeparatorLine = this.rootView.findViewById(R.id.plv_multiple_selection_separator_line);
        this.plvMultipleSelectionCancelTv = (TextView) this.rootView.findViewById(R.id.plv_multiple_selection_cancel_tv);
    }

    private void initView() {
        this.rootView = LayoutInflater.from(this.context).inflate(R.layout.plv_multiple_selection_window_layout, (ViewGroup) null);
        findView();
        this.plvMultipleSelectionCancelTv.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVMultipleSelectionWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVMultipleSelectionWindow.this.hide();
                if (PLVMultipleSelectionWindow.this.onCancelListener != null) {
                    PLVMultipleSelectionWindow.this.onCancelListener.onCancel();
                }
            }
        });
        PopupWindow popupWindow = new PopupWindow();
        this.popupWindow = popupWindow;
        popupWindow.setContentView(this.rootView);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setWidth(-1);
        this.popupWindow.setHeight(-1);
    }

    public PLVMultipleSelectionWindow addSelectionItem(final SelectionItem selectionItem) {
        if (this.selectionSeparatorLineHeightInPx > 0 && this.plvMultipleSelectionLl.getChildCount() > 0) {
            View view = new View(this.context);
            view.setLayoutParams(new ViewGroup.LayoutParams(-1, this.selectionSeparatorLineHeightInPx));
            view.setBackgroundColor(this.selectionSeparatorLineColor);
            this.plvMultipleSelectionLl.addView(view);
        }
        AppCompatTextView appCompatTextView = new AppCompatTextView(this.context);
        int iDip2px = selectionItem.viewHeightInPx;
        if (iDip2px < -2) {
            iDip2px = DpOrPxUtils.dip2px(this.context, 48.0f);
        }
        appCompatTextView.setLayoutParams(new ViewGroup.LayoutParams(-1, iDip2px));
        appCompatTextView.setText(selectionItem.text);
        appCompatTextView.setTextSize(selectionItem.textSizeInSp);
        appCompatTextView.setTextColor(selectionItem.textColor);
        appCompatTextView.setGravity(17);
        appCompatTextView.setBackgroundColor(selectionItem.backgroundColor);
        appCompatTextView.setOnClickListener(selectionItem.onClickListener);
        this.plvMultipleSelectionLl.addView(appCompatTextView);
        return this;
    }

    public PLVMultipleSelectionWindow cancelable(boolean cancelable) {
        if (cancelable) {
            this.plvMultipleSelectionSeparatorLine.setVisibility(0);
            this.plvMultipleSelectionCancelTv.setVisibility(0);
        } else {
            this.plvMultipleSelectionSeparatorLine.setVisibility(8);
            this.plvMultipleSelectionCancelTv.setVisibility(8);
        }
        return this;
    }

    public void hide() {
        this.popupWindow.dismiss();
    }

    public PLVMultipleSelectionWindow onCancel(@Nullable OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return this;
    }

    public PLVMultipleSelectionWindow removeAllItems() {
        this.plvMultipleSelectionLl.removeAllViews();
        return this;
    }

    public PLVMultipleSelectionWindow setCancelButtonText(CharSequence text) {
        this.plvMultipleSelectionCancelTv.setText(text);
        return this;
    }

    public PLVMultipleSelectionWindow setCancelButtonTextColor(@ColorInt int color) {
        this.plvMultipleSelectionCancelTv.setTextColor(color);
        return this;
    }

    public PLVMultipleSelectionWindow setCancelButtonTextSizeInSp(int textSizeInSp) {
        this.plvMultipleSelectionCancelTv.setTextSize(textSizeInSp);
        return this;
    }

    public PLVMultipleSelectionWindow setCancelSeparateLineColor(@ColorInt int color) {
        this.plvMultipleSelectionSeparatorLine.setBackgroundColor(color);
        return this;
    }

    public PLVMultipleSelectionWindow setCancelSeparateLineHeightInPx(int heightInPx) {
        ViewGroup.LayoutParams layoutParams = this.plvMultipleSelectionSeparatorLine.getLayoutParams();
        layoutParams.height = heightInPx;
        this.plvMultipleSelectionSeparatorLine.setLayoutParams(layoutParams);
        return this;
    }

    public PLVMultipleSelectionWindow setMaskColor(@ColorInt int maskColor) {
        this.plvMultipleSelectionMaskView.setBackgroundColor(maskColor);
        return this;
    }

    public PLVMultipleSelectionWindow setSelectionSeparatorLineColor(@ColorInt int selectionSeparatorLineColor) {
        this.selectionSeparatorLineColor = selectionSeparatorLineColor;
        return this;
    }

    public PLVMultipleSelectionWindow setSelectionSeparatorLineHeightInPx(int selectionSeparatorLineHeightInPx) {
        this.selectionSeparatorLineHeightInPx = selectionSeparatorLineHeightInPx;
        return this;
    }

    public void show(View parent) {
        this.popupWindow.showAtLocation(parent, 17, 0, 0);
    }
}
