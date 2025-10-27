package com.ykb.ebook.fragment;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.SmartGlideImageLoader;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RView;
import com.ruffian.library.widget.helper.RBaseHelper;
import com.ruffian.library.widget.helper.RCheckHelper;
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
import com.ykb.ebook.base.BaseVmFragment;
import com.ykb.ebook.base.ViewBindingProperty;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AdapterConvertListener;
import com.ykb.ebook.databinding.FragmentParagraphCommentBinding;
import com.ykb.ebook.dialog.AddReviewCommentDialog;
import com.ykb.ebook.dialog.CommonSureDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.FragmentViewBindingsKt;
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
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0012\u0004\u0012\u00020\u00040\u0005B\u0005¢\u0006\u0002\u0010\u0006J \u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u0004H\u0016J.\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u00042\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0016J\b\u0010&\u001a\u00020\u0002H\u0014J\b\u0010'\u001a\u00020\u001eH\u0014J*\u0010(\u001a\u00020\u001e2\u0010\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030)2\u0006\u0010*\u001a\u00020+2\u0006\u0010!\u001a\u00020\rH\u0016J\u001a\u0010,\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+2\b\u0010-\u001a\u0004\u0018\u00010.H\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R:\u0010\u0019\u001a.\u0012*\u0012(\u0012\f\u0012\n \u001c*\u0004\u0018\u00010\u001b0\u001b \u001c*\u0014\u0012\u000e\b\u0001\u0012\n \u001c*\u0004\u0018\u00010\u001b0\u001b\u0018\u00010\u001a0\u001a0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lcom/ykb/ebook/fragment/ParagraphCommentFragment;", "Lcom/ykb/ebook/base/BaseVmFragment;", "Lcom/ykb/ebook/vm/AllTagViewModel;", "Lcom/ykb/ebook/common_interface/AdapterConvertListener;", "Lcom/ykb/ebook/model/ParagraphReviewList;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "()V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "albumLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "allCount", "", "binding", "Lcom/ykb/ebook/databinding/FragmentParagraphCommentBinding;", "getBinding", "()Lcom/ykb/ebook/databinding/FragmentParagraphCommentBinding;", "binding$delegate", "Lcom/ykb/ebook/base/ViewBindingProperty;", "clickParagraphReviewList", "clickpostion", "isReverse", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "permissionsLauncher", "", "", "kotlin.jvm.PlatformType", "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "payloads", "", "", "initViewModel", "observeViewModel", "onClick", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "onFragmentCreated", "savedInstanceState", "Landroid/os/Bundle;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nParagraphCommentFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParagraphCommentFragment.kt\ncom/ykb/ebook/fragment/ParagraphCommentFragment\n+ 2 FragmentViewBindings.kt\ncom/ykb/ebook/extensions/FragmentViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,330:1\n35#2,5:331\n42#3:336\n42#3:337\n42#3:338\n42#3:339\n42#3:340\n42#3:341\n42#3:342\n42#3:343\n42#3:344\n42#3:345\n42#3:346\n42#3:347\n42#3:348\n42#3:349\n350#4,7:350\n215#5,2:357\n1#6:359\n*S KotlinDebug\n*F\n+ 1 ParagraphCommentFragment.kt\ncom/ykb/ebook/fragment/ParagraphCommentFragment\n*L\n51#1:331,5\n101#1:336\n116#1:337\n120#1:338\n150#1:339\n151#1:340\n263#1:341\n268#1:342\n275#1:343\n276#1:344\n277#1:345\n278#1:346\n279#1:347\n280#1:348\n283#1:349\n314#1:350,7\n69#1:357,2\n*E\n"})
/* loaded from: classes7.dex */
public final class ParagraphCommentFragment extends BaseVmFragment<AllTagViewModel> implements AdapterConvertListener<ParagraphReviewList>, BaseQuickAdapter.OnItemClickListener<ParagraphReviewList> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(ParagraphCommentFragment.class, "binding", "getBinding()Lcom/ykb/ebook/databinding/FragmentParagraphCommentBinding;", 0))};
    private CommonAdapter<ParagraphReviewList> adapter;
    private ActivityResultLauncher<Intent> albumLauncher;
    private int allCount;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final ViewBindingProperty binding;

    @Nullable
    private ParagraphReviewList clickParagraphReviewList;
    private int clickpostion;
    private int isReverse;
    private int page;
    private int pageSize;

    @NotNull
    private final ActivityResultLauncher<String[]> permissionsLauncher;

    public ParagraphCommentFragment() {
        super(R.layout.fragment_paragraph_comment);
        this.binding = FragmentViewBindingsKt.viewBindingFragment(this, new Function1<ParagraphCommentFragment, FragmentParagraphCommentBinding>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment$special$$inlined$viewBindingFragment$default$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final FragmentParagraphCommentBinding invoke(@NotNull ParagraphCommentFragment fragment) {
                Intrinsics.checkNotNullParameter(fragment, "fragment");
                return FragmentParagraphCommentBinding.bind(fragment.requireView());
            }
        });
        this.page = 1;
        this.pageSize = 20;
        ActivityResultLauncher<String[]> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback() { // from class: com.ykb.ebook.fragment.w
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ParagraphCommentFragment.permissionsLauncher$lambda$2(this.f26443a, (Map) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…\n            }\n\n        }");
        this.permissionsLauncher = activityResultLauncherRegisterForActivityResult;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$16(ParagraphCommentFragment this$0, ParagraphReviewList item, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        new XPopup.Builder(this$0.requireActivity()).asImageViewer(null, item.getPicture(), new SmartGlideImageLoader(true, R.drawable.imgplacehodel_image)).isShowSaveButton(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentParagraphCommentBinding getBinding() {
        return (FragmentParagraphCommentBinding) this.binding.getValue((ViewBindingProperty) this, $$delegatedProperties[0]);
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
    public static final void observeViewModel$lambda$15(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$12(ParagraphCommentFragment this$0, ActivityResult activityResult) {
        Uri data;
        Context it1;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intent data2 = activityResult.getData();
        if (data2 == null || (data = data2.getData()) == null || (it1 = this$0.getContext()) == null) {
            return;
        }
        Intrinsics.checkNotNullExpressionValue(it1, "it1");
        FilePathUtilKt.getPathFromUri(it1, data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$7(ParagraphCommentFragment this$0, CompoundButton compoundButton, boolean z2) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        compoundButton.setText(z2 ? "正序" : "倒序");
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllParagraphComment$default(viewModel, id, z2 ? 1 : 0, this$0.page, this$0.pageSize, null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$8(ParagraphCommentFragment this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllParagraphComment$default(viewModel, id, 0, this$0.page, this$0.pageSize, null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$9(ParagraphCommentFragment this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllParagraphComment$default(viewModel, id, 0, this$0.page, this$0.pageSize, null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void permissionsLauncher$lambda$2(final ParagraphCommentFragment this$0, Map result) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullExpressionValue(result, "result");
        Iterator it = result.entrySet().iterator();
        while (it.hasNext()) {
            if (!((Boolean) ((Map.Entry) it.next()).getValue()).booleanValue()) {
                ToastUtilsKt.toastOnUi(this$0, "权限被拒绝，请到设置界面手动开启");
                return;
            }
        }
        ParagraphReviewList paragraphReviewList = this$0.clickParagraphReviewList;
        if (paragraphReviewList != null) {
            FragmentActivity fragmentActivityRequireActivity = this$0.requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity()");
            Context contextRequireContext = this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
            new AddReviewCommentDialog.Builder(fragmentActivityRequireActivity, contextRequireContext, "", "", "", paragraphReviewList.getId(), paragraphReviewList.getComment(), "编辑评论", paragraphReviewList.getComment(), null, false, null, R2.attr.tab_use_typeface_bold, null).setOnPublishSuccess(new Function0<Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment$permissionsLauncher$1$2$1
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
                    AllTagViewModel.getAllParagraphComment$default(viewModel, id, this.this$0.isReverse, this.this$0.page, this.this$0.pageSize, null, 16, null);
                }
            }).show();
        }
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull QuickViewHolder holder, int position, @NotNull ParagraphReviewList item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    @Override // com.ykb.ebook.base.BaseFragment
    public void observeViewModel() {
        MutableLiveData<BaseListResponse<AllParagraphComment>> allParagraphComment = getViewModel().getAllParagraphComment();
        final Function1<BaseListResponse<AllParagraphComment>, Unit> function1 = new Function1<BaseListResponse<AllParagraphComment>, Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment.observeViewModel.1
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
                ParagraphCommentFragment.this.allCount = Integer.parseInt(baseListResponse.getCount());
                ParagraphCommentFragment.this.getBinding().tvAllDrawLine.setText((char) 20849 + baseListResponse.getCount() + "个段评");
                if (ParagraphCommentFragment.this.allCount == 0) {
                    ParagraphCommentFragment.this.getBinding().tvAllDrawLine.setVisibility(8);
                }
                ParagraphCommentFragment.this.getBinding().refreshLayout.finishRefresh();
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
                if (ParagraphCommentFragment.this.page == 1) {
                    CommonAdapter commonAdapter2 = ParagraphCommentFragment.this.adapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.submitList(arrayList);
                    if (arrayList.size() < ParagraphCommentFragment.this.pageSize) {
                        ParagraphCommentFragment.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                } else {
                    CommonAdapter commonAdapter3 = ParagraphCommentFragment.this.adapter;
                    if (commonAdapter3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter3;
                    }
                    commonAdapter.addAll(arrayList);
                    if (arrayList.size() < ParagraphCommentFragment.this.pageSize) {
                        ParagraphCommentFragment.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        ParagraphCommentFragment.this.getBinding().refreshLayout.finishLoadMore();
                    }
                }
                ParagraphCommentFragment.this.getBinding().cbSort.setVisibility(ParagraphCommentFragment.this.allCount == 0 ? 4 : 0);
            }
        };
        allParagraphComment.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ParagraphCommentFragment.observeViewModel$lambda$13(function1, obj);
            }
        });
        MutableLiveData<ParagraphReviewList> delReviewComment = getViewModel().getDelReviewComment();
        final Function1<ParagraphReviewList, Unit> function12 = new Function1<ParagraphReviewList, Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment.observeViewModel.2
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
                CommonAdapter commonAdapter = ParagraphCommentFragment.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                Intrinsics.checkNotNullExpressionValue(it, "it");
                commonAdapter.remove(it);
                ParagraphCommentFragment paragraphCommentFragment = ParagraphCommentFragment.this;
                paragraphCommentFragment.allCount--;
                ParagraphCommentFragment.this.getBinding().tvAllDrawLine.setText((char) 20849 + ParagraphCommentFragment.this.allCount + "个段评");
                if (ParagraphCommentFragment.this.allCount == 0) {
                    ParagraphCommentFragment.this.getBinding().tvAllDrawLine.setVisibility(8);
                }
            }
        };
        delReviewComment.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.p
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ParagraphCommentFragment.observeViewModel$lambda$14(function12, obj);
            }
        });
        MutableLiveData<String> errorEvent = getViewModel().getErrorEvent();
        final Function1<String, Unit> function13 = new Function1<String, Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment.observeViewModel.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str) {
                invoke2(str);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                if (!TextUtils.equals(it, "请登录") && !TextUtils.equals(it, "user_id参数错误")) {
                    ParagraphCommentFragment paragraphCommentFragment = ParagraphCommentFragment.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    ToastUtilsKt.toastOnUi(paragraphCommentFragment, it);
                    return;
                }
                EventBus.getDefault().post("ebook_logout");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("yikaobang.app://ykb_login/"));
                Context context = ParagraphCommentFragment.this.getContext();
                if (context != null) {
                    intent.setPackage(context.getPackageName());
                }
                ParagraphCommentFragment.this.startActivity(intent);
                FragmentActivity activity = ParagraphCommentFragment.this.getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        };
        errorEvent.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ParagraphCommentFragment.observeViewModel$lambda$15(function13, obj);
            }
        });
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
            FragmentActivity activity = getActivity();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    @Override // com.ykb.ebook.base.BaseFragment
    public void onFragmentCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        String id;
        Intrinsics.checkNotNullParameter(view, "view");
        CommonAdapter<ParagraphReviewList> commonAdapter = new CommonAdapter<>(R.layout.item_all_paragraph_comment, this);
        this.adapter = commonAdapter;
        CommonAdapter<ParagraphReviewList> commonAdapter2 = null;
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_view_book, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_empty);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.img_empty);
        int colorMode = readConfig.getColorMode();
        imageView.setImageResource(colorMode != 1 ? colorMode != 2 ? R.drawable.ic_empty : R.mipmap.ic_empty_night : R.mipmap.ic_empty_yellow);
        commonAdapter.setEmptyView(viewInflate);
        CommonAdapter<ParagraphReviewList> commonAdapter3 = this.adapter;
        if (commonAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter3 = null;
        }
        commonAdapter3.setEmptyViewEnable(true);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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
            commonAdapter2 = commonAdapter5;
        }
        commonAdapter2.setOnItemClickListener(this);
        getBinding().cbSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.fragment.s
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                ParagraphCommentFragment.onFragmentCreated$lambda$7(this.f26439c, compoundButton, z2);
            }
        });
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.fragment.t
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                ParagraphCommentFragment.onFragmentCreated$lambda$8(this.f26440c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.fragment.u
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                ParagraphCommentFragment.onFragmentCreated$lambda$9(this.f26441c, refreshLayout);
            }
        });
        AllTagViewModel viewModel = getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllParagraphComment$default(viewModel, id, 0, this.page, this.pageSize, null, 16, null);
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: com.ykb.ebook.fragment.v
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                ParagraphCommentFragment.onFragmentCreated$lambda$12(this.f26442a, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResul…          }\n            }");
        this.albumLauncher = activityResultLauncherRegisterForActivityResult;
        if (readConfig.getColorMode() == 2) {
            TextView textView2 = getBinding().tvAllDrawLine;
            int i2 = R.color.color_575F79;
            textView2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i2));
            getBinding().cbSort.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i2));
        }
        RCheckHelper helper = getBinding().cbSort.getHelper();
        if (helper != null) {
            helper.setIconCheckedLeft(ContextCompat.getDrawable(getBinding().cbSort.getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_positive_sequence_blue : R.drawable.icon_positive_sequence));
        }
        RCheckHelper helper2 = getBinding().cbSort.getHelper();
        if (helper2 != null) {
            helper2.setIconNormalLeft(ContextCompat.getDrawable(getBinding().cbSort.getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_reverse_order_blue : R.drawable.icon_reverse_order));
        }
        int colorMode2 = readConfig.getColorMode();
        SmartRefreshLayout smartRefreshLayout2 = getBinding().refreshLayout;
        Intrinsics.checkNotNullExpressionValue(smartRefreshLayout2, "binding.refreshLayout");
        ClassicsHeader classicsHeader = getBinding().refreshHeader;
        Intrinsics.checkNotNullExpressionValue(classicsHeader, "binding.refreshHeader");
        ViewUtilKt.setRefreshTileView(colorMode2, smartRefreshLayout2, classicsHeader, getContext());
    }

    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    public /* bridge */ /* synthetic */ void convert(QuickViewHolder quickViewHolder, int i2, ParagraphReviewList paragraphReviewList, List list) {
        convert2(quickViewHolder, i2, paragraphReviewList, (List<? extends Object>) list);
    }

    @Override // com.ykb.ebook.base.BaseVmFragment
    @NotNull
    public AllTagViewModel initViewModel() {
        return (AllTagViewModel) new ViewModelProvider(this).get(AllTagViewModel.class);
    }

    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    public void convert(@NotNull QuickViewHolder holder, final int position, @NotNull final ParagraphReviewList item) throws SecurityException {
        int iColor;
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        int i2 = R.id.v_line;
        View view = holder.getView(i2);
        int i3 = R.id.v_line_child;
        View view2 = holder.getView(i3);
        textView.setText((char) 31532 + item.getSort() + "章 " + item.getTitle());
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
        int i4 = R.id.tv_content;
        BaseViewHolder text = holder.setText(i4, item.getComment());
        int i5 = R.id.tv_citation;
        BaseViewHolder text2 = text.setText(i5, StringsKt__StringsKt.trim((CharSequence) item.getParagraphContent()).toString());
        int i6 = R.id.tv_time;
        text2.setText(i6, item.getCtime());
        int i7 = R.id.img_delete;
        ViewExtensionsKt.clickDelay(holder.getView(i7), new Function0<Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment.convert.1
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
                Context contextRequireContext = ParagraphCommentFragment.this.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext()");
                final ParagraphCommentFragment paragraphCommentFragment = ParagraphCommentFragment.this;
                final ParagraphReviewList paragraphReviewList = item;
                new CommonSureDialog.Builder(contextRequireContext, "确定删除该段评吗？", new Function0<Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment.convert.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        paragraphCommentFragment.getViewModel().delReviewComment(paragraphReviewList.getId(), paragraphReviewList);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }
                }).show();
            }
        });
        int i8 = R.id.img_edit;
        ViewExtensionsKt.clickDelay(holder.getView(i8), new Function0<Unit>() { // from class: com.ykb.ebook.fragment.ParagraphCommentFragment.convert.2
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
                ParagraphCommentFragment.this.clickpostion = position;
                ParagraphCommentFragment.this.clickParagraphReviewList = item;
                ParagraphCommentFragment.this.permissionsLauncher.launch(new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA});
            }
        });
        RBaseHelper helper = ((RView) holder.getView(R.id.dot)).getHelper();
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (readConfig.getColorMode() == 2) {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_909090);
        }
        helper.setBackgroundColorNormal(iColor);
        int colorMode = readConfig.getColorMode();
        if (colorMode == 1) {
            Context contextRequireContext = requireContext();
            int i9 = R.color.color_EDE2C3;
            view.setBackground(new ColorDrawable(contextRequireContext.getColor(i9)));
            view2.setBackground(new ColorDrawable(requireContext().getColor(i9)));
            ((RLinearLayout) holder.getView(R.id.ll_quote)).getHelper().setBackgroundDrawableNormal(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_f9e9c2)));
        } else if (colorMode == 2) {
            Context contextRequireContext2 = requireContext();
            int i10 = R.color.color_171C2D;
            view.setBackground(new ColorDrawable(contextRequireContext2.getColor(i10)));
            view2.setBackground(new ColorDrawable(AppCtxKt.getAppCtx().getColor(i10)));
            int i11 = R.color.color_575F79;
            textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i11));
            holder.setTextColor(i4, ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9)).setTextColor(i6, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i11)).setTextColor(i5, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i11)).setBackgroundColor(i3, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10)).setBackgroundColor(i2, ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10)).setImageResource(i8, R.mipmap.icon_edit_night).setImageResource(i7, R.mipmap.icon_delete_night);
            ((RLinearLayout) holder.getView(R.id.ll_quote)).getHelper().setBackgroundDrawableNormal(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10)));
        }
        int i12 = R.id.image;
        ImageView imageView = (ImageView) holder.getView(i12);
        if (item.getPicture().length() > 0) {
            imageView.setVisibility(0);
            ImageLoader imageLoader = ImageLoader.INSTANCE;
            Context contextRequireContext3 = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext3, "requireContext()");
            imageLoader.load(contextRequireContext3, item.getPicture()).into(imageView);
        } else {
            imageView.setVisibility(8);
        }
        ((ImageView) holder.getView(i12)).setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.fragment.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ParagraphCommentFragment.convert$lambda$16(this.f26437c, item, view3);
            }
        });
    }
}
