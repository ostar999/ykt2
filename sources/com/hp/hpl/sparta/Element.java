package com.hp.hpl.sparta;

import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.XPath;
import com.hp.hpl.sparta.xpath.XPathException;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes4.dex */
public class Element extends Node {
    private static final boolean DEBUG = false;
    private Vector attributeNames_;
    private Hashtable attributes_;
    private Node firstChild_;
    private Node lastChild_;
    private String tagName_;

    public Element() {
        this.firstChild_ = null;
        this.lastChild_ = null;
        this.attributes_ = null;
        this.attributeNames_ = null;
        this.tagName_ = null;
    }

    public Element(String str) {
        this.firstChild_ = null;
        this.lastChild_ = null;
        this.attributes_ = null;
        this.attributeNames_ = null;
        this.tagName_ = null;
        this.tagName_ = Sparta.intern(str);
    }

    private void checkInvariant() {
    }

    private boolean removeChildNoChecking(Node node) {
        for (Node nextSibling = this.firstChild_; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            if (nextSibling.equals(node)) {
                if (this.firstChild_ == nextSibling) {
                    this.firstChild_ = nextSibling.getNextSibling();
                }
                if (this.lastChild_ == nextSibling) {
                    this.lastChild_ = nextSibling.getPreviousSibling();
                }
                nextSibling.removeFromLinkedList();
                nextSibling.setParentNode(null);
                nextSibling.setOwnerDocument(null);
                return true;
            }
        }
        return false;
    }

    private void replaceChild_(Node node, Node node2) throws DOMException {
        for (Node nextSibling = this.firstChild_; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            if (nextSibling == node2) {
                if (this.firstChild_ == node2) {
                    this.firstChild_ = node;
                }
                if (this.lastChild_ == node2) {
                    this.lastChild_ = node;
                }
                node2.replaceInLinkedList(node);
                node.setParentNode(this);
                node2.setParentNode(null);
                return;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot find ");
        stringBuffer.append(node2);
        stringBuffer.append(" in ");
        stringBuffer.append(this);
        throw new DOMException((short) 8, stringBuffer.toString());
    }

    private XPathVisitor visitor(String str, boolean z2) throws XPathException {
        XPath xPath = XPath.get(str);
        if (xPath.isStringValue() == z2) {
            return new XPathVisitor(this, xPath);
        }
        String str2 = z2 ? "evaluates to element not string" : "evaluates to string not element";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\"");
        stringBuffer.append(xPath);
        stringBuffer.append("\" evaluates to ");
        stringBuffer.append(str2);
        throw new XPathException(xPath, stringBuffer.toString());
    }

    public void appendChild(Node node) {
        if (!canHaveAsDescendent(node)) {
            node = (Element) node.clone();
        }
        appendChildNoChecking(node);
        notifyObservers();
    }

    public void appendChildNoChecking(Node node) {
        Element parentNode = node.getParentNode();
        if (parentNode != null) {
            parentNode.removeChildNoChecking(node);
        }
        node.insertAtEndOfLinkedList(this.lastChild_);
        if (this.firstChild_ == null) {
            this.firstChild_ = node;
        }
        node.setParentNode(this);
        this.lastChild_ = node;
        node.setOwnerDocument(getOwnerDocument());
    }

    public boolean canHaveAsDescendent(Node node) {
        if (node == this) {
            return false;
        }
        Element parentNode = getParentNode();
        if (parentNode == null) {
            return true;
        }
        return parentNode.canHaveAsDescendent(node);
    }

    @Override // com.hp.hpl.sparta.Node
    public Object clone() {
        return cloneElement(true);
    }

    public Element cloneElement(boolean z2) {
        Element element = new Element(this.tagName_);
        Vector vector = this.attributeNames_;
        if (vector != null) {
            Enumeration enumerationElements = vector.elements();
            while (enumerationElements.hasMoreElements()) {
                String str = (String) enumerationElements.nextElement();
                element.setAttribute(str, (String) this.attributes_.get(str));
            }
        }
        if (z2) {
            for (Node nextSibling = this.firstChild_; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                element.appendChild((Node) nextSibling.clone());
            }
        }
        return element;
    }

    public Element cloneShallow() {
        return cloneElement(false);
    }

    @Override // com.hp.hpl.sparta.Node
    public int computeHashCode() {
        int iHashCode = this.tagName_.hashCode();
        Hashtable hashtable = this.attributes_;
        if (hashtable != null) {
            Enumeration enumerationKeys = hashtable.keys();
            while (enumerationKeys.hasMoreElements()) {
                String str = (String) enumerationKeys.nextElement();
                iHashCode = (((iHashCode * 31) + str.hashCode()) * 31) + ((String) this.attributes_.get(str)).hashCode();
            }
        }
        for (Node nextSibling = this.firstChild_; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            iHashCode = (iHashCode * 31) + nextSibling.hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Element)) {
            return false;
        }
        Element element = (Element) obj;
        if (!this.tagName_.equals(element.tagName_)) {
            return false;
        }
        Hashtable hashtable = this.attributes_;
        int size = hashtable == null ? 0 : hashtable.size();
        Hashtable hashtable2 = element.attributes_;
        if (size != (hashtable2 == null ? 0 : hashtable2.size())) {
            return false;
        }
        Hashtable hashtable3 = this.attributes_;
        if (hashtable3 != null) {
            Enumeration enumerationKeys = hashtable3.keys();
            while (enumerationKeys.hasMoreElements()) {
                String str = (String) enumerationKeys.nextElement();
                if (!((String) this.attributes_.get(str)).equals((String) element.attributes_.get(str))) {
                    return false;
                }
            }
        }
        Node nextSibling = this.firstChild_;
        Node nextSibling2 = element.firstChild_;
        while (nextSibling != null) {
            if (!nextSibling.equals(nextSibling2)) {
                return false;
            }
            nextSibling = nextSibling.getNextSibling();
            nextSibling2 = nextSibling2.getNextSibling();
        }
        return true;
    }

