package org.jsoup.nodes;

import org.jsoup.nodes.Document;

/* loaded from: classes9.dex */
public class DataNode extends Node {
    private static final String DATA_KEY = "data";

    public DataNode(String str, String str2) {
        super(str2);
        this.attributes.put("data", str);
    }

    public static DataNode createFromEncoded(String str, String str2) {
        return new DataNode(Entities.unescape(str), str2);
    }

    public String getWholeData() {
        return this.attributes.get("data");
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#data";
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlHead(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        sb.append(getWholeData());
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlTail(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
    }

    public DataNode setWholeData(String str) {
        this.attributes.put("data", str);
        return this;
    }

    @Override // org.jsoup.nodes.Node
    public String toString() {
        return outerHtml();
    }
}
