package com.psychiatrygarden.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class NewToast {

    public interface ViewMoreListener {
        void onViewClick();
    }

    public static void showLong(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        View viewInflate = View.inflate(context, R.layout.custom_toast, null);
        ((TextView) viewInflate.findViewById(R.id.tv_text)).setText(text);
        int iDp2px = SizeUtil.dp2px(context, text.length() <= 4 ? 32 : 12);
        viewInflate.setPadding(iDp2px, viewInflate.getPaddingTop(), iDp2px, viewInflate.getPaddingBottom());
        Toast toast = new Toast(ProjectApp.instance());
        toast.setGravity(81, 0, SizeUtil.dp2px(context, 162));
        toast.setDuration(1);
        toast.setView(viewInflate);
        toast.show();
    }

    public static void showLongViewJump(Context context, String text, final ViewMoreListener listener) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        View viewInflate = View.inflate(context, R.layout.custom_toast, null);
        viewInflate.setFocusable(true);
        viewInflate.setFocusableInTouchMode(true);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_text);
        viewInflate.findViewById(R.id.iv_icon).setVisibility(0);
        textView.setText(text);
        int iDp2px = SizeUtil.dp2px(context, text.length() <= 4 ? 32 : 12);
        viewInflate.setPadding(iDp2px, viewInflate.getPaddingTop(), iDp2px, viewInflate.getPaddingBottom());
        Toast toast = new Toast(ProjectApp.instance());
        toast.setGravity(81, 0, SizeUtil.dp2px(context, 162));
        toast.setDuration(1);
        toast.setView(viewInflate);
        if (listener != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.utils.s
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    listener.onViewClick();
                }
            });
        }
        toast.show();
    }

    public static void showShort(Context context, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        View viewInflate = View.inflate(context, R.layout.custom_toast, null);
        ((TextView) viewInflate.findViewById(R.id.tv_text)).setText(text);
        int iDp2px = SizeUtil.dp2px(context, text.length() <= 4 ? 32 : 12);
        viewInflate.setPadding(iDp2px, viewInflate.getPaddingTop(), iDp2px, viewInflate.getPaddingBottom());
        Toast toast = new Toast(ProjectApp.instance());
        toast.setGravity(81, 0, SizeUtil.dp2px(context, 162));
        toast.setDuration(0);
        toast.setView(viewInflate);
        toast.show();
    }

    public static Toast showShort(Context context, String text, int x2) {
        View viewInflate = View.inflate(context, R.layout.custom_toast, null);
        ((TextView) viewInflate.findViewById(R.id.tv_text)).setText(text);
        int iDp2px = SizeUtil.dp2px(context, text.length() <= 4 ? 32 : 12);
        viewInflate.setPadding(iDp2px, viewInflate.getPaddingTop(), iDp2px, viewInflate.getPaddingBottom());
        Toast toast = new Toast(ProjectApp.instance());
        toast.setGravity(81, 0, SizeUtil.dp2px(context, 162));
        toast.setDuration(0);
        toast.setView(viewInflate);
        return toast;
    }

    public static Toast showShort(Context context, int resId, int x2) {
        View viewInflate = View.inflate(context, R.layout.custom_toast, null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_text);
        String string = context.getString(resId);
        textView.setText(string);
        int iDp2px = SizeUtil.dp2px(context, string.length() <= 4 ? 32 : 12);
        viewInflate.setPadding(iDp2px, viewInflate.getPaddingTop(), iDp2px, viewInflate.getPaddingBottom());
        Toast toast = new Toast(ProjectApp.instance());
        toast.setGravity(81, 0, SizeUtil.dp2px(context, 162));
        toast.setDuration(0);
        toast.setView(viewInflate);
        return toast;
    }
}
