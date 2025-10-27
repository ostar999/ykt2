package org.jsoup.nodes;

import com.google.android.exoplayer2.text.ttml.TtmlNode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/* loaded from: classes9.dex */
public class Document extends Element {
    private String location;
    private OutputSettings outputSettings;
    private QuirksMode quirksMode;

    public static class OutputSettings implements Cloneable {
        private Charset charset;
        private CharsetEncoder charsetEncoder;
        private Entities.EscapeMode escapeMode = Entities.EscapeMode.base;
        private int indentAmount;
        private boolean outline;
        private boolean prettyPrint;
        private Syntax syntax;

        public enum Syntax {
            html,
            xml
        }

        public OutputSettings() {
            Charset charsetForName = Charset.forName("UTF-8");
            this.charset = charsetForName;
            this.charsetEncoder = charsetForName.newEncoder();
            this.prettyPrint = true;
            this.outline = false;
            this.indentAmount = 1;
            this.syntax = Syntax.html;
        }

        public Charset charset() {
            return this.charset;
        }

        public CharsetEncoder encoder() {
            return this.charsetEncoder;
        }

        public Entities.EscapeMode escapeMode() {
            return this.escapeMode;
        }

        public int indentAmount() {
            return this.indentAmount;
        }

        public boolean outline() {
            return this.outline;
        }

        public boolean prettyPrint() {
            return this.prettyPrint;
        }

        public Syntax syntax() {
            return this.syntax;
        }

        public OutputSettings charset(Charset charset) {
            this.charset = charset;
            this.charsetEncoder = charset.newEncoder();
            return this;
        }

        public OutputSettings clone() {
            try {
                OutputSettings outputSettings = (OutputSettings) super.clone();
                outputSettings.charset(this.charset.name());
                outputSettings.escapeMode = Entities.EscapeMode.valueOf(this.escapeMode.name());
                return outputSettings;
            } catch (CloneNotSupportedException e2) {
                throw new RuntimeException(e2);
            }
        }

        public OutputSettings escapeMode(Entities.EscapeMode escapeMode) {
            this.escapeMode = escapeMode;
            return this;
        }

        public OutputSettings indentAmount(int i2) {
            Validate.isTrue(i2 >= 0);
            this.indentAmount = i2;
            return this;
        }

        public OutputSettings outline(boolean z2) {
            this.outline = z2;
            return this;
        }

        public OutputSettings prettyPrint(boolean z2) {
            this.prettyPrint = z2;
            return this;
        }

        public OutputSettings syntax(Syntax syntax) {
            this.syntax = syntax;
            return this;
        }

        public OutputSettings charset(String str) {
            charset(Charset.forName(str));
            return this;
        }
    }

    public enum QuirksMode {
        noQuirks,
        quirks,
        limitedQuirks
    }

    public Document(String str) {
        super(Tag.valueOf("#root"), str);
        this.outputSettings = new OutputSettings();
        this.quirksMode = QuirksMode.noQuirks;
        this.location = str;
    }

    public static Document createShell(String str) {
        Validate.notNull(str);
        Document document = new Document(str);
        Element elementAppendElement = document.appendElement("html");
        elementAppendElement.appendElement(TtmlNode.TAG_HEAD);
        elementAppendElement.appendElement(TtmlNode.TAG_BODY);
        return document;
    }

    private Element findFirstElementByTagName(String str, Node node) {
        if (node.nodeName().equals(str)) {
            return (Element) node;
        }
        Iterator<Node> it = node.childNodes.iterator();
        while (it.hasNext()) {
            Element elementFindFirstElementByTagName = findFirstElementByTagName(str, it.next());
            if (elementFindFirstElementByTagName != null) {
                return elementFindFirstElementByTagName;
            }
        }
        return null;
    }

    private void normaliseStructure(String str, Element element) {
        Elements elementsByTag = getElementsByTag(str);
        Element elementFirst = elementsByTag.first();
        if (elementsByTag.size() > 1) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 1; i2 < elementsByTag.size(); i2++) {
                Element element2 = elementsByTag.get(i2);
                Iterator<Node> it = element2.childNodes.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next());
                }
                element2.remove();
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                elementFirst.appendChild((Node) it2.next());
            }
        }
        if (elementFirst.parent().equals(element)) {
            return;
        }
        element.appendChild(elementFirst);
    }

    private void normaliseTextNodes(Element element) {
        ArrayList arrayList = new ArrayList();
        for (Node node : element.childNodes) {
            if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                if (!textNode.isBlank()) {
                    arrayList.add(textNode);
                }
            }
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Node node2 = (Node) arrayList.get(size);
            element.removeChild(node2);
            body().prependChild(new TextNode(" ", ""));
            body().prependChild(node2);
        }
    }

    public Element body() {
        return findFirstElementByTagName(TtmlNode.TAG_BODY, this);
    }

    public Element createElement(String str) {
        return new Element(Tag.valueOf(str), baseUri());
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Element head() {
        return findFirstElementByTagName(TtmlNode.TAG_HEAD, this);
    }

    public String location() {
        return this.location;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public String nodeName() {
        return "#document";
    }

    public Document normalise() {
        Element elementFindFirstElementByTagName = findFirstElementByTagName("html", this);
        if (elementFindFirstElementByTagName == null) {
            elementFindFirstElementByTagName = appendElement("html");
        }
        if (head() == null) {
            elementFindFirstElementByTagName.prependElement(TtmlNode.TAG_HEAD);
        }
        if (body() == null) {
            elementFindFirstElementByTagName.appendElement(TtmlNode.TAG_BODY);
        }
        normaliseTextNodes(head());
        normaliseTextNodes(elementFindFirstElementByTagName);
        normaliseTextNodes(this);
        normaliseStructure(TtmlNode.TAG_HEAD, elementFindFirstElementByTagName);
        normaliseStructure(TtmlNode.TAG_BODY, elementFindFirstElementByTagName);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public String outerHtml() {
        return super.html();
    }

    public OutputSettings outputSettings() {
        return this.outputSettings;
    }

    public QuirksMode quirksMode() {
        return this.quirksMode;
    }

    @Override // org.jsoup.nodes.Element
    public Element text(String str) {
        body().text(str);
        return this;
    }

    public String title() {
        Element elementFirst = getElementsByTag("title").first();
        return elementFirst != null ? StringUtil.normaliseWhitespace(elementFirst.text()).trim() : "";
    }

    public Document outputSettings(OutputSettings outputSettings) {
        Validate.notNull(outputSettings);
        this.outputSettings = outputSettings;
        return this;
    }

    public Document quirksMode(QuirksMode quirksMode) {
        this.quirksMode = quirksMode;
        return this;
    }

    public void title(String str) {
        Validate.notNull(str);
        Element elementFirst = getElementsByTag("title").first();
        if (elementFirst == null) {
            head().appendElement("title").text(str);
        } else {
            elementFirst.text(str);
        }
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Document mo2578clone() {
        Document document = (Document) super.mo2578clone();
        document.outputSettings = this.outputSettings.clone();
        return document;
    }
}
