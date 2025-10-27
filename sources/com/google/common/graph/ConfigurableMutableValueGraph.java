package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;

/* loaded from: classes4.dex */
final class ConfigurableMutableValueGraph<N, V> extends ConfigurableValueGraph<N, V> implements MutableValueGraph<N, V> {
    public ConfigurableMutableValueGraph(AbstractGraphBuilder<? super N> abstractGraphBuilder) {
        super(abstractGraphBuilder);
    }

    @CanIgnoreReturnValue
    private GraphConnections<N, V> addNodeInternal(N n2) {
        GraphConnections<N, V> graphConnectionsNewConnections = newConnections();
        Preconditions.checkState(this.nodeConnections.put(n2, graphConnectionsNewConnections) == null);
        return graphConnectionsNewConnections;
    }

    private GraphConnections<N, V> newConnections() {
        return isDirected() ? DirectedGraphConnections.of() : UndirectedGraphConnections.of();
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public boolean addNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        if (containsNode(n2)) {
            return false;
        }
        addNodeInternal(n2);
        return true;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public V putEdgeValue(N n2, N n3, V v2) {
        Preconditions.checkNotNull(n2, "nodeU");
        Preconditions.checkNotNull(n3, "nodeV");
        Preconditions.checkNotNull(v2, "value");
        if (!allowsSelfLoops()) {
            Preconditions.checkArgument(!n2.equals(n3), "Cannot add self-loop edge on node %s, as self-loops are not allowed. To construct a graph that allows self-loops, call allowsSelfLoops(true) on the Builder.", n2);
        }
        GraphConnections<N, V> graphConnectionsAddNodeInternal = this.nodeConnections.get(n2);
        if (graphConnectionsAddNodeInternal == null) {
            graphConnectionsAddNodeInternal = addNodeInternal(n2);
        }
        V vAddSuccessor = graphConnectionsAddNodeInternal.addSuccessor(n3, v2);
        GraphConnections<N, V> graphConnectionsAddNodeInternal2 = this.nodeConnections.get(n3);
        if (graphConnectionsAddNodeInternal2 == null) {
            graphConnectionsAddNodeInternal2 = addNodeInternal(n3);
        }
        graphConnectionsAddNodeInternal2.addPredecessor(n2, v2);
        if (vAddSuccessor == null) {
            long j2 = this.edgeCount + 1;
            this.edgeCount = j2;
            Graphs.checkPositive(j2);
        }
        return vAddSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public V removeEdge(N n2, N n3) {
        Preconditions.checkNotNull(n2, "nodeU");
        Preconditions.checkNotNull(n3, "nodeV");
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n2);
        GraphConnections<N, V> graphConnections2 = this.nodeConnections.get(n3);
        if (graphConnections == null || graphConnections2 == null) {
            return null;
        }
        V vRemoveSuccessor = graphConnections.removeSuccessor(n3);
        if (vRemoveSuccessor != null) {
            graphConnections2.removePredecessor(n2);
            long j2 = this.edgeCount - 1;
            this.edgeCount = j2;
            Graphs.checkNonNegative(j2);
        }
        return vRemoveSuccessor;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public boolean removeNode(N n2) {
        Preconditions.checkNotNull(n2, "node");
        GraphConnections<N, V> graphConnections = this.nodeConnections.get(n2);
        if (graphConnections == null) {
            return false;
        }
        if (allowsSelfLoops() && graphConnections.removeSuccessor(n2) != null) {
            graphConnections.removePredecessor(n2);
            this.edgeCount--;
        }
        Iterator<N> it = graphConnections.successors().iterator();
        while (it.hasNext()) {
            this.nodeConnections.getWithoutCaching(it.next()).removePredecessor(n2);
            this.edgeCount--;
        }
        if (isDirected()) {
            Iterator<N> it2 = graphConnections.predecessors().iterator();
            while (it2.hasNext()) {
                Preconditions.checkState(this.nodeConnections.getWithoutCaching(it2.next()).removeSuccessor(n2) != null);
                this.edgeCount--;
            }
        }
        this.nodeConnections.remove(n2);
        Graphs.checkNonNegative(this.edgeCount);
        return true;
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public V removeEdge(EndpointPair<N> endpointPair) {
        validateEndpoints(endpointPair);
        return removeEdge(endpointPair.nodeU(), endpointPair.nodeV());
    }

    @Override // com.google.common.graph.MutableValueGraph
    @CanIgnoreReturnValue
    public V putEdgeValue(EndpointPair<N> endpointPair, V v2) {
        validateEndpoints(endpointPair);
        return putEdgeValue(endpointPair.nodeU(), endpointPair.nodeV(), v2);
    }
}
