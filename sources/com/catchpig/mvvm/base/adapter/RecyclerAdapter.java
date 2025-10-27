package com.catchpig.mvvm.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewbinding.ViewBinding;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.utils.ext.TextViewExtKt;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.noober.background.view.BLTextView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004:\u0002]^B\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010(\u001a\u00020)2\u000e\u0010*\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bJ\u0010\u0010+\u001a\u00020)2\b\b\u0001\u0010,\u001a\u00020\fJ\u0010\u0010-\u001a\u00020)2\b\b\u0001\u0010,\u001a\u00020\fJ+\u0010.\u001a\u00020)2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u00100\u001a\u00028\u00002\u0006\u00101\u001a\u00020\fH&¢\u0006\u0002\u00102J\u0006\u00103\u001a\u00020)J1\u0010\u0013\u001a\u00020)\"\n\b\u0002\u00104\u0018\u0001*\u00020\u00032\u0017\u00105\u001a\u0013\u0012\u0004\u0012\u0002H4\u0012\u0004\u0012\u00020)06¢\u0006\u0002\b7H\u0086\bø\u0001\u0000J1\u0010\u001a\u001a\u00020)\"\n\b\u0002\u00108\u0018\u0001*\u00020\u00032\u0017\u00109\u001a\u0013\u0012\u0004\u0012\u0002H8\u0012\u0004\u0012\u00020)06¢\u0006\u0002\b7H\u0086\bø\u0001\u0000J\u0015\u0010:\u001a\u0004\u0018\u00018\u00002\u0006\u00101\u001a\u00020\f¢\u0006\u0002\u0010;J\u0010\u0010<\u001a\u00020\f2\u0006\u00101\u001a\u00020\fH\u0007J\f\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000\bJ\b\u0010>\u001a\u00020\fH\u0016J\u0010\u0010?\u001a\u00020\f2\u0006\u00101\u001a\u00020\fH\u0016J1\u0010\u001d\u001a\u00020)\"\n\b\u0002\u0010@\u0018\u0001*\u00020\u00032\u0017\u00109\u001a\u0013\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u00020)06¢\u0006\u0002\b7H\u0086\bø\u0001\u0000J\u0010\u0010A\u001a\u00020)2\u0006\u0010$\u001a\u00020#H\u0016J\u001e\u0010B\u001a\u00020)2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u00101\u001a\u00020\fH\u0016J\u001e\u0010C\u001a\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010D\u001a\u00020E2\u0006\u0010F\u001a\u00020\fH\u0016J\u0016\u0010G\u001a\u00020)2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005H\u0016J\u0016\u0010H\u001a\u00020)2\u000e\u0010*\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\bJ\u0010\u0010I\u001a\u00020)2\b\b\u0001\u0010J\u001a\u00020\fJ\u0016\u0010K\u001a\u00020)2\u0006\u0010L\u001a\u00020\f2\u0006\u0010M\u001a\u00020\fJ\u000e\u0010N\u001a\u00020)2\u0006\u00105\u001a\u00020\nJ\u000e\u0010O\u001a\u00020)2\u0006\u0010P\u001a\u00020\u0010J\u0010\u0010Q\u001a\u00020)2\b\b\u0001\u0010R\u001a\u00020\fJ\u000e\u0010S\u001a\u00020)2\u0006\u0010T\u001a\u00020\fJS\u0010U\u001a\u00020)2K\u0010V\u001aG\u0012\u0013\u0012\u00110\f¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(J\u0012\u0013\u0012\u00118\u0000¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(0\u0012\u0013\u0012\u00110\f¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(1\u0012\u0004\u0012\u00020)0WJ)\u0010U\u001a\u00020)2!\u0010V\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(0\u0012\u0004\u0012\u00020)06J>\u0010U\u001a\u00020)26\u0010V\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(0\u0012\u0013\u0012\u00110\f¢\u0006\f\bX\u0012\b\bY\u0012\u0004\b\b(1\u0012\u0004\u0012\u00020)0ZJ\u0014\u0010U\u001a\u00020)2\f\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!J\u0015\u0010[\u001a\u00028\u00012\u0006\u0010D\u001a\u00020EH&¢\u0006\u0002\u0010\\R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0016\"\u0004\b\u001c\u0010\u0018R\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0016\"\u0004\b\u001f\u0010\u0018R\u0016\u0010 \u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010!X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010$\u001a\u00020#2\u0006\u0010\"\u001a\u00020#@BX\u0086.¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006_"}, d2 = {"Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter;", "M", "VB", "Landroidx/viewbinding/ViewBinding;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "()V", "data", "", "emptyMShow", "", "emptyNoDataDrawableH", "", "emptyNoDataDrawableId", "emptyNoDataDrawableW", "emptyTip", "", "emptyTipColorId", "emptyType", "emptyView", "Landroid/view/View;", "getEmptyView", "()Landroid/view/View;", "setEmptyView", "(Landroid/view/View;)V", "firstLoad", "footerView", "getFooterView", "setFooterView", "headerView", "getHeaderView", "setHeaderView", "onItemClickListener", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter$OnItemClickListener;", "<set-?>", "Landroidx/recyclerview/widget/RecyclerView;", "recyclerView", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "showEmpty", "add", "", "list", "addFooterView", "layoutId", "addHeaderView", "bindViewHolder", "holder", "m", "position", "(Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;Ljava/lang/Object;I)V", PLVDocumentMarkToolType.CLEAR, "EVB", "empty", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "FVB", "header", "get", "(I)Ljava/lang/Object;", "getCenterViewType", "getData", "getItemCount", "getItemViewType", "HVB", "onAttachedToRecyclerView", "onBindViewHolder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "onViewAttachedToWindow", "set", "setEmptyDawableId", "id", "setEmptyDawableWidthH", "w", "h", "setEmptyMShow", "setEmptyTip", "tip", "setEmptyTipColor", "colorId", "setEmptyType", "type", "setOnItemClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "Lkotlin/Function2;", "viewBinding", "(Landroid/view/ViewGroup;)Landroidx/viewbinding/ViewBinding;", "ItemViewType", "OnItemClickListener", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nRecyclerAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecyclerAdapter.kt\ncom/catchpig/mvvm/base/adapter/RecyclerAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,507:1\n1#2:508\n*E\n"})
/* loaded from: classes2.dex */
public abstract class RecyclerAdapter<M, VB extends ViewBinding> extends RecyclerView.Adapter<CommonViewHolder<VB>> {
    private boolean emptyMShow;

    @Nullable
    private View emptyView;

    @Nullable
    private View footerView;

    @Nullable
    private View headerView;

    @Nullable
    private OnItemClickListener<M> onItemClickListener;
    private RecyclerView recyclerView;
    private boolean showEmpty;

    @NotNull
    private List<M> data = new ArrayList();

    @NotNull
    private String emptyTip = "";
    private int emptyTipColorId = -1;
    private int emptyNoDataDrawableId = -1;
    private int emptyNoDataDrawableW = -1;
    private int emptyNoDataDrawableH = -1;
    private int emptyType = 100;
    private boolean firstLoad = true;

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\f"}, d2 = {"Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter$ItemViewType;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "TYPE_HEADER", "TYPE_FOOTER", "TYPE_EMPTY", "TYPE_NORMAL", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum ItemViewType {
        TYPE_HEADER(-1),
        TYPE_FOOTER(-2),
        TYPE_EMPTY(-3),
        TYPE_NORMAL(0);


        /* renamed from: Companion, reason: from kotlin metadata */
        @NotNull
        public static final Companion INSTANCE = new Companion(null);
        private final int value;

        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter$ItemViewType$Companion;", "", "()V", "enumOfValue", "Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter$ItemViewType;", "value", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
        @SourceDebugExtension({"SMAP\nRecyclerAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RecyclerAdapter.kt\ncom/catchpig/mvvm/base/adapter/RecyclerAdapter$ItemViewType$Companion\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,507:1\n13579#2,2:508\n*S KotlinDebug\n*F\n+ 1 RecyclerAdapter.kt\ncom/catchpig/mvvm/base/adapter/RecyclerAdapter$ItemViewType$Companion\n*L\n57#1:508,2\n*E\n"})
        public static final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @NotNull
            public final ItemViewType enumOfValue(int value) {
                for (ItemViewType itemViewType : ItemViewType.values()) {
                    if (itemViewType.getValue() == value) {
                        return itemViewType;
                    }
                }
                return ItemViewType.TYPE_NORMAL;
            }
        }

        ItemViewType(int i2) {
            this.value = i2;
        }

        public final int getValue() {
            return this.value;
        }
    }

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\bf\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002J'\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u00022\u0006\u0010\b\u001a\u00020\u0006H&¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lcom/catchpig/mvvm/base/adapter/RecyclerAdapter$OnItemClickListener;", "M", "", "itemClick", "", "id", "", "m", "position", "(ILjava/lang/Object;I)V", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemClickListener<M> {
        void itemClick(@IdRes int id, M m2, int position);
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ItemViewType.values().length];
            try {
                iArr[ItemViewType.TYPE_HEADER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ItemViewType.TYPE_FOOTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ItemViewType.TYPE_EMPTY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$6(RecyclerAdapter this$0, Object obj, Ref.IntRef index, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(index, "$index");
        OnItemClickListener<M> onItemClickListener = this$0.onItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.itemClick(this$0.getRecyclerView().getId(), obj, index.element);
        }
    }

    public final void add(@Nullable List<M> list) {
        if (list != null) {
            this.data.addAll(list);
        }
        notifyDataSetChanged();
    }

    public final void addFooterView(@LayoutRes int layoutId) {
        this.footerView = LayoutInflater.from(getRecyclerView().getContext()).inflate(layoutId, (ViewGroup) getRecyclerView(), false);
        notifyDataSetChanged();
    }

    public final void addHeaderView(@LayoutRes int layoutId) {
        this.headerView = LayoutInflater.from(getRecyclerView().getContext()).inflate(layoutId, (ViewGroup) getRecyclerView(), false);
        notifyDataSetChanged();
    }

    public abstract void bindViewHolder(@NotNull CommonViewHolder<VB> holder, M m2, int position);

    public final void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public final /* synthetic */ <EVB extends ViewBinding> void emptyView(Function1<? super EVB, Unit> empty) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(empty, "empty");
        Intrinsics.reifiedOperationMarker(4, "EVB");
        Object objInvoke = ViewBinding.class.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE).invoke(this, LayoutInflater.from(getRecyclerView().getContext()), getRecyclerView(), Boolean.FALSE);
        Intrinsics.reifiedOperationMarker(1, "EVB");
        ViewBinding viewBinding = (ViewBinding) objInvoke;
        setEmptyView(viewBinding.getRoot());
        empty.invoke(viewBinding);
    }

    public final /* synthetic */ <FVB extends ViewBinding> void footerView(Function1<? super FVB, Unit> header) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.reifiedOperationMarker(4, "FVB");
        Object objInvoke = ViewBinding.class.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE).invoke(this, LayoutInflater.from(getRecyclerView().getContext()), getRecyclerView(), Boolean.FALSE);
        Intrinsics.reifiedOperationMarker(1, "FVB");
        ViewBinding viewBinding = (ViewBinding) objInvoke;
        setFooterView(viewBinding.getRoot());
        header.invoke(viewBinding);
    }

    @Nullable
    public final M get(int position) {
        if (position >= 0 && position < this.data.size()) {
            return this.data.get(position);
        }
        throw new IllegalStateException("position必须大于0,且不能大于data.size".toString());
    }

    @IntRange(from = 0)
    public final int getCenterViewType(int position) {
        return ItemViewType.TYPE_NORMAL.getValue();
    }

    @NotNull
    public final List<M> getData() {
        return this.data;
    }

    @Nullable
    public final View getEmptyView() {
        return this.emptyView;
    }

    @Nullable
    public final View getFooterView() {
        return this.footerView;
    }

    @Nullable
    public final View getHeaderView() {
        return this.headerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size = this.data.size();
        if (this.headerView != null) {
            size++;
        }
        if (this.footerView != null) {
            size++;
        }
        if (size != 0) {
            this.showEmpty = false;
            return size;
        }
        if (!this.emptyMShow) {
            return size;
        }
        this.showEmpty = true;
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int position) {
        return this.showEmpty ? (position != 0 || this.headerView == null) ? (position == 0 && this.headerView == null) ? ItemViewType.TYPE_EMPTY.getValue() : (position != 1 || this.headerView == null) ? getCenterViewType(position) : ItemViewType.TYPE_EMPTY.getValue() : ItemViewType.TYPE_HEADER.getValue() : (position != 0 || this.headerView == null) ? (position != getItemCount() - 1 || this.headerView == null || this.footerView == null) ? (position == getItemCount() - 1 && this.headerView == null && this.footerView != null) ? ItemViewType.TYPE_FOOTER.getValue() : getCenterViewType(position) : ItemViewType.TYPE_FOOTER.getValue() : ItemViewType.TYPE_HEADER.getValue();
    }

    @NotNull
    public final RecyclerView getRecyclerView() {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            return recyclerView;
        }
        Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
        return null;
    }

    public final /* synthetic */ <HVB extends ViewBinding> void headerView(Function1<? super HVB, Unit> header) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Intrinsics.checkNotNullParameter(header, "header");
        Intrinsics.reifiedOperationMarker(4, "HVB");
        Object objInvoke = ViewBinding.class.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, Boolean.TYPE).invoke(this, LayoutInflater.from(getRecyclerView().getContext()), getRecyclerView(), Boolean.FALSE);
        Intrinsics.reifiedOperationMarker(1, "HVB");
        ViewBinding viewBinding = (ViewBinding) objInvoke;
        setHeaderView(viewBinding.getRoot());
        header.invoke(viewBinding);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(this) { // from class: com.catchpig.mvvm.base.adapter.RecyclerAdapter.onAttachedToRecyclerView.1
                final /* synthetic */ RecyclerAdapter<M, VB> this$0;

                @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
                /* renamed from: com.catchpig.mvvm.base.adapter.RecyclerAdapter$onAttachedToRecyclerView$1$WhenMappings */
                public /* synthetic */ class WhenMappings {
                    public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                    static {
                        int[] iArr = new int[ItemViewType.values().length];
                        try {
                            iArr[ItemViewType.TYPE_HEADER.ordinal()] = 1;
                        } catch (NoSuchFieldError unused) {
                        }
                        try {
                            iArr[ItemViewType.TYPE_EMPTY.ordinal()] = 2;
                        } catch (NoSuchFieldError unused2) {
                        }
                        try {
                            iArr[ItemViewType.TYPE_FOOTER.ordinal()] = 3;
                        } catch (NoSuchFieldError unused3) {
                        }
                        $EnumSwitchMapping$0 = iArr;
                    }
                }

                {
                    this.this$0 = this;
                }

                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int position) {
                    int i2 = WhenMappings.$EnumSwitchMapping$0[ItemViewType.INSTANCE.enumOfValue(this.this$0.getItemViewType(position)).ordinal()];
                    if (i2 == 1 || i2 == 2 || i2 == 3) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public final void set(@Nullable List<M> list) {
        getRecyclerView();
        this.firstLoad = false;
        if (list == null) {
            clear();
            return;
        }
        this.data.clear();
        this.data.addAll(list);
        notifyDataSetChanged();
    }

    public final void setEmptyDawableId(@DrawableRes int id) {
        this.emptyNoDataDrawableId = id;
    }

    public final void setEmptyDawableWidthH(int w2, int h2) {
        this.emptyNoDataDrawableW = w2;
        this.emptyNoDataDrawableH = h2;
    }

    public final void setEmptyMShow(boolean empty) {
        this.emptyMShow = empty;
    }

    public final void setEmptyTip(@NotNull String tip) {
        Intrinsics.checkNotNullParameter(tip, "tip");
        this.emptyTip = tip;
    }

    public final void setEmptyTipColor(@ColorRes int colorId) {
        this.emptyTipColorId = colorId;
    }

    public final void setEmptyType(int type) {
        this.emptyType = type;
    }

    public final void setEmptyView(@Nullable View view) {
        this.emptyView = view;
    }

    public final void setFooterView(@Nullable View view) {
        this.footerView = view;
    }

    public final void setHeaderView(@Nullable View view) {
        this.headerView = view;
    }

    public final void setOnItemClickListener(@NotNull OnItemClickListener<M> onItemClickListener) {
        Intrinsics.checkNotNullParameter(onItemClickListener, "onItemClickListener");
        this.onItemClickListener = onItemClickListener;
    }

    @NotNull
    public abstract VB viewBinding(@NotNull ViewGroup parent);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NotNull CommonViewHolder<VB> holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = position;
        int i2 = WhenMappings.$EnumSwitchMapping$0[ItemViewType.INSTANCE.enumOfValue(getItemViewType(position)).ordinal()];
        boolean z2 = true;
        if (i2 == 1 || i2 == 2) {
            return;
        }
        if (i2 != 3) {
            if (this.headerView != null) {
                intRef.element--;
            }
            if (intRef.element < this.data.size()) {
                final M m2 = this.data.get(intRef.element);
                holder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.catchpig.mvvm.base.adapter.a
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        RecyclerAdapter.onBindViewHolder$lambda$6(this.f6292c, m2, intRef, view);
                    }
                });
                bindViewHolder(holder, m2, position);
                return;
            }
            return;
        }
        if (this.firstLoad) {
            holder.itemView.setVisibility(4);
        } else {
            holder.itemView.setVisibility(0);
        }
        LinearLayout linearLayout = (LinearLayout) holder.itemView.findViewById(R.id.emptyType100);
        BLTextView bLTextView = (BLTextView) holder.itemView.findViewById(R.id.emptyType101);
        int i3 = this.emptyType;
        if (i3 != 100) {
            if (i3 != 101) {
                return;
            }
            linearLayout.setVisibility(4);
            bLTextView.setVisibility(0);
            String str = this.emptyTip;
            if (str != null && str.length() != 0) {
                z2 = false;
            }
            if (z2) {
                bLTextView.setVisibility(8);
                return;
            }
            bLTextView.setText(this.emptyTip);
            TextViewExtKt.setTextColorRes(bLTextView, this.emptyTipColorId);
            bLTextView.setVisibility(0);
            return;
        }
        linearLayout.setVisibility(0);
        bLTextView.setVisibility(4);
        TextView textView = (TextView) holder.itemView.findViewById(R.id.noDataTv);
        if (textView != null) {
            String str2 = this.emptyTip;
            if (str2 != null && str2.length() != 0) {
                z2 = false;
            }
            if (z2) {
                textView.setVisibility(8);
            } else {
                textView.setText(this.emptyTip);
                TextViewExtKt.setTextColorRes(textView, this.emptyTipColorId);
                textView.setVisibility(0);
            }
        }
        AppCompatImageView appCompatImageView = (AppCompatImageView) holder.itemView.findViewById(R.id.noDataImgv);
        if (appCompatImageView != null) {
            int i4 = this.emptyNoDataDrawableW;
            if (i4 > -1) {
                ViewExtKt.setWh(appCompatImageView, i4, this.emptyNoDataDrawableH);
            }
            int i5 = this.emptyNoDataDrawableId;
            if (-1 == i5) {
                appCompatImageView.setImageResource(R.drawable.common_no_data);
            } else {
                appCompatImageView.setImageResource(i5);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public CommonViewHolder<VB> onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view;
        Intrinsics.checkNotNullParameter(parent, "parent");
        int i2 = WhenMappings.$EnumSwitchMapping$0[ItemViewType.INSTANCE.enumOfValue(viewType).ordinal()];
        if (i2 == 1) {
            view = this.headerView;
            Intrinsics.checkNotNull(view);
        } else if (i2 == 2) {
            view = this.footerView;
            Intrinsics.checkNotNull(view);
        } else {
            if (i2 != 3) {
                return new CommonViewHolder<>(viewBinding(parent));
            }
            if (this.emptyView == null) {
                this.emptyView = KotlinMvvmCompiler.INSTANCE.globalConfig().getRecyclerEmptyBanding(parent).getRoot();
            }
            view = this.emptyView;
            Intrinsics.checkNotNull(view);
        }
        return new CommonViewHolder<>(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(@NotNull CommonViewHolder<VB> holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewAttachedToWindow((RecyclerAdapter<M, VB>) holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if ((layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) && holder.getLayoutPosition() == 0) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    public final void setOnItemClickListener(@NotNull final Function3<? super Integer, ? super M, ? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onItemClickListener = new OnItemClickListener<M>() { // from class: com.catchpig.mvvm.base.adapter.RecyclerAdapter.setOnItemClickListener.1
            @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter.OnItemClickListener
            public void itemClick(int id, M m2, int position) {
                listener.invoke(Integer.valueOf(id), m2, Integer.valueOf(position));
            }
        };
    }

    public final void setOnItemClickListener(@NotNull final Function1<? super M, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onItemClickListener = new OnItemClickListener<M>() { // from class: com.catchpig.mvvm.base.adapter.RecyclerAdapter.setOnItemClickListener.3
            @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter.OnItemClickListener
            public void itemClick(int id, M m2, int position) {
                listener.invoke(m2);
            }
        };
    }

    public final void setOnItemClickListener(@NotNull final Function2<? super M, ? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.onItemClickListener = new OnItemClickListener<M>() { // from class: com.catchpig.mvvm.base.adapter.RecyclerAdapter.setOnItemClickListener.5
            @Override // com.catchpig.mvvm.base.adapter.RecyclerAdapter.OnItemClickListener
            public void itemClick(int id, M m2, int position) {
                listener.invoke(m2, Integer.valueOf(position));
            }
        };
    }
}
