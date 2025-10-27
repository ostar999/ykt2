package androidx.room;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.room.InvalidationTracker;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.Set;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public class RxRoom {
    public static final Object NOTHING = new Object();

    @Deprecated
    public RxRoom() {
    }

    public static Flowable<Object> createFlowable(final RoomDatabase roomDatabase, final String... strArr) {
        return Flowable.create(new FlowableOnSubscribe<Object>() { // from class: androidx.room.RxRoom.1
            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(final FlowableEmitter<Object> flowableEmitter) throws Exception {
                final InvalidationTracker.Observer observer = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.RxRoom.1.1
                    @Override // androidx.room.InvalidationTracker.Observer
                    public void onInvalidated(@NonNull Set<String> set) {
                        if (flowableEmitter.isCancelled()) {
                            return;
                        }
                        flowableEmitter.onNext(RxRoom.NOTHING);
                    }
                };
                if (!flowableEmitter.isCancelled()) {
                    roomDatabase.getInvalidationTracker().addObserver(observer);
                    flowableEmitter.setDisposable(Disposables.fromAction(new Action() { // from class: androidx.room.RxRoom.1.2
                        @Override // io.reactivex.functions.Action
                        public void run() throws Exception {
                            roomDatabase.getInvalidationTracker().removeObserver(observer);
                        }
                    }));
                }
                if (flowableEmitter.isCancelled()) {
                    return;
                }
                flowableEmitter.onNext(RxRoom.NOTHING);
            }
        }, BackpressureStrategy.LATEST);
    }

    public static Observable<Object> createObservable(final RoomDatabase roomDatabase, final String... strArr) {
        return Observable.create(new ObservableOnSubscribe<Object>() { // from class: androidx.room.RxRoom.3
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<Object> observableEmitter) throws Exception {
                final InvalidationTracker.Observer observer = new InvalidationTracker.Observer(strArr) { // from class: androidx.room.RxRoom.3.1
                    @Override // androidx.room.InvalidationTracker.Observer
                    public void onInvalidated(@NonNull Set<String> set) {
                        observableEmitter.onNext(RxRoom.NOTHING);
                    }
                };
                roomDatabase.getInvalidationTracker().addObserver(observer);
                observableEmitter.setDisposable(Disposables.fromAction(new Action() { // from class: androidx.room.RxRoom.3.2
                    @Override // io.reactivex.functions.Action
                    public void run() throws Exception {
                        roomDatabase.getInvalidationTracker().removeObserver(observer);
                    }
                }));
                observableEmitter.onNext(RxRoom.NOTHING);
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static <T> Flowable<T> createFlowable(RoomDatabase roomDatabase, String[] strArr, Callable<T> callable) {
        Scheduler schedulerFrom = Schedulers.from(roomDatabase.getQueryExecutor());
        final Maybe maybeFromCallable = Maybe.fromCallable(callable);
        return (Flowable<T>) createFlowable(roomDatabase, strArr).observeOn(schedulerFrom).flatMapMaybe(new Function<Object, MaybeSource<T>>() { // from class: androidx.room.RxRoom.2
            @Override // io.reactivex.functions.Function
            public MaybeSource<T> apply(Object obj) throws Exception {
                return maybeFromCallable;
            }
        });
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static <T> Observable<T> createObservable(RoomDatabase roomDatabase, String[] strArr, Callable<T> callable) {
        Scheduler schedulerFrom = Schedulers.from(roomDatabase.getQueryExecutor());
        final Maybe maybeFromCallable = Maybe.fromCallable(callable);
        return (Observable<T>) createObservable(roomDatabase, strArr).observeOn(schedulerFrom).flatMapMaybe(new Function<Object, MaybeSource<T>>() { // from class: androidx.room.RxRoom.4
            @Override // io.reactivex.functions.Function
            public MaybeSource<T> apply(Object obj) throws Exception {
                return maybeFromCallable;
            }
        });
    }
}
