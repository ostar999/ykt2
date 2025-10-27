package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes4.dex */
abstract class AbstractDirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
    protected final Map<E, N> inEdgeMap;
    protected final Map<E, N> outEdgeMap;
    private int selfLoopCount;

    public AbstractDirectedNetworkConnections(Map<E, N> map, Map<E, N> map2, int i2) {
        this.inEdgeMap = (Map) Preconditions.checkNotNull(map);
        this.outEdgeMap = (Map) Preconditions.checkNotNull(map2);
        this.selfLoopCount = Graphs.checkNonNegative(i2);
        Preconditions.checkState(i2 <= map.size() && i2 <= map2.size());
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addInEdge(E e2, N n2, boolean z2) {
        if (z2) {
            int i2 = this.selfLoopCount + 1;
            this.selfLoopCount = i2;
            Graphs.checkPositive(i2);
        }
        Preconditions.checkState(this.inEdgeMap.put(e2, n2) == null);
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addOutEdge(E e2, N n2) {
        Preconditions.checkState(this.outEdgeMap.put(e2, n2) == null);
    }

    @Override // com.google.common.graph.NetworkConnections
    public N adjacentNode(E e2) {
        return (N) Preconditions.checkNotNull(this.outEdgeMap.get(e2));
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> adjacentNodes() {
        return Sets.union(predecessors(), successors());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> inEdges() {
        return Collections.unmodifiableSet(this.inEdgeMap.keySet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> incidentEdges() {
        return new AbstractSet<E>() { // from class: com.google.common.graph.AbstractDirectedNetworkConnections.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return AbstractDirectedNetworkConnections.this.inEdgeMap.containsKey(obj) || AbstractDirectedNetworkConnections.this.outEdgeMap.containsKey(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return IntMath.saturatedAdd(AbstractDirectedNetworkConnections.this.inEdgeMap.size(), AbstractDirectedNetworkConnections.this.outEdgeMap.size() - AbstractDirectedNetworkConnections.this.selfLoopCount);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<E> iterator() {
                return Iterators.unmodifiableIterator((AbstractDirectedNetworkConnections.this.selfLoopCount == 0 ? Iterables.concat(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet()) : Sets.union(AbstractDirectedNetworkConnections.this.inEdgeMap.keySet(), AbstractDirectedNetworkConnections.this.outEdgeMap.keySet())).iterator());
            }
        };
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> outEdges() {
        return Collections.unmodifiableSet(this.outEdgeMap.keySet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeInEdge(E e2, boolean z2) {
        if (z2) {
            int i2 = this.selfLoopCount - 1;
            this.selfLoopCount = i2;
            Graphs.checkNonNegative(i2);
        }
        return (N) Preconditions.checkNotNull(this.inEdgeMap.remove(e2));
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeOutEdge(E e2) {
        return (N) Preconditions.checkNotNull(this.outEdgeMap.remove(e2));
    }
}
