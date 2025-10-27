package org.jsoup.parser;

import org.jsoup.helper.DescendableLinkedList;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Token;

/* loaded from: classes9.dex */
abstract class TreeBuilder {
    protected String baseUri;
    protected Token currentToken;
    protected Document doc;
    protected ParseErrorList errors;
    CharacterReader reader;
    protected DescendableLinkedList<Element> stack;
    Tokeniser tokeniser;

    public Element currentElement() {
        return this.stack.getLast();
    }

    public void initialiseParse(String str, String str2, ParseErrorList parseErrorList) {
        Validate.notNull(str, "String input must not be null");
        Validate.notNull(str2, "BaseURI must not be null");
        this.doc = new Document(str2);
        CharacterReader characterReader = new CharacterReader(str);
        this.reader = characterReader;
        this.errors = parseErrorList;
        this.tokeniser = new Tokeniser(characterReader, parseErrorList);
        this.stack = new DescendableLinkedList<>();
        this.baseUri = str2;
    }

    public Document parse(String str, String str2) {
        return parse(str, str2, ParseErrorList.noTracking());
    }

    public abstract boolean process(Token token);

    public void runParser() {
        Token token;
        do {
            token = this.tokeniser.read();
            process(token);
        } while (token.type != Token.TokenType.EOF);
    }

    public Document parse(String str, String str2, ParseErrorList parseErrorList) {
        initialiseParse(str, str2, parseErrorList);
        runParser();
        return this.doc;
    }
}
