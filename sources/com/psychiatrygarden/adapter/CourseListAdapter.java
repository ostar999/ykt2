package com.psychiatrygarden.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.CourseList2Bean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseListAdapter extends BaseQuickAdapter<CourseList2Bean.DataBean, BaseViewHolder> {
    public String c_id;
    public OnItemChildClickListenr onItemChildClickListenr;

    /* renamed from: com.psychiatrygarden.adapter.CourseListAdapter$1, reason: invalid class name */
    public class AnonymousClass1 extends BaseQuickAdapter<CourseList2Bean.DataBean.DataChildBean, BaseViewHolder> {
        public AnonymousClass1(int layoutResId, List data) {
            super(layoutResId, data);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(CourseList2Bean.DataBean.DataChildBean dataChildBean, View view) {
            CourseListAdapter.this.onItemChildClickListenr.onItemChildClickMethod(dataChildBean);
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NonNull BaseViewHolder helper, final CourseList2Bean.DataBean.DataChildBean childBean) {
            RoundedImageView roundedImageView = (RoundedImageView) helper.getView(R.id.img);
            ImageView imageView = (ImageView) helper.getView(R.id.laybelimg);
            TextView textView = (TextView) helper.getView(R.id.titleTv);
            roundedImageView.setLayoutParams(new RelativeLayout.LayoutParams((CommonUtil.getScreenWidth(getContext()) - (CommonUtil.dip2px(getContext(), 10.0f) * 4)) / 3, (CommonUtil.getScreenWidth(getContext()) - (CommonUtil.dip2px(getContext(), 10.0f) * 4)) / 3));
            if ("".equals(childBean.getLabel())) {
                imageView.setVisibility(8);
            } else {
                GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(childBean.getLabel())).override(Integer.MIN_VALUE).into(imageView);
                imageView.setVisibility(0);
            }
            textView.setText(childBean.getTitle());
            GlideUtils.loadImage(getContext(), childBean.getCover_img(), roundedImageView);
            roundedImageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.q3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f14902c.lambda$convert$0(childBean, view);
                }
            });
        }
    }

    public interface OnItemChildClickListenr {
        void onItemChildClickMethod(CourseList2Bean.DataBean.DataChildBean childBean);

        void onItemClickMethod(String c_id, String title);
    }

    public CourseListAdapter(String c_id) {
        super(R.layout.layout_courselist_item);
        this.c_id = c_id;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(CourseList2Bean.DataBean dataBean, View view) {
        this.onItemChildClickListenr.onItemClickMethod(dataBean.getC_id() + "", dataBean.getLabel() + "");
    }

    public void setOnItemChildClickListenr(OnItemChildClickListenr onItemChildClickListenr) {
        this.onItemChildClickListenr = onItemChildClickListenr;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder helper, final CourseList2Bean.DataBean item) {
        TextView textView = (TextView) helper.getView(R.id.labelTv);
        RelativeLayout relativeLayout = (RelativeLayout) helper.getView(R.id.relview);
        TextView textView2 = (TextView) helper.getView(R.id.morelistTv);
        RecyclerView recyclerView = (RecyclerView) helper.getView(R.id.gridview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if (this.c_id.equals("")) {
            relativeLayout.setVisibility(0);
        } else {
            relativeLayout.setVisibility(8);
        }
        textView.setText(item.getLabel());
        if (item.getMore() == 0) {
            textView2.setVisibility(8);
        } else {
            textView2.setVisibility(0);
        }
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.p3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14865c.lambda$convert$0(item, view);
            }
        });
        recyclerView.setAdapter(new AnonymousClass1(R.layout.layout_courselist_child_item, item.getItems()));
    }
}
