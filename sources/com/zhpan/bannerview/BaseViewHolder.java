package com.zhpan.bannerview;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes8.dex */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    public BaseViewHolder(@NonNull View view) {
        super(view);
        this.mViews = new SparseArray<>();
    }

    @Deprecated
    public void bindData(T t2, int i2, int i3) {
    }

    public <V extends View> V findViewById(int i2) {
        V v2 = (V) this.mViews.get(i2);
        if (v2 != null) {
            return v2;
        }
        V v3 = (V) this.itemView.findViewById(i2);
        this.mViews.put(i2, v3);
        return v3;
    }

    public void setBackgroundColor(int i2, @ColorInt int i3) {
        findViewById(i2).setBackgroundColor(i3);
    }

    public void setBackgroundResource(int i2, @DrawableRes int i3) {
        findViewById(i2).setBackgroundResource(i3);
    }

    public void setImageBitmap(@IdRes int i2, Bitmap bitmap) {
        ((ImageView) findViewById(i2)).setImageBitmap(bitmap);
    }

    public void setImageDrawable(@IdRes int i2, Drawable drawable) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById instanceof ImageView) {
            ((ImageView) viewFindViewById).setImageDrawable(drawable);
        }
    }

    public void setImageResource(@IdRes int i2, @DrawableRes int i3) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById instanceof ImageView) {
            ((ImageView) viewFindViewById).setImageResource(i3);
        }
    }

    public void setOnClickListener(int i2, View.OnClickListener onClickListener) {
        findViewById(i2).setOnClickListener(onClickListener);
    }

    public void setText(int i2, CharSequence charSequence) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setText(charSequence);
        }
    }

    public void setTextColor(int i2, @ColorInt int i3) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setTextColor(i3);
        }
    }

    public void setTextColorRes(@IdRes int i2, @ColorRes int i3) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setTextColor(ContextCompat.getColor(this.itemView.getContext(), i3));
        }
    }

    public void setVisibility(@IdRes int i2, int i3) {
        findViewById(i2).setVisibility(i3);
    }

    public void setText(int i2, @StringRes int i3) {
        View viewFindViewById = findViewById(i2);
        if (viewFindViewById instanceof TextView) {
            ((TextView) viewFindViewById).setText(i3);
        }
    }
}
