package com.google.common.graph;

import java.util.Set;

/* loaded from: classes4.dex */
abstract class ForwardingGraph<N> extends AbstractGraph<N> {
    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n2) {
        return delegate().adjacentNodes(n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return delegate().allowsSelfLoops();
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public int degree(N n2) {
        return delegate().degree(n2);
    }

    public abstract BaseGraph<N> delegate();

    @Override // com.google.common.graph.AbstractBaseGraph
    public long edgeCount() {
        return delegate().edges().size();
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(N n2, N n3) {
        return delegate().hasEdgeConnecting(n2, n3);
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public int inDegree(N n2) {
        return delegate().inDegree(n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean isDirected() {
        return delegate().isDirected();
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return delegate().nodeOrder();
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> nodes() {
        return delegate().nodes();
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public int outDegree(N n2) {
        return delegate().outDegree(n2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((ForwardingGraph<N>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((ForwardingGraph<N>) obj);
    }

    @Override // com.google.common.graph.AbstractGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        return delegate().hasEdgeConnecting(endpointPair);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return delegate().predecessors((BaseGraph<N>) n2);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return delegate().successors((BaseGraph<N>) n2);
    }
}
