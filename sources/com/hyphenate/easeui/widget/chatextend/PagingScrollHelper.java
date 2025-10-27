package com.hyphenate.easeui.widget.chatextend;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes4.dex */
public class PagingScrollHelper {
    private int currentPosition;
    private int lastPageIndex;
    private onPageChangeListener mOnPageChangeListener;
    RecyclerView mRecyclerView = null;
    private PageOnScrollListener mOnScrollListener = new PageOnScrollListener();
    private PageOnFlingListener mOnFlingListener = new PageOnFlingListener();
    private int offsetY = 0;
    private int offsetX = 0;
    int startY = 0;
    int startX = 0;
    ValueAnimator mAnimator = null;
    private MyOnTouchListener mOnTouchListener = new MyOnTouchListener();
    private boolean firstTouch = true;
    private ORIENTATION mOrientation = ORIENTATION.HORIZONTAL;

    public class MyOnTouchListener implements View.OnTouchListener {
        public MyOnTouchListener() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (PagingScrollHelper.this.firstTouch) {
                PagingScrollHelper.this.firstTouch = false;
                PagingScrollHelper pagingScrollHelper = PagingScrollHelper.this;
                pagingScrollHelper.startY = pagingScrollHelper.offsetY;
                PagingScrollHelper pagingScrollHelper2 = PagingScrollHelper.this;
                pagingScrollHelper2.startX = pagingScrollHelper2.offsetX;
            }
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                PagingScrollHelper.this.firstTouch = true;
            }
            return false;
        }
    }

    public enum ORIENTATION {
        HORIZONTAL,
        VERTICAL,
        NULL
    }

    public class PageOnFlingListener extends RecyclerView.OnFlingListener {
        public PageOnFlingListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnFlingListener
        public boolean onFling(int i2, int i3) {
            int width;
            int i4;
            if (PagingScrollHelper.this.mOrientation == ORIENTATION.NULL) {
                return false;
            }
            int startPageIndex = PagingScrollHelper.this.getStartPageIndex();
            if (PagingScrollHelper.this.mOrientation == ORIENTATION.VERTICAL) {
                i4 = PagingScrollHelper.this.offsetY;
                if (i3 < 0) {
                    startPageIndex--;
                } else if (i3 > 0) {
                    startPageIndex++;
                }
                width = startPageIndex * PagingScrollHelper.this.mRecyclerView.getHeight();
            } else {
                int i5 = PagingScrollHelper.this.offsetX;
                if (i2 < 0) {
                    startPageIndex--;
                } else if (i2 > 0) {
                    startPageIndex++;
                }
                width = startPageIndex * PagingScrollHelper.this.mRecyclerView.getWidth();
                i4 = i5;
            }
            if (width < 0) {
                width = 0;
            }
            PagingScrollHelper pagingScrollHelper = PagingScrollHelper.this;
            ValueAnimator valueAnimator = pagingScrollHelper.mAnimator;
            if (valueAnimator == null) {
                pagingScrollHelper.mAnimator = ValueAnimator.ofInt(i4, width);
                PagingScrollHelper.this.mAnimator.setDuration(200L);
                PagingScrollHelper.this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.PageOnFlingListener.1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        int iIntValue = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                        if (PagingScrollHelper.this.mOrientation == ORIENTATION.VERTICAL) {
                            PagingScrollHelper.this.mRecyclerView.scrollBy(0, iIntValue - PagingScrollHelper.this.offsetY);
                        } else {
                            PagingScrollHelper.this.mRecyclerView.scrollBy(iIntValue - PagingScrollHelper.this.offsetX, 0);
                        }
                    }
                });
                PagingScrollHelper.this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.PageOnFlingListener.2
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        int pageIndex = PagingScrollHelper.this.getPageIndex();
                        if (PagingScrollHelper.this.lastPageIndex != pageIndex) {
                            if (PagingScrollHelper.this.mOnPageChangeListener != null) {
                                PagingScrollHelper.this.mOnPageChangeListener.onPageChange(pageIndex);
                            }
                            PagingScrollHelper.this.lastPageIndex = pageIndex;
                        }
                        PagingScrollHelper.this.mRecyclerView.stopScroll();
                        PagingScrollHelper pagingScrollHelper2 = PagingScrollHelper.this;
                        pagingScrollHelper2.startY = pagingScrollHelper2.offsetY;
                        PagingScrollHelper pagingScrollHelper3 = PagingScrollHelper.this;
                        pagingScrollHelper3.startX = pagingScrollHelper3.offsetX;
                    }
                });
            } else {
                valueAnimator.cancel();
                PagingScrollHelper.this.mAnimator.setIntValues(i4, width);
            }
            PagingScrollHelper.this.mAnimator.start();
            return true;
        }
    }

    public class PageOnScrollListener extends RecyclerView.OnScrollListener {
        public PageOnScrollListener() {
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x0075  */
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onScrollStateChanged(androidx.recyclerview.widget.RecyclerView r7, int r8) {
            /*
                r6 = this;
                if (r8 != 0) goto L7f
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper$ORIENTATION r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$000(r8)
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper$ORIENTATION r0 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.ORIENTATION.NULL
                if (r8 == r0) goto L7f
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper$ORIENTATION r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$000(r8)
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper$ORIENTATION r0 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.ORIENTATION.VERTICAL
                r1 = -1000(0xfffffffffffffc18, float:NaN)
                r2 = 1000(0x3e8, float:1.401E-42)
                r3 = 1
                r4 = 0
                if (r8 != r0) goto L47
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$200(r8)
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r0 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r0 = r0.startY
                int r8 = r8 - r0
                int r8 = java.lang.Math.abs(r8)
                int r7 = r7.getHeight()
                int r7 = r7 / 2
                if (r8 <= r7) goto L34
                goto L35
            L34:
                r3 = r4
            L35:
                if (r3 == 0) goto L75
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r7 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r7 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$200(r7)
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r8 = r8.startY
                int r7 = r7 - r8
                if (r7 >= 0) goto L45
                goto L76
            L45:
                r1 = r2
                goto L76
            L47:
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$300(r8)
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r0 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r0 = r0.startX
                int r8 = r8 - r0
                int r8 = java.lang.Math.abs(r8)
                int r7 = r7.getWidth()
                int r7 = r7 / 2
                if (r8 <= r7) goto L5f
                goto L60
            L5f:
                r3 = r4
            L60:
                if (r3 == 0) goto L75
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r7 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r7 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$300(r7)
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r8 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                int r8 = r8.startX
                int r7 = r7 - r8
                if (r7 >= 0) goto L70
                goto L71
            L70:
                r1 = r2
            L71:
                r5 = r4
                r4 = r1
                r1 = r5
                goto L76
            L75:
                r1 = r4
            L76:
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper r7 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.this
                com.hyphenate.easeui.widget.chatextend.PagingScrollHelper$PageOnFlingListener r7 = com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.access$700(r7)
                r7.onFling(r4, r1)
            L7f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.easeui.widget.chatextend.PagingScrollHelper.PageOnScrollListener.onScrollStateChanged(androidx.recyclerview.widget.RecyclerView, int):void");
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
            PagingScrollHelper.access$212(PagingScrollHelper.this, i3);
            PagingScrollHelper.access$312(PagingScrollHelper.this, i2);
        }
    }

    public interface onPageChangeListener {
        void onPageChange(int i2);
    }

    public static /* synthetic */ int access$212(PagingScrollHelper pagingScrollHelper, int i2) {
        int i3 = pagingScrollHelper.offsetY + i2;
        pagingScrollHelper.offsetY = i3;
        return i3;
    }

    public static /* synthetic */ int access$312(PagingScrollHelper pagingScrollHelper, int i2) {
        int i3 = pagingScrollHelper.offsetX + i2;
        pagingScrollHelper.offsetX = i3;
        return i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPageIndex() {
        if (this.mRecyclerView.getHeight() == 0 || this.mRecyclerView.getWidth() == 0) {
            return 0;
        }
        return this.mOrientation == ORIENTATION.VERTICAL ? this.offsetY / this.mRecyclerView.getHeight() : this.offsetX / this.mRecyclerView.getWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getStartPageIndex() {
        if (this.mRecyclerView.getHeight() == 0 || this.mRecyclerView.getWidth() == 0) {
            return 0;
        }
        return this.mOrientation == ORIENTATION.VERTICAL ? this.startY / this.mRecyclerView.getHeight() : this.startX / this.mRecyclerView.getWidth();
    }

    public void checkCurrentStatus() {
        if (this.mOrientation == ORIENTATION.VERTICAL) {
            RecyclerView recyclerView = this.mRecyclerView;
            if (recyclerView == null || this.offsetY == recyclerView.getHeight() * this.currentPosition) {
                return;
            }
            this.offsetX = this.mRecyclerView.getHeight() * this.currentPosition;
            this.mRecyclerView.scrollTo(0, this.offsetY);
            return;
        }
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == null || this.offsetX == recyclerView2.getWidth() * this.currentPosition) {
            return;
        }
        int width = this.mRecyclerView.getWidth() * this.currentPosition;
        this.offsetX = width;
        this.mRecyclerView.scrollTo(width, 0);
    }

    public int getPageCount() {
        ORIENTATION orientation;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView == null || (orientation = this.mOrientation) == ORIENTATION.NULL) {
            return 0;
        }
        if (orientation == ORIENTATION.VERTICAL && recyclerView.computeVerticalScrollExtent() != 0) {
            return this.mRecyclerView.computeVerticalScrollRange() / this.mRecyclerView.computeVerticalScrollExtent();
        }
        if (this.mRecyclerView.computeHorizontalScrollExtent() != 0) {
            return this.mRecyclerView.computeHorizontalScrollRange() / this.mRecyclerView.computeHorizontalScrollExtent();
        }
        return 0;
    }

    public void scrollToPosition(int i2) {
        this.currentPosition = i2;
        if (this.mAnimator == null) {
            this.mOnFlingListener.onFling(0, 0);
        }
        if (this.mAnimator != null) {
            ORIENTATION orientation = this.mOrientation;
            ORIENTATION orientation2 = ORIENTATION.VERTICAL;
            int i3 = orientation == orientation2 ? this.offsetY : this.offsetX;
            int height = (orientation == orientation2 ? this.mRecyclerView.getHeight() : this.mRecyclerView.getWidth()) * i2;
            if (i3 != height) {
                this.mAnimator.setIntValues(i3, height);
                this.mAnimator.start();
            }
        }
    }

    public void setOnPageChangeListener(onPageChangeListener onpagechangelistener) {
        this.mOnPageChangeListener = onpagechangelistener;
    }

    public void setUpRecycleView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new IllegalArgumentException("recycleView must be not null");
        }
        this.mRecyclerView = recyclerView;
        recyclerView.setOnFlingListener(this.mOnFlingListener);
        recyclerView.addOnScrollListener(this.mOnScrollListener);
        recyclerView.setOnTouchListener(this.mOnTouchListener);
        updateLayoutManger();
    }

    public void updateLayoutManger() {
        RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        if (layoutManager != null) {
            if (layoutManager.canScrollVertically()) {
                this.mOrientation = ORIENTATION.VERTICAL;
            } else if (layoutManager.canScrollHorizontally()) {
                this.mOrientation = ORIENTATION.HORIZONTAL;
            } else {
                this.mOrientation = ORIENTATION.NULL;
            }
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.startX = 0;
            this.startY = 0;
            this.offsetX = 0;
            this.offsetY = 0;
        }
    }
}
