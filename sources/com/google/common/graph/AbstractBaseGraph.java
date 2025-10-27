package com.google.common.graph;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.util.AbstractSet;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes4.dex */
abstract class AbstractBaseGraph<N> implements BaseGraph<N> {

    public static abstract class IncidentEdgeSet<N> extends AbstractSet<EndpointPair<N>> {
        protected final BaseGraph<N> graph;
        protected final N node;

        public static final class Directed<N> extends IncidentEdgeSet<N> {
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (!endpointPair.isOrdered()) {
                    return false;
                }
                Object objSource = endpointPair.source();
                Object objTarget = endpointPair.target();
                return (this.node.equals(objSource) && this.graph.successors((BaseGraph<N>) this.node).contains(objTarget)) || (this.node.equals(objTarget) && this.graph.predecessors((BaseGraph<N>) this.node).contains(objSource));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return (this.graph.inDegree(this.node) + this.graph.outDegree(this.node)) - (this.graph.successors((BaseGraph<N>) this.node).contains(this.node) ? 1 : 0);
            }

            private Directed(BaseGraph<N> baseGraph, N n2) {
                super(baseGraph, n2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return Iterators.unmodifiableIterator(Iterators.concat(Iterators.transform(this.graph.predecessors((BaseGraph<N>) this.node).iterator(), new Function<N, EndpointPair<N>>() { // from class: com.google.common.graph.AbstractBaseGraph.IncidentEdgeSet.Directed.1
                    @Override // com.google.common.base.Function
                    public /* bridge */ /* synthetic */ Object apply(Object obj) {
                        return apply((AnonymousClass1) obj);
                    }

                    @Override // com.google.common.base.Function
                    public EndpointPair<N> apply(N n2) {
                        return EndpointPair.ordered(n2, Directed.this.node);
                    }
                }), Iterators.transform(Sets.difference(this.graph.successors((BaseGraph<N>) this.node), ImmutableSet.of(this.node)).iterator(), new Function<N, EndpointPair<N>>() { // from class: com.google.common.graph.AbstractBaseGraph.IncidentEdgeSet.Directed.2
                    @Override // com.google.common.base.Function
                    public /* bridge */ /* synthetic */ Object apply(Object obj) {
                        return apply((AnonymousClass2) obj);
                    }

                    @Override // com.google.common.base.Function
                    public EndpointPair<N> apply(N n2) {
                        return EndpointPair.ordered(Directed.this.node, n2);
                    }
                })));
            }
        }

        public static final class Undirected<N> extends IncidentEdgeSet<N> {
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair endpointPair = (EndpointPair) obj;
                if (endpointPair.isOrdered()) {
                    return false;
                }
                Set<N> setAdjacentNodes = this.graph.adjacentNodes(this.node);
                Object objNodeU = endpointPair.nodeU();
                Object objNodeV = endpointPair.nodeV();
                return (this.node.equals(objNodeV) && setAdjacentNodes.contains(objNodeU)) || (this.node.equals(objNodeU) && setAdjacentNodes.contains(objNodeV));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return this.graph.adjacentNodes(this.node).size();
            }

            private Undirected(BaseGraph<N> baseGraph, N n2) {
                super(baseGraph, n2);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return Iterators.unmodifiableIterator(Iterators.transform(this.graph.adjacentNodes(this.node).iterator(), new Function<N, EndpointPair<N>>() { // from class: com.google.common.graph.AbstractBaseGraph.IncidentEdgeSet.Undirected.1
                    @Override // com.google.common.base.Function
                    public /* bridge */ /* synthetic */ Object apply(Object obj) {
                        return apply((AnonymousClass1) obj);
                    }

                    @Override // com.google.common.base.Function
                    public EndpointPair<N> apply(N n2) {
                        return EndpointPair.unordered(Undirected.this.node, n2);
                    }
                }));
            }
        }

        public static <N> IncidentEdgeSet<N> of(BaseGraph<N> baseGraph, N n2) {
            return baseGraph.isDirected() ? new Directed(baseGraph, n2) : new Undirected(baseGraph, n2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        private IncidentEdgeSet(BaseGraph<N> baseGraph, N n2) {
            this.graph = baseGraph;
            this.node = n2;
        }
    }

    @Override // com.google.common.graph.BaseGraph
    public int degree(N n2) {
        if (isDirected()) {
            return IntMath.saturatedAdd(predecessors((AbstractBaseGraph<N>) n2).size(), successors((AbstractBaseGraph<N>) n2).size());
        }
        Set<N> setAdjacentNodes = adjacentNodes(n2);
        return IntMath.saturatedAdd(setAdjacentNodes.size(), (allowsSelfLoops() && setAdjacentNodes.contains(n2)) ? 1 : 0);
    }

    public long edgeCount() {
        long jDegree = 0;
        while (nodes().iterator().hasNext()) {
            jDegree += degree(r0.next());
        }
        Preconditions.checkState((1 & jDegree) == 0);
        return jDegree >>> 1;
    }

    @Override // com.google.common.graph.BaseGraph
    public Set<EndpointPair<N>> edges() {
        return new AbstractSet<EndpointPair<N>>() { // from class: com.google.common.graph.AbstractBaseGraph.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                if (!(obj instanceof EndpointPair)) {
                    return false;
                }
                EndpointPair<?> endpointPair = (EndpointPair) obj;
                return AbstractBaseGraph.this.isOrderingCompatible(endpointPair) && AbstractBaseGraph.this.nodes().contains(endpointPair.nodeU()) && AbstractBaseGraph.this.successors((AbstractBaseGraph) endpointPair.nodeU()).contains(endpointPair.nodeV());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean remove(Object obj) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return Ints.saturatedCast(AbstractBaseGraph.this.edgeCount());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<EndpointPair<N>> iterator() {
                return EndpointPairIterator.of(AbstractBaseGraph.this);
            }
        };
    }

    @Override // com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(N n2, N n3) {
        Preconditions.checkNotNull(n2);
        Preconditions.checkNotNull(n3);
        return nodes().contains(n2) && successors((AbstractBaseGraph<N>) n2).contains(n3);
    }

    @Override // com.google.common.graph.BaseGraph
    public int inDegree(N n2) {
        return isDirected() ? predecessors((AbstractBaseGraph<N>) n2).size() : degree(n2);
    }

    @Override // com.google.common.graph.BaseGraph
    public Set<EndpointPair<N>> incidentEdges(N n2) {
        Preconditions.checkNotNull(n2);
        Preconditions.checkArgument(nodes().contains(n2), "Node %s is not an element of this graph.", n2);
        return IncidentEdgeSet.of((BaseGraph) this, (Object) n2);
    }

    public final boolean isOrderingCompatible(EndpointPair<?> endpointPair) {
        return endpointPair.isOrdered() || !isDirected();
    }

    @Override // com.google.common.graph.BaseGraph
    public int outDegree(N n2) {
        return isDirected() ? successors((AbstractBaseGraph<N>) n2).size() : degree(n2);
    }

    public final void validateEndpoints(EndpointPair<?> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        Preconditions.checkArgument(isOrderingCompatible(endpointPair), "Mismatch: unordered endpoints cannot be used with directed graphs");
    }

    @Override // com.google.common.graph.BaseGraph
    public boolean hasEdgeConnecting(EndpointPair<N> endpointPair) {
        Preconditions.checkNotNull(endpointPair);
        if (!isOrderingCompatible(endpointPair)) {
            return false;
        }
        N nNodeU = endpointPair.nodeU();
        return nodes().contains(nNodeU) && successors((AbstractBaseGraph<N>) nNodeU).contains(endpointPair.nodeV());
    }
}
