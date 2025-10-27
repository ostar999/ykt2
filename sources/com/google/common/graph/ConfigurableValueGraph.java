package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes4.dex */
class ConfigurableValueGraph<N, V> extends AbstractValueGraph<N, V> {
    private final boolean allowsSelfLoops;
    protected long edgeCount;
    private final boolean isDirected;
    protected final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    public ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        this(abstractGraphBuilder, abstractGraphBuilder.nodeOrder.createMap(abstractGraphBuilder.expectedNodeCount.or((Optional<Integer>) 10).intValue()), 0L);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> adjacentNodes(N n2) {
        return checkedConnections(n2).adjacentNodes();
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    public final GraphConnections<N, V> checkedConnections(N n2) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n2);
        if (graphConnections != null) {
            return graphConnections;
        }
        Preconditions.checkNotNull(n2);
        throw new IllegalArgumentException("Node " + n2 + " is not an element of this graph.");
    }

    public final boolean containsNode(@NullableDecl N n2) {
        return this.nodeConnections.containsKey(n2);
    }

    @Override // com.google.common.graph.AbstractBaseGraph
    public long edgeCount() {
        return this.edgeCount;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ValueGraph
    @NullableDecl
    public V edgeValueOrDefault(N n2, N n3, @NullableDecl V v2) {
        return (V) edgeValueOrDefault_internal(Preconditions.checkNotNull(n2), Preconditions.checkNotNull(n3), v2);
    }

    public final V edgeValueOrDefault_internal(N n2, N n3, V v2) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n2);
        V vValue = graphConnections == null ? null : graphConnections.value(n3);
        return vValue == null ? v2 : vValue;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(N n2, N n3) {
        return hasEdgeConnecting_internal(Preconditions.checkNotNull(n2), Preconditions.checkNotNull(n3));
    }

    public final boolean hasEdgeConnecting_internal(N n2, N n3) {
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n2);
        return graphConnections != null && graphConnections.successors().contains(n3);
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((ConfigurableValueGraph<N, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((ConfigurableValueGraph<N, V>) obj);
    }

    @Override // com.google.common.graph.ValueGraph
    @NullableDecl
    public V edgeValueOrDefault(EndpointPair<N> endpointPair, @NullableDecl V v2) {
        validateEndpoints(endpointPair);
        return edgeValueOrDefault_internal(endpointPair.nodeU(), endpointPair.nodeV(), v2);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        return isOrderingCompatible(endpointPair) && hasEdgeConnecting_internal(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return checkedConnections(n2).predecessors();
    }

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return checkedConnections(n2).successors();
    }

    public ConfigurableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder, Map<N, GraphConnections<N, V>> map, long j2) {
        this.isDirected = abstractGraphBuilder.directed;
        this.allowsSelfLoops = abstractGraphBuilder.allowsSelfLoops;
        this.nodeOrder = (ElementOrder<N>) abstractGraphBuilder.nodeOrder.cast();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.edgeCount = Graphs.checkNonNegative(j2);
    }
}
