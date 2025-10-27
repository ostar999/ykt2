package com.psychiatrygarden.exam;

import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.exam.RvCountDownHelper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public class RvCountDownHelper {
    private final List<Index> countDownPositions = new ArrayList();
    private Disposable countDownTask;
    private OnTimeCollectListener mListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<BaseViewHolder> rvAdapter;

    public static class Index {
        int index;

        public Index(int index) {
            this.index = index;
        }

        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof Index)) {
                return false;
            }
            Index index = (Index) obj;
            return this == index || index.index == this.index;
        }

        public int hashCode() {
            return this.index * 128;
        }
    }

    public interface OnTimeCollectListener {
        void onTimeCollect(RecyclerView.ViewHolder vh, int pos);
    }

    public RvCountDownHelper(RecyclerView.Adapter<BaseViewHolder> rvAdapter, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.rvAdapter = rvAdapter;
        rvAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.psychiatrygarden.exam.RvCountDownHelper.1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                RvCountDownHelper.this.removeAllPosition();
                super.onChanged();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int positionStart, int itemCount) {
                for (Index index : RvCountDownHelper.this.countDownPositions) {
                    int i2 = index.index;
                    if (i2 >= positionStart) {
                        index.index = i2 + itemCount;
                    }
                }
                super.onItemRangeInserted(positionStart, itemCount);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                for (Index index : RvCountDownHelper.this.countDownPositions) {
                    int i2 = index.index;
                    if (i2 == fromPosition) {
                        index.index = toPosition;
                    } else if (i2 == toPosition) {
                        index.index = fromPosition;
                    }
                }
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                for (int size = RvCountDownHelper.this.countDownPositions.size() - 1; size >= 0; size--) {
                    Index index = (Index) RvCountDownHelper.this.countDownPositions.get(size);
                    int i2 = index.index;
                    if (i2 >= positionStart + itemCount) {
                        index.index = i2 - itemCount;
                    } else if (i2 >= positionStart) {
                        RvCountDownHelper.this.removeCountDownPosition(i2);
                    }
                }
                super.onItemRangeRemoved(positionStart, itemCount);
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeCountDownPosition$0(int i2, Index index) {
        return index.index == i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startCountDown$1(Long l2) throws Exception {
        if (this.countDownTask.isDisposed()) {
            return;
        }
        if (this.countDownPositions.isEmpty()) {
            this.countDownTask.dispose();
            return;
        }
        for (Index index : this.countDownPositions) {
            if (this.recyclerView.getLayoutManager() != null && this.recyclerView.getLayoutManager().findViewByPosition(index.index) != null) {
                if (this.mListener != null) {
                    this.mListener.onTimeCollect(this.recyclerView.findViewHolderForLayoutPosition(index.index), index.index);
                }
                this.rvAdapter.notifyItemChanged(index.index);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startCountDown$2(Throwable th) throws Exception {
        Log.e("倒计时异常", th.getMessage());
    }

    public void addPosition2CountDown(int pos) {
        Index index = new Index(pos);
        if (this.countDownPositions.contains(index)) {
            return;
        }
        this.countDownPositions.add(index);
        startCountDown();
    }

    public void destroy() {
        stopCountDown();
        this.mListener = null;
        this.countDownTask = null;
        this.recyclerView = null;
        this.rvAdapter = null;
    }

    public List<Index> getAllRecordPos() {
        return this.countDownPositions;
    }

    public void removeAllPosition() {
        this.countDownPositions.clear();
    }

    public void removeCountDownPosition(final int pos) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.countDownPositions.removeIf(new Predicate() { // from class: com.psychiatrygarden.exam.j
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return RvCountDownHelper.lambda$removeCountDownPosition$0(pos, (RvCountDownHelper.Index) obj);
                }
            });
        }
    }

    public void setOnTimeCollectListener(OnTimeCollectListener listener) {
        this.mListener = listener;
    }

    public void startCountDown() {
        Disposable disposable = this.countDownTask;
        if (disposable == null || disposable.isDisposed()) {
            this.countDownTask = Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.exam.h
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    this.f15308c.lambda$startCountDown$1((Long) obj);
                }
            }, new Consumer() { // from class: com.psychiatrygarden.exam.i
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) throws Exception {
                    RvCountDownHelper.lambda$startCountDown$2((Throwable) obj);
                }
            });
        }
    }

    public void stopCountDown() {
        Disposable disposable = this.countDownTask;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.countDownTask.dispose();
    }
}
