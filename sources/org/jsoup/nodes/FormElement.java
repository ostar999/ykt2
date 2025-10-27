package org.jsoup.nodes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

/* loaded from: classes9.dex */
public class FormElement extends Element {
    private final Elements elements;

    public FormElement(Tag tag, String str, Attributes attributes) {
        super(tag, str, attributes);
        this.elements = new Elements();
    }

    public FormElement addElement(Element element) {
        this.elements.add(element);
        return this;
    }

    public Elements elements() {
        return this.elements;
    }

    @Override // org.jsoup.nodes.Element, org.jsoup.nodes.Node
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public List<Connection.KeyVal> formData() {
        ArrayList arrayList = new ArrayList();
        Iterator<Element> it = this.elements.iterator();
        while (it.hasNext()) {
            Element next = it.next();
            if (next.tag().isFormSubmittable()) {
                String strAttr = next.attr("name");
                if (strAttr.length() != 0) {
                    if ("select".equals(next.tagName())) {
                        Iterator<Element> it2 = next.select("option[selected]").iterator();
                        while (it2.hasNext()) {
                            arrayList.add(HttpConnection.KeyVal.create(strAttr, it2.next().val()));
                        }
                    } else {
                        arrayList.add(HttpConnection.KeyVal.create(strAttr, next.val()));
                    }
                }
            }
        }
        return arrayList;
    }

    public Connection submit() {
        String strAbsUrl = hasAttr("action") ? absUrl("action") : baseUri();
        Validate.notEmpty(strAbsUrl, "Could not determine a form action URL for submit. Ensure you set a base URI when parsing.");
        return Jsoup.connect(strAbsUrl).data(formData()).method(attr("method").toUpperCase().equals("POST") ? Connection.Method.POST : Connection.Method.GET);
    }
}
