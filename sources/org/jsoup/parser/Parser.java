package org.jsoup.parser;

import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

/* loaded from: classes9.dex */
public class Parser {
    private static final int DEFAULT_MAX_ERRORS = 0;
    private ParseErrorList errors;
    private int maxErrors = 0;
    private TreeBuilder treeBuilder;

    public Parser(TreeBuilder treeBuilder) {
        this.treeBuilder = treeBuilder;
    }

    public static Parser htmlParser() {
        return new Parser(new HtmlTreeBuilder());
    }

    public static Document parse(String str, String str2) {
        return new HtmlTreeBuilder().parse(str, str2, ParseErrorList.noTracking());
    }

    public static Document parseBodyFragment(String str, String str2) {
        Document documentCreateShell = Document.createShell(str2);
        Element elementBody = documentCreateShell.body();
        List<Node> fragment = parseFragment(str, elementBody, str2);
        for (Node node : (Node[]) fragment.toArray(new Node[fragment.size()])) {
            elementBody.appendChild(node);
        }
        return documentCreateShell;
    }

    public static Document parseBodyFragmentRelaxed(String str, String str2) {
        return parse(str, str2);
    }

    public static List<Node> parseFragment(String str, Element element, String str2) {
        return new HtmlTreeBuilder().parseFragment(str, element, str2, ParseErrorList.noTracking());
    }

    public static List<Node> parseXmlFragment(String str, String str2) {
        return new XmlTreeBuilder().parseFragment(str, str2, ParseErrorList.noTracking());
    }

    public static String unescapeEntities(String str, boolean z2) {
        return new Tokeniser(new CharacterReader(str), ParseErrorList.noTracking()).unescapeEntities(z2);
    }

    public static Parser xmlParser() {
        return new Parser(new XmlTreeBuilder());
    }

    public List<ParseError> getErrors() {
        return this.errors;
    }

    public TreeBuilder getTreeBuilder() {
        return this.treeBuilder;
    }

    public boolean isTrackErrors() {
        return this.maxErrors > 0;
    }

    public Document parseInput(String str, String str2) {
        ParseErrorList parseErrorListTracking = isTrackErrors() ? ParseErrorList.tracking(this.maxErrors) : ParseErrorList.noTracking();
        this.errors = parseErrorListTracking;
        return this.treeBuilder.parse(str, str2, parseErrorListTracking);
    }

    public Parser setTrackErrors(int i2) {
        this.maxErrors = i2;
        return this;
    }

    public Parser setTreeBuilder(TreeBuilder treeBuilder) {
        this.treeBuilder = treeBuilder;
        return this;
    }
}
