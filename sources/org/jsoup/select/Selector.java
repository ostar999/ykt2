package org.jsoup.select;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;

/* loaded from: classes9.dex */
public class Selector {
    private final Evaluator evaluator;
    private final Element root;

    public static class SelectorParseException extends IllegalStateException {
        public SelectorParseException(String str, Object... objArr) {
            super(String.format(str, objArr));
        }
    }

    private Selector(String str, Element element) {
        Validate.notNull(str);
        String strTrim = str.trim();
        Validate.notEmpty(strTrim);
        Validate.notNull(element);
        this.evaluator = QueryParser.parse(strTrim);
        this.root = element;
    }

    public static Elements filterOut(Collection<Element> collection, Collection<Element> collection2) {
        boolean z2;
        Elements elements = new Elements();
        for (Element element : collection) {
            Iterator<Element> it = collection2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z2 = false;
                    break;
                }
                if (element.equals(it.next())) {
                    z2 = true;
                    break;
                }
            }
            if (!z2) {
                elements.add(element);
            }
        }
        return elements;
    }

    public static Elements select(String str, Element element) {
        return new Selector(str, element).select();
    }

    public static Elements select(String str, Iterable<Element> iterable) {
        Validate.notEmpty(str);
        Validate.notNull(iterable);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<Element> it = iterable.iterator();
        while (it.hasNext()) {
            linkedHashSet.addAll(select(str, it.next()));
        }
        return new Elements(linkedHashSet);
    }

    private Elements select() {
        return Collector.collect(this.evaluator, this.root);
    }
}
