package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* loaded from: classes4.dex */
final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    private final Map<N, Object> adjacentNodeValues;
    private int predecessorCount;
    private int successorCount;

    public static final class PredAndSucc {
        private final Object successorValue;

        public PredAndSucc(Object obj) {
            this.successorValue = obj;
        }
    }

    private DirectedGraphConnections(Map<N, Object> map, int i2, int i3) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(map);
        this.predecessorCount = Graphs.checkNonNegative(i2);
        this.successorCount = Graphs.checkNonNegative(i3);
        Preconditions.checkState(i2 <= map.size() && i3 <= map.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPredecessor(@NullableDecl Object obj) {
        return obj == PRED || (obj instanceof PredAndSucc);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSuccessor(@NullableDecl Object obj) {
        return (obj == PRED || obj == null) ? false : true;
    }

    public static <N, V> DirectedGraphConnections<N, V> of() {
        return new DirectedGraphConnections<>(new HashMap(4, 1.0f), 0, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <N, V> DirectedGraphConnections<N, V> ofImmutable(Set<N> set, Map<N, V> map) {
        HashMap map2 = new HashMap();
        map2.putAll(map);
        for (N n2 : set) {
            Object objPut = map2.put(n2, PRED);
            if (objPut != null) {
                map2.put(n2, new PredAndSucc(objPut));
            }
        }
        return new DirectedGraphConnections<>(ImmutableMap.copyOf((Map) map2), set.size(), map.size());
    }

    @Override // com.google.common.graph.GraphConnections
    public void addPredecessor(N n2, V v2) {
        Map<N, Object> map = this.adjacentNodeValues;
        Object obj = PRED;
        Object objPut = map.put(n2, obj);
        if (objPut == null) {
            int i2 = this.predecessorCount + 1;
            this.predecessorCount = i2;
            Graphs.checkPositive(i2);
        } else if (objPut instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n2, objPut);
        } else if (objPut != obj) {
            this.adjacentNodeValues.put(n2, new PredAndSucc(objPut));
            int i3 = this.predecessorCount + 1;
            this.predecessorCount = i3;
            Graphs.checkPositive(i3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    public V addSuccessor(N n2, V v2) {
        V v3 = (V) this.adjacentNodeValues.put(n2, v2);
        if (v3 == 0) {
            int i2 = this.successorCount + 1;
            this.successorCount = i2;
            Graphs.checkPositive(i2);
            return null;
        }
        if (v3 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n2, new PredAndSucc(v2));
            return (V) ((PredAndSucc) v3).successorValue;
        }
        if (v3 != PRED) {
            return v3;
        }
        this.adjacentNodeValues.put(n2, new PredAndSucc(v2));
        int i3 = this.successorCount + 1;
        this.successorCount = i3;
        Graphs.checkPositive(i3);
        return null;
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> predecessors() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.predecessorCount;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() { // from class: com.google.common.graph.DirectedGraphConnections.1.1
                    @Override // com.google.common.collect.AbstractIterator
                    public N computeNext() {
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (DirectedGraphConnections.isPredecessor(entry.getValue())) {
                                return (N) entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    @Override // com.google.common.graph.GraphConnections
    public void removePredecessor(N n2) {
        Object obj = this.adjacentNodeValues.get(n2);
        if (obj == PRED) {
            this.adjacentNodeValues.remove(n2);
            int i2 = this.predecessorCount - 1;
            this.predecessorCount = i2;
            Graphs.checkNonNegative(i2);
            return;
        }
        if (obj instanceof PredAndSucc) {
            this.adjacentNodeValues.put(n2, ((PredAndSucc) obj).successorValue);
            int i3 = this.predecessorCount - 1;
            this.predecessorCount = i3;
            Graphs.checkNonNegative(i3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    public V removeSuccessor(Object obj) {
        Object obj2;
        V v2 = (V) this.adjacentNodeValues.get(obj);
        if (v2 == 0 || v2 == (obj2 = PRED)) {
            return null;
        }
        if (v2 instanceof PredAndSucc) {
            this.adjacentNodeValues.put(obj, obj2);
            int i2 = this.successorCount - 1;
            this.successorCount = i2;
            Graphs.checkNonNegative(i2);
            return (V) ((PredAndSucc) v2).successorValue;
        }
        this.adjacentNodeValues.remove(obj);
        int i3 = this.successorCount - 1;
        this.successorCount = i3;
        Graphs.checkNonNegative(i3);
        return v2;
    }

    @Override // com.google.common.graph.GraphConnections
    public Set<N> successors() {
        return new AbstractSet<N>() { // from class: com.google.common.graph.DirectedGraphConnections.2
            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(@NullableDecl Object obj) {
                return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return DirectedGraphConnections.this.successorCount;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator<N> iterator() {
                final Iterator it = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() { // from class: com.google.common.graph.DirectedGraphConnections.2.1
                    @Override // com.google.common.collect.AbstractIterator
                    public N computeNext() {
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            if (DirectedGraphConnections.isSuccessor(entry.getValue())) {
                                return (N) entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.graph.GraphConnections
    public V value(N n2) {
        V v2 = (V) this.adjacentNodeValues.get(n2);
        if (v2 == PRED) {
            return null;
        }
        return v2 instanceof PredAndSucc ? (V) ((PredAndSucc) v2).successorValue : v2;
    }
}
