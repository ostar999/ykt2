package com.psychiatrygarden.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.courselist.CourseSubjectActivity;
import com.psychiatrygarden.bean.SpecialBean;
import com.psychiatrygarden.bean.SpecialItemBean;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class AllCourseSubjectBannerAdapter extends BaseQuickAdapter<SpecialBean, BaseViewHolder> {
    public AllCourseSubjectBannerAdapter() {
        super(R.layout.item_all_course_subject_banner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$convert$0(SpecialBean specialBean, View view) {
        getContext().startActivity(CourseSubjectActivity.newIntent(getContext(), specialBean.getId(), specialBean.getTitle()));
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, final SpecialBean item) {
        TextView textView = (TextView) holder.getView(R.id.allCourseSubjectTitle);
        ImageView imageView = (ImageView) holder.getView(R.id.imageSubjectBanner);
        ImageView imageView2 = (ImageView) holder.getView(R.id.imageSubjectBanner2);
        TextView textView2 = (TextView) holder.getView(R.id.tvCourseName1);
        TextView textView3 = (TextView) holder.getView(R.id.tvCourseName2);
        TextView textView4 = (TextView) holder.getView(R.id.tvCourseName3);
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.layoutItem3);
        LinearLayout linearLayout2 = (LinearLayout) holder.getView(R.id.layoutItem2);
        LinearLayout linearLayout3 = (LinearLayout) holder.getView(R.id.layoutItem1);
        List<String> cover = item.getCover();
        if (cover == null || cover.isEmpty()) {
            imageView2.setVisibility(4);
            imageView.setVisibility(4);
        } else {
            if (cover.size() == 1) {
                GlideApp.with(getContext()).load(cover.get(0)).into(imageView);
                imageView2.setVisibility(8);
                imageView.setVisibility(0);
            }
            if (cover.size() > 1) {
                GlideApp.with(getContext()).load(cover.get(0)).into(imageView);
                GlideApp.with(getContext()).load(cover.get(1)).into(imageView2);
                imageView2.setVisibility(0);
            }
        }
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.allCourseSubjectBannerRoot);
        int customerAdapterPosition = BaseViewHolderUtilKt.getCustomerAdapterPosition(holder);
        List<SpecialItemBean> course_list = item.getCourse_list();
        textView.setText(item.getTitle());
        if (course_list == null || course_list.isEmpty()) {
            linearLayout3.setVisibility(4);
            linearLayout2.setVisibility(4);
            linearLayout.setVisibility(4);
        } else if (course_list.size() == 1) {
            textView2.setText(course_list.get(0).getTitle());
            linearLayout3.setVisibility(0);
            linearLayout2.setVisibility(4);
            linearLayout.setVisibility(4);
        } else if (course_list.size() == 2) {
            textView2.setText(course_list.get(0).getTitle());
            textView3.setText(course_list.get(1).getTitle());
            linearLayout3.setVisibility(0);
            linearLayout2.setVisibility(0);
            linearLayout.setVisibility(4);
        } else {
            textView2.setText(course_list.get(0).getTitle());
            textView3.setText(course_list.get(1).getTitle());
            textView4.setText(course_list.get(2).getTitle());
            linearLayout3.setVisibility(0);
            linearLayout2.setVisibility(0);
            linearLayout.setVisibility(0);
        }
        int i2 = customerAdapterPosition % 3;
        if (i2 == 0) {
            relativeLayout.setBackground(getContext().getDrawable(R.drawable.shape_all_course_subject_bg1));
        } else if (i2 == 1) {
            relativeLayout.setBackground(getContext().getDrawable(R.drawable.shape_all_course_subject_bg2));
        } else if (i2 == 2) {
            relativeLayout.setBackground(getContext().getDrawable(R.drawable.shape_all_course_subject_bg3));
        }
        BaseViewHolderUtilKt.getCustomerItemView(holder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f14380c.lambda$convert$0(item, view);
            }
        });
    }
}
