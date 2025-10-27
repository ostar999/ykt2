package org.jsoup.nodes;

import cn.hutool.core.text.StrPool;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import kotlin.text.Typography;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;

/* loaded from: classes9.dex */
public class Element extends Node {
    private Set<String> classNames;
    private Tag tag;

    public Element(Tag tag, String str, Attributes attributes) {
        super(str, attributes);
        Validate.notNull(tag);
        this.tag = tag;
    }

    private static void accumulateParents(Element element, Elements elements) {
        Element elementParent = element.parent();
        if (elementParent == null || elementParent.tagName().equals("#root")) {
            return;
        }
        elements.add(elementParent);
        accumulateParents(elementParent, elements);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void appendNormalisedText(StringBuilder sb, TextNode textNode) {
        String wholeText = textNode.getWholeText();
        if (preserveWhitespace(textNode.parentNode)) {
            sb.append(wholeText);
        } else {
            StringUtil.appendNormalisedWhitespace(sb, wholeText, TextNode.lastCharIsWhitespace(sb));
        }
    }

    private static void appendWhitespaceIfBr(Element element, StringBuilder sb) {
        if (!element.tag.getName().equals("br") || TextNode.lastCharIsWhitespace(sb)) {
            return;
        }
        sb.append(" ");
    }

    private static <E extends Element> Integer indexInList(Element element, List<E> list) {
        Validate.notNull(element);
        Validate.notNull(list);
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (list.get(i2).equals(element)) {
                return Integer.valueOf(i2);
            }
        }
        return null;
    }

    public static boolean preserveWhitespace(Node node) {
        if (node == null || !(node instanceof Element)) {
            return false;
        }
        Element element = (Element) node;
        return element.tag.preserveWhitespace() || (element.parent() != null && element.parent().tag.preserveWhitespace());
    }

    public Element addClass(String str) {
        Validate.notNull(str);
        Set<String> setClassNames = classNames();
        setClassNames.add(str);
        classNames(setClassNames);
        return this;
    }

    public Element append(String str) {
        Validate.notNull(str);
        List<Node> fragment = Parser.parseFragment(str, this, baseUri());
        addChildren((Node[]) fragment.toArray(new Node[fragment.size()]));
        return this;
    }

    public Element appendChild(Node node) {
        Validate.notNull(node);
        addChildren(node);
        return this;
    }

    public Element appendElement(String str) {
        Element element = new Element(Tag.valueOf(str), baseUri());
        appendChild(element);
        return element;
    }

    public Element appendText(String str) {
        appendChild(new TextNode(str, baseUri()));
        return this;
    }

    public Element child(int i2) {
        return children().get(i2);
    }

    public Elements children() {
        ArrayList arrayList = new ArrayList(this.childNodes.size());
        for (Node node : this.childNodes) {
            if (node instanceof Element) {
                arrayList.add((Element) node);
            }
        }
        return new Elements((List<Element>) arrayList);
    }

    public String className() {
        return attr("class");
    }

    public Set<String> classNames() {
        if (this.classNames == null) {
            this.classNames = new LinkedHashSet(Arrays.asList(className().split("\\s+")));
        }
        return this.classNames;
    }

    public String cssSelector() {
        if (id().length() > 0) {
            return DictionaryFactory.SHARP + id();
        }
        StringBuilder sb = new StringBuilder(tagName());
        String strJoin = StringUtil.join(classNames(), StrPool.DOT);
        if (strJoin.length() > 0) {
            sb.append('.');
            sb.append(strJoin);
        }
        if (parent() == null || (parent() instanceof Document)) {
            return sb.toString();
        }
        sb.insert(0, " > ");
        if (parent().select(sb.toString()).size() > 1) {
            sb.append(String.format(":nth-child(%d)", Integer.valueOf(elementSiblingIndex().intValue() + 1)));
        }
        return parent().cssSelector() + sb.toString();
    }

    public String data() {
        StringBuilder sb = new StringBuilder();
        for (Node node : this.childNodes) {
            if (node instanceof DataNode) {
                sb.append(((DataNode) node).getWholeData());
            } else if (node instanceof Element) {
                sb.append(((Element) node).data());
            }
        }
        return sb.toString();
    }

