package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;

/* loaded from: classes.dex */
public interface ViewBinder {
    View bindView(Context context, int i2, Page page, ImageLoader imageLoader, OnPageClickListener onPageClickListener, View view, ViewGroup viewGroup, int i3);
}
