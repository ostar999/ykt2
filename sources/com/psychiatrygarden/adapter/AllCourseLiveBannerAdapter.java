package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.activity.ActCourseOrGoodsDetail;
import com.psychiatrygarden.activity.LoginActivity;
import com.psychiatrygarden.bean.LiveBannerBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.utils.BaseViewHolderUtilKt;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DateTimeUtilKt;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NavigationUtilKt;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.yikaobang.yixue.R;
import com.ykb.ebook.util.DateUtilKt;
import java.util.Objects;

/* loaded from: classes5.dex */
public class AllCourseLiveBannerAdapter extends BaseMultiItemQuickAdapter<LiveBannerBean, BaseViewHolder> {
    private boolean fromNewHome;

    public AllCourseLiveBannerAdapter() {
        addItemType(0, R.layout.item_all_course_live_banner);
        addItemType(1, R.layout.item_all_course_live_banner2);
    }

    private void anim(ImageView imageView) {
        if (isNight()) {
            imageView.setBackgroundResource(R.drawable.live_calendar_living_animation_night);
        } else {
            imageView.setBackgroundResource(R.drawable.live_calendar_living_animation);
        }
        ((AnimationDrawable) imageView.getBackground()).start();
    }

    private String getLiveDate(String startTime, String endTime) {
        String longTimeStampToString;
        if (TextUtils.isEmpty(startTime)) {
            longTimeStampToString = "";
        } else {
            long j2 = Long.parseLong(startTime) * 1000;
            DateUtilKt.formatLongTimeStampToString(j2, "yyyy-MM-dd HH:mm").substring(11);
            longTimeStampToString = DateUtilKt.formatLongTimeStampToString(j2, "M/d HH:mm");
        }
        if (!TextUtils.isEmpty(endTime)) {
            DateUtilKt.formatLongTimeStampToString(Long.parseLong(endTime) * 1000, "yyyy-MM-dd HH:mm").substring(11);
        }
        return longTimeStampToString;
    }