    public List<DataNode> dataNodes() {
        ArrayList arrayList = new ArrayList();
        for (Node node : this.childNodes) {
            if (node instanceof DataNode) {
                arrayList.add((DataNode) node);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Map<String, String> dataset() {
        return this.attributes.dataset();
    }

    public Integer elementSiblingIndex() {
        if (parent() == null) {
            return 0;
        }
        return indexInList(this, parent().children());
    }

    public Element empty() {
        this.childNodes.clear();
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public boolean equals(Object obj) {
        return this == obj;
    }

    public Element firstElementSibling() {
        Elements elementsChildren = parent().children();
        if (elementsChildren.size() > 1) {
            return elementsChildren.get(0);
        }
        return null;
    }

    public Elements getAllElements() {
        return Collector.collect(new Evaluator.AllElements(), this);
    }

    public Element getElementById(String str) {
        Validate.notEmpty(str);
        Elements elementsCollect = Collector.collect(new Evaluator.Id(str), this);
        if (elementsCollect.size() > 0) {
            return elementsCollect.get(0);
        }
        return null;
    }

    public Elements getElementsByAttribute(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Attribute(str.trim().toLowerCase()), this);
    }

    public Elements getElementsByAttributeStarting(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.AttributeStarting(str.trim().toLowerCase()), this);
    }

    public Elements getElementsByAttributeValue(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValue(str, str2), this);
    }

    public Elements getElementsByAttributeValueContaining(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueContaining(str, str2), this);
    }

