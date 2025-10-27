package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;

/* loaded from: classes6.dex */
public class ViewHolder {
    private View mRootView;
    private SparseArray<View> mViews = new SparseArray<>();

    public ViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
        this.mRootView = inflater.inflate(layoutId, parent, false);
    }

    public <T extends View> T get(int i2) {
        T t2 = (T) this.mViews.get(i2);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) this.mRootView.findViewById(i2);
        this.mViews.put(i2, t3);
        return t3;
    }

    public View getRootView() {
        return this.mRootView;
    }

    public void loadImage(Context context, String url, int res_id) {
        Glide.with(context).load((Object) GlideUtils.generateUrl(url)).into((ImageView) get(res_id));
    }

    public void setOnClickListener(View.OnClickListener l2, int... ids) {
        if (ids == null) {
            return;
        }
        for (int i2 : ids) {
            get(i2).setOnClickListener(l2);
        }
    }

    public boolean setText(CharSequence text, @NonNull int res_id) {
        try {
            ((TextView) get(res_id)).setText(text);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean setText(@NonNull int res_id, CharSequence text) {
        return setText(text, res_id);
    }
}
