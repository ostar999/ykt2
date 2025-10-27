package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.CourseDetailBannerItem;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0004H\u0016J\u0018\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0016¨\u0006\u000e"}, d2 = {"com/psychiatrygarden/widget/DetailCourseTopInfoWidget$bannerAdapter$1", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDetailCourseTopInfoWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DetailCourseTopInfoWidget.kt\ncom/psychiatrygarden/widget/DetailCourseTopInfoWidget$bannerAdapter$1\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,961:1\n1#2:962\n*E\n"})
/* loaded from: classes6.dex */
public final class DetailCourseTopInfoWidget$bannerAdapter$1 extends RecyclerView.Adapter<BaseViewHolder> {
    final /* synthetic */ Context $context;
    final /* synthetic */ DetailCourseTopInfoWidget this$0;

    public DetailCourseTopInfoWidget$bannerAdapter$1(DetailCourseTopInfoWidget detailCourseTopInfoWidget, Context context) {
        this.this$0 = detailCourseTopInfoWidget;
        this.$context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$1(DetailCourseTopInfoWidget this$0, int i2, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showBannerImgPop(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$2(CourseDetailBannerItem item, BaseViewHolder holder, DetailCourseTopInfoWidget this$0, View view) {
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(holder, "$holder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (item.isPlay()) {
            this$0.aliPlayer.pause();
            return;
        }
        holder.setGone(R.id.fl_video, false).setGone(R.id.surface_view, false).setGone(R.id.iv_img, true).setGone(R.id.iv_loading, false);
        this$0.mHandler.removeCallbacksAndMessages(null);
        item.setClickPlay(true);
        holder.setGone(R.id.iv_video_play, true).setGone(R.id.fl_video, false).setGone(R.id.iv_loading, false);
        ImageView imageView = this$0.ivLoading;
        Drawable background = imageView != null ? imageView.getBackground() : null;
        if (background instanceof AnimationDrawable) {
            ((AnimationDrawable) background).start();
        }
        String videoId = item.getVideoId();
        Intrinsics.checkNotNullExpressionValue(videoId, "item.videoId");
        this$0.preparePlay(videoId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$3(DetailCourseTopInfoWidget this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showPreviewPop();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.this$0.bannerData.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return ((CourseDetailBannerItem) this.this$0.bannerData.get(position)).getType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NotNull final BaseViewHolder holder, final int position) {
        SurfaceHolder holder2;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Object obj = this.this$0.bannerData.get(position);
        Intrinsics.checkNotNullExpressionValue(obj, "bannerData[position]");
        final CourseDetailBannerItem courseDetailBannerItem = (CourseDetailBannerItem) obj;
        GlideUtils.loadImage(this.$context, courseDetailBannerItem.getImgUrl(), (ImageView) holder.getView(R.id.iv_img));
        this.this$0.imgViewArr.put(Integer.valueOf(position), holder.getView(R.id.iv_img));
        if (courseDetailBannerItem.getType() == 1) {
            View view = holder.itemView;
            final DetailCourseTopInfoWidget detailCourseTopInfoWidget = this.this$0;
            view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.r6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DetailCourseTopInfoWidget$bannerAdapter$1.onBindViewHolder$lambda$1(detailCourseTopInfoWidget, position, view2);
                }
            });
            return;
        }
        this.this$0.ivLoading = (ImageView) holder.getView(R.id.iv_loading);
        this.this$0.videoItemView = holder.itemView;
        holder.setGone(R.id.fl_video, (courseDetailBannerItem.isPlay() || courseDetailBannerItem.isClickPlay()) ? false : true).setGone(R.id.iv_video_play, courseDetailBannerItem.isPlay() || courseDetailBannerItem.isClickPlay()).setGone(R.id.surface_view, (courseDetailBannerItem.isPlay() || courseDetailBannerItem.isClickPlay()) ? false : true).setGone(R.id.iv_img, courseDetailBannerItem.isPlay() || courseDetailBannerItem.isClickPlay()).setGone(R.id.iv_loading, true ^ courseDetailBannerItem.isLoading());
        this.this$0.surfaceView = (SurfaceView) holder.getView(R.id.surface_view);
        ImageView imageView = (ImageView) holder.getView(R.id.iv_video_play);
        final DetailCourseTopInfoWidget detailCourseTopInfoWidget2 = this.this$0;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DetailCourseTopInfoWidget$bannerAdapter$1.onBindViewHolder$lambda$2(courseDetailBannerItem, holder, detailCourseTopInfoWidget2, view2);
            }
        });
        SurfaceView surfaceView = this.this$0.surfaceView;
        if (surfaceView != null) {
            final DetailCourseTopInfoWidget detailCourseTopInfoWidget3 = this.this$0;
            surfaceView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.t6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DetailCourseTopInfoWidget$bannerAdapter$1.onBindViewHolder$lambda$3(detailCourseTopInfoWidget3, view2);
                }
            });
        }
        SurfaceView surfaceView2 = this.this$0.surfaceView;
        if (surfaceView2 == null || (holder2 = surfaceView2.getHolder()) == null) {
            return;
        }
        final DetailCourseTopInfoWidget detailCourseTopInfoWidget4 = this.this$0;
        holder2.addCallback(new SurfaceHolder.Callback() { // from class: com.psychiatrygarden.widget.DetailCourseTopInfoWidget$bannerAdapter$1$onBindViewHolder$4
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(@NotNull SurfaceHolder holder3, int format, int width, int height) {
                Intrinsics.checkNotNullParameter(holder3, "holder");
                detailCourseTopInfoWidget4.aliPlayer.surfaceChanged();
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(@NotNull SurfaceHolder holder3) {
                Intrinsics.checkNotNullParameter(holder3, "holder");
                detailCourseTopInfoWidget4.aliPlayer.setSurface(holder3.getSurface());
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(@NotNull SurfaceHolder holder3) {
                Intrinsics.checkNotNullParameter(holder3, "holder");
                detailCourseTopInfoWidget4.aliPlayer.setSurface(null);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = View.inflate(this.$context, viewType == 1 ? R.layout.img_course_banner : R.layout.img_course_banner_video, null);
        viewInflate.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflate(context, if (vie…outParams.MATCH_PARENT) }");
        return new BaseViewHolder(viewInflate);
    }
}
