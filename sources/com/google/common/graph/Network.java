package com.google.common.graph;

import com.google.common.annotations.Beta;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes4.dex */
public interface Network<N, E> extends SuccessorsFunction<N>, PredecessorsFunction<N> {
    Set<E> adjacentEdges(E e2);

    Set<N> adjacentNodes(N n2);

    boolean allowsParallelEdges();

    boolean allowsSelfLoops();

    Graph<N> asGraph();

    int degree(N n2);

    @NullableDecl
    E edgeConnectingOrNull(EndpointPair<N> endpointPair);

    @NullableDecl
    E edgeConnectingOrNull(N n2, N n3);

    ElementOrder<E> edgeOrder();

    Set<E> edges();

    Set<E> edgesConnecting(EndpointPair<N> endpointPair);

    Set<E> edgesConnecting(N n2, N n3);

    boolean equals(@NullableDecl Object obj);

    boolean hasEdgeConnecting(EndpointPair<N> endpointPair);

    boolean hasEdgeConnecting(N n2, N n3);

    int hashCode();

    int inDegree(N n2);

    Set<E> inEdges(N n2);

    Set<E> incidentEdges(N n2);

    EndpointPair<N> incidentNodes(E e2);

    boolean isDirected();

    ElementOrder<N> nodeOrder();

    Set<N> nodes();

    int outDegree(N n2);

    Set<E> outEdges(N n2);

    Set<N> predecessors(N n2);

    @Override // com.google.common.graph.SuccessorsFunction
    Set<N> successors(N n2);
}
