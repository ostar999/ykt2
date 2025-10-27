package com.easefun.polyv.livecommon.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.PLVOnFocusDialog;
import com.easefun.polyv.livecommon.ui.widget.expandmenu.utils.DpOrPxUtils;
import com.plv.foundationsdk.utils.PLVAppUtils;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class PLVConfirmDialog {
    private final WeakReference<Context> contextWeakReference;
    private PLVOnFocusDialog dialog;
    private TextView plvConfirmContent;
    private TextView plvConfirmTitle;
    private TextView plvLeftConfirmBtn;
    private TextView plvRightConfirmBtn;

    @Nullable
    private View plvSplitView;

    public static class Builder {
        protected final Param param;

        public static class Param {
            private static final OnClickListener hideDialogOnClickListener = new OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVConfirmDialog.Builder.Param.1
                @Override // com.easefun.polyv.livecommon.ui.widget.PLVConfirmDialog.OnClickListener
                public void onClick(DialogInterface dialog, View v2) {
                    dialog.dismiss();
                }
            };
            public String content;
            public Context context;
            public OnClickListener rightBtnListener;
            public String rightBtnText;
            public String title;
            public int titleVisibility = 0;
            public int contentVisibility = 0;
            public String leftBtnText = PLVAppUtils.getString(R.string.plv_common_dialog_click_wrong);
            public OnClickListener leftBtnListener = hideDialogOnClickListener;
            public boolean showLeftButton = true;
            public boolean cancelable = true;

            public PLVConfirmDialog initTo(PLVConfirmDialog dialog) {
                dialog.setTitle(this.title);
                dialog.setContent(this.content);
                dialog.setTitleVisibility(this.titleVisibility);
                dialog.setContentVisibility(this.contentVisibility);
                dialog.setLeftButtonText(this.leftBtnText);
                dialog.setRightButtonText(this.rightBtnText);
                dialog.setLeftBtnListener(this.leftBtnListener);
                dialog.setRightBtnListener(this.rightBtnListener);
                dialog.setIsNeedLeftBtn(this.showLeftButton);
                dialog.setCancelable(this.cancelable);
                return dialog;
            }
        }

        public Builder(@NonNull Context context) {
            Param param = new Param();
            this.param = param;
            param.context = context;
        }

        public static Builder context(@NonNull Context context) {
            return new Builder(context);
        }

        public PLVConfirmDialog build() {
            Param param = this.param;
            return param.initTo(new PLVConfirmDialog(param.context));
        }

        public Builder setCancelable(boolean cancelable) {
            this.param.cancelable = cancelable;
            return this;
        }

        public Builder setContent(String content) {
            this.param.content = content;
            return this;
        }

        public Builder setContentVisibility(int visibility) {
            this.param.contentVisibility = visibility;
            return this;
        }

        public Builder setIsNeedLeftBtn(boolean show) {
            this.param.showLeftButton = show;
            return this;
        }

        public Builder setLeftBtnListener(OnClickListener listener) {
            this.param.leftBtnListener = listener;
            return this;
        }

        public Builder setLeftButtonText(String leftBtnText) {
            this.param.leftBtnText = leftBtnText;
            return this;
        }

        public Builder setRightBtnListener(OnClickListener listener) {
            this.param.rightBtnListener = listener;
            return this;
        }

        public Builder setRightButtonText(String rightBtnText) {
            this.param.rightBtnText = rightBtnText;
            return this;
        }

        public Builder setTitle(String title) {
            this.param.title = title;
            return this;
        }

        public Builder setTitleVisibility(int visibility) {
            this.param.titleVisibility = visibility;
            return this;
        }

        public void show() {
            build().show();
        }

        public Builder setContent(int contentResId) {
            Param param = this.param;
            param.content = param.context.getString(contentResId);
            return this;
        }

        public Builder setLeftButtonText(int leftBtnTextResId) {
            Param param = this.param;
            param.leftBtnText = param.context.getString(leftBtnTextResId);
            return this;
        }

        public Builder setRightButtonText(int rightBtnTextResId) {
            Param param = this.param;
            param.rightBtnText = param.context.getString(rightBtnTextResId);
            return this;
        }

        public Builder setTitle(int titleResId) {
            Param param = this.param;
            param.title = param.context.getString(titleResId);
            return this;
        }
    }

    public static abstract class OnClickListener implements View.OnClickListener {
        public abstract void onClick(DialogInterface dialog, View v2);

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
        }
    }

    public PLVConfirmDialog(Context context) {
        this.contextWeakReference = new WeakReference<>(context);
        View viewInflate = LayoutInflater.from(context).inflate(layoutId(), (ViewGroup) null, false);
        RelativeLayout relativeLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(DpOrPxUtils.dip2px(context, dialogWidthInDp()), -2);
        layoutParams.addRule(13);
        relativeLayout.addView(viewInflate, layoutParams);
        PLVOnFocusDialog pLVOnFocusDialog = new PLVOnFocusDialog(context);
        this.dialog = pLVOnFocusDialog;
        pLVOnFocusDialog.setContentView(relativeLayout);
        this.dialog.setCancelable(true);
        if (this.dialog.getWindow() != null) {
            this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        initView(viewInflate);
    }

    private void initView(View root) {
        this.plvConfirmTitle = (TextView) root.findViewById(confirmTitleId());
        this.plvConfirmContent = (TextView) root.findViewById(confirmContentId());
        this.plvLeftConfirmBtn = (TextView) root.findViewById(leftConfirmTextViewId());
        this.plvRightConfirmBtn = (TextView) root.findViewById(rightConfirmTextViewId());
        if (hasSplitView()) {
            this.plvSplitView = root.findViewById(splitViewId());
        }
        this.plvLeftConfirmBtn.setText(R.string.plv_common_dialog_click_wrong);
        this.plvLeftConfirmBtn.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVConfirmDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVConfirmDialog.this.hide();
            }
        });
    }

    @IdRes
    public int confirmContentId() {
        return R.id.plv_confirm_content;
    }

    @IdRes
    public int confirmTitleId() {
        return R.id.plv_confirm_title;
    }

    public float dialogWidthInDp() {
        return 228.0f;
    }

    public boolean hasSplitView() {
        return true;
    }

    public void hide() {
        this.dialog.dismiss();
    }

    public boolean isShowing() {
        return this.dialog.isShowing();
    }

    @LayoutRes
    public int layoutId() {
        return R.layout.plv_confirm_window_layout;
    }

    @IdRes
    public int leftConfirmTextViewId() {
        return R.id.plv_left_confirm_btn;
    }

    @IdRes
    public int rightConfirmTextViewId() {
        return R.id.plv_right_confirm_btn;
    }

    public PLVConfirmDialog setCancelable(boolean cancelable) {
        this.dialog.setCancelable(cancelable);
        return this;
    }

    public PLVConfirmDialog setContent(String content) {
        this.plvConfirmContent.setText(content);
        return this;
    }

    public PLVConfirmDialog setContentVisibility(int visibility) {
        this.plvConfirmContent.setVisibility(visibility);
        return this;
    }

    public PLVConfirmDialog setIsNeedLeftBtn(boolean isNeedLeftBtn) {
        if (isNeedLeftBtn) {
            View view = this.plvSplitView;
            if (view != null) {
                view.setVisibility(0);
            }
            this.plvLeftConfirmBtn.setVisibility(0);
        } else {
            View view2 = this.plvSplitView;
            if (view2 != null) {
                view2.setVisibility(8);
            }
            this.plvLeftConfirmBtn.setVisibility(8);
        }
        return this;
    }

    public PLVConfirmDialog setLeftBtnListener(final View.OnClickListener leftBtnListener) {
        this.plvLeftConfirmBtn.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVConfirmDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                View.OnClickListener onClickListener = leftBtnListener;
                if (onClickListener != null) {
                    if (onClickListener instanceof OnClickListener) {
                        ((OnClickListener) onClickListener).onClick(PLVConfirmDialog.this.dialog, v2);
                    } else {
                        onClickListener.onClick(v2);
                    }
                }
            }
        });
        return this;
    }

    public PLVConfirmDialog setLeftButtonText(String leftText) {
        this.plvLeftConfirmBtn.setText(leftText);
        return this;
    }

    public void setOnWindowFocusChangedListener(PLVOnFocusDialog.OnWindowFocusChangeListener listener) {
        this.dialog.setOnWindowFocusChangedListener(listener);
    }

    public PLVConfirmDialog setRightBtnListener(final View.OnClickListener rightBtnListener) {
        this.plvRightConfirmBtn.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVConfirmDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                View.OnClickListener onClickListener = rightBtnListener;
                if (onClickListener != null) {
                    if (onClickListener instanceof OnClickListener) {
                        ((OnClickListener) onClickListener).onClick(PLVConfirmDialog.this.dialog, v2);
                    } else {
                        onClickListener.onClick(v2);
                    }
                }
            }
        });
        return this;
    }

    public PLVConfirmDialog setRightButtonText(String rightText) {
        this.plvRightConfirmBtn.setText(rightText);
        return this;
    }

    public PLVConfirmDialog setTitle(String title) {
        this.plvConfirmTitle.setText(title);
        return this;
    }

    public PLVConfirmDialog setTitleVisibility(int visibility) {
        this.plvConfirmTitle.setVisibility(visibility);
        return this;
    }

    public void show() {
        WeakReference<Context> weakReference = this.contextWeakReference;
        if (weakReference == null || weakReference.get() == null || !(this.contextWeakReference.get() instanceof Activity) || ((Activity) this.contextWeakReference.get()).isFinishing()) {
            return;
        }
        this.dialog.show();
    }

    @IdRes
    public int splitViewId() {
        return R.id.plv_split_view;
    }

    public PLVConfirmDialog setContent(@StringRes int resId) {
        return setContent(this.dialog.getContext().getString(resId));
    }

    public PLVConfirmDialog setLeftButtonText(@StringRes int resId) {
        return setLeftButtonText(this.dialog.getContext().getString(resId));
    }

    public PLVConfirmDialog setRightButtonText(@StringRes int resId) {
        return setRightButtonText(this.dialog.getContext().getString(resId));
    }

    public PLVConfirmDialog setTitle(@StringRes int resId) {
        return setTitle(this.dialog.getContext().getString(resId));
    }
}
