package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
/* loaded from: classes4.dex */
abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {

    public abstract class CollectionFutureRunningState extends AggregateFuture<V, C>.RunningState {
        private List<Optional<V>> values;

        public CollectionFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z2) {
            super(immutableCollection, z2, true);
            this.values = immutableCollection.isEmpty() ? ImmutableList.of() : Lists.newArrayListWithCapacity(immutableCollection.size());
            for (int i2 = 0; i2 < immutableCollection.size(); i2++) {
                this.values.add(null);
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public final void collectOneValue(boolean z2, int i2, @NullableDecl V v2) {
            List<Optional<V>> list = this.values;
            if (list != null) {
                list.set(i2, Optional.fromNullable(v2));
            } else {
                Preconditions.checkState(z2 || CollectionFuture.this.isCancelled(), "Future was done before all dependencies completed");
            }
        }

        public abstract C combine(List<Optional<V>> list);

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public final void handleAllCompleted() {
            List<Optional<V>> list = this.values;
            if (list != null) {
                CollectionFuture.this.set(combine(list));
            } else {
                Preconditions.checkState(CollectionFuture.this.isDone());
            }
        }

        @Override // com.google.common.util.concurrent.AggregateFuture.RunningState
        public void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.values = null;
        }
    }

    public static final class ListFuture<V> extends CollectionFuture<V, List<V>> {

        public final class ListFutureRunningState extends CollectionFuture<V, List<V>>.CollectionFutureRunningState {
            public ListFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z2) {
                super(immutableCollection, z2);
            }

            @Override // com.google.common.util.concurrent.CollectionFuture.CollectionFutureRunningState
            public List<V> combine(List<Optional<V>> list) {
                ArrayList arrayListNewArrayListWithCapacity = Lists.newArrayListWithCapacity(list.size());
                Iterator<Optional<V>> it = list.iterator();
                while (it.hasNext()) {
                    Optional<V> next = it.next();
                    arrayListNewArrayListWithCapacity.add(next != null ? next.orNull() : null);
                }
                return Collections.unmodifiableList(arrayListNewArrayListWithCapacity);
            }
        }

        public ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> immutableCollection, boolean z2) {
            init(new ListFutureRunningState(immutableCollection, z2));
        }
    }
}
