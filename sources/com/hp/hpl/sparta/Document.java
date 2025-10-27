package com.hp.hpl.sparta;

import com.hp.hpl.sparta.Sparta;
import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.XPath;
import com.hp.hpl.sparta.xpath.XPathException;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes4.dex */
public class Document extends Node {
    private static final boolean DEBUG = false;
    private final Hashtable indexible_;
    private Sparta.Cache indices_;
    private Vector observers_;
    private Element rootElement_;
    private String systemId_;
    private static final Integer ONE = new Integer(1);
    static final Enumeration EMPTY = new EmptyEnumeration();

    public class Index implements Observer {
        private final String attrName_;
        private transient Sparta.Cache dict_ = null;
        private final XPath xpath_;

        public Index(XPath xPath) throws XPathException {
            this.attrName_ = xPath.getIndexingAttrName();
            this.xpath_ = xPath;
            Document.this.addObserver(this);
        }

        private void regenerate() throws ParseException {
            try {
                this.dict_ = Sparta.newCache();
                Enumeration resultEnumeration = Document.this.visitor(this.xpath_, false).getResultEnumeration();
                while (resultEnumeration.hasMoreElements()) {
                    Element element = (Element) resultEnumeration.nextElement();
                    String attribute = element.getAttribute(this.attrName_);
                    Vector vector = (Vector) this.dict_.get(attribute);
                    if (vector == null) {
                        vector = new Vector(1);
                        this.dict_.put(attribute, vector);
                    }
                    vector.addElement(element);
                }
            } catch (XPathException e2) {
                throw new ParseException("XPath problem", e2);
            }
        }

        public synchronized Enumeration get(String str) throws ParseException {
            Vector vector;
            if (this.dict_ == null) {
                regenerate();
            }
            vector = (Vector) this.dict_.get(str);
            return vector == null ? Document.EMPTY : vector.elements();
        }

        public synchronized int size() throws ParseException {
            if (this.dict_ == null) {
                regenerate();
            }
            return this.dict_.size();
        }

        @Override // com.hp.hpl.sparta.Document.Observer
        public synchronized void update(Document document) {
            this.dict_ = null;
        }
    }

    public interface Observer {
        void update(Document document);
    }

    public Document() {
        this.rootElement_ = null;
        this.indices_ = Sparta.newCache();
        this.observers_ = new Vector();
        this.indexible_ = null;
        this.systemId_ = "MEMORY";
    }

    public Document(String str) {
        this.rootElement_ = null;
        this.indices_ = Sparta.newCache();
        this.observers_ = new Vector();
        this.indexible_ = null;
        this.systemId_ = str;
    }

    private XPathVisitor visitor(String str, boolean z2) throws XPathException {
        if (str.charAt(0) != '/') {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("/");
            stringBuffer.append(str);
            str = stringBuffer.toString();
        }
        return visitor(XPath.get(str), z2);
    }

    public void addObserver(Observer observer) {
        this.observers_.addElement(observer);
    }

    @Override // com.hp.hpl.sparta.Node
    public Object clone() {
        Document document = new Document(this.systemId_);
        document.rootElement_ = (Element) this.rootElement_.clone();
        return document;
    }

    @Override // com.hp.hpl.sparta.Node
    public int computeHashCode() {
        return this.rootElement_.hashCode();
    }

    public void deleteObserver(Observer observer) {
        this.observers_.removeElement(observer);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Document) {
            return this.rootElement_.equals(((Document) obj).rootElement_);
        }
        return false;
    }

    public Element getDocumentElement() {
        return this.rootElement_;
    }

    public String getSystemId() {
        return this.systemId_;
    }

    public void monitor(XPath xPath) throws XPathException {
    }

    @Override // com.hp.hpl.sparta.Node
    public void notifyObservers() {
        Enumeration enumerationElements = this.observers_.elements();
        while (enumerationElements.hasMoreElements()) {
            ((Observer) enumerationElements.nextElement()).update(this);
        }
    }

    public void setDocumentElement(Element element) {
        this.rootElement_ = element;
        element.setOwnerDocument(this);
        notifyObservers();
    }

    public void setSystemId(String str) {
        this.systemId_ = str;
        notifyObservers();
    }

    @Override // com.hp.hpl.sparta.Node
    public String toString() {
        return this.systemId_;
    }

    @Override // com.hp.hpl.sparta.Node
    public void toString(Writer writer) throws IOException {
        this.rootElement_.toString(writer);
    }

    @Override // com.hp.hpl.sparta.Node
    public void toXml(Writer writer) throws IOException {
        writer.write("<?xml version=\"1.0\" ?>\n");
        this.rootElement_.toXml(writer);
    }

    public XPathVisitor visitor(XPath xPath, boolean z2) throws XPathException {
        if (xPath.isStringValue() == z2) {
            return new XPathVisitor(this, xPath);
        }
        String str = z2 ? "evaluates to element not string" : "evaluates to string not element";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\"");
        stringBuffer.append(xPath);
        stringBuffer.append("\" evaluates to ");
        stringBuffer.append(str);
        throw new XPathException(xPath, stringBuffer.toString());
    }

    public boolean xpathEnsure(String str) throws ParseException {
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
            Enumeration steps2 = xPath.getSteps();
            Step step = (Step) steps2.nextElement();
            int i3 = i2 - 1;
            Step[] stepArr = new Step[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                stepArr[i4] = (Step) steps2.nextElement();
            }
            if (this.rootElement_ == null) {
                setDocumentElement(makeMatching(null, step, str));
            } else {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("/");
                stringBuffer.append(step);
                if (xpathSelectElement(stringBuffer.toString()) == null) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Existing root element <");
                    stringBuffer2.append(this.rootElement_.getTagName());
                    stringBuffer2.append("...> does not match first step \"");
                    stringBuffer2.append(step);
                    stringBuffer2.append("\" of \"");
                    stringBuffer2.append(str);
                    throw new ParseException(stringBuffer2.toString());
                }
            }
            if (i3 == 0) {
                return true;
            }
            return this.rootElement_.xpathEnsure(XPath.get(false, stepArr).toString());
        } catch (XPathException e2) {
            throw new ParseException(str, e2);
        }
    }

    public Index xpathGetIndex(String str) throws ParseException {
        try {
            Index index = (Index) this.indices_.get(str);
            if (index != null) {
                return index;
            }
            Index index2 = new Index(XPath.get(str));
            this.indices_.put(str, index2);
            return index2;
        } catch (XPathException e2) {
            throw new ParseException("XPath problem", e2);
        }
    }

    public boolean xpathHasIndex(String str) {
        return this.indices_.get(str) != null;
    }

    @Override // com.hp.hpl.sparta.Node
    public Element xpathSelectElement(String str) throws ParseException {
        try {
            if (str.charAt(0) != '/') {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("/");
                stringBuffer.append(str);
                str = stringBuffer.toString();
            }
            XPath xPath = XPath.get(str);
            monitor(xPath);
            return visitor(xPath, false).getFirstResultElement();
        } catch (XPathException e2) {
            throw new ParseException("XPath problem", e2);
        }
    }

    @Override // com.hp.hpl.sparta.Node
    public Enumeration xpathSelectElements(String str) throws ParseException {
        try {
            if (str.charAt(0) != '/') {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("/");
                stringBuffer.append(str);
                str = stringBuffer.toString();
            }
            XPath xPath = XPath.get(str);
            monitor(xPath);
            return visitor(xPath, false).getResultEnumeration();
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
