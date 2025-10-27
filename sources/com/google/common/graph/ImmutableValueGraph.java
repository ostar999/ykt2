package com.google.common.graph;

import androidx.exifinterface.media.ExifInterface;
import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.Immutable;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf = {"N", ExifInterface.GPS_MEASUREMENT_INTERRUPTED})
@Beta
/* loaded from: classes4.dex */
public final class ImmutableValueGraph<N, V> extends ConfigurableValueGraph<N, V> {
    private ImmutableValueGraph(ValueGraph<N, V> valueGraph) {
        super(ValueGraphBuilder.from(valueGraph), getNodeConnections(valueGraph), valueGraph.edges().size());
    }

    private static <N, V> GraphConnections<N, V> connectionsOf(final ValueGraph<N, V> valueGraph, final N n2) {
        Function<N, V> function = new Function<N, V>() { // from class: com.google.common.graph.ImmutableValueGraph.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.base.Function
            public V apply(N n3) {
                return (V) valueGraph.edgeValueOrDefault(n2, n3, null);
            }
        };
        return valueGraph.isDirected() ? DirectedGraphConnections.ofImmutable(valueGraph.predecessors((ValueGraph<N, V>) n2), Maps.asMap(valueGraph.successors((ValueGraph<N, V>) n2), function)) : UndirectedGraphConnections.ofImmutable(Maps.asMap(valueGraph.adjacentNodes(n2), function));
    }

    public static <N, V> ImmutableValueGraph<N, V> copyOf(ValueGraph<N, V> valueGraph) {
        return valueGraph instanceof ImmutableValueGraph ? (ImmutableValueGraph) valueGraph : new ImmutableValueGraph<>(valueGraph);
    }

    private static <N, V> ImmutableMap<N, GraphConnections<N, V>> getNodeConnections(ValueGraph<N, V> valueGraph) {
        ImmutableMap.Builder builder = ImmutableMap.builder();
        for (N n2 : valueGraph.nodes()) {
            builder.put(n2, connectionsOf(valueGraph, n2));
        }
        return builder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set adjacentNodes(Object obj) {
        return super.adjacentNodes(obj);
    }

    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean allowsSelfLoops() {
        return super.allowsSelfLoops();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.ValueGraph
    @NullableDecl
    public /* bridge */ /* synthetic */ Object edgeValueOrDefault(EndpointPair endpointPair, @NullableDecl Object obj) {
        return super.edgeValueOrDefault(endpointPair, obj);
    }

    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public /* bridge */ /* synthetic */ boolean hasEdgeConnecting(EndpointPair endpointPair) {
        return super.hasEdgeConnecting(endpointPair);
    }

    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ boolean isDirected() {
        return super.isDirected();
    }

    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ ElementOrder nodeOrder() {
        return super.nodeOrder();
    }

    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.Graph
    public /* bridge */ /* synthetic */ Set nodes() {
        return super.nodes();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.PredecessorsFunction
    public /* bridge */ /* synthetic */ Set predecessors(Object obj) {
        return super.predecessors((ImmutableValueGraph<N, V>) obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.BaseGraph, com.google.common.graph.SuccessorsFunction
    public /* bridge */ /* synthetic */ Set successors(Object obj) {
        return super.successors((ImmutableValueGraph<N, V>) obj);
    }

    @Deprecated
    public static <N, V> ImmutableValueGraph<N, V> copyOf(ImmutableValueGraph<N, V> immutableValueGraph) {
        return (ImmutableValueGraph) Preconditions.checkNotNull(immutableValueGraph);
    }

    @Override // com.google.common.graph.AbstractValueGraph, com.google.common.graph.ValueGraph
    public ImmutableGraph<N> asGraph() {
        return new ImmutableGraph<>(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.ValueGraph
    @NullableDecl
    public /* bridge */ /* synthetic */ Object edgeValueOrDefault(Object obj, Object obj2, @NullableDecl Object obj3) {
        return super.edgeValueOrDefault(obj, obj2, obj3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.ConfigurableValueGraph, com.google.common.graph.AbstractValueGraph, com.google.common.graph.AbstractBaseGraph, com.google.common.graph.BaseGraph
    public /* bridge */ /* synthetic */ boolean hasEdgeConnecting(Object obj, Object obj2) {
        return super.hasEdgeConnecting(obj, obj2);
    }
}
