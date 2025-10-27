package com.ykb.ebook.dialog;

import android.app.Activity;
import android.content.AppCtxKt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.ColorResourcesKt;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RTextView;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.open.SocialConstants;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.activity.ReplyCollectionAct;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.AddReviewCommentDialog;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.CommentItemChooseDialog;
import com.ykb.ebook.dialog.ReportReasonChooseDialog;
import com.ykb.ebook.dialog.ReviewCommentDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.SubFloorComments;
import com.ykb.ebook.util.Coroutine;
import com.ykb.ebook.util.FastClickUtilKt;
import com.ykb.ebook.util.FileUtils;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.weight.FloorCommentListenter;
import com.ykb.ebook.weight.FloorViews;
import com.ykb.ebook.weight.RoundCornerDrawable;
import com.ykb.ebook.weight.SubFloorFactorys;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/ykb/ebook/dialog/ReviewCommentDialog;", "", "()V", "Builder", "CallBack", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ReviewCommentDialog {

    @Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001kB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ(\u0010U\u001a\u0002072\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010V\u001a\u00020\u00072\u0006\u0010W\u001a\u00020\u00072\u0006\u0010X\u001a\u00020\u0007H\u0002J*\u0010Y\u001a\u0002072\u0006\u0010Z\u001a\u00020(2\u0006\u0010[\u001a\u00020\u00102\u0006\u0010\\\u001a\u00020\f2\b\b\u0002\u0010]\u001a\u00020\fH\u0002J(\u0010^\u001a\u0002072\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010_\u001a\u00020\u00072\u0006\u0010V\u001a\u00020\u00072\u0006\u0010`\u001a\u00020\u0007H\u0002J0\u0010a\u001a\u0002072\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010b\u001a\u00020c2\u0006\u0010\\\u001a\u00020\f2\u0006\u0010Z\u001a\u00020(H\u0002J&\u0010a\u001a\u0002072\u0006\u0010b\u001a\u00020c2\u0006\u0010Z\u001a\u00020(2\f\u0010d\u001a\b\u0012\u0004\u0012\u00020f0eH\u0002J\u0010\u0010g\u001a\u0002072\u0006\u0010Z\u001a\u00020(H\u0002J*\u0010h\u001a\u0002072\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010i\u001a\u00020\u0010H\u0002J\u000e\u0010j\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u000eR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0019\u001a\u0004\u0018\u00010\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u0018\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\u0018\u001a\u0004\b \u0010!R\u001d\u0010#\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\u0018\u001a\u0004\b$\u0010!R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010+\u001a\u0012\u0012\u0004\u0012\u00020(0,j\b\u0012\u0004\u0012\u00020(`-X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010/\u001a\u0004\u0018\u0001008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b3\u0010\u0018\u001a\u0004\b1\u00102R\"\u00104\u001a\u0016\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u000207\u0018\u000105X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020;X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020;X\u0082.¢\u0006\u0002\n\u0000R\u0012\u0010=\u001a\u00060>R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u001d\u0010?\u001a\u0004\u0018\u00010@8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bC\u0010\u0018\u001a\u0004\bA\u0010BR\u001d\u0010D\u001a\u0004\u0018\u00010E8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bH\u0010\u0018\u001a\u0004\bF\u0010GR\u001d\u0010I\u001a\u0004\u0018\u00010J8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bM\u0010\u0018\u001a\u0004\bK\u0010LR\u000e\u0010N\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010O\u001a\u0004\u0018\u0001008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bQ\u0010\u0018\u001a\u0004\bP\u00102R\u001d\u0010R\u001a\u0004\u0018\u0001008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\bT\u0010\u0018\u001a\u0004\bS\u00102¨\u0006l"}, d2 = {"Lcom/ykb/ebook/dialog/ReviewCommentDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "bookId", "", "id", "chapterId", "(Landroid/content/Context;Landroid/app/Activity;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "allCount", "", "cb", "Lcom/ykb/ebook/dialog/ReviewCommentDialog$CallBack;", "hasHot", "", "headerTitle", "hotCount", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "Lkotlin/Lazy;", "imgHead", "Lcom/ruffian/library/widget/RImageView;", "getImgHead", "()Lcom/ruffian/library/widget/RImageView;", "imgHead$delegate", "llFloat", "Landroid/widget/LinearLayout;", "getLlFloat", "()Landroid/widget/LinearLayout;", "llFloat$delegate", "lyView", "getLyView", "lyView$delegate", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/BookReview;", "mBookId", "mChapterId", "mCommentList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "mId", "mTvContent", "Landroid/widget/TextView;", "getMTvContent", "()Landroid/widget/TextView;", "mTvContent$delegate", "onItemClick", "Lkotlin/Function2;", "Lcom/ykb/ebook/model/Chapter;", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "paragraphContent", "rbHot", "Lcom/ruffian/library/widget/RTextView;", "rbNew", SocialConstants.PARAM_RECEIVER, "Lcom/ykb/ebook/dialog/ReviewCommentDialog$Builder$AdpReceiver;", "recycler", "Landroidx/recyclerview/widget/RecyclerView;", "getRecycler", "()Landroidx/recyclerview/widget/RecyclerView;", "recycler$delegate", "refresh", "Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "getRefresh", "()Lcom/scwang/smartrefresh/layout/SmartRefreshLayout;", "refresh$delegate", "refreshHeader", "Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", "getRefreshHeader", "()Lcom/scwang/smartrefresh/layout/header/ClassicsHeader;", "refreshHeader$delegate", "timeLineCount", "tvFloatTitle", "getTvFloatTitle", "tvFloatTitle$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "accountBanned", "reasonId", "day", "userId", "addSupportOrOppse", "item", "isSupport", "position", "parentPos", "commentReport", "commentId", "reason", "convert", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "payloads", "", "", "delReviewComment", "loadData", "isPutNewComment", "setCallBack", "AdpReceiver", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nReviewCommentDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReviewCommentDialog.kt\ncom/ykb/ebook/dialog/ReviewCommentDialog$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,1157:1\n42#2:1158\n42#2:1159\n1#3:1160\n350#4,7:1161\n*S KotlinDebug\n*F\n+ 1 ReviewCommentDialog.kt\ncom/ykb/ebook/dialog/ReviewCommentDialog$Builder\n*L\n128#1:1158\n132#1:1159\n193#1:1161,7\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {
        private int allCount;

        @Nullable
        private CallBack cb;
        private boolean hasHot;

        @NotNull
        private String headerTitle;
        private int hotCount;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        /* renamed from: imgHead$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgHead;

        /* renamed from: llFloat$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy llFloat;

        /* renamed from: lyView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy lyView;

        @NotNull
        private CommonAdapter<BookReview> mAdapter;

        @NotNull
        private String mBookId;

        @NotNull
        private String mChapterId;

        @NotNull
        private final ArrayList<BookReview> mCommentList;

        @NotNull
        private String mId;

        /* renamed from: mTvContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy mTvContent;

        @Nullable
        private Function2<? super Integer, ? super Chapter, Unit> onItemClick;
        private int page;

        @NotNull
        private String paragraphContent;
        private RTextView rbHot;
        private RTextView rbNew;
        private AdpReceiver receiver;

        /* renamed from: recycler$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy recycler;

        /* renamed from: refresh$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy refresh;

        /* renamed from: refreshHeader$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy refreshHeader;
        private int timeLineCount;

        /* renamed from: tvFloatTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvFloatTitle;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/dialog/ReviewCommentDialog$Builder$AdpReceiver;", "Landroid/content/BroadcastReceiver;", "(Lcom/ykb/ebook/dialog/ReviewCommentDialog$Builder;)V", "onReceive", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        public final class AdpReceiver extends BroadcastReceiver {
            public AdpReceiver() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(@Nullable Context context, @Nullable Intent intent) {
                Log.INSTANCE.logE("receive_code", "====>收到通知");
                Builder.this.mAdapter.notifyDataSetChanged();
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull final Context context, @NotNull final Activity activity, @NotNull final String bookId, @NotNull final String id, @NotNull final String chapterId) throws SecurityException {
            int i2;
            RefreshHeader refreshHeader;
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(id, "id");
            Intrinsics.checkNotNullParameter(chapterId, "chapterId");
            this.page = 1;
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$tvTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_title);
                }
            });
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$imgClose$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_close);
                }
            });
            this.lyView = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$lyView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ly_view);
                }
            });
            this.tvFloatTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$tvFloatTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_type_text_float);
                }
            });
            this.llFloat = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$llFloat$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.ll_float);
                }
            });
            this.refresh = LazyKt__LazyJVMKt.lazy(new Function0<SmartRefreshLayout>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$refresh$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final SmartRefreshLayout invoke() {
                    return (SmartRefreshLayout) this.this$0.findViewById(R.id.refresh);
                }
            });
            this.recycler = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$recycler$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.recycler);
                }
            });
            this.imgHead = LazyKt__LazyJVMKt.lazy(new Function0<RImageView>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$imgHead$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RImageView invoke() {
                    return (RImageView) this.this$0.findViewById(R.id.img_head);
                }
            });
            this.mTvContent = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$mTvContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.et_comment);
                }
            });
            this.refreshHeader = LazyKt__LazyJVMKt.lazy(new Function0<ClassicsHeader>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$refreshHeader$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ClassicsHeader invoke() {
                    return (ClassicsHeader) this.this$0.findViewById(R.id.refresh_header);
                }
            });
            this.paragraphContent = "";
            this.mCommentList = new ArrayList<>();
            this.headerTitle = "";
            this.mBookId = bookId;
            this.mChapterId = chapterId;
            this.mId = id;
            this.receiver = new AdpReceiver();
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            AdpReceiver adpReceiver = this.receiver;
            if (adpReceiver == null) {
                Intrinsics.throwUninitializedPropertyAccessException(SocialConstants.PARAM_RECEIVER);
                adpReceiver = null;
            }
            localBroadcastManager.registerReceiver(adpReceiver, new IntentFilter("uploadAdp"));
            setContentView(R.layout.dialog_review_comment);
            setHeight((ScreenUtil.getScreenHeight(getActivity()) * R2.attr.bl_unPressed_drawable) / R2.attr.border_width_selected);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            View viewFindViewById = findViewById(R.id.tv_hot);
            Intrinsics.checkNotNull(viewFindViewById);
            this.rbHot = (RTextView) viewFindViewById;
            View viewFindViewById2 = findViewById(R.id.tv_new);
            Intrinsics.checkNotNull(viewFindViewById2);
            this.rbNew = (RTextView) viewFindViewById2;
            SmartRefreshLayout refresh = getRefresh();
            if (refresh != null && (refreshHeader = refresh.getRefreshHeader()) != null) {
                if (refreshHeader instanceof ClassicsHeader) {
                    ((ClassicsHeader) refreshHeader).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
                }
                RefreshFooter refreshFooter = refresh.getRefreshFooter();
                if (refreshFooter != null) {
                    if (refreshFooter instanceof ClassicsFooter) {
                        ((ClassicsFooter) refreshFooter).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), ReadConfig.INSTANCE.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            RLinearLayout rLinearLayout = (RLinearLayout) findViewById(R.id.ll_comment_wrap);
            ReadConfig readConfig = ReadConfig.INSTANCE;
            int colorMode = readConfig.getColorMode();
            if (colorMode == 1) {
                Intrinsics.checkNotNull(rLinearLayout);
                rLinearLayout.getHelper().setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
                RTextView rTextView = this.rbHot;
                if (rTextView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView = null;
                }
                RTextViewHelper helper = rTextView.getHelper();
                int i3 = R.color.color_303030;
                helper.setTextColorSelected(getColor(i3));
                RTextView rTextView2 = this.rbHot;
                if (rTextView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView2 = null;
                }
                RTextViewHelper helper2 = rTextView2.getHelper();
                int i4 = R.color.color_bfbfbf;
                helper2.setTextColorNormal(getColor(i4));
                RTextView rTextView3 = this.rbHot;
                if (rTextView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView3 = null;
                }
                RTextViewHelper helper3 = rTextView3.getHelper();
                int i5 = R.color.color_F5EBCE;
                helper3.setBackgroundColorSelected(getColor(i5));
                RTextView rTextView4 = this.rbHot;
                if (rTextView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView4 = null;
                }
                RTextViewHelper helper4 = rTextView4.getHelper();
                int i6 = R.color.transparent;
                helper4.setBackgroundColorNormal(getColor(i6));
                RTextView rTextView5 = this.rbNew;
                if (rTextView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView5 = null;
                }
                rTextView5.getHelper().setTextColorSelected(getColor(i3));
                RTextView rTextView6 = this.rbNew;
                if (rTextView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView6 = null;
                }
                rTextView6.getHelper().setTextColorNormal(getColor(i4));
                RTextView rTextView7 = this.rbNew;
                if (rTextView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView7 = null;
                }
                rTextView7.getHelper().setBackgroundColorSelected(getColor(i5));
                RTextView rTextView8 = this.rbNew;
                if (rTextView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView8 = null;
                }
                rTextView8.getHelper().setBackgroundColorNormal(getColor(i6));
                Unit unit2 = Unit.INSTANCE;
            } else if (colorMode != 2) {
                Intrinsics.checkNotNull(rLinearLayout);
                rLinearLayout.getHelper().setBackgroundColorNormal(getColor(R.color.color_f7f8fa));
                RTextView rTextView9 = this.rbHot;
                if (rTextView9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView9 = null;
                }
                RTextViewHelper helper5 = rTextView9.getHelper();
                int i7 = R.color.color_303030;
                helper5.setTextColorSelected(getColor(i7));
                RTextView rTextView10 = this.rbHot;
                if (rTextView10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView10 = null;
                }
                RTextViewHelper helper6 = rTextView10.getHelper();
                int i8 = R.color.color_bfbfbf;
                helper6.setTextColorNormal(getColor(i8));
                RTextView rTextView11 = this.rbHot;
                if (rTextView11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView11 = null;
                }
                RTextViewHelper helper7 = rTextView11.getHelper();
                int i9 = R.color.white;
                helper7.setBackgroundColorSelected(getColor(i9));
                RTextView rTextView12 = this.rbHot;
                if (rTextView12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView12 = null;
                }
                RTextViewHelper helper8 = rTextView12.getHelper();
                int i10 = R.color.color_f9fafb;
                helper8.setBackgroundColorNormal(getColor(i10));
                RTextView rTextView13 = this.rbNew;
                if (rTextView13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView13 = null;
                }
                rTextView13.getHelper().setTextColorSelected(getColor(i7));
                RTextView rTextView14 = this.rbNew;
                if (rTextView14 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView14 = null;
                }
                rTextView14.getHelper().setTextColorNormal(getColor(i8));
                RTextView rTextView15 = this.rbNew;
                if (rTextView15 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView15 = null;
                }
                rTextView15.getHelper().setBackgroundColorSelected(getColor(i9));
                RTextView rTextView16 = this.rbNew;
                if (rTextView16 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView16 = null;
                }
                rTextView16.getHelper().setBackgroundColorNormal(getColor(i10));
                Unit unit3 = Unit.INSTANCE;
            } else {
                Intrinsics.checkNotNull(rLinearLayout);
                RBaseHelper helper9 = rLinearLayout.getHelper();
                int i11 = R.color.color_171C2D;
                helper9.setBackgroundColorNormal(getColor(i11));
                RTextView rTextView17 = this.rbHot;
                if (rTextView17 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView17 = null;
                }
                RTextViewHelper helper10 = rTextView17.getHelper();
                int i12 = R.color.color_7380a9;
                helper10.setTextColorSelected(getColor(i12));
                RTextView rTextView18 = this.rbHot;
                if (rTextView18 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView18 = null;
                }
                RTextViewHelper helper11 = rTextView18.getHelper();
                int i13 = R.color.color_575F79;
                helper11.setTextColorNormal(getColor(i13));
                RTextView rTextView19 = this.rbHot;
                if (rTextView19 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView19 = null;
                }
                RTextViewHelper helper12 = rTextView19.getHelper();
                int i14 = R.color.color_121622;
                helper12.setBackgroundColorSelected(getColor(i14));
                RTextView rTextView20 = this.rbHot;
                if (rTextView20 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                    rTextView20 = null;
                }
                rTextView20.getHelper().setBackgroundColorNormal(getColor(i11));
                RTextView rTextView21 = this.rbNew;
                if (rTextView21 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView21 = null;
                }
                rTextView21.getHelper().setTextColorSelected(getColor(i12));
                RTextView rTextView22 = this.rbNew;
                if (rTextView22 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView22 = null;
                }
                rTextView22.getHelper().setTextColorNormal(getColor(i13));
                RTextView rTextView23 = this.rbNew;
                if (rTextView23 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView23 = null;
                }
                rTextView23.getHelper().setBackgroundColorSelected(getColor(i14));
                RTextView rTextView24 = this.rbNew;
                if (rTextView24 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                    rTextView24 = null;
                }
                rTextView24.getHelper().setBackgroundColorNormal(getColor(i11));
                Unit unit4 = Unit.INSTANCE;
            }
            RTextView rTextView25 = this.rbHot;
            if (rTextView25 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                rTextView25 = null;
            }
            rTextView25.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.c1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ReviewCommentDialog.Builder._init_$lambda$4(this.f26309c, view);
                }
            });
            RTextView rTextView26 = this.rbNew;
            if (rTextView26 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                rTextView26 = null;
            }
            rTextView26.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.d1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ReviewCommentDialog.Builder._init_$lambda$8(this.f26314c, view);
                }
            });
            TextView tvTitle = getTvTitle();
            if (tvTitle != null) {
                tvTitle.setText("全部段评（0条）");
            }
            TextView tvTitle2 = getTvTitle();
            if (tvTitle2 != null) {
                tvTitle2.setTextColor(getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
                Unit unit5 = Unit.INSTANCE;
            }
            TextView tvFloatTitle = getTvFloatTitle();
            if (tvFloatTitle != null) {
                tvFloatTitle.setTextColor(getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
                Unit unit6 = Unit.INSTANCE;
            }
            String prefString$default = ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "avatar", null, 2, null);
            RImageView imgHead = getImgHead();
            if (imgHead != null) {
                ImageLoader.INSTANCE.load(context, prefString$default).placeholder(R.drawable.personal_headimg_icon).into(imgHead);
            }
            int colorMode2 = readConfig.getColorMode();
            int color = context.getColor(colorMode2 != 1 ? colorMode2 != 2 ? R.color.white : R.color.color_121622 : R.color.color_F5EBCE);
            LinearLayout lyView = getLyView();
            if (lyView == null) {
                i2 = 1;
            } else {
                i2 = 1;
                lyView.setBackground(new RoundCornerDrawable(color, ConvertExtensionsKt.dpToPx(16), true));
            }
            LinearLayout llFloat = getLlFloat();
            if (llFloat != null) {
                int colorMode3 = readConfig.getColorMode();
                llFloat.setBackground(new ColorDrawable(context.getColor(colorMode3 != i2 ? colorMode3 != 2 ? R.color.white : R.color.color_121622 : R.color.color_F5EBCE)));
            }
            View viewFindViewById3 = findViewById(R.id.type_line);
            if (viewFindViewById3 != null) {
                int colorMode4 = readConfig.getColorMode();
                viewFindViewById3.setBackground(new ColorDrawable(context.getColor(colorMode4 != 1 ? colorMode4 != 2 ? R.color.color_eeeeee : R.color.color_1C2134 : R.color.color_EDE2C3)));
            }
            View viewFindViewById4 = findViewById(R.id.line);
            if (viewFindViewById4 != null) {
                int colorMode5 = readConfig.getColorMode();
                viewFindViewById4.setBackground(new ColorDrawable(context.getColor(colorMode5 != 1 ? colorMode5 != 2 ? R.color.color_eeeeee : R.color.color_1C2134 : R.color.color_EDE2C3)));
            }
            CommonAdapter<BookReview> commonAdapter = new CommonAdapter<>(R.layout.item_review_comment, null, 2, null);
            this.mAdapter = commonAdapter;
            commonAdapter.setEmptyViewEnable(true);
            this.mAdapter.setEmptyViewLayout(context, readConfig.getColorMode() != 2 ? R.layout.empty_comment_layout_day : R.layout.empty_comment_layout_night);
            this.mAdapter.setConvert(new Function3<QuickViewHolder, Integer, BookReview, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog.Builder.5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, BookReview bookReview) {
                    invoke(quickViewHolder, num.intValue(), bookReview);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i15, @Nullable BookReview bookReview) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Builder builder = Builder.this;
                    Activity activity2 = activity;
                    Context context2 = context;
                    Intrinsics.checkNotNull(bookReview);
                    builder.convert(activity2, context2, holder, i15, bookReview);
                }
            });
            this.mAdapter.setConvertPayload(new Function4<QuickViewHolder, Integer, BookReview, List<? extends Object>, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog.Builder.6
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, BookReview bookReview, List<? extends Object> list) {
                    invoke(quickViewHolder, num.intValue(), bookReview, list);
                    return Unit.INSTANCE;
                }

                public final void invoke(@NotNull QuickViewHolder holder, int i15, @Nullable BookReview bookReview, @NotNull List<? extends Object> payloads) {
                    Intrinsics.checkNotNullParameter(holder, "holder");
                    Intrinsics.checkNotNullParameter(payloads, "payloads");
                    Builder builder = Builder.this;
                    Intrinsics.checkNotNull(bookReview);
                    builder.convert(holder, bookReview, payloads);
                }
            });
            this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.ykb.ebook.dialog.e1
                @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onClick(BaseQuickAdapter baseQuickAdapter, View view, int i15) {
                    ReviewCommentDialog.Builder._init_$lambda$10(this.f26325c, context, activity, baseQuickAdapter, view, i15);
                }
            });
            RecyclerView recycler = getRecycler();
            if (recycler != null) {
                recycler.setAdapter(this.mAdapter);
            }
            RecyclerView recycler2 = getRecycler();
            RecyclerView.LayoutManager layoutManager = recycler2 != null ? recycler2.getLayoutManager() : null;
            Intrinsics.checkNotNull(layoutManager);
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            final float fApplyDimension = TypedValue.applyDimension(1, 56.0f, getResources().getDisplayMetrics());
            RecyclerView recycler3 = getRecycler();
            if (recycler3 != null) {
                recycler3.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog.Builder.8
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
                        super.onScrolled(recyclerView, dx, dy);
                        if (Builder.this.mCommentList.size() <= 3) {
                            return;
                        }
                        int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        View viewFindViewByPosition = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
                        Intrinsics.checkNotNull(viewFindViewByPosition);
                        Object obj = Builder.this.mCommentList.get(iFindFirstVisibleItemPosition);
                        Intrinsics.checkNotNullExpressionValue(obj, "mCommentList[firstItemPosition]");
                        BookReview bookReview = (BookReview) obj;
                        int iFindFirstVisibleItemPosition2 = linearLayoutManager.findFirstVisibleItemPosition() + 1;
                        if (iFindFirstVisibleItemPosition2 > Builder.this.mCommentList.size() - 1) {
                            iFindFirstVisibleItemPosition2 = Builder.this.mCommentList.size() - 1;
                        }
                        View viewFindViewByPosition2 = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition2);
                        Object obj2 = Builder.this.mCommentList.get(iFindFirstVisibleItemPosition2);
                        Intrinsics.checkNotNullExpressionValue(obj2, "mCommentList[secondItemPosition]");
                        BookReview bookReview2 = (BookReview) obj2;
                        if (dy > 0) {
                            if (dy >= 200) {
                                LinearLayout llFloat2 = Builder.this.getLlFloat();
                                Intrinsics.checkNotNull(llFloat2);
                                llFloat2.setY(0.0f);
                            } else if (bookReview2.getSuspend()) {
                                float y2 = viewFindViewByPosition2 != null ? viewFindViewByPosition2.getY() : 0.0f;
                                float f2 = fApplyDimension;
                                if (y2 < f2) {
                                    float f3 = y2 - f2;
                                    LinearLayout llFloat3 = Builder.this.getLlFloat();
                                    Intrinsics.checkNotNull(llFloat3);
                                    llFloat3.setY(f3);
                                }
                            }
                            if (bookReview.getSuspend() && viewFindViewByPosition.getY() <= 0.0f) {
                                LinearLayout llFloat4 = Builder.this.getLlFloat();
                                Intrinsics.checkNotNull(llFloat4);
                                llFloat4.setY(0.0f);
                            }
                        } else if (bookReview2.getSuspend()) {
                            float y3 = viewFindViewByPosition2 != null ? viewFindViewByPosition2.getY() : 0.0f;
                            if (y3 < fApplyDimension) {
                                LinearLayout llFloat5 = Builder.this.getLlFloat();
                                Intrinsics.checkNotNull(llFloat5);
                                llFloat5.setY(y3 - fApplyDimension);
                            } else {
                                LinearLayout llFloat6 = Builder.this.getLlFloat();
                                Intrinsics.checkNotNull(llFloat6);
                                llFloat6.setY(0.0f);
                            }
                        } else {
                            LinearLayout llFloat7 = Builder.this.getLlFloat();
                            Intrinsics.checkNotNull(llFloat7);
                            llFloat7.setY(0.0f);
                        }
                        RTextView rTextView27 = null;
                        if (bookReview.getType() == 1) {
                            RTextView rTextView28 = Builder.this.rbHot;
                            if (rTextView28 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                                rTextView28 = null;
                            }
                            rTextView28.setSelected(true);
                            RTextView rTextView29 = Builder.this.rbNew;
                            if (rTextView29 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                            } else {
                                rTextView27 = rTextView29;
                            }
                            rTextView27.setSelected(false);
                            TextView tvFloatTitle2 = Builder.this.getTvFloatTitle();
                            if (tvFloatTitle2 == null) {
                                return;
                            }
                            tvFloatTitle2.setText("最热评论 （" + Builder.this.hotCount + "条）");
                            return;
                        }
                        RTextView rTextView30 = Builder.this.rbHot;
                        if (rTextView30 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rbHot");
                            rTextView30 = null;
                        }
                        rTextView30.setSelected(false);
                        RTextView rTextView31 = Builder.this.rbNew;
                        if (rTextView31 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("rbNew");
                        } else {
                            rTextView27 = rTextView31;
                        }
                        rTextView27.setSelected(true);
                        TextView tvFloatTitle3 = Builder.this.getTvFloatTitle();
                        if (tvFloatTitle3 == null) {
                            return;
                        }
                        tvFloatTitle3.setText("最新评论 （" + Builder.this.timeLineCount + "条）");
                    }
                });
                Unit unit7 = Unit.INSTANCE;
            }
            ImageView imgClose = getImgClose();
            if (imgClose != null) {
                imgClose.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.f1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ReviewCommentDialog.Builder._init_$lambda$11(this.f26330c, view);
                    }
                });
                Unit unit8 = Unit.INSTANCE;
            }
            ImageView imgClose2 = getImgClose();
            if (imgClose2 != null) {
                int colorMode6 = readConfig.getColorMode();
                imgClose2.setImageResource(colorMode6 != 0 ? colorMode6 != 1 ? R.drawable.icon_close_night_svg : R.mipmap.ic_close_color_mode_1 : R.drawable.ic_close);
                Unit unit9 = Unit.INSTANCE;
            }
            SmartRefreshLayout refresh2 = getRefresh();
            if (refresh2 != null) {
                refresh2.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.dialog.g1
                    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
                    public final void onRefresh(RefreshLayout refreshLayout) {
                        ReviewCommentDialog.Builder._init_$lambda$12(this.f26334c, context, chapterId, id, refreshLayout);
                    }
                });
            }
            SmartRefreshLayout refresh3 = getRefresh();
            if (refresh3 != null) {
                refresh3.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.dialog.w0
                    @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
                    public final void onLoadMore(RefreshLayout refreshLayout) {
                        ReviewCommentDialog.Builder._init_$lambda$13(this.f26397c, context, chapterId, id, refreshLayout);
                    }
                });
            }
            TextView mTvContent = getMTvContent();
            if (mTvContent != null) {
                mTvContent.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.x0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ReviewCommentDialog.Builder._init_$lambda$14(this.f26402c, activity, context, bookId, chapterId, id, view);
                    }
                });
                Unit unit10 = Unit.INSTANCE;
            }
            View viewFindViewById5 = findViewById(R.id.ll_comment);
            Intrinsics.checkNotNull(viewFindViewById5);
            LinearLayout linearLayout = (LinearLayout) viewFindViewById5;
            int colorMode7 = readConfig.getColorMode();
            linearLayout.setBackground(new RoundCornerDrawable(context.getColor(colorMode7 != 1 ? colorMode7 != 2 ? R.color.color_f9fafb : R.color.color_171c2d : R.color.color_EDE2C3), ConvertExtensionsKt.dpToPx(100)));
            TextView mTvContent2 = getMTvContent();
            if (mTvContent2 != null) {
                mTvContent2.setHintTextColor(readConfig.getColorMode() == 2 ? getColor(R.color.color_575F79) : getColor(R.color.color_c2c6cb));
                Unit unit11 = Unit.INSTANCE;
            }
            View viewFindViewById6 = findViewById(R.id.iv_add_comment);
            Intrinsics.checkNotNull(viewFindViewById6);
            ImageView imageView = (ImageView) viewFindViewById6;
            imageView.setColorFilter((ColorFilter) null);
            if (readConfig.getColorMode() == 2) {
                Drawable drawable = getDrawable(R.drawable.ic_add_comment);
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(getColor(R.color.color_7380a9), PorterDuff.Mode.SRC_IN));
                }
                imageView.setImageDrawable(drawable);
            } else {
                Drawable drawable2 = getDrawable(R.drawable.ic_add_comment);
                if (drawable2 != null) {
                    drawable2.setColorFilter(new PorterDuffColorFilter(getColor(R.color.color_C2C6CB), PorterDuff.Mode.SRC_IN));
                }
                imageView.setImageDrawable(drawable2);
            }
            loadData$default(this, context, chapterId, id, false, 8, null);
            int colorMode8 = readConfig.getColorMode();
            SmartRefreshLayout refresh4 = getRefresh();
            Intrinsics.checkNotNull(refresh4);
            ClassicsHeader refreshHeader2 = getRefreshHeader();
            Intrinsics.checkNotNull(refreshHeader2);
            ViewUtilKt.setRefreshTileView(colorMode8, refresh4, refreshHeader2, getContext());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$10(final Builder this$0, final Context context, final Activity activity, BaseQuickAdapter baseQuickAdapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            Intrinsics.checkNotNullParameter(baseQuickAdapter, "<anonymous parameter 0>");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            if (FastClickUtilKt.isFastClick()) {
                return;
            }
            final BookReview item = this$0.mAdapter.getItem(i2);
            Intrinsics.checkNotNull(item);
            new CommentItemChooseDialog.Builder(context, Intrinsics.areEqual(item.getUserId(), ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "user_id", null, 2, null)), item.getNickname() + (char) 65306 + item.getComment()).setOnItemClick(new Function1<String, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$7$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
                java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
                	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
                	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
                	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
                	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
                	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
                	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
                 */
                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    switch (it.hashCode()) {
                        case 646183:
                            if (it.equals("举报")) {
                                ReportReasonChooseDialog.Builder builder = new ReportReasonChooseDialog.Builder(context, item.getComment(), item.getId(), true);
                                final ReviewCommentDialog.Builder builder2 = this$0;
                                final Context context2 = context;
                                final BookReview bookReview = item;
                                builder.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$7$1.4
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                                        invoke(num.intValue(), str);
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(int i3, @NotNull String str) {
                                        Intrinsics.checkNotNullParameter(str, "str");
                                        builder2.commentReport(context2, bookReview.getId(), String.valueOf(i3 + 1), str);
                                    }
                                }).show();
                                break;
                            }
                            break;
                        case 690244:
                            if (it.equals("删除")) {
                                this$0.delReviewComment(item);
                                break;
                            }
                            break;
                        case 712175:
                            if (it.equals("回复")) {
                                AddReviewCommentDialog.Builder builder3 = new AddReviewCommentDialog.Builder(activity, context, this$0.mBookId, this$0.mChapterId, this$0.mId, item.getId(), this$0.paragraphContent, "回复：" + item.getNickname(), null, null, false, item.getReplyPrimaryId(), R2.attr.ic_info_desc_had_collection, null);
                                final ReviewCommentDialog.Builder builder4 = this$0;
                                final Context context3 = context;
                                builder3.setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$7$1.1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public /* bridge */ /* synthetic */ Unit invoke() {
                                        invoke2();
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2() {
                                        builder4.page = 1;
                                        ReviewCommentDialog.CallBack callBack = builder4.cb;
                                        if (callBack != null) {
                                            callBack.updateParagraphCount(true);
                                        }
                                        ReviewCommentDialog.Builder builder5 = builder4;
                                        ReviewCommentDialog.Builder.loadData$default(builder5, context3, builder5.mChapterId, builder4.mId, false, 8, null);
                                    }
                                }).show();
                                break;
                            }
                            break;
                        case 727753:
                            if (it.equals("复制")) {
                                FileUtils fileUtils = FileUtils.INSTANCE;
                                Context context4 = context;
                                BookReview bookReview2 = item;
                                Intrinsics.checkNotNull(bookReview2);
                                fileUtils.copyContent(context4, bookReview2.getComment());
                                break;
                            }
                            break;
                        case 761248:
                            if (it.equals("封禁")) {
                                ReportReasonChooseDialog.Builder builder5 = new ReportReasonChooseDialog.Builder(context, item.getComment(), item.getId(), false);
                                final ReviewCommentDialog.Builder builder6 = this$0;
                                final Context context5 = context;
                                final BookReview bookReview3 = item;
                                builder5.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$7$1.3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(2);
                                    }

                                    @Override // kotlin.jvm.functions.Function2
                                    public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                                        invoke(num.intValue(), str);
                                        return Unit.INSTANCE;
                                    }

                                    public final void invoke(int i3, @NotNull String str) {
                                        Intrinsics.checkNotNullParameter(str, "str");
                                        builder6.accountBanned(context5, String.valueOf(i3 + 1), str, bookReview3.getUserId());
                                    }
                                }).show();
                                break;
                            }
                            break;
                        case 1045307:
                            if (it.equals("编辑")) {
                                AddReviewCommentDialog.Builder builder7 = new AddReviewCommentDialog.Builder(activity, context, this$0.mBookId, this$0.mChapterId, this$0.mId, item.getId(), this$0.paragraphContent, "编辑评论", item.getComment(), item.getPicture(), false, item.getReplyPrimaryId(), 1024, null);
                                final ReviewCommentDialog.Builder builder8 = this$0;
                                final Context context6 = context;
                                builder7.setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$7$1.2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // kotlin.jvm.functions.Function0
                                    public /* bridge */ /* synthetic */ Unit invoke() {
                                        invoke2();
                                        return Unit.INSTANCE;
                                    }

                                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                    public final void invoke2() {
                                        builder8.page = 1;
                                        ReviewCommentDialog.Builder builder9 = builder8;
                                        ReviewCommentDialog.Builder.loadData$default(builder9, context6, builder9.mChapterId, builder8.mId, false, 8, null);
                                    }
                                }).show();
                                break;
                            }
                            break;
                    }
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$11(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$12(Builder this$0, Context context, String chapterId, String id, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(chapterId, "$chapterId");
            Intrinsics.checkNotNullParameter(id, "$id");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.page = 1;
            loadData$default(this$0, context, chapterId, id, false, 8, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$13(Builder this$0, Context context, String chapterId, String id, RefreshLayout it) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(chapterId, "$chapterId");
            Intrinsics.checkNotNullParameter(id, "$id");
            Intrinsics.checkNotNullParameter(it, "it");
            this$0.page++;
            loadData$default(this$0, context, chapterId, id, false, 8, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$14(final Builder this$0, Activity activity, final Context context, String bookId, final String chapterId, final String id, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(bookId, "$bookId");
            Intrinsics.checkNotNullParameter(chapterId, "$chapterId");
            Intrinsics.checkNotNullParameter(id, "$id");
            Log.INSTANCE.logE("gra_content", this$0.paragraphContent);
            new AddReviewCommentDialog.Builder(activity, context, bookId, chapterId, id, "", this$0.paragraphContent, "写段评", null, null, true, null, R2.attr.plvRedPointColor, null).setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$12$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.page = 1;
                    this.this$0.loadData(context, chapterId, id, true);
                    ReviewCommentDialog.CallBack callBack = this.this$0.cb;
                    if (callBack != null) {
                        callBack.updateParagraphCount(true);
                    }
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$4(Builder this$0, View view) {
            RecyclerView.LayoutManager layoutManager;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            RecyclerView recycler = this$0.getRecycler();
            if (recycler == null || (layoutManager = recycler.getLayoutManager()) == null || !(layoutManager instanceof LinearLayoutManager)) {
                return;
            }
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(0, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$8(Builder this$0, View view) {
            Object next;
            RecyclerView recycler;
            RecyclerView.LayoutManager layoutManager;
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Iterator<T> it = this$0.mCommentList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (((BookReview) next).getType() == 2) {
                        break;
                    }
                }
            }
            if (next != null) {
                Iterator<BookReview> it2 = this$0.mCommentList.iterator();
                int i2 = 0;
                while (true) {
                    if (!it2.hasNext()) {
                        i2 = -1;
                        break;
                    } else {
                        if (it2.next().getType() == 2) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                if (i2 == -1 || (recycler = this$0.getRecycler()) == null || (layoutManager = recycler.getLayoutManager()) == null || !(layoutManager instanceof LinearLayoutManager)) {
                    return;
                }
                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i2, 0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void accountBanned(Context context, String reasonId, String day, String userId) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReviewCommentDialog$Builder$accountBanned$1(MapsKt__MapsKt.hashMapOf(new Pair("days", day), new Pair("reason_id", reasonId), new Pair("target_user_id", userId), new Pair("type", "ebook")), null), 15, null), null, new ReviewCommentDialog$Builder$accountBanned$2(context, this, null), 1, null), null, new ReviewCommentDialog$Builder$accountBanned$3(null), 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void addSupportOrOppse(BookReview item, boolean isSupport, int position, int parentPos) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReviewCommentDialog$Builder$addSupportOrOppse$1(isSupport, MapsKt__MapsKt.hashMapOf(new Pair("review_id", item.getId())), null), 15, null), null, new ReviewCommentDialog$Builder$addSupportOrOppse$2(position, isSupport, item, this, parentPos, null), 1, null), null, new ReviewCommentDialog$Builder$addSupportOrOppse$3(null), 1, null);
        }

        public static /* synthetic */ void addSupportOrOppse$default(Builder builder, BookReview bookReview, boolean z2, int i2, int i3, int i4, Object obj) {
            if ((i4 & 8) != 0) {
                i3 = 0;
            }
            builder.addSupportOrOppse(bookReview, z2, i2, i3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void commentReport(Context context, String commentId, String reasonId, String reason) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReviewCommentDialog$Builder$commentReport$1(MapsKt__MapsKt.hashMapOf(new Pair("review_id", commentId), new Pair("reason_id", reasonId), new Pair("content", reason)), null), 15, null), null, new ReviewCommentDialog$Builder$commentReport$2(context, this, null), 1, null), null, new ReviewCommentDialog$Builder$commentReport$3(null), 1, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(final Activity activity, final Context context, final QuickViewHolder holder, final int position, final BookReview item) {
            StringBuilder sb;
            int i2;
            int i3;
            boolean z2;
            int i4;
            boolean z3;
            if (item.getTitle().length() > 0) {
                int i5 = R.id.ll_type_content;
                holder.setVisible(i5, true);
                View view = holder.getView(R.id.type_line_customer);
                int colorMode = ReadConfig.INSTANCE.getColorMode();
                view.setBackground(new ColorDrawable(context.getColor(colorMode != 1 ? colorMode != 2 ? R.color.color_eeeeee : R.color.color_1C2134 : R.color.color_EDE2C3)));
                this.mCommentList.get(position).setSuspend(true);
                holder.setGone(R.id.ll_content, true);
                holder.setVisible(i5, true);
            } else {
                int type = item.getType();
                BookReview item2 = this.mAdapter.getItem(position - 1);
                Integer numValueOf = item2 != null ? Integer.valueOf(item2.getType()) : null;
                int i6 = R.id.ll_type_content;
                holder.setGone(i6, true);
                this.mCommentList.get(position).setSuspend(numValueOf == null || type != numValueOf.intValue());
                boolean z4 = holder.getView(i6).getVisibility() == 0;
                int i7 = R.id.ll_content;
                holder.setGone(i7, z4);
                holder.setVisible(i7, true);
                holder.setGone(i6, true);
            }
            int i8 = R.id.tv_type_text;
            if (item.getType() == 1) {
                sb = new StringBuilder();
                sb.append("最热评论 （");
                i2 = this.hotCount;
            } else {
                sb = new StringBuilder();
                sb.append("最新评论 （");
                i2 = this.timeLineCount;
            }
            sb.append(i2);
            sb.append("条）");
            holder.setText(i8, sb.toString());
            ReadConfig readConfig = ReadConfig.INSTANCE;
            holder.setTextColor(i8, getColor(readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
            RImageView rImageView = (RImageView) holder.getView(R.id.img_avatar);
            ImageLoader imageLoader = ImageLoader.INSTANCE;
            imageLoader.load(context, item.getAvatar()).placeholder(R.drawable.personal_headimg_icon).into(rImageView);
            rImageView.setTag(item.getUserId());
            rImageView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.v0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ReviewCommentDialog.Builder.convert$lambda$15(item, context, view2);
                }
            });
            this.mAdapter.getItemCount();
            View view2 = holder.getView(R.id.v_line);
            int colorMode2 = readConfig.getColorMode();
            view2.setBackground(new ColorDrawable(context.getColor(colorMode2 != 1 ? colorMode2 != 2 ? R.color.color_eeeeee : R.color.color_1C2134 : R.color.color_EDE2C3)));
            int i9 = R.id.tv_support;
            RTextView rTextView = (RTextView) holder.getView(i9);
            int i10 = R.id.tv_opposition;
            RTextView rTextView2 = (RTextView) holder.getView(i10);
            rTextView.setText("赞同(" + item.getSupportCount() + ')');
            rTextView.setSelected(item.isSupport() == 1);
            rTextView2.setText("反对(" + item.getOppositionCount() + ')');
            rTextView2.setSelected(item.isOpposition() == 1);
            BaseViewHolder text = holder.setText(R.id.tv_nick_name, item.getNickname());
            int i11 = R.id.tv_hospital;
            BaseViewHolder text2 = text.setText(i11, item.getSchool());
            int i12 = R.id.tv_time;
            BaseViewHolder text3 = text2.setText(i12, item.getCtime());
            int i13 = R.id.tv_comment;
            BaseViewHolder text4 = text3.setText(i13, item.getComment());
            int i14 = R.id.img_picture;
            if (item.getPicture().length() == 0) {
                i3 = i12;
                z2 = true;
            } else {
                i3 = i12;
                z2 = false;
            }
            text4.setGone(i14, z2);
            ImageView imageView = (ImageView) holder.getView(R.id.personalVipIv);
            if (Intrinsics.areEqual(item.getIdentity(), "0") || Intrinsics.areEqual(item.getIdentity(), "")) {
                i4 = i10;
                imageView.setVisibility(8);
            } else {
                imageView.setVisibility(0);
                if (readConfig.getColorMode() != 0) {
                    i4 = i10;
                    if (readConfig.getColorMode() != 1) {
                        if (readConfig.getColorMode() == 2) {
                            imageView.setImageResource(Intrinsics.areEqual(item.getIdentity(), "2") ? R.drawable.personal_vip_blue : R.drawable.personal_vip_night);
                        }
                    }
                } else {
                    i4 = i10;
                }
                imageView.setImageResource(Intrinsics.areEqual(item.getIdentity(), "2") ? R.drawable.personal_vip_blue : R.drawable.personal_vip);
            }
            RImageView rImageView2 = (RImageView) holder.getView(i14);
            if (item.getPicture().length() > 0) {
                imageLoader.load(context, item.getPicture()).into(rImageView2);
            }
            FloorViews floorViews = (FloorViews) holder.getView(R.id.floor);
            SubFloorComments subFloorComments = new SubFloorComments(item.getReplyList());
            if (subFloorComments.size() > 0) {
                z3 = false;
                floorViews.setVisibility(0);
            } else {
                z3 = false;
                floorViews.setVisibility(8);
            }
            floorViews.setComments(subFloorComments, z3, this.mAdapter);
            floorViews.setFactory(new SubFloorFactorys());
            floorViews.setIslouzhu("1");
            if (readConfig.getColorMode() != 2) {
                floorViews.setBoundDrawer(getContext().getResources().getDrawable(R.drawable.ebook_bondcomm));
            } else {
                floorViews.setBoundDrawer(getContext().getResources().getDrawable(R.drawable.ebook_bondcomm_night));
            }
            int i15 = i3;
            int i16 = i4;
            floorViews.setmCommentListenter(new FloorCommentListenter() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2
                @Override // com.ykb.ebook.weight.FloorCommentListenter
                public void commListOppose(@Nullable String is_Oppose, @NotNull String id, @Nullable String obj_id) {
                    Intrinsics.checkNotNullParameter(id, "id");
                    item.setId(id);
                    this.addSupportOrOppse(item, false, -1, position);
                }

                @Override // com.ykb.ebook.weight.FloorCommentListenter
                public void commListPraise(@Nullable String is_praise, @Nullable View v2, @Nullable String id, @Nullable String obj_id) {
                }

                @Override // com.ykb.ebook.weight.FloorCommentListenter
                public void commListPraiseFaile() {
                }

                @Override // com.ykb.ebook.weight.FloorCommentListenter
                public void commListReply(@NotNull BookReview cmt, @Nullable View v2) {
                    Intrinsics.checkNotNullParameter(cmt, "cmt");
                    if (cmt.getReplyCount() > 0) {
                        ReplyCollectionAct.INSTANCE.newIntent(context, this.mBookId, this.mChapterId, this.mId, this.paragraphContent, cmt, false);
                        return;
                    }
                    AddReviewCommentDialog.Builder builder = new AddReviewCommentDialog.Builder(activity, context, this.mBookId, this.mChapterId, this.mId, cmt.getId(), this.paragraphContent, "回复：" + cmt.getNickname(), null, null, false, cmt.getReplyPrimaryId(), R2.attr.ic_info_desc_had_collection, null);
                    final ReviewCommentDialog.Builder builder2 = this;
                    final Context context2 = context;
                    builder.setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2$commListReply$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            builder2.page = 1;
                            ReviewCommentDialog.Builder builder3 = builder2;
                            ReviewCommentDialog.Builder.loadData$default(builder3, context2, builder3.mChapterId, builder2.mId, false, 8, null);
                        }
                    }).show();
                }

                @Override // com.ykb.ebook.weight.FloorCommentListenter
                public void commListSupport(@Nullable String is_Support, @NotNull String id, @Nullable String obj_id) {
                    Intrinsics.checkNotNullParameter(id, "id");
                    item.setId(id);
                    this.addSupportOrOppse(item, true, -1, position);
                }

                @Override // com.ykb.ebook.weight.FloorCommentListenter
                public void commentListenerData(@Nullable final BookReview commListBean, @Nullable View v2) {
                    Intrinsics.checkNotNull(commListBean);
                    boolean zAreEqual = Intrinsics.areEqual(commListBean.getUserId(), ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "user_id", null, 2, null));
                    CommentItemChooseDialog.Builder builder = new CommentItemChooseDialog.Builder(context, zAreEqual, commListBean.getNickname() + (char) 65306 + commListBean.getComment());
                    final Activity activity2 = activity;
                    final Context context2 = context;
                    final ReviewCommentDialog.Builder builder2 = this;
                    builder.setOnItemClick(new Function1<String, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2$commentListenerData$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(String str) {
                            invoke2(str);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
                        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
                        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
                        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
                        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
                        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
                        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
                        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
                         */
                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(@NotNull String it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            switch (it.hashCode()) {
                                case 646183:
                                    if (it.equals("举报")) {
                                        ReportReasonChooseDialog.Builder builder3 = new ReportReasonChooseDialog.Builder(context2, commListBean.getComment(), commListBean.getId(), true);
                                        final ReviewCommentDialog.Builder builder4 = builder2;
                                        final Context context3 = context2;
                                        final BookReview bookReview = commListBean;
                                        builder3.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2$commentListenerData$1.4
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                                                invoke(num.intValue(), str);
                                                return Unit.INSTANCE;
                                            }

                                            public final void invoke(int i17, @NotNull String str) {
                                                Intrinsics.checkNotNullParameter(str, "str");
                                                builder4.commentReport(context3, bookReview.getId(), String.valueOf(i17 + 1), str);
                                            }
                                        }).show();
                                        break;
                                    }
                                    break;
                                case 690244:
                                    if (it.equals("删除")) {
                                        builder2.delReviewComment(commListBean);
                                        break;
                                    }
                                    break;
                                case 712175:
                                    if (it.equals("回复")) {
                                        AddReviewCommentDialog.Builder builder5 = new AddReviewCommentDialog.Builder(activity2, context2, builder2.mBookId, builder2.mChapterId, builder2.mId, commListBean.getId(), builder2.paragraphContent, "回复：" + commListBean.getNickname(), null, null, false, commListBean.getReplyPrimaryId(), R2.attr.ic_info_desc_had_collection, null);
                                        final ReviewCommentDialog.Builder builder6 = builder2;
                                        final Context context4 = context2;
                                        builder5.setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2$commentListenerData$1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public /* bridge */ /* synthetic */ Unit invoke() {
                                                invoke2();
                                                return Unit.INSTANCE;
                                            }

                                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2() {
                                                builder6.page = 1;
                                                ReviewCommentDialog.CallBack callBack = builder6.cb;
                                                if (callBack != null) {
                                                    callBack.updateParagraphCount(true);
                                                }
                                                ReviewCommentDialog.Builder builder7 = builder6;
                                                ReviewCommentDialog.Builder.loadData$default(builder7, context4, builder7.mChapterId, builder6.mId, false, 8, null);
                                            }
                                        }).show();
                                        break;
                                    }
                                    break;
                                case 727753:
                                    if (it.equals("复制")) {
                                        FileUtils fileUtils = FileUtils.INSTANCE;
                                        Context context5 = context2;
                                        BookReview bookReview2 = commListBean;
                                        Intrinsics.checkNotNull(bookReview2);
                                        fileUtils.copyContent(context5, bookReview2.getComment());
                                        break;
                                    }
                                    break;
                                case 761248:
                                    if (it.equals("封禁")) {
                                        ReportReasonChooseDialog.Builder builder7 = new ReportReasonChooseDialog.Builder(context2, commListBean.getComment(), commListBean.getId(), false);
                                        final ReviewCommentDialog.Builder builder8 = builder2;
                                        final Context context6 = context2;
                                        final BookReview bookReview3 = commListBean;
                                        builder7.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2$commentListenerData$1.3
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                                                invoke(num.intValue(), str);
                                                return Unit.INSTANCE;
                                            }

                                            public final void invoke(int i17, @NotNull String str) {
                                                Intrinsics.checkNotNullParameter(str, "str");
                                                builder8.accountBanned(context6, String.valueOf(i17 + 1), str, bookReview3.getUserId());
                                            }
                                        }).show();
                                        break;
                                    }
                                    break;
                                case 1045307:
                                    if (it.equals("编辑")) {
                                        AddReviewCommentDialog.Builder builder9 = new AddReviewCommentDialog.Builder(activity2, context2, builder2.mBookId, builder2.mChapterId, builder2.mId, commListBean.getId(), builder2.paragraphContent, "编辑评论", commListBean.getComment(), commListBean.getPicture(), false, commListBean.getReplyPrimaryId(), 1024, null);
                                        final ReviewCommentDialog.Builder builder10 = builder2;
                                        final Context context7 = context2;
                                        builder9.setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$2$commentListenerData$1.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            public /* bridge */ /* synthetic */ Unit invoke() {
                                                invoke2();
                                                return Unit.INSTANCE;
                                            }

                                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2() {
                                                builder10.page = 1;
                                                ReviewCommentDialog.Builder builder11 = builder10;
                                                ReviewCommentDialog.Builder.loadData$default(builder11, context7, builder11.mChapterId, builder10.mId, false, 8, null);
                                            }
                                        }).show();
                                        break;
                                    }
                                    break;
                            }
                        }
                    }).show();
                }
            });
            floorViews.init();
            TextView textView = (TextView) holder.getView(R.id.tv_comment_reply);
            Log.INSTANCE.logE("reply_count", "回复数量：" + item.getReplyCount());
            int colorMode3 = readConfig.getColorMode();
            if (colorMode3 == 0 || colorMode3 == 1) {
                TextView textView2 = (TextView) holder.getView(i11);
                int i17 = R.color.color_909090;
                textView2.setTextColor(getColor(i17));
                ((TextView) holder.getView(i15)).setTextColor(getColor(i17));
                TextView textView3 = (TextView) holder.getView(i13);
                int i18 = R.color.color_303030;
                textView3.setTextColor(getColor(i18));
                textView.setTextColor(item.getReplyCount() > 0 ? AppCtxKt.getAppCtx().getColor(i18) : AppCtxKt.getAppCtx().getColor(R.color.color_c2c6cb));
                RTextViewHelper helper = rTextView.getHelper();
                Context appCtx = AppCtxKt.getAppCtx();
                int i19 = R.color.color_c2c6cb;
                helper.setTextColorNormal(appCtx.getColor(i19));
                rTextView2.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(i19));
            } else if (colorMode3 == 2) {
                TextView textView4 = (TextView) holder.getView(i11);
                int i20 = R.color.color_575F79;
                textView4.setTextColor(getColor(i20));
                ((TextView) holder.getView(i15)).setTextColor(getColor(i20));
                TextView textView5 = (TextView) holder.getView(i13);
                int i21 = R.color.color_7380a9;
                textView5.setTextColor(getColor(i21));
                textView.setTextColor(item.getReplyCount() > 0 ? AppCtxKt.getAppCtx().getColor(i21) : AppCtxKt.getAppCtx().getColor(i20));
                rTextView.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(i20));
                rTextView2.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(i20));
            }
            if (item.getReplyCount() > 0) {
                textView.setBackgroundResource(readConfig.getColorMode() == 2 ? R.drawable.shape_reply_selected_bg_night : R.drawable.shape_reply_selected_bg);
                textView.setText(item.getReplyCount() + "回复");
            } else {
                textView.setBackgroundColor(AppCtxKt.getAppCtx().getColor(R.color.transparent));
                textView.setText("回复");
            }
            ((TextView) holder.getView(i9)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.y0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    ReviewCommentDialog.Builder.convert$lambda$16(item, holder, this, position, view3);
                }
            });
            ((TextView) holder.getView(i16)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.z0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    ReviewCommentDialog.Builder.convert$lambda$17(item, holder, this, position, view3);
                }
            });
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.a1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    ReviewCommentDialog.Builder.convert$lambda$18(item, context, this, activity, view3);
                }
            });
            holder.getView(i14).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.b1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    ReviewCommentDialog.Builder.convert$lambda$19(context, item, view3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$15(BookReview item, Context context, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(context, "$context");
            if (!Intrinsics.areEqual("0", item.is_logout())) {
                Toast.makeText(context, "该用户已注销", 0).show();
                return;
            }
            Object tag = view.getTag();
            Intrinsics.checkNotNull(tag, "null cannot be cast to non-null type kotlin.String");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setPackage(context.getPackageName());
            intent.setData(Uri.parse("ebook://ykb_user_info/?user_id=" + ((String) tag)));
            context.startActivity(intent);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$16(BookReview item, QuickViewHolder holder, Builder this$0, int i2, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(holder, "$holder");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (item.isOpposition() == 1) {
                return;
            }
            if (item.isSupport() == 0) {
                ViewUtilKt.toastPop(holder.getView(R.id.tv_support), 0);
            }
            addSupportOrOppse$default(this$0, item, true, i2, 0, 8, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$17(BookReview item, QuickViewHolder holder, Builder this$0, int i2, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(holder, "$holder");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            if (item.isSupport() == 1) {
                return;
            }
            if (item.isOpposition() == 0) {
                ViewUtilKt.toastPop(holder.getView(R.id.tv_opposition), 1);
            }
            addSupportOrOppse$default(this$0, item, false, i2, 0, 8, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$18(BookReview item, final Context context, final Builder this$0, Activity activity, View view) {
            Intrinsics.checkNotNullParameter(item, "$item");
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(activity, "$activity");
            if (FastClickUtilKt.isFastClick()) {
                return;
            }
            if (item.getReplyCount() > 0) {
                ReplyCollectionAct.INSTANCE.newIntent(context, this$0.mBookId, this$0.mChapterId, this$0.mId, this$0.paragraphContent, item, false);
                return;
            }
            new AddReviewCommentDialog.Builder(activity, context, this$0.mBookId, this$0.mChapterId, this$0.mId, item.getId(), this$0.paragraphContent, "回复：" + item.getNickname(), null, null, false, item.getReplyPrimaryId(), R2.attr.ic_info_desc_had_collection, null).setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.dialog.ReviewCommentDialog$Builder$convert$5$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    this.this$0.page = 1;
                    ReviewCommentDialog.Builder builder = this.this$0;
                    ReviewCommentDialog.Builder.loadData$default(builder, context, builder.mChapterId, this.this$0.mId, false, 8, null);
                }
            }).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void convert$lambda$19(Context context, BookReview item, View view) {
            Intrinsics.checkNotNullParameter(context, "$context");
            Intrinsics.checkNotNullParameter(item, "$item");
            new XPopup.Builder(context).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void delReviewComment(BookReview item) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReviewCommentDialog$Builder$delReviewComment$1(MapsKt__MapsKt.hashMapOf(new Pair("id", item.getId())), null), 15, null), null, new ReviewCommentDialog$Builder$delReviewComment$2(this, item, null), 1, null), null, new ReviewCommentDialog$Builder$delReviewComment$3(null), 1, null);
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final RImageView getImgHead() {
            return (RImageView) this.imgHead.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final LinearLayout getLlFloat() {
            return (LinearLayout) this.llFloat.getValue();
        }

        private final LinearLayout getLyView() {
            return (LinearLayout) this.lyView.getValue();
        }

        private final TextView getMTvContent() {
            return (TextView) this.mTvContent.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final RecyclerView getRecycler() {
            return (RecyclerView) this.recycler.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final SmartRefreshLayout getRefresh() {
            return (SmartRefreshLayout) this.refresh.getValue();
        }

        private final ClassicsHeader getRefreshHeader() {
            return (ClassicsHeader) this.refreshHeader.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getTvFloatTitle() {
            return (TextView) this.tvFloatTitle.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void loadData(Context context, String chapterId, String id, boolean isPutNewComment) {
            Coroutine.onError$default(Coroutine.onSuccess$default(Coroutine.Companion.async$default(Coroutine.INSTANCE, null, null, null, null, new ReviewCommentDialog$Builder$loadData$1(MapsKt__MapsKt.hashMapOf(new Pair(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, String.valueOf(this.page)), new Pair("page_size", "20"), new Pair("chapter_id", chapterId), new Pair("paragraph_id", id), new Pair("review_type", "3")), null), 15, null), null, new ReviewCommentDialog$Builder$loadData$2(this, isPutNewComment, context, null), 1, null), null, new ReviewCommentDialog$Builder$loadData$3(this, null), 1, null);
        }

        public static /* synthetic */ void loadData$default(Builder builder, Context context, String str, String str2, boolean z2, int i2, Object obj) {
            if ((i2 & 8) != 0) {
                z2 = false;
            }
            builder.loadData(context, str, str2, z2);
        }

        @NotNull
        public final Builder setCallBack(@NotNull CallBack cb) {
            Intrinsics.checkNotNullParameter(cb, "cb");
            this.cb = cb;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void convert(QuickViewHolder holder, BookReview item, List<? extends Object> payloads) {
            int color;
            Context appCtx;
            int i2;
            if (!payloads.isEmpty()) {
                Object obj = payloads.get(0);
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
                if (((Integer) obj).intValue() == 1) {
                    RTextView rTextView = (RTextView) holder.getView(R.id.tv_support);
                    RTextView rTextView2 = (RTextView) holder.getView(R.id.tv_opposition);
                    rTextView.setText("赞同(" + item.getSupportCount() + ')');
                    rTextView.setSelected(item.isSupport() == 1);
                    rTextView2.setText("反对(" + item.getOppositionCount() + ')');
                    rTextView2.setSelected(item.isOpposition() == 1);
                    TextView textView = (TextView) holder.getView(R.id.tv_reply);
                    if (item.getReplyCount() > 0) {
                        textView.setBackgroundResource(ReadConfig.INSTANCE.getColorMode() == 2 ? R.drawable.shape_reply_selected_bg_night : R.drawable.shape_reply_selected_bg);
                        textView.setText(item.getReplyCount() + "回复");
                    } else {
                        textView.setBackgroundResource(R.drawable.shape_reply_selected_bg);
                        textView.setText("回复");
                    }
                    int colorMode = ReadConfig.INSTANCE.getColorMode();
                    if (colorMode == 0 || colorMode == 1) {
                        if (item.getReplyCount() > 0) {
                            color = AppCtxKt.getAppCtx().getColor(R.color.color_303030);
                        } else {
                            color = AppCtxKt.getAppCtx().getColor(R.color.color_c2c6cb);
                        }
                        textView.setTextColor(color);
                        RTextViewHelper helper = rTextView.getHelper();
                        Context appCtx2 = AppCtxKt.getAppCtx();
                        int i3 = R.color.color_c2c6cb;
                        helper.setTextColorNormal(appCtx2.getColor(i3));
                        rTextView2.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(i3));
                        return;
                    }
                    if (colorMode != 2) {
                        return;
                    }
                    if (item.getReplyCount() == 0) {
                        appCtx = AppCtxKt.getAppCtx();
                        i2 = R.color.color_575F79;
                    } else {
                        appCtx = AppCtxKt.getAppCtx();
                        i2 = R.color.color_7380a9;
                    }
                    textView.setTextColor(appCtx.getColor(i2));
                    RTextViewHelper helper2 = rTextView.getHelper();
                    Context appCtx3 = AppCtxKt.getAppCtx();
                    int i4 = R.color.color_575F79;
                    helper2.setTextColorNormal(appCtx3.getColor(i4));
                    rTextView2.getHelper().setTextColorNormal(AppCtxKt.getAppCtx().getColor(i4));
                }
            }
        }
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/dialog/ReviewCommentDialog$CallBack;", "", "updateParagraphCount", "", "addComment", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface CallBack {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void updateParagraphCount$default(CallBack callBack, boolean z2, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: updateParagraphCount");
                }
                if ((i2 & 1) != 0) {
                    z2 = false;
                }
                callBack.updateParagraphCount(z2);
            }
        }

        void updateParagraphCount(boolean addComment);
    }
}
