package com.ykb.ebook.activity;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
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
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RView;
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
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.base.BaseListResponse;
import com.ykb.ebook.base.BaseVmActivity;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AdapterConvertListener;
import com.ykb.ebook.databinding.LayoutSearchNoteOrParagraphBinding;
import com.ykb.ebook.dialog.AddReviewCommentDialog;
import com.ykb.ebook.dialog.CommonSureDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.AllParagraphComment;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.ParagraphReviewList;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.FilePathUtilKt;
import com.ykb.ebook.util.ImageLoader;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.vm.AllTagViewModel;
import com.ykb.ebook.weight.DrawLineTextView;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 22\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0012\u0004\u0012\u00020\u00050\u0006:\u00012B\u0005¢\u0006\u0002\u0010\u0007J \u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0005H\u0016J.\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u00052\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#H\u0016J\b\u0010%\u001a\u00020\u001dH\u0002J\b\u0010&\u001a\u00020\u0003H\u0014J\b\u0010'\u001a\u00020\u001dH\u0014J\u0012\u0010(\u001a\u00020\u001d2\b\u0010)\u001a\u0004\u0018\u00010*H\u0014J*\u0010+\u001a\u00020\u001d2\u0010\u0010\b\u001a\f\u0012\u0004\u0012\u00020\u0005\u0012\u0002\b\u00030,2\u0006\u0010-\u001a\u00020.2\u0006\u0010 \u001a\u00020\u0014H\u0016J\b\u0010/\u001a\u00020\u001dH\u0014J\u0006\u00100\u001a\u00020\u001dJ\b\u00101\u001a\u00020\u001dH\u0002R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\r\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R:\u0010\u0019\u001a.\u0012*\u0012(\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\u00160\u0016 \u001b*\u0014\u0012\u000e\b\u0001\u0012\n \u001b*\u0004\u0018\u00010\u00160\u0016\u0018\u00010\u001a0\u001a0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u00063"}, d2 = {"Lcom/ykb/ebook/activity/SearchParagraphAct;", "Lcom/ykb/ebook/base/BaseVmActivity;", "Lcom/ykb/ebook/databinding/LayoutSearchNoteOrParagraphBinding;", "Lcom/ykb/ebook/vm/AllTagViewModel;", "Lcom/ykb/ebook/common_interface/AdapterConvertListener;", "Lcom/ykb/ebook/model/ParagraphReviewList;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "()V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "albumLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/LayoutSearchNoteOrParagraphBinding;", "binding$delegate", "Lkotlin/Lazy;", "clickParagraphReviewList", "clickpostion", "", "keywords", "", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "permissionsLauncher", "", "kotlin.jvm.PlatformType", "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "payloads", "", "", "hideSystemUI", "initViewModel", "observeViewModel", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onClick", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "onResume", "upSystemUiVisibility", "updateStatusBar", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSearchParagraphAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SearchParagraphAct.kt\ncom/ykb/ebook/activity/SearchParagraphAct\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 TextView.kt\nandroidx/core/widget/TextViewKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 7 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,495:1\n13#2,10:496\n42#3:506\n42#3:507\n42#3:508\n42#3:535\n42#3:536\n42#3:537\n42#3:538\n42#3:539\n42#3:540\n42#3:541\n42#3:542\n42#3:543\n42#3:545\n65#4,16:509\n93#4,3:525\n350#5,7:528\n1#6:544\n215#7,2:546\n*S KotlinDebug\n*F\n+ 1 SearchParagraphAct.kt\ncom/ykb/ebook/activity/SearchParagraphAct\n*L\n90#1:496,10\n162#1:506\n177#1:507\n181#1:508\n394#1:535\n400#1:536\n407#1:537\n408#1:538\n409#1:539\n410#1:540\n411#1:541\n412#1:542\n416#1:543\n474#1:545\n219#1:509,16\n219#1:525,3\n313#1:528,7\n105#1:546,2\n*E\n"})
/* loaded from: classes6.dex */
public final class SearchParagraphAct extends BaseVmActivity<LayoutSearchNoteOrParagraphBinding, AllTagViewModel> implements AdapterConvertListener<ParagraphReviewList>, BaseQuickAdapter.OnItemClickListener<ParagraphReviewList> {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private CommonAdapter<ParagraphReviewList> adapter;
    private ActivityResultLauncher<Intent> albumLauncher;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;

