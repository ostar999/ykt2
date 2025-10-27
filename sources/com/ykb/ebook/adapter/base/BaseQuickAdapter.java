package com.ykb.ebook.adapter.base;

import android.animation.Animator;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.chad.library.adapter.base.animation.AlphaInAnimation;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.chad.library.adapter.base.animation.ScaleInAnimation;
import com.chad.library.adapter.base.animation.SlideInBottomAnimation;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.animation.SlideInRightAnimation;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.plv.socket.event.linkmic.PLVRemoveMicSiteEvent;
import com.umeng.analytics.pro.d;
import com.ykb.ebook.adapter.base.BaseQuickAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000ª\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0010\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\b&\u0018\u0000 \u008a\u0001*\u0004\b\u0000\u0010\u0001*\b\b\u0001\u0010\u0002*\u00020\u00032\b\u0012\u0004\u0012\u00020\u00030\u0004:\u000e\u0089\u0001\u008a\u0001\u008b\u0001\u008c\u0001\u008d\u0001\u008e\u0001\u008f\u0001B\u0015\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0002\u0010\u0007J\u0017\u0010=\u001a\u00020>2\b\b\u0001\u0010?\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010@J\u001f\u0010=\u001a\u00020>2\b\b\u0001\u0010A\u001a\u00020)2\u0006\u0010?\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010BJ \u0010C\u001a\u00020>2\b\b\u0001\u0010A\u001a\u00020)2\f\u0010D\u001a\b\u0012\u0004\u0012\u00028\u00000EH\u0016J\u0018\u0010C\u001a\u00020>2\u000e\b\u0001\u0010D\u001a\b\u0012\u0004\u0012\u00028\u00000EH\u0016J*\u0010F\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0001\u0010G\u001a\u00020)2\f\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000,J*\u0010I\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0001\u0010G\u001a\u00020)2\f\u0010H\u001a\b\u0012\u0004\u0012\u00028\u00000.J\u000e\u0010J\u001a\u00020>2\u0006\u0010H\u001a\u000207J\u001d\u0010K\u001a\u00020>2\u0006\u0010L\u001a\u00028\u00012\u0006\u0010M\u001a\u00020)H\u0014¢\u0006\u0002\u0010NJ\u0006\u0010O\u001a\u00020>J\u0016\u0010P\u001a\u00020\u00122\u000e\b\u0002\u0010Q\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006J\u0017\u0010R\u001a\u0004\u0018\u00018\u00002\b\b\u0001\u0010A\u001a\u00020)¢\u0006\u0002\u0010SJ\u0006\u0010T\u001a\u00020)J\u0016\u0010T\u001a\u00020)2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u0014J\u0010\u0010U\u001a\u00020V2\u0006\u0010A\u001a\u00020)H\u0016J\u0015\u0010W\u001a\u00020)2\b\u0010X\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010YJ\u000e\u0010Z\u001a\u00020)2\u0006\u0010A\u001a\u00020)J\u001e\u0010Z\u001a\u00020)2\u0006\u0010A\u001a\u00020)2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u0014J\u000e\u0010[\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000100J\u000e\u0010\\\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000102J\u0010\u0010]\u001a\u00020\u00122\u0006\u0010^\u001a\u00020)H\u0016J\u0010\u0010_\u001a\u00020>2\u0006\u00108\u001a\u00020\tH\u0017J'\u0010`\u001a\u00020>2\u0006\u0010a\u001a\u00028\u00012\u0006\u0010A\u001a\u00020)2\b\u0010X\u001a\u0004\u0018\u00018\u0000H$¢\u0006\u0002\u0010bJ5\u0010`\u001a\u00020>2\u0006\u0010a\u001a\u00028\u00012\u0006\u0010A\u001a\u00020)2\b\u0010X\u001a\u0004\u0018\u00018\u00002\f\u0010c\u001a\b\u0012\u0004\u0012\u00020d0\u0006H\u0014¢\u0006\u0002\u0010eJ\u0016\u0010`\u001a\u00020>2\u0006\u0010a\u001a\u00020\u00032\u0006\u0010A\u001a\u00020)J$\u0010`\u001a\u00020>2\u0006\u0010a\u001a\u00020\u00032\u0006\u0010A\u001a\u00020)2\f\u0010c\u001a\b\u0012\u0004\u0012\u00020d04J%\u0010f\u001a\u00028\u00012\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010g\u001a\u00020h2\u0006\u0010M\u001a\u00020)H$¢\u0006\u0002\u0010iJ\u0016\u0010f\u001a\u00020\u00032\u0006\u0010g\u001a\u00020h2\u0006\u0010M\u001a\u00020)J\u0010\u0010j\u001a\u00020>2\u0006\u00108\u001a\u00020\tH\u0017J\u0018\u0010k\u001a\u00020>2\u0006\u0010l\u001a\u00020\u001b2\u0006\u0010A\u001a\u00020)H\u0014J\u0018\u0010m\u001a\u00020\u00122\u0006\u0010l\u001a\u00020\u001b2\u0006\u0010A\u001a\u00020)H\u0014J\u0018\u0010n\u001a\u00020>2\u0006\u0010l\u001a\u00020\u001b2\u0006\u0010A\u001a\u00020)H\u0014J\u0018\u0010o\u001a\u00020\u00122\u0006\u0010l\u001a\u00020\u001b2\u0006\u0010A\u001a\u00020)H\u0014J\u0010\u0010p\u001a\u00020>2\u0006\u0010a\u001a\u00020\u0003H\u0017J\u0010\u0010q\u001a\u00020>2\u0006\u0010a\u001a\u00020\u0003H\u0017J\u0015\u0010r\u001a\u00020>2\u0006\u0010?\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010@J\u0012\u0010s\u001a\u00020>2\b\b\u0001\u0010A\u001a\u00020)H\u0016J\u001c\u0010t\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0001\u0010G\u001a\u00020)J\u001c\u0010u\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0001\u0010G\u001a\u00020)J\u000e\u0010v\u001a\u00020>2\u0006\u0010H\u001a\u000207J\u0010\u0010w\u001a\u00020>2\u0006\u0010a\u001a\u00020\u0003H\u0002J \u0010x\u001a\u00020>2\b\b\u0001\u0010A\u001a\u00020)2\u0006\u0010?\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010BJ\u0018\u0010y\u001a\u00020>2\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0001\u0010z\u001a\u00020)J\u000e\u0010{\u001a\u00020>2\u0006\u0010|\u001a\u00020}J\"\u0010~\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u000e\u0010H\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000100J\"\u0010\u007f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u000e\u0010H\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000102J\u001c\u0010\u0080\u0001\u001a\u00020>2\b\u0010\u0081\u0001\u001a\u00030\u0082\u00012\u0007\u0010\u0083\u0001\u001a\u00020)H\u0014J\u0019\u0010\u0084\u0001\u001a\u00020>2\u000e\u0010Q\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006H\u0016J\u001b\u0010\u0085\u0001\u001a\u00020>2\u0007\u0010\u0086\u0001\u001a\u00020)2\u0007\u0010\u0087\u0001\u001a\u00020)H\u0016J\r\u0010\u0088\u0001\u001a\u00020>*\u00020\u0003H\u0004R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R(\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR(\u0010\u001c\u001a\u0004\u0018\u00010\u001b2\b\u0010\n\u001a\u0004\u0018\u00010\u001b@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0014\"\u0004\b\"\u0010\u0016R$\u0010#\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0012@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0014\"\u0004\b$\u0010\u0016R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010\u0007R\u000e\u0010(\u001a\u00020)X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000,0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000.0+X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010/\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000100X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u00101\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000102X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u00103\u001a\b\u0012\u0004\u0012\u00028\u0000048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b5\u0010&R\u0016\u00106\u001a\n\u0012\u0004\u0012\u000207\u0018\u000104X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u00108\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b9\u0010:R\u0016\u0010;\u001a\u00020\u0012*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b;\u0010<¨\u0006\u0090\u0001"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", ExifInterface.GPS_DIRECTION_TRUE, "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "items", "", "(Ljava/util/List;)V", "_recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "value", "Lcom/chad/library/adapter/base/animation/BaseAnimation;", "adapterAnimation", "getAdapterAnimation", "()Lcom/chad/library/adapter/base/animation/BaseAnimation;", "setAdapterAnimation", "(Lcom/chad/library/adapter/base/animation/BaseAnimation;)V", "animationEnable", "", "getAnimationEnable", "()Z", "setAnimationEnable", "(Z)V", d.R, "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "Landroid/view/View;", "emptyView", "getEmptyView", "()Landroid/view/View;", "setEmptyView", "(Landroid/view/View;)V", "isAnimationFirstOnly", "setAnimationFirstOnly", "isEmptyViewEnable", "setEmptyViewEnable", "getItems", "()Ljava/util/List;", "setItems", "mLastPosition", "", "mOnItemChildClickArray", "Landroid/util/SparseArray;", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemChildClickListener;", "mOnItemChildLongClickArray", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemChildLongClickListener;", "mOnItemClickListener", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", "mOnItemLongClickListener", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemLongClickListener;", "mutableItems", "", "getMutableItems", "onViewAttachStateChangeListeners", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnViewAttachStateChangeListener;", "recyclerView", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "isEmptyViewHolder", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;)Z", "add", "", "data", "(Ljava/lang/Object;)V", "position", "(ILjava/lang/Object;)V", "addAll", "newCollection", "", "addOnItemChildClickListener", "id", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "addOnItemChildLongClickListener", "addOnViewAttachStateChangeListener", "bindViewClickListener", "viewHolder", "viewType", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V", "clearOnViewAttachStateChangeListener", "displayEmptyView", "list", "getItem", "(I)Ljava/lang/Object;", "getItemCount", "getItemId", "", "getItemPosition", "item", "(Ljava/lang/Object;)I", "getItemViewType", "getOnItemClickListener", "getOnItemLongClickListener", "isFullSpanItem", "itemType", "onAttachedToRecyclerView", "onBindViewHolder", "holder", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;ILjava/lang/Object;)V", "payloads", "", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;ILjava/lang/Object;Ljava/util/List;)V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "(Landroid/content/Context;Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "onDetachedFromRecyclerView", "onItemChildClick", "v", "onItemChildLongClick", "onItemClick", "onItemLongClick", "onViewAttachedToWindow", "onViewDetachedFromWindow", PLVRemoveMicSiteEvent.EVENT_NAME, "removeAt", "removeOnItemChildClickListener", "removeOnItemChildLongClickListener", "removeOnViewAttachStateChangeListener", "runAnimator", "set", "setEmptyViewLayout", "layoutResId", "setItemAnimation", "animationType", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$AnimationType;", "setOnItemClickListener", "setOnItemLongClickListener", "startAnim", "anim", "Landroid/animation/Animator;", "index", "submitList", "swap", "fromPosition", "toPosition", "asStaggeredGridFullSpan", "AnimationType", "Companion", "OnItemChildClickListener", "OnItemChildLongClickListener", "OnItemClickListener", "OnItemLongClickListener", "OnViewAttachStateChangeListener", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nBaseQuickAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BaseQuickAdapter.kt\ncom/ykb/ebook/adapter/base/BaseQuickAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,748:1\n1855#2,2:749\n1855#2,2:751\n350#2,7:753\n13579#3,2:760\n1#4:762\n*S KotlinDebug\n*F\n+ 1 BaseQuickAdapter.kt\ncom/ykb/ebook/adapter/base/BaseQuickAdapter\n*L\n266#1:749,2\n273#1:751,2\n420#1:753,7\n451#1:760,2\n*E\n"})
/* loaded from: classes6.dex */
public abstract class BaseQuickAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int EMPTY_PAYLOAD = 0;
    public static final int EMPTY_VIEW = 268436821;

    @Nullable
    private RecyclerView _recyclerView;

    @Nullable
    private BaseAnimation adapterAnimation;
    private boolean animationEnable;

    @Nullable
    private View emptyView;
    private boolean isAnimationFirstOnly;
    private boolean isEmptyViewEnable;

    @NotNull
    private List<? extends T> items;
    private int mLastPosition;

    @NotNull
    private final SparseArray<OnItemChildClickListener<T>> mOnItemChildClickArray;

    @NotNull
    private final SparseArray<OnItemChildLongClickListener<T>> mOnItemChildLongClickArray;

    @Nullable
    private OnItemClickListener<T> mOnItemClickListener;

    @Nullable
    private OnItemLongClickListener<T> mOnItemLongClickListener;

    @Nullable
    private List<OnViewAttachStateChangeListener> onViewAttachStateChangeListeners;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$AnimationType;", "", "(Ljava/lang/String;I)V", "AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum AnimationType {
        AlphaIn,
        ScaleIn,
        SlideInBottom,
        SlideInLeft,
        SlideInRight
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002J*\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\u0004\u0012\u00028\u0002\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemChildClickListener;", ExifInterface.GPS_DIRECTION_TRUE, "", "onItemClick", "", "adapter", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemChildClickListener<T> {
        void onItemClick(@NotNull BaseQuickAdapter<T, ?> adapter, @NotNull View view, int position);
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002J*\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\u0004\u0012\u00028\u0002\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemChildLongClickListener;", ExifInterface.GPS_DIRECTION_TRUE, "", "onItemLongClick", "", "adapter", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemChildLongClickListener<T> {
        boolean onItemLongClick(@NotNull BaseQuickAdapter<T, ?> adapter, @NotNull View view, int position);
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002J*\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\u0004\u0012\u00028\u0002\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemClickListener;", ExifInterface.GPS_DIRECTION_TRUE, "", "onClick", "", "adapter", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemClickListener<T> {
        void onClick(@NotNull BaseQuickAdapter<T, ?> adapter, @NotNull View view, int position);
    }

    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0002\u0010\u00012\u00020\u0002J*\u0010\u0003\u001a\u00020\u00042\u0010\u0010\u0005\u001a\f\u0012\u0004\u0012\u00028\u0002\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnItemLongClickListener;", ExifInterface.GPS_DIRECTION_TRUE, "", "onLongClick", "", "adapter", "Lcom/ykb/ebook/adapter/base/BaseQuickAdapter;", "view", "Landroid/view/View;", "position", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnItemLongClickListener<T> {
        boolean onLongClick(@NotNull BaseQuickAdapter<T, ?> adapter, @NotNull View view, int position);
    }

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/adapter/base/BaseQuickAdapter$OnViewAttachStateChangeListener;", "", "onViewAttachedToWindow", "", "holder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "onViewDetachedFromWindow", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnViewAttachStateChangeListener {
        void onViewAttachedToWindow(@NotNull RecyclerView.ViewHolder holder);

        void onViewDetachedFromWindow(@NotNull RecyclerView.ViewHolder holder);
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[AnimationType.values().length];
            try {
                iArr[AnimationType.AlphaIn.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[AnimationType.ScaleIn.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[AnimationType.SlideInBottom.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[AnimationType.SlideInLeft.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[AnimationType.SlideInRight.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BaseQuickAdapter() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ BaseQuickAdapter(List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? CollectionsKt__CollectionsKt.emptyList() : list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindViewClickListener$lambda$10$lambda$9(RecyclerView.ViewHolder viewHolder, BaseQuickAdapter this$0, View v2) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int layoutPosition = viewHolder.getLayoutPosition();
        if (layoutPosition == -1) {
            return;
        }
        Intrinsics.checkNotNullExpressionValue(v2, "v");
        this$0.onItemChildClick(v2, layoutPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean bindViewClickListener$lambda$12$lambda$11(RecyclerView.ViewHolder viewHolder, BaseQuickAdapter this$0, View v2) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int layoutPosition = viewHolder.getLayoutPosition();
        if (layoutPosition == -1) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue(v2, "v");
        return this$0.onItemChildLongClick(v2, layoutPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void bindViewClickListener$lambda$6$lambda$5(RecyclerView.ViewHolder viewHolder, BaseQuickAdapter this$0, View v2) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int layoutPosition = viewHolder.getLayoutPosition();
        if (layoutPosition == -1) {
            return;
        }
        Intrinsics.checkNotNullExpressionValue(v2, "v");
        this$0.onItemClick(v2, layoutPosition);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean bindViewClickListener$lambda$8$lambda$7(RecyclerView.ViewHolder viewHolder, BaseQuickAdapter this$0, View v2) {
        Intrinsics.checkNotNullParameter(viewHolder, "$viewHolder");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int layoutPosition = viewHolder.getLayoutPosition();
        if (layoutPosition == -1) {
            return false;
        }
        Intrinsics.checkNotNullExpressionValue(v2, "v");
        return this$0.onItemLongClick(v2, layoutPosition);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean displayEmptyView$default(BaseQuickAdapter baseQuickAdapter, List list, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: displayEmptyView");
        }
        if ((i2 & 1) != 0) {
            list = baseQuickAdapter.getItems();
        }
        return baseQuickAdapter.displayEmptyView(list);
    }

    private final List<T> getMutableItems() {
        List<T> items = getItems();
        if (items instanceof ArrayList) {
            List<T> items2 = getItems();
            Intrinsics.checkNotNull(items2, "null cannot be cast to non-null type java.util.ArrayList<T of com.ykb.ebook.adapter.base.BaseQuickAdapter>");
            return (ArrayList) items2;
        }
        if (TypeIntrinsics.isMutableList(items)) {
            List<T> items3 = getItems();
            Intrinsics.checkNotNull(items3, "null cannot be cast to non-null type kotlin.collections.MutableList<T of com.ykb.ebook.adapter.base.BaseQuickAdapter>");
            return TypeIntrinsics.asMutableList(items3);
        }
        List<T> mutableList = CollectionsKt___CollectionsKt.toMutableList((Collection) getItems());
        setItems(mutableList);
        return mutableList;
    }

    private final void runAnimator(RecyclerView.ViewHolder holder) {
        if (this.animationEnable) {
            if (!this.isAnimationFirstOnly || holder.getLayoutPosition() > this.mLastPosition) {
                BaseAnimation alphaInAnimation = this.adapterAnimation;
                if (alphaInAnimation == null) {
                    alphaInAnimation = new AlphaInAnimation(0.0f, 1, null);
                }
                View view = holder.itemView;
                Intrinsics.checkNotNullExpressionValue(view, "holder.itemView");
                for (Animator animator : alphaInAnimation.animators(view)) {
                    startAnim(animator, holder.getLayoutPosition());
                }
                this.mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    public void add(@IntRange(from = 0) int position, T data) {
        if (position <= getItems().size() && position >= 0) {
            if (displayEmptyView$default(this, null, 1, null)) {
                notifyItemRemoved(0);
            }
            getMutableItems().add(position, data);
            notifyItemInserted(position);
            return;
        }
        throw new IndexOutOfBoundsException("position: " + position + ". size:" + getItems().size());
    }

    public void addAll(@IntRange(from = 0) int position, @NotNull Collection<? extends T> newCollection) {
        Intrinsics.checkNotNullParameter(newCollection, "newCollection");
        if (position <= getItems().size() && position >= 0) {
            if (displayEmptyView$default(this, null, 1, null)) {
                notifyItemRemoved(0);
            }
            getMutableItems().addAll(position, newCollection);
            notifyItemRangeInserted(position, newCollection.size());
            return;
        }
        throw new IndexOutOfBoundsException("position: " + position + ". size:" + getItems().size());
    }

    @NotNull
    public final BaseQuickAdapter<T, VH> addOnItemChildClickListener(@IdRes int id, @NotNull OnItemChildClickListener<T> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnItemChildClickArray.put(id, listener);
        return this;
    }

    @NotNull
    public final BaseQuickAdapter<T, VH> addOnItemChildLongClickListener(@IdRes int id, @NotNull OnItemChildLongClickListener<T> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mOnItemChildLongClickArray.put(id, listener);
        return this;
    }

    public final void addOnViewAttachStateChangeListener(@NotNull OnViewAttachStateChangeListener listener) {
        List<OnViewAttachStateChangeListener> list;
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (this.onViewAttachStateChangeListeners == null) {
            this.onViewAttachStateChangeListeners = new ArrayList();
        }
        List<OnViewAttachStateChangeListener> list2 = this.onViewAttachStateChangeListeners;
        boolean z2 = false;
        if (list2 != null && list2.contains(listener)) {
            z2 = true;
        }
        if (z2 || (list = this.onViewAttachStateChangeListeners) == null) {
            return;
        }
        list.add(listener);
    }

    public final void asStaggeredGridFullSpan(@NotNull RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(viewHolder, "<this>");
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
        }
    }

    public void bindViewClickListener(@NotNull final VH viewHolder, int viewType) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        if (this.mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: q1.a
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    BaseQuickAdapter.bindViewClickListener$lambda$6$lambda$5(viewHolder, this, view);
                }
            });
        }
        if (this.mOnItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: q1.b
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return BaseQuickAdapter.bindViewClickListener$lambda$8$lambda$7(viewHolder, this, view);
                }
            });
        }
        int size = this.mOnItemChildClickArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            View viewFindViewById = viewHolder.itemView.findViewById(this.mOnItemChildClickArray.keyAt(i2));
            if (viewFindViewById != null) {
                viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: q1.c
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BaseQuickAdapter.bindViewClickListener$lambda$10$lambda$9(viewHolder, this, view);
                    }
                });
            }
        }
        int size2 = this.mOnItemChildLongClickArray.size();
        for (int i3 = 0; i3 < size2; i3++) {
            View viewFindViewById2 = viewHolder.itemView.findViewById(this.mOnItemChildLongClickArray.keyAt(i3));
            if (viewFindViewById2 != null) {
                viewFindViewById2.setOnLongClickListener(new View.OnLongClickListener() { // from class: q1.d
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view) {
                        return BaseQuickAdapter.bindViewClickListener$lambda$12$lambda$11(viewHolder, this, view);
                    }
                });
            }
        }
    }

    public final void clearOnViewAttachStateChangeListener() {
        List<OnViewAttachStateChangeListener> list = this.onViewAttachStateChangeListeners;
        if (list != null) {
            list.clear();
        }
    }

    public final boolean displayEmptyView(@NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        if (this.emptyView == null || !this.isEmptyViewEnable) {
            return false;
        }
        return list.isEmpty();
    }

    @Nullable
    public final BaseAnimation getAdapterAnimation() {
        return this.adapterAnimation;
    }

    public final boolean getAnimationEnable() {
        return this.animationEnable;
    }

    @NotNull
    public final Context getContext() {
        Context context = getRecyclerView().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "recyclerView.context");
        return context;
    }

    @Nullable
    public final View getEmptyView() {
        return this.emptyView;
    }

    @Nullable
    public final T getItem(@IntRange(from = 0) int position) {
        return (T) CollectionsKt___CollectionsKt.getOrNull(getItems(), position);
    }

    public int getItemCount(@NotNull List<? extends T> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        return items.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int position) {
        return position;
    }

    public final int getItemPosition(@Nullable T item) {
        Iterator<T> it = getItems().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            if (Intrinsics.areEqual(item, it.next())) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int position) {
        if (displayEmptyView$default(this, null, 1, null)) {
            return 268436821;
        }
        return getItemViewType(position, getItems());
    }

    public int getItemViewType(int position, @NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        return 0;
    }

    @NotNull
    public List<T> getItems() {
        return this.items;
    }

    @Nullable
    public final OnItemClickListener<T> getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    @Nullable
    public final OnItemLongClickListener<T> getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    @NotNull
    public final RecyclerView getRecyclerView() {
        RecyclerView recyclerView = this._recyclerView;
        if (recyclerView == null) {
            throw new IllegalStateException("Please get it after onAttachedToRecyclerView()".toString());
        }
        Intrinsics.checkNotNull(recyclerView);
        return recyclerView;
    }

    /* renamed from: isAnimationFirstOnly, reason: from getter */
    public final boolean getIsAnimationFirstOnly() {
        return this.isAnimationFirstOnly;
    }

    /* renamed from: isEmptyViewEnable, reason: from getter */
    public final boolean getIsEmptyViewEnable() {
        return this.isEmptyViewEnable;
    }

    public final boolean isEmptyViewHolder(@NotNull RecyclerView.ViewHolder viewHolder) {
        Intrinsics.checkNotNullParameter(viewHolder, "<this>");
        return viewHolder instanceof EmptyLayoutVH;
    }

    public boolean isFullSpanItem(int itemType) {
        return itemType == 268436821;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onAttachedToRecyclerView(recyclerView);
        this._recyclerView = recyclerView;
    }

    public abstract void onBindViewHolder(@NotNull VH holder, int position, @Nullable T item);

    public void onBindViewHolder(@NotNull VH holder, int position, @Nullable T item, @NotNull List<? extends Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        onBindViewHolder((BaseQuickAdapter<T, VH>) holder, position, (int) item);
    }

    @NotNull
    public abstract VH onCreateViewHolder(@NotNull Context context, @NotNull ViewGroup parent, int viewType);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public final RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        if (viewType == 268436821) {
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            return new EmptyLayoutVH(frameLayout);
        }
        Context context = parent.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "parent.context");
        RecyclerView.ViewHolder viewHolderOnCreateViewHolder = onCreateViewHolder(context, parent, viewType);
        bindViewClickListener(viewHolderOnCreateViewHolder, viewType);
        return viewHolderOnCreateViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onDetachedFromRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        super.onDetachedFromRecyclerView(recyclerView);
        this._recyclerView = null;
    }

    public void onItemChildClick(@NotNull View v2, int position) {
        Intrinsics.checkNotNullParameter(v2, "v");
        OnItemChildClickListener<T> onItemChildClickListener = this.mOnItemChildClickArray.get(v2.getId());
        if (onItemChildClickListener != null) {
            onItemChildClickListener.onItemClick(this, v2, position);
        }
    }

    public boolean onItemChildLongClick(@NotNull View v2, int position) {
        Intrinsics.checkNotNullParameter(v2, "v");
        OnItemChildLongClickListener<T> onItemChildLongClickListener = this.mOnItemChildLongClickArray.get(v2.getId());
        if (onItemChildLongClickListener != null) {
            return onItemChildLongClickListener.onItemLongClick(this, v2, position);
        }
        return false;
    }

    public void onItemClick(@NotNull View v2, int position) {
        Intrinsics.checkNotNullParameter(v2, "v");
        OnItemClickListener<T> onItemClickListener = this.mOnItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onClick(this, v2, position);
        }
    }

    public boolean onItemLongClick(@NotNull View v2, int position) {
        Intrinsics.checkNotNullParameter(v2, "v");
        OnItemLongClickListener<T> onItemLongClickListener = this.mOnItemLongClickListener;
        if (onItemLongClickListener != null) {
            return onItemLongClickListener.onLongClick(this, v2, position);
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewAttachedToWindow(@NotNull RecyclerView.ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onViewAttachedToWindow(holder);
        if (isFullSpanItem(getItemViewType(holder.getLayoutPosition()))) {
            asStaggeredGridFullSpan(holder);
        } else {
            runAnimator(holder);
        }
        List<OnViewAttachStateChangeListener> list = this.onViewAttachStateChangeListeners;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ((OnViewAttachStateChangeListener) it.next()).onViewAttachedToWindow(holder);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewDetachedFromWindow(@NotNull RecyclerView.ViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        List<OnViewAttachStateChangeListener> list = this.onViewAttachStateChangeListeners;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ((OnViewAttachStateChangeListener) it.next()).onViewDetachedFromWindow(holder);
            }
        }
    }

    public void remove(T data) {
        int iIndexOf = getItems().indexOf(data);
        if (iIndexOf == -1) {
            return;
        }
        removeAt(iIndexOf);
    }

    public void removeAt(@IntRange(from = 0) int position) {
        if (position < getItems().size()) {
            getMutableItems().remove(position);
            notifyItemRemoved(position);
            if (displayEmptyView$default(this, null, 1, null)) {
                notifyItemInserted(0);
                return;
            }
            return;
        }
        throw new IndexOutOfBoundsException("position: " + position + ". size:" + getItems().size());
    }

    @NotNull
    public final BaseQuickAdapter<T, VH> removeOnItemChildClickListener(@IdRes int id) {
        this.mOnItemChildClickArray.remove(id);
        return this;
    }

    @NotNull
    public final BaseQuickAdapter<T, VH> removeOnItemChildLongClickListener(@IdRes int id) {
        this.mOnItemChildLongClickArray.remove(id);
        return this;
    }

    public final void removeOnViewAttachStateChangeListener(@NotNull OnViewAttachStateChangeListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        List<OnViewAttachStateChangeListener> list = this.onViewAttachStateChangeListeners;
        if (list != null) {
            list.remove(listener);
        }
    }

    public void set(@IntRange(from = 0) int position, T data) {
        if (position < getItems().size()) {
            getMutableItems().set(position, data);
            notifyItemChanged(position);
            return;
        }
        throw new IndexOutOfBoundsException("position: " + position + ". size:" + getItems().size());
    }

    public final void setAdapterAnimation(@Nullable BaseAnimation baseAnimation) {
        this.animationEnable = true;
        this.adapterAnimation = baseAnimation;
    }

    public final void setAnimationEnable(boolean z2) {
        this.animationEnable = z2;
    }

    public final void setAnimationFirstOnly(boolean z2) {
        this.isAnimationFirstOnly = z2;
    }

    public final void setEmptyView(@Nullable View view) {
        boolean zDisplayEmptyView$default = displayEmptyView$default(this, null, 1, null);
        this.emptyView = view;
        boolean zDisplayEmptyView$default2 = displayEmptyView$default(this, null, 1, null);
        if (zDisplayEmptyView$default && !zDisplayEmptyView$default2) {
            notifyItemRemoved(0);
            return;
        }
        if (zDisplayEmptyView$default2 && !zDisplayEmptyView$default) {
            notifyItemInserted(0);
        } else if (zDisplayEmptyView$default && zDisplayEmptyView$default2) {
            notifyItemChanged(0, 0);
        }
    }

    public final void setEmptyViewEnable(boolean z2) {
        boolean zDisplayEmptyView$default = displayEmptyView$default(this, null, 1, null);
        this.isEmptyViewEnable = z2;
        boolean zDisplayEmptyView$default2 = displayEmptyView$default(this, null, 1, null);
        if (zDisplayEmptyView$default && !zDisplayEmptyView$default2) {
            notifyItemRemoved(0);
            return;
        }
        if (zDisplayEmptyView$default2 && !zDisplayEmptyView$default) {
            notifyItemInserted(0);
        } else if (zDisplayEmptyView$default && zDisplayEmptyView$default2) {
            notifyItemChanged(0, 0);
        }
    }

    public final void setEmptyViewLayout(@NotNull Context context, @LayoutRes int layoutResId) {
        Intrinsics.checkNotNullParameter(context, "context");
        setEmptyView(LayoutInflater.from(context).inflate(layoutResId, (ViewGroup) new FrameLayout(context), false));
    }

    public final void setItemAnimation(@NotNull AnimationType animationType) {
        BaseAnimation alphaInAnimation;
        Intrinsics.checkNotNullParameter(animationType, "animationType");
        int i2 = WhenMappings.$EnumSwitchMapping$0[animationType.ordinal()];
        if (i2 == 1) {
            alphaInAnimation = new AlphaInAnimation(0.0f, 1, null);
        } else if (i2 == 2) {
            alphaInAnimation = new ScaleInAnimation(0.0f, 1, null);
        } else if (i2 == 3) {
            alphaInAnimation = new SlideInBottomAnimation();
        } else if (i2 == 4) {
            alphaInAnimation = new SlideInLeftAnimation();
        } else {
            if (i2 != 5) {
                throw new NoWhenBranchMatchedException();
            }
            alphaInAnimation = new SlideInRightAnimation();
        }
        setAdapterAnimation(alphaInAnimation);
    }

    public void setItems(@NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.items = list;
    }

    @NotNull
    public final BaseQuickAdapter<T, VH> setOnItemClickListener(@Nullable OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
        return this;
    }

    @NotNull
    public final BaseQuickAdapter<T, VH> setOnItemLongClickListener(@Nullable OnItemLongClickListener<T> listener) {
        this.mOnItemLongClickListener = listener;
        return this;
    }

    public void startAnim(@NotNull Animator anim, int index) {
        Intrinsics.checkNotNullParameter(anim, "anim");
        anim.start();
    }

    public void submitList(@Nullable List<? extends T> list) {
        if (list == getItems()) {
            return;
        }
        this.mLastPosition = -1;
        if (list == null) {
            list = CollectionsKt__CollectionsKt.emptyList();
        }
        boolean zDisplayEmptyView$default = displayEmptyView$default(this, null, 1, null);
        boolean zDisplayEmptyView = displayEmptyView(list);
        if (zDisplayEmptyView$default && !zDisplayEmptyView) {
            setItems(list);
            notifyItemRemoved(0);
            notifyItemRangeInserted(0, list.size());
        } else if (zDisplayEmptyView && !zDisplayEmptyView$default) {
            notifyItemRangeRemoved(0, getItems().size());
            setItems(list);
            notifyItemInserted(0);
        } else if (zDisplayEmptyView$default && zDisplayEmptyView) {
            setItems(list);
            notifyItemChanged(0, 0);
        } else {
            setItems(list);
            notifyDataSetChanged();
        }
    }

    public void swap(int fromPosition, int toPosition) {
        int size = getItems().size();
        if (fromPosition >= 0 && fromPosition < size) {
            if (toPosition >= 0 && toPosition < size) {
                Collections.swap(getItems(), fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        if (displayEmptyView$default(this, null, 1, null)) {
            return 1;
        }
        return getItemCount(getItems());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        if (holder instanceof EmptyLayoutVH) {
            ((EmptyLayoutVH) holder).changeEmptyView(this.emptyView);
        } else {
            onBindViewHolder((BaseQuickAdapter<T, VH>) holder, position, (int) getItem(position));
        }
    }

    public BaseQuickAdapter(@NotNull List<? extends T> items) {
        Intrinsics.checkNotNullParameter(items, "items");
        this.items = items;
        this.mLastPosition = -1;
        this.mOnItemChildClickArray = new SparseArray<>(3);
        this.mOnItemChildLongClickArray = new SparseArray<>(3);
        this.isAnimationFirstOnly = true;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position, @NotNull List<Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        if (holder instanceof EmptyLayoutVH) {
            ((EmptyLayoutVH) holder).changeEmptyView(this.emptyView);
        } else if (payloads.isEmpty()) {
            onBindViewHolder((BaseQuickAdapter<T, VH>) holder, position, (int) getItem(position));
        } else {
            onBindViewHolder(holder, position, getItem(position), payloads);
        }
    }

    public void add(@NonNull T data) {
        if (displayEmptyView$default(this, null, 1, null)) {
            notifyItemRemoved(0);
        }
        getMutableItems().add(data);
        notifyItemInserted(getItems().size() - 1);
    }

    public void addAll(@NonNull @NotNull Collection<? extends T> newCollection) {
        Intrinsics.checkNotNullParameter(newCollection, "newCollection");
        if (displayEmptyView$default(this, null, 1, null)) {
            notifyItemRemoved(0);
        }
        int size = getItems().size();
        getMutableItems().addAll(newCollection);
        notifyItemRangeInserted(size, newCollection.size());
    }
}
