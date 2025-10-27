package org.jsoup.parser;

import java.util.Iterator;
import java.util.List;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Token;

/* loaded from: classes9.dex */
public class XmlTreeBuilder extends TreeBuilder {

    /* renamed from: org.jsoup.parser.XmlTreeBuilder$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jsoup$parser$Token$TokenType;

        static {
            int[] iArr = new int[Token.TokenType.values().length];
            $SwitchMap$org$jsoup$parser$Token$TokenType = iArr;
            try {
                iArr[Token.TokenType.StartTag.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.EndTag.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Comment.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Character.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.Doctype.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$jsoup$parser$Token$TokenType[Token.TokenType.EOF.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private void insertNode(Node node) {
        currentElement().appendChild(node);
    }

    private void popStackToClose(Token.EndTag endTag) {
        Element next;
        String strName = endTag.name();
        Iterator<Element> itDescendingIterator = this.stack.descendingIterator();
        while (true) {
            if (!itDescendingIterator.hasNext()) {
                next = null;
                break;
            } else {
                next = itDescendingIterator.next();
                if (next.nodeName().equals(strName)) {
                    break;
                }
            }
        }
        if (next == null) {
            return;
        }
        Iterator<Element> itDescendingIterator2 = this.stack.descendingIterator();
        while (itDescendingIterator2.hasNext()) {
            if (itDescendingIterator2.next() == next) {
                itDescendingIterator2.remove();
                return;
            }
            itDescendingIterator2.remove();
        }
    }

    @Override // org.jsoup.parser.TreeBuilder
    public void initialiseParse(String str, String str2, ParseErrorList parseErrorList) {
        super.initialiseParse(str, str2, parseErrorList);
        this.stack.add(this.doc);
        this.doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
    }

    public Element insert(Token.StartTag startTag) {
        Tag tagValueOf = Tag.valueOf(startTag.name());
        Element element = new Element(tagValueOf, this.baseUri, startTag.attributes);
        insertNode(element);
        if (startTag.isSelfClosing()) {
            this.tokeniser.acknowledgeSelfClosingFlag();
            if (!tagValueOf.isKnownTag()) {
                tagValueOf.setSelfClosing();
            }
        } else {
            this.stack.add(element);
        }
        return element;
    }

    public List<Node> parseFragment(String str, String str2, ParseErrorList parseErrorList) {
        initialiseParse(str, str2, parseErrorList);
        runParser();
        return this.doc.childNodes();
    }

    @Override // org.jsoup.parser.TreeBuilder
    public boolean process(Token token) {
        switch (AnonymousClass1.$SwitchMap$org$jsoup$parser$Token$TokenType[token.type.ordinal()]) {
            case 1:
                insert(token.asStartTag());
                break;
            case 2:
                popStackToClose(token.asEndTag());
                break;
            case 3:
                insert(token.asComment());
                break;
            case 4:
                insert(token.asCharacter());
                break;
            case 5:
                insert(token.asDoctype());
                break;
            case 6:
                break;
            default:
                Validate.fail("Unexpected token type: " + token.type);
                break;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [org.jsoup.nodes.XmlDeclaration] */
    /* JADX WARN: Type inference failed for: r4v0, types: [org.jsoup.parser.TreeBuilder, org.jsoup.parser.XmlTreeBuilder] */
    public void insert(Token.Comment comment) {
        Comment comment2 = new Comment(comment.getData(), this.baseUri);
        if (comment.bogus) {
            String data = comment2.getData();
            if (data.length() > 1 && (data.startsWith("!") || data.startsWith("?"))) {
                comment2 = new XmlDeclaration(data.substring(1), comment2.baseUri(), data.startsWith("!"));
            }
        }
        insertNode(comment2);
    }

    public void insert(Token.Character character) {
        insertNode(new TextNode(character.getData(), this.baseUri));
    }

    public void insert(Token.Doctype doctype) {
        insertNode(new DocumentType(doctype.getName(), doctype.getPublicIdentifier(), doctype.getSystemIdentifier(), this.baseUri));
    }
}
