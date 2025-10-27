package org.jsoup.nodes;

import org.jsoup.nodes.Document;

/* loaded from: classes9.dex */
public class XmlDeclaration extends Node {
    private static final String DECL_KEY = "declaration";
    private final boolean isProcessingInstruction;

    public XmlDeclaration(String str, String str2, boolean z2) {
        super(str2);
        this.attributes.put(DECL_KEY, str);
        this.isProcessingInstruction = z2;
    }

    public String getWholeDeclaration() {
        return this.attributes.get(DECL_KEY);
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#declaration";
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlHead(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        sb.append("<");
        sb.append(this.isProcessingInstruction ? "!" : "?");
        sb.append(getWholeDeclaration());
        sb.append(">");
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlTail(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }
}