    public String getAttribute(String str) {
        Hashtable hashtable = this.attributes_;
        if (hashtable == null) {
            return null;
        }
        return (String) hashtable.get(str);
    }

    public Enumeration getAttributeNames() {
        Vector vector = this.attributeNames_;
        return vector == null ? Document.EMPTY : vector.elements();
    }

    public Node getFirstChild() {
        return this.firstChild_;
    }

    public Node getLastChild() {
        return this.lastChild_;
    }

    public String getTagName() {
        return this.tagName_;
    }

    public void removeAttribute(String str) {
        Hashtable hashtable = this.attributes_;
        if (hashtable == null) {
            return;
        }
        hashtable.remove(str);
        this.attributeNames_.removeElement(str);
        notifyObservers();
    }

    public void removeChild(Node node) throws DOMException {
        if (removeChildNoChecking(node)) {
            notifyObservers();
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Cannot find ");
        stringBuffer.append(node);
        stringBuffer.append(" in ");
        stringBuffer.append(this);
        throw new DOMException((short) 8, stringBuffer.toString());
    }

    public void replaceChild(Element element, Node node) throws DOMException {
        replaceChild_(element, node);
        notifyObservers();
    }

    public void replaceChild(Text text, Node node) throws DOMException {
        replaceChild_(text, node);
        notifyObservers();
    }

    public void setAttribute(String str, String str2) {
        if (this.attributes_ == null) {
            this.attributes_ = new Hashtable();
            this.attributeNames_ = new Vector();
        }
        if (this.attributes_.get(str) == null) {
            this.attributeNames_.addElement(str);
        }
        this.attributes_.put(str, str2);
        notifyObservers();
    }

    public void setTagName(String str) {
        this.tagName_ = Sparta.intern(str);
        notifyObservers();
    }

    @Override // com.hp.hpl.sparta.Node
    public void toString(Writer writer) throws IOException {
        for (Node nextSibling = this.firstChild_; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
            nextSibling.toString(writer);
        }
    }

    @Override // com.hp.hpl.sparta.Node
    public void toXml(Writer writer) throws IOException {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<");
        stringBuffer.append(this.tagName_);
        writer.write(stringBuffer.toString());
        Vector vector = this.attributeNames_;
        if (vector != null) {
            Enumeration enumerationElements = vector.elements();
            while (enumerationElements.hasMoreElements()) {
                String str = (String) enumerationElements.nextElement();
                String str2 = (String) this.attributes_.get(str);
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append(" ");
                stringBuffer2.append(str);
                stringBuffer2.append("=\"");
                writer.write(stringBuffer2.toString());
                Node.htmlEncode(writer, str2);
                writer.write("\"");
            }
        }
        if (this.firstChild_ == null) {
            string = "/>";
        } else {
            writer.write(">");
            for (Node nextSibling = this.firstChild_; nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                nextSibling.toXml(writer);
            }
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("</");
            stringBuffer3.append(this.tagName_);
            stringBuffer3.append(">");
            string = stringBuffer3.toString();
        }
        writer.write(string);
    }

    public boolean xpathEnsure(String str) throws ParseException {
        Element elementXpathSelectElement;
        try {
            if (xpathSelectElement(str) != null) {
                return false;
            }
            XPath xPath = XPath.get(str);
            Enumeration steps = xPath.getSteps();
            int i2 = 0;
            while (steps.hasMoreElements()) {
                steps.nextElement();
                i2++;
            }
            int i3 = i2 - 1;
            Step[] stepArr = new Step[i3];
            Enumeration steps2 = xPath.getSteps();
            for (int i4 = 0; i4 < i3; i4++) {
                stepArr[i4] = (Step) steps2.nextElement();
            }
            Step step = (Step) steps2.nextElement();
            if (i3 == 0) {
                elementXpathSelectElement = this;
            } else {
                String string = XPath.get(xPath.isAbsolute(), stepArr).toString();
                xpathEnsure(string.toString());
                elementXpathSelectElement = xpathSelectElement(string);
            }
            elementXpathSelectElement.appendChildNoChecking(makeMatching(elementXpathSelectElement, step, str));
            return true;
        } catch (XPathException e2) {
            throw new ParseException(str, e2);
        }
    }

    @Override // com.hp.hpl.sparta.Node
    public Element xpathSelectElement(String str) throws ParseException {
        try {
            return visitor(str, false).getFirstResultElement();
        } catch (XPathException e2) {
            throw new ParseException("XPath problem", e2);
        }
    }

    @Override // com.hp.hpl.sparta.Node
    public Enumeration xpathSelectElements(String str) throws ParseException {
        try {
            return visitor(str, false).getResultEnumeration();
        } catch (XPathException e2) {
            throw new ParseException("XPath problem", e2);
        }
    }

    @Override // com.hp.hpl.sparta.Node
    public String xpathSelectString(String str) throws ParseException {
        try {
            return visitor(str, true).getFirstResultString();
        } catch (XPathException e2) {
            throw new ParseException("XPath problem", e2);
        }
    }

    @Override // com.hp.hpl.sparta.Node
    public Enumeration xpathSelectStrings(String str) throws ParseException {
        try {
            return visitor(str, true).getResultEnumeration();
        } catch (XPathException e2) {
            throw new ParseException("XPath problem", e2);
        }
    }
}
