package org.jsoup.select;

import org.jsoup.nodes.Node;

/* loaded from: classes9.dex */
public class NodeTraversor {
    private NodeVisitor visitor;

    public NodeTraversor(NodeVisitor nodeVisitor) {
        this.visitor = nodeVisitor;
    }

    public void traverse(Node node) {
        Node nodeChildNode = node;
        int i2 = 0;
        while (nodeChildNode != null) {
            this.visitor.head(nodeChildNode, i2);
            if (nodeChildNode.childNodeSize() > 0) {
                nodeChildNode = nodeChildNode.childNode(0);
                i2++;
            } else {
                while (nodeChildNode.nextSibling() == null && i2 > 0) {
                    this.visitor.tail(nodeChildNode, i2);
                    nodeChildNode = nodeChildNode.parentNode();
                    i2--;
                }
                this.visitor.tail(nodeChildNode, i2);
                if (nodeChildNode == node) {
                    return;
                } else {
                    nodeChildNode = nodeChildNode.nextSibling();
                }
            }
        }
    }
}
