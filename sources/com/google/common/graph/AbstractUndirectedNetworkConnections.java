package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
abstract class AbstractUndirectedNetworkConnections<N, E> implements NetworkConnections<N, E> {
    protected final Map<E, N> incidentEdgeMap;

    public AbstractUndirectedNetworkConnections(Map<E, N> map) {
        this.incidentEdgeMap = (Map) Preconditions.checkNotNull(map);
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addInEdge(E e2, N n2, boolean z2) {
        if (z2) {
            return;
        }
        addOutEdge(e2, n2);
    }

    @Override // com.google.common.graph.NetworkConnections
    public void addOutEdge(E e2, N n2) {
        Preconditions.checkState(this.incidentEdgeMap.put(e2, n2) == null);
    }

    @Override // com.google.common.graph.NetworkConnections
    public N adjacentNode(E e2) {
        return (N) Preconditions.checkNotNull(this.incidentEdgeMap.get(e2));
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> inEdges() {
        return incidentEdges();
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> incidentEdges() {
        return Collections.unmodifiableSet(this.incidentEdgeMap.keySet());
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<E> outEdges() {
        return incidentEdges();
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> predecessors() {
        return adjacentNodes();
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeInEdge(E e2, boolean z2) {
        if (z2) {
            return null;
        }
        return removeOutEdge(e2);
    }

    @Override // com.google.common.graph.NetworkConnections
    public N removeOutEdge(E e2) {
        return (N) Preconditions.checkNotNull(this.incidentEdgeMap.remove(e2));
    }

    @Override // com.google.common.graph.NetworkConnections
    public Set<N> successors() {
        return adjacentNodes();
    }
}
