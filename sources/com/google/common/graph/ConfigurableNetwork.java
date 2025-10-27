package com.google.common.graph;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes4.dex */
class ConfigurableNetwork<N, E> extends AbstractNetwork<N, E> {
    private final boolean allowsParallelEdges;
    private final boolean allowsSelfLoops;
    private final ElementOrder<E> edgeOrder;
    protected final MapIteratorCache<E, N> edgeToReferenceNode;
    private final boolean isDirected;
    protected final MapIteratorCache<N, NetworkConnections<N, E>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    public ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        this(networkBuilder, networkBuilder.nodeOrder.createMap(networkBuilder.expectedNodeCount.or((Optional<Integer>) 10).intValue()), networkBuilder.edgeOrder.createMap(networkBuilder.expectedEdgeCount.or((Optional<Integer>) 20).intValue()));
    }

    @Override // com.google.common.graph.Network
    public Set<N> adjacentNodes(N n2) {
        return checkedConnections(n2).adjacentNodes();
    }

    @Override // com.google.common.graph.Network
    public boolean allowsParallelEdges() {
        return this.allowsParallelEdges;
    }

    @Override // com.google.common.graph.Network
    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    public final NetworkConnections<N, E> checkedConnections(N n2) {
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n2);
        if (networkConnections != null) {
            return networkConnections;
        }
        Preconditions.checkNotNull(n2);
        throw new IllegalArgumentException(String.format("Node %s is not an element of this graph.", n2));
    }

    public final N checkedReferenceNode(E e2) {
        N n2 = this.edgeToReferenceNode.get(e2);
        if (n2 != null) {
            return n2;
        }
        Preconditions.checkNotNull(e2);
        throw new IllegalArgumentException(String.format("Edge %s is not an element of this graph.", e2));
    }

    public final boolean containsEdge(@NullableDecl E e2) {
        return this.edgeToReferenceNode.containsKey(e2);
    }

    public final boolean containsNode(@NullableDecl N n2) {
        return this.nodeConnections.containsKey(n2);
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<E> edgeOrder() {
        return this.edgeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<E> edges() {
        return this.edgeToReferenceNode.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.AbstractNetwork, com.google.common.graph.Network
    public Set<E> edgesConnecting(N n2, N n3) {
        NetworkConnections<N, E> networkConnectionsCheckedConnections = checkedConnections(n2);
        if (!this.allowsSelfLoops && n2 == n3) {
            return ImmutableSet.of();
        }
        Preconditions.checkArgument(containsNode(n3), "Node %s is not an element of this graph.", n3);
        return networkConnectionsCheckedConnections.edgesConnecting(n3);
    }

    @Override // com.google.common.graph.Network
    public Set<E> inEdges(N n2) {
        return checkedConnections(n2).inEdges();
    }

    @Override // com.google.common.graph.Network
    public Set<E> incidentEdges(N n2) {
        return checkedConnections(n2).incidentEdges();
    }

    @Override // com.google.common.graph.Network
    public EndpointPair<N> incidentNodes(E e2) {
        N nCheckedReferenceNode = checkedReferenceNode(e2);
        return EndpointPair.of(this, nCheckedReferenceNode, this.nodeConnections.get(nCheckedReferenceNode).adjacentNode(e2));
    }

    @Override // com.google.common.graph.Network
    public boolean isDirected() {
        return this.isDirected;
    }

    @Override // com.google.common.graph.Network
    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    @Override // com.google.common.graph.Network
    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    @Override // com.google.common.graph.Network
    public Set<E> outEdges(N n2) {
        return checkedConnections(n2).outEdges();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Iterable predecessors(Object obj) {
        return predecessors((ConfigurableNetwork<N, E>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Iterable successors(Object obj) {
        return successors((ConfigurableNetwork<N, E>) obj);
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.PredecessorsFunction
    public Set<N> predecessors(N n2) {
        return checkedConnections(n2).predecessors();
    }

    @Override // com.google.common.graph.Network, com.google.common.graph.SuccessorsFunction
    public Set<N> successors(N n2) {
        return checkedConnections(n2).successors();
    }

    public ConfigurableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder, Map<N, NetworkConnections<N, E>> map, Map<E, N> map2) {
        this.isDirected = networkBuilder.directed;
        this.allowsParallelEdges = networkBuilder.allowsParallelEdges;
        this.allowsSelfLoops = networkBuilder.allowsSelfLoops;
        this.nodeOrder = (ElementOrder<N>) networkBuilder.nodeOrder.cast();
        this.edgeOrder = (ElementOrder<E>) networkBuilder.edgeOrder.cast();
        this.nodeConnections = map instanceof TreeMap ? new MapRetrievalCache<>(map) : new MapIteratorCache<>(map);
        this.edgeToReferenceNode = new MapIteratorCache<>(map2);
    }
}
