package com.psychiatrygarden.activity.purchase.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ViewHolder {
    private View mConVertView;
    private int mPosition;
    private SparseArray<View> myViews = new SparseArray<>();

    public ViewHolder(Context mContext, ViewGroup parent, int LayoutId, int position) {
        this.mPosition = position;
        View viewInflate = LayoutInflater.from(mContext).inflate(LayoutId, parent, false);
        this.mConVertView = viewInflate;
        viewInflate.setTag(this);
    }

    public static ViewHolder get(Context mcontext, View convertView, ViewGroup mparent, int selectDialogItem, int position) {
        if (convertView == null) {
            return new ViewHolder(mcontext, mparent, selectDialogItem, position);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mPosition = position;
        return viewHolder;
    }

    public View getConvertView() {
        return this.mConVertView;
    }

    public <T extends View> T getView(int i2) {
        T t2 = (T) this.myViews.get(i2);
        if (t2 != null) {
            return t2;
        }
        T t3 = (T) this.mConVertView.findViewById(i2);
        this.myViews.put(i2, t3);
        return t3;
    }

    public ViewHolder setImageHeadUrl(int viewId, String url) {
        ImageView imageView = (ImageView) getView(viewId);
        new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.personal_headimg_icon).showImageForEmptyUri(R.drawable.personal_headimg_icon).showImageOnFail(R.drawable.personal_headimg_icon).cacheInMemory(true).cacheOnDisc(true).build();
        GlideUtils.loadImage(imageView.getContext(), url, imageView, R.drawable.personal_headimg_icon, R.drawable.personal_headimg_icon);
        return this;
    }

    public ViewHolder setImageUrl(int viewId, String url) {
        ImageView imageView = (ImageView) getView(viewId);
        GlideUtils.loadImage(imageView.getContext(), url, imageView);
        return this;
    }

    public ViewHolder setText(int viewId, String txt) {
        ((TextView) getView(viewId)).setText(txt);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int color) {
        ((TextView) getView(viewId)).setTextColor(color);
        return this;
    }

    public ViewHolder setText(int viewId, CharSequence txt) {
        ((TextView) getView(viewId)).setText(txt);
        return this;
    }
}