    public Elements getElementsByAttributeValueEnding(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueEnding(str, str2), this);
    }

    public Elements getElementsByAttributeValueMatching(String str, Pattern pattern) {
        return Collector.collect(new Evaluator.AttributeWithValueMatching(str, pattern), this);
    }

    public Elements getElementsByAttributeValueNot(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueNot(str, str2), this);
    }

    public Elements getElementsByAttributeValueStarting(String str, String str2) {
        return Collector.collect(new Evaluator.AttributeWithValueStarting(str, str2), this);
    }

    public Elements getElementsByClass(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Class(str), this);
    }

    public Elements getElementsByIndexEquals(int i2) {
        return Collector.collect(new Evaluator.IndexEquals(i2), this);
    }

    public Elements getElementsByIndexGreaterThan(int i2) {
        return Collector.collect(new Evaluator.IndexGreaterThan(i2), this);
    }

    public Elements getElementsByIndexLessThan(int i2) {
        return Collector.collect(new Evaluator.IndexLessThan(i2), this);
    }

    public Elements getElementsByTag(String str) {
        Validate.notEmpty(str);
        return Collector.collect(new Evaluator.Tag(str.toLowerCase().trim()), this);
    }

    public Elements getElementsContainingOwnText(String str) {
        return Collector.collect(new Evaluator.ContainsOwnText(str), this);
    }

    public Elements getElementsContainingText(String str) {
        return Collector.collect(new Evaluator.ContainsText(str), this);
    }

    public Elements getElementsMatchingOwnText(Pattern pattern) {
        return Collector.collect(new Evaluator.MatchesOwn(pattern), this);
    }

    public Elements getElementsMatchingText(Pattern pattern) {
        return Collector.collect(new Evaluator.Matches(pattern), this);
    }

    public boolean hasClass(String str) {
        Iterator<String> it = classNames().iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasText() {
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                if (!((TextNode) node).isBlank()) {
                    return true;
                }
            } else if ((node instanceof Element) && ((Element) node).hasText()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jsoup.nodes.Node
    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        Tag tag = this.tag;
        return iHashCode + (tag != null ? tag.hashCode() : 0);
    }

    public String html() {
        StringBuilder sb = new StringBuilder();
        html(sb);
        boolean zPrettyPrint = getOutputSettings().prettyPrint();
        String string = sb.toString();
        return zPrettyPrint ? string.trim() : string;
    }

    public String id() {
        String strAttr = attr("id");
        return strAttr == null ? "" : strAttr;
    }

    public Element insertChildren(int i2, Collection<? extends Node> collection) {
        Validate.notNull(collection, "Children collection to be inserted must not be null.");
        int iChildNodeSize = childNodeSize();
        if (i2 < 0) {
            i2 += iChildNodeSize + 1;
        }
        Validate.isTrue(i2 >= 0 && i2 <= iChildNodeSize, "Insert position out of bounds.");
        ArrayList arrayList = new ArrayList(collection);
        addChildren(i2, (Node[]) arrayList.toArray(new Node[arrayList.size()]));
        return this;
    }

    public boolean isBlock() {
        return this.tag.isBlock();
    }

    public Element lastElementSibling() {
        Elements elementsChildren = parent().children();
        if (elementsChildren.size() > 1) {
            return elementsChildren.get(elementsChildren.size() - 1);
        }
        return null;
    }

    public Element nextElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        Elements elementsChildren = parent().children();
        Integer numIndexInList = indexInList(this, elementsChildren);
        Validate.notNull(numIndexInList);
        if (elementsChildren.size() > numIndexInList.intValue() + 1) {
            return elementsChildren.get(numIndexInList.intValue() + 1);
        }
        return null;
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return this.tag.getName();
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlHead(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        if (sb.length() > 0 && outputSettings.prettyPrint() && (this.tag.formatAsBlock() || ((parent() != null && parent().tag().formatAsBlock()) || outputSettings.outline()))) {
            indent(sb, i2, outputSettings);
        }
        sb.append("<");
        sb.append(tagName());
        this.attributes.html(sb, outputSettings);
        if (!this.childNodes.isEmpty() || !this.tag.isSelfClosing()) {
            sb.append(">");
        } else if (outputSettings.syntax() == Document.OutputSettings.Syntax.html && this.tag.isEmpty()) {
            sb.append(Typography.greater);
        } else {
            sb.append(" />");
        }
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlTail(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        if (this.childNodes.isEmpty() && this.tag.isSelfClosing()) {
            return;
        }
        if (outputSettings.prettyPrint() && !this.childNodes.isEmpty() && (this.tag.formatAsBlock() || (outputSettings.outline() && (this.childNodes.size() > 1 || (this.childNodes.size() == 1 && !(this.childNodes.get(0) instanceof TextNode)))))) {
            indent(sb, i2, outputSettings);
        }
        sb.append("</");
        sb.append(tagName());
        sb.append(">");
    }

    public String ownText() {
        StringBuilder sb = new StringBuilder();
        ownText(sb);
        return sb.toString().trim();
    }

    public Elements parents() {
        Elements elements = new Elements();
        accumulateParents(this, elements);
        return elements;
    }

    public Element prepend(String str) {
        Validate.notNull(str);
        List<Node> fragment = Parser.parseFragment(str, this, baseUri());
        addChildren(0, (Node[]) fragment.toArray(new Node[fragment.size()]));
        return this;
    }

    public Element prependChild(Node node) {
        Validate.notNull(node);
        addChildren(0, node);
        return this;
    }

    public Element prependElement(String str) {
        Element element = new Element(Tag.valueOf(str), baseUri());
        prependChild(element);
        return element;
    }

    public Element prependText(String str) {
        prependChild(new TextNode(str, baseUri()));
        return this;
    }

    public Element previousElementSibling() {
        if (this.parentNode == null) {
            return null;
        }
        Elements elementsChildren = parent().children();
        Integer numIndexInList = indexInList(this, elementsChildren);
        Validate.notNull(numIndexInList);
        if (numIndexInList.intValue() > 0) {
            return elementsChildren.get(numIndexInList.intValue() - 1);
        }
        return null;
    }

    public Element removeClass(String str) {
        Validate.notNull(str);
        Set<String> setClassNames = classNames();
        setClassNames.remove(str);
        classNames(setClassNames);
        return this;
    }

    public Elements select(String str) {
        return Selector.select(str, this);
    }

    public Elements siblingElements() {
        if (this.parentNode == null) {
            return new Elements(0);
        }
        Elements elementsChildren = parent().children();
        Elements elements = new Elements(elementsChildren.size() - 1);
        for (Element element : elementsChildren) {
            if (element != this) {
                elements.add(element);
            }
        }
        return elements;
    }

    public Tag tag() {
        return this.tag;
    }

    public String tagName() {
        return this.tag.getName();
    }

    public String text() {
        final StringBuilder sb = new StringBuilder();
        new NodeTraversor(new NodeVisitor() { // from class: org.jsoup.nodes.Element.1
            @Override // org.jsoup.select.NodeVisitor
            public void head(Node node, int i2) {
                if (node instanceof TextNode) {
                    Element.appendNormalisedText(sb, (TextNode) node);
                } else if (node instanceof Element) {
                    Element element = (Element) node;
                    if (sb.length() > 0) {
                        if ((element.isBlock() || element.tag.getName().equals("br")) && !TextNode.lastCharIsWhitespace(sb)) {
                            sb.append(" ");
                        }
                    }
                }
            }

            @Override // org.jsoup.select.NodeVisitor
            public void tail(Node node, int i2) {
            }
        }).traverse(this);
        return sb.toString().trim();
    }

    public List<TextNode> textNodes() {
        ArrayList arrayList = new ArrayList();
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                arrayList.add((TextNode) node);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }

    public Element toggleClass(String str) {
        Validate.notNull(str);
        Set<String> setClassNames = classNames();
        if (setClassNames.contains(str)) {
            setClassNames.remove(str);
        } else {
            setClassNames.add(str);
        }
        classNames(setClassNames);
        return this;
    }

    public String val() {
        return tagName().equals("textarea") ? text() : attr("value");
    }

    @Override // org.jsoup.nodes.Node
    public Element attr(String str, String str2) {
        super.attr(str, str2);
        return this;
    }

    public Elements getElementsByAttributeValueMatching(String str, String str2) {
        try {
            return getElementsByAttributeValueMatching(str, Pattern.compile(str2));
        } catch (PatternSyntaxException e2) {
            throw new IllegalArgumentException("Pattern syntax error: " + str2, e2);
        }
    }

    public Elements getElementsMatchingOwnText(String str) {
        try {
            return getElementsMatchingOwnText(Pattern.compile(str));
        } catch (PatternSyntaxException e2) {
            throw new IllegalArgumentException("Pattern syntax error: " + str, e2);
        }
    }

    public Elements getElementsMatchingText(String str) {
        try {
            return getElementsMatchingText(Pattern.compile(str));
        } catch (PatternSyntaxException e2) {
            throw new IllegalArgumentException("Pattern syntax error: " + str, e2);
        }
    }

    @Override // org.jsoup.nodes.Node
    public final Element parent() {
        return (Element) this.parentNode;
    }

    public Element tagName(String str) {
        Validate.notEmpty(str, "Tag name must not be empty.");
        this.tag = Tag.valueOf(str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public Element wrap(String str) {
        return (Element) super.wrap(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element after(String str) {
        return (Element) super.after(str);
    }

    @Override // org.jsoup.nodes.Node
    public Element before(String str) {
        return (Element) super.before(str);
    }

    @Override // org.jsoup.nodes.Node
    /* renamed from: clone */
    public Element mo2578clone() {
        Element element = (Element) super.mo2578clone();
        element.classNames = null;
        return element;
    }

    public Element(Tag tag, String str) {
        this(tag, str, new Attributes());
    }

    private void html(StringBuilder sb) {
        Iterator<Node> it = this.childNodes.iterator();
        while (it.hasNext()) {
            it.next().outerHtml(sb);
        }
    }

    private void ownText(StringBuilder sb) {
        for (Node node : this.childNodes) {
            if (node instanceof TextNode) {
                appendNormalisedText(sb, (TextNode) node);
            } else if (node instanceof Element) {
                appendWhitespaceIfBr((Element) node, sb);
            }
        }
    }

    @Override // org.jsoup.nodes.Node
    public Element after(Node node) {
        return (Element) super.after(node);
    }

    @Override // org.jsoup.nodes.Node
    public Element before(Node node) {
        return (Element) super.before(node);
    }

    public Element text(String str) {
        Validate.notNull(str);
        empty();
        appendChild(new TextNode(str, this.baseUri));
        return this;
    }

    public Element val(String str) {
        if (tagName().equals("textarea")) {
            text(str);
        } else {
            attr("value", str);
        }
        return this;
    }

    public Element classNames(Set<String> set) {
        Validate.notNull(set);
        this.attributes.put("class", StringUtil.join(set, " "));
        return this;
    }

    public Element html(String str) {
        empty();
        append(str);
        return this;
    }
}
