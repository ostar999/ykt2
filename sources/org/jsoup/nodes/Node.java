package org.jsoup.nodes;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/* loaded from: classes9.dex */
public abstract class Node implements Cloneable {
    Attributes attributes;
    String baseUri;
    List<Node> childNodes;
    Node parentNode;
    int siblingIndex;

    public static class OuterHtmlVisitor implements NodeVisitor {
        private StringBuilder accum;
        private Document.OutputSettings out;

        public OuterHtmlVisitor(StringBuilder sb, Document.OutputSettings outputSettings) {
            this.accum = sb;
            this.out = outputSettings;
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(Node node, int i2) {
            node.outerHtmlHead(this.accum, i2, this.out);
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(Node node, int i2) {
            if (node.nodeName().equals("#text")) {
                return;
            }
            node.outerHtmlTail(this.accum, i2, this.out);
        }
    }

    public Node(String str, Attributes attributes) {
        Validate.notNull(str);
        Validate.notNull(attributes);
        this.childNodes = new ArrayList(4);
        this.baseUri = str.trim();
        this.attributes = attributes;
    }

    private void addSiblingHtml(int i2, String str) {
        Validate.notNull(str);
        Validate.notNull(this.parentNode);
        List<Node> fragment = Parser.parseFragment(str, parent() instanceof Element ? (Element) parent() : null, baseUri());
        this.parentNode.addChildren(i2, (Node[]) fragment.toArray(new Node[fragment.size()]));
    }

    private Element getDeepChild(Element element) {
        Elements elementsChildren = element.children();
        return elementsChildren.size() > 0 ? getDeepChild(elementsChildren.get(0)) : element;
    }

    private void reindexChildren() {
        for (int i2 = 0; i2 < this.childNodes.size(); i2++) {
            this.childNodes.get(i2).setSiblingIndex(i2);
        }
    }

    private void reparentChild(Node node) {
        Node node2 = node.parentNode;
        if (node2 != null) {
            node2.removeChild(node);
        }
        node.setParentNode(this);
    }

    public String absUrl(String str) {
        Validate.notEmpty(str);
        String strAttr = attr(str);
        try {
            if (!hasAttr(str)) {
                return "";
            }
            try {
                URL url = new URL(this.baseUri);
                if (strAttr.startsWith("?")) {
                    strAttr = url.getPath() + strAttr;
                }
                return new URL(url, strAttr).toExternalForm();
            } catch (MalformedURLException unused) {
                return new URL(strAttr).toExternalForm();
            }
        } catch (MalformedURLException unused2) {
            return "";
        }
    }

    public void addChildren(Node... nodeArr) {
        for (Node node : nodeArr) {
            reparentChild(node);
            this.childNodes.add(node);
            node.setSiblingIndex(this.childNodes.size() - 1);
        }
    }

    public Node after(String str) {
        addSiblingHtml(siblingIndex() + 1, str);
        return this;
    }

    public String attr(String str) {
        Validate.notNull(str);
        return this.attributes.hasKey(str) ? this.attributes.get(str) : str.toLowerCase().startsWith("abs:") ? absUrl(str.substring(4)) : "";
    }

    public Attributes attributes() {
        return this.attributes;
    }

    public String baseUri() {
        return this.baseUri;
    }

    public Node before(String str) {
        addSiblingHtml(siblingIndex(), str);
        return this;
    }

    public Node childNode(int i2) {
        return this.childNodes.get(i2);
    }

    public final int childNodeSize() {
        return this.childNodes.size();
    }

    public List<Node> childNodes() {
        return Collections.unmodifiableList(this.childNodes);
    }

    public Node[] childNodesAsArray() {
        return (Node[]) this.childNodes.toArray(new Node[childNodeSize()]);
    }

    public List<Node> childNodesCopy() {
        ArrayList arrayList = new ArrayList(this.childNodes.size());
        Iterator<Node> it = this.childNodes.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().mo2578clone());
        }
        return arrayList;
    }

