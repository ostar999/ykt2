package androidx.camera.core.impl.utils.futures;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import cn.hutool.core.text.StrPool;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

@RequiresApi(21)
/* loaded from: classes.dex */
class ListFuture<V> implements ListenableFuture<List<V>> {
    private final boolean mAllMustSucceed;

    @Nullable
    List<? extends ListenableFuture<? extends V>> mFutures;

    @NonNull
    private final AtomicInteger mRemaining;

    @NonNull
    private final ListenableFuture<List<V>> mResult = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver<List<V>>() { // from class: androidx.camera.core.impl.utils.futures.ListFuture.1
        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
        public Object attachCompleter(@NonNull CallbackToFutureAdapter.Completer<List<V>> completer) {
            Preconditions.checkState(ListFuture.this.mResultNotifier == null, "The result can only set once!");
            ListFuture.this.mResultNotifier = completer;
            return "ListFuture[" + this + StrPool.BRACKET_END;
        }
    });
    CallbackToFutureAdapter.Completer<List<V>> mResultNotifier;

    @Nullable
    List<V> mValues;

    public ListFuture(@NonNull List<? extends ListenableFuture<? extends V>> list, boolean z2, @NonNull Executor executor) {
        this.mFutures = (List) Preconditions.checkNotNull(list);
        this.mValues = new ArrayList(list.size());
        this.mAllMustSucceed = z2;
        this.mRemaining = new AtomicInteger(list.size());
        init(executor);
    }

    private void callAllGets() throws InterruptedException {
        List<? extends ListenableFuture<? extends V>> list = this.mFutures;
        if (list == null || isDone()) {
            return;
        }
        for (ListenableFuture<? extends V> listenableFuture : list) {
            while (!listenableFuture.isDone()) {
                try {
                    listenableFuture.get();
                } catch (Error e2) {
                    throw e2;
                } catch (InterruptedException e3) {
                    throw e3;
                } catch (Throwable unused) {
                    if (this.mAllMustSucceed) {
                        return;
                    }
                }
            }
        }
    }

    private void init(@NonNull Executor executor) {
        addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.ListFuture.2
            @Override // java.lang.Runnable
            public void run() {
                ListFuture listFuture = ListFuture.this;
                listFuture.mValues = null;
                listFuture.mFutures = null;
            }
        }, CameraXExecutors.directExecutor());
        if (this.mFutures.isEmpty()) {
            this.mResultNotifier.set(new ArrayList(this.mValues));
            return;
        }
        for (int i2 = 0; i2 < this.mFutures.size(); i2++) {
            this.mValues.add(null);
        }
        List<? extends ListenableFuture<? extends V>> list = this.mFutures;
        for (final int i3 = 0; i3 < list.size(); i3++) {
            final ListenableFuture<? extends V> listenableFuture = list.get(i3);
            listenableFuture.addListener(new Runnable() { // from class: androidx.camera.core.impl.utils.futures.ListFuture.3
                @Override // java.lang.Runnable
                public void run() {
                    ListFuture.this.setOneValue(i3, listenableFuture);
                }
            }, executor);
        }
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(@NonNull Runnable runnable, @NonNull Executor executor) {
        this.mResult.addListener(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z2) {
        List<? extends ListenableFuture<? extends V>> list = this.mFutures;
        if (list != null) {
            Iterator<? extends ListenableFuture<? extends V>> it = list.iterator();
            while (it.hasNext()) {
                it.next().cancel(z2);
            }
        }
        return this.mResult.cancel(z2);
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.mResult.isCancelled();
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.mResult.isDone();
    }

    public void setOneValue(int i2, @NonNull Future<? extends V> future) {
        CallbackToFutureAdapter.Completer<List<V>> completer;
        ArrayList arrayList;
        int iDecrementAndGet;
        List<V> list = this.mValues;
        if (isDone() || list == null) {
            Preconditions.checkState(this.mAllMustSucceed, "Future was done before all dependencies completed");
            return;
        }
        try {
            try {
                try {
                    try {
                        Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                        list.set(i2, Futures.getUninterruptibly(future));
                        iDecrementAndGet = this.mRemaining.decrementAndGet();
                        Preconditions.checkState(iDecrementAndGet >= 0, "Less than 0 remaining futures");
                    } catch (Error e2) {
                        this.mResultNotifier.setException(e2);
                        int iDecrementAndGet2 = this.mRemaining.decrementAndGet();
                        Preconditions.checkState(iDecrementAndGet2 >= 0, "Less than 0 remaining futures");
                        if (iDecrementAndGet2 != 0) {
                            return;
                        }
                        List<V> list2 = this.mValues;
                        if (list2 != null) {
                            completer = this.mResultNotifier;
                            arrayList = new ArrayList(list2);
                        }
                    } catch (CancellationException unused) {
                        if (this.mAllMustSucceed) {
                            cancel(false);
                        }
                        int iDecrementAndGet3 = this.mRemaining.decrementAndGet();
                        Preconditions.checkState(iDecrementAndGet3 >= 0, "Less than 0 remaining futures");
                        if (iDecrementAndGet3 != 0) {
                            return;
                        }
                        List<V> list3 = this.mValues;
                        if (list3 != null) {
                            completer = this.mResultNotifier;
                            arrayList = new ArrayList(list3);
                        }
                    }
                } catch (RuntimeException e3) {
                    if (this.mAllMustSucceed) {
                        this.mResultNotifier.setException(e3);
                    }
                    int iDecrementAndGet4 = this.mRemaining.decrementAndGet();
                    Preconditions.checkState(iDecrementAndGet4 >= 0, "Less than 0 remaining futures");
                    if (iDecrementAndGet4 != 0) {
                        return;
                    }
                    List<V> list4 = this.mValues;
                    if (list4 != null) {
                        completer = this.mResultNotifier;
                        arrayList = new ArrayList(list4);
                    }
                }
            } catch (ExecutionException e4) {
                if (this.mAllMustSucceed) {
                    this.mResultNotifier.setException(e4.getCause());
                }
                int iDecrementAndGet5 = this.mRemaining.decrementAndGet();
                Preconditions.checkState(iDecrementAndGet5 >= 0, "Less than 0 remaining futures");
                if (iDecrementAndGet5 != 0) {
                    return;
                }
                List<V> list5 = this.mValues;
                if (list5 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(list5);
                }
            }
            if (iDecrementAndGet == 0) {
                List<V> list6 = this.mValues;
                if (list6 != null) {
                    completer = this.mResultNotifier;
                    arrayList = new ArrayList(list6);
                    completer.set(arrayList);
                    return;
                }
                Preconditions.checkState(isDone());
            }
        } catch (Throwable th) {
            int iDecrementAndGet6 = this.mRemaining.decrementAndGet();
            Preconditions.checkState(iDecrementAndGet6 >= 0, "Less than 0 remaining futures");
            if (iDecrementAndGet6 == 0) {
                List<V> list7 = this.mValues;
                if (list7 != null) {
                    this.mResultNotifier.set(new ArrayList(list7));
                } else {
                    Preconditions.checkState(isDone());
                }
            }
            throw th;
        }
    }

    @Override // java.util.concurrent.Future
    @Nullable
    public List<V> get() throws ExecutionException, InterruptedException {
        callAllGets();
        return this.mResult.get();
    }

    @Override // java.util.concurrent.Future
    public List<V> get(long j2, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        return this.mResult.get(j2, timeUnit);
    }
}