    public static int getLiveStatus(LiveBannerBean data) {
        if (TextUtils.isEmpty(data.getEnd_live_time()) || TextUtils.isEmpty(data.getStart_live_time())) {
            return 1;
        }
        String video_id = data.getVideo_id();
        Long lValueOf = Long.valueOf(Long.parseLong(data.getStart_live_time()) * 1000);
        Long lValueOf2 = Long.valueOf(Long.parseLong(data.getEnd_live_time()) * 1000);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis > lValueOf.longValue()) {
            int i2 = (jCurrentTimeMillis > lValueOf2.longValue() ? 1 : (jCurrentTimeMillis == lValueOf2.longValue() ? 0 : -1));
        }
        return jCurrentTimeMillis > lValueOf2.longValue() ? (TextUtils.isEmpty(video_id) || Integer.parseInt(video_id) <= 0) ? 3 : 2 : jCurrentTimeMillis >= lValueOf.longValue() ? 0 : 1;
    }

    private void initMoreItem(BaseViewHolder holder, final LiveBannerBean item) {
        View view = holder.getView(R.id.allCourseLiveBannerLine2);
        RoundedImageView roundedImageView = (RoundedImageView) holder.getView(R.id.allCourseLiveBannerImg);
        TextView textView = (TextView) holder.getView(R.id.allCourseLiveBannerTitle);
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.allCourseLiveBannerTeacherIcon);
        TextView textView2 = (TextView) holder.getView(R.id.allCourseLiveBannerTeacherName);
        TextView textView3 = (TextView) holder.getView(R.id.allCourseLiveBannerTime);
        LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.allCourseLiveBannerLiveFlag);
        ImageView imageView = (ImageView) holder.getView(R.id.liveCalendarAnim);
        TextView textView4 = (TextView) holder.getView(R.id.allCourseLiveBannerLiveFlag2);
        RelativeLayout relativeLayout = (RelativeLayout) holder.getView(R.id.allCourseLiveBannerRoot);
        Drawable drawable = getContext().getDrawable(R.drawable.shape_no_live_banner_bg);
        Drawable drawable2 = getContext().getDrawable(R.drawable.shape_no_live_banner_bg);
        if (!TextUtils.isEmpty(item.getEnd_live_time()) && !TextUtils.isEmpty(item.getStart_live_time()) && System.currentTimeMillis() > Long.parseLong(item.getStart_live_time()) * 1000 && System.currentTimeMillis() < Long.parseLong(item.getEnd_live_time()) * 1000) {
            relativeLayout.setBackground(drawable);
        } else {
            relativeLayout.setBackground(drawable2);
        }
        if (TextUtils.isEmpty(item.getThumb())) {
            roundedImageView.setImageResource(R.mipmap.ic_order_default);
        } else {
            Context context = getContext();
            String thumb = item.getThumb();
            Objects.requireNonNull(thumb);
            GlideUtils.loadImageDef(context, thumb, roundedImageView);
        }
        textView.setText(item.getTitle());
        if (!TextUtils.isEmpty(item.getTeacher_avatar())) {
            Context context2 = getContext();
            String teacher_avatar = item.getTeacher_avatar();
            Objects.requireNonNull(teacher_avatar);
            GlideUtils.loadImageDef(context2, teacher_avatar, circleImageView);
        }
        boolean z2 = !TextUtils.isEmpty(item.getTeacher());
        circleImageView.setVisibility(z2 ? 0 : 8);
        textView2.setVisibility(z2 ? 0 : 8);
        view.setVisibility(z2 ? 0 : 8);
        textView2.setText(item.getTeacher());
        textView3.setText(getLiveDate(item.getStart_live_time(), item.getEnd_live_time()));
        final int liveStatus = getLiveStatus(item);
        final boolean zEquals = "1".equals(item.is_permission());
        if (liveStatus == 0) {
            linearLayout.setVisibility(0);
            textView4.setVisibility(8);
            anim(imageView);
        } else if (liveStatus == 1) {
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            stopAnim(imageView);
            textView4.setBackground(getContext().getDrawable(R.drawable.shape_main_color_round8));
            textView4.setTextColor(getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.main_theme_red_color}).getColor(0, -1));
            textView4.setText("即将直播");
        } else if (liveStatus == 2 || liveStatus == 3) {
            linearLayout.setVisibility(8);
            textView4.setVisibility(0);
            stopAnim(imageView);
            textView4.setBackground(getContext().getDrawable(R.drawable.shape_bg_one_round8));
            textView4.setTextColor(getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.forth_txt_color}).getColor(0, -1));
            if (liveStatus == 2) {
                textView4.setText("查看回放");
            } else {
                textView4.setText("直播结束");
            }
        }
        BaseViewHolderUtilKt.getCustomerItemView(holder).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f14337c.lambda$initMoreItem$0(liveStatus, zEquals, item, view2);
            }
        });
    }

    private boolean isNight() {
        return SkinManager.getCurrentSkinType(getContext()) == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initMoreItem$0(int i2, boolean z2, LiveBannerBean liveBannerBean, View view) {
        if (i2 == 0) {
            if (isLogin()) {
                if (z2) {
                    CommonUtil.launchLiving(getContext(), liveBannerBean.getUser_id(), liveBannerBean.getApp_id(), liveBannerBean.getApp_secret(), liveBannerBean.getRoom_id(), liveBannerBean.getCourse_id(), liveBannerBean.getLive_id());
                    return;
                }
                ActCourseOrGoodsDetail.Companion companion = ActCourseOrGoodsDetail.INSTANCE;
                Context context = getContext();
                String course_id = liveBannerBean.getCourse_id();
                Objects.requireNonNull(course_id);
                companion.navigationToCourseOrGoodsDetail(context, course_id, "");
                return;
            }
            return;
        }
        if (i2 == 1) {
            if (isLogin()) {
                if (z2) {
                    if (DateTimeUtilKt.timeWithinHalfAnHour(liveBannerBean.getStart_live_time())) {
                        CommonUtil.launchLiving(getContext(), liveBannerBean.getUser_id(), liveBannerBean.getApp_id(), liveBannerBean.getApp_secret(), liveBannerBean.getRoom_id(), liveBannerBean.getCourse_id(), liveBannerBean.getLive_id());
                        return;
                    } else {
                        ToastUtil.shortToast(getContext(), "直播未开始");
                        return;
                    }
                }
                ActCourseOrGoodsDetail.Companion companion2 = ActCourseOrGoodsDetail.INSTANCE;
                Context context2 = getContext();
                String course_id2 = liveBannerBean.getCourse_id();
                Objects.requireNonNull(course_id2);
                companion2.navigationToCourseOrGoodsDetail(context2, course_id2, "");
                return;
            }
            return;
        }
        if (i2 == 2) {
            if (isLogin()) {
                if (z2) {
                    NavigationUtilKt.goToAliPlayerVideoPlayActivity(getContext(), liveBannerBean.getLive_id(), liveBannerBean.getCourse_id(), liveBannerBean.getVid(), liveBannerBean.getCover(), "2");
                    return;
                }
                ActCourseOrGoodsDetail.Companion companion3 = ActCourseOrGoodsDetail.INSTANCE;
                Context context3 = getContext();
                String course_id3 = liveBannerBean.getCourse_id();
                Objects.requireNonNull(course_id3);
                companion3.navigationToCourseOrGoodsDetail(context3, course_id3, "");
                return;
            }
            return;
        }
        if (i2 == 3 && isLogin()) {
            if (z2) {
                ToastUtil.shortToast(getContext(), "视频剪辑中，暂无法查看回放");
                return;
            }
            ActCourseOrGoodsDetail.Companion companion4 = ActCourseOrGoodsDetail.INSTANCE;
            Context context4 = getContext();
            String course_id4 = liveBannerBean.getCourse_id();
            Objects.requireNonNull(course_id4);
            companion4.navigationToCourseOrGoodsDetail(context4, course_id4, "");
        }
    }

    private void stopAnim(ImageView imageView) {
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    public boolean isLogin() {
        if (UserConfig.isLogin()) {
            return true;
        }
        getContext().startActivity(new Intent(getContext(), (Class<?>) LoginActivity.class));
        return false;
    }

    public void setNewHome(boolean newHome) {
        this.fromNewHome = newHome;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder holder, LiveBannerBean item) {
        int customerItemViewType = BaseViewHolderUtilKt.getCustomerItemViewType(holder);
        if (customerItemViewType == 0 || customerItemViewType == 1) {
            initMoreItem(holder, item);
        }
        if (this.fromNewHome) {
            int layoutPosition = holder.getLayoutPosition();
            View view = holder.getView(R.id.allCourseLiveBannerRoot);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = layoutPosition == 0 ? 0 : SizeUtil.dp2px(getContext(), 16);
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = layoutPosition == getData().size() - 1 ? SizeUtil.dp2px(getContext(), 16) : 0;
            view.setLayoutParams(layoutParams);
            return;
        }
        int layoutPosition2 = holder.getLayoutPosition();
        View view2 = holder.getView(R.id.allCourseLiveBannerRoot);
        RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) view2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = SizeUtil.dp2px(getContext(), 16);
        ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin = layoutPosition2 == getData().size() - 1 ? SizeUtil.dp2px(getContext(), 16) : 0;
        view2.setLayoutParams(layoutParams2);
    }
}
