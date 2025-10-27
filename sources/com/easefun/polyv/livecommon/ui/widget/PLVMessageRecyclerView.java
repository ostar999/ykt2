package com.easefun.polyv.livecommon.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVMessageRecyclerView extends RecyclerView {
    private static final int FLAG_SCROLL = 1;

    @SuppressLint({"HandlerLeak"})
    private Handler handler;
    private boolean hasScrollEvent;
    private boolean heightZero;
    private boolean isScrolledEnd;
    private int lastVerticalScrollExtent;
    private int lastVerticalScrollOffset;
    private int lastVerticalScrollRange;
    private OnScrollToLastItemListener scrollToLastItemListener;
    private int unreadCount;
    private List<OnUnreadCountChangeListener> unreadCountChangeListeners;
    private List<View> unreadViews;

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;
        private int topSpacing;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int topSpacing) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
            this.topSpacing = topSpacing;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            int i2 = this.spanCount;
            int i3 = childAdapterPosition % i2;
            if (this.includeEdge) {
                int i4 = this.spacing;
                outRect.left = i4 - ((i3 * i4) / i2);
                outRect.right = ((i3 + 1) * i4) / i2;
                if (childAdapterPosition < i2) {
                    outRect.top = this.topSpacing;
                }
                outRect.bottom = i4;
                return;
            }
            int i5 = this.spacing;
            outRect.left = (i3 * i5) / i2;
            outRect.right = i5 - (((i3 + 1) * i5) / i2);
            if (childAdapterPosition >= i2) {
                outRect.top = this.topSpacing;
            }
        }
    }

    public interface OnScrollToLastItemListener {
        void onCall();
    }

    public interface OnUnreadCountChangeListener {
        void onChange(int currentUnreadCount);
    }

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int firstTop;
        private int noTopLayoutId;
        private int space;

        public SpacesItemDecoration(int space) {
            this(space, 0);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            int i2 = this.noTopLayoutId;
            if (i2 != 0 && i2 == view.getId()) {
                outRect.top = 0;
            } else if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = this.firstTop;
            } else {
                outRect.top = view instanceof PLVEmptyViewGroup ? 0 : this.space;
            }
        }

        public SpacesItemDecoration(int space, int firstTop) {
            this.space = space;
            this.firstTop = firstTop;
        }

        public SpacesItemDecoration(int space, int firstTop, int noTopLayoutId) {
            this.space = space;
            this.firstTop = firstTop;
            this.noTopLayoutId = noTopLayoutId;
        }
    }

    public PLVMessageRecyclerView(Context context) {
        this(context, null);
    }

    private void addOnUnreadListener() {
        addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int itemCount;
                super.onScrolled(recyclerView, dx, dy);
                if (PLVMessageRecyclerView.this.lastVerticalScrollRange == 0 || PLVMessageRecyclerView.this.lastVerticalScrollRange != PLVMessageRecyclerView.this.computeVerticalScrollRange() || PLVMessageRecyclerView.this.lastVerticalScrollOffset == 0 || PLVMessageRecyclerView.this.lastVerticalScrollOffset != PLVMessageRecyclerView.this.computeVerticalScrollOffset() || PLVMessageRecyclerView.this.lastVerticalScrollExtent == 0 || PLVMessageRecyclerView.this.lastVerticalScrollExtent <= PLVMessageRecyclerView.this.computeVerticalScrollExtent() || !PLVMessageRecyclerView.this.isScrolledEnd) {
                    PLVMessageRecyclerView pLVMessageRecyclerView = PLVMessageRecyclerView.this;
                    pLVMessageRecyclerView.lastVerticalScrollRange = pLVMessageRecyclerView.computeVerticalScrollRange();
                    PLVMessageRecyclerView pLVMessageRecyclerView2 = PLVMessageRecyclerView.this;
                    pLVMessageRecyclerView2.lastVerticalScrollExtent = pLVMessageRecyclerView2.computeVerticalScrollExtent();
                    PLVMessageRecyclerView pLVMessageRecyclerView3 = PLVMessageRecyclerView.this;
                    pLVMessageRecyclerView3.lastVerticalScrollOffset = pLVMessageRecyclerView3.computeVerticalScrollOffset();
                    PLVMessageRecyclerView.this.setIsScrolledEnd(!r2.canScrollVertically(1));
                    if (PLVMessageRecyclerView.this.unreadCount >= 2 && PLVMessageRecyclerView.this.getAdapter() != null && (itemCount = (PLVMessageRecyclerView.this.getAdapter().getItemCount() - 1) - ((LinearLayoutManager) PLVMessageRecyclerView.this.getLayoutManager()).findLastVisibleItemPosition()) < PLVMessageRecyclerView.this.unreadCount) {
                        PLVMessageRecyclerView.this.unreadCount = itemCount;
                        if (PLVMessageRecyclerView.this.unreadCount == 0 && PLVMessageRecyclerView.this.unreadViews != null) {
                            Iterator it = PLVMessageRecyclerView.this.unreadViews.iterator();
                            while (it.hasNext()) {
                                ((View) it.next()).setVisibility(8);
                            }
                        }
                        PLVMessageRecyclerView pLVMessageRecyclerView4 = PLVMessageRecyclerView.this;
                        pLVMessageRecyclerView4.callOnUnreadChange(pLVMessageRecyclerView4.unreadCount);
                    }
                    if (PLVMessageRecyclerView.this.isScrolledEnd) {
                        PLVMessageRecyclerView.this.unreadCount = 0;
                        if (PLVMessageRecyclerView.this.unreadViews != null) {
                            Iterator it2 = PLVMessageRecyclerView.this.unreadViews.iterator();
                            while (it2.hasNext()) {
                                ((View) it2.next()).setVisibility(8);
                            }
                        }
                        PLVMessageRecyclerView pLVMessageRecyclerView5 = PLVMessageRecyclerView.this;
                        pLVMessageRecyclerView5.callOnUnreadChange(pLVMessageRecyclerView5.unreadCount);
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnUnreadChange(int currentUnreadCount) {
        List<OnUnreadCountChangeListener> list = this.unreadCountChangeListeners;
        if (list != null) {
            Iterator<OnUnreadCountChangeListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onChange(currentUnreadCount);
            }
        }
    }

    @SuppressLint({"SetTextI18n"})
    private void changeUnreadViewWithCount(int count) {
        this.unreadCount += count;
        List<View> list = this.unreadViews;
        if (list != null) {
            Iterator<View> it = list.iterator();
            while (it.hasNext()) {
                it.next().setVisibility(0);
            }
        } else {
            super.scrollToPosition(getAdapter().getItemCount() - 1);
            OnScrollToLastItemListener onScrollToLastItemListener = this.scrollToLastItemListener;
            if (onScrollToLastItemListener != null) {
                onScrollToLastItemListener.onCall();
            }
        }
        callOnUnreadChange(this.unreadCount);
    }

    public static void closeDefaultAnimator(RecyclerView recyclerView) {
        recyclerView.getItemAnimator().setAddDuration(0L);
        recyclerView.getItemAnimator().setChangeDuration(0L);
        recyclerView.getItemAnimator().setMoveDuration(0L);
        recyclerView.getItemAnimator().setRemoveDuration(0L);
        if (recyclerView.getItemAnimator() instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    private void processStackFromEnd() {
        if ((canScrollVertically(1) || canScrollVertically(-1)) && (getLayoutManager() instanceof LinearLayoutManager) && ((LinearLayoutManager) getLayoutManager()).getStackFromEnd()) {
            ((LinearLayoutManager) getLayoutManager()).setStackFromEnd(false);
            this.unreadCount = 0;
            List<View> list = this.unreadViews;
            if (list != null) {
                Iterator<View> it = list.iterator();
                while (it.hasNext()) {
                    it.next().setVisibility(8);
                }
            }
            callOnUnreadChange(this.unreadCount);
            scrollToPosition(getAdapter().getItemCount() - 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setIsScrolledEnd(boolean isScrolledEnd) {
        this.isScrolledEnd = isScrolledEnd;
    }

    public static LinearLayoutManager setLayoutManager(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(), 1, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        return linearLayoutManager;
    }

    public void addOnUnreadCountChangeListener(OnUnreadCountChangeListener listener) {
        if (this.unreadCountChangeListeners == null) {
            this.unreadCountChangeListeners = new ArrayList();
        }
        this.unreadCountChangeListeners.add(listener);
    }

    public void addUnreadView(final View unreadView) {
        if (this.unreadViews == null) {
            this.unreadViews = new ArrayList();
        }
        this.unreadViews.add(unreadView);
        if (unreadView != null) {
            unreadView.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    PLVMessageRecyclerView.this.unreadCount = 0;
                    if (PLVMessageRecyclerView.this.unreadViews != null) {
                        Iterator it = PLVMessageRecyclerView.this.unreadViews.iterator();
                        while (it.hasNext()) {
                            ((View) it.next()).setVisibility(8);
                        }
                    }
                    PLVMessageRecyclerView pLVMessageRecyclerView = PLVMessageRecyclerView.this;
                    pLVMessageRecyclerView.callOnUnreadChange(pLVMessageRecyclerView.unreadCount);
                    if (PLVMessageRecyclerView.this.getAdapter() == null || PLVMessageRecyclerView.this.getAdapter().getItemCount() <= 0) {
                        return;
                    }
                    if ((PLVMessageRecyclerView.this.getAdapter().getItemCount() - 1) - ((LinearLayoutManager) PLVMessageRecyclerView.this.getLayoutManager()).findLastVisibleItemPosition() <= 10) {
                        PLVMessageRecyclerView.this.smoothScrollToPosition(r3.getAdapter().getItemCount() - 1);
                    } else {
                        PLVMessageRecyclerView.this.scrollToPosition(r3.getAdapter().getItemCount() - 1);
                    }
                }
            });
        }
    }

    public void clear() {
        this.unreadCount = 0;
        List<View> list = this.unreadViews;
        if (list != null) {
            Iterator<View> it = list.iterator();
            while (it.hasNext()) {
                it.next().setVisibility(8);
            }
        }
        callOnUnreadChange(this.unreadCount);
    }

    @Override // android.view.View
    public float getBottomFadingEdgeStrength() {
        return 0.0f;
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.handler.removeMessages(1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (t2 >= b3) {
            this.heightZero = true;
            return;
        }
        this.heightZero = false;
        if (this.hasScrollEvent) {
            this.hasScrollEvent = false;
            this.handler.sendEmptyMessage(1);
        }
    }

    public boolean scrollToBottomOrShowMore(int count) {
        if (this.heightZero) {
            if (this.isScrolledEnd) {
                this.hasScrollEvent = true;
                return false;
            }
            changeUnreadViewWithCount(count);
            return true;
        }
        if (!this.isScrolledEnd) {
            if ((getHeight() - getPaddingBottom()) - getPaddingTop() >= computeVerticalScrollRange()) {
                return false;
            }
            changeUnreadViewWithCount(count);
            return true;
        }
        if (getAdapter() == null) {
            return false;
        }
        super.scrollToPosition(getAdapter().getItemCount() - 1);
        OnScrollToLastItemListener onScrollToLastItemListener = this.scrollToLastItemListener;
        if (onScrollToLastItemListener == null) {
            return false;
        }
        onScrollToLastItemListener.onCall();
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void scrollToPosition(int position) {
        OnScrollToLastItemListener onScrollToLastItemListener;
        if (this.heightZero) {
            this.hasScrollEvent = true;
            return;
        }
        super.scrollToPosition(position);
        if (getAdapter() == null || position != getAdapter().getItemCount() - 1 || (onScrollToLastItemListener = this.scrollToLastItemListener) == null) {
            return;
        }
        onScrollToLastItemListener.onCall();
    }

    public void setOnScrollToLastItemListener(OnScrollToLastItemListener listener) {
        this.scrollToLastItemListener = listener;
    }

    public void setStackFromEnd(boolean stackFromEnd) {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            ((LinearLayoutManager) getLayoutManager()).setStackFromEnd(stackFromEnd);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void smoothScrollToPosition(int position) {
        OnScrollToLastItemListener onScrollToLastItemListener;
        if (this.heightZero) {
            this.hasScrollEvent = true;
            return;
        }
        super.smoothScrollToPosition(position);
        if (getAdapter() == null || position != getAdapter().getItemCount() - 1 || (onScrollToLastItemListener = this.scrollToLastItemListener) == null) {
            return;
        }
        onScrollToLastItemListener.onCall();
    }

    public PLVMessageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVMessageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.handler = new Handler() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what != 1 || PLVMessageRecyclerView.this.getAdapter() == null) {
                    return;
                }
                PLVMessageRecyclerView pLVMessageRecyclerView = PLVMessageRecyclerView.this;
                pLVMessageRecyclerView.scrollToPosition(pLVMessageRecyclerView.getAdapter().getItemCount() - 1);
            }
        };
        addOnUnreadListener();
    }
}
