package cn.hutool.core.map;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.LinkedForestMap;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class LinkedForestMap<K, V> implements ForestMap<K, V> {
    private final boolean allowOverrideParent;
    private final Map<K, TreeEntryNode<K, V>> nodes = new LinkedHashMap();

    public static class EntryNodeWrapper<K, V, N extends TreeEntry<K, V>> implements Map.Entry<K, TreeEntry<K, V>> {
        private final N entryNode;

        public EntryNodeWrapper(N n2) {
            this.entryNode = n2;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return (K) this.entryNode.getKey();
        }

        @Override // java.util.Map.Entry
        public TreeEntry<K, V> getValue() {
            return this.entryNode;
        }

        @Override // java.util.Map.Entry
        public TreeEntry<K, V> setValue(TreeEntry<K, V> treeEntry) {
            throw new UnsupportedOperationException();
        }
    }

    public static class TreeEntryNode<K, V> implements TreeEntry<K, V> {
        private final Map<K, TreeEntryNode<K, V>> children;
        private final K key;
        private TreeEntryNode<K, V> parent;
        private TreeEntryNode<K, V> root;
        private V value;
        private int weight;

        public TreeEntryNode(TreeEntryNode<K, V> treeEntryNode, K k2) {
            this(treeEntryNode, k2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addChild$8(TreeEntryNode treeEntryNode, TreeEntryNode treeEntryNode2) throws Throwable {
            K k2 = treeEntryNode2.key;
            Assert.notEquals(k2, treeEntryNode.key, "circular reference between [{}] and [{}]!", k2, this.key);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addChild$9(Integer num, TreeEntryNode treeEntryNode) {
            treeEntryNode.root = getRoot();
            treeEntryNode.weight = num.intValue() + getWeight() + 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getChild$11(Integer num, TreeEntryNode treeEntryNode) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getChild$12(Object obj, Integer num, TreeEntryNode treeEntryNode) {
            return treeEntryNode.equalsKey(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ void lambda$getChildren$13(Map map, Integer num, TreeEntryNode treeEntryNode) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getParent$4(TreeEntryNode treeEntryNode) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getParent$5(Object obj, TreeEntryNode treeEntryNode) {
            return treeEntryNode.equalsKey(obj);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$getRoot$2(TreeEntryNode treeEntryNode) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$getRoot$3(TreeEntryNode treeEntryNode) {
            return !treeEntryNode.hasParent();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$null$0(TreeEntryNode treeEntryNode) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void lambda$removeDeclaredChild$10(TreeEntryNode treeEntryNode, Integer num, TreeEntryNode treeEntryNode2) {
            treeEntryNode2.root = treeEntryNode;
            treeEntryNode2.weight = num.intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$traverseChildNodes$7(Integer num, TreeEntryNode treeEntryNode) {
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ Predicate lambda$traverseParentNodes$1(Predicate predicate) {
            return new Predicate() { // from class: cn.hutool.core.map.a0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return LinkedForestMap.TreeEntryNode.lambda$null$0((LinkedForestMap.TreeEntryNode) obj);
                }
            };
        }

        public void addChild(final TreeEntryNode<K, V> treeEntryNode) {
            if (containsChild(treeEntryNode.key)) {
                return;
            }
            traverseParentNodes(true, new Consumer() { // from class: cn.hutool.core.map.y
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws Throwable {
                    this.f2570c.lambda$addChild$8(treeEntryNode, (LinkedForestMap.TreeEntryNode) obj);
                }
            }, null);
            treeEntryNode.parent = this;
            treeEntryNode.traverseChildNodes(true, new BiConsumer() { // from class: cn.hutool.core.map.d0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f2524c.lambda$addChild$9((Integer) obj, (LinkedForestMap.TreeEntryNode) obj2);
                }
            }, null);
            this.children.put(treeEntryNode.key, treeEntryNode);
        }

        public void clear() {
            this.root = null;
            this.children.clear();
            this.parent = null;
        }

        @Override // cn.hutool.core.map.TreeEntry
        public /* synthetic */ boolean containsChild(Object obj) {
            return b2.a(this, obj);
        }

        @Override // cn.hutool.core.map.TreeEntry
        public /* synthetic */ boolean containsParent(Object obj) {
            return b2.b(this, obj);
        }

        public TreeEntryNode<K, V> copy(V v2) {
            TreeEntryNode<K, V> treeEntryNode = new TreeEntryNode<>(this.parent, this.key, ObjectUtil.defaultIfNull(v2, this.value));
            treeEntryNode.children.putAll(this.children);
            return treeEntryNode;
        }

        @Override // cn.hutool.core.map.TreeEntry, java.util.Map.Entry
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass().equals(obj.getClass()) || ClassUtil.isAssignable(getClass(), obj.getClass())) {
                return false;
            }
            return ObjectUtil.equals(getKey(), ((TreeEntry) obj).getKey());
        }

        public boolean equalsKey(K k2) {
            return ObjectUtil.equal(getKey(), k2);
        }

        @Override // cn.hutool.core.map.TreeEntry
        public void forEachChild(boolean z2, final Consumer<TreeEntry<K, V>> consumer) {
            traverseChildNodes(z2, new BiConsumer() { // from class: cn.hutool.core.map.g0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    cn.hutool.core.builder.a.a(consumer, (LinkedForestMap.TreeEntryNode) obj2);
                }
            }, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // cn.hutool.core.map.TreeEntry
        public /* bridge */ /* synthetic */ TreeEntry getChild(Object obj) {
            return getChild((TreeEntryNode<K, V>) obj);
        }

        @Override // cn.hutool.core.map.TreeEntry
        public Map<K, TreeEntry<K, V>> getChildren() {
            final LinkedHashMap linkedHashMap = new LinkedHashMap();
            traverseChildNodes(false, new BiConsumer() { // from class: cn.hutool.core.map.f0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    LinkedForestMap.TreeEntryNode.lambda$getChildren$13(linkedHashMap, (Integer) obj, (LinkedForestMap.TreeEntryNode) obj2);
                }
            }, null);
            return linkedHashMap;
        }

        @Override // cn.hutool.core.map.TreeEntry
        public Map<K, TreeEntry<K, V>> getDeclaredChildren() {
            return new LinkedHashMap(this.children);
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.key;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // cn.hutool.core.map.TreeEntry
        public /* bridge */ /* synthetic */ TreeEntry getParent(Object obj) {
            return getParent((TreeEntryNode<K, V>) obj);
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.value;
        }

        @Override // cn.hutool.core.map.TreeEntry
        public int getWeight() {
            return this.weight;
        }

        @Override // cn.hutool.core.map.TreeEntry
        public /* synthetic */ boolean hasChildren() {
            return b2.c(this);
        }

        @Override // cn.hutool.core.map.TreeEntry
        public /* synthetic */ boolean hasParent() {
            return b2.d(this);
        }

        @Override // cn.hutool.core.map.TreeEntry, java.util.Map.Entry
        public int hashCode() {
            return Objects.hash(getKey());
        }

        public boolean isRoot() {
            return getRoot() == this;
        }

        public void removeDeclaredChild(K k2) {
            final TreeEntryNode<K, V> treeEntryNode = this.children.get(k2);
            if (ObjectUtil.isNull(treeEntryNode)) {
                return;
            }
            this.children.remove(k2);
            treeEntryNode.parent = null;
            treeEntryNode.traverseChildNodes(true, new BiConsumer() { // from class: cn.hutool.core.map.z
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    LinkedForestMap.TreeEntryNode.lambda$removeDeclaredChild$10(this.f2575c, (Integer) obj, (LinkedForestMap.TreeEntryNode) obj2);
                }
            }, null);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v2) {
            V value = getValue();
            this.value = v2;
            return value;
        }

        public TreeEntryNode<K, V> traverseChildNodes(boolean z2, BiConsumer<Integer, TreeEntryNode<K, V>> biConsumer, BiPredicate<Integer, TreeEntryNode<K, V>> biPredicate) {
            BiPredicate biPredicate2 = (BiPredicate) ObjectUtil.defaultIfNull(biPredicate, new BiPredicate() { // from class: cn.hutool.core.map.j0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return LinkedForestMap.TreeEntryNode.lambda$traverseChildNodes$7((Integer) obj, (LinkedForestMap.TreeEntryNode) obj2);
                }
            });
            LinkedList linkedListNewLinkedList = CollUtil.newLinkedList(CollUtil.newArrayList(this));
            int i2 = !z2 ? 1 : 0;
            TreeEntryNode<K, V> treeEntryNode = null;
            while (!linkedListNewLinkedList.isEmpty()) {
                List<TreeEntryNode<K, V>> list = (List) linkedListNewLinkedList.removeFirst();
                ArrayList arrayList = new ArrayList();
                for (TreeEntryNode<K, V> treeEntryNode2 : list) {
                    if (z2) {
                        biConsumer.accept(Integer.valueOf(i2), treeEntryNode2);
                        if (biPredicate2.test(Integer.valueOf(i2), treeEntryNode2)) {
                            return treeEntryNode2;
                        }
                    } else {
                        z2 = true;
                    }
                    CollUtil.addAll((Collection) arrayList, (Iterable) treeEntryNode2.children.values());
                }
                if (!arrayList.isEmpty()) {
                    linkedListNewLinkedList.addLast(arrayList);
                }
                treeEntryNode = (TreeEntryNode) CollUtil.getLast(arrayList);
                i2++;
            }
            return treeEntryNode;
        }

        public TreeEntryNode<K, V> traverseParentNodes(boolean z2, Consumer<TreeEntryNode<K, V>> consumer, Predicate<TreeEntryNode<K, V>> predicate) {
            Predicate predicate2 = (Predicate) ObjectUtil.defaultIfNull(predicate, (Function<Predicate<TreeEntryNode<K, V>>, ? extends Predicate<TreeEntryNode<K, V>>>) new Function() { // from class: cn.hutool.core.map.e0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return LinkedForestMap.TreeEntryNode.lambda$traverseParentNodes$1((Predicate) obj);
                }
            });
            TreeEntryNode<K, V> treeEntryNode = z2 ? this : this.parent;
            while (ObjectUtil.isNotNull(treeEntryNode)) {
                consumer.accept(treeEntryNode);
                if (predicate2.test(treeEntryNode)) {
                    break;
                }
                treeEntryNode = treeEntryNode.parent;
            }
            return treeEntryNode;
        }

        public TreeEntryNode(TreeEntryNode<K, V> treeEntryNode, K k2, V v2) {
            this.parent = treeEntryNode;
            this.key = k2;
            this.value = v2;
            this.children = new LinkedHashMap();
            if (ObjectUtil.isNull(treeEntryNode)) {
                this.root = this;
                this.weight = 0;
            } else {
                treeEntryNode.addChild(this);
                this.weight = treeEntryNode.weight + 1;
                this.root = treeEntryNode.root;
            }
        }

        @Override // cn.hutool.core.map.TreeEntry
        public TreeEntryNode<K, V> getChild(final K k2) {
            return traverseChildNodes(false, new BiConsumer() { // from class: cn.hutool.core.map.h0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    LinkedForestMap.TreeEntryNode.lambda$getChild$11((Integer) obj, (LinkedForestMap.TreeEntryNode) obj2);
                }
            }, new BiPredicate() { // from class: cn.hutool.core.map.i0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return LinkedForestMap.TreeEntryNode.lambda$getChild$12(k2, (Integer) obj, (LinkedForestMap.TreeEntryNode) obj2);
                }
            });
        }

        @Override // cn.hutool.core.map.TreeEntry
        public TreeEntryNode<K, V> getDeclaredParent() {
            return this.parent;
        }

        @Override // cn.hutool.core.map.TreeEntry
        public TreeEntryNode<K, V> getParent(final K k2) {
            return traverseParentNodes(false, new Consumer() { // from class: cn.hutool.core.map.b0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LinkedForestMap.TreeEntryNode.lambda$getParent$4((LinkedForestMap.TreeEntryNode) obj);
                }
            }, new Predicate() { // from class: cn.hutool.core.map.c0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return LinkedForestMap.TreeEntryNode.lambda$getParent$5(k2, (LinkedForestMap.TreeEntryNode) obj);
                }
            });
        }

        @Override // cn.hutool.core.map.TreeEntry
        public TreeEntryNode<K, V> getRoot() {
            if (ObjectUtil.isNotNull(this.root)) {
                return this.root;
            }
            TreeEntryNode<K, V> treeEntryNodeTraverseParentNodes = traverseParentNodes(true, new Consumer() { // from class: cn.hutool.core.map.k0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    LinkedForestMap.TreeEntryNode.lambda$getRoot$2((LinkedForestMap.TreeEntryNode) obj);
                }
            }, new Predicate() { // from class: cn.hutool.core.map.l0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return LinkedForestMap.TreeEntryNode.lambda$getRoot$3((LinkedForestMap.TreeEntryNode) obj);
                }
            });
            this.root = treeEntryNodeTraverseParentNodes;
            return treeEntryNodeTraverseParentNodes;
        }
    }

    public LinkedForestMap(boolean z2) {
        this.allowOverrideParent = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$linkNodes$3(TreeEntry treeEntry, TreeEntry treeEntry2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ TreeEntryNode lambda$linkNodes$4(Object obj) {
        return new TreeEntryNode(null, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$putLinkedNodes$1(Object obj, Object obj2, TreeEntry treeEntry, TreeEntry treeEntry2) {
        treeEntry.setValue(obj);
        treeEntry2.setValue(obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$remove$0(TreeEntryNode treeEntryNode, Object obj, TreeEntry treeEntry) {
        treeEntryNode.addChild((TreeEntryNode) treeEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map.Entry<K, TreeEntry<K, V>> wrap(Map.Entry<K, TreeEntryNode<K, V>> entry) {
        return new EntryNodeWrapper(entry.getValue());
    }

    @Override // cn.hutool.core.map.ForestMap, java.util.Map
    public void clear() {
        this.nodes.values().forEach(new Consumer() { // from class: cn.hutool.core.map.t
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((LinkedForestMap.TreeEntryNode) obj).clear();
            }
        });
        this.nodes.clear();
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ boolean containsChildNode(Object obj, Object obj2) {
        return q.a(this, obj, obj2);
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.nodes.containsKey(obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ boolean containsParentNode(Object obj, Object obj2) {
        return q.b(this, obj, obj2);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return this.nodes.containsValue(obj);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, TreeEntry<K, V>>> entrySet() {
        return (Set) this.nodes.entrySet().stream().map(new Function() { // from class: cn.hutool.core.map.x
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f2567c.wrap((Map.Entry) obj);
            }
        }).collect(Collectors.toSet());
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ Collection getChildNodes(Object obj) {
        return q.c(this, obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ Collection getDeclaredChildNodes(Object obj) {
        return q.d(this, obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ TreeEntry getDeclaredParentNode(Object obj) {
        return q.e(this, obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ Object getNodeValue(Object obj) {
        return q.f(this, obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ TreeEntry getParentNode(Object obj, Object obj2) {
        return q.g(this, obj, obj2);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ TreeEntry getRootNode(Object obj) {
        return q.h(this, obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ Set getTreeNodes(Object obj) {
        return q.i(this, obj);
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.nodes.isEmpty();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return this.nodes.keySet();
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ void linkNodes(Object obj, Object obj2) {
        q.j(this, obj, obj2);
    }

    @Override // cn.hutool.core.map.ForestMap
    public void linkNodes(K k2, K k3, BiConsumer<TreeEntry<K, V>, TreeEntry<K, V>> biConsumer) {
        BiConsumer biConsumer2 = (BiConsumer) ObjectUtil.defaultIfNull(biConsumer, new BiConsumer() { // from class: cn.hutool.core.map.u
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LinkedForestMap.lambda$linkNodes$3((TreeEntry) obj, (TreeEntry) obj2);
            }
        });
        TreeEntryNode treeEntryNode = (TreeEntryNode) this.nodes.computeIfAbsent(k2, new Function() { // from class: cn.hutool.core.map.v
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LinkedForestMap.lambda$linkNodes$4(obj);
            }
        });
        TreeEntryNode<K, V> treeEntryNode2 = this.nodes.get(k3);
        if (ObjectUtil.isNull(treeEntryNode2)) {
            TreeEntryNode<K, V> treeEntryNode3 = new TreeEntryNode<>(treeEntryNode, k3);
            biConsumer2.accept(treeEntryNode, treeEntryNode3);
            this.nodes.put(k3, treeEntryNode3);
        } else {
            if (ObjectUtil.equals(treeEntryNode, treeEntryNode2.getDeclaredParent())) {
                biConsumer2.accept(treeEntryNode, treeEntryNode2);
                return;
            }
            if (!treeEntryNode2.hasParent()) {
                treeEntryNode.addChild(treeEntryNode2);
            } else {
                if (!this.allowOverrideParent) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("[{}] has been used as child of [{}], can not be overwrite as child of [{}]", treeEntryNode2.getKey(), treeEntryNode2.getDeclaredParent().getKey(), k2));
                }
                treeEntryNode2.getDeclaredParent().removeDeclaredChild(treeEntryNode2.getKey());
                treeEntryNode.addChild(treeEntryNode2);
            }
            biConsumer2.accept(treeEntryNode, treeEntryNode2);
        }
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ TreeEntry put(Object obj, TreeEntry treeEntry) {
        return q.k(this, obj, treeEntry);
    }

    @Override // cn.hutool.core.map.ForestMap, java.util.Map
    public /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        return put((LinkedForestMap<K, V>) ((ForestMap) obj), (TreeEntry<LinkedForestMap<K, V>, V>) obj2);
    }

    @Override // cn.hutool.core.map.ForestMap, java.util.Map
    public /* synthetic */ void putAll(Map map) {
        q.m(this, map);
    }

    @Override // cn.hutool.core.map.ForestMap
    public /* synthetic */ void putAllNode(Collection collection, Function function, Function function2, boolean z2) {
        q.n(this, collection, function, function2, z2);
    }

    @Override // cn.hutool.core.map.ForestMap
    public void putLinkedNodes(K k2, final V v2, K k3, final V v3) {
        linkNodes(k2, k3, new BiConsumer() { // from class: cn.hutool.core.map.r
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LinkedForestMap.lambda$putLinkedNodes$1(v2, v3, (TreeEntry) obj, (TreeEntry) obj2);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // cn.hutool.core.map.ForestMap
    public /* bridge */ /* synthetic */ TreeEntry putNode(Object obj, Object obj2) {
        return putNode((LinkedForestMap<K, V>) obj, obj2);
    }

    @Override // java.util.Map
    public int size() {
        return this.nodes.size();
    }

    @Override // cn.hutool.core.map.ForestMap
    public void unlinkNode(K k2, K k3) {
        TreeEntryNode<K, V> treeEntryNode = this.nodes.get(k3);
        if (!ObjectUtil.isNull(treeEntryNode) && treeEntryNode.hasParent()) {
            treeEntryNode.getDeclaredParent().removeDeclaredChild(treeEntryNode.getKey());
        }
    }

    @Override // java.util.Map
    public Collection<TreeEntry<K, V>> values() {
        return new ArrayList(this.nodes.values());
    }

    @Override // java.util.Map
    public TreeEntry<K, V> get(Object obj) {
        return this.nodes.get(obj);
    }

    @Override // cn.hutool.core.map.ForestMap
    public void putLinkedNodes(K k2, K k3, final V v2) {
        linkNodes(k2, k3, new BiConsumer() { // from class: cn.hutool.core.map.s
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((TreeEntry) obj2).setValue(v2);
            }
        });
    }

    @Override // cn.hutool.core.map.ForestMap
    public TreeEntryNode<K, V> putNode(K k2, V v2) {
        TreeEntryNode<K, V> treeEntryNode = this.nodes.get(k2);
        if (ObjectUtil.isNotNull(treeEntryNode)) {
            V value = treeEntryNode.getValue();
            treeEntryNode.setValue(v2);
            return treeEntryNode.copy(value);
        }
        this.nodes.put(k2, new TreeEntryNode<>(null, k2, v2));
        return null;
    }

    @Override // cn.hutool.core.map.ForestMap, java.util.Map
    public TreeEntry<K, V> remove(Object obj) {
        TreeEntryNode<K, V> treeEntryNodeRemove = this.nodes.remove(obj);
        if (ObjectUtil.isNull(treeEntryNodeRemove)) {
            return null;
        }
        if (treeEntryNodeRemove.hasParent()) {
            final TreeEntryNode<K, V> declaredParent = treeEntryNodeRemove.getDeclaredParent();
            Map<K, TreeEntry<K, V>> children = treeEntryNodeRemove.getChildren();
            declaredParent.removeDeclaredChild(treeEntryNodeRemove.getKey());
            treeEntryNodeRemove.clear();
            children.forEach(new BiConsumer() { // from class: cn.hutool.core.map.w
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj2, Object obj3) {
                    LinkedForestMap.lambda$remove$0(declaredParent, obj2, (TreeEntry) obj3);
                }
            });
        }
        return treeEntryNodeRemove;
    }
}
