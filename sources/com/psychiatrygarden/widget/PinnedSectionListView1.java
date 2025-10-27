package com.psychiatrygarden.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class PinnedSectionListView1 extends ListView {
    private int isPlanCanvas;
    private final DataSetObserver mDataSetObserver;
    AbsListView.OnScrollListener mDelegateOnScrollListener;
    private MotionEvent mDownEvent;
    private List<View> mListView;
    private final AbsListView.OnScrollListener mOnScrollListener;
    PinnedSection mPinnedSection;
    PinnedSection mRecycleSection;
    private int mSectionsDistanceY;
    private GradientDrawable mShadowDrawable;
    private int mShadowHeight;
    private final PointF mTouchPoint;
    private final Rect mTouchRect;
    private int mTouchSlop;
    private View mTouchTarget;
    int mTranslateY;
    View sectionView;

    public static class PinnedSection {
        public long id;
        public int position;
        public View view;
    }

    public interface PinnedSectionListAdapter extends ListAdapter {
        boolean isItemViewTypePinned(int viewType);
    }

    public PinnedSectionListView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTouchRect = new Rect();
        this.mTouchPoint = new PointF();
        this.isPlanCanvas = 0;
        this.mListView = new ArrayList();
        this.mOnScrollListener = new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.widget.PinnedSectionListView1.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView1.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                ListAdapter adapter = PinnedSectionListView1.this.getAdapter();
                if (adapter == null || visibleItemCount == 0) {
                    return;
                }
                if (PinnedSectionListView1.isItemViewTypePinned(adapter, adapter.getItemViewType(firstVisibleItem))) {
                    PinnedSectionListView1 pinnedSectionListView1 = PinnedSectionListView1.this;
                    pinnedSectionListView1.sectionView = pinnedSectionListView1.getChildAt(0);
                    PinnedSectionListView1.this.mListView.add(PinnedSectionListView1.this.sectionView);
                    if (PinnedSectionListView1.this.sectionView.getTop() == PinnedSectionListView1.this.getPaddingTop()) {
                        PinnedSectionListView1.this.destroyPinnedShadow();
                        PinnedSectionListView1.this.sectionView.setVisibility(0);
                    } else {
                        PinnedSectionListView1.this.sectionView.setVisibility(8);
                        PinnedSectionListView1.this.ensureShadowForPosition(firstVisibleItem, firstVisibleItem, visibleItemCount);
                    }
                } else {
                    int iFindCurrentSectionPosition = PinnedSectionListView1.this.findCurrentSectionPosition(firstVisibleItem);
                    if (iFindCurrentSectionPosition > -1) {
                        PinnedSectionListView1.this.ensureShadowForPosition(iFindCurrentSectionPosition, firstVisibleItem, visibleItemCount);
                    } else {
                        PinnedSectionListView1.this.destroyPinnedShadow();
                        View view2 = PinnedSectionListView1.this.sectionView;
                        if (view2 != null) {
                            view2.setVisibility(0);
                        }
                    }
                }
                if (firstVisibleItem != 0 || PinnedSectionListView1.this.mListView == null || PinnedSectionListView1.this.mListView.size() <= 2) {
                    return;
                }
                for (int i2 = 0; i2 < PinnedSectionListView1.this.mListView.size(); i2++) {
                    ((View) PinnedSectionListView1.this.mListView.get(i2)).setVisibility(0);
                }
                PinnedSectionListView1.this.mListView.clear();
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView1.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }
        };
        this.mDataSetObserver = new DataSetObserver() { // from class: com.psychiatrygarden.widget.PinnedSectionListView1.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                PinnedSectionListView1.this.recreatePinnedShadow();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                PinnedSectionListView1.this.recreatePinnedShadow();
            }
        };
        initView();
    }

    private void clearTouchTarget() {
        this.mTouchTarget = null;
        MotionEvent motionEvent = this.mDownEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mDownEvent = null;
        }
    }

    private void initView() {
        setOnScrollListener(this.mOnScrollListener);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
    }

    public static boolean isItemViewTypePinned(ListAdapter adapter, int viewType) {
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) adapter).isItemViewTypePinned(viewType);
    }

    private boolean isPinnedViewTouched(View view, float x2, float y2) {
        view.getHitRect(this.mTouchRect);
        Rect rect = this.mTouchRect;
        int i2 = rect.top;
        int i3 = this.mTranslateY;
        rect.top = i2 + i3;
        rect.bottom += i3 + getPaddingTop();
        this.mTouchRect.left += getPaddingLeft();
        this.mTouchRect.right -= getPaddingRight();
        return this.mTouchRect.contains((int) x2, (int) y2);
    }

    private boolean performPinnedItemClick() {
        AdapterView.OnItemClickListener onItemClickListener;
        if (this.mPinnedSection == null || (onItemClickListener = getOnItemClickListener()) == null || !getAdapter().isEnabled(this.mPinnedSection.position)) {
            return false;
        }
        View view = this.mPinnedSection.view;
        playSoundEffect(0);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        PinnedSection pinnedSection = this.mPinnedSection;
        onItemClickListener.onItemClick(this, view, pinnedSection.position, pinnedSection.id);
        return true;
    }

    public void createPinnedShadow(int position) {
        PinnedSection pinnedSection = this.mRecycleSection;
        this.mRecycleSection = null;
        if (pinnedSection == null) {
            pinnedSection = new PinnedSection();
        }
        View view = getAdapter().getView(position, pinnedSection.view, this);
        AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new AbsListView.LayoutParams(-2, -2);
            view.setLayoutParams(layoutParams);
        }
        int mode = View.MeasureSpec.getMode(layoutParams.height);
        int size = View.MeasureSpec.getSize(layoutParams.height);
        if (mode == 0) {
            mode = 1073741824;
        }
        int height = (getHeight() - getListPaddingTop()) - getListPaddingBottom();
        if (size > height) {
            size = height;
        }
        view.measure(View.MeasureSpec.makeMeasureSpec((getWidth() - getListPaddingLeft()) - getListPaddingRight(), 1073741824), View.MeasureSpec.makeMeasureSpec(size, mode));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        this.mTranslateY = 0;
        pinnedSection.view = view;
        pinnedSection.position = position;
        pinnedSection.id = getAdapter().getItemId(position);
        this.mPinnedSection = pinnedSection;
    }

    public void destroyPinnedShadow() {
        PinnedSection pinnedSection = this.mPinnedSection;
        if (pinnedSection != null) {
            this.mRecycleSection = pinnedSection;
            this.mPinnedSection = null;
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mPinnedSection == null || this.isPlanCanvas != 0) {
            return;
        }
        int listPaddingLeft = getListPaddingLeft();
        int listPaddingTop = getListPaddingTop();
        View view = this.mPinnedSection.view;
        canvas.save();
        canvas.clipRect(listPaddingLeft, listPaddingTop, view.getWidth() + listPaddingLeft, view.getHeight() + (this.mShadowDrawable == null ? 0 : Math.min(this.mShadowHeight, this.mSectionsDistanceY)) + listPaddingTop);
        canvas.translate(listPaddingLeft, listPaddingTop + this.mTranslateY);
        drawChild(canvas, this.mPinnedSection.view, getDrawingTime());
        GradientDrawable gradientDrawable = this.mShadowDrawable;
        if (gradientDrawable != null && this.mSectionsDistanceY > 0) {
            gradientDrawable.setBounds(this.mPinnedSection.view.getLeft(), this.mPinnedSection.view.getBottom(), this.mPinnedSection.view.getRight(), this.mPinnedSection.view.getBottom() + this.mShadowHeight);
            this.mShadowDrawable.draw(canvas);
        }
        canvas.restore();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        PinnedSection pinnedSection;
        float x2 = ev.getX();
        float y2 = ev.getY();
        int action = ev.getAction();
        if (action == 0 && this.mTouchTarget == null && (pinnedSection = this.mPinnedSection) != null && isPinnedViewTouched(pinnedSection.view, x2, y2)) {
            this.mTouchTarget = this.mPinnedSection.view;
            PointF pointF = this.mTouchPoint;
            pointF.x = x2;
            pointF.y = y2;
            this.mDownEvent = MotionEvent.obtain(ev);
        }
        View view = this.mTouchTarget;
        if (view == null) {
            return super.dispatchTouchEvent(ev);
        }
        if (isPinnedViewTouched(view, x2, y2)) {
            this.mTouchTarget.dispatchTouchEvent(ev);
        }
        if (action == 1) {
            super.dispatchTouchEvent(ev);
            performPinnedItemClick();
            clearTouchTarget();
        } else if (action == 3) {
            clearTouchTarget();
        } else if (action == 2 && Math.abs(y2 - this.mTouchPoint.y) > this.mTouchSlop) {
            MotionEvent motionEventObtain = MotionEvent.obtain(ev);
            motionEventObtain.setAction(3);
            this.mTouchTarget.dispatchTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
            super.dispatchTouchEvent(this.mDownEvent);
            super.dispatchTouchEvent(ev);
            clearTouchTarget();
        }
        return true;
    }

    public void ensureShadowForPosition(int sectionPosition, int firstVisibleItem, int visibleItemCount) {
        if (visibleItemCount < 0) {
            destroyPinnedShadow();
            return;
        }
        PinnedSection pinnedSection = this.mPinnedSection;
        if (pinnedSection != null && pinnedSection.position != sectionPosition) {
            destroyPinnedShadow();
        }
        if (this.mPinnedSection == null) {
            createPinnedShadow(sectionPosition);
        }
        int i2 = sectionPosition + 1;
        if (i2 < getCount()) {
            int iFindFirstVisibleSectionPosition = findFirstVisibleSectionPosition(i2, visibleItemCount - (i2 - firstVisibleItem));
            if (iFindFirstVisibleSectionPosition <= -1) {
                this.mTranslateY = 0;
                this.mSectionsDistanceY = Integer.MAX_VALUE;
                return;
            }
            View childAt = getChildAt(iFindFirstVisibleSectionPosition - firstVisibleItem);
            childAt.setVisibility(0);
            int top2 = childAt.getTop() - (this.mPinnedSection.view.getBottom() + getPaddingTop());
            this.mSectionsDistanceY = top2;
            if (top2 < 0) {
                this.mTranslateY = top2;
            } else {
                this.mTranslateY = 0;
            }
        }
    }

    public int findCurrentSectionPosition(int fromPosition) {
        ListAdapter adapter = getAdapter();
        if (fromPosition >= adapter.getCount()) {
            return -1;
        }
        if (adapter instanceof SectionIndexer) {
            SectionIndexer sectionIndexer = (SectionIndexer) adapter;
            int positionForSection = sectionIndexer.getPositionForSection(sectionIndexer.getSectionForPosition(fromPosition));
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(positionForSection))) {
                return positionForSection;
            }
        }
        while (fromPosition >= 0) {
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(fromPosition))) {
                return fromPosition;
            }
            fromPosition--;
        }
        return -1;
    }

    public int findFirstVisibleSectionPosition(int firstVisibleItem, int visibleItemCount) {
        ListAdapter adapter = getAdapter();
        if (firstVisibleItem + visibleItemCount >= adapter.getCount()) {
            return -1;
        }
        for (int i2 = 0; i2 < visibleItemCount; i2++) {
            int i3 = firstVisibleItem + i2;
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(i3))) {
                return i3;
            }
        }
        return -1;
    }

    public void initShadow(boolean visible) {
        if (visible) {
            if (this.mShadowDrawable == null) {
                this.mShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#FFFF0000"), Color.parseColor("#FF00FF00"), Color.parseColor("#FF0000FF")});
                this.mShadowHeight = 0;
                return;
            }
            return;
        }
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable = null;
            this.mShadowHeight = 0;
        }
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (this.mPinnedSection == null || ((r2 - l2) - getPaddingLeft()) - getPaddingRight() == this.mPinnedSection.view.getWidth()) {
            return;
        }
        recreatePinnedShadow();
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        post(new Runnable() { // from class: com.psychiatrygarden.widget.PinnedSectionListView1.3
            @Override // java.lang.Runnable
            public void run() {
                PinnedSectionListView1.this.recreatePinnedShadow();
            }
        });
    }

    public void recreatePinnedShadow() {
        int firstVisiblePosition;
        int iFindCurrentSectionPosition;
        destroyPinnedShadow();
        ListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() <= 0 || (iFindCurrentSectionPosition = findCurrentSectionPosition((firstVisiblePosition = getFirstVisiblePosition()))) == -1) {
            return;
        }
        ensureShadowForPosition(iFindCurrentSectionPosition, firstVisiblePosition, getLastVisiblePosition() - firstVisiblePosition);
    }

    public void setIsPlanCanvas(int isPlanCanvas) {
        this.isPlanCanvas = isPlanCanvas;
    }

    @Override // android.widget.AbsListView
    public void setOnScrollListener(AbsListView.OnScrollListener listener) {
        if (listener == this.mOnScrollListener) {
            super.setOnScrollListener(listener);
        } else {
            this.mDelegateOnScrollListener = listener;
        }
    }

    public void setShadowVisible(boolean visible) {
        initShadow(visible);
        PinnedSection pinnedSection = this.mPinnedSection;
        if (pinnedSection != null) {
            View view = pinnedSection.view;
            invalidate(view.getLeft(), view.getTop(), view.getRight(), view.getBottom() + this.mShadowHeight);
        }
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter adapter) {
        ListAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.unregisterDataSetObserver(this.mDataSetObserver);
        }
        if (adapter != null) {
            adapter.registerDataSetObserver(this.mDataSetObserver);
        }
        if (adapter2 != adapter) {
            destroyPinnedShadow();
        }
        super.setAdapter(adapter);
    }

    public PinnedSectionListView1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTouchRect = new Rect();
        this.mTouchPoint = new PointF();
        this.isPlanCanvas = 0;
        this.mListView = new ArrayList();
        this.mOnScrollListener = new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.widget.PinnedSectionListView1.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView1.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                ListAdapter adapter = PinnedSectionListView1.this.getAdapter();
                if (adapter == null || visibleItemCount == 0) {
                    return;
                }
                if (PinnedSectionListView1.isItemViewTypePinned(adapter, adapter.getItemViewType(firstVisibleItem))) {
                    PinnedSectionListView1 pinnedSectionListView1 = PinnedSectionListView1.this;
                    pinnedSectionListView1.sectionView = pinnedSectionListView1.getChildAt(0);
                    PinnedSectionListView1.this.mListView.add(PinnedSectionListView1.this.sectionView);
                    if (PinnedSectionListView1.this.sectionView.getTop() == PinnedSectionListView1.this.getPaddingTop()) {
                        PinnedSectionListView1.this.destroyPinnedShadow();
                        PinnedSectionListView1.this.sectionView.setVisibility(0);
                    } else {
                        PinnedSectionListView1.this.sectionView.setVisibility(8);
                        PinnedSectionListView1.this.ensureShadowForPosition(firstVisibleItem, firstVisibleItem, visibleItemCount);
                    }
                } else {
                    int iFindCurrentSectionPosition = PinnedSectionListView1.this.findCurrentSectionPosition(firstVisibleItem);
                    if (iFindCurrentSectionPosition > -1) {
                        PinnedSectionListView1.this.ensureShadowForPosition(iFindCurrentSectionPosition, firstVisibleItem, visibleItemCount);
                    } else {
                        PinnedSectionListView1.this.destroyPinnedShadow();
                        View view2 = PinnedSectionListView1.this.sectionView;
                        if (view2 != null) {
                            view2.setVisibility(0);
                        }
                    }
                }
                if (firstVisibleItem != 0 || PinnedSectionListView1.this.mListView == null || PinnedSectionListView1.this.mListView.size() <= 2) {
                    return;
                }
                for (int i2 = 0; i2 < PinnedSectionListView1.this.mListView.size(); i2++) {
                    ((View) PinnedSectionListView1.this.mListView.get(i2)).setVisibility(0);
                }
                PinnedSectionListView1.this.mListView.clear();
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView1.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }
            }
        };
        this.mDataSetObserver = new DataSetObserver() { // from class: com.psychiatrygarden.widget.PinnedSectionListView1.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                PinnedSectionListView1.this.recreatePinnedShadow();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                PinnedSectionListView1.this.recreatePinnedShadow();
            }
        };
        initView();
    }
}
