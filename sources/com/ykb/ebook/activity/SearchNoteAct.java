package com.ykb.ebook.activity;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ColorResourcesKt;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.lxj.xpopup.util.KeyboardUtils;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.helper.RTextViewHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseVmActivity;
import com.ykb.ebook.common.EventBus;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AdapterConvertListener;
import com.ykb.ebook.databinding.LayoutSearchNoteOrParagraphBinding;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.AllNotes;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.Notes;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.vm.AllTagViewModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 *2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0012\u0004\u0012\u00020\u00050\u0006:\u0001*B\u0005¢\u0006\u0002\u0010\u0007J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0005H\u0016J.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u00052\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u0015H\u0002J\b\u0010\u001e\u001a\u00020\u0003H\u0014J\b\u0010\u001f\u001a\u00020\u0015H\u0014J\u0012\u0010 \u001a\u00020\u00152\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J*\u0010#\u001a\u00020\u00152\u0010\u0010\b\u001a\f\u0012\u0004\u0012\u00020\u0005\u0012\u0002\b\u00030$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\b\u0010'\u001a\u00020\u0015H\u0014J\u0006\u0010(\u001a\u00020\u0015J\b\u0010)\u001a\u00020\u0015H\u0002R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/ykb/ebook/activity/SearchNoteAct;", "Lcom/ykb/ebook/base/BaseVmActivity;", "Lcom/ykb/ebook/databinding/LayoutSearchNoteOrParagraphBinding;", "Lcom/ykb/ebook/vm/AllTagViewModel;", "Lcom/ykb/ebook/common_interface/AdapterConvertListener;", "Lcom/ykb/ebook/model/Notes;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "()V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/LayoutSearchNoteOrParagraphBinding;", "binding$delegate", "Lkotlin/Lazy;", "keywords", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "payloads", "", "", "hideSystemUI", "initViewModel", "observeViewModel", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onClick", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "onResume", "upSystemUiVisibility", "updateStatusBar", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSearchNoteAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SearchNoteAct.kt\ncom/ykb/ebook/activity/SearchNoteAct\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 TextView.kt\nandroidx/core/widget/TextViewKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,456:1\n13#2,10:457\n42#3:467\n42#3:468\n42#3:469\n42#3:489\n42#3:490\n42#3:499\n65#4,16:470\n93#4,3:486\n350#5,7:491\n1#6:498\n*S KotlinDebug\n*F\n+ 1 SearchNoteAct.kt\ncom/ykb/ebook/activity/SearchNoteAct\n*L\n81#1:457,10\n118#1:467\n133#1:468\n137#1:469\n351#1:489\n361#1:490\n435#1:499\n167#1:470,16\n167#1:486,3\n384#1:491,7\n*E\n"})
/* loaded from: classes6.dex */
public final class SearchNoteAct extends BaseVmActivity<LayoutSearchNoteOrParagraphBinding, AllTagViewModel> implements AdapterConvertListener<Notes>, BaseQuickAdapter.OnItemClickListener<Notes> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private CommonAdapter<Notes> adapter;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;
    private int page = 1;
    private int pageSize = 20;

    @NotNull
    private String keywords = "";

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/activity/SearchNoteAct$Companion;", "", "()V", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) SearchNoteAct.class));
        }
    }

    public SearchNoteAct() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<LayoutSearchNoteOrParagraphBinding>() { // from class: com.ykb.ebook.activity.SearchNoteAct$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final LayoutSearchNoteOrParagraphBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                LayoutSearchNoteOrParagraphBinding layoutSearchNoteOrParagraphBindingInflate = LayoutSearchNoteOrParagraphBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(layoutSearchNoteOrParagraphBindingInflate.getRoot());
                }
                return layoutSearchNoteOrParagraphBindingInflate;
            }
        });
    }

    private final void hideSystemUI() {
        int iColor;
        Window window = getWindow();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 1) {
            iColor = AppCtxKt.getAppCtx().getColor(R.color.color_EDE2C3);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() != 2 ? R.color.color_f9fafb : R.color.color_171C2D);
        }
        window.setNavigationBarColor(iColor);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                insetsController.setSystemBarsBehavior(2);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(4098);
        }
        if (i2 >= 28) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            Intrinsics.checkNotNullExpressionValue(attributes, "getWindow().getAttributes()");
            attributes.layoutInDisplayCutoutMode = 1;
            getWindow().setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$12(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$13(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$14(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$10(SearchNoteAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        KeyboardUtils.hideSoftInput(this$0.getBinding().etInput);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$4(SearchNoteAct this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        if (TextUtils.isEmpty(this$0.keywords)) {
            return;
        }
        this$0.page = 1;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllNotes(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$5(SearchNoteAct this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllNotes(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$6(SearchNoteAct this$0, View view) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = StringsKt__StringsKt.trim((CharSequence) String.valueOf(this$0.getBinding().etInput.getText())).toString();
        if (string.length() == 0) {
            ToastUtilsKt.toastOnUi$default(this$0, "请输入关键字！", 0, 2, (Object) null);
            return;
        }
        this$0.keywords = string;
        KeyboardUtils.hideSoftInput(this$0.getBinding().etInput);
        this$0.page = 1;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllNotes(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onActivityCreated$lambda$8(SearchNoteAct this$0, TextView textView, int i2, KeyEvent keyEvent) {
        String string;
        String id;
        Editable text;
        String string2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (i2 != 3) {
            return false;
        }
        REditText rEditText = this$0.getBinding().etInput;
        if (rEditText == null || (text = rEditText.getText()) == null || (string2 = text.toString()) == null || (string = StringsKt__StringsKt.trim((CharSequence) string2).toString()) == null) {
            string = "";
        }
        if (string.length() == 0) {
            ToastUtilsKt.toastOnUi$default(this$0, "请输入关键字！", 0, 2, (Object) null);
        } else {
            this$0.keywords = string;
            KeyboardUtils.hideSoftInput(this$0.getBinding().etInput);
            this$0.page = 1;
            AllTagViewModel viewModel = this$0.getViewModel();
            BookInfo book = ReadBook.INSTANCE.getBook();
            if (book == null || (id = book.getId()) == null) {
                id = "1";
            }
            viewModel.getAllNotes(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$9(SearchNoteAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().etInput.setText("");
        this$0.page = 1;
        CommonAdapter<Notes> commonAdapter = this$0.adapter;
        if (commonAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter = null;
        }
        commonAdapter.submitList(new ArrayList());
    }

    private final void updateStatusBar() {
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().setSystemUiVisibility(ReadConfig.INSTANCE.getColorMode() < 2 ? 8192 : 256);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull QuickViewHolder holder, int position, @NotNull Notes item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    public void observeViewModel() {
        MutableLiveData<BaseListResponse<AllNotes>> allNotes = getViewModel().getAllNotes();
        final Function1<BaseListResponse<AllNotes>, Unit> function1 = new Function1<BaseListResponse<AllNotes>, Unit>() { // from class: com.ykb.ebook.activity.SearchNoteAct.observeViewModel.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseListResponse<AllNotes> baseListResponse) {
                invoke2(baseListResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseListResponse<AllNotes> baseListResponse) {
                SearchNoteAct.this.getBinding().refreshLayout.finishRefresh();
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                for (Object obj : baseListResponse.getList()) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    AllNotes allNotes2 = (AllNotes) obj;
                    for (Notes notes : allNotes2.getNotesList()) {
                        notes.setTitle(allNotes2.getTitle());
                        notes.setSort(allNotes2.getSort());
                    }
                    arrayList.addAll(allNotes2.getNotesList());
                    i2 = i3;
                }
                Log.INSTANCE.logE("note_list", "note_list===>" + arrayList.size());
                CommonAdapter commonAdapter = null;
                if (SearchNoteAct.this.page == 1) {
                    CommonAdapter commonAdapter2 = SearchNoteAct.this.adapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.submitList(arrayList);
                    if (arrayList.size() < SearchNoteAct.this.pageSize) {
                        SearchNoteAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    return;
                }
                CommonAdapter commonAdapter3 = SearchNoteAct.this.adapter;
                if (commonAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                } else {
                    commonAdapter = commonAdapter3;
                }
                commonAdapter.addAll(arrayList);
                if (arrayList.size() < SearchNoteAct.this.pageSize) {
                    SearchNoteAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    SearchNoteAct.this.getBinding().refreshLayout.finishLoadMore();
                }
            }
        };
        allNotes.observe(this, new Observer() { // from class: com.ykb.ebook.activity.p2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchNoteAct.observeViewModel$lambda$11(function1, obj);
            }
        });
        MutableLiveData<Notes> editNote = getViewModel().getEditNote();
        final Function1<Notes, Unit> function12 = new Function1<Notes, Unit>() { // from class: com.ykb.ebook.activity.SearchNoteAct.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Notes notes) {
                invoke2(notes);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Notes notes) {
                CommonAdapter commonAdapter = SearchNoteAct.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                commonAdapter.notifyItemChanged(notes.getParentPos());
            }
        };
        editNote.observe(this, new Observer() { // from class: com.ykb.ebook.activity.q2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchNoteAct.observeViewModel$lambda$12(function12, obj);
            }
        });
        MutableLiveData<Notes> delNote = getViewModel().getDelNote();
        final Function1<Notes, Unit> function13 = new Function1<Notes, Unit>() { // from class: com.ykb.ebook.activity.SearchNoteAct.observeViewModel.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Notes notes) {
                invoke2(notes);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Notes it) {
                CommonAdapter commonAdapter = SearchNoteAct.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                Intrinsics.checkNotNullExpressionValue(it, "it");
                commonAdapter.remove(it);
                EventBus.INSTANCE.post(24, it.getId());
            }
        };
        delNote.observe(this, new Observer() { // from class: com.ykb.ebook.activity.r2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchNoteAct.observeViewModel$lambda$13(function13, obj);
            }
        });
        MutableLiveData<String> errorEvent = getViewModel().getErrorEvent();
        final Function1<String, Unit> function14 = new Function1<String, Unit>() { // from class: com.ykb.ebook.activity.SearchNoteAct.observeViewModel.4
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
                if (!TextUtils.equals(str, "请登录") && !TextUtils.equals(str, "user_id参数错误")) {
                    ToastUtilsKt.toastOnUi$default(SearchNoteAct.this, str, 0, 2, (Object) null);
                    return;
                }
                de.greenrobot.event.EventBus.getDefault().post("ebook_logout");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setPackage(SearchNoteAct.this.getPackageName());
                intent.setData(Uri.parse("yikaobang.app://ykb_login/"));
                SearchNoteAct.this.startActivity(intent);
                SearchNoteAct.this.finish();
            }
        };
        errorEvent.observe(this, new Observer() { // from class: com.ykb.ebook.activity.s2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchNoteAct.observeViewModel$lambda$14(function14, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws SecurityException {
        updateStatusBar();
        hideSystemUI();
        getBinding().etInput.setHint("输入关键字搜索笔记");
        KeyboardUtils.showSoftInput(getBinding().etInput);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        CommonAdapter<Notes> commonAdapter = null;
        if (colorMode == 0) {
            getBinding().btnSearch.setTextColor(getColor(R.color.color_303030));
        } else if (colorMode == 1) {
            getBinding().btnSearch.setTextColor(getColor(R.color.color_303030));
            FrameLayout frameLayout = getBinding().layoutSearch;
            if (frameLayout != null) {
                frameLayout.setBackgroundColor(getColor(R.color.color_F5EBCE));
            }
            REditText rEditText = getBinding().etInput;
            RTextViewHelper helper = rEditText != null ? rEditText.getHelper() : null;
            if (helper != null) {
                helper.setBackgroundColorNormal(getColor(R.color.color_EDE2C3));
            }
            ImageView imageView = getBinding().imgClean;
            if (imageView != null) {
                imageView.setImageResource(R.drawable.icon_close_yellow_theme_svg);
            }
        } else if (colorMode == 2) {
            TextView textView = getBinding().btnSearch;
            int i2 = R.color.color_7380a9;
            textView.setTextColor(getColor(i2));
            REditText rEditText2 = getBinding().etInput;
            if (rEditText2 != null) {
                rEditText2.setTextColor(getColor(i2));
            }
            REditText rEditText3 = getBinding().etInput;
            if (rEditText3 != null) {
                rEditText3.setHintTextColor(getColor(R.color.color_575F79));
            }
            FrameLayout frameLayout2 = getBinding().layoutSearch;
            if (frameLayout2 != null) {
                frameLayout2.setBackgroundColor(getColor(R.color.color_121622));
            }
            REditText rEditText4 = getBinding().etInput;
            RTextViewHelper helper2 = rEditText4 != null ? rEditText4.getHelper() : null;
            if (helper2 != null) {
                helper2.setBackgroundColorNormal(getColor(R.color.color_171C2D));
            }
            REditText rEditText5 = getBinding().etInput;
            RTextViewHelper helper3 = rEditText5 != null ? rEditText5.getHelper() : null;
            if (helper3 != null) {
                helper3.setIconNormal(getDrawable(R.drawable.icon_search_blue_theme_svg));
            }
            ImageView imageView2 = getBinding().imgClean;
            if (imageView2 != null) {
                imageView2.setImageResource(R.drawable.icon_close_night_svg);
            }
        }
        CommonAdapter<Notes> commonAdapter2 = new CommonAdapter<>(R.layout.item_all_notes, this);
        this.adapter = commonAdapter2;
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_empty_view_book, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.tv_empty)).setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        ImageView imageView3 = (ImageView) viewInflate.findViewById(R.id.img_empty);
        int colorMode2 = readConfig.getColorMode();
        imageView3.setImageResource(colorMode2 != 1 ? colorMode2 != 2 ? R.drawable.ic_empty : R.mipmap.ic_empty_night : R.mipmap.ic_empty_yellow);
        commonAdapter2.setEmptyView(viewInflate);
        CommonAdapter<Notes> commonAdapter3 = this.adapter;
        if (commonAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter3 = null;
        }
        commonAdapter3.setEmptyViewEnable(true);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = getBinding().recyclerView;
        CommonAdapter<Notes> commonAdapter4 = this.adapter;
        if (commonAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter4 = null;
        }
        recyclerView.setAdapter(commonAdapter4);
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
        CommonAdapter<Notes> commonAdapter5 = this.adapter;
        if (commonAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            commonAdapter = commonAdapter5;
        }
        commonAdapter.setOnItemClickListener(this);
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.activity.t2
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                SearchNoteAct.onActivityCreated$lambda$4(this.f26201c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.activity.u2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                SearchNoteAct.onActivityCreated$lambda$5(this.f26205c, refreshLayout);
            }
        });
        getBinding().btnSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.v2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchNoteAct.onActivityCreated$lambda$6(this.f26209c, view);
            }
        });
        REditText rEditText6 = getBinding().etInput;
        Intrinsics.checkNotNullExpressionValue(rEditText6, "binding.etInput");
        rEditText6.addTextChangedListener(new TextWatcher() { // from class: com.ykb.ebook.activity.SearchNoteAct$onActivityCreated$$inlined$addTextChangedListener$default$1
            @Override // android.text.TextWatcher
            public void afterTextChanged(@Nullable Editable s2) {
                String string;
                if (s2 == null || (string = s2.toString()) == null) {
                    string = "";
                }
                if (string.length() == 0) {
                    ImageView imgClean = this.this$0.getBinding().imgClean;
                    if (imgClean != null) {
                        Intrinsics.checkNotNullExpressionValue(imgClean, "imgClean");
                        ViewExtensionsKt.gone(imgClean);
                        return;
                    }
                    return;
                }
                ImageView imgClean2 = this.this$0.getBinding().imgClean;
                if (imgClean2 != null) {
                    Intrinsics.checkNotNullExpressionValue(imgClean2, "imgClean");
                    ViewExtensionsKt.visible(imgClean2);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
            }
        });
        getBinding().etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.ykb.ebook.activity.w2
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i3, KeyEvent keyEvent) {
                return SearchNoteAct.onActivityCreated$lambda$8(this.f26213c, textView2, i3, keyEvent);
            }
        });
        getBinding().imgClean.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.x2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchNoteAct.onActivityCreated$lambda$9(this.f26217c, view);
            }
        });
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.y2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchNoteAct.onActivityCreated$lambda$10(this.f26221c, view);
            }
        });
        int colorMode3 = readConfig.getColorMode();
        SmartRefreshLayout smartRefreshLayout2 = getBinding().refreshLayout;
        Intrinsics.checkNotNullExpressionValue(smartRefreshLayout2, "binding.refreshLayout");
        ClassicsHeader classicsHeader = getBinding().refreshHeader;
        Intrinsics.checkNotNullExpressionValue(classicsHeader, "binding.refreshHeader");
        ViewUtilKt.setRefreshTileView(colorMode3, smartRefreshLayout2, classicsHeader, this);
    }

    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
    public void onClick(@NotNull BaseQuickAdapter<Notes, ?> adapter, @NotNull View view, int position) {
        String chapterId;
        BookInfo book;
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "view");
        Notes item = adapter.getItem(position);
        if (item == null || (chapterId = item.getChapterId()) == null || (book = ReadBook.INSTANCE.getBook()) == null) {
            return;
        }
        Iterator<Chapter> it = book.getChapterList().iterator();
        int i2 = 0;
        while (true) {
            if (!it.hasNext()) {
                i2 = -1;
                break;
            } else if (Intrinsics.areEqual(it.next().getId(), chapterId)) {
                break;
            } else {
                i2++;
            }
        }
        if (i2 != -1) {
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, StringExtensionsKt.formatContent(item.getDrawContent()));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, item.getChapterId());
            ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.START_POSITION, Integer.parseInt(item.getStart_position() == null ? "0" : item.getStart_position()));
            ReadBook readBook = ReadBook.INSTANCE;
            if (readBook.getDurChapterIndex() == i2) {
                EventBus.INSTANCE.post(25);
            } else {
                ReadBook.openChapter$default(readBook, i2, 0, null, 6, null);
            }
            EventBus.INSTANCE.post(33);
            finish();
        }
    }

    @Override // com.ykb.ebook.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        upSystemUiVisibility();
    }

    public final void upSystemUiVisibility() {
        if (Build.VERSION.SDK_INT >= 30) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.show(WindowInsets.Type.statusBars());
                return;
            }
            return;
        }
        int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility() | 2 | 4096 | 1024;
        getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        getWindow().clearFlags(1024);
        getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() & (-5));
        if (ReadConfig.INSTANCE.getColorMode() != 2) {
            getWindow().clearFlags(67108864);
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility | 8192);
        }
    }

    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    public /* bridge */ /* synthetic */ void convert(QuickViewHolder quickViewHolder, int i2, Notes notes, List list) {
        convert2(quickViewHolder, i2, notes, (List<? extends Object>) list);
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public LayoutSearchNoteOrParagraphBinding getBinding() {
        return (LayoutSearchNoteOrParagraphBinding) this.binding.getValue();
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    @NotNull
    public AllTagViewModel initViewModel() {
        return (AllTagViewModel) new ViewModelProvider(this).get(AllTagViewModel.class);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:63:0x02e0  */
    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(@org.jetbrains.annotations.NotNull com.ykb.ebook.adapter.base.QuickViewHolder r13, final int r14, @org.jetbrains.annotations.NotNull final com.ykb.ebook.model.Notes r15) throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 858
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.activity.SearchNoteAct.convert(com.ykb.ebook.adapter.base.QuickViewHolder, int, com.ykb.ebook.model.Notes):void");
    }
}
