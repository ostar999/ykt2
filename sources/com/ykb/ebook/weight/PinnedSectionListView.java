package com.ykb.ebook.weight;

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

/* loaded from: classes8.dex */
public class PinnedSectionListView extends ListView {
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
        boolean isItemViewTypePinned(int i2);
    }

    public PinnedSectionListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchRect = new Rect();
        this.mTouchPoint = new PointF();
        this.isPlanCanvas = 0;
        this.mListView = new ArrayList();
        this.mOnScrollListener = new AbsListView.OnScrollListener() { // from class: com.ykb.ebook.weight.PinnedSectionListView.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView absListView, int i2, int i3, int i4) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScroll(absListView, i2, i3, i4);
                }
                ListAdapter adapter = PinnedSectionListView.this.getAdapter();
                if (adapter == null || i3 == 0) {
                    return;
                }
                if (PinnedSectionListView.isItemViewTypePinned(adapter, adapter.getItemViewType(i2))) {
                    PinnedSectionListView pinnedSectionListView = PinnedSectionListView.this;
                    pinnedSectionListView.sectionView = pinnedSectionListView.getChildAt(0);
                    PinnedSectionListView.this.mListView.add(PinnedSectionListView.this.sectionView);
                    if (PinnedSectionListView.this.sectionView.getTop() == PinnedSectionListView.this.getPaddingTop()) {
                        PinnedSectionListView.this.destroyPinnedShadow();
                        PinnedSectionListView.this.sectionView.setVisibility(0);
                    } else {
                        PinnedSectionListView.this.sectionView.setVisibility(8);
                        PinnedSectionListView.this.ensureShadowForPosition(i2, i2, i3);
                    }
                } else {
                    int iFindCurrentSectionPosition = PinnedSectionListView.this.findCurrentSectionPosition(i2);
                    if (iFindCurrentSectionPosition > -1) {
                        PinnedSectionListView.this.ensureShadowForPosition(iFindCurrentSectionPosition, i2, i3);
                    } else {
                        PinnedSectionListView.this.destroyPinnedShadow();
                        View view = PinnedSectionListView.this.sectionView;
                        if (view != null) {
                            view.setVisibility(0);
                        }
                    }
                }
                if (i2 != 0 || PinnedSectionListView.this.mListView == null || PinnedSectionListView.this.mListView.size() <= 2) {
                    return;
                }
                for (int i5 = 0; i5 < PinnedSectionListView.this.mListView.size(); i5++) {
                    ((View) PinnedSectionListView.this.mListView.get(i5)).setVisibility(0);
                }
                PinnedSectionListView.this.mListView.clear();
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView absListView, int i2) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(absListView, i2);
                }
            }
        };
        this.mDataSetObserver = new DataSetObserver() { // from class: com.ykb.ebook.weight.PinnedSectionListView.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                PinnedSectionListView.this.recreatePinnedShadow();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                PinnedSectionListView.this.recreatePinnedShadow();
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

    public static boolean isItemViewTypePinned(ListAdapter listAdapter, int i2) {
        if (listAdapter instanceof HeaderViewListAdapter) {
            listAdapter = ((HeaderViewListAdapter) listAdapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) listAdapter).isItemViewTypePinned(i2);
    }

    private boolean isPinnedViewTouched(View view, float f2, float f3) {
        view.getHitRect(this.mTouchRect);
        Rect rect = this.mTouchRect;
        int i2 = rect.top;
        int i3 = this.mTranslateY;
        rect.top = i2 + i3;
        rect.bottom += i3 + getPaddingTop();
        this.mTouchRect.left += getPaddingLeft();
        this.mTouchRect.right -= getPaddingRight();
        return this.mTouchRect.contains((int) f2, (int) f3);
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

    public void createPinnedShadow(int i2) {
        PinnedSection pinnedSection = this.mRecycleSection;
        this.mRecycleSection = null;
        if (pinnedSection == null) {
            pinnedSection = new PinnedSection();
        }
        View view = getAdapter().getView(i2, pinnedSection.view, this);
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
        pinnedSection.position = i2;
        pinnedSection.id = getAdapter().getItemId(i2);
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
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        PinnedSection pinnedSection;
        float x2 = motionEvent.getX();
        float y2 = motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0 && this.mTouchTarget == null && (pinnedSection = this.mPinnedSection) != null && isPinnedViewTouched(pinnedSection.view, x2, y2)) {
            this.mTouchTarget = this.mPinnedSection.view;
            PointF pointF = this.mTouchPoint;
            pointF.x = x2;
            pointF.y = y2;
            this.mDownEvent = MotionEvent.obtain(motionEvent);
        }
        View view = this.mTouchTarget;
        if (view == null) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (isPinnedViewTouched(view, x2, y2)) {
            this.mTouchTarget.dispatchTouchEvent(motionEvent);
        }
        if (action == 1) {
            super.dispatchTouchEvent(motionEvent);
            performPinnedItemClick();
            clearTouchTarget();
        } else if (action == 3) {
            clearTouchTarget();
        } else if (action == 2 && Math.abs(y2 - this.mTouchPoint.y) > this.mTouchSlop) {
            MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
            motionEventObtain.setAction(3);
            this.mTouchTarget.dispatchTouchEvent(motionEventObtain);
            motionEventObtain.recycle();
            super.dispatchTouchEvent(this.mDownEvent);
            super.dispatchTouchEvent(motionEvent);
            clearTouchTarget();
        }
        return true;
    }

    public void ensureShadowForPosition(int i2, int i3, int i4) {
        if (i4 < 0) {
            destroyPinnedShadow();
            return;
        }
        PinnedSection pinnedSection = this.mPinnedSection;
        if (pinnedSection != null && pinnedSection.position != i2) {
            destroyPinnedShadow();
        }
        if (this.mPinnedSection == null) {
            createPinnedShadow(i2);
        }
        int i5 = i2 + 1;
        if (i5 < getCount()) {
            int iFindFirstVisibleSectionPosition = findFirstVisibleSectionPosition(i5, i4 - (i5 - i3));
            if (iFindFirstVisibleSectionPosition <= -1) {
                this.mTranslateY = 0;
                this.mSectionsDistanceY = Integer.MAX_VALUE;
                return;
            }
            View childAt = getChildAt(iFindFirstVisibleSectionPosition - i3);
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

    public int findCurrentSectionPosition(int i2) {
        ListAdapter adapter = getAdapter();
        if (i2 >= adapter.getCount()) {
            return -1;
        }
        if (adapter instanceof SectionIndexer) {
            SectionIndexer sectionIndexer = (SectionIndexer) adapter;
            int positionForSection = sectionIndexer.getPositionForSection(sectionIndexer.getSectionForPosition(i2));
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(positionForSection))) {
                return positionForSection;
            }
        }
        while (i2 >= 0) {
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(i2))) {
                return i2;
            }
            i2--;
        }
        return -1;
    }

    public int findFirstVisibleSectionPosition(int i2, int i3) {
        ListAdapter adapter = getAdapter();
        if (i2 + i3 >= adapter.getCount()) {
            return -1;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i2 + i4;
            if (isItemViewTypePinned(adapter, adapter.getItemViewType(i5))) {
                return i5;
            }
        }
        return -1;
    }

    public void initShadow(boolean z2) {
        if (z2) {
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
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.mPinnedSection == null || ((i4 - i2) - getPaddingLeft()) - getPaddingRight() == this.mPinnedSection.view.getWidth()) {
            return;
        }
        recreatePinnedShadow();
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        super.onRestoreInstanceState(parcelable);
        post(new Runnable() { // from class: com.ykb.ebook.weight.PinnedSectionListView.3
            @Override // java.lang.Runnable
            public void run() {
                PinnedSectionListView.this.recreatePinnedShadow();
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

    public void setIsPlanCanvas(int i2) {
        this.isPlanCanvas = i2;
    }

    @Override // android.widget.AbsListView
    public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
        if (onScrollListener == this.mOnScrollListener) {
            super.setOnScrollListener(onScrollListener);
        } else {
            this.mDelegateOnScrollListener = onScrollListener;
        }
    }

    public void setShadowVisible(boolean z2) {
        initShadow(z2);
        PinnedSection pinnedSection = this.mPinnedSection;
        if (pinnedSection != null) {
            View view = pinnedSection.view;
            invalidate(view.getLeft(), view.getTop(), view.getRight(), view.getBottom() + this.mShadowHeight);
        }
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        ListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.mDataSetObserver);
        }
        if (adapter != listAdapter) {
            destroyPinnedShadow();
        }
        super.setAdapter(listAdapter);
    }

    public PinnedSectionListView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTouchRect = new Rect();
        this.mTouchPoint = new PointF();
        this.isPlanCanvas = 0;
        this.mListView = new ArrayList();
        this.mOnScrollListener = new AbsListView.OnScrollListener() { // from class: com.ykb.ebook.weight.PinnedSectionListView.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView absListView, int i22, int i3, int i4) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScroll(absListView, i22, i3, i4);
                }
                ListAdapter adapter = PinnedSectionListView.this.getAdapter();
                if (adapter == null || i3 == 0) {
                    return;
                }
                if (PinnedSectionListView.isItemViewTypePinned(adapter, adapter.getItemViewType(i22))) {
                    PinnedSectionListView pinnedSectionListView = PinnedSectionListView.this;
                    pinnedSectionListView.sectionView = pinnedSectionListView.getChildAt(0);
                    PinnedSectionListView.this.mListView.add(PinnedSectionListView.this.sectionView);
                    if (PinnedSectionListView.this.sectionView.getTop() == PinnedSectionListView.this.getPaddingTop()) {
                        PinnedSectionListView.this.destroyPinnedShadow();
                        PinnedSectionListView.this.sectionView.setVisibility(0);
                    } else {
                        PinnedSectionListView.this.sectionView.setVisibility(8);
                        PinnedSectionListView.this.ensureShadowForPosition(i22, i22, i3);
                    }
                } else {
                    int iFindCurrentSectionPosition = PinnedSectionListView.this.findCurrentSectionPosition(i22);
                    if (iFindCurrentSectionPosition > -1) {
                        PinnedSectionListView.this.ensureShadowForPosition(iFindCurrentSectionPosition, i22, i3);
                    } else {
                        PinnedSectionListView.this.destroyPinnedShadow();
                        View view = PinnedSectionListView.this.sectionView;
                        if (view != null) {
                            view.setVisibility(0);
                        }
                    }
                }
                if (i22 != 0 || PinnedSectionListView.this.mListView == null || PinnedSectionListView.this.mListView.size() <= 2) {
                    return;
                }
                for (int i5 = 0; i5 < PinnedSectionListView.this.mListView.size(); i5++) {
                    ((View) PinnedSectionListView.this.mListView.get(i5)).setVisibility(0);
                }
                PinnedSectionListView.this.mListView.clear();
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView absListView, int i22) {
                AbsListView.OnScrollListener onScrollListener = PinnedSectionListView.this.mDelegateOnScrollListener;
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(absListView, i22);
                }
            }
        };
        this.mDataSetObserver = new DataSetObserver() { // from class: com.ykb.ebook.weight.PinnedSectionListView.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                PinnedSectionListView.this.recreatePinnedShadow();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                PinnedSectionListView.this.recreatePinnedShadow();
            }
        };
        initView();
    }
}