    public Node doClone(Node node) {
        try {
            Node node2 = (Node) super.clone();
            node2.parentNode = node;
            node2.siblingIndex = node == null ? 0 : this.siblingIndex;
            Attributes attributes = this.attributes;
            node2.attributes = attributes != null ? attributes.clone() : null;
            node2.baseUri = this.baseUri;
            node2.childNodes = new ArrayList(this.childNodes.size());
            Iterator<Node> it = this.childNodes.iterator();
            while (it.hasNext()) {
                node2.childNodes.add(it.next());
            }
            return node2;
        } catch (CloneNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public Document.OutputSettings getOutputSettings() {
        return (ownerDocument() != null ? ownerDocument() : new Document("")).outputSettings();
    }

    public boolean hasAttr(String str) {
        Validate.notNull(str);
        if (str.startsWith("abs:")) {
            String strSubstring = str.substring(4);
            if (this.attributes.hasKey(strSubstring) && !absUrl(strSubstring).equals("")) {
                return true;
            }
        }
        return this.attributes.hasKey(str);
    }

    public int hashCode() {
        Node node = this.parentNode;
        int iHashCode = (node != null ? node.hashCode() : 0) * 31;
        Attributes attributes = this.attributes;
        return iHashCode + (attributes != null ? attributes.hashCode() : 0);
    }

    public void indent(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        sb.append("\n");
        sb.append(StringUtil.padding(i2 * outputSettings.indentAmount()));
    }

    public Node nextSibling() {
        Node node = this.parentNode;
        if (node == null) {
            return null;
        }
        List<Node> list = node.childNodes;
        Integer numValueOf = Integer.valueOf(siblingIndex());
        Validate.notNull(numValueOf);
        if (list.size() > numValueOf.intValue() + 1) {
            return list.get(numValueOf.intValue() + 1);
        }
        return null;
    }

    public abstract String nodeName();

    public String outerHtml() {
        StringBuilder sb = new StringBuilder(128);
        outerHtml(sb);
        return sb.toString();
    }

    public abstract void outerHtmlHead(StringBuilder sb, int i2, Document.OutputSettings outputSettings);

    public abstract void outerHtmlTail(StringBuilder sb, int i2, Document.OutputSettings outputSettings);

    public Document ownerDocument() {
        if (this instanceof Document) {
            return (Document) this;
        }
        Node node = this.parentNode;
        if (node == null) {
            return null;
        }
        return node.ownerDocument();
    }

    public Node parent() {
        return this.parentNode;
    }

    public final Node parentNode() {
        return this.parentNode;
    }

    public Node previousSibling() {
        Node node = this.parentNode;
        if (node == null) {
            return null;
        }
        List<Node> list = node.childNodes;
        Integer numValueOf = Integer.valueOf(siblingIndex());
        Validate.notNull(numValueOf);
        if (numValueOf.intValue() > 0) {
            return list.get(numValueOf.intValue() - 1);
        }
        return null;
    }

    public void remove() {
        Validate.notNull(this.parentNode);
        this.parentNode.removeChild(this);
    }

    public Node removeAttr(String str) {
        Validate.notNull(str);
        this.attributes.remove(str);
        return this;
    }

    public void removeChild(Node node) {
        Validate.isTrue(node.parentNode == this);
        this.childNodes.remove(node.siblingIndex());
        reindexChildren();
        node.parentNode = null;
    }

    public void replaceChild(Node node, Node node2) {
        Validate.isTrue(node.parentNode == this);
        Validate.notNull(node2);
        Node node3 = node2.parentNode;
        if (node3 != null) {
            node3.removeChild(node2);
        }
        Integer numValueOf = Integer.valueOf(node.siblingIndex());
        this.childNodes.set(numValueOf.intValue(), node2);
        node2.parentNode = this;
        node2.setSiblingIndex(numValueOf.intValue());
        node.parentNode = null;
    }

    public void replaceWith(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.replaceChild(this, node);
    }

    public void setBaseUri(final String str) {
        Validate.notNull(str);
        traverse(new NodeVisitor() { // from class: org.jsoup.nodes.Node.1
            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int i2) {
                node.baseUri = str;
            }

            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int i2) {
            }
        });
    }

