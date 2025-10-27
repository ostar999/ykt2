package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;

/* loaded from: classes4.dex */
final class ConfigurableMutableNetwork<N, E> extends ConfigurableNetwork<N, E> implements MutableNetwork<N, E> {
    public ConfigurableMutableNetwork(NetworkBuilder<? super N, ? super E> networkBuilder) {
        super(networkBuilder);
    }

    @CanIgnoreReturnValue
    private NetworkConnections<N, E> addNodeInternal(N n2) {
        NetworkConnections<N, E> networkConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.nodeConnections.put(n2, networkConnectionsNewConnections) == null);
        return networkConnectionsNewConnections;
    }

    private NetworkConnections<N, E> newConnections() {
        return isDirected() ? allowsParallelEdges() ? DirectedMultiNetworkConnections.of() : DirectedNetworkConnections.of() : allowsParallelEdges() ? UndirectedMultiNetworkConnections.of() : UndirectedNetworkConnections.of();
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addEdge(N n2, N n3, E e2) {
        Preconditions.checkNotNull(n2, "nodeU");
        Preconditions.checkNotNull(n3, "nodeV");
        Preconditions.checkNotNull(e2, "edge");
        if (containsEdge(e2)) {
            EndpointPair<N> endpointPairIncidentNodes = incidentNodes(e2);
            EndpointPair endpointPairOf = EndpointPair.of(this, n2, n3);
            Preconditions.checkArgument(endpointPairIncidentNodes.equals(endpointPairOf), "Edge %s already exists between the following nodes: %s, so it cannot be reused to connect the following nodes: %s.", e2, endpointPairIncidentNodes, endpointPairOf);
            return false;
        }
        NetworkConnections<N, E> networkConnectionsAddNodeInternal = this.nodeConnections.get(n2);
        if (!allowsParallelEdges()) {
            Preconditions.checkArgument(networkConnectionsAddNodeInternal == null || !networkConnectionsAddNodeInternal.successors().contains(n3), "Nodes %s and %s are already connected by a different edge. To construct a graph that allows parallel edges, call allowsParallelEdges(true) on the Builder.", n2, n3);
        }
        boolean zEquals = n2.equals(n3);
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!zEquals, "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n2);
        }
        if (networkConnectionsAddNodeInternal == null) {
            networkConnectionsAddNodeInternal = addNodeInternal(n2);
        }
        networkConnectionsAddNodeInternal.addOutEdge(e2, n3);
        NetworkConnections<N, E> networkConnectionsAddNodeInternal2 = this.nodeConnections.get(n3);
        if (networkConnectionsAddNodeInternal2 == null) {
            networkConnectionsAddNodeInternal2 = addNodeInternal(n3);
        }
        networkConnectionsAddNodeInternal2.addInEdge(e2, n2, zEquals);
        this.edgeToReferenceNode.put(e2, n2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        if (containsNode(n2)) {
            return false;
        }
        addNodeInternal(n2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeEdge(E e2) {
        Preconditions.checkNotNull(e2, "edge");
        N n2 = this.edgeToReferenceNode.get(e2);
        boolean z2 = false;
        if (n2 == null) {
            return false;
        }
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n2);
        N nAdjacentNode = networkConnections.adjacentNode(e2);
        NetworkConnections<N, E> networkConnections2 = this.nodeConnections.get(nAdjacentNode);
        networkConnections.removeOutEdge(e2);
        if (allowsSelfLoops() && n2.equals(nAdjacentNode)) {
            z2 = true;
        }
        networkConnections2.removeInEdge(e2, z2);
        this.edgeToReferenceNode.remove(e2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean removeNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        NetworkConnections<N, E> networkConnections = this.nodeConnections.get(n2);
        if (networkConnections == null) {
            return false;
        }
        UnmodifiableIterator<E> it = ImmutableList.copyOf((Collection) networkConnections.incidentEdges()).iterator();
        while (it.hasNext()) {
            removeEdge(it.next());
        }
        this.nodeConnections.remove(n2);
        return true;
    }

    @Override // com.google.common.graph.MutableNetwork
    @CanIgnoreReturnValue
    public boolean addEdge(EndpointPair<N> endpointPair, E e2) {
        validateEndpoints(endpointPair);
        return addEdge(endpointPair.nodeU(), endpointPair.nodeV(), e2);
    }
}
