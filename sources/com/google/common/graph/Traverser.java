package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
/* loaded from: classes4.dex */
public abstract class Traverser<N> {

    public enum Order {
        PREORDER,
        POSTORDER
    }

    public static <N> Traverser<N> forGraph(SuccessorsFunction<N> successorsFunction) {
        Preconditions.checkNotNull(successorsFunction);
        return new GraphTraverser(successorsFunction);
    }

    public static <N> Traverser<N> forTree(SuccessorsFunction<N> successorsFunction) {
        Preconditions.checkNotNull(successorsFunction);
        if (successorsFunction instanceof BaseGraph) {
            Preconditions.checkArgument(((BaseGraph) successorsFunction).isDirected(), "Undirected graphs can never be trees.");
        }
        if (successorsFunction instanceof Network) {
            Preconditions.checkArgument(((Network) successorsFunction).isDirected(), "Undirected networks can never be trees.");
        }
        return new TreeTraverser(successorsFunction);
    }

    public abstract Iterable<N> breadthFirst(Iterable<? extends N> iterable);

    public abstract Iterable<N> breadthFirst(N n2);

    public abstract Iterable<N> depthFirstPostOrder(Iterable<? extends N> iterable);

    public abstract Iterable<N> depthFirstPostOrder(N n2);

    public abstract Iterable<N> depthFirstPreOrder(Iterable<? extends N> iterable);

    public abstract Iterable<N> depthFirstPreOrder(N n2);

    public static final class GraphTraverser<N> extends Traverser<N> {
        private final SuccessorsFunction<N> graph;

        public final class BreadthFirstIterator extends UnmodifiableIterator<N> {
            private final Queue<N> queue = new ArrayDeque();
            private final Set<N> visited = new HashSet();

            public BreadthFirstIterator(Iterable<? extends N> iterable) {
                for (N n2 : iterable) {
                    if (this.visited.add(n2)) {
                        this.queue.add(n2);
                    }
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.queue.isEmpty();
            }

            @Override // java.util.Iterator
            public N next() {
                N nRemove = this.queue.remove();
                for (N n2 : GraphTraverser.this.graph.successors(nRemove)) {
                    if (this.visited.add(n2)) {
                        this.queue.add(n2);
                    }
                }
                return nRemove;
            }
        }

        public final class DepthFirstIterator extends AbstractIterator<N> {
            private final Order order;
            private final Deque<GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors> stack;
            private final Set<N> visited;

            public final class NodeAndSuccessors {

                @NullableDecl
                final N node;
                final Iterator<? extends N> successorIterator;

                public NodeAndSuccessors(@NullableDecl N n2, Iterable<? extends N> iterable) {
                    this.node = n2;
                    this.successorIterator = iterable.iterator();
                }
            }

            public DepthFirstIterator(Iterable<? extends N> iterable, Order order) {
                ArrayDeque arrayDeque = new ArrayDeque();
                this.stack = arrayDeque;
                this.visited = new HashSet();
                arrayDeque.push(new NodeAndSuccessors(null, iterable));
                this.order = order;
            }

            @Override // com.google.common.collect.AbstractIterator
            public N computeNext() {
                N n2;
                while (!this.stack.isEmpty()) {
                    GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors first = this.stack.getFirst();
                    boolean zAdd = this.visited.add(first.node);
                    boolean z2 = true;
                    boolean z3 = !first.successorIterator.hasNext();
                    if ((!zAdd || this.order != Order.PREORDER) && (!z3 || this.order != Order.POSTORDER)) {
                        z2 = false;
                    }
                    if (z3) {
                        this.stack.pop();
                    } else {
                        N next = first.successorIterator.next();
                        if (!this.visited.contains(next)) {
                            this.stack.push(withSuccessors(next));
                        }
                    }
                    if (z2 && (n2 = first.node) != null) {
                        return n2;
                    }
                }
                return (N) endOfData();
            }

            public GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors withSuccessors(N n2) {
                return new NodeAndSuccessors(n2, GraphTraverser.this.graph.successors(n2));
            }
        }

        public GraphTraverser(SuccessorsFunction<N> successorsFunction) {
            super();
            this.graph = (SuccessorsFunction) Preconditions.checkNotNull(successorsFunction);
        }

