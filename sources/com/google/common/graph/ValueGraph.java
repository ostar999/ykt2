package com.google.common.graph;

import com.google.common.annotations.Beta;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes4.dex */
public interface ValueGraph<N, V> extends BaseGraph<N> {
    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    Set<N> adjacentNodes(N n2);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    boolean allowsSelfLoops();

    Graph<N> asGraph();

    @Override // com.google.common.graph.BaseGraph
    int degree(N n2);

    @NullableDecl
    V edgeValueOrDefault(EndpointPair<N> endpointPair, @NullableDecl V v2);

    @NullableDecl
    V edgeValueOrDefault(N n2, N n3, @NullableDecl V v2);

    @Override // com.google.common.graph.BaseGraph
    Set<EndpointPair<N>> edges();

    boolean equals(@NullableDecl Object obj);

    @Override // com.google.common.graph.BaseGraph
    boolean hasEdgeConnecting(EndpointPair<N> endpointPair);

    @Override // com.google.common.graph.BaseGraph
    boolean hasEdgeConnecting(N n2, N n3);

    int hashCode();

    @Override // com.google.common.graph.BaseGraph
    int inDegree(N n2);

    @Override // com.google.common.graph.BaseGraph
    Set<EndpointPair<N>> incidentEdges(N n2);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    boolean isDirected();

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    ElementOrder<N> nodeOrder();

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    Set<N> nodes();

    @Override // com.google.common.graph.BaseGraph
    int outDegree(N n2);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    Set<N> predecessors(N n2);

    @Override // com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    Set<N> successors(N n2);
}
