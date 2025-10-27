package com.ykb.ebook.activity;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.plv.socket.user.PLVSocketUserConstant;
import com.ykb.ebook.R;
import com.ykb.ebook.base.BaseVmActivity;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.databinding.ActivityBookInfoBinding;
import com.ykb.ebook.dialog.ReadGuideDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.StatusBarUtil;
import com.ykb.ebook.vm.BookInfoViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0003H\u0014J\b\u0010\u0010\u001a\u00020\u000eH\u0014J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014R\u001b\u0010\u0005\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lcom/ykb/ebook/activity/BookInfoActivity;", "Lcom/ykb/ebook/base/BaseVmActivity;", "Lcom/ykb/ebook/databinding/ActivityBookInfoBinding;", "Lcom/ykb/ebook/vm/BookInfoViewModel;", "()V", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/ActivityBookInfoBinding;", "binding$delegate", "Lkotlin/Lazy;", "bookId", "", "imgSrc", "doBusiness", "", "initViewModel", "observeViewModel", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBookInfoActivity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BookInfoActivity.kt\ncom/ykb/ebook/activity/BookInfoActivity\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ContextExtensions.kt\ncom/ykb/ebook/extensions/ContextExtensionsKt\n*L\n1#1,128:1\n13#2,10:129\n26#3,4:139\n*S KotlinDebug\n*F\n+ 1 BookInfoActivity.kt\ncom/ykb/ebook/activity/BookInfoActivity\n*L\n50#1:129,10\n68#1:139,4\n*E\n"})
/* loaded from: classes6.dex */
public final class BookInfoActivity extends BaseVmActivity<ActivityBookInfoBinding, BookInfoViewModel> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;

    @NotNull
    private String bookId = "";

    @NotNull
    private String imgSrc = "";

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b¨\u0006\r"}, d2 = {"Lcom/ykb/ebook/activity/BookInfoActivity$Companion;", "", "()V", "newIntent", "Landroid/content/Intent;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "bookId", "", "userId", "appId", PLVSocketUserConstant.ROLE_ADMIN, "avatar", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final Intent newIntent(@NotNull Context context, @NotNull String bookId, @NotNull String userId, @NotNull String appId, @NotNull String admin, @NotNull String avatar) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(bookId, "bookId");
            Intrinsics.checkNotNullParameter(userId, "userId");
            Intrinsics.checkNotNullParameter(appId, "appId");
            Intrinsics.checkNotNullParameter(admin, "admin");
            Intrinsics.checkNotNullParameter(avatar, "avatar");
            Intent intent = new Intent(context, (Class<?>) BookInfoActivity.class);
            intent.putExtra("book_id", bookId);
            intent.putExtra("user_id", userId);
            intent.putExtra("app_id", appId);
            intent.putExtra(PLVSocketUserConstant.ROLE_ADMIN, admin);
            intent.putExtra("avatar", avatar);
            return intent;
        }
    }

    public BookInfoActivity() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<ActivityBookInfoBinding>() { // from class: com.ykb.ebook.activity.BookInfoActivity$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final ActivityBookInfoBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                ActivityBookInfoBinding activityBookInfoBindingInflate = ActivityBookInfoBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(activityBookInfoBindingInflate.getRoot());
                }
                return activityBookInfoBindingInflate;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$6(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$1(BookInfoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent intent = new Intent(this$0, (Class<?>) ReadBookActivity.class);
        intent.putExtra("book_id", this$0.bookId);
        this$0.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$2(BookInfoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$3(BookInfoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookReviewActivity.INSTANCE.newIntent(this$0, this$0.bookId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$4(BookInfoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        BookReviewActivity.INSTANCE.newIntent(this$0, this$0.bookId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$5(BookInfoActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        new XPopup.Builder(this$0).asImageViewer(null, this$0.imgSrc, new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void doBusiness() {
        getViewModel().bookInfo(this.bookId);
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    public void observeViewModel() {
        super.observeViewModel();
        MutableLiveData<BookInfo> bookInfo = getViewModel().getBookInfo();
        final Function1<BookInfo, Unit> function1 = new Function1<BookInfo, Unit>() { // from class: com.ykb.ebook.activity.BookInfoActivity.observeViewModel.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BookInfo bookInfo2) {
                invoke2(bookInfo2);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BookInfo bookInfo2) {
                String str;
                String str2;
                BookInfoActivity.this.imgSrc = bookInfo2.getThumbnail();
                ImageLoader.INSTANCE.load(BookInfoActivity.this, bookInfo2.getThumbnail()).optionalCenterCrop().into(BookInfoActivity.this.getBinding().imgCover);
                BookInfoActivity.this.getBinding().tvTitle.setText(bookInfo2.getTitle());
                BookInfoActivity.this.getBinding().tvDesc.setText(bookInfo2.getAuthor());
                TextView textView = BookInfoActivity.this.getBinding().tvWordNum;
                if (Integer.parseInt(bookInfo2.getWordCount()) > 10000) {
                    str = " · " + (Integer.parseInt(bookInfo2.getWordCount()) / 10000) + "万字";
                } else {
                    str = " · " + bookInfo2.getWordCount() + (char) 23383;
                }
                textView.setText(str);
                StringBuilder sb = new StringBuilder();
                sb.append(bookInfo2.getBookReviewCount());
                sb.append((char) 26465);
                BookInfoActivity.this.getBinding().tvReviewNum.setText(sb.toString());
                if (Integer.parseInt(bookInfo2.getReadCount()) > 10000) {
                    str2 = (Integer.parseInt(bookInfo2.getReadCount()) / 10000) + "万人";
                } else {
                    str2 = bookInfo2.getReadCount() + (char) 20154;
                }
                BookInfoActivity.this.getBinding().tvReadNum.setText(str2);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(bookInfo2.getCommentCount());
                sb2.append((char) 26465);
                BookInfoActivity.this.getBinding().tvCommentNum.setText(sb2.toString());
            }
        };
        bookInfo.observe(this, new Observer() { // from class: com.ykb.ebook.activity.d
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookInfoActivity.observeViewModel$lambda$6(function1, obj);
            }
        });
        MutableLiveData<String> bookError = getViewModel().getBookError();
        final Function1<String, Unit> function12 = new Function1<String, Unit>() { // from class: com.ykb.ebook.activity.BookInfoActivity.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String str) {
                Toast.makeText(BookInfoActivity.this, str, 0).show();
            }
        };
        bookError.observe(this, new Observer() { // from class: com.ykb.ebook.activity.e
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BookInfoActivity.observeViewModel$lambda$7(function12, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getColor(R.color.color_f7f8fa));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "user_id", getIntent().getStringExtra("user_id"));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "app_id", getIntent().getStringExtra("app_id"));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PLVSocketUserConstant.ROLE_ADMIN, getIntent().getStringExtra(PLVSocketUserConstant.ROLE_ADMIN));
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), "avatar", getIntent().getStringExtra("avatar"));
        String stringExtra = getIntent().getStringExtra("book_id");
        if (stringExtra == null) {
            stringExtra = "";
        }
        this.bookId = stringExtra;
        getBinding().tvBeginRead.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookInfoActivity.onActivityCreated$lambda$1(this.f26124c, view);
            }
        });
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookInfoActivity.onActivityCreated$lambda$2(this.f26129c, view);
            }
        });
        getBinding().llComment.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.h
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookInfoActivity.onActivityCreated$lambda$3(this.f26134c, view);
            }
        });
        getBinding().llBookComment.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookInfoActivity.onActivityCreated$lambda$4(this.f26142c, view);
            }
        });
        getBinding().imgCover.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BookInfoActivity.onActivityCreated$lambda$5(this.f26149c, view);
            }
        });
        if (ContextExtensionsKt.getPrefBoolean$default(AppCtxKt.getAppCtx(), PreferKeyKt.READ_GUIDE, false, 2, null)) {
            return;
        }
        new ReadGuideDialog.Builder(this).show();
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public ActivityBookInfoBinding getBinding() {
        return (ActivityBookInfoBinding) this.binding.getValue();
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    @NotNull
    public BookInfoViewModel initViewModel() {
        return (BookInfoViewModel) new ViewModelProvider(this).get(BookInfoViewModel.class);
    }
}
