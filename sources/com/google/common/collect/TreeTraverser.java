package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Queue;

@Beta
@GwtCompatible
@Deprecated
/* loaded from: classes4.dex */
public abstract class TreeTraverser<T> {

    public final class BreadthFirstIterator extends UnmodifiableIterator<T> implements PeekingIterator<T> {
        private final Queue<T> queue;

        public BreadthFirstIterator(T t2) {
            ArrayDeque arrayDeque = new ArrayDeque();
            this.queue = arrayDeque;
            arrayDeque.add(t2);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override // java.util.Iterator, com.google.common.collect.PeekingIterator
        public T next() {
            T tRemove = this.queue.remove();
            Iterables.addAll(this.queue, TreeTraverser.this.children(tRemove));
            return tRemove;
        }

        @Override // com.google.common.collect.PeekingIterator
        public T peek() {
            return this.queue.element();
        }
    }

    public final class PostOrderIterator extends AbstractIterator<T> {
        private final ArrayDeque<PostOrderNode<T>> stack;

        public PostOrderIterator(T t2) {
            ArrayDeque<PostOrderNode<T>> arrayDeque = new ArrayDeque<>();
            this.stack = arrayDeque;
            arrayDeque.addLast(expand(t2));
        }

        private PostOrderNode<T> expand(T t2) {
            return new PostOrderNode<>(t2, TreeTraverser.this.children(t2).iterator());
        }

        @Override // com.google.common.collect.AbstractIterator
        public T computeNext() {
            while (!this.stack.isEmpty()) {
                PostOrderNode<T> last = this.stack.getLast();
                if (!last.childIterator.hasNext()) {
                    this.stack.removeLast();
                    return last.root;
                }
                this.stack.addLast(expand(last.childIterator.next()));
            }
            return endOfData();
        }
    }

    public static final class PostOrderNode<T> {
        final Iterator<T> childIterator;
        final T root;

        public PostOrderNode(T t2, Iterator<T> it) {
            this.root = (T) Preconditions.checkNotNull(t2);
            this.childIterator = (Iterator) Preconditions.checkNotNull(it);
        }
    }

    public final class PreOrderIterator extends UnmodifiableIterator<T> {
        private final Deque<Iterator<T>> stack;

        public PreOrderIterator(T t2) {
            ArrayDeque arrayDeque = new ArrayDeque();
            this.stack = arrayDeque;
            arrayDeque.addLast(Iterators.singletonIterator(Preconditions.checkNotNull(t2)));
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.stack.isEmpty();
        }

        @Override // java.util.Iterator
        public T next() {
            Iterator<T> last = this.stack.getLast();
            T t2 = (T) Preconditions.checkNotNull(last.next());
            if (!last.hasNext()) {
                this.stack.removeLast();
            }
            Iterator<T> it = TreeTraverser.this.children(t2).iterator();
            if (it.hasNext()) {
                this.stack.addLast(it);
            }
            return t2;
        }
    }

    @Deprecated
    public static <T> TreeTraverser<T> using(final Function<T, ? extends Iterable<T>> function) {
        Preconditions.checkNotNull(function);
        return new TreeTraverser<T>() { // from class: com.google.common.collect.TreeTraverser.1
            @Override // com.google.common.collect.TreeTraverser
            public Iterable<T> children(T t2) {
                return (Iterable) function.apply(t2);
            }
        };
    }

    @Deprecated
    public final FluentIterable<T> breadthFirstTraversal(final T t2) {
        Preconditions.checkNotNull(t2);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.4
            @Override // java.lang.Iterable
            public UnmodifiableIterator<T> iterator() {
                return new BreadthFirstIterator(t2);
            }
        };
    }

    public abstract Iterable<T> children(T t2);

    public UnmodifiableIterator<T> postOrderIterator(T t2) {
        return new PostOrderIterator(t2);
    }

    @Deprecated
    public final FluentIterable<T> postOrderTraversal(final T t2) {
        Preconditions.checkNotNull(t2);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Iterable
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.postOrderIterator(t2);
            }
        };
    }

    public UnmodifiableIterator<T> preOrderIterator(T t2) {
        return new PreOrderIterator(t2);
    }

    @Deprecated
    public final FluentIterable<T> preOrderTraversal(final T t2) {
        Preconditions.checkNotNull(t2);
        return new FluentIterable<T>() { // from class: com.google.common.collect.TreeTraverser.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Iterable
            public UnmodifiableIterator<T> iterator() {
                return TreeTraverser.this.preOrderIterator(t2);
            }
        };
    }
}
