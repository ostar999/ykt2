package cn.lightsky.infiniteindicator.recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.lightsky.infiniteindicator.ImageLoader;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import cn.lightsky.infiniteindicator.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

/* loaded from: classes.dex */
public class BaseViewBinder implements ViewBinder {

    public static class ViewHolder {
        SkinGrakImagView iv_play;
        final SkinGrakImagView target;

        public ViewHolder(View view) {
            this.target = (SkinGrakImagView) view.findViewById(R.id.slider_image);
            this.iv_play = (SkinGrakImagView) view.findViewById(R.id.iv_play);
        }
    }

    @Override // cn.lightsky.infiniteindicator.recycle.ViewBinder
    public View bindView(Context context, final int i2, final Page page, ImageLoader imageLoader, final OnPageClickListener onPageClickListener, View view, ViewGroup viewGroup, int i3) {
        ViewHolder viewHolder;
        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.simple_slider_view, (ViewGroup) null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        SkinGrakImagView skinGrakImagView = viewHolder.target;
        if (skinGrakImagView != null) {
            skinGrakImagView.setmTheme(i3);
            if (page.data.equals("video")) {
                viewHolder.iv_play.setVisibility(8);
            } else {
                viewHolder.iv_play.setVisibility(8);
            }
            if (onPageClickListener != null) {
                viewHolder.target.setOnClickListener(new View.OnClickListener() { // from class: cn.lightsky.infiniteindicator.recycle.BaseViewBinder.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        onPageClickListener.onPageClick(i2, page);
                    }
                });
            }
            Glide.with(context).load(page.res).apply((BaseRequestOptions<?>) new RequestOptions().override(Integer.MIN_VALUE)).into(viewHolder.target);
        }
        return view;
    }
}