        private void checkThatNodeIsInGraph(N n2) {
            this.graph.successors(n2);
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(N n2) {
            Preconditions.checkNotNull(n2);
            return breadthFirst((Iterable) ImmutableSet.of(n2));
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(N n2) {
            Preconditions.checkNotNull(n2);
            return depthFirstPostOrder((Iterable) ImmutableSet.of(n2));
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(N n2) {
            Preconditions.checkNotNull(n2);
            return depthFirstPreOrder((Iterable) ImmutableSet.of(n2));
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                checkThatNodeIsInGraph(it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.GraphTraverser.1
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new BreadthFirstIterator(iterable);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                checkThatNodeIsInGraph(it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.GraphTraverser.3
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new DepthFirstIterator(iterable, Order.POSTORDER);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                checkThatNodeIsInGraph(it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.GraphTraverser.2
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new DepthFirstIterator(iterable, Order.PREORDER);
                }
            };
        }
    }

    public static final class TreeTraverser<N> extends Traverser<N> {
        private final SuccessorsFunction<N> tree;

        public final class BreadthFirstIterator extends UnmodifiableIterator<N> {
            private final Queue<N> queue = new ArrayDeque();

            public BreadthFirstIterator(Iterable<? extends N> iterable) {
                Iterator<? extends N> it = iterable.iterator();
                while (it.hasNext()) {
                    this.queue.add(it.next());
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.queue.isEmpty();
            }

            @Override // java.util.Iterator
            public N next() {
                N nRemove = this.queue.remove();
                Iterables.addAll(this.queue, TreeTraverser.this.tree.successors(nRemove));
                return nRemove;
            }
        }

        public final class DepthFirstPostOrderIterator extends AbstractIterator<N> {
            private final ArrayDeque<TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren> stack;

            public final class NodeAndChildren {
                final Iterator<? extends N> childIterator;

                @NullableDecl
                final N node;

                public NodeAndChildren(@NullableDecl N n2, Iterable<? extends N> iterable) {
                    this.node = n2;
                    this.childIterator = iterable.iterator();
                }
            }

            public DepthFirstPostOrderIterator(Iterable<? extends N> iterable) {
                ArrayDeque<TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren> arrayDeque = new ArrayDeque<>();
                this.stack = arrayDeque;
                arrayDeque.addLast(new NodeAndChildren(null, iterable));
            }

            @Override // com.google.common.collect.AbstractIterator
            public N computeNext() {
                while (!this.stack.isEmpty()) {
                    TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren last = this.stack.getLast();
                    if (last.childIterator.hasNext()) {
                        this.stack.addLast(withChildren(last.childIterator.next()));
                    } else {
                        this.stack.removeLast();
                        N n2 = last.node;
                        if (n2 != null) {
                            return n2;
                        }
                    }
                }
                return (N) endOfData();
            }

            public TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren withChildren(N n2) {
                return new NodeAndChildren(n2, TreeTraverser.this.tree.successors(n2));
            }
        }

        public final class DepthFirstPreOrderIterator extends UnmodifiableIterator<N> {
            private final Deque<Iterator<? extends N>> stack;

            public DepthFirstPreOrderIterator(Iterable<? extends N> iterable) {
                ArrayDeque arrayDeque = new ArrayDeque();
                this.stack = arrayDeque;
                arrayDeque.addLast(iterable.iterator());
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.stack.isEmpty();
            }

            @Override // java.util.Iterator
            public N next() {
                Iterator<? extends N> last = this.stack.getLast();
                N n2 = (N) Preconditions.checkNotNull(last.next());
                if (!last.hasNext()) {
                    this.stack.removeLast();
                }
                Iterator<? extends N> it = TreeTraverser.this.tree.successors(n2).iterator();
                if (it.hasNext()) {
                    this.stack.addLast(it);
                }
                return n2;
            }
        }

        public TreeTraverser(SuccessorsFunction<N> successorsFunction) {
            super();
            this.tree = (SuccessorsFunction) Preconditions.checkNotNull(successorsFunction);
        }

        private void checkThatNodeIsInTree(N n2) {
            this.tree.successors(n2);
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(N n2) {
            Preconditions.checkNotNull(n2);
            return breadthFirst((Iterable) ImmutableSet.of(n2));
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(N n2) {
            Preconditions.checkNotNull(n2);
            return depthFirstPostOrder((Iterable) ImmutableSet.of(n2));
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(N n2) {
            Preconditions.checkNotNull(n2);
            return depthFirstPreOrder((Iterable) ImmutableSet.of(n2));
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> breadthFirst(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                checkThatNodeIsInTree(it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.TreeTraverser.1
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new BreadthFirstIterator(iterable);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                checkThatNodeIsInTree(it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.TreeTraverser.3
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new DepthFirstPostOrderIterator(iterable);
                }
            };
        }

        @Override // com.google.common.graph.Traverser
        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> iterable) {
            Preconditions.checkNotNull(iterable);
            if (Iterables.isEmpty(iterable)) {
                return ImmutableSet.of();
            }
            Iterator<? extends N> it = iterable.iterator();
            while (it.hasNext()) {
                checkThatNodeIsInTree(it.next());
            }
            return new Iterable<N>() { // from class: com.google.common.graph.Traverser.TreeTraverser.2
                @Override // java.lang.Iterable
                public Iterator<N> iterator() {
                    return new DepthFirstPreOrderIterator(iterable);
                }
            };
        }
    }

    private Traverser() {
    }
}
