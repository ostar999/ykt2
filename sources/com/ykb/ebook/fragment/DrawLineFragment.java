package com.ykb.ebook.fragment;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.utils.Constants;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.helper.RCheckHelper;
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
import com.ykb.ebook.base.BaseVmFragment;
import com.ykb.ebook.base.ViewBindingProperty;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AdapterConvertListener;
import com.ykb.ebook.databinding.FragmentDrawLineBinding;
import com.ykb.ebook.dialog.CommonSureDialog;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.FragmentViewBindingsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.model.AllDrawLine;
import com.ykb.ebook.model.BookInfo;
import com.ykb.ebook.model.Chapter;
import com.ykb.ebook.model.Draw;
import com.ykb.ebook.page.ReadBook;
import com.ykb.ebook.util.ToastUtilsKt;
import com.ykb.ebook.util.ViewUtilKt;
import com.ykb.ebook.vm.AllTagViewModel;
import com.ykb.ebook.weight.UnderlineTextView;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0012\u0004\u0012\u00020\u00040\u0005B\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u0004H\u0016J.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u00042\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0016J\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0018\u0010\u001f\u001a\u00020\n2\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\nH\u0002J\b\u0010\"\u001a\u00020\u0002H\u0014J\b\u0010#\u001a\u00020\u0015H\u0014J*\u0010$\u001a\u00020\u00152\u0010\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030%2\u0006\u0010&\u001a\u00020'2\u0006\u0010\u0018\u001a\u00020\nH\u0016J\u001a\u0010(\u001a\u00020\u00152\u0006\u0010&\u001a\u00020'2\b\u0010)\u001a\u0004\u0018\u00010*H\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/ykb/ebook/fragment/DrawLineFragment;", "Lcom/ykb/ebook/base/BaseVmFragment;", "Lcom/ykb/ebook/vm/AllTagViewModel;", "Lcom/ykb/ebook/common_interface/AdapterConvertListener;", "Lcom/ykb/ebook/model/Draw;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "()V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "allCount", "", "binding", "Lcom/ykb/ebook/databinding/FragmentDrawLineBinding;", "getBinding", "()Lcom/ykb/ebook/databinding/FragmentDrawLineBinding;", "binding$delegate", "Lcom/ykb/ebook/base/ViewBindingProperty;", "isReverse", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "payloads", "", "", "drawBgColor", "index", "drawLineColor", "appCtx", "Landroid/content/Context;", "initViewModel", "observeViewModel", "onClick", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "onFragmentCreated", "savedInstanceState", "Landroid/os/Bundle;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nDrawLineFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DrawLineFragment.kt\ncom/ykb/ebook/fragment/DrawLineFragment\n+ 2 FragmentViewBindings.kt\ncom/ykb/ebook/extensions/FragmentViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,422:1\n35#2,5:423\n42#3:428\n42#3:429\n42#3:430\n350#4,7:431\n*S KotlinDebug\n*F\n+ 1 DrawLineFragment.kt\ncom/ykb/ebook/fragment/DrawLineFragment\n*L\n64#1:423,5\n82#1:428\n95#1:429\n99#1:430\n347#1:431,7\n*E\n"})
/* loaded from: classes7.dex */
public final class DrawLineFragment extends BaseVmFragment<AllTagViewModel> implements AdapterConvertListener<Draw>, BaseQuickAdapter.OnItemClickListener<Draw> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(DrawLineFragment.class, "binding", "getBinding()Lcom/ykb/ebook/databinding/FragmentDrawLineBinding;", 0))};
    private CommonAdapter<Draw> adapter;
    private int allCount;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final ViewBindingProperty binding;
    private int isReverse;
    private int page;
    private int pageSize;

    public DrawLineFragment() {
        super(R.layout.fragment_draw_line);
        this.binding = FragmentViewBindingsKt.viewBindingFragment(this, new Function1<DrawLineFragment, FragmentDrawLineBinding>() { // from class: com.ykb.ebook.fragment.DrawLineFragment$special$$inlined$viewBindingFragment$default$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final FragmentDrawLineBinding invoke(@NotNull DrawLineFragment fragment) {
                Intrinsics.checkNotNullParameter(fragment, "fragment");
                return FragmentDrawLineBinding.bind(fragment.requireView());
            }
        });
        this.page = 1;
        this.pageSize = 20;
    }

    private final int drawBgColor(int index) {
        return index != 0 ? index != 1 ? index != 2 ? index != 3 ? index != 4 ? AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6) : AppCtxKt.getAppCtx().getColor(R.color.color_26ffc86e) : AppCtxKt.getAppCtx().getColor(R.color.color_2690d691) : AppCtxKt.getAppCtx().getColor(R.color.color_2678aeff) : AppCtxKt.getAppCtx().getColor(R.color.color_26b6a0ff) : AppCtxKt.getAppCtx().getColor(R.color.color_26ff9da6);
    }

    private final int drawLineColor(Context appCtx, int index) {
        return index != 0 ? index != 1 ? index != 2 ? index != 3 ? index != 4 ? appCtx.getColor(R.color.color_b6a0ff) : appCtx.getColor(R.color.color_ffc86e) : appCtx.getColor(R.color.color_90d691) : appCtx.getColor(R.color.color_78aeff) : appCtx.getColor(R.color.color_b6a0ff) : appCtx.getColor(R.color.color_ff9da6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentDrawLineBinding getBinding() {
        return (FragmentDrawLineBinding) this.binding.getValue((ViewBindingProperty) this, $$delegatedProperties[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$10(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$7(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$8(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void observeViewModel$lambda$9(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$4(DrawLineFragment this$0, CompoundButton compoundButton, boolean z2) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        compoundButton.setText(z2 ? "正序" : "倒序");
        this$0.isReverse = z2 ? 1 : 0;
        this$0.page = 1;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllDrawLine(id, this$0.isReverse, this$0.page, this$0.pageSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$5(DrawLineFragment this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllDrawLine(id, this$0.isReverse, this$0.page, this$0.pageSize);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$6(DrawLineFragment this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllDrawLine(id, this$0.isReverse, this$0.page, this$0.pageSize);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull QuickViewHolder holder, int position, @NotNull Draw item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    @Override // com.ykb.ebook.base.BaseFragment
    public void observeViewModel() {
        MutableLiveData<BaseListResponse<AllDrawLine>> allDrawLine = getViewModel().getAllDrawLine();
        final Function1<BaseListResponse<AllDrawLine>, Unit> function1 = new Function1<BaseListResponse<AllDrawLine>, Unit>() { // from class: com.ykb.ebook.fragment.DrawLineFragment.observeViewModel.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(BaseListResponse<AllDrawLine> baseListResponse) {
                invoke2(baseListResponse);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(BaseListResponse<AllDrawLine> baseListResponse) {
                String str = (char) 20849 + baseListResponse.getCount() + "个划线";
                DrawLineFragment.this.allCount = Integer.parseInt(baseListResponse.getCount());
                DrawLineFragment.this.getBinding().tvAllDrawLine.setText(str);
                if (DrawLineFragment.this.allCount == 0) {
                    DrawLineFragment.this.getBinding().tvAllDrawLine.setVisibility(8);
                }
                DrawLineFragment.this.getBinding().refreshLayout.finishRefresh();
                ArrayList arrayList = new ArrayList();
                int i2 = 0;
                for (Object obj : baseListResponse.getList()) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    AllDrawLine allDrawLine2 = (AllDrawLine) obj;
                    for (Draw draw : allDrawLine2.getDrawList()) {
                        draw.setTitle(allDrawLine2.getTitle());
                        draw.setSort(allDrawLine2.getSort());
                    }
                    arrayList.addAll(allDrawLine2.getDrawList());
                    i2 = i3;
                }
                CommonAdapter commonAdapter = null;
                if (DrawLineFragment.this.page == 1) {
                    CommonAdapter commonAdapter2 = DrawLineFragment.this.adapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.submitList(arrayList);
                    if (arrayList.size() < DrawLineFragment.this.pageSize) {
                        DrawLineFragment.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                } else {
                    CommonAdapter commonAdapter3 = DrawLineFragment.this.adapter;
                    if (commonAdapter3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter3;
                    }
                    commonAdapter.addAll(arrayList);
                    if (arrayList.size() < DrawLineFragment.this.pageSize) {
                        DrawLineFragment.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        DrawLineFragment.this.getBinding().refreshLayout.finishLoadMore();
                    }
                }
                DrawLineFragment.this.getBinding().cbSort.setVisibility(DrawLineFragment.this.allCount == 0 ? 4 : 0);
            }
        };
        allDrawLine.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.a
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DrawLineFragment.observeViewModel$lambda$7(function1, obj);
            }
        });
        MutableLiveData<Integer> loadEvent = getViewModel().getLoadEvent();
        final Function1<Integer, Unit> function12 = new Function1<Integer, Unit>() { // from class: com.ykb.ebook.fragment.DrawLineFragment.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke2(num);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Integer num) {
                if ((num != null && num.intValue() == 0) || num == null || num.intValue() != 1) {
                    return;
                }
                if (DrawLineFragment.this.page == 1) {
                    DrawLineFragment.this.getBinding().refreshLayout.finishRefresh();
                } else {
                    DrawLineFragment.this.getBinding().refreshLayout.finishLoadMore();
                }
            }
        };
        loadEvent.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.b
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DrawLineFragment.observeViewModel$lambda$8(function12, obj);
            }
        });
        MutableLiveData<String> errorEvent = getViewModel().getErrorEvent();
        final Function1<String, Unit> function13 = new Function1<String, Unit>() { // from class: com.ykb.ebook.fragment.DrawLineFragment.observeViewModel.3
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
                    DrawLineFragment drawLineFragment = DrawLineFragment.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    ToastUtilsKt.toastOnUi(drawLineFragment, it);
                    return;
                }
                EventBus.getDefault().post("ebook_logout");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("yikaobang.app://ykb_login/"));
                Context context = DrawLineFragment.this.getContext();
                if (context != null) {
                    intent.setPackage(context.getPackageName());
                }
                DrawLineFragment.this.startActivity(intent);
                FragmentActivity activity = DrawLineFragment.this.getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        };
        errorEvent.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.c
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DrawLineFragment.observeViewModel$lambda$9(function13, obj);
            }
        });
        MutableLiveData<Draw> delLine = getViewModel().getDelLine();
        final Function1<Draw, Unit> function14 = new Function1<Draw, Unit>() { // from class: com.ykb.ebook.fragment.DrawLineFragment.observeViewModel.4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Draw draw) {
                invoke2(draw);
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Draw it) {
                CommonAdapter commonAdapter = DrawLineFragment.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                Intrinsics.checkNotNullExpressionValue(it, "it");
                commonAdapter.remove(it);
                DrawLineFragment drawLineFragment = DrawLineFragment.this;
                drawLineFragment.allCount--;
                DrawLineFragment.this.getBinding().tvAllDrawLine.setText((char) 20849 + DrawLineFragment.this.allCount + "个划线");
                if (DrawLineFragment.this.allCount == 0) {
                    DrawLineFragment.this.getBinding().tvAllDrawLine.setVisibility(8);
                }
                com.ykb.ebook.common.EventBus.INSTANCE.post(23, it.getId());
            }
        };
        delLine.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.d
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                DrawLineFragment.observeViewModel$lambda$10(function14, obj);
            }
        });
    }

    @Override // com.ykb.ebook.adapter.base.BaseQuickAdapter.OnItemClickListener
    public void onClick(@NotNull BaseQuickAdapter<Draw, ?> adapter, @NotNull View view, int position) {
        String chapterId;
        BookInfo book;
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(view, "view");
        Draw item = adapter.getItem(position);
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
        CommonAdapter<Draw> commonAdapter = new CommonAdapter<>(R.layout.item_draw_line, this);
        this.adapter = commonAdapter;
        commonAdapter.setOnItemClickListener(this);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        CommonAdapter<Draw> commonAdapter2 = this.adapter;
        CommonAdapter<Draw> commonAdapter3 = null;
        if (commonAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter2 = null;
        }
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_view_book, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_empty);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.img_empty);
        int colorMode = readConfig.getColorMode();
        imageView.setImageResource(colorMode != 1 ? colorMode != 2 ? R.drawable.ic_empty : R.mipmap.ic_empty_night : R.mipmap.ic_empty_yellow);
        commonAdapter2.setEmptyView(viewInflate);
        CommonAdapter<Draw> commonAdapter4 = this.adapter;
        if (commonAdapter4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter4 = null;
        }
        commonAdapter4.setEmptyViewEnable(true);
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
        RecyclerView recyclerView = getBinding().recyclerView;
        CommonAdapter<Draw> commonAdapter5 = this.adapter;
        if (commonAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
        } else {
            commonAdapter3 = commonAdapter5;
        }
        recyclerView.setAdapter(commonAdapter3);
        getBinding().cbSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.fragment.e
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                DrawLineFragment.onFragmentCreated$lambda$4(this.f26424c, compoundButton, z2);
            }
        });
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.fragment.f
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                DrawLineFragment.onFragmentCreated$lambda$5(this.f26425c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.fragment.g
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                DrawLineFragment.onFragmentCreated$lambda$6(this.f26426c, refreshLayout);
            }
        });
        if (readConfig.getColorMode() == 2) {
            RCheckBox rCheckBox = getBinding().cbSort;
            Context appCtx = AppCtxKt.getAppCtx();
            int i2 = R.color.color_575F79;
            rCheckBox.setTextColor(appCtx.getColor(i2));
            getBinding().tvAllDrawLine.setTextColor(AppCtxKt.getAppCtx().getColor(i2));
        }
        RCheckHelper helper = getBinding().cbSort.getHelper();
        if (helper != null) {
            helper.setIconCheckedLeft(ContextCompat.getDrawable(getBinding().cbSort.getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_positive_sequence_blue : R.drawable.icon_positive_sequence));
        }
        RCheckHelper helper2 = getBinding().cbSort.getHelper();
        if (helper2 != null) {
            helper2.setIconNormalLeft(ContextCompat.getDrawable(getBinding().cbSort.getContext(), readConfig.getColorMode() == 2 ? R.mipmap.icon_reverse_order_blue : R.drawable.icon_reverse_order));
        }
        AllTagViewModel viewModel = getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        viewModel.getAllDrawLine(id, this.isReverse, this.page, this.pageSize);
        int colorMode2 = readConfig.getColorMode();
        SmartRefreshLayout smartRefreshLayout2 = getBinding().refreshLayout;
        Intrinsics.checkNotNullExpressionValue(smartRefreshLayout2, "binding.refreshLayout");
        ClassicsHeader classicsHeader = getBinding().refreshHeader;
        Intrinsics.checkNotNullExpressionValue(classicsHeader, "binding.refreshHeader");
        ViewUtilKt.setRefreshTileView(colorMode2, smartRefreshLayout2, classicsHeader, getContext());
    }

    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    public /* bridge */ /* synthetic */ void convert(QuickViewHolder quickViewHolder, int i2, Draw draw, List list) {
        convert2(quickViewHolder, i2, draw, (List<? extends Object>) list);
    }

    @Override // com.ykb.ebook.base.BaseVmFragment
    @NotNull
    public AllTagViewModel initViewModel() {
        return (AllTagViewModel) new ViewModelProvider(this).get(AllTagViewModel.class);
    }

    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    public void convert(@NotNull QuickViewHolder holder, int position, @NotNull final Draw item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        View view = holder.getView(R.id.v_line);
        View view2 = holder.getView(R.id.v_line_child);
        textView.setText((char) 31532 + item.getSort() + "章 " + item.getTitle());
        if (position == 0) {
            ViewExtensionsKt.visible(textView);
            ViewExtensionsKt.gone(view);
            ViewExtensionsKt.gone(view2);
        } else {
            String sort = item.getSort();
            CommonAdapter<Draw> commonAdapter = this.adapter;
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
        UnderlineTextView underlineTextView = (UnderlineTextView) holder.getView(R.id.tv_content_line);
        TextView textView2 = (TextView) holder.getView(R.id.tv_content_bg);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int colorMode = readConfig.getColorMode();
        if (colorMode == 1) {
            Context contextRequireContext = requireContext();
            int i2 = R.color.color_EDE2C3;
            view.setBackground(new ColorDrawable(contextRequireContext.getColor(i2)));
            view2.setBackground(new ColorDrawable(requireContext().getColor(i2)));
        } else if (colorMode == 2) {
            textView.setTextColor(AppCtxKt.getAppCtx().getColor(R.color.color_575F79));
            Context appCtx = AppCtxKt.getAppCtx();
            int i3 = R.color.color_7380a9;
            underlineTextView.setTextColor(appCtx.getColor(i3));
            textView2.setTextColor(AppCtxKt.getAppCtx().getColor(i3));
            Context contextRequireContext2 = requireContext();
            int i4 = R.color.color_171C2D;
            view.setBackground(new ColorDrawable(contextRequireContext2.getColor(i4)));
            view2.setBackground(new ColorDrawable(AppCtxKt.getAppCtx().getColor(i4)));
        }
        int i5 = R.id.tv_time;
        BaseViewHolder textColor = holder.setTextColor(i5, AppCtxKt.getAppCtx().getColor(readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        int i6 = R.id.img_delete;
        textColor.setImageResource(i6, readConfig.getColorMode() == 2 ? R.mipmap.icon_delete_night : R.drawable.icon_delete);
        holder.setText(i5, item.getCtime());
        ViewExtensionsKt.clickDelay(holder.getView(i6), new Function0<Unit>() { // from class: com.ykb.ebook.fragment.DrawLineFragment.convert.1
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
                Context contextRequireContext3 = DrawLineFragment.this.requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext3, "requireContext()");
                final DrawLineFragment drawLineFragment = DrawLineFragment.this;
                final Draw draw = item;
                new CommonSureDialog.Builder(contextRequireContext3, "确定删除该划线吗？", new Function0<Unit>() { // from class: com.ykb.ebook.fragment.DrawLineFragment.convert.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        drawLineFragment.getViewModel().delLine(draw.getId(), draw);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }
                }).show();
            }
        });
        String style = item.getStyle();
        if (Intrinsics.areEqual(style, "0")) {
            ViewExtensionsKt.gone(textView2);
            ViewExtensionsKt.visible(underlineTextView);
            Context contextRequireContext3 = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext3, "requireContext()");
            underlineTextView.setUnderLineColor(drawLineColor(contextRequireContext3, Integer.parseInt(item.getColor())));
            underlineTextView.setText(StringExtensionsKt.formatContent(item.getDrawContent()));
            return;
        }
        if (Intrinsics.areEqual(style, "1")) {
            ViewExtensionsKt.gone(underlineTextView);
            ViewExtensionsKt.visible(textView2);
            textView2.setText(StringsKt__StringsKt.trimStart((CharSequence) item.getDrawContent()).toString());
            SpannableString spannableString = new SpannableString(StringExtensionsKt.formatContent(item.getDrawContent()));
            spannableString.setSpan(new GapLineBackgroundSpan(drawBgColor(Integer.parseInt(item.getColor())), 8.0f), 0, spannableString.length(), 33);
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(requireContext(), readConfig.getColorMode() == 2 ? R.color.color_7380a9 : R.color.color_303030)), 0, spannableString.length(), 33);
            textView2.setText(spannableString);
            textView2.setLineSpacing(25.0f, 1.0f);
        }
    }
}
