package com.psychiatrygarden.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private int dX;
    private int dY;
    private boolean isLoading;
    private boolean isMove;
    private int mItemCount;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    private RecyclerView mRecyclerView;
    private int mTouchSlop;
    private View mViewFooter;
    private int uX;
    private int uY;

    public interface OnLoadListener {
        void onLoad();
    }

    public MySwipeRefreshLayout(@NonNull Context context) {
        super(context);
        this.dX = 0;
        this.dY = 0;
        this.uX = 0;
        this.uY = 0;
        this.isMove = false;
        this.isLoading = false;
        this.mItemCount = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean canLoad() {
        return isBottom() && !this.isLoading && isPullUp();
    }

    private void getView() {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (childAt instanceof ListView) {
                ListView listView = (ListView) childAt;
                this.mListView = listView;
                listView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.utils.MySwipeRefreshLayout.1
                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    }

                    @Override // android.widget.AbsListView.OnScrollListener
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (MySwipeRefreshLayout.this.canLoad()) {
                            MySwipeRefreshLayout.this.loadData();
                        }
                    }
                });
            } else if (childAt instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) childAt;
                this.mRecyclerView = recyclerView;
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.psychiatrygarden.utils.MySwipeRefreshLayout.2
                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView2, int newState) {
                        super.onScrollStateChanged(recyclerView2, newState);
                        if (MySwipeRefreshLayout.this.canLoad()) {
                            MySwipeRefreshLayout.this.loadData();
                        }
                    }

                    @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                    public void onScrolled(@NonNull RecyclerView recyclerView2, int dx, int dy) {
                        super.onScrolled(recyclerView2, dx, dy);
                    }
                });
            }
        }
    }

    private boolean isBottom() {
        ListView listView = this.mListView;
        if (listView == null || listView.getAdapter() == null) {
            return false;
        }
        if (this.mItemCount > 0) {
            if (this.mListView.getAdapter().getCount() < this.mItemCount || this.mListView.getLastVisiblePosition() != this.mListView.getAdapter().getCount() - 1) {
                return false;
            }
        } else if (this.mListView.getLastVisiblePosition() != this.mListView.getAdapter().getCount() - 1) {
            return false;
        }
        return true;
    }

    private boolean isPullUp() {
        return this.dY - this.uY >= this.mTouchSlop;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadData() {
        if (!this.isMove || this.mOnLoadListener == null) {
            return;
        }
        setLoading(true);
        this.mOnLoadListener.onLoad();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            this.dX = (int) event.getX();
            this.dY = (int) event.getY();
            Log.e("TAG", "dX: " + this.dX + "   dY : " + this.dY);
        } else if (action == 1) {
            this.uX = (int) event.getX();
            int y2 = (int) event.getY();
            this.uY = y2;
            if (this.uX != this.dX && y2 != this.dY) {
                this.isMove = true;
            }
            Log.e("TAG", "uX: " + this.uX + "   uY : " + this.uY);
        } else if (action == 2) {
            this.isMove = false;
            if (canLoad()) {
                loadData();
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public boolean getIsLoading() {
        return this.isLoading;
    }

    @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        if (this.mListView == null || this.mRecyclerView == null) {
            getView();
        }
    }

    public void setItemCount(int itemCount) {
        this.mItemCount = itemCount;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
        if (loading) {
            this.mListView.addFooterView(this.mViewFooter);
            return;
        }
        this.mListView.removeFooterView(this.mViewFooter);
        this.uY = 0;
        this.dY = 0;
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        this.mOnLoadListener = loadListener;
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.dX = 0;
        this.dY = 0;
        this.uX = 0;
        this.uY = 0;
        this.isMove = false;
        this.isLoading = false;
        this.mItemCount = -1;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mViewFooter = LayoutInflater.from(context).inflate(R.layout.view_footer, (ViewGroup) null, false);
    }
}
