package org.jsoup.nodes;

import kotlin.text.Typography;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

/* loaded from: classes9.dex */
public class DocumentType extends Node {
    public DocumentType(String str, String str2, String str3, String str4) {
        super(str4);
        attr("name", str);
        attr("publicId", str2);
        attr("systemId", str3);
    }

    @Override // org.jsoup.nodes.Node
    public String nodeName() {
        return "#doctype";
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlHead(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
        sb.append("<!DOCTYPE");
        if (!StringUtil.isBlank(attr("name"))) {
            sb.append(" ");
            sb.append(attr("name"));
        }
        if (!StringUtil.isBlank(attr("publicId"))) {
            sb.append(" PUBLIC \"");
            sb.append(attr("publicId"));
            sb.append('\"');
        }
        if (!StringUtil.isBlank(attr("systemId"))) {
            sb.append(" \"");
            sb.append(attr("systemId"));
            sb.append('\"');
        }
        sb.append(Typography.greater);
    }

    @Override // org.jsoup.nodes.Node
    public void outerHtmlTail(StringBuilder sb, int i2, Document.OutputSettings outputSettings) {
    }
}
