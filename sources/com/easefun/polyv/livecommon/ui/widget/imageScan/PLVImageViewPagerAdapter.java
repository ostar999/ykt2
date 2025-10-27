package com.easefun.polyv.livecommon.ui.widget.imageScan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVUrlTag;
import com.easefun.polyv.livecommon.ui.widget.imageScan.PLVChatImageContainerWidget;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVImageViewPagerAdapter<D extends PLVUrlTag, V extends PLVChatImageContainerWidget> extends PagerAdapter {
    private Context context;
    private List<D> datas;
    private View.OnClickListener onClickListener;
    private LinkedList<V> recycledViews = new LinkedList<>();

    public PLVImageViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void bindData(List<D> datas) {
        this.datas = datas;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup viewGroup, int i2, @NonNull Object obj) {
        PLVChatImageContainerWidget pLVChatImageContainerWidget = (PLVChatImageContainerWidget) obj;
        viewGroup.removeView(pLVChatImageContainerWidget);
        this.recycledViews.addLast(pLVChatImageContainerWidget);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.datas.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(@NonNull Object object) {
        return -2;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        V vRemoveFirst = !this.recycledViews.isEmpty() ? this.recycledViews.removeFirst() : new PLVChatImageContainerWidget(this.context);
        vRemoveFirst.setOnImgClickListener(this.onClickListener);
        vRemoveFirst.setData(this.datas.get(position), position);
        container.addView(vRemoveFirst);
        return vRemoveFirst;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void setOnImgClickListener(View.OnClickListener listener) {
        this.onClickListener = listener;
    }
}
