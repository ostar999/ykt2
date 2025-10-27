package org.jsoup.nodes;

import org.jsoup.nodes.Document;

/* loaded from: classes9.dex */
public class Comment extends Node {
    private static final String COMMENT_KEY = "comment";

    public Comment(String str, String str2) {
        super(str2);
        this.attributes.put("comment", str);
    }

    public String getData() {
        return this.attributes.get("comment");
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#comment";
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlHead(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        if (outputSettings.prettyPrint()) {
            indent(sb, i2, outputSettings);
        }
        sb.append("<!--");
        sb.append(getData());
        sb.append("-->");
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlTail(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }
}
