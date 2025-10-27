package com.google.common.graph;

import com.google.common.base.Optional;

/* loaded from: classes4.dex */
abstract class AbstractGraphBuilder<N> {
    final boolean directed;
    boolean allowsSelfLoops = false;
    ElementOrder<N> nodeOrder = ElementOrder.insertion();
    Optional<Integer> expectedNodeCount = Optional.absent();

    public AbstractGraphBuilder(boolean z2) {
        this.directed = z2;
    }
}
