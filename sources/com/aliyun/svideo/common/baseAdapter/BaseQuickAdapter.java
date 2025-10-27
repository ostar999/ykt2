package com.aliyun.svideo.common.baseAdapter;

import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.IdRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.aliyun.svideo.common.baseAdapter.BaseViewHolder;
import com.aliyun.svideo.common.baseAdapter.animation.AlphaInAnimation;
import com.aliyun.svideo.common.baseAdapter.delegate.MultiTypeDelegate;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseQuickAdapter<T, K extends BaseViewHolder> extends RecyclerView.Adapter<K> {
    public static final int EMPTY_VIEW = 1365;
    public static final int FOOTER_VIEW = 819;
    public static final int HEADER_VIEW = 273;
    public static final int LOADING_VIEW = 546;
    protected static final String TAG = "BaseQuickAdapter";
    private boolean footerViewAsFlow;
    private boolean headerViewAsFlow;
    protected Context mContext;
    private AlphaInAnimation mCustomAnimation;
    protected List<T> mData;
    private int mDuration;
    private FrameLayout mEmptyLayout;
    private boolean mEnableLoadMoreEndClick;
    private boolean mFirstOnlyEnable;
    private boolean mFootAndEmptyEnable;
    private LinearLayout mFooterLayout;
    private boolean mHeadAndEmptyEnable;
    private LinearLayout mHeaderLayout;
    private Interpolator mInterpolator;
    private boolean mIsUseEmpty;
    private int mLastPosition;
    protected LayoutInflater mLayoutInflater;
    protected int mLayoutResId;
    private boolean mLoadMoreEnable;
    private LoadMoreView mLoadMoreView;
    private boolean mLoading;
    private MultiTypeDelegate<T> mMultiTypeDelegate;
    private boolean mNextLoadEnable;
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemChildLongClickListener mOnItemChildLongClickListener;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private boolean mOpenAnimationEnable;
    private int mPreLoadNumber;
    private RecyclerView mRecyclerView;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private SpanSizeLookup mSpanSizeLookup;
    private int mStartUpFetchPosition;
    private boolean mUpFetchEnable;
    private UpFetchListener mUpFetchListener;
    private boolean mUpFetching;

    public interface OnItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface OnItemClickListener {
        void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i2);
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int i2);
    }

    public interface UpFetchListener {
        void onUpFetch();
    }

    public BaseQuickAdapter(@LayoutRes int i2, @Nullable List<T> list) {
        this.mNextLoadEnable = false;
        this.mLoadMoreEnable = false;
        this.mLoading = false;
        this.mLoadMoreView = new SimpleLoadMoreView();
        this.mEnableLoadMoreEndClick = false;
        this.mFirstOnlyEnable = true;
        this.mOpenAnimationEnable = false;
        this.mInterpolator = new LinearInterpolator();
        this.mDuration = 300;
        this.mLastPosition = -1;
        this.mIsUseEmpty = true;
        this.mPreLoadNumber = 1;
        this.mStartUpFetchPosition = 1;
        this.mData = list == null ? new ArrayList<>() : list;
        if (i2 != 0) {
            this.mLayoutResId = i2;
        }
    }

    private void addAnimation(RecyclerView.ViewHolder viewHolder) {
        if (this.mOpenAnimationEnable) {
            if (!this.mFirstOnlyEnable || viewHolder.getLayoutPosition() > this.mLastPosition) {
                AlphaInAnimation alphaInAnimation = new AlphaInAnimation();
                AlphaInAnimation alphaInAnimation2 = this.mCustomAnimation;
                if (alphaInAnimation2 != null) {
                    alphaInAnimation = alphaInAnimation2;
                }
                for (Animator animator : alphaInAnimation.getAnimators(viewHolder.itemView)) {
                    startAnim(animator, viewHolder.getLayoutPosition());
                }
                this.mLastPosition = viewHolder.getLayoutPosition();
            }
        }
    }

    private void autoLoadMore(int i2) {
        if (getLoadMoreViewCount() != 0 && i2 >= getItemCount() - this.mPreLoadNumber && this.mLoadMoreView.getLoadMoreStatus() == 1) {
            this.mLoadMoreView.setLoadMoreStatus(2);
            if (this.mLoading) {
                return;
            }
            this.mLoading = true;
            if (getRecyclerView() != null) {
                getRecyclerView().post(new Runnable() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.7
                    @Override // java.lang.Runnable
                    public void run() {
                        BaseQuickAdapter.this.mRequestLoadMoreListener.onLoadMoreRequested();
                    }
                });
            } else {
                this.mRequestLoadMoreListener.onLoadMoreRequested();
            }
        }
    }

    private void autoUpFetch(int i2) {
        UpFetchListener upFetchListener;
        if (!isUpFetchEnable() || isUpFetching() || i2 > this.mStartUpFetchPosition || (upFetchListener = this.mUpFetchListener) == null) {
            return;
        }
        upFetchListener.onUpFetch();
    }

    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        View view;
        if (baseViewHolder == null || (view = baseViewHolder.itemView) == null) {
            return;
        }
        if (getOnItemClickListener() != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.5
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    BaseQuickAdapter.this.setOnItemClick(view2, baseViewHolder.getLayoutPosition() - BaseQuickAdapter.this.getHeaderLayoutCount());
                }
            });
        }
        if (getOnItemLongClickListener() != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.6
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view2) {
                    return BaseQuickAdapter.this.setOnItemLongClick(view2, baseViewHolder.getLayoutPosition() - BaseQuickAdapter.this.getHeaderLayoutCount());
                }
            });
        }
    }

    private void checkNotNull() {
        if (getRecyclerView() == null) {
            throw new IllegalStateException("please bind recyclerView first!");
        }
    }

    private void compatibilityDataSizeChanged(int i2) {
        List<T> list = this.mData;
        if ((list == null ? 0 : list.size()) == i2) {
            notifyDataSetChanged();
        }
    }

    private K createGenericKInstance(Class cls, View view) throws NoSuchMethodException, SecurityException {
        try {
            if (!cls.isMemberClass() || Modifier.isStatic(cls.getModifiers())) {
                Constructor<T> declaredConstructor = cls.getDeclaredConstructor(View.class);
                declaredConstructor.setAccessible(true);
                return (K) declaredConstructor.newInstance(view);
            }
            Constructor<T> declaredConstructor2 = cls.getDeclaredConstructor(getClass(), View.class);
            declaredConstructor2.setAccessible(true);
            return (K) declaredConstructor2.newInstance(this, view);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    private int getFooterViewPosition() {
        int i2 = 1;
        if (getEmptyViewCount() != 1) {
            return getHeaderLayoutCount() + this.mData.size();
        }
        if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
            i2 = 2;
        }
        if (this.mFootAndEmptyEnable) {
            return i2;
        }
        return -1;
    }

    private int getHeaderViewPosition() {
        return (getEmptyViewCount() != 1 || this.mHeadAndEmptyEnable) ? 0 : -1;
    }

    private Class getInstancedGenericKClass(Class cls) {
        Type genericSuperclass = cls.getGenericSuperclass();
        if (!(genericSuperclass instanceof ParameterizedType)) {
            return null;
        }
        for (Type type : ((ParameterizedType) genericSuperclass).getActualTypeArguments()) {
            if (type instanceof Class) {
                Class cls2 = (Class) type;
                if (BaseViewHolder.class.isAssignableFrom(cls2)) {
                    return cls2;
                }
            } else if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType instanceof Class) {
                    Class cls3 = (Class) rawType;
                    if (BaseViewHolder.class.isAssignableFrom(cls3)) {
                        return cls3;
                    }
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return null;
    }

    private int getItemPosition(T t2) {
        List<T> list;
        if (t2 == null || (list = this.mData) == null || list.isEmpty()) {
            return -1;
        }
        return this.mData.indexOf(t2);
    }

    private K getLoadingView(ViewGroup viewGroup) {
        K k2 = (K) createBaseViewHolder(getItemView(this.mLoadMoreView.getLayoutId(), viewGroup));
        k2.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 3) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
                if (BaseQuickAdapter.this.mEnableLoadMoreEndClick && BaseQuickAdapter.this.mLoadMoreView.getLoadMoreStatus() == 4) {
                    BaseQuickAdapter.this.notifyLoadMoreToLoading();
                }
            }
        });
        return k2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTheBiggestNumber(int[] iArr) {
        int i2 = -1;
        if (iArr != null && iArr.length != 0) {
            for (int i3 : iArr) {
                if (i3 > i2) {
                    i2 = i3;
                }
            }
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFullScreen(LinearLayoutManager linearLayoutManager) {
        return (linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 == getItemCount() && linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) ? false : true;
    }

    private void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        this.mNextLoadEnable = true;
        this.mLoadMoreEnable = true;
        this.mLoading = false;
    }

    private void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    @Deprecated
    public void add(@IntRange(from = 0) int i2, @NonNull T t2) {
        addData(i2, (int) t2);
    }

    public void addData(@IntRange(from = 0) int i2, @NonNull T t2) {
        this.mData.add(i2, t2);
        notifyItemInserted(i2 + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public int addFooterView(View view) {
        return addFooterView(view, -1, 1);
    }

    public int addHeaderView(View view) {
        return addHeaderView(view, -1);
    }

    public void bindToRecyclerView(RecyclerView recyclerView) {
        if (getRecyclerView() != null) {
            throw new IllegalStateException("Don't bind twice");
        }
        setRecyclerView(recyclerView);
        getRecyclerView().setAdapter(this);
    }

    public void closeLoadAnimation() {
        this.mOpenAnimationEnable = false;
    }

    public abstract void convert(K k2, T t2);

    public K createBaseViewHolder(ViewGroup viewGroup, int i2) {
        return (K) createBaseViewHolder(getItemView(i2, viewGroup));
    }

    public void disableLoadMoreIfNotFullPage() {
        checkNotNull();
        disableLoadMoreIfNotFullPage(getRecyclerView());
    }

    public void enableLoadMoreEndClick(boolean z2) {
        this.mEnableLoadMoreEndClick = z2;
    }

    @NonNull
    public List<T> getData() {
        return this.mData;
    }

    public int getDefItemViewType(int i2) {
        MultiTypeDelegate<T> multiTypeDelegate = this.mMultiTypeDelegate;
        return multiTypeDelegate != null ? multiTypeDelegate.getDefItemViewType(this.mData, i2) : super.getItemViewType(i2);
    }

    public View getEmptyView() {
        return this.mEmptyLayout;
    }

    public int getEmptyViewCount() {
        FrameLayout frameLayout = this.mEmptyLayout;
        return (frameLayout == null || frameLayout.getChildCount() == 0 || !this.mIsUseEmpty || this.mData.size() != 0) ? 0 : 1;
    }

    public LinearLayout getFooterLayout() {
        return this.mFooterLayout;
    }

    public int getFooterLayoutCount() {
        LinearLayout linearLayout = this.mFooterLayout;
        return (linearLayout == null || linearLayout.getChildCount() == 0) ? 0 : 1;
    }

    @Deprecated
    public int getFooterViewsCount() {
        return getFooterLayoutCount();
    }

    public LinearLayout getHeaderLayout() {
        return this.mHeaderLayout;
    }

    public int getHeaderLayoutCount() {
        LinearLayout linearLayout = this.mHeaderLayout;
        return (linearLayout == null || linearLayout.getChildCount() == 0) ? 0 : 1;
    }

    @Deprecated
    public int getHeaderViewsCount() {
        return getHeaderLayoutCount();
    }

    @Nullable
    public T getItem(@IntRange(from = 0) int i2) {
        if (i2 < 0 || i2 >= this.mData.size()) {
            return null;
        }
        return this.mData.get(i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int i2 = 1;
        if (1 != getEmptyViewCount()) {
            return getLoadMoreViewCount() + getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
        }
        if (this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
            i2 = 2;
        }
        return (!this.mFootAndEmptyEnable || getFooterLayoutCount() == 0) ? i2 : i2 + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    public View getItemView(@LayoutRes int i2, ViewGroup viewGroup) {
        return this.mLayoutInflater.inflate(i2, viewGroup, false);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i2) {
        if (getEmptyViewCount() == 1) {
            boolean z2 = this.mHeadAndEmptyEnable && getHeaderLayoutCount() != 0;
            return i2 != 0 ? i2 != 1 ? i2 != 2 ? 1365 : 819 : z2 ? 1365 : 819 : z2 ? 273 : 1365;
        }
        int headerLayoutCount = getHeaderLayoutCount();
        if (i2 < headerLayoutCount) {
            return 273;
        }
        int i3 = i2 - headerLayoutCount;
        int size = this.mData.size();
        return i3 < size ? getDefItemViewType(i3) : i3 - size < getFooterLayoutCount() ? 819 : 546;
    }

    public int getLoadMoreViewCount() {
        if (this.mRequestLoadMoreListener == null || !this.mLoadMoreEnable) {
            return 0;
        }
        return ((this.mNextLoadEnable || !this.mLoadMoreView.isLoadEndMoreGone()) && this.mData.size() != 0) ? 1 : 0;
    }

    public int getLoadMoreViewPosition() {
        return getHeaderLayoutCount() + this.mData.size() + getFooterLayoutCount();
    }

    public MultiTypeDelegate<T> getMultiTypeDelegate() {
        return this.mMultiTypeDelegate;
    }

    @Nullable
    public final OnItemChildClickListener getOnItemChildClickListener() {
        return this.mOnItemChildClickListener;
    }

    @Nullable
    public final OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return this.mOnItemChildLongClickListener;
    }

    public final OnItemClickListener getOnItemClickListener() {
        return this.mOnItemClickListener;
    }

    public final OnItemLongClickListener getOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    @Nullable
    public View getViewByPosition(int i2, @IdRes int i3) {
        checkNotNull();
        return getViewByPosition(getRecyclerView(), i2, i3);
    }

    public void isFirstOnly(boolean z2) {
        this.mFirstOnlyEnable = z2;
    }

    public boolean isFixedViewType(int i2) {
        return i2 == 1365 || i2 == 273 || i2 == 819 || i2 == 546;
    }

    public boolean isFooterViewAsFlow() {
        return this.footerViewAsFlow;
    }

    public boolean isHeaderViewAsFlow() {
        return this.headerViewAsFlow;
    }

    public boolean isLoadMoreEnable() {
        return this.mLoadMoreEnable;
    }

    public boolean isLoading() {
        return this.mLoading;
    }

    public boolean isUpFetchEnable() {
        return this.mUpFetchEnable;
    }

    public boolean isUpFetching() {
        return this.mUpFetching;
    }

    public void isUseEmpty(boolean z2) {
        this.mIsUseEmpty = z2;
    }

    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mNextLoadEnable = true;
        this.mLoadMoreView.setLoadMoreStatus(1);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mLoadMoreView.setLoadMoreStatus(3);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    public void notifyLoadMoreToLoading() {
        if (this.mLoadMoreView.getLoadMoreStatus() == 2) {
            return;
        }
        this.mLoadMoreView.setLoadMoreStatus(1);
        notifyItemChanged(getLoadMoreViewPosition());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.4
                @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                public int getSpanSize(int i2) {
                    int itemViewType = BaseQuickAdapter.this.getItemViewType(i2);
                    if (itemViewType == 273 && BaseQuickAdapter.this.isHeaderViewAsFlow()) {
                        return 1;
                    }
                    if (itemViewType == 819 && BaseQuickAdapter.this.isFooterViewAsFlow()) {
                        return 1;
                    }
                    if (BaseQuickAdapter.this.mSpanSizeLookup != null) {
                        return BaseQuickAdapter.this.isFixedViewType(itemViewType) ? gridLayoutManager.getSpanCount() : BaseQuickAdapter.this.mSpanSizeLookup.getSpanSize(gridLayoutManager, i2 - BaseQuickAdapter.this.getHeaderLayoutCount());
                    }
                    if (BaseQuickAdapter.this.isFixedViewType(itemViewType)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public K onCreateDefViewHolder(ViewGroup viewGroup, int i2) {
        int layoutId = this.mLayoutResId;
        MultiTypeDelegate<T> multiTypeDelegate = this.mMultiTypeDelegate;
        if (multiTypeDelegate != null) {
            layoutId = multiTypeDelegate.getLayoutId(i2);
        }
        return (K) createBaseViewHolder(viewGroup, layoutId);
    }

    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    public final void refreshNotifyItemChanged(int i2) {
        notifyItemChanged(i2 + getHeaderLayoutCount());
    }

    public void remove(@IntRange(from = 0) int i2) {
        this.mData.remove(i2);
        int headerLayoutCount = i2 + getHeaderLayoutCount();
        notifyItemRemoved(headerLayoutCount);
        compatibilityDataSizeChanged(0);
        notifyItemRangeChanged(headerLayoutCount, this.mData.size() - headerLayoutCount);
    }

    public void removeAllFooterView() {
        if (getFooterLayoutCount() == 0) {
            return;
        }
        this.mFooterLayout.removeAllViews();
        int footerViewPosition = getFooterViewPosition();
        if (footerViewPosition != -1) {
            notifyItemRemoved(footerViewPosition);
        }
    }

    public void removeAllHeaderView() {
        if (getHeaderLayoutCount() == 0) {
            return;
        }
        this.mHeaderLayout.removeAllViews();
        int headerViewPosition = getHeaderViewPosition();
        if (headerViewPosition != -1) {
            notifyItemRemoved(headerViewPosition);
        }
    }

    public void removeFooterView(View view) {
        int footerViewPosition;
        if (getFooterLayoutCount() == 0) {
            return;
        }
        this.mFooterLayout.removeView(view);
        if (this.mFooterLayout.getChildCount() != 0 || (footerViewPosition = getFooterViewPosition()) == -1) {
            return;
        }
        notifyItemRemoved(footerViewPosition);
    }

    public void removeHeaderView(View view) {
        int headerViewPosition;
        if (getHeaderLayoutCount() == 0) {
            return;
        }
        this.mHeaderLayout.removeView(view);
        if (this.mHeaderLayout.getChildCount() != 0 || (headerViewPosition = getHeaderViewPosition()) == -1) {
            return;
        }
        notifyItemRemoved(headerViewPosition);
    }

    public void replaceData(@NonNull Collection<? extends T> collection) {
        List<T> list = this.mData;
        if (collection != list) {
            list.clear();
            this.mData.addAll(collection);
        }
        notifyDataSetChanged();
    }

    @Deprecated
    public void setAutoLoadMoreSize(int i2) {
        setPreLoadNumber(i2);
    }

    public void setData(@IntRange(from = 0) int i2, @NonNull T t2) {
        this.mData.set(i2, t2);
        notifyItemChanged(i2 + getHeaderLayoutCount());
    }

    public void setDuration(int i2) {
        this.mDuration = i2;
    }

    public void setEmptyView(int i2, ViewGroup viewGroup) {
        setEmptyView(LayoutInflater.from(viewGroup.getContext()).inflate(i2, viewGroup, false));
    }

    public void setEnableLoadMore(boolean z2) {
        int loadMoreViewCount = getLoadMoreViewCount();
        this.mLoadMoreEnable = z2;
        int loadMoreViewCount2 = getLoadMoreViewCount();
        if (loadMoreViewCount == 1) {
            if (loadMoreViewCount2 == 0) {
                notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else if (loadMoreViewCount2 == 1) {
            this.mLoadMoreView.setLoadMoreStatus(1);
            notifyItemInserted(getLoadMoreViewPosition());
        }
    }

    public int setFooterView(View view) {
        return setFooterView(view, 0, 1);
    }

    public void setFooterViewAsFlow(boolean z2) {
        this.footerViewAsFlow = z2;
    }

    public void setFullSpan(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams()).setFullSpan(true);
        }
    }

    public void setHeaderAndEmpty(boolean z2) {
        setHeaderFooterEmpty(z2, false);
    }

    public void setHeaderFooterEmpty(boolean z2, boolean z3) {
        this.mHeadAndEmptyEnable = z2;
        this.mFootAndEmptyEnable = z3;
    }

    public int setHeaderView(View view) {
        return setHeaderView(view, 0, 1);
    }

    public void setHeaderViewAsFlow(boolean z2) {
        this.headerViewAsFlow = z2;
    }

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        this.mLoadMoreView = loadMoreView;
    }

    public void setMultiTypeDelegate(MultiTypeDelegate<T> multiTypeDelegate) {
        this.mMultiTypeDelegate = multiTypeDelegate;
    }

    public void setNewData(@Nullable List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.mData = list;
        if (this.mRequestLoadMoreListener != null) {
            this.mNextLoadEnable = true;
            this.mLoadMoreEnable = true;
            this.mLoading = false;
            this.mLoadMoreView.setLoadMoreStatus(1);
        }
        this.mLastPosition = -1;
        notifyDataSetChanged();
    }

    public void setNotDoAnimationCount(int i2) {
        this.mLastPosition = i2;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.mOnItemChildClickListener = onItemChildClickListener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    public void setOnItemClick(View view, int i2) {
        getOnItemClickListener().onItemClick(this, view, i2);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public boolean setOnItemLongClick(View view, int i2) {
        return getOnItemLongClickListener().onItemLongClick(this, view, i2);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Deprecated
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        openLoadMore(requestLoadMoreListener);
    }

    public void setPreLoadNumber(int i2) {
        if (i2 > 1) {
            this.mPreLoadNumber = i2;
        }
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void setStartUpFetchPosition(int i2) {
        this.mStartUpFetchPosition = i2;
    }

    public void setUpFetchEnable(boolean z2) {
        this.mUpFetchEnable = z2;
    }

    public void setUpFetchListener(UpFetchListener upFetchListener) {
        this.mUpFetchListener = upFetchListener;
    }

    public void setUpFetching(boolean z2) {
        this.mUpFetching = z2;
    }

    public void startAnim(Animator animator, int i2) {
        animator.setDuration(this.mDuration).start();
        animator.setInterpolator(this.mInterpolator);
    }

    public int addFooterView(View view, int i2) {
        return addFooterView(view, i2, 1);
    }

    public int addHeaderView(View view, int i2) {
        return addHeaderView(view, i2, 1);
    }

    public K createBaseViewHolder(View view) {
        Class instancedGenericKClass = null;
        for (Class<?> superclass = getClass(); instancedGenericKClass == null && superclass != null; superclass = superclass.getSuperclass()) {
            instancedGenericKClass = getInstancedGenericKClass(superclass);
        }
        K k2 = instancedGenericKClass == null ? (K) new BaseViewHolder(view) : (K) createGenericKInstance(instancedGenericKClass, view);
        return k2 != null ? k2 : (K) new BaseViewHolder(view);
    }

    public void loadMoreEnd(boolean z2) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        this.mLoading = false;
        this.mNextLoadEnable = false;
        this.mLoadMoreView.setLoadMoreEndGone(z2);
        if (z2) {
            notifyItemRemoved(getLoadMoreViewPosition());
        } else {
            this.mLoadMoreView.setLoadMoreStatus(4);
            notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(K k2, int i2) {
        autoUpFetch(i2);
        autoLoadMore(i2);
        int itemViewType = k2.getItemViewType();
        if (itemViewType == 0) {
            convert(k2, getItem(i2 - getHeaderLayoutCount()));
            return;
        }
        if (itemViewType != 273) {
            if (itemViewType == 546) {
                this.mLoadMoreView.convert(k2);
            } else {
                if (itemViewType == 819 || itemViewType == 1365) {
                    return;
                }
                convert(k2, getItem(i2 - getHeaderLayoutCount()));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public K onCreateViewHolder(ViewGroup viewGroup, int i2) {
        K k2;
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        if (i2 == 273) {
            k2 = (K) createBaseViewHolder(this.mHeaderLayout);
        } else if (i2 == 546) {
            k2 = (K) getLoadingView(viewGroup);
        } else if (i2 == 819) {
            k2 = (K) createBaseViewHolder(this.mFooterLayout);
        } else if (i2 != 1365) {
            k2 = (K) onCreateDefViewHolder(viewGroup, i2);
            bindViewClickListener(k2);
        } else {
            k2 = (K) createBaseViewHolder(this.mEmptyLayout);
        }
        k2.setAdapter(this);
        return k2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(K k2) {
        super.onViewAttachedToWindow((BaseQuickAdapter<T, K>) k2);
        int itemViewType = k2.getItemViewType();
        if (itemViewType == 1365 || itemViewType == 273 || itemViewType == 819 || itemViewType == 546) {
            setFullSpan(k2);
        } else {
            addAnimation(k2);
        }
    }

    public int setFooterView(View view, int i2) {
        return setFooterView(view, i2, 1);
    }

    public int setHeaderView(View view, int i2) {
        return setHeaderView(view, i2, 1);
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener, RecyclerView recyclerView) {
        openLoadMore(requestLoadMoreListener);
        if (getRecyclerView() == null) {
            setRecyclerView(recyclerView);
        }
    }

    public int addFooterView(View view, int i2, int i3) {
        int footerViewPosition;
        if (this.mFooterLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            this.mFooterLayout = linearLayout;
            if (i3 == 1) {
                linearLayout.setOrientation(1);
                this.mFooterLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            } else {
                linearLayout.setOrientation(0);
                this.mFooterLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mFooterLayout.getChildCount();
        if (i2 < 0 || i2 > childCount) {
            i2 = childCount;
        }
        this.mFooterLayout.addView(view, i2);
        if (this.mFooterLayout.getChildCount() == 1 && (footerViewPosition = getFooterViewPosition()) != -1) {
            notifyItemInserted(footerViewPosition);
        }
        return i2;
    }

    public int addHeaderView(View view, int i2, int i3) {
        int headerViewPosition;
        if (this.mHeaderLayout == null) {
            LinearLayout linearLayout = new LinearLayout(view.getContext());
            this.mHeaderLayout = linearLayout;
            if (i3 == 1) {
                linearLayout.setOrientation(1);
                this.mHeaderLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            } else {
                linearLayout.setOrientation(0);
                this.mHeaderLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -1));
            }
        }
        int childCount = this.mHeaderLayout.getChildCount();
        if (i2 < 0 || i2 > childCount) {
            i2 = childCount;
        }
        this.mHeaderLayout.addView(view, i2);
        if (this.mHeaderLayout.getChildCount() == 1 && (headerViewPosition = getHeaderViewPosition()) != -1) {
            notifyItemInserted(headerViewPosition);
        }
        return i2;
    }

    public void disableLoadMoreIfNotFullPage(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager;
        setEnableLoadMore(false);
        if (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null) {
            return;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            recyclerView.postDelayed(new Runnable() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.1
                @Override // java.lang.Runnable
                public void run() {
                    if (BaseQuickAdapter.this.isFullScreen(linearLayoutManager)) {
                        BaseQuickAdapter.this.setEnableLoadMore(true);
                    }
                }
            }, 50L);
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            recyclerView.postDelayed(new Runnable() { // from class: com.aliyun.svideo.common.baseAdapter.BaseQuickAdapter.2
                @Override // java.lang.Runnable
                public void run() {
                    int[] iArr = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(iArr);
                    if (BaseQuickAdapter.this.getTheBiggestNumber(iArr) + 1 != BaseQuickAdapter.this.getItemCount()) {
                        BaseQuickAdapter.this.setEnableLoadMore(true);
                    }
                }
            }, 50L);
        }
    }

    @Nullable
    public View getViewByPosition(RecyclerView recyclerView, int i2, @IdRes int i3) {
        BaseViewHolder baseViewHolder;
        if (recyclerView == null || (baseViewHolder = (BaseViewHolder) recyclerView.findViewHolderForLayoutPosition(i2)) == null) {
            return null;
        }
        return baseViewHolder.getView(i3);
    }

    @Deprecated
    public void setEmptyView(int i2) {
        checkNotNull();
        setEmptyView(i2, getRecyclerView());
    }

    public int setFooterView(View view, int i2, int i3) {
        LinearLayout linearLayout = this.mFooterLayout;
        if (linearLayout != null && linearLayout.getChildCount() > i2) {
            this.mFooterLayout.removeViewAt(i2);
            this.mFooterLayout.addView(view, i2);
            return i2;
        }
        return addFooterView(view, i2, i3);
    }

    public int setHeaderView(View view, int i2, int i3) {
        LinearLayout linearLayout = this.mHeaderLayout;
        if (linearLayout != null && linearLayout.getChildCount() > i2) {
            this.mHeaderLayout.removeViewAt(i2);
            this.mHeaderLayout.addView(view, i2);
            return i2;
        }
        return addHeaderView(view, i2, i3);
    }

    public void addData(@NonNull T t2) {
        this.mData.add(t2);
        notifyItemInserted(this.mData.size() + getHeaderLayoutCount());
        compatibilityDataSizeChanged(1);
    }

    public void setEmptyView(View view) {
        boolean z2;
        int itemCount = getItemCount();
        if (this.mEmptyLayout == null) {
            this.mEmptyLayout = new FrameLayout(view.getContext());
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
            ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
            if (layoutParams2 != null) {
                layoutParams.width = layoutParams2.width;
                layoutParams.height = layoutParams2.height;
            }
            this.mEmptyLayout.setLayoutParams(layoutParams);
            z2 = true;
        } else {
            z2 = false;
        }
        this.mEmptyLayout.removeAllViews();
        this.mEmptyLayout.addView(view);
        this.mIsUseEmpty = true;
        if (z2 && getEmptyViewCount() == 1) {
            int i2 = (!this.mHeadAndEmptyEnable || getHeaderLayoutCount() == 0) ? 0 : 1;
            if (getItemCount() > itemCount) {
                notifyItemInserted(i2);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    public void addData(@IntRange(from = 0) int i2, @NonNull Collection<? extends T> collection) {
        this.mData.addAll(i2, collection);
        notifyItemRangeInserted(i2 + getHeaderLayoutCount(), collection.size());
        compatibilityDataSizeChanged(collection.size());
    }

    public void addData(@NonNull Collection<? extends T> collection) {
        this.mData.addAll(collection);
        notifyItemRangeInserted((this.mData.size() - collection.size()) + getHeaderLayoutCount(), collection.size());
        compatibilityDataSizeChanged(collection.size());
    }

    public BaseQuickAdapter(@Nullable List<T> list) {
        this(0, list);
    }

    public BaseQuickAdapter(@LayoutRes int i2) {
        this(i2, null);
    }
}