    @Nullable
    private ParagraphReviewList clickParagraphReviewList;
    private int clickpostion;

    @NotNull
    private final ActivityResultLauncher<String[]> permissionsLauncher;
    private int page = 1;
    private int pageSize = 20;

    @NotNull
    private String keywords = "";

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/activity/SearchParagraphAct$Companion;", "", "()V", "newIntent", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void newIntent(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            context.startActivity(new Intent(context, (Class<?>) SearchParagraphAct.class));
        }
    }

    public SearchParagraphAct() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<LayoutSearchNoteOrParagraphBinding>() { // from class: com.ykb.ebook.activity.SearchParagraphAct$special$$inlined$viewBindingActivity$default$1
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
        ActivityResultLauncher<String[]> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.ykb.ebook.activity.i3
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                SearchParagraphAct.permissionsLauncher$lambda$2(this.f26148a, (Map) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…\n            }\n\n        }");
        this.permissionsLauncher = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$23(SearchParagraphAct this$0, ParagraphReviewList item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        new XPopup.Builder(this$0).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
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
    public static final void observeViewModel$lambda$17(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$18(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$19(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$11(SearchParagraphAct this$0, ActivityResult activityResult) {
        Uri data;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent data2 = activityResult.getData();
        if (data2 == null || (data = data2.getData()) == null) {
            return;
        }
        FilePathUtilKt.getPathFromUri(this$0, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$12(SearchParagraphAct this$0, View view) {
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
        viewModel.getAllParagraphComment(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onActivityCreated$lambda$14(SearchParagraphAct this$0, TextView textView, int i2, KeyEvent keyEvent) {
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
            viewModel.getAllParagraphComment(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$15(SearchParagraphAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().etInput.setText("");
        this$0.page = 1;
        CommonAdapter<ParagraphReviewList> commonAdapter = this$0.adapter;
        if (commonAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter = null;
        }
        commonAdapter.submitList(new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$16(SearchParagraphAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        KeyboardUtils.hideSoftInput(this$0.getBinding().etInput);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$7(SearchParagraphAct this$0, RefreshLayout it) {
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
        viewModel.getAllParagraphComment(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$8(SearchParagraphAct this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllParagraphComment(id, 0, this$0.page, this$0.pageSize, this$0.keywords);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsLauncher$lambda$2(final SearchParagraphAct this$0, Map result) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(result, "result");
        Iterator it = result.entrySet().iterator();
        while (it.hasNext()) {
            if (!((Boolean) ((Map.Entry) it.next()).getValue()).booleanValue()) {
                ToastUtilsKt.toastOnUi$default(this$0, "权限被拒绝，请到设置界面手动开启", 0, 2, (Object) null);
                return;
            }
        }
        ParagraphReviewList paragraphReviewList = this$0.clickParagraphReviewList;
        if (paragraphReviewList != null) {
            new AddReviewCommentDialog.Builder(this$0, this$0, "", "", "", paragraphReviewList.getId(), paragraphReviewList.getComment(), "编辑评论", paragraphReviewList.getComment(), null, false, null, R2.attr.tab_use_typeface_bold, null).setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct$permissionsLauncher$1$2$1
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
                    String id;
                    this.this$0.page = 1;
                    AllTagViewModel viewModel = this.this$0.getViewModel();
                    BookInfo book = ReadBook.INSTANCE.getBook();
                    if (book == null || (id = book.getId()) == null) {
                        id = "1";
                    }
                    AllTagViewModel.getAllParagraphComment$default(viewModel, id, 0, this.this$0.page, this.this$0.pageSize, null, 16, null);
                }
            }).show();
        }
    }

    private final void updateStatusBar() {
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        getWindow().setStatusBarColor(0);
        getWindow().getDecorView().setSystemUiVisibility(ReadConfig.INSTANCE.getColorMode() < 2 ? 8192 : 256);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull QuickViewHolder holder, int position, @NotNull ParagraphReviewList item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    @Override // com.ykb.ebook.base.BaseVmActivity
    public void observeViewModel() {
        MutableLiveData<BaseListResponse<AllParagraphComment>> allParagraphComment = getViewModel().getAllParagraphComment();
        final Function1<BaseListResponse<AllParagraphComment>, Unit> function1 = new Function1<BaseListResponse<AllParagraphComment>, Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct.observeViewModel.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseListResponse<AllParagraphComment> baseListResponse) {
                invoke2(baseListResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseListResponse<AllParagraphComment> baseListResponse) {
                SearchParagraphAct.this.getBinding().refreshLayout.finishRefresh();
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                for (Object obj : baseListResponse.getList()) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    AllParagraphComment allParagraphComment2 = (AllParagraphComment) obj;
                    for (ParagraphReviewList paragraphReviewList : allParagraphComment2.getParagraphReviewList()) {
                        paragraphReviewList.setTitle(allParagraphComment2.getTitle());
                        paragraphReviewList.setSort(allParagraphComment2.getSort());
                    }
                    arrayList.addAll(allParagraphComment2.getParagraphReviewList());
                    i2 = i3;
                }
                CommonAdapter commonAdapter = null;
                if (SearchParagraphAct.this.page == 1) {
                    CommonAdapter commonAdapter2 = SearchParagraphAct.this.adapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.submitList(arrayList);
                    if (arrayList.size() < SearchParagraphAct.this.pageSize) {
                        SearchParagraphAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                        return;
                    }
                    return;
                }
                CommonAdapter commonAdapter3 = SearchParagraphAct.this.adapter;
                if (commonAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                } else {
                    commonAdapter = commonAdapter3;
                }
                commonAdapter.addAll(arrayList);
                if (arrayList.size() < SearchParagraphAct.this.pageSize) {
                    SearchParagraphAct.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                } else {
                    SearchParagraphAct.this.getBinding().refreshLayout.finishLoadMore();
                }
            }
        };
        allParagraphComment.observe(this, new Observer() { // from class: com.ykb.ebook.activity.j3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchParagraphAct.observeViewModel$lambda$17(function1, obj);
            }
        });
        MutableLiveData<ParagraphReviewList> delReviewComment = getViewModel().getDelReviewComment();
        final Function1<ParagraphReviewList, Unit> function12 = new Function1<ParagraphReviewList, Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ParagraphReviewList paragraphReviewList) {
                invoke2(paragraphReviewList);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ParagraphReviewList it) {
                CommonAdapter commonAdapter = SearchParagraphAct.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                Intrinsics.checkNotNullExpressionValue(it, "it");
                commonAdapter.remove(it);
            }
        };
        delReviewComment.observe(this, new Observer() { // from class: com.ykb.ebook.activity.k3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchParagraphAct.observeViewModel$lambda$18(function12, obj);
            }
        });
        MutableLiveData<String> errorEvent = getViewModel().getErrorEvent();
        final Function1<String, Unit> function13 = new Function1<String, Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct.observeViewModel.3
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
                    ToastUtilsKt.toastOnUi$default(SearchParagraphAct.this, str, 0, 2, (Object) null);
                    return;
                }
                EventBus.getDefault().post("ebook_logout");
                Intent intent = new Intent();
                intent.setPackage(SearchParagraphAct.this.getPackageName());
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("yikaobang.app://ykb_login/"));
                SearchParagraphAct.this.startActivity(intent);
                SearchParagraphAct.this.finish();
            }
        };
        errorEvent.observe(this, new Observer() { // from class: com.ykb.ebook.activity.a3
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SearchParagraphAct.observeViewModel$lambda$19(function13, obj);
            }
        });
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) throws SecurityException {
        updateStatusBar();
        hideSystemUI();
        getBinding().etInput.setHint("输入关键字搜索段评");
        KeyboardUtils.showSoftInput(getBinding().etInput);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        CommonAdapter<ParagraphReviewList> commonAdapter = null;
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
        CommonAdapter<ParagraphReviewList> commonAdapter2 = new CommonAdapter<>(R.layout.item_all_paragraph_comment, this);
        this.adapter = commonAdapter2;
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_empty_view_book, (ViewGroup) null);
        ((TextView) viewInflate.findViewById(R.id.tv_empty)).setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        ImageView imageView3 = (ImageView) viewInflate.findViewById(R.id.img_empty);
        int colorMode2 = readConfig.getColorMode();
        imageView3.setImageResource(colorMode2 != 1 ? colorMode2 != 2 ? R.drawable.ic_empty : R.mipmap.ic_empty_night : R.mipmap.ic_empty_yellow);
        commonAdapter2.setEmptyView(viewInflate);
        CommonAdapter<ParagraphReviewList> commonAdapter3 = this.adapter;
        if (commonAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter3 = null;
        }
        commonAdapter3.setEmptyViewEnable(true);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView = getBinding().recyclerView;
        CommonAdapter<ParagraphReviewList> commonAdapter4 = this.adapter;
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
        CommonAdapter<ParagraphReviewList> commonAdapter5 = this.adapter;
        if (commonAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            commonAdapter = commonAdapter5;
        }
        commonAdapter.setOnItemClickListener(this);
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.activity.z2
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                SearchParagraphAct.onActivityCreated$lambda$7(this.f26225c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.activity.c3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                SearchParagraphAct.onActivityCreated$lambda$8(this.f26110c, refreshLayout);
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.ykb.ebook.activity.d3
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                SearchParagraphAct.onActivityCreated$lambda$11(this.f26115a, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…          }\n            }");
        this.albumLauncher = activityResultLauncherRegisterForActivityResult;
        getBinding().btnSearch.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.e3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchParagraphAct.onActivityCreated$lambda$12(this.f26123c, view);
            }
        });
        REditText rEditText6 = getBinding().etInput;
        Intrinsics.checkNotNullExpressionValue(rEditText6, "binding.etInput");
        rEditText6.addTextChangedListener(new TextWatcher() { // from class: com.ykb.ebook.activity.SearchParagraphAct$onActivityCreated$$inlined$addTextChangedListener$default$1
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
        getBinding().etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.ykb.ebook.activity.f3
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i3, KeyEvent keyEvent) {
                return SearchParagraphAct.onActivityCreated$lambda$14(this.f26128c, textView2, i3, keyEvent);
            }
        });
        getBinding().imgClean.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.g3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchParagraphAct.onActivityCreated$lambda$15(this.f26133c, view);
            }
        });
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.h3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SearchParagraphAct.onActivityCreated$lambda$16(this.f26141c, view);
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
    public void onClick(@NotNull BaseQuickAdapter<ParagraphReviewList, ?> adapter, @NotNull View view, int position) {
        String chapterId;
        BookInfo book;
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "view");
        ParagraphReviewList item = adapter.getItem(position);
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
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_DRAW_ID, StringExtensionsKt.formatContent(item.getParagraphContent()));
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_CHAPTER_ID, item.getChapterId());
            ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.JUMP_PARAGRAPH_ID, item.getParagraphId());
            ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.START_POSITION, Integer.parseInt(item.getStart_position() == null ? "0" : item.getStart_position()));
            ReadBook readBook = ReadBook.INSTANCE;
            if (readBook.getDurChapterIndex() == i2) {
                com.ykb.ebook.common.EventBus.INSTANCE.post(25);
            } else {
                ReadBook.openChapter$default(readBook, i2, 0, null, 6, null);
            }
            com.ykb.ebook.common.EventBus.INSTANCE.post(33);
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
    public /* bridge */ /* synthetic */ void convert(QuickViewHolder quickViewHolder, int i2, ParagraphReviewList paragraphReviewList, List list) {
        convert2(quickViewHolder, i2, paragraphReviewList, (List<? extends Object>) list);
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

    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    public void convert(@NotNull QuickViewHolder holder, final int position, @NotNull final ParagraphReviewList item) throws SecurityException {
        String string;
        String string2;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        View view = holder.getView(R.id.v_line);
        View view2 = holder.getView(R.id.v_line_child);
        textView.setText((char) 31532 + item.getSort() + "章 " + item.getTitle());
        int color = getColor(ReadConfig.INSTANCE.getColorMode() != 2 ? R.color.color_F95843 : R.color.color_B2575C);
        if (position == 0) {
            ViewExtensionsKt.visible(textView);
            ViewExtensionsKt.gone(view);
            ViewExtensionsKt.gone(view2);
        } else {
            String sort = item.getSort();
            CommonAdapter<ParagraphReviewList> commonAdapter = this.adapter;
            if (commonAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                commonAdapter = null;
            }
            if (Intrinsics.areEqual(sort, commonAdapter.getItems().get(position - 1).getSort())) {
                ViewExtensionsKt.gone(textView);
                ViewExtensionsKt.visible(view2);
                ViewExtensionsKt.gone(view);
            } else {
                ViewExtensionsKt.gone(view2);
                ViewExtensionsKt.visible(textView);
                ViewExtensionsKt.visible(view);
            }
        }
        DrawLineTextView drawLineTextView = (DrawLineTextView) holder.getView(R.id.tv_content);
        REditText rEditText = getBinding().etInput;
        Intrinsics.checkNotNull(rEditText);
        Editable text = rEditText.getText();
        if (text == null || (string2 = text.toString()) == null || (string = StringsKt__StringsKt.trim((CharSequence) string2).toString()) == null) {
            string = "";
        }
        if (!TextUtils.isEmpty(string)) {
            SpannableString spannableString = new SpannableString(item.getComment());
            Matcher matcher = Pattern.compile(string).matcher(item.getComment());
            while (matcher.find()) {
                spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), 18);
            }
            drawLineTextView.setText(spannableString);
        } else {
            drawLineTextView.setText(item.getComment());
        }
        int i2 = R.id.tv_citation;
        BaseViewHolder text2 = holder.setText(i2, StringsKt__StringsKt.trim((CharSequence) item.getParagraphContent()).toString());
        int i3 = R.id.tv_time;
        text2.setText(i3, item.getCtime());
        int i4 = R.id.img_delete;
        ViewExtensionsKt.clickDelay(holder.getView(i4), new Function0<Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct.convert.1
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
                final SearchParagraphAct searchParagraphAct = SearchParagraphAct.this;
                final ParagraphReviewList paragraphReviewList = item;
                new CommonSureDialog.Builder(searchParagraphAct, "确定删除该段评吗？", new Function0<Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct.convert.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        searchParagraphAct.getViewModel().delReviewComment(paragraphReviewList.getId(), paragraphReviewList);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }
                }).show();
            }
        });
        int i5 = R.id.img_edit;
        ViewExtensionsKt.clickDelay(holder.getView(i5), new Function0<Unit>() { // from class: com.ykb.ebook.activity.SearchParagraphAct.convert.2
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
                SearchParagraphAct.this.clickpostion = position;
                SearchParagraphAct.this.clickParagraphReviewList = item;
                SearchParagraphAct.this.permissionsLauncher.launch(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA});
            }
        });
        RBaseHelper helper = ((RView) holder.getView(R.id.dot)).getHelper();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        helper.setBackgroundColorNormal(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        int colorMode = readConfig.getColorMode();
        if (colorMode == 1) {
            int i6 = R.color.color_EDE2C3;
            view.setBackground(new ColorDrawable(getColor(i6)));
            view2.setBackground(new ColorDrawable(getColor(i6)));
            ((RLinearLayout) holder.getView(R.id.ll_quote)).getHelper().setBackgroundDrawableNormal(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f9e9c2)));
        } else if (colorMode == 2) {
            int i7 = R.color.color_171C2D;
            view.setBackground(new ColorDrawable(getColor(i7)));
            view2.setBackground(new ColorDrawable(AppCtxKt.getAppCtx().getColor(i7)));
            int i8 = R.color.color_575F79;
            textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8));
            holder.setTextColor(R.id.tv_content, ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9)).setTextColor(i3, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8)).setTextColor(i2, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8)).setBackgroundColor(R.id.v_line_child, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i7)).setBackgroundColor(R.id.v_line, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i7)).setImageResource(i5, R.mipmap.icon_edit_night).setImageResource(i4, R.mipmap.icon_delete_night);
            ((RLinearLayout) holder.getView(R.id.ll_quote)).getHelper().setBackgroundDrawableNormal(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i7)));
        }
        int i9 = R.id.image;
        ImageView imageView = (ImageView) holder.getView(i9);
        if (item.getPicture().length() > 0) {
            imageView.setVisibility(0);
            ImageLoader.INSTANCE.load(this, item.getPicture()).into(imageView);
        } else {
            imageView.setVisibility(8);
        }
        ((ImageView) holder.getView(i9)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.b3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                SearchParagraphAct.convert$lambda$23(this.f26103c, item, view3);
            }
        });
    }
}
