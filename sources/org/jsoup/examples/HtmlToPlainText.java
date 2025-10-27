package org.jsoup.examples;

import com.caverock.androidsvg.SVGParser;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.umeng.analytics.pro.am;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

/* loaded from: classes9.dex */
public class HtmlToPlainText {

    public class FormattingVisitor implements NodeVisitor {
        private static final int maxWidth = 80;
        private StringBuilder accum;
        private int width;

        private FormattingVisitor() {
            this.width = 0;
            this.accum = new StringBuilder();
        }

        private void append(String str) {
            if (str.startsWith("\n")) {
                this.width = 0;
            }
            if (str.equals(" ")) {
                if (this.accum.length() == 0) {
                    return;
                }
                StringBuilder sb = this.accum;
                if (StringUtil.in(sb.substring(sb.length() - 1), " ", "\n")) {
                    return;
                }
            }
            if (str.length() + this.width <= 80) {
                this.accum.append(str);
                this.width += str.length();
                return;
            }
            String[] strArrSplit = str.split("\\s+");
            int i2 = 0;
            while (i2 < strArrSplit.length) {
                String str2 = strArrSplit[i2];
                if (!(i2 == strArrSplit.length - 1)) {
                    str2 = str2 + " ";
                }
                if (str2.length() + this.width > 80) {
                    StringBuilder sb2 = this.accum;
                    sb2.append("\n");
                    sb2.append(str2);
                    this.width = str2.length();
                } else {
                    this.accum.append(str2);
                    this.width += str2.length();
                }
                i2++;
            }
        }

        @Override // org.jsoup.select.NodeVisitor
        public void head(Node node, int i2) {
            String strNodeName = node.nodeName();
            if (node instanceof TextNode) {
                append(((TextNode) node).text());
            } else if (strNodeName.equals(AppIconSetting.LARGE_ICON_URL)) {
                append("\n * ");
            }
        }

        @Override // org.jsoup.select.NodeVisitor
        public void tail(Node node, int i2) {
            String strNodeName = node.nodeName();
            if (strNodeName.equals("br")) {
                append("\n");
            } else if (StringUtil.in(strNodeName, "p", "h1", "h2", "h3", "h4", "h5")) {
                append("\n\n");
            } else if (strNodeName.equals(am.av)) {
                append(String.format(" <%s>", node.absUrl(SVGParser.XML_STYLESHEET_ATTR_HREF)));
            }
        }

        public String toString() {
            return this.accum.toString();
        }
    }

    public static void main(String... strArr) throws IOException {
        Validate.isTrue(strArr.length == 1, "usage: supply url to fetch");
        System.out.println(new HtmlToPlainText().getPlainText(Jsoup.connect(strArr[0]).get()));
    }

    public String getPlainText(Element element) {
        FormattingVisitor formattingVisitor = new FormattingVisitor();
        new NodeTraversor(formattingVisitor).traverse(element);
        return formattingVisitor.toString();
    }
}
