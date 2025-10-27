package com.beizi.ad.internal.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import com.beizi.ad.R;
import com.beizi.ad.internal.g;
import com.beizi.ad.internal.network.ServerResponse;
import com.google.android.material.badge.BadgeDrawable;

/* loaded from: classes2.dex */
public class ViewUtil {
    public static void convertFromDPToPixels(Activity activity, int[] iArr) {
        float f2 = activity.getResources().getDisplayMetrics().density;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = (int) ((iArr[i2] * f2) + 0.5f);
        }
    }

    public static void convertFromPixelsToDP(Activity activity, int[] iArr) {
        float f2 = activity.getResources().getDisplayMetrics().density;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = (int) ((iArr[i2] / f2) + 0.5f);
        }
    }

    public static FrameLayout createAdImageView(Context context, ServerResponse.AdLogoInfo adLogoInfo) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setVisibility(4);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        if (adLogoInfo != null) {
            if (adLogoInfo.getType() == ServerResponse.AdLogoInfo.TYPE_PIC) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(85, 42, 83);
                layoutParams.setMargins(0, 0, 0, 0);
                imageView.setLayoutParams(layoutParams);
                imageView.setVisibility(0);
                ImageManager.with(context).load(adLogoInfo.getAdurl()).into(imageView);
                frameLayout.addView(imageView);
            } else if (adLogoInfo.getType() == ServerResponse.AdLogoInfo.TYPE_TEXT) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(context);
                appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_count_down_background));
                appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
                appCompatTextView.setTextSize(2, 12.0f);
                appCompatTextView.setGravity(17);
                appCompatTextView.setText(adLogoInfo.getAdurl());
                new FrameLayout.LayoutParams(85, 42, 83).setMargins(0, 0, 0, 0);
                frameLayout.addView(appCompatTextView);
            }
        }
        return frameLayout;
    }

    public static AppCompatTextView createCloseButton(Context context) {
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_close_background));
        appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
        appCompatTextView.setTextSize(2, 12.0f);
        appCompatTextView.setGravity(17);
        appCompatTextView.setText(R.string.skip_ad);
        g gVarA = g.a();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, BadgeDrawable.TOP_END);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, 5.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 10.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 42.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 10.0f, gVarA.k()));
        appCompatTextView.setLayoutParams(layoutParams);
        return appCompatTextView;
    }

    public static AppCompatTextView createCountDown(Context context, int i2) {
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_count_down_background));
        appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
        appCompatTextView.setTextSize(2, 12.0f);
        g gVarA = g.a();
        appCompatTextView.setGravity(17);
        appCompatTextView.setText(Integer.toString(i2));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) TypedValue.applyDimension(1, 30.0f, gVarA.k()), -2, 53);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, 5.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 10.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 5.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 10.0f, gVarA.k()));
        appCompatTextView.setLayoutParams(layoutParams);
        appCompatTextView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.utilities.ViewUtil.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        return appCompatTextView;
    }

    public static ImageView createImageCloseButton(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.banner_da_close);
        g gVarA = g.a();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(getValueInPixel(context, 18), getValueInPixel(context, 18), BadgeDrawable.TOP_END);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, 5.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 0.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 0.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 5.0f, gVarA.k()));
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    public static AppCompatTextView createInterstitialCountDown(Context context, int i2) {
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_count_down_interstitial_background));
        appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
        appCompatTextView.setTextSize(2, 12.0f);
        g gVarA = g.a();
        appCompatTextView.setGravity(17);
        appCompatTextView.setText(Integer.toString(i2));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) TypedValue.applyDimension(1, 30.0f, gVarA.k()), -2, 53);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, 0.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 0.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 0.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 0.0f, gVarA.k()));
        appCompatTextView.setLayoutParams(layoutParams);
        appCompatTextView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.utilities.ViewUtil.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        return appCompatTextView;
    }

    public static FrameLayout createLogoImageView(Context context, ServerResponse.AdLogoInfo adLogoInfo) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setVisibility(4);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, 17));
        if (adLogoInfo != null) {
            if (adLogoInfo.getType() == ServerResponse.AdLogoInfo.TYPE_PIC) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(42, 42, 85);
                layoutParams.setMargins(0, 0, 0, 0);
                imageView.setLayoutParams(layoutParams);
                imageView.setVisibility(0);
                ImageManager.with(context).load(adLogoInfo.getAdurl()).into(imageView);
                frameLayout.addView(imageView);
            } else if (adLogoInfo.getType() == ServerResponse.AdLogoInfo.TYPE_TEXT) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(context);
                appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_count_down_background));
                appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
                appCompatTextView.setTextSize(2, 12.0f);
                appCompatTextView.setGravity(17);
                appCompatTextView.setText(adLogoInfo.getAdurl());
                new FrameLayout.LayoutParams(42, 42, 85).setMargins(0, 0, 0, 0);
                frameLayout.addView(appCompatTextView);
            }
        }
        return frameLayout;
    }

    public static AppCompatImageView createMuteButton(Context context, boolean z2) {
        AppCompatImageView appCompatImageView = new AppCompatImageView(context);
        appCompatImageView.setImageResource(z2 ? R.drawable.voice_off : R.drawable.voice_on);
        g gVarA = g.a();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, BadgeDrawable.TOP_START);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, 5.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 15.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 15.0f, gVarA.k()), (int) TypedValue.applyDimension(1, 15.0f, gVarA.k()));
        appCompatImageView.setLayoutParams(layoutParams);
        return appCompatImageView;
    }

    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int[] getScreenSizeAsDP(Activity activity) {
        int[] screenSizeAsPixels = getScreenSizeAsPixels(activity);
        convertFromPixelsToDP(activity, screenSizeAsPixels);
        return screenSizeAsPixels;
    }

    public static int[] getScreenSizeAsPixels(Activity activity) {
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return new int[]{point.x, point.y};
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Context getTopContext(View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        if (parent != null) {
            boolean z2 = parent instanceof View;
            ViewParent parent2 = parent;
            if (z2) {
                while (parent2.getParent() != null && (parent2.getParent() instanceof View)) {
                    parent2 = parent2.getParent();
                }
                return ((View) parent2).getContext();
            }
        }
        return view.getContext() instanceof MutableContextWrapper ? ((MutableContextWrapper) view.getContext()).getBaseContext() : view.getContext();
    }

    public static int getValueInPixel(Context context, int i2) {
        return (int) ((i2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int px2dip(Context context, float f2) {
        return (int) ((f2 / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void removeChildFromParent(View view) {
        if (view == null || view.getParent() == null) {
            return;
        }
        ((ViewGroup) view.getParent()).removeView(view);
    }

    public static AppCompatTextView createCloseButton(Context context, int i2, int i3, int i4, int i5) {
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_close_background));
        appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
        appCompatTextView.setTextSize(2, 12.0f);
        appCompatTextView.setGravity(17);
        appCompatTextView.setText(R.string.skip_ad);
        g gVarA = g.a();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2, BadgeDrawable.TOP_END);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, i2 + 5, gVarA.k()), (int) TypedValue.applyDimension(1, i3 + 10, gVarA.k()), (int) TypedValue.applyDimension(1, i4 + 42, gVarA.k()), (int) TypedValue.applyDimension(1, i5 + 10, gVarA.k()));
        appCompatTextView.setLayoutParams(layoutParams);
        return appCompatTextView;
    }

    public static AppCompatTextView createCountDown(Context context, int i2, int i3, int i4, int i5, int i6) {
        AppCompatTextView appCompatTextView = new AppCompatTextView(context);
        appCompatTextView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_count_down_background));
        appCompatTextView.setTextColor(ContextCompat.getColorStateList(context, R.color.button_text_selector));
        appCompatTextView.setTextSize(2, 12.0f);
        g gVarA = g.a();
        appCompatTextView.setGravity(17);
        appCompatTextView.setText(Integer.toString(i2));
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) TypedValue.applyDimension(1, 30.0f, gVarA.k()), -2, 53);
        layoutParams.setMargins((int) TypedValue.applyDimension(1, i3 + 5, gVarA.k()), (int) TypedValue.applyDimension(1, i4 + 10, gVarA.k()), (int) TypedValue.applyDimension(1, i5 + 5, gVarA.k()), (int) TypedValue.applyDimension(1, i6 + 10, gVarA.k()));
        appCompatTextView.setLayoutParams(layoutParams);
        appCompatTextView.setOnClickListener(new View.OnClickListener() { // from class: com.beizi.ad.internal.utilities.ViewUtil.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        return appCompatTextView;
    }
}
