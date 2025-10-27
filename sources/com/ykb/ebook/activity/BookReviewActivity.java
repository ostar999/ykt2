package com.ykb.ebook.activity;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.appbar.AppBarLayout;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.mobile.auth.gatewayauth.Constant;
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
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.activity.BookReviewActivity;
import com.ykb.ebook.activity.ReplyCollectionAct;
import com.ykb.ebook.adapter.BookCommentsAdapter;
import com.ykb.ebook.base.BaseVmActivity;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.ActivityBookReviewBinding;
import com.ykb.ebook.dialog.BookCommentDialog;
import com.ykb.ebook.dialog.CommentItemChooseDialog;
import com.ykb.ebook.dialog.ReportReasonChooseDialog;
import com.ykb.ebook.dialog.WriteSpDialog;
import com.ykb.ebook.event.ImageUploadSuccessEvent;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.BookReview;
import com.ykb.ebook.model.CommentData;
import com.ykb.ebook.util.FilePathUtilKt;
import com.ykb.ebook.util.FileUtils;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.vm.BookReviewViewModel;
import com.ykb.ebook.weight.RoundCornerProgressBar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 Y2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001YB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010G\u001a\u00020HH\u0014J\b\u0010I\u001a\u00020HH\u0003J\b\u0010J\u001a\u00020\u0003H\u0014J\b\u0010K\u001a\u00020\u0012H\u0016J\b\u0010L\u001a\u00020HH\u0015J\u0012\u0010M\u001a\u00020H2\b\u0010N\u001a\u0004\u0018\u00010OH\u0014J\"\u0010P\u001a\u00020H2\u0006\u0010Q\u001a\u00020\u00102\u0006\u0010R\u001a\u00020\u00102\b\u0010S\u001a\u0004\u0018\u00010\u0007H\u0014J\b\u0010T\u001a\u00020\u0012H\u0002J\u0010\u0010U\u001a\u00020H2\u0006\u0010V\u001a\u00020WH\u0002J\b\u0010X\u001a\u00020HH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\f\u001a\u0004\b!\u0010\"R\u000e\u0010$\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0018X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R:\u00101\u001a.\u0012*\u0012(\u0012\f\u0012\n 3*\u0004\u0018\u00010\u000e0\u000e 3*\u0014\u0012\u000e\b\u0001\u0012\n 3*\u0004\u0018\u00010\u000e0\u000e\u0018\u000102020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000206X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u000208X\u0082.¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u000208X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020;X\u0082.¢\u0006\u0002\n\u0000R\u001d\u0010<\u001a\u0004\u0018\u0001088BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b?\u0010\f\u001a\u0004\b=\u0010>R\u000e\u0010@\u001a\u00020;X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020;X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020;X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u000208X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u000208X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u000208X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u000208X\u0082.¢\u0006\u0002\n\u0000¨\u0006Z"}, d2 = {"Lcom/ykb/ebook/activity/BookReviewActivity;", "Lcom/ykb/ebook/base/BaseVmActivity;", "Lcom/ykb/ebook/databinding/ActivityBookReviewBinding;", "Lcom/ykb/ebook/vm/BookReviewViewModel;", "()V", "albumLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/ActivityBookReviewBinding;", "binding$delegate", "Lkotlin/Lazy;", "bookTitle", "", "commentType", "", "expand", "", "hasHot", "headView", "Landroid/view/View;", "headerHeight", "highCommentProgress", "Lcom/ykb/ebook/weight/RoundCornerProgressBar;", "hotCount", "imgCover", "Lcom/ruffian/library/widget/RImageView;", "imgLock", "Landroid/widget/ImageView;", "isAddComment", "llFloat", "Landroid/widget/LinearLayout;", "getLlFloat", "()Landroid/widget/LinearLayout;", "llFloat$delegate", "lowCommentProgress", "mBookId", "mCommentDialogTitle", "mCommentsAdapter", "Lcom/ykb/ebook/adapter/BookCommentsAdapter;", "mNewsPosition", "mRate", "mRateContent", "mRatePic", "middleCommentProgress", "newsCount", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "permissionsLauncher", "", "kotlin.jvm.PlatformType", "publishComment2LatestPosition", "readSecond", "", "tvAuthor", "Landroid/widget/TextView;", "tvCommentNum", "tvContinue", "Lcom/ruffian/library/widget/RTextView;", "tvFloatTitle", "getTvFloatTitle", "()Landroid/widget/TextView;", "tvFloatTitle$delegate", "tvHigh", "tvLow", "tvMiddle", "tvReadCommentHint", "tvScore", "tvScoreHint", "tvTitle", "doBusiness", "", "initHeaderView", "initViewModel", "isShowLoading", "observeViewModel", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onActivityResult", Constant.LOGIN_ACTIVITY_REQUEST_CODE, "resultCode", "data", "readSecondFilter", "setCommentHint", "bookInfo", "Lcom/ykb/ebook/model/BookInfo;", "showBookCommentDialog", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBookReviewActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BookReviewActivity.kt\ncom/ykb/ebook/activity/BookReviewActivity\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,938:1\n13#2,10:939\n42#3:949\n42#3:950\n42#3:951\n42#3:952\n42#3:953\n42#3:954\n42#3:955\n42#3:956\n42#3:957\n42#3:958\n42#3:959\n42#3:960\n42#3:961\n42#3:962\n42#3:963\n42#3:964\n42#3:965\n42#3:966\n42#3:967\n42#3:968\n42#3:969\n42#3:970\n42#3:971\n42#3:972\n42#3:973\n42#3:974\n42#3:975\n42#3:976\n42#3:977\n42#3:978\n42#3:979\n42#3:980\n42#3:981\n42#3:982\n42#3:983\n42#3:984\n42#3:985\n42#3:986\n42#3:987\n42#3:988\n42#3:989\n42#3:990\n42#3:991\n42#3:992\n215#4,2:993\n1#5:995\n350#6,7:996\n*S KotlinDebug\n*F\n+ 1 BookReviewActivity.kt\ncom/ykb/ebook/activity/BookReviewActivity\n*L\n70#1:939,10\n138#1:949\n141#1:950\n143#1:951\n146#1:952\n148#1:953\n151#1:954\n153#1:955\n154#1:956\n155#1:957\n156#1:958\n162#1:959\n164#1:960\n336#1:961\n341#1:962\n363#1:963\n367#1:964\n590#1:965\n591#1:966\n592#1:967\n593#1:968\n596#1:969\n597#1:970\n602#1:971\n603#1:972\n604#1:973\n606#1:974\n608#1:975\n612#1:976\n619#1:977\n621#1:978\n631#1:979\n632#1:980\n634#1:981\n635#1:982\n638#1:983\n639#1:984\n641#1:985\n642#1:986\n645#1:987\n646#1:988\n648#1:989\n649#1:990\n911#1:991\n912#1:992\n117#1:993,2\n548#1:996,7\n*E\n"})
/* loaded from: classes6.dex */
public final class BookReviewActivity extends BaseVmActivity<ActivityBookReviewBinding, BookReviewViewModel> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    public static final int TYPE_BOOK_COMMENT = 1;
    public static final int TYPE_PARAGRAPH_COMMENT = 2;
    private ActivityResultLauncher<Intent> albumLauncher;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;
    private boolean hasHot;

    @Nullable
    private View headView;
    private int headerHeight;
    private RoundCornerProgressBar highCommentProgress;
    private int hotCount;
    private RImageView imgCover;
    private ImageView imgLock;
    private boolean isAddComment;
    private RoundCornerProgressBar lowCommentProgress;
    private BookCommentsAdapter mCommentsAdapter;
    private int mNewsPosition;
    private RoundCornerProgressBar middleCommentProgress;
    private int newsCount;

    @NotNull
    private final ActivityResultLauncher<String[]> permissionsLauncher;
    private boolean publishComment2LatestPosition;
    private long readSecond;
    private TextView tvAuthor;
    private TextView tvCommentNum;
    private RTextView tvContinue;
    private RTextView tvHigh;
    private RTextView tvLow;
    private RTextView tvMiddle;
    private TextView tvReadCommentHint;
    private TextView tvScore;
    private TextView tvScoreHint;
    private TextView tvTitle;

    @NotNull
    private String mBookId = "";

    @NotNull
    private String mRate = "0";

    @NotNull
    private String mRateContent = "";

    @NotNull
    private String bookTitle = "";

    @NotNull
    private String mRatePic = "";

    @NotNull
    private String mCommentDialogTitle = "评价本书";
    private boolean expand = true;
    private int commentType = 1;

    /* renamed from: tvFloatTitle$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy tvFloatTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.activity.BookReviewActivity$tvFloatTitle$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final TextView invoke() {
            return (TextView) this.this$0.findViewById(R.id.tv_type_text_float);
        }
    });

    /* renamed from: llFloat$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy llFloat = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.activity.BookReviewActivity$llFloat$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final LinearLayout invoke() {
            return (LinearLayout) this.this$0.findViewById(R.id.ll_float);
        }
    });
    private int page = 1;
    private int pageSize = 20;

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/activity/BookReviewActivity$Companion;", "", "()V", "TYPE_BOOK_COMMENT", "", "TYPE_PARAGRAPH_COMMENT", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context, @NotNull String bookId) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intent intent = new Intent(context, (Class<?>) BookReviewActivity.class);
            intent.putExtra("bookId", bookId);
            context.startActivity(intent);
        }
    }

    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", AdvanceSetting.NETWORK_TYPE, "Lcom/ykb/ebook/model/CommentData;", "kotlin.jvm.PlatformType", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nBookReviewActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BookReviewActivity.kt\ncom/ykb/ebook/activity/BookReviewActivity$observeViewModel$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,938:1\n2634#2:939\n2634#2:941\n2634#2:943\n1#3:940\n1#3:942\n1#3:944\n*S KotlinDebug\n*F\n+ 1 BookReviewActivity.kt\ncom/ykb/ebook/activity/BookReviewActivity$observeViewModel$1\n*L\n713#1:939\n727#1:941\n763#1:943\n713#1:940\n727#1:942\n763#1:944\n*E\n"})
    /* renamed from: com.ykb.ebook.activity.BookReviewActivity$observeViewModel$1, reason: invalid class name */
    public static final class AnonymousClass1 extends Lambda implements Function1<CommentData, Unit> {
        public AnonymousClass1() {
            super(1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$2(BookReviewActivity this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.getBinding().tvNew.performClick();
        }

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(CommentData commentData) {
            invoke2(commentData);
            return Unit.INSTANCE;
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(CommentData commentData) {
            BookReviewActivity.this.hideLoading();
            if (BookReviewActivity.this.page == 1) {
                BookReviewActivity.this.getBinding().refreshLayout.finishRefresh();
            }
            LinearLayout llFloat = BookReviewActivity.this.getLlFloat();
            if (llFloat != null) {
                ViewExtensionsKt.visible(llFloat);
            }
            if (commentData == null) {
                BookReviewActivity.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                return;
            }
            List<BookReview> hot = commentData.getHot();
            List<BookReview> time_line = commentData.getTime_line();
            BookReviewActivity.this.hasHot = false;
            BookCommentsAdapter bookCommentsAdapter = null;
            if (BookReviewActivity.this.page == 1) {
                ArrayList arrayList = new ArrayList();
                List<BookReview> list = hot;
                if (!list.isEmpty()) {
                    Iterator<T> it = hot.iterator();
                    while (it.hasNext()) {
                        ((BookReview) it.next()).setType(1);
                    }
                    BookReviewActivity.this.hasHot = true;
                    int hot_total = commentData.getHot_total();
                    BookReviewActivity.this.hotCount = commentData.getHot_total();
                    arrayList.add(new BookReview(false, null, null, null, null, null, 0, 0, null, null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, true, 1, null, 0, "最热评论（" + hot_total + "条）", null, false, false, 0, false, false, null, null, -159383553, 15, null));
                    arrayList.addAll(list);
                    if (commentData.getMore_hot() == 1) {
                        arrayList.add(new BookReview(false, null, null, null, null, null, 0, 0, null, null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, false, 0, null, 3, "查看更多热评", null, false, false, 0, false, false, null, null, -201326593, 15, null));
                    }
                }
                List<BookReview> list2 = time_line;
                if (!list2.isEmpty()) {
                    Iterator<T> it2 = time_line.iterator();
                    while (it2.hasNext()) {
                        ((BookReview) it2.next()).setType(2);
                    }
                    BookReviewActivity.this.newsCount = commentData.getTime_line_total();
                    arrayList.add(new BookReview(false, null, null, null, null, null, 0, 0, null, null, null, 0, null, null, null, 0, null, null, null, null, null, null, null, true, 2, null, 0, "最新评论（" + commentData.getTime_line_total() + "条）", null, false, false, 0, false, false, null, null, -159383553, 15, null));
                    BookReviewActivity.this.mNewsPosition = arrayList.size();
                    arrayList.addAll(list2);
                }
                BookCommentsAdapter bookCommentsAdapter2 = BookReviewActivity.this.mCommentsAdapter;
                if (bookCommentsAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                    bookCommentsAdapter2 = null;
                }
                bookCommentsAdapter2.submitList(arrayList);
                if (arrayList.size() < BookReviewActivity.this.pageSize) {
                    BookReviewActivity.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                }
                if ((!commentData.getHot().isEmpty()) && (!commentData.getTime_line().isEmpty())) {
                    TextView tvFloatTitle = BookReviewActivity.this.getTvFloatTitle();
                    if (tvFloatTitle != null) {
                        tvFloatTitle.setText("最热评论（" + BookReviewActivity.this.hotCount + "条）");
                    }
                    View viewFindViewById = BookReviewActivity.this.findViewById(R.id.ll_comment_locate);
                    if (viewFindViewById != null) {
                        ViewExtensionsKt.visible(viewFindViewById);
                    }
                } else {
                    TextView tvFloatTitle2 = BookReviewActivity.this.getTvFloatTitle();
                    if (tvFloatTitle2 != null) {
                        tvFloatTitle2.setText("最新评论（" + BookReviewActivity.this.newsCount + "条）");
                    }
                    View viewFindViewById2 = BookReviewActivity.this.findViewById(R.id.ll_comment_locate);
                    if (viewFindViewById2 != null) {
                        ViewExtensionsKt.gone(viewFindViewById2);
                    }
                }
                if (BookReviewActivity.this.publishComment2LatestPosition) {
                    if (BookReviewActivity.this.expand) {
                        BookReviewActivity.this.getBinding().appbar.setExpanded(false);
                    }
                    SmartRefreshLayout smartRefreshLayout = BookReviewActivity.this.getBinding().refreshLayout;
                    final BookReviewActivity bookReviewActivity = BookReviewActivity.this;
                    smartRefreshLayout.postDelayed(new Runnable() { // from class: com.ykb.ebook.activity.k0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BookReviewActivity.AnonymousClass1.invoke$lambda$2(bookReviewActivity);
                        }
                    }, !BookReviewActivity.this.expand ? 1500L : 500L);
                    BookReviewActivity.this.publishComment2LatestPosition = false;
                }
            } else {
                List<BookReview> list3 = time_line;
                if (true ^ list3.isEmpty()) {
                    Iterator<T> it3 = time_line.iterator();
                    while (it3.hasNext()) {
                        ((BookReview) it3.next()).setType(2);
                    }
                    BookCommentsAdapter bookCommentsAdapter3 = BookReviewActivity.this.mCommentsAdapter;
                    if (bookCommentsAdapter3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                        bookCommentsAdapter3 = null;
                    }
                    bookCommentsAdapter3.addAll(list3);
                    if (time_line.size() < BookReviewActivity.this.pageSize) {
                        BookReviewActivity.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        BookReviewActivity.this.getBinding().refreshLayout.finishLoadMore();
                    }
                } else {
                    BookReviewActivity.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }
            BookCommentsAdapter bookCommentsAdapter4 = BookReviewActivity.this.mCommentsAdapter;
            if (bookCommentsAdapter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            } else {
                bookCommentsAdapter = bookCommentsAdapter4;
            }
            if (bookCommentsAdapter.getItems().isEmpty()) {
                LinearLayout llFloat2 = BookReviewActivity.this.getLlFloat();
                if (llFloat2 != null) {
                    ViewExtensionsKt.gone(llFloat2);
                    return;
                }
                return;
            }
            LinearLayout llFloat3 = BookReviewActivity.this.getLlFloat();
            if (llFloat3 != null) {
                ViewExtensionsKt.visible(llFloat3);
            }
        }
    }

    public BookReviewActivity() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<ActivityBookReviewBinding>() { // from class: com.ykb.ebook.activity.BookReviewActivity$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final ActivityBookReviewBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                ActivityBookReviewBinding activityBookReviewBindingInflate = ActivityBookReviewBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(activityBookReviewBindingInflate.getRoot());
                }
                return activityBookReviewBindingInflate;
            }
        });
        ActivityResultLauncher<String[]> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.ykb.ebook.activity.v
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BookReviewActivity.permissionsLauncher$lambda$1(this.f26206a, (Map) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…       }.show()\n        }");
        this.permissionsLauncher = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LinearLayout getLlFloat() {
        return (LinearLayout) this.llFloat.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TextView getTvFloatTitle() {
        return (TextView) this.tvFloatTitle.getValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0343  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0354  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0379  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x03ac  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x03ba  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x03c5  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x03df  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0437  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0461  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0489  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x04cb  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0508  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0529  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x052c  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0583  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0596  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x02da  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02ee  */
    @android.annotation.SuppressLint({"MissingInflatedId"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private final void initHeaderView() throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 1463
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.BookReviewActivity.initHeaderView():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initHeaderView$lambda$26(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.headerHeight = view.getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$27(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$28(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$29(BookReviewActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ToastUtilsKt.toastOnUi$default(this$0, "发布成功", 0, 2, (Object) null);
        this$0.publishComment2LatestPosition = true;
        this$0.page = 1;
        this$0.showLoading();
        this$0.getViewModel().getBookReview(this$0.page, this$0.pageSize, this$0.mBookId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$30(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$31(BookReviewActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ToastUtilsKt.toastOnUi$default(this$0, "举报成功", 0, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$32(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$33(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$34(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$35(BookReviewActivity this$0, Object obj) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ToastUtilsKt.toastOnUi$default(this$0, "封禁成功", 0, 2, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$10(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$11(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$12(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$13(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.permissionsLauncher.launch(Build.VERSION.SDK_INT >= 33 ? new String[]{Permission.READ_MEDIA_IMAGES, Permission.CAMERA} : new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$14(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.readSecondFilter()) {
            this$0.mRate = "1";
            this$0.showBookCommentDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$15(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.readSecondFilter()) {
            this$0.mRate = "2";
            this$0.showBookCommentDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$16(BookReviewActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.readSecondFilter()) {
            this$0.mRate = "3";
            this$0.showBookCommentDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$17(BookReviewActivity this$0, AppBarLayout appBarLayout, int i2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.INSTANCE.logD("onOffsetChanged", "verticalOffset = " + i2);
        float fAbs = ((float) Math.abs(i2)) / ((float) appBarLayout.getTotalScrollRange());
        if (Math.abs(i2) == 0) {
            this$0.getBinding().toolbarTitle.setText("");
            RTextView rTextView = this$0.getBinding().toolbarContinue;
            Intrinsics.checkNotNullExpressionValue(rTextView, "binding.toolbarContinue");
            ViewExtensionsKt.invisible(rTextView);
            return;
        }
        if (fAbs >= 0.95f) {
            TextView textView = this$0.getBinding().toolbarTitle;
            Intrinsics.checkNotNullExpressionValue(textView, "binding.toolbarTitle");
            ViewExtensionsKt.visible(textView);
            RTextView rTextView2 = this$0.getBinding().toolbarContinue;
            Intrinsics.checkNotNullExpressionValue(rTextView2, "binding.toolbarContinue");
            ViewExtensionsKt.visible(rTextView2);
            this$0.getBinding().toolbarTitle.setText(this$0.bookTitle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$20(BookReviewActivity this$0, final RTextView rbHot, final RTextView rbNew, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(rbHot, "$rbHot");
        Intrinsics.checkNotNullParameter(rbNew, "$rbNew");
        RecyclerView.LayoutManager layoutManager = this$0.getBinding().rvComments.getLayoutManager();
        if (layoutManager != null && (layoutManager instanceof LinearLayoutManager)) {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(0, 0);
        }
        view.postDelayed(new Runnable() { // from class: com.ykb.ebook.activity.c0
            @Override // java.lang.Runnable
            public final void run() {
                BookReviewActivity.onActivityCreated$lambda$20$lambda$19(rbHot, rbNew);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$20$lambda$19(RTextView rbHot, RTextView rbNew) {
        Intrinsics.checkNotNullParameter(rbHot, "$rbHot");
        Intrinsics.checkNotNullParameter(rbNew, "$rbNew");
        rbHot.setSelected(true);
        rbNew.setSelected(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$25(BookReviewActivity this$0, final RTextView rbHot, final RTextView rbNew, View view) {
        Object next;
        RecyclerView.LayoutManager layoutManager;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(rbHot, "$rbHot");
        Intrinsics.checkNotNullParameter(rbNew, "$rbNew");
        BookCommentsAdapter bookCommentsAdapter = this$0.mCommentsAdapter;
        BookCommentsAdapter bookCommentsAdapter2 = null;
        if (bookCommentsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            bookCommentsAdapter = null;
        }
        Iterator<T> it = bookCommentsAdapter.getItems().iterator();
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
            BookCommentsAdapter bookCommentsAdapter3 = this$0.mCommentsAdapter;
            if (bookCommentsAdapter3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            } else {
                bookCommentsAdapter2 = bookCommentsAdapter3;
            }
            Iterator<BookReview> it2 = bookCommentsAdapter2.getItems().iterator();
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
            if (i2 != -1 && (layoutManager = this$0.getBinding().rvComments.getLayoutManager()) != null && (layoutManager instanceof LinearLayoutManager)) {
                ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(i2, 1);
            }
            view.postDelayed(new Runnable() { // from class: com.ykb.ebook.activity.n
                @Override // java.lang.Runnable
                public final void run() {
                    BookReviewActivity.onActivityCreated$lambda$25$lambda$24(rbHot, rbNew);
                }
            }, 200L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$25$lambda$24(RTextView rbHot, RTextView rbNew) {
        Intrinsics.checkNotNullParameter(rbHot, "$rbHot");
        Intrinsics.checkNotNullParameter(rbNew, "$rbNew");
        rbHot.setSelected(false);
        rbNew.setSelected(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$4(BookReviewActivity this$0, ActivityResult activityResult) {
        Uri data;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent data2 = activityResult.getData();
        if (data2 == null || (data = data2.getData()) == null) {
            return;
        }
        FilePathUtilKt.getPathFromUri(this$0, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$8(BookReviewActivity this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        this$0.getViewModel().bookInfo(this$0.mBookId);
        this$0.getViewModel().getBookReview(this$0.page, this$0.pageSize, this$0.mBookId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$9(BookReviewActivity this$0, RefreshLayout it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        this$0.getViewModel().getBookReview(this$0.page, this$0.pageSize, this$0.mBookId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsLauncher$lambda$1(final BookReviewActivity this$0, Map result) {
        ActivityResultLauncher<Intent> activityResultLauncher;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(result, "result");
        Iterator it = result.entrySet().iterator();
        while (it.hasNext()) {
            if (!((Boolean) ((Map.Entry) it.next()).getValue()).booleanValue()) {
                ToastUtilsKt.toastOnUi$default(this$0, "权限被拒绝，请到设置界面手动开启", 0, 2, (Object) null);
                return;
            }
        }
        String str = "";
        String str2 = "评价本书";
        String str3 = "";
        String str4 = "";
        ActivityResultLauncher<Intent> activityResultLauncher2 = this$0.albumLauncher;
        if (activityResultLauncher2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
            activityResultLauncher = null;
        } else {
            activityResultLauncher = activityResultLauncher2;
        }
        new WriteSpDialog.Builder(this$0, this$0, str, str2, str3, str4, activityResultLauncher, false, false, false, R2.attr.arrow_right, null).setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$permissionsLauncher$1$2
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str5, String str6, String str7) {
                invoke2(str5, str6, str7);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String parentId) {
                Intrinsics.checkNotNullParameter(path, "path");
                Intrinsics.checkNotNullParameter(content, "content");
                Intrinsics.checkNotNullParameter(parentId, "parentId");
                this.this$0.getViewModel().publishBookReview(false, content, this.this$0.mBookId, path, 0, null);
            }
        }).show();
    }

    private final boolean readSecondFilter() {
        if (this.readSecond >= 300) {
            return true;
        }
        ToastUtilsKt.toastOnUi$default(this, "阅读本书5分钟后，方可点评", 0, 2, (Object) null);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setCommentHint(BookInfo bookInfo) {
        View viewFindViewById;
        TextView textView = this.tvReadCommentHint;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvReadCommentHint");
            textView = null;
        }
        ReadConfig readConfig = ReadConfig.INSTANCE;
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_909090 : R.color.color_575F79));
        ImageView imageView = this.imgLock;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgLock");
            imageView = null;
        }
        imageView.setImageTintList(ColorStateList.valueOf(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_909090 : R.color.color_7380a9)));
        if (bookInfo.getRate() != 0) {
            View view = this.headView;
            if (view != null && (viewFindViewById = view.findViewById(R.id.ll_comment_hint)) != null) {
                ViewExtensionsKt.gone(viewFindViewById);
            }
            ImageView imageView2 = this.imgLock;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgLock");
                imageView2 = null;
            }
            imageView2.setVisibility(8);
            TextView textView3 = this.tvReadCommentHint;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvReadCommentHint");
            } else {
                textView2 = textView3;
            }
            textView2.setText("已点评");
            return;
        }
        if (Long.parseLong(bookInfo.getPerusalDuration().getDuration()) >= 300) {
            ImageView imageView3 = this.imgLock;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgLock");
                imageView3 = null;
            }
            imageView3.setVisibility(0);
            ImageView imageView4 = this.imgLock;
            if (imageView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("imgLock");
                imageView4 = null;
            }
            imageView4.setImageResource(R.drawable.icon_write_comment_theme_day);
            TextView textView4 = this.tvReadCommentHint;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvReadCommentHint");
            } else {
                textView2 = textView4;
            }
            textView2.setText("我要点评本书");
            return;
        }
        ImageView imageView5 = this.imgLock;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgLock");
            imageView5 = null;
        }
        imageView5.setVisibility(0);
        ImageView imageView6 = this.imgLock;
        if (imageView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("imgLock");
            imageView6 = null;
        }
        imageView6.setImageResource(R.drawable.icon_review_lock);
        TextView textView5 = this.tvReadCommentHint;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvReadCommentHint");
        } else {
            textView2 = textView5;
        }
        textView2.setText("阅读本书5分钟后，即可点评");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showBookCommentDialog() {
        BookCommentDialog.Builder builder = new BookCommentDialog.Builder(this, this.mRate, this.mRateContent, this.mRatePic, this.mCommentDialogTitle);
        builder.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity.showBookCommentDialog.1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(String str, String str2, String str3) {
                invoke2(str, str2, str3);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@NotNull String imgPath, @NotNull String content, @NotNull String rate) {
                Intrinsics.checkNotNullParameter(imgPath, "imgPath");
                Intrinsics.checkNotNullParameter(content, "content");
                Intrinsics.checkNotNullParameter(rate, "rate");
                BookReviewActivity.this.getViewModel().publishReview(rate, content, imgPath, BookReviewActivity.this.mBookId);
            }
        });
        builder.show();
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void doBusiness() {
        getViewModel().bookInfo(this.mBookId);
        getViewModel().getBookReview(this.page, this.pageSize, this.mBookId);
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    public boolean isShowLoading() {
        return false;
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    @SuppressLint({"SetTextI18n"})
    public void observeViewModel() {
        super.observeViewModel();
        MutableLiveData<CommentData> commentBookReview = getViewModel().getCommentBookReview();
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        commentBookReview.observe(this, new Observer() { // from class: com.ykb.ebook.activity.d0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$27(anonymousClass1, obj);
            }
        });
        MutableLiveData<BookInfo> bookInfo = getViewModel().getBookInfo();
        final Function1<BookInfo, Unit> function1 = new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookInfo bookInfo2) throws NumberFormatException {
                invoke2(bookInfo2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookInfo it) throws NumberFormatException {
                BookReviewActivity.this.getBinding().toolbarTitle.setText(it.getTitle());
                TextView textView = BookReviewActivity.this.tvTitle;
                RTextView rTextView = null;
                if (textView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvTitle");
                    textView = null;
                }
                textView.setText(it.getTitle());
                RequestBuilder<Drawable> requestBuilderLoad = ImageLoader.INSTANCE.load(BookReviewActivity.this, it.getThumbnail());
                RImageView rImageView = BookReviewActivity.this.imgCover;
                if (rImageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("imgCover");
                    rImageView = null;
                }
                requestBuilderLoad.into(rImageView);
                TextView textView2 = BookReviewActivity.this.tvAuthor;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvAuthor");
                    textView2 = null;
                }
                textView2.setText(it.getAuthor());
                if (it.getCommentCount() == 0) {
                    TextView textView3 = BookReviewActivity.this.tvCommentNum;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvCommentNum");
                        textView3 = null;
                    }
                    textView3.setText("快去评价吧！");
                    TextView textView4 = BookReviewActivity.this.tvScore;
                    if (textView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvScore");
                        textView4 = null;
                    }
                    textView4.setText("暂无");
                    TextView textView5 = BookReviewActivity.this.tvScoreHint;
                    if (textView5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvScoreHint");
                        textView5 = null;
                    }
                    textView5.setVisibility(8);
                } else {
                    TextView textView6 = BookReviewActivity.this.tvCommentNum;
                    if (textView6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvCommentNum");
                        textView6 = null;
                    }
                    textView6.setText(it.getCommentCount() + "人评分");
                    TextView textView7 = BookReviewActivity.this.tvScore;
                    if (textView7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvScore");
                        textView7 = null;
                    }
                    textView7.setText(it.getGrade());
                    TextView textView8 = BookReviewActivity.this.tvScoreHint;
                    if (textView8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvScoreHint");
                        textView8 = null;
                    }
                    textView8.setVisibility(0);
                }
                BookReviewActivity.this.bookTitle = it.getTitle();
                RoundCornerProgressBar roundCornerProgressBar = BookReviewActivity.this.highCommentProgress;
                if (roundCornerProgressBar == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("highCommentProgress");
                    roundCornerProgressBar = null;
                }
                roundCornerProgressBar.setProgressMax(it.getCommentCount());
                RoundCornerProgressBar roundCornerProgressBar2 = BookReviewActivity.this.middleCommentProgress;
                if (roundCornerProgressBar2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("middleCommentProgress");
                    roundCornerProgressBar2 = null;
                }
                roundCornerProgressBar2.setProgressMax(it.getCommentCount());
                RoundCornerProgressBar roundCornerProgressBar3 = BookReviewActivity.this.lowCommentProgress;
                if (roundCornerProgressBar3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lowCommentProgress");
                    roundCornerProgressBar3 = null;
                }
                roundCornerProgressBar3.setProgressMax(it.getCommentCount());
                String highCommentCount = it.getHighCommentCount();
                if (highCommentCount.length() == 0) {
                    highCommentCount = "0";
                }
                int i2 = Integer.parseInt(highCommentCount);
                RoundCornerProgressBar roundCornerProgressBar4 = BookReviewActivity.this.highCommentProgress;
                if (roundCornerProgressBar4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("highCommentProgress");
                    roundCornerProgressBar4 = null;
                }
                roundCornerProgressBar4.setProgress(i2);
                String middleCommentCount = it.getMiddleCommentCount();
                if (middleCommentCount.length() == 0) {
                    middleCommentCount = "0";
                }
                int i3 = Integer.parseInt(middleCommentCount);
                RoundCornerProgressBar roundCornerProgressBar5 = BookReviewActivity.this.middleCommentProgress;
                if (roundCornerProgressBar5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("middleCommentProgress");
                    roundCornerProgressBar5 = null;
                }
                roundCornerProgressBar5.setProgress(i3);
                String lowCommentCount = it.getLowCommentCount();
                int i4 = Integer.parseInt(lowCommentCount.length() == 0 ? "0" : lowCommentCount);
                RoundCornerProgressBar roundCornerProgressBar6 = BookReviewActivity.this.lowCommentProgress;
                if (roundCornerProgressBar6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("lowCommentProgress");
                    roundCornerProgressBar6 = null;
                }
                roundCornerProgressBar6.setProgress(i4);
                RTextView rTextView2 = BookReviewActivity.this.tvHigh;
                if (rTextView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvHigh");
                    rTextView2 = null;
                }
                rTextView2.setSelected(it.getRate() == 1);
                RTextView rTextView3 = BookReviewActivity.this.tvMiddle;
                if (rTextView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvMiddle");
                    rTextView3 = null;
                }
                rTextView3.setSelected(it.getRate() == 2);
                RTextView rTextView4 = BookReviewActivity.this.tvLow;
                if (rTextView4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("tvLow");
                } else {
                    rTextView = rTextView4;
                }
                rTextView.setSelected(it.getRate() == 3);
                BookReviewActivity.this.mRate = String.valueOf(it.getRate());
                BookReviewActivity.this.mRateContent = it.getRateComment();
                BookReviewActivity.this.mRatePic = it.getRatePicture();
                BookReviewActivity.this.readSecond = Long.parseLong(it.getPerusalDuration().getDuration());
                BookReviewActivity bookReviewActivity = BookReviewActivity.this;
                Intrinsics.checkNotNullExpressionValue(it, "it");
                bookReviewActivity.setCommentHint(it);
                View view = BookReviewActivity.this.headView;
                if (view != null) {
                    ViewExtensionsKt.visible(view);
                }
            }
        };
        bookInfo.observe(this, new Observer() { // from class: com.ykb.ebook.activity.e0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$28(function1, obj);
            }
        });
        getViewModel().getPublishReview().observe(this, new Observer() { // from class: com.ykb.ebook.activity.f0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$29(this.f26125a, obj);
            }
        });
        MutableLiveData<BookReview> publishBookReview = getViewModel().getPublishBookReview();
        final Function1<BookReview, Unit> function12 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity.observeViewModel.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(@Nullable BookReview bookReview) {
                BookReviewActivity.this.isAddComment = true;
                ToastUtilsKt.toastOnUi$default(BookReviewActivity.this, "发布成功", 0, 2, (Object) null);
                BookReviewActivity.this.publishComment2LatestPosition = true;
                BookReviewActivity.this.page = 1;
                BookReviewActivity.this.showLoading();
                BookReviewActivity.this.getViewModel().getBookReview(BookReviewActivity.this.page, BookReviewActivity.this.pageSize, BookReviewActivity.this.mBookId);
            }
        };
        publishBookReview.observe(this, new Observer() { // from class: com.ykb.ebook.activity.g0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$30(function12, obj);
            }
        });
        getViewModel().getCommentReport().observe(this, new Observer() { // from class: com.ykb.ebook.activity.h0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$31(this.f26135a, obj);
            }
        });
        MutableLiveData<BookReview> supportAndOppose = getViewModel().getSupportAndOppose();
        final Function1<BookReview, Unit> function13 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity.observeViewModel.6
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookReview bookReview) {
                int i2 = 0;
                if (bookReview.isActionSupport()) {
                    bookReview.setSupport(bookReview.isSupport() == 1 ? 0 : 1);
                    if (bookReview.isSupport() == 1) {
                        bookReview.setSupportCount(String.valueOf(Integer.parseInt(bookReview.getSupportCount()) + 1));
                    } else {
                        bookReview.setSupportCount(String.valueOf(Integer.parseInt(bookReview.getSupportCount()) - 1));
                    }
                } else {
                    bookReview.setOpposition(bookReview.isOpposition() == 1 ? 0 : 1);
                    if (bookReview.isOpposition() == 1) {
                        bookReview.setOppositionCount(String.valueOf(Integer.parseInt(bookReview.getOppositionCount()) + 1));
                    } else {
                        bookReview.setOppositionCount(String.valueOf(Integer.parseInt(bookReview.getOppositionCount()) - 1));
                    }
                }
                BookCommentsAdapter bookCommentsAdapter = BookReviewActivity.this.mCommentsAdapter;
                if (bookCommentsAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                    bookCommentsAdapter = null;
                }
                List<BookReview> items = bookCommentsAdapter.getItems();
                BookReviewActivity bookReviewActivity = BookReviewActivity.this;
                for (Object obj : items) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    if (TextUtils.equals(((BookReview) obj).getId(), bookReview.getId())) {
                        BookCommentsAdapter bookCommentsAdapter2 = bookReviewActivity.mCommentsAdapter;
                        if (bookCommentsAdapter2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                            bookCommentsAdapter2 = null;
                        }
                        bookCommentsAdapter2.notifyItemChanged(i2);
                    }
                    i2 = i3;
                }
            }
        };
        supportAndOppose.observe(this, new Observer() { // from class: com.ykb.ebook.activity.i0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$32(function13, obj);
            }
        });
        MutableLiveData<BookReview> delReviewComment = getViewModel().getDelReviewComment();
        final Function1<BookReview, Unit> function14 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity.observeViewModel.7
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:32:0x00a3  */
            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void invoke2(com.ykb.ebook.model.BookReview r6) {
                /*
                    r5 = this;
                    com.ykb.ebook.activity.BookReviewActivity r0 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r0 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r0)
                    r1 = 0
                    java.lang.String r2 = "mCommentsAdapter"
                    if (r0 != 0) goto Lf
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    r0 = r1
                Lf:
                    r0.remove(r6)
                    com.ykb.ebook.activity.BookReviewActivity r6 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r6 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r6)
                    if (r6 != 0) goto L1e
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    r6 = r1
                L1e:
                    java.util.List r6 = r6.getItems()
                    int r6 = r6.size()
                    r0 = 1
                    r3 = 0
                    if (r6 != r0) goto L4b
                    com.ykb.ebook.activity.BookReviewActivity r6 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r6 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r6)
                    if (r6 != 0) goto L36
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    r6 = r1
                L36:
                    java.util.List r6 = r6.getItems()
                    java.lang.Object r6 = r6.get(r3)
                    com.ykb.ebook.model.BookReview r6 = (com.ykb.ebook.model.BookReview) r6
                    java.lang.String r6 = r6.getShowName()
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto La3
                    goto La4
                L4b:
                    com.ykb.ebook.activity.BookReviewActivity r6 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r6 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r6)
                    if (r6 != 0) goto L57
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    r6 = r1
                L57:
                    java.util.List r6 = r6.getItems()
                    int r6 = r6.size()
                    r4 = 2
                    if (r6 != r4) goto La3
                    com.ykb.ebook.activity.BookReviewActivity r6 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r6 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r6)
                    if (r6 != 0) goto L6e
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    r6 = r1
                L6e:
                    java.util.List r6 = r6.getItems()
                    java.lang.Object r6 = r6.get(r3)
                    com.ykb.ebook.model.BookReview r6 = (com.ykb.ebook.model.BookReview) r6
                    com.ykb.ebook.activity.BookReviewActivity r4 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r4 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r4)
                    if (r4 != 0) goto L84
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    r4 = r1
                L84:
                    java.util.List r4 = r4.getItems()
                    java.lang.Object r4 = r4.get(r0)
                    com.ykb.ebook.model.BookReview r4 = (com.ykb.ebook.model.BookReview) r4
                    java.lang.String r6 = r6.getShowName()
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto La3
                    java.lang.String r6 = r4.getShowName()
                    boolean r6 = android.text.TextUtils.isEmpty(r6)
                    if (r6 != 0) goto La3
                    goto La4
                La3:
                    r0 = r3
                La4:
                    if (r0 == 0) goto Lba
                    com.ykb.ebook.activity.BookReviewActivity r6 = com.ykb.ebook.activity.BookReviewActivity.this
                    com.ykb.ebook.adapter.BookCommentsAdapter r6 = com.ykb.ebook.activity.BookReviewActivity.access$getMCommentsAdapter$p(r6)
                    if (r6 != 0) goto Lb2
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
                    goto Lb3
                Lb2:
                    r1 = r6
                Lb3:
                    java.util.List r6 = kotlin.collections.CollectionsKt.emptyList()
                    r1.submitList(r6)
                Lba:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.BookReviewActivity.AnonymousClass7.invoke2(com.ykb.ebook.model.BookReview):void");
            }
        };
        delReviewComment.observe(this, new Observer() { // from class: com.ykb.ebook.activity.j0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$33(function14, obj);
            }
        });
        MutableLiveData<BookReview> editComment = getViewModel().getEditComment();
        final Function1<BookReview, Unit> function15 = new Function1<BookReview, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity.observeViewModel.8
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookReview bookReview) {
                invoke2(bookReview);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookReview bookReview) {
                BookCommentsAdapter bookCommentsAdapter = BookReviewActivity.this.mCommentsAdapter;
                if (bookCommentsAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                    bookCommentsAdapter = null;
                }
                List<BookReview> items = bookCommentsAdapter.getItems();
                BookReviewActivity bookReviewActivity = BookReviewActivity.this;
                int i2 = 0;
                for (Object obj : items) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    if (TextUtils.equals(((BookReview) obj).getId(), bookReview.getId())) {
                        bookReviewActivity.page = 1;
                        bookReviewActivity.getViewModel().getBookReview(bookReviewActivity.page, bookReviewActivity.pageSize, bookReviewActivity.mBookId);
                    }
                    i2 = i3;
                }
            }
        };
        editComment.observe(this, new Observer() { // from class: com.ykb.ebook.activity.l
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$34(function15, obj);
            }
        });
        getViewModel().getAccountBanned().observe(this, new Observer() { // from class: com.ykb.ebook.activity.m
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookReviewActivity.observeViewModel$lambda$35(this.f26168a, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws SecurityException {
        int iColor;
        int iColor2;
        int iColor3;
        int iColor4;
        this.commentType = getIntent().getIntExtra("type", 1);
        FrameLayout root = getBinding().getRoot();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int iColor5 = -1;
        root.setBackground(new ColorDrawable(readConfig.getColorMode() == 2 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622) : readConfig.getColorMode() == 1 ? AppCtxKt.getAppCtx().getColor(R.color.color_FEEEC6) : -1));
        getBinding().llAdd.setBackground(new ColorDrawable(readConfig.getColorMode() == 2 ? ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622) : readConfig.getColorMode() == 1 ? AppCtxKt.getAppCtx().getColor(R.color.color_FEEEC6) : -1));
        View viewFindViewById = findViewById(R.id.type_line);
        if (readConfig.getColorMode() == 1) {
            iColor = AppCtxKt.getAppCtx().getColor(R.color.color_D6C9A9);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 0 ? R.color.color_eeeeee : R.color.color_1C2134);
        }
        viewFindViewById.setBackground(new ColorDrawable(iColor));
        View viewFindViewById2 = findViewById(R.id.v_bottom);
        if (readConfig.getColorMode() == 1) {
            iColor2 = AppCtxKt.getAppCtx().getColor(R.color.color_D6C9A9);
        } else {
            iColor2 = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 0 ? R.color.color_eeeeee : R.color.color_1C2134);
        }
        viewFindViewById2.setBackground(new ColorDrawable(iColor2));
        RTextViewHelper helper = getBinding().tvAddSp.getHelper();
        if (readConfig.getColorMode() == 1) {
            iColor3 = AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3);
        } else {
            iColor3 = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_f9fafb : R.color.color_171C2D);
        }
        helper.setBackgroundColorNormal(iColor3);
        if (readConfig.getColorMode() == 2) {
            getBinding().tvAddSp.getHelper().setIconNormal(getDrawable(R.mipmap.ic_add_comment_night));
            getBinding().tvAddSp.getHelper().setTextColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79));
        }
        Window window = getWindow();
        if (readConfig.getColorMode() == 1) {
            iColor4 = AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3);
        } else {
            iColor4 = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_f9fafb : R.color.color_171C2D);
        }
        window.setNavigationBarColor(iColor4);
        getBinding().tvAddSp.setHintTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_C2C6CB : R.color.color_575F79));
        TextView tvFloatTitle = getTvFloatTitle();
        if (tvFloatTitle != null) {
            tvFloatTitle.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        }
        getBinding().toolbarTitle.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030));
        if (readConfig.getColorMode() != 2) {
            getWindow().clearFlags(67108864);
            getWindow().getDecorView().setSystemUiVisibility(8192);
        }
        getWindow().setStatusBarColor(readConfig.getColorMode() == 1 ? AppCtxKt.getAppCtx().getColor(R.color.color_FEEEC6) : readConfig.getColorMode() != 2 ? -1 : ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622));
        LinearLayout llFloat = getLlFloat();
        if (llFloat != null) {
            if (readConfig.getColorMode() == 1) {
                iColor5 = AppCtxKt.getAppCtx().getColor(R.color.color_FEEEC6);
            } else if (readConfig.getColorMode() == 2) {
                iColor5 = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_121622);
            }
            llFloat.setBackground(new ColorDrawable(iColor5));
        }
        if (readConfig.getColorMode() == 2) {
            getBinding().toolbarContinue.getHelper().setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_B2575C));
        } else {
            getBinding().toolbarContinue.getHelper().setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_dd594a));
        }
        String stringExtra = getIntent().getStringExtra("bookId");
        if (stringExtra != null) {
            this.mBookId = stringExtra;
        }
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.ykb.ebook.activity.o
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                BookReviewActivity.onActivityCreated$lambda$4(this.f26176a, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…          }\n            }");
        this.albumLauncher = activityResultLauncherRegisterForActivityResult;
        RTextView rTextView = null;
        ImageLoader.INSTANCE.load(this, ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "avatar", null, 2, null)).placeholder(R.drawable.personal_headimg_icon).into(getBinding().userAvatar);
        this.mCommentsAdapter = new BookCommentsAdapter();
        initHeaderView();
        BookCommentsAdapter bookCommentsAdapter = this.mCommentsAdapter;
        if (bookCommentsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            bookCommentsAdapter = null;
        }
        bookCommentsAdapter.setOnItemActionLisenter(new BookCommentsAdapter.OnItemActionLisenter() { // from class: com.ykb.ebook.activity.BookReviewActivity.onActivityCreated.3
            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemClickAction(final int position, @NotNull final BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                boolean zAreEqual = Intrinsics.areEqual(item.getUserId(), ContextExtensionsKt.getPrefString$default(AppCtxKt.getAppCtx(), "user_id", null, 2, null));
                CommentItemChooseDialog.Builder builder = new CommentItemChooseDialog.Builder(BookReviewActivity.this, zAreEqual, item.getNickname() + (char) 65306 + item.getComment());
                final BookReviewActivity bookReviewActivity = BookReviewActivity.this;
                builder.setOnItemClick(new Function1<String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$onActivityCreated$3$onItemClickAction$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(String str) {
                        invoke2(str);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull String it) {
                        ActivityResultLauncher activityResultLauncher;
                        ActivityResultLauncher activityResultLauncher2;
                        Intrinsics.checkNotNullParameter(it, "it");
                        switch (it.hashCode()) {
                            case 646183:
                                if (it.equals("举报")) {
                                    ReportReasonChooseDialog.Builder builder2 = new ReportReasonChooseDialog.Builder(bookReviewActivity, item.getComment(), item.getId(), true);
                                    final BookReviewActivity bookReviewActivity2 = bookReviewActivity;
                                    final BookReview bookReview = item;
                                    builder2.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$onActivityCreated$3$onItemClickAction$1.4
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                                            invoke(num.intValue(), str);
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(int i2, @NotNull String str) {
                                            Intrinsics.checkNotNullParameter(str, "str");
                                            bookReviewActivity2.getViewModel().commentReport(bookReview.getId(), String.valueOf(i2 + 1), str);
                                        }
                                    }).show();
                                    break;
                                }
                                break;
                            case 690244:
                                if (it.equals("删除")) {
                                    bookReviewActivity.getViewModel().delReviewComment(position, item);
                                    break;
                                }
                                break;
                            case 712175:
                                if (it.equals("回复")) {
                                    BookReviewActivity bookReviewActivity3 = bookReviewActivity;
                                    String id = item.getId();
                                    String str = "回复：" + item.getNickname();
                                    ActivityResultLauncher activityResultLauncher3 = bookReviewActivity.albumLauncher;
                                    if (activityResultLauncher3 == null) {
                                        Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
                                        activityResultLauncher = null;
                                    } else {
                                        activityResultLauncher = activityResultLauncher3;
                                    }
                                    WriteSpDialog.Builder builder3 = new WriteSpDialog.Builder(bookReviewActivity3, bookReviewActivity3, id, str, "", "", activityResultLauncher, false, false, false, R2.attr.carousel_previousState, null);
                                    final BookReviewActivity bookReviewActivity4 = bookReviewActivity;
                                    final int i2 = position;
                                    final BookReview bookReview2 = item;
                                    builder3.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$onActivityCreated$3$onItemClickAction$1.1
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(3);
                                        }

                                        @Override // kotlin.jvm.functions.Function3
                                        public /* bridge */ /* synthetic */ Unit invoke(String str2, String str3, String str4) {
                                            invoke2(str2, str3, str4);
                                            return Unit.INSTANCE;
                                        }

                                        /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                        public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String parentId) {
                                            Intrinsics.checkNotNullParameter(path, "path");
                                            Intrinsics.checkNotNullParameter(content, "content");
                                            Intrinsics.checkNotNullParameter(parentId, "parentId");
                                            bookReviewActivity4.getViewModel().publishBookReview(true, content, bookReviewActivity4.mBookId, path, i2, bookReview2);
                                        }
                                    }).show();
                                    break;
                                }
                                break;
                            case 727753:
                                if (it.equals("复制")) {
                                    FileUtils.INSTANCE.copyContent(bookReviewActivity, item.getComment());
                                    break;
                                }
                                break;
                            case 761248:
                                if (it.equals("封禁")) {
                                    ReportReasonChooseDialog.Builder builder4 = new ReportReasonChooseDialog.Builder(bookReviewActivity, item.getComment(), item.getId(), false);
                                    final BookReviewActivity bookReviewActivity5 = bookReviewActivity;
                                    final BookReview bookReview3 = item;
                                    builder4.setOnItemClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$onActivityCreated$3$onItemClickAction$1.3
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(2);
                                        }

                                        @Override // kotlin.jvm.functions.Function2
                                        public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str2) {
                                            invoke(num.intValue(), str2);
                                            return Unit.INSTANCE;
                                        }

                                        public final void invoke(int i3, @NotNull String str2) {
                                            Intrinsics.checkNotNullParameter(str2, "str");
                                            bookReviewActivity5.getViewModel().accountBanned(str2, String.valueOf(i3 + 1), bookReview3.getUserId());
                                        }
                                    }).show();
                                    break;
                                }
                                break;
                            case 1045307:
                                if (it.equals("编辑")) {
                                    if (!Intrinsics.areEqual(item.getReviewType(), "2")) {
                                        BookReviewActivity bookReviewActivity6 = bookReviewActivity;
                                        String id2 = item.getId();
                                        String comment = item.getComment();
                                        String picture = item.getPicture();
                                        ActivityResultLauncher activityResultLauncher4 = bookReviewActivity.albumLauncher;
                                        if (activityResultLauncher4 == null) {
                                            Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
                                            activityResultLauncher2 = null;
                                        } else {
                                            activityResultLauncher2 = activityResultLauncher4;
                                        }
                                        WriteSpDialog.Builder builder5 = new WriteSpDialog.Builder(bookReviewActivity6, bookReviewActivity6, id2, "编辑书评", comment, picture, activityResultLauncher2, false, false, false, R2.attr.carousel_previousState, null);
                                        final BookReviewActivity bookReviewActivity7 = bookReviewActivity;
                                        final BookReview bookReview4 = item;
                                        final int i3 = position;
                                        builder5.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$onActivityCreated$3$onItemClickAction$1.2
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(3);
                                            }

                                            @Override // kotlin.jvm.functions.Function3
                                            public /* bridge */ /* synthetic */ Unit invoke(String str2, String str3, String str4) {
                                                invoke2(str2, str3, str4);
                                                return Unit.INSTANCE;
                                            }

                                            /* renamed from: invoke, reason: avoid collision after fix types in other method */
                                            public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String str2) {
                                                Intrinsics.checkNotNullParameter(path, "path");
                                                Intrinsics.checkNotNullParameter(content, "content");
                                                Intrinsics.checkNotNullParameter(str2, "<anonymous parameter 2>");
                                                bookReviewActivity7.getViewModel().editComment(content, bookReview4.getId(), path, i3, bookReview4);
                                            }
                                        }).show();
                                        break;
                                    } else {
                                        bookReviewActivity.mRate = item.getRate();
                                        bookReviewActivity.mRateContent = item.getComment();
                                        bookReviewActivity.mCommentDialogTitle = "编辑点评";
                                        bookReviewActivity.showBookCommentDialog();
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                }).show();
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemFloorOpposeAction(int pos, @NotNull BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                BookReviewActivity.this.getViewModel().addSupportOrOppse(item, false, -1);
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemFloorSupportAction(int pos, @NotNull BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                BookReviewActivity.this.getViewModel().addSupportOrOppse(item, true, -1);
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemHotAndNewsAction(boolean isHot) {
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemOpposeAction(int pos, @NotNull BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                if (item.isSupport() == 1) {
                    return;
                }
                BookReviewActivity.this.getViewModel().addSupportOrOppse(item, false, pos);
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemPicAction(int pos, @NotNull BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                new XPopup.Builder(BookReviewActivity.this).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemReplyAction(final int pos, @NotNull final BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                if (item.getReplyCount() > 0) {
                    ReplyCollectionAct.Companion companion = ReplyCollectionAct.INSTANCE;
                    BookReviewActivity bookReviewActivity = BookReviewActivity.this;
                    companion.newIntent(bookReviewActivity, bookReviewActivity.mBookId, item, true);
                    return;
                }
                BookReviewActivity bookReviewActivity2 = BookReviewActivity.this;
                String id = item.getId();
                String str = "回复：" + item.getNickname();
                String str2 = "";
                String str3 = "";
                ActivityResultLauncher activityResultLauncher = BookReviewActivity.this.albumLauncher;
                if (activityResultLauncher == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("albumLauncher");
                    activityResultLauncher = null;
                }
                WriteSpDialog.Builder builder = new WriteSpDialog.Builder(bookReviewActivity2, bookReviewActivity2, id, str, str2, str3, activityResultLauncher, false, false, false, R2.attr.carousel_previousState, null);
                final BookReviewActivity bookReviewActivity3 = BookReviewActivity.this;
                builder.setOnPublishClick(new Function3<String, String, String, Unit>() { // from class: com.ykb.ebook.activity.BookReviewActivity$onActivityCreated$3$onItemReplyAction$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(3);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public /* bridge */ /* synthetic */ Unit invoke(String str4, String str5, String str6) {
                        invoke2(str4, str5, str6);
                        return Unit.INSTANCE;
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(@NotNull String path, @NotNull String content, @NotNull String parentId) {
                        Intrinsics.checkNotNullParameter(path, "path");
                        Intrinsics.checkNotNullParameter(content, "content");
                        Intrinsics.checkNotNullParameter(parentId, "parentId");
                        bookReviewActivity3.getViewModel().publishBookReview(true, content, bookReviewActivity3.mBookId, path, pos, item);
                    }
                }).show();
            }

            @Override // com.ykb.ebook.adapter.BookCommentsAdapter.OnItemActionLisenter
            public void onItemSupportAction(int pos, @NotNull BookReview item) {
                Intrinsics.checkNotNullParameter(item, "item");
                if (item.isOpposition() == 1) {
                    return;
                }
                BookReviewActivity.this.getViewModel().addSupportOrOppse(item, true, pos);
            }
        });
        final RTextView rTextView2 = getBinding().tvHot;
        Intrinsics.checkNotNullExpressionValue(rTextView2, "binding.tvHot");
        final RTextView rTextView3 = getBinding().tvNew;
        Intrinsics.checkNotNullExpressionValue(rTextView3, "binding.tvNew");
        RLinearLayout rLinearLayout = (RLinearLayout) findViewById(R.id.ll_comment_wrap);
        int colorMode = readConfig.getColorMode();
        if (colorMode == 0) {
            Intrinsics.checkNotNull(rLinearLayout);
            RBaseHelper helper2 = rLinearLayout.getHelper();
            int i2 = R.color.color_f7f8fa;
            helper2.setBackgroundColorNormal(getColor(i2));
            RTextViewHelper helper3 = rTextView2.getHelper();
            int i3 = R.color.color_303030;
            helper3.setTextColorSelected(getColor(i3));
            RTextViewHelper helper4 = rTextView2.getHelper();
            int i4 = R.color.color_bfbfbf;
            helper4.setTextColorNormal(getColor(i4));
            RTextViewHelper helper5 = rTextView2.getHelper();
            int i5 = R.color.white;
            helper5.setBackgroundColorSelected(getColor(i5));
            rTextView2.getHelper().setBackgroundColorNormal(getColor(i2));
            rTextView3.getHelper().setTextColorSelected(getColor(i3));
            rTextView3.getHelper().setTextColorNormal(getColor(i4));
            rTextView3.getHelper().setBackgroundColorSelected(getColor(i5));
            rTextView3.getHelper().setBackgroundColorNormal(getColor(i2));
        } else if (colorMode == 1) {
            Intrinsics.checkNotNull(rLinearLayout);
            rLinearLayout.getHelper().setBackgroundColorNormal(AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3));
            RTextViewHelper helper6 = rTextView2.getHelper();
            int i6 = R.color.color_303030;
            helper6.setTextColorSelected(getColor(i6));
            RTextViewHelper helper7 = rTextView2.getHelper();
            int i7 = R.color.color_bfbfbf;
            helper7.setTextColorNormal(getColor(i7));
            RTextViewHelper helper8 = rTextView2.getHelper();
            int i8 = R.color.color_FEEEC6;
            helper8.setBackgroundColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8));
            rTextView2.getHelper().setBackgroundColorNormal(0);
            rTextView3.getHelper().setTextColorSelected(getColor(i6));
            rTextView3.getHelper().setTextColorNormal(getColor(i7));
            rTextView3.getHelper().setBackgroundColorSelected(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8));
            rTextView3.getHelper().setBackgroundColorNormal(0);
        } else if (colorMode == 2) {
            Intrinsics.checkNotNull(rLinearLayout);
            rLinearLayout.getHelper().setBackgroundColorNormal(getColor(R.color.color_171C2D));
            RTextViewHelper helper9 = rTextView2.getHelper();
            int i9 = R.color.color_7380a9;
            helper9.setTextColorSelected(getColor(i9));
            RTextViewHelper helper10 = rTextView2.getHelper();
            int i10 = R.color.color_575F79;
            helper10.setTextColorNormal(getColor(i10));
            RTextViewHelper helper11 = rTextView2.getHelper();
            int i11 = R.color.color_121622;
            helper11.setBackgroundColorSelected(getColor(i11));
            rTextView2.getHelper().setBackgroundColorNormal(0);
            rTextView3.getHelper().setTextColorSelected(getColor(i9));
            rTextView3.getHelper().setTextColorNormal(getColor(i10));
            rTextView3.getHelper().setBackgroundColorSelected(getColor(i11));
            rTextView3.getHelper().setBackgroundColorNormal(0);
        }
        getBinding().refreshLayout.setEnableAutoLoadMore(true);
        SmartRefreshLayout smartRefreshLayout = getBinding().refreshLayout;
        RefreshHeader refreshHeader = smartRefreshLayout.getRefreshHeader();
        if (refreshHeader != null) {
            if (refreshHeader instanceof ClassicsHeader) {
                ((ClassicsHeader) refreshHeader).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
            }
            RefreshFooter refreshFooter = smartRefreshLayout.getRefreshFooter();
            if (refreshFooter != null && (refreshFooter instanceof ClassicsFooter)) {
                ((ClassicsFooter) refreshFooter).setAccentColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
            }
        }
        int colorMode2 = readConfig.getColorMode();
        SmartRefreshLayout smartRefreshLayout2 = getBinding().refreshLayout;
        Intrinsics.checkNotNullExpressionValue(smartRefreshLayout2, "binding.refreshLayout");
        ClassicsHeader classicsHeader = getBinding().refreshHeader;
        Intrinsics.checkNotNullExpressionValue(classicsHeader, "binding.refreshHeader");
        ViewUtilKt.setRefreshTileView(colorMode2, smartRefreshLayout2, classicsHeader, this);
        RecyclerView recyclerView = getBinding().rvComments;
        BookCommentsAdapter bookCommentsAdapter2 = this.mCommentsAdapter;
        if (bookCommentsAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            bookCommentsAdapter2 = null;
        }
        recyclerView.setAdapter(bookCommentsAdapter2);
        BookCommentsAdapter bookCommentsAdapter3 = this.mCommentsAdapter;
        if (bookCommentsAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            bookCommentsAdapter3 = null;
        }
        bookCommentsAdapter3.setEmptyViewEnable(true);
        BookCommentsAdapter bookCommentsAdapter4 = this.mCommentsAdapter;
        if (bookCommentsAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
            bookCommentsAdapter4 = null;
        }
        bookCommentsAdapter4.setEmptyViewLayout(this, readConfig.getColorMode() == 2 ? R.layout.empty_comment_layout_night : R.layout.empty_comment_layout_day);
        final float fApplyDimension = TypedValue.applyDimension(1, 56.0f, getResources().getDisplayMetrics());
        RecyclerView.LayoutManager layoutManager = getBinding().rvComments.getLayoutManager();
        Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.LinearLayoutManager");
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        getBinding().rvComments.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.ykb.ebook.activity.BookReviewActivity.onActivityCreated.5
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(@NotNull RecyclerView recyclerView2, int dx, int dy) {
                Intrinsics.checkNotNullParameter(recyclerView2, "recyclerView");
                super.onScrolled(recyclerView2, dx, dy);
                BookCommentsAdapter bookCommentsAdapter5 = BookReviewActivity.this.mCommentsAdapter;
                BookCommentsAdapter bookCommentsAdapter6 = null;
                if (bookCommentsAdapter5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                    bookCommentsAdapter5 = null;
                }
                if (bookCommentsAdapter5.getItems().size() <= 3) {
                    return;
                }
                int iFindFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                View viewFindViewByPosition = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
                Intrinsics.checkNotNull(viewFindViewByPosition);
                BookCommentsAdapter bookCommentsAdapter7 = BookReviewActivity.this.mCommentsAdapter;
                if (bookCommentsAdapter7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                    bookCommentsAdapter7 = null;
                }
                BookReview bookReview = bookCommentsAdapter7.getItems().get(iFindFirstVisibleItemPosition);
                int iFindFirstVisibleItemPosition2 = linearLayoutManager.findFirstVisibleItemPosition() + 1;
                BookCommentsAdapter bookCommentsAdapter8 = BookReviewActivity.this.mCommentsAdapter;
                if (bookCommentsAdapter8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                    bookCommentsAdapter8 = null;
                }
                if (iFindFirstVisibleItemPosition2 > bookCommentsAdapter8.getItems().size() - 1) {
                    BookCommentsAdapter bookCommentsAdapter9 = BookReviewActivity.this.mCommentsAdapter;
                    if (bookCommentsAdapter9 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                        bookCommentsAdapter9 = null;
                    }
                    iFindFirstVisibleItemPosition2 = bookCommentsAdapter9.getItems().size() - 1;
                }
                View viewFindViewByPosition2 = linearLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition2);
                BookCommentsAdapter bookCommentsAdapter10 = BookReviewActivity.this.mCommentsAdapter;
                if (bookCommentsAdapter10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("mCommentsAdapter");
                } else {
                    bookCommentsAdapter6 = bookCommentsAdapter10;
                }
                BookReview bookReview2 = bookCommentsAdapter6.getItems().get(iFindFirstVisibleItemPosition2);
                if (dy > 0) {
                    if (dy >= 200) {
                        LinearLayout llFloat2 = BookReviewActivity.this.getLlFloat();
                        Intrinsics.checkNotNull(llFloat2);
                        llFloat2.setY(0.0f);
                    } else if (bookReview2.getSuspend()) {
                        float y2 = viewFindViewByPosition2 != null ? viewFindViewByPosition2.getY() : 0.0f;
                        float f2 = fApplyDimension;
                        if (y2 < f2) {
                            float f3 = y2 - f2;
                            LinearLayout llFloat3 = BookReviewActivity.this.getLlFloat();
                            Intrinsics.checkNotNull(llFloat3);
                            llFloat3.setY(f3);
                        }
                    }
                    if (bookReview.getSuspend() && viewFindViewByPosition.getY() <= 0.0f) {
                        LinearLayout llFloat4 = BookReviewActivity.this.getLlFloat();
                        Intrinsics.checkNotNull(llFloat4);
                        llFloat4.setY(0.0f);
                    }
                } else if (bookReview2.getSuspend()) {
                    float y3 = viewFindViewByPosition2 != null ? viewFindViewByPosition2.getY() : 0.0f;
                    if (y3 < fApplyDimension) {
                        LinearLayout llFloat5 = BookReviewActivity.this.getLlFloat();
                        Intrinsics.checkNotNull(llFloat5);
                        llFloat5.setY(y3 - fApplyDimension);
                    } else {
                        LinearLayout llFloat6 = BookReviewActivity.this.getLlFloat();
                        Intrinsics.checkNotNull(llFloat6);
                        llFloat6.setY(0.0f);
                    }
                } else {
                    LinearLayout llFloat7 = BookReviewActivity.this.getLlFloat();
                    Intrinsics.checkNotNull(llFloat7);
                    llFloat7.setY(0.0f);
                }
                if (bookReview.getType() == 1) {
                    rTextView2.setSelected(true);
                    rTextView3.setSelected(false);
                    TextView tvFloatTitle2 = BookReviewActivity.this.getTvFloatTitle();
                    if (tvFloatTitle2 == null) {
                        return;
                    }
                    tvFloatTitle2.setText("最热评论（" + BookReviewActivity.this.hotCount + "条）");
                    return;
                }
                rTextView2.setSelected(false);
                rTextView3.setSelected(true);
                TextView tvFloatTitle3 = BookReviewActivity.this.getTvFloatTitle();
                if (tvFloatTitle3 == null) {
                    return;
                }
                tvFloatTitle3.setText("最新评论（" + BookReviewActivity.this.newsCount + "条）");
            }
        });
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.activity.s
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                BookReviewActivity.onActivityCreated$lambda$8(this.f26193c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.activity.t
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                BookReviewActivity.onActivityCreated$lambda$9(this.f26197c, refreshLayout);
            }
        });
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$10(this.f26202c, view);
            }
        });
        getBinding().toolbarContinue.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$11(this.f26210c, view);
            }
        });
        RTextView rTextView4 = this.tvContinue;
        if (rTextView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvContinue");
            rTextView4 = null;
        }
        rTextView4.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$12(this.f26214c, view);
            }
        });
        getBinding().tvAddSp.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$13(this.f26218c, view);
            }
        });
        RTextView rTextView5 = this.tvHigh;
        if (rTextView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvHigh");
            rTextView5 = null;
        }
        rTextView5.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$14(this.f26222c, view);
            }
        });
        RTextView rTextView6 = this.tvMiddle;
        if (rTextView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvMiddle");
            rTextView6 = null;
        }
        rTextView6.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$15(this.f26095c, view);
            }
        });
        RTextView rTextView7 = this.tvLow;
        if (rTextView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLow");
        } else {
            rTextView = rTextView7;
        }
        rTextView.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$16(this.f26100c, view);
            }
        });
        getBinding().appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() { // from class: com.ykb.ebook.activity.p
            @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
            public final void onOffsetChanged(AppBarLayout appBarLayout, int i12) {
                BookReviewActivity.onActivityCreated$lambda$17(this.f26179a, appBarLayout, i12);
            }
        });
        rTextView2.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.q
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$20(this.f26182c, rTextView2, rTextView3, view);
            }
        });
        rTextView3.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookReviewActivity.onActivityCreated$lambda$25(this.f26187c, rTextView2, rTextView3, view);
            }
        });
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String stringExtra;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 999 || resultCode != -1 || data == null || (stringExtra = data.getStringExtra("imageUrl")) == null) {
            return;
        }
        EventBus.getDefault().post(new ImageUploadSuccessEvent(stringExtra));
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public ActivityBookReviewBinding getBinding() {
        return (ActivityBookReviewBinding) this.binding.getValue();
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    @NotNull
    public BookReviewViewModel initViewModel() {
        return (BookReviewViewModel) new ViewModelProvider(this).get(BookReviewViewModel.class);
    }
}