    public void setParentNode(Node node) {
        Node node2 = this.parentNode;
        if (node2 != null) {
            node2.removeChild(this);
        }
        this.parentNode = node;
    }

    public void setSiblingIndex(int i2) {
        this.siblingIndex = i2;
    }

    public int siblingIndex() {
        return this.siblingIndex;
    }

    public List<Node> siblingNodes() {
        Node node = this.parentNode;
        if (node == null) {
            return Collections.emptyList();
        }
        List<Node> list = node.childNodes;
        ArrayList arrayList = new ArrayList(list.size() - 1);
        for (Node node2 : list) {
            if (node2 != this) {
                arrayList.add(node2);
            }
        }
        return arrayList;
    }

    public String toString() {
        return outerHtml();
    }

    public Node traverse(NodeVisitor nodeVisitor) {
        Validate.notNull(nodeVisitor);
        new NodeTraversor(nodeVisitor).traverse(this);
        return this;
    }

    public Node unwrap() {
        Validate.notNull(this.parentNode);
        int i2 = this.siblingIndex;
        Node node = this.childNodes.size() > 0 ? this.childNodes.get(0) : null;
        this.parentNode.addChildren(i2, childNodesAsArray());
        remove();
        return node;
    }

    public Node wrap(String str) {
        Validate.notEmpty(str);
        List<Node> fragment = Parser.parseFragment(str, parent() instanceof Element ? (Element) parent() : null, baseUri());
        Node node = fragment.get(0);
        if (node == null || !(node instanceof Element)) {
            return null;
        }
        Element element = (Element) node;
        Element deepChild = getDeepChild(element);
        this.parentNode.replaceChild(this, element);
        deepChild.addChildren(this);
        if (fragment.size() > 0) {
            for (int i2 = 0; i2 < fragment.size(); i2++) {
                Node node2 = fragment.get(i2);
                node2.parentNode.removeChild(node2);
                element.appendChild(node2);
            }
        }
        return this;
    }

    public Node after(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(siblingIndex() + 1, node);
        return this;
    }

    public Node before(Node node) {
        Validate.notNull(node);
        Validate.notNull(this.parentNode);
        this.parentNode.addChildren(siblingIndex(), node);
        return this;
    }

    @Override // 
    /* renamed from: clone */
    public Node mo2578clone() {
        Node nodeDoClone = doClone(null);
        LinkedList linkedList = new LinkedList();
        linkedList.add(nodeDoClone);
        while (!linkedList.isEmpty()) {
            Node node = (Node) linkedList.remove();
            for (int i2 = 0; i2 < node.childNodes.size(); i2++) {
                Node nodeDoClone2 = node.childNodes.get(i2).doClone(node);
                node.childNodes.set(i2, nodeDoClone2);
                linkedList.add(nodeDoClone2);
            }
        }
        return nodeDoClone;
    }

    public void outerHtml(StringBuilder sb) {
        new NodeTraversor(new OuterHtmlVisitor(sb, getOutputSettings())).traverse(this);
    }

    public void addChildren(int i2, Node... nodeArr) {
        Validate.noNullElements(nodeArr);
        for (int length = nodeArr.length - 1; length >= 0; length--) {
            Node node = nodeArr[length];
            reparentChild(node);
            this.childNodes.add(i2, node);
        }
        reindexChildren();
    }

    public Node attr(String str, String str2) {
        this.attributes.put(str, str2);
        return this;
    }

    public Node(String str) {
        this(str, new Attributes());
    }

    public Node() {
        this.childNodes = Collections.emptyList();
        this.attributes = null;
    }
}
