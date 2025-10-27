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
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Iterator;
import org.bouncycastle.i18n.ErrorBundle;
import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Token;

/* loaded from: classes9.dex */
enum HtmlTreeBuilderState {
    Initial { // from class: org.jsoup.parser.HtmlTreeBuilderState.1
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else {
                if (!token.isDoctype()) {
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHtml);
                    return htmlTreeBuilder.process(token);
                }
                Token.Doctype doctypeAsDoctype = token.asDoctype();
                htmlTreeBuilder.getDocument().appendChild(new DocumentType(doctypeAsDoctype.getName(), doctypeAsDoctype.getPublicIdentifier(), doctypeAsDoctype.getSystemIdentifier(), htmlTreeBuilder.getBaseUri()));
                if (doctypeAsDoctype.isForceQuirks()) {
                    htmlTreeBuilder.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHtml);
            }
            return true;
        }
    },
    BeforeHtml { // from class: org.jsoup.parser.HtmlTreeBuilderState.2
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.insert("html");
            htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHead);
            return htmlTreeBuilder.process(token);
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else {
                if (HtmlTreeBuilderState.isWhitespace(token)) {
                    return true;
                }
                if (!token.isStartTag() || !token.asStartTag().name().equals("html")) {
                    if (token.isEndTag() && StringUtil.in(token.asEndTag().name(), TtmlNode.TAG_HEAD, TtmlNode.TAG_BODY, "html", "br")) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    if (!token.isEndTag()) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.insert(token.asStartTag());
                htmlTreeBuilder.transition(HtmlTreeBuilderState.BeforeHead);
            }
            return true;
        }
    },
    BeforeHead { // from class: org.jsoup.parser.HtmlTreeBuilderState.3
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else {
                if (token.isDoctype()) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                    return HtmlTreeBuilderState.InBody.process(token, htmlTreeBuilder);
                }
                if (!token.isStartTag() || !token.asStartTag().name().equals(TtmlNode.TAG_HEAD)) {
                    if (token.isEndTag() && StringUtil.in(token.asEndTag().name(), TtmlNode.TAG_HEAD, TtmlNode.TAG_BODY, "html", "br")) {
                        htmlTreeBuilder.process(new Token.StartTag(TtmlNode.TAG_HEAD));
                        return htmlTreeBuilder.process(token);
                    }
                    if (token.isEndTag()) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.process(new Token.StartTag(TtmlNode.TAG_HEAD));
                    return htmlTreeBuilder.process(token);
                }
                htmlTreeBuilder.setHeadElement(htmlTreeBuilder.insert(token.asStartTag()));
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InHead);
            }
            return true;
        }
    },
    InHead { // from class: org.jsoup.parser.HtmlTreeBuilderState.4
        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            treeBuilder.process(new Token.EndTag(TtmlNode.TAG_HEAD));
            return treeBuilder.process(token);
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            int i2 = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i2 == 1) {
                htmlTreeBuilder.insert(token.asComment());
            } else {
                if (i2 == 2) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                if (i2 == 3) {
                    Token.StartTag startTagAsStartTag = token.asStartTag();
                    String strName = startTagAsStartTag.name();
                    if (strName.equals("html")) {
                        return HtmlTreeBuilderState.InBody.process(token, htmlTreeBuilder);
                    }
                    if (StringUtil.in(strName, TtmlNode.RUBY_BASE, "basefont", "bgsound", b.f7200y, PLVErrorCodeLinkMicBase.LINK_MODULE)) {
                        Element elementInsertEmpty = htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                        if (strName.equals(TtmlNode.RUBY_BASE) && elementInsertEmpty.hasAttr(SVGParser.XML_STYLESHEET_ATTR_HREF)) {
                            htmlTreeBuilder.maybeSetBaseUri(elementInsertEmpty);
                        }
                    } else if (strName.equals("meta")) {
                        htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                    } else if (strName.equals("title")) {
                        HtmlTreeBuilderState.handleRcData(startTagAsStartTag, htmlTreeBuilder);
                    } else if (StringUtil.in(strName, "noframes", TtmlNode.TAG_STYLE)) {
                        HtmlTreeBuilderState.handleRawtext(startTagAsStartTag, htmlTreeBuilder);
                    } else if (strName.equals("noscript")) {
                        htmlTreeBuilder.insert(startTagAsStartTag);
                        htmlTreeBuilder.transition(HtmlTreeBuilderState.InHeadNoscript);
                    } else {
                        if (!strName.equals("script")) {
                            if (!strName.equals(TtmlNode.TAG_HEAD)) {
                                return anythingElse(token, htmlTreeBuilder);
                            }
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.tokeniser.transition(TokeniserState.ScriptData);
                        htmlTreeBuilder.markInsertionMode();
                        htmlTreeBuilder.transition(HtmlTreeBuilderState.Text);
                        htmlTreeBuilder.insert(startTagAsStartTag);
                    }
                } else {
                    if (i2 != 4) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    String strName2 = token.asEndTag().name();
                    if (!strName2.equals(TtmlNode.TAG_HEAD)) {
                        if (StringUtil.in(strName2, TtmlNode.TAG_BODY, "html", "br")) {
                            return anythingElse(token, htmlTreeBuilder);
                        }
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.pop();
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterHead);
                }
            }
            return true;
        }
    },
    InHeadNoscript { // from class: org.jsoup.parser.HtmlTreeBuilderState.5
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.process(new Token.EndTag("noscript"));
            return htmlTreeBuilder.process(token);
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return true;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("noscript")) {
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InHead);
                return true;
            }
            if (HtmlTreeBuilderState.isWhitespace(token) || token.isComment() || (token.isStartTag() && StringUtil.in(token.asStartTag().name(), "basefont", "bgsound", PLVErrorCodeLinkMicBase.LINK_MODULE, "meta", "noframes", TtmlNode.TAG_STYLE))) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("br")) {
                return anythingElse(token, htmlTreeBuilder);
            }
            if ((!token.isStartTag() || !StringUtil.in(token.asStartTag().name(), TtmlNode.TAG_HEAD, "noscript")) && !token.isEndTag()) {
                return anythingElse(token, htmlTreeBuilder);
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    },
    AfterHead { // from class: org.jsoup.parser.HtmlTreeBuilderState.6
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.process(new Token.StartTag(TtmlNode.TAG_BODY));
            htmlTreeBuilder.framesetOk(true);
            return htmlTreeBuilder.process(token);
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return true;
            }
            if (!token.isStartTag()) {
                if (!token.isEndTag()) {
                    anythingElse(token, htmlTreeBuilder);
                    return true;
                }
                if (StringUtil.in(token.asEndTag().name(), TtmlNode.TAG_BODY, "html")) {
                    anythingElse(token, htmlTreeBuilder);
                    return true;
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            Token.StartTag startTagAsStartTag = token.asStartTag();
            String strName = startTagAsStartTag.name();
            if (strName.equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (strName.equals(TtmlNode.TAG_BODY)) {
                htmlTreeBuilder.insert(startTagAsStartTag);
                htmlTreeBuilder.framesetOk(false);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InBody);
                return true;
            }
            if (strName.equals("frameset")) {
                htmlTreeBuilder.insert(startTagAsStartTag);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InFrameset);
                return true;
            }
            if (!StringUtil.in(strName, TtmlNode.RUBY_BASE, "basefont", "bgsound", PLVErrorCodeLinkMicBase.LINK_MODULE, "meta", "noframes", "script", TtmlNode.TAG_STYLE, "title")) {
                if (strName.equals(TtmlNode.TAG_HEAD)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                anythingElse(token, htmlTreeBuilder);
                return true;
            }
            htmlTreeBuilder.error(this);
            Element headElement = htmlTreeBuilder.getHeadElement();
            htmlTreeBuilder.push(headElement);
            htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
            htmlTreeBuilder.removeFromStack(headElement);
            return true;
        }
    },
    InBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.7
        public boolean anyOtherEndTag(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            String strName = token.asEndTag().name();
            Iterator<Element> itDescendingIterator = htmlTreeBuilder.getStack().descendingIterator();
            while (itDescendingIterator.hasNext()) {
                Element next = itDescendingIterator.next();
                if (next.nodeName().equals(strName)) {
                    htmlTreeBuilder.generateImpliedEndTags(strName);
                    if (!strName.equals(htmlTreeBuilder.currentElement().nodeName())) {
                        htmlTreeBuilder.error(this);
                    }
                    htmlTreeBuilder.popStackToClose(strName);
                    return true;
                }
                if (htmlTreeBuilder.isSpecial(next)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            }
            return true;
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            Element element;
            int i2 = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i2 == 1) {
                htmlTreeBuilder.insert(token.asComment());
            } else {
                if (i2 == 2) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                int i3 = 3;
                if (i2 == 3) {
                    Token.StartTag startTagAsStartTag = token.asStartTag();
                    String strName = startTagAsStartTag.name();
                    if (strName.equals("html")) {
                        htmlTreeBuilder.error(this);
                        Element first = htmlTreeBuilder.getStack().getFirst();
                        Iterator<Attribute> it = startTagAsStartTag.getAttributes().iterator();
                        while (it.hasNext()) {
                            Attribute next = it.next();
                            if (!first.hasAttr(next.getKey())) {
                                first.attributes().put(next);
                            }
                        }
                    } else {
                        if (StringUtil.in(strName, Constants.InBodyStartToHead)) {
                            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
                        }
                        if (strName.equals(TtmlNode.TAG_BODY)) {
                            htmlTreeBuilder.error(this);
                            DescendableLinkedList<Element> stack = htmlTreeBuilder.getStack();
                            if (stack.size() == 1 || (stack.size() > 2 && !stack.get(1).nodeName().equals(TtmlNode.TAG_BODY))) {
                                return false;
                            }
                            htmlTreeBuilder.framesetOk(false);
                            Element element2 = stack.get(1);
                            Iterator<Attribute> it2 = startTagAsStartTag.getAttributes().iterator();
                            while (it2.hasNext()) {
                                Attribute next2 = it2.next();
                                if (!element2.hasAttr(next2.getKey())) {
                                    element2.attributes().put(next2);
                                }
                            }
                        } else if (strName.equals("frameset")) {
                            htmlTreeBuilder.error(this);
                            DescendableLinkedList<Element> stack2 = htmlTreeBuilder.getStack();
                            if (stack2.size() == 1 || ((stack2.size() > 2 && !stack2.get(1).nodeName().equals(TtmlNode.TAG_BODY)) || !htmlTreeBuilder.framesetOk())) {
                                return false;
                            }
                            Element element3 = stack2.get(1);
                            if (element3.parent() != null) {
                                element3.remove();
                            }
                            while (stack2.size() > 1) {
                                stack2.removeLast();
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.transition(HtmlTreeBuilderState.InFrameset);
                        } else if (StringUtil.in(strName, Constants.InBodyStartPClosers)) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        } else if (StringUtil.in(strName, Constants.Headings)) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            if (StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), Constants.Headings)) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.pop();
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        } else if (StringUtil.in(strName, Constants.InBodyStartPreListing)) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.framesetOk(false);
                        } else if (strName.equals(c.f3228c)) {
                            if (htmlTreeBuilder.getFormElement() != null) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insertForm(startTagAsStartTag, true);
                        } else if (strName.equals(AppIconSetting.LARGE_ICON_URL)) {
                            htmlTreeBuilder.framesetOk(false);
                            DescendableLinkedList<Element> stack3 = htmlTreeBuilder.getStack();
                            int size = stack3.size() - 1;
                            while (true) {
                                if (size <= 0) {
                                    break;
                                }
                                Element element4 = stack3.get(size);
                                if (element4.nodeName().equals(AppIconSetting.LARGE_ICON_URL)) {
                                    htmlTreeBuilder.process(new Token.EndTag(AppIconSetting.LARGE_ICON_URL));
                                    break;
                                }
                                if (htmlTreeBuilder.isSpecial(element4) && !StringUtil.in(element4.nodeName(), Constants.InBodyStartLiBreakers)) {
                                    break;
                                }
                                size--;
                            }
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        } else if (StringUtil.in(strName, Constants.DdDt)) {
                            htmlTreeBuilder.framesetOk(false);
                            DescendableLinkedList<Element> stack4 = htmlTreeBuilder.getStack();
                            int size2 = stack4.size() - 1;
                            while (true) {
                                if (size2 <= 0) {
                                    break;
                                }
                                Element element5 = stack4.get(size2);
                                if (StringUtil.in(element5.nodeName(), Constants.DdDt)) {
                                    htmlTreeBuilder.process(new Token.EndTag(element5.nodeName()));
                                    break;
                                }
                                if (htmlTreeBuilder.isSpecial(element5) && !StringUtil.in(element5.nodeName(), Constants.InBodyStartLiBreakers)) {
                                    break;
                                }
                                size2--;
                            }
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        } else if (strName.equals("plaintext")) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.tokeniser.transition(TokeniserState.PLAINTEXT);
                        } else if (strName.equals("button")) {
                            if (htmlTreeBuilder.inButtonScope("button")) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.process(new Token.EndTag("button"));
                                htmlTreeBuilder.process(startTagAsStartTag);
                            } else {
                                htmlTreeBuilder.reconstructFormattingElements();
                                htmlTreeBuilder.insert(startTagAsStartTag);
                                htmlTreeBuilder.framesetOk(false);
                            }
                        } else if (strName.equals(am.av)) {
                            if (htmlTreeBuilder.getActiveFormattingElement(am.av) != null) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.process(new Token.EndTag(am.av));
                                Element fromStack = htmlTreeBuilder.getFromStack(am.av);
                                if (fromStack != null) {
                                    htmlTreeBuilder.removeFromActiveFormattingElements(fromStack);
                                    htmlTreeBuilder.removeFromStack(fromStack);
                                }
                            }
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.pushActiveFormattingElements(htmlTreeBuilder.insert(startTagAsStartTag));
                        } else if (StringUtil.in(strName, Constants.Formatters)) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.pushActiveFormattingElements(htmlTreeBuilder.insert(startTagAsStartTag));
                        } else if (strName.equals("nobr")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            if (htmlTreeBuilder.inScope("nobr")) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.process(new Token.EndTag("nobr"));
                                htmlTreeBuilder.reconstructFormattingElements();
                            }
                            htmlTreeBuilder.pushActiveFormattingElements(htmlTreeBuilder.insert(startTagAsStartTag));
                        } else if (StringUtil.in(strName, Constants.InBodyStartApplets)) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.insertMarkerToFormattingElements();
                            htmlTreeBuilder.framesetOk(false);
                        } else if (strName.equals("table")) {
                            if (htmlTreeBuilder.getDocument().quirksMode() != Document.QuirksMode.quirks && htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.framesetOk(false);
                            htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
                        } else if (StringUtil.in(strName, Constants.InBodyStartEmptyFormatters)) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                            htmlTreeBuilder.framesetOk(false);
                        } else if (strName.equals("input")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            if (!htmlTreeBuilder.insertEmpty(startTagAsStartTag).attr("type").equalsIgnoreCase("hidden")) {
                                htmlTreeBuilder.framesetOk(false);
                            }
                        } else if (StringUtil.in(strName, Constants.InBodyStartMedia)) {
                            htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                        } else if (strName.equals("hr")) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                            htmlTreeBuilder.framesetOk(false);
                        } else if (strName.equals("image")) {
                            if (htmlTreeBuilder.getFromStack("svg") == null) {
                                return htmlTreeBuilder.process(startTagAsStartTag.name("img"));
                            }
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        } else if (strName.equals("isindex")) {
                            htmlTreeBuilder.error(this);
                            if (htmlTreeBuilder.getFormElement() != null) {
                                return false;
                            }
                            htmlTreeBuilder.tokeniser.acknowledgeSelfClosingFlag();
                            htmlTreeBuilder.process(new Token.StartTag(c.f3228c));
                            if (startTagAsStartTag.attributes.hasKey("action")) {
                                htmlTreeBuilder.getFormElement().attr("action", startTagAsStartTag.attributes.get("action"));
                            }
                            htmlTreeBuilder.process(new Token.StartTag("hr"));
                            htmlTreeBuilder.process(new Token.StartTag(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL));
                            htmlTreeBuilder.process(new Token.Character(startTagAsStartTag.attributes.hasKey("prompt") ? startTagAsStartTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: "));
                            Attributes attributes = new Attributes();
                            Iterator<Attribute> it3 = startTagAsStartTag.attributes.iterator();
                            while (it3.hasNext()) {
                                Attribute next3 = it3.next();
                                if (!StringUtil.in(next3.getKey(), Constants.InBodyStartInputAttribs)) {
                                    attributes.put(next3);
                                }
                            }
                            attributes.put("name", "isindex");
                            htmlTreeBuilder.process(new Token.StartTag("input", attributes));
                            htmlTreeBuilder.process(new Token.EndTag(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL));
                            htmlTreeBuilder.process(new Token.StartTag("hr"));
                            htmlTreeBuilder.process(new Token.EndTag(c.f3228c));
                        } else if (strName.equals("textarea")) {
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.tokeniser.transition(TokeniserState.Rcdata);
                            htmlTreeBuilder.markInsertionMode();
                            htmlTreeBuilder.framesetOk(false);
                            htmlTreeBuilder.transition(HtmlTreeBuilderState.Text);
                        } else if (strName.equals("xmp")) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.process(new Token.EndTag("p"));
                            }
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.framesetOk(false);
                            HtmlTreeBuilderState.handleRawtext(startTagAsStartTag, htmlTreeBuilder);
                        } else if (strName.equals(PLVLiveClassDetailVO.MENUTYPE_IFRAME)) {
                            htmlTreeBuilder.framesetOk(false);
                            HtmlTreeBuilderState.handleRawtext(startTagAsStartTag, htmlTreeBuilder);
                        } else if (strName.equals("noembed")) {
                            HtmlTreeBuilderState.handleRawtext(startTagAsStartTag, htmlTreeBuilder);
                        } else if (strName.equals("select")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.framesetOk(false);
                            HtmlTreeBuilderState htmlTreeBuilderStateState = htmlTreeBuilder.state();
                            if (htmlTreeBuilderStateState.equals(HtmlTreeBuilderState.InTable) || htmlTreeBuilderStateState.equals(HtmlTreeBuilderState.InCaption) || htmlTreeBuilderStateState.equals(HtmlTreeBuilderState.InTableBody) || htmlTreeBuilderStateState.equals(HtmlTreeBuilderState.InRow) || htmlTreeBuilderStateState.equals(HtmlTreeBuilderState.InCell)) {
                                htmlTreeBuilder.transition(HtmlTreeBuilderState.InSelectInTable);
                            } else {
                                htmlTreeBuilder.transition(HtmlTreeBuilderState.InSelect);
                            }
                        } else if (StringUtil.in(strName, Constants.InBodyStartOptions)) {
                            if (htmlTreeBuilder.currentElement().nodeName().equals(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION)) {
                                htmlTreeBuilder.process(new Token.EndTag(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION));
                            }
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        } else if (StringUtil.in(strName, Constants.InBodyStartRuby)) {
                            if (htmlTreeBuilder.inScope(TtmlNode.ATTR_TTS_RUBY)) {
                                htmlTreeBuilder.generateImpliedEndTags();
                                if (!htmlTreeBuilder.currentElement().nodeName().equals(TtmlNode.ATTR_TTS_RUBY)) {
                                    htmlTreeBuilder.error(this);
                                    htmlTreeBuilder.popStackToBefore(TtmlNode.ATTR_TTS_RUBY);
                                }
                                htmlTreeBuilder.insert(startTagAsStartTag);
                            }
                        } else if (strName.equals("math") || strName.equals("svg")) {
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTagAsStartTag);
                            htmlTreeBuilder.tokeniser.acknowledgeSelfClosingFlag();
                        } else {
                            if (StringUtil.in(strName, Constants.InBodyStartDrop)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTagAsStartTag);
                        }
                    }
                } else if (i2 == 4) {
                    Token.EndTag endTagAsEndTag = token.asEndTag();
                    String strName2 = endTagAsEndTag.name();
                    if (strName2.equals(TtmlNode.TAG_BODY)) {
                        if (!htmlTreeBuilder.inScope(TtmlNode.TAG_BODY)) {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterBody);
                    } else if (strName2.equals("html")) {
                        if (htmlTreeBuilder.process(new Token.EndTag(TtmlNode.TAG_BODY))) {
                            return htmlTreeBuilder.process(endTagAsEndTag);
                        }
                    } else if (!StringUtil.in(strName2, Constants.InBodyEndClosers)) {
                        Element element6 = null;
                        if (strName2.equals(c.f3228c)) {
                            FormElement formElement = htmlTreeBuilder.getFormElement();
                            htmlTreeBuilder.setFormElement(null);
                            if (formElement == null || !htmlTreeBuilder.inScope(strName2)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags();
                            if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.removeFromStack(formElement);
                        } else if (strName2.equals("p")) {
                            if (!htmlTreeBuilder.inButtonScope(strName2)) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.process(new Token.StartTag(strName2));
                                return htmlTreeBuilder.process(endTagAsEndTag);
                            }
                            htmlTreeBuilder.generateImpliedEndTags(strName2);
                            if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(strName2);
                        } else if (strName2.equals(AppIconSetting.LARGE_ICON_URL)) {
                            if (!htmlTreeBuilder.inListItemScope(strName2)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags(strName2);
                            if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(strName2);
                        } else if (StringUtil.in(strName2, Constants.DdDt)) {
                            if (!htmlTreeBuilder.inScope(strName2)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags(strName2);
                            if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(strName2);
                        } else if (StringUtil.in(strName2, Constants.Headings)) {
                            if (!htmlTreeBuilder.inScope(Constants.Headings)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags(strName2);
                            if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(Constants.Headings);
                        } else {
                            if (strName2.equals("sarcasm")) {
                                return anyOtherEndTag(token, htmlTreeBuilder);
                            }
                            if (StringUtil.in(strName2, Constants.InBodyEndAdoptionFormatters)) {
                                int i4 = 0;
                                while (i4 < 8) {
                                    Element activeFormattingElement = htmlTreeBuilder.getActiveFormattingElement(strName2);
                                    if (activeFormattingElement == null) {
                                        return anyOtherEndTag(token, htmlTreeBuilder);
                                    }
                                    if (!htmlTreeBuilder.onStack(activeFormattingElement)) {
                                        htmlTreeBuilder.error(this);
                                        htmlTreeBuilder.removeFromActiveFormattingElements(activeFormattingElement);
                                        return true;
                                    }
                                    if (!htmlTreeBuilder.inScope(activeFormattingElement.nodeName())) {
                                        htmlTreeBuilder.error(this);
                                        return false;
                                    }
                                    if (htmlTreeBuilder.currentElement() != activeFormattingElement) {
                                        htmlTreeBuilder.error(this);
                                    }
                                    DescendableLinkedList<Element> stack5 = htmlTreeBuilder.getStack();
                                    int size3 = stack5.size();
                                    boolean z2 = false;
                                    Element element7 = element6;
                                    for (int i5 = 0; i5 < size3 && i5 < 64; i5++) {
                                        element = stack5.get(i5);
                                        if (element != activeFormattingElement) {
                                            if (z2 && htmlTreeBuilder.isSpecial(element)) {
                                                break;
                                            }
                                        } else {
                                            element7 = stack5.get(i5 - 1);
                                            z2 = true;
                                        }
                                    }
                                    element = element6;
                                    if (element == null) {
                                        htmlTreeBuilder.popStackToClose(activeFormattingElement.nodeName());
                                        htmlTreeBuilder.removeFromActiveFormattingElements(activeFormattingElement);
                                        return true;
                                    }
                                    int i6 = 0;
                                    Element elementAboveOnStack = element;
                                    Node node = elementAboveOnStack;
                                    while (i6 < i3) {
                                        if (htmlTreeBuilder.onStack(elementAboveOnStack)) {
                                            elementAboveOnStack = htmlTreeBuilder.aboveOnStack(elementAboveOnStack);
                                        }
                                        if (!htmlTreeBuilder.isInActiveFormattingElements(elementAboveOnStack)) {
                                            htmlTreeBuilder.removeFromStack(elementAboveOnStack);
                                        } else {
                                            if (elementAboveOnStack == activeFormattingElement) {
                                                break;
                                            }
                                            Element element8 = new Element(Tag.valueOf(elementAboveOnStack.nodeName()), htmlTreeBuilder.getBaseUri());
                                            htmlTreeBuilder.replaceActiveFormattingElement(elementAboveOnStack, element8);
                                            htmlTreeBuilder.replaceOnStack(elementAboveOnStack, element8);
                                            if (node.parent() != null) {
                                                node.remove();
                                            }
                                            element8.appendChild(node);
                                            elementAboveOnStack = element8;
                                            node = elementAboveOnStack;
                                        }
                                        i6++;
                                        i3 = 3;
                                    }
                                    if (StringUtil.in(element7.nodeName(), Constants.InBodyEndTableFosters)) {
                                        if (node.parent() != null) {
                                            node.remove();
                                        }
                                        htmlTreeBuilder.insertInFosterParent(node);
                                    } else {
                                        if (node.parent() != null) {
                                            node.remove();
                                        }
                                        element7.appendChild(node);
                                    }
                                    Element element9 = new Element(activeFormattingElement.tag(), htmlTreeBuilder.getBaseUri());
                                    element9.attributes().addAll(activeFormattingElement.attributes());
                                    for (Node node2 : (Node[]) element.childNodes().toArray(new Node[element.childNodeSize()])) {
                                        element9.appendChild(node2);
                                    }
                                    element.appendChild(element9);
                                    htmlTreeBuilder.removeFromActiveFormattingElements(activeFormattingElement);
                                    htmlTreeBuilder.removeFromStack(activeFormattingElement);
                                    htmlTreeBuilder.insertOnStackAfter(element, element9);
                                    i4++;
                                    i3 = 3;
                                    element6 = null;
                                }
                            } else {
                                if (!StringUtil.in(strName2, Constants.InBodyStartApplets)) {
                                    if (!strName2.equals("br")) {
                                        return anyOtherEndTag(token, htmlTreeBuilder);
                                    }
                                    htmlTreeBuilder.error(this);
                                    htmlTreeBuilder.process(new Token.StartTag("br"));
                                    return false;
                                }
                                if (!htmlTreeBuilder.inScope("name")) {
                                    if (!htmlTreeBuilder.inScope(strName2)) {
                                        htmlTreeBuilder.error(this);
                                        return false;
                                    }
                                    htmlTreeBuilder.generateImpliedEndTags();
                                    if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                                        htmlTreeBuilder.error(this);
                                    }
                                    htmlTreeBuilder.popStackToClose(strName2);
                                    htmlTreeBuilder.clearFormattingElementsToLastMarker();
                                }
                            }
                        }
                    } else {
                        if (!htmlTreeBuilder.inScope(strName2)) {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.generateImpliedEndTags();
                        if (!htmlTreeBuilder.currentElement().nodeName().equals(strName2)) {
                            htmlTreeBuilder.error(this);
                        }
                        htmlTreeBuilder.popStackToClose(strName2);
                    }
                } else if (i2 == 5) {
                    Token.Character characterAsCharacter = token.asCharacter();
                    if (characterAsCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    if (htmlTreeBuilder.framesetOk() && HtmlTreeBuilderState.isWhitespace(characterAsCharacter)) {
                        htmlTreeBuilder.reconstructFormattingElements();
                        htmlTreeBuilder.insert(characterAsCharacter);
                    } else {
                        htmlTreeBuilder.reconstructFormattingElements();
                        htmlTreeBuilder.insert(characterAsCharacter);
                        htmlTreeBuilder.framesetOk(false);
                    }
                }
            }
            return true;
        }
    },
    Text { // from class: org.jsoup.parser.HtmlTreeBuilderState.8
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isCharacter()) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            if (token.isEOF()) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
                return htmlTreeBuilder.process(token);
            }
            if (!token.isEndTag()) {
                return true;
            }
            htmlTreeBuilder.pop();
            htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
            return true;
        }
    },
    InTable { // from class: org.jsoup.parser.HtmlTreeBuilderState.9
        public boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            if (!StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            htmlTreeBuilder.setFosterInserts(true);
            boolean zProcess = htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            htmlTreeBuilder.setFosterInserts(false);
            return zProcess;
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isCharacter()) {
                htmlTreeBuilder.newPendingTableCharacters();
                htmlTreeBuilder.markInsertionMode();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InTableText);
                return htmlTreeBuilder.process(token);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (!token.isStartTag()) {
                if (!token.isEndTag()) {
                    if (!token.isEOF()) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                        htmlTreeBuilder.error(this);
                    }
                    return true;
                }
                String strName = token.asEndTag().name();
                if (!strName.equals("table")) {
                    if (!StringUtil.in(strName, TtmlNode.TAG_BODY, "caption", "col", "colgroup", "html", "tbody", TimeDisplaySetting.TIME_DISPLAY, "tfoot", "th", "thead", "tr")) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    return false;
                }
                if (!htmlTreeBuilder.inTableScope(strName)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.popStackToClose("table");
                htmlTreeBuilder.resetInsertionMode();
                return true;
            }
            Token.StartTag startTagAsStartTag = token.asStartTag();
            String strName2 = startTagAsStartTag.name();
            if (strName2.equals("caption")) {
                htmlTreeBuilder.clearStackToTableContext();
                htmlTreeBuilder.insertMarkerToFormattingElements();
                htmlTreeBuilder.insert(startTagAsStartTag);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InCaption);
            } else if (strName2.equals("colgroup")) {
                htmlTreeBuilder.clearStackToTableContext();
                htmlTreeBuilder.insert(startTagAsStartTag);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InColumnGroup);
            } else {
                if (strName2.equals("col")) {
                    htmlTreeBuilder.process(new Token.StartTag("colgroup"));
                    return htmlTreeBuilder.process(token);
                }
                if (StringUtil.in(strName2, "tbody", "tfoot", "thead")) {
                    htmlTreeBuilder.clearStackToTableContext();
                    htmlTreeBuilder.insert(startTagAsStartTag);
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InTableBody);
                } else {
                    if (StringUtil.in(strName2, TimeDisplaySetting.TIME_DISPLAY, "th", "tr")) {
                        htmlTreeBuilder.process(new Token.StartTag("tbody"));
                        return htmlTreeBuilder.process(token);
                    }
                    if (strName2.equals("table")) {
                        htmlTreeBuilder.error(this);
                        if (htmlTreeBuilder.process(new Token.EndTag("table"))) {
                            return htmlTreeBuilder.process(token);
                        }
                    } else {
                        if (StringUtil.in(strName2, TtmlNode.TAG_STYLE, "script")) {
                            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
                        }
                        if (strName2.equals("input")) {
                            if (!startTagAsStartTag.attributes.get("type").equalsIgnoreCase("hidden")) {
                                return anythingElse(token, htmlTreeBuilder);
                            }
                            htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                        } else {
                            if (!strName2.equals(c.f3228c)) {
                                return anythingElse(token, htmlTreeBuilder);
                            }
                            htmlTreeBuilder.error(this);
                            if (htmlTreeBuilder.getFormElement() != null) {
                                return false;
                            }
                            htmlTreeBuilder.insertForm(startTagAsStartTag, false);
                        }
                    }
                }
            }
            return true;
        }
    },
    InTableText { // from class: org.jsoup.parser.HtmlTreeBuilderState.10
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()] == 5) {
                Token.Character characterAsCharacter = token.asCharacter();
                if (characterAsCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.getPendingTableCharacters().add(characterAsCharacter);
                return true;
            }
            if (htmlTreeBuilder.getPendingTableCharacters().size() > 0) {
                for (Token.Character character : htmlTreeBuilder.getPendingTableCharacters()) {
                    if (HtmlTreeBuilderState.isWhitespace(character)) {
                        htmlTreeBuilder.insert(character);
                    } else {
                        htmlTreeBuilder.error(this);
                        if (StringUtil.in(htmlTreeBuilder.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                            htmlTreeBuilder.setFosterInserts(true);
                            htmlTreeBuilder.process(character, HtmlTreeBuilderState.InBody);
                            htmlTreeBuilder.setFosterInserts(false);
                        } else {
                            htmlTreeBuilder.process(character, HtmlTreeBuilderState.InBody);
                        }
                    }
                }
                htmlTreeBuilder.newPendingTableCharacters();
            }
            htmlTreeBuilder.transition(htmlTreeBuilder.originalState());
            return htmlTreeBuilder.process(token);
        }
    },
    InCaption { // from class: org.jsoup.parser.HtmlTreeBuilderState.11
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isEndTag() && token.asEndTag().name().equals("caption")) {
                if (!htmlTreeBuilder.inTableScope(token.asEndTag().name())) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.generateImpliedEndTags();
                if (!htmlTreeBuilder.currentElement().nodeName().equals("caption")) {
                    htmlTreeBuilder.error(this);
                }
                htmlTreeBuilder.popStackToClose("caption");
                htmlTreeBuilder.clearFormattingElementsToLastMarker();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
                return true;
            }
            if ((token.isStartTag() && StringUtil.in(token.asStartTag().name(), "caption", "col", "colgroup", "tbody", TimeDisplaySetting.TIME_DISPLAY, "tfoot", "th", "thead", "tr")) || (token.isEndTag() && token.asEndTag().name().equals("table"))) {
                htmlTreeBuilder.error(this);
                if (htmlTreeBuilder.process(new Token.EndTag("caption"))) {
                    return htmlTreeBuilder.process(token);
                }
                return true;
            }
            if (!token.isEndTag() || !StringUtil.in(token.asEndTag().name(), TtmlNode.TAG_BODY, "col", "colgroup", "html", "tbody", TimeDisplaySetting.TIME_DISPLAY, "tfoot", "th", "thead", "tr")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    },
    InColumnGroup { // from class: org.jsoup.parser.HtmlTreeBuilderState.12
        private boolean anythingElse(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.process(new Token.EndTag("colgroup"))) {
                return treeBuilder.process(token);
            }
            return true;
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            int i2 = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i2 == 1) {
                htmlTreeBuilder.insert(token.asComment());
            } else if (i2 == 2) {
                htmlTreeBuilder.error(this);
            } else if (i2 == 3) {
                Token.StartTag startTagAsStartTag = token.asStartTag();
                String strName = startTagAsStartTag.name();
                if (strName.equals("html")) {
                    return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
                }
                if (!strName.equals("col")) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                htmlTreeBuilder.insertEmpty(startTagAsStartTag);
            } else {
                if (i2 != 4) {
                    if (i2 != 6) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                        return true;
                    }
                    return anythingElse(token, htmlTreeBuilder);
                }
                if (!token.asEndTag().name().equals("colgroup")) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
            }
            return true;
        }
    },
    InTableBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.13
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InTable);
        }

        private boolean exitTableBody(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (!htmlTreeBuilder.inTableScope("tbody") && !htmlTreeBuilder.inTableScope("thead") && !htmlTreeBuilder.inScope("tfoot")) {
                htmlTreeBuilder.error(this);
                return false;
            }
            htmlTreeBuilder.clearStackToTableBodyContext();
            htmlTreeBuilder.process(new Token.EndTag(htmlTreeBuilder.currentElement().nodeName()));
            return htmlTreeBuilder.process(token);
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            int i2 = AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()];
            if (i2 == 3) {
                Token.StartTag startTagAsStartTag = token.asStartTag();
                String strName = startTagAsStartTag.name();
                if (strName.equals("tr")) {
                    htmlTreeBuilder.clearStackToTableBodyContext();
                    htmlTreeBuilder.insert(startTagAsStartTag);
                    htmlTreeBuilder.transition(HtmlTreeBuilderState.InRow);
                    return true;
                }
                if (!StringUtil.in(strName, "th", TimeDisplaySetting.TIME_DISPLAY)) {
                    return StringUtil.in(strName, "caption", "col", "colgroup", "tbody", "tfoot", "thead") ? exitTableBody(token, htmlTreeBuilder) : anythingElse(token, htmlTreeBuilder);
                }
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.process(new Token.StartTag("tr"));
                return htmlTreeBuilder.process(startTagAsStartTag);
            }
            if (i2 != 4) {
                return anythingElse(token, htmlTreeBuilder);
            }
            String strName2 = token.asEndTag().name();
            if (!StringUtil.in(strName2, "tbody", "tfoot", "thead")) {
                if (strName2.equals("table")) {
                    return exitTableBody(token, htmlTreeBuilder);
                }
                if (!StringUtil.in(strName2, TtmlNode.TAG_BODY, "caption", "col", "colgroup", "html", TimeDisplaySetting.TIME_DISPLAY, "th", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            if (!htmlTreeBuilder.inTableScope(strName2)) {
                htmlTreeBuilder.error(this);
                return false;
            }
            htmlTreeBuilder.clearStackToTableBodyContext();
            htmlTreeBuilder.pop();
            htmlTreeBuilder.transition(HtmlTreeBuilderState.InTable);
            return true;
        }
    },
    InRow { // from class: org.jsoup.parser.HtmlTreeBuilderState.14
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InTable);
        }

        private boolean handleMissingTr(Token token, TreeBuilder treeBuilder) {
            if (treeBuilder.process(new Token.EndTag("tr"))) {
                return treeBuilder.process(token);
            }
            return false;
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag()) {
                Token.StartTag startTagAsStartTag = token.asStartTag();
                String strName = startTagAsStartTag.name();
                if (!StringUtil.in(strName, "th", TimeDisplaySetting.TIME_DISPLAY)) {
                    return StringUtil.in(strName, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr") ? handleMissingTr(token, htmlTreeBuilder) : anythingElse(token, htmlTreeBuilder);
                }
                htmlTreeBuilder.clearStackToTableRowContext();
                htmlTreeBuilder.insert(startTagAsStartTag);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InCell);
                htmlTreeBuilder.insertMarkerToFormattingElements();
                return true;
            }
            if (!token.isEndTag()) {
                return anythingElse(token, htmlTreeBuilder);
            }
            String strName2 = token.asEndTag().name();
            if (strName2.equals("tr")) {
                if (!htmlTreeBuilder.inTableScope(strName2)) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.clearStackToTableRowContext();
                htmlTreeBuilder.pop();
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InTableBody);
                return true;
            }
            if (strName2.equals("table")) {
                return handleMissingTr(token, htmlTreeBuilder);
            }
            if (!StringUtil.in(strName2, "tbody", "tfoot", "thead")) {
                if (!StringUtil.in(strName2, TtmlNode.TAG_BODY, "caption", "col", "colgroup", "html", TimeDisplaySetting.TIME_DISPLAY, "th")) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            if (htmlTreeBuilder.inTableScope(strName2)) {
                htmlTreeBuilder.process(new Token.EndTag("tr"));
                return htmlTreeBuilder.process(token);
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    },
    InCell { // from class: org.jsoup.parser.HtmlTreeBuilderState.15
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
        }

        private void closeCell(HtmlTreeBuilder htmlTreeBuilder) {
            if (htmlTreeBuilder.inTableScope(TimeDisplaySetting.TIME_DISPLAY)) {
                htmlTreeBuilder.process(new Token.EndTag(TimeDisplaySetting.TIME_DISPLAY));
            } else {
                htmlTreeBuilder.process(new Token.EndTag("th"));
            }
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (!token.isEndTag()) {
                if (!token.isStartTag() || !StringUtil.in(token.asStartTag().name(), "caption", "col", "colgroup", "tbody", TimeDisplaySetting.TIME_DISPLAY, "tfoot", "th", "thead", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                if (htmlTreeBuilder.inTableScope(TimeDisplaySetting.TIME_DISPLAY) || htmlTreeBuilder.inTableScope("th")) {
                    closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            String strName = token.asEndTag().name();
            if (!StringUtil.in(strName, TimeDisplaySetting.TIME_DISPLAY, "th")) {
                if (StringUtil.in(strName, TtmlNode.TAG_BODY, "caption", "col", "colgroup", "html")) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                if (!StringUtil.in(strName, "table", "tbody", "tfoot", "thead", "tr")) {
                    return anythingElse(token, htmlTreeBuilder);
                }
                if (htmlTreeBuilder.inTableScope(strName)) {
                    closeCell(htmlTreeBuilder);
                    return htmlTreeBuilder.process(token);
                }
                htmlTreeBuilder.error(this);
                return false;
            }
            if (!htmlTreeBuilder.inTableScope(strName)) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.transition(HtmlTreeBuilderState.InRow);
                return false;
            }
            htmlTreeBuilder.generateImpliedEndTags();
            if (!htmlTreeBuilder.currentElement().nodeName().equals(strName)) {
                htmlTreeBuilder.error(this);
            }
            htmlTreeBuilder.popStackToClose(strName);
            htmlTreeBuilder.clearFormattingElementsToLastMarker();
            htmlTreeBuilder.transition(HtmlTreeBuilderState.InRow);
            return true;
        }
    },
    InSelect { // from class: org.jsoup.parser.HtmlTreeBuilderState.16
        private boolean anythingElse(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            htmlTreeBuilder.error(this);
            return false;
        }

        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            switch (AnonymousClass24.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
                case 1:
                    htmlTreeBuilder.insert(token.asComment());
                    return true;
                case 2:
                    htmlTreeBuilder.error(this);
                    return false;
                case 3:
                    Token.StartTag startTagAsStartTag = token.asStartTag();
                    String strName = startTagAsStartTag.name();
                    if (strName.equals("html")) {
                        return htmlTreeBuilder.process(startTagAsStartTag, HtmlTreeBuilderState.InBody);
                    }
                    if (strName.equals(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION)) {
                        htmlTreeBuilder.process(new Token.EndTag(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION));
                        htmlTreeBuilder.insert(startTagAsStartTag);
                        return true;
                    }
                    if (strName.equals("optgroup")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION)) {
                            htmlTreeBuilder.process(new Token.EndTag(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION));
                        } else if (htmlTreeBuilder.currentElement().nodeName().equals("optgroup")) {
                            htmlTreeBuilder.process(new Token.EndTag("optgroup"));
                        }
                        htmlTreeBuilder.insert(startTagAsStartTag);
                        return true;
                    }
                    if (strName.equals("select")) {
                        htmlTreeBuilder.error(this);
                        return htmlTreeBuilder.process(new Token.EndTag("select"));
                    }
                    if (!StringUtil.in(strName, "input", "keygen", "textarea")) {
                        return strName.equals("script") ? htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead) : anythingElse(token, htmlTreeBuilder);
                    }
                    htmlTreeBuilder.error(this);
                    if (!htmlTreeBuilder.inSelectScope("select")) {
                        return false;
                    }
                    htmlTreeBuilder.process(new Token.EndTag("select"));
                    return htmlTreeBuilder.process(startTagAsStartTag);
                case 4:
                    String strName2 = token.asEndTag().name();
                    if (strName2.equals("optgroup")) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION) && htmlTreeBuilder.aboveOnStack(htmlTreeBuilder.currentElement()) != null && htmlTreeBuilder.aboveOnStack(htmlTreeBuilder.currentElement()).nodeName().equals("optgroup")) {
                            htmlTreeBuilder.process(new Token.EndTag(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION));
                        }
                        if (htmlTreeBuilder.currentElement().nodeName().equals("optgroup")) {
                            htmlTreeBuilder.pop();
                            return true;
                        }
                        htmlTreeBuilder.error(this);
                        return true;
                    }
                    if (strName2.equals(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION)) {
                        if (htmlTreeBuilder.currentElement().nodeName().equals(Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION)) {
                            htmlTreeBuilder.pop();
                            return true;
                        }
                        htmlTreeBuilder.error(this);
                        return true;
                    }
                    if (!strName2.equals("select")) {
                        return anythingElse(token, htmlTreeBuilder);
                    }
                    if (!htmlTreeBuilder.inSelectScope(strName2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.popStackToClose(strName2);
                    htmlTreeBuilder.resetInsertionMode();
                    return true;
                case 5:
                    Token.Character characterAsCharacter = token.asCharacter();
                    if (characterAsCharacter.getData().equals(HtmlTreeBuilderState.nullString)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.insert(characterAsCharacter);
                    return true;
                case 6:
                    if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                        return true;
                    }
                    htmlTreeBuilder.error(this);
                    return true;
                default:
                    return anythingElse(token, htmlTreeBuilder);
            }
        }
    },
    InSelectInTable { // from class: org.jsoup.parser.HtmlTreeBuilderState.17
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isStartTag() && StringUtil.in(token.asStartTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", TimeDisplaySetting.TIME_DISPLAY, "th")) {
                htmlTreeBuilder.error(this);
                htmlTreeBuilder.process(new Token.EndTag("select"));
                return htmlTreeBuilder.process(token);
            }
            if (!token.isEndTag() || !StringUtil.in(token.asEndTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", TimeDisplaySetting.TIME_DISPLAY, "th")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InSelect);
            }
            htmlTreeBuilder.error(this);
            if (!htmlTreeBuilder.inTableScope(token.asEndTag().name())) {
                return false;
            }
            htmlTreeBuilder.process(new Token.EndTag("select"));
            return htmlTreeBuilder.process(token);
        }
    },
    AfterBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.18
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("html")) {
                if (htmlTreeBuilder.isFragmentParsing()) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterAfterBody);
                return true;
            }
            if (token.isEOF()) {
                return true;
            }
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.transition(HtmlTreeBuilderState.InBody);
            return htmlTreeBuilder.process(token);
        }
    },
    InFrameset { // from class: org.jsoup.parser.HtmlTreeBuilderState.19
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
            } else if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
            } else {
                if (token.isDoctype()) {
                    htmlTreeBuilder.error(this);
                    return false;
                }
                if (token.isStartTag()) {
                    Token.StartTag startTagAsStartTag = token.asStartTag();
                    String strName = startTagAsStartTag.name();
                    if (strName.equals("html")) {
                        return htmlTreeBuilder.process(startTagAsStartTag, HtmlTreeBuilderState.InBody);
                    }
                    if (strName.equals("frameset")) {
                        htmlTreeBuilder.insert(startTagAsStartTag);
                    } else {
                        if (!strName.equals(TypedValues.AttributesType.S_FRAME)) {
                            if (strName.equals("noframes")) {
                                return htmlTreeBuilder.process(startTagAsStartTag, HtmlTreeBuilderState.InHead);
                            }
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        htmlTreeBuilder.insertEmpty(startTagAsStartTag);
                    }
                } else if (token.isEndTag() && token.asEndTag().name().equals("frameset")) {
                    if (htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.pop();
                    if (!htmlTreeBuilder.isFragmentParsing() && !htmlTreeBuilder.currentElement().nodeName().equals("frameset")) {
                        htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterFrameset);
                    }
                } else {
                    if (!token.isEOF()) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    if (!htmlTreeBuilder.currentElement().nodeName().equals("html")) {
                        htmlTreeBuilder.error(this);
                    }
                }
            }
            return true;
        }
    },
    AfterFrameset { // from class: org.jsoup.parser.HtmlTreeBuilderState.20
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (HtmlTreeBuilderState.isWhitespace(token)) {
                htmlTreeBuilder.insert(token.asCharacter());
                return true;
            }
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("html")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isEndTag() && token.asEndTag().name().equals("html")) {
                htmlTreeBuilder.transition(HtmlTreeBuilderState.AfterAfterFrameset);
                return true;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("noframes")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
            }
            if (token.isEOF()) {
                return true;
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    },
    AfterAfterBody { // from class: org.jsoup.parser.HtmlTreeBuilderState.21
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || (token.isStartTag() && token.asStartTag().name().equals("html"))) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isEOF()) {
                return true;
            }
            htmlTreeBuilder.error(this);
            htmlTreeBuilder.transition(HtmlTreeBuilderState.InBody);
            return htmlTreeBuilder.process(token);
        }
    },
    AfterAfterFrameset { // from class: org.jsoup.parser.HtmlTreeBuilderState.22
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            if (token.isComment()) {
                htmlTreeBuilder.insert(token.asComment());
                return true;
            }
            if (token.isDoctype() || HtmlTreeBuilderState.isWhitespace(token) || (token.isStartTag() && token.asStartTag().name().equals("html"))) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InBody);
            }
            if (token.isEOF()) {
                return true;
            }
            if (token.isStartTag() && token.asStartTag().name().equals("noframes")) {
                return htmlTreeBuilder.process(token, HtmlTreeBuilderState.InHead);
            }
            htmlTreeBuilder.error(this);
            return false;
        }
    },
    ForeignContent { // from class: org.jsoup.parser.HtmlTreeBuilderState.23
        @Override // org.jsoup.parser.HtmlTreeBuilderState
        public boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder) {
            return true;
        }
    };

    private static String nullString = String.valueOf((char) 0);

    /* renamed from: org.jsoup.parser.HtmlTreeBuilderState$24, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass24 {
        static final /* synthetic */ int[] $SwitchMap$org$jsoup$parser$Token$TokenType;

        static {
            int[] iArr = new int[Token.TokenType.values().length];
            $SwitchMap$org$jsoup$parser$Token$TokenType = iArr;
            try {
                iArr[Token.TokenType.Comment.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Doctype.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.StartTag.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.EndTag.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Character.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.EOF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static final class Constants {
        private static final String[] InBodyStartToHead = {TtmlNode.RUBY_BASE, "basefont", "bgsound", b.f7200y, PLVErrorCodeLinkMicBase.LINK_MODULE, "meta", "noframes", "script", TtmlNode.TAG_STYLE, "title"};
        private static final String[] InBodyStartPClosers = {"address", "article", "aside", "blockquote", TtmlNode.CENTER, ErrorBundle.DETAIL_ENTRY, "dir", TtmlNode.TAG_DIV, "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul"};
        private static final String[] Headings = {"h1", "h2", "h3", "h4", "h5", "h6"};
        private static final String[] InBodyStartPreListing = {"pre", "listing"};
        private static final String[] InBodyStartLiBreakers = {"address", TtmlNode.TAG_DIV, "p"};
        private static final String[] DdDt = {"dd", SocializeProtocolConstants.PROTOCOL_KEY_DT};
        private static final String[] Formatters = {"b", "big", "code", "em", "font", am.aC, "s", "small", "strike", "strong", "tt", am.aG};
        private static final String[] InBodyStartApplets = {"applet", PLVBaseVideoParams.MARQUEE, "object"};
        private static final String[] InBodyStartEmptyFormatters = {"area", "br", "embed", "img", "keygen", "wbr"};
        private static final String[] InBodyStartMedia = {"param", SocialConstants.PARAM_SOURCE, "track"};
        private static final String[] InBodyStartInputAttribs = {"name", "action", "prompt"};
        private static final String[] InBodyStartOptions = {"optgroup", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION};
        private static final String[] InBodyStartRuby = {AliyunLogKey.KEY_RESOURCE_PATH, "rt"};
        private static final String[] InBodyStartDrop = {"caption", "col", "colgroup", TypedValues.AttributesType.S_FRAME, TtmlNode.TAG_HEAD, "tbody", TimeDisplaySetting.TIME_DISPLAY, "tfoot", "th", "thead", "tr"};
        private static final String[] InBodyEndClosers = {"address", "article", "aside", "blockquote", "button", TtmlNode.CENTER, ErrorBundle.DETAIL_ENTRY, "dir", TtmlNode.TAG_DIV, "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul"};
        private static final String[] InBodyEndAdoptionFormatters = {am.av, "b", "big", "code", "em", "font", am.aC, "nobr", "s", "small", "strike", "strong", "tt", am.aG};
        private static final String[] InBodyEndTableFosters = {"table", "tbody", "tfoot", "thead", "tr"};

        private Constants() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.insert(startTag);
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rawtext);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder htmlTreeBuilder) {
        htmlTreeBuilder.insert(startTag);
        htmlTreeBuilder.tokeniser.transition(TokeniserState.Rcdata);
        htmlTreeBuilder.markInsertionMode();
        htmlTreeBuilder.transition(Text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWhitespace(Token token) {
        if (!token.isCharacter()) {
            return false;
        }
        String data = token.asCharacter().getData();
        for (int i2 = 0; i2 < data.length(); i2++) {
            if (!StringUtil.isWhitespace(data.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder);
}
