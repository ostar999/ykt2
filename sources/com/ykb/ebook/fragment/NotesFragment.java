package com.ykb.ebook.fragment;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
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
import com.ykb.ebook.common.EventBus;
import com.ykb.ebook.common.PreferKeyKt;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AdapterConvertListener;
import com.ykb.ebook.databinding.FragmentNotesBinding;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.FragmentViewBindingsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
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
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00040\u00032\b\u0012\u0004\u0012\u00020\u00040\u0005B\u0005¢\u0006\u0002\u0010\u0006J \u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u0004H\u0016J.\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u00042\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0016J\b\u0010\u001f\u001a\u00020\u0002H\u0014J\b\u0010 \u001a\u00020\u0017H\u0014J*\u0010!\u001a\u00020\u00172\u0010\u0010\u0007\u001a\f\u0012\u0004\u0012\u00020\u0004\u0012\u0002\b\u00030\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001a\u001a\u00020\nH\u0016J\u001a\u0010%\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$2\b\u0010&\u001a\u0004\u0018\u00010'H\u0014R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lcom/ykb/ebook/fragment/NotesFragment;", "Lcom/ykb/ebook/base/BaseVmFragment;", "Lcom/ykb/ebook/vm/AllTagViewModel;", "Lcom/ykb/ebook/common_interface/AdapterConvertListener;", "Lcom/ykb/ebook/model/Notes;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "()V", "adapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "allCount", "", "binding", "Lcom/ykb/ebook/databinding/FragmentNotesBinding;", "getBinding", "()Lcom/ykb/ebook/databinding/FragmentNotesBinding;", "binding$delegate", "Lcom/ykb/ebook/base/ViewBindingProperty;", "editNoteContent", "", "isReverse", Constants.PARAMS_CONSTANTS.PARAMS_PAGE, ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "convert", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "position", "item", "payloads", "", "", "initViewModel", "observeViewModel", "onClick", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "onFragmentCreated", "savedInstanceState", "Landroid/os/Bundle;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nNotesFragment.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NotesFragment.kt\ncom/ykb/ebook/fragment/NotesFragment\n+ 2 FragmentViewBindings.kt\ncom/ykb/ebook/extensions/FragmentViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,303:1\n35#2,5:304\n42#3:309\n42#3:310\n42#3:311\n42#3:312\n42#3:313\n42#3:314\n42#3:315\n350#4,7:316\n*S KotlinDebug\n*F\n+ 1 NotesFragment.kt\ncom/ykb/ebook/fragment/NotesFragment\n*L\n51#1:304,5\n65#1:309\n82#1:310\n86#1:311\n109#1:312\n110#1:313\n256#1:314\n265#1:315\n288#1:316,7\n*E\n"})
/* loaded from: classes7.dex */
public final class NotesFragment extends BaseVmFragment<AllTagViewModel> implements AdapterConvertListener<Notes>, BaseQuickAdapter.OnItemClickListener<Notes> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(NotesFragment.class, "binding", "getBinding()Lcom/ykb/ebook/databinding/FragmentNotesBinding;", 0))};
    private CommonAdapter<Notes> adapter;
    private int allCount;

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final ViewBindingProperty binding;

    @NotNull
    private String editNoteContent;
    private int isReverse;
    private int page;
    private int pageSize;

    public NotesFragment() {
        super(R.layout.fragment_notes);
        this.binding = FragmentViewBindingsKt.viewBindingFragment(this, new Function1<NotesFragment, FragmentNotesBinding>() { // from class: com.ykb.ebook.fragment.NotesFragment$special$$inlined$viewBindingFragment$default$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final FragmentNotesBinding invoke(@NotNull NotesFragment fragment) {
                Intrinsics.checkNotNullParameter(fragment, "fragment");
                return FragmentNotesBinding.bind(fragment.requireView());
            }
        });
        this.page = 1;
        this.pageSize = 20;
        this.editNoteContent = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FragmentNotesBinding getBinding() {
        return (FragmentNotesBinding) this.binding.getValue((ViewBindingProperty) this, $$delegatedProperties[0]);
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
    public static final void onFragmentCreated$lambda$4(NotesFragment this$0, CompoundButton compoundButton, boolean z2) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        compoundButton.setText(z2 ? "正序" : "倒序");
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllNotes$default(viewModel, id, z2 ? 1 : 0, this$0.page, this$0.pageSize, null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$5(NotesFragment this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page = 1;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllNotes$default(viewModel, id, 0, this$0.page, this$0.pageSize, null, 16, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onFragmentCreated$lambda$6(NotesFragment this$0, RefreshLayout it) {
        String id;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.page++;
        AllTagViewModel viewModel = this$0.getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllNotes$default(viewModel, id, 0, this$0.page, this$0.pageSize, null, 16, null);
    }

    /* renamed from: convert, reason: avoid collision after fix types in other method */
    public void convert2(@NotNull QuickViewHolder holder, int position, @NotNull Notes item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
    }

    @Override // com.ykb.ebook.base.BaseFragment
    public void observeViewModel() {
        MutableLiveData<BaseListResponse<AllNotes>> allNotes = getViewModel().getAllNotes();
        final Function1<BaseListResponse<AllNotes>, Unit> function1 = new Function1<BaseListResponse<AllNotes>, Unit>() { // from class: com.ykb.ebook.fragment.NotesFragment.observeViewModel.1
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
                NotesFragment.this.allCount = Integer.parseInt(baseListResponse.getCount());
                NotesFragment.this.getBinding().tvAllDrawLine.setText((char) 20849 + baseListResponse.getCount() + "个笔记");
                if (NotesFragment.this.allCount == 0) {
                    NotesFragment.this.getBinding().tvAllDrawLine.setVisibility(8);
                }
                NotesFragment.this.getBinding().refreshLayout.finishRefresh();
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
                if (NotesFragment.this.page == 1) {
                    CommonAdapter commonAdapter2 = NotesFragment.this.adapter;
                    if (commonAdapter2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter2;
                    }
                    commonAdapter.submitList(arrayList);
                    if (arrayList.size() < NotesFragment.this.pageSize) {
                        NotesFragment.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                } else {
                    CommonAdapter commonAdapter3 = NotesFragment.this.adapter;
                    if (commonAdapter3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    } else {
                        commonAdapter = commonAdapter3;
                    }
                    commonAdapter.addAll(arrayList);
                    if (arrayList.size() < NotesFragment.this.pageSize) {
                        NotesFragment.this.getBinding().refreshLayout.finishLoadMoreWithNoMoreData();
                    } else {
                        NotesFragment.this.getBinding().refreshLayout.finishLoadMore();
                    }
                }
                NotesFragment.this.getBinding().cbSort.setVisibility(NotesFragment.this.allCount == 0 ? 4 : 0);
            }
        };
        allNotes.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.h
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotesFragment.observeViewModel$lambda$7(function1, obj);
            }
        });
        MutableLiveData<Notes> editNote = getViewModel().getEditNote();
        final Function1<Notes, Unit> function12 = new Function1<Notes, Unit>() { // from class: com.ykb.ebook.fragment.NotesFragment.observeViewModel.2
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
                CommonAdapter commonAdapter = NotesFragment.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                commonAdapter.notifyItemChanged(notes.getParentPos());
            }
        };
        editNote.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.i
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotesFragment.observeViewModel$lambda$8(function12, obj);
            }
        });
        MutableLiveData<Notes> delNote = getViewModel().getDelNote();
        final Function1<Notes, Unit> function13 = new Function1<Notes, Unit>() { // from class: com.ykb.ebook.fragment.NotesFragment.observeViewModel.3
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
                CommonAdapter commonAdapter = NotesFragment.this.adapter;
                if (commonAdapter == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("adapter");
                    commonAdapter = null;
                }
                Intrinsics.checkNotNullExpressionValue(it, "it");
                commonAdapter.remove(it);
                NotesFragment notesFragment = NotesFragment.this;
                notesFragment.allCount--;
                NotesFragment.this.getBinding().tvAllDrawLine.setText((char) 20849 + NotesFragment.this.allCount + "个笔记");
                if (NotesFragment.this.allCount == 0) {
                    NotesFragment.this.getBinding().tvAllDrawLine.setVisibility(8);
                }
                EventBus.INSTANCE.post(24, it.getId());
            }
        };
        delNote.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.j
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotesFragment.observeViewModel$lambda$9(function13, obj);
            }
        });
        MutableLiveData<String> errorEvent = getViewModel().getErrorEvent();
        final Function1<String, Unit> function14 = new Function1<String, Unit>() { // from class: com.ykb.ebook.fragment.NotesFragment.observeViewModel.4
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
                    NotesFragment notesFragment = NotesFragment.this;
                    Intrinsics.checkNotNullExpressionValue(it, "it");
                    ToastUtilsKt.toastOnUi(notesFragment, it);
                    return;
                }
                de.greenrobot.event.EventBus.getDefault().post("ebook_logout");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("yikaobang.app://ykb_login/"));
                Context context = NotesFragment.this.getContext();
                if (context != null) {
                    intent.setPackage(context.getPackageName());
                }
                NotesFragment.this.startActivity(intent);
                FragmentActivity activity = NotesFragment.this.getActivity();
                if (activity != null) {
                    activity.finish();
                }
            }
        };
        errorEvent.observe(this, new Observer() { // from class: com.ykb.ebook.fragment.k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NotesFragment.observeViewModel$lambda$10(function14, obj);
            }
        });
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
        CommonAdapter<Notes> commonAdapter = new CommonAdapter<>(R.layout.item_all_notes, this);
        this.adapter = commonAdapter;
        CommonAdapter<Notes> commonAdapter2 = null;
        View viewInflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_empty_view_book, (ViewGroup) null);
        TextView textView = (TextView) viewInflate.findViewById(R.id.tv_empty);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), readConfig.getColorMode() == 2 ? R.color.color_575F79 : R.color.color_909090));
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.img_empty);
        int colorMode = readConfig.getColorMode();
        imageView.setImageResource(colorMode != 1 ? colorMode != 2 ? R.drawable.ic_empty : R.mipmap.ic_empty_night : R.mipmap.ic_empty_yellow);
        commonAdapter.setEmptyView(viewInflate);
        CommonAdapter<Notes> commonAdapter3 = this.adapter;
        if (commonAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            commonAdapter3 = null;
        }
        commonAdapter3.setEmptyViewEnable(true);
        if (readConfig.getColorMode() == 2) {
            getBinding().cbSort.setTextColor(AppCtxKt.getAppCtx().getColor(R.color.color_575F79));
        }
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
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
            commonAdapter2 = commonAdapter5;
        }
        commonAdapter2.setOnItemClickListener(this);
        getBinding().cbSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.fragment.l
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                NotesFragment.onFragmentCreated$lambda$4(this.f26431c, compoundButton, z2);
            }
        });
        getBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.ykb.ebook.fragment.m
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                NotesFragment.onFragmentCreated$lambda$5(this.f26432c, refreshLayout);
            }
        });
        getBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.ykb.ebook.fragment.n
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                NotesFragment.onFragmentCreated$lambda$6(this.f26433c, refreshLayout);
            }
        });
        AllTagViewModel viewModel = getViewModel();
        BookInfo book = ReadBook.INSTANCE.getBook();
        if (book == null || (id = book.getId()) == null) {
            id = "1";
        }
        AllTagViewModel.getAllNotes$default(viewModel, id, 0, this.page, this.pageSize, null, 16, null);
        if (readConfig.getColorMode() == 2) {
            RCheckBox rCheckBox = getBinding().cbSort;
            int i2 = R.color.color_575F79;
            rCheckBox.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i2));
            getBinding().tvAllDrawLine.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i2));
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
    public /* bridge */ /* synthetic */ void convert(QuickViewHolder quickViewHolder, int i2, Notes notes, List list) {
        convert2(quickViewHolder, i2, notes, (List<? extends Object>) list);
    }

    @Override // com.ykb.ebook.base.BaseVmFragment
    @NotNull
    public AllTagViewModel initViewModel() {
        return (AllTagViewModel) new ViewModelProvider(this).get(AllTagViewModel.class);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x02f4  */
    @Override // com.ykb.ebook.common_interface.AdapterConvertListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void convert(@org.jetbrains.annotations.NotNull com.ykb.ebook.adapter.base.QuickViewHolder r17, final int r18, @org.jetbrains.annotations.NotNull final com.ykb.ebook.model.Notes r19) throws java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 786
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.fragment.NotesFragment.convert(com.ykb.ebook.adapter.base.QuickViewHolder, int, com.ykb.ebook.model.Notes):void");
    }
}
