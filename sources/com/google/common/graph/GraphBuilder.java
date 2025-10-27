package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

@Beta
/* loaded from: classes4.dex */
public final class GraphBuilder<N> extends AbstractGraphBuilder<N> {
    private GraphBuilder(boolean z2) {
        super(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private <N1 extends N> GraphBuilder<N1> cast() {
        return this;
    }

    public static GraphBuilder<Object> directed() {
        return new GraphBuilder<>(true);
    }

    public static <N> GraphBuilder<N> from(Graph<N> graph) {
        return (GraphBuilder<N>) new GraphBuilder(graph.isDirected()).allowsSelfLoops(graph.allowsSelfLoops()).nodeOrder(graph.nodeOrder());
    }

    public static GraphBuilder<Object> undirected() {
        return new GraphBuilder<>(false);
    }

    public GraphBuilder<N> allowsSelfLoops(boolean z2) {
        this.allowsSelfLoops = z2;
        return this;
    }

    public <N1 extends N> MutableGraph<N1> build() {
        return new ConfigurableMutableGraph(this);
    }

    public GraphBuilder<N> expectedNodeCount(int i2) {
        this.expectedNodeCount = Optional.of(Integer.valueOf(Graphs.checkNonNegative(i2)));
        return this;
    }

    public <N1 extends N> GraphBuilder<N1> nodeOrder(ElementOrder<N1> elementOrder) {
        GraphBuilder<N1> graphBuilderCast = cast();
        graphBuilderCast.nodeOrder = (ElementOrder) Preconditions.checkNotNull(elementOrder);
        return graphBuilderCast;
    }
}
