package com.google.common.graph;

import java.util.Set;

/* loaded from: classes4.dex */
abstract class ForwardingNetwork<N, E> extends AbstractNetwork<N, E> {
    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> adjacentEdges(E e2) {
        return delegate().adjacentEdges(e2);
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N n2) {
        return delegate().adjacentNodes(n2);
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return delegate().allowsParallelEdges();
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return delegate().allowsSelfLoops();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public int degree(N n2) {
        return delegate().degree(n2);
    }

    public abstract Network<N, E> delegate();

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public E edgeConnectingOrNull(N n2, N n3) {
        return delegate().edgeConnectingOrNull(n2, n3);
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return delegate().edgeOrder();
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return delegate().edges();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N n2, N n3) {
        return delegate().edgesConnecting(n2, n3);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public boolean hasEdgeConnecting(N n2, N n3) {
        return delegate().hasEdgeConnecting(n2, n3);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public int inDegree(N n2) {
        return delegate().inDegree(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N n2) {
        return delegate().inEdges(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N n2) {
        return delegate().incidentEdges(n2);
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E e2) {
        return delegate().incidentNodes(e2);
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return delegate().isDirected();
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return delegate().nodeOrder();
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return delegate().nodes();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public int outDegree(N n2) {
        return delegate().outDegree(n2);
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N n2) {
        return delegate().outEdges(n2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((ForwardingNetwork<N, E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((ForwardingNetwork<N, E>) obj);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public E edgeConnectingOrNull(EndpointPair<N> endpointPair) {
        return delegate().edgeConnectingOrNull(endpointPair);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(EndpointPair<N> endpointPair) {
        return delegate().edgesConnecting(endpointPair);
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        return delegate().hasEdgeConnecting(endpointPair);
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return delegate().predecessors((Network<N, E>) n2);
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return delegate().successors((Network<N, E>) n2);
    }
}
