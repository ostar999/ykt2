package com.easefun.polyv.livecommon.ui.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.google.android.material.badge.BadgeDrawable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

/* loaded from: classes3.dex */
public class PLVCopyBoardPopupWindow {

    public static abstract class CopyBoardClickListener implements View.OnClickListener {
        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
        }

        public abstract void onClickAnswerButton();

        public abstract boolean onClickCopyButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void copy(PopupWindow popupWindow, Context context, String copyContent, View.OnClickListener listener) {
        popupWindow.dismiss();
        try {
            ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("Label", copyContent));
            if (listener instanceof CopyBoardClickListener ? true ^ ((CopyBoardClickListener) listener).onClickCopyButton() : true) {
                ToastUtils.showLong("复制成功");
            }
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    public static PopupWindow show(final View anchor, boolean isLeft, final String copyContent) {
        final PopupWindow popupWindow = new PopupWindow();
        View viewInflate = LayoutInflater.from(anchor.getContext()).inflate(R.layout.plv_copy_board_popup_layout, (ViewGroup) null, false);
        viewInflate.findViewById(R.id.long_press_copy_tv).setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVCopyBoardPopupWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVCopyBoardPopupWindow.copy(popupWindow, anchor.getContext(), copyContent, null);
            }
        });
        popupWindow.setContentView(viewInflate);
        popupWindow.setHeight(-2);
        popupWindow.setWidth(PLVScreenUtils.dip2px(anchor.getContext(), 96.0f));
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        int[] iArr = new int[2];
        anchor.getLocationInWindow(iArr);
        popupWindow.showAtLocation(anchor, BadgeDrawable.TOP_START, iArr[0] + ((isLeft ? anchor.getWidth() : -anchor.getWidth()) / 2), (iArr[1] + anchor.getHeight()) - ConvertUtils.dp2px(8.0f));
        return popupWindow;
    }

    public static PopupWindow showAndAnswer(final View anchor, boolean isLeft, @Nullable final String copyContent, final View.OnClickListener clickListener) {
        return showAndAnswer(anchor, isLeft, false, copyContent, clickListener);
    }

    public static PopupWindow showAndAnswer(final View anchor, boolean isLeft, boolean onlyShowCopyItem, @Nullable final String copyContent, final View.OnClickListener clickListener) {
        return showAndAnswer(anchor, isLeft, 0, onlyShowCopyItem, copyContent, R.drawable.plv_cp_ly_corner_bg, R.drawable.plv_inverted_triangle_layer_list, Color.parseColor("#F0F1F5"), clickListener);
    }

    public static PopupWindow showAndAnswer(final View anchor, boolean isLeft, int parentY, @Nullable final String copyContent, @DrawableRes int bgResId, @DrawableRes int triangleResId, @ColorInt int textColor, final View.OnClickListener clickListener) {
        return showAndAnswer(anchor, isLeft, parentY, false, copyContent, bgResId, triangleResId, textColor, clickListener);
    }

    public static PopupWindow showAndAnswer(final View anchor, boolean isLeft, int parentY, boolean onlyShowCopyItem, @Nullable final String copyContent, @DrawableRes int bgResId, @DrawableRes int triangleResId, @ColorInt int textColor, final View.OnClickListener clickListener) {
        final PopupWindow popupWindow = new PopupWindow();
        View viewInflate = LayoutInflater.from(anchor.getContext()).inflate(R.layout.plv_copy_answer_board_popup_layout, (ViewGroup) null, false);
        viewInflate.findViewById(R.id.copy_answer_parent_ly).setBackgroundResource(bgResId);
        View viewFindViewById = viewInflate.findViewById(R.id.long_press_copy_tv);
        ((TextView) viewFindViewById).setTextColor(textColor);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVCopyBoardPopupWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                PLVCopyBoardPopupWindow.copy(popupWindow, anchor.getContext(), copyContent, clickListener);
            }
        });
        View viewFindViewById2 = viewInflate.findViewById(R.id.split_view);
        View viewFindViewById3 = viewInflate.findViewById(R.id.long_press_answer_tv);
        ((TextView) viewFindViewById3).setTextColor(textColor);
        viewFindViewById3.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVCopyBoardPopupWindow.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v2) {
                popupWindow.dismiss();
                View.OnClickListener onClickListener = clickListener;
                if (onClickListener instanceof CopyBoardClickListener) {
                    ((CopyBoardClickListener) onClickListener).onClickAnswerButton();
                } else if (onClickListener != null) {
                    onClickListener.onClick(v2);
                }
            }
        });
        viewInflate.findViewById(R.id.inverted_triangle_view).setBackgroundResource(triangleResId);
        if (copyContent == null) {
            viewFindViewById.setVisibility(8);
            viewFindViewById2.setVisibility(8);
        }
        if (onlyShowCopyItem) {
            viewFindViewById3.setVisibility(8);
            viewFindViewById2.setVisibility(8);
        }
        viewInflate.measure(0, 0);
        int measuredWidth = viewInflate.getMeasuredWidth();
        popupWindow.setContentView(viewInflate);
        popupWindow.setHeight(-2);
        popupWindow.setWidth(measuredWidth);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        int[] iArr = new int[2];
        anchor.getLocationInWindow(iArr);
        popupWindow.showAtLocation(anchor, BadgeDrawable.TOP_START, (iArr[0] + ((isLeft ? anchor.getWidth() : -anchor.getWidth()) / 2)) - (measuredWidth / 2), (Math.max(iArr[1], parentY) - ConvertUtils.dp2px(42.0f)) - ConvertUtils.dp2px(4.0f));
        return popupWindow;
    }
}
