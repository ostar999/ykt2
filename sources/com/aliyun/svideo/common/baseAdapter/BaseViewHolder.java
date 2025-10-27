package com.aliyun.svideo.common.baseAdapter;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/* loaded from: classes2.dex */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private BaseQuickAdapter adapter;
    private Object associatedObject;
    private final LinkedHashSet<Integer> childClickViewIds;
    private final LinkedHashSet<Integer> itemChildLongClickViewIds;
    private final HashSet<Integer> nestViews;
    private final SparseArray<View> views;

    public BaseViewHolder(View view) {
        super(view);
        this.views = new SparseArray<>();
        this.childClickViewIds = new LinkedHashSet<>();
        this.itemChildLongClickViewIds = new LinkedHashSet<>();
        this.nestViews = new HashSet<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getClickPosition() {
        if (getLayoutPosition() >= this.adapter.getHeaderLayoutCount()) {
            return getLayoutPosition() - this.adapter.getHeaderLayoutCount();
        }
        return 0;
    }

    public BaseViewHolder addOnClickListener(@IdRes int... iArr) {
        for (int i2 : iArr) {
            this.childClickViewIds.add(Integer.valueOf(i2));
            View view = getView(i2);
            if (view != null) {
                if (!view.isClickable()) {
                    view.setClickable(true);
                }
                view.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.BaseViewHolder.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        if (BaseViewHolder.this.adapter.getOnItemChildClickListener() != null) {
                            BaseViewHolder.this.adapter.getOnItemChildClickListener().onItemChildClick(BaseViewHolder.this.adapter, view2, BaseViewHolder.this.getClickPosition());
                        }
                    }
                });
            }
        }
        return this;
    }

    public BaseViewHolder addOnLongClickListener(@IdRes int... iArr) {
        for (int i2 : iArr) {
            this.itemChildLongClickViewIds.add(Integer.valueOf(i2));
            View view = getView(i2);
            if (view != null) {
                if (!view.isLongClickable()) {
                    view.setLongClickable(true);
                }
                view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.BaseViewHolder.2
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view2) {
                        return BaseViewHolder.this.adapter.getOnItemChildLongClickListener() != null && BaseViewHolder.this.adapter.getOnItemChildLongClickListener().onItemChildLongClick(BaseViewHolder.this.adapter, view2, BaseViewHolder.this.getClickPosition());
                    }
                });
            }
        }
        return this;
    }

    public Object getAssociatedObject() {
        return this.associatedObject;
    }

    public HashSet<Integer> getChildClickViewIds() {
        return this.childClickViewIds;
    }

    public HashSet<Integer> getItemChildLongClickViewIds() {
        return this.itemChildLongClickViewIds;
    }

    public Set<Integer> getNestViews() {
        return this.nestViews;
    }

    public <T extends View> T getView(@IdRes int i2) {
        T t2 = (T) this.views.get(i2);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) this.itemView.findViewById(i2);
        this.views.put(i2, t3);
        return t3;
    }

    public BaseViewHolder linkify(@IdRes int i2) {
        Linkify.addLinks((TextView) getView(i2), 15);
        return this;
    }

    public BaseViewHolder setAdapter(@IdRes int i2, Adapter adapter) {
        ((AdapterView) getView(i2)).setAdapter(adapter);
        return this;
    }

    public BaseViewHolder setAlpha(@IdRes int i2, float f2) {
        getView(i2).setAlpha(f2);
        return this;
    }

    public void setAssociatedObject(Object obj) {
        this.associatedObject = obj;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int i2, @ColorInt int i3) {
        getView(i2).setBackgroundColor(i3);
        return this;
    }

    public BaseViewHolder setBackgroundRes(@IdRes int i2, @DrawableRes int i3) {
        getView(i2).setBackgroundResource(i3);
        return this;
    }

    public BaseViewHolder setChecked(@IdRes int i2, boolean z2) {
        KeyEvent.Callback view = getView(i2);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(z2);
        }
        return this;
    }

    public BaseViewHolder setEnabled(@IdRes int i2, boolean z2) {
        getView(i2).setEnabled(z2);
        return this;
    }

    public BaseViewHolder setGone(@IdRes int i2, boolean z2) {
        getView(i2).setVisibility(z2 ? 0 : 8);
        return this;
    }

    public BaseViewHolder setImageBitmap(@IdRes int i2, Bitmap bitmap) {
        ((ImageView) getView(i2)).setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setImageDrawable(@IdRes int i2, Drawable drawable) {
        ((ImageView) getView(i2)).setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int i2, @DrawableRes int i3) {
        ((ImageView) getView(i2)).setImageResource(i3);
        return this;
    }

    public BaseViewHolder setMax(@IdRes int i2, int i3) {
        ((ProgressBar) getView(i2)).setMax(i3);
        return this;
    }

    public BaseViewHolder setNestView(@IdRes int... iArr) {
        for (int i2 : iArr) {
            this.nestViews.add(Integer.valueOf(i2));
        }
        addOnClickListener(iArr);
        addOnLongClickListener(iArr);
        return this;
    }

    public BaseViewHolder setOnCheckedChangeListener(@IdRes int i2, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        ((CompoundButton) getView(i2)).setOnCheckedChangeListener(onCheckedChangeListener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(@IdRes int i2, AdapterView.OnItemLongClickListener onItemLongClickListener) {
        ((AdapterView) getView(i2)).setOnItemLongClickListener(onItemLongClickListener);
        return this;
    }

    public BaseViewHolder setOnItemSelectedClickListener(@IdRes int i2, AdapterView.OnItemSelectedListener onItemSelectedListener) {
        ((AdapterView) getView(i2)).setOnItemSelectedListener(onItemSelectedListener);
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int i2, int i3) {
        ((ProgressBar) getView(i2)).setProgress(i3);
        return this;
    }

    public BaseViewHolder setRating(@IdRes int i2, float f2) {
        ((RatingBar) getView(i2)).setRating(f2);
        return this;
    }

    public BaseViewHolder setTag(@IdRes int i2, Object obj) {
        getView(i2).setTag(obj);
        return this;
    }

    public BaseViewHolder setText(@IdRes int i2, CharSequence charSequence) {
        ((TextView) getView(i2)).setText(charSequence);
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int i2, @ColorInt int i3) {
        ((TextView) getView(i2)).setTextColor(i3);
        return this;
    }

    public BaseViewHolder setTypeface(@IdRes int i2, Typeface typeface) {
        TextView textView = (TextView) getView(i2);
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | 128);
        return this;
    }

    public BaseViewHolder setVisible(@IdRes int i2, boolean z2) {
        getView(i2).setVisibility(z2 ? 0 : 4);
        return this;
    }

    public BaseViewHolder setAdapter(BaseQuickAdapter baseQuickAdapter) {
        this.adapter = baseQuickAdapter;
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int i2, int i3, int i4) {
        ProgressBar progressBar = (ProgressBar) getView(i2);
        progressBar.setMax(i4);
        progressBar.setProgress(i3);
        return this;
    }

    public BaseViewHolder setRating(@IdRes int i2, float f2, int i3) {
        RatingBar ratingBar = (RatingBar) getView(i2);
        ratingBar.setMax(i3);
        ratingBar.setRating(f2);
        return this;
    }

    public BaseViewHolder setTag(@IdRes int i2, int i3, Object obj) {
        getView(i2).setTag(i3, obj);
        return this;
    }

    public BaseViewHolder setText(@IdRes int i2, @StringRes int i3) {
        ((TextView) getView(i2)).setText(i3);
        return this;
    }

    public BaseViewHolder setTypeface(Typeface typeface, int... iArr) {
        for (int i2 : iArr) {
            TextView textView = (TextView) getView(i2);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | 128);
        }
        return this;
    }
}
