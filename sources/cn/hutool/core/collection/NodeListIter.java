package cn.hutool.core.collection;

import cn.hutool.core.lang.Assert;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* loaded from: classes.dex */
public class NodeListIter implements ResettableIter<Node> {
    private int index = 0;
    private final NodeList nodeList;

    public NodeListIter(NodeList nodeList) {
        this.nodeList = (NodeList) Assert.notNull(nodeList, "NodeList must not be null.", new Object[0]);
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        NodeList nodeList = this.nodeList;
        return nodeList != null && this.index < nodeList.getLength();
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("remove() method not supported for a NodeListIterator.");
    }

    @Override // cn.hutool.core.collection.ResettableIter
    public void reset() {
        this.index = 0;
    }

    @Override // java.util.Iterator
    public Node next() {
        NodeList nodeList = this.nodeList;
        if (nodeList == null || this.index >= nodeList.getLength()) {
            throw new NoSuchElementException("underlying nodeList has no more elements");
        }
        NodeList nodeList2 = this.nodeList;
        int i2 = this.index;
        this.index = i2 + 1;
        return nodeList2.item(i2);
    }
}
