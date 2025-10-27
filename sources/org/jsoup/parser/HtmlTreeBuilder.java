package org.jsoup.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alipay.sdk.cons.c;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.caverock.androidsvg.SVGParser;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.heytap.mcssdk.constant.b;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
import com.plv.business.model.video.PLVBaseVideoParams;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicBase;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.psychiatrygarden.utils.Constants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.bouncycastle.i18n.ErrorBundle;
import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Token;
import org.jsoup.select.Elements;

/* loaded from: classes9.dex */
class HtmlTreeBuilder extends TreeBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Element contextElement;
    private FormElement formElement;
    private Element headElement;
    private HtmlTreeBuilderState originalState;
    private HtmlTreeBuilderState state;
    private static final String[] TagsScriptStyle = {"script", TtmlNode.TAG_STYLE};
    public static final String[] TagsSearchInScope = {"applet", "caption", "html", "table", TimeDisplaySetting.TIME_DISPLAY, "th", PLVBaseVideoParams.MARQUEE, "object"};
    private static final String[] TagSearchList = {"ol", "ul"};
    private static final String[] TagSearchButton = {"button"};
    private static final String[] TagSearchTableScope = {"html", "table"};
    private static final String[] TagSearchSelectScope = {"optgroup", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION};
    private static final String[] TagSearchEndTags = {"dd", SocializeProtocolConstants.PROTOCOL_KEY_DT, AppIconSetting.LARGE_ICON_URL, Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION, "optgroup", "p", AliyunLogKey.KEY_RESOURCE_PATH, "rt"};
    private static final String[] TagSearchSpecial = {"address", "applet", "area", "article", "aside", TtmlNode.RUBY_BASE, "basefont", "bgsound", "blockquote", TtmlNode.TAG_BODY, "br", "button", "caption", TtmlNode.CENTER, "col", "colgroup", b.f7200y, "dd", ErrorBundle.DETAIL_ENTRY, "dir", TtmlNode.TAG_DIV, "dl", SocializeProtocolConstants.PROTOCOL_KEY_DT, "embed", "fieldset", "figcaption", "figure", "footer", c.f3228c, TypedValues.AttributesType.S_FRAME, "frameset", "h1", "h2", "h3", "h4", "h5", "h6", TtmlNode.TAG_HEAD, "header", "hgroup", "hr", "html", PLVLiveClassDetailVO.MENUTYPE_IFRAME, "img", "input", "isindex", AppIconSetting.LARGE_ICON_URL, PLVErrorCodeLinkMicBase.LINK_MODULE, "listing", PLVBaseVideoParams.MARQUEE, "menu", "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", "script", "section", "select", TtmlNode.TAG_STYLE, "summary", "table", "tbody", TimeDisplaySetting.TIME_DISPLAY, "textarea", "tfoot", "th", "thead", "title", "tr", "ul", "wbr", "xmp"};
    private boolean baseUriSetFromDoc = false;
    private DescendableLinkedList<Element> formattingElements = new DescendableLinkedList<>();
    private List<Token.Character> pendingTableCharacters = new ArrayList();
    private boolean framesetOk = true;
    private boolean fosterInserts = false;
    private boolean fragmentParsing = false;

    private void clearStackToContext(String... strArr) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            Element next = itDescendingIterator.next();
            if (StringUtil.in(next.nodeName(), strArr) || next.nodeName().equals("html")) {
                return;
            } else {
                itDescendingIterator.remove();
            }
        }
    }

    private boolean inSpecificScope(String str, String[] strArr, String[] strArr2) {
        return inSpecificScope(new String[]{str}, strArr, strArr2);
    }

    private void insertNode(Node node) {
        FormElement formElement;
        if (this.stack.size() == 0) {
            this.doc.appendChild(node);
        } else if (isFosterInserts()) {
            insertInFosterParent(node);
        } else {
            currentElement().appendChild(node);
        }
        if (node instanceof Element) {
            Element element = (Element) node;
            if (!element.tag().isFormListed() || (formElement = this.formElement) == null) {
                return;
            }
            formElement.addElement(element);
        }
    }

    private boolean isElementInQueue(DescendableLinkedList<Element> descendableLinkedList, Element element) {
        Iterator<Element> itDescendingIterator = descendableLinkedList.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next() == element) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameFormattingElement(Element element, Element element2) {
        return element.nodeName().equals(element2.nodeName()) && element.attributes().equals(element2.attributes());
    }

    private void replaceInQueue(LinkedList<Element> linkedList, Element element, Element element2) {
        int iLastIndexOf = linkedList.lastIndexOf(element);
        Validate.isTrue(iLastIndexOf != -1);
        linkedList.remove(iLastIndexOf);
        linkedList.add(iLastIndexOf, element2);
    }

    public Element aboveOnStack(Element element) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next() == element) {
                return itDescendingIterator.next();
            }
        }
        return null;
    }

    public void clearFormattingElementsToLastMarker() {
        while (!this.formattingElements.isEmpty()) {
            Element elementPeekLast = this.formattingElements.peekLast();
            this.formattingElements.removeLast();
            if (elementPeekLast == null) {
                return;
            }
        }
    }

    public void clearStackToTableBodyContext() {
        clearStackToContext("tbody", "tfoot", "thead");
    }

    public void clearStackToTableContext() {
        clearStackToContext("table");
    }

    public void clearStackToTableRowContext() {
        clearStackToContext("tr");
    }

    public void error(HtmlTreeBuilderState htmlTreeBuilderState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected token [%s] when in state [%s]", this.currentToken.tokenType(), htmlTreeBuilderState));
        }
    }

    public void framesetOk(boolean z2) {
        this.framesetOk = z2;
    }

    public void generateImpliedEndTags(String str) {
        while (str != null && !currentElement().nodeName().equals(str) && StringUtil.in(currentElement().nodeName(), TagSearchEndTags)) {
            pop();
        }
    }

    public Element getActiveFormattingElement(String str) {
        Element next;
        Iterator<Element> itDescendingIterator = this.formattingElements.descendingIterator();
        while (itDescendingIterator.hasNext() && (next = itDescendingIterator.next()) != null) {
            if (next.nodeName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public String getBaseUri() {
        return this.baseUri;
    }

    public Document getDocument() {
        return this.doc;
    }

    public FormElement getFormElement() {
        return this.formElement;
    }

    public Element getFromStack(String str) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            Element next = itDescendingIterator.next();
            if (next.nodeName().equals(str)) {
                return next;
            }
        }
        return null;
    }

    public Element getHeadElement() {
        return this.headElement;
    }

    public List<Token.Character> getPendingTableCharacters() {
        return this.pendingTableCharacters;
    }

    public DescendableLinkedList<Element> getStack() {
        return this.stack;
    }

    public boolean inButtonScope(String str) {
        return inScope(str, TagSearchButton);
    }

    public boolean inListItemScope(String str) {
        return inScope(str, TagSearchList);
    }

    public boolean inScope(String[] strArr) {
        return inSpecificScope(strArr, TagsSearchInScope, (String[]) null);
    }

    public boolean inSelectScope(String str) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            String strNodeName = itDescendingIterator.next().nodeName();
            if (strNodeName.equals(str)) {
                return true;
            }
            if (!StringUtil.in(strNodeName, TagSearchSelectScope)) {
                return false;
            }
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    public boolean inTableScope(String str) {
        return inSpecificScope(str, TagSearchTableScope, (String[]) null);
    }

    public Element insert(Token.StartTag startTag) {
        if (!startTag.isSelfClosing()) {
            Element element = new Element(Tag.valueOf(startTag.name()), this.baseUri, startTag.attributes);
            insert(element);
            return element;
        }
        Element elementInsertEmpty = insertEmpty(startTag);
        this.stack.add(elementInsertEmpty);
        this.tokeniser.transition(TokeniserState.Data);
        this.tokeniser.emit(new Token.EndTag(elementInsertEmpty.tagName()));
        return elementInsertEmpty;
    }

    public Element insertEmpty(Token.StartTag startTag) {
        Tag tagValueOf = Tag.valueOf(startTag.name());
        Element element = new Element(tagValueOf, this.baseUri, startTag.attributes);
        insertNode(element);
        if (startTag.isSelfClosing()) {
            if (!tagValueOf.isKnownTag()) {
                tagValueOf.setSelfClosing();
                this.tokeniser.acknowledgeSelfClosingFlag();
            } else if (tagValueOf.isSelfClosing()) {
                this.tokeniser.acknowledgeSelfClosingFlag();
            }
        }
        return element;
    }

    public FormElement insertForm(Token.StartTag startTag, boolean z2) {
        FormElement formElement = new FormElement(Tag.valueOf(startTag.name()), this.baseUri, startTag.attributes);
        setFormElement(formElement);
        insertNode(formElement);
        if (z2) {
            this.stack.add(formElement);
        }
        return formElement;
    }

    public void insertInFosterParent(Node node) {
        Element elementAboveOnStack;
        Element fromStack = getFromStack("table");
        boolean z2 = false;
        if (fromStack == null) {
            elementAboveOnStack = this.stack.get(0);
        } else if (fromStack.parent() != null) {
            elementAboveOnStack = fromStack.parent();
            z2 = true;
        } else {
            elementAboveOnStack = aboveOnStack(fromStack);
        }
        if (!z2) {
            elementAboveOnStack.appendChild(node);
        } else {
            Validate.notNull(fromStack);
            fromStack.before(node);
        }
    }

    public void insertMarkerToFormattingElements() {
        this.formattingElements.add(null);
    }

    public void insertOnStackAfter(Element element, Element element2) {
        int iLastIndexOf = this.stack.lastIndexOf(element);
        Validate.isTrue(iLastIndexOf != -1);
        this.stack.add(iLastIndexOf + 1, element2);
    }

    public boolean isFosterInserts() {
        return this.fosterInserts;
    }

    public boolean isFragmentParsing() {
        return this.fragmentParsing;
    }

    public boolean isInActiveFormattingElements(Element element) {
        return isElementInQueue(this.formattingElements, element);
    }

    public boolean isSpecial(Element element) {
        return StringUtil.in(element.nodeName(), TagSearchSpecial);
    }

    public void markInsertionMode() {
        this.originalState = this.state;
    }

    public void maybeSetBaseUri(Element element) {
        if (this.baseUriSetFromDoc) {
            return;
        }
        String strAbsUrl = element.absUrl(SVGParser.XML_STYLESHEET_ATTR_HREF);
        if (strAbsUrl.length() != 0) {
            this.baseUri = strAbsUrl;
            this.baseUriSetFromDoc = true;
            this.doc.setBaseUri(strAbsUrl);
        }
    }

    public void newPendingTableCharacters() {
        this.pendingTableCharacters = new ArrayList();
    }

    public boolean onStack(Element element) {
        return isElementInQueue(this.stack, element);
    }

    public HtmlTreeBuilderState originalState() {
        return this.originalState;
    }

    @Override // org.jsoup.parser.TreeBuilder
    public Document parse(String str, String str2, ParseErrorList parseErrorList) {
        this.state = HtmlTreeBuilderState.Initial;
        this.baseUriSetFromDoc = false;
        return super.parse(str, str2, parseErrorList);
    }

    public List<Node> parseFragment(String str, Element element, String str2, ParseErrorList parseErrorList) {
        Element element2;
        this.state = HtmlTreeBuilderState.Initial;
        initialiseParse(str, str2, parseErrorList);
        this.contextElement = element;
        this.fragmentParsing = true;
        if (element != null) {
            if (element.ownerDocument() != null) {
                this.doc.quirksMode(element.ownerDocument().quirksMode());
            }
            String strTagName = element.tagName();
            if (StringUtil.in(strTagName, "title", "textarea")) {
                this.tokeniser.transition(TokeniserState.Rcdata);
            } else if (StringUtil.in(strTagName, PLVLiveClassDetailVO.MENUTYPE_IFRAME, "noembed", "noframes", TtmlNode.TAG_STYLE, "xmp")) {
                this.tokeniser.transition(TokeniserState.Rawtext);
            } else if (strTagName.equals("script")) {
                this.tokeniser.transition(TokeniserState.ScriptData);
            } else if (!strTagName.equals("noscript") && strTagName.equals("plaintext")) {
                this.tokeniser.transition(TokeniserState.Data);
            } else {
                this.tokeniser.transition(TokeniserState.Data);
            }
            element2 = new Element(Tag.valueOf("html"), str2);
            this.doc.appendChild(element2);
            this.stack.push(element2);
            resetInsertionMode();
            Elements elementsParents = element.parents();
            elementsParents.add(0, element);
            Iterator<Element> it = elementsParents.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Element next = it.next();
                if (next instanceof FormElement) {
                    this.formElement = (FormElement) next;
                    break;
                }
            }
        } else {
            element2 = null;
        }
        runParser();
        return element != null ? element2.childNodes() : this.doc.childNodes();
    }

    public Element pop() {
        if (this.stack.peekLast().nodeName().equals(TimeDisplaySetting.TIME_DISPLAY) && !this.state.name().equals("InCell")) {
            Validate.isFalse(true, "pop td not in cell");
        }
        if (this.stack.peekLast().nodeName().equals("html")) {
            Validate.isFalse(true, "popping html!");
        }
        return this.stack.pollLast();
    }

    public void popStackToBefore(String str) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext() && !itDescendingIterator.next().nodeName().equals(str)) {
            itDescendingIterator.remove();
        }
    }

    public void popStackToClose(String str) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next().nodeName().equals(str)) {
                itDescendingIterator.remove();
                return;
            }
            itDescendingIterator.remove();
        }
    }

    @Override // org.jsoup.parser.TreeBuilder
    public boolean process(Token token) {
        this.currentToken = token;
        return this.state.process(token, this);
    }

    public void push(Element element) {
        this.stack.add(element);
    }

    public void pushActiveFormattingElements(Element element) {
        Element next;
        Iterator<Element> itDescendingIterator = this.formattingElements.descendingIterator();
        int i2 = 0;
        while (true) {
            if (!itDescendingIterator.hasNext() || (next = itDescendingIterator.next()) == null) {
                break;
            }
            if (isSameFormattingElement(element, next)) {
                i2++;
            }
            if (i2 == 3) {
                itDescendingIterator.remove();
                break;
            }
        }
        this.formattingElements.add(element);
    }

    public void reconstructFormattingElements() {
        int size = this.formattingElements.size();
        if (size == 0 || this.formattingElements.getLast() == null || onStack(this.formattingElements.getLast())) {
            return;
        }
        Element last = this.formattingElements.getLast();
        boolean z2 = true;
        int i2 = size - 1;
        int i3 = i2;
        while (i3 != 0) {
            i3--;
            last = this.formattingElements.get(i3);
            if (last == null || onStack(last)) {
                z2 = false;
                break;
            }
        }
        while (true) {
            if (!z2) {
                i3++;
                last = this.formattingElements.get(i3);
            }
            Validate.notNull(last);
            Element elementInsert = insert(last.nodeName());
            elementInsert.attributes().addAll(last.attributes());
            this.formattingElements.add(i3, elementInsert);
            this.formattingElements.remove(i3 + 1);
            if (i3 == i2) {
                return;
            } else {
                z2 = false;
            }
        }
    }

    public void removeFromActiveFormattingElements(Element element) {
        Iterator<Element> itDescendingIterator = this.formattingElements.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next() == element) {
                itDescendingIterator.remove();
                return;
            }
        }
    }

    public boolean removeFromStack(Element element) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (itDescendingIterator.next() == element) {
                itDescendingIterator.remove();
                return true;
            }
        }
        return false;
    }

    public void replaceActiveFormattingElement(Element element, Element element2) {
        replaceInQueue(this.formattingElements, element, element2);
    }

    public void replaceOnStack(Element element, Element element2) {
        replaceInQueue(this.stack, element, element2);
    }

    public void resetInsertionMode() {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        boolean z2 = false;
        while (itDescendingIterator.hasNext()) {
            Element next = itDescendingIterator.next();
            if (!itDescendingIterator.hasNext()) {
                next = this.contextElement;
                z2 = true;
            }
            String strNodeName = next.nodeName();
            if ("select".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InSelect);
                return;
            }
            if (TimeDisplaySetting.TIME_DISPLAY.equals(strNodeName) || (TimeDisplaySetting.TIME_DISPLAY.equals(strNodeName) && !z2)) {
                transition(HtmlTreeBuilderState.InCell);
                return;
            }
            if ("tr".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InRow);
                return;
            }
            if ("tbody".equals(strNodeName) || "thead".equals(strNodeName) || "tfoot".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InTableBody);
                return;
            }
            if ("caption".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InCaption);
                return;
            }
            if ("colgroup".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InColumnGroup);
                return;
            }
            if ("table".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InTable);
                return;
            }
            if (TtmlNode.TAG_HEAD.equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            }
            if (TtmlNode.TAG_BODY.equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            }
            if ("frameset".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.InFrameset);
                return;
            } else if ("html".equals(strNodeName)) {
                transition(HtmlTreeBuilderState.BeforeHead);
                return;
            } else if (z2) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            }
        }
    }

    public void setFormElement(FormElement formElement) {
        this.formElement = formElement;
    }

    public void setFosterInserts(boolean z2) {
        this.fosterInserts = z2;
    }

    public void setHeadElement(Element element) {
        this.headElement = element;
    }

    public void setPendingTableCharacters(List<Token.Character> list) {
        this.pendingTableCharacters = list;
    }

    public HtmlTreeBuilderState state() {
        return this.state;
    }

    public String toString() {
        return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + this.state + ", currentElement=" + currentElement() + '}';
    }

    public void transition(HtmlTreeBuilderState htmlTreeBuilderState) {
        this.state = htmlTreeBuilderState;
    }

    private boolean inSpecificScope(String[] strArr, String[] strArr2, String[] strArr3) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            String strNodeName = itDescendingIterator.next().nodeName();
            if (StringUtil.in(strNodeName, strArr)) {
                return true;
            }
            if (StringUtil.in(strNodeName, strArr2)) {
                return false;
            }
            if (strArr3 != null && StringUtil.in(strNodeName, strArr3)) {
                return false;
            }
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    public boolean framesetOk() {
        return this.framesetOk;
    }

    public boolean inScope(String str) {
        return inScope(str, null);
    }

    public void generateImpliedEndTags() {
        generateImpliedEndTags(null);
    }

    public boolean inScope(String str, String[] strArr) {
        return inSpecificScope(str, TagsSearchInScope, strArr);
    }

    public boolean process(Token token, HtmlTreeBuilderState htmlTreeBuilderState) {
        this.currentToken = token;
        return htmlTreeBuilderState.process(token, this);
    }

    public void popStackToClose(String... strArr) {
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            if (StringUtil.in(itDescendingIterator.next().nodeName(), strArr)) {
                itDescendingIterator.remove();
                return;
            }
            itDescendingIterator.remove();
        }
    }

    public Element insert(String str) {
        Element element = new Element(Tag.valueOf(str), this.baseUri);
        insert(element);
        return element;
    }

    public void insert(Element element) {
        insertNode(element);
        this.stack.add(element);
    }

    public void insert(Token.Comment comment) {
        insertNode(new Comment(comment.getData(), this.baseUri));
    }

    public void insert(Token.Character character) {
        Node dataNode;
        String strTagName = currentElement().tagName();
        if (!strTagName.equals("script") && !strTagName.equals(TtmlNode.TAG_STYLE)) {
            dataNode = new TextNode(character.getData(), this.baseUri);
        } else {
            dataNode = new DataNode(character.getData(), this.baseUri);
        }
        currentElement().appendChild(dataNode);
    }
}
