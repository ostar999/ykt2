package com.psychiatrygarden.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ToastUtil {
    public static void shortToast(Context context, String desc) {
        if (context == null) {
            return;
        }
        NewToast.showShort(context, desc);
    }

    public static void showToastView(Activity context, String text) {
        Toast toast = new Toast(context);
        View viewInflate = context.getLayoutInflater().inflate(R.layout.show_comment_tips_window, (ViewGroup) null);
        if (SkinManager.getCurrentSkinType(context) == 1) {
            ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_icon);
            Drawable drawable = ContextCompat.getDrawable(context, R.mipmap.ic_config_checked);
            if (drawable != null) {
                drawable.setColorFilter(Color.parseColor("#7380A9"), PorterDuff.Mode.SRC_IN);
                imageView.setImageDrawable(drawable);
            }
        }
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_content);
        if (SkinManager.getCurrentSkinType(context) == 1) {
            text = text.replace("white", "#7380A9").replace("red", "#B2575C");
        }
        if (Build.VERSION.SDK_INT >= 24) {
            textView.setText(Html.fromHtml(text, 0));
        } else {
            textView.setText(Html.fromHtml(text));
        }
        toast.setGravity(17, 0, 0);
        toast.setDuration(1);
        toast.setView(viewInflate);
        toast.show();
    }
}
