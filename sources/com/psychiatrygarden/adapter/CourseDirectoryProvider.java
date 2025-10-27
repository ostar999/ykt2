package com.psychiatrygarden.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import cn.hutool.core.lang.RegexPool;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity;
import com.psychiatrygarden.activity.comment.bean.DiscussStatus;
import com.psychiatrygarden.bean.CourseDetailBean;
import com.psychiatrygarden.bean.CourseDirectoryContentItem;
import com.psychiatrygarden.bean.GoodsDetailItem;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.DetailCourseDirectoryWidget;
import com.yikaobang.yixue.R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t¨\u0006\u0011"}, d2 = {"Lcom/psychiatrygarden/adapter/CourseDirectoryProvider;", "Lcom/chad/library/adapter/base/provider/BaseItemProvider;", "Lcom/psychiatrygarden/bean/GoodsDetailItem;", "()V", "init", "", "itemViewType", "", "getItemViewType", "()I", "layoutId", "getLayoutId", "convert", "", "helper", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseDirectoryProvider extends BaseItemProvider<GoodsDetailItem> {
    private boolean init;

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getItemViewType() {
        return 4;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public int getLayoutId() {
        return R.layout.item_course_directory;
    }

    @Override // com.chad.library.adapter.base.provider.BaseItemProvider
    public void convert(@NotNull BaseViewHolder helper, @NotNull GoodsDetailItem item) {
        Intrinsics.checkNotNullParameter(helper, "helper");
        Intrinsics.checkNotNullParameter(item, "item");
        if (this.init) {
            return;
        }
        this.init = true;
        final CourseDetailBean courseData = item.getCourseData();
        if (courseData != null) {
            DetailCourseDirectoryWidget detailCourseDirectoryWidget = (DetailCourseDirectoryWidget) helper.getView(R.id.view);
            String goodsId = item.getGoodsId();
            Intrinsics.checkNotNullExpressionValue(goodsId, "item.goodsId");
            DetailCourseDirectoryWidget.initParams$default(detailCourseDirectoryWidget, goodsId, courseData.getType(), false, courseData.hasPermission(), null, false, 52, null);
            detailCourseDirectoryWidget.setClickListenerCallBack(new Function5<String, String, String, String, CourseDirectoryContentItem, Unit>() { // from class: com.psychiatrygarden.adapter.CourseDirectoryProvider$convert$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(5);
                }

                @Override // kotlin.jvm.functions.Function5
                public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3, String str4, CourseDirectoryContentItem courseDirectoryContentItem) {
                    invoke2(str, str2, str3, str4, courseDirectoryContentItem);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull String rootChapterId, @NotNull String chapterId, @NotNull String rootChapterTitle, @NotNull String chapterTitle, @NotNull CourseDirectoryContentItem item2) {
                    Intrinsics.checkNotNullParameter(rootChapterId, "rootChapterId");
                    Intrinsics.checkNotNullParameter(chapterId, "chapterId");
                    Intrinsics.checkNotNullParameter(rootChapterTitle, "rootChapterTitle");
                    Intrinsics.checkNotNullParameter(chapterTitle, "chapterTitle");
                    Intrinsics.checkNotNullParameter(item2, "item");
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    if (Intrinsics.areEqual("2", item2.getType()) && !TextUtils.isEmpty(item2.getStart_live_time())) {
                        String start_live_time = item2.getStart_live_time();
                        Intrinsics.checkNotNullExpressionValue(start_live_time, "item.start_live_time");
                        if (new Regex(RegexPool.NUMBERS).matches(start_live_time)) {
                            String start_live_time2 = item2.getStart_live_time();
                            Intrinsics.checkNotNullExpressionValue(start_live_time2, "item.start_live_time");
                            if (Long.parseLong(start_live_time2) > System.currentTimeMillis() / 1000) {
                                ToastUtil.shortToast(this.this$0.getContext(), "直播未开始");
                                return;
                            }
                        }
                    }
                    if (TextUtils.equals("1", item2.getType())) {
                        if (!courseData.hasPermission() && (TextUtils.equals("0", item2.getFree_watch()) || TextUtils.isEmpty(item2.getFree_watch()))) {
                            ToastUtil.shortToast(this.this$0.getContext(), "暂无查看权限");
                            if (this.this$0.getContext() instanceof AliPlayerVideoPlayActivity) {
                                Context context = this.this$0.getContext();
                                Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.psychiatrygarden.activity.comment.alipler.AliPlayerVideoPlayActivity");
                                ((AliPlayerVideoPlayActivity) context).mShareDataBuyCourse();
                                return;
                            }
                            return;
                        }
                        Bundle bundle = new Bundle();
                        CourseDetailBean courseDetailBean = courseData;
                        bundle.putString("obj_id", item2.getObj_id());
                        bundle.putString("seeDuration", item2.getCurrent_see());
                        bundle.putString("chapter_id", courseDetailBean.getId());
                        bundle.putString("course_id", courseDetailBean.getId());
                        bundle.putString("vid", item2.getVid());
                        bundle.putInt("module_type", 8);
                        bundle.putString("watch_permission", "1");
                        bundle.putString("isFreeWatch", "1");
                        bundle.putSerializable("commentEnum", DiscussStatus.CourseReview);
                        Intent intentPutExtra = new Intent(this.this$0.getContext(), (Class<?>) AliPlayerVideoPlayActivity.class).putExtra("rootChapterId", rootChapterId).putExtra("title", courseData.getTitle()).putExtra("pid", rootChapterId).putExtra("chapterId", chapterId).putExtra("rootChapterTitle", rootChapterTitle).putExtra("fromCourseDetail", true).putExtra("chapterTitle", chapterTitle).putExtras(bundle).putExtra("video_title", item2.getTitle()).putExtra("hasChapter", !TextUtils.isEmpty(item2.getChapter_id()));
                        Intrinsics.checkNotNullExpressionValue(intentPutExtra, "Intent(context, AliPlaye…isEmpty(item.chapter_id))");
                        if (Intrinsics.areEqual("1", item2.getFree_watch()) && !TextUtils.isEmpty(item2.getDuration())) {
                            String duration = item2.getDuration();
                            Intrinsics.checkNotNullExpressionValue(duration, "item.duration");
                            if (new Regex(RegexPool.NUMBERS).matches(duration)) {
                                String duration2 = item2.getDuration();
                                Intrinsics.checkNotNullExpressionValue(duration2, "item.duration");
                                intentPutExtra.putExtra("free_watch_time", Long.parseLong(duration2));
                            }
                        }
                        this.this$0.getContext().sendBroadcast(new Intent().setAction(EventBusConstant.CLOSE_PIP));
                        this.this$0.getContext().startActivity(intentPutExtra);
                    }
                }
            });
        }
    }
}
