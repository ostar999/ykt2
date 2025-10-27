package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.aliyun.player.AliPlayer;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.exoplayer2.C;
import com.psychiatrygarden.bean.CourseDetailBannerItem;
import com.psychiatrygarden.utils.GlideUtils;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0000*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014J&\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0014¨\u0006\u000b"}, d2 = {"com/psychiatrygarden/widget/CourseImgVideoPreviewPop$mAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/CourseDetailBannerItem;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "payloads", "", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CourseImgVideoPreviewPop$mAdapter$1 extends BaseQuickAdapter<CourseDetailBannerItem, BaseViewHolder> {
    final /* synthetic */ Context $context;
    final /* synthetic */ CourseImgVideoPreviewPop this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CourseImgVideoPreviewPop$mAdapter$1(CourseImgVideoPreviewPop courseImgVideoPreviewPop, Context context, ArrayList<CourseDetailBannerItem> arrayList) {
        super(R.layout.img_course_banner_preview, arrayList);
        this.this$0 = courseImgVideoPreviewPop;
        this.$context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$0(ImageView playPauseView) {
        Intrinsics.checkNotNullParameter(playPauseView, "$playPauseView");
        if (playPauseView.getVisibility() == 0) {
            ViewExtensionsKt.gone(playPauseView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$1(ImageView playPauseView) {
        Intrinsics.checkNotNullParameter(playPauseView, "$playPauseView");
        if (playPauseView.getVisibility() == 0) {
            ViewExtensionsKt.gone(playPauseView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3(CourseImgVideoPreviewPop this$0, final ImageView playPauseView, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(playPauseView, "$playPauseView");
        AliPlayer aliPlayer = null;
        if (this$0.playComplete) {
            AliPlayer aliPlayer2 = this$0.mPlayer;
            if (aliPlayer2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                aliPlayer2 = null;
            }
            aliPlayer2.seekTo(0L);
            AliPlayer aliPlayer3 = this$0.mPlayer;
            if (aliPlayer3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            } else {
                aliPlayer = aliPlayer3;
            }
            aliPlayer.start();
            this$0.isPlaying = true;
        } else if (this$0.isPlaying) {
            AliPlayer aliPlayer4 = this$0.mPlayer;
            if (aliPlayer4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            } else {
                aliPlayer = aliPlayer4;
            }
            aliPlayer.pause();
            this$0.isPlaying = false;
        } else {
            this$0.isPlaying = true;
            AliPlayer aliPlayer5 = this$0.mPlayer;
            if (aliPlayer5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
            } else {
                aliPlayer = aliPlayer5;
            }
            aliPlayer.start();
        }
        if (this$0.isPlaying) {
            playPauseView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.h4
                @Override // java.lang.Runnable
                public final void run() {
                    CourseImgVideoPreviewPop$mAdapter$1.convert$lambda$3$lambda$2(playPauseView);
                }
            }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$3$lambda$2(ImageView playPauseView) {
        Intrinsics.checkNotNullParameter(playPauseView, "$playPauseView");
        if (playPauseView.getVisibility() == 0) {
            ViewExtensionsKt.gone(playPauseView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean convert$lambda$5(final ImageView playPauseView, CourseImgVideoPreviewPop this$0, View view, MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(playPauseView, "$playPauseView");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ViewExtensionsKt.visible(playPauseView);
        if (!this$0.isPlaying) {
            playPauseView.setImageResource(R.drawable.alivc_playstate_play);
            return false;
        }
        playPauseView.setImageResource(R.drawable.alivc_playstate_pause);
        playPauseView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.f4
            @Override // java.lang.Runnable
            public final void run() {
                CourseImgVideoPreviewPop$mAdapter$1.convert$lambda$5$lambda$4(playPauseView);
            }
        }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$5$lambda$4(ImageView playPauseView) {
        Intrinsics.checkNotNullParameter(playPauseView, "$playPauseView");
        if (playPauseView.getVisibility() == 0) {
            ViewExtensionsKt.gone(playPauseView);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, CourseDetailBannerItem courseDetailBannerItem, List list) {
        convert2(baseViewHolder, courseDetailBannerItem, (List<? extends Object>) list);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull BaseViewHolder holder, @NotNull CourseDetailBannerItem item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        final ImageView imageView = (ImageView) holder.getView(R.id.iv_video_play);
        if (this.this$0.isPlaying) {
            imageView.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.g4
                @Override // java.lang.Runnable
                public final void run() {
                    CourseImgVideoPreviewPop$mAdapter$1.convert$lambda$0(imageView);
                }
            }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        }
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull CourseDetailBannerItem item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        ImageView imageView = (ImageView) holder.getView(R.id.iv_img);
        SurfaceView surfaceView = (SurfaceView) holder.getView(R.id.videoView);
        final ImageView imageView2 = (ImageView) holder.getView(R.id.iv_video_play);
        holder.setGone(R.id.iv_video_play, item.getType() == 1);
        ProgressBar progressBar = null;
        if (item.getType() == 1) {
            ViewExtensionsKt.visible(imageView);
            ViewExtensionsKt.gone(surfaceView);
            ProgressBar progressBar2 = this.this$0.seekBar;
            if (progressBar2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("seekBar");
            } else {
                progressBar = progressBar2;
            }
            ViewExtensionsKt.gone(progressBar);
            GlideUtils.loadImage(this.$context, item.getImgUrl(), imageView);
            return;
        }
        if (this.this$0.isPlaying) {
            imageView2.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.i4
                @Override // java.lang.Runnable
                public final void run() {
                    CourseImgVideoPreviewPop$mAdapter$1.convert$lambda$1(imageView2);
                }
            }, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
        }
        final CourseImgVideoPreviewPop courseImgVideoPreviewPop = this.this$0;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.j4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CourseImgVideoPreviewPop$mAdapter$1.convert$lambda$3(courseImgVideoPreviewPop, imageView2, view);
            }
        });
        final CourseImgVideoPreviewPop courseImgVideoPreviewPop2 = this.this$0;
        surfaceView.setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.widget.k4
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return CourseImgVideoPreviewPop$mAdapter$1.convert$lambda$5(imageView2, courseImgVideoPreviewPop2, view, motionEvent);
            }
        });
        SurfaceHolder holder2 = surfaceView.getHolder();
        final CourseImgVideoPreviewPop courseImgVideoPreviewPop3 = this.this$0;
        holder2.addCallback(new SurfaceHolder.Callback() { // from class: com.psychiatrygarden.widget.CourseImgVideoPreviewPop$mAdapter$1$convert$5
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(@NotNull SurfaceHolder holder3, int format, int width, int height) {
                Intrinsics.checkNotNullParameter(holder3, "holder");
                AliPlayer aliPlayer = courseImgVideoPreviewPop3.mPlayer;
                if (aliPlayer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                    aliPlayer = null;
                }
                aliPlayer.surfaceChanged();
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(@NotNull SurfaceHolder holder3) {
                Intrinsics.checkNotNullParameter(holder3, "holder");
                AliPlayer aliPlayer = courseImgVideoPreviewPop3.mPlayer;
                if (aliPlayer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                    aliPlayer = null;
                }
                aliPlayer.setSurface(holder3.getSurface());
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(@NotNull SurfaceHolder holder3) {
                Intrinsics.checkNotNullParameter(holder3, "holder");
                AliPlayer aliPlayer = courseImgVideoPreviewPop3.mPlayer;
                if (aliPlayer == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mPlayer");
                    aliPlayer = null;
                }
                aliPlayer.setSurface(null);
            }
        });
        ViewExtensionsKt.gone(imageView);
        ViewExtensionsKt.visible(surfaceView);
        ProgressBar progressBar3 = this.this$0.seekBar;
        if (progressBar3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("seekBar");
        } else {
            progressBar = progressBar3;
        }
        ViewExtensionsKt.visible(progressBar);
    }
}
