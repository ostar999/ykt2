package me.dkzwm.widget.srl.extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import me.dkzwm.widget.srl.ext.classic.R;
import me.dkzwm.widget.srl.utils.PixelUtl;

/* loaded from: classes9.dex */
public class ClassicConfig {
    private static final String SP_NAME = "sr_classic_last_update_time";
    private static final SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    private ClassicConfig() {
    }

    public static void createClassicViews(RelativeLayout relativeLayout) {
        TextView textView = new TextView(relativeLayout.getContext());
        textView.setId(R.id.sr_classic_title);
        textView.setTextSize(12.0f);
        textView.setTextColor(Color.parseColor("#333333"));
        TextView textView2 = new TextView(relativeLayout.getContext());
        textView2.setId(R.id.sr_classic_last_update);
        textView2.setTextSize(10.0f);
        textView2.setTextColor(Color.parseColor("#969696"));
        textView2.setVisibility(8);
        LinearLayout linearLayout = new LinearLayout(relativeLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setGravity(1);
        int i2 = R.id.sr_classic_text_container;
        linearLayout.setId(i2);
        linearLayout.addView(textView, new LinearLayout.LayoutParams(-2, -2));
        linearLayout.addView(textView2, new LinearLayout.LayoutParams(-2, -2));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13);
        relativeLayout.addView(linearLayout, layoutParams);
        View imageView = new ImageView(relativeLayout.getContext());
        imageView.setId(R.id.sr_classic_arrow);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        int iDp2px = PixelUtl.dp2px(relativeLayout.getContext(), 6.0f);
        layoutParams2.setMargins(iDp2px, iDp2px, iDp2px, iDp2px);
        layoutParams2.addRule(0, i2);
        layoutParams2.addRule(15);
        relativeLayout.addView(imageView, layoutParams2);
        View progressBar = new ProgressBar(relativeLayout.getContext(), null, android.R.attr.progressBarStyleSmallInverse);
        progressBar.setId(R.id.sr_classic_progress);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.setMargins(iDp2px, iDp2px, iDp2px, iDp2px);
        layoutParams3.addRule(0, i2);
        layoutParams3.addRule(15);
        relativeLayout.addView(progressBar, layoutParams3);
    }

    public static String getLastUpdateTime(@NonNull Context context, long j2, @NonNull String str) {
        if (j2 == -1 && !TextUtils.isEmpty(str)) {
            j2 = context.getSharedPreferences(SP_NAME, 0).getLong(str, -1L);
        }
        if (j2 == -1) {
            return null;
        }
        long time = new Date().getTime() - j2;
        int i2 = (int) (time / 1000);
        if (time < 0 || i2 <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(context.getString(R.string.sr_last_update));
        if (i2 < 60) {
            sb.append(i2);
            sb.append(context.getString(R.string.sr_seconds_ago));
        } else {
            int i3 = i2 / 60;
            if (i3 > 60) {
                int i4 = i3 / 60;
                if (i4 > 24) {
                    sb.append(sDataFormat.format(new Date(j2)));
                } else {
                    sb.append(i4);
                    sb.append(context.getString(R.string.sr_hours_ago));
                }
            } else {
                sb.append(i3);
                sb.append(context.getString(R.string.sr_minutes_ago));
            }
        }
        return sb.toString();
    }

    public static void updateTime(@NonNull Context context, String str, long j2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, 0);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        sharedPreferences.edit().putLong(str, j2).apply();
    }
}
